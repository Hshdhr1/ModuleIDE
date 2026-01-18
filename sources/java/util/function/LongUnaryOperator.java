package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface LongUnaryOperator {
    LongUnaryOperator andThen(LongUnaryOperator longUnaryOperator);

    long applyAsLong(long j);

    LongUnaryOperator compose(LongUnaryOperator longUnaryOperator);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* synthetic */ long lambda$identity$2(long j) {
            return j;
        }

        public static LongUnaryOperator $default$compose(LongUnaryOperator _this, LongUnaryOperator longUnaryOperator) {
            longUnaryOperator.getClass();
            return new LongUnaryOperator$$ExternalSyntheticLambda2(_this, longUnaryOperator);
        }

        public static /* synthetic */ long $private$lambda$compose$0(LongUnaryOperator _this, LongUnaryOperator longUnaryOperator, long j) {
            return _this.applyAsLong(longUnaryOperator.applyAsLong(j));
        }

        public static LongUnaryOperator $default$andThen(LongUnaryOperator _this, LongUnaryOperator longUnaryOperator) {
            longUnaryOperator.getClass();
            return new LongUnaryOperator$$ExternalSyntheticLambda1(_this, longUnaryOperator);
        }

        public static /* synthetic */ long $private$lambda$andThen$1(LongUnaryOperator _this, LongUnaryOperator longUnaryOperator, long j) {
            return longUnaryOperator.applyAsLong(_this.applyAsLong(j));
        }

        public static LongUnaryOperator identity() {
            return new LongUnaryOperator$$ExternalSyntheticLambda0();
        }
    }
}
