package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface IntPredicate {
    IntPredicate and(IntPredicate intPredicate);

    IntPredicate negate();

    IntPredicate or(IntPredicate intPredicate);

    boolean test(int i);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static IntPredicate $default$and(IntPredicate _this, IntPredicate intPredicate) {
            intPredicate.getClass();
            return new IntPredicate$$ExternalSyntheticLambda2(_this, intPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(IntPredicate _this, IntPredicate intPredicate, int i) {
            return _this.test(i) && intPredicate.test(i);
        }

        public static IntPredicate $default$negate(IntPredicate _this) {
            return new IntPredicate$$ExternalSyntheticLambda1(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(IntPredicate _this, int i) {
            return !_this.test(i);
        }

        public static IntPredicate $default$or(IntPredicate _this, IntPredicate intPredicate) {
            intPredicate.getClass();
            return new IntPredicate$$ExternalSyntheticLambda0(_this, intPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(IntPredicate _this, IntPredicate intPredicate, int i) {
            return _this.test(i) || intPredicate.test(i);
        }
    }
}
