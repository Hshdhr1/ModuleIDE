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
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Comparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ChronoZonedDateTime extends Temporal, Comparable {
    int compareTo(ChronoZonedDateTime chronoZonedDateTime);

    boolean equals(Object obj);

    String format(DateTimeFormatter dateTimeFormatter);

    int get(TemporalField temporalField);

    Chronology getChronology();

    long getLong(TemporalField temporalField);

    ZoneOffset getOffset();

    ZoneId getZone();

    int hashCode();

    boolean isAfter(ChronoZonedDateTime chronoZonedDateTime);

    boolean isBefore(ChronoZonedDateTime chronoZonedDateTime);

    boolean isEqual(ChronoZonedDateTime chronoZonedDateTime);

    boolean isSupported(TemporalField temporalField);

    boolean isSupported(TemporalUnit temporalUnit);

    ChronoZonedDateTime minus(long j, TemporalUnit temporalUnit);

    ChronoZonedDateTime minus(TemporalAmount temporalAmount);

    ChronoZonedDateTime plus(long j, TemporalUnit temporalUnit);

    ChronoZonedDateTime plus(TemporalAmount temporalAmount);

    Object query(TemporalQuery temporalQuery);

    ValueRange range(TemporalField temporalField);

    long toEpochSecond();

    Instant toInstant();

    ChronoLocalDate toLocalDate();

    ChronoLocalDateTime toLocalDateTime();

    LocalTime toLocalTime();

    String toString();

    ChronoZonedDateTime with(TemporalAdjuster temporalAdjuster);

    ChronoZonedDateTime with(TemporalField temporalField, long j);

    ChronoZonedDateTime withEarlierOffsetAtOverlap();

    ChronoZonedDateTime withLaterOffsetAtOverlap();

    ChronoZonedDateTime withZoneSameInstant(ZoneId zoneId);

    ChronoZonedDateTime withZoneSameLocal(ZoneId zoneId);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ int $default$compareTo(ChronoZonedDateTime _this, Object obj) {
            return _this.compareTo((ChronoZonedDateTime) obj);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoZonedDateTime _this, long j, TemporalUnit temporalUnit) {
            return _this.minus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$minus(ChronoZonedDateTime _this, TemporalAmount temporalAmount) {
            return _this.minus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoZonedDateTime _this, long j, TemporalUnit temporalUnit) {
            return _this.plus(j, temporalUnit);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$plus(ChronoZonedDateTime _this, TemporalAmount temporalAmount) {
            return _this.plus(temporalAmount);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoZonedDateTime _this, TemporalAdjuster temporalAdjuster) {
            return _this.with(temporalAdjuster);
        }

        public static /* bridge */ /* synthetic */ Temporal $default$with(ChronoZonedDateTime _this, TemporalField temporalField, long j) {
            return _this.with(temporalField, j);
        }

        public static Comparator timeLineOrder() {
            return new ChronoZonedDateTime$$ExternalSyntheticLambda1();
        }

        public static /* synthetic */ int lambda$timeLineOrder$f56e6d02$1(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
            int i = (chronoZonedDateTime.toEpochSecond() > chronoZonedDateTime2.toEpochSecond() ? 1 : (chronoZonedDateTime.toEpochSecond() == chronoZonedDateTime2.toEpochSecond() ? 0 : -1));
            return i == 0 ? (chronoZonedDateTime.toLocalTime().getNano() > chronoZonedDateTime2.toLocalTime().getNano() ? 1 : (chronoZonedDateTime.toLocalTime().getNano() == chronoZonedDateTime2.toLocalTime().getNano() ? 0 : -1)) : i;
        }

        public static ChronoZonedDateTime from(TemporalAccessor temporalAccessor) {
            if (temporalAccessor instanceof ChronoZonedDateTime) {
                return (ChronoZonedDateTime) temporalAccessor;
            }
            ChronoZonedDateTime$$ExternalSyntheticBackport0.m(temporalAccessor, "temporal");
            Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
            if (chronology == null) {
                throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + temporalAccessor.getClass());
            }
            return chronology.zonedDateTime(temporalAccessor);
        }

        public static ValueRange $default$range(ChronoZonedDateTime _this, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                if (temporalField == ChronoField.INSTANT_SECONDS || temporalField == ChronoField.OFFSET_SECONDS) {
                    return temporalField.range();
                }
                return _this.toLocalDateTime().range(temporalField);
            }
            return temporalField.rangeRefinedBy(_this);
        }

        public static int $default$get(ChronoZonedDateTime _this, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                int i = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
                if (i == 1) {
                    throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
                }
                if (i == 2) {
                    return _this.getOffset().getTotalSeconds();
                }
                return _this.toLocalDateTime().get(temporalField);
            }
            return TemporalAccessor.-CC.$default$get(_this, temporalField);
        }

        public static long $default$getLong(ChronoZonedDateTime _this, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                int i = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
                if (i == 1) {
                    return _this.toEpochSecond();
                }
                if (i == 2) {
                    return _this.getOffset().getTotalSeconds();
                }
                return _this.toLocalDateTime().getLong(temporalField);
            }
            return temporalField.getFrom(_this);
        }

        public static ChronoLocalDate $default$toLocalDate(ChronoZonedDateTime _this) {
            return _this.toLocalDateTime().toLocalDate();
        }

        public static LocalTime $default$toLocalTime(ChronoZonedDateTime _this) {
            return _this.toLocalDateTime().toLocalTime();
        }

        public static Chronology $default$getChronology(ChronoZonedDateTime _this) {
            return _this.toLocalDate().getChronology();
        }

        public static boolean $default$isSupported(ChronoZonedDateTime _this, TemporalUnit temporalUnit) {
            return temporalUnit instanceof ChronoUnit ? temporalUnit != ChronoUnit.FOREVER : temporalUnit != null && temporalUnit.isSupportedBy(_this);
        }

        public static ChronoZonedDateTime $default$with(ChronoZonedDateTime _this, TemporalAdjuster temporalAdjuster) {
            return ChronoZonedDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$with(_this, temporalAdjuster));
        }

        public static ChronoZonedDateTime $default$plus(ChronoZonedDateTime _this, TemporalAmount temporalAmount) {
            return ChronoZonedDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$plus(_this, temporalAmount));
        }

        public static ChronoZonedDateTime $default$minus(ChronoZonedDateTime _this, TemporalAmount temporalAmount) {
            return ChronoZonedDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, temporalAmount));
        }

        public static ChronoZonedDateTime $default$minus(ChronoZonedDateTime _this, long j, TemporalUnit temporalUnit) {
            return ChronoZonedDateTimeImpl.ensureValid(_this.getChronology(), Temporal.-CC.$default$minus(_this, j, temporalUnit));
        }

        public static Object $default$query(ChronoZonedDateTime _this, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.zoneId()) {
                return _this.getZone();
            }
            if (temporalQuery == TemporalQueries.offset()) {
                return _this.getOffset();
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

        public static String $default$format(ChronoZonedDateTime _this, DateTimeFormatter dateTimeFormatter) {
            ChronoZonedDateTime$$ExternalSyntheticBackport0.m(dateTimeFormatter, "formatter");
            return dateTimeFormatter.format(_this);
        }

        public static Instant $default$toInstant(ChronoZonedDateTime _this) {
            return Instant.ofEpochSecond(_this.toEpochSecond(), _this.toLocalTime().getNano());
        }

        public static long $default$toEpochSecond(ChronoZonedDateTime _this) {
            return ((_this.toLocalDate().toEpochDay() * 86400) + _this.toLocalTime().toSecondOfDay()) - _this.getOffset().getTotalSeconds();
        }

        public static int $default$compareTo(ChronoZonedDateTime _this, ChronoZonedDateTime chronoZonedDateTime) {
            int i = (_this.toEpochSecond() > chronoZonedDateTime.toEpochSecond() ? 1 : (_this.toEpochSecond() == chronoZonedDateTime.toEpochSecond() ? 0 : -1));
            if (i != 0) {
                return i;
            }
            int nano = _this.toLocalTime().getNano() - chronoZonedDateTime.toLocalTime().getNano();
            return (nano == 0 && (nano = _this.toLocalDateTime().compareTo(chronoZonedDateTime.toLocalDateTime())) == 0 && (nano = _this.getZone().getId().compareTo(chronoZonedDateTime.getZone().getId())) == 0) ? _this.getChronology().compareTo(chronoZonedDateTime.getChronology()) : nano;
        }

        public static boolean $default$isBefore(ChronoZonedDateTime _this, ChronoZonedDateTime chronoZonedDateTime) {
            long epochSecond = _this.toEpochSecond();
            long epochSecond2 = chronoZonedDateTime.toEpochSecond();
            if (epochSecond >= epochSecond2) {
                return epochSecond == epochSecond2 && _this.toLocalTime().getNano() < chronoZonedDateTime.toLocalTime().getNano();
            }
            return true;
        }

        public static boolean $default$isAfter(ChronoZonedDateTime _this, ChronoZonedDateTime chronoZonedDateTime) {
            long epochSecond = _this.toEpochSecond();
            long epochSecond2 = chronoZonedDateTime.toEpochSecond();
            if (epochSecond <= epochSecond2) {
                return epochSecond == epochSecond2 && _this.toLocalTime().getNano() > chronoZonedDateTime.toLocalTime().getNano();
            }
            return true;
        }

        public static boolean $default$isEqual(ChronoZonedDateTime _this, ChronoZonedDateTime chronoZonedDateTime) {
            return _this.toEpochSecond() == chronoZonedDateTime.toEpochSecond() && _this.toLocalTime().getNano() == chronoZonedDateTime.toLocalTime().getNano();
        }
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.INSTANT_SECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.OFFSET_SECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
