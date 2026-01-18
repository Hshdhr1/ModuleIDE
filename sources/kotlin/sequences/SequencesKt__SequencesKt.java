package kotlin.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\bø\u0001\u0000\u001a\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a+\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\t\"\u0002H\u0002¢\u0006\u0002\u0010\n\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a!\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a2\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a)\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00100\u0001H\u0007¢\u0006\u0002\b\u0011\u001aC\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00050\u0013H\u0002¢\u0006\u0002\b\u0014\u001a@\u0010\u0015\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00170\u0016\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0012*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00120\u00160\u0001\u001a\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a&\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\u001ab\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u001c\"\u0004\b\u0002\u0010\u00122\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u001c0\u001f2\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00050\u0013H\u0000\u001a\u001c\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a&\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020#2\u000e\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a=\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020#2\b\u0010%\u001a\u0004\u0018\u0001H\u00022\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0013H\u0007¢\u0006\u0002\u0010&\u001a<\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020#2\u000e\u0010'\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006("}, d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "asSequence", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "emptySequence", "orEmpty", "ifEmpty", "defaultValue", "flatten", "", "flattenSequenceOfIterable", "R", "Lkotlin/Function1;", "flatten$SequencesKt__SequencesKt", "unzip", "Lkotlin/Pair;", "", "shuffled", "random", "Lkotlin/random/Random;", "flatMapIndexed", "C", "source", "transform", "Lkotlin/Function2;", "", "constrainOnce", "generateSequence", "", "nextFunction", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "seedFunction", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/sequences/SequencesKt")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    public static /* synthetic */ Object $r8$lambda$25ni0aU1ETnphMQDOvfd00B9RQ4(Object obj) {
        return generateSequence$lambda$5$SequencesKt__SequencesKt(obj);
    }

    public static /* synthetic */ Iterator $r8$lambda$9iJWMFkQ2tlaK6F7xoYFrSibhaA(Sequence sequence) {
        return flatten$lambda$1$SequencesKt__SequencesKt(sequence);
    }

    public static /* synthetic */ Object $r8$lambda$L9TKF4N7ulNLX9-IwT8egc-EgVE(Function0 function0, Object obj) {
        return generateSequence$lambda$4$SequencesKt__SequencesKt(function0, obj);
    }

    public static /* synthetic */ Object $r8$lambda$o_e9IOKJQHtSUcj4z_dpV9t2EjY(Object obj) {
        return flatten$lambda$3$SequencesKt__SequencesKt(obj);
    }

    public static /* synthetic */ Iterator $r8$lambda$szS2W32gArCeCRFAteFxa2rX3tk(Iterable iterable) {
        return flatten$lambda$2$SequencesKt__SequencesKt(iterable);
    }

    private static final Object flatten$lambda$3$SequencesKt__SequencesKt(Object obj) {
        return obj;
    }

    private static final Object generateSequence$lambda$5$SequencesKt__SequencesKt(Object obj) {
        return obj;
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004"}, d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Sequence {
        final /* synthetic */ Function0 $iterator;

        public 1(Function0 function0) {
            this.$iterator = function0;
        }

        public Iterator iterator() {
            return (Iterator) this.$iterator.invoke();
        }
    }

    @InlineOnly
    private static final Sequence Sequence(Function0 iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return new 1(iterator);
    }

    @NotNull
    public static final Sequence asSequence(@NotNull Iterator it) {
        Intrinsics.checkNotNullParameter(it, "<this>");
        return SequencesKt.constrainOnce(new SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1(it));
    }

    @NotNull
    public static final Sequence sequenceOf(@NotNull Object... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return ArraysKt.asSequence(elements);
    }

    @NotNull
    public static final Sequence emptySequence() {
        return EmptySequence.INSTANCE;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Sequence orEmpty(Sequence sequence) {
        return sequence == null ? SequencesKt.emptySequence() : sequence;
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1", f = "Sequences.kt", i = {}, l = {69, 71}, m = "invokeSuspend", n = {}, s = {})
    static final class 1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function0 $defaultValue;
        final /* synthetic */ Sequence $this_ifEmpty;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(Sequence sequence, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$this_ifEmpty = sequence;
            this.$defaultValue = function0;
        }

        public final Continuation create(Object obj, Continuation continuation) {
            1 r0 = new 1(this.$this_ifEmpty, this.$defaultValue, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
        
            if (r6.yieldAll(r1, r5) == r0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x004b, code lost:
        
            if (r6.yieldAll((kotlin.sequences.Sequence) r5.$defaultValue.invoke(), r5) == r0) goto L17;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1b
                if (r1 == r3) goto L17
                if (r1 != r2) goto Lf
                goto L17
            Lf:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            L17:
                kotlin.ResultKt.throwOnFailure(r6)
                goto L4e
            L1b:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Object r6 = r5.L$0
                kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
                kotlin.sequences.Sequence r1 = r5.$this_ifEmpty
                java.util.Iterator r1 = r1.iterator()
                boolean r4 = r1.hasNext()
                if (r4 == 0) goto L3a
                r2 = r5
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r5.label = r3
                java.lang.Object r6 = r6.yieldAll(r1, r2)
                if (r6 != r0) goto L4e
                goto L4d
            L3a:
                kotlin.jvm.functions.Function0 r1 = r5.$defaultValue
                java.lang.Object r1 = r1.invoke()
                kotlin.sequences.Sequence r1 = (kotlin.sequences.Sequence) r1
                r3 = r5
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                r5.label = r2
                java.lang.Object r6 = r6.yieldAll(r1, r3)
                if (r6 != r0) goto L4e
            L4d:
                return r0
            L4e:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt__SequencesKt.ifEmpty.1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Sequence ifEmpty(@NotNull Sequence sequence, @NotNull Function0 defaultValue) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return SequencesKt.sequence(new 1(sequence, defaultValue, null));
    }

    @NotNull
    public static final Sequence flatten(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return flatten$SequencesKt__SequencesKt(sequence, new SequencesKt__SequencesKt$$ExternalSyntheticLambda2());
    }

    private static final Iterator flatten$lambda$1$SequencesKt__SequencesKt(Sequence it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.iterator();
    }

    private static final Iterator flatten$lambda$2$SequencesKt__SequencesKt(Iterable it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.iterator();
    }

    @JvmName(name = "flattenSequenceOfIterable")
    @NotNull
    public static final Sequence flattenSequenceOfIterable(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return flatten$SequencesKt__SequencesKt(sequence, new SequencesKt__SequencesKt$$ExternalSyntheticLambda3());
    }

    private static final Sequence flatten$SequencesKt__SequencesKt(Sequence sequence, Function1 function1) {
        if (sequence instanceof TransformingSequence) {
            return ((TransformingSequence) sequence).flatten$kotlin_stdlib(function1);
        }
        return new FlatteningSequence(sequence, new SequencesKt__SequencesKt$$ExternalSyntheticLambda4(), function1);
    }

    @NotNull
    public static final Pair unzip(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    @SinceKotlin(version = "1.4")
    @NotNull
    public static final Sequence shuffled(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return SequencesKt.shuffled(sequence, Random.INSTANCE);
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$shuffled$1", f = "Sequences.kt", i = {0, 0}, l = {145}, m = "invokeSuspend", n = {"$this$sequence", "buffer"}, s = {"L$0", "L$1"})
    static final class 1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Random $random;
        final /* synthetic */ Sequence $this_shuffled;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(Sequence sequence, Random random, Continuation continuation) {
            super(2, continuation);
            this.$this_shuffled = sequence;
            this.$random = random;
        }

        public final Continuation create(Object obj, Continuation continuation) {
            1 r0 = new 1(this.$this_shuffled, this.$random, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            List mutableList;
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                mutableList = SequencesKt.toMutableList(this.$this_shuffled);
                sequenceScope = sequenceScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutableList = (List) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (!((Collection) mutableList).isEmpty()) {
                int nextInt = this.$random.nextInt(mutableList.size());
                Object removeLast = CollectionsKt.removeLast(mutableList);
                if (nextInt < mutableList.size()) {
                    removeLast = mutableList.set(nextInt, removeLast);
                }
                this.L$0 = sequenceScope;
                this.L$1 = mutableList;
                this.label = 1;
                if (sequenceScope.yield(removeLast, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    @SinceKotlin(version = "1.4")
    @NotNull
    public static final Sequence shuffled(@NotNull Sequence sequence, @NotNull Random random) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        return SequencesKt.sequence(new 1(sequence, random, null));
    }

    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "R", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {2, 1, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$flatMapIndexed$1", f = "Sequences.kt", i = {0, 0}, l = {350}, m = "invokeSuspend", n = {"$this$sequence", "index"}, s = {"L$0", "I$0"})
    static final class 1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $iterator;
        final /* synthetic */ Sequence $source;
        final /* synthetic */ Function2 $transform;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(Sequence sequence, Function2 function2, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$source = sequence;
            this.$transform = function2;
            this.$iterator = function1;
        }

        public final Continuation create(Object obj, Continuation continuation) {
            1 r0 = new 1(this.$source, this.$transform, this.$iterator, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            SequenceScope sequenceScope;
            Iterator it;
            int i;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
                it = this.$source.iterator();
                i = 0;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i3 = this.I$0;
                it = (Iterator) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                i = i3;
            }
            while (it.hasNext()) {
                Object next = it.next();
                Function2 function2 = this.$transform;
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Object invoke = function2.invoke(Boxing.boxInt(i), next);
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.I$0 = i4;
                this.label = 1;
                if (sequenceScope.yieldAll((Iterator) this.$iterator.invoke(invoke), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                i = i4;
            }
            return Unit.INSTANCE;
        }
    }

    @NotNull
    public static final Sequence flatMapIndexed(@NotNull Sequence source, @NotNull Function2 transform, @NotNull Function1 iterator) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return SequencesKt.sequence(new 1(source, transform, iterator, null));
    }

    @NotNull
    public static final Sequence constrainOnce(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return sequence instanceof ConstrainedOnceSequence ? sequence : new ConstrainedOnceSequence(sequence);
    }

    @NotNull
    public static final Sequence generateSequence(@NotNull Function0 nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return SequencesKt.constrainOnce(new GeneratorSequence(nextFunction, new SequencesKt__SequencesKt$$ExternalSyntheticLambda1(nextFunction)));
    }

    private static final Object generateSequence$lambda$4$SequencesKt__SequencesKt(Function0 function0, Object it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return function0.invoke();
    }

    @LowPriorityInOverloadResolution
    @NotNull
    public static final Sequence generateSequence(@Nullable Object obj, @NotNull Function1 nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        if (obj == null) {
            return EmptySequence.INSTANCE;
        }
        return new GeneratorSequence(new SequencesKt__SequencesKt$$ExternalSyntheticLambda0(obj), nextFunction);
    }

    @NotNull
    public static final Sequence generateSequence(@NotNull Function0 seedFunction, @NotNull Function1 nextFunction) {
        Intrinsics.checkNotNullParameter(seedFunction, "seedFunction");
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return new GeneratorSequence(seedFunction, nextFunction);
    }
}
