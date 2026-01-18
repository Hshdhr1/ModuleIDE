package sun.nio.fs;

import java.io.IOException;
import java.lang.Iterable;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.ClosedDirectoryStreamException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import sun.nio.fs.UnixUserPrincipals;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixSecureDirectoryStream implements SecureDirectoryStream {
    private final int dfd;
    private final UnixDirectoryStream ds;

    static /* bridge */ /* synthetic */ int -$$Nest$fgetdfd(UnixSecureDirectoryStream unixSecureDirectoryStream) {
        return unixSecureDirectoryStream.dfd;
    }

    static /* bridge */ /* synthetic */ UnixDirectoryStream -$$Nest$fgetds(UnixSecureDirectoryStream unixSecureDirectoryStream) {
        return unixSecureDirectoryStream.ds;
    }

    public /* synthetic */ void forEach(Consumer consumer) {
        Iterable.-CC.$default$forEach(this, consumer);
    }

    public /* synthetic */ Spliterator spliterator() {
        return Iterable.-CC.$default$spliterator(this);
    }

    UnixSecureDirectoryStream(UnixPath unixPath, long j, int i, DirectoryStream.Filter filter) {
        this.ds = new UnixDirectoryStream(unixPath, j, filter);
        this.dfd = i;
    }

    public void close() throws IOException {
        this.ds.writeLock().lock();
        try {
            if (this.ds.closeImpl()) {
                UnixNativeDispatcher.close(this.dfd);
            }
        } finally {
            this.ds.writeLock().unlock();
        }
    }

    public Iterator iterator() {
        return this.ds.iterator(this);
    }

    private UnixPath getName(Path path) {
        path.getClass();
        if (!(path instanceof UnixPath)) {
            throw new ProviderMismatchException();
        }
        return (UnixPath) path;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0050 A[Catch: all -> 0x0087, TRY_ENTER, TryCatch #1 {all -> 0x0087, blocks: (B:6:0x0024, B:13:0x0034, B:15:0x003e, B:17:0x0042, B:18:0x0065, B:25:0x0050, B:27:0x0055, B:28:0x0058, B:30:0x0060, B:31:0x0077, B:32:0x0080, B:40:0x0081, B:41:0x0086), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0055 A[Catch: all -> 0x0087, TryCatch #1 {all -> 0x0087, blocks: (B:6:0x0024, B:13:0x0034, B:15:0x003e, B:17:0x0042, B:18:0x0065, B:25:0x0050, B:27:0x0055, B:28:0x0058, B:30:0x0060, B:31:0x0077, B:32:0x0080, B:40:0x0081, B:41:0x0086), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0060 A[Catch: all -> 0x0087, TryCatch #1 {all -> 0x0087, blocks: (B:6:0x0024, B:13:0x0034, B:15:0x003e, B:17:0x0042, B:18:0x0065, B:25:0x0050, B:27:0x0055, B:28:0x0058, B:30:0x0060, B:31:0x0077, B:32:0x0080, B:40:0x0081, B:41:0x0086), top: B:5:0x0024 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077 A[Catch: all -> 0x0087, TRY_ENTER, TryCatch #1 {all -> 0x0087, blocks: (B:6:0x0024, B:13:0x0034, B:15:0x003e, B:17:0x0042, B:18:0x0065, B:25:0x0050, B:27:0x0055, B:28:0x0058, B:30:0x0060, B:31:0x0077, B:32:0x0080, B:40:0x0081, B:41:0x0086), top: B:5:0x0024 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.nio.file.SecureDirectoryStream newDirectoryStream(java.nio.file.Path r8, java.nio.file.LinkOption... r9) throws java.io.IOException {
        /*
            r7 = this;
            sun.nio.fs.UnixPath r8 = r7.getName(r8)
            sun.nio.fs.UnixDirectoryStream r0 = r7.ds
            sun.nio.fs.UnixPath r0 = r0.directory()
            sun.nio.fs.UnixPath r2 = r0.resolve(r8)
            boolean r9 = sun.nio.fs.Util.followLinks(r9)
            java.lang.SecurityManager r0 = java.lang.System.getSecurityManager()
            if (r0 == 0) goto L1b
            r2.checkRead()
        L1b:
            sun.nio.fs.UnixDirectoryStream r0 = r7.ds
            java.util.concurrent.locks.Lock r0 = r0.readLock()
            r0.lock()
            sun.nio.fs.UnixDirectoryStream r0 = r7.ds     // Catch: java.lang.Throwable -> L87
            boolean r0 = r0.isOpen()     // Catch: java.lang.Throwable -> L87
            if (r0 == 0) goto L81
            r0 = 0
            if (r9 != 0) goto L32
            r9 = 131072(0x20000, float:1.83671E-40)
            goto L33
        L32:
            r9 = 0
        L33:
            r1 = -1
            int r3 = r7.dfd     // Catch: sun.nio.fs.UnixException -> L4b java.lang.Throwable -> L87
            byte[] r4 = r8.asByteArray()     // Catch: sun.nio.fs.UnixException -> L4b java.lang.Throwable -> L87
            int r9 = sun.nio.fs.UnixNativeDispatcher.openat(r3, r4, r9, r0)     // Catch: sun.nio.fs.UnixException -> L4b java.lang.Throwable -> L87
            int r3 = sun.nio.fs.UnixNativeDispatcher.dup(r9)     // Catch: sun.nio.fs.UnixException -> L49 java.lang.Throwable -> L87
            long r8 = sun.nio.fs.UnixNativeDispatcher.fdopendir(r9)     // Catch: sun.nio.fs.UnixException -> L47 java.lang.Throwable -> L87
            goto L65
        L47:
            r0 = move-exception
            goto L4e
        L49:
            r0 = move-exception
            goto L4d
        L4b:
            r0 = move-exception
            r9 = -1
        L4d:
            r3 = -1
        L4e:
            if (r9 == r1) goto L53
            sun.nio.fs.UnixNativeDispatcher.close(r9)     // Catch: java.lang.Throwable -> L87
        L53:
            if (r3 == r1) goto L58
            sun.nio.fs.UnixNativeDispatcher.close(r3)     // Catch: java.lang.Throwable -> L87
        L58:
            int r9 = r0.errno()     // Catch: java.lang.Throwable -> L87
            r1 = 20
            if (r9 == r1) goto L77
            r0.rethrowAsIOException(r8)     // Catch: java.lang.Throwable -> L87
            r8 = 0
        L65:
            r5 = r3
            r3 = r8
            sun.nio.fs.UnixSecureDirectoryStream r1 = new sun.nio.fs.UnixSecureDirectoryStream     // Catch: java.lang.Throwable -> L87
            r6 = 0
            r1.<init>(r2, r3, r5, r6)     // Catch: java.lang.Throwable -> L87
            sun.nio.fs.UnixDirectoryStream r8 = r7.ds
            java.util.concurrent.locks.Lock r8 = r8.readLock()
            r8.unlock()
            return r1
        L77:
            java.nio.file.NotDirectoryException r9 = new java.nio.file.NotDirectoryException     // Catch: java.lang.Throwable -> L87
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L87
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L87
            throw r9     // Catch: java.lang.Throwable -> L87
        L81:
            java.nio.file.ClosedDirectoryStreamException r8 = new java.nio.file.ClosedDirectoryStreamException     // Catch: java.lang.Throwable -> L87
            r8.<init>()     // Catch: java.lang.Throwable -> L87
            throw r8     // Catch: java.lang.Throwable -> L87
        L87:
            r0 = move-exception
            r8 = r0
            sun.nio.fs.UnixDirectoryStream r9 = r7.ds
            java.util.concurrent.locks.Lock r9 = r9.readLock()
            r9.unlock()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixSecureDirectoryStream.newDirectoryStream(java.nio.file.Path, java.nio.file.LinkOption[]):java.nio.file.SecureDirectoryStream");
    }

    public SeekableByteChannel newByteChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        UnixPath name = getName(path);
        int unixMode = UnixFileModeAttribute.toUnixMode(438, fileAttributeArr);
        String pathForPermissionCheck = this.ds.directory().resolve((Path) name).getPathForPermissionCheck();
        this.ds.readLock().lock();
        try {
            if (!this.ds.isOpen()) {
                throw new ClosedDirectoryStreamException();
            }
            try {
                return UnixChannelFactory.newFileChannel(this.dfd, name, pathForPermissionCheck, set, unixMode);
            } catch (UnixException e) {
                e.rethrowAsIOException(name);
                this.ds.readLock().unlock();
                return null;
            }
        } finally {
            this.ds.readLock().unlock();
        }
    }

    private void implDelete(Path path, boolean z, int i) throws IOException {
        UnixFileAttributes unixFileAttributes;
        UnixPath name = getName(path);
        if (System.getSecurityManager() != null) {
            this.ds.directory().resolve((Path) name).checkDelete();
        }
        this.ds.readLock().lock();
        try {
            if (!this.ds.isOpen()) {
                throw new ClosedDirectoryStreamException();
            }
            if (!z) {
                try {
                    unixFileAttributes = UnixFileAttributes.get(this.dfd, name, false);
                } catch (UnixException e) {
                    e.rethrowAsIOException(name);
                    unixFileAttributes = null;
                }
                i = unixFileAttributes.isDirectory() ? 512 : 0;
            }
            try {
                UnixNativeDispatcher.unlinkat(this.dfd, name.asByteArray(), i);
            } catch (UnixException e2) {
                if ((i & 512) != 0 && (e2.errno() == 17 || e2.errno() == 39)) {
                    throw new DirectoryNotEmptyException(null);
                }
                e2.rethrowAsIOException(name);
            }
        } finally {
            this.ds.readLock().unlock();
        }
    }

    public void deleteFile(Path path) throws IOException {
        implDelete(path, true, 0);
    }

    public void deleteDirectory(Path path) throws IOException {
        implDelete(path, true, 512);
    }

    public void move(Path path, SecureDirectoryStream secureDirectoryStream, Path path2) throws IOException {
        UnixPath name = getName(path);
        UnixPath name2 = getName(path2);
        secureDirectoryStream.getClass();
        if (!(secureDirectoryStream instanceof UnixSecureDirectoryStream)) {
            throw new ProviderMismatchException();
        }
        UnixSecureDirectoryStream unixSecureDirectoryStream = (UnixSecureDirectoryStream) secureDirectoryStream;
        if (System.getSecurityManager() != null) {
            this.ds.directory().resolve((Path) name).checkWrite();
            unixSecureDirectoryStream.ds.directory().resolve((Path) name2).checkWrite();
        }
        this.ds.readLock().lock();
        try {
            unixSecureDirectoryStream.ds.readLock().lock();
            try {
                if (!this.ds.isOpen() || !unixSecureDirectoryStream.ds.isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                try {
                    UnixNativeDispatcher.renameat(this.dfd, name.asByteArray(), unixSecureDirectoryStream.dfd, name2.asByteArray());
                } catch (UnixException e) {
                    if (e.errno() == 18) {
                        throw new AtomicMoveNotSupportedException(name.toString(), name2.toString(), e.errorString());
                    }
                    e.rethrowAsIOException(name, name2);
                }
            } finally {
                unixSecureDirectoryStream.ds.readLock().unlock();
            }
        } finally {
            this.ds.readLock().unlock();
        }
    }

    private FileAttributeView getFileAttributeViewImpl(UnixPath unixPath, Class cls, boolean z) {
        cls.getClass();
        if (cls == BasicFileAttributeView.class) {
            return new BasicFileAttributeViewImpl(unixPath, z);
        }
        if (cls == PosixFileAttributeView.class || cls == FileOwnerAttributeView.class) {
            return new PosixFileAttributeViewImpl(unixPath, z);
        }
        return null;
    }

    public FileAttributeView getFileAttributeView(Class cls) {
        return getFileAttributeViewImpl(null, cls, false);
    }

    public FileAttributeView getFileAttributeView(Path path, Class cls, LinkOption... linkOptionArr) {
        return getFileAttributeViewImpl(getName(path), cls, Util.followLinks(linkOptionArr));
    }

    private class BasicFileAttributeViewImpl implements BasicFileAttributeView {
        final UnixPath file;
        final boolean followLinks;

        static /* bridge */ /* synthetic */ void -$$Nest$mcheckWriteAccess(BasicFileAttributeViewImpl basicFileAttributeViewImpl) {
            basicFileAttributeViewImpl.checkWriteAccess();
        }

        BasicFileAttributeViewImpl(UnixPath unixPath, boolean z) {
            this.file = unixPath;
            this.followLinks = z;
        }

        int open() throws IOException {
            try {
                return UnixNativeDispatcher.openat(UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this), this.file.asByteArray(), !this.followLinks ? 131072 : 0, 0);
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
                return -1;
            }
        }

        private void checkWriteAccess() {
            if (System.getSecurityManager() != null) {
                if (this.file == null) {
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().checkWrite();
                } else {
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().resolve((Path) this.file).checkWrite();
                }
            }
        }

        public String name() {
            return "basic";
        }

        public BasicFileAttributes readAttributes() throws IOException {
            UnixFileAttributes unixFileAttributes;
            UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().lock();
            try {
                if (!UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                if (System.getSecurityManager() != null) {
                    if (this.file == null) {
                        UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().checkRead();
                    } else {
                        UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().resolve((Path) this.file).checkRead();
                    }
                }
                try {
                    if (this.file == null) {
                        unixFileAttributes = UnixFileAttributes.get(UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this));
                    } else {
                        unixFileAttributes = UnixFileAttributes.get(UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this), this.file, this.followLinks);
                    }
                    return unixFileAttributes.asBasicFileAttributes();
                } catch (UnixException e) {
                    e.rethrowAsIOException(this.file);
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
                    return null;
                }
            } finally {
                UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }

        public void setTimes(FileTime fileTime, FileTime fileTime2, FileTime fileTime3) throws IOException {
            checkWriteAccess();
            UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().lock();
            try {
                if (!UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                int i = this.file == null ? UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this) : open();
                if (fileTime == null || fileTime2 == null) {
                    try {
                        try {
                            UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(i);
                            if (fileTime == null) {
                                fileTime = unixFileAttributes.lastModifiedTime();
                            }
                            if (fileTime2 == null) {
                                fileTime2 = unixFileAttributes.lastAccessTime();
                            }
                        } finally {
                            if (this.file != null) {
                                UnixNativeDispatcher.close(i);
                            }
                        }
                    } catch (UnixException e) {
                        e.rethrowAsIOException(this.file);
                    }
                }
                try {
                    UnixNativeDispatcher.futimes(i, fileTime2.to(TimeUnit.MICROSECONDS), fileTime.to(TimeUnit.MICROSECONDS));
                } catch (UnixException e2) {
                    e2.rethrowAsIOException(this.file);
                }
            } finally {
                UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }
    }

    private class PosixFileAttributeViewImpl extends BasicFileAttributeViewImpl implements PosixFileAttributeView {
        PosixFileAttributeViewImpl(UnixPath unixPath, boolean z) {
            super(unixPath, z);
        }

        private void checkWriteAndUserAccess() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                BasicFileAttributeViewImpl.-$$Nest$mcheckWriteAccess(this);
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        public String name() {
            return "posix";
        }

        public PosixFileAttributes readAttributes() throws IOException {
            UnixFileAttributes unixFileAttributes;
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                if (this.file == null) {
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().checkRead();
                } else {
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).directory().resolve((Path) this.file).checkRead();
                }
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
            UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().lock();
            try {
                if (!UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                try {
                    if (this.file == null) {
                        unixFileAttributes = UnixFileAttributes.get(UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this));
                    } else {
                        unixFileAttributes = UnixFileAttributes.get(UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this), this.file, this.followLinks);
                    }
                    return unixFileAttributes;
                } catch (UnixException e) {
                    e.rethrowAsIOException(this.file);
                    UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
                    return null;
                }
            } finally {
                UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }

        public void setPermissions(Set set) throws IOException {
            checkWriteAndUserAccess();
            UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().lock();
            try {
                if (!UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                int i = this.file == null ? UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this) : open();
                try {
                    try {
                        UnixNativeDispatcher.fchmod(i, UnixFileModeAttribute.toUnixMode(set));
                    } finally {
                        if (this.file != null && i >= 0) {
                            UnixNativeDispatcher.close(i);
                        }
                    }
                } catch (UnixException e) {
                    e.rethrowAsIOException(this.file);
                    if (this.file != null && i >= 0) {
                    }
                }
            } finally {
                UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }

        private void setOwners(int i, int i2) throws IOException {
            checkWriteAndUserAccess();
            UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().lock();
            try {
                if (!UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).isOpen()) {
                    throw new ClosedDirectoryStreamException();
                }
                int i3 = this.file == null ? UnixSecureDirectoryStream.-$$Nest$fgetdfd(UnixSecureDirectoryStream.this) : open();
                try {
                    try {
                        UnixNativeDispatcher.fchown(i3, i, i2);
                    } finally {
                        if (this.file != null && i3 >= 0) {
                            UnixNativeDispatcher.close(i3);
                        }
                    }
                } catch (UnixException e) {
                    e.rethrowAsIOException(this.file);
                    if (this.file != null && i3 >= 0) {
                    }
                }
            } finally {
                UnixSecureDirectoryStream.-$$Nest$fgetds(UnixSecureDirectoryStream.this).readLock().unlock();
            }
        }

        public UserPrincipal getOwner() throws IOException {
            return readAttributes().owner();
        }

        public void setOwner(UserPrincipal userPrincipal) throws IOException {
            if (!(userPrincipal instanceof UnixUserPrincipals.User)) {
                throw new ProviderMismatchException();
            }
            if (userPrincipal instanceof UnixUserPrincipals.Group) {
                throw new IOException("'owner' parameter can't be a group");
            }
            setOwners(((UnixUserPrincipals.User) userPrincipal).uid(), -1);
        }

        public void setGroup(GroupPrincipal groupPrincipal) throws IOException {
            if (!(groupPrincipal instanceof UnixUserPrincipals.Group)) {
                throw new ProviderMismatchException();
            }
            setOwners(-1, ((UnixUserPrincipals.Group) groupPrincipal).gid());
        }
    }
}
