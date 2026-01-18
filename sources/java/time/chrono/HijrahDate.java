package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
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
public final class HijrahDate extends ChronoLocalDateImpl implements ChronoLocalDate, Serializable {
    private static final long serialVersionUID = -5207853542612002020L;
    private final transient HijrahChronology chrono;
    private final transient int dayOfMonth;
    private final transient int monthOfYear;
    private final transient int prolepticYear;

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ long until(Temporal temporal, TemporalUnit temporalUnit) {
        return super.until(temporal, temporalUnit);
    }

    static HijrahDate of(HijrahChronology hijrahChronology, int i, int i2, int i3) {
        return new HijrahDate(hijrahChronology, i, i2, i3);
    }

    static HijrahDate ofEpochDay(HijrahChronology hijrahChronology, long j) {
        return new HijrahDate(hijrahChronology, j);
    }

    public static HijrahDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static HijrahDate now(ZoneId zoneId) {
        return now(Clock.system(zoneId));
    }

    public static HijrahDate now(Clock clock) {
        return ofEpochDay(HijrahChronology.INSTANCE, LocalDate.now(clock).toEpochDay());
    }

    public static HijrahDate of(int i, int i2, int i3) {
        return HijrahChronology.INSTANCE.date(i, i2, i3);
    }

    public static HijrahDate from(TemporalAccessor temporalAccessor) {
        return HijrahChronology.INSTANCE.date(temporalAccessor);
    }

    private HijrahDate(HijrahChronology hijrahChronology, int i, int i2, int i3) {
        hijrahChronology.getEpochDay(i, i2, i3);
        this.chrono = hijrahChronology;
        this.prolepticYear = i;
        this.monthOfYear = i2;
        this.dayOfMonth = i3;
    }

    private HijrahDate(HijrahChronology hijrahChronology, long j) {
        int[] hijrahDateInfo = hijrahChronology.getHijrahDateInfo((int) j);
        this.chrono = hijrahChronology;
        this.prolepticYear = hijrahDateInfo[0];
        this.monthOfYear = hijrahDateInfo[1];
        this.dayOfMonth = hijrahDateInfo[2];
    }

    public HijrahChronology getChronology() {
        return this.chrono;
    }

    public HijrahEra getEra() {
        return HijrahEra.AH;
    }

    public int lengthOfMonth() {
        return this.chrono.getMonthLength(this.prolepticYear, this.monthOfYear);
    }

    public int lengthOfYear() {
        return this.chrono.getYearLength(this.prolepticYear);
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
                    return ValueRange.of(1L, 5L);
                }
                return getChronology().range(chronoField);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.DAY_OF_MONTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.DAY_OF_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_WEEK_OF_MONTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.DAY_OF_WEEK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.EPOCH_DAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ALIGNED_WEEK_OF_YEAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.MONTH_OF_YEAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.PROLEPTIC_MONTH.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.YEAR_OF_ERA.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.YEAR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.ERA.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public long getLong(TemporalField temporalField) {
        int i;
        int i2;
        if (temporalField instanceof ChronoField) {
            switch (1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()]) {
                case 1:
                    i = this.dayOfMonth;
                    return i;
                case 2:
                    i = getDayOfYear();
                    return i;
                case 3:
                    i2 = (this.dayOfMonth - 1) / 7;
                    i = i2 + 1;
                    return i;
                case 4:
                    i = getDayOfWeek();
                    return i;
                case 5:
                    i2 = (this.dayOfMonth - 1) % 7;
                    i = i2 + 1;
                    return i;
                case 6:
                    i2 = (getDayOfYear() - 1) % 7;
                    i = i2 + 1;
                    return i;
                case 7:
                    return toEpochDay();
                case 8:
                    i2 = (getDayOfYear() - 1) / 7;
                    i = i2 + 1;
                    return i;
                case 9:
                    i = this.monthOfYear;
                    return i;
                case 10:
                    return getProlepticMonth();
                case 11:
                    i = this.prolepticYear;
                    return i;
                case 12:
                    i = this.prolepticYear;
                    return i;
                case 13:
                    i = getEraValue();
                    return i;
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
        }
        return temporalField.getFrom(this);
    }

    private long getProlepticMonth() {
        return ((this.prolepticYear * 12) + this.monthOfYear) - 1;
    }

