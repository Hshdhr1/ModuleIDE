package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Collectors$$ExternalSyntheticLambda56 implements Function {
    public final /* synthetic */ Collector f$0;

    public /* synthetic */ Collectors$$ExternalSyntheticLambda56(Collector collector) {
        this.f$0 = collector;
    }

    public /* synthetic */ Function andThen(Function function) {
        return Function.-CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj) {
        return Collectors.lambda$partitioningBy$65(this.f$0, (Collectors.Partition) obj);
    }

    public /* synthetic */ Function compose(Function function) {
        return Function.-CC.$default$compose(this, function);
    }
}
