package sun.nio.fs;

import java.io.FileDescriptor;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.misc.JavaIOFileDescriptorAccess;
import jdk.internal.misc.SharedSecrets;
import sun.nio.ch.FileChannelImpl;
import sun.nio.ch.SimpleAsynchronousFileChannelImpl;
import sun.nio.ch.ThreadPool;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixChannelFactory {
    private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();

    protected UnixChannelFactory() {
    }

    protected static class Flags {
        boolean append;
        boolean create;
        boolean createNew;
        boolean deleteOnClose;
        boolean direct;
        boolean dsync;
        boolean noFollowLinks;
        boolean read;
        boolean sync;
        boolean truncateExisting;
        boolean write;

        protected Flags() {
        }

        static Flags toFlags(Set set) {
            Flags flags = new Flags();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                OpenOption openOption = (OpenOption) it.next();
                if (openOption instanceof StandardOpenOption) {
                    switch (1.$SwitchMap$java$nio$file$StandardOpenOption[((StandardOpenOption) openOption).ordinal()]) {
                        case 1:
                            flags.read = true;
                            break;
                        case 2:
                            flags.write = true;
                            break;
                        case 3:
                            flags.append = true;
                            break;
                        case 4:
                            flags.truncateExisting = true;
                            break;
                        case 5:
                            flags.create = true;
                            break;
                        case 6:
                            flags.createNew = true;
                            break;
                        case 7:
                            flags.deleteOnClose = true;
                            break;
                        case 8:
                            break;
                        case 9:
                            flags.sync = true;
                            break;
                        case 10:
                            flags.dsync = true;
                            break;
                        default:
                            throw new UnsupportedOperationException();
                    }
                } else if (openOption == LinkOption.NOFOLLOW_LINKS) {
                    flags.noFollowLinks = true;
                } else if (ExtendedOptions.DIRECT.matches(openOption)) {
                    flags.direct = true;
                } else {
                    openOption.getClass();
                    throw new UnsupportedOperationException(openOption + " not supported");
                }
            }
            return flags;
        }
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$StandardOpenOption;

        static {
            int[] iArr = new int[StandardOpenOption.values().length];
            $SwitchMap$java$nio$file$StandardOpenOption = iArr;
            try {
                iArr[StandardOpenOption.READ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.APPEND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.TRUNCATE_EXISTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.CREATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.CREATE_NEW.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.DELETE_ON_CLOSE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.SPARSE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.SYNC.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$nio$file$StandardOpenOption[StandardOpenOption.DSYNC.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    static FileChannel newFileChannel(int i, String str, boolean z, boolean z2) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fdAccess.set(fileDescriptor, i);
        return FileChannelImpl.open(fileDescriptor, str, z, z2, false, null);
    }

    static FileChannel newFileChannel(int i, UnixPath unixPath, String str, Set set, int i2) throws UnixException {
        Flags flags = Flags.toFlags(set);
        if (!flags.read && !flags.write) {
            if (flags.append) {
                flags.write = true;
            } else {
                flags.read = true;
            }
        }
        if (flags.read && flags.append) {
            throw new IllegalArgumentException("READ + APPEND not allowed");
        }
        if (flags.append && flags.truncateExisting) {
            throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
        }
        return FileChannelImpl.open(open(i, unixPath, str, flags, i2), unixPath.toString(), flags.read, flags.write, flags.direct, null);
    }

    static FileChannel newFileChannel(UnixPath unixPath, Set set, int i) throws UnixException {
        return newFileChannel(-1, unixPath, null, set, i);
    }

    static AsynchronousFileChannel newAsynchronousFileChannel(UnixPath unixPath, Set set, int i, ThreadPool threadPool) throws UnixException {
        Flags flags = Flags.toFlags(set);
        if (!flags.read && !flags.write) {
            flags.read = true;
        }
        if (flags.append) {
            throw new UnsupportedOperationException("APPEND not allowed");
        }
        return SimpleAsynchronousFileChannelImpl.open(open(-1, unixPath, null, flags, i), flags.read, flags.write, threadPool);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0058  */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static java.io.FileDescriptor open(int r7, sun.nio.fs.UnixPath r8, java.lang.String r9, sun.nio.fs.UnixChannelFactory.Flags r10, int r11) throws sun.nio.fs.UnixException {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixChannelFactory.open(int, sun.nio.fs.UnixPath, java.lang.String, sun.nio.fs.UnixChannelFactory$Flags, int):java.io.FileDescriptor");
    }
}
