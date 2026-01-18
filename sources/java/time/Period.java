package java.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Period implements ChronoPeriod, Serializable {
    private static final long serialVersionUID = -3587258372562876L;
    private final int days;
    private final int months;
    private final int years;
    public static final Period ZERO = new Period(0, 0, 0);
    private static final Pattern PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
    private static final List SUPPORTED_UNITS = Period$$ExternalSyntheticBackport7.m(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS);

    public static Period ofYears(int i) {
        return create(i, 0, 0);
    }

    public static Period ofMonths(int i) {
        return create(0, i, 0);
    }

    public static Period ofWeeks(int i) {
        return create(0, 0, Period$$ExternalSyntheticBackport2.m(i, 7));
    }

    public static Period ofDays(int i) {
        return create(0, 0, i);
    }

    public static Period of(int i, int i2, int i3) {
        return create(i, i2, i3);
    }

    public static Period from(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            return (Period) temporalAmount;
        }
        if ((temporalAmount instanceof ChronoPeriod) && !IsoChronology.INSTANCE.equals(((ChronoPeriod) temporalAmount).getChronology())) {
            throw new DateTimeException("Period requires ISO chronology: " + temporalAmount);
        }
        Period$$ExternalSyntheticBackport4.m(temporalAmount, "amount");
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (TemporalUnit temporalUnit : temporalAmount.getUnits()) {
            long j = temporalAmount.get(temporalUnit);
            if (temporalUnit == ChronoUnit.YEARS) {
                i = Period$$ExternalSyntheticBackport0.m(j);
            } else if (temporalUnit == ChronoUnit.MONTHS) {
                i2 = Period$$ExternalSyntheticBackport0.m(j);
            } else if (temporalUnit == ChronoUnit.DAYS) {
                i3 = Period$$ExternalSyntheticBackport0.m(j);
            } else {
                throw new DateTimeException("Unit must be Years, Months or Days, but was " + temporalUnit);
            }
        }
        return create(i, i2, i3);
    }

    public static Period parse(CharSequence charSequence) {
        Period$$ExternalSyntheticBackport4.m(charSequence, "text");
        Matcher matcher = PATTERN.matcher(charSequence);
        if (matcher.matches()) {
            int i = charMatch(charSequence, matcher.start(1), matcher.end(1), '-') ? -1 : 1;
            int start = matcher.start(2);
            int end = matcher.end(2);
            int start2 = matcher.start(3);
            int end2 = matcher.end(3);
            int start3 = matcher.start(4);
            int end3 = matcher.end(4);
            int start4 = matcher.start(5);
            int end4 = matcher.end(5);
            if (start >= 0 || start2 >= 0 || start3 >= 0 || start4 >= 0) {
                try {
                    return create(parseNumber(charSequence, start, end, i), parseNumber(charSequence, start2, end2, i), Period$$ExternalSyntheticBackport8.m(parseNumber(charSequence, start4, end4, i), Period$$ExternalSyntheticBackport2.m(parseNumber(charSequence, start3, end3, i), 7)));
                } catch (NumberFormatException e) {
                    throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, e);
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0);
    }

    private static boolean charMatch(CharSequence charSequence, int i, int i2, char c) {
        return i >= 0 && i2 == i + 1 && charSequence.charAt(i) == c;
    }

    private static int parseNumber(CharSequence charSequence, int i, int i2, int i3) {
        if (i < 0 || i2 < 0) {
            return 0;
        }
        if (charSequence.charAt(i) == '+') {
            i++;
        }
        try {
            return Period$$ExternalSyntheticBackport2.m(Period$$ExternalSyntheticBackport1.m(charSequence, i, i2, 10), i3);
        } catch (ArithmeticException e) {
            throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, e);
        }
    }

    public static Period between(LocalDate localDate, LocalDate localDate2) {
        return localDate.until((ChronoLocalDate) localDate2);
    }

    private static Period create(int i, int i2, int i3) {
        if ((i | i2 | i3) == 0) {
            return ZERO;
        }
        return new Period(i, i2, i3);
    }

    private Period(int i, int i2, int i3) {
        this.years = i;
        this.months = i2;
        this.days = i3;
    }

    public long get(TemporalUnit temporalUnit) {
        int days;
        if (temporalUnit == ChronoUnit.YEARS) {
            days = getYears();
        } else if (temporalUnit == ChronoUnit.MONTHS) {
            days = getMonths();
        } else if (temporalUnit == ChronoUnit.DAYS) {
            days = getDays();
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
        }
        return days;
    }

    public List getUnits() {
        return SUPPORTED_UNITS;
    }

    public IsoChronology getChronology() {
        return IsoChronology.INSTANCE;
    }

    public boolean isZero() {
        return this == ZERO;
    }

    public boolean isNegative() {
        return this.years < 0 || this.months < 0 || this.days < 0;
    }

    public int getYears() {
        return this.years;
    }

    public int getMonths() {
        return this.months;
    }

    public int getDays() {
        return this.days;
    }

    public Period withYears(int i) {
        return i == this.years ? this : create(i, this.months, this.days);
    }

    public Period withMonths(int i) {
        return i == this.months ? this : create(this.years, i, this.days);
    }

    public Period withDays(int i) {
        return i == this.days ? this : create(this.years, this.months, i);
    }

    public Period plus(TemporalAmount temporalAmount) {
        Period from = from(temporalAmount);
        return create(Period$$ExternalSyntheticBackport8.m(this.years, from.years), Period$$ExternalSyntheticBackport8.m(this.months, from.months), Period$$ExternalSyntheticBackport8.m(this.days, from.days));
    }

    public Period plusYears(long j) {
        return j == 0 ? this : create(Period$$ExternalSyntheticBackport0.m(Period$$ExternalSyntheticBackport3.m(this.years, j)), this.months, this.days);
    }

    public Period plusMonths(long j) {
        return j == 0 ? this : create(this.years, Period$$ExternalSyntheticBackport0.m(Period$$ExternalSyntheticBackport3.m(this.months, j)), this.days);
    }

    public Period plusDays(long j) {
        return j == 0 ? this : create(this.years, this.months, Period$$ExternalSyntheticBackport0.m(Period$$ExternalSyntheticBackport3.m(this.days, j)));
    }

    public Period minus(TemporalAmount temporalAmount) {
        Period from = from(temporalAmount);
        return create(Period$$ExternalSyntheticBackport6.m(this.years, from.years), Period$$ExternalSyntheticBackport6.m(this.months, from.months), Period$$ExternalSyntheticBackport6.m(this.days, from.days));
    }

    public Period minusYears(long j) {
        return j == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-j);
    }

    public Period minusMonths(long j) {
        return j == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-j);
    }

    public Period minusDays(long j) {
        return j == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-j);
    }

    public Period multipliedBy(int i) {
        return (this == ZERO || i == 1) ? this : create(Period$$ExternalSyntheticBackport2.m(this.years, i), Period$$ExternalSyntheticBackport2.m(this.months, i), Period$$ExternalSyntheticBackport2.m(this.days, i));
    }

    public Period negated() {
        return multipliedBy(-1);
    }

    public Period normalized() {
        long totalMonths = toTotalMonths();
        long j = totalMonths / 12;
        int i = (int) (totalMonths % 12);
        return (j == ((long) this.years) && i == this.months) ? this : create(Period$$ExternalSyntheticBackport0.m(j), i, this.days);
    }

    public long toTotalMonths() {
        return (this.years * 12) + this.months;
    }

    public Temporal addTo(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.plus(i, ChronoUnit.YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.plus(totalMonths, ChronoUnit.MONTHS);
            }
        }
        int i2 = this.days;
        return i2 != 0 ? temporal.plus(i2, ChronoUnit.DAYS) : temporal;
    }

    public Temporal subtractFrom(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.minus(i, ChronoUnit.YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.minus(totalMonths, ChronoUnit.MONTHS);
            }
        }
        int i2 = this.days;
        return i2 != 0 ? temporal.minus(i2, ChronoUnit.DAYS) : temporal;
    }

    private void validateChrono(TemporalAccessor temporalAccessor) {
        Period$$ExternalSyntheticBackport4.m(temporalAccessor, "temporal");
        Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
        if (chronology == null || IsoChronology.INSTANCE.equals(chronology)) {
            return;
        }
        throw new DateTimeException("Chronology mismatch, expected: ISO, actual: " + chronology.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Period) {
            Period period = (Period) obj;
            if (this.years == period.years && this.months == period.months && this.days == period.days) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16);
    }

    public String toString() {
        if (this == ZERO) {
            return "P0D";
        }
        StringBuilder sb = new StringBuilder("P");
        int i = this.years;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.months;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.days;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    private Object writeReplace() {
        return new Ser((byte) 14, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }

    static Period readExternal(DataInput dataInput) throws IOException {
        return of(dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }
}
