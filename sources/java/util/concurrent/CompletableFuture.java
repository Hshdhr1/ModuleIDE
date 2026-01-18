package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class CompletableFuture implements Future, CompletionStage {
    static final int ASYNC = 1;
    private static final Executor ASYNC_POOL;
    static final int NESTED = -1;
    private static final VarHandle NEXT;
    static final AltResult NIL = new AltResult(null);
    private static final VarHandle RESULT;
    private static final VarHandle STACK;
    static final int SYNC = 0;
    private static final boolean USE_COMMON_POOL;
    volatile Object result;
    volatile Completion stack;

    public interface AsynchronousCompletionTask {
    }

    public CompletableFuture toCompletableFuture() {
        return this;
    }

    final boolean internalComplete(Object obj) {
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    final boolean tryPushStack(Completion completion) {
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    final void pushStack(Completion completion) {
        while (!tryPushStack(completion)) {
        }
    }

    static final class AltResult {
        final Throwable ex;

        AltResult(Throwable th) {
            this.ex = th;
        }
    }

    static {
        boolean z = ForkJoinPool.getCommonPoolParallelism() > 1;
        USE_COMMON_POOL = z;
        ASYNC_POOL = z ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            RESULT = lookup.findVarHandle(CompletableFuture.class, "result", Object.class);
            STACK = lookup.findVarHandle(CompletableFuture.class, "stack", Completion.class);
            NEXT = lookup.findVarHandle(Completion.class, "next", Completion.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    final boolean completeNull() {
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    final Object encodeValue(Object obj) {
        return obj == null ? NIL : obj;
    }

    final boolean completeValue(Object obj) {
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    static AltResult encodeThrowable(Throwable th) {
        if (!(th instanceof CompletionException)) {
            th = new CompletionException(th);
        }
        return new AltResult(th);
    }

    final boolean completeThrowable(Throwable th) {
        encodeThrowable(th);
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    static Object encodeThrowable(Throwable th, Object obj) {
        if (!(th instanceof CompletionException)) {
            th = new CompletionException(th);
        } else if ((obj instanceof AltResult) && th == ((AltResult) obj).ex) {
            return obj;
        }
        return new AltResult(th);
    }

    final boolean completeThrowable(Throwable th, Object obj) {
        encodeThrowable(th, obj);
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    Object encodeOutcome(Object obj, Throwable th) {
        return th == null ? obj == null ? NIL : obj : encodeThrowable(th);
    }

    static Object encodeRelay(Object obj) {
        Throwable th;
        return (!(obj instanceof AltResult) || (th = ((AltResult) obj).ex) == null || (th instanceof CompletionException)) ? obj : new AltResult(new CompletionException(th));
    }

    final boolean completeRelay(Object obj) {
        encodeRelay(obj);
        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    private static Object reportGet(Object obj) throws InterruptedException, ExecutionException {
        Throwable cause;
        if (obj == null) {
            throw new InterruptedException();
        }
        if (!(obj instanceof AltResult)) {
            return obj;
        }
        Throwable th = ((AltResult) obj).ex;
        if (th == null) {
            return null;
        }
        if (th instanceof CancellationException) {
            throw ((CancellationException) th);
        }
        if ((th instanceof CompletionException) && (cause = th.getCause()) != null) {
            th = cause;
        }
        throw new ExecutionException(th);
    }

    private static Object reportJoin(Object obj) {
        if (!(obj instanceof AltResult)) {
            return obj;
        }
        CompletionException completionException = ((AltResult) obj).ex;
        if (completionException == null) {
            return null;
        }
        if (completionException instanceof CancellationException) {
            throw ((CancellationException) completionException);
        }
        if (completionException instanceof CompletionException) {
            throw completionException;
        }
        throw new CompletionException(completionException);
    }

    static final class ThreadPerTaskExecutor implements Executor {
        ThreadPerTaskExecutor() {
        }

        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    static Executor screenExecutor(Executor executor) {
        if (!USE_COMMON_POOL && executor == ForkJoinPool.commonPool()) {
            return ASYNC_POOL;
        }
        executor.getClass();
        return executor;
    }

    static abstract class Completion extends ForkJoinTask implements Runnable, AsynchronousCompletionTask {
        volatile Completion next;

        public final Void getRawResult() {
            return null;
        }

        abstract boolean isLive();

        public final void setRawResult(Void r1) {
        }

        abstract CompletableFuture tryFire(int i);

        Completion() {
        }

        public final void run() {
            tryFire(1);
        }

        public final boolean exec() {
            tryFire(1);
            return false;
        }
    }

    final void postComplete() {
        CompletableFuture completableFuture = this;
        while (true) {
            Completion completion = completableFuture.stack;
            if (completion == null) {
                if (completableFuture == this || (completion = this.stack) == null) {
                    return;
                } else {
                    completableFuture = this;
                }
            }
            Completion completion2 = completion.next;
            CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    final void cleanStack() {
        Completion completion = this.stack;
        while (completion != null) {
            if (!completion.isLive()) {
                Completion completion2 = completion.next;
                CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                completion = this.stack;
            } else {
                Completion completion3 = completion.next;
                while (completion3 != null) {
                    Completion completion4 = completion3.next;
                    if (completion3.isLive()) {
                        completion = completion3;
                        completion3 = completion4;
                    } else {
                        CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                        completion3 = completion.next;
                    }
                }
                return;
            }
        }
    }

    static abstract class UniCompletion extends Completion {
        CompletableFuture dep;
        Executor executor;
        CompletableFuture src;

        UniCompletion(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2) {
            this.executor = executor;
            this.dep = completableFuture;
            this.src = completableFuture2;
        }

        final boolean claim() {
            Executor executor = this.executor;
            if (compareAndSetForkJoinTaskTag((short) 0, (short) 1)) {
                if (executor == null) {
                    return true;
                }
                this.executor = null;
                executor.execute(this);
            }
            return false;
        }

        final boolean isLive() {
            return this.dep != null;
        }
    }

    final void unipush(Completion completion) {
        if (completion != null) {
            while (true) {
                if (tryPushStack(completion)) {
                    break;
                } else if (this.result != null) {
                    CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    break;
                }
            }
            if (this.result != null) {
                completion.tryFire(0);
            }
        }
    }

    final CompletableFuture postFire(CompletableFuture completableFuture, int i) {
        if (completableFuture != null && completableFuture.stack != null) {
            Object obj = completableFuture.result;
            if (obj == null) {
                completableFuture.cleanStack();
            }
            if (i >= 0 && (obj != null || completableFuture.result != null)) {
                completableFuture.postComplete();
            }
        }
        if (this.result == null || this.stack == null) {
            return null;
        }
        if (i < 0) {
            return this;
        }
        postComplete();
        return null;
    }

    static final class UniApply extends UniCompletion {
        Function fn;

        UniApply(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, Function function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r7) {
            /*
                r6 = this;
                java.util.concurrent.CompletableFuture r0 = r6.dep
                r1 = 0
                if (r0 == 0) goto L46
                java.util.function.Function r2 = r6.fn
                if (r2 == 0) goto L46
                java.util.concurrent.CompletableFuture r3 = r6.src
                if (r3 == 0) goto L46
                java.lang.Object r4 = r3.result
                if (r4 != 0) goto L12
                goto L46
            L12:
                java.lang.Object r5 = r0.result
                if (r5 != 0) goto L3b
                boolean r5 = r4 instanceof java.util.concurrent.CompletableFuture.AltResult
                if (r5 == 0) goto L26
                r5 = r4
                java.util.concurrent.CompletableFuture$AltResult r5 = (java.util.concurrent.CompletableFuture.AltResult) r5
                java.lang.Throwable r5 = r5.ex
                if (r5 == 0) goto L25
                r0.completeThrowable(r5, r4)
                goto L3b
            L25:
                r4 = r1
            L26:
                if (r7 > 0) goto L2f
                boolean r5 = r6.claim()     // Catch: java.lang.Throwable -> L37
                if (r5 != 0) goto L2f
                return r1
            L2f:
                java.lang.Object r2 = r2.apply(r4)     // Catch: java.lang.Throwable -> L37
                r0.completeValue(r2)     // Catch: java.lang.Throwable -> L37
                goto L3b
            L37:
                r2 = move-exception
                r0.completeThrowable(r2)
            L3b:
                r6.dep = r1
                r6.src = r1
                r6.fn = r1
                java.util.concurrent.CompletableFuture r7 = r0.postFire(r3, r7)
                return r7
            L46:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.UniApply.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture uniApplyStage(Executor executor, Function function) {
        function.getClass();
        Object obj = this.result;
        if (obj != null) {
            return uniApplyNow(obj, executor, function);
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        unipush(new UniApply(executor, newIncompleteFuture, this, function));
        return newIncompleteFuture;
    }

    private CompletableFuture uniApplyNow(Object obj, Executor executor, Function function) {
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        if (obj instanceof AltResult) {
            Throwable th = ((AltResult) obj).ex;
            if (th != null) {
                newIncompleteFuture.result = encodeThrowable(th, obj);
                return newIncompleteFuture;
            }
            obj = null;
        }
        try {
            if (executor != null) {
                executor.execute(new UniApply(null, newIncompleteFuture, this, function));
                return newIncompleteFuture;
            }
            newIncompleteFuture.result = newIncompleteFuture.encodeValue(function.apply(obj));
            return newIncompleteFuture;
        } catch (Throwable th2) {
            newIncompleteFuture.result = encodeThrowable(th2);
            return newIncompleteFuture;
        }
    }

    static final class UniAccept extends UniCompletion {
        Consumer fn;

        UniAccept(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, Consumer consumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = consumer;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r7) {
            /*
                r6 = this;
                java.util.concurrent.CompletableFuture r0 = r6.dep
                r1 = 0
                if (r0 == 0) goto L45
                java.util.function.Consumer r2 = r6.fn
                if (r2 == 0) goto L45
                java.util.concurrent.CompletableFuture r3 = r6.src
                if (r3 == 0) goto L45
                java.lang.Object r4 = r3.result
                if (r4 != 0) goto L12
                goto L45
            L12:
                java.lang.Object r5 = r0.result
                if (r5 != 0) goto L3a
                boolean r5 = r4 instanceof java.util.concurrent.CompletableFuture.AltResult
                if (r5 == 0) goto L26
                r5 = r4
                java.util.concurrent.CompletableFuture$AltResult r5 = (java.util.concurrent.CompletableFuture.AltResult) r5
                java.lang.Throwable r5 = r5.ex
                if (r5 == 0) goto L25
                r0.completeThrowable(r5, r4)
                goto L3a
            L25:
                r4 = r1
            L26:
                if (r7 > 0) goto L2f
                boolean r5 = r6.claim()     // Catch: java.lang.Throwable -> L36
                if (r5 != 0) goto L2f
                return r1
            L2f:
                r2.accept(r4)     // Catch: java.lang.Throwable -> L36
                r0.completeNull()     // Catch: java.lang.Throwable -> L36
                goto L3a
            L36:
                r2 = move-exception
                r0.completeThrowable(r2)
            L3a:
                r6.dep = r1
                r6.src = r1
                r6.fn = r1
                java.util.concurrent.CompletableFuture r7 = r0.postFire(r3, r7)
                return r7
            L45:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.UniAccept.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture uniAcceptStage(Executor executor, Consumer consumer) {
        consumer.getClass();
        Object obj = this.result;
        if (obj != null) {
            return uniAcceptNow(obj, executor, consumer);
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        unipush(new UniAccept(executor, newIncompleteFuture, this, consumer));
        return newIncompleteFuture;
    }

    private CompletableFuture uniAcceptNow(Object obj, Executor executor, Consumer consumer) {
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        if (obj instanceof AltResult) {
            Throwable th = ((AltResult) obj).ex;
            if (th != null) {
                newIncompleteFuture.result = encodeThrowable(th, obj);
                return newIncompleteFuture;
            }
            obj = null;
        }
        try {
            if (executor != null) {
                executor.execute(new UniAccept(null, newIncompleteFuture, this, consumer));
                return newIncompleteFuture;
            }
            consumer.accept(obj);
            newIncompleteFuture.result = NIL;
            return newIncompleteFuture;
        } catch (Throwable th2) {
            newIncompleteFuture.result = encodeThrowable(th2);
            return newIncompleteFuture;
        }
    }

    static final class UniRun extends UniCompletion {
        Runnable fn;

        UniRun(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, Runnable runnable) {
            super(executor, completableFuture, completableFuture2);
            this.fn = runnable;
        }

        final CompletableFuture tryFire(int i) {
            Runnable runnable;
            CompletableFuture completableFuture;
            Object obj;
            Throwable th;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 == null || (runnable = this.fn) == null || (completableFuture = this.src) == null || (obj = completableFuture.result) == null) {
                return null;
            }
            if (completableFuture2.result == null) {
                if ((obj instanceof AltResult) && (th = ((AltResult) obj).ex) != null) {
                    completableFuture2.completeThrowable(th, obj);
                } else {
                    if (i <= 0) {
                        try {
                            if (!claim()) {
                                return null;
                            }
                        } catch (Throwable th2) {
                            completableFuture2.completeThrowable(th2);
                        }
                    }
                    runnable.run();
                    completableFuture2.completeNull();
                }
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return completableFuture2.postFire(completableFuture, i);
        }
    }

    private CompletableFuture uniRunStage(Executor executor, Runnable runnable) {
        runnable.getClass();
        Object obj = this.result;
        if (obj != null) {
            return uniRunNow(obj, executor, runnable);
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        unipush(new UniRun(executor, newIncompleteFuture, this, runnable));
        return newIncompleteFuture;
    }

    private CompletableFuture uniRunNow(Object obj, Executor executor, Runnable runnable) {
        Throwable th;
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        if ((obj instanceof AltResult) && (th = ((AltResult) obj).ex) != null) {
            newIncompleteFuture.result = encodeThrowable(th, obj);
            return newIncompleteFuture;
        }
        try {
            if (executor != null) {
                executor.execute(new UniRun(null, newIncompleteFuture, this, runnable));
                return newIncompleteFuture;
            }
            runnable.run();
            newIncompleteFuture.result = NIL;
            return newIncompleteFuture;
        } catch (Throwable th2) {
            newIncompleteFuture.result = encodeThrowable(th2);
            return newIncompleteFuture;
        }
    }

    static final class UniWhenComplete extends UniCompletion {
        BiConsumer fn;

        UniWhenComplete(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, BiConsumer biConsumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biConsumer;
        }

        final CompletableFuture tryFire(int i) {
            BiConsumer biConsumer;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && (biConsumer = this.fn) != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null) {
                if (completableFuture2.uniWhenComplete(obj, biConsumer, i > 0 ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.fn = null;
                    return completableFuture2.postFire(completableFuture, i);
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final boolean uniWhenComplete(java.lang.Object r3, java.util.function.BiConsumer r4, java.util.concurrent.CompletableFuture.UniWhenComplete r5) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.result
            r1 = 1
            if (r0 != 0) goto L36
            r0 = 0
            if (r5 == 0) goto L10
            boolean r5 = r5.claim()     // Catch: java.lang.Throwable -> L28
            if (r5 != 0) goto L10
            r3 = 0
            return r3
        L10:
            boolean r5 = r3 instanceof java.util.concurrent.CompletableFuture.AltResult     // Catch: java.lang.Throwable -> L28
            if (r5 == 0) goto L1a
            r5 = r3
            java.util.concurrent.CompletableFuture$AltResult r5 = (java.util.concurrent.CompletableFuture.AltResult) r5     // Catch: java.lang.Throwable -> L28
            java.lang.Throwable r5 = r5.ex     // Catch: java.lang.Throwable -> L28
            goto L1c
        L1a:
            r5 = r0
            r0 = r3
        L1c:
            r4.accept(r0, r5)     // Catch: java.lang.Throwable -> L25
            if (r5 != 0) goto L33
            r2.internalComplete(r3)     // Catch: java.lang.Throwable -> L25
            return r1
        L25:
            r4 = move-exception
            r0 = r5
            goto L29
        L28:
            r4 = move-exception
        L29:
            if (r0 != 0) goto L2d
            r5 = r4
            goto L33
        L2d:
            if (r0 == r4) goto L32
            java.util.concurrent.CompletableFuture$$ExternalSyntheticBackport0.m(r0, r4)
        L32:
            r5 = r0
        L33:
            r2.completeThrowable(r5, r3)
        L36:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.uniWhenComplete(java.lang.Object, java.util.function.BiConsumer, java.util.concurrent.CompletableFuture$UniWhenComplete):boolean");
    }

    private CompletableFuture uniWhenCompleteStage(Executor executor, BiConsumer biConsumer) {
        biConsumer.getClass();
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj = this.result;
        if (obj == null) {
            unipush(new UniWhenComplete(executor, newIncompleteFuture, this, biConsumer));
            return newIncompleteFuture;
        }
        if (executor == null) {
            newIncompleteFuture.uniWhenComplete(obj, biConsumer, null);
            return newIncompleteFuture;
        }
        try {
            executor.execute(new UniWhenComplete(null, newIncompleteFuture, this, biConsumer));
            return newIncompleteFuture;
        } catch (Throwable th) {
            newIncompleteFuture.result = encodeThrowable(th);
            return newIncompleteFuture;
        }
    }

    static final class UniHandle extends UniCompletion {
        BiFunction fn;

        UniHandle(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, BiFunction biFunction) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biFunction;
        }

        final CompletableFuture tryFire(int i) {
            BiFunction biFunction;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && (biFunction = this.fn) != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null) {
                if (completableFuture2.uniHandle(obj, biFunction, i > 0 ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.fn = null;
                    return completableFuture2.postFire(completableFuture, i);
                }
            }
            return null;
        }
    }

    final boolean uniHandle(Object obj, BiFunction biFunction, UniHandle uniHandle) {
        if (this.result != null) {
            return true;
        }
        if (uniHandle != null) {
            try {
                if (!uniHandle.claim()) {
                    return false;
                }
            } catch (Throwable th) {
                completeThrowable(th);
                return true;
            }
        }
        Throwable th2 = null;
        if (obj instanceof AltResult) {
            th2 = ((AltResult) obj).ex;
            obj = null;
        }
        completeValue(biFunction.apply(obj, th2));
        return true;
    }

    private CompletableFuture uniHandleStage(Executor executor, BiFunction biFunction) {
        biFunction.getClass();
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj = this.result;
        if (obj == null) {
            unipush(new UniHandle(executor, newIncompleteFuture, this, biFunction));
            return newIncompleteFuture;
        }
        if (executor == null) {
            newIncompleteFuture.uniHandle(obj, biFunction, null);
            return newIncompleteFuture;
        }
        try {
            executor.execute(new UniHandle(null, newIncompleteFuture, this, biFunction));
            return newIncompleteFuture;
        } catch (Throwable th) {
            newIncompleteFuture.result = encodeThrowable(th);
            return newIncompleteFuture;
        }
    }

    static final class UniExceptionally extends UniCompletion {
        Function fn;

        UniExceptionally(CompletableFuture completableFuture, CompletableFuture completableFuture2, Function function) {
            super(null, completableFuture, completableFuture2);
            this.fn = function;
        }

        final CompletableFuture tryFire(int i) {
            Function function;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 == null || (function = this.fn) == null || (completableFuture = this.src) == null || (obj = completableFuture.result) == null || !completableFuture2.uniExceptionally(obj, function, this)) {
                return null;
            }
            this.dep = null;
            this.src = null;
            this.fn = null;
            return completableFuture2.postFire(completableFuture, i);
        }
    }

    final boolean uniExceptionally(Object obj, Function function, UniExceptionally uniExceptionally) {
        Throwable th;
        if (this.result != null) {
            return true;
        }
        try {
            if ((obj instanceof AltResult) && (th = ((AltResult) obj).ex) != null) {
                if (uniExceptionally != null && !uniExceptionally.claim()) {
                    return false;
                }
                completeValue(function.apply(th));
                return true;
            }
            internalComplete(obj);
            return true;
        } catch (Throwable th2) {
            completeThrowable(th2);
            return true;
        }
    }

    private CompletableFuture uniExceptionallyStage(Function function) {
        function.getClass();
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj = this.result;
        if (obj == null) {
            unipush(new UniExceptionally(newIncompleteFuture, this, function));
            return newIncompleteFuture;
        }
        newIncompleteFuture.uniExceptionally(obj, function, null);
        return newIncompleteFuture;
    }

    static final class UniRelay extends UniCompletion {
        UniRelay(CompletableFuture completableFuture, CompletableFuture completableFuture2) {
            super(null, completableFuture, completableFuture2);
        }

        final CompletableFuture tryFire(int i) {
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 == null || (completableFuture = this.src) == null || (obj = completableFuture.result) == null) {
                return null;
            }
            if (completableFuture2.result == null) {
                completableFuture2.completeRelay(obj);
            }
            this.src = null;
            this.dep = null;
            return completableFuture2.postFire(completableFuture, i);
        }
    }

    private static CompletableFuture uniCopyStage(CompletableFuture completableFuture) {
        CompletableFuture newIncompleteFuture = completableFuture.newIncompleteFuture();
        Object obj = completableFuture.result;
        if (obj != null) {
            newIncompleteFuture.result = encodeRelay(obj);
            return newIncompleteFuture;
        }
        completableFuture.unipush(new UniRelay(newIncompleteFuture, completableFuture));
        return newIncompleteFuture;
    }

    private MinimalStage uniAsMinimalStage() {
        Object obj = this.result;
        if (obj != null) {
            return new MinimalStage(encodeRelay(obj));
        }
        MinimalStage minimalStage = new MinimalStage();
        unipush(new UniRelay(minimalStage, this));
        return minimalStage;
    }

    static final class UniCompose extends UniCompletion {
        Function fn;

        UniCompose(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, Function function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x002f A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:26:0x0028, B:18:0x002f, B:20:0x003d, B:21:0x0041), top: B:25:0x0028 }] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x003d A[Catch: all -> 0x004e, TryCatch #0 {all -> 0x004e, blocks: (B:26:0x0028, B:18:0x002f, B:20:0x003d, B:21:0x0041), top: B:25:0x0028 }] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0041 A[Catch: all -> 0x004e, TRY_LEAVE, TryCatch #0 {all -> 0x004e, blocks: (B:26:0x0028, B:18:0x002f, B:20:0x003d, B:21:0x0041), top: B:25:0x0028 }] */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0028 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r7) {
            /*
                r6 = this;
                java.util.concurrent.CompletableFuture r0 = r6.dep
                r1 = 0
                if (r0 == 0) goto L5d
                java.util.function.Function r2 = r6.fn
                if (r2 == 0) goto L5d
                java.util.concurrent.CompletableFuture r3 = r6.src
                if (r3 == 0) goto L5d
                java.lang.Object r4 = r3.result
                if (r4 != 0) goto L12
                goto L5d
            L12:
                java.lang.Object r5 = r0.result
                if (r5 != 0) goto L52
                boolean r5 = r4 instanceof java.util.concurrent.CompletableFuture.AltResult
                if (r5 == 0) goto L26
                r5 = r4
                java.util.concurrent.CompletableFuture$AltResult r5 = (java.util.concurrent.CompletableFuture.AltResult) r5
                java.lang.Throwable r5 = r5.ex
                if (r5 == 0) goto L25
                r0.completeThrowable(r5, r4)
                goto L52
            L25:
                r4 = r1
            L26:
                if (r7 > 0) goto L2f
                boolean r5 = r6.claim()     // Catch: java.lang.Throwable -> L4e
                if (r5 != 0) goto L2f
                return r1
            L2f:
                java.lang.Object r2 = r2.apply(r4)     // Catch: java.lang.Throwable -> L4e
                java.util.concurrent.CompletionStage r2 = (java.util.concurrent.CompletionStage) r2     // Catch: java.lang.Throwable -> L4e
                java.util.concurrent.CompletableFuture r2 = r2.toCompletableFuture()     // Catch: java.lang.Throwable -> L4e
                java.lang.Object r4 = r2.result     // Catch: java.lang.Throwable -> L4e
                if (r4 == 0) goto L41
                r0.completeRelay(r4)     // Catch: java.lang.Throwable -> L4e
                goto L52
            L41:
                java.util.concurrent.CompletableFuture$UniRelay r4 = new java.util.concurrent.CompletableFuture$UniRelay     // Catch: java.lang.Throwable -> L4e
                r4.<init>(r0, r2)     // Catch: java.lang.Throwable -> L4e
                r2.unipush(r4)     // Catch: java.lang.Throwable -> L4e
                java.lang.Object r2 = r0.result     // Catch: java.lang.Throwable -> L4e
                if (r2 != 0) goto L52
                return r1
            L4e:
                r2 = move-exception
                r0.completeThrowable(r2)
            L52:
                r6.dep = r1
                r6.src = r1
                r6.fn = r1
                java.util.concurrent.CompletableFuture r7 = r0.postFire(r3, r7)
                return r7
            L5d:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.UniCompose.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture uniComposeStage(Executor executor, Function function) {
        function.getClass();
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj = this.result;
        if (obj == null) {
            unipush(new UniCompose(executor, newIncompleteFuture, this, function));
            return newIncompleteFuture;
        }
        if (executor == null) {
            if (obj instanceof AltResult) {
                Throwable th = ((AltResult) obj).ex;
                if (th != null) {
                    newIncompleteFuture.result = encodeThrowable(th, obj);
                    return newIncompleteFuture;
                }
                obj = null;
            }
            try {
                CompletableFuture completableFuture = ((CompletionStage) function.apply(obj)).toCompletableFuture();
                Object obj2 = completableFuture.result;
                if (obj2 != null) {
                    newIncompleteFuture.result = encodeRelay(obj2);
                    return newIncompleteFuture;
                }
                completableFuture.unipush(new UniRelay(newIncompleteFuture, completableFuture));
                return newIncompleteFuture;
            } catch (Throwable th2) {
                newIncompleteFuture.result = encodeThrowable(th2);
            }
        } else {
            try {
                executor.execute(new UniCompose(null, newIncompleteFuture, this, function));
                return newIncompleteFuture;
            } catch (Throwable th3) {
                newIncompleteFuture.result = encodeThrowable(th3);
            }
        }
        return newIncompleteFuture;
    }

    static abstract class BiCompletion extends UniCompletion {
        CompletableFuture snd;

        BiCompletion(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3) {
            super(executor, completableFuture, completableFuture2);
            this.snd = completableFuture3;
        }
    }

    static final class CoCompletion extends Completion {
        BiCompletion base;

        CoCompletion(BiCompletion biCompletion) {
            this.base = biCompletion;
        }

        final CompletableFuture tryFire(int i) {
            CompletableFuture tryFire;
            BiCompletion biCompletion = this.base;
            if (biCompletion == null || (tryFire = biCompletion.tryFire(i)) == null) {
                return null;
            }
            this.base = null;
            return tryFire;
        }

        final boolean isLive() {
            BiCompletion biCompletion = this.base;
            return (biCompletion == null || biCompletion.dep == null) ? false : true;
        }
    }

    final void bipush(CompletableFuture completableFuture, BiCompletion biCompletion) {
        if (biCompletion != null) {
            while (this.result == null) {
                if (tryPushStack(biCompletion)) {
                    if (completableFuture.result == null) {
                        completableFuture.unipush(new CoCompletion(biCompletion));
                        return;
                    } else {
                        if (this.result != null) {
                            biCompletion.tryFire(0);
                            return;
                        }
                        return;
                    }
                }
            }
            completableFuture.unipush(biCompletion);
        }
    }

    final CompletableFuture postFire(CompletableFuture completableFuture, CompletableFuture completableFuture2, int i) {
        if (completableFuture2 != null && completableFuture2.stack != null) {
            Object obj = completableFuture2.result;
            if (obj == null) {
                completableFuture2.cleanStack();
            }
            if (i >= 0 && (obj != null || completableFuture2.result != null)) {
                completableFuture2.postComplete();
            }
        }
        return postFire(completableFuture, i);
    }

    static final class BiApply extends BiCompletion {
        BiFunction fn;

        BiApply(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, BiFunction biFunction) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biFunction;
        }

        final CompletableFuture tryFire(int i) {
            BiFunction biFunction;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Object obj2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && (biFunction = this.fn) != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null && (completableFuture2 = this.snd) != null && (obj2 = completableFuture2.result) != null) {
                if (completableFuture3.biApply(obj, obj2, biFunction, i > 0 ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return completableFuture3.postFire(completableFuture, completableFuture2, i);
                }
            }
            return null;
        }
    }

    final boolean biApply(Object obj, Object obj2, BiFunction biFunction, BiApply biApply) {
        if (this.result != null) {
            return true;
        }
        if (obj instanceof AltResult) {
            Throwable th = ((AltResult) obj).ex;
            if (th != null) {
                completeThrowable(th, obj);
                return true;
            }
            obj = null;
        }
        if (obj2 instanceof AltResult) {
            Throwable th2 = ((AltResult) obj2).ex;
            if (th2 != null) {
                completeThrowable(th2, obj2);
                return true;
            }
            obj2 = null;
        }
        if (biApply != null) {
            try {
                if (!biApply.claim()) {
                    return false;
                }
            } catch (Throwable th3) {
                completeThrowable(th3);
                return true;
            }
        }
        completeValue(biFunction.apply(obj, obj2));
        return true;
    }

    private CompletableFuture biApplyStage(Executor executor, CompletionStage completionStage, BiFunction biFunction) {
        CompletableFuture completableFuture;
        Object obj;
        if (biFunction == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj2 = this.result;
        if (obj2 == null || (obj = completableFuture.result) == null) {
            bipush(completableFuture, new BiApply(executor, newIncompleteFuture, this, completableFuture, biFunction));
            return newIncompleteFuture;
        }
        if (executor == null) {
            newIncompleteFuture.biApply(obj2, obj, biFunction, null);
            return newIncompleteFuture;
        }
        try {
            executor.execute(new BiApply(null, newIncompleteFuture, this, completableFuture, biFunction));
            return newIncompleteFuture;
        } catch (Throwable th) {
            newIncompleteFuture.result = encodeThrowable(th);
            return newIncompleteFuture;
        }
    }

    static final class BiAccept extends BiCompletion {
        BiConsumer fn;

        BiAccept(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, BiConsumer biConsumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biConsumer;
        }

        final CompletableFuture tryFire(int i) {
            BiConsumer biConsumer;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Object obj2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && (biConsumer = this.fn) != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null && (completableFuture2 = this.snd) != null && (obj2 = completableFuture2.result) != null) {
                if (completableFuture3.biAccept(obj, obj2, biConsumer, i > 0 ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return completableFuture3.postFire(completableFuture, completableFuture2, i);
                }
            }
            return null;
        }
    }

    final boolean biAccept(Object obj, Object obj2, BiConsumer biConsumer, BiAccept biAccept) {
        if (this.result != null) {
            return true;
        }
        if (obj instanceof AltResult) {
            Throwable th = ((AltResult) obj).ex;
            if (th != null) {
                completeThrowable(th, obj);
                return true;
            }
            obj = null;
        }
        if (obj2 instanceof AltResult) {
            Throwable th2 = ((AltResult) obj2).ex;
            if (th2 != null) {
                completeThrowable(th2, obj2);
                return true;
            }
            obj2 = null;
        }
        if (biAccept != null) {
            try {
                if (!biAccept.claim()) {
                    return false;
                }
            } catch (Throwable th3) {
                completeThrowable(th3);
                return true;
            }
        }
        biConsumer.accept(obj, obj2);
        completeNull();
        return true;
    }

    private CompletableFuture biAcceptStage(Executor executor, CompletionStage completionStage, BiConsumer biConsumer) {
        CompletableFuture completableFuture;
        Object obj;
        if (biConsumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj2 = this.result;
        if (obj2 == null || (obj = completableFuture.result) == null) {
            bipush(completableFuture, new BiAccept(executor, newIncompleteFuture, this, completableFuture, biConsumer));
            return newIncompleteFuture;
        }
        if (executor == null) {
            newIncompleteFuture.biAccept(obj2, obj, biConsumer, null);
            return newIncompleteFuture;
        }
        try {
            executor.execute(new BiAccept(null, newIncompleteFuture, this, completableFuture, biConsumer));
            return newIncompleteFuture;
        } catch (Throwable th) {
            newIncompleteFuture.result = encodeThrowable(th);
            return newIncompleteFuture;
        }
    }

    static final class BiRun extends BiCompletion {
        Runnable fn;

        BiRun(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        final CompletableFuture tryFire(int i) {
            Runnable runnable;
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Object obj2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && (runnable = this.fn) != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null && (completableFuture2 = this.snd) != null && (obj2 = completableFuture2.result) != null) {
                if (completableFuture3.biRun(obj, obj2, runnable, i > 0 ? null : this)) {
                    this.dep = null;
                    this.src = null;
                    this.snd = null;
                    this.fn = null;
                    return completableFuture3.postFire(completableFuture, completableFuture2, i);
                }
            }
            return null;
        }
    }

    final boolean biRun(Object obj, Object obj2, Runnable runnable, BiRun biRun) {
        Throwable th;
        if (this.result != null) {
            return true;
        }
        if (!(obj instanceof AltResult) || (th = ((AltResult) obj).ex) == null) {
            if (!(obj2 instanceof AltResult) || (th = ((AltResult) obj2).ex) == null) {
                if (biRun != null) {
                    try {
                        if (!biRun.claim()) {
                            return false;
                        }
                    } catch (Throwable th2) {
                        completeThrowable(th2);
                        return true;
                    }
                }
                runnable.run();
                completeNull();
                return true;
            }
            obj = obj2;
        }
        completeThrowable(th, obj);
        return true;
    }

    private CompletableFuture biRunStage(Executor executor, CompletionStage completionStage, Runnable runnable) {
        CompletableFuture completableFuture;
        Object obj;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        CompletableFuture newIncompleteFuture = newIncompleteFuture();
        Object obj2 = this.result;
        if (obj2 == null || (obj = completableFuture.result) == null) {
            bipush(completableFuture, new BiRun(executor, newIncompleteFuture, this, completableFuture, runnable));
            return newIncompleteFuture;
        }
        if (executor == null) {
            newIncompleteFuture.biRun(obj2, obj, runnable, null);
            return newIncompleteFuture;
        }
        try {
            executor.execute(new BiRun(null, newIncompleteFuture, this, completableFuture, runnable));
            return newIncompleteFuture;
        } catch (Throwable th) {
            newIncompleteFuture.result = encodeThrowable(th);
            return newIncompleteFuture;
        }
    }

    static final class BiRelay extends BiCompletion {
        BiRelay(CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }

        final CompletableFuture tryFire(int i) {
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture completableFuture2;
            Object obj2;
            Throwable th;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 == null || (completableFuture = this.src) == null || (obj = completableFuture.result) == null || (completableFuture2 = this.snd) == null || (obj2 = completableFuture2.result) == null) {
                return null;
            }
            if (completableFuture3.result == null) {
                if ((obj instanceof AltResult) && (th = ((AltResult) obj).ex) != null) {
                    completableFuture3.completeThrowable(th, obj);
                } else if ((obj2 instanceof AltResult) && (th = ((AltResult) obj2).ex) != null) {
                    obj = obj2;
                    completableFuture3.completeThrowable(th, obj);
                } else {
                    completableFuture3.completeNull();
                }
            }
            this.src = null;
            this.snd = null;
            this.dep = null;
            return completableFuture3.postFire(completableFuture, completableFuture2, i);
        }
    }

    static CompletableFuture andTree(CompletableFuture[] completableFutureArr, int i, int i2) {
        CompletableFuture andTree;
        CompletableFuture andTree2;
        Object obj;
        Throwable th;
        CompletableFuture completableFuture = new CompletableFuture();
        if (i > i2) {
            completableFuture.result = NIL;
            return completableFuture;
        }
        int i3 = (i + i2) >>> 1;
        if (i == i3) {
            andTree = completableFutureArr[i];
        } else {
            andTree = andTree(completableFutureArr, i, i3);
        }
        if (andTree != null) {
            if (i == i2) {
                andTree2 = andTree;
            } else {
                int i4 = i3 + 1;
                if (i2 == i4) {
                    andTree2 = completableFutureArr[i2];
                } else {
                    andTree2 = andTree(completableFutureArr, i4, i2);
                }
            }
            if (andTree2 != null) {
                Object obj2 = andTree.result;
                if (obj2 == null || (obj = andTree2.result) == null) {
                    andTree.bipush(andTree2, new BiRelay(completableFuture, andTree, andTree2));
                    return completableFuture;
                }
                if (!(obj2 instanceof AltResult) || (th = ((AltResult) obj2).ex) == null) {
                    if (!(obj instanceof AltResult) || (th = ((AltResult) obj).ex) == null) {
                        completableFuture.result = NIL;
                        return completableFuture;
                    }
                    obj2 = obj;
                }
                completableFuture.result = encodeThrowable(th, obj2);
                return completableFuture;
            }
        }
        throw null;
    }

    final void orpush(CompletableFuture completableFuture, BiCompletion biCompletion) {
        if (biCompletion != null) {
            while (true) {
                if (tryPushStack(biCompletion)) {
                    break;
                } else if (this.result != null) {
                    CompletableFuture$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    break;
                }
            }
            if (this.result != null) {
                biCompletion.tryFire(0);
            } else {
                completableFuture.unipush(new CoCompletion(biCompletion));
            }
        }
    }

    static final class OrApply extends BiCompletion {
        Function fn;

        OrApply(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, Function function) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = function;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x002b A[Catch: all -> 0x003f, TryCatch #0 {all -> 0x003f, blocks: (B:24:0x0020, B:16:0x0027, B:18:0x002b, B:20:0x0032, B:22:0x0037), top: B:23:0x0020 }] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0037 A[Catch: all -> 0x003f, PHI: r5
          0x0037: PHI (r5v2 java.lang.Object) = (r5v1 java.lang.Object), (r5v3 java.lang.Object) binds: [B:17:0x0029, B:21:0x0036] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {all -> 0x003f, blocks: (B:24:0x0020, B:16:0x0027, B:18:0x002b, B:20:0x0032, B:22:0x0037), top: B:23:0x0020 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r8) {
            /*
                r7 = this;
                java.util.concurrent.CompletableFuture r0 = r7.dep
                r1 = 0
                if (r0 == 0) goto L50
                java.util.function.Function r2 = r7.fn
                if (r2 == 0) goto L50
                java.util.concurrent.CompletableFuture r3 = r7.src
                if (r3 == 0) goto L50
                java.util.concurrent.CompletableFuture r4 = r7.snd
                if (r4 == 0) goto L50
                java.lang.Object r5 = r3.result
                if (r5 != 0) goto L1a
                java.lang.Object r5 = r4.result
                if (r5 != 0) goto L1a
                goto L50
            L1a:
                java.lang.Object r6 = r0.result
                if (r6 != 0) goto L43
                if (r8 > 0) goto L27
                boolean r6 = r7.claim()     // Catch: java.lang.Throwable -> L3f
                if (r6 != 0) goto L27
                return r1
            L27:
                boolean r6 = r5 instanceof java.util.concurrent.CompletableFuture.AltResult     // Catch: java.lang.Throwable -> L3f
                if (r6 == 0) goto L37
                r6 = r5
                java.util.concurrent.CompletableFuture$AltResult r6 = (java.util.concurrent.CompletableFuture.AltResult) r6     // Catch: java.lang.Throwable -> L3f
                java.lang.Throwable r6 = r6.ex     // Catch: java.lang.Throwable -> L3f
                if (r6 == 0) goto L36
                r0.completeThrowable(r6, r5)     // Catch: java.lang.Throwable -> L3f
                goto L43
            L36:
                r5 = r1
            L37:
                java.lang.Object r2 = r2.apply(r5)     // Catch: java.lang.Throwable -> L3f
                r0.completeValue(r2)     // Catch: java.lang.Throwable -> L3f
                goto L43
            L3f:
                r2 = move-exception
                r0.completeThrowable(r2)
            L43:
                r7.dep = r1
                r7.src = r1
                r7.snd = r1
                r7.fn = r1
                java.util.concurrent.CompletableFuture r8 = r0.postFire(r3, r4, r8)
                return r8
            L50:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.OrApply.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture orApplyStage(Executor executor, CompletionStage completionStage, Function function) {
        CompletableFuture completableFuture;
        if (function == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        Object obj = this.result;
        if (obj == null) {
            obj = completableFuture.result;
            if (obj == null) {
                CompletableFuture newIncompleteFuture = newIncompleteFuture();
                orpush(completableFuture, new OrApply(executor, newIncompleteFuture, this, completableFuture, function));
                return newIncompleteFuture;
            }
        } else {
            completableFuture = this;
        }
        return completableFuture.uniApplyNow(obj, executor, function);
    }

    static final class OrAccept extends BiCompletion {
        Consumer fn;

        OrAccept(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, Consumer consumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = consumer;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x002b A[Catch: all -> 0x003e, TryCatch #0 {all -> 0x003e, blocks: (B:24:0x0020, B:16:0x0027, B:18:0x002b, B:20:0x0032, B:22:0x0037), top: B:23:0x0020 }] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0037 A[Catch: all -> 0x003e, PHI: r5
          0x0037: PHI (r5v2 java.lang.Object) = (r5v1 java.lang.Object), (r5v3 java.lang.Object) binds: [B:17:0x0029, B:21:0x0036] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {all -> 0x003e, blocks: (B:24:0x0020, B:16:0x0027, B:18:0x002b, B:20:0x0032, B:22:0x0037), top: B:23:0x0020 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r8) {
            /*
                r7 = this;
                java.util.concurrent.CompletableFuture r0 = r7.dep
                r1 = 0
                if (r0 == 0) goto L4f
                java.util.function.Consumer r2 = r7.fn
                if (r2 == 0) goto L4f
                java.util.concurrent.CompletableFuture r3 = r7.src
                if (r3 == 0) goto L4f
                java.util.concurrent.CompletableFuture r4 = r7.snd
                if (r4 == 0) goto L4f
                java.lang.Object r5 = r3.result
                if (r5 != 0) goto L1a
                java.lang.Object r5 = r4.result
                if (r5 != 0) goto L1a
                goto L4f
            L1a:
                java.lang.Object r6 = r0.result
                if (r6 != 0) goto L42
                if (r8 > 0) goto L27
                boolean r6 = r7.claim()     // Catch: java.lang.Throwable -> L3e
                if (r6 != 0) goto L27
                return r1
            L27:
                boolean r6 = r5 instanceof java.util.concurrent.CompletableFuture.AltResult     // Catch: java.lang.Throwable -> L3e
                if (r6 == 0) goto L37
                r6 = r5
                java.util.concurrent.CompletableFuture$AltResult r6 = (java.util.concurrent.CompletableFuture.AltResult) r6     // Catch: java.lang.Throwable -> L3e
                java.lang.Throwable r6 = r6.ex     // Catch: java.lang.Throwable -> L3e
                if (r6 == 0) goto L36
                r0.completeThrowable(r6, r5)     // Catch: java.lang.Throwable -> L3e
                goto L42
            L36:
                r5 = r1
            L37:
                r2.accept(r5)     // Catch: java.lang.Throwable -> L3e
                r0.completeNull()     // Catch: java.lang.Throwable -> L3e
                goto L42
            L3e:
                r2 = move-exception
                r0.completeThrowable(r2)
            L42:
                r7.dep = r1
                r7.src = r1
                r7.snd = r1
                r7.fn = r1
                java.util.concurrent.CompletableFuture r8 = r0.postFire(r3, r4, r8)
                return r8
            L4f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.OrAccept.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture orAcceptStage(Executor executor, CompletionStage completionStage, Consumer consumer) {
        CompletableFuture completableFuture;
        if (consumer == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        Object obj = this.result;
        if (obj == null) {
            obj = completableFuture.result;
            if (obj == null) {
                CompletableFuture newIncompleteFuture = newIncompleteFuture();
                orpush(completableFuture, new OrAccept(executor, newIncompleteFuture, this, completableFuture, consumer));
                return newIncompleteFuture;
            }
        } else {
            completableFuture = this;
        }
        return completableFuture.uniAcceptNow(obj, executor, consumer);
    }

    static final class OrRun extends BiCompletion {
        Runnable fn;

        OrRun(Executor executor, CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0036 A[Catch: all -> 0x003d, TRY_LEAVE, TryCatch #0 {all -> 0x003d, blocks: (B:23:0x0020, B:16:0x0027, B:18:0x002b, B:20:0x0032, B:21:0x0036), top: B:22:0x0020 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.CompletableFuture tryFire(int r8) {
            /*
                r7 = this;
                java.util.concurrent.CompletableFuture r0 = r7.dep
                r1 = 0
                if (r0 == 0) goto L4e
                java.lang.Runnable r2 = r7.fn
                if (r2 == 0) goto L4e
                java.util.concurrent.CompletableFuture r3 = r7.src
                if (r3 == 0) goto L4e
                java.util.concurrent.CompletableFuture r4 = r7.snd
                if (r4 == 0) goto L4e
                java.lang.Object r5 = r3.result
                if (r5 != 0) goto L1a
                java.lang.Object r5 = r4.result
                if (r5 != 0) goto L1a
                goto L4e
            L1a:
                java.lang.Object r6 = r0.result
                if (r6 != 0) goto L41
                if (r8 > 0) goto L27
                boolean r6 = r7.claim()     // Catch: java.lang.Throwable -> L3d
                if (r6 != 0) goto L27
                return r1
            L27:
                boolean r6 = r5 instanceof java.util.concurrent.CompletableFuture.AltResult     // Catch: java.lang.Throwable -> L3d
                if (r6 == 0) goto L36
                r6 = r5
                java.util.concurrent.CompletableFuture$AltResult r6 = (java.util.concurrent.CompletableFuture.AltResult) r6     // Catch: java.lang.Throwable -> L3d
                java.lang.Throwable r6 = r6.ex     // Catch: java.lang.Throwable -> L3d
                if (r6 == 0) goto L36
                r0.completeThrowable(r6, r5)     // Catch: java.lang.Throwable -> L3d
                goto L41
            L36:
                r2.run()     // Catch: java.lang.Throwable -> L3d
                r0.completeNull()     // Catch: java.lang.Throwable -> L3d
                goto L41
            L3d:
                r2 = move-exception
                r0.completeThrowable(r2)
            L41:
                r7.dep = r1
                r7.src = r1
                r7.snd = r1
                r7.fn = r1
                java.util.concurrent.CompletableFuture r8 = r0.postFire(r3, r4, r8)
                return r8
            L4e:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.OrRun.tryFire(int):java.util.concurrent.CompletableFuture");
        }
    }

    private CompletableFuture orRunStage(Executor executor, CompletionStage completionStage, Runnable runnable) {
        CompletableFuture completableFuture;
        if (runnable == null || (completableFuture = completionStage.toCompletableFuture()) == null) {
            throw null;
        }
        Object obj = this.result;
        if (obj == null) {
            obj = completableFuture.result;
            if (obj == null) {
                CompletableFuture newIncompleteFuture = newIncompleteFuture();
                orpush(completableFuture, new OrRun(executor, newIncompleteFuture, this, completableFuture, runnable));
                return newIncompleteFuture;
            }
        } else {
            completableFuture = this;
        }
        return completableFuture.uniRunNow(obj, executor, runnable);
    }

    static class AnyOf extends Completion {
        CompletableFuture dep;
        CompletableFuture src;
        CompletableFuture[] srcs;

        AnyOf(CompletableFuture completableFuture, CompletableFuture completableFuture2, CompletableFuture[] completableFutureArr) {
            this.dep = completableFuture;
            this.src = completableFuture2;
            this.srcs = completableFutureArr;
        }

        final CompletableFuture tryFire(int i) {
            CompletableFuture completableFuture;
            Object obj;
            CompletableFuture[] completableFutureArr;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && (completableFuture = this.src) != null && (obj = completableFuture.result) != null && (completableFutureArr = this.srcs) != null) {
                this.dep = null;
                this.src = null;
                this.srcs = null;
                if (completableFuture2.completeRelay(obj)) {
                    for (CompletableFuture completableFuture3 : completableFutureArr) {
                        if (completableFuture3 != completableFuture) {
                            completableFuture3.cleanStack();
                        }
                    }
                    if (i < 0) {
                        return completableFuture2;
                    }
                    completableFuture2.postComplete();
                }
            }
            return null;
        }

        final boolean isLive() {
            CompletableFuture completableFuture = this.dep;
            return completableFuture != null && completableFuture.result == null;
        }
    }

    static final class AsyncSupply extends ForkJoinTask implements Runnable, AsynchronousCompletionTask {
        CompletableFuture dep;
        Supplier fn;

        public final Void getRawResult() {
            return null;
        }

        public final void setRawResult(Void r1) {
        }

        AsyncSupply(CompletableFuture completableFuture, Supplier supplier) {
            this.dep = completableFuture;
            this.fn = supplier;
        }

        public final boolean exec() {
            run();
            return false;
        }

        public void run() {
            Supplier supplier;
            CompletableFuture completableFuture = this.dep;
            if (completableFuture == null || (supplier = this.fn) == null) {
                return;
            }
            this.dep = null;
            this.fn = null;
            if (completableFuture.result == null) {
                try {
                    completableFuture.completeValue(supplier.get());
                } catch (Throwable th) {
                    completableFuture.completeThrowable(th);
                }
            }
            completableFuture.postComplete();
        }
    }

    static CompletableFuture asyncSupplyStage(Executor executor, Supplier supplier) {
        supplier.getClass();
        CompletableFuture completableFuture = new CompletableFuture();
        executor.execute(new AsyncSupply(completableFuture, supplier));
        return completableFuture;
    }

    static final class AsyncRun extends ForkJoinTask implements Runnable, AsynchronousCompletionTask {
        CompletableFuture dep;
        Runnable fn;

        public final Void getRawResult() {
            return null;
        }

        public final void setRawResult(Void r1) {
        }

        AsyncRun(CompletableFuture completableFuture, Runnable runnable) {
            this.dep = completableFuture;
            this.fn = runnable;
        }

        public final boolean exec() {
            run();
            return false;
        }

        public void run() {
            Runnable runnable;
            CompletableFuture completableFuture = this.dep;
            if (completableFuture == null || (runnable = this.fn) == null) {
                return;
            }
            this.dep = null;
            this.fn = null;
            if (completableFuture.result == null) {
                try {
                    runnable.run();
                    completableFuture.completeNull();
                } catch (Throwable th) {
                    completableFuture.completeThrowable(th);
                }
            }
            completableFuture.postComplete();
        }
    }

    static CompletableFuture asyncRunStage(Executor executor, Runnable runnable) {
        runnable.getClass();
        CompletableFuture completableFuture = new CompletableFuture();
        executor.execute(new AsyncRun(completableFuture, runnable));
        return completableFuture;
    }

    static final class Signaller extends Completion implements ForkJoinPool.ManagedBlocker {
        final long deadline;
        boolean interrupted;
        final boolean interruptible;
        long nanos;
        volatile Thread thread = Thread.currentThread();

        Signaller(boolean z, long j, long j2) {
            this.interruptible = z;
            this.nanos = j;
            this.deadline = j2;
        }

        final CompletableFuture tryFire(int i) {
            Thread thread = this.thread;
            if (thread != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean isReleasable() {
            /*
                r8 = this;
                boolean r0 = java.lang.Thread.interrupted()
                r1 = 1
                if (r0 == 0) goto L9
                r8.interrupted = r1
            L9:
                boolean r0 = r8.interrupted
                if (r0 == 0) goto L11
                boolean r0 = r8.interruptible
                if (r0 != 0) goto L2e
            L11:
                long r2 = r8.deadline
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 == 0) goto L2a
                long r6 = r8.nanos
                int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r0 <= 0) goto L2e
                long r6 = java.lang.System.nanoTime()
                long r2 = r2 - r6
                r8.nanos = r2
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L2e
            L2a:
                java.lang.Thread r0 = r8.thread
                if (r0 != 0) goto L2f
            L2e:
                return r1
            L2f:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.Signaller.isReleasable():boolean");
        }

        public boolean block() {
            while (!isReleasable()) {
                if (this.deadline == 0) {
                    LockSupport.park(this);
                } else {
                    LockSupport.parkNanos(this, this.nanos);
                }
            }
            return true;
        }

        final boolean isLive() {
            return this.thread != null;
        }
    }

    private Object waitingGet(boolean z) {
        Object obj;
        boolean z2;
        boolean z3 = false;
        Signaller signaller = null;
        while (true) {
            obj = this.result;
            if (obj != null) {
                z2 = z;
                break;
            }
            if (signaller != null) {
                z2 = z;
                if (!z3) {
                    z3 = tryPushStack(signaller);
                } else {
                    try {
                        ForkJoinPool.managedBlock(signaller);
                    } catch (InterruptedException unused) {
                        signaller.interrupted = true;
                    }
                    if (signaller.interrupted && z2) {
                        break;
                    }
                }
            } else {
                z2 = z;
                Signaller signaller2 = new Signaller(z2, 0L, 0L);
                if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                    ForkJoinPool.helpAsyncBlocker(defaultExecutor(), signaller2);
                }
                signaller = signaller2;
            }
            z = z2;
        }
        if (signaller != null && z3) {
            signaller.thread = null;
            if (!z2 && signaller.interrupted) {
                Thread.currentThread().interrupt();
            }
            if (obj == null) {
                cleanStack();
            }
        }
        if (obj != null || (obj = this.result) != null) {
            postComplete();
        }
        return obj;
    }

    private Object timedGet(long j) throws TimeoutException {
        Object obj;
        long j2;
        if (Thread.interrupted()) {
            return null;
        }
        if (j > 0) {
            long nanoTime = System.nanoTime() + j;
            if (nanoTime == 0) {
                nanoTime = 1;
            }
            long j3 = nanoTime;
            boolean z = false;
            Signaller signaller = null;
            while (true) {
                obj = this.result;
                if (obj != null) {
                    break;
                }
                if (signaller == null) {
                    j2 = j;
                    Signaller signaller2 = new Signaller(true, j2, j3);
                    if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                        ForkJoinPool.helpAsyncBlocker(defaultExecutor(), signaller2);
                    }
                    signaller = signaller2;
                } else {
                    j2 = j;
                    if (!z) {
                        z = tryPushStack(signaller);
                    } else {
                        if (signaller.nanos <= 0) {
                            break;
                        }
                        try {
                            ForkJoinPool.managedBlock(signaller);
                        } catch (InterruptedException unused) {
                            signaller.interrupted = true;
                        }
                        if (signaller.interrupted) {
                            break;
                        }
                    }
                }
                j = j2;
            }
            if (signaller != null && z) {
                signaller.thread = null;
                if (obj == null) {
                    cleanStack();
                }
            }
            if (obj != null || (obj = this.result) != null) {
                postComplete();
            }
            if (obj != null || (signaller != null && signaller.interrupted)) {
                return obj;
            }
        }
        throw new TimeoutException();
    }

    public CompletableFuture() {
    }

    CompletableFuture(Object obj) {
        this.result = obj;
    }

    public static CompletableFuture supplyAsync(Supplier supplier) {
        return asyncSupplyStage(ASYNC_POOL, supplier);
    }

    public static CompletableFuture supplyAsync(Supplier supplier, Executor executor) {
        return asyncSupplyStage(screenExecutor(executor), supplier);
    }

    public static CompletableFuture runAsync(Runnable runnable) {
        return asyncRunStage(ASYNC_POOL, runnable);
    }

    public static CompletableFuture runAsync(Runnable runnable, Executor executor) {
        return asyncRunStage(screenExecutor(executor), runnable);
    }

    public static CompletableFuture completedFuture(Object obj) {
        if (obj == null) {
            obj = NIL;
        }
        return new CompletableFuture(obj);
    }

    public boolean isDone() {
        return this.result != null;
    }

    public Object get() throws InterruptedException, ExecutionException {
        Object obj = this.result;
        if (obj == null) {
            obj = waitingGet(true);
        }
        return reportGet(obj);
    }

    public Object get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        long nanos = timeUnit.toNanos(j);
        Object obj = this.result;
        if (obj == null) {
            obj = timedGet(nanos);
        }
        return reportGet(obj);
    }

    public Object join() {
        Object obj = this.result;
        if (obj == null) {
            obj = waitingGet(false);
        }
        return reportJoin(obj);
    }

    public Object getNow(Object obj) {
        Object obj2 = this.result;
        return obj2 == null ? obj : reportJoin(obj2);
    }

    public boolean complete(Object obj) {
        boolean completeValue = completeValue(obj);
        postComplete();
        return completeValue;
    }

    public boolean completeExceptionally(Throwable th) {
        th.getClass();
        boolean internalComplete = internalComplete(new AltResult(th));
        postComplete();
        return internalComplete;
    }

    public CompletableFuture thenApply(Function function) {
        return uniApplyStage(null, function);
    }

    public CompletableFuture thenApplyAsync(Function function) {
        return uniApplyStage(defaultExecutor(), function);
    }

    public CompletableFuture thenApplyAsync(Function function, Executor executor) {
        return uniApplyStage(screenExecutor(executor), function);
    }

    public CompletableFuture thenAccept(Consumer consumer) {
        return uniAcceptStage(null, consumer);
    }

    public CompletableFuture thenAcceptAsync(Consumer consumer) {
        return uniAcceptStage(defaultExecutor(), consumer);
    }

    public CompletableFuture thenAcceptAsync(Consumer consumer, Executor executor) {
        return uniAcceptStage(screenExecutor(executor), consumer);
    }

    public CompletableFuture thenRun(Runnable runnable) {
        return uniRunStage(null, runnable);
    }

    public CompletableFuture thenRunAsync(Runnable runnable) {
        return uniRunStage(defaultExecutor(), runnable);
    }

    public CompletableFuture thenRunAsync(Runnable runnable, Executor executor) {
        return uniRunStage(screenExecutor(executor), runnable);
    }

    public CompletableFuture thenCombine(CompletionStage completionStage, BiFunction biFunction) {
        return biApplyStage(null, completionStage, biFunction);
    }

    public CompletableFuture thenCombineAsync(CompletionStage completionStage, BiFunction biFunction) {
        return biApplyStage(defaultExecutor(), completionStage, biFunction);
    }

    public CompletableFuture thenCombineAsync(CompletionStage completionStage, BiFunction biFunction, Executor executor) {
        return biApplyStage(screenExecutor(executor), completionStage, biFunction);
    }

    public CompletableFuture thenAcceptBoth(CompletionStage completionStage, BiConsumer biConsumer) {
        return biAcceptStage(null, completionStage, biConsumer);
    }

    public CompletableFuture thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer) {
        return biAcceptStage(defaultExecutor(), completionStage, biConsumer);
    }

    public CompletableFuture thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer, Executor executor) {
        return biAcceptStage(screenExecutor(executor), completionStage, biConsumer);
    }

    public CompletableFuture runAfterBoth(CompletionStage completionStage, Runnable runnable) {
        return biRunStage(null, completionStage, runnable);
    }

    public CompletableFuture runAfterBothAsync(CompletionStage completionStage, Runnable runnable) {
        return biRunStage(defaultExecutor(), completionStage, runnable);
    }

    public CompletableFuture runAfterBothAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
        return biRunStage(screenExecutor(executor), completionStage, runnable);
    }

    public CompletableFuture applyToEither(CompletionStage completionStage, Function function) {
        return orApplyStage(null, completionStage, function);
    }

    public CompletableFuture applyToEitherAsync(CompletionStage completionStage, Function function) {
        return orApplyStage(defaultExecutor(), completionStage, function);
    }

    public CompletableFuture applyToEitherAsync(CompletionStage completionStage, Function function, Executor executor) {
        return orApplyStage(screenExecutor(executor), completionStage, function);
    }

    public CompletableFuture acceptEither(CompletionStage completionStage, Consumer consumer) {
        return orAcceptStage(null, completionStage, consumer);
    }

    public CompletableFuture acceptEitherAsync(CompletionStage completionStage, Consumer consumer) {
        return orAcceptStage(defaultExecutor(), completionStage, consumer);
    }

    public CompletableFuture acceptEitherAsync(CompletionStage completionStage, Consumer consumer, Executor executor) {
        return orAcceptStage(screenExecutor(executor), completionStage, consumer);
    }

    public CompletableFuture runAfterEither(CompletionStage completionStage, Runnable runnable) {
        return orRunStage(null, completionStage, runnable);
    }

    public CompletableFuture runAfterEitherAsync(CompletionStage completionStage, Runnable runnable) {
        return orRunStage(defaultExecutor(), completionStage, runnable);
    }

    public CompletableFuture runAfterEitherAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
        return orRunStage(screenExecutor(executor), completionStage, runnable);
    }

    public CompletableFuture thenCompose(Function function) {
        return uniComposeStage(null, function);
    }

    public CompletableFuture thenComposeAsync(Function function) {
        return uniComposeStage(defaultExecutor(), function);
    }

    public CompletableFuture thenComposeAsync(Function function, Executor executor) {
        return uniComposeStage(screenExecutor(executor), function);
    }

    public CompletableFuture whenComplete(BiConsumer biConsumer) {
        return uniWhenCompleteStage(null, biConsumer);
    }

    public CompletableFuture whenCompleteAsync(BiConsumer biConsumer) {
        return uniWhenCompleteStage(defaultExecutor(), biConsumer);
    }

    public CompletableFuture whenCompleteAsync(BiConsumer biConsumer, Executor executor) {
        return uniWhenCompleteStage(screenExecutor(executor), biConsumer);
    }

    public CompletableFuture handle(BiFunction biFunction) {
        return uniHandleStage(null, biFunction);
    }

    public CompletableFuture handleAsync(BiFunction biFunction) {
        return uniHandleStage(defaultExecutor(), biFunction);
    }

    public CompletableFuture handleAsync(BiFunction biFunction, Executor executor) {
        return uniHandleStage(screenExecutor(executor), biFunction);
    }

    public CompletableFuture exceptionally(Function function) {
        return uniExceptionallyStage(function);
    }

    public static CompletableFuture allOf(CompletableFuture... completableFutureArr) {
        return andTree(completableFutureArr, 0, completableFutureArr.length - 1);
    }

    public static CompletableFuture anyOf(CompletableFuture... completableFutureArr) {
        int length = completableFutureArr.length;
        int i = 0;
        if (length <= 1) {
            if (length == 0) {
                return new CompletableFuture();
            }
            return uniCopyStage(completableFutureArr[0]);
        }
        for (CompletableFuture completableFuture : completableFutureArr) {
            Object obj = completableFuture.result;
            if (obj != null) {
                return new CompletableFuture(encodeRelay(obj));
            }
        }
        CompletableFuture[] completableFutureArr2 = (CompletableFuture[]) completableFutureArr.clone();
        CompletableFuture completableFuture2 = new CompletableFuture();
        for (CompletableFuture completableFuture3 : completableFutureArr2) {
            completableFuture3.unipush(new AnyOf(completableFuture2, completableFuture3, completableFutureArr2));
        }
        if (completableFuture2.result != null) {
            int length2 = completableFutureArr2.length;
            while (i < length2) {
                if (completableFutureArr2[i].result != null) {
                    while (true) {
                        i++;
                        if (i < length2) {
                            if (completableFutureArr2[i].result == null) {
                                completableFutureArr2[i].cleanStack();
                            }
                        }
                    }
                }
                i++;
            }
        }
        return completableFuture2;
    }

    public boolean cancel(boolean z) {
        boolean z2 = this.result == null && internalComplete(new AltResult(new CancellationException()));
        postComplete();
        return z2 || isCancelled();
    }

    public boolean isCancelled() {
        Object obj = this.result;
        return (obj instanceof AltResult) && (((AltResult) obj).ex instanceof CancellationException);
    }

    public boolean isCompletedExceptionally() {
        Object obj = this.result;
        return (obj instanceof AltResult) && obj != NIL;
    }

    public void obtrudeValue(Object obj) {
        if (obj == null) {
            obj = NIL;
        }
        this.result = obj;
        postComplete();
    }

    public void obtrudeException(Throwable th) {
        th.getClass();
        this.result = new AltResult(th);
        postComplete();
    }

    public int getNumberOfDependents() {
        int i = 0;
        for (Completion completion = this.stack; completion != null; completion = completion.next) {
            i++;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.result
            java.util.concurrent.CompletableFuture$Completion r1 = r4.stack
            r2 = 0
        L5:
            if (r1 == 0) goto Lc
            int r2 = r2 + 1
            java.util.concurrent.CompletableFuture$Completion r1 = r1.next
            goto L5
        Lc:
            java.lang.String r1 = super.toString()
            if (r0 != 0) goto L2b
            if (r2 != 0) goto L17
            java.lang.String r0 = "[Not completed]"
            goto L4d
        L17:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "[Not completed, "
            r0.<init>(r3)
            r0.append(r2)
            java.lang.String r2 = " dependents]"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            goto L4d
        L2b:
            boolean r2 = r0 instanceof java.util.concurrent.CompletableFuture.AltResult
            if (r2 == 0) goto L4b
            java.util.concurrent.CompletableFuture$AltResult r0 = (java.util.concurrent.CompletableFuture.AltResult) r0
            java.lang.Throwable r2 = r0.ex
            if (r2 == 0) goto L4b
            java.lang.Throwable r0 = r0.ex
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "[Completed exceptionally: "
            r2.<init>(r3)
            r2.append(r0)
            java.lang.String r0 = "]"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            goto L4d
        L4b:
            java.lang.String r0 = "[Completed normally]"
        L4d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.CompletableFuture.toString():java.lang.String");
    }

    public CompletableFuture newIncompleteFuture() {
        return new CompletableFuture();
    }

    public Executor defaultExecutor() {
        return ASYNC_POOL;
    }

    public CompletableFuture copy() {
        return uniCopyStage(this);
    }

    public CompletionStage minimalCompletionStage() {
        return uniAsMinimalStage();
    }

    public CompletableFuture completeAsync(Supplier supplier, Executor executor) {
        if (supplier == null || executor == null) {
            throw null;
        }
        executor.execute(new AsyncSupply(this, supplier));
        return this;
    }

    public CompletableFuture completeAsync(Supplier supplier) {
        return completeAsync(supplier, defaultExecutor());
    }

    public CompletableFuture orTimeout(long j, TimeUnit timeUnit) {
        timeUnit.getClass();
        if (this.result == null) {
            whenComplete((BiConsumer) new Canceller(Delayer.delay(new Timeout(this), j, timeUnit)));
        }
        return this;
    }

    public CompletableFuture completeOnTimeout(Object obj, long j, TimeUnit timeUnit) {
        timeUnit.getClass();
        if (this.result == null) {
            whenComplete((BiConsumer) new Canceller(Delayer.delay(new DelayedCompleter(this, obj), j, timeUnit)));
        }
        return this;
    }

    public static Executor delayedExecutor(long j, TimeUnit timeUnit, Executor executor) {
        if (timeUnit == null || executor == null) {
            throw null;
        }
        return new DelayedExecutor(j, timeUnit, executor);
    }

    public static Executor delayedExecutor(long j, TimeUnit timeUnit) {
        timeUnit.getClass();
        return new DelayedExecutor(j, timeUnit, ASYNC_POOL);
    }

    public static CompletionStage completedStage(Object obj) {
        if (obj == null) {
            obj = NIL;
        }
        return new MinimalStage(obj);
    }

    public static CompletableFuture failedFuture(Throwable th) {
        th.getClass();
        return new CompletableFuture(new AltResult(th));
    }

    public static CompletionStage failedStage(Throwable th) {
        th.getClass();
        return new MinimalStage(new AltResult(th));
    }

    static final class Delayer {
        static final ScheduledThreadPoolExecutor delayer;

        Delayer() {
        }

        static ScheduledFuture delay(Runnable runnable, long j, TimeUnit timeUnit) {
            return delayer.schedule(runnable, j, timeUnit);
        }

        static final class DaemonThreadFactory implements ThreadFactory {
            DaemonThreadFactory() {
            }

            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                thread.setName("CompletableFutureDelayScheduler");
                return thread;
            }
        }

        static {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
            delayer = scheduledThreadPoolExecutor;
            scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        }
    }

    static final class DelayedExecutor implements Executor {
        final long delay;
        final Executor executor;
        final TimeUnit unit;

        DelayedExecutor(long j, TimeUnit timeUnit, Executor executor) {
            this.delay = j;
            this.unit = timeUnit;
            this.executor = executor;
        }

        public void execute(Runnable runnable) {
            Delayer.delay(new TaskSubmitter(this.executor, runnable), this.delay, this.unit);
        }
    }

    static final class TaskSubmitter implements Runnable {
        final Runnable action;
        final Executor executor;

        TaskSubmitter(Executor executor, Runnable runnable) {
            this.executor = executor;
            this.action = runnable;
        }

        public void run() {
            this.executor.execute(this.action);
        }
    }

    static final class Timeout implements Runnable {
        final CompletableFuture f;

        Timeout(CompletableFuture completableFuture) {
            this.f = completableFuture;
        }

        public void run() {
            CompletableFuture completableFuture = this.f;
            if (completableFuture == null || completableFuture.isDone()) {
                return;
            }
            this.f.completeExceptionally(new TimeoutException());
        }
    }

    static final class DelayedCompleter implements Runnable {
        final CompletableFuture f;
        final Object u;

        DelayedCompleter(CompletableFuture completableFuture, Object obj) {
            this.f = completableFuture;
            this.u = obj;
        }

        public void run() {
            CompletableFuture completableFuture = this.f;
            if (completableFuture != null) {
                completableFuture.complete(this.u);
            }
        }
    }

    static final class Canceller implements BiConsumer {
        final Future f;

        public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
            return BiConsumer.-CC.$default$andThen(this, biConsumer);
        }

        Canceller(Future future) {
            this.f = future;
        }

        public void accept(Object obj, Throwable th) {
            Future future;
            if (th != null || (future = this.f) == null || future.isDone()) {
                return;
            }
            this.f.cancel(false);
        }
    }

    static final class MinimalStage extends CompletableFuture {
        public /* bridge */ /* synthetic */ CompletionStage acceptEither(CompletionStage completionStage, Consumer consumer) {
            return super.acceptEither(completionStage, consumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage acceptEitherAsync(CompletionStage completionStage, Consumer consumer) {
            return super.acceptEitherAsync(completionStage, consumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage acceptEitherAsync(CompletionStage completionStage, Consumer consumer, Executor executor) {
            return super.acceptEitherAsync(completionStage, consumer, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage applyToEither(CompletionStage completionStage, Function function) {
            return super.applyToEither(completionStage, function);
        }

        public /* bridge */ /* synthetic */ CompletionStage applyToEitherAsync(CompletionStage completionStage, Function function) {
            return super.applyToEitherAsync(completionStage, function);
        }

        public /* bridge */ /* synthetic */ CompletionStage applyToEitherAsync(CompletionStage completionStage, Function function, Executor executor) {
            return super.applyToEitherAsync(completionStage, function, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage exceptionally(Function function) {
            return super.exceptionally(function);
        }

        public /* bridge */ /* synthetic */ CompletionStage handle(BiFunction biFunction) {
            return super.handle(biFunction);
        }

        public /* bridge */ /* synthetic */ CompletionStage handleAsync(BiFunction biFunction) {
            return super.handleAsync(biFunction);
        }

        public /* bridge */ /* synthetic */ CompletionStage handleAsync(BiFunction biFunction, Executor executor) {
            return super.handleAsync(biFunction, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterBoth(CompletionStage completionStage, Runnable runnable) {
            return super.runAfterBoth(completionStage, runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterBothAsync(CompletionStage completionStage, Runnable runnable) {
            return super.runAfterBothAsync(completionStage, runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterBothAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
            return super.runAfterBothAsync(completionStage, runnable, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterEither(CompletionStage completionStage, Runnable runnable) {
            return super.runAfterEither(completionStage, runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterEitherAsync(CompletionStage completionStage, Runnable runnable) {
            return super.runAfterEitherAsync(completionStage, runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage runAfterEitherAsync(CompletionStage completionStage, Runnable runnable, Executor executor) {
            return super.runAfterEitherAsync(completionStage, runnable, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAccept(Consumer consumer) {
            return super.thenAccept(consumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAcceptAsync(Consumer consumer) {
            return super.thenAcceptAsync(consumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAcceptAsync(Consumer consumer, Executor executor) {
            return super.thenAcceptAsync(consumer, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBoth(CompletionStage completionStage, BiConsumer biConsumer) {
            return super.thenAcceptBoth(completionStage, biConsumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer) {
            return super.thenAcceptBothAsync(completionStage, biConsumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenAcceptBothAsync(CompletionStage completionStage, BiConsumer biConsumer, Executor executor) {
            return super.thenAcceptBothAsync(completionStage, biConsumer, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenApply(Function function) {
            return super.thenApply(function);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenApplyAsync(Function function) {
            return super.thenApplyAsync(function);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenApplyAsync(Function function, Executor executor) {
            return super.thenApplyAsync(function, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenCombine(CompletionStage completionStage, BiFunction biFunction) {
            return super.thenCombine(completionStage, biFunction);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenCombineAsync(CompletionStage completionStage, BiFunction biFunction) {
            return super.thenCombineAsync(completionStage, biFunction);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenCombineAsync(CompletionStage completionStage, BiFunction biFunction, Executor executor) {
            return super.thenCombineAsync(completionStage, biFunction, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenCompose(Function function) {
            return super.thenCompose(function);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenComposeAsync(Function function) {
            return super.thenComposeAsync(function);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenComposeAsync(Function function, Executor executor) {
            return super.thenComposeAsync(function, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenRun(Runnable runnable) {
            return super.thenRun(runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenRunAsync(Runnable runnable) {
            return super.thenRunAsync(runnable);
        }

        public /* bridge */ /* synthetic */ CompletionStage thenRunAsync(Runnable runnable, Executor executor) {
            return super.thenRunAsync(runnable, executor);
        }

        public /* bridge */ /* synthetic */ CompletionStage whenComplete(BiConsumer biConsumer) {
            return super.whenComplete(biConsumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage whenCompleteAsync(BiConsumer biConsumer) {
            return super.whenCompleteAsync(biConsumer);
        }

        public /* bridge */ /* synthetic */ CompletionStage whenCompleteAsync(BiConsumer biConsumer, Executor executor) {
            return super.whenCompleteAsync(biConsumer, executor);
        }

        MinimalStage() {
        }

        MinimalStage(Object obj) {
            super(obj);
        }

        public CompletableFuture newIncompleteFuture() {
            return new MinimalStage();
        }

        public Object get() {
            throw new UnsupportedOperationException();
        }

        public Object get(long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        public Object getNow(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Object join() {
            throw new UnsupportedOperationException();
        }

        public boolean complete(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean completeExceptionally(Throwable th) {
            throw new UnsupportedOperationException();
        }

        public boolean cancel(boolean z) {
            throw new UnsupportedOperationException();
        }

        public void obtrudeValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public void obtrudeException(Throwable th) {
            throw new UnsupportedOperationException();
        }

        public boolean isDone() {
            throw new UnsupportedOperationException();
        }

        public boolean isCancelled() {
            throw new UnsupportedOperationException();
        }

        public boolean isCompletedExceptionally() {
            throw new UnsupportedOperationException();
        }

        public int getNumberOfDependents() {
            throw new UnsupportedOperationException();
        }

        public CompletableFuture completeAsync(Supplier supplier, Executor executor) {
            throw new UnsupportedOperationException();
        }

        public CompletableFuture completeAsync(Supplier supplier) {
            throw new UnsupportedOperationException();
        }

        public CompletableFuture orTimeout(long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        public CompletableFuture completeOnTimeout(Object obj, long j, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        public CompletableFuture toCompletableFuture() {
            Object obj = this.result;
            if (obj != null) {
                return new CompletableFuture(encodeRelay(obj));
            }
            CompletableFuture completableFuture = new CompletableFuture();
            unipush(new UniRelay(completableFuture, this));
            return completableFuture;
        }
    }
}
