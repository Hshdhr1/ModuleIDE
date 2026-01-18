package java.time.format;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DateTimeFormatterBuilder$ReducedPrinterParser$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ DateTimeFormatterBuilder.ReducedPrinterParser f$0;
    public final /* synthetic */ DateTimeParseContext f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ DateTimeFormatterBuilder$ReducedPrinterParser$$ExternalSyntheticLambda2(DateTimeFormatterBuilder.ReducedPrinterParser reducedPrinterParser, DateTimeParseContext dateTimeParseContext, long j, int i, int i2) {
        this.f$0 = reducedPrinterParser;
        this.f$1 = dateTimeParseContext;
        this.f$2 = j;
        this.f$3 = i;
        this.f$4 = i2;
    }

    public final void accept(Object obj) {
        this.f$0.lambda$setValue$0$java-time-format-DateTimeFormatterBuilder$ReducedPrinterParser(this.f$1, this.f$2, this.f$3, this.f$4, (Chronology) obj);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.-CC.$default$andThen(this, consumer);
    }
}
