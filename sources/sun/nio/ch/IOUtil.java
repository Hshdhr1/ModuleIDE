package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class IOUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IOV_MAX;

    public static native void configureBlocking(FileDescriptor fileDescriptor, boolean z) throws IOException;

    static native boolean drain(int i) throws IOException;

    static native int drain1(int i) throws IOException;

    static native int fdLimit();

    public static native int fdVal(FileDescriptor fileDescriptor);

    static native void initIDs();

    static native int iovMax();

    public static void load() {
    }

    static native long makePipe(boolean z) throws IOException;

    static native boolean randomBytes(byte[] bArr);

    static native void setfdVal(FileDescriptor fileDescriptor, int i);

    static native int write1(int i, byte b) throws IOException;

    private IOUtil() {
    }

    static int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, NativeDispatcher nativeDispatcher) throws IOException {
        return write(fileDescriptor, byteBuffer, j, false, -1, nativeDispatcher);
    }

    static int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, boolean z, int i, NativeDispatcher nativeDispatcher) throws IOException {
        ByteBuffer temporaryDirectBuffer;
        if (byteBuffer instanceof DirectBuffer) {
            return writeFromNativeBuffer(fileDescriptor, byteBuffer, j, z, i, nativeDispatcher);
        }
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i2 = position <= limit ? limit - position : 0;
        if (z) {
            Util.checkRemainingBufferSizeAligned(i2, i);
            temporaryDirectBuffer = Util.getTemporaryAlignedDirectBuffer(i2, i);
        } else {
            temporaryDirectBuffer = Util.getTemporaryDirectBuffer(i2);
        }
        try {
            temporaryDirectBuffer.put(byteBuffer);
            temporaryDirectBuffer.flip();
            byteBuffer.position(position);
            int writeFromNativeBuffer = writeFromNativeBuffer(fileDescriptor, temporaryDirectBuffer, j, z, i, nativeDispatcher);
            if (writeFromNativeBuffer > 0) {
                byteBuffer.position(position + writeFromNativeBuffer);
            }
            return writeFromNativeBuffer;
        } finally {
            Util.offerFirstTemporaryDirectBuffer(temporaryDirectBuffer);
        }
    }

    private static int writeFromNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, boolean z, int i, NativeDispatcher nativeDispatcher) throws IOException {
        int write;
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i2 = position <= limit ? limit - position : 0;
        if (z) {
            Util.checkBufferPositionAligned(byteBuffer, position, i);
            Util.checkRemainingBufferSizeAligned(i2, i);
        }
        if (i2 == 0) {
            return 0;
        }
        if (j != -1) {
            write = nativeDispatcher.pwrite(fileDescriptor, ((DirectBuffer) byteBuffer).address() + position, i2, j);
        } else {
            write = nativeDispatcher.write(fileDescriptor, ((DirectBuffer) byteBuffer).address() + position, i2);
        }
        if (write > 0) {
            byteBuffer.position(position + write);
        }
        return write;
    }

    static long write(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, NativeDispatcher nativeDispatcher) throws IOException {
        return write(fileDescriptor, byteBufferArr, 0, byteBufferArr.length, false, -1, nativeDispatcher);
    }

    static long write(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, int i, int i2, NativeDispatcher nativeDispatcher) throws IOException {
        return write(fileDescriptor, byteBufferArr, i, i2, false, -1, nativeDispatcher);
    }

    static long write(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, int i, int i2, boolean z, int i3, NativeDispatcher nativeDispatcher) throws IOException {
        ByteBuffer temporaryDirectBuffer;
        IOVecWrapper iOVecWrapper = IOVecWrapper.get(i2);
        int i4 = i2 + i;
        int i5 = 0;
        int i6 = 0;
        while (i < i4) {
            try {
                if (i6 >= IOV_MAX) {
                    break;
                }
                ByteBuffer byteBuffer = byteBufferArr[i];
                int position = byteBuffer.position();
                int limit = byteBuffer.limit();
                int i7 = position <= limit ? limit - position : 0;
                if (z) {
                    Util.checkRemainingBufferSizeAligned(i7, i3);
                }
                if (i7 > 0) {
                    iOVecWrapper.setBuffer(i6, byteBuffer, position, i7);
                    if (!(byteBuffer instanceof DirectBuffer)) {
                        if (z) {
                            temporaryDirectBuffer = Util.getTemporaryAlignedDirectBuffer(i7, i3);
                        } else {
                            temporaryDirectBuffer = Util.getTemporaryDirectBuffer(i7);
                        }
                        temporaryDirectBuffer.put(byteBuffer);
                        temporaryDirectBuffer.flip();
                        iOVecWrapper.setShadow(i6, temporaryDirectBuffer);
                        byteBuffer.position(position);
                        position = temporaryDirectBuffer.position();
                        byteBuffer = temporaryDirectBuffer;
                    }
                    iOVecWrapper.putBase(i6, ((DirectBuffer) byteBuffer).address() + position);
                    iOVecWrapper.putLen(i6, i7);
                    i6++;
                }
                i++;
            } finally {
                while (i5 < i6) {
                    ByteBuffer shadow = iOVecWrapper.getShadow(i5);
                    if (shadow != null) {
                        Util.offerLastTemporaryDirectBuffer(shadow);
                    }
                    iOVecWrapper.clearRefs(i5);
                    i5++;
                }
            }
        }
        if (i6 == 0) {
            return 0L;
        }
        long writev = nativeDispatcher.writev(fileDescriptor, iOVecWrapper.address, i6);
        long j = writev;
        for (int i8 = 0; i8 < i6; i8++) {
            if (j > 0) {
                ByteBuffer buffer = iOVecWrapper.getBuffer(i8);
                int position2 = iOVecWrapper.getPosition(i8);
                int remaining = iOVecWrapper.getRemaining(i8);
                if (j <= remaining) {
                    remaining = (int) j;
                }
                buffer.position(position2 + remaining);
                j -= remaining;
            }
            ByteBuffer shadow2 = iOVecWrapper.getShadow(i8);
            if (shadow2 != null) {
                Util.offerLastTemporaryDirectBuffer(shadow2);
            }
            iOVecWrapper.clearRefs(i8);
        }
        return writev;
    }

    static int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, NativeDispatcher nativeDispatcher) throws IOException {
        return read(fileDescriptor, byteBuffer, j, false, -1, nativeDispatcher);
    }

    static int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, boolean z, int i, NativeDispatcher nativeDispatcher) throws IOException {
        ByteBuffer temporaryDirectBuffer;
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("Read-only buffer");
        }
        if (byteBuffer instanceof DirectBuffer) {
            return readIntoNativeBuffer(fileDescriptor, byteBuffer, j, z, i, nativeDispatcher);
        }
        int remaining = byteBuffer.remaining();
        if (z) {
            Util.checkRemainingBufferSizeAligned(remaining, i);
            temporaryDirectBuffer = Util.getTemporaryAlignedDirectBuffer(remaining, i);
        } else {
            temporaryDirectBuffer = Util.getTemporaryDirectBuffer(remaining);
        }
        try {
            int readIntoNativeBuffer = readIntoNativeBuffer(fileDescriptor, temporaryDirectBuffer, j, z, i, nativeDispatcher);
            temporaryDirectBuffer.flip();
            if (readIntoNativeBuffer > 0) {
                byteBuffer.put(temporaryDirectBuffer);
            }
            return readIntoNativeBuffer;
        } finally {
            Util.offerFirstTemporaryDirectBuffer(temporaryDirectBuffer);
        }
    }

    private static int readIntoNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long j, boolean z, int i, NativeDispatcher nativeDispatcher) throws IOException {
        int read;
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i2 = position <= limit ? limit - position : 0;
        if (z) {
            Util.checkBufferPositionAligned(byteBuffer, position, i);
            Util.checkRemainingBufferSizeAligned(i2, i);
        }
        if (i2 == 0) {
            return 0;
        }
        if (j != -1) {
            read = nativeDispatcher.pread(fileDescriptor, ((DirectBuffer) byteBuffer).address() + position, i2, j);
        } else {
            read = nativeDispatcher.read(fileDescriptor, ((DirectBuffer) byteBuffer).address() + position, i2);
        }
        if (read > 0) {
            byteBuffer.position(position + read);
        }
        return read;
    }

    static long read(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, NativeDispatcher nativeDispatcher) throws IOException {
        return read(fileDescriptor, byteBufferArr, 0, byteBufferArr.length, false, -1, nativeDispatcher);
    }

    static long read(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, int i, int i2, NativeDispatcher nativeDispatcher) throws IOException {
        return read(fileDescriptor, byteBufferArr, i, i2, false, -1, nativeDispatcher);
    }

    static long read(FileDescriptor fileDescriptor, ByteBuffer[] byteBufferArr, int i, int i2, boolean z, int i3, NativeDispatcher nativeDispatcher) throws IOException {
        IOVecWrapper iOVecWrapper = IOVecWrapper.get(i2);
        int i4 = i2 + i;
        int i5 = 0;
        int i6 = 0;
        while (i < i4) {
            try {
                if (i6 >= IOV_MAX) {
                    break;
                }
                ByteBuffer byteBuffer = byteBufferArr[i];
                if (byteBuffer.isReadOnly()) {
                    throw new IllegalArgumentException("Read-only buffer");
                }
                int position = byteBuffer.position();
                int limit = byteBuffer.limit();
                int i7 = position <= limit ? limit - position : 0;
                if (z) {
                    Util.checkRemainingBufferSizeAligned(i7, i3);
                }
                if (i7 > 0) {
                    iOVecWrapper.setBuffer(i6, byteBuffer, position, i7);
                    if (!(byteBuffer instanceof DirectBuffer)) {
                        if (z) {
                            byteBuffer = Util.getTemporaryAlignedDirectBuffer(i7, i3);
                        } else {
                            byteBuffer = Util.getTemporaryDirectBuffer(i7);
                        }
                        iOVecWrapper.setShadow(i6, byteBuffer);
                        position = byteBuffer.position();
                    }
                    iOVecWrapper.putBase(i6, ((DirectBuffer) byteBuffer).address() + position);
                    iOVecWrapper.putLen(i6, i7);
                    i6++;
                }
                i++;
            } finally {
                while (i5 < i6) {
                    ByteBuffer shadow = iOVecWrapper.getShadow(i5);
                    if (shadow != null) {
                        Util.offerLastTemporaryDirectBuffer(shadow);
                    }
                    iOVecWrapper.clearRefs(i5);
                    i5++;
                }
            }
        }
        if (i6 == 0) {
            return 0L;
        }
        long readv = nativeDispatcher.readv(fileDescriptor, iOVecWrapper.address, i6);
        long j = readv;
        for (int i8 = 0; i8 < i6; i8++) {
            ByteBuffer shadow2 = iOVecWrapper.getShadow(i8);
            if (j > 0) {
                ByteBuffer buffer = iOVecWrapper.getBuffer(i8);
                int remaining = iOVecWrapper.getRemaining(i8);
                if (j <= remaining) {
                    remaining = (int) j;
                }
                if (shadow2 == null) {
                    buffer.position(iOVecWrapper.getPosition(i8) + remaining);
                } else {
                    shadow2.limit(shadow2.position() + remaining);
                    buffer.put(shadow2);
                }
                j -= remaining;
            }
            if (shadow2 != null) {
                Util.offerLastTemporaryDirectBuffer(shadow2);
            }
            iOVecWrapper.clearRefs(i8);
        }
        return readv;
    }

    public static FileDescriptor newFD(int i) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        setfdVal(fileDescriptor, i);
        return fileDescriptor;
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public Void run() {
            System.loadLibrary("net");
            System.loadLibrary("nio");
            return null;
        }
    }

    static {
        AccessController.doPrivileged(new 1());
        initIDs();
        IOV_MAX = iovMax();
    }
}
