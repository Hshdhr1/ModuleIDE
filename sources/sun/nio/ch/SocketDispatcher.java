package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SocketDispatcher extends NativeDispatcher {
    SocketDispatcher() {
    }

    int read(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return FileDispatcherImpl.read0(fileDescriptor, j, i);
    }

    long readv(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return FileDispatcherImpl.readv0(fileDescriptor, j, i);
    }

    int write(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return FileDispatcherImpl.write0(fileDescriptor, j, i);
    }

    long writev(FileDescriptor fileDescriptor, long j, int i) throws IOException {
        return FileDispatcherImpl.writev0(fileDescriptor, j, i);
    }

    void close(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.close0(fileDescriptor);
    }

    void preClose(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.preClose0(fileDescriptor);
    }
}
