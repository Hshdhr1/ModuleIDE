package java.time.format;

import java.text.DateFormatSymbols;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DateTimeTextProvider {
    private static final ConcurrentMap CACHE = new ConcurrentHashMap(16, 0.75f, 2);
    private static final Comparator COMPARATOR = new 1();
    private static final DateTimeTextProvider INSTANCE = new DateTimeTextProvider();

    static /* bridge */ /* synthetic */ Comparator -$$Nest$sfgetCOMPARATOR() {
        return COMPARATOR;
    }

    static /* bridge */ /* synthetic */ Map.Entry -$$Nest$smcreateEntry(Object obj, Object obj2) {
        return createEntry(obj, obj2);
    }

    private static int toWeekDay(int i) {
        if (i == 1) {
            return 7;
        }
        return i - 1;
    }

    class 1 implements Comparator {
        public /* synthetic */ Comparator reversed() {
            return Comparator.-CC.$default$reversed(this);
        }

        public /* synthetic */ Comparator thenComparing(Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, comparator);
        }

        public /* synthetic */ Comparator thenComparing(Function function) {
            return Comparator.-CC.$default$thenComparing(this, function);
        }

        public /* synthetic */ Comparator thenComparing(Function function, Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, function, comparator);
        }

        public /* synthetic */ Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
            return Comparator.-CC.$default$thenComparingDouble(this, toDoubleFunction);
        }

        public /* synthetic */ Comparator thenComparingInt(ToIntFunction toIntFunction) {
            return Comparator.-CC.$default$thenComparingInt(this, toIntFunction);
        }

        public /* synthetic */ Comparator thenComparingLong(ToLongFunction toLongFunction) {
            return Comparator.-CC.$default$thenComparingLong(this, toLongFunction);
        }

        1() {
        }

        public int compare(Map.Entry entry, Map.Entry entry2) {
            return ((String) entry2.getKey()).length() - ((String) entry.getKey()).length();
        }
    }

    DateTimeTextProvider() {
    }

    static DateTimeTextProvider getInstance() {
        return INSTANCE;
    }

    public String getText(TemporalField temporalField, long j, TextStyle textStyle, Locale locale) {
        Object findStore = findStore(temporalField, locale);
        if (findStore instanceof LocaleStore) {
            return ((LocaleStore) findStore).getText(j, textStyle);
        }
        return null;
    }

    public String getText(Chronology chronology, TemporalField temporalField, long j, TextStyle textStyle, Locale locale) {
        if (chronology == IsoChronology.INSTANCE || !(temporalField instanceof ChronoField)) {
            return getText(temporalField, j, textStyle, locale);
        }
        return null;
    }

    public Iterator getTextIterator(TemporalField temporalField, TextStyle textStyle, Locale locale) {
        Object findStore = findStore(temporalField, locale);
        if (findStore instanceof LocaleStore) {
            return ((LocaleStore) findStore).getTextIterator(textStyle);
        }
        return null;
    }

    public Iterator getTextIterator(Chronology chronology, TemporalField temporalField, TextStyle textStyle, Locale locale) {
        if (chronology == IsoChronology.INSTANCE || !(temporalField instanceof ChronoField)) {
            return getTextIterator(temporalField, textStyle, locale);
        }
        return null;
    }

    private Object findStore(TemporalField temporalField, Locale locale) {
        Map.Entry createEntry = createEntry(temporalField, locale);
        ConcurrentMap concurrentMap = CACHE;
        Object obj = concurrentMap.get(createEntry);
        if (obj != null) {
            return obj;
        }
        concurrentMap.putIfAbsent(createEntry, createStore(temporalField, locale));
        return concurrentMap.get(createEntry);
    }

    private Object createStore(TemporalField temporalField, Locale locale) {
        HashMap hashMap = new HashMap();
        int i = 0;
        if (temporalField == ChronoField.ERA) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            String[] eras = dateFormatSymbols.getEras();
            while (i < eras.length) {
                if (!eras[i].isEmpty()) {
                    long j = i;
                    hashMap2.put(Long.valueOf(j), eras[i]);
                    hashMap3.put(Long.valueOf(j), firstCodePoint(eras[i]));
                }
                i++;
            }
            if (!hashMap2.isEmpty()) {
                hashMap.put(TextStyle.FULL, hashMap2);
                hashMap.put(TextStyle.SHORT, hashMap2);
                hashMap.put(TextStyle.NARROW, hashMap3);
            }
            return new LocaleStore(hashMap);
        }
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            DateFormatSymbols dateFormatSymbols2 = DateFormatSymbols.getInstance(locale);
            HashMap hashMap4 = new HashMap();
            HashMap hashMap5 = new HashMap();
            String[] months = dateFormatSymbols2.getMonths();
            for (int i2 = 0; i2 < months.length; i2++) {
                if (!months[i2].isEmpty()) {
                    long j2 = i2 + 1;
                    hashMap4.put(Long.valueOf(j2), months[i2]);
                    hashMap5.put(Long.valueOf(j2), firstCodePoint(months[i2]));
                }
            }
            if (!hashMap4.isEmpty()) {
                hashMap.put(TextStyle.FULL, hashMap4);
                hashMap.put(TextStyle.NARROW, hashMap5);
            }
            HashMap hashMap6 = new HashMap();
            String[] shortMonths = dateFormatSymbols2.getShortMonths();
            while (i < shortMonths.length) {
                if (!shortMonths[i].isEmpty()) {
                    hashMap6.put(Long.valueOf(i + 1), shortMonths[i]);
                }
                i++;
            }
            if (!hashMap6.isEmpty()) {
                hashMap.put(TextStyle.SHORT, hashMap6);
            }
            return new LocaleStore(hashMap);
        }
        if (temporalField == ChronoField.DAY_OF_WEEK) {
            DateFormatSymbols dateFormatSymbols3 = DateFormatSymbols.getInstance(locale);
            HashMap hashMap7 = new HashMap();
            String[] weekdays = dateFormatSymbols3.getWeekdays();
            hashMap7.put(1L, weekdays[2]);
            hashMap7.put(2L, weekdays[3]);
            hashMap7.put(3L, weekdays[4]);
            hashMap7.put(4L, weekdays[5]);
            hashMap7.put(5L, weekdays[6]);
            hashMap7.put(6L, weekdays[7]);
            hashMap7.put(7L, weekdays[1]);
            hashMap.put(TextStyle.FULL, hashMap7);
            HashMap hashMap8 = new HashMap();
            hashMap8.put(1L, firstCodePoint(weekdays[2]));
            hashMap8.put(2L, firstCodePoint(weekdays[3]));
            hashMap8.put(3L, firstCodePoint(weekdays[4]));
            hashMap8.put(4L, firstCodePoint(weekdays[5]));
            hashMap8.put(5L, firstCodePoint(weekdays[6]));
            hashMap8.put(6L, firstCodePoint(weekdays[7]));
            hashMap8.put(7L, firstCodePoint(weekdays[1]));
            hashMap.put(TextStyle.NARROW, hashMap8);
            HashMap hashMap9 = new HashMap();
            String[] shortWeekdays = dateFormatSymbols3.getShortWeekdays();
            hashMap9.put(1L, shortWeekdays[2]);
            hashMap9.put(2L, shortWeekdays[3]);
            hashMap9.put(3L, shortWeekdays[4]);
            hashMap9.put(4L, shortWeekdays[5]);
            hashMap9.put(5L, shortWeekdays[6]);
            hashMap9.put(6L, shortWeekdays[7]);
            hashMap9.put(7L, shortWeekdays[1]);
            hashMap.put(TextStyle.SHORT, hashMap9);
            return new LocaleStore(hashMap);
        }
        if (temporalField == ChronoField.AMPM_OF_DAY) {
            DateFormatSymbols dateFormatSymbols4 = DateFormatSymbols.getInstance(locale);
            HashMap hashMap10 = new HashMap();
            HashMap hashMap11 = new HashMap();
            String[] amPmStrings = dateFormatSymbols4.getAmPmStrings();
            while (i < amPmStrings.length) {
                if (!amPmStrings[i].isEmpty()) {
                    long j3 = i;
                    hashMap10.put(Long.valueOf(j3), amPmStrings[i]);
                    hashMap11.put(Long.valueOf(j3), firstCodePoint(amPmStrings[i]));
                }
                i++;
            }
            if (!hashMap10.isEmpty()) {
                hashMap.put(TextStyle.FULL, hashMap10);
                hashMap.put(TextStyle.SHORT, hashMap10);
                hashMap.put(TextStyle.NARROW, hashMap11);
            }
            return new LocaleStore(hashMap);
        }
        return "";
    }

    private static String firstCodePoint(String str) {
        return str.substring(0, Character.charCount(str.codePointAt(0)));
    }

    private static Map.Entry createEntry(Object obj, Object obj2) {
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    static final class LocaleStore {
        private final Map parsable;
        private final Map valueTextMap;

        LocaleStore(Map map) {
            this.valueTextMap = map;
            HashMap hashMap = new HashMap();
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                HashMap hashMap2 = new HashMap();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    hashMap2.put((String) entry2.getValue(), DateTimeTextProvider.-$$Nest$smcreateEntry((String) entry2.getValue(), (Long) entry2.getKey()));
                }
                ArrayList arrayList2 = new ArrayList(hashMap2.values());
                Collections.sort(arrayList2, DateTimeTextProvider.-$$Nest$sfgetCOMPARATOR());
                hashMap.put((TextStyle) entry.getKey(), arrayList2);
                arrayList.addAll(arrayList2);
                hashMap.put(null, arrayList);
            }
            Collections.sort(arrayList, DateTimeTextProvider.-$$Nest$sfgetCOMPARATOR());
            this.parsable = hashMap;
        }

        String getText(long j, TextStyle textStyle) {
            Map map = (Map) this.valueTextMap.get(textStyle);
            if (map != null) {
                return (String) map.get(Long.valueOf(j));
            }
            return null;
        }

        Iterator getTextIterator(TextStyle textStyle) {
            List list = (List) this.parsable.get(textStyle);
            if (list != null) {
                return list.iterator();
            }
            return null;
        }
    }
}
