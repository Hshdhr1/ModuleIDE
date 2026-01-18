package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Comparator;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BinaryOperator extends BiFunction {

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static BinaryOperator minBy(Comparator comparator) {
            comparator.getClass();
            return new BinaryOperator$$ExternalSyntheticLambda1(comparator);
        }

        public static /* synthetic */ Object lambda$minBy$0(Comparator comparator, Object obj, Object obj2) {
            return comparator.compare(obj, obj2) <= 0 ? obj : obj2;
        }

        public static BinaryOperator maxBy(Comparator comparator) {
            comparator.getClass();
            return new BinaryOperator$$ExternalSyntheticLambda0(comparator);
        }

        public static /* synthetic */ Object lambda$maxBy$1(Comparator comparator, Object obj, Object obj2) {
            return comparator.compare(obj, obj2) >= 0 ? obj : obj2;
        }
    }
}
