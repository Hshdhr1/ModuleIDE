package com.github.difflib.text;

import java.util.List;
import java.util.stream.Collectors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
final class StringUtils {
    public static String htmlEntites(String str) {
        return str.replace("<", "&lt;").replace(">", "&gt;");
    }

    public static String normalize(String str) {
        return htmlEntites(str).replace("\t", "    ");
    }

    public static List wrapText(List list, int i) {
        return (List) list.stream().map(new StringUtils$$ExternalSyntheticLambda0(i)).collect(Collectors.toList());
    }

    static /* synthetic */ String lambda$wrapText$0(int i, String str) {
        return wrapText(str, i);
    }

    public static String wrapText(String str, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("columnWidth may not be less 0");
        }
        if (i == 0) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(str);
        int i2 = 0;
        int i3 = i;
        while (length > i3) {
            int i4 = (5 * i2) + i3;
            if (Character.isHighSurrogate(sb.charAt(i4 - 1)) && Character.isLowSurrogate(sb.charAt(i4))) {
                int i5 = i4 + 1;
                i4 = i5 == sb.length() ? i4 - 1 : i5;
            }
            sb.insert(i4, "<br/>");
            i3 += i;
            i2++;
        }
        return sb.toString();
    }

    private StringUtils() {
    }
}
