package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NoConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import sun.net.NetHooks;
import sun.net.ext.ExtendedSocketOptions;
import sun.net.util.SocketExceptions;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SocketChannelImpl extends SocketChannel implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CLOSING = 3;
    private static final int ST_CONNECTED = 2;
    private static final int ST_CONNECTIONPENDING = 1;
    private static final int ST_KILLED = 5;
    private static final int ST_KILLPENDING = 4;
    private static final int ST_UNCONNECTED = 0;
    private static NativeDispatcher nd;
    private final FileDescriptor fd;
    private final int fdVal;
    private volatile boolean isInputClosed;
    private volatile boolean isOutputClosed;
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private final ReentrantLock readLock;
    private long readerThread;
    private InetSocketAddress remoteAddress;
    private Socket socket;
    private volatile int state;
    private final Object stateLock;
    private final ReentrantLock writeLock;
    private long writerThread;

    private static native int checkConnect(FileDescriptor fileDescriptor, boolean z) throws IOException;

    private static native int sendOutOfBandData(FileDescriptor fileDescriptor, byte b) throws IOException;

    SocketChannelImpl(SelectorProvider selectorProvider) throws IOException {
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        this.stateLock = new Object();
        FileDescriptor socket = Net.socket(true);
        this.fd = socket;
        this.fdVal = IOUtil.fdVal(socket);
    }

    SocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, boolean z) throws IOException {
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        Object obj = new Object();
        this.stateLock = obj;
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        if (z) {
            synchronized (obj) {
                this.localAddress = Net.localAddress(fileDescriptor);
            }
        }
    }

    SocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        Object obj = new Object();
        this.stateLock = obj;
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        synchronized (obj) {
            this.localAddress = Net.localAddress(fileDescriptor);
            this.remoteAddress = inetSocketAddress;
            this.state = 2;
        }
    }

    private void ensureOpen() throws ClosedChannelException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    private void ensureOpenAndConnected() throws ClosedChannelException {
        int i = this.state;
        if (i < 2) {
            throw new NotYetConnectedException();
        }
        if (i > 2) {
            throw new ClosedChannelException();
        }
    }

    public Socket socket() {
        Socket socket;
        synchronized (this.stateLock) {
            if (this.socket == null) {
                this.socket = SocketAdaptor.create(this);
            }
            socket = this.socket;
        }
        return socket;
    }

    public SocketAddress getLocalAddress() throws IOException {
        InetSocketAddress revealedLocalAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            revealedLocalAddress = Net.getRevealedLocalAddress(this.localAddress);
        }
        return revealedLocalAddress;
    }

    public SocketAddress getRemoteAddress() throws IOException {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            inetSocketAddress = this.remoteAddress;
        }
        return inetSocketAddress;
    }

    public SocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (socketOption == StandardSocketOptions.IP_TOS) {
                Net.setSocketOption(this.fd, Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET, socketOption, obj);
                return this;
            }
            if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                this.isReuseAddress = ((Boolean) obj).booleanValue();
                return this;
            }
            Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, obj);
            return this;
        }
    }

    public Object getOption(SocketOption socketOption) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                return Boolean.valueOf(this.isReuseAddress);
            }
            if (socketOption == StandardSocketOptions.IP_TOS) {
                return Net.getSocketOption(this.fd, Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET, socketOption);
            }
            return Net.getSocketOption(this.fd, Net.UNSPEC, socketOption);
        }
    }

    private static class DefaultOptionsHolder {
        static final Set defaultOptions = defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set defaultOptions() {
            HashSet hashSet = new HashSet();
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            if (Net.isReusePortAvailable()) {
                hashSet.add(StandardSocketOptions.SO_REUSEPORT);
            }
            hashSet.add(StandardSocketOptions.SO_LINGER);
            hashSet.add(StandardSocketOptions.TCP_NODELAY);
            hashSet.add(StandardSocketOptions.IP_TOS);
            hashSet.add(ExtendedSocketOption.SO_OOBINLINE);
            hashSet.addAll(ExtendedSocketOptions.options((short) 1));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public final Set supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    private void beginRead(boolean z) throws ClosedChannelException {
        if (z) {
            begin();
            synchronized (this.stateLock) {
                ensureOpenAndConnected();
                this.readerThread = NativeThread.current();
            }
            return;
        }
        ensureOpenAndConnected();
    }

    private void endRead(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.readerThread = 0L;
                if (this.state == 3) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        int i;
        byteBuffer.getClass();
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking);
                if (this.isInputClosed) {
                    endRead(isBlocking, false);
                    boolean z = this.isInputClosed;
                } else {
                    if (isBlocking) {
                        i = 0;
                        do {
                            try {
                                i = IOUtil.read(this.fd, byteBuffer, -1L, nd);
                                if (i != -3) {
                                    break;
                                }
                            } catch (Throwable th) {
                                th = th;
                                endRead(isBlocking, i > 0);
                                if (i > 0 || !this.isInputClosed) {
                                    throw th;
                                }
                                return -1;
                            }
                        } while (isOpen());
                    } else {
                        i = IOUtil.read(this.fd, byteBuffer, -1L, nd);
                    }
                    endRead(isBlocking, i > 0);
                    if (i > 0 || !this.isInputClosed) {
                        return IOStatus.normalize(i);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
            return -1;
        } finally {
            this.readLock.unlock();
        }
    }

    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        SocketChannelImpl$$ExternalSyntheticBackport0.m(i, i2, byteBufferArr.length);
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking);
                if (this.isInputClosed) {
                    endRead(isBlocking, false);
                    boolean z = this.isInputClosed;
                } else {
                    if (isBlocking) {
                        j = 0;
                        do {
                            try {
                                j = IOUtil.read(this.fd, byteBufferArr, i, i2, nd);
                                if (j != -3) {
                                    break;
                                }
                            } catch (Throwable th) {
                                th = th;
                                endRead(isBlocking, j > 0);
                                if (j > 0 || !this.isInputClosed) {
                                    throw th;
                                }
                                return -1L;
                            }
                        } while (isOpen());
                    } else {
                        j = IOUtil.read(this.fd, byteBufferArr, i, i2, nd);
                    }
                    endRead(isBlocking, j > 0);
                    if (j > 0 || !this.isInputClosed) {
                        return IOStatus.normalize(j);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
            return -1L;
        } finally {
            this.readLock.unlock();
        }
    }

    private void beginWrite(boolean z) throws ClosedChannelException {
        if (z) {
            begin();
            synchronized (this.stateLock) {
                ensureOpenAndConnected();
                if (this.isOutputClosed) {
                    throw new ClosedChannelException();
                }
                this.writerThread = NativeThread.current();
            }
            return;
        }
        ensureOpenAndConnected();
    }

    private void endWrite(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.writerThread = 0L;
                if (this.state == 3) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        int i;
        byteBuffer.getClass();
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginWrite(isBlocking);
                if (isBlocking) {
                    i = 0;
                    do {
                        try {
                            i = IOUtil.write(this.fd, byteBuffer, -1L, nd);
                            if (i != -3) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            endWrite(isBlocking, i > 0);
                            if (i <= 0 && this.isOutputClosed) {
                                throw new AsynchronousCloseException();
                            }
                            throw th;
                        }
                    } while (isOpen());
                } else {
                    i = IOUtil.write(this.fd, byteBuffer, -1L, nd);
                }
                endWrite(isBlocking, i > 0);
                if (i <= 0 && this.isOutputClosed) {
                    throw new AsynchronousCloseException();
                }
                return IOStatus.normalize(i);
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    public long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        SocketChannelImpl$$ExternalSyntheticBackport0.m(i, i2, byteBufferArr.length);
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginWrite(isBlocking);
                if (isBlocking) {
                    j = 0;
                    do {
                        try {
                            j = IOUtil.write(this.fd, byteBufferArr, i, i2, nd);
                            if (j != -3) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            endWrite(isBlocking, j > 0);
                            if (j <= 0 && this.isOutputClosed) {
                                throw new AsynchronousCloseException();
                            }
                            throw th;
                        }
                    } while (isOpen());
                } else {
                    j = IOUtil.write(this.fd, byteBufferArr, i, i2, nd);
                }
                endWrite(isBlocking, j > 0);
                if (j <= 0 && this.isOutputClosed) {
                    throw new AsynchronousCloseException();
                }
                return IOStatus.normalize(j);
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    int sendOutOfBandData(byte b) throws IOException {
        int i;
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginWrite(isBlocking);
                if (isBlocking) {
                    i = 0;
                    do {
                        try {
                            i = sendOutOfBandData(this.fd, b);
                            if (i != -3) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            endWrite(isBlocking, i > 0);
                            if (i <= 0 && this.isOutputClosed) {
                                throw new AsynchronousCloseException();
                            }
                            throw th;
                        }
                    } while (isOpen());
                } else {
                    i = sendOutOfBandData(this.fd, b);
                }
                endWrite(isBlocking, i > 0);
                if (i <= 0 && this.isOutputClosed) {
                    throw new AsynchronousCloseException();
                }
                return IOStatus.normalize(i);
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    protected void implConfigureBlocking(boolean z) throws IOException {
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    ensureOpen();
                    IOUtil.configureBlocking(this.fd, z);
                }
            } finally {
                this.writeLock.unlock();
            }
        } finally {
            this.readLock.unlock();
        }
    }

    InetSocketAddress localAddress() {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            inetSocketAddress = this.localAddress;
        }
        return inetSocketAddress;
    }

    InetSocketAddress remoteAddress() {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            inetSocketAddress = this.remoteAddress;
        }
        return inetSocketAddress;
    }

    public SocketChannel bind(SocketAddress socketAddress) throws IOException {
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    ensureOpen();
                    if (this.state == 1) {
                        throw new ConnectionPendingException();
                    }
                    if (this.localAddress != null) {
                        throw new AlreadyBoundException();
                    }
                    InetSocketAddress inetSocketAddress = socketAddress == null ? new InetSocketAddress(0) : Net.checkAddress(socketAddress);
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkListen(inetSocketAddress.getPort());
                    }
                    NetHooks.beforeTcpBind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                    Net.bind(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
                    this.localAddress = Net.localAddress(this.fd);
                }
                return this;
            } finally {
                this.writeLock.unlock();
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public boolean isConnected() {
        return this.state == 2;
    }

    public boolean isConnectionPending() {
        return this.state == 1;
    }

    private void beginConnect(boolean z, InetSocketAddress inetSocketAddress) throws IOException {
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            ensureOpen();
            int i = this.state;
            if (i == 2) {
                throw new AlreadyConnectedException();
            }
            if (i == 1) {
                throw new ConnectionPendingException();
            }
            this.state = 1;
            if (this.localAddress == null) {
                NetHooks.beforeTcpConnect(this.fd, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
            }
            this.remoteAddress = inetSocketAddress;
            if (z) {
                this.readerThread = NativeThread.current();
            }
        }
    }

    private void endConnect(boolean z, boolean z2) throws IOException {
        endRead(z, z2);
        if (z2) {
            synchronized (this.stateLock) {
                if (this.state == 1) {
                    this.localAddress = Net.localAddress(this.fd);
                    this.state = 2;
                }
            }
        }
    }

    public boolean connect(SocketAddress socketAddress) throws IOException {
        int i;
        InetSocketAddress checkAddress = Net.checkAddress(socketAddress);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkConnect(checkAddress.getAddress().getHostAddress(), checkAddress.getPort());
        }
        InetAddress address = checkAddress.getAddress();
        if (address.isAnyLocalAddress()) {
            address = InetAddress.getLocalHost();
        }
        try {
            this.readLock.lock();
            try {
                this.writeLock.lock();
                try {
                    boolean isBlocking = isBlocking();
                    try {
                        beginConnect(isBlocking, checkAddress);
                        i = 0;
                        do {
                            try {
                                i = Net.connect(this.fd, address, checkAddress.getPort());
                                if (i != -3) {
                                    break;
                                }
                            } catch (Throwable th) {
                                th = th;
                                endConnect(isBlocking, i > 0);
                                throw th;
                            }
                        } while (isOpen());
                        endConnect(isBlocking, i > 0);
                        return i > 0;
                    } catch (Throwable th2) {
                        th = th2;
                        i = 0;
                    }
                } finally {
                    this.writeLock.unlock();
                }
            } finally {
                this.readLock.unlock();
            }
        } catch (IOException e) {
            close();
            throw SocketExceptions.of(e, checkAddress);
        }
    }

    private void beginFinishConnect(boolean z) throws ClosedChannelException {
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (this.state != 1) {
                throw new NoConnectionPendingException();
            }
            if (z) {
                this.readerThread = NativeThread.current();
            }
        }
    }

    private void endFinishConnect(boolean z, boolean z2) throws IOException {
        endRead(z, z2);
        if (z2) {
            synchronized (this.stateLock) {
                if (this.state == 1) {
                    this.localAddress = Net.localAddress(this.fd);
                    this.state = 2;
                }
            }
        }
    }

    public boolean finishConnect() throws IOException {
        int checkConnect;
        ReentrantLock reentrantLock;
        try {
            this.readLock.lock();
            try {
                this.writeLock.lock();
                try {
                    if (!isConnected()) {
                        boolean isBlocking = isBlocking();
                        try {
                            beginFinishConnect(isBlocking);
                            if (isBlocking) {
                                do {
                                    checkConnect = checkConnect(this.fd, true);
                                    if (checkConnect != 0 && checkConnect != -3) {
                                        break;
                                    }
                                } while (isOpen());
                            } else {
                                checkConnect = checkConnect(this.fd, false);
                            }
                            r1 = checkConnect > 0;
                            endFinishConnect(isBlocking, r1);
                            this.writeLock.unlock();
                            reentrantLock = this.readLock;
                        } catch (Throwable th) {
                            endFinishConnect(isBlocking, false);
                            throw th;
                        }
                    } else {
                        reentrantLock = this.readLock;
                    }
                    reentrantLock.unlock();
                    return r1;
                } finally {
                    this.writeLock.unlock();
                }
            } catch (Throwable th2) {
                this.readLock.unlock();
                throw th2;
            }
        } catch (IOException e) {
            close();
            throw SocketExceptions.of(e, this.remoteAddress);
        }
    }

    protected void implCloseSelectableChannel() throws IOException {
        boolean isBlocking;
        boolean z;
        boolean z2;
        boolean z3;
        synchronized (this.stateLock) {
            isBlocking = isBlocking();
            z = false;
            z2 = this.state == 2;
            this.state = 3;
        }
        if (isBlocking) {
            synchronized (this.stateLock) {
                long j = this.readerThread;
                long j2 = this.writerThread;
                if (j == 0 && j2 == 0) {
                    z = z2;
                    z3 = false;
                } else {
                    nd.preClose(this.fd);
                    if (j != 0) {
                        NativeThread.signal(j);
                    }
                    if (j2 != 0) {
                        NativeThread.signal(j2);
                    }
                    z3 = false;
                    while (true) {
                        if (this.readerThread == 0 && this.writerThread == 0) {
                            break;
                        }
                        try {
                            this.stateLock.wait();
                        } catch (InterruptedException unused) {
                            z3 = true;
                        }
                    }
                }
            }
            z2 = z;
            z = z3;
        } else {
            this.readLock.lock();
            try {
                this.writeLock.lock();
                this.writeLock.unlock();
            } finally {
                this.readLock.unlock();
            }
        }
        synchronized (this.stateLock) {
            if (z2) {
                if (isRegistered()) {
                    try {
                        SocketOption socketOption = StandardSocketOptions.SO_LINGER;
                        int intValue = ((Integer) Net.getSocketOption(this.fd, Net.UNSPEC, socketOption)).intValue();
                        if (intValue != 0) {
                            if (intValue > 0) {
                                Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, -1);
                            }
                            Net.shutdown(this.fd, 1);
                        }
                    } catch (IOException unused2) {
                    }
                }
                this.state = 4;
            } else {
                this.state = 4;
            }
        }
        if (!isRegistered()) {
            kill();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public void kill() throws IOException {
        synchronized (this.stateLock) {
            if (this.state == 4) {
                this.state = 5;
                nd.close(this.fd);
            }
        }
    }

    public SocketChannel shutdownInput() throws IOException {
        synchronized (this.stateLock) {
            ensureOpen();
            if (!isConnected()) {
                throw new NotYetConnectedException();
            }
            if (!this.isInputClosed) {
                Net.shutdown(this.fd, 0);
                long j = this.readerThread;
                if (j != 0) {
                    NativeThread.signal(j);
                }
                this.isInputClosed = true;
            }
        }
        return this;
    }

    public SocketChannel shutdownOutput() throws IOException {
        synchronized (this.stateLock) {
            ensureOpen();
            if (!isConnected()) {
                throw new NotYetConnectedException();
            }
            if (!this.isOutputClosed) {
                Net.shutdown(this.fd, 1);
                long j = this.writerThread;
                if (j != 0) {
                    NativeThread.signal(j);
                }
                this.isOutputClosed = true;
            }
        }
        return this;
    }

    boolean isInputOpen() {
        return !this.isInputClosed;
    }

    boolean isOutputOpen() {
        return !this.isOutputClosed;
    }

    boolean pollRead(long j) throws IOException {
        boolean isBlocking = isBlocking();
        this.readLock.lock();
        try {
            try {
                beginRead(isBlocking);
                return Net.poll(this.fd, Net.POLLIN, j) != 0;
            } finally {
                endRead(isBlocking, false);
            }
        } finally {
            this.readLock.unlock();
        }
    }

    boolean pollConnected(long j) throws IOException {
        boolean isBlocking = isBlocking();
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                try {
                    beginFinishConnect(isBlocking);
                    return Net.poll(this.fd, Net.POLLCONN, j) != 0;
                } finally {
                    this.writeLock.unlock();
                }
            } finally {
                endFinishConnect(isBlocking, false);
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
        int nioInterestOps = selectionKeyImpl.nioInterestOps();
        int nioReadyOps = selectionKeyImpl.nioReadyOps();
        if ((Net.POLLNVAL & i) != 0) {
            return false;
        }
        if (((Net.POLLERR | Net.POLLHUP) & i) != 0) {
            selectionKeyImpl.nioReadyOps(nioInterestOps);
            return ((nioReadyOps ^ (-1)) & nioInterestOps) != 0;
        }
        boolean isConnected = isConnected();
        if ((Net.POLLIN & i) != 0 && (nioInterestOps & 1) != 0 && isConnected) {
            i2 |= 1;
        }
        if ((Net.POLLCONN & i) != 0 && (nioInterestOps & 8) != 0 && isConnectionPending()) {
            i2 |= 8;
        }
        if ((i & Net.POLLOUT) != 0 && (nioInterestOps & 4) != 0 && isConnected) {
            i2 |= 4;
        }
        selectionKeyImpl.nioReadyOps(i2);
        return ((nioReadyOps ^ (-1)) & i2) != 0;
    }

    public boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, selectionKeyImpl.nioReadyOps(), selectionKeyImpl);
    }

    public boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, 0, selectionKeyImpl);
    }

    public int translateInterestOps(int i) {
        int i2 = (i & 1) != 0 ? Net.POLLIN : 0;
        if ((i & 4) != 0) {
            i2 |= Net.POLLOUT;
        }
        return (i & 8) != 0 ? Net.POLLCONN | i2 : i2;
    }

    public FileDescriptor getFD() {
        return this.fd;
    }

    public int getFDVal() {
        return this.fdVal;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSuperclass().getName());
        sb.append('[');
        if (!isOpen()) {
            sb.append("closed");
        } else {
            synchronized (this.stateLock) {
                int i = this.state;
                if (i == 0) {
                    sb.append("unconnected");
                } else if (i == 1) {
                    sb.append("connection-pending");
                } else if (i == 2) {
                    sb.append("connected");
                    if (this.isInputClosed) {
                        sb.append(" ishut");
                    }
                    if (this.isOutputClosed) {
                        sb.append(" oshut");
                    }
                }
                InetSocketAddress localAddress = localAddress();
                if (localAddress != null) {
                    sb.append(" local=");
                    sb.append(Net.getRevealedLocalAddressAsString(localAddress));
                }
                if (remoteAddress() != null) {
                    sb.append(" remote=");
                    sb.append(remoteAddress().toString());
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }

    static {
        IOUtil.load();
        nd = new SocketDispatcher();
    }
}
