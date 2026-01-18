package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.MatchOps;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class MatchOps$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ MatchOps.MatchKind f$0;
    public final /* synthetic */ IntPredicate f$1;

    public /* synthetic */ MatchOps$$ExternalSyntheticLambda1(MatchOps.MatchKind matchKind, IntPredicate intPredicate) {
        this.f$0 = matchKind;
        this.f$1 = intPredicate;
    }

    public final Object get() {
        return MatchOps.lambda$makeInt$1(this.f$0, this.f$1);
    }
}
