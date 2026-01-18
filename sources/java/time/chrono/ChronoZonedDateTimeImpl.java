package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
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
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class ChronoZonedDateTimeImpl implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;
    private final transient ChronoLocalDateTimeImpl dateTime;
    private final transient ZoneOffset offset;
    private final transient ZoneId zone;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return ChronoZonedDateTime.-CC.$default$compareTo(this, obj);
    }

    public /* synthetic */ int compareTo(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.-CC.$default$compareTo((ChronoZonedDateTime) this, chronoZonedDateTime);
    }

    public /* synthetic */ String format(DateTimeFormatter dateTimeFormatter) {
        return ChronoZonedDateTime.-CC.$default$format(this, dateTimeFormatter);
    }

    public /* synthetic */ int get(TemporalField temporalField) {
        return ChronoZonedDateTime.-CC.$default$get(this, temporalField);
    }

    public /* synthetic */ Chronology getChronology() {
        return ChronoZonedDateTime.-CC.$default$getChronology(this);
    }

    public /* synthetic */ long getLong(TemporalField temporalField) {
        return ChronoZonedDateTime.-CC.$default$getLong(this, temporalField);
    }

    public /* synthetic */ boolean isAfter(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.-CC.$default$isAfter(this, chronoZonedDateTime);
    }

    public /* synthetic */ boolean isBefore(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.-CC.$default$isBefore(this, chronoZonedDateTime);
    }

    public /* synthetic */ boolean isEqual(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.-CC.$default$isEqual(this, chronoZonedDateTime);
    }

    public /* synthetic */ boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoZonedDateTime.-CC.$default$isSupported(this, temporalUnit);
    }

    public /* synthetic */ ChronoZonedDateTime minus(long j, TemporalUnit temporalUnit) {
        return ChronoZonedDateTime.-CC.$default$minus((ChronoZonedDateTime) this, j, temporalUnit);
    }

    public /* synthetic */ ChronoZonedDateTime minus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTime.-CC.$default$minus((ChronoZonedDateTime) this, temporalAmount);
    }

    public /* bridge */ /* synthetic */ Temporal minus(long j, TemporalUnit temporalUnit) {
        return ChronoZonedDateTime.-CC.$default$minus((ChronoZonedDateTime) this, j, temporalUnit);
    }

    public /* bridge */ /* synthetic */ Temporal minus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTime.-CC.$default$minus((ChronoZonedDateTime) this, temporalAmount);
    }

    public /* synthetic */ ChronoZonedDateTime plus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTime.-CC.$default$plus((ChronoZonedDateTime) this, temporalAmount);
    }

    public /* bridge */ /* synthetic */ Temporal plus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTime.-CC.$default$plus((ChronoZonedDateTime) this, temporalAmount);
    }

    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return ChronoZonedDateTime.-CC.$default$query(this, temporalQuery);
    }

    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return ChronoZonedDateTime.-CC.$default$range(this, temporalField);
    }

    public /* synthetic */ long toEpochSecond() {
        return ChronoZonedDateTime.-CC.$default$toEpochSecond(this);
    }

    public /* synthetic */ Instant toInstant() {
        return ChronoZonedDateTime.-CC.$default$toInstant(this);
    }

    public /* synthetic */ ChronoLocalDate toLocalDate() {
        return ChronoZonedDateTime.-CC.$default$toLocalDate(this);
    }

    public /* synthetic */ LocalTime toLocalTime() {
        return ChronoZonedDateTime.-CC.$default$toLocalTime(this);
    }

    public /* synthetic */ ChronoZonedDateTime with(TemporalAdjuster temporalAdjuster) {
        return ChronoZonedDateTime.-CC.$default$with((ChronoZonedDateTime) this, temporalAdjuster);
    }

    public /* bridge */ /* synthetic */ Temporal with(TemporalAdjuster temporalAdjuster) {
        return ChronoZonedDateTime.-CC.$default$with((ChronoZonedDateTime) this, temporalAdjuster);
    }

    static ChronoZonedDateTime ofBest(ChronoLocalDateTimeImpl chronoLocalDateTimeImpl, ZoneId zoneId, ZoneOffset zoneOffset) {
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(chronoLocalDateTimeImpl, "localDateTime");
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ChronoZonedDateTimeImpl(chronoLocalDateTimeImpl, (ZoneOffset) zoneId, zoneId);
        }
        ZoneRules rules = zoneId.getRules();
        LocalDateTime from = LocalDateTime.from(chronoLocalDateTimeImpl);
        List validOffsets = rules.getValidOffsets(from);
        if (validOffsets.size() == 1) {
            zoneOffset = (ZoneOffset) validOffsets.get(0);
        } else if (validOffsets.size() == 0) {
            ZoneOffsetTransition transition = rules.getTransition(from);
            chronoLocalDateTimeImpl = chronoLocalDateTimeImpl.plusSeconds(transition.getDuration().getSeconds());
            zoneOffset = transition.getOffsetAfter();
        } else if (zoneOffset == null || !validOffsets.contains(zoneOffset)) {
            zoneOffset = (ZoneOffset) validOffsets.get(0);
        }
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
        return new ChronoZonedDateTimeImpl(chronoLocalDateTimeImpl, zoneOffset, zoneId);
    }

    static ChronoZonedDateTimeImpl ofInstant(Chronology chronology, Instant instant, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(offset, "offset");
        return new ChronoZonedDateTimeImpl((ChronoLocalDateTimeImpl) chronology.localDateTime(LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), offset)), offset, zoneId);
    }

    private ChronoZonedDateTimeImpl create(Instant instant, ZoneId zoneId) {
        return ofInstant(getChronology(), instant, zoneId);
    }

    static ChronoZonedDateTimeImpl ensureValid(Chronology chronology, Temporal temporal) {
        ChronoZonedDateTimeImpl chronoZonedDateTimeImpl = (ChronoZonedDateTimeImpl) temporal;
        if (chronology.equals(chronoZonedDateTimeImpl.getChronology())) {
            return chronoZonedDateTimeImpl;
        }
        throw new ClassCastException("Chronology mismatch, required: " + chronology.getId() + ", actual: " + chronoZonedDateTimeImpl.getChronology().getId());
    }

    private ChronoZonedDateTimeImpl(ChronoLocalDateTimeImpl chronoLocalDateTimeImpl, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.dateTime = (ChronoLocalDateTimeImpl) ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(chronoLocalDateTimeImpl, "dateTime");
        this.offset = (ZoneOffset) ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
        this.zone = (ZoneId) ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(zoneId, "zone");
    }

    public ZoneOffset getOffset() {
        return this.offset;
    }

    public ChronoZonedDateTime withEarlierOffsetAtOverlap() {
        ZoneOffsetTransition transition = getZone().getRules().getTransition(LocalDateTime.from(this));
        if (transition != null && transition.isOverlap()) {
            ZoneOffset offsetBefore = transition.getOffsetBefore();
            if (!offsetBefore.equals(this.offset)) {
                return new ChronoZonedDateTimeImpl(this.dateTime, offsetBefore, this.zone);
            }
        }
        return this;
    }

    public ChronoZonedDateTime withLaterOffsetAtOverlap() {
        ZoneOffsetTransition transition = getZone().getRules().getTransition(LocalDateTime.from(this));
        if (transition != null) {
            ZoneOffset offsetAfter = transition.getOffsetAfter();
            if (!offsetAfter.equals(getOffset())) {
                return new ChronoZonedDateTimeImpl(this.dateTime, offsetAfter, this.zone);
            }
        }
        return this;
    }

    public ChronoLocalDateTime toLocalDateTime() {
        return this.dateTime;
    }

    public ZoneId getZone() {
        return this.zone;
    }

    public ChronoZonedDateTime withZoneSameLocal(ZoneId zoneId) {
        return ofBest(this.dateTime, zoneId, this.offset);
    }

    public ChronoZonedDateTime withZoneSameInstant(ZoneId zoneId) {
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(zoneId, "zone");
        return this.zone.equals(zoneId) ? this : create(this.dateTime.toInstant(this.offset), zoneId);
    }

    public boolean isSupported(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return true;
        }
        return temporalField != null && temporalField.isSupportedBy(this);
    }

    public ChronoZonedDateTime with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            int i = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (i == 1) {
                return plus(j - toEpochSecond(), (TemporalUnit) ChronoUnit.SECONDS);
            }
            if (i == 2) {
                return create(this.dateTime.toInstant(ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(j))), this.zone);
            }
            return ofBest(this.dateTime.with(temporalField, j), this.zone, this.offset);
        }
        return ensureValid(getChronology(), temporalField.adjustInto(this, j));
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

    public ChronoZonedDateTime plus(long j, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return with((TemporalAdjuster) this.dateTime.plus(j, temporalUnit));
        }
        return ensureValid(getChronology(), temporalUnit.addTo(this, j));
    }

    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(temporal, "endExclusive");
        ChronoZonedDateTime zonedDateTime = getChronology().zonedDateTime(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            return this.dateTime.until(zonedDateTime.withZoneSameInstant(this.offset).toLocalDateTime(), temporalUnit);
        }
        ChronoZonedDateTimeImpl$$ExternalSyntheticBackport0.m(temporalUnit, "unit");
        return temporalUnit.between(this, zonedDateTime);
    }

    private Object writeReplace() {
        return new Ser((byte) 3, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.dateTime);
        objectOutput.writeObject(this.offset);
        objectOutput.writeObject(this.zone);
    }

    static ChronoZonedDateTime readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        ChronoLocalDateTime chronoLocalDateTime = (ChronoLocalDateTime) objectInput.readObject();
        ZoneOffset zoneOffset = (ZoneOffset) objectInput.readObject();
        return chronoLocalDateTime.atZone(zoneOffset).withZoneSameLocal((ZoneId) objectInput.readObject());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && compareTo((ChronoZonedDateTime) obj) == 0;
    }

    public int hashCode() {
        return (toLocalDateTime().hashCode() ^ getOffset().hashCode()) ^ Integer.rotateLeft(getZone().hashCode(), 3);
    }

    public String toString() {
        String str = toLocalDateTime().toString() + getOffset().toString();
        if (getOffset() == getZone()) {
            return str;
        }
        return str + "[" + getZone().toString() + "]";
    }
}
