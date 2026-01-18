package sun.nio.ch;

import java.nio.ByteOrder;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class NativeObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final long address;
    protected long allocationAddress;
    protected static final Unsafe unsafe = Unsafe.getUnsafe();
    private static ByteOrder byteOrder = null;
    private static int pageSize = -1;

    NativeObject(long j) {
        this.allocationAddress = j;
        this.address = j;
    }

    NativeObject(long j, long j2) {
        this.allocationAddress = j;
        this.address = j + j2;
    }

    protected NativeObject(int i, boolean z) {
        if (!z) {
            long allocateMemory = unsafe.allocateMemory(i);
            this.allocationAddress = allocateMemory;
            this.address = allocateMemory;
        } else {
            int pageSize2 = pageSize();
            long allocateMemory2 = unsafe.allocateMemory(i + pageSize2);
            this.allocationAddress = allocateMemory2;
            this.address = (pageSize2 + allocateMemory2) - ((pageSize2 - 1) & allocateMemory2);
        }
    }

    long address() {
        return this.address;
    }

    long allocationAddress() {
        return this.allocationAddress;
    }

    NativeObject subObject(int i) {
        return new NativeObject(i + this.address);
    }

    NativeObject getObject(int i) {
        long j;
        int addressSize = addressSize();
        if (addressSize == 4) {
            j = unsafe.getInt(i + this.address);
        } else if (addressSize == 8) {
            j = unsafe.getLong(i + this.address);
        } else {
            throw new InternalError("Address size not supported");
        }
        return new NativeObject(j);
    }

    void putObject(int i, NativeObject nativeObject) {
        int addressSize = addressSize();
        if (addressSize == 4) {
            putInt(i, (int) nativeObject.address);
        } else {
            if (addressSize == 8) {
                putLong(i, nativeObject.address);
                return;
            }
            throw new InternalError("Address size not supported");
        }
    }

    final byte getByte(int i) {
        return unsafe.getByte(i + this.address);
    }

    final void putByte(int i, byte b) {
        unsafe.putByte(i + this.address, b);
    }

    final short getShort(int i) {
        return unsafe.getShort(i + this.address);
    }

    final void putShort(int i, short s) {
        unsafe.putShort(i + this.address, s);
    }

    final char getChar(int i) {
        return unsafe.getChar(i + this.address);
    }

    final void putChar(int i, char c) {
        unsafe.putChar(i + this.address, c);
    }

    final int getInt(int i) {
        return unsafe.getInt(i + this.address);
    }

    final void putInt(int i, int i2) {
        unsafe.putInt(i + this.address, i2);
    }

    final long getLong(int i) {
        return unsafe.getLong(i + this.address);
    }

    final void putLong(int i, long j) {
        unsafe.putLong(i + this.address, j);
    }

    final float getFloat(int i) {
        return unsafe.getFloat(i + this.address);
    }

    final void putFloat(int i, float f) {
        unsafe.putFloat(i + this.address, f);
    }

    final double getDouble(int i) {
        return unsafe.getDouble(i + this.address);
    }

    final void putDouble(int i, double d) {
        unsafe.putDouble(i + this.address, d);
    }

    static int addressSize() {
        return unsafe.addressSize();
    }

    static ByteOrder byteOrder() {
        ByteOrder byteOrder2 = byteOrder;
        if (byteOrder2 != null) {
            return byteOrder2;
        }
        Unsafe unsafe2 = unsafe;
        long allocateMemory = unsafe2.allocateMemory(8L);
        try {
            unsafe2.putLong(allocateMemory, 72623859790382856L);
            byte b = unsafe2.getByte(allocateMemory);
            if (b == 1) {
                byteOrder = ByteOrder.BIG_ENDIAN;
            } else if (b == 8) {
                byteOrder = ByteOrder.LITTLE_ENDIAN;
            }
            unsafe2.freeMemory(allocateMemory);
            return byteOrder;
        } catch (Throwable th) {
            unsafe.freeMemory(allocateMemory);
            throw th;
        }
    }

    static int pageSize() {
        int i = pageSize;
        if (i != -1) {
            return i;
        }
        int pageSize2 = unsafe.pageSize();
        pageSize = pageSize2;
        return pageSize2;
    }
}
