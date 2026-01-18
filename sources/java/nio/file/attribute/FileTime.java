package java.nio.file.attribute;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class FileTime implements Comparable {
    private static final long DAYS_PER_10000_YEARS = 3652425;
    private static final long HOURS_PER_DAY = 24;
    private static final long MAX_SECOND = 31556889864403199L;
    private static final long MICROS_PER_SECOND = 1000000;
    private static final long MILLIS_PER_SECOND = 1000;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long MIN_SECOND = -31557014167219200L;
    private static final int NANOS_PER_MICRO = 1000;
    private static final int NANOS_PER_MILLI = 1000000;
    private static final long NANOS_PER_SECOND = 1000000000;
    private static final long SECONDS_0000_TO_1970 = 62167219200L;
    private static final long SECONDS_PER_10000_YEARS = 315569520000L;
    private static final long SECONDS_PER_DAY = 86400;
    private static final long SECONDS_PER_HOUR = 3600;
    private static final long SECONDS_PER_MINUTE = 60;
    private Instant instant;
    private final TimeUnit unit;
    private final long value;
    private String valueAsString;

    private static long scale(long j, long j2, long j3) {
        if (j > j3) {
            return Long.MAX_VALUE;
        }
        if (j < (-j3)) {
            return Long.MIN_VALUE;
        }
        return j * j2;
    }

    private FileTime(long j, TimeUnit timeUnit, Instant instant) {
        this.value = j;
        this.unit = timeUnit;
        this.instant = instant;
    }

    public static FileTime from(long j, TimeUnit timeUnit) {
        FileTime$$ExternalSyntheticBackport0.m(timeUnit, "unit");
        return new FileTime(j, timeUnit, null);
    }

    public static FileTime fromMillis(long j) {
        return new FileTime(j, TimeUnit.MILLISECONDS, null);
    }

    public static FileTime from(Instant instant) {
        FileTime$$ExternalSyntheticBackport0.m(instant, "instant");
        return new FileTime(0L, null, instant);
    }

    public long to(TimeUnit timeUnit) {
        FileTime$$ExternalSyntheticBackport0.m(timeUnit, "unit");
        TimeUnit timeUnit2 = this.unit;
        if (timeUnit2 != null) {
            return timeUnit.convert(this.value, timeUnit2);
        }
        long convert = timeUnit.convert(this.instant.getEpochSecond(), TimeUnit.SECONDS);
        if (convert == Long.MIN_VALUE || convert == Long.MAX_VALUE) {
            return convert;
        }
        long convert2 = timeUnit.convert(this.instant.getNano(), TimeUnit.NANOSECONDS);
        long j = convert + convert2;
        return ((convert2 ^ j) & (convert ^ j)) < 0 ? convert < 0 ? Long.MIN_VALUE : Long.MAX_VALUE : j;
    }

    public long toMillis() {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toMillis(this.value);
        }
        long epochSecond = this.instant.getEpochSecond();
        int nano = this.instant.getNano();
        long j = epochSecond * 1000;
        if (((Math.abs(epochSecond) | 1000) >>> 31) == 0 || j / 1000 == epochSecond) {
            return j + (nano / 1000000);
        }
        return epochSecond < 0 ? Long.MIN_VALUE : Long.MAX_VALUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.time.Instant toInstant() {
        /*
            r10 = this;
            java.time.Instant r0 = r10.instant
            if (r0 != 0) goto La6
            int[] r0 = java.nio.file.attribute.FileTime.1.$SwitchMap$java$util$concurrent$TimeUnit
            java.util.concurrent.TimeUnit r1 = r10.unit
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 0
            switch(r0) {
                case 1: goto L73;
                case 2: goto L65;
                case 3: goto L57;
                case 4: goto L54;
                case 5: goto L3f;
                case 6: goto L2c;
                case 7: goto L1a;
                default: goto L12;
            }
        L12:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            java.lang.String r1 = "Unit not handled"
            r0.<init>(r1)
            throw r0
        L1a:
            long r0 = r10.value
            r2 = 1000000000(0x3b9aca00, double:4.94065646E-315)
            long r0 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport1.m(r0, r2)
            long r4 = r10.value
            long r2 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport2.m(r4, r2)
            int r3 = (int) r2
            r2 = r3
            goto L83
        L2c:
            long r0 = r10.value
            r2 = 1000000(0xf4240, double:4.940656E-318)
            long r0 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport1.m(r0, r2)
            long r4 = r10.value
            long r2 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport2.m(r4, r2)
            int r3 = (int) r2
            int r2 = r3 * 1000
            goto L83
        L3f:
            long r0 = r10.value
            r2 = 1000(0x3e8, double:4.94E-321)
            long r0 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport1.m(r0, r2)
            long r4 = r10.value
            long r2 = java.nio.file.attribute.FileTime$$ExternalSyntheticBackport2.m(r4, r2)
            int r3 = (int) r2
            r2 = 1000000(0xf4240, float:1.401298E-39)
            int r2 = r2 * r3
            goto L83
        L54:
            long r2 = r10.value
            goto L81
        L57:
            long r4 = r10.value
            r6 = 60
            r8 = 153722867280912930(0x222222222222222, double:2.166167076120538E-298)
            long r2 = scale(r4, r6, r8)
            goto L81
        L65:
            long r4 = r10.value
            r6 = 3600(0xe10, double:1.7786E-320)
            r8 = 2562047788015215(0x91a2b3c4d5e6f, double:1.2658197950618743E-308)
            long r2 = scale(r4, r6, r8)
            goto L81
        L73:
            long r4 = r10.value
            r6 = 86400(0x15180, double:4.26873E-319)
            r8 = 106751991167300(0x611722833944, double:5.2742491460911E-310)
            long r2 = scale(r4, r6, r8)
        L81:
            r0 = r2
            r2 = 0
        L83:
            r3 = -31557014167219200(0xff8fe31014641400, double:-2.7989734602046733E306)
            int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r5 > 0) goto L91
            java.time.Instant r0 = java.time.Instant.MIN
            r10.instant = r0
            goto La6
        L91:
            r3 = 31556889864403199(0x701cd2fa9578ff, double:1.434068493154717E-306)
            int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r5 < 0) goto L9f
            java.time.Instant r0 = java.time.Instant.MAX
            r10.instant = r0
            goto La6
        L9f:
            long r2 = (long) r2
            java.time.Instant r0 = java.time.Instant.ofEpochSecond(r0, r2)
            r10.instant = r0
        La6:
            java.time.Instant r0 = r10.instant
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java.nio.file.attribute.FileTime.toInstant():java.time.Instant");
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr;
            try {
                iArr[TimeUnit.DAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.HOURS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MINUTES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.NANOSECONDS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof FileTime) && compareTo((FileTime) obj) == 0;
    }

    public int hashCode() {
        return toInstant().hashCode();
    }

    private long toDays() {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toDays(this.value);
        }
        return TimeUnit.SECONDS.toDays(toInstant().getEpochSecond());
    }

    private long toExcessNanos(long j) {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toNanos(this.value - timeUnit.convert(j, TimeUnit.DAYS));
        }
        return TimeUnit.SECONDS.toNanos(toInstant().getEpochSecond() - TimeUnit.DAYS.toSeconds(j));
    }

    public int compareTo(FileTime fileTime) {
        long excessNanos;
        long excessNanos2;
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null && timeUnit == fileTime.unit) {
            excessNanos = this.value;
            excessNanos2 = fileTime.value;
        } else {
            long epochSecond = toInstant().getEpochSecond();
            int i = (epochSecond > fileTime.toInstant().getEpochSecond() ? 1 : (epochSecond == fileTime.toInstant().getEpochSecond() ? 0 : -1));
            if (i != 0) {
                return i;
            }
            int i2 = (toInstant().getNano() > fileTime.toInstant().getNano() ? 1 : (toInstant().getNano() == fileTime.toInstant().getNano() ? 0 : -1));
            if (i2 != 0) {
                return i2;
            }
            if (epochSecond != 31556889864403199L && epochSecond != -31557014167219200L) {
                return 0;
            }
            long days = toDays();
            long days2 = fileTime.toDays();
            if (days != days2) {
                return (days > days2 ? 1 : (days == days2 ? 0 : -1));
            }
            excessNanos = toExcessNanos(days);
            excessNanos2 = fileTime.toExcessNanos(days2);
        }
        return (excessNanos > excessNanos2 ? 1 : (excessNanos == excessNanos2 ? 0 : -1));
    }

    private StringBuilder append(StringBuilder sb, int i, int i2) {
        while (i > 0) {
            sb.append((char) ((i2 / i) + 48));
            i2 %= i;
            i /= 10;
        }
        return sb;
    }

    public String toString() {
        long epochSecond;
        int nano;
        long j;
        LocalDateTime ofEpochSecond;
        int year;
        if (this.valueAsString == null) {
            if (this.instant == null && this.unit.compareTo(TimeUnit.SECONDS) >= 0) {
                epochSecond = this.unit.toSeconds(this.value);
                nano = 0;
            } else {
                epochSecond = toInstant().getEpochSecond();
                nano = toInstant().getNano();
            }
            if (epochSecond >= -62167219200L) {
                long j2 = epochSecond - 253402300800L;
                j = FileTime$$ExternalSyntheticBackport1.m(j2, 315569520000L) + 1;
                ofEpochSecond = LocalDateTime.ofEpochSecond(FileTime$$ExternalSyntheticBackport2.m(j2, 315569520000L) - 62167219200L, nano, ZoneOffset.UTC);
                year = ofEpochSecond.getYear();
            } else {
                long j3 = epochSecond + 62167219200L;
                j = j3 / 315569520000L;
                ofEpochSecond = LocalDateTime.ofEpochSecond((j3 % 315569520000L) - 62167219200L, nano, ZoneOffset.UTC);
                year = ofEpochSecond.getYear();
            }
            int i = year + (((int) j) * 10000);
            if (i <= 0) {
                i--;
            }
            int nano2 = ofEpochSecond.getNano();
            StringBuilder sb = new StringBuilder(64);
            sb.append(i < 0 ? "-" : "");
            int abs = Math.abs(i);
            if (abs < 10000) {
                append(sb, 1000, Math.abs(abs));
            } else {
                sb.append(String.valueOf(abs));
            }
            sb.append('-');
            append(sb, 10, ofEpochSecond.getMonthValue());
            sb.append('-');
            append(sb, 10, ofEpochSecond.getDayOfMonth());
            sb.append('T');
            append(sb, 10, ofEpochSecond.getHour());
            sb.append(':');
            append(sb, 10, ofEpochSecond.getMinute());
            sb.append(':');
            append(sb, 10, ofEpochSecond.getSecond());
            if (nano2 != 0) {
                sb.append('.');
                int i2 = 100000000;
                while (nano2 % 10 == 0) {
                    nano2 /= 10;
                    i2 /= 10;
                }
                append(sb, i2, nano2);
            }
            sb.append('Z');
            this.valueAsString = sb.toString();
        }
        return this.valueAsString;
    }
}
