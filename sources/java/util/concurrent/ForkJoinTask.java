package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class ForkJoinTask implements Future, Serializable {
    private static final int ABNORMAL = 262144;
    private static final int DONE = Integer.MIN_VALUE;
    private static final int SIGNAL = 65536;
    private static final int SMASK = 65535;
    private static final VarHandle STATUS;
    private static final int THROWN = 131072;
    private static final ExceptionNode[] exceptionTable = new ExceptionNode[32];
    private static final ReentrantLock exceptionTableLock = new ReentrantLock();
    private static final ReferenceQueue exceptionTableRefQueue = new ReferenceQueue();
    private static final long serialVersionUID = -7721805057305804111L;
    volatile int status;

    static boolean isExceptionalStatus(int i) {
        return (i & 131072) != 0;
    }

    protected abstract boolean exec();

    public abstract Object getRawResult();

    void internalPropagateException(Throwable th) {
    }

    protected abstract void setRawResult(Object obj);

    private int setDone() {
        ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return Integer.MIN_VALUE;
    }

    private int abnormalCompletion(int i) {
        while (true) {
            int i2 = this.status;
            if (i2 < 0) {
                return i2;
            }
            ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    final int doExec() {
        boolean z;
        int i = this.status;
        if (i < 0) {
            return i;
        }
        try {
            z = exec();
        } catch (Throwable th) {
            i = setExceptionalCompletion(th);
            z = false;
        }
        return z ? setDone() : i;
    }

    final void internalWait(long j) {
        ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        synchronized (this) {
            if (this.status >= 0) {
                try {
                    wait(j);
                } catch (InterruptedException unused) {
                }
            } else {
                notifyAll();
            }
        }
    }

    private int externalAwaitDone() {
        boolean z;
        int i;
        int tryExternalHelp = tryExternalHelp();
        if (tryExternalHelp < 0) {
            return tryExternalHelp;
        }
        ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        synchronized (this) {
            z = false;
            while (true) {
                i = this.status;
                if (i < 0) {
                    break;
                }
                try {
                    wait(0L);
                } catch (InterruptedException unused) {
                    z = true;
                }
            }
            notifyAll();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return i;
    }

    private int externalInterruptibleAwaitDone() throws InterruptedException {
        int i;
        int tryExternalHelp = tryExternalHelp();
        if (tryExternalHelp >= 0) {
            ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            synchronized (this) {
                while (true) {
                    i = this.status;
                    if (i >= 0) {
                        wait(0L);
                    } else {
                        notifyAll();
                    }
                }
            }
            return i;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return tryExternalHelp;
    }

    private int tryExternalHelp() {
        int i = this.status;
        if (i < 0) {
            return i;
        }
        if (this instanceof CountedCompleter) {
            return ForkJoinPool.common.externalHelpComplete((CountedCompleter) this, 0);
        }
        if (ForkJoinPool.common.tryExternalUnpush(this)) {
            return doExec();
        }
        return 0;
    }

    private int doJoin() {
        int doExec;
        int i = this.status;
        if (i < 0) {
            return i;
        }
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            ForkJoinPool.WorkQueue workQueue = forkJoinWorkerThread.workQueue;
            return (!workQueue.tryUnpush(this) || (doExec = doExec()) >= 0) ? forkJoinWorkerThread.pool.awaitJoin(workQueue, this, 0L) : doExec;
        }
        return externalAwaitDone();
    }

    private int doInvoke() {
        int doExec = doExec();
        if (doExec < 0) {
            return doExec;
        }
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            return forkJoinWorkerThread.pool.awaitJoin(forkJoinWorkerThread.workQueue, this, 0L);
        }
        return externalAwaitDone();
    }

    static {
        try {
            STATUS = MethodHandles.lookup().findVarHandle(ForkJoinTask.class, "status", Integer.TYPE);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static final class ExceptionNode extends WeakReference {
        final Throwable ex;
        final int hashCode;
        ExceptionNode next;
        final long thrower;

        ExceptionNode(ForkJoinTask forkJoinTask, Throwable th, ExceptionNode exceptionNode, ReferenceQueue referenceQueue) {
            super(forkJoinTask, referenceQueue);
            this.ex = th;
            this.next = exceptionNode;
            this.thrower = Thread.currentThread().getId();
            this.hashCode = System.identityHashCode(forkJoinTask);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001a, code lost:
    
        r2[r0] = new java.util.concurrent.ForkJoinTask.ExceptionNode(r6, r7, r2[r0], java.util.concurrent.ForkJoinTask.exceptionTableRefQueue);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int recordExceptionalCompletion(java.lang.Throwable r7) {
        /*
            r6 = this;
            int r0 = r6.status
            if (r0 < 0) goto L3e
            int r0 = java.lang.System.identityHashCode(r6)
            java.util.concurrent.locks.ReentrantLock r1 = java.util.concurrent.ForkJoinTask.exceptionTableLock
            r1.lock()
            expungeStaleExceptions()     // Catch: java.lang.Throwable -> L39
            java.util.concurrent.ForkJoinTask$ExceptionNode[] r2 = java.util.concurrent.ForkJoinTask.exceptionTable     // Catch: java.lang.Throwable -> L39
            int r3 = r2.length     // Catch: java.lang.Throwable -> L39
            int r3 = r3 + (-1)
            r0 = r0 & r3
            r3 = r2[r0]     // Catch: java.lang.Throwable -> L39
        L18:
            if (r3 != 0) goto L26
            java.util.concurrent.ForkJoinTask$ExceptionNode r3 = new java.util.concurrent.ForkJoinTask$ExceptionNode     // Catch: java.lang.Throwable -> L39
            r4 = r2[r0]     // Catch: java.lang.Throwable -> L39
            java.lang.ref.ReferenceQueue r5 = java.util.concurrent.ForkJoinTask.exceptionTableRefQueue     // Catch: java.lang.Throwable -> L39
            r3.<init>(r6, r7, r4, r5)     // Catch: java.lang.Throwable -> L39
            r2[r0] = r3     // Catch: java.lang.Throwable -> L39
            goto L2c
        L26:
            java.lang.Object r4 = r3.get()     // Catch: java.lang.Throwable -> L39
            if (r4 != r6) goto L36
        L2c:
            r1.unlock()
            r7 = -2147090432(0xffffffff80060000, float:-5.51013E-40)
            int r7 = r6.abnormalCompletion(r7)
            return r7
        L36:
            java.util.concurrent.ForkJoinTask$ExceptionNode r3 = r3.next     // Catch: java.lang.Throwable -> L39
            goto L18
        L39:
            r7 = move-exception
            r1.unlock()
            throw r7
        L3e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinTask.recordExceptionalCompletion(java.lang.Throwable):int");
    }

    private int setExceptionalCompletion(Throwable th) {
        int recordExceptionalCompletion = recordExceptionalCompletion(th);
        if ((131072 & recordExceptionalCompletion) != 0) {
            internalPropagateException(th);
        }
        return recordExceptionalCompletion;
    }

    static final void cancelIgnoringExceptions(ForkJoinTask forkJoinTask) {
        if (forkJoinTask == null || forkJoinTask.status < 0) {
            return;
        }
        try {
            forkJoinTask.cancel(false);
        } catch (Throwable unused) {
        }
    }

    private void clearExceptionalCompletion() {
        int identityHashCode = System.identityHashCode(this);
        ReentrantLock reentrantLock = exceptionTableLock;
        reentrantLock.lock();
        try {
            ExceptionNode[] exceptionNodeArr = exceptionTable;
            int length = identityHashCode & (exceptionNodeArr.length - 1);
            ExceptionNode exceptionNode = exceptionNodeArr[length];
            ExceptionNode exceptionNode2 = null;
            while (true) {
                if (exceptionNode == null) {
                    break;
                }
                ExceptionNode exceptionNode3 = exceptionNode.next;
                if (exceptionNode.get() != this) {
                    exceptionNode2 = exceptionNode;
                    exceptionNode = exceptionNode3;
                } else if (exceptionNode2 == null) {
                    exceptionNodeArr[length] = exceptionNode3;
                } else {
                    exceptionNode2.next = exceptionNode3;
                }
            }
            expungeStaleExceptions();
            this.status = 0;
        } finally {
            reentrantLock.unlock();
        }
    }

    private Throwable getThrowableException() {
        Throwable th;
        int identityHashCode = System.identityHashCode(this);
        ReentrantLock reentrantLock = exceptionTableLock;
        reentrantLock.lock();
        try {
            expungeStaleExceptions();
            ExceptionNode[] exceptionNodeArr = exceptionTable;
            ExceptionNode exceptionNode = exceptionNodeArr[identityHashCode & (exceptionNodeArr.length - 1)];
            while (exceptionNode != null) {
                if (exceptionNode.get() == this) {
                    break;
                }
                exceptionNode = exceptionNode.next;
            }
            reentrantLock.unlock();
            Constructor constructor = null;
            if (exceptionNode == null || (th = exceptionNode.ex) == null) {
                return null;
            }
            if (exceptionNode.thrower != Thread.currentThread().getId()) {
                try {
                    for (Constructor constructor2 : th.getClass().getConstructors()) {
                        Class[] parameterTypes = constructor2.getParameterTypes();
                        if (parameterTypes.length == 0) {
                            constructor = constructor2;
                        } else if (parameterTypes.length == 1 && parameterTypes[0] == Throwable.class) {
                            return (Throwable) constructor2.newInstance(new Object[]{th});
                        }
                    }
                    if (constructor != null) {
                        Throwable th2 = (Throwable) constructor.newInstance(new Object[0]);
                        th2.initCause(th);
                        return th2;
                    }
                } catch (Exception unused) {
                }
            }
            return th;
        } catch (Throwable th3) {
            reentrantLock.unlock();
            throw th3;
        }
    }

    private static void expungeStaleExceptions() {
        while (true) {
            ExceptionNode poll = exceptionTableRefQueue.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof ExceptionNode) {
                ExceptionNode[] exceptionNodeArr = exceptionTable;
                int length = poll.hashCode & (exceptionNodeArr.length - 1);
                ExceptionNode exceptionNode = exceptionNodeArr[length];
                ExceptionNode exceptionNode2 = null;
                while (true) {
                    if (exceptionNode != null) {
                        ExceptionNode exceptionNode3 = exceptionNode.next;
                        if (exceptionNode != poll) {
                            exceptionNode2 = exceptionNode;
                            exceptionNode = exceptionNode3;
                        } else if (exceptionNode2 == null) {
                            exceptionNodeArr[length] = exceptionNode3;
                        } else {
                            exceptionNode2.next = exceptionNode3;
                        }
                    }
                }
            }
        }
    }

    static final void helpExpungeStaleExceptions() {
        ReentrantLock reentrantLock = exceptionTableLock;
        if (reentrantLock.tryLock()) {
            try {
                expungeStaleExceptions();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    static void rethrow(Throwable th) {
        uncheckedThrow(th);
    }

    static void uncheckedThrow(Throwable th) throws Throwable {
        if (th != null) {
            throw th;
        }
        throw new Error("Unknown Exception");
    }

    private void reportException(int i) {
        Throwable cancellationException;
        if ((i & 131072) != 0) {
            cancellationException = getThrowableException();
        } else {
            cancellationException = new CancellationException();
        }
        rethrow(cancellationException);
    }

    public final ForkJoinTask fork() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            currentThread.workQueue.push(this);
            return this;
        }
        ForkJoinPool.common.externalPush(this);
        return this;
    }

    public final Object join() {
        int doJoin = doJoin();
        if ((262144 & doJoin) != 0) {
            reportException(doJoin);
        }
        return getRawResult();
    }

    public final Object invoke() {
        int doInvoke = doInvoke();
        if ((262144 & doInvoke) != 0) {
            reportException(doInvoke);
        }
        return getRawResult();
    }

    public static void invokeAll(ForkJoinTask forkJoinTask, ForkJoinTask forkJoinTask2) {
        forkJoinTask2.fork();
        int doInvoke = forkJoinTask.doInvoke();
        if ((doInvoke & 262144) != 0) {
            forkJoinTask.reportException(doInvoke);
        }
        int doJoin = forkJoinTask2.doJoin();
        if ((doJoin & 262144) != 0) {
            forkJoinTask2.reportException(doJoin);
        }
    }

    public static void invokeAll(ForkJoinTask... forkJoinTaskArr) {
        int length = forkJoinTaskArr.length - 1;
        NullPointerException nullPointerException = null;
        for (int i = length; i >= 0; i--) {
            ForkJoinTask forkJoinTask = forkJoinTaskArr[i];
            if (forkJoinTask == null) {
                if (nullPointerException == null) {
                    nullPointerException = new NullPointerException();
                }
            } else if (i != 0) {
                forkJoinTask.fork();
            } else if ((262144 & forkJoinTask.doInvoke()) != 0 && nullPointerException == null) {
                nullPointerException = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= length; i2++) {
            ForkJoinTask forkJoinTask2 = forkJoinTaskArr[i2];
            if (forkJoinTask2 != null) {
                if (nullPointerException != null) {
                    forkJoinTask2.cancel(false);
                } else if ((forkJoinTask2.doJoin() & 262144) != 0) {
                    nullPointerException = forkJoinTask2.getException();
                }
            }
        }
        if (nullPointerException != null) {
            rethrow(nullPointerException);
        }
    }

    public static Collection invokeAll(Collection collection) {
        if (!(collection instanceof RandomAccess) || !(collection instanceof List)) {
            invokeAll((ForkJoinTask[]) collection.toArray(new ForkJoinTask[0]));
            return collection;
        }
        List list = (List) collection;
        int size = list.size() - 1;
        NullPointerException nullPointerException = null;
        for (int i = size; i >= 0; i--) {
            ForkJoinTask forkJoinTask = (ForkJoinTask) list.get(i);
            if (forkJoinTask == null) {
                if (nullPointerException == null) {
                    nullPointerException = new NullPointerException();
                }
            } else if (i != 0) {
                forkJoinTask.fork();
            } else if ((262144 & forkJoinTask.doInvoke()) != 0 && nullPointerException == null) {
                nullPointerException = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= size; i2++) {
            ForkJoinTask forkJoinTask2 = (ForkJoinTask) list.get(i2);
            if (forkJoinTask2 != null) {
                if (nullPointerException != null) {
                    forkJoinTask2.cancel(false);
                } else if ((forkJoinTask2.doJoin() & 262144) != 0) {
                    nullPointerException = forkJoinTask2.getException();
                }
            }
        }
        if (nullPointerException != null) {
            rethrow(nullPointerException);
        }
        return collection;
    }

    public boolean cancel(boolean z) {
        return (abnormalCompletion(-2147221504) & 393216) == 262144;
    }

    public final boolean isDone() {
        return this.status < 0;
    }

    public final boolean isCancelled() {
        return (this.status & 393216) == 262144;
    }

    public final boolean isCompletedAbnormally() {
        return (this.status & 262144) != 0;
    }

    public final boolean isCompletedNormally() {
        return (this.status & (-2147221504)) == Integer.MIN_VALUE;
    }

    public final Throwable getException() {
        int i = this.status;
        if ((262144 & i) == 0) {
            return null;
        }
        if ((i & 131072) == 0) {
            return new CancellationException();
        }
        return getThrowableException();
    }

    public void completeExceptionally(Throwable th) {
        if (!(th instanceof RuntimeException) && !(th instanceof Error)) {
            th = new RuntimeException(th);
        }
        setExceptionalCompletion(th);
    }

    public void complete(Object obj) {
        try {
            setRawResult(obj);
            setDone();
        } catch (Throwable th) {
            setExceptionalCompletion(th);
        }
    }

    public final void quietlyComplete() {
        setDone();
    }

    public final Object get() throws InterruptedException, ExecutionException {
        int doJoin = Thread.currentThread() instanceof ForkJoinWorkerThread ? doJoin() : externalInterruptibleAwaitDone();
        if ((131072 & doJoin) != 0) {
            throw new ExecutionException(getThrowableException());
        }
        if ((doJoin & 262144) != 0) {
            throw new CancellationException();
        }
        return getRawResult();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0052 A[LOOP:0: B:18:0x0052->B:34:0x0052, LOOP_START] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(long r6, java.util.concurrent.TimeUnit r8) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
            r5 = this;
            long r6 = r8.toNanos(r6)
            boolean r8 = java.lang.Thread.interrupted()
            if (r8 != 0) goto La7
            int r8 = r5.status
            if (r8 < 0) goto L80
            r0 = 0
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 <= 0) goto L80
            long r2 = java.lang.System.nanoTime()
            long r2 = r2 + r6
            int r6 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r6 != 0) goto L1f
            r2 = 1
        L1f:
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            boolean r7 = r6 instanceof java.util.concurrent.ForkJoinWorkerThread
            if (r7 == 0) goto L32
            java.util.concurrent.ForkJoinWorkerThread r6 = (java.util.concurrent.ForkJoinWorkerThread) r6
            java.util.concurrent.ForkJoinPool r7 = r6.pool
            java.util.concurrent.ForkJoinPool$WorkQueue r6 = r6.workQueue
            int r8 = r7.awaitJoin(r6, r5, r2)
            goto L80
        L32:
            boolean r6 = r5 instanceof java.util.concurrent.CountedCompleter
            r7 = 0
            if (r6 == 0) goto L42
            java.util.concurrent.ForkJoinPool r6 = java.util.concurrent.ForkJoinPool.common
            r8 = r5
            java.util.concurrent.CountedCompleter r8 = (java.util.concurrent.CountedCompleter) r8
            int r6 = r6.externalHelpComplete(r8, r7)
        L40:
            r8 = r6
            goto L50
        L42:
            java.util.concurrent.ForkJoinPool r6 = java.util.concurrent.ForkJoinPool.common
            boolean r6 = r6.tryExternalUnpush(r5)
            if (r6 == 0) goto L4f
            int r6 = r5.doExec()
            goto L40
        L4f:
            r8 = 0
        L50:
            if (r8 < 0) goto L80
        L52:
            int r8 = r5.status
            if (r8 < 0) goto L80
            long r6 = java.lang.System.nanoTime()
            long r6 = r2 - r6
            int r4 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r4 <= 0) goto L80
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r6 = r8.toMillis(r6)
            int r8 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r8 <= 0) goto L52
            java.lang.String r8 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.ForkJoinTask$$ExternalSyntheticThrowRTE0.m(r8)
            monitor-enter(r5)
            int r8 = r5.status     // Catch: java.lang.Throwable -> L7d
            if (r8 < 0) goto L78
            r5.wait(r6)     // Catch: java.lang.Throwable -> L7d
            goto L7b
        L78:
            r5.notifyAll()     // Catch: java.lang.Throwable -> L7d
        L7b:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L7d
            goto L52
        L7d:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L7d
            throw r6
        L80:
            if (r8 >= 0) goto La1
            r6 = 131072(0x20000, float:1.83671E-40)
            r6 = r6 & r8
            if (r6 != 0) goto L97
            r6 = 262144(0x40000, float:3.67342E-40)
            r6 = r6 & r8
            if (r6 != 0) goto L91
            java.lang.Object r6 = r5.getRawResult()
            return r6
        L91:
            java.util.concurrent.CancellationException r6 = new java.util.concurrent.CancellationException
            r6.<init>()
            throw r6
        L97:
            java.util.concurrent.ExecutionException r6 = new java.util.concurrent.ExecutionException
            java.lang.Throwable r7 = r5.getThrowableException()
            r6.<init>(r7)
            throw r6
        La1:
            java.util.concurrent.TimeoutException r6 = new java.util.concurrent.TimeoutException
            r6.<init>()
            throw r6
        La7:
            java.lang.InterruptedException r6 = new java.lang.InterruptedException
            r6.<init>()
            goto Lae
        Lad:
            throw r6
        Lae:
            goto Lad
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ForkJoinTask.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    public final void quietlyJoin() {
        doJoin();
    }

    public final void quietlyInvoke() {
        doInvoke();
    }

    public static void helpQuiesce() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            forkJoinWorkerThread.pool.helpQuiescePool(forkJoinWorkerThread.workQueue);
        } else {
            ForkJoinPool.quiesceCommonPool();
        }
    }

    public void reinitialize() {
        if ((this.status & 131072) != 0) {
            clearExceptionalCompletion();
        } else {
            this.status = 0;
        }
    }

    public static ForkJoinPool getPool() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return currentThread.pool;
        }
        return null;
    }

    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }

    public boolean tryUnfork() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return currentThread.workQueue.tryUnpush(this);
        }
        return ForkJoinPool.common.tryExternalUnpush(this);
    }

    public static int getQueuedTaskCount() {
        ForkJoinPool.WorkQueue commonSubmitterQueue;
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            commonSubmitterQueue = currentThread.workQueue;
        } else {
            commonSubmitterQueue = ForkJoinPool.commonSubmitterQueue();
        }
        if (commonSubmitterQueue == null) {
            return 0;
        }
        return commonSubmitterQueue.queueSize();
    }

    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.getSurplusQueuedTaskCount();
    }

    protected static ForkJoinTask peekNextLocalTask() {
        ForkJoinPool.WorkQueue commonSubmitterQueue;
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            commonSubmitterQueue = currentThread.workQueue;
        } else {
            commonSubmitterQueue = ForkJoinPool.commonSubmitterQueue();
        }
        if (commonSubmitterQueue == null) {
            return null;
        }
        return commonSubmitterQueue.peek();
    }

    protected static ForkJoinTask pollNextLocalTask() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return currentThread.workQueue.nextLocalTask();
        }
        return null;
    }

    protected static ForkJoinTask pollTask() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return null;
        }
        ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
        return forkJoinWorkerThread.pool.nextTaskFor(forkJoinWorkerThread.workQueue);
    }

    protected static ForkJoinTask pollSubmission() {
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return currentThread.pool.pollSubmission();
        }
        return null;
    }

    public final short getForkJoinTaskTag() {
        return (short) this.status;
    }

    public final short setForkJoinTaskTag(short s) {
        while (true) {
            ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    public final boolean compareAndSetForkJoinTaskTag(short s, short s2) {
        while (((short) this.status) == s) {
            ForkJoinTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
        return false;
    }

    static final class AdaptedRunnable extends ForkJoinTask implements RunnableFuture {
        private static final long serialVersionUID = 5232453952276885070L;
        Object result;
        final Runnable runnable;

        AdaptedRunnable(Runnable runnable, Object obj) {
            runnable.getClass();
            this.runnable = runnable;
            this.result = obj;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void setRawResult(Object obj) {
            this.result = obj;
        }

        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.runnable + "]";
        }
    }

    static final class AdaptedRunnableAction extends ForkJoinTask implements RunnableFuture {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        public final Void getRawResult() {
            return null;
        }

        public final void setRawResult(Void r1) {
        }

        AdaptedRunnableAction(Runnable runnable) {
            runnable.getClass();
            this.runnable = runnable;
        }

        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.runnable + "]";
        }
    }

    static final class RunnableExecuteAction extends ForkJoinTask {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        public final Void getRawResult() {
            return null;
        }

        public final void setRawResult(Void r1) {
        }

        RunnableExecuteAction(Runnable runnable) {
            runnable.getClass();
            this.runnable = runnable;
        }

        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        void internalPropagateException(Throwable th) {
            rethrow(th);
        }
    }

    static final class AdaptedCallable extends ForkJoinTask implements RunnableFuture {
        private static final long serialVersionUID = 2838392045355241008L;
        final Callable callable;
        Object result;

        AdaptedCallable(Callable callable) {
            callable.getClass();
            this.callable = callable;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void setRawResult(Object obj) {
            this.result = obj;
        }

        public final boolean exec() {
            try {
                this.result = this.callable.call();
                return true;
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.callable + "]";
        }
    }

    public static ForkJoinTask adapt(Runnable runnable) {
        return new AdaptedRunnableAction(runnable);
    }

    public static ForkJoinTask adapt(Runnable runnable, Object obj) {
        return new AdaptedRunnable(runnable, obj);
    }

    public static ForkJoinTask adapt(Callable callable) {
        return new AdaptedCallable(callable);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getException());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Object readObject = objectInputStream.readObject();
        if (readObject != null) {
            setExceptionalCompletion((Throwable) readObject);
        }
    }
}
