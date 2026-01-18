package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.DoublePredicate;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DoublePredicate$$ExternalSyntheticLambda0 implements DoublePredicate {
    public final /* synthetic */ DoublePredicate f$0;

    public /* synthetic */ DoublePredicate$$ExternalSyntheticLambda0(DoublePredicate doublePredicate) {
        this.f$0 = doublePredicate;
    }

    public /* synthetic */ DoublePredicate and(DoublePredicate doublePredicate) {
        return DoublePredicate.-CC.$default$and(this, doublePredicate);
    }

    public /* synthetic */ DoublePredicate negate() {
        return DoublePredicate.-CC.$default$negate(this);
    }

    public /* synthetic */ DoublePredicate or(DoublePredicate doublePredicate) {
        return DoublePredicate.-CC.$default$or(this, doublePredicate);
    }

    public final boolean test(double d) {
        return DoublePredicate.-CC.$private$lambda$negate$1(this.f$0, d);
    }
}
