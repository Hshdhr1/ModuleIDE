package java.time.temporal;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Temporal extends TemporalAccessor {
    boolean isSupported(TemporalUnit temporalUnit);

    Temporal minus(long j, TemporalUnit temporalUnit);

    Temporal minus(TemporalAmount temporalAmount);

    Temporal plus(long j, TemporalUnit temporalUnit);

    Temporal plus(TemporalAmount temporalAmount);

    long until(Temporal temporal, TemporalUnit temporalUnit);

    Temporal with(TemporalAdjuster temporalAdjuster);

    Temporal with(TemporalField temporalField, long j);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Temporal $default$with(Temporal _this, TemporalAdjuster temporalAdjuster) {
            return temporalAdjuster.adjustInto(_this);
        }

        public static Temporal $default$plus(Temporal _this, TemporalAmount temporalAmount) {
            return temporalAmount.addTo(_this);
        }

        public static Temporal $default$minus(Temporal _this, TemporalAmount temporalAmount) {
            return temporalAmount.subtractFrom(_this);
        }

        public static Temporal $default$minus(Temporal _this, long j, TemporalUnit temporalUnit) {
            return j == Long.MIN_VALUE ? _this.plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : _this.plus(-j, temporalUnit);
        }
    }
}
