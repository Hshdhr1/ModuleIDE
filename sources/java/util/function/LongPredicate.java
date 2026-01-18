package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface LongPredicate {
    LongPredicate and(LongPredicate longPredicate);

    LongPredicate negate();

    LongPredicate or(LongPredicate longPredicate);

    boolean test(long j);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static LongPredicate $default$and(LongPredicate _this, LongPredicate longPredicate) {
            longPredicate.getClass();
            return new LongPredicate$$ExternalSyntheticLambda1(_this, longPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$and$0(LongPredicate _this, LongPredicate longPredicate, long j) {
            return _this.test(j) && longPredicate.test(j);
        }

        public static LongPredicate $default$negate(LongPredicate _this) {
            return new LongPredicate$$ExternalSyntheticLambda2(_this);
        }

        public static /* synthetic */ boolean $private$lambda$negate$1(LongPredicate _this, long j) {
            return !_this.test(j);
        }

        public static LongPredicate $default$or(LongPredicate _this, LongPredicate longPredicate) {
            longPredicate.getClass();
            return new LongPredicate$$ExternalSyntheticLambda0(_this, longPredicate);
        }

        public static /* synthetic */ boolean $private$lambda$or$2(LongPredicate _this, LongPredicate longPredicate, long j) {
            return _this.test(j) || longPredicate.test(j);
        }
    }
}
