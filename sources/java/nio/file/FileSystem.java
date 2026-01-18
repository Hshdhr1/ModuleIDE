package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class FileSystem implements Closeable {
    public abstract void close() throws IOException;

    public abstract Iterable getFileStores();

    public abstract Path getPath(String str, String... strArr);

    public abstract PathMatcher getPathMatcher(String str);

    public abstract Iterable getRootDirectories();

    public abstract String getSeparator();

    public abstract UserPrincipalLookupService getUserPrincipalLookupService();

    public abstract boolean isOpen();

    public abstract boolean isReadOnly();

    public abstract WatchService newWatchService() throws IOException;

    public abstract FileSystemProvider provider();

    public abstract Set supportedFileAttributeViews();

    protected FileSystem() {
    }
}
