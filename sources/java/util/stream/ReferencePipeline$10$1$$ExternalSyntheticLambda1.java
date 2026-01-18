package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.LongConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class ReferencePipeline$10$1$$ExternalSyntheticLambda1 implements LongConsumer {
    public final /* synthetic */ Sink f$0;

    public /* synthetic */ ReferencePipeline$10$1$$ExternalSyntheticLambda1(Sink sink) {
        this.f$0 = sink;
    }

    public final void accept(long j) {
        this.f$0.accept(j);
    }

    public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return LongConsumer.-CC.$default$andThen(this, longConsumer);
    }
}
