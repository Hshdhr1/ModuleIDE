package java.nio.file.spi;

import java.io.IOException;
import java.nio.file.Path;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class FileTypeDetector {
    public abstract String probeContentType(Path path) throws IOException;

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        securityManager.checkPermission(new RuntimePermission("fileTypeDetector"));
        return null;
    }

    private FileTypeDetector(Void r1) {
    }

    protected FileTypeDetector() {
        this(checkPermission());
    }
}
