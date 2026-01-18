package sun.nio.fs;

import java.lang.ref.Cleaner;
import jdk.internal.misc.Unsafe;
import jdk.internal.ref.CleanerFactory;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class NativeBuffer {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final long address;
    private final Cleaner.Cleanable cleanable;
    private Object owner;
    private final int size;

    static /* bridge */ /* synthetic */ Unsafe -$$Nest$sfgetunsafe() {
        return unsafe;
    }

    private static class Deallocator implements Runnable {
        private final long address;

        Deallocator(long j) {
            this.address = j;
        }

        public void run() {
            NativeBuffer.-$$Nest$sfgetunsafe().freeMemory(this.address);
        }
    }

    NativeBuffer(int i) {
        long allocateMemory = unsafe.allocateMemory(i);
        this.address = allocateMemory;
        this.size = i;
        this.cleanable = CleanerFactory.cleaner().register(this, new Deallocator(allocateMemory));
    }

    void release() {
        NativeBuffers.releaseNativeBuffer(this);
    }

    long address() {
        return this.address;
    }

    int size() {
        return this.size;
    }

    void free() {
        this.cleanable.clean();
    }

    void setOwner(Object obj) {
        this.owner = obj;
    }

    Object owner() {
        return this.owner;
    }
}
