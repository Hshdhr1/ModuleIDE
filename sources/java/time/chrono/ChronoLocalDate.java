package java.time.chrono;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Comparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ChronoLocalDate extends Temporal, TemporalAdjuster, Comparable {
    Temporal adjustInto(Temporal temporal);

    ChronoLocalDateTime atTime(LocalTime localTime);

    int compareTo(ChronoLocalDate chronoLocalDate);

    boolean equals(Object obj);

    String format(DateTimeFormatter dateTimeFormatter);

    Chronology getChronology();

    Era getEra();

    int hashCode();

    boolean isAfter(ChronoLocalDate chronoLocalDate);

    boolean isBefore(ChronoLocalDate chronoLocalDate);

    boolean isEqual(ChronoLocalDate chronoLocalDate);

    boolean isLeapYear();

    boolean isSupported(TemporalField temporalField);

    boolean isSupported(TemporalUnit temporalUnit);

    int lengthOfMonth();

    int lengthOfYear();

    ChronoLocalDate minus(long j, TemporalUnit temporalUnit);

    ChronoLocalDate minus(TemporalAmount temporalAmount);

    ChronoLocalDate plus(long j, TemporalUnit temporalUnit);

    ChronoLocalDate plus(TemporalAmount temporalAmount);

    Object query(TemporalQuery temporalQuery);

    long toEpochDay();

    String toString();

    long until(Temporal temporal, TemporalUnit temporalUnit);

    ChronoPeriod until(ChronoLocalDate chronoLocalDate);

    ChronoLocalDate with(TemporalAdjuster temporalAdjuster);

    ChronoLocalDate with(TemporalField temporalField, long j);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ int $default$compareTo(ChronoLocalDate _this, Object obj) {
            return _this.compareTo((ChronoLocalDate) obj);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoLocalDate _this, long j, TemporalUnit temporalUnit) {
            return _this.minus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoLocalDate _this, TemporalAmount temporalAmount) {
            return _this.minus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoLocalDate _this, long j, TemporalUnit temporalUnit) {
            return _this.plus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoLocalDate _this, TemporalAmount temporalAmount) {
            return _this.plus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoLocalDate _this, TemporalAdjuster temporalAdjuster) {
            return _this.with(temporalAdjuster);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoLocalDate _this, TemporalField temporalField, long j) {
            return _this.with(temporalField, j);
        }

        public static Comparator timeLineOrder() {
            return new ChronoLocalDate$$ExternalSyntheticLambda1();
        }

        public static /* synthetic */ int lambda$timeLineOrder$493d4938$1(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            return (chronoLocalDate.toEpochDay() > chronoLocalDate2.toEpochDay() ? 1 : (chronoLocalDate.toEpochDay() == chronoLocalDate2.toEpochDay() ? 0 : -1));
        }

        public static ChronoLocalDate from(TemporalAccessor temporalAccessor) {
            if (temporalAccessor instanceof ChronoLocalDate) {
                return (ChronoLocalDate) temporalAccessor;
            }
            ChronoLocalDate$$ExternalSyntheticBackport0.m(temporalAccessor, "temporal");
            Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
            if (chronology == null) {
                throw new DateTimeException("Unable to obtain ChronoLocalDate from TemporalAccessor: " + temporalAccessor.getClass());
            }
            return chronology.date(temporalAccessor);
        }

        public static Era $default$getEra(ChronoLocalDate _this) {
            return _this.getChronology().eraOf(_this.get(ChronoField.ERA));
        }

        public static boolean $default$isLeapYear(ChronoLocalDate _this) {
            return _this.getChronology().isLeapYear(_this.getLong(ChronoField.YEAR));
        }

        public static int $default$lengthOfYear(ChronoLocalDate _this) {
            return _this.isLeapYear() ? 366 : 365;
        }

        public static boolean $default$isSupported(ChronoLocalDate _this, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                return temporalField.isDateBased();
            }
            return temporalField != null && temporalField.isSupportedBy(_this);
        }

        public static boolean $default$isSupported(ChronoLocalDate _this, TemporalUnit temporalUnit) {
            if (temporalUnit instanceof ChronoUnit) {
                return temporalUnit.isDateBased();
            }
            return temporalUnit != null && temporalUnit.isSupportedBy(_this);
        }

        public static ChronoLocalDate $default$with(ChronoLocalDate _this, TemporalAdjuster temporalAdjuster) {
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$with(_this, temporalAdjuster));
        }

        public static ChronoLocalDate $default$with(ChronoLocalDate _this, TemporalField temporalField, long j) {
            if (temporalField instanceof ChronoField) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), temporalField.adjustInto(_this, j));
        }

        public static ChronoLocalDate $default$plus(ChronoLocalDate _this, TemporalAmount temporalAmount) {
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$plus(_this, temporalAmount));
        }

        public static ChronoLocalDate $default$plus(ChronoLocalDate _this, long j, TemporalUnit temporalUnit) {
            if (temporalUnit instanceof ChronoUnit) {
                throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), temporalUnit.addTo(_this, j));
        }

        public static ChronoLocalDate $default$minus(ChronoLocalDate _this, TemporalAmount temporalAmount) {
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, temporalAmount));
        }

        public static ChronoLocalDate $default$minus(ChronoLocalDate _this, long j, TemporalUnit temporalUnit) {
            return ChronoLocalDateImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, j, temporalUnit));
        }

        public static Object $default$query(ChronoLocalDate _this, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.localTime()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return _this.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return ChronoUnit.DAYS;
            }
            return temporalQuery.queryFrom(_this);
        }

        public static Temporal $default$adjustInto(ChronoLocalDate _this, Temporal temporal) {
            return temporal.with(ChronoField.EPOCH_DAY, _this.toEpochDay());
        }

        public static String $default$format(ChronoLocalDate _this, DateTimeFormatter dateTimeFormatter) {
            ChronoLocalDate$$ExternalSyntheticBackport0.m(dateTimeFormatter, "formatter");
            return dateTimeFormatter.format(_this);
        }

        public static ChronoLocalDateTime $default$atTime(ChronoLocalDate _this, LocalTime localTime) {
            return ChronoLocalDateTimeImpl.of(_this, localTime);
        }

        public static long $default$toEpochDay(ChronoLocalDate _this) {
            return _this.getLong(ChronoField.EPOCH_DAY);
        }

        public static int $default$compareTo(ChronoLocalDate _this, ChronoLocalDate chronoLocalDate) {
            int i = (_this.toEpochDay() > chronoLocalDate.toEpochDay() ? 1 : (_this.toEpochDay() == chronoLocalDate.toEpochDay() ? 0 : -1));
            return i == 0 ? _this.getChronology().compareTo(chronoLocalDate.getChronology()) : i;
        }

        public static boolean $default$isAfter(ChronoLocalDate _this, ChronoLocalDate chronoLocalDate) {
            return _this.toEpochDay() > chronoLocalDate.toEpochDay();
        }

        public static boolean $default$isBefore(ChronoLocalDate _this, ChronoLocalDate chronoLocalDate) {
            return _this.toEpochDay() < chronoLocalDate.toEpochDay();
        }

        public static boolean $default$isEqual(ChronoLocalDate _this, ChronoLocalDate chronoLocalDate) {
            return _this.toEpochDay() == chronoLocalDate.toEpochDay();
        }
    }
}
