package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.LongUnaryOperator;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class LongUnaryOperator$$ExternalSyntheticLambda2 implements LongUnaryOperator {
    public final /* synthetic */ LongUnaryOperator f$0;
    public final /* synthetic */ LongUnaryOperator f$1;

    public /* synthetic */ LongUnaryOperator$$ExternalSyntheticLambda2(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        this.f$0 = longUnaryOperator;
        this.f$1 = longUnaryOperator2;
    }

    public /* synthetic */ LongUnaryOperator andThen(LongUnaryOperator longUnaryOperator) {
        return LongUnaryOperator.-CC.$default$andThen(this, longUnaryOperator);
    }

    public final long applyAsLong(long j) {
        return LongUnaryOperator.-CC.$private$lambda$compose$0(this.f$0, this.f$1, j);
    }

    public /* synthetic */ LongUnaryOperator compose(LongUnaryOperator longUnaryOperator) {
        return LongUnaryOperator.-CC.$default$compose(this, longUnaryOperator);
    }
}
