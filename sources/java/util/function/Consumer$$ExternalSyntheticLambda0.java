package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Consumer$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ Consumer$$ExternalSyntheticLambda0(Consumer consumer, Consumer consumer2) {
        this.f$0 = consumer;
        this.f$1 = consumer2;
    }

    public final void accept(Object obj) {
        Consumer.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, obj);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.-CC.$default$andThen(this, consumer);
    }
}
