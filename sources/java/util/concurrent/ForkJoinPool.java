package java.util.concurrent;

import java.lang.Thread;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Predicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ForkJoinPool extends AbstractExecutorService {
    private static final long ADD_WORKER = 140737488355328L;
    private static final int COMMON_MAX_SPARES;
    static final int COMMON_PARALLELISM;
    private static final VarHandle CTL;
    private static final int DEFAULT_COMMON_MAX_SPARES = 256;
    private static final long DEFAULT_KEEPALIVE = 60000;
    static final int DORMANT = -1073741824;
    static final int FIFO = 65536;
    static final int INITIAL_QUEUE_CAPACITY = 8192;
    static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
    static final int MAX_CAP = 32767;
    private static final VarHandle MODE;
    static final int OWNED = 1;
    static final VarHandle QA;
    static final int QLOCK = 1;
    static final int QUIET = 1073741824;
    private static final long RC_MASK = -281474976710656L;
    private static final int RC_SHIFT = 48;
    private static final long RC_UNIT = 281474976710656L;
    private static final int SEED_INCREMENT = -1640531527;
    static final int SHUTDOWN = 262144;
    static final int SMASK = 65535;
    private static final long SP_MASK = 4294967295L;
    static final int SQMASK = 126;
    static final int SS_SEQ = 65536;
    static final int STOP = Integer.MIN_VALUE;
    static final int SWIDTH = 16;
    private static final long TC_MASK = 281470681743360L;
    private static final int TC_SHIFT = 32;
    private static final long TC_UNIT = 4294967296L;
    static final int TERMINATED = 524288;
    private static final long TIMEOUT_SLOP = 20;
    static final int TOP_BOUND_SHIFT = 10;
    private static final long UC_MASK = -4294967296L;
    static final int UNSIGNALLED = Integer.MIN_VALUE;
    static final ForkJoinPool common;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    static final RuntimePermission modifyThreadPermission;
    private static int poolNumberSequence;
    final int bounds;
    volatile long ctl;
    final ForkJoinWorkerThreadFactory factory;
    int indexSeed;
    final long keepAlive;
    volatile int mode;
    final Predicate saturate;
    volatile long stealCount;
    final Thread.UncaughtExceptionHandler ueh;
    WorkQueue[] workQueues;
    final String workerNamePrefix;

    public interface ForkJoinWorkerThreadFactory {
        ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool);
    }

    public interface ManagedBlocker {
        boolean block() throws InterruptedException;

        boolean isReleasable();
    }

    /* synthetic */ ForkJoinPool(byte b, ForkJoinPool-IA r2) {
        this(b);
    }

    private static void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(modifyThreadPermission);
        }
    }

    static AccessControlContext contextWithPermissions(Permission... permissionArr) {
        Permissions permissions = new Permissions();
        for (Permission permission : permissionArr) {
            permissions.add(permission);
        }
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource) null, permissions)});
    }

    private static final class DefaultForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory {
        private static final AccessControlContext ACC = ForkJoinPool.contextWithPermissions(new RuntimePermission("getClassLoader"), new RuntimePermission("setContextClassLoader"));

        /* synthetic */ DefaultForkJoinWorkerThreadFactory(ForkJoinPool-IA r1) {
            this();
        }

        private DefaultForkJoinWorkerThreadFactory() {
        }

        class 1 implements PrivilegedAction {
            final /* synthetic */ ForkJoinPool val$pool;

            1(ForkJoinPool forkJoinPool) {
                this.val$pool = forkJoinPool;
            }

            public ForkJoinWorkerThread run() {
                return new ForkJoinWorkerThread(this.val$pool, ClassLoader.getSystemClassLoader());
            }
        }

        public final ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool) {
            return (ForkJoinWorkerThread) AccessController.doPrivileged(new 1(forkJoinPool), ACC);
        }
    }

    static final class WorkQueue {
        static final VarHandle BASE;
        static final VarHandle PHASE;
        static final VarHandle TOP;
        ForkJoinTask[] array;
        int id;
        int nsteals;
        final ForkJoinWorkerThread owner;
        volatile int phase;
        final ForkJoinPool pool;
        volatile int source;
        int stackPred;
        int top = 4096;
        int base = 4096;

        WorkQueue(ForkJoinPool forkJoinPool, ForkJoinWorkerThread forkJoinWorkerThread) {
            this.pool = forkJoinPool;
            this.owner = forkJoinWorkerThread;
        }

        final boolean tryLockPhase() {
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final void releasePhaseLock() {
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        final int getPoolIndex() {
            return (this.id & 65535) >>> 1;
        }

        final int queueSize() {
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            int i = 0 - this.top;
            if (i >= 0) {
                return 0;
            }
            return -i;
        }

        final boolean isEmpty() {
            int length;
            VarHandle.acquireFence();
            int i = this.base;
            int i2 = i - this.top;
            if (i2 < 0) {
                if (i2 != -1) {
                    return false;
                }
                ForkJoinTask[] forkJoinTaskArr = this.array;
                if (forkJoinTaskArr != null && (length = forkJoinTaskArr.length) != 0 && forkJoinTaskArr[i & (length - 1)] != null) {
                    return false;
                }
            }
            return true;
        }

        final void push(ForkJoinTask forkJoinTask) {
            int length;
            int i = this.top;
            ForkJoinPool forkJoinPool = this.pool;
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr == null || (length = forkJoinTaskArr.length) <= 0) {
                return;
            }
            VarHandle varHandle = ForkJoinPool.QA;
            int i2 = length - 1;
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            this.top = i + 1;
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            if ((i & (-2)) == 0 && forkJoinPool != null) {
                VarHandle.fullFence();
                forkJoinPool.signalWork();
            } else if (i == i2) {
                growArray(false);
            }
        }

        final boolean lockedPush(ForkJoinTask forkJoinTask) {
            int length;
            int i = this.top;
            int i2 = this.base;
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr != null && (length = forkJoinTaskArr.length) > 0) {
                forkJoinTaskArr[(length - 1) & i] = forkJoinTask;
                this.top = i + 1;
                if (((i2 - i) + length) - 1 == 0) {
                    growArray(true);
                    return false;
                }
                this.phase = 0;
                if (((i - this.base) & (-2)) == 0) {
                    return true;
                }
            }
            return false;
        }

        final void growArray(boolean z) {
            int length;
            int i;
            try {
                ForkJoinTask[] forkJoinTaskArr = this.array;
                ForkJoinTask[] forkJoinTaskArr2 = null;
                if (forkJoinTaskArr != null && (length = forkJoinTaskArr.length) > 0 && (i = length << 1) <= 67108864 && i > 0) {
                    try {
                        forkJoinTaskArr2 = new ForkJoinTask[i];
                    } catch (OutOfMemoryError unused) {
                    }
                    if (forkJoinTaskArr2 != null) {
                        if (length - 1 >= 0) {
                            VarHandle varHandle = ForkJoinPool.QA;
                            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        }
                        this.array = forkJoinTaskArr2;
                        VarHandle.releaseFence();
                    }
                }
                if (forkJoinTaskArr2 == null) {
                    throw new RejectedExecutionException("Queue capacity exceeded");
                }
            } finally {
                if (z) {
                    this.phase = 0;
                }
            }
        }

        final ForkJoinTask poll() {
            while (true) {
                ForkJoinTask[] forkJoinTaskArr = this.array;
                if (forkJoinTaskArr == null || forkJoinTaskArr.length <= 0) {
                    return null;
                }
                int i = this.top;
                int i2 = this.base;
                if (i - i2 <= 0) {
                    return null;
                }
                VarHandle varHandle = ForkJoinPool.QA;
                ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                if (this.base == i2) {
                    Thread.yield();
                }
            }
        }

        final ForkJoinTask nextLocalTask() {
            int i;
            int i2 = this.id;
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr != null && forkJoinTaskArr.length > 0 && (i = this.top - this.base) > 0) {
                if ((i2 & 65536) == 0 || i == 1) {
                    VarHandle varHandle = ForkJoinPool.QA;
                    ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                } else {
                    VarHandle varHandle2 = ForkJoinPool.QA;
                    ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    return poll();
                }
            }
            return null;
        }

        final ForkJoinTask peek() {
            int length;
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr == null || (length = forkJoinTaskArr.length) <= 0) {
                return null;
            }
            return forkJoinTaskArr[(length - 1) & ((this.id & 65536) != 0 ? this.base : this.top - 1)];
        }

        final boolean tryUnpush(ForkJoinTask forkJoinTask) {
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr == null || forkJoinTaskArr.length <= 0 || this.top == this.base) {
                return false;
            }
            VarHandle varHandle = ForkJoinPool.QA;
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final boolean tryLockedUnpush(ForkJoinTask forkJoinTask) {
            int length;
            int i = this.top;
            int i2 = i - 1;
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr == null || (length = forkJoinTaskArr.length) <= 0 || forkJoinTaskArr[i2 & (length - 1)] != forkJoinTask || !tryLockPhase()) {
                return false;
            }
            if (this.top == i && this.array == forkJoinTaskArr) {
                VarHandle varHandle = ForkJoinPool.QA;
                ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            releasePhaseLock();
            return false;
        }

        final void cancelAll() {
            while (true) {
                ForkJoinTask poll = poll();
                if (poll == null) {
                    return;
                } else {
                    ForkJoinTask.cancelIgnoringExceptions(poll);
                }
            }
        }

        final void topLevelExec(ForkJoinTask forkJoinTask, WorkQueue workQueue, int i) {
            if (forkJoinTask == null || workQueue == null) {
                return;
            }
            int i2 = 1;
            while (true) {
                forkJoinTask.doExec();
                int i3 = i - 1;
                if (i < 0) {
                    break;
                }
                ForkJoinTask nextLocalTask = nextLocalTask();
                if (nextLocalTask == null) {
                    nextLocalTask = workQueue.poll();
                    if (nextLocalTask == null) {
                        break;
                    } else {
                        i2++;
                    }
                }
                ForkJoinTask forkJoinTask2 = nextLocalTask;
                i = i3;
                forkJoinTask = forkJoinTask2;
            }
            ForkJoinWorkerThread forkJoinWorkerThread = this.owner;
            this.nsteals += i2;
            this.source = 0;
            if (forkJoinWorkerThread != null) {
                forkJoinWorkerThread.afterTopLevelExec();
            }
        }

        final void tryRemoveAndExec(ForkJoinTask forkJoinTask) {
            ForkJoinTask[] forkJoinTaskArr = this.array;
            if (forkJoinTaskArr == null || forkJoinTaskArr.length <= 0 || this.top - this.base <= 0) {
                return;
            }
            VarHandle varHandle = ForkJoinPool.QA;
            ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        final int helpCC(CountedCompleter countedCompleter, int i, boolean z) {
            ForkJoinTask[] forkJoinTaskArr;
            int length;
            if (countedCompleter == null) {
                return 0;
            }
            int i2 = countedCompleter.status;
            if (i2 >= 0 && (forkJoinTaskArr = this.array) != null && (length = forkJoinTaskArr.length) > 0) {
                int i3 = this.top;
                if (i3 - this.base > 0) {
                    ForkJoinTask forkJoinTask = forkJoinTaskArr[(i3 - 1) & (length - 1)];
                    if (forkJoinTask instanceof CountedCompleter) {
                        CountedCompleter countedCompleter2 = (CountedCompleter) forkJoinTask;
                        while (true) {
                            if (countedCompleter2 != countedCompleter) {
                                countedCompleter2 = countedCompleter2.completer;
                                if (countedCompleter2 == null) {
                                    break;
                                }
                            } else if (z) {
                                if (tryLockPhase()) {
                                    if (this.top == i3 && this.array == forkJoinTaskArr) {
                                        VarHandle varHandle = ForkJoinPool.QA;
                                        ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                                    }
                                    releasePhaseLock();
                                }
                            } else {
                                VarHandle varHandle2 = ForkJoinPool.QA;
                                ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                            }
                        }
                    }
                    return countedCompleter.status;
                }
            }
            return i2;
        }

        final void helpAsyncBlocker(ManagedBlocker managedBlocker) {
            if (managedBlocker != null) {
                do {
                    ForkJoinTask[] forkJoinTaskArr = this.array;
                    if (forkJoinTaskArr == null || forkJoinTaskArr.length <= 0 || this.top - this.base <= 0) {
                        return;
                    }
                    VarHandle varHandle = ForkJoinPool.QA;
                    ForkJoinPool$WorkQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                } while (!managedBlocker.isReleasable());
            }
        }

        final boolean isApparentlyUnblocked() {
            Thread.State state;
            ForkJoinWorkerThread forkJoinWorkerThread = this.owner;
            return (forkJoinWorkerThread == null || (state = forkJoinWorkerThread.getState()) == Thread.State.BLOCKED || state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) ? false : true;
        }

        static {
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                PHASE = lookup.findVarHandle(WorkQueue.class, "phase", Integer.TYPE);
                BASE = lookup.findVarHandle(WorkQueue.class, "base", Integer.TYPE);
                TOP = lookup.findVarHandle(WorkQueue.class, "top", Integer.TYPE);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static final synchronized int nextPoolId() {
        int i;
        synchronized (ForkJoinPool.class) {
            i = poolNumberSequence + 1;
            poolNumberSequence = i;
        }
        return i;
    }

    private boolean createWorker() {
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = this.factory;
        Throwable th = null;
        if (forkJoinWorkerThreadFactory != null) {
            try {
                forkJoinWorkerThread = forkJoinWorkerThreadFactory.newThread(this);
                if (forkJoinWorkerThread != null) {
                    try {
                        forkJoinWorkerThread.start();
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                forkJoinWorkerThread = null;
            }
        } else {
            forkJoinWorkerThread = null;
        }
        deregisterWorker(forkJoinWorkerThread, th);
        return false;
    }

    private void tryAddWorker(long j) {
        do {
            if (this.ctl == j) {
                ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            j = this.ctl;
            if ((140737488355328L & j) == 0) {
                return;
            }
        } while (((int) j) == 0);
    }

    final WorkQueue registerWorker(ForkJoinWorkerThread forkJoinWorkerThread) {
        int i;
        int length;
        forkJoinWorkerThread.setDaemon(true);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.ueh;
        if (uncaughtExceptionHandler != null) {
            forkJoinWorkerThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        int i2 = this.mode & 65536;
        String str = this.workerNamePrefix;
        WorkQueue workQueue = new WorkQueue(this, forkJoinWorkerThread);
        if (str == null) {
            return workQueue;
        }
        synchronized (str) {
            WorkQueue[] workQueueArr = this.workQueues;
            int i3 = this.indexSeed - 1640531527;
            this.indexSeed = i3;
            int i4 = i2 | (1073610752 & i3);
            i = 0;
            if (workQueueArr != null && (length = workQueueArr.length) > 1) {
                int i5 = length - 1;
                int i6 = ((i3 << 1) | 1) & i5;
                int i7 = length >>> 1;
                while (true) {
                    WorkQueue workQueue2 = workQueueArr[i6];
                    if (workQueue2 == null || workQueue2.phase == 1073741824) {
                        break;
                    }
                    i7--;
                    if (i7 == 0) {
                        i6 = length | 1;
                        break;
                    }
                    i6 = (i6 + 2) & i5;
                }
                int i8 = i4 | i6;
                workQueue.id = i8;
                workQueue.phase = i8;
                if (i6 < length) {
                    workQueueArr[i6] = workQueue;
                } else {
                    int i9 = length << 1;
                    WorkQueue[] workQueueArr2 = new WorkQueue[i9];
                    workQueueArr2[i6] = workQueue;
                    int i10 = i9 - 1;
                    while (i < length) {
                        WorkQueue workQueue3 = workQueueArr[i];
                        if (workQueue3 != null) {
                            workQueueArr2[workQueue3.id & i10 & 126] = workQueue3;
                        }
                        int i11 = i + 1;
                        if (i11 >= length) {
                            break;
                        }
                        workQueueArr2[i11] = workQueueArr[i11];
                        i += 2;
                    }
                    this.workQueues = workQueueArr2;
                }
                i = i6;
            }
        }
        forkJoinWorkerThread.setName(str.concat(Integer.toString(i)));
        return workQueue;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003b A[LOOP:0: B:27:0x003b->B:28:0x003b, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void deregisterWorker(java.util.concurrent.ForkJoinWorkerThread r9, java.lang.Throwable r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            if (r9 == 0) goto L35
            java.util.concurrent.ForkJoinPool$WorkQueue r9 = r9.workQueue
            if (r9 == 0) goto L36
            java.lang.String r2 = r8.workerNamePrefix
            int r3 = r9.id
            int r4 = r9.nsteals
            long r4 = (long) r4
            r6 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r6
            if (r2 == 0) goto L32
            monitor-enter(r2)
            java.util.concurrent.ForkJoinPool$WorkQueue[] r6 = r8.workQueues     // Catch: java.lang.Throwable -> L2f
            if (r6 == 0) goto L28
            int r7 = r6.length     // Catch: java.lang.Throwable -> L2f
            if (r7 <= 0) goto L28
            int r7 = r7 + (-1)
            r3 = r3 & r7
            r7 = r6[r3]     // Catch: java.lang.Throwable -> L2f
            if (r7 != r9) goto L28
            r6[r3] = r0     // Catch: java.lang.Throwable -> L2f
        L28:
            long r6 = r8.stealCount     // Catch: java.lang.Throwable -> L2f
            long r6 = r6 + r4
            r8.stealCount = r6     // Catch: java.lang.Throwable -> L2f
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2f
            goto L32
        L2f:
            r9 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2f
            throw r9
        L32:
            int r0 = r9.phase
            goto L37
        L35:
            r9 = r0
        L36:
            r0 = 0
        L37:
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 == r2) goto L41
        L3b:
            java.lang.String r9 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.ForkJoinPool$$ExternalSyntheticThrowRTE0.m(r9)
            goto L3b
        L41:
            if (r9 == 0) goto L46
            r9.cancelAll()
        L46:
            boolean r0 = r8.tryTerminate(r1, r1)
            if (r0 != 0) goto L55
            if (r9 == 0) goto L55
            java.util.concurrent.ForkJoinTask[] r9 = r9.array
            if (r9 == 0) goto L55
            r8.signalWork()
        L55:
            if (r10 != 0) goto L5b
            java.util.concurrent.ForkJoinTask.helpExpungeStaleExceptions()
            return
        L5b:
            java.util.concurrent.ForkJoinTask.rethrow(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinPool.deregisterWorker(java.util.concurrent.ForkJoinWorkerThread, java.lang.Throwable):void");
    }

    final void signalWork() {
        int i;
        WorkQueue workQueue;
        while (true) {
            long j = this.ctl;
            if (j >= 0) {
                return;
            }
            int i2 = (int) j;
            if (i2 == 0) {
                if ((140737488355328L & j) != 0) {
                    tryAddWorker(j);
                    return;
                }
                return;
            }
            WorkQueue[] workQueueArr = this.workQueues;
            if (workQueueArr == null || workQueueArr.length <= (i = 65535 & i2) || (workQueue = workQueueArr[i]) == null) {
                return;
            }
            int i3 = workQueue.phase;
            int i4 = workQueue.stackPred;
            ForkJoinWorkerThread forkJoinWorkerThread = workQueue.owner;
            if (i2 == i3) {
                ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    private int tryCompensate(WorkQueue workQueue) {
        int length;
        Thread.State state;
        long j = this.ctl;
        WorkQueue[] workQueueArr = this.workQueues;
        short s = (short) (j >>> 32);
        if (s >= 0) {
            if (workQueueArr != null && (length = workQueueArr.length) > 0 && workQueue != null) {
                int i = (int) j;
                int i2 = 1;
                if (i != 0) {
                    WorkQueue workQueue2 = workQueueArr[i & (length - 1)];
                    int i3 = workQueue.phase;
                    if (workQueue2 != null) {
                        int i4 = workQueue2.phase;
                        ForkJoinWorkerThread forkJoinWorkerThread = workQueue2.owner;
                        int i5 = workQueue2.stackPred;
                        if (i4 == i) {
                            ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        }
                    }
                    return 0;
                }
                if (((int) (j >> 48)) - ((short) (this.bounds & 65535)) > 0) {
                    ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    return 0;
                }
                int i6 = this.mode & 65535;
                int i7 = i6 + s;
                int i8 = i7;
                int i9 = 0;
                while (true) {
                    if (i2 < length) {
                        WorkQueue workQueue3 = workQueueArr[i2];
                        if (workQueue3 != null) {
                            if (workQueue3.source == 0) {
                                break;
                            }
                            i8--;
                            ForkJoinWorkerThread forkJoinWorkerThread2 = workQueue3.owner;
                            if (forkJoinWorkerThread2 != null && ((state = forkJoinWorkerThread2.getState()) == Thread.State.BLOCKED || state == Thread.State.WAITING)) {
                                i9++;
                            }
                        }
                        i2 += 2;
                    } else if (i8 == 0 && this.ctl == j) {
                        if (i7 >= 32767 || s >= (this.bounds >>> 16)) {
                            Predicate predicate = this.saturate;
                            if (predicate != null && predicate.test(this)) {
                                return -1;
                            }
                            if (i9 < i6) {
                                Thread.yield();
                                return 0;
                            }
                            throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
                        }
                    }
                }
            }
            return 0;
        }
        ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return 0;
    }

    final void runWorker(WorkQueue workQueue) {
        int nextSecondarySeed = (workQueue.id ^ ThreadLocalRandom.nextSecondarySeed()) | 65536;
        workQueue.array = new ForkJoinTask[8192];
        while (true) {
            if (scan(workQueue, nextSecondarySeed)) {
                int i = nextSecondarySeed ^ (nextSecondarySeed << 13);
                int i2 = i ^ (i >>> 17);
                nextSecondarySeed = i2 ^ (i2 << 5);
            } else {
                int i3 = workQueue.phase;
                if (i3 >= 0) {
                    workQueue.phase = Integer.MIN_VALUE | (i3 + 65536);
                    while (true) {
                        workQueue.stackPred = (int) this.ctl;
                        ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    }
                } else {
                    int i4 = workQueue.stackPred;
                    Thread.interrupted();
                    workQueue.source = -1073741824;
                    long j = this.ctl;
                    int i5 = this.mode;
                    int i6 = (65535 & i5) + ((int) (j >> 48));
                    if (i5 < 0) {
                        return;
                    }
                    if (i6 <= 0 && (i5 & 262144) != 0 && tryTerminate(false, false)) {
                        return;
                    }
                    if (i6 <= 0 && i4 != 0 && i3 == ((int) j)) {
                        long currentTimeMillis = this.keepAlive + System.currentTimeMillis();
                        LockSupport.parkUntil(this, currentTimeMillis);
                        if (this.ctl == j && currentTimeMillis - System.currentTimeMillis() <= 20) {
                            ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        }
                    } else if (workQueue.phase < 0) {
                        LockSupport.park(this);
                    }
                    workQueue.source = 0;
                }
            }
        }
    }

    private boolean scan(WorkQueue workQueue, int i) {
        int length;
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr == null || (length = workQueueArr.length) <= 0 || workQueue == null) {
            return false;
        }
        int i2 = length - 1;
        while (true) {
            int i3 = i & i2;
            WorkQueue workQueue2 = workQueueArr[i3];
            if (workQueue2 != null && workQueue2.top != workQueue2.base) {
                int i4 = workQueue2.id;
                ForkJoinTask[] forkJoinTaskArr = workQueue2.array;
                if (forkJoinTaskArr == null || forkJoinTaskArr.length <= 0) {
                    return true;
                }
                ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                int i5 = workQueue2.base;
                return true;
            }
            length--;
            if (length <= 0) {
                return false;
            }
            i = i3 + 1;
        }
    }

    final int awaitJoin(WorkQueue workQueue, ForkJoinTask forkJoinTask, long j) {
        int helpCC;
        int nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed();
        if (workQueue == null || forkJoinTask == null) {
            return 0;
        }
        if ((forkJoinTask instanceof CountedCompleter) && (helpCC = workQueue.helpCC((CountedCompleter) forkJoinTask, 0, false)) < 0) {
            return helpCC;
        }
        workQueue.tryRemoveAndExec(forkJoinTask);
        int i = workQueue.source;
        int i2 = workQueue.id;
        int i3 = (nextSecondarySeed >>> 16) | 1;
        int i4 = (nextSecondarySeed & (-2)) | 2;
        int i5 = forkJoinTask.status;
        while (i5 >= 0) {
            WorkQueue[] workQueueArr = this.workQueues;
            int length = workQueueArr == null ? 0 : workQueueArr.length;
            int i6 = length - 1;
            while (true) {
                if (length <= 0) {
                    break;
                }
                WorkQueue workQueue2 = workQueueArr[i3 & i6];
                if (workQueue2 == null || workQueue2.source != i2 || workQueue2.top == workQueue2.base) {
                    i3 += i4;
                    length--;
                } else {
                    int i7 = workQueue2.id;
                    ForkJoinTask[] forkJoinTaskArr = workQueue2.array;
                    if (forkJoinTaskArr != null && forkJoinTaskArr.length > 0) {
                        ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        if (workQueue2.source == i2) {
                            int i8 = workQueue2.base;
                        }
                    }
                }
            }
            i5 = forkJoinTask.status;
            if (i5 >= 0) {
                if (length == 0) {
                    long j2 = 0;
                    if (j != 0) {
                        long nanoTime = j - System.nanoTime();
                        if (nanoTime > 0) {
                            long millis = TimeUnit.NANOSECONDS.toMillis(nanoTime);
                            j2 = millis <= 0 ? 1L : millis;
                        }
                    }
                    if (tryCompensate(workQueue) != 0) {
                        forkJoinTask.internalWait(j2);
                        ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    }
                    i5 = forkJoinTask.status;
                }
            }
            return i5;
        }
        return i5;
    }

    final void helpQuiescePool(WorkQueue workQueue) {
        boolean z;
        int i = workQueue.source;
        int nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed() >>> 16;
        int i2 = nextSecondarySeed | 1;
        char c = 65535;
        int i3 = i;
        char c2 = 65535;
        while (true) {
            ForkJoinTask nextLocalTask = workQueue.nextLocalTask();
            if (nextLocalTask != null) {
                nextLocalTask.doExec();
            } else {
                if (workQueue.phase >= 0 && c2 == c) {
                    c2 = 1;
                }
                WorkQueue[] workQueueArr = this.workQueues;
                int length = workQueueArr == null ? 0 : workQueueArr.length;
                int i4 = length - 1;
                boolean z2 = true;
                while (true) {
                    if (length <= 0) {
                        z = true;
                        break;
                    }
                    WorkQueue workQueue2 = workQueueArr[nextSecondarySeed & i4];
                    if (workQueue2 != null) {
                        int i5 = workQueue2.source;
                        if (workQueue2.top != workQueue2.base) {
                            int i6 = workQueue2.id;
                            ForkJoinTask[] forkJoinTaskArr = workQueue2.array;
                            if (forkJoinTaskArr != null && forkJoinTaskArr.length > 0) {
                                if (c2 == 0) {
                                    ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                                    c2 = 1;
                                }
                                ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                                int i7 = workQueue2.base;
                            }
                            z = false;
                            z2 = false;
                        } else if ((i5 & 1073741824) == 0) {
                            z2 = false;
                        }
                    }
                    nextSecondarySeed += i2;
                    length--;
                }
                if (z2) {
                    break;
                }
                if (z) {
                    if (i3 != 1073741824) {
                        workQueue.source = 1073741824;
                        i3 = 1073741824;
                    }
                    if (c2 == 1) {
                        ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        c2 = 0;
                    }
                }
                c = 65535;
            }
        }
        if (c2 == 0) {
            ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
        workQueue.source = i;
    }

    private ForkJoinTask pollScan(boolean z) {
        WorkQueue[] workQueueArr;
        int length;
        int i;
        int i2;
        if ((this.mode & Integer.MIN_VALUE) != 0 || (workQueueArr = this.workQueues) == null || (length = workQueueArr.length) <= 0) {
            return null;
        }
        int i3 = length - 1;
        int nextSecondarySeed = ThreadLocalRandom.nextSecondarySeed();
        int i4 = nextSecondarySeed >>> 16;
        if (z) {
            i = nextSecondarySeed & (-2) & i3;
            i2 = (i4 & (-2)) | 2;
        } else {
            i = nextSecondarySeed & i3;
            i2 = i4 | 1;
        }
        int i5 = i;
        int i6 = 0;
        boolean z2 = false;
        int i7 = 0;
        while (true) {
            WorkQueue workQueue = workQueueArr[i5];
            if (workQueue != null) {
                int i8 = workQueue.top;
                int i9 = workQueue.base;
                if (i8 - i9 > 0) {
                    ForkJoinTask poll = workQueue.poll();
                    if (poll != null) {
                        return poll;
                    }
                    z2 = true;
                } else {
                    i6 += i9 + workQueue.id;
                }
            }
            i5 = (i5 + i2) & i3;
            if (i5 == i) {
                if (!z2) {
                    if (i7 == i6) {
                        return null;
                    }
                    i7 = i6;
                }
                i6 = 0;
                z2 = false;
            }
        }
    }

    final ForkJoinTask nextTaskFor(WorkQueue workQueue) {
        ForkJoinTask nextLocalTask;
        return (workQueue == null || (nextLocalTask = workQueue.nextLocalTask()) == null) ? pollScan(false) : nextLocalTask;
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0073, code lost:
    
        throw new java.util.concurrent.RejectedExecutionException();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void externalPush(java.util.concurrent.ForkJoinTask r8) {
        /*
            r7 = this;
            int r0 = java.util.concurrent.ThreadLocalRandom.getProbe()
            if (r0 != 0) goto Ld
            java.util.concurrent.ThreadLocalRandom.localInit()
            int r0 = java.util.concurrent.ThreadLocalRandom.getProbe()
        Ld:
            int r1 = r7.mode
            java.util.concurrent.ForkJoinPool$WorkQueue[] r2 = r7.workQueues
            r3 = 262144(0x40000, float:3.67342E-40)
            r1 = r1 & r3
            if (r1 != 0) goto L6d
            if (r2 == 0) goto L6d
            int r1 = r2.length
            if (r1 <= 0) goto L6d
            int r1 = r1 + (-1)
            r1 = r1 & r0
            r1 = r1 & 126(0x7e, float:1.77E-43)
            r1 = r2[r1]
            if (r1 != 0) goto L58
            r1 = 1073741824(0x40000000, float:2.0)
            r2 = r0 | r1
            r3 = -65538(0xfffffffffffefffe, float:NaN)
            r2 = r2 & r3
            java.lang.String r3 = r7.workerNamePrefix
            r4 = 8192(0x2000, float:1.148E-41)
            java.util.concurrent.ForkJoinTask[] r4 = new java.util.concurrent.ForkJoinTask[r4]
            java.util.concurrent.ForkJoinPool$WorkQueue r5 = new java.util.concurrent.ForkJoinPool$WorkQueue
            r6 = 0
            r5.<init>(r7, r6)
            r5.array = r4
            r5.id = r2
            r5.source = r1
            if (r3 == 0) goto Ld
            monitor-enter(r3)
            java.util.concurrent.ForkJoinPool$WorkQueue[] r1 = r7.workQueues     // Catch: java.lang.Throwable -> L55
            if (r1 == 0) goto L53
            int r4 = r1.length     // Catch: java.lang.Throwable -> L55
            if (r4 <= 0) goto L53
            int r4 = r4 + (-1)
            r2 = r2 & r4
            r2 = r2 & 126(0x7e, float:1.77E-43)
            r4 = r1[r2]     // Catch: java.lang.Throwable -> L55
            if (r4 != 0) goto L53
            r1[r2] = r5     // Catch: java.lang.Throwable -> L55
        L53:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L55
            goto Ld
        L55:
            r8 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L55
            throw r8
        L58:
            boolean r2 = r1.tryLockPhase()
            if (r2 != 0) goto L63
            int r0 = java.util.concurrent.ThreadLocalRandom.advanceProbe(r0)
            goto Ld
        L63:
            boolean r8 = r1.lockedPush(r8)
            if (r8 == 0) goto L6c
            r7.signalWork()
        L6c:
            return
        L6d:
            java.util.concurrent.RejectedExecutionException r8 = new java.util.concurrent.RejectedExecutionException
            r8.<init>()
            goto L74
        L73:
            throw r8
        L74:
            goto L73
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinPool.externalPush(java.util.concurrent.ForkJoinTask):void");
    }

    private ForkJoinTask externalSubmit(ForkJoinTask forkJoinTask) {
        WorkQueue workQueue;
        forkJoinTask.getClass();
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            if (forkJoinWorkerThread.pool == this && (workQueue = forkJoinWorkerThread.workQueue) != null) {
                workQueue.push(forkJoinTask);
                return forkJoinTask;
            }
        }
        externalPush(forkJoinTask);
        return forkJoinTask;
    }

    static WorkQueue commonSubmitterQueue() {
        WorkQueue[] workQueueArr;
        int length;
        ForkJoinPool forkJoinPool = common;
        int probe = ThreadLocalRandom.getProbe();
        if (forkJoinPool == null || (workQueueArr = forkJoinPool.workQueues) == null || (length = workQueueArr.length) <= 0) {
            return null;
        }
        return workQueueArr[probe & (length - 1) & 126];
    }

    final boolean tryExternalUnpush(ForkJoinTask forkJoinTask) {
        int length;
        WorkQueue workQueue;
        int probe = ThreadLocalRandom.getProbe();
        WorkQueue[] workQueueArr = this.workQueues;
        return workQueueArr != null && (length = workQueueArr.length) > 0 && (workQueue = workQueueArr[(probe & (length - 1)) & 126]) != null && workQueue.tryLockedUnpush(forkJoinTask);
    }

    final int externalHelpComplete(CountedCompleter countedCompleter, int i) {
        int length;
        WorkQueue workQueue;
        int probe = ThreadLocalRandom.getProbe();
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr == null || (length = workQueueArr.length) <= 0 || (workQueue = workQueueArr[probe & (length - 1) & 126]) == null) {
            return 0;
        }
        return workQueue.helpCC(countedCompleter, i, true);
    }

    final int helpComplete(WorkQueue workQueue, CountedCompleter countedCompleter, int i) {
        if (workQueue == null) {
            return 0;
        }
        return workQueue.helpCC(countedCompleter, i, false);
    }

    static int getSurplusQueuedTaskCount() {
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinPool forkJoinPool;
        WorkQueue workQueue;
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        int i = 0;
        if (!(currentThread instanceof ForkJoinWorkerThread) || (forkJoinPool = (forkJoinWorkerThread = currentThread).pool) == null || (workQueue = forkJoinWorkerThread.workQueue) == null) {
            return 0;
        }
        int i2 = forkJoinPool.mode & 65535;
        int i3 = ((int) (forkJoinPool.ctl >> 48)) + i2;
        int i4 = workQueue.top - workQueue.base;
        if (i3 <= (i2 >>> 1)) {
            if (i3 > (i2 >>> 2)) {
                i = 1;
            } else if (i3 > (i2 >>> 3)) {
                i = 2;
            } else {
                i = 4;
                if (i3 <= (i2 >>> 4)) {
                    i = 8;
                }
            }
        }
        return i4 - i;
    }

    private boolean tryTerminate(boolean z, boolean z2) {
        int i;
        int i2;
        long j;
        int i3;
        long j2;
        boolean z3;
        boolean z4;
        long j3;
        int i4;
        while ((this.mode & 262144) == 0) {
            if (!z2 || this == common) {
                return false;
            }
            ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
        while (true) {
            int i5 = this.mode;
            int i6 = 65535;
            long j4 = 0;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                while ((this.mode & 524288) == 0) {
                    long j5 = 0;
                    while (true) {
                        long j6 = this.ctl;
                        WorkQueue[] workQueueArr = this.workQueues;
                        if (workQueueArr != null) {
                            int i7 = 0;
                            while (i7 < workQueueArr.length) {
                                WorkQueue workQueue = workQueueArr[i7];
                                if (workQueue != null) {
                                    ForkJoinWorkerThread forkJoinWorkerThread = workQueue.owner;
                                    workQueue.cancelAll();
                                    if (forkJoinWorkerThread != null) {
                                        try {
                                            forkJoinWorkerThread.interrupt();
                                        } catch (Throwable unused) {
                                        }
                                    }
                                    j = j5;
                                    j6 += (workQueue.phase << 32) + workQueue.base;
                                } else {
                                    j = j5;
                                }
                                i7++;
                                j5 = j;
                            }
                        }
                        long j7 = j5;
                        i = this.mode;
                        i2 = i & 524288;
                        if (i2 != 0) {
                            break;
                        }
                        if (this.workQueues != workQueueArr) {
                            j5 = j7;
                        } else {
                            if (j7 == j6) {
                                break;
                            }
                            j5 = j6;
                        }
                    }
                    if (i2 != 0 || (i & 65535) + ((short) (this.ctl >>> 32)) > 0) {
                        break;
                    }
                    ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                }
                return true;
            }
            if (z) {
                i3 = Integer.MIN_VALUE;
            } else {
                while (true) {
                    long j8 = this.ctl;
                    WorkQueue[] workQueueArr2 = this.workQueues;
                    if ((i5 & i6) + ((int) (j8 >> 48)) > 0) {
                        j2 = j4;
                        i3 = Integer.MIN_VALUE;
                        z4 = true;
                        z3 = false;
                    } else if (workQueueArr2 != null) {
                        int i8 = 0;
                        while (i8 < workQueueArr2.length) {
                            WorkQueue workQueue2 = workQueueArr2[i8];
                            if (workQueue2 != null) {
                                int i9 = workQueue2.source;
                                int i10 = workQueue2.phase;
                                z3 = false;
                                int i11 = workQueue2.id;
                                i3 = Integer.MIN_VALUE;
                                int i12 = workQueue2.base;
                                if (i12 != workQueue2.top || ((i11 & 1) == 1 && (i9 >= 0 || i10 >= 0))) {
                                    j2 = j4;
                                    z4 = true;
                                    break;
                                }
                                j3 = j4;
                                j8 += (i9 << 48) + (i10 << 32) + (i12 << 16) + i11;
                            } else {
                                j3 = j4;
                            }
                            i8++;
                            j4 = j3;
                        }
                        j2 = j4;
                        i3 = Integer.MIN_VALUE;
                        z3 = false;
                        z4 = false;
                    } else {
                        j2 = j4;
                        i3 = Integer.MIN_VALUE;
                        z3 = false;
                        z4 = false;
                    }
                    i4 = this.mode;
                    if ((i4 & i3) != 0) {
                        break;
                    }
                    if (z4) {
                        return z3;
                    }
                    if (this.workQueues != workQueueArr2) {
                        j4 = j2;
                    } else {
                        if (j2 == j8) {
                            break;
                        }
                        j4 = j8;
                    }
                    i5 = i4;
                    i6 = 65535;
                }
                i5 = i4;
            }
            if ((i5 & i3) == 0) {
                ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i) {
        this(i, defaultForkJoinWorkerThreadFactory, null, false, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean z) {
        this(i, forkJoinWorkerThreadFactory, uncaughtExceptionHandler, z, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean z, int i2, int i3, int i4, Predicate predicate, long j, TimeUnit timeUnit) {
        if (i <= 0 || i > 32767 || i3 < i || j <= 0) {
            throw new IllegalArgumentException();
        }
        forkJoinWorkerThreadFactory.getClass();
        long max = Math.max(timeUnit.toMillis(j), 20L);
        long j2 = (((-Math.min(Math.max(i2, i), 32767)) << 32) & 281470681743360L) | (((-i) << 48) & (-281474976710656L));
        int i5 = (z ? 65536 : 0) | i;
        int min = ((Math.min(i3, 32767) - i) << 16) | ((Math.min(Math.max(i4, 0), 32767) - i) & 65535);
        int i6 = i > 1 ? i - 1 : 1;
        int i7 = i6 | (i6 >>> 1);
        int i8 = i7 | (i7 >>> 2);
        int i9 = i8 | (i8 >>> 4);
        int i10 = i9 | (i9 >>> 8);
        this.workerNamePrefix = "ForkJoinPool-" + nextPoolId() + "-worker-";
        this.workQueues = new WorkQueue[((i10 | (i10 >>> 16)) + 1) << 1];
        this.factory = forkJoinWorkerThreadFactory;
        this.ueh = uncaughtExceptionHandler;
        this.saturate = predicate;
        this.keepAlive = max;
        this.bounds = min;
        this.mode = i5;
        this.ctl = j2;
        checkPermission();
    }

    private static Object newInstanceFromSystemProperty(String str) throws ReflectiveOperationException {
        String property = System.getProperty(str);
        if (property == null) {
            return null;
        }
        return ClassLoader.getSystemClassLoader().loadClass(property).getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ForkJoinPool(byte r11) {
        /*
            r10 = this;
            r10.<init>()
            r11 = -1
            r0 = 0
            java.lang.String r1 = "java.util.concurrent.ForkJoinPool.common.parallelism"
            java.lang.String r1 = java.lang.System.getProperty(r1)     // Catch: java.lang.Exception -> L24
            if (r1 == 0) goto L11
            int r11 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Exception -> L24
        L11:
            java.lang.String r1 = "java.util.concurrent.ForkJoinPool.common.threadFactory"
            java.lang.Object r1 = newInstanceFromSystemProperty(r1)     // Catch: java.lang.Exception -> L24
            java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory r1 = (java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory) r1     // Catch: java.lang.Exception -> L24
            java.lang.String r2 = "java.util.concurrent.ForkJoinPool.common.exceptionHandler"
            java.lang.Object r2 = newInstanceFromSystemProperty(r2)     // Catch: java.lang.Exception -> L22
            java.lang.Thread$UncaughtExceptionHandler r2 = (java.lang.Thread.UncaughtExceptionHandler) r2     // Catch: java.lang.Exception -> L22
            goto L27
        L22:
            goto L26
        L24:
            r1 = r0
        L26:
            r2 = r0
        L27:
            if (r1 != 0) goto L37
            java.lang.SecurityManager r1 = java.lang.System.getSecurityManager()
            if (r1 != 0) goto L32
            java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory r1 = java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory
            goto L37
        L32:
            java.util.concurrent.ForkJoinPool$InnocuousForkJoinWorkerThreadFactory r1 = new java.util.concurrent.ForkJoinPool$InnocuousForkJoinWorkerThreadFactory
            r1.<init>(r0)
        L37:
            r3 = 1
            if (r11 >= 0) goto L46
            java.lang.Runtime r11 = java.lang.Runtime.getRuntime()
            int r11 = r11.availableProcessors()
            int r11 = r11 - r3
            if (r11 > 0) goto L46
            r11 = 1
        L46:
            r4 = 32767(0x7fff, float:4.5916E-41)
            if (r11 <= r4) goto L4c
            r11 = 32767(0x7fff, float:4.5916E-41)
        L4c:
            int r4 = -r11
            long r4 = (long) r4
            r6 = 32
            long r6 = r4 << r6
            r8 = 281470681743360(0xffff00000000, double:1.39064994160909E-309)
            long r6 = r6 & r8
            r8 = 48
            long r4 = r4 << r8
            r8 = -281474976710656(0xffff000000000000, double:NaN)
            long r4 = r4 & r8
            long r4 = r4 | r6
            int r6 = 1 - r11
            r7 = 65535(0xffff, float:9.1834E-41)
            r6 = r6 & r7
            int r7 = java.util.concurrent.ForkJoinPool.COMMON_MAX_SPARES
            int r7 = r7 << 16
            r6 = r6 | r7
            if (r11 <= r3) goto L6f
            int r7 = r11 + (-1)
            goto L70
        L6f:
            r7 = 1
        L70:
            int r8 = r7 >>> 1
            r7 = r7 | r8
            int r8 = r7 >>> 2
            r7 = r7 | r8
            int r8 = r7 >>> 4
            r7 = r7 | r8
            int r8 = r7 >>> 8
            r7 = r7 | r8
            int r8 = r7 >>> 16
            r7 = r7 | r8
            int r7 = r7 + r3
            int r3 = r7 << 1
            java.lang.String r7 = "ForkJoinPool.commonPool-worker-"
            r10.workerNamePrefix = r7
            java.util.concurrent.ForkJoinPool$WorkQueue[] r3 = new java.util.concurrent.ForkJoinPool.WorkQueue[r3]
            r10.workQueues = r3
            r10.factory = r1
            r10.ueh = r2
            r10.saturate = r0
            r0 = 60000(0xea60, double:2.9644E-319)
            r10.keepAlive = r0
            r10.bounds = r6
            r10.mode = r11
            r10.ctl = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinPool.<init>(byte):void");
    }

    public static ForkJoinPool commonPool() {
        return common;
    }

    public Object invoke(ForkJoinTask forkJoinTask) {
        forkJoinTask.getClass();
        externalSubmit(forkJoinTask);
        return forkJoinTask.join();
    }

    public void execute(ForkJoinTask forkJoinTask) {
        externalSubmit(forkJoinTask);
    }

    public void execute(Runnable runnable) {
        ForkJoinTask runnableExecuteAction;
        runnable.getClass();
        if (runnable instanceof ForkJoinTask) {
            runnableExecuteAction = (ForkJoinTask) runnable;
        } else {
            runnableExecuteAction = new ForkJoinTask.RunnableExecuteAction(runnable);
        }
        externalSubmit(runnableExecuteAction);
    }

    public ForkJoinTask submit(ForkJoinTask forkJoinTask) {
        return externalSubmit(forkJoinTask);
    }

    public ForkJoinTask submit(Callable callable) {
        return externalSubmit(new ForkJoinTask.AdaptedCallable(callable));
    }

    public ForkJoinTask submit(Runnable runnable, Object obj) {
        return externalSubmit(new ForkJoinTask.AdaptedRunnable(runnable, obj));
    }

    public ForkJoinTask submit(Runnable runnable) {
        ForkJoinTask adaptedRunnableAction;
        runnable.getClass();
        if (runnable instanceof ForkJoinTask) {
            adaptedRunnableAction = (ForkJoinTask) runnable;
        } else {
            adaptedRunnableAction = new ForkJoinTask.AdaptedRunnableAction(runnable);
        }
        return externalSubmit(adaptedRunnableAction);
    }

    public List invokeAll(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        try {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                ForkJoinTask.AdaptedCallable adaptedCallable = new ForkJoinTask.AdaptedCallable((Callable) it.next());
                arrayList.add(adaptedCallable);
                externalSubmit(adaptedCallable);
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((ForkJoinTask) arrayList.get(i)).quietlyJoin();
            }
            return arrayList;
        } catch (Throwable th) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Future) arrayList.get(i2)).cancel(false);
            }
            throw th;
        }
    }

    public ForkJoinWorkerThreadFactory getFactory() {
        return this.factory;
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.ueh;
    }

    public int getParallelism() {
        int i = this.mode & 65535;
        if (i > 0) {
            return i;
        }
        return 1;
    }

    public static int getCommonPoolParallelism() {
        return COMMON_PARALLELISM;
    }

    public int getPoolSize() {
        return (this.mode & 65535) + ((short) (this.ctl >>> 32));
    }

    public boolean getAsyncMode() {
        return (this.mode & 65536) != 0;
    }

    public int getRunningThreadCount() {
        VarHandle.acquireFence();
        WorkQueue[] workQueueArr = this.workQueues;
        int i = 0;
        if (workQueueArr != null) {
            for (int i2 = 1; i2 < workQueueArr.length; i2 += 2) {
                WorkQueue workQueue = workQueueArr[i2];
                if (workQueue != null && workQueue.isApparentlyUnblocked()) {
                    i++;
                }
            }
        }
        return i;
    }

    public int getActiveThreadCount() {
        int i = (this.mode & 65535) + ((int) (this.ctl >> 48));
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    public boolean isQuiescent() {
        while (true) {
            long j = this.ctl;
            int i = this.mode;
            int i2 = 65535 & i;
            int i3 = ((short) (j >>> 32)) + i2;
            int i4 = i2 + ((int) (j >> 48));
            if ((i & (-2146959360)) != 0) {
                return true;
            }
            if (i4 > 0) {
                return false;
            }
            WorkQueue[] workQueueArr = this.workQueues;
            if (workQueueArr != null) {
                for (int i5 = 1; i5 < workQueueArr.length; i5 += 2) {
                    WorkQueue workQueue = workQueueArr[i5];
                    if (workQueue != null) {
                        if (workQueue.source > 0) {
                            return false;
                        }
                        i3--;
                    }
                }
            }
            if (i3 == 0 && this.ctl == j) {
                return true;
            }
        }
    }

    public long getStealCount() {
        long j = this.stealCount;
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr != null) {
            for (int i = 1; i < workQueueArr.length; i += 2) {
                if (workQueueArr[i] != null) {
                    j += r4.nsteals & 4294967295L;
                }
            }
        }
        return j;
    }

    public long getQueuedTaskCount() {
        VarHandle.acquireFence();
        WorkQueue[] workQueueArr = this.workQueues;
        int i = 0;
        if (workQueueArr != null) {
            for (int i2 = 1; i2 < workQueueArr.length; i2 += 2) {
                WorkQueue workQueue = workQueueArr[i2];
                if (workQueue != null) {
                    i += workQueue.queueSize();
                }
            }
        }
        return i;
    }

    public int getQueuedSubmissionCount() {
        VarHandle.acquireFence();
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < workQueueArr.length; i2 += 2) {
            WorkQueue workQueue = workQueueArr[i2];
            if (workQueue != null) {
                i += workQueue.queueSize();
            }
        }
        return i;
    }

    public boolean hasQueuedSubmissions() {
        VarHandle.acquireFence();
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr != null) {
            for (int i = 0; i < workQueueArr.length; i += 2) {
                WorkQueue workQueue = workQueueArr[i];
                if (workQueue != null && !workQueue.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected ForkJoinTask pollSubmission() {
        return pollScan(true);
    }

    protected int drainTasksTo(Collection collection) {
        VarHandle.acquireFence();
        WorkQueue[] workQueueArr = this.workQueues;
        if (workQueueArr == null) {
            return 0;
        }
        int i = 0;
        for (WorkQueue workQueue : workQueueArr) {
            if (workQueue != null) {
                while (true) {
                    ForkJoinTask poll = workQueue.poll();
                    if (poll != null) {
                        collection.add(poll);
                        i++;
                    }
                }
            }
        }
        return i;
    }

    public String toString() {
        long j;
        int i;
        String str;
        int i2 = this.mode;
        long j2 = this.ctl;
        long j3 = this.stealCount;
        WorkQueue[] workQueueArr = this.workQueues;
        long j4 = 0;
        if (workQueueArr != null) {
            long j5 = 0;
            i = 0;
            for (int i3 = 0; i3 < workQueueArr.length; i3++) {
                WorkQueue workQueue = workQueueArr[i3];
                if (workQueue != null) {
                    int queueSize = workQueue.queueSize();
                    if ((i3 & 1) == 0) {
                        j5 += queueSize;
                    } else {
                        long j6 = j5;
                        j4 += queueSize;
                        j3 += workQueue.nsteals & 4294967295L;
                        if (workQueue.isApparentlyUnblocked()) {
                            i++;
                        }
                        j5 = j6;
                    }
                }
            }
            j = j5;
        } else {
            j = 0;
            i = 0;
        }
        int i4 = 65535 & i2;
        int i5 = ((short) (j2 >>> 32)) + i4;
        int i6 = ((int) (j2 >> 48)) + i4;
        if (i6 < 0) {
            i6 = 0;
        }
        if ((524288 & i2) != 0) {
            str = "Terminated";
        } else if ((Integer.MIN_VALUE & i2) != 0) {
            str = "Terminating";
        } else if ((i2 & 262144) != 0) {
            str = "Shutting down";
        } else {
            str = "Running";
        }
        return super/*java.lang.Object*/.toString() + "[" + str + ", parallelism = " + i4 + ", size = " + i5 + ", active = " + i6 + ", running = " + i + ", steals = " + j3 + ", tasks = " + j4 + ", submissions = " + j + "]";
    }

    public void shutdown() {
        checkPermission();
        tryTerminate(false, true);
    }

    public List shutdownNow() {
        checkPermission();
        tryTerminate(true, true);
        return Collections.EMPTY_LIST;
    }

    public boolean isTerminated() {
        return (this.mode & 524288) != 0;
    }

    public boolean isTerminating() {
        int i = this.mode;
        return (Integer.MIN_VALUE & i) != 0 && (i & 524288) == 0;
    }

    public boolean isShutdown() {
        return (this.mode & 262144) != 0;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this == common) {
            awaitQuiescence(j, timeUnit);
            return false;
        }
        long nanos = timeUnit.toNanos(j);
        if (isTerminated()) {
            return true;
        }
        if (nanos <= 0) {
            return false;
        }
        long nanoTime = System.nanoTime() + nanos;
        synchronized (this) {
            while (!isTerminated()) {
                if (nanos <= 0) {
                    return false;
                }
                long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
                if (millis <= 0) {
                    millis = 1;
                }
                wait(millis);
                nanos = nanoTime - System.nanoTime();
            }
            return true;
        }
    }

    public boolean awaitQuiescence(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            if (forkJoinWorkerThread.pool == this) {
                helpQuiescePool(forkJoinWorkerThread.workQueue);
                return true;
            }
        }
        long nanoTime = System.nanoTime();
        while (true) {
            ForkJoinTask pollScan = pollScan(false);
            if (pollScan != null) {
                pollScan.doExec();
            } else {
                if (isQuiescent()) {
                    return true;
                }
                if (System.nanoTime() - nanoTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
        }
    }

    static void quiesceCommonPool() {
        common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public static void managedBlock(ManagedBlocker managedBlocker) throws InterruptedException {
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinPool forkJoinPool;
        WorkQueue workQueue;
        managedBlocker.getClass();
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if ((currentThread instanceof ForkJoinWorkerThread) && (forkJoinPool = (forkJoinWorkerThread = currentThread).pool) != null && (workQueue = forkJoinWorkerThread.workQueue) != null) {
            while (!managedBlocker.isReleasable()) {
                if (forkJoinPool.tryCompensate(workQueue) != 0) {
                    while (!managedBlocker.isReleasable() && !managedBlocker.block()) {
                        try {
                        } finally {
                            ForkJoinPool$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        }
                    }
                    return;
                }
            }
            return;
        }
        while (!managedBlocker.isReleasable() && !managedBlocker.block()) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void helpAsyncBlocker(java.util.concurrent.Executor r2, java.util.concurrent.ForkJoinPool.ManagedBlocker r3) {
        /*
            boolean r0 = r2 instanceof java.util.concurrent.ForkJoinPool
            if (r0 == 0) goto L32
            java.util.concurrent.ForkJoinPool r2 = (java.util.concurrent.ForkJoinPool) r2
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r1 = r0 instanceof java.util.concurrent.ForkJoinWorkerThread
            if (r1 == 0) goto L17
            java.util.concurrent.ForkJoinWorkerThread r0 = (java.util.concurrent.ForkJoinWorkerThread) r0
            java.util.concurrent.ForkJoinPool r1 = r0.pool
            if (r1 != r2) goto L17
            java.util.concurrent.ForkJoinPool$WorkQueue r2 = r0.workQueue
            goto L2d
        L17:
            int r0 = java.util.concurrent.ThreadLocalRandom.getProbe()
            if (r0 == 0) goto L2c
            java.util.concurrent.ForkJoinPool$WorkQueue[] r2 = r2.workQueues
            if (r2 == 0) goto L2c
            int r1 = r2.length
            if (r1 <= 0) goto L2c
            int r1 = r1 + (-1)
            r0 = r0 & r1
            r0 = r0 & 126(0x7e, float:1.77E-43)
            r2 = r2[r0]
            goto L2d
        L2c:
            r2 = 0
        L2d:
            if (r2 == 0) goto L32
            r2.helpAsyncBlocker(r3)
        L32:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinPool.helpAsyncBlocker(java.util.concurrent.Executor, java.util.concurrent.ForkJoinPool$ManagedBlocker):void");
    }

    protected RunnableFuture newTaskFor(Runnable runnable, Object obj) {
        return new ForkJoinTask.AdaptedRunnable(runnable, obj);
    }

    protected RunnableFuture newTaskFor(Callable callable) {
        return new ForkJoinTask.AdaptedCallable(callable);
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            CTL = lookup.findVarHandle(ForkJoinPool.class, "ctl", Long.TYPE);
            MODE = lookup.findVarHandle(ForkJoinPool.class, "mode", Integer.TYPE);
            QA = MethodHandles.arrayElementVarHandle(ForkJoinTask[].class);
            int i = 256;
            try {
                String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.maximumSpares");
                if (property != null) {
                    i = Integer.parseInt(property);
                }
            } catch (Exception unused) {
            }
            COMMON_MAX_SPARES = i;
            defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory(null);
            modifyThreadPermission = new RuntimePermission("modifyThread");
            ForkJoinPool forkJoinPool = (ForkJoinPool) AccessController.doPrivileged(new 1());
            common = forkJoinPool;
            COMMON_PARALLELISM = Math.max(forkJoinPool.mode & 65535, 1);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public ForkJoinPool run() {
            return new ForkJoinPool((byte) 0, null);
        }
    }

    private static final class InnocuousForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory {
        private static final AccessControlContext ACC = ForkJoinPool.contextWithPermissions(ForkJoinPool.modifyThreadPermission, new RuntimePermission("enableContextClassLoaderOverride"), new RuntimePermission("modifyThreadGroup"), new RuntimePermission("getClassLoader"), new RuntimePermission("setContextClassLoader"));

        /* synthetic */ InnocuousForkJoinWorkerThreadFactory(ForkJoinPool-IA r1) {
            this();
        }

        private InnocuousForkJoinWorkerThreadFactory() {
        }

        class 1 implements PrivilegedAction {
            final /* synthetic */ ForkJoinPool val$pool;

            1(ForkJoinPool forkJoinPool) {
                this.val$pool = forkJoinPool;
            }

            public ForkJoinWorkerThread run() {
                return new ForkJoinWorkerThread.InnocuousForkJoinWorkerThread(this.val$pool);
            }
        }

        public final ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool) {
            return (ForkJoinWorkerThread) AccessController.doPrivileged(new 1(forkJoinPool), ACC);
        }
    }
}
