package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Predicate {
    Predicate and(Predicate predicate);

    Predicate negate();

    Predicate or(Predicate predicate);

    boolean test(Object obj);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Predicate $default$and(Predicate _this, Predicate predicate) {
            predicate.getClass();
            return new Predicate$$ExternalSyntheticLambda1(_this, predicate);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(Predicate _this, Predicate predicate, Object obj) {
            return _this.test(obj) && predicate.test(obj);
        }

        public static Predicate $default$negate(Predicate _this) {
            return new Predicate$$ExternalSyntheticLambda2(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(Predicate _this, Object obj) {
            return !_this.test(obj);
        }

        public static Predicate $default$or(Predicate _this, Predicate predicate) {
            predicate.getClass();
            return new Predicate$$ExternalSyntheticLambda3(_this, predicate);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(Predicate _this, Predicate predicate, Object obj) {
            return _this.test(obj) || predicate.test(obj);
        }

        public static Predicate isEqual(Object obj) {
            return obj == null ? new Predicate$$ExternalSyntheticLambda4() : new Predicate$$ExternalSyntheticLambda5(obj);
        }

        public static /* synthetic */ boolean lambda$isEqual$3(Object obj, Object obj2) {
            return obj.equals(obj2);
        }

        public static Predicate not(Predicate predicate) {
            predicate.getClass();
            return predicate.negate();
        }
    }
}
