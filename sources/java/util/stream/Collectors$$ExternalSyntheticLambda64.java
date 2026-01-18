package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Collectors$$ExternalSyntheticLambda64 implements BiConsumer {
    public final /* synthetic */ Function f$0;
    public final /* synthetic */ Supplier f$1;
    public final /* synthetic */ BiConsumer f$2;

    public /* synthetic */ Collectors$$ExternalSyntheticLambda64(Function function, Supplier supplier, BiConsumer biConsumer) {
        this.f$0 = function;
        this.f$1 = supplier;
        this.f$2 = biConsumer;
    }

    public final void accept(Object obj, Object obj2) {
        Collectors.lambda$groupingBy$53(this.f$0, this.f$1, this.f$2, (Map) obj, obj2);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return BiConsumer.-CC.$default$andThen(this, biConsumer);
    }
}
