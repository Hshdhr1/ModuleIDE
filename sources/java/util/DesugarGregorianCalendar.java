package java.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarGregorianCalendar {
    private DesugarGregorianCalendar() {
    }

    public static ZonedDateTime toZonedDateTime(GregorianCalendar gregorianCalendar) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(gregorianCalendar.getTimeInMillis()), gregorianCalendar.getTimeZone().toZoneId());
    }

    public static GregorianCalendar from(ZonedDateTime zonedDateTime) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(zonedDateTime.getZone()));
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        gregorianCalendar.setFirstDayOfWeek(2);
        gregorianCalendar.setMinimalDaysInFirstWeek(4);
        try {
            gregorianCalendar.setTimeInMillis(DesugarGregorianCalendar$$ExternalSyntheticBackport2.m(DesugarGregorianCalendar$$ExternalSyntheticBackport1.m(zonedDateTime.toEpochSecond(), 1000), zonedDateTime.get(ChronoField.MILLI_OF_SECOND)));
            return gregorianCalendar;
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
