package java.nio.file;

import java.io.IOException;
import java.net.URI;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import sun.nio.fs.DefaultFileSystemProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class FileSystems {
    private FileSystems() {
    }

    private static class DefaultFileSystemHolder {
        static final FileSystem defaultFileSystem = defaultFileSystem();

        static /* bridge */ /* synthetic */ FileSystemProvider -$$Nest$smgetDefaultProvider() {
            return getDefaultProvider();
        }

        private DefaultFileSystemHolder() {
        }

        class 1 implements PrivilegedAction {
            1() {
            }

            public FileSystemProvider run() {
                return DefaultFileSystemHolder.-$$Nest$smgetDefaultProvider();
            }
        }

        private static FileSystem defaultFileSystem() {
            return ((FileSystemProvider) AccessController.doPrivileged(new 1())).getFileSystem(URI.create("file:///"));
        }

        private static FileSystemProvider getDefaultProvider() {
            FileSystemProvider instance = DefaultFileSystemProvider.instance();
            String property = System.getProperty("java.nio.file.spi.DefaultFileSystemProvider");
            if (property != null) {
                for (String str : property.split(",")) {
                    try {
                        instance = (FileSystemProvider) Class.forName(str, true, ClassLoader.getSystemClassLoader()).getDeclaredConstructor(new Class[]{FileSystemProvider.class}).newInstance(new Object[]{instance});
                        if (!instance.getScheme().equals("file")) {
                            throw new Error("Default provider must use scheme 'file'");
                        }
                    } catch (Exception e) {
                        throw new Error(e);
                    }
                }
            }
            return instance;
        }
    }

    public static FileSystem getDefault() {
        return DefaultFileSystemHolder.defaultFileSystem;
    }

    public static FileSystem getFileSystem(URI uri) {
        String scheme = uri.getScheme();
        for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
            if (scheme.equalsIgnoreCase(fileSystemProvider.getScheme())) {
                return fileSystemProvider.getFileSystem(uri);
            }
        }
        throw new ProviderNotFoundException("Provider \"" + scheme + "\" not found");
    }

    public static FileSystem newFileSystem(URI uri, Map map) throws IOException {
        return newFileSystem(uri, map, null);
    }

    public static FileSystem newFileSystem(URI uri, Map map, ClassLoader classLoader) throws IOException {
        String scheme = uri.getScheme();
        for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
            if (scheme.equalsIgnoreCase(fileSystemProvider.getScheme())) {
                try {
                    return fileSystemProvider.newFileSystem(uri, map);
                } catch (UnsupportedOperationException unused) {
                }
            }
        }
        if (classLoader != null) {
            Iterator it = ServiceLoader.load(FileSystemProvider.class, classLoader).iterator();
            while (it.hasNext()) {
                FileSystemProvider fileSystemProvider2 = (FileSystemProvider) it.next();
                if (scheme.equalsIgnoreCase(fileSystemProvider2.getScheme())) {
                    try {
                        return fileSystemProvider2.newFileSystem(uri, map);
                    } catch (UnsupportedOperationException unused2) {
                    }
                }
            }
        }
        throw new ProviderNotFoundException("Provider \"" + scheme + "\" not found");
    }

    public static FileSystem newFileSystem(Path path, ClassLoader classLoader) throws IOException {
        path.getClass();
        Map map = Collections.EMPTY_MAP;
        Iterator it = FileSystemProvider.installedProviders().iterator();
        while (it.hasNext()) {
            try {
                return ((FileSystemProvider) it.next()).newFileSystem(path, map);
            } catch (UnsupportedOperationException unused) {
            }
        }
        if (classLoader != null) {
            Iterator it2 = ServiceLoader.load(FileSystemProvider.class, classLoader).iterator();
            while (it2.hasNext()) {
                try {
                    return ((FileSystemProvider) it2.next()).newFileSystem(path, map);
                } catch (UnsupportedOperationException unused2) {
                }
            }
        }
        throw new ProviderNotFoundException("Provider not found");
    }
}
