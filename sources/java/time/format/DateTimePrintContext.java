package java.time.format;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class DateTimePrintContext {
    private DateTimeFormatter formatter;
    private int optional;
    private TemporalAccessor temporal;

    DateTimePrintContext(TemporalAccessor temporalAccessor, DateTimeFormatter dateTimeFormatter) {
        this.temporal = adjust(temporalAccessor, dateTimeFormatter);
        this.formatter = dateTimeFormatter;
    }

    private static TemporalAccessor adjust(TemporalAccessor temporalAccessor, DateTimeFormatter dateTimeFormatter) {
        Chronology chronology = dateTimeFormatter.getChronology();
        ZoneId zone = dateTimeFormatter.getZone();
        if (chronology != null || zone != null) {
            Chronology chronology2 = (Chronology) temporalAccessor.query(TemporalQueries.chronology());
            ZoneId zoneId = (ZoneId) temporalAccessor.query(TemporalQueries.zoneId());
            ChronoLocalDate chronoLocalDate = null;
            if (DateTimePrintContext$$ExternalSyntheticBackport1.m(chronology, chronology2)) {
                chronology = null;
            }
            if (DateTimePrintContext$$ExternalSyntheticBackport1.m(zone, zoneId)) {
                zone = null;
            }
            if (chronology != null || zone != null) {
                Chronology chronology3 = chronology != null ? chronology : chronology2;
                if (zone != null) {
                    if (temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
                        return ((Chronology) DateTimePrintContext$$ExternalSyntheticBackport2.m(chronology3, IsoChronology.INSTANCE)).zonedDateTime(Instant.from(temporalAccessor), zone);
                    }
                    if ((zone.normalized() instanceof ZoneOffset) && temporalAccessor.isSupported(ChronoField.OFFSET_SECONDS) && temporalAccessor.get(ChronoField.OFFSET_SECONDS) != zone.getRules().getOffset(Instant.EPOCH).getTotalSeconds()) {
                        throw new DateTimeException("Unable to apply override zone '" + zone + "' because the temporal object being formatted has a different offset but does not represent an instant: " + temporalAccessor);
                    }
                }
                if (zone != null) {
                    zoneId = zone;
                }
                if (chronology != null) {
                    if (temporalAccessor.isSupported(ChronoField.EPOCH_DAY)) {
                        chronoLocalDate = chronology3.date(temporalAccessor);
                    } else if (chronology != IsoChronology.INSTANCE || chronology2 != null) {
                        for (ChronoField chronoField : ChronoField.values()) {
                            if (chronoField.isDateBased() && temporalAccessor.isSupported(chronoField)) {
                                throw new DateTimeException("Unable to apply override chronology '" + chronology + "' because the temporal object being formatted contains date fields but does not represent a whole date: " + temporalAccessor);
                            }
                        }
                    }
                }
                return new 1(chronoLocalDate, temporalAccessor, chronology3, zoneId);
            }
        }
        return temporalAccessor;
    }

    class 1 implements TemporalAccessor {
        final /* synthetic */ Chronology val$effectiveChrono;
        final /* synthetic */ ChronoLocalDate val$effectiveDate;
        final /* synthetic */ ZoneId val$effectiveZone;
        final /* synthetic */ TemporalAccessor val$temporal;

        public /* synthetic */ int get(TemporalField temporalField) {
            return TemporalAccessor.-CC.$default$get(this, temporalField);
        }

        1(ChronoLocalDate chronoLocalDate, TemporalAccessor temporalAccessor, Chronology chronology, ZoneId zoneId) {
            this.val$effectiveDate = chronoLocalDate;
            this.val$temporal = temporalAccessor;
            this.val$effectiveChrono = chronology;
            this.val$effectiveZone = zoneId;
        }

        public boolean isSupported(TemporalField temporalField) {
            if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                return this.val$effectiveDate.isSupported(temporalField);
            }
            return this.val$temporal.isSupported(temporalField);
        }

        public ValueRange range(TemporalField temporalField) {
            if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                return this.val$effectiveDate.range(temporalField);
            }
            return this.val$temporal.range(temporalField);
        }

        public long getLong(TemporalField temporalField) {
            if (this.val$effectiveDate != null && temporalField.isDateBased()) {
                return this.val$effectiveDate.getLong(temporalField);
            }
            return this.val$temporal.getLong(temporalField);
        }

        public Object query(TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.chronology()) {
                return this.val$effectiveChrono;
            }
            if (temporalQuery == TemporalQueries.zoneId()) {
                return this.val$effectiveZone;
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return this.val$temporal.query(temporalQuery);
            }
            return temporalQuery.queryFrom(this);
        }

        public String toString() {
            String str;
            TemporalAccessor temporalAccessor = this.val$temporal;
            Chronology chronology = this.val$effectiveChrono;
            String str2 = "";
            if (chronology != null) {
                str = " with chronology " + chronology;
            } else {
                str = "";
            }
            ZoneId zoneId = this.val$effectiveZone;
            if (zoneId != null) {
                str2 = " with zone " + zoneId;
            }
            return temporalAccessor + str + str2;
        }
    }

    TemporalAccessor getTemporal() {
        return this.temporal;
    }

    Locale getLocale() {
        return this.formatter.getLocale();
    }

    DecimalStyle getDecimalStyle() {
        return this.formatter.getDecimalStyle();
    }

    void startOptional() {
        this.optional++;
    }

    void endOptional() {
        this.optional--;
    }

    Object getValue(TemporalQuery temporalQuery) {
        Object query = this.temporal.query(temporalQuery);
        if (query != null || this.optional != 0) {
            return query;
        }
        throw new DateTimeException("Unable to extract " + temporalQuery + " from temporal " + this.temporal);
    }

    Long getValue(TemporalField temporalField) {
        if (this.optional <= 0 || this.temporal.isSupported(temporalField)) {
            return Long.valueOf(this.temporal.getLong(temporalField));
        }
        return null;
    }

    public String toString() {
        return this.temporal.toString();
    }
}
