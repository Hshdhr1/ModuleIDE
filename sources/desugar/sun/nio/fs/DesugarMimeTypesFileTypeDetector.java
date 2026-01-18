package desugar.sun.nio.fs;

import java.nio.file.Path;
import libcore.content.type.MimeMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DesugarMimeTypesFileTypeDetector extends DesugarAbstractFileTypeDetector {
    DesugarMimeTypesFileTypeDetector() {
    }

    protected String implProbeContentType(Path path) {
        String guessMimeTypeFromExtension;
        Path fileName = path.getFileName();
        if (fileName == null) {
            return null;
        }
        String extension = getExtension(fileName.toString());
        if (extension.isEmpty()) {
            return null;
        }
        do {
            guessMimeTypeFromExtension = MimeMap.getDefault().guessMimeTypeFromExtension(extension);
            if (guessMimeTypeFromExtension == null) {
                extension = getExtension(extension);
            }
            if (guessMimeTypeFromExtension != null) {
                break;
            }
        } while (!extension.isEmpty());
        return guessMimeTypeFromExtension;
    }
}
