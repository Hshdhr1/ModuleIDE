package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class JapaneseDate extends ChronoLocalDateImpl implements ChronoLocalDate, Serializable {
    static final LocalDate MEIJI_6_ISODATE = LocalDate.of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;
    private transient JapaneseEra era;
    private final transient LocalDate isoDate;
    private transient int yearOfEra;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ long until(Temporal temporal, TemporalUnit temporalUnit) {
        return super.until(temporal, temporalUnit);
    }

    public static JapaneseDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static JapaneseDate now(ZoneId zoneId) {
        return now(Clock.system(zoneId));
    }

    public static JapaneseDate now(Clock clock) {
        return new JapaneseDate(LocalDate.now(clock));
    }

    public static JapaneseDate of(JapaneseEra japaneseEra, int i, int i2, int i3) {
        JapaneseDate$$ExternalSyntheticBackport0.m(japaneseEra, "era");
        LocalDate of = LocalDate.of((japaneseEra.getSince().getYear() + i) - 1, i2, i3);
        if (of.isBefore(japaneseEra.getSince()) || japaneseEra != JapaneseEra.from(of)) {
            throw new DateTimeException("year, month, and day not valid for Era");
        }
        return new JapaneseDate(japaneseEra, i, of);
    }

    public static JapaneseDate of(int i, int i2, int i3) {
        return new JapaneseDate(LocalDate.of(i, i2, i3));
    }

    static JapaneseDate ofYearDay(JapaneseEra japaneseEra, int i, int i2) {
        LocalDate ofYearDay;
        JapaneseDate$$ExternalSyntheticBackport0.m(japaneseEra, "era");
        if (i == 1) {
            ofYearDay = LocalDate.ofYearDay(japaneseEra.getSince().getYear(), (japaneseEra.getSince().getDayOfYear() + i2) - 1);
        } else {
            ofYearDay = LocalDate.ofYearDay((japaneseEra.getSince().getYear() + i) - 1, i2);
        }
        if (ofYearDay.isBefore(japaneseEra.getSince()) || japaneseEra != JapaneseEra.from(ofYearDay)) {
            throw new DateTimeException("Invalid parameters");
        }
        return new JapaneseDate(japaneseEra, i, ofYearDay);
    }

    public static JapaneseDate from(TemporalAccessor temporalAccessor) {
        return JapaneseChronology.INSTANCE.date(temporalAccessor);
    }

    JapaneseDate(LocalDate localDate) {
        if (localDate.isBefore(MEIJI_6_ISODATE)) {
            throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
        }
        this.era = JapaneseEra.from(localDate);
        this.yearOfEra = (localDate.getYear() - this.era.getSince().getYear()) + 1;
        this.isoDate = localDate;
    }

    JapaneseDate(JapaneseEra japaneseEra, int i, LocalDate localDate) {
        if (localDate.isBefore(MEIJI_6_ISODATE)) {
            throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
        }
        this.era = japaneseEra;
        this.yearOfEra = i;
        this.isoDate = localDate;
    }

    public JapaneseChronology getChronology() {
        return JapaneseChronology.INSTANCE;
    }

    public JapaneseEra getEra() {
        return this.era;
    }

    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    public int lengthOfYear() {
        int lengthOfYear;
        JapaneseEra next = this.era.next();
        if (next != null && next.getSince().getYear() == this.isoDate.getYear()) {
            lengthOfYear = next.getSince().getDayOfYear() - 1;
        } else {
            lengthOfYear = this.isoDate.lengthOfYear();
        }
        return this.yearOfEra == 1 ? lengthOfYear - (this.era.getSince().getDayOfYear() - 1) : lengthOfYear;
    }

    public boolean isSupported(TemporalField temporalField) {
        if (temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH && temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR && temporalField != ChronoField.ALIGNED_WEEK_OF_MONTH && temporalField != ChronoField.ALIGNED_WEEK_OF_YEAR) {
            if (temporalField instanceof ChronoField) {
                return temporalField.isDateBased();
            }
            if (temporalField != null && temporalField.isSupportedBy(this)) {
                return true;
            }
        }
        return false;
    }

    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (isSupported(temporalField)) {
                ChronoField chronoField = (ChronoField) temporalField;
                int i = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                if (i == 1) {
                    return ValueRange.of(1L, lengthOfMonth());
                }
                if (i == 2) {
                    return ValueRange.of(1L, lengthOfYear());
                }
                if (i == 3) {
                    int year = this.era.getSince().getYear();
                    if (this.era.next() != null) {
                        return ValueRange.of(1L, (r0.getSince().getYear() - year) + 1);
                    }
                    return ValueRange.of(1L, 999999999 - year);
                }
                return getChronology().range(chronoField);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            switch ((ChronoField) temporalField) {
                case DAY_OF_YEAR:
                    if (this.yearOfEra == 1) {
                        return (this.isoDate.getDayOfYear() - this.era.getSince().getDayOfYear()) + 1;
                    }
                    return this.isoDate.getDayOfYear();
                case YEAR_OF_ERA:
                    return this.yearOfEra;
                case ALIGNED_DAY_OF_WEEK_IN_MONTH:
                case ALIGNED_DAY_OF_WEEK_IN_YEAR:
                case ALIGNED_WEEK_OF_MONTH:
                case ALIGNED_WEEK_OF_YEAR:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
                case ERA:
                    return this.era.getValue();
                default:
                    return this.isoDate.getLong(temporalField);
            }
        }
        return temporalField.getFrom(this);
    }

    public JapaneseDate with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            if (getLong(chronoField) == j) {
                return this;
            }
            int i = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (i == 3 || i == 8 || i == 9) {
                int checkValidIntValue = getChronology().range(chronoField).checkValidIntValue(j, chronoField);
                int i2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                if (i2 == 3) {
                    return withYear(checkValidIntValue);
                }
                if (i2 == 8) {
                    return withYear(JapaneseEra.of(checkValidIntValue), this.yearOfEra);
                }
                if (i2 == 9) {
                    return with(this.isoDate.withYear(checkValidIntValue));
                }
            }
            return with(this.isoDate.with(temporalField, j));
        }
        return (JapaneseDate) super.with(temporalField, j);
    }

    public JapaneseDate with(TemporalAdjuster temporalAdjuster) {
        return (JapaneseDate) super.with(temporalAdjuster);
    }

    public JapaneseDate plus(TemporalAmount temporalAmount) {
        return (JapaneseDate) super.plus(temporalAmount);
    }

    public JapaneseDate minus(TemporalAmount temporalAmount) {
        return (JapaneseDate) super.minus(temporalAmount);
    }

    private JapaneseDate withYear(JapaneseEra japaneseEra, int i) {
        return with(this.isoDate.withYear(JapaneseChronology.INSTANCE.prolepticYear(japaneseEra, i)));
    }

    private JapaneseDate withYear(int i) {
        return withYear(getEra(), i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate plusYears(long j) {
        return with(this.isoDate.plusYears(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate plusMonths(long j) {
        return with(this.isoDate.plusMonths(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate plusWeeks(long j) {
        return with(this.isoDate.plusWeeks(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate plusDays(long j) {
        return with(this.isoDate.plusDays(j));
    }

    public JapaneseDate plus(long j, TemporalUnit temporalUnit) {
        return (JapaneseDate) super.plus(j, temporalUnit);
    }

    public JapaneseDate minus(long j, TemporalUnit temporalUnit) {
        return (JapaneseDate) super.minus(j, temporalUnit);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate minusYears(long j) {
        return (JapaneseDate) super.minusYears(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate minusMonths(long j) {
        return (JapaneseDate) super.minusMonths(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate minusWeeks(long j) {
        return (JapaneseDate) super.minusWeeks(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JapaneseDate minusDays(long j) {
        return (JapaneseDate) super.minusDays(j);
    }

    private JapaneseDate with(LocalDate localDate) {
        return localDate.equals(this.isoDate) ? this : new JapaneseDate(localDate);
    }

    public final ChronoLocalDateTime atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    public ChronoPeriod until(ChronoLocalDate chronoLocalDate) {
        Period until = this.isoDate.until(chronoLocalDate);
        return getChronology().period(until.getYears(), until.getMonths(), until.getDays());
    }

    public long toEpochDay() {
        return this.isoDate.toEpochDay();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JapaneseDate) {
            return this.isoDate.equals(((JapaneseDate) obj).isoDate);
        }
        return false;
    }

    public int hashCode() {
        return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser((byte) 4, this);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(get(ChronoField.YEAR));
        dataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
    }

    static JapaneseDate readExternal(DataInput dataInput) throws IOException {
        return JapaneseChronology.INSTANCE.date(dataInput.readInt(), (int) dataInput.readByte(), (int) dataInput.readByte());
    }
}
