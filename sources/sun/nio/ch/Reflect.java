package sun.nio.ch;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class Reflect {
    private Reflect() {
    }

    private static class ReflectionError extends Error {
        private static final long serialVersionUID = -8659519328078164097L;

        ReflectionError(Throwable th) {
            super(th);
        }
    }

    class 1 implements PrivilegedAction {
        final /* synthetic */ AccessibleObject val$ao;

        1(AccessibleObject accessibleObject) {
            this.val$ao = accessibleObject;
        }

        public Void run() {
            this.val$ao.setAccessible(true);
            return null;
        }
    }

    private static void setAccessible(AccessibleObject accessibleObject) {
        AccessController.doPrivileged(new 1(accessibleObject));
    }

    static Constructor lookupConstructor(String str, Class[] clsArr) {
        try {
            Constructor declaredConstructor = Class.forName(str).getDeclaredConstructor(clsArr);
            setAccessible(declaredConstructor);
            return declaredConstructor;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new ReflectionError(e);
        }
    }

    static Object invoke(Constructor constructor, Object[] objArr) {
        try {
            return constructor.newInstance(objArr);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionError(e);
        }
    }

    static Method lookupMethod(String str, String str2, Class... clsArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            setAccessible(declaredMethod);
            return declaredMethod;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new ReflectionError(e);
        }
    }

    static Object invoke(Method method, Object obj, Object[] objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionError(e);
        }
    }

    static Object invokeIO(Method method, Object obj, Object[] objArr) throws IOException {
        try {
            return method.invoke(obj, objArr);
        } catch (InvocationTargetException e) {
            if (IOException.class.isInstance(e.getCause())) {
                throw e.getCause();
            }
            throw new ReflectionError(e);
        } catch (IllegalAccessException e2) {
            throw new ReflectionError(e2);
        }
    }

    static Field lookupField(String str, String str2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            setAccessible(declaredField);
            return declaredField;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new ReflectionError(e);
        }
    }

    static Object get(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(e);
        }
    }

    static Object get(Field field) {
        return get(null, field);
    }

    static void set(Object obj, Field field, Object obj2) {
        try {
            field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(e);
        }
    }

    static void setInt(Object obj, Field field, int i) {
        try {
            field.setInt(obj, i);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(e);
        }
    }

    static void setBoolean(Object obj, Field field, boolean z) {
        try {
            field.setBoolean(obj, z);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(e);
        }
    }
}
