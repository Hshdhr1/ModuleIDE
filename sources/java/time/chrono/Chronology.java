package java.time.chrono;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Chronology extends Comparable {
    int compareTo(Chronology chronology);

    ChronoLocalDate date(int i, int i2, int i3);

    ChronoLocalDate date(Era era, int i, int i2, int i3);

    ChronoLocalDate date(TemporalAccessor temporalAccessor);

    ChronoLocalDate dateEpochDay(long j);

    ChronoLocalDate dateNow();

    ChronoLocalDate dateNow(Clock clock);

    ChronoLocalDate dateNow(ZoneId zoneId);

    ChronoLocalDate dateYearDay(int i, int i2);

    ChronoLocalDate dateYearDay(Era era, int i, int i2);

    long epochSecond(int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset);

    long epochSecond(Era era, int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset);

    boolean equals(Object obj);

    Era eraOf(int i);

    List eras();

    String getCalendarType();

    String getDisplayName(TextStyle textStyle, Locale locale);

    String getId();

    int hashCode();

    boolean isLeapYear(long j);

    ChronoLocalDateTime localDateTime(TemporalAccessor temporalAccessor);

    ChronoPeriod period(int i, int i2, int i3);

    int prolepticYear(Era era, int i);

    ValueRange range(ChronoField chronoField);

    ChronoLocalDate resolveDate(Map map, ResolverStyle resolverStyle);

    String toString();

    ChronoZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId);

    ChronoZonedDateTime zonedDateTime(TemporalAccessor temporalAccessor);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ int $default$compareTo(Chronology _this, Object obj) {
            return _this.compareTo((Chronology) obj);
        }

        public static Chronology from(TemporalAccessor temporalAccessor) {
            Chronology$$ExternalSyntheticBackport0.m(temporalAccessor, "temporal");
            return (Chronology) Chronology$$ExternalSyntheticBackport3.m((Chronology) temporalAccessor.query(TemporalQueries.chronology()), IsoChronology.INSTANCE);
        }

        public static Chronology ofLocale(Locale locale) {
            return AbstractChronology.ofLocale(locale);
        }

        public static Chronology of(String str) {
            return AbstractChronology.of(str);
        }

        public static Set getAvailableChronologies() {
            return AbstractChronology.getAvailableChronologies();
        }

        public static ChronoLocalDate $default$date(Chronology _this, Era era, int i, int i2, int i3) {
            return _this.date(_this.prolepticYear(era, i), i2, i3);
        }

        public static ChronoLocalDate $default$dateYearDay(Chronology _this, Era era, int i, int i2) {
            return _this.dateYearDay(_this.prolepticYear(era, i), i2);
        }

        public static ChronoLocalDate $default$dateNow(Chronology _this) {
            return _this.dateNow(Clock.systemDefaultZone());
        }

        public static ChronoLocalDate $default$dateNow(Chronology _this, ZoneId zoneId) {
            return _this.dateNow(Clock.system(zoneId));
        }

        public static ChronoLocalDate $default$dateNow(Chronology _this, Clock clock) {
            Chronology$$ExternalSyntheticBackport0.m(clock, "clock");
            return _this.date(LocalDate.now(clock));
        }

        public static ChronoLocalDateTime $default$localDateTime(Chronology _this, TemporalAccessor temporalAccessor) {
            try {
                return _this.date(temporalAccessor).atTime(LocalTime.from(temporalAccessor));
            } catch (DateTimeException e) {
                throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporalAccessor.getClass(), e);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v6, types: [java.time.chrono.ChronoZonedDateTime] */
        public static ChronoZonedDateTime $default$zonedDateTime(Chronology _this, TemporalAccessor temporalAccessor) {
            try {
                ZoneId from = ZoneId.from(temporalAccessor);
                try {
                    temporalAccessor = _this.zonedDateTime(Instant.from(temporalAccessor), from);
                    return temporalAccessor;
                } catch (DateTimeException unused) {
                    return ChronoZonedDateTimeImpl.ofBest(ChronoLocalDateTimeImpl.ensureValid(_this, _this.localDateTime(temporalAccessor)), from, null);
                }
            } catch (DateTimeException e) {
                throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + temporalAccessor.getClass(), e);
            }
        }

        public static ChronoZonedDateTime $default$zonedDateTime(Chronology _this, Instant instant, ZoneId zoneId) {
            return ChronoZonedDateTimeImpl.ofInstant(_this, instant, zoneId);
        }

        public static String $default$getDisplayName(Chronology _this, TextStyle textStyle, Locale locale) {
            return new DateTimeFormatterBuilder().appendChronologyText(textStyle).toFormatter(locale).format(_this.new 1());
        }

        public static ChronoPeriod $default$period(Chronology _this, int i, int i2, int i3) {
            return new ChronoPeriodImpl(_this, i, i2, i3);
        }

        public static long $default$epochSecond(Chronology _this, int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
            Chronology$$ExternalSyntheticBackport0.m(zoneOffset, "zoneOffset");
            ChronoField.HOUR_OF_DAY.checkValidValue(i4);
            ChronoField.MINUTE_OF_HOUR.checkValidValue(i5);
            ChronoField.SECOND_OF_MINUTE.checkValidValue(i6);
            return Chronology$$ExternalSyntheticBackport2.m(Chronology$$ExternalSyntheticBackport1.m(_this.date(i, i2, i3).toEpochDay(), 86400), ((((i4 * 60) + i5) * 60) + i6) - zoneOffset.getTotalSeconds());
        }

        public static long $default$epochSecond(Chronology _this, Era era, int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
            Chronology$$ExternalSyntheticBackport0.m(era, "era");
            return _this.epochSecond(_this.prolepticYear(era, i), i2, i3, i4, i5, i6, zoneOffset);
        }
    }

    class 1 implements TemporalAccessor {
        public /* synthetic */ int get(TemporalField temporalField) {
            return TemporalAccessor.-CC.$default$get(this, temporalField);
        }

        public boolean isSupported(TemporalField temporalField) {
            return false;
        }

        public /* synthetic */ ValueRange range(TemporalField temporalField) {
            return TemporalAccessor.-CC.$default$range(this, temporalField);
        }

        1() {
        }

        public long getLong(TemporalField temporalField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }

        public Object query(TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.chronology()) {
                return Chronology.this;
            }
            return TemporalAccessor.-CC.$default$query(this, temporalQuery);
        }
    }
}
