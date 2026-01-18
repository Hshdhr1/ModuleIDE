package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;

@FunctionalInterface
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DoubleUnaryOperator {
    DoubleUnaryOperator andThen(DoubleUnaryOperator doubleUnaryOperator);

    double applyAsDouble(double d);

    DoubleUnaryOperator compose(DoubleUnaryOperator doubleUnaryOperator);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* synthetic */ double lambda$identity$2(double d) {
            return d;
        }

        public static DoubleUnaryOperator $default$compose(DoubleUnaryOperator _this, DoubleUnaryOperator doubleUnaryOperator) {
            doubleUnaryOperator.getClass();
            return new DoubleUnaryOperator$$ExternalSyntheticLambda0(_this, doubleUnaryOperator);
        }

        public static /* synthetic */ double $private$lambda$compose$0(DoubleUnaryOperator _this, DoubleUnaryOperator doubleUnaryOperator, double d) {
            return _this.applyAsDouble(doubleUnaryOperator.applyAsDouble(d));
        }

        public static DoubleUnaryOperator $default$andThen(DoubleUnaryOperator _this, DoubleUnaryOperator doubleUnaryOperator) {
            doubleUnaryOperator.getClass();
            return new DoubleUnaryOperator$$ExternalSyntheticLambda2(_this, doubleUnaryOperator);
        }

        public static /* synthetic */ double $private$lambda$andThen$1(DoubleUnaryOperator _this, DoubleUnaryOperator doubleUnaryOperator, double d) {
            return doubleUnaryOperator.applyAsDouble(_this.applyAsDouble(d));
        }

        public static DoubleUnaryOperator identity() {
            return new DoubleUnaryOperator$$ExternalSyntheticLambda1();
        }
    }
}
