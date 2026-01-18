package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.net.NetHooks;
import sun.net.ext.ExtendedSocketOptions;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AsynchronousServerSocketChannelImpl extends AsynchronousServerSocketChannel implements Cancellable, Groupable {
    private volatile boolean acceptKilled;
    private ReadWriteLock closeLock;
    private volatile boolean closed;
    protected final FileDescriptor fd;
    private boolean isReuseAddress;
    protected volatile InetSocketAddress localAddress;
    private final Object stateLock;

    abstract Future implAccept(Object obj, CompletionHandler completionHandler);

    abstract void implClose() throws IOException;

    AsynchronousServerSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        super(asynchronousChannelGroupImpl.provider());
        this.stateLock = new Object();
        this.closeLock = new ReentrantReadWriteLock();
        this.fd = Net.serverSocket(true);
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

    public final Future accept() {
        return implAccept(null, null);
    }

    public final void accept(Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        implAccept(obj, completionHandler);
    }

    final boolean isAcceptKilled() {
        return this.acceptKilled;
    }

    public final void onCancel(PendingFuture pendingFuture) {
        this.acceptKilled = true;
    }

    public final AsynchronousServerSocketChannel bind(SocketAddress socketAddress, int i) throws IOException {
        InetSocketAddress checkAddress;
        if (socketAddress == null) {
            checkAddress = new InetSocketAddress(0);
        } else {
            checkAddress = Net.checkAddress(socketAddress);
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkListen(checkAddress.getPort());
        }
        try {
            begin();
            synchronized (this.stateLock) {
                if (this.localAddress != null) {
                    throw new AlreadyBoundException();
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

    public final AsynchronousServerSocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        try {
            begin();
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
            HashSet hashSet = new HashSet(2);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            if (Net.isReusePortAvailable()) {
                hashSet.add(StandardSocketOptions.SO_REUSEPORT);
            }
            hashSet.addAll(ExtendedSocketOptions.options((short) 1));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public final Set supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append('[');
        if (!isOpen()) {
            sb.append("closed");
        } else if (this.localAddress == null) {
            sb.append("unbound");
        } else {
            sb.append(Net.getRevealedLocalAddressAsString(this.localAddress));
        }
        sb.append(']');
        return sb.toString();
    }
}
