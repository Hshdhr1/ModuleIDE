package java.time.chrono;

import java.time.DateTimeException;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarIsoChronology {
    private static final long DAYS_0000_TO_1970 = 719528;

    public static long epochSecond(IsoChronology isoChronology, int i, int i2, int i3, int i4, int i5, int i6, ZoneOffset zoneOffset) {
        long j;
        long j2 = i;
        ChronoField.YEAR.checkValidValue(j2);
        ChronoField.MONTH_OF_YEAR.checkValidValue(i2);
        ChronoField.DAY_OF_MONTH.checkValidValue(i3);
        ChronoField.HOUR_OF_DAY.checkValidValue(i4);
        ChronoField.MINUTE_OF_HOUR.checkValidValue(i5);
        ChronoField.SECOND_OF_MINUTE.checkValidValue(i6);
        DesugarIsoChronology$$ExternalSyntheticBackport0.m(zoneOffset, "zoneOffset");
        if (i3 <= 28 || i3 <= numberOfDaysOfMonth(i, i2)) {
            long j3 = 365 * j2;
            if (i >= 0) {
                j = j3 + (((3 + j2) / 4) - ((99 + j2) / 100)) + ((399 + j2) / 400);
            } else {
                j = j3 - (((i / (-4)) - (i / (-100))) + (i / (-400)));
            }
            long j4 = j + (((i2 * 367) - 362) / 12) + (i3 - 1);
            if (i2 > 2) {
                j4 = !IsoChronology.INSTANCE.isLeapYear(j2) ? j4 - 2 : j4 - 1;
            }
            return DesugarIsoChronology$$ExternalSyntheticBackport2.m(DesugarIsoChronology$$ExternalSyntheticBackport1.m(j4 - 719528, 86400L), ((((i4 * 60) + i5) * 60) + i6) - zoneOffset.getTotalSeconds());
        }
        if (i3 == 29) {
            throw new DateTimeException("Invalid date 'February 29' as '" + i + "' is not a leap year");
        }
        throw new DateTimeException("Invalid date '" + Month.of(i2).name() + " " + i3 + "'");
    }

    private static int numberOfDaysOfMonth(int i, int i2) {
        return i2 != 2 ? (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31 : IsoChronology.INSTANCE.isLeapYear((long) i) ? 29 : 28;
    }
}
