package desugar.sun.nio.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Iterable;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarLinuxFileSystemProvider extends FileSystemProvider {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final String FILE_SCHEME = "file";
    private final String rootDir;
    private volatile DesugarLinuxFileSystem theFileSystem;
    private final String userDir;

    static /* synthetic */ DesugarLinuxFileSystem access$000(DesugarLinuxFileSystemProvider desugarLinuxFileSystemProvider) {
        return desugarLinuxFileSystemProvider.theFileSystem;
    }

    static /* synthetic */ String access$100(DesugarLinuxFileSystemProvider desugarLinuxFileSystemProvider) {
        return desugarLinuxFileSystemProvider.userDir;
    }

    static /* synthetic */ String access$200(DesugarLinuxFileSystemProvider desugarLinuxFileSystemProvider) {
        return desugarLinuxFileSystemProvider.rootDir;
    }

    public static DesugarLinuxFileSystemProvider create() {
        return new DesugarLinuxFileSystemProvider(System.getProperty("user.dir"), "/");
    }

    DesugarLinuxFileSystemProvider(String userDir, String rootDir) {
        this.userDir = userDir;
        this.rootDir = rootDir;
    }

    public String getScheme() {
        return "file";
    }

    public DesugarLinuxFileSystem newFileSystem(URI uri, Map env) {
        checkFileUri(uri);
        throw new FileSystemAlreadyExistsException();
    }

    public final DesugarLinuxFileSystem getFileSystem(URI uri) {
        DesugarLinuxFileSystem desugarLinuxFileSystem;
        checkFileUri(uri);
        DesugarLinuxFileSystem desugarLinuxFileSystem2 = this.theFileSystem;
        if (desugarLinuxFileSystem2 != null) {
            return desugarLinuxFileSystem2;
        }
        synchronized (this) {
            desugarLinuxFileSystem = this.theFileSystem;
            if (desugarLinuxFileSystem == null) {
                desugarLinuxFileSystem = new DesugarLinuxFileSystem(this, this.userDir, this.rootDir);
                this.theFileSystem = desugarLinuxFileSystem;
            }
        }
        return desugarLinuxFileSystem;
    }

    public Path getPath(URI uri) {
        return DesugarUnixUriUtils.fromUri(this.theFileSystem, uri, this.userDir, this.rootDir);
    }

    public DirectoryStream newDirectoryStream(Path dir, DirectoryStream.Filter filter) throws IOException {
        filter.getClass();
        return new DesugarDirectoryStream(dir, filter);
    }

    public void createDirectory(Path dir, FileAttribute... attrs) throws IOException {
        if (dir.getParent() != null && !Files.exists(dir.getParent(), new LinkOption[0])) {
            throw new NoSuchFileException(dir.toString());
        }
        if (!dir.toFile().mkdirs()) {
            throw new FileAlreadyExistsException(dir.toString());
        }
    }

    private boolean exists(Path file) {
        try {
            checkAccess(file, new AccessMode[0]);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public void delete(Path path) throws IOException {
        if (exists(path)) {
            deleteIfExists(path);
            return;
        }
        throw new NoSuchFileException(path.toString());
    }

    public boolean deleteIfExists(Path path) throws IOException {
        return path.toFile().delete();
    }

    public SeekableByteChannel newByteChannel(Path path, Set options, FileAttribute... attrs) throws IOException {
        return newFileChannel(path, options, attrs);
    }

    public FileChannel newFileChannel(Path path, Set options, FileAttribute... attrs) throws IOException {
        if (path.toFile().isDirectory()) {
            throw new UnsupportedOperationException("The desugar library does not support creating a file channel on a directory: " + path);
        }
        return DesugarFileChannel.openEmulatedFileChannel(path, options, attrs);
    }

    public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set options, ExecutorService executor, FileAttribute... attrs) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void createLink(Path link, Path existing) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void createSymbolicLink(Path link, Path target, FileAttribute... attrs) throws IOException {
        throw new UnsupportedOperationException();
    }

    public Path readSymbolicLink(Path link) throws IOException {
        return new DesugarUnixPath(this.theFileSystem, link.toFile().getCanonicalPath(), this.userDir, this.rootDir);
    }

    public void copy(Path source, Path target, CopyOption... options) throws IOException {
        if (!containsCopyOption(options, StandardCopyOption.REPLACE_EXISTING) && Files.exists(target, new LinkOption[0])) {
            throw new FileAlreadyExistsException(target.toString());
        }
        if (containsCopyOption(options, StandardCopyOption.ATOMIC_MOVE)) {
            throw new UnsupportedOperationException("Unsupported copy option");
        }
        FileInputStream fileInputStream = new FileInputStream(source.toFile());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(target.toFile());
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = fileInputStream.read(bArr, 0, 8192);
                    if (read >= 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        fileInputStream.close();
                        return;
                    }
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                DesugarLinuxFileSystemProvider$$ExternalSyntheticBackport0.m(th, th2);
            }
            throw th;
        }
    }

    public void move(Path source, Path target, CopyOption... options) throws IOException {
        if (!containsCopyOption(options, StandardCopyOption.REPLACE_EXISTING) && Files.exists(target, new LinkOption[0])) {
            throw new FileAlreadyExistsException(target.toString());
        }
        if (containsCopyOption(options, StandardCopyOption.COPY_ATTRIBUTES)) {
            throw new UnsupportedOperationException("Unsupported copy option");
        }
        source.toFile().renameTo(target.toFile());
    }

    private boolean containsCopyOption(CopyOption[] options, CopyOption option) {
        for (CopyOption copyOption : options) {
            if (copyOption == option) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameFile(Path path, Path path2) throws IOException {
        if (path.equals(path2)) {
            return true;
        }
        checkAccess(path, new AccessMode[0]);
        checkAccess(path2, new AccessMode[0]);
        return path.toFile().equals(path2.toFile());
    }

    public boolean isHidden(Path path) throws IOException {
        return path.toFile().isHidden();
    }

    public FileStore getFileStore(Path path) throws IOException {
        throw new SecurityException("getFileStore");
    }

    public FileAttributeView getFileAttributeView(Path path, Class type, LinkOption... options) {
        type.getClass();
        if (type == BasicFileAttributeView.class) {
            return (FileAttributeView) type.cast(new DesugarBasicFileAttributeView(path));
        }
        return null;
    }

    public void checkAccess(Path path, AccessMode... modes) throws IOException {
        boolean canRead;
        File file = path.toFile();
        if (!file.exists()) {
            throw new NoSuchFileException(path.toString());
        }
        boolean z = true;
        for (AccessMode accessMode : modes) {
            int i = 1.$SwitchMap$java$nio$file$AccessMode[accessMode.ordinal()];
            if (i == 1) {
                canRead = file.canRead();
            } else if (i == 2) {
                canRead = file.canWrite();
            } else if (i == 3) {
                canRead = file.canExecute();
            }
            z &= canRead;
        }
        if (!z) {
            throw new IOException(String.format("Unable to access file %s", path));
        }
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$AccessMode;

        static {
            int[] iArr = new int[AccessMode.values().length];
            $SwitchMap$java$nio$file$AccessMode = iArr;
            try {
                iArr[AccessMode.READ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$nio$file$AccessMode[AccessMode.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$nio$file$AccessMode[AccessMode.EXECUTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public BasicFileAttributes readAttributes(Path path, Class type, LinkOption... options) throws IOException {
        if (type != BasicFileAttributes.class) {
            throw new UnsupportedOperationException();
        }
        return (BasicFileAttributes) type.cast(((BasicFileAttributeView) getFileAttributeView(path, BasicFileAttributeView.class, options)).readAttributes());
    }

    public Map readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
        String[] split;
        Class<BasicFileAttributeView> cls;
        int indexOf = attributes.indexOf(":");
        if (indexOf == -1) {
            cls = BasicFileAttributeView.class;
            split = attributes.split(",");
        } else {
            String substring = attributes.substring(0, indexOf);
            if ("basic".equals(substring)) {
                split = attributes.substring(indexOf + 1).split(",");
                cls = BasicFileAttributeView.class;
            } else {
                throw new UnsupportedOperationException(String.format("Requested attribute type for: %s is not available.", substring));
            }
        }
        if (cls == BasicFileAttributeView.class) {
            return new DesugarBasicFileAttributeView(path).readAttributes(split);
        }
        throw new AssertionError("Unexpected View '" + cls + "' requested");
    }

    public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
        Class<BasicFileAttributeView> cls;
        int indexOf = attribute.indexOf(":");
        if (indexOf == -1) {
            cls = BasicFileAttributeView.class;
        } else {
            String substring = attribute.substring(0, indexOf);
            if (!"basic".equals(substring)) {
                throw new UnsupportedOperationException(String.format("Requested attribute type for: %s is not available.", substring));
            }
            attribute = attribute.substring(indexOf + 1);
            cls = BasicFileAttributeView.class;
        }
        if (cls == BasicFileAttributeView.class) {
            new DesugarBasicFileAttributeView(path).setAttribute(attribute, value);
            return;
        }
        throw new AssertionError("Unexpected View '" + cls + "' requested");
    }

    FileTypeDetector getFileTypeDetector() {
        return new DesugarMimeTypesFileTypeDetector();
    }

    private void checkFileUri(URI uri) {
        if (!uri.getScheme().equalsIgnoreCase(getScheme())) {
            throw new IllegalArgumentException("URI does not match this provider");
        }
        if (uri.getRawAuthority() != null) {
            throw new IllegalArgumentException("Authority component present");
        }
        String path = uri.getPath();
        if (path == null) {
            throw new IllegalArgumentException("Path component is undefined");
        }
        if (!path.equals("/")) {
            throw new IllegalArgumentException("Path component should be '/'");
        }
        if (uri.getRawQuery() != null) {
            throw new IllegalArgumentException("Query component present");
        }
        if (uri.getRawFragment() != null) {
            throw new IllegalArgumentException("Fragment component present");
        }
    }

    class DesugarPathIterator implements Iterator {
        private final File[] candidates;
        private final DirectoryStream.Filter filter;
        private int index = 0;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        DesugarPathIterator(Path dir, DirectoryStream.Filter filter) {
            File[] listFiles = dir.toFile().listFiles();
            this.candidates = listFiles == null ? new File[0] : listFiles;
            this.filter = filter;
        }

        public boolean hasNext() {
            if (next() == null) {
                return false;
            }
            this.index--;
            return true;
        }

        public Path next() {
            DesugarUnixPath desugarUnixPath;
            do {
                int i = this.index;
                File[] fileArr = this.candidates;
                if (i >= fileArr.length) {
                    return null;
                }
                this.index = i + 1;
                desugarUnixPath = new DesugarUnixPath(DesugarLinuxFileSystemProvider.access$000(DesugarLinuxFileSystemProvider.this), fileArr[i].getPath(), DesugarLinuxFileSystemProvider.access$100(DesugarLinuxFileSystemProvider.this), DesugarLinuxFileSystemProvider.access$200(DesugarLinuxFileSystemProvider.this));
                try {
                } catch (IOException e) {
                    throw new DirectoryIteratorException(e);
                }
            } while (!this.filter.accept(desugarUnixPath));
            return desugarUnixPath;
        }
    }

    class DesugarDirectoryStream implements DirectoryStream {
        DesugarPathIterator iterator;

        public void close() throws IOException {
        }

        public /* synthetic */ void forEach(Consumer consumer) {
            Iterable.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Iterable.-CC.$default$spliterator(this);
        }

        DesugarDirectoryStream(Path dir, DirectoryStream.Filter filter) {
            this.iterator = DesugarLinuxFileSystemProvider.this.new DesugarPathIterator(dir, filter);
        }

        public Iterator iterator() {
            return this.iterator;
        }
    }
}
