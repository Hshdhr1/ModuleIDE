package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetBoundException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import sun.net.NetHooks;
import sun.net.ext.ExtendedSocketOptions;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class ServerSocketChannelImpl extends ServerSocketChannel implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CLOSING = 1;
    private static final int ST_INUSE = 0;
    private static final int ST_KILLED = 3;
    private static final int ST_KILLPENDING = 2;
    private static NativeDispatcher nd;
    private final ReentrantLock acceptLock;
    private final FileDescriptor fd;
    private final int fdVal;
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private ServerSocket socket;
    private int state;
    private final Object stateLock;
    private long thread;

    private native int accept0(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] inetSocketAddressArr) throws IOException;

    private static native void initIDs();

    ServerSocketChannelImpl(SelectorProvider selectorProvider) throws IOException {
        super(selectorProvider);
        this.acceptLock = new ReentrantLock();
        this.stateLock = new Object();
        FileDescriptor serverSocket = Net.serverSocket(true);
        this.fd = serverSocket;
        this.fdVal = IOUtil.fdVal(serverSocket);
    }

    ServerSocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, boolean z) throws IOException {
        super(selectorProvider);
        this.acceptLock = new ReentrantLock();
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

    private void ensureOpen() throws ClosedChannelException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    public ServerSocket socket() {
        ServerSocket serverSocket;
        synchronized (this.stateLock) {
            if (this.socket == null) {
                this.socket = ServerSocketAdaptor.create(this);
            }
            serverSocket = this.socket;
        }
        return serverSocket;
    }

    public SocketAddress getLocalAddress() throws IOException {
        SocketAddress revealedLocalAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            InetSocketAddress inetSocketAddress = this.localAddress;
            revealedLocalAddress = inetSocketAddress == null ? null : Net.getRevealedLocalAddress(inetSocketAddress);
        }
        return revealedLocalAddress;
    }

    public ServerSocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
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
            } else {
                Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, obj);
            }
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
            return Net.getSocketOption(this.fd, Net.UNSPEC, socketOption);
        }
    }

    private static class DefaultOptionsHolder {
        static final Set defaultOptions = defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set defaultOptions() {
            HashSet hashSet = new HashSet();
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            if (Net.isReusePortAvailable()) {
                hashSet.add(StandardSocketOptions.SO_REUSEPORT);
            }
            hashSet.add(StandardSocketOptions.IP_TOS);
            hashSet.addAll(ExtendedSocketOptions.options((short) 1));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public final Set supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    public ServerSocketChannel bind(SocketAddress socketAddress, int i) throws IOException {
        InetSocketAddress checkAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            if (this.localAddress != null) {
                throw new AlreadyBoundException();
            }
            if (socketAddress == null) {
                checkAddress = new InetSocketAddress(0);
            } else {
                checkAddress = Net.checkAddress(socketAddress);
            }
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkListen(checkAddress.getPort());
            }
            NetHooks.beforeTcpBind(this.fd, checkAddress.getAddress(), checkAddress.getPort());
            Net.bind(this.fd, checkAddress.getAddress(), checkAddress.getPort());
            FileDescriptor fileDescriptor = this.fd;
            if (i < 1) {
                i = 50;
            }
            Net.listen(fileDescriptor, i);
            this.localAddress = Net.localAddress(this.fd);
        }
        return this;
    }

    private void begin(boolean z) throws ClosedChannelException {
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (this.localAddress == null) {
                throw new NotYetBoundException();
            }
            if (z) {
                this.thread = NativeThread.current();
            }
        }
    }

    private void end(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.thread = 0L;
                if (this.state == 1) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    public SocketChannel accept() throws IOException {
        int i;
        this.acceptLock.lock();
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            InetSocketAddress[] inetSocketAddressArr = new InetSocketAddress[1];
            boolean isBlocking = isBlocking();
            try {
                begin(isBlocking);
                i = 0;
                do {
                    try {
                        i = accept(this.fd, fileDescriptor, inetSocketAddressArr);
                        if (i != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        end(isBlocking, i > 0);
                        throw th;
                    }
                } while (isOpen());
                end(isBlocking, i > 0);
                if (i >= 1) {
                    IOUtil.configureBlocking(fileDescriptor, true);
                    InetSocketAddress inetSocketAddress = inetSocketAddressArr[0];
                    SocketChannelImpl socketChannelImpl = new SocketChannelImpl(provider(), fileDescriptor, inetSocketAddress);
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            securityManager.checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                        } catch (SecurityException e) {
                            socketChannelImpl.close();
                            throw e;
                        }
                    }
                    return socketChannelImpl;
                }
                this.acceptLock.unlock();
                return null;
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.acceptLock.unlock();
        }
    }

    protected void implConfigureBlocking(boolean z) throws IOException {
        this.acceptLock.lock();
        try {
            synchronized (this.stateLock) {
                ensureOpen();
                IOUtil.configureBlocking(this.fd, z);
            }
        } finally {
            this.acceptLock.unlock();
        }
    }

    protected void implCloseSelectableChannel() throws IOException {
        boolean isBlocking;
        synchronized (this.stateLock) {
            this.state = 1;
            isBlocking = isBlocking();
        }
        boolean z = false;
        if (isBlocking) {
            synchronized (this.stateLock) {
                long j = this.thread;
                if (j != 0) {
                    nd.preClose(this.fd);
                    NativeThread.signal(j);
                    while (this.thread != 0) {
                        try {
                            this.stateLock.wait();
                        } catch (InterruptedException unused) {
                            z = true;
                        }
                    }
                }
            }
        } else {
            this.acceptLock.lock();
            this.acceptLock.unlock();
        }
        synchronized (this.stateLock) {
            this.state = 2;
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
            if (this.state == 2) {
                this.state = 3;
                nd.close(this.fd);
            }
        }
    }

    boolean isBound() {
        boolean z;
        synchronized (this.stateLock) {
            z = this.localAddress != null;
        }
        return z;
    }

    InetSocketAddress localAddress() {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            inetSocketAddress = this.localAddress;
        }
        return inetSocketAddress;
    }

    boolean pollAccept(long j) throws IOException {
        this.acceptLock.lock();
        try {
            try {
                begin(true);
                return Net.poll(this.fd, Net.POLLIN, j) != 0;
            } finally {
                end(true, false);
            }
        } finally {
            this.acceptLock.unlock();
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
        if ((i & Net.POLLIN) != 0 && (nioInterestOps & 16) != 0) {
            i2 |= 16;
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
        if ((i & 16) != 0) {
            return Net.POLLIN;
        }
        return 0;
    }

    public FileDescriptor getFD() {
        return this.fd;
    }

    public int getFDVal() {
        return this.fdVal;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append('[');
        if (!isOpen()) {
            sb.append("closed");
        } else {
            synchronized (this.stateLock) {
                InetSocketAddress inetSocketAddress = this.localAddress;
                if (inetSocketAddress == null) {
                    sb.append("unbound");
                } else {
                    sb.append(Net.getRevealedLocalAddressAsString(inetSocketAddress));
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private int accept(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] inetSocketAddressArr) throws IOException {
        return accept0(fileDescriptor, fileDescriptor2, inetSocketAddressArr);
    }

    static {
        IOUtil.load();
        initIDs();
        nd = new SocketDispatcher();
    }
}
