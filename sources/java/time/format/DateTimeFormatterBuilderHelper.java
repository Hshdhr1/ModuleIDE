package java.time.format;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class DateTimeFormatterBuilderHelper {
    private DateTimeFormatterBuilderHelper() {
    }

    static String transformAndroidJavaTextDateTimePattern(String str) {
        if (str == null) {
            return null;
        }
        return ((str.indexOf(66) != -1) || (str.indexOf(98) != -1)) ? rewriteIcuDateTimePattern(str) : str;
    }

    private static String rewriteIcuDateTimePattern(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int i = 0;
        char c = ' ';
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ' ') {
                if (charAt != 'B' && charAt != 'b') {
                    sb.append(charAt);
                }
            } else if (i == 0 || (c != 'B' && c != 'b')) {
                sb.append(charAt);
            }
            i++;
            c = charAt;
        }
        int length = sb.length() - 1;
        if (length >= 0 && sb.charAt(length) == ' ') {
            sb.deleteCharAt(length);
        }
        return sb.toString();
    }
}
