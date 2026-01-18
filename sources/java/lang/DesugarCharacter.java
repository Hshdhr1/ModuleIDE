package java.lang;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarCharacter {
    public static final char MAX_HIGH_SURROGATE = 56319;
    public static final char MAX_LOW_SURROGATE = 57343;
    public static final char MAX_SURROGATE = 57343;
    public static final char MIN_HIGH_SURROGATE = 55296;
    public static final char MIN_LOW_SURROGATE = 56320;
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 65536;
    public static final char MIN_SURROGATE = 55296;

    public static char highSurrogate(int i) {
        return (char) ((i >>> 10) + 55232);
    }

    public static boolean isBmpCodePoint(int i) {
        return (i >>> 16) == 0;
    }

    public static boolean isSurrogate(char c) {
        return c >= 55296 && c < 57344;
    }

    public static char lowSurrogate(int i) {
        return (char) ((i & 1023) + 56320);
    }

    private DesugarCharacter() {
    }
}
