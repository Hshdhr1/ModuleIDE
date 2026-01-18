package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class ChronoPeriodImpl implements ChronoPeriod, Serializable {
    private static final List SUPPORTED_UNITS = ChronoPeriodImpl$$ExternalSyntheticBackport1.m(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS);
    private static final long serialVersionUID = 57387258289L;
    private final Chronology chrono;
    final int days;
    final int months;
    final int years;

    public /* synthetic */ ChronoPeriod negated() {
        return ChronoPeriod.-CC.$default$negated(this);
    }

    ChronoPeriodImpl(Chronology chronology, int i, int i2, int i3) {
        ChronoPeriodImpl$$ExternalSyntheticBackport4.m(chronology, "chrono");
        this.chrono = chronology;
        this.years = i;
        this.months = i2;
        this.days = i3;
    }

    public long get(TemporalUnit temporalUnit) {
        int i;
        if (temporalUnit == ChronoUnit.YEARS) {
            i = this.years;
        } else if (temporalUnit == ChronoUnit.MONTHS) {
            i = this.months;
        } else if (temporalUnit == ChronoUnit.DAYS) {
            i = this.days;
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
        }
        return i;
    }

    public List getUnits() {
        return SUPPORTED_UNITS;
    }

    public Chronology getChronology() {
        return this.chrono;
    }

    public boolean isZero() {
        return this.years == 0 && this.months == 0 && this.days == 0;
    }

    public boolean isNegative() {
        return this.years < 0 || this.months < 0 || this.days < 0;
    }

    public ChronoPeriod plus(TemporalAmount temporalAmount) {
        ChronoPeriodImpl validateAmount = validateAmount(temporalAmount);
        return new ChronoPeriodImpl(this.chrono, ChronoPeriodImpl$$ExternalSyntheticBackport5.m(this.years, validateAmount.years), ChronoPeriodImpl$$ExternalSyntheticBackport5.m(this.months, validateAmount.months), ChronoPeriodImpl$$ExternalSyntheticBackport5.m(this.days, validateAmount.days));
    }

    public ChronoPeriod minus(TemporalAmount temporalAmount) {
        ChronoPeriodImpl validateAmount = validateAmount(temporalAmount);
        return new ChronoPeriodImpl(this.chrono, ChronoPeriodImpl$$ExternalSyntheticBackport2.m(this.years, validateAmount.years), ChronoPeriodImpl$$ExternalSyntheticBackport2.m(this.months, validateAmount.months), ChronoPeriodImpl$$ExternalSyntheticBackport2.m(this.days, validateAmount.days));
    }

    private ChronoPeriodImpl validateAmount(TemporalAmount temporalAmount) {
        ChronoPeriodImpl$$ExternalSyntheticBackport4.m(temporalAmount, "amount");
        if (!(temporalAmount instanceof ChronoPeriodImpl)) {
            throw new DateTimeException("Unable to obtain ChronoPeriod from TemporalAmount: " + temporalAmount.getClass());
        }
        ChronoPeriodImpl chronoPeriodImpl = (ChronoPeriodImpl) temporalAmount;
        if (this.chrono.equals(chronoPeriodImpl.getChronology())) {
            return chronoPeriodImpl;
        }
        throw new ClassCastException("Chronology mismatch, expected: " + this.chrono.getId() + ", actual: " + chronoPeriodImpl.getChronology().getId());
    }

    public ChronoPeriod multipliedBy(int i) {
        return (isZero() || i == 1) ? this : new ChronoPeriodImpl(this.chrono, ChronoPeriodImpl$$ExternalSyntheticBackport3.m(this.years, i), ChronoPeriodImpl$$ExternalSyntheticBackport3.m(this.months, i), ChronoPeriodImpl$$ExternalSyntheticBackport3.m(this.days, i));
    }

    public ChronoPeriod normalized() {
        long monthRange = monthRange();
        if (monthRange > 0) {
            int i = this.years;
            int i2 = this.months;
            long j = (i * monthRange) + i2;
            long j2 = j / monthRange;
            int i3 = (int) (j % monthRange);
            if (j2 != i || i3 != i2) {
                return new ChronoPeriodImpl(this.chrono, ChronoPeriodImpl$$ExternalSyntheticBackport6.m(j2), i3, this.days);
            }
        }
        return this;
    }

    private long monthRange() {
        ValueRange range = this.chrono.range(ChronoField.MONTH_OF_YEAR);
        if (range.isFixed() && range.isIntValue()) {
            return (range.getMaximum() - range.getMinimum()) + 1;
        }
        return -1L;
    }

    public Temporal addTo(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.plus(i, ChronoUnit.YEARS);
            }
        } else {
            long monthRange = monthRange();
            if (monthRange > 0) {
                temporal = temporal.plus((this.years * monthRange) + this.months, ChronoUnit.MONTHS);
            } else {
                int i2 = this.years;
                if (i2 != 0) {
                    temporal = temporal.plus(i2, ChronoUnit.YEARS);
                }
                temporal = temporal.plus(this.months, ChronoUnit.MONTHS);
            }
        }
        int i3 = this.days;
        return i3 != 0 ? temporal.plus(i3, ChronoUnit.DAYS) : temporal;
    }

    public Temporal subtractFrom(Temporal temporal) {
        validateChrono(temporal);
        if (this.months == 0) {
            int i = this.years;
            if (i != 0) {
                temporal = temporal.minus(i, ChronoUnit.YEARS);
            }
        } else {
            long monthRange = monthRange();
            if (monthRange > 0) {
                temporal = temporal.minus((this.years * monthRange) + this.months, ChronoUnit.MONTHS);
            } else {
                int i2 = this.years;
                if (i2 != 0) {
                    temporal = temporal.minus(i2, ChronoUnit.YEARS);
                }
                temporal = temporal.minus(this.months, ChronoUnit.MONTHS);
            }
        }
        int i3 = this.days;
        return i3 != 0 ? temporal.minus(i3, ChronoUnit.DAYS) : temporal;
    }

    private void validateChrono(TemporalAccessor temporalAccessor) {
        ChronoPeriodImpl$$ExternalSyntheticBackport4.m(temporalAccessor, "temporal");
        Chronology chronology = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
        if (chronology == null || this.chrono.equals(chronology)) {
            return;
        }
        throw new DateTimeException("Chronology mismatch, expected: " + this.chrono.getId() + ", actual: " + chronology.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChronoPeriodImpl) {
            ChronoPeriodImpl chronoPeriodImpl = (ChronoPeriodImpl) obj;
            if (this.years == chronoPeriodImpl.years && this.months == chronoPeriodImpl.months && this.days == chronoPeriodImpl.days && this.chrono.equals(chronoPeriodImpl.chrono)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.years + Integer.rotateLeft(this.months, 8)) + Integer.rotateLeft(this.days, 16)) ^ this.chrono.hashCode();
    }

    public String toString() {
        if (isZero()) {
            return getChronology().toString() + " P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getChronology().toString());
        sb.append(" P");
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

    protected Object writeReplace() {
        return new Ser((byte) 9, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.chrono.getId());
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }

    static ChronoPeriodImpl readExternal(DataInput dataInput) throws IOException {
        return new ChronoPeriodImpl(Chronology.-CC.of(dataInput.readUTF()), dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }
}
