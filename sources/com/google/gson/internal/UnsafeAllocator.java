package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public abstract class UnsafeAllocator {
    public abstract Object newInstance(Class cls) throws Exception;

    public static UnsafeAllocator create() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object unsafe = f.get((Object) null);
            Method allocateInstance = unsafeClass.getMethod("allocateInstance", new Class[]{Class.class});
            return new 1(allocateInstance, unsafe);
        } catch (Exception e) {
            try {
                Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                getConstructorId.setAccessible(true);
                int constructorId = ((Integer) getConstructorId.invoke((Object) null, new Object[]{Object.class})).intValue();
                Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                newInstance.setAccessible(true);
                return new 2(newInstance, constructorId);
            } catch (Exception e2) {
                try {
                    Method newInstance2 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    newInstance2.setAccessible(true);
                    return new 3(newInstance2);
                } catch (Exception e3) {
                    return new 4();
                }
            }
        }
    }

    class 1 extends UnsafeAllocator {
        final /* synthetic */ Method val$allocateInstance;
        final /* synthetic */ Object val$unsafe;

        1(Method method, Object obj) {
            this.val$allocateInstance = method;
            this.val$unsafe = obj;
        }

        public Object newInstance(Class cls) throws Exception {
            assertInstantiable(cls);
            return this.val$allocateInstance.invoke(this.val$unsafe, new Object[]{cls});
        }
    }

    class 2 extends UnsafeAllocator {
        final /* synthetic */ int val$constructorId;
        final /* synthetic */ Method val$newInstance;

        2(Method method, int i) {
            this.val$newInstance = method;
            this.val$constructorId = i;
        }

        public Object newInstance(Class cls) throws Exception {
            assertInstantiable(cls);
            return this.val$newInstance.invoke((Object) null, new Object[]{cls, Integer.valueOf(this.val$constructorId)});
        }
    }

    class 3 extends UnsafeAllocator {
        final /* synthetic */ Method val$newInstance;

        3(Method method) {
            this.val$newInstance = method;
        }

        public Object newInstance(Class cls) throws Exception {
            assertInstantiable(cls);
            return this.val$newInstance.invoke((Object) null, new Object[]{cls, Object.class});
        }
    }

    class 4 extends UnsafeAllocator {
        4() {
        }

        public Object newInstance(Class cls) {
            throw new UnsupportedOperationException("Cannot allocate " + cls);
        }
    }

    static void assertInstantiable(Class cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            throw new UnsupportedOperationException("Interface can't be instantiated! Interface name: " + cls.getName());
        }
        if (Modifier.isAbstract(modifiers)) {
            throw new UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + cls.getName());
        }
    }
}
