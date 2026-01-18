package sun.nio.fs;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixUriUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long H_ALPHA;
    private static final long H_ALPHANUM;
    private static final long H_DIGIT = 0;
    private static final long H_LOWALPHA;
    private static final long H_MARK;
    private static final long H_PATH;
    private static final long H_PCHAR;
    private static final long H_UNRESERVED;
    private static final long H_UPALPHA;
    private static final long L_ALPHA = 0;
    private static final long L_ALPHANUM;
    private static final long L_DIGIT;
    private static final long L_LOWALPHA = 0;
    private static final long L_MARK;
    private static final long L_PATH;
    private static final long L_PCHAR;
    private static final long L_UNRESERVED;
    private static final long L_UPALPHA = 0;
    private static final char[] hexDigits;

    private static boolean match(char c, long j, long j2) {
        return c < '@' ? (j & (1 << c)) != 0 : c < 128 && ((1 << (c - 64)) & j2) != 0;
    }

    private UnixUriUtils() {
    }

    static Path fromUri(UnixFileSystem unixFileSystem, URI uri) {
        byte b;
        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("URI is not absolute");
        }
        if (uri.isOpaque()) {
            throw new IllegalArgumentException("URI is not hierarchical");
        }
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equalsIgnoreCase("file")) {
            throw new IllegalArgumentException("URI scheme is not \"file\"");
        }
        if (uri.getRawAuthority() != null) {
            throw new IllegalArgumentException("URI has an authority component");
        }
        if (uri.getRawFragment() != null) {
            throw new IllegalArgumentException("URI has a fragment component");
        }
        if (uri.getRawQuery() != null) {
            throw new IllegalArgumentException("URI has a query component");
        }
        if (!uri.toString().startsWith("file:///")) {
            return new File(uri).toPath();
        }
        String rawPath = uri.getRawPath();
        int length = rawPath.length();
        if (length == 0) {
            throw new IllegalArgumentException("URI path component is empty");
        }
        if (rawPath.endsWith("/") && length > 1) {
            length--;
        }
        byte[] bArr = new byte[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            char charAt = rawPath.charAt(i);
            if (charAt == '%') {
                int i4 = i + 2;
                i += 3;
                b = (byte) ((decode(rawPath.charAt(i3)) << 4) | decode(rawPath.charAt(i4)));
                if (b == 0) {
                    throw new IllegalArgumentException("Nul character not allowed");
                }
            } else {
                if (charAt == 0 || charAt >= 128) {
                    throw new IllegalArgumentException("Bad escape");
                }
                b = (byte) charAt;
                i = i3;
            }
            bArr[i2] = b;
            i2++;
        }
        if (i2 != length) {
            bArr = Arrays.copyOf(bArr, i2);
        }
        return new UnixPath(unixFileSystem, bArr);
    }

    static URI toUri(UnixPath unixPath) {
        byte[] asByteArray = unixPath.toAbsolutePath().asByteArray();
        StringBuilder sb = new StringBuilder("file:///");
        for (int i = 1; i < asByteArray.length; i++) {
            char c = (char) (asByteArray[i] & 255);
            if (match(c, L_PATH, H_PATH)) {
                sb.append(c);
            } else {
                sb.append('%');
                char[] cArr = hexDigits;
                sb.append(cArr[(c >> 4) & 15]);
                sb.append(cArr[c & 15]);
            }
        }
        if (sb.charAt(sb.length() - 1) != '/') {
            try {
                unixPath.checkRead();
                if ((UnixNativeDispatcher.stat(unixPath) & 61440) == 16384) {
                    sb.append('/');
                }
            } catch (SecurityException unused) {
            }
        }
        try {
            return new URI(sb.toString());
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    private static long lowMask(String str) {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < '@') {
                j |= 1 << charAt;
            }
        }
        return j;
    }

    private static long highMask(String str) {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt >= '@' && charAt < 128) {
                j |= 1 << (charAt - '@');
            }
        }
        return j;
    }

    private static long lowMask(char c, char c2) {
        long j = 0;
        for (int max = Math.max(Math.min(c, 63), 0); max <= Math.max(Math.min(c2, 63), 0); max++) {
            j |= 1 << max;
        }
        return j;
    }

    private static long highMask(char c, char c2) {
        long j = 0;
        for (int max = Math.max(Math.min(c, 127), 64) - 64; max <= Math.max(Math.min(c2, 127), 64) - 64; max++) {
            j |= 1 << max;
        }
        return j;
    }

    private static int decode(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'W';
        }
        if (c < 'A' || c > 'F') {
            throw new AssertionError();
        }
        return c - '7';
    }

    static {
        long lowMask = lowMask('0', '9');
        L_DIGIT = lowMask;
        long highMask = highMask('A', 'Z');
        H_UPALPHA = highMask;
        long highMask2 = highMask('a', 'z');
        H_LOWALPHA = highMask2;
        long j = highMask | highMask2;
        H_ALPHA = j;
        L_ALPHANUM = lowMask;
        H_ALPHANUM = j;
        long lowMask2 = lowMask("-_.!~*'()");
        L_MARK = lowMask2;
        long highMask3 = highMask("-_.!~*'()");
        H_MARK = highMask3;
        long j2 = lowMask | lowMask2;
        L_UNRESERVED = j2;
        long j3 = j | highMask3;
        H_UNRESERVED = j3;
        long lowMask3 = j2 | lowMask(":@&=+$,");
        L_PCHAR = lowMask3;
        long highMask4 = j3 | highMask(":@&=+$,");
        H_PCHAR = highMask4;
        L_PATH = lowMask3 | lowMask(";/");
        H_PATH = highMask(";/") | highMask4;
        hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }
}
