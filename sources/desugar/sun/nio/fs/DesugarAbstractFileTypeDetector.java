package desugar.sun.nio.fs;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class DesugarAbstractFileTypeDetector extends FileTypeDetector {
    private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";

    protected abstract String implProbeContentType(Path file) throws IOException;

    protected DesugarAbstractFileTypeDetector() {
    }

    protected final String getExtension(String name) {
        int indexOf;
        if (name != null && !name.isEmpty() && (indexOf = name.indexOf(46)) >= 0 && indexOf < name.length() - 1) {
            return name.substring(indexOf + 1);
        }
        return "";
    }

    public final String probeContentType(Path file) throws IOException {
        Path fileName;
        if (file == null) {
            throw new NullPointerException("'file' is null");
        }
        String implProbeContentType = implProbeContentType(file);
        if (implProbeContentType == null && (fileName = file.getFileName()) != null) {
            implProbeContentType = URLConnection.getFileNameMap().getContentTypeFor(fileName.toString());
        }
        if (implProbeContentType == null) {
            return null;
        }
        return parse(implProbeContentType);
    }

    private static String parse(String s) {
        String substring;
        int indexOf = s.indexOf(47);
        int indexOf2 = s.indexOf(59);
        if (indexOf < 0) {
            return null;
        }
        String lowerCase = s.substring(0, indexOf).trim().toLowerCase(Locale.ENGLISH);
        if (!isValidToken(lowerCase)) {
            return null;
        }
        if (indexOf2 < 0) {
            substring = s.substring(indexOf + 1);
        } else {
            substring = s.substring(indexOf + 1, indexOf2);
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

    private static boolean isValidToken(String s) {
        int length = s.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!isTokenChar(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
