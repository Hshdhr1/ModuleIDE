package sun.security.action;

import java.io.File;
import java.io.FileInputStream;
import java.security.PrivilegedExceptionAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class OpenFileInputStreamAction implements PrivilegedExceptionAction {
    private final File file;

    public OpenFileInputStreamAction(File file) {
        this.file = file;
    }

    public OpenFileInputStreamAction(String str) {
        this.file = new File(str);
    }

    public FileInputStream run() throws Exception {
        return new FileInputStream(this.file);
    }
}
