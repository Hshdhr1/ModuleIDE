package sun.nio.fs;

import java.io.IOException;
import java.nio.file.AccessMode;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class AbstractFileSystemProvider extends FileSystemProvider {
    abstract DynamicFileAttributeView getFileAttributeView(Path path, String str, LinkOption... linkOptionArr);

    abstract boolean implDelete(Path path, boolean z) throws IOException;

    protected AbstractFileSystemProvider() {
    }

    private static String[] split(String str) {
        String[] strArr = new String[2];
        int indexOf = str.indexOf(58);
        if (indexOf == -1) {
            strArr[0] = "basic";
            strArr[1] = str;
            return strArr;
        }
        int i = indexOf + 1;
        strArr[0] = str.substring(0, indexOf);
        strArr[1] = i == str.length() ? "" : str.substring(i);
        return strArr;
    }

    public final void setAttribute(Path path, String str, Object obj, LinkOption... linkOptionArr) throws IOException {
        String[] split = split(str);
        if (split[0].isEmpty()) {
            throw new IllegalArgumentException(str);
        }
        DynamicFileAttributeView fileAttributeView = getFileAttributeView(path, split[0], linkOptionArr);
        if (fileAttributeView == null) {
            throw new UnsupportedOperationException("View '" + split[0] + "' not available");
        }
        fileAttributeView.setAttribute(split[1], obj);
    }

    public final Map readAttributes(Path path, String str, LinkOption... linkOptionArr) throws IOException {
        String[] split = split(str);
        if (split[0].isEmpty()) {
            throw new IllegalArgumentException(str);
        }
        DynamicFileAttributeView fileAttributeView = getFileAttributeView(path, split[0], linkOptionArr);
        if (fileAttributeView == null) {
            throw new UnsupportedOperationException("View '" + split[0] + "' not available");
        }
        return fileAttributeView.readAttributes(split[1].split(","));
    }

    public final void delete(Path path) throws IOException {
        implDelete(path, true);
    }

    public final boolean deleteIfExists(Path path) throws IOException {
        return implDelete(path, false);
    }

    public boolean isDirectory(Path path) {
        try {
            return readAttributes(path, BasicFileAttributes.class, new LinkOption[0]).isDirectory();
        } catch (IOException unused) {
            return false;
        }
    }

    public boolean isRegularFile(Path path) {
        try {
            return readAttributes(path, BasicFileAttributes.class, new LinkOption[0]).isRegularFile();
        } catch (IOException unused) {
            return false;
        }
    }

    public boolean exists(Path path) {
        try {
            checkAccess(path, new AccessMode[0]);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }
}
