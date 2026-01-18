package desugar.sun.nio.fs;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.util.HashSet;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DesugarUtil {
    private static final Charset jnuEncoding = StandardCharsets.UTF_8;

    private DesugarUtil() {
    }

    static Charset jnuEncoding() {
        return jnuEncoding;
    }

    static byte[] toBytes(String s) {
        return s.getBytes(jnuEncoding);
    }

    static String toString(byte[] bytes) {
        return new String(bytes, jnuEncoding);
    }

    static String[] split(String s, char c) {
        int i = 0;
        for (int i2 = 0; i2 < s.length(); i2++) {
            if (s.charAt(i2) == c) {
                i++;
            }
        }
        String[] strArr = new String[i + 1];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < s.length(); i5++) {
            if (s.charAt(i5) == c) {
                strArr[i3] = s.substring(i4, i5);
                i4 = i5 + 1;
                i3++;
            }
        }
        strArr[i3] = s.substring(i4, s.length());
        return strArr;
    }

    @SafeVarargs
    static Set newSet(Object... elements) {
        HashSet hashSet = new HashSet();
        for (Object obj : elements) {
            hashSet.add(obj);
        }
        return hashSet;
    }

    @SafeVarargs
    static Set newSet(Set other, Object... elements) {
        HashSet hashSet = new HashSet(other);
        for (Object obj : elements) {
            hashSet.add(obj);
        }
        return hashSet;
    }

    static boolean followLinks(LinkOption... options) {
        int length = options.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            LinkOption linkOption = options[i];
            if (linkOption != LinkOption.NOFOLLOW_LINKS) {
                linkOption.getClass();
                throw new AssertionError("Should not get here");
            }
            i++;
            z = false;
        }
        return z;
    }
}
