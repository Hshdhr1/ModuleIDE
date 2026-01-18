package java.time;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarLocalTime {
    static final long NANOS_PER_SECOND = 1000000000;
    static final int SECONDS_PER_DAY = 86400;

    private DesugarLocalTime() {
    }

    public static long toEpochSecond(LocalTime localTime, LocalDate localDate, ZoneOffset zoneOffset) {
        DesugarLocalTime$$ExternalSyntheticBackport0.m(localDate, "date");
        DesugarLocalTime$$ExternalSyntheticBackport0.m(zoneOffset, "offset");
        return ((localDate.toEpochDay() * 86400) + localTime.toSecondOfDay()) - zoneOffset.getTotalSeconds();
    }

    public static LocalTime ofInstant(Instant instant, ZoneId zoneId) {
        DesugarLocalTime$$ExternalSyntheticBackport0.m(instant, "instant");
        DesugarLocalTime$$ExternalSyntheticBackport0.m(zoneId, "zone");
        return LocalTime.ofNanoOfDay((DesugarLocalTime$$ExternalSyntheticBackport1.m(instant.getEpochSecond() + zoneId.getRules().getOffset(instant).getTotalSeconds(), 86400) * 1000000000) + instant.getNano());
    }
}
