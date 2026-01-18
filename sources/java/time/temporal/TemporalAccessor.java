package java.time.temporal;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.DateTimeException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface TemporalAccessor {
    int get(TemporalField temporalField);

    long getLong(TemporalField temporalField);

    boolean isSupported(TemporalField temporalField);

    Object query(TemporalQuery temporalQuery);

    ValueRange range(TemporalField temporalField);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static ValueRange $default$range(TemporalAccessor _this, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                if (_this.isSupported(temporalField)) {
                    return temporalField.range();
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            TemporalAccessor$$ExternalSyntheticBackport0.m(temporalField, "field");
            return temporalField.rangeRefinedBy(_this);
        }

        public static int $default$get(TemporalAccessor _this, TemporalField temporalField) {
            ValueRange range = _this.range(temporalField);
            if (!range.isIntValue()) {
                throw new UnsupportedTemporalTypeException("Invalid field " + temporalField + " for get() method, use getLong() instead");
            }
            long j = _this.getLong(temporalField);
            if (range.isValidValue(j)) {
                return (int) j;
            }
            throw new DateTimeException("Invalid value for " + temporalField + " (valid values " + range + "): " + j);
        }

        public static Object $default$query(TemporalAccessor _this, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.chronology() || temporalQuery == TemporalQueries.precision()) {
                return null;
            }
            return temporalQuery.queryFrom(_this);
        }
    }
}
