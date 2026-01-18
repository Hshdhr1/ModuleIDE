package sun.nio.fs;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxWatchService extends AbstractWatchService {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final Poller poller;

    static /* bridge */ /* synthetic */ Poller -$$Nest$fgetpoller(LinuxWatchService linuxWatchService) {
        return linuxWatchService.poller;
    }

    static /* bridge */ /* synthetic */ Unsafe -$$Nest$sfgetunsafe() {
        return unsafe;
    }

    static /* bridge */ /* synthetic */ int[] -$$Nest$smeventOffsets() {
        return eventOffsets();
    }

    static /* bridge */ /* synthetic */ int -$$Nest$smeventSize() {
        return eventSize();
    }

    static /* bridge */ /* synthetic */ int -$$Nest$sminotifyAddWatch(int i, long j, int i2) {
        return inotifyAddWatch(i, j, i2);
    }

    static /* bridge */ /* synthetic */ void -$$Nest$sminotifyRmWatch(int i, int i2) {
        inotifyRmWatch(i, i2);
    }

    static /* bridge */ /* synthetic */ int -$$Nest$smpoll(int i, int i2) {
        return poll(i, i2);
    }

    private static native void configureBlocking(int i, boolean z) throws UnixException;

    private static native int[] eventOffsets();

    private static native int eventSize();

    private static native int inotifyAddWatch(int i, long j, int i2) throws UnixException;

    private static native int inotifyInit() throws UnixException;

    private static native void inotifyRmWatch(int i, int i2) throws UnixException;

    private static native int poll(int i, int i2) throws UnixException;

    private static native void socketpair(int[] iArr) throws UnixException;

    static {
        AccessController.doPrivileged(new 1());
    }

    LinuxWatchService(UnixFileSystem unixFileSystem) throws IOException {
        String errorString;
        try {
            int inotifyInit = inotifyInit();
            int[] iArr = new int[2];
            try {
                configureBlocking(inotifyInit, false);
                socketpair(iArr);
                configureBlocking(iArr[0], false);
                Poller poller = new Poller(unixFileSystem, this, inotifyInit, iArr);
                this.poller = poller;
                poller.start();
            } catch (UnixException e) {
                UnixNativeDispatcher.close(inotifyInit);
                throw new IOException(e.errorString());
            }
        } catch (UnixException e2) {
            if (e2.errno() == 24) {
                errorString = "User limit of inotify instances reached or too many open files";
            } else {
                errorString = e2.errorString();
            }
            throw new IOException(errorString);
        }
    }

    WatchKey register(Path path, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException {
        return this.poller.register(path, kindArr, modifierArr);
    }

    void implClose() throws IOException {
        this.poller.close();
    }

    private static class LinuxWatchKey extends AbstractWatchKey {
        private final int ifd;
        private volatile int wd;

        LinuxWatchKey(UnixPath unixPath, LinuxWatchService linuxWatchService, int i, int i2) {
            super(unixPath, linuxWatchService);
            this.ifd = i;
            this.wd = i2;
        }

        int descriptor() {
            return this.wd;
        }

        void invalidate(boolean z) {
            if (z) {
                try {
                    LinuxWatchService.-$$Nest$sminotifyRmWatch(this.ifd, this.wd);
                } catch (UnixException unused) {
                }
            }
            this.wd = -1;
        }

        public boolean isValid() {
            return this.wd != -1;
        }

        public void cancel() {
            if (isValid()) {
                LinuxWatchService.-$$Nest$fgetpoller((LinuxWatchService) watcher()).cancel(this);
            }
        }
    }

    private static class Poller extends AbstractPoller {
        private static final int BUFFER_SIZE = 8192;
        private static final int IN_ATTRIB = 4;
        private static final int IN_CREATE = 256;
        private static final int IN_DELETE = 512;
        private static final int IN_IGNORED = 32768;
        private static final int IN_MODIFY = 2;
        private static final int IN_MOVED_FROM = 64;
        private static final int IN_MOVED_TO = 128;
        private static final int IN_Q_OVERFLOW = 16384;
        private static final int IN_UNMOUNT = 8192;
        private static final int OFFSETOF_LEN;
        private static final int OFFSETOF_MASK;
        private static final int OFFSETOF_NAME;
        private static final int OFFSETOF_WD;
        private static final int SIZEOF_INOTIFY_EVENT = LinuxWatchService.-$$Nest$smeventSize();
        private static final int[] offsets;
        private final UnixFileSystem fs;
        private final int ifd;
        private final int[] socketpair;
        private final LinuxWatchService watcher;
        private final Map wdToKey = new HashMap();
        private final long address = LinuxWatchService.-$$Nest$sfgetunsafe().allocateMemory(8192);

        static {
            int[] iArr = LinuxWatchService.-$$Nest$smeventOffsets();
            offsets = iArr;
            OFFSETOF_WD = iArr[0];
            OFFSETOF_MASK = iArr[1];
            OFFSETOF_LEN = iArr[3];
            OFFSETOF_NAME = iArr[4];
        }

        Poller(UnixFileSystem unixFileSystem, LinuxWatchService linuxWatchService, int i, int[] iArr) {
            this.fs = unixFileSystem;
            this.watcher = linuxWatchService;
            this.ifd = i;
            this.socketpair = iArr;
        }

        void wakeup() throws IOException {
            try {
                UnixNativeDispatcher.write(this.socketpair[1], this.address, 1);
            } catch (UnixException e) {
                throw new IOException(e.errorString());
            }
        }

        Object implRegister(Path path, Set set, WatchEvent.Modifier... modifierArr) {
            UnixPath unixPath = (UnixPath) path;
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                WatchEvent.Kind kind = (WatchEvent.Kind) it.next();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    i |= 384;
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    i |= 576;
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    i |= 6;
                }
            }
            if (modifierArr.length > 0) {
                for (WatchEvent.Modifier modifier : modifierArr) {
                    if (modifier == null) {
                        return new NullPointerException();
                    }
                    if (!ExtendedOptions.SENSITIVITY_HIGH.matches(modifier) && !ExtendedOptions.SENSITIVITY_MEDIUM.matches(modifier) && !ExtendedOptions.SENSITIVITY_LOW.matches(modifier)) {
                        return new UnsupportedOperationException("Modifier not supported");
                    }
                }
            }
            try {
                if (!UnixFileAttributes.get(unixPath, true).isDirectory()) {
                    return new NotDirectoryException(unixPath.getPathForExceptionMessage());
                }
                try {
                    NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(unixPath.getByteArrayForSysCalls());
                    try {
                        int i2 = LinuxWatchService.-$$Nest$sminotifyAddWatch(this.ifd, asNativeBuffer.address(), i);
                        LinuxWatchKey linuxWatchKey = (LinuxWatchKey) this.wdToKey.get(Integer.valueOf(i2));
                        if (linuxWatchKey != null) {
                            return linuxWatchKey;
                        }
                        LinuxWatchKey linuxWatchKey2 = new LinuxWatchKey(unixPath, this.watcher, this.ifd, i2);
                        this.wdToKey.put(Integer.valueOf(i2), linuxWatchKey2);
                        return linuxWatchKey2;
                    } finally {
                        asNativeBuffer.release();
                    }
                } catch (UnixException e) {
                    if (e.errno() == 28) {
                        return new IOException("User limit of inotify watches reached");
                    }
                    return e.asIOException(unixPath);
                }
            } catch (UnixException e2) {
                return e2.asIOException(unixPath);
            }
        }

        void implCancelKey(WatchKey watchKey) {
            LinuxWatchKey linuxWatchKey = (LinuxWatchKey) watchKey;
            if (linuxWatchKey.isValid()) {
                this.wdToKey.remove(Integer.valueOf(linuxWatchKey.descriptor()));
                linuxWatchKey.invalidate(true);
            }
        }

        void implCloseAll() {
            Iterator it = this.wdToKey.entrySet().iterator();
            while (it.hasNext()) {
                ((LinuxWatchKey) ((Map.Entry) it.next()).getValue()).invalidate(true);
            }
            this.wdToKey.clear();
            LinuxWatchService.-$$Nest$sfgetunsafe().freeMemory(this.address);
            UnixNativeDispatcher.close(this.socketpair[0]);
            UnixNativeDispatcher.close(this.socketpair[1]);
            UnixNativeDispatcher.close(this.ifd);
        }

        public void run() {
            int i;
            int i2;
            UnixPath unixPath;
            while (true) {
                try {
                    int i3 = LinuxWatchService.-$$Nest$smpoll(this.ifd, this.socketpair[0]);
                    try {
                        i = UnixNativeDispatcher.read(this.ifd, this.address, 8192);
                    } catch (UnixException e) {
                        if (e.errno() != 11 && e.errno() != 11) {
                            throw e;
                        }
                        i = 0;
                    }
                    int i4 = 0;
                    while (i4 < i) {
                        long j = this.address + i4;
                        int i5 = LinuxWatchService.-$$Nest$sfgetunsafe().getInt(OFFSETOF_WD + j);
                        int i6 = LinuxWatchService.-$$Nest$sfgetunsafe().getInt(OFFSETOF_MASK + j);
                        int i7 = LinuxWatchService.-$$Nest$sfgetunsafe().getInt(OFFSETOF_LEN + j);
                        if (i7 > 0) {
                            int i8 = i7;
                            while (true) {
                                if (i8 <= 0) {
                                    i2 = i4;
                                    break;
                                }
                                i2 = i4;
                                if (LinuxWatchService.-$$Nest$sfgetunsafe().getByte(((OFFSETOF_NAME + j) + i8) - 1) != 0) {
                                    break;
                                }
                                i8--;
                                i4 = i2;
                            }
                            if (i8 > 0) {
                                byte[] bArr = new byte[i8];
                                LinuxWatchService.-$$Nest$sfgetunsafe().copyMemory((Object) null, j + OFFSETOF_NAME, bArr, Unsafe.ARRAY_BYTE_BASE_OFFSET, i8);
                                unixPath = new UnixPath(this.fs, bArr);
                            }
                            processEvent(i5, i6, unixPath);
                            i4 = i2 + SIZEOF_INOTIFY_EVENT + i7;
                        } else {
                            i2 = i4;
                        }
                        unixPath = null;
                        processEvent(i5, i6, unixPath);
                        i4 = i2 + SIZEOF_INOTIFY_EVENT + i7;
                    }
                    if (i3 > 1 || (i3 == 1 && i == 0)) {
                        try {
                            UnixNativeDispatcher.read(this.socketpair[0], this.address, 8192);
                            if (processRequests()) {
                                return;
                            }
                        } catch (UnixException e2) {
                            if (e2.errno() == 11) {
                                continue;
                            } else if (e2.errno() != 11) {
                                throw e2;
                            }
                        }
                    }
                } catch (UnixException e3) {
                    e3.printStackTrace();
                    return;
                }
            }
        }

        private WatchEvent.Kind maskToEventKind(int i) {
            if ((i & 2) > 0) {
                return StandardWatchEventKinds.ENTRY_MODIFY;
            }
            if ((i & 4) > 0) {
                return StandardWatchEventKinds.ENTRY_MODIFY;
            }
            if ((i & 256) > 0) {
                return StandardWatchEventKinds.ENTRY_CREATE;
            }
            if ((i & 128) > 0) {
                return StandardWatchEventKinds.ENTRY_CREATE;
            }
            if ((i & 512) > 0) {
                return StandardWatchEventKinds.ENTRY_DELETE;
            }
            if ((i & 64) > 0) {
                return StandardWatchEventKinds.ENTRY_DELETE;
            }
            return null;
        }

        private void processEvent(int i, int i2, UnixPath unixPath) {
            WatchEvent.Kind maskToEventKind;
            if ((i2 & 16384) > 0) {
                Iterator it = this.wdToKey.entrySet().iterator();
                while (it.hasNext()) {
                    ((LinuxWatchKey) ((Map.Entry) it.next()).getValue()).signalEvent(StandardWatchEventKinds.OVERFLOW, null);
                }
                return;
            }
            LinuxWatchKey linuxWatchKey = (LinuxWatchKey) this.wdToKey.get(Integer.valueOf(i));
            if (linuxWatchKey == null) {
                return;
            }
            if ((32768 & i2) > 0) {
                this.wdToKey.remove(Integer.valueOf(i));
                linuxWatchKey.invalidate(false);
                linuxWatchKey.signal();
            } else {
                if (unixPath == null || (maskToEventKind = maskToEventKind(i2)) == null) {
                    return;
                }
                linuxWatchKey.signalEvent(maskToEventKind, unixPath);
            }
        }
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public Void run() {
            System.loadLibrary("nio");
            return null;
        }
    }
}
