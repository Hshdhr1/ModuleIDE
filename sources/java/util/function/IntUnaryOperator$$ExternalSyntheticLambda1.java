package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.IntUnaryOperator;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class IntUnaryOperator$$ExternalSyntheticLambda1 implements IntUnaryOperator {
    public final /* synthetic */ IntUnaryOperator f$0;
    public final /* synthetic */ IntUnaryOperator f$1;

    public /* synthetic */ IntUnaryOperator$$ExternalSyntheticLambda1(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        this.f$0 = intUnaryOperator;
        this.f$1 = intUnaryOperator2;
    }

    public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        return IntUnaryOperator.-CC.$default$andThen(this, intUnaryOperator);
    }

    public final int applyAsInt(int i) {
        return IntUnaryOperator.-CC.$private$lambda$compose$0(this.f$0, this.f$1, i);
    }

    public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        return IntUnaryOperator.-CC.$default$compose(this, intUnaryOperator);
    }
}
