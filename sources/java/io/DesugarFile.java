package java.io;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarFile {
    private DesugarFile() {
    }

    public static Path toPath(File file) {
        return FileSystems.getDefault().getPath(file.getPath(), new String[0]);
    }
}
