package java.time.temporal;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class IsoFields {
    public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
    public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
    public static final TemporalField WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
    public static final TemporalField WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
    public static final TemporalUnit WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
    public static final TemporalUnit QUARTER_YEARS = Unit.QUARTER_YEARS;

    private IsoFields() {
        throw new AssertionError("Not instantiable");
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    private static abstract class Field implements TemporalField {
        private static final /* synthetic */ Field[] $VALUES;
        public static final Field DAY_OF_QUARTER;
        private static final int[] QUARTER_DAYS;
        public static final Field QUARTER_OF_YEAR;
        public static final Field WEEK_BASED_YEAR;
        public static final Field WEEK_OF_WEEK_BASED_YEAR;

        static /* bridge */ /* synthetic */ int[] -$$Nest$sfgetQUARTER_DAYS() {
            return QUARTER_DAYS;
        }

        static /* bridge */ /* synthetic */ void -$$Nest$smensureIso(TemporalAccessor temporalAccessor) {
            ensureIso(temporalAccessor);
        }

        static /* bridge */ /* synthetic */ int -$$Nest$smgetWeek(LocalDate localDate) {
            return getWeek(localDate);
        }

        static /* bridge */ /* synthetic */ int -$$Nest$smgetWeekBasedYear(LocalDate localDate) {
            return getWeekBasedYear(localDate);
        }

        static /* bridge */ /* synthetic */ int -$$Nest$smgetWeekRange(int i) {
            return getWeekRange(i);
        }

        static /* bridge */ /* synthetic */ ValueRange -$$Nest$smgetWeekRange(LocalDate localDate) {
            return getWeekRange(localDate);
        }

        /* synthetic */ Field(String str, int i, IsoFields-IA r3) {
            this(str, i);
        }

        public /* synthetic */ String getDisplayName(Locale locale) {
            return TemporalField.-CC.$default$getDisplayName(this, locale);
        }

        public boolean isDateBased() {
            return true;
        }

        public boolean isTimeBased() {
            return false;
        }

        public /* synthetic */ TemporalAccessor resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            return TemporalField.-CC.$default$resolve(this, map, temporalAccessor, resolverStyle);
        }

        enum 1 extends Field {
            /* synthetic */ 1(String str, int i, IsoFields-IA r3) {
                this(str, i);
            }

            private 1(String str, int i) {
                super(str, i, null);
            }

            public TemporalUnit getBaseUnit() {
                return ChronoUnit.DAYS;
            }

            public TemporalUnit getRangeUnit() {
                return IsoFields.QUARTER_YEARS;
            }

            public ValueRange range() {
                return ValueRange.of(1L, 90L, 92L);
            }

            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR) && temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && temporalAccessor.isSupported(ChronoField.YEAR) && IsoFields.isIso(temporalAccessor);
            }

            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
                }
                long j = temporalAccessor.getLong(QUARTER_OF_YEAR);
                if (j == 1) {
                    return IsoChronology.INSTANCE.isLeapYear(temporalAccessor.getLong(ChronoField.YEAR)) ? ValueRange.of(1L, 91L) : ValueRange.of(1L, 90L);
                }
                if (j == 2) {
                    return ValueRange.of(1L, 91L);
                }
                if (j == 3 || j == 4) {
                    return ValueRange.of(1L, 92L);
                }
                return range();
            }

            public long getFrom(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
                }
                return temporalAccessor.get(ChronoField.DAY_OF_YEAR) - Field.-$$Nest$sfgetQUARTER_DAYS()[((temporalAccessor.get(ChronoField.MONTH_OF_YEAR) - 1) / 3) + (IsoChronology.INSTANCE.isLeapYear(temporalAccessor.getLong(ChronoField.YEAR)) ? 4 : 0)];
            }

            public Temporal adjustInto(Temporal temporal, long j) {
                long from = getFrom(temporal);
                range().checkValidValue(j, this);
                return temporal.with(ChronoField.DAY_OF_YEAR, temporal.getLong(ChronoField.DAY_OF_YEAR) + (j - from));
            }

            public ChronoLocalDate resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
                LocalDate of;
                long j;
                Long l = (Long) map.get(ChronoField.YEAR);
                Long l2 = (Long) map.get(QUARTER_OF_YEAR);
                if (l == null || l2 == null) {
                    return null;
                }
                int checkValidIntValue = ChronoField.YEAR.checkValidIntValue(l.longValue());
                long longValue = ((Long) map.get(DAY_OF_QUARTER)).longValue();
                Field.-$$Nest$smensureIso(temporalAccessor);
                if (resolverStyle == ResolverStyle.LENIENT) {
                    of = LocalDate.of(checkValidIntValue, 1, 1).plusMonths(IsoFields$Field$1$$ExternalSyntheticBackport1.m(IsoFields$Field$1$$ExternalSyntheticBackport0.m(l2.longValue(), 1L), 3));
                    j = IsoFields$Field$1$$ExternalSyntheticBackport0.m(longValue, 1L);
                } else {
                    of = LocalDate.of(checkValidIntValue, ((QUARTER_OF_YEAR.range().checkValidIntValue(l2.longValue(), QUARTER_OF_YEAR) - 1) * 3) + 1, 1);
                    if (longValue < 1 || longValue > 90) {
                        if (resolverStyle == ResolverStyle.STRICT) {
                            rangeRefinedBy(of).checkValidValue(longValue, this);
                        } else {
                            range().checkValidValue(longValue, this);
                        }
                    }
                    j = longValue - 1;
                }
                map.remove(this);
                map.remove(ChronoField.YEAR);
                map.remove(QUARTER_OF_YEAR);
                return of.plusDays(j);
            }

            public String toString() {
                return "DayOfQuarter";
            }
        }

        private Field(String str, int i) {
        }

        public static Field valueOf(String str) {
            return (Field) Enum.valueOf(Field.class, str);
        }

        public static Field[] values() {
            return (Field[]) $VALUES.clone();
        }

        static {
            1 r0 = new 1("DAY_OF_QUARTER", 0, null);
            DAY_OF_QUARTER = r0;
            2 r1 = new 2("QUARTER_OF_YEAR", 1, null);
            QUARTER_OF_YEAR = r1;
            3 r4 = new 3("WEEK_OF_WEEK_BASED_YEAR", 2, null);
            WEEK_OF_WEEK_BASED_YEAR = r4;
            4 r6 = new 4("WEEK_BASED_YEAR", 3, null);
            WEEK_BASED_YEAR = r6;
            $VALUES = new Field[]{r0, r1, r4, r6};
            QUARTER_DAYS = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
        }

        enum 2 extends Field {
            /* synthetic */ 2(String str, int i, IsoFields-IA r3) {
                this(str, i);
            }

            private 2(String str, int i) {
                super(str, i, null);
            }

            public TemporalUnit getBaseUnit() {
                return IsoFields.QUARTER_YEARS;
            }

            public TemporalUnit getRangeUnit() {
                return ChronoUnit.YEARS;
            }

            public ValueRange range() {
                return ValueRange.of(1L, 4L);
            }

            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && IsoFields.isIso(temporalAccessor);
            }

            public long getFrom(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
                }
                return (temporalAccessor.getLong(ChronoField.MONTH_OF_YEAR) + 2) / 3;
            }

            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
                }
                return super.rangeRefinedBy(temporalAccessor);
            }

            public Temporal adjustInto(Temporal temporal, long j) {
                long from = getFrom(temporal);
                range().checkValidValue(j, this);
                return temporal.with(ChronoField.MONTH_OF_YEAR, temporal.getLong(ChronoField.MONTH_OF_YEAR) + ((j - from) * 3));
            }

            public String toString() {
                return "QuarterOfYear";
            }
        }

        enum 3 extends Field {
            /* synthetic */ 3(String str, int i, IsoFields-IA r3) {
                this(str, i);
            }

            private 3(String str, int i) {
                super(str, i, null);
            }

            public String getDisplayName(Locale locale) {
                IsoFields$Field$3$$ExternalSyntheticBackport0.m(locale, "locale");
                return "Week";
            }

            public TemporalUnit getBaseUnit() {
                return ChronoUnit.WEEKS;
            }

            public TemporalUnit getRangeUnit() {
                return IsoFields.WEEK_BASED_YEARS;
            }

            public ValueRange range() {
                return ValueRange.of(1L, 52L, 53L);
            }

            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && IsoFields.isIso(temporalAccessor);
            }

            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
                }
                return Field.-$$Nest$smgetWeekRange(LocalDate.from(temporalAccessor));
            }

            public long getFrom(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
                }
                return Field.-$$Nest$smgetWeek(LocalDate.from(temporalAccessor));
            }

            public Temporal adjustInto(Temporal temporal, long j) {
                range().checkValidValue(j, this);
                return temporal.plus(IsoFields$Field$3$$ExternalSyntheticBackport1.m(j, getFrom(temporal)), ChronoUnit.WEEKS);
            }

            public ChronoLocalDate resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
                LocalDate with;
                long j;
                Long l = (Long) map.get(WEEK_BASED_YEAR);
                Long l2 = (Long) map.get(ChronoField.DAY_OF_WEEK);
                if (l == null || l2 == null) {
                    return null;
                }
                int checkValidIntValue = WEEK_BASED_YEAR.range().checkValidIntValue(l.longValue(), WEEK_BASED_YEAR);
                long longValue = ((Long) map.get(WEEK_OF_WEEK_BASED_YEAR)).longValue();
                Field.-$$Nest$smensureIso(temporalAccessor);
                LocalDate of = LocalDate.of(checkValidIntValue, 1, 4);
                if (resolverStyle == ResolverStyle.LENIENT) {
                    long longValue2 = l2.longValue();
                    if (longValue2 > 7) {
                        long j2 = longValue2 - 1;
                        of = of.plusWeeks(j2 / 7);
                        j = j2 % 7;
                    } else {
                        if (longValue2 < 1) {
                            of = of.plusWeeks(IsoFields$Field$3$$ExternalSyntheticBackport1.m(longValue2, 7L) / 7);
                            j = (longValue2 + 6) % 7;
                        }
                        with = of.plusWeeks(IsoFields$Field$3$$ExternalSyntheticBackport1.m(longValue, 1L)).with((TemporalField) ChronoField.DAY_OF_WEEK, longValue2);
                    }
                    longValue2 = j + 1;
                    with = of.plusWeeks(IsoFields$Field$3$$ExternalSyntheticBackport1.m(longValue, 1L)).with((TemporalField) ChronoField.DAY_OF_WEEK, longValue2);
                } else {
                    int checkValidIntValue2 = ChronoField.DAY_OF_WEEK.checkValidIntValue(l2.longValue());
                    if (longValue < 1 || longValue > 52) {
                        if (resolverStyle == ResolverStyle.STRICT) {
                            Field.-$$Nest$smgetWeekRange(of).checkValidValue(longValue, this);
                        } else {
                            range().checkValidValue(longValue, this);
                        }
                    }
                    with = of.plusWeeks(longValue - 1).with((TemporalField) ChronoField.DAY_OF_WEEK, checkValidIntValue2);
                }
                map.remove(this);
                map.remove(WEEK_BASED_YEAR);
                map.remove(ChronoField.DAY_OF_WEEK);
                return with;
            }

            public String toString() {
                return "WeekOfWeekBasedYear";
            }
        }

        enum 4 extends Field {
            /* synthetic */ 4(String str, int i, IsoFields-IA r3) {
                this(str, i);
            }

            private 4(String str, int i) {
                super(str, i, null);
            }

            public TemporalUnit getBaseUnit() {
                return IsoFields.WEEK_BASED_YEARS;
            }

            public TemporalUnit getRangeUnit() {
                return ChronoUnit.FOREVER;
            }

            public ValueRange range() {
                return ChronoField.YEAR.range();
            }

            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && IsoFields.isIso(temporalAccessor);
            }

            public long getFrom(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
                }
                return Field.-$$Nest$smgetWeekBasedYear(LocalDate.from(temporalAccessor));
            }

            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (!isSupportedBy(temporalAccessor)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
                }
                return super.rangeRefinedBy(temporalAccessor);
            }

            public Temporal adjustInto(Temporal temporal, long j) {
                if (!isSupportedBy(temporal)) {
                    throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
                }
                int checkValidIntValue = range().checkValidIntValue(j, WEEK_BASED_YEAR);
                LocalDate from = LocalDate.from(temporal);
                int i = from.get(ChronoField.DAY_OF_WEEK);
                int i2 = Field.-$$Nest$smgetWeek(from);
                if (i2 == 53 && Field.-$$Nest$smgetWeekRange(checkValidIntValue) == 52) {
                    i2 = 52;
                }
                return temporal.with(LocalDate.of(checkValidIntValue, 1, 4).plusDays((i - r5.get(ChronoField.DAY_OF_WEEK)) + ((i2 - 1) * 7)));
            }

            public String toString() {
                return "WeekBasedYear";
            }
        }

        public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
            return range();
        }

        private static void ensureIso(TemporalAccessor temporalAccessor) {
            if (!IsoFields.isIso(temporalAccessor)) {
                throw new DateTimeException("Resolve requires IsoChronology");
            }
        }

        private static ValueRange getWeekRange(LocalDate localDate) {
            return ValueRange.of(1L, getWeekRange(getWeekBasedYear(localDate)));
        }

        private static int getWeekRange(int i) {
            LocalDate of = LocalDate.of(i, 1, 1);
            if (of.getDayOfWeek() != DayOfWeek.THURSDAY) {
                return (of.getDayOfWeek() == DayOfWeek.WEDNESDAY && of.isLeapYear()) ? 53 : 52;
            }
            return 53;
        }

        private static int getWeek(LocalDate localDate) {
            int ordinal = localDate.getDayOfWeek().ordinal();
            int dayOfYear = localDate.getDayOfYear() - 1;
            int i = (3 - ordinal) + dayOfYear;
            int i2 = i - ((i / 7) * 7);
            int i3 = i2 - 3;
            if (i3 < -3) {
                i3 = i2 + 4;
            }
            if (dayOfYear < i3) {
                return (int) getWeekRange(localDate.withDayOfYear(180).minusYears(1L)).getMaximum();
            }
            int i4 = ((dayOfYear - i3) / 7) + 1;
            if (i4 != 53 || i3 == -3 || (i3 == -2 && localDate.isLeapYear())) {
                return i4;
            }
            return 1;
        }

        private static int getWeekBasedYear(LocalDate localDate) {
            int year = localDate.getYear();
            int dayOfYear = localDate.getDayOfYear();
            if (dayOfYear <= 3) {
                return dayOfYear - localDate.getDayOfWeek().ordinal() < -2 ? year - 1 : year;
            }
            if (dayOfYear >= 363) {
                return ((dayOfYear - 363) - (localDate.isLeapYear() ? 1 : 0)) - localDate.getDayOfWeek().ordinal() >= 0 ? year + 1 : year;
            }
            return year;
        }
    }

    private enum Unit implements TemporalUnit {
        WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952)),
        QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238));

        private final Duration duration;
        private final String name;

        public boolean isDateBased() {
            return true;
        }

        public boolean isDurationEstimated() {
            return true;
        }

        public boolean isTimeBased() {
            return false;
        }

        Unit(String str, Duration duration) {
            this.name = str;
            this.duration = duration;
        }

        public Duration getDuration() {
            return this.duration;
        }

        public boolean isSupportedBy(Temporal temporal) {
            return temporal.isSupported(ChronoField.EPOCH_DAY) && IsoFields.isIso(temporal);
        }

        public Temporal addTo(Temporal temporal, long j) {
            int i = 1.$SwitchMap$java$time$temporal$IsoFields$Unit[ordinal()];
            if (i == 1) {
                return temporal.with(IsoFields.WEEK_BASED_YEAR, IsoFields$Unit$$ExternalSyntheticBackport1.m(temporal.get(IsoFields.WEEK_BASED_YEAR), j));
            }
            if (i == 2) {
                return temporal.plus(j / 4, ChronoUnit.YEARS).plus((j % 4) * 3, ChronoUnit.MONTHS);
            }
            throw new IllegalStateException("Unreachable");
        }

        public long between(Temporal temporal, Temporal temporal2) {
            if (temporal.getClass() != temporal2.getClass()) {
                return temporal.until(temporal2, this);
            }
            int i = 1.$SwitchMap$java$time$temporal$IsoFields$Unit[ordinal()];
            if (i == 1) {
                return IsoFields$Unit$$ExternalSyntheticBackport0.m(temporal2.getLong(IsoFields.WEEK_BASED_YEAR), temporal.getLong(IsoFields.WEEK_BASED_YEAR));
            }
            if (i == 2) {
                return temporal.until(temporal2, ChronoUnit.MONTHS) / 3;
            }
            throw new IllegalStateException("Unreachable");
        }

        public String toString() {
            return this.name;
        }
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$IsoFields$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$java$time$temporal$IsoFields$Unit = iArr;
            try {
                iArr[Unit.WEEK_BASED_YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$IsoFields$Unit[Unit.QUARTER_YEARS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static boolean isIso(TemporalAccessor temporalAccessor) {
        return Chronology.-CC.from(temporalAccessor).equals(IsoChronology.INSTANCE);
    }
}
