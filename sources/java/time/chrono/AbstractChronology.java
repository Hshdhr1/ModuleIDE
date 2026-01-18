package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class AbstractChronology implements Chronology {
    private static final ConcurrentHashMap CHRONOS_BY_ID = new ConcurrentHashMap();
    private static final ConcurrentHashMap CHRONOS_BY_TYPE = new ConcurrentHashMap();
    private static final Locale JAPANESE_CALENDAR_LOCALE = new Locale("ja", "JP", "JP");

    public /* synthetic */ ChronoLocalDate date(Era era, int i, int i2, int i3) {
        return Chronology.-CC.$default$date(this, era, i, i2, i3);
    }

    public /* synthetic */ ChronoLocalDate dateNow() {
        return Chronology.-CC.$default$dateNow(this);
    }

    public /* synthetic */ ChronoLocalDate dateNow(Clock clock) {
        return Chronology.-CC.$default$dateNow(this, clock);
    }

    public /* synthetic */ ChronoLocalDate dateNow(ZoneId zoneId) {
        return Chronology.-CC.$default$dateNow(this, zoneId);
    }

    public /* synthetic */ ChronoLocalDate dateYearDay(Era era, int i, int i2) {
        return Chronology.-CC.$default$dateYearDay(this, era, i, i2);
    }

    public /* synthetic */ long epochSecond(int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
        return Chronology.-CC.$default$epochSecond(this, i, i2, i3, i4, i5, i6, zoneOffset);
    }

    public /* synthetic */ long epochSecond(Era era, int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
        return Chronology.-CC.$default$epochSecond(this, era, i, i2, i3, i4, i5, i6, zoneOffset);
    }

    public /* synthetic */ String getDisplayName(TextStyle textStyle, Locale locale) {
        return Chronology.-CC.$default$getDisplayName(this, textStyle, locale);
    }

    public /* synthetic */ ChronoLocalDateTime localDateTime(TemporalAccessor temporalAccessor) {
        return Chronology.-CC.$default$localDateTime(this, temporalAccessor);
    }

    public /* synthetic */ ChronoPeriod period(int i, int i2, int i3) {
        return Chronology.-CC.$default$period(this, i, i2, i3);
    }

    public /* synthetic */ ChronoZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId) {
        return Chronology.-CC.$default$zonedDateTime(this, instant, zoneId);
    }

    public /* synthetic */ ChronoZonedDateTime zonedDateTime(TemporalAccessor temporalAccessor) {
        return Chronology.-CC.$default$zonedDateTime(this, temporalAccessor);
    }

    static Chronology registerChrono(Chronology chronology) {
        return registerChrono(chronology, chronology.getId());
    }

    static Chronology registerChrono(Chronology chronology, String str) {
        String calendarType;
        Chronology chronology2 = (Chronology) CHRONOS_BY_ID.putIfAbsent(str, chronology);
        if (chronology2 == null && (calendarType = chronology.getCalendarType()) != null) {
            CHRONOS_BY_TYPE.putIfAbsent(calendarType, chronology);
        }
        return chronology2;
    }

    private static boolean initCache() {
        if (CHRONOS_BY_ID.get("ISO") != null) {
            return false;
        }
        registerChrono(HijrahChronology.INSTANCE);
        registerChrono(JapaneseChronology.INSTANCE);
        registerChrono(MinguoChronology.INSTANCE);
        registerChrono(ThaiBuddhistChronology.INSTANCE);
        Iterator it = ServiceLoader.load(AbstractChronology.class, (ClassLoader) null).iterator();
        while (it.hasNext()) {
            AbstractChronology abstractChronology = (AbstractChronology) it.next();
            if (!abstractChronology.getId().equals("ISO")) {
                registerChrono(abstractChronology);
            }
        }
        registerChrono(IsoChronology.INSTANCE);
        return true;
    }

    static Chronology ofLocale(Locale locale) {
        AbstractChronology$$ExternalSyntheticBackport1.m(locale, "locale");
        String calendarType = getCalendarType(locale);
        if (calendarType == null || "iso".equals(calendarType) || "iso8601".equals(calendarType)) {
            return IsoChronology.INSTANCE;
        }
        do {
            Chronology chronology = (Chronology) CHRONOS_BY_TYPE.get(calendarType);
            if (chronology != null) {
                return chronology;
            }
        } while (initCache());
        Iterator it = ServiceLoader.load(Chronology.class).iterator();
        while (it.hasNext()) {
            Chronology chronology2 = (Chronology) it.next();
            if (calendarType.equals(chronology2.getCalendarType())) {
                return chronology2;
            }
        }
        throw new DateTimeException("Unknown calendar system: " + calendarType);
    }

    private static String getCalendarType(Locale locale) {
        String unicodeLocaleType = locale.getUnicodeLocaleType("ca");
        if (unicodeLocaleType != null) {
            return unicodeLocaleType;
        }
        if (locale.equals(JAPANESE_CALENDAR_LOCALE)) {
            return "japanese";
        }
        return null;
    }

    static Chronology of(String str) {
        AbstractChronology$$ExternalSyntheticBackport1.m(str, "id");
        do {
            Chronology of0 = of0(str);
            if (of0 != null) {
                return of0;
            }
        } while (initCache());
        Iterator it = ServiceLoader.load(Chronology.class).iterator();
        while (it.hasNext()) {
            Chronology chronology = (Chronology) it.next();
            if (str.equals(chronology.getId()) || str.equals(chronology.getCalendarType())) {
                return chronology;
            }
        }
        throw new DateTimeException("Unknown chronology: " + str);
    }

    private static Chronology of0(String str) {
        Chronology chronology = (Chronology) CHRONOS_BY_ID.get(str);
        return chronology == null ? (Chronology) CHRONOS_BY_TYPE.get(str) : chronology;
    }

    static Set getAvailableChronologies() {
        initCache();
        HashSet hashSet = new HashSet(CHRONOS_BY_ID.values());
        Iterator it = ServiceLoader.load(Chronology.class).iterator();
        while (it.hasNext()) {
            hashSet.add((Chronology) it.next());
        }
        return hashSet;
    }

    protected AbstractChronology() {
    }

    public ChronoLocalDate resolveDate(Map map, ResolverStyle resolverStyle) {
        if (map.containsKey(ChronoField.EPOCH_DAY)) {
            return dateEpochDay(((Long) map.remove(ChronoField.EPOCH_DAY)).longValue());
        }
        resolveProlepticMonth(map, resolverStyle);
        ChronoLocalDate resolveYearOfEra = resolveYearOfEra(map, resolverStyle);
        if (resolveYearOfEra != null) {
            return resolveYearOfEra;
        }
        if (!map.containsKey(ChronoField.YEAR)) {
            return null;
        }
        if (map.containsKey(ChronoField.MONTH_OF_YEAR)) {
            if (map.containsKey(ChronoField.DAY_OF_MONTH)) {
                return resolveYMD(map, resolverStyle);
            }
            if (map.containsKey(ChronoField.ALIGNED_WEEK_OF_MONTH)) {
                if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) {
                    return resolveYMAA(map, resolverStyle);
                }
                if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
                    return resolveYMAD(map, resolverStyle);
                }
            }
        }
        if (map.containsKey(ChronoField.DAY_OF_YEAR)) {
            return resolveYD(map, resolverStyle);
        }
        if (!map.containsKey(ChronoField.ALIGNED_WEEK_OF_YEAR)) {
            return null;
        }
        if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)) {
            return resolveYAA(map, resolverStyle);
        }
        if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
            return resolveYAD(map, resolverStyle);
        }
        return null;
    }

    void resolveProlepticMonth(Map map, ResolverStyle resolverStyle) {
        Long l = (Long) map.remove(ChronoField.PROLEPTIC_MONTH);
        if (l != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                ChronoField.PROLEPTIC_MONTH.checkValidValue(l.longValue());
            }
            ChronoLocalDate with = dateNow().with((TemporalField) ChronoField.DAY_OF_MONTH, 1L).with((TemporalField) ChronoField.PROLEPTIC_MONTH, l.longValue());
            addFieldValue(map, ChronoField.MONTH_OF_YEAR, with.get(ChronoField.MONTH_OF_YEAR));
            addFieldValue(map, ChronoField.YEAR, with.get(ChronoField.YEAR));
        }
    }

    ChronoLocalDate resolveYearOfEra(Map map, ResolverStyle resolverStyle) {
        int m;
        Long l = (Long) map.remove(ChronoField.YEAR_OF_ERA);
        if (l != null) {
            Long l2 = (Long) map.remove(ChronoField.ERA);
            if (resolverStyle != ResolverStyle.LENIENT) {
                m = range(ChronoField.YEAR_OF_ERA).checkValidIntValue(l.longValue(), ChronoField.YEAR_OF_ERA);
            } else {
                m = AbstractChronology$$ExternalSyntheticBackport2.m(l.longValue());
            }
            if (l2 != null) {
                addFieldValue(map, ChronoField.YEAR, prolepticYear(eraOf(range(ChronoField.ERA).checkValidIntValue(l2.longValue(), ChronoField.ERA)), m));
                return null;
            }
            if (map.containsKey(ChronoField.YEAR)) {
                addFieldValue(map, ChronoField.YEAR, prolepticYear(dateYearDay(range(ChronoField.YEAR).checkValidIntValue(((Long) map.get(ChronoField.YEAR)).longValue(), ChronoField.YEAR), 1).getEra(), m));
                return null;
            }
            if (resolverStyle == ResolverStyle.STRICT) {
                map.put(ChronoField.YEAR_OF_ERA, l);
                return null;
            }
            if (eras().isEmpty()) {
                addFieldValue(map, ChronoField.YEAR, m);
                return null;
            }
            addFieldValue(map, ChronoField.YEAR, prolepticYear((Era) r8.get(r8.size() - 1), m));
            return null;
        }
        if (!map.containsKey(ChronoField.ERA)) {
            return null;
        }
        range(ChronoField.ERA).checkValidValue(((Long) map.get(ChronoField.ERA)).longValue(), ChronoField.ERA);
        return null;
    }

    ChronoLocalDate resolveYMD(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long m = AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
            return date(checkValidIntValue, 1, 1).plus(m, (TemporalUnit) ChronoUnit.MONTHS).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        int checkValidIntValue2 = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
        int checkValidIntValue3 = range(ChronoField.DAY_OF_MONTH).checkValidIntValue(((Long) map.remove(ChronoField.DAY_OF_MONTH)).longValue(), ChronoField.DAY_OF_MONTH);
        if (resolverStyle == ResolverStyle.SMART) {
            try {
                return date(checkValidIntValue, checkValidIntValue2, checkValidIntValue3);
            } catch (DateTimeException unused) {
                return date(checkValidIntValue, checkValidIntValue2, 1).with(TemporalAdjusters.lastDayOfMonth());
            }
        }
        return date(checkValidIntValue, checkValidIntValue2, checkValidIntValue3);
    }

    ChronoLocalDate resolveYD(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return dateYearDay(checkValidIntValue, 1).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        return dateYearDay(checkValidIntValue, range(ChronoField.DAY_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.DAY_OF_YEAR)).longValue(), ChronoField.DAY_OF_YEAR));
    }

    ChronoLocalDate resolveYMAA(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long m = AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
            return date(checkValidIntValue, 1, 1).plus(m, (TemporalUnit) ChronoUnit.MONTHS).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.WEEKS).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        int checkValidIntValue2 = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
        ChronoLocalDate plus = date(checkValidIntValue, checkValidIntValue2, 1).plus(((range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), ChronoField.ALIGNED_WEEK_OF_MONTH) - 1) * 7) + (range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)).longValue(), ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH) - 1), (TemporalUnit) ChronoUnit.DAYS);
        if (resolverStyle != ResolverStyle.STRICT || plus.get(ChronoField.MONTH_OF_YEAR) == checkValidIntValue2) {
            return plus;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
    }

    ChronoLocalDate resolveYMAD(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return resolveAligned(date(checkValidIntValue, 1, 1), AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L), AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L), AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L));
        }
        int checkValidIntValue2 = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
        ChronoLocalDate with = date(checkValidIntValue, checkValidIntValue2, 1).plus((range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), ChronoField.ALIGNED_WEEK_OF_MONTH) - 1) * 7, (TemporalUnit) ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(range(ChronoField.DAY_OF_WEEK).checkValidIntValue(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), ChronoField.DAY_OF_WEEK))));
        if (resolverStyle != ResolverStyle.STRICT || with.get(ChronoField.MONTH_OF_YEAR) == checkValidIntValue2) {
            return with;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
    }

    ChronoLocalDate resolveYAA(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return dateYearDay(checkValidIntValue, 1).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.WEEKS).plus(AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)).longValue(), 1L), (TemporalUnit) ChronoUnit.DAYS);
        }
        ChronoLocalDate plus = dateYearDay(checkValidIntValue, 1).plus(((range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), ChronoField.ALIGNED_WEEK_OF_YEAR) - 1) * 7) + (range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)).longValue(), ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR) - 1), (TemporalUnit) ChronoUnit.DAYS);
        if (resolverStyle != ResolverStyle.STRICT || plus.get(ChronoField.YEAR) == checkValidIntValue) {
            return plus;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
    }

    ChronoLocalDate resolveYAD(Map map, ResolverStyle resolverStyle) {
        int checkValidIntValue = range(ChronoField.YEAR).checkValidIntValue(((Long) map.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            return resolveAligned(dateYearDay(checkValidIntValue, 1), 0L, AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L), AbstractChronology$$ExternalSyntheticBackport0.m(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L));
        }
        ChronoLocalDate with = dateYearDay(checkValidIntValue, 1).plus((range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(((Long) map.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), ChronoField.ALIGNED_WEEK_OF_YEAR) - 1) * 7, (TemporalUnit) ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(range(ChronoField.DAY_OF_WEEK).checkValidIntValue(((Long) map.remove(ChronoField.DAY_OF_WEEK)).longValue(), ChronoField.DAY_OF_WEEK))));
        if (resolverStyle != ResolverStyle.STRICT || with.get(ChronoField.YEAR) == checkValidIntValue) {
            return with;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
    }

    ChronoLocalDate resolveAligned(ChronoLocalDate chronoLocalDate, long j, long j2, long j3) {
        long j4;
        ChronoLocalDate plus = chronoLocalDate.plus(j, (TemporalUnit) ChronoUnit.MONTHS).plus(j2, (TemporalUnit) ChronoUnit.WEEKS);
        if (j3 > 7) {
            long j5 = j3 - 1;
            plus = plus.plus(j5 / 7, (TemporalUnit) ChronoUnit.WEEKS);
            j4 = j5 % 7;
        } else {
            if (j3 < 1) {
                plus = plus.plus(AbstractChronology$$ExternalSyntheticBackport0.m(j3, 7L) / 7, (TemporalUnit) ChronoUnit.WEEKS);
                j4 = (j3 + 6) % 7;
            }
            return plus.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int) j3)));
        }
        j3 = j4 + 1;
        return plus.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int) j3)));
    }

    void addFieldValue(Map map, ChronoField chronoField, long j) {
        Long l = (Long) map.get(chronoField);
        if (l != null && l.longValue() != j) {
            throw new DateTimeException("Conflict found: " + chronoField + " " + l + " differs from " + chronoField + " " + j);
        }
        map.put(chronoField, Long.valueOf(j));
    }

    public int compareTo(Chronology chronology) {
        return getId().compareTo(chronology.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AbstractChronology) && compareTo((Chronology) obj) == 0;
    }

    public int hashCode() {
        return getClass().hashCode() ^ getId().hashCode();
    }

    public String toString() {
        return getId();
    }

    Object writeReplace() {
        return new Ser((byte) 1, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(getId());
    }

    static Chronology readExternal(DataInput dataInput) throws IOException {
        return Chronology.-CC.of(dataInput.readUTF());
    }
}
