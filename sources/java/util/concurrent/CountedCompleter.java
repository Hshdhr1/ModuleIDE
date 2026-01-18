package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class CountedCompleter extends ForkJoinTask {
    private static final VarHandle PENDING;
    private static final long serialVersionUID = 5232453752276485070L;
    final CountedCompleter completer;
    volatile int pending;

    public abstract void compute();

    public Object getRawResult() {
        return null;
    }

    public void onCompletion(CountedCompleter countedCompleter) {
    }

    public boolean onExceptionalCompletion(Throwable th, CountedCompleter countedCompleter) {
        return true;
    }

    protected void setRawResult(Object obj) {
    }

    protected CountedCompleter(CountedCompleter countedCompleter, int i) {
        this.completer = countedCompleter;
        this.pending = i;
    }

    protected CountedCompleter(CountedCompleter countedCompleter) {
        this.completer = countedCompleter;
    }

    protected CountedCompleter() {
        this.completer = null;
    }

    public final CountedCompleter getCompleter() {
        return this.completer;
    }

    public final int getPendingCount() {
        return this.pending;
    }

    public final void setPendingCount(int i) {
        this.pending = i;
    }

    public final void addToPendingCount(int i) {
        CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
    }

    public final boolean compareAndSetPendingCount(int i, int i2) {
        CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    public final int decrementPendingCountUnlessZero() {
        while (true) {
            int i = this.pending;
            if (i == 0) {
                return i;
            }
            CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    public final CountedCompleter getRoot() {
        CountedCompleter countedCompleter = this;
        while (true) {
            CountedCompleter countedCompleter2 = countedCompleter.completer;
            if (countedCompleter2 == null) {
                return countedCompleter;
            }
            countedCompleter = countedCompleter2;
        }
    }

    public final void tryComplete() {
        CountedCompleter countedCompleter = this;
        CountedCompleter countedCompleter2 = countedCompleter;
        while (true) {
            if (countedCompleter.pending == 0) {
                countedCompleter.onCompletion(countedCompleter2);
                CountedCompleter countedCompleter3 = countedCompleter.completer;
                if (countedCompleter3 == null) {
                    countedCompleter.quietlyComplete();
                    return;
                } else {
                    countedCompleter2 = countedCompleter;
                    countedCompleter = countedCompleter3;
                }
            } else {
                CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    public final void propagateCompletion() {
        CountedCompleter countedCompleter = this;
        while (true) {
            if (countedCompleter.pending == 0) {
                CountedCompleter countedCompleter2 = countedCompleter.completer;
                if (countedCompleter2 == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                countedCompleter = countedCompleter2;
            } else {
                CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    public void complete(Object obj) {
        setRawResult(obj);
        onCompletion(this);
        quietlyComplete();
        CountedCompleter countedCompleter = this.completer;
        if (countedCompleter != null) {
            countedCompleter.tryComplete();
        }
    }

    public final CountedCompleter firstComplete() {
        while (this.pending != 0) {
            CountedCompleter$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
        return this;
    }

    public final CountedCompleter nextComplete() {
        CountedCompleter countedCompleter = this.completer;
        if (countedCompleter != null) {
            return countedCompleter.firstComplete();
        }
        quietlyComplete();
        return null;
    }

    public final void quietlyCompleteRoot() {
        CountedCompleter countedCompleter = this;
        while (true) {
            CountedCompleter countedCompleter2 = countedCompleter.completer;
            if (countedCompleter2 == null) {
                countedCompleter.quietlyComplete();
                return;
            }
            countedCompleter = countedCompleter2;
        }
    }

    public final void helpComplete(int i) {
        if (i <= 0 || this.status < 0) {
            return;
        }
        ForkJoinWorkerThread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = currentThread;
            forkJoinWorkerThread.pool.helpComplete(forkJoinWorkerThread.workQueue, this, i);
        } else {
            ForkJoinPool.common.externalHelpComplete(this, i);
        }
    }

    void internalPropagateException(Throwable th) {
        CountedCompleter countedCompleter;
        CountedCompleter countedCompleter2 = this;
        CountedCompleter countedCompleter3 = countedCompleter2;
        while (countedCompleter2.onExceptionalCompletion(th, countedCompleter3) && (countedCompleter = countedCompleter2.completer) != null && countedCompleter.status >= 0 && isExceptionalStatus(countedCompleter.recordExceptionalCompletion(th))) {
            countedCompleter3 = countedCompleter2;
            countedCompleter2 = countedCompleter;
        }
    }

    protected final boolean exec() {
        compute();
        return false;
    }

    static {
        try {
            PENDING = MethodHandles.lookup().findVarHandle(CountedCompleter.class, "pending", Integer.TYPE);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
