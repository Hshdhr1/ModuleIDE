package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import jdk.internal.misc.JavaIOFileDescriptorAccess;
import jdk.internal.misc.SharedSecrets;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class FileDispatcherImpl extends FileDispatcher {
    private static final JavaIOFileDescriptorAccess fdAccess;

    static native void close0(FileDescriptor fileDescriptor) throws IOException;

    static native void closeIntFD(int i) throws IOException;

    static native int force0(FileDescriptor fileDescriptor, boolean z) throws IOException;

    static native void init();

    static native int lock0(FileDescriptor fileDescriptor, boolean z, long j, long j2, boolean z2) throws IOException;

    static native void preClose0(FileDescriptor fileDescriptor) throws IOException;

    static native int pread0(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException;

    static native int pwrite0(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException;

    static native int read0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native long readv0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native void release0(FileDescriptor fileDescriptor, long j, long j2) throws IOException;

    static native long seek0(FileDescriptor fileDescriptor, long j) throws IOException;

    static native int setDirect0(FileDescriptor fileDescriptor) throws IOException;

    static native long size0(FileDescriptor fileDescriptor) throws IOException;

    static native int truncate0(FileDescriptor fileDescriptor, long j) throws IOException;

    static native int write0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native long writev0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    boolean canTransferToDirectly(SelectableChannel selectableChannel) {
        return true;
    }

    boolean transferToDirectlyNeedsPositionLock() {
        return false;
    }

    static {
        IOUtil.load();
        init();
        fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
    }

    FileDispatcherImpl() {
    }

    int read(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return read0(fileDescriptor, j, i);
    }

    int pread(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException {
        return pread0(fileDescriptor, j, i, j2);
    }

    long readv(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return readv0(fileDescriptor, j, i);
    }

    int write(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return write0(fileDescriptor, j, i);
    }

    int pwrite(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException {
        return pwrite0(fileDescriptor, j, i, j2);
    }

    long writev(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return writev0(fileDescriptor, j, i);
    }

    long seek(FileDescriptor fileDescriptor, long j) throws IOException {
        return seek0(fileDescriptor, j);
    }

    int force(FileDescriptor fileDescriptor, boolean z) throws IOException {
        return force0(fileDescriptor, z);
    }

    int truncate(FileDescriptor fileDescriptor, long j) throws IOException {
        return truncate0(fileDescriptor, j);
    }

    long size(FileDescriptor fileDescriptor) throws IOException {
        return size0(fileDescriptor);
    }

    int lock(FileDescriptor fileDescriptor, boolean z, long j, long j2, boolean z2) throws IOException {
        return lock0(fileDescriptor, z, j, j2, z2);
    }

    void release(FileDescriptor fileDescriptor, long j, long j2) throws IOException {
        release0(fileDescriptor, j, j2);
    }

    void close(FileDescriptor fileDescriptor) throws IOException {
        fdAccess.close(fileDescriptor);
    }

    void preClose(FileDescriptor fileDescriptor) throws IOException {
        preClose0(fileDescriptor);
    }

    FileDescriptor duplicateForMapping(FileDescriptor fileDescriptor) {
        return new FileDescriptor();
    }

    int setDirectIO(FileDescriptor fileDescriptor, String str) {
        try {
            return setDirect0(fileDescriptor);
        } catch (IOException e) {
            throw new UnsupportedOperationException("Error setting up DirectIO", e);
        }
    }
}
