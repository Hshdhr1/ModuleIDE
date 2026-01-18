package kotlin.coroutines;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final /* synthetic */ class CombinedContext$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ CoroutineContext[] f$0;
    public final /* synthetic */ Ref.IntRef f$1;

    public /* synthetic */ CombinedContext$$ExternalSyntheticLambda0(CoroutineContext[] coroutineContextArr, Ref.IntRef intRef) {
        this.f$0 = coroutineContextArr;
        this.f$1 = intRef;
    }

    public final Object invoke(Object obj, Object obj2) {
        return CombinedContext.$r8$lambda$qks1Z-UeH7QikfX0vXl_9yUQdpE(this.f$0, this.f$1, (Unit) obj, (CoroutineContext.Element) obj2);
    }
}
