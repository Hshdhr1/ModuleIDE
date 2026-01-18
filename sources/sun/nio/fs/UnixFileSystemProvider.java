package sun.nio.fs;

import java.io.FilePermission;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.LinkPermission;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.spi.FileTypeDetector;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import sun.nio.ch.ThreadPool;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class UnixFileSystemProvider extends AbstractFileSystemProvider {
    private static final String USER_DIR = "user.dir";
    private final UnixFileSystem theFileSystem = newFileSystem(System.getProperty("user.dir"));

    abstract FileStore getFileStore(UnixPath unixPath) throws IOException;

    abstract UnixFileSystem newFileSystem(String str);

    UnixFileSystem theFileSystem() {
        return this.theFileSystem;
    }

    public final String getScheme() {
        return "file";
    }

    private void checkUri(URI uri) {
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

    public final FileSystem newFileSystem(URI uri, Map map) {
        checkUri(uri);
        throw new FileSystemAlreadyExistsException();
    }

    public final FileSystem getFileSystem(URI uri) {
        checkUri(uri);
        return this.theFileSystem;
    }

    public Path getPath(URI uri) {
        return UnixUriUtils.fromUri(this.theFileSystem, uri);
    }

    UnixPath checkPath(Path path) {
        path.getClass();
        if (!(path instanceof UnixPath)) {
            throw new ProviderMismatchException();
        }
        return (UnixPath) path;
    }

    public FileAttributeView getFileAttributeView(Path path, Class cls, LinkOption... linkOptionArr) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        boolean followLinks = Util.followLinks(linkOptionArr);
        if (cls == BasicFileAttributeView.class) {
            return UnixFileAttributeViews.createBasicView(unixPath, followLinks);
        }
        if (cls == PosixFileAttributeView.class) {
            return UnixFileAttributeViews.createPosixView(unixPath, followLinks);
        }
        if (cls == FileOwnerAttributeView.class) {
            return UnixFileAttributeViews.createOwnerView(unixPath, followLinks);
        }
        cls.getClass();
        return null;
    }

    public BasicFileAttributes readAttributes(Path path, Class cls, LinkOption... linkOptionArr) throws IOException {
        Class cls2;
        if (cls == BasicFileAttributes.class) {
            cls2 = BasicFileAttributeView.class;
        } else if (cls == PosixFileAttributes.class) {
            cls2 = PosixFileAttributeView.class;
        } else {
            cls.getClass();
            throw new UnsupportedOperationException();
        }
        return ((BasicFileAttributeView) getFileAttributeView(path, cls2, linkOptionArr)).readAttributes();
    }

    protected DynamicFileAttributeView getFileAttributeView(Path path, String str, LinkOption... linkOptionArr) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        boolean followLinks = Util.followLinks(linkOptionArr);
        if (str.equals("basic")) {
            return UnixFileAttributeViews.createBasicView(unixPath, followLinks);
        }
        if (str.equals("posix")) {
            return UnixFileAttributeViews.createPosixView(unixPath, followLinks);
        }
        if (str.equals("unix")) {
            return UnixFileAttributeViews.createUnixView(unixPath, followLinks);
        }
        if (str.equals("owner")) {
            return UnixFileAttributeViews.createOwnerView(unixPath, followLinks);
        }
        return null;
    }

    public FileChannel newFileChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath checkPath = checkPath(path);
        try {
            return UnixChannelFactory.newFileChannel(checkPath, set, UnixFileModeAttribute.toUnixMode(438, fileAttributeArr));
        } catch (UnixException e) {
            e.rethrowAsIOException(checkPath);
            return null;
        }
    }

    public AsynchronousFileChannel newAsynchronousFileChannel(Path path, Set set, ExecutorService executorService, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath checkPath = checkPath(path);
        try {
            return UnixChannelFactory.newAsynchronousFileChannel(checkPath, set, UnixFileModeAttribute.toUnixMode(438, fileAttributeArr), executorService == null ? null : ThreadPool.wrap(executorService, 0));
        } catch (UnixException e) {
            e.rethrowAsIOException(checkPath);
            return null;
        }
    }

    public SeekableByteChannel newByteChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        try {
            return UnixChannelFactory.newFileChannel(unixPath, set, UnixFileModeAttribute.toUnixMode(438, fileAttributeArr));
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
            return null;
        }
    }

    boolean implDelete(Path path, boolean z) throws IOException {
        UnixFileAttributes unixFileAttributes;
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkDelete();
        try {
            unixFileAttributes = UnixFileAttributes.get(unixPath, false);
            try {
                if (unixFileAttributes.isDirectory()) {
                    UnixNativeDispatcher.rmdir(unixPath);
                    return true;
                }
                UnixNativeDispatcher.unlink(unixPath);
                return true;
            } catch (UnixException e) {
                e = e;
                if (!z && e.errno() == 2) {
                    return false;
                }
                if (unixFileAttributes != null && unixFileAttributes.isDirectory() && (e.errno() == 17 || e.errno() == 39)) {
                    throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
                }
                e.rethrowAsIOException(unixPath);
                return false;
            }
        } catch (UnixException e2) {
            e = e2;
            unixFileAttributes = null;
        }
    }

    public void copy(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        UnixCopyFile.copy(UnixPath.toUnixPath(path), UnixPath.toUnixPath(path2), copyOptionArr);
    }

    public void move(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        UnixCopyFile.move(UnixPath.toUnixPath(path), UnixPath.toUnixPath(path2), copyOptionArr);
    }

    public void checkAccess(Path path, AccessMode... accessModeArr) throws IOException {
        boolean z;
        boolean z2;
        boolean z3;
        UnixPath unixPath = UnixPath.toUnixPath(path);
        boolean z4 = true;
        int i = 0;
        if (accessModeArr.length == 0) {
            z = false;
            z2 = false;
            z3 = false;
        } else {
            z = false;
            z2 = false;
            z3 = false;
            for (AccessMode accessMode : accessModeArr) {
                int i2 = 3.$SwitchMap$java$nio$file$AccessMode[accessMode.ordinal()];
                if (i2 == 1) {
                    z = true;
                } else if (i2 == 2) {
                    z2 = true;
                } else {
                    if (i2 != 3) {
                        throw new AssertionError("Should not get here");
                    }
                    z3 = true;
                }
            }
            z4 = false;
        }
        if (z4 || z) {
            unixPath.checkRead();
            if (z) {
                i = 4;
            }
        }
        if (z2) {
            unixPath.checkWrite();
            i |= 2;
        }
        if (z3) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkExec(unixPath.getPathForPermissionCheck());
            }
            i |= 1;
        }
        try {
            UnixNativeDispatcher.access(unixPath, i);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
        }
    }

    static /* synthetic */ class 3 {
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

    public boolean isSameFile(Path path, Path path2) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        if (unixPath.equals(path2)) {
            return true;
        }
        path2.getClass();
        if (!(path2 instanceof UnixPath)) {
            return false;
        }
        UnixPath unixPath2 = (UnixPath) path2;
        unixPath.checkRead();
        unixPath2.checkRead();
        try {
            try {
                return UnixFileAttributes.get(unixPath, true).isSameFile(UnixFileAttributes.get(unixPath2, true));
            } catch (UnixException e) {
                e.rethrowAsIOException(unixPath2);
                return false;
            }
        } catch (UnixException e2) {
            e2.rethrowAsIOException(unixPath);
            return false;
        }
    }

    public boolean isHidden(Path path) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkRead();
        UnixPath fileName = unixPath.getFileName();
        return fileName != null && fileName.asByteArray()[0] == 46;
    }

    public FileStore getFileStore(Path path) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
            unixPath.checkRead();
        }
        return getFileStore(unixPath);
    }

    public void createDirectory(Path path, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkWrite();
        try {
            UnixNativeDispatcher.mkdir(unixPath, UnixFileModeAttribute.toUnixMode(511, fileAttributeArr));
        } catch (UnixException e) {
            if (e.errno() == 21) {
                throw new FileAlreadyExistsException(unixPath.toString());
            }
            e.rethrowAsIOException(unixPath);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.nio.file.DirectoryStream newDirectoryStream(java.nio.file.Path r7, java.nio.file.DirectoryStream.Filter r8) throws java.io.IOException {
        /*
            r6 = this;
            sun.nio.fs.UnixPath r1 = sun.nio.fs.UnixPath.toUnixPath(r7)
            r1.checkRead()
            r8.getClass()
            boolean r7 = sun.nio.fs.UnixNativeDispatcher.openatSupported()
            r2 = 20
            if (r7 == 0) goto L13
            goto L28
        L13:
            long r3 = sun.nio.fs.UnixNativeDispatcher.opendir(r1)     // Catch: sun.nio.fs.UnixException -> L1d
            sun.nio.fs.UnixDirectoryStream r7 = new sun.nio.fs.UnixDirectoryStream     // Catch: sun.nio.fs.UnixException -> L1d
            r7.<init>(r1, r3, r8)     // Catch: sun.nio.fs.UnixException -> L1d
            return r7
        L1d:
            r0 = move-exception
            r7 = r0
            int r0 = r7.errno()
            if (r0 == r2) goto L64
            r7.rethrowAsIOException(r1)
        L28:
            r7 = 0
            r3 = -1
            int r7 = sun.nio.fs.UnixNativeDispatcher.open(r1, r7, r7)     // Catch: sun.nio.fs.UnixException -> L3b
            int r4 = sun.nio.fs.UnixNativeDispatcher.dup(r7)     // Catch: sun.nio.fs.UnixException -> L39
            long r2 = sun.nio.fs.UnixNativeDispatcher.fdopendir(r7)     // Catch: sun.nio.fs.UnixException -> L37
            goto L53
        L37:
            r0 = move-exception
            goto L3e
        L39:
            r0 = move-exception
            goto L3d
        L3b:
            r0 = move-exception
            r7 = -1
        L3d:
            r4 = -1
        L3e:
            if (r7 == r3) goto L43
            sun.nio.fs.UnixNativeDispatcher.close(r7)
        L43:
            if (r4 == r3) goto L48
            sun.nio.fs.UnixNativeDispatcher.close(r4)
        L48:
            int r7 = r0.errno()
            if (r7 == r2) goto L5a
            r0.rethrowAsIOException(r1)
            r2 = 0
        L53:
            sun.nio.fs.UnixSecureDirectoryStream r0 = new sun.nio.fs.UnixSecureDirectoryStream
            r5 = r8
            r0.<init>(r1, r2, r4, r5)
            return r0
        L5a:
            java.nio.file.NotDirectoryException r7 = new java.nio.file.NotDirectoryException
            java.lang.String r8 = r1.getPathForExceptionMessage()
            r7.<init>(r8)
            throw r7
        L64:
            java.nio.file.NotDirectoryException r7 = new java.nio.file.NotDirectoryException
            java.lang.String r8 = r1.getPathForExceptionMessage()
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixFileSystemProvider.newDirectoryStream(java.nio.file.Path, java.nio.file.DirectoryStream$Filter):java.nio.file.DirectoryStream");
    }

    public void createSymbolicLink(Path path, Path path2, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        UnixPath unixPath2 = UnixPath.toUnixPath(path2);
        if (fileAttributeArr.length > 0) {
            UnixFileModeAttribute.toUnixMode(0, fileAttributeArr);
            throw new UnsupportedOperationException("Initial file attributesnot supported when creating symbolic link");
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new LinkPermission("symbolic"));
            unixPath.checkWrite();
        }
        try {
            UnixNativeDispatcher.symlink(unixPath2.asByteArray(), unixPath);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
        }
    }

    public void createLink(Path path, Path path2) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        UnixPath unixPath2 = UnixPath.toUnixPath(path2);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new LinkPermission("hard"));
            unixPath.checkWrite();
            unixPath2.checkWrite();
        }
        try {
            UnixNativeDispatcher.link(unixPath2, unixPath);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath, unixPath2);
        }
    }

    public Path readSymbolicLink(Path path) throws IOException {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new FilePermission(unixPath.getPathForPermissionCheck(), "readlink"));
        }
        try {
            return new UnixPath(unixPath.getFileSystem(), UnixNativeDispatcher.readlink(unixPath));
        } catch (UnixException e) {
            if (e.errno() == 22) {
                throw new NotLinkException(unixPath.getPathForExceptionMessage());
            }
            e.rethrowAsIOException(unixPath);
            return null;
        }
    }

    public final boolean isDirectory(Path path) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkRead();
        return (UnixNativeDispatcher.stat(unixPath) & 61440) == 16384;
    }

    public final boolean isRegularFile(Path path) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkRead();
        return (UnixNativeDispatcher.stat(unixPath) & 61440) == 32768;
    }

    public final boolean exists(Path path) {
        UnixPath unixPath = UnixPath.toUnixPath(path);
        unixPath.checkRead();
        return UnixNativeDispatcher.exists(unixPath);
    }

    class 1 extends AbstractFileTypeDetector {
        public String implProbeContentType(Path path) {
            return null;
        }

        1() {
        }
    }

    FileTypeDetector getFileTypeDetector() {
        return new 1();
    }

    class 2 extends AbstractFileTypeDetector {
        final /* synthetic */ AbstractFileTypeDetector[] val$detectors;

        2(AbstractFileTypeDetector[] abstractFileTypeDetectorArr) {
            this.val$detectors = abstractFileTypeDetectorArr;
        }

        protected String implProbeContentType(Path path) throws IOException {
            for (AbstractFileTypeDetector abstractFileTypeDetector : this.val$detectors) {
                String implProbeContentType = abstractFileTypeDetector.implProbeContentType(path);
                if (implProbeContentType != null && !implProbeContentType.isEmpty()) {
                    return implProbeContentType;
                }
            }
            return null;
        }
    }

    final FileTypeDetector chain(AbstractFileTypeDetector... abstractFileTypeDetectorArr) {
        return new 2(abstractFileTypeDetectorArr);
    }
}
