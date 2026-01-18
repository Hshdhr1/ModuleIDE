package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.Channel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import sun.security.action.GetIntegerAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AsynchronousChannelGroupImpl extends AsynchronousChannelGroup implements Executor {
    private static final int internalThreadCount = ((Integer) AccessController.doPrivileged(new GetIntegerAction("sun.nio.ch.internalThreadPoolSize", 1))).intValue();
    private final ThreadPool pool;
    private final AtomicBoolean shutdown;
    private final Object shutdownNowLock;
    private final Queue taskQueue;
    private volatile boolean terminateInitiated;
    private final AtomicInteger threadCount;
    private ScheduledThreadPoolExecutor timeoutExecutor;

    static /* bridge */ /* synthetic */ ThreadPool -$$Nest$fgetpool(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        return asynchronousChannelGroupImpl.pool;
    }

    static /* bridge */ /* synthetic */ ScheduledThreadPoolExecutor -$$Nest$fgettimeoutExecutor(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        return asynchronousChannelGroupImpl.timeoutExecutor;
    }

    abstract Object attachForeignChannel(Channel channel, FileDescriptor fileDescriptor) throws IOException;

    abstract void closeAllChannels() throws IOException;

    abstract void detachForeignChannel(Object obj);

    abstract void executeOnHandlerTask(Runnable runnable);

    abstract boolean isEmpty();

    abstract void shutdownHandlerTasks();

    AsynchronousChannelGroupImpl(AsynchronousChannelProvider asynchronousChannelProvider, ThreadPool threadPool) {
        super(asynchronousChannelProvider);
        this.threadCount = new AtomicInteger();
        this.shutdown = new AtomicBoolean();
        this.shutdownNowLock = new Object();
        this.pool = threadPool;
        if (threadPool.isFixedThreadPool()) {
            this.taskQueue = new ConcurrentLinkedQueue();
        } else {
            this.taskQueue = null;
        }
        ScheduledThreadPoolExecutor newScheduledThreadPool = Executors.newScheduledThreadPool(1, ThreadPool.defaultThreadFactory());
        this.timeoutExecutor = newScheduledThreadPool;
        newScheduledThreadPool.setRemoveOnCancelPolicy(true);
    }

    final ExecutorService executor() {
        return this.pool.executor();
    }

    final boolean isFixedThreadPool() {
        return this.pool.isFixedThreadPool();
    }

    final int fixedThreadCount() {
        if (isFixedThreadPool()) {
            return this.pool.poolSize();
        }
        return this.pool.poolSize() + internalThreadCount;
    }

    class 1 implements Runnable {
        final /* synthetic */ Runnable val$task;
        final /* synthetic */ AsynchronousChannelGroupImpl val$thisGroup;

        1(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl, Runnable runnable) {
            this.val$thisGroup = asynchronousChannelGroupImpl;
            this.val$task = runnable;
        }

        public void run() {
            Invoker.bindToGroup(this.val$thisGroup);
            this.val$task.run();
        }
    }

    private Runnable bindToGroup(Runnable runnable) {
        return new 1(this, runnable);
    }

    class 2 implements PrivilegedAction {
        final /* synthetic */ Runnable val$task;

        2(Runnable runnable) {
            this.val$task = runnable;
        }

        public Void run() {
            ThreadPool.defaultThreadFactory().newThread(this.val$task).start();
            return null;
        }
    }

    private void startInternalThread(Runnable runnable) {
        AccessController.doPrivileged(new 2(runnable));
    }

    protected final void startThreads(Runnable runnable) {
        if (!isFixedThreadPool()) {
            for (int i = 0; i < internalThreadCount; i++) {
                startInternalThread(runnable);
                this.threadCount.incrementAndGet();
            }
        }
        if (this.pool.poolSize() > 0) {
            Runnable bindToGroup = bindToGroup(runnable);
            for (int i2 = 0; i2 < this.pool.poolSize(); i2++) {
                try {
                    this.pool.executor().execute(bindToGroup);
                    this.threadCount.incrementAndGet();
                } catch (RejectedExecutionException unused) {
                    return;
                }
            }
        }
    }

    final int threadCount() {
        return this.threadCount.get();
    }

    final int threadExit(Runnable runnable, boolean z) {
        if (z) {
            try {
                if (Invoker.isBoundToAnyGroup()) {
                    this.pool.executor().execute(bindToGroup(runnable));
                } else {
                    startInternalThread(runnable);
                }
                return this.threadCount.get();
            } catch (RejectedExecutionException unused) {
            }
        }
        return this.threadCount.decrementAndGet();
    }

    final void executeOnPooledThread(Runnable runnable) {
        if (isFixedThreadPool()) {
            executeOnHandlerTask(runnable);
        } else {
            this.pool.executor().execute(bindToGroup(runnable));
        }
    }

    final void offerTask(Runnable runnable) {
        this.taskQueue.offer(runnable);
    }

    final Runnable pollTask() {
        Queue queue = this.taskQueue;
        if (queue == null) {
            return null;
        }
        return (Runnable) queue.poll();
    }

    final Future schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            return this.timeoutExecutor.schedule(runnable, j, timeUnit);
        } catch (RejectedExecutionException e) {
            if (this.terminateInitiated) {
                return null;
            }
            throw new AssertionError(e);
        }
    }

    public final boolean isShutdown() {
        return this.shutdown.get();
    }

    public final boolean isTerminated() {
        return this.pool.executor().isTerminated();
    }

    class 3 implements PrivilegedAction {
        3() {
        }

        public Void run() {
            AsynchronousChannelGroupImpl.-$$Nest$fgetpool(AsynchronousChannelGroupImpl.this).executor().shutdown();
            AsynchronousChannelGroupImpl.-$$Nest$fgettimeoutExecutor(AsynchronousChannelGroupImpl.this).shutdown();
            return null;
        }
    }

    private void shutdownExecutors() {
        AccessController.doPrivileged(new 3(), (AccessControlContext) null, new Permission[]{new RuntimePermission("modifyThread")});
    }

    public final void shutdown() {
        if (!this.shutdown.getAndSet(true) && isEmpty()) {
            synchronized (this.shutdownNowLock) {
                if (!this.terminateInitiated) {
                    this.terminateInitiated = true;
                    shutdownHandlerTasks();
                    shutdownExecutors();
                }
            }
        }
    }

    public final void shutdownNow() throws IOException {
        this.shutdown.set(true);
        synchronized (this.shutdownNowLock) {
            if (!this.terminateInitiated) {
                this.terminateInitiated = true;
                closeAllChannels();
                shutdownHandlerTasks();
                shutdownExecutors();
            }
        }
    }

    final void detachFromThreadPool() {
        if (this.shutdown.getAndSet(true)) {
            throw new AssertionError("Already shutdown");
        }
        if (!isEmpty()) {
            throw new AssertionError("Group not empty");
        }
        shutdownHandlerTasks();
    }

    public final boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.pool.executor().awaitTermination(j, timeUnit);
    }

    public final void execute(Runnable runnable) {
        if (System.getSecurityManager() != null) {
            runnable = new 4(runnable, AccessController.getContext());
        }
        executeOnPooledThread(runnable);
    }

    class 4 implements Runnable {
        final /* synthetic */ AccessControlContext val$acc;
        final /* synthetic */ Runnable val$delegate;

        4(Runnable runnable, AccessControlContext accessControlContext) {
            this.val$delegate = runnable;
            this.val$acc = accessControlContext;
        }

        class 1 implements PrivilegedAction {
            1() {
            }

            public Void run() {
                4.this.val$delegate.run();
                return null;
            }
        }

        public void run() {
            AccessController.doPrivileged(new 1(), this.val$acc);
        }
    }
}
