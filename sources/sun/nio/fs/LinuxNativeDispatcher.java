package sun.nio.fs;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxNativeDispatcher extends UnixNativeDispatcher {
    static native void endmntent(long j) throws UnixException;

    private static native int fgetxattr0(int i, long j, long j2, int i2) throws UnixException;

    static native int flistxattr(int i, long j, int i2) throws UnixException;

    private static native void fremovexattr0(int i, long j) throws UnixException;

    private static native void fsetxattr0(int i, long j, long j2, int i2) throws UnixException;

    static native int getmntent0(long j, UnixMountEntry unixMountEntry, long j2, int i) throws UnixException;

    private static native void init();

    private static native long setmntent0(long j, long j2) throws UnixException;

    private LinuxNativeDispatcher() {
    }

    static long setmntent(byte[] bArr, byte[] bArr2) throws UnixException {
        NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(bArr);
        NativeBuffer asNativeBuffer2 = NativeBuffers.asNativeBuffer(bArr2);
        try {
            return setmntent0(asNativeBuffer.address(), asNativeBuffer2.address());
        } finally {
            asNativeBuffer2.release();
            asNativeBuffer.release();
        }
    }

    static int getmntent(long j, UnixMountEntry unixMountEntry, int i) throws UnixException {
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(i);
        try {
            return getmntent0(j, unixMountEntry, nativeBuffer.address(), i);
        } finally {
            nativeBuffer.release();
        }
    }

    static int fgetxattr(int i, byte[] bArr, long j, int i2) throws UnixException {
        NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(bArr);
        try {
            return fgetxattr0(i, asNativeBuffer.address(), j, i2);
        } finally {
            asNativeBuffer.release();
        }
    }

    static void fsetxattr(int i, byte[] bArr, long j, int i2) throws UnixException {
        NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(bArr);
        try {
            fsetxattr0(i, asNativeBuffer.address(), j, i2);
        } finally {
            asNativeBuffer.release();
        }
    }

    static void fremovexattr(int i, byte[] bArr) throws UnixException {
        NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(bArr);
        try {
            fremovexattr0(i, asNativeBuffer.address());
        } finally {
            asNativeBuffer.release();
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

    static {
        AccessController.doPrivileged(new 1());
        init();
    }
}
