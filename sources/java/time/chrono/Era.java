package java.time.chrono;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Era extends TemporalAccessor, TemporalAdjuster {
    Temporal adjustInto(Temporal temporal);

    int get(TemporalField temporalField);

    String getDisplayName(TextStyle textStyle, Locale locale);

    long getLong(TemporalField temporalField);

    int getValue();

    boolean isSupported(TemporalField temporalField);

    Object query(TemporalQuery temporalQuery);

    ValueRange range(TemporalField temporalField);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static boolean $default$isSupported(Era _this, TemporalField temporalField) {
            return temporalField instanceof ChronoField ? temporalField == ChronoField.ERA : temporalField != null && temporalField.isSupportedBy(_this);
        }

        public static ValueRange $default$range(Era _this, TemporalField temporalField) {
            return TemporalAccessor.-CC.$default$range(_this, temporalField);
        }

        public static int $default$get(Era _this, TemporalField temporalField) {
            if (temporalField == ChronoField.ERA) {
                return _this.getValue();
            }
            return TemporalAccessor.-CC.$default$get(_this, temporalField);
        }

        public static long $default$getLong(Era _this, TemporalField temporalField) {
            if (temporalField == ChronoField.ERA) {
                return _this.getValue();
            }
            if (temporalField instanceof ChronoField) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            return temporalField.getFrom(_this);
        }

        public static Object $default$query(Era _this, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.precision()) {
                return ChronoUnit.ERAS;
            }
            return TemporalAccessor.-CC.$default$query(_this, temporalQuery);
        }

        public static Temporal $default$adjustInto(Era _this, Temporal temporal) {
            return temporal.with(ChronoField.ERA, _this.getValue());
        }

        public static String $default$getDisplayName(Era _this, TextStyle textStyle, Locale locale) {
            return new DateTimeFormatterBuilder().appendText(ChronoField.ERA, textStyle).toFormatter(locale).format(_this);
        }
    }
}
