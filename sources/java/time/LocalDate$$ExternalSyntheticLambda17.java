package java.time;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.LongFunction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class LocalDate$$ExternalSyntheticLambda17 implements LongFunction {
    public final /* synthetic */ LocalDate f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ LocalDate$$ExternalSyntheticLambda17(LocalDate localDate, long j, long j2) {
        this.f$0 = localDate;
        this.f$1 = j;
        this.f$2 = j2;
    }

    public final Object apply(long j) {
        return this.f$0.lambda$datesUntil$1$java-time-LocalDate(this.f$1, this.f$2, j);
    }
}
