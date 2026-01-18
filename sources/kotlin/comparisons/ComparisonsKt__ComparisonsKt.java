package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Comparisons.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0007\u001aY\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u000226\u0010\u0005\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u00070\u0006\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007¢\u0006\u0002\u0010\t\u001aG\u0010\n\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u00022 \u0010\u0005\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u00070\u0006H\u0002¢\u0006\u0004\b\u000b\u0010\t\u001aA\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u00022\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\r\u001a]\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e2\u0006\u0010\u0003\u001a\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u00022\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`\u00112\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000e0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a-\u0010\u0013\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\b2\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0004\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0014\u001aY\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u000226\u0010\u0005\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u00070\u0006\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007¢\u0006\u0002\u0010\u0016\u001aC\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a_\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e2\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`\u00112\u0014\b\u0004\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000e0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001aC\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a_\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e2\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`\u00112\u0014\b\u0004\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000e0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001aW\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\b\u0004\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001as\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`\u00112\u0014\b\u0004\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000e0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001b\u001aW\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\b\u0004\u0010\f\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001as\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000e*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`\u00112\u0014\b\u0004\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000e0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001b\u001au\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u001128\b\u0004\u0010\u001e\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b \u0012\b\b!\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b \u0012\b\b!\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u00020\u00010\u001fH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\"\u001aT\u0010#\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0011H\u0086\u0004¢\u0006\u0002\u0010$\u001aT\u0010%\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u00112\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0011H\u0086\u0004¢\u0006\u0002\u0010$\u001aE\u0010&\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0010j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0011\"\b\b\u0000\u0010\u0002*\u00020'2\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0011¢\u0006\u0002\u0010(\u001a2\u0010&\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0010j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0011\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\b¢\u0006\u0002\u0010)\u001aE\u0010*\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0010j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0011\"\b\b\u0000\u0010\u0002*\u00020'2\u001a\u0010\u000f\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0010j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0011¢\u0006\u0002\u0010(\u001a2\u0010*\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0010j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0011\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\b¢\u0006\u0002\u0010)\u001a+\u0010+\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b¢\u0006\u0002\u0010)\u001a+\u0010,\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b¢\u0006\u0002\u0010)\u001a5\u0010-\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0010j\b\u0012\u0004\u0012\u0002H\u0002`\u0011¢\u0006\u0002\u0010(\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006."}, d2 = {"compareValuesBy", "", "T", "a", "b", "selectors", "", "Lkotlin/Function1;", "", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "selector", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "K", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValues", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareBy", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "(Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "(Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "compareByDescending", "thenBy", "(Ljava/util/Comparator;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "(Ljava/util/Comparator;Lkotlin/jvm/functions/Function2;)Ljava/util/Comparator;", "then", "(Ljava/util/Comparator;Ljava/util/Comparator;)Ljava/util/Comparator;", "thenDescending", "nullsFirst", "", "(Ljava/util/Comparator;)Ljava/util/Comparator;", "()Ljava/util/Comparator;", "nullsLast", "naturalOrder", "reverseOrder", "reversed", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/comparisons/ComparisonsKt")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class ComparisonsKt__ComparisonsKt {
    public static /* synthetic */ int $r8$lambda$75RqBHjEE2iU7zbZ3cBiUB4M5DE(Comparator comparator, Object obj, Object obj2) {
        return nullsLast$lambda$4$ComparisonsKt__ComparisonsKt(comparator, obj, obj2);
    }

    public static /* synthetic */ int $r8$lambda$OB0mUMQVuAc-U0wu9PhfvNNbqqs(Comparator comparator, Comparator comparator2, Object obj, Object obj2) {
        return thenDescending$lambda$2$ComparisonsKt__ComparisonsKt(comparator, comparator2, obj, obj2);
    }

    public static /* synthetic */ int $r8$lambda$W8AWSBCuy65sMGWfMo6pJ3RDjRw(Function1[] function1Arr, Object obj, Object obj2) {
        return compareBy$lambda$0$ComparisonsKt__ComparisonsKt(function1Arr, obj, obj2);
    }

    public static /* synthetic */ int $r8$lambda$fFv4wzBXuBoGFl05zSxqQb7pKRU(Comparator comparator, Comparator comparator2, Object obj, Object obj2) {
        return then$lambda$1$ComparisonsKt__ComparisonsKt(comparator, comparator2, obj, obj2);
    }

    public static /* synthetic */ int $r8$lambda$tgO5p6pL1ym8xxzpZUrxHlIPkYM(Comparator comparator, Object obj, Object obj2) {
        return nullsFirst$lambda$3$ComparisonsKt__ComparisonsKt(comparator, obj, obj2);
    }

    public static final int compareValuesBy(Object obj, Object obj2, @NotNull Function1... selectors) {
        Intrinsics.checkNotNullParameter(selectors, "selectors");
        if (selectors.length <= 0) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return compareValuesByImpl$ComparisonsKt__ComparisonsKt(obj, obj2, selectors);
    }

    private static final int compareValuesByImpl$ComparisonsKt__ComparisonsKt(Object obj, Object obj2, Function1[] function1Arr) {
        for (Function1 function1 : function1Arr) {
            int compareValues = ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
            if (compareValues != 0) {
                return compareValues;
            }
        }
        return 0;
    }

    @InlineOnly
    private static final int compareValuesBy(Object obj, Object obj2, Function1 selector) {
        Intrinsics.checkNotNullParameter(selector, "selector");
        return ComparisonsKt.compareValues((Comparable) selector.invoke(obj), (Comparable) selector.invoke(obj2));
    }

    @InlineOnly
    private static final int compareValuesBy(Object obj, Object obj2, Comparator comparator, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return comparator.compare(selector.invoke(obj), selector.invoke(obj2));
    }

    public static final int compareValues(@Nullable Comparable comparable, @Nullable Comparable comparable2) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return -1;
        }
        if (comparable2 == null) {
            return 1;
        }
        return comparable.compareTo(comparable2);
    }

    @NotNull
    public static final Comparator compareBy(@NotNull Function1... selectors) {
        Intrinsics.checkNotNullParameter(selectors, "selectors");
        if (selectors.length <= 0) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda3(selectors);
    }

    private static final int compareBy$lambda$0$ComparisonsKt__ComparisonsKt(Function1[] function1Arr, Object obj, Object obj2) {
        return compareValuesByImpl$ComparisonsKt__ComparisonsKt(obj, obj2, function1Arr);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 2 implements Comparator {
        final /* synthetic */ Function1 $selector;

        public 2(Function1 function1) {
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            Function1 function1 = this.$selector;
            return ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
        }
    }

    @InlineOnly
    private static final Comparator compareBy(Function1 selector) {
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 2(selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 3 implements Comparator {
        final /* synthetic */ Comparator $comparator;
        final /* synthetic */ Function1 $selector;

        public 3(Comparator comparator, Function1 function1) {
            this.$comparator = comparator;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            Comparator comparator = this.$comparator;
            Function1 function1 = this.$selector;
            return comparator.compare(function1.invoke(obj), function1.invoke(obj2));
        }
    }

    @InlineOnly
    private static final Comparator compareBy(Comparator comparator, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 3(comparator, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Comparator {
        final /* synthetic */ Function1 $selector;

        public 1(Function1 function1) {
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            Function1 function1 = this.$selector;
            return ComparisonsKt.compareValues((Comparable) function1.invoke(obj2), (Comparable) function1.invoke(obj));
        }
    }

    @InlineOnly
    private static final Comparator compareByDescending(Function1 selector) {
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 1(selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 2 implements Comparator {
        final /* synthetic */ Comparator $comparator;
        final /* synthetic */ Function1 $selector;

        public 2(Comparator comparator, Function1 function1) {
            this.$comparator = comparator;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            Comparator comparator = this.$comparator;
            Function1 function1 = this.$selector;
            return comparator.compare(function1.invoke(obj2), function1.invoke(obj));
        }
    }

    @InlineOnly
    private static final Comparator compareByDescending(Comparator comparator, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 2(comparator, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Comparator {
        final /* synthetic */ Function1 $selector;
        final /* synthetic */ Comparator $this_thenBy;

        public 1(Comparator comparator, Function1 function1) {
            this.$this_thenBy = comparator;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            int compare = this.$this_thenBy.compare(obj, obj2);
            if (compare != 0) {
                return compare;
            }
            Function1 function1 = this.$selector;
            return ComparisonsKt.compareValues((Comparable) function1.invoke(obj), (Comparable) function1.invoke(obj2));
        }
    }

    @InlineOnly
    private static final Comparator thenBy(Comparator comparator, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 1(comparator, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 2 implements Comparator {
        final /* synthetic */ Comparator $comparator;
        final /* synthetic */ Function1 $selector;
        final /* synthetic */ Comparator $this_thenBy;

        public 2(Comparator comparator, Comparator comparator2, Function1 function1) {
            this.$this_thenBy = comparator;
            this.$comparator = comparator2;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            int compare = this.$this_thenBy.compare(obj, obj2);
            if (compare != 0) {
                return compare;
            }
            Comparator comparator = this.$comparator;
            Function1 function1 = this.$selector;
            return comparator.compare(function1.invoke(obj), function1.invoke(obj2));
        }
    }

    @InlineOnly
    private static final Comparator thenBy(Comparator comparator, Comparator comparator2, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(comparator2, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 2(comparator, comparator2, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Comparator {
        final /* synthetic */ Function1 $selector;
        final /* synthetic */ Comparator $this_thenByDescending;

        public 1(Comparator comparator, Function1 function1) {
            this.$this_thenByDescending = comparator;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            int compare = this.$this_thenByDescending.compare(obj, obj2);
            if (compare != 0) {
                return compare;
            }
            Function1 function1 = this.$selector;
            return ComparisonsKt.compareValues((Comparable) function1.invoke(obj2), (Comparable) function1.invoke(obj));
        }
    }

    @InlineOnly
    private static final Comparator thenByDescending(Comparator comparator, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 1(comparator, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 2 implements Comparator {
        final /* synthetic */ Comparator $comparator;
        final /* synthetic */ Function1 $selector;
        final /* synthetic */ Comparator $this_thenByDescending;

        public 2(Comparator comparator, Comparator comparator2, Function1 function1) {
            this.$this_thenByDescending = comparator;
            this.$comparator = comparator2;
            this.$selector = function1;
        }

        public final int compare(Object obj, Object obj2) {
            int compare = this.$this_thenByDescending.compare(obj, obj2);
            if (compare != 0) {
                return compare;
            }
            Comparator comparator = this.$comparator;
            Function1 function1 = this.$selector;
            return comparator.compare(function1.invoke(obj2), function1.invoke(obj));
        }
    }

    @InlineOnly
    private static final Comparator thenByDescending(Comparator comparator, Comparator comparator2, Function1 selector) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(comparator2, "comparator");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return new 2(comparator, comparator2, selector);
    }

    /* compiled from: Comparisons.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Comparator {
        final /* synthetic */ Function2 $comparison;
        final /* synthetic */ Comparator $this_thenComparator;

        public 1(Comparator comparator, Function2 function2) {
            this.$this_thenComparator = comparator;
            this.$comparison = function2;
        }

        public final int compare(Object obj, Object obj2) {
            int compare = this.$this_thenComparator.compare(obj, obj2);
            return compare != 0 ? compare : ((Number) this.$comparison.invoke(obj, obj2)).intValue();
        }
    }

    @InlineOnly
    private static final Comparator thenComparator(Comparator comparator, Function2 comparison) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(comparison, "comparison");
        return new 1(comparator, comparison);
    }

    @NotNull
    public static final Comparator then(@NotNull Comparator comparator, @NotNull Comparator comparator2) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(comparator2, "comparator");
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda2(comparator, comparator2);
    }

    private static final int then$lambda$1$ComparisonsKt__ComparisonsKt(Comparator comparator, Comparator comparator2, Object obj, Object obj2) {
        int compare = comparator.compare(obj, obj2);
        return compare != 0 ? compare : comparator2.compare(obj, obj2);
    }

    @NotNull
    public static final Comparator thenDescending(@NotNull Comparator comparator, @NotNull Comparator comparator2) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        Intrinsics.checkNotNullParameter(comparator2, "comparator");
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1(comparator, comparator2);
    }

    private static final int thenDescending$lambda$2$ComparisonsKt__ComparisonsKt(Comparator comparator, Comparator comparator2, Object obj, Object obj2) {
        int compare = comparator.compare(obj, obj2);
        return compare != 0 ? compare : comparator2.compare(obj2, obj);
    }

    @NotNull
    public static final Comparator nullsFirst(@NotNull Comparator comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda4(comparator);
    }

    private static final int nullsFirst$lambda$3$ComparisonsKt__ComparisonsKt(Comparator comparator, Object obj, Object obj2) {
        if (obj == obj2) {
            return 0;
        }
        if (obj == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return comparator.compare(obj, obj2);
    }

    @InlineOnly
    private static final Comparator nullsFirst() {
        return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
    }

    @NotNull
    public static final Comparator nullsLast(@NotNull Comparator comparator) {
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(comparator);
    }

    private static final int nullsLast$lambda$4$ComparisonsKt__ComparisonsKt(Comparator comparator, Object obj, Object obj2) {
        if (obj == obj2) {
            return 0;
        }
        if (obj == null) {
            return 1;
        }
        if (obj2 == null) {
            return -1;
        }
        return comparator.compare(obj, obj2);
    }

    @InlineOnly
    private static final Comparator nullsLast() {
        return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
    }

    @NotNull
    public static final Comparator naturalOrder() {
        NaturalOrderComparator naturalOrderComparator = NaturalOrderComparator.INSTANCE;
        Intrinsics.checkNotNull(naturalOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.naturalOrder>");
        return naturalOrderComparator;
    }

    @NotNull
    public static final Comparator reverseOrder() {
        ReverseOrderComparator reverseOrderComparator = ReverseOrderComparator.INSTANCE;
        Intrinsics.checkNotNull(reverseOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reverseOrder>");
        return reverseOrderComparator;
    }

    @NotNull
    public static final Comparator reversed(@NotNull Comparator comparator) {
        Intrinsics.checkNotNullParameter(comparator, "<this>");
        if (comparator instanceof ReversedComparator) {
            return ((ReversedComparator) comparator).getComparator();
        }
        if (Intrinsics.areEqual(comparator, NaturalOrderComparator.INSTANCE)) {
            ReverseOrderComparator reverseOrderComparator = ReverseOrderComparator.INSTANCE;
            Intrinsics.checkNotNull(reverseOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed>");
            return reverseOrderComparator;
        }
        if (!Intrinsics.areEqual(comparator, ReverseOrderComparator.INSTANCE)) {
            return new ReversedComparator(comparator);
        }
        NaturalOrderComparator naturalOrderComparator = NaturalOrderComparator.INSTANCE;
        Intrinsics.checkNotNull(naturalOrderComparator, "null cannot be cast to non-null type java.util.Comparator<T of kotlin.comparisons.ComparisonsKt__ComparisonsKt.reversed>");
        return naturalOrderComparator;
    }
}
