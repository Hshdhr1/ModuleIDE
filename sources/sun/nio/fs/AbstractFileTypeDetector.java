package sun.nio.fs;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class AbstractFileTypeDetector extends FileTypeDetector {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";

    protected abstract String implProbeContentType(Path path) throws IOException;

    protected AbstractFileTypeDetector() {
    }

    protected final String getExtension(String str) {
        int indexOf;
        if (str != null && !str.isEmpty() && (indexOf = str.indexOf(46)) >= 0 && indexOf < str.length() - 1) {
            return str.substring(indexOf + 1);
        }
        return "";
    }

    public final String probeContentType(Path path) throws IOException {
        Path fileName;
        if (path == null) {
            throw new NullPointerException("'file' is null");
        }
        String implProbeContentType = implProbeContentType(path);
        if (implProbeContentType == null && (fileName = path.getFileName()) != null) {
            implProbeContentType = URLConnection.getFileNameMap().getContentTypeFor(fileName.toString());
        }
        if (implProbeContentType == null) {
            return null;
        }
        return parse(implProbeContentType);
    }

    private static String parse(String str) {
        String substring;
        int indexOf = str.indexOf(47);
        int indexOf2 = str.indexOf(59);
        if (indexOf < 0) {
            return null;
        }
        String lowerCase = str.substring(0, indexOf).trim().toLowerCase(Locale.ENGLISH);
        if (!isValidToken(lowerCase)) {
            return null;
        }
        if (indexOf2 < 0) {
            substring = str.substring(indexOf + 1);
        } else {
            substring = str.substring(indexOf + 1, indexOf2);
        }
        String lowerCase2 = substring.trim().toLowerCase(Locale.ENGLISH);
        if (!isValidToken(lowerCase2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(lowerCase.length() + lowerCase2.length() + 1);
        sb.append(lowerCase);
        sb.append('/');
        sb.append(lowerCase2);
        return sb.toString();
    }

    private static boolean isTokenChar(char c) {
        return c > ' ' && c < 127 && "()<>@,;:/[]?=\\\"".indexOf(c) < 0;
    }

    private static boolean isValidToken(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!isTokenChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
