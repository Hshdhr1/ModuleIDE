package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.DoubleConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DoubleConsumer$$ExternalSyntheticLambda0 implements DoubleConsumer {
    public final /* synthetic */ DoubleConsumer f$0;
    public final /* synthetic */ DoubleConsumer f$1;

    public /* synthetic */ DoubleConsumer$$ExternalSyntheticLambda0(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        this.f$0 = doubleConsumer;
        this.f$1 = doubleConsumer2;
    }

    public final void accept(double d) {
        DoubleConsumer.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, d);
    }

    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
    }
}
