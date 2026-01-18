package desugar.sun.nio.fs;

import java.io.IOException;
import java.nio.file.AccessMode;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DesugarBasicFileAttributeView extends DesugarAbstractBasicFileAttributeView {
    private final Path path;

    public DesugarBasicFileAttributeView(Path path) {
        this.path = path;
    }

    public BasicFileAttributes readAttributes() throws IOException {
        this.path.getFileSystem().provider().checkAccess(this.path, new AccessMode[0]);
        return DesugarBasicFileAttributes.create(this.path.toFile());
    }

    public void setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime) throws IOException {
        this.path.toFile().setLastModified(lastModifiedTime.to(TimeUnit.MILLISECONDS));
    }
}
