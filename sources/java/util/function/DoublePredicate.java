package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DoublePredicate {
    DoublePredicate and(DoublePredicate doublePredicate);

    DoublePredicate negate();

    DoublePredicate or(DoublePredicate doublePredicate);

    boolean test(double d);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static DoublePredicate $default$and(DoublePredicate _this, DoublePredicate doublePredicate) {
            doublePredicate.getClass();
            return new DoublePredicate$$ExternalSyntheticLambda2(_this, doublePredicate);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(DoublePredicate _this, DoublePredicate doublePredicate, double d) {
            return _this.test(d) && doublePredicate.test(d);
        }

        public static DoublePredicate $default$negate(DoublePredicate _this) {
            return new DoublePredicate$$ExternalSyntheticLambda0(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(DoublePredicate _this, double d) {
            return !_this.test(d);
        }

        public static DoublePredicate $default$or(DoublePredicate _this, DoublePredicate doublePredicate) {
            doublePredicate.getClass();
            return new DoublePredicate$$ExternalSyntheticLambda1(_this, doublePredicate);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(DoublePredicate _this, DoublePredicate doublePredicate, double d) {
            return _this.test(d) || doublePredicate.test(d);
        }
    }
}
