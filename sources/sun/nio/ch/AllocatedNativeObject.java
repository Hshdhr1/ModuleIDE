package sun.nio.ch;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class AllocatedNativeObject extends NativeObject {
    AllocatedNativeObject(int i, boolean z) {
        super(i, z);
    }

    synchronized void free() {
        if (this.allocationAddress != 0) {
            unsafe.freeMemory(this.allocationAddress);
            this.allocationAddress = 0L;
        }
    }
}
