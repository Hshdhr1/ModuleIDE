package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.ReadPendingException;
import java.nio.channels.WritePendingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.net.NetHooks;
import sun.net.ext.ExtendedSocketOptions;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AsynchronousSocketChannelImpl extends AsynchronousSocketChannel implements Cancellable, Groupable {
    static final int ST_CONNECTED = 2;
    static final int ST_PENDING = 1;
    static final int ST_UNCONNECTED = 0;
    static final int ST_UNINITIALIZED = -1;
    private final ReadWriteLock closeLock;
    private volatile boolean closed;
    protected final FileDescriptor fd;
    private boolean isReuseAddress;
    protected volatile InetSocketAddress localAddress;
    private boolean readKilled;
    private final Object readLock;
    private boolean readShutdown;
    private boolean reading;
    protected volatile InetSocketAddress remoteAddress;
    protected volatile int state;
    protected final Object stateLock;
    private boolean writeKilled;
    private final Object writeLock;
    private boolean writeShutdown;
    private boolean writing;

    abstract void implClose() throws IOException;

    abstract Future implConnect(SocketAddress socketAddress, Object obj, CompletionHandler completionHandler);

    abstract Future implRead(boolean z, ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler);

    abstract Future implWrite(boolean z, ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler);

    AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) throws IOException {
        super(asynchronousChannelGroupImpl.provider());
        this.stateLock = new Object();
        this.state = -1;
        this.readLock = new Object();
        this.writeLock = new Object();
        this.closeLock = new ReentrantReadWriteLock();
        this.fd = Net.socket(true);
        this.state = 0;
    }

    AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(asynchronousChannelGroupImpl.provider());
        this.stateLock = new Object();
        this.state = -1;
        this.readLock = new Object();
        this.writeLock = new Object();
        this.closeLock = new ReentrantReadWriteLock();
        this.fd = fileDescriptor;
        this.state = 2;
        this.localAddress = Net.localAddress(fileDescriptor);
        this.remoteAddress = inetSocketAddress;
    }

    public final boolean isOpen() {
        return !this.closed;
    }

    final void begin() throws IOException {
        this.closeLock.readLock().lock();
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    final void end() {
        this.closeLock.readLock().unlock();
    }

    public final void close() throws IOException {
        this.closeLock.writeLock().lock();
        try {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.closeLock.writeLock().unlock();
            implClose();
        } finally {
            this.closeLock.writeLock().unlock();
        }
    }

    final void enableReading(boolean z) {
        synchronized (this.readLock) {
            this.reading = false;
            if (z) {
                this.readKilled = true;
            }
        }
    }

    final void enableReading() {
        enableReading(false);
    }

    final void enableWriting(boolean z) {
        synchronized (this.writeLock) {
            this.writing = false;
            if (z) {
                this.writeKilled = true;
            }
        }
    }

    final void enableWriting() {
        enableWriting(false);
    }

    final void killReading() {
        synchronized (this.readLock) {
            this.readKilled = true;
        }
    }

    final void killWriting() {
        synchronized (this.writeLock) {
            this.writeKilled = true;
        }
    }

    final void killConnect() {
        killReading();
        killWriting();
    }

    public final Future connect(SocketAddress socketAddress) {
        return implConnect(socketAddress, null, null);
    }

    public final void connect(SocketAddress socketAddress, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        implConnect(socketAddress, obj, completionHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Future read(boolean z, ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        Throwable th;
        Long valueOf;
        if (!isOpen()) {
            ClosedChannelException closedChannelException = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure(closedChannelException);
            }
            Invoker.invoke(this, completionHandler, obj, null, closedChannelException);
            return null;
        }
        if (this.remoteAddress == null) {
            throw new NotYetConnectedException();
        }
        boolean z2 = true;
        boolean z3 = z || byteBuffer.hasRemaining();
        synchronized (this.readLock) {
            try {
                try {
                    if (this.readKilled) {
                        throw new IllegalStateException("Reading not allowed due to timeout or cancellation");
                    }
                    if (this.reading) {
                        throw new ReadPendingException();
                    }
                    if (!this.readShutdown) {
                        if (z3) {
                            try {
                                this.reading = true;
                            } catch (Throwable th2) {
                                th = th2;
                                throw th;
                            }
                        }
                        z2 = false;
                    }
                    if (!z2 && z3) {
                        return implRead(z, byteBuffer, byteBufferArr, j, timeUnit, obj, completionHandler);
                    }
                    if (z) {
                        valueOf = Long.valueOf(z2 ? -1L : 0L);
                    } else {
                        valueOf = Integer.valueOf(z2 ? -1 : 0);
                    }
                    if (completionHandler == null) {
                        return CompletedFuture.withResult(valueOf);
                    }
                    Invoker.invoke(this, completionHandler, obj, valueOf, null);
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    th = th;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    public final Future read(ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("Read-only buffer");
        }
        return read(false, byteBuffer, (ByteBuffer[]) null, 0L, TimeUnit.MILLISECONDS, (Object) null, (CompletionHandler) null);
    }

    public final void read(ByteBuffer byteBuffer, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("Read-only buffer");
        }
        read(false, byteBuffer, (ByteBuffer[]) null, j, timeUnit, obj, completionHandler);
    }

    public final void read(ByteBuffer[] byteBufferArr, int i, int i2, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        if (i < 0 || i2 < 0 || i > byteBufferArr.length - i2) {
            throw new IndexOutOfBoundsException();
        }
        ByteBuffer[] subsequence = Util.subsequence(byteBufferArr, i, i2);
        for (ByteBuffer byteBuffer : subsequence) {
            if (byteBuffer.isReadOnly()) {
                throw new IllegalArgumentException("Read-only buffer");
            }
        }
        read(true, (ByteBuffer) null, subsequence, j, timeUnit, obj, completionHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Future write(boolean z, ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        boolean z2 = true;
        boolean z3 = z || byteBuffer.hasRemaining();
        if (isOpen()) {
            if (this.remoteAddress == null) {
                throw new NotYetConnectedException();
            }
            synchronized (this.writeLock) {
                if (this.writeKilled) {
                    throw new IllegalStateException("Writing not allowed due to timeout or cancellation");
                }
                if (this.writing) {
                    throw new WritePendingException();
                }
                if (!this.writeShutdown) {
                    if (z3) {
                        this.writing = true;
                    }
                    z2 = false;
                }
            }
        }
        if (z2) {
            ClosedChannelException closedChannelException = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure(closedChannelException);
            }
            Invoker.invoke(this, completionHandler, obj, null, closedChannelException);
            return null;
        }
        if (!z3) {
            Long l = z ? 0L : 0;
            if (completionHandler == null) {
                return CompletedFuture.withResult(l);
            }
            Invoker.invoke(this, completionHandler, obj, l, null);
            return null;
        }
        return implWrite(z, byteBuffer, byteBufferArr, j, timeUnit, obj, completionHandler);
    }

    public final Future write(ByteBuffer byteBuffer) {
        return write(false, byteBuffer, (ByteBuffer[]) null, 0L, TimeUnit.MILLISECONDS, (Object) null, (CompletionHandler) null);
    }

    public final void write(ByteBuffer byteBuffer, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        write(false, byteBuffer, (ByteBuffer[]) null, j, timeUnit, obj, completionHandler);
    }

    public final void write(ByteBuffer[] byteBufferArr, int i, int i2, long j, TimeUnit timeUnit, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        if (i < 0 || i2 < 0 || i > byteBufferArr.length - i2) {
            throw new IndexOutOfBoundsException();
        }
        write(true, (ByteBuffer) null, Util.subsequence(byteBufferArr, i, i2), j, timeUnit, obj, completionHandler);
    }

    public final AsynchronousSocketChannel bind(SocketAddress socketAddress) throws IOException {
        try {
            begin();
            synchronized (this.stateLock) {
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
            end();
        }
    }

    public final SocketAddress getLocalAddress() throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        return Net.getRevealedLocalAddress(this.localAddress);
    }

    public final AsynchronousSocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        try {
            begin();
            if (this.writeShutdown) {
                throw new IOException("Connection has been shutdown for writing");
            }
            if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                this.isReuseAddress = ((Boolean) obj).booleanValue();
            } else {
                Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, obj);
            }
            return this;
        } finally {
            end();
        }
    }

    public final Object getOption(SocketOption socketOption) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        try {
            begin();
            if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                return Boolean.valueOf(this.isReuseAddress);
            }
            return Net.getSocketOption(this.fd, Net.UNSPEC, socketOption);
        } finally {
            end();
        }
    }

    private static class DefaultOptionsHolder {
        static final Set defaultOptions = defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set defaultOptions() {
            HashSet hashSet = new HashSet(5);
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            if (Net.isReusePortAvailable()) {
                hashSet.add(StandardSocketOptions.SO_REUSEPORT);
            }
            hashSet.add(StandardSocketOptions.TCP_NODELAY);
            hashSet.addAll(ExtendedSocketOptions.options((short) 1));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public final Set supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    public final SocketAddress getRemoteAddress() throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        return this.remoteAddress;
    }

    public final AsynchronousSocketChannel shutdownInput() throws IOException {
        try {
            begin();
            if (this.remoteAddress == null) {
                throw new NotYetConnectedException();
            }
            synchronized (this.readLock) {
                if (!this.readShutdown) {
                    Net.shutdown(this.fd, 0);
                    this.readShutdown = true;
                }
            }
            return this;
        } finally {
            end();
        }
    }

    public final AsynchronousSocketChannel shutdownOutput() throws IOException {
        try {
            begin();
            if (this.remoteAddress == null) {
                throw new NotYetConnectedException();
            }
            synchronized (this.writeLock) {
                if (!this.writeShutdown) {
                    Net.shutdown(this.fd, 1);
                    this.writeShutdown = true;
                }
            }
            return this;
        } finally {
            end();
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append('[');
        synchronized (this.stateLock) {
            if (!isOpen()) {
                sb.append("closed");
            } else {
                int i = this.state;
                if (i == 0) {
                    sb.append("unconnected");
                } else if (i == 1) {
                    sb.append("connection-pending");
                } else if (i == 2) {
                    sb.append("connected");
                    if (this.readShutdown) {
                        sb.append(" ishut");
                    }
                    if (this.writeShutdown) {
                        sb.append(" oshut");
                    }
                }
                if (this.localAddress != null) {
                    sb.append(" local=");
                    sb.append(Net.getRevealedLocalAddressAsString(this.localAddress));
                }
                if (this.remoteAddress != null) {
                    sb.append(" remote=");
                    sb.append(this.remoteAddress.toString());
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
