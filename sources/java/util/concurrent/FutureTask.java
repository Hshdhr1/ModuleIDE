package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class FutureTask implements RunnableFuture {
    private static final int CANCELLED = 4;
    private static final int COMPLETING = 1;
    private static final int EXCEPTIONAL = 3;
    private static final int INTERRUPTED = 6;
    private static final int INTERRUPTING = 5;
    private static final int NEW = 0;
    private static final int NORMAL = 2;
    private static final VarHandle RUNNER;
    private static final VarHandle STATE;
    private static final VarHandle WAITERS;
    private Callable callable;
    private Object outcome;
    private volatile Thread runner;
    private volatile int state;
    private volatile WaitNode waiters;

    protected void done() {
    }

    private Object report(int i) throws ExecutionException {
        Object obj = this.outcome;
        if (i == 2) {
            return obj;
        }
        if (i >= 4) {
            throw new CancellationException();
        }
        throw new ExecutionException((Throwable) obj);
    }

    public FutureTask(Callable callable) {
        callable.getClass();
        this.callable = callable;
        this.state = 0;
    }

    public FutureTask(Runnable runnable, Object obj) {
        this.callable = Executors.callable(runnable, obj);
        this.state = 0;
    }

    public boolean isCancelled() {
        return this.state >= 4;
    }

    public boolean isDone() {
        return this.state != 0;
    }

    public boolean cancel(boolean z) {
        if (this.state != 0) {
            return false;
        }
        FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    public Object get() throws InterruptedException, ExecutionException {
        int i = this.state;
        if (i <= 1) {
            i = awaitDone(false, 0L);
        }
        return report(i);
    }

    public Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        timeUnit.getClass();
        int i = this.state;
        if (i <= 1 && (i = awaitDone(true, timeUnit.toNanos(j))) <= 1) {
            throw new TimeoutException();
        }
        return report(i);
    }

    protected void set(Object obj) {
        FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
    }

    protected void setException(Throwable th) {
        FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
    }

    public void run() {
        if (this.state == 0) {
            Thread.currentThread();
            FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    protected boolean runAndReset() {
        if (this.state != 0) {
            return false;
        }
        Thread.currentThread();
        FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    private void handlePossibleCancellationInterrupt(int i) {
        if (i == 5) {
            while (this.state == 5) {
                Thread.yield();
            }
        }
    }

    static final class WaitNode {
        volatile WaitNode next;
        volatile Thread thread = Thread.currentThread();

        WaitNode() {
        }
    }

    private void finishCompletion() {
        while (this.waiters != null) {
            FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
        done();
        this.callable = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0022, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int awaitDone(boolean r6, long r7) throws java.lang.InterruptedException {
        /*
            r5 = this;
            r0 = 0
            r1 = r0
        L2:
            int r2 = r5.state
            r3 = 1
            if (r2 <= r3) goto Lc
            if (r1 == 0) goto L22
            r1.thread = r0
            return r2
        Lc:
            if (r2 != r3) goto L12
            java.lang.Thread.yield()
            goto L2
        L12:
            boolean r3 = java.lang.Thread.interrupted()
            if (r3 != 0) goto L33
            if (r1 != 0) goto L29
            if (r6 == 0) goto L23
            r3 = 0
            int r1 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r1 > 0) goto L23
        L22:
            return r2
        L23:
            java.util.concurrent.FutureTask$WaitNode r1 = new java.util.concurrent.FutureTask$WaitNode
            r1.<init>()
            goto L2
        L29:
            java.util.concurrent.FutureTask$WaitNode r2 = r5.waiters
            r1.next = r2
            java.lang.String r2 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.FutureTask$$ExternalSyntheticThrowRTE0.m(r2)
            goto L2
        L33:
            r5.removeWaiter(r1)
            java.lang.InterruptedException r6 = new java.lang.InterruptedException
            r6.<init>()
            goto L3d
        L3c:
            throw r6
        L3d:
            goto L3c
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.FutureTask.awaitDone(boolean, long):int");
    }

    private void removeWaiter(WaitNode waitNode) {
        if (waitNode != null) {
            waitNode.thread = null;
            while (true) {
                WaitNode waitNode2 = this.waiters;
                WaitNode waitNode3 = null;
                while (waitNode2 != null) {
                    WaitNode waitNode4 = waitNode2.next;
                    if (waitNode2.thread != null) {
                        waitNode3 = waitNode2;
                    } else if (waitNode3 != null) {
                        waitNode3.next = waitNode4;
                        if (waitNode3.thread == null) {
                            break;
                        }
                    } else {
                        FutureTask$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    }
                    waitNode2 = waitNode4;
                }
                return;
            }
        }
    }

    public String toString() {
        String str;
        int i = this.state;
        if (i == 2) {
            str = "[Completed normally]";
        } else if (i == 3) {
            str = "[Completed exceptionally: " + this.outcome + "]";
        } else if (i == 4 || i == 5 || i == 6) {
            str = "[Cancelled]";
        } else {
            Callable callable = this.callable;
            if (callable == null) {
                str = "[Not completed]";
            } else {
                str = "[Not completed, task = " + callable + "]";
            }
        }
        return super.toString() + str;
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            STATE = lookup.findVarHandle(FutureTask.class, "state", Integer.TYPE);
            RUNNER = lookup.findVarHandle(FutureTask.class, "runner", Thread.class);
            WAITERS = lookup.findVarHandle(FutureTask.class, "waiters", WaitNode.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
