package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class FileKey {
    private long st_dev;
    private long st_ino;

    private native void init(FileDescriptor fileDescriptor) throws IOException;

    private static native void initIDs();

    private FileKey() {
    }

    public static FileKey create(FileDescriptor fileDescriptor) throws IOException {
        FileKey fileKey = new FileKey();
        fileKey.init(fileDescriptor);
        return fileKey;
    }

    public int hashCode() {
        long j = this.st_dev;
        int i = (int) (j ^ (j >>> 32));
        long j2 = this.st_ino;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FileKey)) {
            return false;
        }
        FileKey fileKey = (FileKey) obj;
        return this.st_dev == fileKey.st_dev && this.st_ino == fileKey.st_ino;
    }

    static {
        initIDs();
    }
}
