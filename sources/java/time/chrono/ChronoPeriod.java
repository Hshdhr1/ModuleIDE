package java.time.chrono;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ChronoPeriod extends TemporalAmount {
    Temporal addTo(Temporal temporal);

    boolean equals(Object obj);

    long get(TemporalUnit temporalUnit);

    Chronology getChronology();

    List getUnits();

    int hashCode();

    boolean isNegative();

    boolean isZero();

    ChronoPeriod minus(TemporalAmount temporalAmount);

    ChronoPeriod multipliedBy(int i);

    ChronoPeriod negated();

    ChronoPeriod normalized();

    ChronoPeriod plus(TemporalAmount temporalAmount);

    Temporal subtractFrom(Temporal temporal);

    String toString();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static ChronoPeriod between(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            ChronoPeriod$$ExternalSyntheticBackport0.m(chronoLocalDate, "startDateInclusive");
            ChronoPeriod$$ExternalSyntheticBackport0.m(chronoLocalDate2, "endDateExclusive");
            return chronoLocalDate.until(chronoLocalDate2);
        }

        public static boolean $default$isZero(ChronoPeriod _this) {
            Iterator it = _this.getUnits().iterator();
            while (it.hasNext()) {
                if (_this.get((TemporalUnit) it.next()) != 0) {
                    return false;
                }
            }
            return true;
        }

        public static boolean $default$isNegative(ChronoPeriod _this) {
            Iterator it = _this.getUnits().iterator();
            while (it.hasNext()) {
                if (_this.get((TemporalUnit) it.next()) < 0) {
                    return true;
                }
            }
            return false;
        }

        public static ChronoPeriod $default$negated(ChronoPeriod _this) {
            return _this.multipliedBy(-1);
        }
    }
}
