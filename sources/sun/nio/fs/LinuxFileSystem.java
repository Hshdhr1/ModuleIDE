package sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxFileSystem extends UnixFileSystem {
    LinuxFileSystem(UnixFileSystemProvider unixFileSystemProvider, String str) {
        super(unixFileSystemProvider, str);
    }

    public WatchService newWatchService() throws IOException {
        return new LinuxWatchService(this);
    }

    private static class SupportedFileFileAttributeViewsHolder {
        static final Set supportedFileAttributeViews = supportedFileAttributeViews();

        private SupportedFileFileAttributeViewsHolder() {
        }

        private static Set supportedFileAttributeViews() {
            HashSet hashSet = new HashSet();
            hashSet.addAll(UnixFileSystem.standardFileAttributeViews());
            hashSet.add("dos");
            hashSet.add("user");
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public Set supportedFileAttributeViews() {
        return SupportedFileFileAttributeViewsHolder.supportedFileAttributeViews;
    }

    void copyNonPosixAttributes(int i, int i2) {
        LinuxUserDefinedFileAttributeView.copyExtendedAttributes(i, i2);
    }

    List getMountEntries(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            long j = LinuxNativeDispatcher.setmntent(Util.toBytes(str), Util.toBytes("r"));
            int i = 1024;
            while (true) {
                try {
                    int i2 = LinuxNativeDispatcher.getlinelen(j);
                    if (i2 == -1) {
                        break;
                    }
                    if (i2 > i) {
                        i = i2;
                    }
                } catch (UnixException unused) {
                } catch (Throwable th) {
                    LinuxNativeDispatcher.rewind(j);
                    throw th;
                }
            }
            LinuxNativeDispatcher.rewind(j);
            while (true) {
                try {
                    UnixMountEntry unixMountEntry = new UnixMountEntry();
                    if (LinuxNativeDispatcher.getmntent(j, unixMountEntry, i + 1) < 0) {
                        break;
                    }
                    arrayList.add(unixMountEntry);
                } finally {
                    LinuxNativeDispatcher.endmntent(j);
                }
            }
        } catch (UnixException unused2) {
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List getMountEntries() {
        return getMountEntries("/etc/mtab");
    }

    FileStore getFileStore(UnixMountEntry unixMountEntry) throws IOException {
        return new LinuxFileStore(this, unixMountEntry);
    }
}
