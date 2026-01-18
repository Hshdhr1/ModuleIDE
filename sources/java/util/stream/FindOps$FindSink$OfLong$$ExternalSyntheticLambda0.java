package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.OptionalLong;
import java.util.function.Predicate;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class FindOps$FindSink$OfLong$$ExternalSyntheticLambda0 implements Predicate {
    public /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate.-CC.$default$and(this, predicate);
    }

    public /* synthetic */ Predicate negate() {
        return Predicate.-CC.$default$negate(this);
    }

    public /* synthetic */ Predicate or(Predicate predicate) {
        return Predicate.-CC.$default$or(this, predicate);
    }

    public final boolean test(Object obj) {
        return ((OptionalLong) obj).isPresent();
    }
}
