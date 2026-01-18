package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.IntPredicate;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class IntPredicate$$ExternalSyntheticLambda0 implements IntPredicate {
    public final /* synthetic */ IntPredicate f$0;
    public final /* synthetic */ IntPredicate f$1;

    public /* synthetic */ IntPredicate$$ExternalSyntheticLambda0(IntPredicate intPredicate, IntPredicate intPredicate2) {
        this.f$0 = intPredicate;
        this.f$1 = intPredicate2;
    }

    public /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
        return IntPredicate.-CC.$default$and(this, intPredicate);
    }

    public /* synthetic */ IntPredicate negate() {
        return IntPredicate.-CC.$default$negate(this);
    }

    public /* synthetic */ IntPredicate or(IntPredicate intPredicate) {
        return IntPredicate.-CC.$default$or(this, intPredicate);
    }

    public final boolean test(int i) {
        return IntPredicate.-CC.$private$lambda$or$2(this.f$0, this.f$1, i);
    }
}
