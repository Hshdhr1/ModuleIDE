package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.IntConsumer;
import java.util.function.IntToDoubleFunction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarArrays$$ExternalSyntheticLambda1 implements IntConsumer {
    public final /* synthetic */ double[] f$0;
    public final /* synthetic */ IntToDoubleFunction f$1;

    public /* synthetic */ DesugarArrays$$ExternalSyntheticLambda1(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        this.f$0 = dArr;
        this.f$1 = intToDoubleFunction;
    }

    public final void accept(int i) {
        DesugarArrays.lambda$parallelSetAll$3(this.f$0, this.f$1, i);
    }

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer.-CC.$default$andThen(this, intConsumer);
    }
}
