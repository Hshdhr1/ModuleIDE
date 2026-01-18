package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Collectors$$ExternalSyntheticLambda77 implements BinaryOperator {
    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction.-CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj, Object obj2) {
        return Collectors.lambda$toSet$7((Set) obj, (Set) obj2);
    }
}
