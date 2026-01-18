package java.time.format;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public enum TextStyle {
    FULL(2, 0),
    FULL_STANDALONE(32770, 0),
    SHORT(1, 1),
    SHORT_STANDALONE(32769, 1),
    NARROW(4, 1),
    NARROW_STANDALONE(32772, 1);

    private final int calendarStyle;
    private final int zoneNameStyleIndex;

    TextStyle(int i, int i2) {
        this.calendarStyle = i;
        this.zoneNameStyleIndex = i2;
    }

    public boolean isStandalone() {
        return (ordinal() & 1) == 1;
    }

    public TextStyle asStandalone() {
        return values()[ordinal() | 1];
    }

    public TextStyle asNormal() {
        return values()[ordinal() & (-2)];
    }

    int toCalendarStyle() {
        return this.calendarStyle;
    }

    int zoneNameStyleIndex() {
        return this.zoneNameStyleIndex;
    }
}
