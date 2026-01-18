package kotlin;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeepRecursive.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004BJ\u00129\u0010\u0005\u001a5\b\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b\u0012\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\u0017\u001a\u00020\u00182\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\u0011H\u0016¢\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00028\u00012\u0006\u0010\t\u001a\u00028\u0000H\u0096@¢\u0006\u0002\u0010\u001bJ2\u0010\u001a\u001a\u0002H\u001c\"\u0004\b\u0002\u0010\u001d\"\u0004\b\u0003\u0010\u001c*\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001c0\u001e2\u0006\u0010\t\u001a\u0002H\u001dH\u0096@¢\u0006\u0002\u0010\u001fJd\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00042=\u0010!\u001a9\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006j\u0002`\r¢\u0006\u0002\b\b2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\u0002¢\u0006\u0002\u0010\"J\u000b\u0010#\u001a\u00028\u0001¢\u0006\u0002\u0010$RG\u0010\f\u001a9\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006j\u0002`\r¢\u0006\u0002\b\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000eR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0011X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006%"}, d2 = {"Lkotlin/DeepRecursiveScopeImpl;", "T", "R", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function3;", "", "Lkotlin/ExtensionFunctionType;", "value", "<init>", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)V", "function", "Lkotlin/DeepRecursiveFunctionBlock;", "Lkotlin/jvm/functions/Function3;", "cont", "result", "Lkotlin/Result;", "Ljava/lang/Object;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "(Ljava/lang/Object;)V", "callRecursive", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "S", "U", "Lkotlin/DeepRecursiveFunction;", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crossFunctionCompletion", "currentFunction", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "runCallLoop", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
final class DeepRecursiveScopeImpl extends DeepRecursiveScope implements Continuation {

    @Nullable
    private Continuation cont;

    @NotNull
    private Function3 function;

    @NotNull
    private Object result;

    @Nullable
    private Object value;

    public static final /* synthetic */ void access$setCont$p(DeepRecursiveScopeImpl deepRecursiveScopeImpl, Continuation continuation) {
        deepRecursiveScopeImpl.cont = continuation;
    }

    public static final /* synthetic */ void access$setFunction$p(DeepRecursiveScopeImpl deepRecursiveScopeImpl, Function3 function3) {
        deepRecursiveScopeImpl.function = function3;
    }

    public static final /* synthetic */ void access$setResult$p(DeepRecursiveScopeImpl deepRecursiveScopeImpl, Object obj) {
        deepRecursiveScopeImpl.result = obj;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeepRecursiveScopeImpl(@NotNull Function3 block, Object obj) {
        super(null);
        Intrinsics.checkNotNullParameter(block, "block");
        this.function = block;
        this.value = obj;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        this.cont = this;
        this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
    }

    @NotNull
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    public void resumeWith(@NotNull Object result) {
        this.cont = null;
        this.result = result;
    }

    @Nullable
    public Object callRecursive(Object obj, @NotNull Continuation continuation) {
        Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        this.cont = continuation;
        this.value = obj;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended;
    }

    @Nullable
    public Object callRecursive(@NotNull DeepRecursiveFunction deepRecursiveFunction, Object obj, @NotNull Continuation continuation) {
        Function3 block = deepRecursiveFunction.getBlock();
        Intrinsics.checkNotNull(block, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.coroutines.SuspendFunction2<kotlin.DeepRecursiveScope<*, *>, kotlin.Any?, kotlin.Any?>");
        Function3 function3 = this.function;
        if (block != function3) {
            this.function = block;
            Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            this.cont = crossFunctionCompletion(function3, continuation);
        } else {
            Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            this.cont = continuation;
        }
        this.value = obj;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (coroutine_suspended == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return coroutine_suspended;
    }

    private final Continuation crossFunctionCompletion(Function3 currentFunction, Continuation cont) {
        return new DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1(EmptyCoroutineContext.INSTANCE, this, currentFunction, cont);
    }

    public final Object runCallLoop() {
        while (true) {
            Object obj = this.result;
            Continuation continuation = this.cont;
            if (continuation == null) {
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            if (Result.equals-impl0(DeepRecursiveKt.access$getUNDEFINED_RESULT$p(), obj)) {
                try {
                    Function3 function3 = this.function;
                    Object obj2 = this.value;
                    Object wrapWithContinuationImpl = !(function3 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function3, this, obj2, continuation) : ((Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function3, 3)).invoke(this, obj2, continuation);
                    if (wrapWithContinuationImpl != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                        Result.Companion companion = Result.Companion;
                        continuation.resumeWith(Result.constructor-impl(wrapWithContinuationImpl));
                    }
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    continuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(th)));
                }
            } else {
                this.result = DeepRecursiveKt.access$getUNDEFINED_RESULT$p();
                continuation.resumeWith(obj);
            }
        }
    }
}
