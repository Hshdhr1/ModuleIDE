package java.time.format;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class Parsed implements TemporalAccessor {
    Chronology chrono;
    private ChronoLocalDate date;
    boolean leapSecond;
    private ResolverStyle resolverStyle;
    private LocalTime time;
    ZoneId zone;
    final Map fieldValues = new HashMap();
    Period excessDays = Period.ZERO;

    public /* synthetic */ int get(TemporalField temporalField) {
        return TemporalAccessor.-CC.$default$get(this, temporalField);
    }

    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.-CC.$default$range(this, temporalField);
    }

    Parsed() {
    }

    Parsed copy() {
        Parsed parsed = new Parsed();
        parsed.fieldValues.putAll(this.fieldValues);
        parsed.zone = this.zone;
        parsed.chrono = this.chrono;
        parsed.leapSecond = this.leapSecond;
        return parsed;
    }

    public boolean isSupported(TemporalField temporalField) {
        ChronoLocalDate chronoLocalDate;
        LocalTime localTime;
        if (this.fieldValues.containsKey(temporalField) || (((chronoLocalDate = this.date) != null && chronoLocalDate.isSupported(temporalField)) || ((localTime = this.time) != null && localTime.isSupported(temporalField)))) {
            return true;
        }
        return (temporalField == null || (temporalField instanceof ChronoField) || !temporalField.isSupportedBy(this)) ? false : true;
    }

    public long getLong(TemporalField temporalField) {
        Parsed$$ExternalSyntheticBackport5.m(temporalField, "field");
        Long l = (Long) this.fieldValues.get(temporalField);
        if (l != null) {
            return l.longValue();
        }
        ChronoLocalDate chronoLocalDate = this.date;
        if (chronoLocalDate != null && chronoLocalDate.isSupported(temporalField)) {
            return this.date.getLong(temporalField);
        }
        LocalTime localTime = this.time;
        if (localTime != null && localTime.isSupported(temporalField)) {
            return this.time.getLong(temporalField);
        }
        if (temporalField instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.getFrom(this);
    }

    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.zoneId()) {
            return this.zone;
        }
        if (temporalQuery == TemporalQueries.chronology()) {
            return this.chrono;
        }
        if (temporalQuery == TemporalQueries.localDate()) {
            ChronoLocalDate chronoLocalDate = this.date;
            if (chronoLocalDate != null) {
                return LocalDate.from(chronoLocalDate);
            }
            return null;
        }
        if (temporalQuery == TemporalQueries.localTime()) {
            return this.time;
        }
        if (temporalQuery == TemporalQueries.offset()) {
            Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
            if (l != null) {
                return ZoneOffset.ofTotalSeconds(l.intValue());
            }
            ZoneId zoneId = this.zone;
            return zoneId instanceof ZoneOffset ? zoneId : temporalQuery.queryFrom(this);
        }
        if (temporalQuery == TemporalQueries.zone()) {
            return temporalQuery.queryFrom(this);
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return null;
        }
        return temporalQuery.queryFrom(this);
    }

    TemporalAccessor resolve(ResolverStyle resolverStyle, Set set) {
        if (set != null) {
            this.fieldValues.keySet().retainAll(set);
        }
        this.resolverStyle = resolverStyle;
        resolveFields();
        resolveTimeLenient();
        crossCheck();
        resolvePeriod();
        resolveFractional();
        resolveInstant();
        return this;
    }

    private void resolveFields() {
        resolveInstantFields();
        resolveDateFields();
        resolveTimeFields();
        if (this.fieldValues.size() > 0) {
            int i = 0;
            loop0: while (i < 50) {
                Iterator it = this.fieldValues.entrySet().iterator();
                while (it.hasNext()) {
                    TemporalField temporalField = (TemporalField) ((Map.Entry) it.next()).getKey();
                    TemporalAccessor resolve = temporalField.resolve(this.fieldValues, this, this.resolverStyle);
                    if (resolve != null) {
                        if (resolve instanceof ChronoZonedDateTime) {
                            ChronoZonedDateTime chronoZonedDateTime = (ChronoZonedDateTime) resolve;
                            ZoneId zoneId = this.zone;
                            if (zoneId == null) {
                                this.zone = chronoZonedDateTime.getZone();
                            } else if (!zoneId.equals(chronoZonedDateTime.getZone())) {
                                throw new DateTimeException("ChronoZonedDateTime must use the effective parsed zone: " + this.zone);
                            }
                            resolve = chronoZonedDateTime.toLocalDateTime();
                        }
                        if (resolve instanceof ChronoLocalDateTime) {
                            ChronoLocalDateTime chronoLocalDateTime = (ChronoLocalDateTime) resolve;
                            updateCheckConflict(chronoLocalDateTime.toLocalTime(), Period.ZERO);
                            updateCheckConflict(chronoLocalDateTime.toLocalDate());
                        } else if (resolve instanceof ChronoLocalDate) {
                            updateCheckConflict((ChronoLocalDate) resolve);
                        } else if (resolve instanceof LocalTime) {
                            updateCheckConflict((LocalTime) resolve, Period.ZERO);
                        } else {
                            throw new DateTimeException("Method resolve() can only return ChronoZonedDateTime, ChronoLocalDateTime, ChronoLocalDate or LocalTime");
                        }
                    } else if (!this.fieldValues.containsKey(temporalField)) {
                        break;
                    }
                    i++;
                }
            }
            if (i == 50) {
                throw new DateTimeException("One of the parsed fields has an incorrectly implemented resolve method");
            }
            if (i > 0) {
                resolveInstantFields();
                resolveDateFields();
                resolveTimeFields();
            }
        }
    }

    private void updateCheckConflict(TemporalField temporalField, TemporalField temporalField2, Long l) {
        Long l2 = (Long) this.fieldValues.put(temporalField2, l);
        if (l2 == null || l2.longValue() == l.longValue()) {
            return;
        }
        throw new DateTimeException("Conflict found: " + temporalField2 + " " + l2 + " differs from " + temporalField2 + " " + l + " while resolving  " + temporalField);
    }

    private void resolveInstantFields() {
        if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS)) {
            ZoneId zoneId = this.zone;
            if (zoneId != null) {
                resolveInstantFields0(zoneId);
                return;
            }
            Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
            if (l != null) {
                resolveInstantFields0(ZoneOffset.ofTotalSeconds(l.intValue()));
            }
        }
    }

    private void resolveInstantFields0(ZoneId zoneId) {
        updateCheckConflict(this.chrono.zonedDateTime(Instant.ofEpochSecond(((Long) this.fieldValues.remove(ChronoField.INSTANT_SECONDS)).longValue()), zoneId).toLocalDate());
        updateCheckConflict(ChronoField.INSTANT_SECONDS, ChronoField.SECOND_OF_DAY, Long.valueOf(r5.toLocalTime().toSecondOfDay()));
    }

    private void resolveDateFields() {
        updateCheckConflict(this.chrono.resolveDate(this.fieldValues, this.resolverStyle));
    }

    private void updateCheckConflict(ChronoLocalDate chronoLocalDate) {
        ChronoLocalDate chronoLocalDate2 = this.date;
        if (chronoLocalDate2 != null) {
            if (chronoLocalDate == null || chronoLocalDate2.equals(chronoLocalDate)) {
                return;
            }
            throw new DateTimeException("Conflict found: Fields resolved to two different dates: " + this.date + " " + chronoLocalDate);
        }
        if (chronoLocalDate != null) {
            if (!this.chrono.equals(chronoLocalDate.getChronology())) {
                throw new DateTimeException("ChronoLocalDate must use the effective parsed chronology: " + this.chrono);
            }
            this.date = chronoLocalDate;
        }
    }

    private void resolveTimeFields() {
        if (this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_DAY)) {
            long longValue = ((Long) this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_DAY)).longValue();
            if (this.resolverStyle == ResolverStyle.STRICT || (this.resolverStyle == ResolverStyle.SMART && longValue != 0)) {
                ChronoField.CLOCK_HOUR_OF_DAY.checkValidValue(longValue);
            }
            ChronoField chronoField = ChronoField.CLOCK_HOUR_OF_DAY;
            ChronoField chronoField2 = ChronoField.HOUR_OF_DAY;
            if (longValue == 24) {
                longValue = 0;
            }
            updateCheckConflict(chronoField, chronoField2, Long.valueOf(longValue));
        }
        if (this.fieldValues.containsKey(ChronoField.CLOCK_HOUR_OF_AMPM)) {
            long longValue2 = ((Long) this.fieldValues.remove(ChronoField.CLOCK_HOUR_OF_AMPM)).longValue();
            if (this.resolverStyle == ResolverStyle.STRICT || (this.resolverStyle == ResolverStyle.SMART && longValue2 != 0)) {
                ChronoField.CLOCK_HOUR_OF_AMPM.checkValidValue(longValue2);
            }
            updateCheckConflict(ChronoField.CLOCK_HOUR_OF_AMPM, ChronoField.HOUR_OF_AMPM, Long.valueOf(longValue2 != 12 ? longValue2 : 0L));
        }
        if (this.fieldValues.containsKey(ChronoField.AMPM_OF_DAY) && this.fieldValues.containsKey(ChronoField.HOUR_OF_AMPM)) {
            long longValue3 = ((Long) this.fieldValues.remove(ChronoField.AMPM_OF_DAY)).longValue();
            long longValue4 = ((Long) this.fieldValues.remove(ChronoField.HOUR_OF_AMPM)).longValue();
            if (this.resolverStyle == ResolverStyle.LENIENT) {
                updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(Parsed$$ExternalSyntheticBackport2.m(Parsed$$ExternalSyntheticBackport1.m(longValue3, 12), longValue4)));
            } else {
                ChronoField.AMPM_OF_DAY.checkValidValue(longValue3);
                ChronoField.HOUR_OF_AMPM.checkValidValue(longValue3);
                updateCheckConflict(ChronoField.AMPM_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf((longValue3 * 12) + longValue4));
            }
        }
        if (this.fieldValues.containsKey(ChronoField.NANO_OF_DAY)) {
            long longValue5 = ((Long) this.fieldValues.remove(ChronoField.NANO_OF_DAY)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.NANO_OF_DAY.checkValidValue(longValue5);
            }
            updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(longValue5 / 3600000000000L));
            updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf((longValue5 / 60000000000L) % 60));
            updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.SECOND_OF_MINUTE, Long.valueOf((longValue5 / 1000000000) % 60));
            updateCheckConflict(ChronoField.NANO_OF_DAY, ChronoField.NANO_OF_SECOND, Long.valueOf(longValue5 % 1000000000));
        }
        if (this.fieldValues.containsKey(ChronoField.MICRO_OF_DAY)) {
            long longValue6 = ((Long) this.fieldValues.remove(ChronoField.MICRO_OF_DAY)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MICRO_OF_DAY.checkValidValue(longValue6);
            }
            updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.SECOND_OF_DAY, Long.valueOf(longValue6 / 1000000));
            updateCheckConflict(ChronoField.MICRO_OF_DAY, ChronoField.MICRO_OF_SECOND, Long.valueOf(longValue6 % 1000000));
        }
        if (this.fieldValues.containsKey(ChronoField.MILLI_OF_DAY)) {
            long longValue7 = ((Long) this.fieldValues.remove(ChronoField.MILLI_OF_DAY)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MILLI_OF_DAY.checkValidValue(longValue7);
            }
            updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.SECOND_OF_DAY, Long.valueOf(longValue7 / 1000));
            updateCheckConflict(ChronoField.MILLI_OF_DAY, ChronoField.MILLI_OF_SECOND, Long.valueOf(longValue7 % 1000));
        }
        if (this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY)) {
            long longValue8 = ((Long) this.fieldValues.remove(ChronoField.SECOND_OF_DAY)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.SECOND_OF_DAY.checkValidValue(longValue8);
            }
            updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(longValue8 / 3600));
            updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf((longValue8 / 60) % 60));
            updateCheckConflict(ChronoField.SECOND_OF_DAY, ChronoField.SECOND_OF_MINUTE, Long.valueOf(longValue8 % 60));
        }
        if (this.fieldValues.containsKey(ChronoField.MINUTE_OF_DAY)) {
            long longValue9 = ((Long) this.fieldValues.remove(ChronoField.MINUTE_OF_DAY)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.MINUTE_OF_DAY.checkValidValue(longValue9);
            }
            updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.HOUR_OF_DAY, Long.valueOf(longValue9 / 60));
            updateCheckConflict(ChronoField.MINUTE_OF_DAY, ChronoField.MINUTE_OF_HOUR, Long.valueOf(longValue9 % 60));
        }
        if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
            long longValue10 = ((Long) this.fieldValues.get(ChronoField.NANO_OF_SECOND)).longValue();
            if (this.resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.NANO_OF_SECOND.checkValidValue(longValue10);
            }
            if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                long longValue11 = ((Long) this.fieldValues.remove(ChronoField.MICRO_OF_SECOND)).longValue();
                if (this.resolverStyle != ResolverStyle.LENIENT) {
                    ChronoField.MICRO_OF_SECOND.checkValidValue(longValue11);
                }
                longValue10 = (longValue10 % 1000) + (longValue11 * 1000);
                updateCheckConflict(ChronoField.MICRO_OF_SECOND, ChronoField.NANO_OF_SECOND, Long.valueOf(longValue10));
            }
            if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
                long longValue12 = ((Long) this.fieldValues.remove(ChronoField.MILLI_OF_SECOND)).longValue();
                if (this.resolverStyle != ResolverStyle.LENIENT) {
                    ChronoField.MILLI_OF_SECOND.checkValidValue(longValue12);
                }
                updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.NANO_OF_SECOND, Long.valueOf((longValue12 * 1000000) + (longValue10 % 1000000)));
            }
        }
        if (this.fieldValues.containsKey(ChronoField.HOUR_OF_DAY) && this.fieldValues.containsKey(ChronoField.MINUTE_OF_HOUR) && this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE) && this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
            resolveTime(((Long) this.fieldValues.remove(ChronoField.HOUR_OF_DAY)).longValue(), ((Long) this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR)).longValue(), ((Long) this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE)).longValue(), ((Long) this.fieldValues.remove(ChronoField.NANO_OF_SECOND)).longValue());
        }
    }

    private void resolveTimeLenient() {
        if (this.time == null) {
            if (this.fieldValues.containsKey(ChronoField.MILLI_OF_SECOND)) {
                long longValue = ((Long) this.fieldValues.remove(ChronoField.MILLI_OF_SECOND)).longValue();
                if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                    long longValue2 = (longValue * 1000) + (((Long) this.fieldValues.get(ChronoField.MICRO_OF_SECOND)).longValue() % 1000);
                    updateCheckConflict(ChronoField.MILLI_OF_SECOND, ChronoField.MICRO_OF_SECOND, Long.valueOf(longValue2));
                    this.fieldValues.remove(ChronoField.MICRO_OF_SECOND);
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(longValue2 * 1000));
                } else {
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(longValue * 1000000));
                }
            } else if (this.fieldValues.containsKey(ChronoField.MICRO_OF_SECOND)) {
                this.fieldValues.put(ChronoField.NANO_OF_SECOND, Long.valueOf(((Long) this.fieldValues.remove(ChronoField.MICRO_OF_SECOND)).longValue() * 1000));
            }
            Long l = (Long) this.fieldValues.get(ChronoField.HOUR_OF_DAY);
            if (l != null) {
                Long l2 = (Long) this.fieldValues.get(ChronoField.MINUTE_OF_HOUR);
                Long l3 = (Long) this.fieldValues.get(ChronoField.SECOND_OF_MINUTE);
                Long l4 = (Long) this.fieldValues.get(ChronoField.NANO_OF_SECOND);
                if (l2 == null && (l3 != null || l4 != null)) {
                    return;
                }
                if (l2 != null && l3 == null && l4 != null) {
                    return;
                }
                long longValue3 = l2 != null ? l2.longValue() : 0L;
                long longValue4 = l3 != null ? l3.longValue() : 0L;
                resolveTime(l.longValue(), longValue3, longValue4, l4 != null ? l4.longValue() : 0L);
                this.fieldValues.remove(ChronoField.HOUR_OF_DAY);
                this.fieldValues.remove(ChronoField.MINUTE_OF_HOUR);
                this.fieldValues.remove(ChronoField.SECOND_OF_MINUTE);
                this.fieldValues.remove(ChronoField.NANO_OF_SECOND);
            }
        }
        if (this.resolverStyle == ResolverStyle.LENIENT || this.fieldValues.size() <= 0) {
            return;
        }
        for (Map.Entry entry : this.fieldValues.entrySet()) {
            TemporalField temporalField = (TemporalField) entry.getKey();
            if ((temporalField instanceof ChronoField) && temporalField.isTimeBased()) {
                ((ChronoField) temporalField).checkValidValue(((Long) entry.getValue()).longValue());
            }
        }
    }

    private void resolveTime(long j, long j2, long j3, long j4) {
        if (this.resolverStyle == ResolverStyle.LENIENT) {
            long m = Parsed$$ExternalSyntheticBackport2.m(Parsed$$ExternalSyntheticBackport2.m(Parsed$$ExternalSyntheticBackport2.m(Parsed$$ExternalSyntheticBackport0.m(j, 3600000000000L), Parsed$$ExternalSyntheticBackport0.m(j2, 60000000000L)), Parsed$$ExternalSyntheticBackport0.m(j3, 1000000000L)), j4);
            updateCheckConflict(LocalTime.ofNanoOfDay(Parsed$$ExternalSyntheticBackport4.m(m, 86400000000000L)), Period.ofDays((int) Parsed$$ExternalSyntheticBackport3.m(m, 86400000000000L)));
            return;
        }
        int checkValidIntValue = ChronoField.MINUTE_OF_HOUR.checkValidIntValue(j2);
        int checkValidIntValue2 = ChronoField.NANO_OF_SECOND.checkValidIntValue(j4);
        if (this.resolverStyle == ResolverStyle.SMART && j == 24 && checkValidIntValue == 0 && j3 == 0 && checkValidIntValue2 == 0) {
            updateCheckConflict(LocalTime.MIDNIGHT, Period.ofDays(1));
        } else {
            updateCheckConflict(LocalTime.of(ChronoField.HOUR_OF_DAY.checkValidIntValue(j), checkValidIntValue, ChronoField.SECOND_OF_MINUTE.checkValidIntValue(j3), checkValidIntValue2), Period.ZERO);
        }
    }

    private void resolvePeriod() {
        if (this.date == null || this.time == null || this.excessDays.isZero()) {
            return;
        }
        this.date = this.date.plus((TemporalAmount) this.excessDays);
        this.excessDays = Period.ZERO;
    }

    private void resolveFractional() {
        if (this.time == null) {
            if (this.fieldValues.containsKey(ChronoField.INSTANT_SECONDS) || this.fieldValues.containsKey(ChronoField.SECOND_OF_DAY) || this.fieldValues.containsKey(ChronoField.SECOND_OF_MINUTE)) {
                if (this.fieldValues.containsKey(ChronoField.NANO_OF_SECOND)) {
                    long longValue = ((Long) this.fieldValues.get(ChronoField.NANO_OF_SECOND)).longValue();
                    this.fieldValues.put(ChronoField.MICRO_OF_SECOND, Long.valueOf(longValue / 1000));
                    this.fieldValues.put(ChronoField.MILLI_OF_SECOND, Long.valueOf(longValue / 1000000));
                } else {
                    this.fieldValues.put(ChronoField.NANO_OF_SECOND, 0L);
                    this.fieldValues.put(ChronoField.MICRO_OF_SECOND, 0L);
                    this.fieldValues.put(ChronoField.MILLI_OF_SECOND, 0L);
                }
            }
        }
    }

    private void resolveInstant() {
        if (this.date == null || this.time == null) {
            return;
        }
        Long l = (Long) this.fieldValues.get(ChronoField.OFFSET_SECONDS);
        if (l != null) {
            this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(this.date.atTime(this.time).atZone(ZoneOffset.ofTotalSeconds(l.intValue())).toEpochSecond()));
        } else if (this.zone != null) {
            this.fieldValues.put(ChronoField.INSTANT_SECONDS, Long.valueOf(this.date.atTime(this.time).atZone(this.zone).toEpochSecond()));
        }
    }

    private void updateCheckConflict(LocalTime localTime, Period period) {
        LocalTime localTime2 = this.time;
        if (localTime2 != null) {
            if (!localTime2.equals(localTime)) {
                throw new DateTimeException("Conflict found: Fields resolved to different times: " + this.time + " " + localTime);
            }
            if (!this.excessDays.isZero() && !period.isZero() && !this.excessDays.equals(period)) {
                throw new DateTimeException("Conflict found: Fields resolved to different excess periods: " + this.excessDays + " " + period);
            }
            this.excessDays = period;
            return;
        }
        this.time = localTime;
        this.excessDays = period;
    }

    private void crossCheck() {
        ChronoLocalDate chronoLocalDate = this.date;
        if (chronoLocalDate != null) {
            crossCheck(chronoLocalDate);
        }
        LocalTime localTime = this.time;
        if (localTime != null) {
            crossCheck(localTime);
            if (this.date == null || this.fieldValues.size() <= 0) {
                return;
            }
            crossCheck(this.date.atTime(this.time));
        }
    }

    private void crossCheck(TemporalAccessor temporalAccessor) {
        Iterator it = this.fieldValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            TemporalField temporalField = (TemporalField) entry.getKey();
            if (temporalAccessor.isSupported(temporalField)) {
                try {
                    long j = temporalAccessor.getLong(temporalField);
                    long longValue = ((Long) entry.getValue()).longValue();
                    if (j != longValue) {
                        throw new DateTimeException("Conflict found: Field " + temporalField + " " + j + " differs from " + temporalField + " " + longValue + " derived from " + temporalAccessor);
                    }
                    it.remove();
                } catch (RuntimeException unused) {
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(this.fieldValues);
        sb.append(',');
        sb.append(this.chrono);
        if (this.zone != null) {
            sb.append(',');
            sb.append(this.zone);
        }
        if (this.date != null || this.time != null) {
            sb.append(" resolved to ");
            ChronoLocalDate chronoLocalDate = this.date;
            if (chronoLocalDate != null) {
                sb.append(chronoLocalDate);
                if (this.time != null) {
                    sb.append('T');
                    sb.append(this.time);
                }
            } else {
                sb.append(this.time);
            }
        }
        return sb.toString();
    }
}
