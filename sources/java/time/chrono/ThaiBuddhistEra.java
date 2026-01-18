package java.time.chrono;

import java.time.DateTimeException;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public enum ThaiBuddhistEra implements Era {
    BEFORE_BE,
    BE;

    public /* synthetic */ Temporal adjustInto(Temporal temporal) {
        return Era.-CC.$default$adjustInto(this, temporal);
    }

    public /* synthetic */ int get(TemporalField temporalField) {
        return Era.-CC.$default$get(this, temporalField);
    }

    public /* synthetic */ long getLong(TemporalField temporalField) {
        return Era.-CC.$default$getLong(this, temporalField);
    }

    public /* synthetic */ boolean isSupported(TemporalField temporalField) {
        return Era.-CC.$default$isSupported(this, temporalField);
    }

    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return Era.-CC.$default$query(this, temporalQuery);
    }

    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return Era.-CC.$default$range(this, temporalField);
    }

    public static ThaiBuddhistEra of(int i) {
        if (i == 0) {
            return BEFORE_BE;
        }
        if (i == 1) {
            return BE;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    public int getValue() {
        return ordinal();
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(ChronoField.ERA, textStyle).toFormatter(locale).withChronology(ThaiBuddhistChronology.INSTANCE).format(this == BE ? ThaiBuddhistDate.of(1, 1, 1) : ThaiBuddhistDate.of(0, 1, 1));
    }
}
