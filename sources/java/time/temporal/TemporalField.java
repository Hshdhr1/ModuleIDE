package java.time.temporal;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface TemporalField {
    Temporal adjustInto(Temporal temporal, long j);

    TemporalUnit getBaseUnit();

    String getDisplayName(Locale locale);

    long getFrom(TemporalAccessor temporalAccessor);

    TemporalUnit getRangeUnit();

    boolean isDateBased();

    boolean isSupportedBy(TemporalAccessor temporalAccessor);

    boolean isTimeBased();

    ValueRange range();

    ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor);

    TemporalAccessor resolve(Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle);

    String toString();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static TemporalAccessor $default$resolve(TemporalField _this, Map map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
            return null;
        }

        public static String $default$getDisplayName(TemporalField _this, Locale locale) {
            TemporalField$$ExternalSyntheticBackport0.m(locale, "locale");
            return _this.toString();
        }
    }
}
