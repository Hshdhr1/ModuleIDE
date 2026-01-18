package java.util.function;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.LongPredicate;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class LongPredicate$$ExternalSyntheticLambda0 implements LongPredicate {
    public final /* synthetic */ LongPredicate f$0;
    public final /* synthetic */ LongPredicate f$1;

    public /* synthetic */ LongPredicate$$ExternalSyntheticLambda0(LongPredicate longPredicate, LongPredicate longPredicate2) {
        this.f$0 = longPredicate;
        this.f$1 = longPredicate2;
    }

    public /* synthetic */ LongPredicate and(LongPredicate longPredicate) {
        return LongPredicate.-CC.$default$and(this, longPredicate);
    }

    public /* synthetic */ LongPredicate negate() {
        return LongPredicate.-CC.$default$negate(this);
    }

    public /* synthetic */ LongPredicate or(LongPredicate longPredicate) {
        return LongPredicate.-CC.$default$or(this, longPredicate);
    }

    public final boolean test(long j) {
        return LongPredicate.-CC.$private$lambda$or$2(this.f$0, this.f$1, j);
    }
}
