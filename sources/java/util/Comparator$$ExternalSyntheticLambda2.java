package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda2 implements Comparator, Serializable {
    public final /* synthetic */ Comparator f$0;
    public final /* synthetic */ Comparator f$1;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda2(Comparator comparator, Comparator comparator2) {
        this.f$0 = comparator;
        this.f$1 = comparator2;
    }

    public final int compare(Object obj, Object obj2) {
        return Comparator.-CC.$private$lambda$thenComparing$36697e65$1(this.f$0, this.f$1, obj, obj2);
    }

    public /* synthetic */ Comparator reversed() {
        return Comparator.-CC.$default$reversed(this);
    }

    public /* synthetic */ Comparator thenComparing(Comparator comparator) {
        return Comparator.-CC.$default$thenComparing(this, comparator);
    }

    public /* synthetic */ Comparator thenComparing(Function function) {
        return Comparator.-CC.$default$thenComparing(this, function);
    }

    public /* synthetic */ Comparator thenComparing(Function function, Comparator comparator) {
        return Comparator.-CC.$default$thenComparing(this, function, comparator);
    }

    public /* synthetic */ Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
        return Comparator.-CC.$default$thenComparingDouble(this, toDoubleFunction);
    }

    public /* synthetic */ Comparator thenComparingInt(ToIntFunction toIntFunction) {
        return Comparator.-CC.$default$thenComparingInt(this, toIntFunction);
    }

    public /* synthetic */ Comparator thenComparingLong(ToLongFunction toLongFunction) {
        return Comparator.-CC.$default$thenComparingLong(this, toLongFunction);
    }
}
