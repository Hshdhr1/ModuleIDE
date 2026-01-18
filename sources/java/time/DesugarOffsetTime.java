package java.time;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarOffsetTime {
    private DesugarOffsetTime() {
    }

    public static long toEpochSecond(OffsetTime offsetTime, LocalDate localDate) {
        DesugarOffsetTime$$ExternalSyntheticBackport0.m(localDate, "date");
        return ((localDate.toEpochDay() * 86400) + offsetTime.toLocalTime().toSecondOfDay()) - offsetTime.getOffset().getTotalSeconds();
    }
}
