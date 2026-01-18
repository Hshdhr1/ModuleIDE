package java.time.format;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DateTimeFormatter {
    public static final DateTimeFormatter BASIC_ISO_DATE;
    public static final DateTimeFormatter ISO_DATE;
    public static final DateTimeFormatter ISO_DATE_TIME;
    public static final DateTimeFormatter ISO_INSTANT;
    public static final DateTimeFormatter ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter ISO_LOCAL_TIME;
    public static final DateTimeFormatter ISO_OFFSET_DATE;
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter ISO_OFFSET_TIME;
    public static final DateTimeFormatter ISO_ORDINAL_DATE;
    public static final DateTimeFormatter ISO_TIME;
    public static final DateTimeFormatter ISO_WEEK_DATE;
    public static final DateTimeFormatter ISO_ZONED_DATE_TIME;
    private static final TemporalQuery PARSED_EXCESS_DAYS;
    private static final TemporalQuery PARSED_LEAP_SECOND;
    public static final DateTimeFormatter RFC_1123_DATE_TIME;
    private final Chronology chrono;
    private final DecimalStyle decimalStyle;
    private final Locale locale;
    private final DateTimeFormatterBuilder.CompositePrinterParser printerParser;
    private final Set resolverFields;
    private final ResolverStyle resolverStyle;
    private final ZoneId zone;

    static /* bridge */ /* synthetic */ Set -$$Nest$fgetresolverFields(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.resolverFields;
    }

    static /* bridge */ /* synthetic */ ResolverStyle -$$Nest$fgetresolverStyle(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.resolverStyle;
    }

    static /* bridge */ /* synthetic */ TemporalAccessor -$$Nest$mparseResolved0(DateTimeFormatter dateTimeFormatter, CharSequence charSequence, ParsePosition parsePosition) {
        return dateTimeFormatter.parseResolved0(charSequence, parsePosition);
    }

    static /* bridge */ /* synthetic */ DateTimeParseContext -$$Nest$mparseUnresolved0(DateTimeFormatter dateTimeFormatter, CharSequence charSequence, ParsePosition parsePosition) {
        return dateTimeFormatter.parseUnresolved0(charSequence, parsePosition);
    }

    public static DateTimeFormatter ofPattern(String str) {
        return new DateTimeFormatterBuilder().appendPattern(str).toFormatter();
    }

    public static DateTimeFormatter ofPattern(String str, Locale locale) {
        return new DateTimeFormatterBuilder().appendPattern(str).toFormatter(locale);
    }

    public static DateTimeFormatter ofLocalizedDate(FormatStyle formatStyle) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(formatStyle, "dateStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, null).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedTime(FormatStyle formatStyle) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(formatStyle, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(null, formatStyle).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle formatStyle) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(formatStyle, "dateTimeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, formatStyle).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle formatStyle, FormatStyle formatStyle2) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(formatStyle, "dateStyle");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(formatStyle2, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, formatStyle2).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    static {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_LOCAL_DATE = formatter;
        ISO_OFFSET_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter).appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        DateTimeFormatter formatter2 = new DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalStart().appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter(ResolverStyle.STRICT, null);
        ISO_LOCAL_TIME = formatter2;
        ISO_OFFSET_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter2).appendOffsetId().toFormatter(ResolverStyle.STRICT, null);
        ISO_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter2).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, null);
        DateTimeFormatter formatter3 = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter).appendLiteral('T').append(formatter2).toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_LOCAL_DATE_TIME = formatter3;
        DateTimeFormatter formatter4 = new DateTimeFormatterBuilder().parseCaseInsensitive().append(formatter3).parseLenient().appendOffsetId().parseStrict().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_OFFSET_DATE_TIME = formatter4;
        ISO_ZONED_DATE_TIME = new DateTimeFormatterBuilder().append(formatter4).optionalStart().appendLiteral('[').parseCaseSensitive().appendZoneRegionId().appendLiteral(']').toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_DATE_TIME = new DateTimeFormatterBuilder().append(formatter3).optionalStart().appendOffsetId().optionalStart().appendLiteral('[').parseCaseSensitive().appendZoneRegionId().appendLiteral(']').toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_ORDINAL_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.DAY_OF_YEAR, 3).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_WEEK_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(IsoFields.WEEK_BASED_YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral("-W").appendValue(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_WEEK, 1).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_INSTANT = new DateTimeFormatterBuilder().parseCaseInsensitive().appendInstant().toFormatter(ResolverStyle.STRICT, null);
        BASIC_ISO_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(ChronoField.YEAR, 4).appendValue(ChronoField.MONTH_OF_YEAR, 2).appendValue(ChronoField.DAY_OF_MONTH, 2).optionalStart().parseLenient().appendOffset("+HHMMss", "Z").parseStrict().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        Map hashMap = new HashMap();
        hashMap.put(1L, "Mon");
        hashMap.put(2L, "Tue");
        hashMap.put(3L, "Wed");
        hashMap.put(4L, "Thu");
        hashMap.put(5L, "Fri");
        hashMap.put(6L, "Sat");
        hashMap.put(7L, "Sun");
        Map hashMap2 = new HashMap();
        hashMap2.put(1L, "Jan");
        hashMap2.put(2L, "Feb");
        hashMap2.put(3L, "Mar");
        hashMap2.put(4L, "Apr");
        hashMap2.put(5L, "May");
        hashMap2.put(6L, "Jun");
        hashMap2.put(7L, "Jul");
        hashMap2.put(8L, "Aug");
        hashMap2.put(9L, "Sep");
        hashMap2.put(10L, "Oct");
        hashMap2.put(11L, "Nov");
        hashMap2.put(12L, "Dec");
        RFC_1123_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient().optionalStart().appendText(ChronoField.DAY_OF_WEEK, hashMap).appendLiteral(", ").optionalEnd().appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(' ').appendText(ChronoField.MONTH_OF_YEAR, hashMap2).appendLiteral(' ').appendValue(ChronoField.YEAR, 4).appendLiteral(' ').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalStart().appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalEnd().appendLiteral(' ').appendOffset("+HHMM", "GMT").toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
        PARSED_EXCESS_DAYS = new DateTimeFormatter$$ExternalSyntheticLambda2();
        PARSED_LEAP_SECOND = new DateTimeFormatter$$ExternalSyntheticLambda3();
    }

    public static final TemporalQuery parsedExcessDays() {
        return PARSED_EXCESS_DAYS;
    }

    static /* synthetic */ Period lambda$static$0(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Parsed) {
            return ((Parsed) temporalAccessor).excessDays;
        }
        return Period.ZERO;
    }

    public static final TemporalQuery parsedLeapSecond() {
        return PARSED_LEAP_SECOND;
    }

    static /* synthetic */ Boolean lambda$static$1(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Parsed) {
            return Boolean.valueOf(((Parsed) temporalAccessor).leapSecond);
        }
        return Boolean.FALSE;
    }

    DateTimeFormatter(DateTimeFormatterBuilder.CompositePrinterParser compositePrinterParser, Locale locale, DecimalStyle decimalStyle, ResolverStyle resolverStyle, Set set, Chronology chronology, ZoneId zoneId) {
        this.printerParser = (DateTimeFormatterBuilder.CompositePrinterParser) DateTimeFormatter$$ExternalSyntheticBackport1.m(compositePrinterParser, "printerParser");
        this.resolverFields = set;
        this.locale = (Locale) DateTimeFormatter$$ExternalSyntheticBackport1.m(locale, "locale");
        this.decimalStyle = (DecimalStyle) DateTimeFormatter$$ExternalSyntheticBackport1.m(decimalStyle, "decimalStyle");
        this.resolverStyle = (ResolverStyle) DateTimeFormatter$$ExternalSyntheticBackport1.m(resolverStyle, "resolverStyle");
        this.chrono = chronology;
        this.zone = zoneId;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public DateTimeFormatter withLocale(Locale locale) {
        return this.locale.equals(locale) ? this : new DateTimeFormatter(this.printerParser, locale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public DecimalStyle getDecimalStyle() {
        return this.decimalStyle;
    }

    public DateTimeFormatter withDecimalStyle(DecimalStyle decimalStyle) {
        return this.decimalStyle.equals(decimalStyle) ? this : new DateTimeFormatter(this.printerParser, this.locale, decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public Chronology getChronology() {
        return this.chrono;
    }

    public DateTimeFormatter withChronology(Chronology chronology) {
        return DateTimeFormatter$$ExternalSyntheticBackport0.m(this.chrono, chronology) ? this : new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, chronology, this.zone);
    }

    public ZoneId getZone() {
        return this.zone;
    }

    public DateTimeFormatter withZone(ZoneId zoneId) {
        return DateTimeFormatter$$ExternalSyntheticBackport0.m(this.zone, zoneId) ? this : new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, zoneId);
    }

    public ResolverStyle getResolverStyle() {
        return this.resolverStyle;
    }

    public DateTimeFormatter withResolverStyle(ResolverStyle resolverStyle) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(resolverStyle, "resolverStyle");
        return DateTimeFormatter$$ExternalSyntheticBackport0.m(this.resolverStyle, resolverStyle) ? this : new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public Set getResolverFields() {
        return this.resolverFields;
    }

    public DateTimeFormatter withResolverFields(TemporalField... temporalFieldArr) {
        Set unmodifiableSet = temporalFieldArr != null ? Collections.unmodifiableSet(new HashSet(Arrays.asList(temporalFieldArr))) : null;
        return DateTimeFormatter$$ExternalSyntheticBackport0.m(this.resolverFields, unmodifiableSet) ? this : new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, unmodifiableSet, this.chrono, this.zone);
    }

    public DateTimeFormatter withResolverFields(Set set) {
        if (DateTimeFormatter$$ExternalSyntheticBackport0.m(this.resolverFields, set)) {
            return this;
        }
        if (set != null) {
            set = Collections.unmodifiableSet(new HashSet(set));
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, set, this.chrono, this.zone);
    }

    public String format(TemporalAccessor temporalAccessor) {
        StringBuilder sb = new StringBuilder(32);
        formatTo(temporalAccessor, sb);
        return sb.toString();
    }

    public void formatTo(TemporalAccessor temporalAccessor, Appendable appendable) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(temporalAccessor, "temporal");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(appendable, "appendable");
        try {
            DateTimePrintContext dateTimePrintContext = new DateTimePrintContext(temporalAccessor, this);
            if (appendable instanceof StringBuilder) {
                this.printerParser.format(dateTimePrintContext, (StringBuilder) appendable);
                return;
            }
            StringBuilder sb = new StringBuilder(32);
            this.printerParser.format(dateTimePrintContext, sb);
            appendable.append(sb);
        } catch (IOException e) {
            throw new DateTimeException(e.getMessage(), e);
        }
    }

    public TemporalAccessor parse(CharSequence charSequence) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(charSequence, "text");
        try {
            return parseResolved0(charSequence, null);
        } catch (DateTimeParseException e) {
            throw e;
        } catch (RuntimeException e2) {
            throw createError(charSequence, e2);
        }
    }

    public TemporalAccessor parse(CharSequence charSequence, ParsePosition parsePosition) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(charSequence, "text");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(parsePosition, "position");
        try {
            return parseResolved0(charSequence, parsePosition);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        } catch (DateTimeParseException e2) {
            throw e2;
        } catch (RuntimeException e3) {
            throw createError(charSequence, e3);
        }
    }

    public Object parse(CharSequence charSequence, TemporalQuery temporalQuery) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(charSequence, "text");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(temporalQuery, "query");
        try {
            return parseResolved0(charSequence, null).query(temporalQuery);
        } catch (DateTimeParseException e) {
            throw e;
        } catch (RuntimeException e2) {
            throw createError(charSequence, e2);
        }
    }

    public TemporalAccessor parseBest(CharSequence charSequence, TemporalQuery... temporalQueryArr) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(charSequence, "text");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(temporalQueryArr, "queries");
        if (temporalQueryArr.length < 2) {
            throw new IllegalArgumentException("At least two queries must be specified");
        }
        try {
            try {
                TemporalAccessor parseResolved0 = parseResolved0(charSequence, null);
                for (TemporalQuery temporalQuery : temporalQueryArr) {
                    try {
                        return (TemporalAccessor) parseResolved0.query(temporalQuery);
                    } catch (RuntimeException unused) {
                    }
                }
                throw new DateTimeException("Unable to convert parsed text using any of the specified queries");
            } catch (DateTimeParseException e) {
                throw e;
            }
        } catch (RuntimeException e2) {
            throw createError(charSequence, e2);
        }
    }

    private DateTimeParseException createError(CharSequence charSequence, RuntimeException runtimeException) {
        String charSequence2;
        if (charSequence.length() > 64) {
            charSequence2 = charSequence.subSequence(0, 64).toString() + "...";
        } else {
            charSequence2 = charSequence.toString();
        }
        return new DateTimeParseException("Text '" + charSequence2 + "' could not be parsed: " + runtimeException.getMessage(), charSequence, 0, runtimeException);
    }

    private TemporalAccessor parseResolved0(CharSequence charSequence, ParsePosition parsePosition) {
        String charSequence2;
        ParsePosition parsePosition2 = parsePosition != null ? parsePosition : new ParsePosition(0);
        DateTimeParseContext parseUnresolved0 = parseUnresolved0(charSequence, parsePosition2);
        if (parseUnresolved0 == null || parsePosition2.getErrorIndex() >= 0 || (parsePosition == null && parsePosition2.getIndex() < charSequence.length())) {
            if (charSequence.length() > 64) {
                charSequence2 = charSequence.subSequence(0, 64).toString() + "...";
            } else {
                charSequence2 = charSequence.toString();
            }
            if (parsePosition2.getErrorIndex() >= 0) {
                throw new DateTimeParseException("Text '" + charSequence2 + "' could not be parsed at index " + parsePosition2.getErrorIndex(), charSequence, parsePosition2.getErrorIndex());
            }
            throw new DateTimeParseException("Text '" + charSequence2 + "' could not be parsed, unparsed text found at index " + parsePosition2.getIndex(), charSequence, parsePosition2.getIndex());
        }
        return parseUnresolved0.toResolved(this.resolverStyle, this.resolverFields);
    }

    public TemporalAccessor parseUnresolved(CharSequence charSequence, ParsePosition parsePosition) {
        DateTimeParseContext parseUnresolved0 = parseUnresolved0(charSequence, parsePosition);
        if (parseUnresolved0 == null) {
            return null;
        }
        return parseUnresolved0.toUnresolved();
    }

    private DateTimeParseContext parseUnresolved0(CharSequence charSequence, ParsePosition parsePosition) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(charSequence, "text");
        DateTimeFormatter$$ExternalSyntheticBackport1.m(parsePosition, "position");
        DateTimeParseContext dateTimeParseContext = new DateTimeParseContext(this);
        int parse = this.printerParser.parse(dateTimeParseContext, charSequence, parsePosition.getIndex());
        if (parse < 0) {
            parsePosition.setErrorIndex(parse ^ (-1));
            return null;
        }
        parsePosition.setIndex(parse);
        return dateTimeParseContext;
    }

    DateTimeFormatterBuilder.CompositePrinterParser toPrinterParser(boolean z) {
        return this.printerParser.withOptional(z);
    }

    public Format toFormat() {
        return new ClassicFormat(this, null);
    }

    public Format toFormat(TemporalQuery temporalQuery) {
        DateTimeFormatter$$ExternalSyntheticBackport1.m(temporalQuery, "parseQuery");
        return new ClassicFormat(this, temporalQuery);
    }

    public String toString() {
        String compositePrinterParser = this.printerParser.toString();
        return compositePrinterParser.startsWith("[") ? compositePrinterParser : compositePrinterParser.substring(1, compositePrinterParser.length() - 1);
    }

    static class ClassicFormat extends Format {
        private final DateTimeFormatter formatter;
        private final TemporalQuery parseType;

        public ClassicFormat(DateTimeFormatter dateTimeFormatter, TemporalQuery temporalQuery) {
            this.formatter = dateTimeFormatter;
            this.parseType = temporalQuery;
        }

        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            DateTimeFormatter$ClassicFormat$$ExternalSyntheticBackport0.m(obj, "obj");
            DateTimeFormatter$ClassicFormat$$ExternalSyntheticBackport0.m(stringBuffer, "toAppendTo");
            DateTimeFormatter$ClassicFormat$$ExternalSyntheticBackport0.m(fieldPosition, "pos");
            if (!(obj instanceof TemporalAccessor)) {
                throw new IllegalArgumentException("Format target must implement TemporalAccessor");
            }
            fieldPosition.setBeginIndex(0);
            fieldPosition.setEndIndex(0);
            try {
                this.formatter.formatTo((TemporalAccessor) obj, stringBuffer);
                return stringBuffer;
            } catch (RuntimeException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        public Object parseObject(String str) throws ParseException {
            DateTimeFormatter$ClassicFormat$$ExternalSyntheticBackport0.m(str, "text");
            try {
                TemporalQuery temporalQuery = this.parseType;
                if (temporalQuery == null) {
                    return DateTimeFormatter.-$$Nest$mparseResolved0(this.formatter, str, null);
                }
                return this.formatter.parse(str, temporalQuery);
            } catch (DateTimeParseException e) {
                throw new ParseException(e.getMessage(), e.getErrorIndex());
            } catch (RuntimeException e2) {
                throw new ParseException(e2.getMessage(), 0).initCause(e2);
            }
        }

        public Object parseObject(String str, ParsePosition parsePosition) {
            DateTimeFormatter$ClassicFormat$$ExternalSyntheticBackport0.m(str, "text");
            try {
                DateTimeParseContext dateTimeParseContext = DateTimeFormatter.-$$Nest$mparseUnresolved0(this.formatter, str, parsePosition);
                if (dateTimeParseContext == null) {
                    if (parsePosition.getErrorIndex() < 0) {
                        parsePosition.setErrorIndex(0);
                    }
                    return null;
                }
                try {
                    TemporalAccessor resolved = dateTimeParseContext.toResolved(DateTimeFormatter.-$$Nest$fgetresolverStyle(this.formatter), DateTimeFormatter.-$$Nest$fgetresolverFields(this.formatter));
                    TemporalQuery temporalQuery = this.parseType;
                    return temporalQuery == null ? resolved : resolved.query(temporalQuery);
                } catch (RuntimeException unused) {
                    parsePosition.setErrorIndex(0);
                    return null;
                }
            } catch (IndexOutOfBoundsException unused2) {
                if (parsePosition.getErrorIndex() < 0) {
                    parsePosition.setErrorIndex(0);
                }
                return null;
            }
        }
    }
}
