package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DatagramDispatcher extends NativeDispatcher {
    static native int read0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native long readv0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native int write0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    static native long writev0(FileDescriptor fileDescriptor, long j, int i) throws IOException;

    DatagramDispatcher() {
    }

    static {
        IOUtil.load();
    }

    int read(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return read0(fileDescriptor, j, i);
    }

    long readv(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return readv0(fileDescriptor, j, i);
    }

    int write(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return write0(fileDescriptor, j, i);
    }

    long writev(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return writev0(fileDescriptor, j, i);
    }

    void close(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.close0(fileDescriptor);
    }

    void preClose(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.preClose0(fileDescriptor);
    }
}
