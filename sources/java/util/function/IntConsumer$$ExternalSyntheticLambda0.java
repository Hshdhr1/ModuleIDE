package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.IntConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class IntConsumer$$ExternalSyntheticLambda0 implements IntConsumer {
    public final /* synthetic */ IntConsumer f$0;
    public final /* synthetic */ IntConsumer f$1;

    public /* synthetic */ IntConsumer$$ExternalSyntheticLambda0(IntConsumer intConsumer, IntConsumer intConsumer2) {
        this.f$0 = intConsumer;
        this.f$1 = intConsumer2;
    }

    public final void accept(int i) {
        IntConsumer.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, i);
    }

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer.-CC.$default$andThen(this, intConsumer);
    }
}
