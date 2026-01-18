package java.nio.file.attribute;

import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface BasicFileAttributeView extends FileAttributeView {
    String name();

    BasicFileAttributes readAttributes() throws IOException;

    void setTimes(FileTime fileTime, FileTime fileTime2, FileTime fileTime3) throws IOException;
}
