package java.util;

import java.time.ZoneId;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarTimeZone {
    private DesugarTimeZone() {
    }

    public static TimeZone getTimeZone(String str) {
        return TimeZone.getTimeZone(str);
    }

    public static TimeZone getTimeZone(ZoneId zoneId) {
        String id = zoneId.getId();
        char charAt = id.charAt(0);
        if (charAt == '+' || charAt == '-') {
            id = "GMT" + id;
        } else if (charAt == 'Z' && id.length() == 1) {
            id = "UTC";
        }
        return TimeZone.getTimeZone(id);
    }

    public static ZoneId toZoneId(TimeZone timeZone) {
        return ZoneId.of(timeZone.getID(), ZoneId.SHORT_IDS);
    }
}
