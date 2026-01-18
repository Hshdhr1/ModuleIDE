package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BiFunction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class BiFunction$$ExternalSyntheticLambda0 implements BiFunction {
    public final /* synthetic */ BiFunction f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ BiFunction$$ExternalSyntheticLambda0(BiFunction biFunction, Function function) {
        this.f$0 = biFunction;
        this.f$1 = function;
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction.-CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj, Object obj2) {
        return BiFunction.-CC.$private$lambda$andThen$0(this.f$0, this.f$1, obj, obj2);
    }
}
