package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BiConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class BiConsumer$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ BiConsumer f$0;
    public final /* synthetic */ BiConsumer f$1;

    public /* synthetic */ BiConsumer$$ExternalSyntheticLambda0(BiConsumer biConsumer, BiConsumer biConsumer2) {
        this.f$0 = biConsumer;
        this.f$1 = biConsumer2;
    }

    public final void accept(Object obj, Object obj2) {
        BiConsumer.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, obj, obj2);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return BiConsumer.-CC.$default$andThen(this, biConsumer);
    }
}
