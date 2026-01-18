package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.LongConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class LongConsumer$$ExternalSyntheticLambda0 implements LongConsumer {
    public final /* synthetic */ LongConsumer f$0;
    public final /* synthetic */ LongConsumer f$1;

    public /* synthetic */ LongConsumer$$ExternalSyntheticLambda0(LongConsumer longConsumer, LongConsumer longConsumer2) {
        this.f$0 = longConsumer;
        this.f$1 = longConsumer2;
    }

    public final void accept(long j) {
        LongConsumer.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, j);
    }

    public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return LongConsumer.-CC.$default$andThen(this, longConsumer);
    }
}
