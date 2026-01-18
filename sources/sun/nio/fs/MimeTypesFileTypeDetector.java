package sun.nio.fs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class MimeTypesFileTypeDetector extends AbstractFileTypeDetector {
    private volatile boolean loaded;
    private Map mimeTypeMap;
    private final Path mimeTypesFile;

    static /* bridge */ /* synthetic */ Path -$$Nest$fgetmimeTypesFile(MimeTypesFileTypeDetector mimeTypesFileTypeDetector) {
        return mimeTypesFileTypeDetector.mimeTypesFile;
    }

    public MimeTypesFileTypeDetector(Path path) {
        this.mimeTypesFile = path;
    }

    protected String implProbeContentType(Path path) {
        Path fileName = path.getFileName();
        String str = null;
        if (fileName == null) {
            return null;
        }
        String extension = getExtension(fileName.toString());
        if (extension.isEmpty()) {
            return null;
        }
        loadMimeTypes();
        Map map = this.mimeTypeMap;
        if (map != null && !map.isEmpty()) {
            do {
                str = (String) this.mimeTypeMap.get(extension);
                if (str == null) {
                    extension = getExtension(extension);
                }
                if (str != null) {
                    break;
                }
            } while (!extension.isEmpty());
        }
        return str;
    }

    private void loadMimeTypes() {
        if (this.loaded) {
            return;
        }
        synchronized (this) {
            if (!this.loaded) {
                List list = (List) AccessController.doPrivileged(new 1());
                this.mimeTypeMap = new HashMap(list.size());
                String str = "";
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str2 = str + ((String) it.next());
                    if (str2.endsWith("\\")) {
                        str = str2.substring(0, str2.length() - 1);
                    } else {
                        parseMimeEntry(str2);
                        str = "";
                    }
                }
                if (!str.isEmpty()) {
                    parseMimeEntry(str);
                }
                this.loaded = true;
            }
        }
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public List run() {
            try {
                return Files.readAllLines(MimeTypesFileTypeDetector.-$$Nest$fgetmimeTypesFile(MimeTypesFileTypeDetector.this), Charset.defaultCharset());
            } catch (IOException unused) {
                return Collections.EMPTY_LIST;
            }
        }
    }

    private void parseMimeEntry(String str) {
        String trim = str.trim();
        if (trim.isEmpty()) {
            return;
        }
        if (trim.charAt(0) == '#') {
            return;
        }
        String replaceAll = trim.replaceAll("\\s*#.*", "");
        if (replaceAll.indexOf(61) > 0) {
            Matcher matcher = Pattern.compile("\\btype=(\"\\p{Graph}+?/\\p{Graph}+?\"|\\p{Graph}+/\\p{Graph}+\\b)").matcher(replaceAll);
            if (matcher.find()) {
                String substring = matcher.group().substring(5);
                if (substring.charAt(0) == '\"') {
                    substring = substring.substring(1, substring.length() - 1);
                }
                Matcher matcher2 = Pattern.compile("\\bexts=(\"[\\p{Graph}\\p{Blank}]+?\"|\\p{Graph}+\\b)").matcher(replaceAll);
                if (matcher2.find()) {
                    String substring2 = matcher2.group().substring(5);
                    if (substring2.charAt(0) == '\"') {
                        substring2 = substring2.substring(1, substring2.length() - 1);
                    }
                    for (String str2 : substring2.split("[\\p{Blank}\\p{Punct}]+")) {
                        putIfAbsent(str2, substring);
                    }
                    return;
                }
                return;
            }
            return;
        }
        String[] split = replaceAll.split("\\s+");
        for (int i = 1; i < split.length; i++) {
            putIfAbsent(split[i], split[0]);
        }
    }

    private void putIfAbsent(String str, String str2) {
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty() || this.mimeTypeMap.containsKey(str)) {
            return;
        }
        this.mimeTypeMap.put(str, str2);
    }
}
