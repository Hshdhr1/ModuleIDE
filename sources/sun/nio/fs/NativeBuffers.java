package sun.nio.fs;

import jdk.internal.misc.TerminatingThreadLocal;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class NativeBuffers {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int TEMP_BUF_POOL_SIZE = 3;
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static ThreadLocal threadLocal = new 1();

    private NativeBuffers() {
    }

    class 1 extends TerminatingThreadLocal {
        1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void threadTerminated(NativeBuffer[] nativeBufferArr) {
            if (nativeBufferArr != null) {
                for (int i = 0; i < 3; i++) {
                    NativeBuffer nativeBuffer = nativeBufferArr[i];
                    if (nativeBuffer != null) {
                        nativeBuffer.free();
                        nativeBufferArr[i] = null;
                    }
                }
            }
        }
    }

    static NativeBuffer allocNativeBuffer(int i) {
        if (i < 2048) {
            i = 2048;
        }
        return new NativeBuffer(i);
    }

    static NativeBuffer getNativeBufferFromCache(int i) {
        NativeBuffer[] nativeBufferArr = (NativeBuffer[]) threadLocal.get();
        if (nativeBufferArr != null) {
            for (int i2 = 0; i2 < 3; i2++) {
                NativeBuffer nativeBuffer = nativeBufferArr[i2];
                if (nativeBuffer != null && nativeBuffer.size() >= i) {
                    nativeBufferArr[i2] = null;
                    return nativeBuffer;
                }
            }
        }
        return null;
    }

    static NativeBuffer getNativeBuffer(int i) {
        NativeBuffer nativeBufferFromCache = getNativeBufferFromCache(i);
        if (nativeBufferFromCache != null) {
            nativeBufferFromCache.setOwner(null);
            return nativeBufferFromCache;
        }
        return allocNativeBuffer(i);
    }

    static void releaseNativeBuffer(NativeBuffer nativeBuffer) {
        NativeBuffer[] nativeBufferArr = (NativeBuffer[]) threadLocal.get();
        if (nativeBufferArr == null) {
            NativeBuffer[] nativeBufferArr2 = new NativeBuffer[3];
            nativeBufferArr2[0] = nativeBuffer;
            threadLocal.set(nativeBufferArr2);
            return;
        }
        for (int i = 0; i < 3; i++) {
            if (nativeBufferArr[i] == null) {
                nativeBufferArr[i] = nativeBuffer;
                return;
            }
        }
        for (int i2 = 0; i2 < 3; i2++) {
            NativeBuffer nativeBuffer2 = nativeBufferArr[i2];
            if (nativeBuffer2.size() < nativeBuffer.size()) {
                nativeBuffer2.free();
                nativeBufferArr[i2] = nativeBuffer;
                return;
            }
        }
        nativeBuffer.free();
    }

    static void copyCStringToNativeBuffer(byte[] bArr, NativeBuffer nativeBuffer) {
        long j = Unsafe.ARRAY_BYTE_BASE_OFFSET;
        long length = bArr.length;
        Unsafe unsafe2 = unsafe;
        unsafe2.copyMemory(bArr, j, (Object) null, nativeBuffer.address(), length);
        unsafe2.putByte(nativeBuffer.address() + length, (byte) 0);
    }

    static NativeBuffer asNativeBuffer(byte[] bArr) {
        NativeBuffer nativeBuffer = getNativeBuffer(bArr.length + 1);
        copyCStringToNativeBuffer(bArr, nativeBuffer);
        return nativeBuffer;
    }
}
