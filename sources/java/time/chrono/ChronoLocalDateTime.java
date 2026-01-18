package java.time.chrono;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
import java.util.Comparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ChronoLocalDateTime extends Temporal, TemporalAdjuster, Comparable {
    Temporal adjustInto(Temporal temporal);

    ChronoZonedDateTime atZone(ZoneId zoneId);

    int compareTo(ChronoLocalDateTime chronoLocalDateTime);

    boolean equals(Object obj);

    String format(DateTimeFormatter dateTimeFormatter);

    Chronology getChronology();

    int hashCode();

    boolean isAfter(ChronoLocalDateTime chronoLocalDateTime);

    boolean isBefore(ChronoLocalDateTime chronoLocalDateTime);

    boolean isEqual(ChronoLocalDateTime chronoLocalDateTime);

    boolean isSupported(TemporalField temporalField);

    boolean isSupported(TemporalUnit temporalUnit);

    ChronoLocalDateTime minus(long j, TemporalUnit temporalUnit);

    ChronoLocalDateTime minus(TemporalAmount temporalAmount);

    ChronoLocalDateTime plus(long j, TemporalUnit temporalUnit);

    ChronoLocalDateTime plus(TemporalAmount temporalAmount);

    Object query(TemporalQuery temporalQuery);

    long toEpochSecond(ZoneOffset zoneOffset);

    Instant toInstant(ZoneOffset zoneOffset);

    ChronoLocalDate toLocalDate();

    LocalTime toLocalTime();

    String toString();

    ChronoLocalDateTime with(TemporalAdjuster temporalAdjuster);

    ChronoLocalDateTime with(TemporalField temporalField, long j);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ int $default$compareTo(ChronoLocalDateTime _this, Object obj) {
            return _this.compareTo((ChronoLocalDateTime) obj);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoLocalDateTime _this, long j, TemporalUnit temporalUnit) {
            return _this.minus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoLocalDateTime _this, TemporalAmount temporalAmount) {
            return _this.minus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoLocalDateTime _this, long j, TemporalUnit temporalUnit) {
            return _this.plus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoLocalDateTime _this, TemporalAmount temporalAmount) {
            return _this.plus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoLocalDateTime _this, TemporalAdjuster temporalAdjuster) {
            return _this.with(temporalAdjuster);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoLocalDateTime _this, TemporalField temporalField, long j) {
            return _this.with(temporalField, j);
        }

        public static Comparator timeLineOrder() {
            return new ChronoLocalDateTime$$ExternalSyntheticLambda1();
        }

        public static /* synthetic */ int lambda$timeLineOrder$b9959cb5$1(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
            int i = (chronoLocalDateTime.toLocalDate().toEpochDay() > chronoLocalDateTime2.toLocalDate().toEpochDay() ? 1 : (chronoLocalDateTime.toLocalDate().toEpochDay() == chronoLocalDateTime2.toLocalDate().toEpochDay() ? 0 : -1));
            return i == 0 ? (chronoLocalDateTime.toLocalTime().toNanoOfDay() > chronoLocalDateTime2.toLocalTime().toNanoOfDay() ? 1 : (chronoLocalDateTime.toLocalTime().toNanoOfDay() == chronoLocalDateTime2.toLocalTime().toNanoOfDay() ? 0 : -1)) : i;
        }

        public static ChronoLocalDateTime from(TemporalAccessor temporalAccessor) {
            if (temporalAccessor instanceof ChronoLocalDateTime) {
                return (ChronoLocalDateTime) temporalAccessor;
            }
            ChronoLocalDateTime$$ExternalSyntheticBackport0.m(temporalAccessor, "temporal");
            Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
            if (chronology == null) {
                throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporalAccessor.getClass());
            }
            return chronology.localDateTime(temporalAccessor);
        }

        public static Chronology $default$getChronology(ChronoLocalDateTime _this) {
            return _this.toLocalDate().getChronology();
        }

        public static boolean $default$isSupported(ChronoLocalDateTime _this, TemporalUnit temporalUnit) {
            return temporalUnit instanceof ChronoUnit ? temporalUnit != ChronoUnit.FOREVER : temporalUnit != null && temporalUnit.isSupportedBy(_this);
        }

        public static ChronoLocalDateTime $default$with(ChronoLocalDateTime _this, TemporalAdjuster temporalAdjuster) {
            return ChronoLocalDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$with(_this, temporalAdjuster));
        }

        public static ChronoLocalDateTime $default$plus(ChronoLocalDateTime _this, TemporalAmount temporalAmount) {
            return ChronoLocalDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$plus(_this, temporalAmount));
        }

        public static ChronoLocalDateTime $default$minus(ChronoLocalDateTime _this, TemporalAmount temporalAmount) {
            return ChronoLocalDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, temporalAmount));
        }

        public static ChronoLocalDateTime $default$minus(ChronoLocalDateTime _this, long j, TemporalUnit temporalUnit) {
            return ChronoLocalDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, j, temporalUnit));
        }

        public static Object $default$query(ChronoLocalDateTime _this, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.localTime()) {
                return _this.toLocalTime();
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return _this.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return ChronoUnit.NANOS;
            }
            return temporalQuery.queryFrom(_this);
        }

        public static Temporal $default$adjustInto(ChronoLocalDateTime _this, Temporal temporal) {
            return temporal.with(ChronoField.EPOCH_DAY, _this.toLocalDate().toEpochDay()).with(ChronoField.NANO_OF_DAY, _this.toLocalTime().toNanoOfDay());
        }

        public static String $default$format(ChronoLocalDateTime _this, DateTimeFormatter dateTimeFormatter) {
            ChronoLocalDateTime$$ExternalSyntheticBackport0.m(dateTimeFormatter, "formatter");
            return dateTimeFormatter.format(_this);
        }

        public static Instant $default$toInstant(ChronoLocalDateTime _this, ZoneOffset zoneOffset) {
            return Instant.ofEpochSecond(_this.toEpochSecond(zoneOffset), _this.toLocalTime().getNano());
        }

        public static long $default$toEpochSecond(ChronoLocalDateTime _this, ZoneOffset zoneOffset) {
            ChronoLocalDateTime$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
            return ((_this.toLocalDate().toEpochDay() * 86400) + _this.toLocalTime().toSecondOfDay()) - zoneOffset.getTotalSeconds();
        }

        public static int $default$compareTo(ChronoLocalDateTime _this, ChronoLocalDateTime chronoLocalDateTime) {
            int compareTo = _this.toLocalDate().compareTo(chronoLocalDateTime.toLocalDate());
            return (compareTo == 0 && (compareTo = _this.toLocalTime().compareTo(chronoLocalDateTime.toLocalTime())) == 0) ? _this.getChronology().compareTo(chronoLocalDateTime.getChronology()) : compareTo;
        }

        public static boolean $default$isAfter(ChronoLocalDateTime _this, ChronoLocalDateTime chronoLocalDateTime) {
            long epochDay = _this.toLocalDate().toEpochDay();
            long epochDay2 = chronoLocalDateTime.toLocalDate().toEpochDay();
            if (epochDay <= epochDay2) {
                return epochDay == epochDay2 && _this.toLocalTime().toNanoOfDay() > chronoLocalDateTime.toLocalTime().toNanoOfDay();
            }
            return true;
        }

        public static boolean $default$isBefore(ChronoLocalDateTime _this, ChronoLocalDateTime chronoLocalDateTime) {
            long epochDay = _this.toLocalDate().toEpochDay();
            long epochDay2 = chronoLocalDateTime.toLocalDate().toEpochDay();
            if (epochDay >= epochDay2) {
                return epochDay == epochDay2 && _this.toLocalTime().toNanoOfDay() < chronoLocalDateTime.toLocalTime().toNanoOfDay();
            }
            return true;
        }

        public static boolean $default$isEqual(ChronoLocalDateTime _this, ChronoLocalDateTime chronoLocalDateTime) {
            return _this.toLocalTime().toNanoOfDay() == chronoLocalDateTime.toLocalTime().toNanoOfDay() && _this.toLocalDate().toEpochDay() == chronoLocalDateTime.toLocalDate().toEpochDay();
        }
    }
}
