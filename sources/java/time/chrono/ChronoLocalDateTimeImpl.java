package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class ChronoLocalDateTimeImpl implements ChronoLocalDateTime, Temporal, TemporalAdjuster, Serializable {
    static final int HOURS_PER_DAY = 24;
    static final long MICROS_PER_DAY = 86400000000L;
    static final long MILLIS_PER_DAY = 86400000;
    static final int MINUTES_PER_DAY = 1440;
    static final int MINUTES_PER_HOUR = 60;
    static final long NANOS_PER_DAY = 86400000000000L;
    static final long NANOS_PER_HOUR = 3600000000000L;
    static final long NANOS_PER_MINUTE = 60000000000L;
    static final long NANOS_PER_SECOND = 1000000000;
    static final int SECONDS_PER_DAY = 86400;
    static final int SECONDS_PER_HOUR = 3600;
    static final int SECONDS_PER_MINUTE = 60;
    private static final long serialVersionUID = 4556003607393004514L;
    private final transient ChronoLocalDate date;
    private final transient LocalTime time;

    public /* synthetic */ Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDateTime.-CC.$default$adjustInto(this, temporal);
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return ChronoLocalDateTime.-CC.$default$compareTo(this, obj);
    }

    public /* synthetic */ int compareTo(ChronoLocalDateTime chronoLocalDateTime) {
        return ChronoLocalDateTime.-CC.$default$compareTo((ChronoLocalDateTime) this, chronoLocalDateTime);
    }

    public /* synthetic */ String format(DateTimeFormatter dateTimeFormatter) {
        return ChronoLocalDateTime.-CC.$default$format(this, dateTimeFormatter);
    }

    public /* synthetic */ Chronology getChronology() {
        return ChronoLocalDateTime.-CC.$default$getChronology(this);
    }

    public /* synthetic */ boolean isAfter(ChronoLocalDateTime chronoLocalDateTime) {
        return ChronoLocalDateTime.-CC.$default$isAfter(this, chronoLocalDateTime);
    }

    public /* synthetic */ boolean isBefore(ChronoLocalDateTime chronoLocalDateTime) {
        return ChronoLocalDateTime.-CC.$default$isBefore(this, chronoLocalDateTime);
    }

    public /* synthetic */ boolean isEqual(ChronoLocalDateTime chronoLocalDateTime) {
        return ChronoLocalDateTime.-CC.$default$isEqual(this, chronoLocalDateTime);
    }

    public /* synthetic */ boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoLocalDateTime.-CC.$default$isSupported(this, temporalUnit);
    }

    public /* synthetic */ ChronoLocalDateTime minus(long j, TemporalUnit temporalUnit) {
        return ChronoLocalDateTime.-CC.$default$minus((ChronoLocalDateTime) this, j, temporalUnit);
    }

    public /* synthetic */ ChronoLocalDateTime minus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTime.-CC.$default$minus((ChronoLocalDateTime) this, temporalAmount);
    }

    public /* bridge */ /* synthetic */ Temporal minus(long j, TemporalUnit temporalUnit) {
        return ChronoLocalDateTime.-CC.$default$minus((ChronoLocalDateTime) this, j, temporalUnit);
    }

    public /* bridge */ /* synthetic */ Temporal minus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTime.-CC.$default$minus((ChronoLocalDateTime) this, temporalAmount);
    }

    public /* synthetic */ ChronoLocalDateTime plus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTime.-CC.$default$plus((ChronoLocalDateTime) this, temporalAmount);
    }

    public /* bridge */ /* synthetic */ Temporal plus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTime.-CC.$default$plus((ChronoLocalDateTime) this, temporalAmount);
    }

    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return ChronoLocalDateTime.-CC.$default$query(this, temporalQuery);
    }

    public /* synthetic */ long toEpochSecond(ZoneOffset zoneOffset) {
        return ChronoLocalDateTime.-CC.$default$toEpochSecond(this, zoneOffset);
    }

    public /* synthetic */ Instant toInstant(ZoneOffset zoneOffset) {
        return ChronoLocalDateTime.-CC.$default$toInstant(this, zoneOffset);
    }

    static ChronoLocalDateTimeImpl of(ChronoLocalDate chronoLocalDate, LocalTime localTime) {
        return new ChronoLocalDateTimeImpl(chronoLocalDate, localTime);
    }

    static ChronoLocalDateTimeImpl ensureValid(Chronology chronology, Temporal temporal) {
        ChronoLocalDateTimeImpl chronoLocalDateTimeImpl = (ChronoLocalDateTimeImpl) temporal;
        if (chronology.equals(chronoLocalDateTimeImpl.getChronology())) {
            return chronoLocalDateTimeImpl;
        }
        throw new ClassCastException("Chronology mismatch, required: " + chronology.getId() + ", actual: " + chronoLocalDateTimeImpl.getChronology().getId());
    }

    private ChronoLocalDateTimeImpl(ChronoLocalDate chronoLocalDate, LocalTime localTime) {
        ChronoLocalDateTimeImpl$$ExternalSyntheticBackport3.m(chronoLocalDate, "date");
        ChronoLocalDateTimeImpl$$ExternalSyntheticBackport3.m(localTime, "time");
        this.date = chronoLocalDate;
        this.time = localTime;
    }

    private ChronoLocalDateTimeImpl with(Temporal temporal, LocalTime localTime) {
        ChronoLocalDate chronoLocalDate = this.date;
        return (chronoLocalDate == temporal && this.time == localTime) ? this : new ChronoLocalDateTimeImpl(ChronoLocalDateImpl.ensureValid(chronoLocalDate.getChronology(), temporal), localTime);
    }

    public ChronoLocalDate toLocalDate() {
        return this.date;
    }

    public LocalTime toLocalTime() {
        return this.time;
    }

    public boolean isSupported(TemporalField temporalField) {
        if (!(temporalField instanceof ChronoField)) {
            return temporalField != null && temporalField.isSupportedBy(this);
        }
        ChronoField chronoField = (ChronoField) temporalField;
        return chronoField.isDateBased() || chronoField.isTimeBased();
    }

    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return ((ChronoField) temporalField).isTimeBased() ? this.time.range(temporalField) : this.date.range(temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return ((ChronoField) temporalField).isTimeBased() ? this.time.get(temporalField) : this.date.get(temporalField);
        }
        return range(temporalField).checkValidIntValue(getLong(temporalField), temporalField);
    }

    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return ((ChronoField) temporalField).isTimeBased() ? this.time.getLong(temporalField) : this.date.getLong(temporalField);
        }
        return temporalField.getFrom(this);
    }

    public ChronoLocalDateTimeImpl with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof ChronoLocalDate) {
            return with((ChronoLocalDate) temporalAdjuster, this.time);
        }
        if (temporalAdjuster instanceof LocalTime) {
            return with(this.date, (LocalTime) temporalAdjuster);
        }
        if (temporalAdjuster instanceof ChronoLocalDateTimeImpl) {
            return ensureValid(this.date.getChronology(), (ChronoLocalDateTimeImpl) temporalAdjuster);
        }
        return ensureValid(this.date.getChronology(), (ChronoLocalDateTimeImpl) temporalAdjuster.adjustInto(this));
    }

    public ChronoLocalDateTimeImpl with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            if (((ChronoField) temporalField).isTimeBased()) {
                return with(this.date, this.time.with(temporalField, j));
            }
            return with(this.date.with(temporalField, j), this.time);
        }
        return ensureValid(this.date.getChronology(), temporalField.adjustInto(this, j));
    }

    public ChronoLocalDateTimeImpl plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return plusNanos(j);
                case 2:
                    return plusDays(j / 86400000000L).plusNanos((j % 86400000000L) * 1000);
                case 3:
                    return plusDays(j / 86400000).plusNanos((j % 86400000) * 1000000);
                case 4:
                    return plusSeconds(j);
                case 5:
                    return plusMinutes(j);
                case 6:
                    return plusHours(j);
                case 7:
                    return plusDays(j / 256).plusHours((j % 256) * 12);
                default:
                    return with(this.date.plus(j, temporalUnit), this.time);
            }
        }
        return ensureValid(this.date.getChronology(), temporalUnit.addTo(this, j));
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;

        static {
            int[] iArr = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr;
            try {
                iArr[ChronoUnit.NANOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MICROS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MILLIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HALF_DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private ChronoLocalDateTimeImpl plusDays(long j) {
        return with(this.date.plus(j, (TemporalUnit) ChronoUnit.DAYS), this.time);
    }

    private ChronoLocalDateTimeImpl plusHours(long j) {
        return plusWithOverflow(this.date, j, 0L, 0L, 0L);
    }

    private ChronoLocalDateTimeImpl plusMinutes(long j) {
        return plusWithOverflow(this.date, 0L, j, 0L, 0L);
    }

    ChronoLocalDateTimeImpl plusSeconds(long j) {
        return plusWithOverflow(this.date, 0L, 0L, j, 0L);
    }

    private ChronoLocalDateTimeImpl plusNanos(long j) {
        return plusWithOverflow(this.date, 0L, 0L, 0L, j);
    }

    private ChronoLocalDateTimeImpl plusWithOverflow(ChronoLocalDate chronoLocalDate, long j, long j2, long j3, long j4) {
        if ((j | j2 | j3 | j4) == 0) {
            return with(chronoLocalDate, this.time);
        }
        long nanoOfDay = this.time.toNanoOfDay();
        long j5 = (j4 % 86400000000000L) + ((j3 % 86400) * 1000000000) + ((j2 % 1440) * 60000000000L) + ((j % 24) * 3600000000000L) + nanoOfDay;
        long m = (j4 / 86400000000000L) + (j3 / 86400) + (j2 / 1440) + (j / 24) + ChronoLocalDateTimeImpl$$ExternalSyntheticBackport0.m(j5, 86400000000000L);
        long m2 = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport1.m(j5, 86400000000000L);
        return with(chronoLocalDate.plus(m, (TemporalUnit) ChronoUnit.DAYS), m2 == nanoOfDay ? this.time : LocalTime.ofNanoOfDay(m2));
    }

    public ChronoZonedDateTime atZone(ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofBest(this, zoneId, null);
    }

    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        ChronoLocalDateTimeImpl$$ExternalSyntheticBackport3.m(temporal, "endExclusive");
        ChronoLocalDateTime localDateTime = getChronology().localDateTime(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            if (temporalUnit.isTimeBased()) {
                long j = localDateTime.getLong(ChronoField.EPOCH_DAY) - this.date.getLong(ChronoField.EPOCH_DAY);
                switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) temporalUnit).ordinal()]) {
                    case 1:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport2.m(j, 86400000000000L);
                        break;
                    case 2:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport2.m(j, 86400000000L);
                        break;
                    case 3:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport2.m(j, 86400000L);
                        break;
                    case 4:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport4.m(j, 86400);
                        break;
                    case 5:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport5.m(j, 1440);
                        break;
                    case 6:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport6.m(j, 24);
                        break;
                    case 7:
                        j = ChronoLocalDateTimeImpl$$ExternalSyntheticBackport7.m(j, 2);
                        break;
                }
                return ChronoLocalDateTimeImpl$$ExternalSyntheticBackport8.m(j, this.time.until(localDateTime.toLocalTime(), temporalUnit));
            }
            ChronoLocalDate localDate = localDateTime.toLocalDate();
            if (localDateTime.toLocalTime().isBefore(this.time)) {
                localDate = localDate.minus(1L, (TemporalUnit) ChronoUnit.DAYS);
            }
            return this.date.until(localDate, temporalUnit);
        }
        ChronoLocalDateTimeImpl$$ExternalSyntheticBackport3.m(temporalUnit, "unit");
        return temporalUnit.between(this, localDateTime);
    }

    private Object writeReplace() {
        return new Ser((byte) 2, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.date);
        objectOutput.writeObject(this.time);
    }

    static ChronoLocalDateTime readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return ((ChronoLocalDate) objectInput.readObject()).atTime((LocalTime) objectInput.readObject());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoLocalDateTime) && compareTo((ChronoLocalDateTime) obj) == 0;
    }

    public int hashCode() {
        return toLocalDate().hashCode() ^ toLocalTime().hashCode();
    }

    public String toString() {
        return toLocalDate().toString() + "T" + toLocalTime().toString();
    }
}
