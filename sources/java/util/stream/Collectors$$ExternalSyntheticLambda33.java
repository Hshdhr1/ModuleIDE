package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.IntSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Collectors$$ExternalSyntheticLambda33 implements BiConsumer {
    public final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ Collectors$$ExternalSyntheticLambda33(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final void accept(Object obj, Object obj2) {
        Collectors.lambda$summarizingInt$70(this.f$0, (IntSummaryStatistics) obj, obj2);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return BiConsumer.-CC.$default$andThen(this, biConsumer);
    }
}
