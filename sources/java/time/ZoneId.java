package java.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class ZoneId implements Serializable {
    public static final Map SHORT_IDS = ZoneId$$ExternalSyntheticBackport2.m(new Map.Entry[]{ZoneId$$ExternalSyntheticBackport1.m("ACT", "Australia/Darwin"), ZoneId$$ExternalSyntheticBackport1.m("AET", "Australia/Sydney"), ZoneId$$ExternalSyntheticBackport1.m("AGT", "America/Argentina/Buenos_Aires"), ZoneId$$ExternalSyntheticBackport1.m("ART", "Africa/Cairo"), ZoneId$$ExternalSyntheticBackport1.m("AST", "America/Anchorage"), ZoneId$$ExternalSyntheticBackport1.m("BET", "America/Sao_Paulo"), ZoneId$$ExternalSyntheticBackport1.m("BST", "Asia/Dhaka"), ZoneId$$ExternalSyntheticBackport1.m("CAT", "Africa/Harare"), ZoneId$$ExternalSyntheticBackport1.m("CNT", "America/St_Johns"), ZoneId$$ExternalSyntheticBackport1.m("CST", "America/Chicago"), ZoneId$$ExternalSyntheticBackport1.m("CTT", "Asia/Shanghai"), ZoneId$$ExternalSyntheticBackport1.m("EAT", "Africa/Addis_Ababa"), ZoneId$$ExternalSyntheticBackport1.m("ECT", "Europe/Paris"), ZoneId$$ExternalSyntheticBackport1.m("IET", "America/Indiana/Indianapolis"), ZoneId$$ExternalSyntheticBackport1.m("IST", "Asia/Kolkata"), ZoneId$$ExternalSyntheticBackport1.m("JST", "Asia/Tokyo"), ZoneId$$ExternalSyntheticBackport1.m("MIT", "Pacific/Apia"), ZoneId$$ExternalSyntheticBackport1.m("NET", "Asia/Yerevan"), ZoneId$$ExternalSyntheticBackport1.m("NST", "Pacific/Auckland"), ZoneId$$ExternalSyntheticBackport1.m("PLT", "Asia/Karachi"), ZoneId$$ExternalSyntheticBackport1.m("PNT", "America/Phoenix"), ZoneId$$ExternalSyntheticBackport1.m("PRT", "America/Puerto_Rico"), ZoneId$$ExternalSyntheticBackport1.m("PST", "America/Los_Angeles"), ZoneId$$ExternalSyntheticBackport1.m("SST", "Pacific/Guadalcanal"), ZoneId$$ExternalSyntheticBackport1.m("VST", "Asia/Ho_Chi_Minh"), ZoneId$$ExternalSyntheticBackport1.m("EST", "-05:00"), ZoneId$$ExternalSyntheticBackport1.m("MST", "-07:00"), ZoneId$$ExternalSyntheticBackport1.m("HST", "-10:00")});
    private static final long serialVersionUID = 8352817235686L;

    public abstract String getId();

    public abstract ZoneRules getRules();

    abstract void write(DataOutput dataOutput) throws IOException;

    public static ZoneId systemDefault() {
        return TimeZone.getDefault().toZoneId();
    }

    public static Set getAvailableZoneIds() {
        return new HashSet(ZoneRulesProvider.getAvailableZoneIds());
    }

    public static ZoneId of(String str, Map map) {
        ZoneId$$ExternalSyntheticBackport0.m(str, "zoneId");
        ZoneId$$ExternalSyntheticBackport0.m(map, "aliasMap");
        return of((String) ZoneId$$ExternalSyntheticBackport3.m((String) map.get(str), str));
    }

    public static ZoneId of(String str) {
        return of(str, true);
    }

    public static ZoneId ofOffset(String str, ZoneOffset zoneOffset) {
        ZoneId$$ExternalSyntheticBackport0.m(str, "prefix");
        ZoneId$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
        if (str.isEmpty()) {
            return zoneOffset;
        }
        if (!str.equals("GMT") && !str.equals("UTC") && !str.equals("UT")) {
            throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: " + str);
        }
        if (zoneOffset.getTotalSeconds() != 0) {
            str = str.concat(zoneOffset.getId());
        }
        return new ZoneRegion(str, zoneOffset.getRules());
    }

    static ZoneId of(String str, boolean z) {
        ZoneId$$ExternalSyntheticBackport0.m(str, "zoneId");
        if (str.length() <= 1 || str.startsWith("+") || str.startsWith("-")) {
            return ZoneOffset.of(str);
        }
        if (str.startsWith("UTC") || str.startsWith("GMT")) {
            return ofWithPrefix(str, 3, z);
        }
        if (str.startsWith("UT")) {
            return ofWithPrefix(str, 2, z);
        }
        return ZoneRegion.ofId(str, z);
    }

    private static ZoneId ofWithPrefix(String str, int i, boolean z) {
        String substring = str.substring(0, i);
        if (str.length() == i) {
            return ofOffset(substring, ZoneOffset.UTC);
        }
        if (str.charAt(i) != '+' && str.charAt(i) != '-') {
            return ZoneRegion.ofId(str, z);
        }
        try {
            ZoneOffset of = ZoneOffset.of(str.substring(i));
            if (of == ZoneOffset.UTC) {
                return ofOffset(substring, of);
            }
            return ofOffset(substring, of);
        } catch (DateTimeException e) {
            throw new DateTimeException("Invalid ID for offset-based ZoneId: " + str, e);
        }
    }

    public static ZoneId from(TemporalAccessor temporalAccessor) {
        ZoneId zoneId = (ZoneId) temporalAccessor.query(TemporalQueries.zone());
        if (zoneId != null) {
            return zoneId;
        }
        throw new DateTimeException("Unable to obtain ZoneId from TemporalAccessor: " + temporalAccessor + " of type " + temporalAccessor.getClass().getName());
    }

    ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != ZoneRegion.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendZoneText(textStyle).toFormatter(locale).format(toTemporal());
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
            if (temporalQuery == TemporalQueries.zoneId()) {
                return ZoneId.this;
            }
            return TemporalAccessor.-CC.$default$query(this, temporalQuery);
        }
    }

    private TemporalAccessor toTemporal() {
        return new 1();
    }

    public ZoneId normalized() {
        try {
            ZoneRules rules = getRules();
            if (rules.isFixedOffset()) {
                return rules.getOffset(Instant.EPOCH);
            }
        } catch (ZoneRulesException unused) {
        }
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneId) {
            return getId().equals(((ZoneId) obj).getId());
        }
        return false;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public String toString() {
        return getId();
    }

    private Object writeReplace() {
        return new Ser((byte) 7, this);
    }
}
