package java.time.temporal;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class TemporalAdjusters$$ExternalSyntheticLambda7 implements TemporalAdjuster {
    public final /* synthetic */ int f$0;

    public /* synthetic */ TemporalAdjusters$$ExternalSyntheticLambda7(int i) {
        this.f$0 = i;
    }

    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$nextOrSame$10(this.f$0, temporal);
    }
}
