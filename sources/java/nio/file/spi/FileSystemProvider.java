package java.nio.file.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class FileSystemProvider {
    private static volatile List installedProviders = null;
    private static boolean loadingProviders = false;
    private static final Object lock = new Object();
    private static final Set DEFAULT_OPEN_OPTIONS = FileSystemProvider$$ExternalSyntheticBackport0.m(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

    static /* bridge */ /* synthetic */ List -$$Nest$smloadInstalledProviders() {
        return loadInstalledProviders();
    }

    public abstract void checkAccess(Path path, AccessMode... accessModeArr) throws IOException;

    public abstract void copy(Path path, Path path2, CopyOption... copyOptionArr) throws IOException;

    public abstract void createDirectory(Path path, FileAttribute... fileAttributeArr) throws IOException;

    public abstract void delete(Path path) throws IOException;

    public abstract FileAttributeView getFileAttributeView(Path path, Class cls, LinkOption... linkOptionArr);

    public abstract FileStore getFileStore(Path path) throws IOException;

    public abstract FileSystem getFileSystem(URI uri);

    public abstract Path getPath(URI uri);

    public abstract String getScheme();

    public abstract boolean isHidden(Path path) throws IOException;

    public abstract boolean isSameFile(Path path, Path path2) throws IOException;

    public abstract void move(Path path, Path path2, CopyOption... copyOptionArr) throws IOException;

    public abstract SeekableByteChannel newByteChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException;

    public abstract DirectoryStream newDirectoryStream(Path path, DirectoryStream.Filter filter) throws IOException;

    public abstract FileSystem newFileSystem(URI uri, Map map) throws IOException;

    public abstract BasicFileAttributes readAttributes(Path path, Class cls, LinkOption... linkOptionArr) throws IOException;

    public abstract Map readAttributes(Path path, String str, LinkOption... linkOptionArr) throws IOException;

    public abstract void setAttribute(Path path, String str, Object obj, LinkOption... linkOptionArr) throws IOException;

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        securityManager.checkPermission(new RuntimePermission("fileSystemProvider"));
        return null;
    }

    private FileSystemProvider(Void r1) {
    }

    protected FileSystemProvider() {
        this(checkPermission());
    }

    private static List loadInstalledProviders() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ServiceLoader.load(FileSystemProvider.class, ClassLoader.getSystemClassLoader()).iterator();
        while (it.hasNext()) {
            FileSystemProvider fileSystemProvider = (FileSystemProvider) it.next();
            String scheme = fileSystemProvider.getScheme();
            if (!scheme.equalsIgnoreCase("file")) {
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((FileSystemProvider) it2.next()).getScheme().equalsIgnoreCase(scheme)) {
                            break;
                        }
                    } else {
                        arrayList.add(fileSystemProvider);
                        break;
                    }
                }
            }
        }
        return arrayList;
    }

    public static List installedProviders() {
        if (installedProviders == null) {
            FileSystemProvider provider = FileSystems.getDefault().provider();
            synchronized (lock) {
                if (installedProviders == null) {
                    if (loadingProviders) {
                        throw new Error("Circular loading of installed providers detected");
                    }
                    loadingProviders = true;
                    List list = (List) AccessController.doPrivileged(new 1());
                    list.add(0, provider);
                    installedProviders = Collections.unmodifiableList(list);
                }
            }
        }
        return installedProviders;
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public List run() {
            return FileSystemProvider.-$$Nest$smloadInstalledProviders();
        }
    }

    public FileSystem newFileSystem(Path path, Map map) throws IOException {
        throw new UnsupportedOperationException();
    }

    public InputStream newInputStream(Path path, OpenOption... openOptionArr) throws IOException {
        if (openOptionArr.length > 0) {
            for (OpenOption openOption : openOptionArr) {
                if (openOption == StandardOpenOption.APPEND || openOption == StandardOpenOption.WRITE) {
                    throw new UnsupportedOperationException("'" + openOption + "' not allowed");
                }
            }
        }
        return Channels.newInputStream(Files.newByteChannel(path, openOptionArr));
    }

    public OutputStream newOutputStream(Path path, OpenOption... openOptionArr) throws IOException {
        Set set;
        if (openOptionArr.length == 0) {
            set = DEFAULT_OPEN_OPTIONS;
        } else {
            Set hashSet = new HashSet();
            for (OpenOption openOption : openOptionArr) {
                if (openOption == StandardOpenOption.READ) {
                    throw new IllegalArgumentException("READ not allowed");
                }
                hashSet.add(openOption);
            }
            hashSet.add(StandardOpenOption.WRITE);
            set = hashSet;
        }
        return Channels.newOutputStream(newByteChannel(path, set, new FileAttribute[0]));
    }

    public FileChannel newFileChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        throw new UnsupportedOperationException();
    }

    public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set set, ExecutorService executorService, FileAttribute... fileAttributeArr) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void createSymbolicLink(Path path, Path path2, FileAttribute... fileAttributeArr) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void createLink(Path path, Path path2) throws IOException {
        throw new UnsupportedOperationException();
    }

    public boolean deleteIfExists(Path path) throws IOException {
        try {
            delete(path);
            return true;
        } catch (NoSuchFileException unused) {
            return false;
        }
    }

    public Path readSymbolicLink(Path path) throws IOException {
        throw new UnsupportedOperationException();
    }
}