    public HijrahDate with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            this.chrono.range(chronoField).checkValidValue(j, chronoField);
            int i = (int) j;
            switch (1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()]) {
                case 1:
                    return resolvePreviousValid(this.prolepticYear, this.monthOfYear, i);
                case 2:
                    return plusDays(Math.min(i, lengthOfYear()) - getDayOfYear());
                case 3:
                    return plusDays((j - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH)) * 7);
                case 4:
                    return plusDays(j - getDayOfWeek());
                case 5:
                    return plusDays(j - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case 6:
                    return plusDays(j - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case 7:
                    return new HijrahDate(this.chrono, j);
                case 8:
                    return plusDays((j - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR)) * 7);
                case 9:
                    return resolvePreviousValid(this.prolepticYear, i, this.dayOfMonth);
                case 10:
                    return plusMonths(j - getProlepticMonth());
                case 11:
                    if (this.prolepticYear < 1) {
                        i = 1 - i;
                    }
                    return resolvePreviousValid(i, this.monthOfYear, this.dayOfMonth);
                case 12:
                    return resolvePreviousValid(i, this.monthOfYear, this.dayOfMonth);
                case 13:
                    return resolvePreviousValid(1 - this.prolepticYear, this.monthOfYear, this.dayOfMonth);
                default:
                    throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
        }
        return (HijrahDate) super.with(temporalField, j);
    }

    private HijrahDate resolvePreviousValid(int i, int i2, int i3) {
        int monthLength = this.chrono.getMonthLength(i, i2);
        if (i3 > monthLength) {
            i3 = monthLength;
        }
        return of(this.chrono, i, i2, i3);
    }

    public HijrahDate with(TemporalAdjuster temporalAdjuster) {
        return (HijrahDate) super.with(temporalAdjuster);
    }

    public HijrahDate withVariant(HijrahChronology hijrahChronology) {
        if (this.chrono == hijrahChronology) {
            return this;
        }
        int dayOfYear = hijrahChronology.getDayOfYear(this.prolepticYear, this.monthOfYear);
        int i = this.prolepticYear;
        int i2 = this.monthOfYear;
        int i3 = this.dayOfMonth;
        if (i3 <= dayOfYear) {
            dayOfYear = i3;
        }
        return of(hijrahChronology, i, i2, dayOfYear);
    }

    public HijrahDate plus(TemporalAmount temporalAmount) {
        return (HijrahDate) super.plus(temporalAmount);
    }

    public HijrahDate minus(TemporalAmount temporalAmount) {
        return (HijrahDate) super.minus(temporalAmount);
    }

    public long toEpochDay() {
        return this.chrono.getEpochDay(this.prolepticYear, this.monthOfYear, this.dayOfMonth);
    }

    private int getDayOfYear() {
        return this.chrono.getDayOfYear(this.prolepticYear, this.monthOfYear) + this.dayOfMonth;
    }

    private int getDayOfWeek() {
        return HijrahDate$$ExternalSyntheticBackport0.m(toEpochDay() + 3, 7) + 1;
    }

    private int getEraValue() {
        return this.prolepticYear > 1 ? 1 : 0;
    }

    public boolean isLeapYear() {
        return this.chrono.isLeapYear(this.prolepticYear);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate plusYears(long j) {
        return j == 0 ? this : resolvePreviousValid(HijrahDate$$ExternalSyntheticBackport4.m(this.prolepticYear, (int) j), this.monthOfYear, this.dayOfMonth);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate plusMonths(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.prolepticYear * 12) + (this.monthOfYear - 1) + j;
        return resolvePreviousValid(this.chrono.checkValidYear(HijrahDate$$ExternalSyntheticBackport2.m(j2, 12L)), ((int) HijrahDate$$ExternalSyntheticBackport3.m(j2, 12L)) + 1, this.dayOfMonth);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate plusWeeks(long j) {
        return (HijrahDate) super.plusWeeks(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate plusDays(long j) {
        return new HijrahDate(this.chrono, toEpochDay() + j);
    }

    public HijrahDate plus(long j, TemporalUnit temporalUnit) {
        return (HijrahDate) super.plus(j, temporalUnit);
    }

    public HijrahDate minus(long j, TemporalUnit temporalUnit) {
        return (HijrahDate) super.minus(j, temporalUnit);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate minusYears(long j) {
        return (HijrahDate) super.minusYears(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate minusMonths(long j) {
        return (HijrahDate) super.minusMonths(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate minusWeeks(long j) {
        return (HijrahDate) super.minusWeeks(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HijrahDate minusDays(long j) {
        return (HijrahDate) super.minusDays(j);
    }

    public final ChronoLocalDateTime atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    public ChronoPeriod until(ChronoLocalDate chronoLocalDate) {
        HijrahDate date = getChronology().date((TemporalAccessor) chronoLocalDate);
        long j = ((date.prolepticYear - this.prolepticYear) * 12) + (date.monthOfYear - this.monthOfYear);
        int i = date.dayOfMonth - this.dayOfMonth;
        if (j > 0 && i < 0) {
            j--;
            i = (int) (date.toEpochDay() - plusMonths(j).toEpochDay());
        } else if (j < 0 && i > 0) {
            j++;
            i -= date.lengthOfMonth();
        }
        return getChronology().period(HijrahDate$$ExternalSyntheticBackport1.m(j / 12), (int) (j % 12), i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof HijrahDate) {
            HijrahDate hijrahDate = (HijrahDate) obj;
            if (this.prolepticYear == hijrahDate.prolepticYear && this.monthOfYear == hijrahDate.monthOfYear && this.dayOfMonth == hijrahDate.dayOfMonth && getChronology().equals(hijrahDate.getChronology())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.prolepticYear;
        int i2 = this.monthOfYear;
        int i3 = this.dayOfMonth;
        return (((i << 11) + (i2 << 6)) + i3) ^ (getChronology().getId().hashCode() ^ (i & (-2048)));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser((byte) 6, this);
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(getChronology());
        objectOutput.writeInt(get(ChronoField.YEAR));
        objectOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
        objectOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
    }

    static HijrahDate readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return ((HijrahChronology) objectInput.readObject()).date(objectInput.readInt(), (int) objectInput.readByte(), (int) objectInput.readByte());
    }
}
