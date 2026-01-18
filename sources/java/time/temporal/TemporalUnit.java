package java.time.temporal;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.Duration;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface TemporalUnit {
    Temporal addTo(Temporal temporal, long j);

    long between(Temporal temporal, Temporal temporal2);

    Duration getDuration();

    boolean isDateBased();

    boolean isDurationEstimated();

    boolean isSupportedBy(Temporal temporal);

    boolean isTimeBased();

    String toString();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static boolean $default$isSupportedBy(TemporalUnit _this, Temporal temporal) {
            if (temporal instanceof LocalTime) {
                return _this.isTimeBased();
            }
            if (temporal instanceof ChronoLocalDate) {
                return _this.isDateBased();
            }
            if ((temporal instanceof ChronoLocalDateTime) || (temporal instanceof ChronoZonedDateTime)) {
                return true;
            }
            try {
                temporal.plus(1L, _this);
                return true;
            } catch (UnsupportedTemporalTypeException | RuntimeException unused) {
                return false;
            } catch (RuntimeException unused2) {
                temporal.plus(-1L, _this);
                return true;
            }
        }
    }
}
