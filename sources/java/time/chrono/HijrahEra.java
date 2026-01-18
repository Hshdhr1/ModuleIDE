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
public enum HijrahEra implements Era {
    AH;

    public /* synthetic */ Temporal adjustInto(Temporal temporal) {
        return Era.-CC.$default$adjustInto(this, temporal);
    }

    public /* synthetic */ int get(TemporalField temporalField) {
        return Era.-CC.$default$get(this, temporalField);
    }

    public /* synthetic */ long getLong(TemporalField temporalField) {
        return Era.-CC.$default$getLong(this, temporalField);
    }

    public int getValue() {
        return 1;
    }

    public /* synthetic */ boolean isSupported(TemporalField temporalField) {
        return Era.-CC.$default$isSupported(this, temporalField);
    }

    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return Era.-CC.$default$query(this, temporalQuery);
    }

    public static HijrahEra of(int i) {
        if (i == 1) {
            return AH;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return ValueRange.of(1L, 1L);
        }
        return Era.-CC.$default$range(this, temporalField);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(ChronoField.ERA, textStyle).toFormatter(locale).withChronology(HijrahChronology.INSTANCE).format(HijrahDate.now());
    }
}
