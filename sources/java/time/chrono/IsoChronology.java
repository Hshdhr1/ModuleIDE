package java.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class IsoChronology extends AbstractChronology implements Serializable {
    private static final long DAYS_0000_TO_1970 = 719528;
    public static final IsoChronology INSTANCE = new IsoChronology();
    private static final long serialVersionUID = -1440403870442975015L;

    private IsoChronology() {
    }

    public String getId() {
        return "ISO";
    }

    public String getCalendarType() {
        return "iso8601";
    }

    public LocalDate date(Era era, int i, int i2, int i3) {
        return date(prolepticYear(era, i), i2, i3);
    }

    public LocalDate date(int i, int i2, int i3) {
        return LocalDate.of(i, i2, i3);
    }

    public LocalDate dateYearDay(Era era, int i, int i2) {
        return dateYearDay(prolepticYear(era, i), i2);
    }

    public LocalDate dateYearDay(int i, int i2) {
        return LocalDate.ofYearDay(i, i2);
    }

    public LocalDate dateEpochDay(long j) {
        return LocalDate.ofEpochDay(j);
    }

    public LocalDate date(TemporalAccessor temporalAccessor) {
        return LocalDate.from(temporalAccessor);
    }

    public long epochSecond(int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
        long j;
        long j2 = i;
        ChronoField.YEAR.checkValidValue(j2);
        ChronoField.MONTH_OF_YEAR.checkValidValue(i2);
        ChronoField.DAY_OF_MONTH.checkValidValue(i3);
        ChronoField.HOUR_OF_DAY.checkValidValue(i4);
        ChronoField.MINUTE_OF_HOUR.checkValidValue(i5);
        ChronoField.SECOND_OF_MINUTE.checkValidValue(i6);
        IsoChronology$$ExternalSyntheticBackport4.m(zoneOffset, "zoneOffset");
        if (i3 <= 28 || i3 <= numberOfDaysOfMonth(i, i2)) {
            long j3 = 365 * j2;
            if (i >= 0) {
                j = j3 + (((3 + j2) / 4) - ((99 + j2) / 100)) + ((399 + j2) / 400);
            } else {
                j = j3 - (((i / (-4)) - (i / (-100))) + (i / (-400)));
            }
            long j4 = j + (((i2 * 367) - 362) / 12) + (i3 - 1);
            if (i2 > 2) {
                j4 = !INSTANCE.isLeapYear(j2) ? j4 - 2 : j4 - 1;
            }
            return IsoChronology$$ExternalSyntheticBackport7.m(IsoChronology$$ExternalSyntheticBackport6.m(j4 - 719528, 86400L), ((((i4 * 60) + i5) * 60) + i6) - zoneOffset.getTotalSeconds());
        }
        if (i3 == 29) {
            throw new DateTimeException("Invalid date 'February 29' as '" + i + "' is not a leap year");
        }
        throw new DateTimeException("Invalid date '" + Month.of(i2).name() + " " + i3 + "'");
    }

    private int numberOfDaysOfMonth(int i, int i2) {
        return i2 != 2 ? (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31 : INSTANCE.isLeapYear((long) i) ? 29 : 28;
    }

    public LocalDateTime localDateTime(TemporalAccessor temporalAccessor) {
        return LocalDateTime.from(temporalAccessor);
    }

    public ZonedDateTime zonedDateTime(TemporalAccessor temporalAccessor) {
        return ZonedDateTime.from(temporalAccessor);
    }

    public ZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    public LocalDate dateNow() {
        return dateNow(Clock.systemDefaultZone());
    }

    public LocalDate dateNow(ZoneId zoneId) {
        return dateNow(Clock.system(zoneId));
    }

    public LocalDate dateNow(Clock clock) {
        IsoChronology$$ExternalSyntheticBackport4.m(clock, "clock");
        return date((TemporalAccessor) LocalDate.now(clock));
    }

    public boolean isLeapYear(long j) {
        if ((3 & j) == 0) {
            return j % 100 != 0 || j % 400 == 0;
        }
        return false;
    }

    public int prolepticYear(Era era, int i) {
        if (era instanceof IsoEra) {
            return era == IsoEra.CE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    public IsoEra eraOf(int i) {
        return IsoEra.of(i);
    }

    public List eras() {
        return IsoChronology$$ExternalSyntheticBackport8.m(IsoEra.values());
    }

    public LocalDate resolveDate(Map map, ResolverStyle resolverStyle) {
        return (LocalDate) super.resolveDate(map, resolverStyle);
    }

    void resolveProlepticMonth(Map map, ResolverStyle resolverStyle) {
        Long l = (Long) map.remove(ChronoField.PROLEPTIC_MONTH);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.PROLEPTIC_MONTH.checkValidValue(l.longValue());
            }
            addFieldValue(map, ChronoField.MONTH_OF_YEAR, IsoChronology$$ExternalSyntheticBackport0.m(l.longValue(), 12) + 1);
            addFieldValue(map, ChronoField.YEAR, IsoChronology$$ExternalSyntheticBackport1.m(l.longValue(), 12));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalDate resolveYearOfEra(Map map, ResolverStyle resolverStyle) {
        Long l = (Long) map.remove(ChronoField.YEAR_OF_ERA);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.YEAR_OF_ERA.checkValidValue(l.longValue());
            }
            Long l2 = (Long) map.remove(ChronoField.ERA);
            if (l2 == null) {
                Long l3 = (Long) map.get(ChronoField.YEAR);
                if (resolverStyle != ResolverStyle.STRICT) {
                    addFieldValue(map, ChronoField.YEAR, (l3 == null || l3.longValue() > 0) ? l.longValue() : IsoChronology$$ExternalSyntheticBackport2.m(1L, l.longValue()));
                    return null;
                }
                if (l3 != null) {
                    addFieldValue(map, ChronoField.YEAR, l3.longValue() > 0 ? l.longValue() : IsoChronology$$ExternalSyntheticBackport2.m(1L, l.longValue()));
                    return null;
                }
                map.put(ChronoField.YEAR_OF_ERA, l);
                return null;
            }
            if (l2.longValue() == 1) {
                addFieldValue(map, ChronoField.YEAR, l.longValue());
                return null;
            }
            if (l2.longValue() == 0) {
                addFieldValue(map, ChronoField.YEAR, IsoChronology$$ExternalSyntheticBackport2.m(1L, l.longValue()));
                return null;
            }
            throw new DateTimeException("Invalid value for era: " + l2);
        }
        if (!map.containsKey(ChronoField.ERA)) {
            return null;
        }
        ChronoField.ERA.checkValidValue(((Long) map.get(ChronoField.ERA)).longValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalDate resolveYMD(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = ChronoField.YEAR.checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue());
        if (resolverStyle == ResolverStyle.LENIENT) {
            return LocalDate.of(checkValidIntValue, 1, 1).plusMonths(IsoChronology$$ExternalSyntheticBackport2.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L)).plusDays(IsoChronology$$ExternalSyntheticBackport2.m(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L));
        }
        int checkValidIntValue2 = ChronoField.MONTH_OF_YEAR.checkValidIntValue(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue());
        int checkValidIntValue3 = ChronoField.DAY_OF_MONTH.checkValidIntValue(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue());
        if (resolverStyle == ResolverStyle.SMART) {
            if (checkValidIntValue2 == 4 || checkValidIntValue2 == 6 || checkValidIntValue2 == 9 || checkValidIntValue2 == 11) {
                checkValidIntValue3 = Math.min(checkValidIntValue3, 30);
            } else if (checkValidIntValue2 == 2) {
                checkValidIntValue3 = Math.min(checkValidIntValue3, Month.FEBRUARY.length(Year.isLeap(checkValidIntValue)));
            }
        }
        return LocalDate.of(checkValidIntValue, checkValidIntValue2, checkValidIntValue3);
    }

    public ValueRange range(ChronoField chronoField) {
        return chronoField.range();
    }

    public Period period(int i, int i2, int i3) {
        return Period.of(i, i2, i3);
    }

    Object writeReplace() {
        return super.writeReplace();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
