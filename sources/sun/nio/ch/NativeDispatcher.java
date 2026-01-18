package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class NativeDispatcher {
    abstract void close(FileDescriptor fileDescriptor) throws IOException;

    boolean needsPositionLock() {
        return false;
    }

    void preClose(FileDescriptor fileDescriptor) throws IOException {
    }

    abstract int read(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    abstract long readv(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    abstract int write(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    abstract long writev(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    NativeDispatcher() {
    }

    int pread(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException {
        throw new IOException("Operation Unsupported");
    }

    int pwrite(FileDescriptor fileDescriptor, long j, int i, long j2) throws IOException {
        throw new IOException("Operation Unsupported");
    }
}
