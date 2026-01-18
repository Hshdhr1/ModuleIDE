package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BiPredicate {
    BiPredicate and(BiPredicate biPredicate);

    BiPredicate negate();

    BiPredicate or(BiPredicate biPredicate);

    boolean test(Object obj, Object obj2);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static BiPredicate $default$and(BiPredicate _this, BiPredicate biPredicate) {
            biPredicate.getClass();
            return new BiPredicate$$ExternalSyntheticLambda2(_this, biPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(BiPredicate _this, BiPredicate biPredicate, Object obj, Object obj2) {
            return _this.test(obj, obj2) && biPredicate.test(obj, obj2);
        }

        public static BiPredicate $default$negate(BiPredicate _this) {
            return new BiPredicate$$ExternalSyntheticLambda0(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(BiPredicate _this, Object obj, Object obj2) {
            return !_this.test(obj, obj2);
        }

        public static BiPredicate $default$or(BiPredicate _this, BiPredicate biPredicate) {
            biPredicate.getClass();
            return new BiPredicate$$ExternalSyntheticLambda1(_this, biPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(BiPredicate _this, BiPredicate biPredicate, Object obj, Object obj2) {
            return _this.test(obj, obj2) || biPredicate.test(obj, obj2);
        }
    }
}
