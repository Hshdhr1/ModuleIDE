package desugar.sun.nio.fs;

import java.nio.file.FileSystems;
import java.nio.file.spi.FileTypeDetector;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarDefaultFileTypeDetector {
    private DesugarDefaultFileTypeDetector() {
    }

    public static FileTypeDetector create() {
        return ((DesugarLinuxFileSystemProvider) FileSystems.getDefault().provider()).getFileTypeDetector();
    }
}
