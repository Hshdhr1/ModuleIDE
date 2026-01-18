package desugar.sun.nio.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DesugarBasicFileAttributes implements BasicFileAttributes {
    private final FileTime creationTime;
    private final Object fileKey;
    private final boolean isDirectory;
    private final boolean isOther;
    private final boolean isRegularFile;
    private final boolean isSymbolicLink;
    private final FileTime lastAccessTime;
    private final FileTime lastModifiedTime;
    private final long size;

    public static DesugarBasicFileAttributes create(File file) {
        FileTime from = FileTime.from(file.lastModified(), TimeUnit.MILLISECONDS);
        boolean isFile = file.isFile();
        boolean isDirectory = file.isDirectory();
        boolean isSymlink = isSymlink(file);
        return new DesugarBasicFileAttributes(from, from, from, isFile, isDirectory, isSymlink, (isFile || isDirectory || isSymlink) ? false : true, file.length(), Integer.valueOf(file.hashCode()));
    }

    public DesugarBasicFileAttributes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime creationTime, boolean isRegularFile, boolean isDirectory, boolean isSymbolicLink, boolean isOther, long size, Object fileKey) {
        this.lastModifiedTime = lastModifiedTime;
        this.lastAccessTime = lastAccessTime;
        this.creationTime = creationTime;
        this.isRegularFile = isRegularFile;
        this.isDirectory = isDirectory;
        this.isSymbolicLink = isSymbolicLink;
        this.isOther = isOther;
        this.size = size;
        this.fileKey = fileKey;
    }

    public FileTime lastModifiedTime() {
        return this.lastModifiedTime;
    }

    public FileTime lastAccessTime() {
        return this.lastAccessTime;
    }

    public FileTime creationTime() {
        return this.creationTime;
    }

    public boolean isRegularFile() {
        return this.isRegularFile;
    }

    public boolean isDirectory() {
        return this.isDirectory;
    }

    public boolean isSymbolicLink() {
        return this.isSymbolicLink;
    }

    public boolean isOther() {
        return this.isOther;
    }

    public long size() {
        return this.size;
    }

    public Object fileKey() {
        return this.fileKey;
    }

    public static boolean isSymlink(File file) {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        try {
            if (file.getParent() != null) {
                file = new File(file.getParentFile().getCanonicalFile(), file.getName());
            }
            return !file.getCanonicalFile().equals(file.getAbsoluteFile());
        } catch (IOException unused) {
            return false;
        }
    }
}
