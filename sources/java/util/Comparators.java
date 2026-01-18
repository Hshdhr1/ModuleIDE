package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class Comparators {
    private Comparators() {
        throw new AssertionError("no instances");
    }

    enum NaturalOrderComparator implements Comparator {
        INSTANCE;

        public /* synthetic */ Comparator thenComparing(Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, comparator);
        }

        public /* synthetic */ Comparator thenComparing(Function function) {
            return Comparator.-CC.$default$thenComparing(this, function);
        }

        public /* synthetic */ Comparator thenComparing(Function function, Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, function, comparator);
        }

        public /* synthetic */ Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
            return Comparator.-CC.$default$thenComparingDouble(this, toDoubleFunction);
        }

        public /* synthetic */ Comparator thenComparingInt(ToIntFunction toIntFunction) {
            return Comparator.-CC.$default$thenComparingInt(this, toIntFunction);
        }

        public /* synthetic */ Comparator thenComparingLong(ToLongFunction toLongFunction) {
            return Comparator.-CC.$default$thenComparingLong(this, toLongFunction);
        }

        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }

        public Comparator reversed() {
            return Comparator.-CC.reverseOrder();
        }
    }

    static final class NullComparator implements Comparator, Serializable {
        private static final long serialVersionUID = -7569533591570686392L;
        private final boolean nullFirst;
        private final Comparator real;

        public /* synthetic */ Comparator thenComparing(Function function) {
            return Comparator.-CC.$default$thenComparing(this, function);
        }

        public /* synthetic */ Comparator thenComparing(Function function, Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, function, comparator);
        }

        public /* synthetic */ Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
            return Comparator.-CC.$default$thenComparingDouble(this, toDoubleFunction);
        }

        public /* synthetic */ Comparator thenComparingInt(ToIntFunction toIntFunction) {
            return Comparator.-CC.$default$thenComparingInt(this, toIntFunction);
        }

        public /* synthetic */ Comparator thenComparingLong(ToLongFunction toLongFunction) {
            return Comparator.-CC.$default$thenComparingLong(this, toLongFunction);
        }

        NullComparator(boolean z, Comparator comparator) {
            this.nullFirst = z;
            this.real = comparator;
        }

        public int compare(Object obj, Object obj2) {
            if (obj == null) {
                if (obj2 == null) {
                    return 0;
                }
                return this.nullFirst ? -1 : 1;
            }
            if (obj2 == null) {
                return this.nullFirst ? 1 : -1;
            }
            Comparator comparator = this.real;
            if (comparator == null) {
                return 0;
            }
            return comparator.compare(obj, obj2);
        }

        public Comparator thenComparing(Comparator comparator) {
            comparator.getClass();
            boolean z = this.nullFirst;
            Comparator comparator2 = this.real;
            if (comparator2 != null) {
                comparator = comparator2.thenComparing(comparator);
            }
            return new NullComparator(z, comparator);
        }

        public Comparator reversed() {
            boolean z = !this.nullFirst;
            Comparator comparator = this.real;
            return new NullComparator(z, comparator == null ? null : comparator.reversed());
        }
    }
}
