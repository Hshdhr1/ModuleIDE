package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Comparators;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Comparator {
    int compare(Object obj, Object obj2);

    boolean equals(Object obj);

    Comparator reversed();

    Comparator thenComparing(Comparator comparator);

    Comparator thenComparing(Function function);

    Comparator thenComparing(Function function, Comparator comparator);

    Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction);

    Comparator thenComparingInt(ToIntFunction toIntFunction);

    Comparator thenComparingLong(ToLongFunction toLongFunction);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Comparator $default$reversed(Comparator _this) {
            return Collections.reverseOrder(_this);
        }

        public static Comparator $default$thenComparing(Comparator _this, Comparator comparator) {
            comparator.getClass();
            return new Comparator$$ExternalSyntheticLambda2(_this, comparator);
        }

        public static /* synthetic */ int $private$lambda$thenComparing$36697e65$1(Comparator _this, Comparator comparator, Object obj, Object obj2) {
            int compare = _this.compare(obj, obj2);
            return compare != 0 ? compare : comparator.compare(obj, obj2);
        }

        public static Comparator $default$thenComparing(Comparator _this, Function function, Comparator comparator) {
            return _this.thenComparing(comparing(function, comparator));
        }

        public static Comparator $default$thenComparing(Comparator _this, Function function) {
            return _this.thenComparing(comparing(function));
        }

        public static Comparator $default$thenComparingInt(Comparator _this, ToIntFunction toIntFunction) {
            return _this.thenComparing(comparingInt(toIntFunction));
        }

        public static Comparator $default$thenComparingLong(Comparator _this, ToLongFunction toLongFunction) {
            return _this.thenComparing(comparingLong(toLongFunction));
        }

        public static Comparator $default$thenComparingDouble(Comparator _this, ToDoubleFunction toDoubleFunction) {
            return _this.thenComparing(comparingDouble(toDoubleFunction));
        }

        public static Comparator reverseOrder() {
            return Collections.reverseOrder();
        }

        public static Comparator naturalOrder() {
            return Comparators.NaturalOrderComparator.INSTANCE;
        }

        public static Comparator nullsFirst(Comparator comparator) {
            return new Comparators.NullComparator(true, comparator);
        }

        public static Comparator nullsLast(Comparator comparator) {
            return new Comparators.NullComparator(false, comparator);
        }

        public static Comparator comparing(Function function, Comparator comparator) {
            function.getClass();
            comparator.getClass();
            return new Comparator$$ExternalSyntheticLambda6(comparator, function);
        }

        public static /* synthetic */ int lambda$comparing$ea9a8b3a$1(Comparator comparator, Function function, Object obj, Object obj2) {
            return comparator.compare(function.apply(obj), function.apply(obj2));
        }

        public static Comparator comparing(Function function) {
            function.getClass();
            return new Comparator$$ExternalSyntheticLambda4(function);
        }

        public static /* synthetic */ int lambda$comparing$77a9974f$1(Function function, Object obj, Object obj2) {
            return ((Comparable) function.apply(obj)).compareTo(function.apply(obj2));
        }

        public static Comparator comparingInt(ToIntFunction toIntFunction) {
            toIntFunction.getClass();
            return new Comparator$$ExternalSyntheticLambda1(toIntFunction);
        }

        public static /* synthetic */ int lambda$comparingInt$7b0bb60$1(ToIntFunction toIntFunction, Object obj, Object obj2) {
            return Comparator$$ExternalSyntheticBackport0.m(toIntFunction.applyAsInt(obj), toIntFunction.applyAsInt(obj2));
        }

        public static Comparator comparingLong(ToLongFunction toLongFunction) {
            toLongFunction.getClass();
            return new Comparator$$ExternalSyntheticLambda5(toLongFunction);
        }

        public static /* synthetic */ int lambda$comparingLong$6043328a$1(ToLongFunction toLongFunction, Object obj, Object obj2) {
            return (toLongFunction.applyAsLong(obj) > toLongFunction.applyAsLong(obj2) ? 1 : (toLongFunction.applyAsLong(obj) == toLongFunction.applyAsLong(obj2) ? 0 : -1));
        }

        public static Comparator comparingDouble(ToDoubleFunction toDoubleFunction) {
            toDoubleFunction.getClass();
            return new Comparator$$ExternalSyntheticLambda3(toDoubleFunction);
        }

        public static /* synthetic */ int lambda$comparingDouble$8dcf42ea$1(ToDoubleFunction toDoubleFunction, Object obj, Object obj2) {
            return Double.compare(toDoubleFunction.applyAsDouble(obj), toDoubleFunction.applyAsDouble(obj2));
        }
    }
}
