package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.SelectableChannel;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class FileDispatcher extends NativeDispatcher {
    public static final int INTERRUPTED = 2;
    public static final int LOCKED = 0;
    public static final int NO_LOCK = -1;
    public static final int RET_EX_LOCK = 1;

    abstract boolean canTransferToDirectly(SelectableChannel selectableChannel);

    abstract FileDescriptor duplicateForMapping(FileDescriptor fileDescriptor) throws IOException;

    abstract int force(FileDescriptor fileDescriptor, boolean z) throws IOException;

    abstract int lock(FileDescriptor fileDescriptor, boolean z, long j, long j2, boolean z2) throws IOException;

    abstract void release(FileDescriptor fileDescriptor, long j, long j2) throws IOException;

    abstract long seek(FileDescriptor fileDescriptor, long j) throws IOException;

    abstract int setDirectIO(FileDescriptor fileDescriptor, String str);

    abstract long size(FileDescriptor fileDescriptor) throws IOException;

    abstract boolean transferToDirectlyNeedsPositionLock();

    abstract int truncate(FileDescriptor fileDescriptor, long j) throws IOException;

    FileDispatcher() {
    }
}
