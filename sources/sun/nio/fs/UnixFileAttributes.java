package sun.nio.fs;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixFileAttributes implements PosixFileAttributes {
    private volatile GroupPrincipal group;
    private volatile UnixFileKey key;
    private volatile UserPrincipal owner;
    private long st_atime_nsec;
    private long st_atime_sec;
    private long st_birthtime_sec;
    private long st_ctime_nsec;
    private long st_ctime_sec;
    private long st_dev;
    private int st_gid;
    private long st_ino;
    private int st_mode;
    private long st_mtime_nsec;
    private long st_mtime_sec;
    private int st_nlink;
    private long st_rdev;
    private long st_size;
    private int st_uid;

    private UnixFileAttributes() {
    }

    static UnixFileAttributes get(UnixPath unixPath, boolean z) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        if (z) {
            UnixNativeDispatcher.stat(unixPath, unixFileAttributes);
            return unixFileAttributes;
        }
        UnixNativeDispatcher.lstat(unixPath, unixFileAttributes);
        return unixFileAttributes;
    }

    static UnixFileAttributes get(int i) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        UnixNativeDispatcher.fstat(i, unixFileAttributes);
        return unixFileAttributes;
    }

    static UnixFileAttributes get(int i, UnixPath unixPath, boolean z) throws UnixException {
        UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
        UnixNativeDispatcher.fstatat(i, unixPath.asByteArray(), z ? 0 : 256, unixFileAttributes);
        return unixFileAttributes;
    }

    boolean isSameFile(UnixFileAttributes unixFileAttributes) {
        return this.st_ino == unixFileAttributes.st_ino && this.st_dev == unixFileAttributes.st_dev;
    }

    int mode() {
        return this.st_mode;
    }

    long ino() {
        return this.st_ino;
    }

    long dev() {
        return this.st_dev;
    }

    long rdev() {
        return this.st_rdev;
    }

    int nlink() {
        return this.st_nlink;
    }

    int uid() {
        return this.st_uid;
    }

    int gid() {
        return this.st_gid;
    }

    private static FileTime toFileTime(long j, long j2) {
        if (j2 == 0) {
            return FileTime.from(j, TimeUnit.SECONDS);
        }
        return FileTime.from((j * 1000000) + (j2 / 1000), TimeUnit.MICROSECONDS);
    }

    FileTime ctime() {
        return toFileTime(this.st_ctime_sec, this.st_ctime_nsec);
    }

    boolean isDevice() {
        int i = this.st_mode & 61440;
        return i == 8192 || i == 24576 || i == 4096;
    }

    public FileTime lastModifiedTime() {
        return toFileTime(this.st_mtime_sec, this.st_mtime_nsec);
    }

    public FileTime lastAccessTime() {
        return toFileTime(this.st_atime_sec, this.st_atime_nsec);
    }

    public FileTime creationTime() {
        if (UnixNativeDispatcher.birthtimeSupported()) {
            return FileTime.from(this.st_birthtime_sec, TimeUnit.SECONDS);
        }
        return lastModifiedTime();
    }

    public boolean isRegularFile() {
        return (this.st_mode & 61440) == 32768;
    }

    public boolean isDirectory() {
        return (this.st_mode & 61440) == 16384;
    }

    public boolean isSymbolicLink() {
        return (this.st_mode & 61440) == 40960;
    }

    public boolean isOther() {
        int i = this.st_mode & 61440;
        return (i == 32768 || i == 16384 || i == 40960) ? false : true;
    }

    public long size() {
        return this.st_size;
    }

    public UnixFileKey fileKey() {
        if (this.key == null) {
            synchronized (this) {
                if (this.key == null) {
                    this.key = new UnixFileKey(this.st_dev, this.st_ino);
                }
            }
        }
        return this.key;
    }

    public UserPrincipal owner() {
        if (this.owner == null) {
            synchronized (this) {
                if (this.owner == null) {
                    this.owner = UnixUserPrincipals.fromUid(this.st_uid);
                }
            }
        }
        return this.owner;
    }

    public GroupPrincipal group() {
        if (this.group == null) {
            synchronized (this) {
                if (this.group == null) {
                    this.group = UnixUserPrincipals.fromGid(this.st_gid);
                }
            }
        }
        return this.group;
    }

    public Set permissions() {
        int i = this.st_mode;
        HashSet hashSet = new HashSet();
        if ((i & 256) > 0) {
            hashSet.add(PosixFilePermission.OWNER_READ);
        }
        if ((i & 128) > 0) {
            hashSet.add(PosixFilePermission.OWNER_WRITE);
        }
        if ((i & 64) > 0) {
            hashSet.add(PosixFilePermission.OWNER_EXECUTE);
        }
        if ((i & 32) > 0) {
            hashSet.add(PosixFilePermission.GROUP_READ);
        }
        if ((i & 16) > 0) {
            hashSet.add(PosixFilePermission.GROUP_WRITE);
        }
        if ((i & 8) > 0) {
            hashSet.add(PosixFilePermission.GROUP_EXECUTE);
        }
        if ((i & 4) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_READ);
        }
        if ((i & 2) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_WRITE);
        }
        if ((i & 1) > 0) {
            hashSet.add(PosixFilePermission.OTHERS_EXECUTE);
        }
        return hashSet;
    }

    BasicFileAttributes asBasicFileAttributes() {
        return UnixAsBasicFileAttributes.wrap(this);
    }

    static UnixFileAttributes toUnixFileAttributes(BasicFileAttributes basicFileAttributes) {
        if (basicFileAttributes instanceof UnixFileAttributes) {
            return (UnixFileAttributes) basicFileAttributes;
        }
        if (basicFileAttributes instanceof UnixAsBasicFileAttributes) {
            return ((UnixAsBasicFileAttributes) basicFileAttributes).unwrap();
        }
        return null;
    }

    private static class UnixAsBasicFileAttributes implements BasicFileAttributes {
        private final UnixFileAttributes attrs;

        private UnixAsBasicFileAttributes(UnixFileAttributes unixFileAttributes) {
            this.attrs = unixFileAttributes;
        }

        static UnixAsBasicFileAttributes wrap(UnixFileAttributes unixFileAttributes) {
            return new UnixAsBasicFileAttributes(unixFileAttributes);
        }

        UnixFileAttributes unwrap() {
            return this.attrs;
        }

        public FileTime lastModifiedTime() {
            return this.attrs.lastModifiedTime();
        }

        public FileTime lastAccessTime() {
            return this.attrs.lastAccessTime();
        }

        public FileTime creationTime() {
            return this.attrs.creationTime();
        }

        public boolean isRegularFile() {
            return this.attrs.isRegularFile();
        }

        public boolean isDirectory() {
            return this.attrs.isDirectory();
        }

        public boolean isSymbolicLink() {
            return this.attrs.isSymbolicLink();
        }

        public boolean isOther() {
            return this.attrs.isOther();
        }

        public long size() {
            return this.attrs.size();
        }

        public Object fileKey() {
            return this.attrs.fileKey();
        }
    }
}
