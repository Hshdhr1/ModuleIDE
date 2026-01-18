package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.DoubleUnaryOperator;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DoubleUnaryOperator$$ExternalSyntheticLambda2 implements DoubleUnaryOperator {
    public final /* synthetic */ DoubleUnaryOperator f$0;
    public final /* synthetic */ DoubleUnaryOperator f$1;

    public /* synthetic */ DoubleUnaryOperator$$ExternalSyntheticLambda2(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        this.f$0 = doubleUnaryOperator;
        this.f$1 = doubleUnaryOperator2;
    }

    public /* synthetic */ DoubleUnaryOperator andThen(DoubleUnaryOperator doubleUnaryOperator) {
        return DoubleUnaryOperator.-CC.$default$andThen(this, doubleUnaryOperator);
    }

    public final double applyAsDouble(double d) {
        return DoubleUnaryOperator.-CC.$private$lambda$andThen$1(this.f$0, this.f$1, d);
    }

    public /* synthetic */ DoubleUnaryOperator compose(DoubleUnaryOperator doubleUnaryOperator) {
        return DoubleUnaryOperator.-CC.$default$compose(this, doubleUnaryOperator);
    }
}
