package java.time;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarLocalDate {
    public static final LocalDate EPOCH = LocalDate.of(1970, 1, 1);
    private static final int SECONDS_PER_DAY = 86400;

    private DesugarLocalDate() {
    }

    public static Stream datesUntil(LocalDate localDate, LocalDate localDate2) {
        long epochDay = localDate2.toEpochDay();
        long epochDay2 = localDate.toEpochDay();
        if (epochDay < epochDay2) {
            throw new IllegalArgumentException(localDate2 + " < " + localDate);
        }
        return LongStream.-CC.range(epochDay2, epochDay).mapToObj(new DesugarLocalDate$$ExternalSyntheticLambda3());
    }

    public static Stream datesUntil(LocalDate localDate, LocalDate localDate2, Period period) {
        long prolepticMonth;
        long prolepticMonth2;
        if (period.isZero()) {
            throw new IllegalArgumentException("step is zero");
        }
        long epochDay = localDate2.toEpochDay();
        long epochDay2 = localDate.toEpochDay();
        long j = epochDay - epochDay2;
        long totalMonths = period.toTotalMonths();
        long days = period.getDays();
        if ((totalMonths < 0 && days > 0) || (totalMonths > 0 && days < 0)) {
            throw new IllegalArgumentException("period months and days are of opposite sign");
        }
        if (j == 0) {
            return Stream.-CC.empty();
        }
        int i = (totalMonths > 0 || days > 0) ? 1 : -1;
        if ((i < 0) ^ (j < 0)) {
            throw new IllegalArgumentException(localDate2 + (i < 0 ? " > " : " < ") + localDate);
        }
        if (totalMonths == 0) {
            return LongStream.-CC.rangeClosed(0L, (j - i) / days).mapToObj(new DesugarLocalDate$$ExternalSyntheticLambda4(epochDay2, days));
        }
        long j2 = (j * 1600) / ((48699 * totalMonths) + (1600 * days));
        long j3 = j2 + 1;
        long j4 = totalMonths * j3;
        long j5 = days * j3;
        if (totalMonths > 0) {
            prolepticMonth = getProlepticMonth(LocalDate.MAX);
            prolepticMonth2 = getProlepticMonth(localDate);
        } else {
            prolepticMonth = getProlepticMonth(localDate);
            prolepticMonth2 = getProlepticMonth(LocalDate.MIN);
        }
        long j6 = prolepticMonth - prolepticMonth2;
        long j7 = i;
        if (j4 * j7 > j6 || (localDate.plusMonths(j4).toEpochDay() + j5) * j7 >= epochDay * j7) {
            long j8 = j4 - totalMonths;
            long j9 = j5 - days;
            if (j8 * j7 > j6 || (localDate.plusMonths(j8).toEpochDay() + j9) * j7 >= epochDay * j7) {
                j2--;
            }
        } else {
            j2 = j3;
        }
        return LongStream.-CC.rangeClosed(0L, j2).mapToObj(new DesugarLocalDate$$ExternalSyntheticLambda5(localDate, totalMonths, days));
    }

    static /* synthetic */ LocalDate lambda$datesUntil$0(long j, long j2, long j3) {
        return LocalDate.ofEpochDay(j + (j3 * j2));
    }

    static /* synthetic */ LocalDate lambda$datesUntil$1(LocalDate localDate, long j, long j2, long j3) {
        return localDate.plusMonths(j * j3).plusDays(j2 * j3);
    }

    public static long toEpochSecond(LocalDate localDate, LocalTime localTime, ZoneOffset zoneOffset) {
        DesugarLocalDate$$ExternalSyntheticBackport0.m(localTime, "time");
        DesugarLocalDate$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
        return ((localDate.toEpochDay() * 86400) + localTime.toSecondOfDay()) - zoneOffset.getTotalSeconds();
    }

    public static LocalDate ofInstant(Instant instant, ZoneId zoneId) {
        DesugarLocalDate$$ExternalSyntheticBackport0.m(instant, "instant");
        DesugarLocalDate$$ExternalSyntheticBackport0.m(zoneId, "zone");
        return LocalDate.ofEpochDay(DesugarLocalDate$$ExternalSyntheticBackport1.m(instant.getEpochSecond() + zoneId.getRules().getOffset(instant).getTotalSeconds(), 86400));
    }

    private static long getProlepticMonth(LocalDate localDate) {
        return ((localDate.getYear() * 12) + localDate.getMonthValue()) - 1;
    }
}
