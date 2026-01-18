package java.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class URLDecoder {
    static String dfltEncName = URLEncoder.dfltEncName;

    private static boolean isValidHexChar(char c) {
        if ('0' <= c && c <= '9') {
            return true;
        }
        if ('a' > c || c > 'f') {
            return 'A' <= c && c <= 'F';
        }
        return true;
    }

    @Deprecated
    public static String decode(String str) {
        try {
            return decode(str, dfltEncName);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String decode(String str, String str2) throws UnsupportedEncodingException {
        if (str2.isEmpty()) {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }
        try {
            return decode(str, Charset.forName(str2));
        } catch (IllegalCharsetNameException | UnsupportedCharsetException unused) {
            throw new UnsupportedEncodingException(str2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ae, code lost:
    
        if (r4 >= r0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b0, code lost:
    
        if (r6 == '%') goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ba, code lost:
    
        throw new java.lang.IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00bb, code lost:
    
        r1.append(new java.lang.String(r3, 0, r5, r13));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String decode(java.lang.String r12, java.nio.charset.Charset r13) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.net.URLDecoder.decode(java.lang.String, java.nio.charset.Charset):java.lang.String");
    }
}
