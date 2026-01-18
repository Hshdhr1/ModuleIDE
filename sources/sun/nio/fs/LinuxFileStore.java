package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import sun.nio.fs.UnixFileStore;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxFileStore extends UnixFileStore {
    private volatile boolean xattrChecked;
    private volatile boolean xattrEnabled;

    LinuxFileStore(UnixPath unixPath) throws IOException {
        super(unixPath);
    }

    LinuxFileStore(UnixFileSystem unixFileSystem, UnixMountEntry unixMountEntry) throws IOException {
        super(unixFileSystem, unixMountEntry);
    }

    UnixMountEntry findMountEntry() throws IOException {
        UnixPath unixPath;
        UnixFileAttributes unixFileAttributes;
        LinuxFileSystem linuxFileSystem = (LinuxFileSystem) file().getFileSystem();
        try {
            unixPath = new UnixPath(linuxFileSystem, UnixNativeDispatcher.realpath(file()));
        } catch (UnixException e) {
            e.rethrowAsIOException(file());
            unixPath = null;
        }
        List<UnixMountEntry> mountEntries = linuxFileSystem.getMountEntries("/proc/mounts");
        UnixPath parent = unixPath.getParent();
        while (true) {
            UnixPath unixPath2 = unixPath;
            unixPath = parent;
            if (unixPath != null) {
                try {
                    unixFileAttributes = UnixFileAttributes.get(unixPath, true);
                } catch (UnixException e2) {
                    e2.rethrowAsIOException(unixPath);
                    unixFileAttributes = null;
                }
                if (unixFileAttributes.dev() != dev()) {
                    byte[] asByteArray = unixPath2.asByteArray();
                    for (UnixMountEntry unixMountEntry : mountEntries) {
                        if (Arrays.equals(asByteArray, unixMountEntry.dir())) {
                            return unixMountEntry;
                        }
                    }
                }
                parent = unixPath.getParent();
            } else {
                byte[] asByteArray2 = unixPath2.asByteArray();
                for (UnixMountEntry unixMountEntry2 : mountEntries) {
                    if (Arrays.equals(asByteArray2, unixMountEntry2.dir())) {
                        return unixMountEntry2;
                    }
                }
                throw new IOException("Mount point not found");
            }
        }
    }

    private boolean isExtendedAttributesEnabled(UnixPath unixPath) {
        int i = -1;
        try {
            i = unixPath.openForAttributeAccess(false);
            LinuxNativeDispatcher.fgetxattr(i, Util.toBytes("user.java"), 0L, 0);
            return true;
        } catch (UnixException e) {
            return e.errno() == 61;
        } finally {
            UnixNativeDispatcher.close(i);
        }
    }

    private static int[] getKernelVersion() {
        String[] split = Pattern.compile("\\D+").split(System.getProperty("os.version"));
        int[] iArr = new int[3];
        int min = Math.min(split.length, 3);
        for (int i = 0; i < min; i++) {
            iArr[i] = Integer.valueOf(split[i]).intValue();
        }
        return iArr;
    }

    public boolean supportsFileAttributeView(Class cls) {
        boolean z = false;
        if (cls == DosFileAttributeView.class || cls == UserDefinedFileAttributeView.class) {
            UnixFileStore.FeatureStatus checkIfFeaturePresent = checkIfFeaturePresent("user_xattr");
            if (checkIfFeaturePresent == UnixFileStore.FeatureStatus.PRESENT) {
                return true;
            }
            if (checkIfFeaturePresent == UnixFileStore.FeatureStatus.NOT_PRESENT) {
                return false;
            }
            if (entry().hasOption("user_xattr")) {
                return true;
            }
            if (entry().hasOption("nouser_xattr") || entry().fstype().equals("ext3")) {
                return false;
            }
            if (entry().fstype().equals("ext4")) {
                if (!this.xattrChecked) {
                    int[] kernelVersion = getKernelVersion();
                    int i = kernelVersion[0];
                    if (i > 2 || ((i == 2 && kernelVersion[1] > 6) || (i == 2 && kernelVersion[1] == 6 && kernelVersion[2] >= 39))) {
                        z = true;
                    }
                    this.xattrEnabled = z;
                    this.xattrChecked = true;
                }
                return this.xattrEnabled;
            }
            if (!this.xattrChecked) {
                this.xattrEnabled = isExtendedAttributesEnabled(new UnixPath(file().getFileSystem(), entry().dir()));
                this.xattrChecked = true;
            }
            return this.xattrEnabled;
        }
        if (cls == PosixFileAttributeView.class && entry().fstype().equals("vfat")) {
            return false;
        }
        return super.supportsFileAttributeView(cls);
    }

    public boolean supportsFileAttributeView(String str) {
        if (str.equals("dos")) {
            return supportsFileAttributeView(DosFileAttributeView.class);
        }
        if (str.equals("user")) {
            return supportsFileAttributeView(UserDefinedFileAttributeView.class);
        }
        return super.supportsFileAttributeView(str);
    }
}
