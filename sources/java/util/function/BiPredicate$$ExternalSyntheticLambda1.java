package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BiPredicate;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class BiPredicate$$ExternalSyntheticLambda1 implements BiPredicate {
    public final /* synthetic */ BiPredicate f$0;
    public final /* synthetic */ BiPredicate f$1;

    public /* synthetic */ BiPredicate$$ExternalSyntheticLambda1(BiPredicate biPredicate, BiPredicate biPredicate2) {
        this.f$0 = biPredicate;
        this.f$1 = biPredicate2;
    }

    public /* synthetic */ BiPredicate and(BiPredicate biPredicate) {
        return BiPredicate.-CC.$default$and(this, biPredicate);
    }

    public /* synthetic */ BiPredicate negate() {
        return BiPredicate.-CC.$default$negate(this);
    }

    public /* synthetic */ BiPredicate or(BiPredicate biPredicate) {
        return BiPredicate.-CC.$default$or(this, biPredicate);
    }

    public final boolean test(Object obj, Object obj2) {
        return BiPredicate.-CC.$private$lambda$or$2(this.f$0, this.f$1, obj, obj2);
    }
}
