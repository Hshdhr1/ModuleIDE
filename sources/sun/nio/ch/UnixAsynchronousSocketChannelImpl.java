package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.concurrent.Future;
import sun.net.NetHooks;
import sun.net.util.SocketExceptions;
import sun.nio.ch.Port;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixAsynchronousSocketChannelImpl extends AsynchronousSocketChannelImpl implements Port.PollableChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean disableSynchronousRead;
    private static final NativeDispatcher nd = new SocketDispatcher();
    private Object connectAttachment;
    private PendingFuture connectFuture;
    private CompletionHandler connectHandler;
    private boolean connectPending;
    private final int fdVal;
    private boolean isGatheringWrite;
    private boolean isScatteringRead;
    private SocketAddress pendingRemote;
    private final Port port;
    private Object readAttachment;
    private ByteBuffer readBuffer;
    private ByteBuffer[] readBuffers;
    private PendingFuture readFuture;
    private CompletionHandler readHandler;
    private boolean readPending;
    private Runnable readTimeoutTask;
    private Future readTimer;
    private final Object updateLock;
    private Object writeAttachment;
    private ByteBuffer writeBuffer;
    private ByteBuffer[] writeBuffers;
    private PendingFuture writeFuture;
    private CompletionHandler writeHandler;
    private boolean writePending;
    private Runnable writeTimeoutTask;
    private Future writeTimer;

    private enum OpType {
        CONNECT,
        READ,
        WRITE
    }

    static /* bridge */ /* synthetic */ Object -$$Nest$fgetreadAttachment(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.readAttachment;
    }

    static /* bridge */ /* synthetic */ PendingFuture -$$Nest$fgetreadFuture(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.readFuture;
    }

    static /* bridge */ /* synthetic */ CompletionHandler -$$Nest$fgetreadHandler(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.readHandler;
    }

    static /* bridge */ /* synthetic */ boolean -$$Nest$fgetreadPending(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.readPending;
    }

    static /* bridge */ /* synthetic */ Object -$$Nest$fgetupdateLock(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.updateLock;
    }

    static /* bridge */ /* synthetic */ Object -$$Nest$fgetwriteAttachment(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.writeAttachment;
    }

    static /* bridge */ /* synthetic */ PendingFuture -$$Nest$fgetwriteFuture(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.writeFuture;
    }

    static /* bridge */ /* synthetic */ CompletionHandler -$$Nest$fgetwriteHandler(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.writeHandler;
    }

    static /* bridge */ /* synthetic */ boolean -$$Nest$fgetwritePending(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl) {
        return unixAsynchronousSocketChannelImpl.writePending;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$fputreadPending(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl, boolean z) {
        unixAsynchronousSocketChannelImpl.readPending = z;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$fputwritePending(UnixAsynchronousSocketChannelImpl unixAsynchronousSocketChannelImpl, boolean z) {
        unixAsynchronousSocketChannelImpl.writePending = z;
    }

    private static native void checkConnect(int i) throws IOException;

    static {
        String privilegedGetProperty = GetPropertyAction.privilegedGetProperty("sun.nio.ch.disableSynchronousRead", "false");
        disableSynchronousRead = privilegedGetProperty.isEmpty() ? true : Boolean.parseBoolean(privilegedGetProperty);
        IOUtil.load();
    }

    UnixAsynchronousSocketChannelImpl(Port port) throws IOException {
        super(port);
        this.updateLock = new Object();
        this.readTimeoutTask = new 1();
        this.writeTimeoutTask = new 2();
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

    UnixAsynchronousSocketChannelImpl(Port port, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(port, fileDescriptor, inetSocketAddress);
        this.updateLock = new Object();
        this.readTimeoutTask = new 1();
        this.writeTimeoutTask = new 2();
        int fdVal = IOUtil.fdVal(fileDescriptor);
        this.fdVal = fdVal;
        IOUtil.configureBlocking(fileDescriptor, false);
        try {
            port.register(fdVal, this);
            this.port = port;
        } catch (ShutdownChannelGroupException e) {
            throw new IOException(e);
        }
    }

    public AsynchronousChannelGroupImpl group() {
        return this.port;
    }

    private void updateEvents() {
        int i = this.readPending ? Net.POLLIN : 0;
        if (this.connectPending || this.writePending) {
            i |= Net.POLLOUT;
        }
        if (i != 0) {
            this.port.startPoll(this.fdVal, i);
        }
    }

    private void lockAndUpdateEvents() {
        synchronized (this.updateLock) {
            updateEvents();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void finish(boolean r4, boolean r5, boolean r6) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.updateLock
            monitor-enter(r0)
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L11
            boolean r5 = r3.readPending     // Catch: java.lang.Throwable -> Lf
            if (r5 == 0) goto L11
            r3.readPending = r2     // Catch: java.lang.Throwable -> Lf
            r5 = 1
            goto L12
        Lf:
            r4 = move-exception
            goto L3d
        L11:
            r5 = 0
        L12:
            if (r6 == 0) goto L24
            boolean r6 = r3.writePending     // Catch: java.lang.Throwable -> Lf
            if (r6 == 0) goto L1c
            r3.writePending = r2     // Catch: java.lang.Throwable -> Lf
            r6 = 0
            goto L26
        L1c:
            boolean r6 = r3.connectPending     // Catch: java.lang.Throwable -> Lf
            if (r6 == 0) goto L24
            r3.connectPending = r2     // Catch: java.lang.Throwable -> Lf
            r6 = 1
            goto L25
        L24:
            r6 = 0
        L25:
            r1 = 0
        L26:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            if (r5 == 0) goto L32
            if (r1 == 0) goto L2e
            r3.finishWrite(r2)
        L2e:
            r3.finishRead(r4)
            return
        L32:
            if (r1 == 0) goto L37
            r3.finishWrite(r4)
        L37:
            if (r6 == 0) goto L3c
            r3.finishConnect(r4)
        L3c:
            return
        L3d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.UnixAsynchronousSocketChannelImpl.finish(boolean, boolean, boolean):void");
    }

    public void onEvent(int i, boolean z) {
        boolean z2 = true;
        boolean z3 = (Net.POLLIN & i) > 0;
        boolean z4 = (Net.POLLOUT & i) > 0;
        if ((i & (Net.POLLERR | Net.POLLHUP)) > 0) {
            z4 = true;
        } else {
            z2 = z3;
        }
        finish(z, z2, z4);
    }

    void implClose() throws IOException {
        this.port.unregister(this.fdVal);
        nd.close(this.fd);
        finish(false, true, true);
    }

    public void onCancel(PendingFuture pendingFuture) {
        if (pendingFuture.getContext() == OpType.CONNECT) {
            killConnect();
        }
        if (pendingFuture.getContext() == OpType.READ) {
            killReading();
        }
        if (pendingFuture.getContext() == OpType.WRITE) {
            killWriting();
        }
    }

    private void setConnected() throws IOException {
        synchronized (this.stateLock) {
            this.state = 2;
            this.localAddress = Net.localAddress(this.fd);
            this.remoteAddress = this.pendingRemote;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void finishConnect(boolean z) {
        try {
            begin();
            checkConnect(this.fdVal);
            setConnected();
            end();
            th = null;
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
        if (th != null) {
            if (th instanceof IOException) {
                th = SocketExceptions.of((IOException) th, this.pendingRemote);
            }
            try {
                close();
            } catch (Throwable th2) {
                UnixAsynchronousSocketChannelImpl$$ExternalSyntheticBackport0.m(th, th2);
            }
        }
        CompletionHandler completionHandler = this.connectHandler;
        this.connectHandler = null;
        Object obj = this.connectAttachment;
        PendingFuture pendingFuture = this.connectFuture;
        if (completionHandler == null) {
            pendingFuture.setResult(null, th);
        } else if (z) {
            Invoker.invokeUnchecked(completionHandler, obj, null, th);
        } else {
            Invoker.invokeIndirectly((AsynchronousChannel) this, completionHandler, obj, (Object) null, th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    Future implConnect(SocketAddress socketAddress, Object obj, CompletionHandler completionHandler) {
        boolean z;
        PendingFuture pendingFuture;
        if (!isOpen()) {
            ClosedChannelException closedChannelException = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure(closedChannelException);
            }
            Invoker.invoke(this, completionHandler, obj, null, closedChannelException);
            return null;
        }
        InetSocketAddress checkAddress = Net.checkAddress(socketAddress);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkConnect(checkAddress.getAddress().getHostAddress(), checkAddress.getPort());
        }
        synchronized (this.stateLock) {
            if (this.state == 2) {
                throw new AlreadyConnectedException();
            }
            if (this.state == 1) {
                throw new ConnectionPendingException();
            }
            this.state = 1;
            this.pendingRemote = socketAddress;
            z = this.localAddress == null;
        }
        try {
            begin();
            if (z) {
                NetHooks.beforeTcpConnect(this.fd, checkAddress.getAddress(), checkAddress.getPort());
            }
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
        if (Net.connect(this.fd, checkAddress.getAddress(), checkAddress.getPort()) == -2) {
            synchronized (this.updateLock) {
                if (completionHandler == null) {
                    pendingFuture = new PendingFuture(this, OpType.CONNECT);
                    this.connectFuture = pendingFuture;
                } else {
                    this.connectHandler = completionHandler;
                    this.connectAttachment = obj;
                    pendingFuture = null;
                }
                this.connectPending = true;
                updateEvents();
            }
            return pendingFuture;
        }
        setConnected();
        end();
        th = null;
        if (th != null) {
            if (th instanceof IOException) {
                th = SocketExceptions.of((IOException) th, checkAddress);
            }
            try {
                close();
            } catch (Throwable th2) {
                UnixAsynchronousSocketChannelImpl$$ExternalSyntheticBackport0.m(th, th2);
            }
        }
        if (completionHandler == null) {
            return CompletedFuture.withResult(null, th);
        }
        Invoker.invoke(this, completionHandler, obj, null, th);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void finishRead(boolean z) {
        boolean z2 = this.isScatteringRead;
        CompletionHandler completionHandler = this.readHandler;
        Object obj = this.readAttachment;
        PendingFuture pendingFuture = this.readFuture;
        Future future = this.readTimer;
        int i = -1;
        try {
            begin();
            i = z2 ? (int) IOUtil.read(this.fd, this.readBuffers, nd) : IOUtil.read(this.fd, this.readBuffer, -1L, nd);
        } catch (Throwable th) {
            th = th;
            try {
                enableReading();
                if (th instanceof ClosedChannelException) {
                    th = new AsynchronousCloseException();
                }
                if (!(th instanceof AsynchronousCloseException)) {
                    lockAndUpdateEvents();
                }
                end();
            } finally {
                lockAndUpdateEvents();
                end();
            }
        }
        if (i == -2) {
            synchronized (this.updateLock) {
                this.readPending = true;
            }
            return;
        }
        this.readBuffer = null;
        this.readBuffers = null;
        this.readAttachment = null;
        this.readHandler = null;
        enableReading();
        lockAndUpdateEvents();
        end();
        th = null;
        if (future != null) {
            future.cancel(false);
        }
        Long valueOf = th == null ? z2 ? Long.valueOf(i) : Integer.valueOf(i) : null;
        if (completionHandler == null) {
            pendingFuture.setResult(valueOf, th);
        } else if (z) {
            Invoker.invokeUnchecked(completionHandler, obj, valueOf, th);
        } else {
            Invoker.invokeIndirectly((AsynchronousChannel) this, completionHandler, obj, (Object) valueOf, th);
        }
    }

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            synchronized (UnixAsynchronousSocketChannelImpl.-$$Nest$fgetupdateLock(UnixAsynchronousSocketChannelImpl.this)) {
                if (UnixAsynchronousSocketChannelImpl.-$$Nest$fgetreadPending(UnixAsynchronousSocketChannelImpl.this)) {
                    UnixAsynchronousSocketChannelImpl.-$$Nest$fputreadPending(UnixAsynchronousSocketChannelImpl.this, false);
                    CompletionHandler completionHandler = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetreadHandler(UnixAsynchronousSocketChannelImpl.this);
                    Object obj = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetreadAttachment(UnixAsynchronousSocketChannelImpl.this);
                    PendingFuture pendingFuture = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetreadFuture(UnixAsynchronousSocketChannelImpl.this);
                    UnixAsynchronousSocketChannelImpl.this.enableReading(true);
                    InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
                    if (completionHandler == null) {
                        pendingFuture.setFailure(interruptedByTimeoutException);
                    } else {
                        Invoker.invokeIndirectly((AsynchronousChannel) UnixAsynchronousSocketChannelImpl.this, completionHandler, obj, (Object) null, (Throwable) interruptedByTimeoutException);
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(1:(1:4)(7:61|(1:65)|6|7|8|(1:(1:11)(1:58))(1:59)|(5:28|29|52|40|41)(3:13|(1:15)(1:(1:26)(1:27))|(1:(2:18|19)(2:21|22))(2:23|24))))(1:66)|5|6|7|8|(0)(0)|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x009d, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.util.concurrent.Future implRead(boolean r17, java.nio.ByteBuffer r18, java.nio.ByteBuffer[] r19, long r20, java.util.concurrent.TimeUnit r22, java.lang.Object r23, java.nio.channels.CompletionHandler r24) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.UnixAsynchronousSocketChannelImpl.implRead(boolean, java.nio.ByteBuffer, java.nio.ByteBuffer[], long, java.util.concurrent.TimeUnit, java.lang.Object, java.nio.channels.CompletionHandler):java.util.concurrent.Future");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void finishWrite(boolean z) {
        boolean z2 = this.isGatheringWrite;
        CompletionHandler completionHandler = this.writeHandler;
        Object obj = this.writeAttachment;
        PendingFuture pendingFuture = this.writeFuture;
        Future future = this.writeTimer;
        int i = -1;
        try {
            begin();
            i = z2 ? (int) IOUtil.write(this.fd, this.writeBuffers, nd) : IOUtil.write(this.fd, this.writeBuffer, -1L, nd);
        } catch (Throwable th) {
            th = th;
            try {
                enableWriting();
                if (th instanceof ClosedChannelException) {
                    th = new AsynchronousCloseException();
                }
                if (!(th instanceof AsynchronousCloseException)) {
                    lockAndUpdateEvents();
                }
                end();
            } finally {
                lockAndUpdateEvents();
                end();
            }
        }
        if (i == -2) {
            synchronized (this.updateLock) {
                this.writePending = true;
            }
            return;
        }
        this.writeBuffer = null;
        this.writeBuffers = null;
        this.writeAttachment = null;
        this.writeHandler = null;
        enableWriting();
        lockAndUpdateEvents();
        end();
        th = null;
        if (future != null) {
            future.cancel(false);
        }
        Long valueOf = th == null ? z2 ? Long.valueOf(i) : Integer.valueOf(i) : null;
        if (completionHandler == null) {
            pendingFuture.setResult(valueOf, th);
        } else if (z) {
            Invoker.invokeUnchecked(completionHandler, obj, valueOf, th);
        } else {
            Invoker.invokeIndirectly((AsynchronousChannel) this, completionHandler, obj, (Object) valueOf, th);
        }
    }

    class 2 implements Runnable {
        2() {
        }

        public void run() {
            synchronized (UnixAsynchronousSocketChannelImpl.-$$Nest$fgetupdateLock(UnixAsynchronousSocketChannelImpl.this)) {
                if (UnixAsynchronousSocketChannelImpl.-$$Nest$fgetwritePending(UnixAsynchronousSocketChannelImpl.this)) {
                    UnixAsynchronousSocketChannelImpl.-$$Nest$fputwritePending(UnixAsynchronousSocketChannelImpl.this, false);
                    CompletionHandler completionHandler = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetwriteHandler(UnixAsynchronousSocketChannelImpl.this);
                    Object obj = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetwriteAttachment(UnixAsynchronousSocketChannelImpl.this);
                    PendingFuture pendingFuture = UnixAsynchronousSocketChannelImpl.-$$Nest$fgetwriteFuture(UnixAsynchronousSocketChannelImpl.this);
                    UnixAsynchronousSocketChannelImpl.this.enableWriting(true);
                    InterruptedByTimeoutException interruptedByTimeoutException = new InterruptedByTimeoutException();
                    if (completionHandler != null) {
                        Invoker.invokeIndirectly((AsynchronousChannel) UnixAsynchronousSocketChannelImpl.this, completionHandler, obj, (Object) null, (Throwable) interruptedByTimeoutException);
                    } else {
                        pendingFuture.setFailure(interruptedByTimeoutException);
                    }
                }
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x009f A[Catch: all -> 0x00cf, TRY_LEAVE, TryCatch #4 {all -> 0x00cf, blocks: (B:51:0x009b, B:53:0x009f), top: B:50:0x009b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.util.concurrent.Future implWrite(boolean r18, java.nio.ByteBuffer r19, java.nio.ByteBuffer[] r20, long r21, java.util.concurrent.TimeUnit r23, java.lang.Object r24, java.nio.channels.CompletionHandler r25) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.UnixAsynchronousSocketChannelImpl.implWrite(boolean, java.nio.ByteBuffer, java.nio.ByteBuffer[], long, java.util.concurrent.TimeUnit, java.lang.Object, java.nio.channels.CompletionHandler):java.util.concurrent.Future");
    }
}
