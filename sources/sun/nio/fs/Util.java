package sun.nio.fs;

import java.nio.charset.Charset;
import java.nio.file.LinkOption;
import java.util.HashSet;
import java.util.Set;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class Util {
    private static final Charset jnuEncoding = Charset.forName(GetPropertyAction.privilegedGetProperty("sun.jnu.encoding"));

    private Util() {
    }

    static Charset jnuEncoding() {
        return jnuEncoding;
    }

    static byte[] toBytes(String str) {
        return str.getBytes(jnuEncoding);
    }

    static String toString(byte[] bArr) {
        return new String(bArr, jnuEncoding);
    }

    static String[] split(String str, char c) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == c) {
                i++;
            }
        }
        String[] strArr = new String[i + 1];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < str.length(); i5++) {
            if (str.charAt(i5) == c) {
                strArr[i3] = str.substring(i4, i5);
                i4 = i5 + 1;
                i3++;
            }
        }
        strArr[i3] = str.substring(i4, str.length());
        return strArr;
    }

    @SafeVarargs
    static Set newSet(Object... objArr) {
        HashSet hashSet = new HashSet();
        for (Object obj : objArr) {
            hashSet.add(obj);
        }
        return hashSet;
    }

    @SafeVarargs
    static Set newSet(Set set, Object... objArr) {
        HashSet hashSet = new HashSet(set);
        for (Object obj : objArr) {
            hashSet.add(obj);
        }
        return hashSet;
    }

    static boolean followLinks(LinkOption... linkOptionArr) {
        int length = linkOptionArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            LinkOption linkOption = linkOptionArr[i];
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
