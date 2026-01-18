package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface IntUnaryOperator {
    IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator);

    int applyAsInt(int i);

    IntUnaryOperator compose(IntUnaryOperator intUnaryOperator);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* synthetic */ int lambda$identity$2(int i) {
            return i;
        }

        public static IntUnaryOperator $default$compose(IntUnaryOperator _this, IntUnaryOperator intUnaryOperator) {
            intUnaryOperator.getClass();
            return new IntUnaryOperator$$ExternalSyntheticLambda1(_this, intUnaryOperator);
        }

        public static /* synthetic */ int $private$lambda$compose$0(IntUnaryOperator _this, IntUnaryOperator intUnaryOperator, int i) {
            return _this.applyAsInt(intUnaryOperator.applyAsInt(i));
        }

        public static IntUnaryOperator $default$andThen(IntUnaryOperator _this, IntUnaryOperator intUnaryOperator) {
            intUnaryOperator.getClass();
            return new IntUnaryOperator$$ExternalSyntheticLambda0(_this, intUnaryOperator);
        }

        public static /* synthetic */ int $private$lambda$andThen$1(IntUnaryOperator _this, IntUnaryOperator intUnaryOperator, int i) {
            return intUnaryOperator.applyAsInt(_this.applyAsInt(i));
        }

        public static IntUnaryOperator identity() {
            return new IntUnaryOperator$$ExternalSyntheticLambda2();
        }
    }
}
