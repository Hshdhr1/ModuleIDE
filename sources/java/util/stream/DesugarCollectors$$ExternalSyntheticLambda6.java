package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.List;
import java.util.function.BiConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarCollectors$$ExternalSyntheticLambda6 implements BiConsumer {
    public final void accept(Object obj, Object obj2) {
        ((List) obj).add(obj2);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return BiConsumer.-CC.$default$andThen(this, biConsumer);
    }
}
