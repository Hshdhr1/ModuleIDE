package java.nio.file;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.WatchEvent;
import java.nio.file.spi.FileSystemProvider;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Path extends Comparable, Iterable, Watchable {
    int compareTo(Path path);

    boolean endsWith(String str);

    boolean endsWith(Path path);

    boolean equals(Object obj);

    Path getFileName();

    FileSystem getFileSystem();

    Path getName(int i);

    int getNameCount();

    Path getParent();

    Path getRoot();

    int hashCode();

    boolean isAbsolute();

    Iterator iterator();

    Path normalize();

    WatchKey register(WatchService watchService, WatchEvent.Kind... kindArr) throws IOException;

    WatchKey register(WatchService watchService, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException;

    Path relativize(Path path);

    Path resolve(String str);

    Path resolve(Path path);

    Path resolveSibling(String str);

    Path resolveSibling(Path path);

    boolean startsWith(String str);

    boolean startsWith(Path path);

    Path subpath(int i, int i2);

    Path toAbsolutePath();

    File toFile();

    Path toRealPath(LinkOption... linkOptionArr) throws IOException;

    String toString();

    URI toUri();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ int $default$compareTo(Path _this, Object obj) {
            return _this.compareTo((Path) obj);
        }

        public static Path of(String str, String... strArr) {
            return FileSystems.getDefault().getPath(str, strArr);
        }

        public static Path of(URI uri) {
            String scheme = uri.getScheme();
            if (scheme == null) {
                throw new IllegalArgumentException("Missing scheme");
            }
            if (scheme.equalsIgnoreCase("file")) {
                return FileSystems.getDefault().provider().getPath(uri);
            }
            for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
                if (fileSystemProvider.getScheme().equalsIgnoreCase(scheme)) {
                    return fileSystemProvider.getPath(uri);
                }
            }
            throw new FileSystemNotFoundException("Provider \"" + scheme + "\" not installed");
        }

        public static boolean $default$startsWith(Path _this, String str) {
            return _this.startsWith(_this.getFileSystem().getPath(str, new String[0]));
        }

        public static boolean $default$endsWith(Path _this, String str) {
            return _this.endsWith(_this.getFileSystem().getPath(str, new String[0]));
        }

        public static Path $default$resolve(Path _this, String str) {
            return _this.resolve(_this.getFileSystem().getPath(str, new String[0]));
        }

        public static Path $default$resolveSibling(Path _this, Path path) {
            path.getClass();
            Path parent = _this.getParent();
            return parent == null ? path : parent.resolve(path);
        }

        public static Path $default$resolveSibling(Path _this, String str) {
            return _this.resolveSibling(_this.getFileSystem().getPath(str, new String[0]));
        }

        public static File $default$toFile(Path _this) {
            if (_this.getFileSystem() == FileSystems.getDefault()) {
                return new File(_this.toString());
            }
            throw new UnsupportedOperationException("Path not associated with default file system.");
        }

        public static WatchKey $default$register(Path _this, WatchService watchService, WatchEvent.Kind... kindArr) throws IOException {
            return _this.register(watchService, kindArr, new WatchEvent.Modifier[0]);
        }

        public static Iterator $default$iterator(Path _this) {
            return _this.new 1();
        }
    }

    class 1 implements Iterator {
        private int i = 0;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        1() {
        }

        public boolean hasNext() {
            return this.i < Path.this.getNameCount();
        }

        public Path next() {
            if (this.i < Path.this.getNameCount()) {
                Path name = Path.this.getName(this.i);
                this.i++;
                return name;
            }
            throw new NoSuchElementException();
        }
    }
}
