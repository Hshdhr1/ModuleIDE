package sun.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarUnsafe {
    private static final DesugarUnsafe theUnsafeWrapper;
    private final Unsafe theUnsafe;

    static {
        Field unsafeField = Unsafe.getUnsafeField();
        unsafeField.setAccessible(true);
        try {
            theUnsafeWrapper = new DesugarUnsafe((Unsafe) unsafeField.get((Object) null));
        } catch (IllegalAccessException e) {
            throw DesugarUnsafe$$ExternalSyntheticBackport0.m("Couldn't get the Unsafe", e);
        }
    }

    DesugarUnsafe(Unsafe unsafe) {
        this.theUnsafe = unsafe;
    }

    private static Field getUnsafeField() {
        try {
            return Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Unsafe.class.isAssignableFrom(field.getType())) {
                    return field;
                }
            }
            throw DesugarUnsafe$$ExternalSyntheticBackport0.m("Couldn't find the Unsafe", e);
        }
    }

    public static DesugarUnsafe getUnsafe() {
        return theUnsafeWrapper;
    }

    public int getAndAddInt(Object obj, long j, int i) {
        while (true) {
            int intVolatile = this.theUnsafe.getIntVolatile(obj, j);
            Object obj2 = obj;
            long j2 = j;
            if (this.theUnsafe.compareAndSwapInt(obj2, j2, intVolatile, intVolatile + i)) {
                return intVolatile;
            }
            obj = obj2;
            j = j2;
        }
    }

    public long getAndAddLong(Object obj, long j, long j2) {
        while (true) {
            long longVolatile = this.theUnsafe.getLongVolatile(obj, j);
            Object obj2 = obj;
            long j3 = j;
            if (this.theUnsafe.compareAndSwapLong(obj2, j3, longVolatile, longVolatile + j2)) {
                return longVolatile;
            }
            obj = obj2;
            j = j3;
        }
    }

    public int getAndSetInt(Object obj, long j, int i) {
        while (true) {
            int intVolatile = this.theUnsafe.getIntVolatile(obj, j);
            Object obj2 = obj;
            long j2 = j;
            int i2 = i;
            if (this.theUnsafe.compareAndSwapInt(obj2, j2, intVolatile, i2)) {
                return intVolatile;
            }
            obj = obj2;
            j = j2;
            i = i2;
        }
    }

    public long getAndSetLong(Object obj, long j, long j2) {
        while (true) {
            long longVolatile = this.theUnsafe.getLongVolatile(obj, j);
            Object obj2 = obj;
            long j3 = j;
            long j4 = j2;
            if (this.theUnsafe.compareAndSwapLong(obj2, j3, longVolatile, j4)) {
                return longVolatile;
            }
            obj = obj2;
            j = j3;
            j2 = j4;
        }
    }

    public Object getAndSetObject(Object obj, long j, Object obj2) {
        while (true) {
            Object objectVolatile = this.theUnsafe.getObjectVolatile(obj, j);
            Object obj3 = obj;
            long j2 = j;
            Object obj4 = obj2;
            if (DesugarUnsafe$$ExternalSyntheticBackportWithForwarding1.m(this.theUnsafe, obj3, j2, objectVolatile, obj4)) {
                return objectVolatile;
            }
            obj = obj3;
            j = j2;
            obj2 = obj4;
        }
    }

    public long objectFieldOffset(Field field) {
        return this.theUnsafe.objectFieldOffset(field);
    }

    public long objectFieldOffset(Class cls, String str) {
        if (cls == null || str == null) {
            throw null;
        }
        try {
            return objectFieldOffset(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e) {
            throw DesugarUnsafe$$ExternalSyntheticBackport0.m("Cannot find field:", e);
        }
    }

    public int arrayBaseOffset(Class cls) {
        return this.theUnsafe.arrayBaseOffset(cls);
    }

    public int arrayIndexScale(Class cls) {
        return this.theUnsafe.arrayIndexScale(cls);
    }

    public Object getObjectAcquire(Object obj, long j) {
        return this.theUnsafe.getObjectVolatile(obj, j);
    }

    public void putObjectRelease(Object obj, long j, Object obj2) {
        this.theUnsafe.putObjectVolatile(obj, j, obj2);
    }

    public boolean compareAndSetInt(Object obj, long j, int i, int i2) {
        return this.theUnsafe.compareAndSwapInt(obj, j, i, i2);
    }

    public boolean compareAndSetLong(Object obj, long j, long j2, long j3) {
        return this.theUnsafe.compareAndSwapLong(obj, j, j2, j3);
    }

    public boolean compareAndSetObject(Object obj, long j, Object obj2, Object obj3) {
        return DesugarUnsafe$$ExternalSyntheticBackportWithForwarding1.m(this.theUnsafe, obj, j, obj2, obj3);
    }

    public long allocateMemory(long j) {
        return this.theUnsafe.allocateMemory(j);
    }

    public void freeMemory(long j) {
        this.theUnsafe.allocateMemory(j);
    }

    public void setMemory(long j, long j2, byte b) {
        this.theUnsafe.setMemory(j, j2, b);
    }

    public void copyMemory(long j, long j2, long j3) {
        this.theUnsafe.copyMemory(j, j2, j3);
    }

    public void copyMemory(Object obj, long j, Object obj2, long j2, long j3) {
        int i = 0;
        while (true) {
            long j4 = i;
            if (j4 >= j3) {
                return;
            }
            long j5 = j4 + j;
            byte b = obj == null ? getByte(j5) : getByte(obj, j5);
            if (obj2 == null) {
                putByte(j4 + j2, b);
            } else {
                putByte(obj2, j4 + j2, b);
            }
            i++;
        }
    }

    public int getInt(Object obj, long j) {
        return this.theUnsafe.getInt(obj, j);
    }

    public void putInt(Object obj, long j, int i) {
        this.theUnsafe.putInt(obj, j, i);
    }

    public Object getObject(Object obj, long j) {
        return this.theUnsafe.getObject(obj, j);
    }

    public void putObject(Object obj, long j, Object obj2) {
        this.theUnsafe.putObject(obj, j, obj2);
    }

    public boolean getBoolean(Object obj, long j) {
        return this.theUnsafe.getBoolean(obj, j);
    }

    public void putBoolean(Object obj, long j, boolean z) {
        this.theUnsafe.putBoolean(obj, j, z);
    }

    public byte getByte(Object obj, long j) {
        return this.theUnsafe.getByte(obj, j);
    }

    public void putByte(Object obj, long j, byte b) {
        this.theUnsafe.putByte(obj, j, b);
    }

    public short getShort(Object obj, long j) {
        return this.theUnsafe.getShort(obj, j);
    }

    public void putShort(Object obj, long j, short s) {
        this.theUnsafe.putShort(obj, j, s);
    }

    public char getChar(Object obj, long j) {
        return this.theUnsafe.getChar(obj, j);
    }

    public void putChar(Object obj, long j, char c) {
        this.theUnsafe.putChar(obj, j, c);
    }

    public long getLong(Object obj, long j) {
        return this.theUnsafe.getLong(obj, j);
    }

    public void putLong(Object obj, long j, long j2) {
        this.theUnsafe.putLong(obj, j, j2);
    }

    public float getFloat(Object obj, long j) {
        return this.theUnsafe.getFloat(obj, j);
    }

    public void putFloat(Object obj, long j, float f) {
        this.theUnsafe.putFloat(obj, j, f);
    }

    public double getDouble(Object obj, long j) {
        return this.theUnsafe.getDouble(obj, j);
    }

    public void putDouble(Object obj, long j, double d) {
        this.theUnsafe.putDouble(obj, j, d);
    }

    public byte getByte(long j) {
        return this.theUnsafe.getByte(j);
    }

    public void putByte(long j, byte b) {
        this.theUnsafe.putByte(j, b);
    }

    public short getShort(long j) {
        return this.theUnsafe.getShort(j);
    }

    public void putShort(long j, short s) {
        this.theUnsafe.putShort(j, s);
    }

    public char getChar(long j) {
        return this.theUnsafe.getChar(j);
    }

    public void putChar(long j, char c) {
        this.theUnsafe.putChar(j, c);
    }

    public int getInt(long j) {
        return this.theUnsafe.getInt(j);
    }

    public void putInt(long j, int i) {
        this.theUnsafe.putInt(j, i);
    }

    public long getLong(long j) {
        return this.theUnsafe.getLong(j);
    }

    public void putLong(long j, long j2) {
        this.theUnsafe.putLong(j, j2);
    }

    public float getFloat(long j) {
        return this.theUnsafe.getFloat(j);
    }

    public void putFloat(long j, float f) {
        this.theUnsafe.putFloat(j, f);
    }

    public double getDouble(long j) {
        return this.theUnsafe.getDouble(j);
    }

    public void putDouble(long j, double d) {
        this.theUnsafe.putDouble(j, d);
    }

    public int addressSize() {
        return this.theUnsafe.addressSize();
    }

    public int pageSize() {
        return this.theUnsafe.pageSize();
    }

    public Object allocateInstance(Class cls) throws InstantiationException {
        return this.theUnsafe.allocateInstance(cls);
    }

    public boolean compareAndSwapObject(Object obj, long j, Object obj2, Object obj3) {
        return DesugarUnsafe$$ExternalSyntheticBackportWithForwarding1.m(this.theUnsafe, obj, j, obj2, obj3);
    }

    public boolean compareAndSwapInt(Object obj, long j, int i, int i2) {
        return this.theUnsafe.compareAndSwapInt(obj, j, i, i2);
    }

    public boolean compareAndSwapLong(Object obj, long j, long j2, long j3) {
        return this.theUnsafe.compareAndSwapLong(obj, j, j2, j3);
    }

    public Object getObjectVolatile(Object obj, long j) {
        return this.theUnsafe.getObjectVolatile(obj, j);
    }

    public void putObjectVolatile(Object obj, long j, Object obj2) {
        this.theUnsafe.putObjectVolatile(obj, j, obj2);
    }

    public int getIntVolatile(Object obj, long j) {
        return this.theUnsafe.getIntVolatile(obj, j);
    }

    public void putIntVolatile(Object obj, long j, int i) {
        this.theUnsafe.putIntVolatile(obj, j, i);
    }

    public long getLongVolatile(Object obj, long j) {
        return this.theUnsafe.getLongVolatile(obj, j);
    }

    public void putLongVolatile(Object obj, long j, long j2) {
        this.theUnsafe.putLongVolatile(obj, j, j2);
    }

    public void putOrderedObject(Object obj, long j, Object obj2) {
        this.theUnsafe.putOrderedObject(obj, j, obj2);
    }

    public void putOrderedInt(Object obj, long j, int i) {
        this.theUnsafe.putOrderedInt(obj, j, i);
    }

    public void putOrderedLong(Object obj, long j, long j2) {
        this.theUnsafe.putOrderedLong(obj, j, j2);
    }

    public void unpark(Object obj) {
        this.theUnsafe.unpark(obj);
    }

    public void park(boolean z, long j) {
        this.theUnsafe.park(z, j);
    }

    public void ensureClassInitialized(Class cls) {
        try {
            Class.forName(cls.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
