package java.time.chrono;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class ChronoLocalDateImpl implements ChronoLocalDate, Temporal, TemporalAdjuster, Serializable {
    private static final long serialVersionUID = 6282433883239719096L;

    public /* synthetic */ Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDate.-CC.$default$adjustInto(this, temporal);
    }

    public /* synthetic */ ChronoLocalDateTime atTime(LocalTime localTime) {
        return ChronoLocalDate.-CC.$default$atTime(this, localTime);
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return ChronoLocalDate.-CC.$default$compareTo(this, obj);
    }

    public /* synthetic */ int compareTo(ChronoLocalDate chronoLocalDate) {
        return ChronoLocalDate.-CC.$default$compareTo((ChronoLocalDate) this, chronoLocalDate);
    }

    public /* synthetic */ String format(DateTimeFormatter dateTimeFormatter) {
        return ChronoLocalDate.-CC.$default$format(this, dateTimeFormatter);
    }

    public /* synthetic */ int get(TemporalField temporalField) {
        return TemporalAccessor.-CC.$default$get(this, temporalField);
    }

    public /* synthetic */ Era getEra() {
        return ChronoLocalDate.-CC.$default$getEra(this);
    }

    public /* synthetic */ boolean isAfter(ChronoLocalDate chronoLocalDate) {
        return ChronoLocalDate.-CC.$default$isAfter(this, chronoLocalDate);
    }

    public /* synthetic */ boolean isBefore(ChronoLocalDate chronoLocalDate) {
        return ChronoLocalDate.-CC.$default$isBefore(this, chronoLocalDate);
    }

    public /* synthetic */ boolean isEqual(ChronoLocalDate chronoLocalDate) {
        return ChronoLocalDate.-CC.$default$isEqual(this, chronoLocalDate);
    }

    public /* synthetic */ boolean isLeapYear() {
        return ChronoLocalDate.-CC.$default$isLeapYear(this);
    }

    public /* synthetic */ boolean isSupported(TemporalField temporalField) {
        return ChronoLocalDate.-CC.$default$isSupported(this, temporalField);
    }

    public /* synthetic */ boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoLocalDate.-CC.$default$isSupported(this, temporalUnit);
    }

    public /* synthetic */ int lengthOfYear() {
        return ChronoLocalDate.-CC.$default$lengthOfYear(this);
    }

    abstract ChronoLocalDate plusDays(long j);

    abstract ChronoLocalDate plusMonths(long j);

    abstract ChronoLocalDate plusYears(long j);

    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return ChronoLocalDate.-CC.$default$query(this, temporalQuery);
    }

    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.-CC.$default$range(this, temporalField);
    }

    public /* synthetic */ long toEpochDay() {
        return ChronoLocalDate.-CC.$default$toEpochDay(this);
    }

    static ChronoLocalDate ensureValid(Chronology chronology, Temporal temporal) {
        ChronoLocalDate chronoLocalDate = (ChronoLocalDate) temporal;
        if (chronology.equals(chronoLocalDate.getChronology())) {
            return chronoLocalDate;
        }
        throw new ClassCastException("Chronology mismatch, expected: " + chronology.getId() + ", actual: " + chronoLocalDate.getChronology().getId());
    }

    ChronoLocalDateImpl() {
    }

    public ChronoLocalDate with(TemporalAdjuster temporalAdjuster) {
        return ChronoLocalDate.-CC.$default$with((ChronoLocalDate) this, temporalAdjuster);
    }

    public ChronoLocalDate with(TemporalField temporalField, long j) {
        return ChronoLocalDate.-CC.$default$with((ChronoLocalDate) this, temporalField, j);
    }

    public ChronoLocalDate plus(TemporalAmount temporalAmount) {
        return ChronoLocalDate.-CC.$default$plus((ChronoLocalDate) this, temporalAmount);
    }

    public ChronoLocalDate plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return plusDays(j);
                case 2:
                    return plusDays(ChronoLocalDateImpl$$ExternalSyntheticBackport3.m(j, 7));
                case 3:
                    return plusMonths(j);
                case 4:
                    return plusYears(j);
                case 5:
                    return plusYears(ChronoLocalDateImpl$$ExternalSyntheticBackport4.m(j, 10));
                case 6:
                    return plusYears(ChronoLocalDateImpl$$ExternalSyntheticBackport5.m(j, 100));
                case 7:
                    return plusYears(ChronoLocalDateImpl$$ExternalSyntheticBackport6.m(j, 1000));
                case 8:
                    return with((TemporalField) ChronoField.ERA, ChronoLocalDateImpl$$ExternalSyntheticBackport7.m(getLong(ChronoField.ERA), j));
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
        }
        return ChronoLocalDate.-CC.$default$plus((ChronoLocalDate) this, j, temporalUnit);
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;

        static {
            int[] iArr = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr;
            try {
                iArr[ChronoUnit.DAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.WEEKS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MONTHS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.YEARS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.DECADES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.CENTURIES.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MILLENNIA.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.ERAS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public ChronoLocalDate minus(TemporalAmount temporalAmount) {
        return ChronoLocalDate.-CC.$default$minus((ChronoLocalDate) this, temporalAmount);
    }

    public ChronoLocalDate minus(long j, TemporalUnit temporalUnit) {
        return ChronoLocalDate.-CC.$default$minus((ChronoLocalDate) this, j, temporalUnit);
    }

    ChronoLocalDate plusWeeks(long j) {
        return plusDays(ChronoLocalDateImpl$$ExternalSyntheticBackport2.m(j, 7));
    }

    ChronoLocalDate minusYears(long j) {
        return j == Long.MIN_VALUE ? ((ChronoLocalDateImpl) plusYears(Long.MAX_VALUE)).plusYears(1L) : plusYears(-j);
    }

    ChronoLocalDate minusMonths(long j) {
        return j == Long.MIN_VALUE ? ((ChronoLocalDateImpl) plusMonths(Long.MAX_VALUE)).plusMonths(1L) : plusMonths(-j);
    }

    ChronoLocalDate minusWeeks(long j) {
        return j == Long.MIN_VALUE ? ((ChronoLocalDateImpl) plusWeeks(Long.MAX_VALUE)).plusWeeks(1L) : plusWeeks(-j);
    }

    ChronoLocalDate minusDays(long j) {
        return j == Long.MIN_VALUE ? ((ChronoLocalDateImpl) plusDays(Long.MAX_VALUE)).plusDays(1L) : plusDays(-j);
    }

    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        ChronoLocalDateImpl$$ExternalSyntheticBackport1.m(temporal, "endExclusive");
        ChronoLocalDate date = getChronology().date(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return daysUntil(date);
                case 2:
                    return daysUntil(date) / 7;
                case 3:
                    return monthsUntil(date);
                case 4:
                    return monthsUntil(date) / 12;
                case 5:
                    return monthsUntil(date) / 120;
                case 6:
                    return monthsUntil(date) / 1200;
                case 7:
                    return monthsUntil(date) / 12000;
                case 8:
                    return date.getLong(ChronoField.ERA) - getLong(ChronoField.ERA);
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
        }
        ChronoLocalDateImpl$$ExternalSyntheticBackport1.m(temporalUnit, "unit");
        return temporalUnit.between(this, date);
    }

    private long daysUntil(ChronoLocalDate chronoLocalDate) {
        return chronoLocalDate.toEpochDay() - toEpochDay();
    }

    private long monthsUntil(ChronoLocalDate chronoLocalDate) {
        if (getChronology().range(ChronoField.MONTH_OF_YEAR).getMaximum() != 12) {
            throw new IllegalStateException("ChronoLocalDateImpl only supports Chronologies with 12 months per year");
        }
        return (((chronoLocalDate.getLong(ChronoField.PROLEPTIC_MONTH) * 32) + chronoLocalDate.get(ChronoField.DAY_OF_MONTH)) - ((getLong(ChronoField.PROLEPTIC_MONTH) * 32) + get(ChronoField.DAY_OF_MONTH))) / 32;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoLocalDate) && compareTo((ChronoLocalDate) obj) == 0;
    }

    public int hashCode() {
        long epochDay = toEpochDay();
        return getChronology().hashCode() ^ ((int) (epochDay ^ (epochDay >>> 32)));
    }

    public String toString() {
        long j = getLong(ChronoField.YEAR_OF_ERA);
        long j2 = getLong(ChronoField.MONTH_OF_YEAR);
        long j3 = getLong(ChronoField.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder(30);
        sb.append(getChronology().toString());
        sb.append(" ");
        sb.append(getEra());
        sb.append(" ");
        sb.append(j);
        sb.append(j2 < 10 ? "-0" : "-");
        sb.append(j2);
        sb.append(j3 >= 10 ? "-" : "-0");
        sb.append(j3);
        return sb.toString();
    }
}
