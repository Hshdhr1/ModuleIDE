package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AcceptPendingException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NotYetBoundException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.nio.ch.Port;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixAsynchronousServerSocketChannelImpl extends AsynchronousServerSocketChannelImpl implements Port.PollableChannel {
    private static final NativeDispatcher nd = new SocketDispatcher();
    private AccessControlContext acceptAcc;
    private Object acceptAttachment;
    private PendingFuture acceptFuture;
    private CompletionHandler acceptHandler;
    private boolean acceptPending;
    private final AtomicBoolean accepting;
    private final int fdVal;
    private final Port port;
    private final Object updateLock;

    private native int accept0(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] inetSocketAddressArr) throws IOException;

    private static native void initIDs();

    static {
        IOUtil.load();
        initIDs();
    }

    private void enableAccept() {
        this.accepting.set(false);
    }

    UnixAsynchronousServerSocketChannelImpl(Port port) throws IOException {
        super(port);
        this.accepting = new AtomicBoolean();
        this.updateLock = new Object();
        try {
            IOUtil.configureBlocking(this.fd, false);
            this.port = port;
            int fdVal = IOUtil.fdVal(this.fd);
            this.fdVal = fdVal;
            port.register(fdVal, this);
        } catch (IOException e) {
            nd.close(this.fd);
            throw e;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void implClose() throws IOException {
        this.port.unregister(this.fdVal);
        nd.close(this.fd);
        synchronized (this.updateLock) {
            if (this.acceptPending) {
                this.acceptPending = false;
                CompletionHandler completionHandler = this.acceptHandler;
                Object obj = this.acceptAttachment;
                PendingFuture pendingFuture = this.acceptFuture;
                AsynchronousCloseException asynchronousCloseException = new AsynchronousCloseException();
                asynchronousCloseException.setStackTrace(new StackTraceElement[0]);
                if (completionHandler == null) {
                    pendingFuture.setFailure(asynchronousCloseException);
                } else {
                    Invoker.invokeIndirectly((AsynchronousChannel) this, completionHandler, obj, (Object) null, (Throwable) asynchronousCloseException);
                }
            }
        }
    }

    public AsynchronousChannelGroupImpl group() {
        return this.port;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onEvent(int i, boolean z) {
        synchronized (this.updateLock) {
            if (this.acceptPending) {
                this.acceptPending = false;
                FileDescriptor fileDescriptor = new FileDescriptor();
                InetSocketAddress[] inetSocketAddressArr = new InetSocketAddress[1];
                AsynchronousSocketChannel asynchronousSocketChannel = null;
                try {
                    begin();
                } catch (Throwable th) {
                    th = th;
                    try {
                        if (th instanceof ClosedChannelException) {
                            th = new AsynchronousCloseException();
                        }
                    } finally {
                        end();
                    }
                }
                if (accept(this.fd, fileDescriptor, inetSocketAddressArr) == -2) {
                    synchronized (this.updateLock) {
                        this.acceptPending = true;
                    }
                    this.port.startPoll(this.fdVal, Net.POLLIN);
                    return;
                }
                end();
                th = null;
                if (th == null) {
                    try {
                        asynchronousSocketChannel = finishAccept(fileDescriptor, inetSocketAddressArr[0], this.acceptAcc);
                    } catch (Throwable th2) {
                        th = ((th2 instanceof IOException) || (th2 instanceof SecurityException)) ? th2 : new IOException(th2);
                    }
                }
                CompletionHandler completionHandler = this.acceptHandler;
                Object obj = this.acceptAttachment;
                PendingFuture pendingFuture = this.acceptFuture;
                enableAccept();
                if (completionHandler != null) {
                    Invoker.invoke(this, completionHandler, obj, asynchronousSocketChannel, th);
                    return;
                }
                pendingFuture.setResult(asynchronousSocketChannel, th);
                if (asynchronousSocketChannel == null || !pendingFuture.isCancelled()) {
                    return;
                }
                try {
                    asynchronousSocketChannel.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    private AsynchronousSocketChannel finishAccept(FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress, AccessControlContext accessControlContext) throws IOException, SecurityException {
        try {
            UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl = new UnixAsynchronousSocketChannelImpl(this.port, fileDescriptor, inetSocketAddress);
            try {
                if (accessControlContext != null) {
                    AccessController.doPrivileged(new 1(inetSocketAddress), accessControlContext);
                    return unixAsynchronousSocketChannelImpl;
                }
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                }
                return unixAsynchronousSocketChannelImpl;
            } catch (SecurityException e) {
                try {
                    unixAsynchronousSocketChannelImpl.close();
                } catch (Throwable th) {
                    UnixAsynchronousServerSocketChannelImpl$$ExternalSyntheticBackport0.m(e, th);
                }
                throw e;
            }
        } catch (IOException e2) {
            nd.close(fileDescriptor);
            throw e2;
        }
    }

    class 1 implements PrivilegedAction {
        final /* synthetic */ InetSocketAddress val$remote;

        1(InetSocketAddress inetSocketAddress) {
            this.val$remote = inetSocketAddress;
        }

        public Void run() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager == null) {
                return null;
            }
            securityManager.checkAccept(this.val$remote.getAddress().getHostAddress(), this.val$remote.getPort());
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    Future implAccept(Object obj, CompletionHandler completionHandler) {
        AsynchronousSocketChannel finishAccept;
        PendingFuture pendingFuture;
        if (!isOpen()) {
            ClosedChannelException closedChannelException = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure(closedChannelException);
            }
            Invoker.invoke(this, completionHandler, obj, null, closedChannelException);
            return null;
        }
        if (this.localAddress == null) {
            throw new NotYetBoundException();
        }
        if (isAcceptKilled()) {
            throw new RuntimeException("Accept not allowed due cancellation");
        }
        if (!this.accepting.compareAndSet(false, true)) {
            throw new AcceptPendingException();
        }
        FileDescriptor fileDescriptor = new FileDescriptor();
        InetSocketAddress[] inetSocketAddressArr = new InetSocketAddress[1];
        try {
            begin();
        } catch (Throwable th) {
            th = th;
            try {
                if (th instanceof ClosedChannelException) {
                    th = new AsynchronousCloseException();
                }
            } finally {
                end();
            }
        }
        if (accept(this.fd, fileDescriptor, inetSocketAddressArr) == -2) {
            synchronized (this.updateLock) {
                if (completionHandler == null) {
                    this.acceptHandler = null;
                    pendingFuture = new PendingFuture(this);
                    this.acceptFuture = pendingFuture;
                } else {
                    this.acceptHandler = completionHandler;
                    this.acceptAttachment = obj;
                    pendingFuture = null;
                }
                this.acceptAcc = System.getSecurityManager() == null ? null : AccessController.getContext();
                this.acceptPending = true;
            }
            this.port.startPoll(this.fdVal, Net.POLLIN);
            return pendingFuture;
        }
        end();
        th = null;
        if (th == null) {
            try {
                finishAccept = finishAccept(fileDescriptor, inetSocketAddressArr[0], null);
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            finishAccept = null;
        }
        enableAccept();
        if (completionHandler == null) {
            return CompletedFuture.withResult(finishAccept, th);
        }
        Invoker.invokeIndirectly((AsynchronousChannel) this, completionHandler, obj, (Object) finishAccept, th);
        return null;
    }

    private int accept(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] inetSocketAddressArr) throws IOException {
        return accept0(fileDescriptor, fileDescriptor2, inetSocketAddressArr);
    }
}
