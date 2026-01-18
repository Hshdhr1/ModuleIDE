package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Collectors$$ExternalSyntheticLambda87 implements Supplier {
    public final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ Collectors$$ExternalSyntheticLambda87(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    public final Object get() {
        return Collectors.lambda$reducing$46(this.f$0);
    }
}
