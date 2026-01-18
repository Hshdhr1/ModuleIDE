package androidx.fragment.app;

import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment;
import java.lang.reflect.InvocationTargetException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public class FragmentFactory {
    private static final SimpleArrayMap sClassCacheMap = new SimpleArrayMap();

    private static Class loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        SimpleArrayMap simpleArrayMap = sClassCacheMap;
        SimpleArrayMap<String, Class<?>> classMap = (SimpleArrayMap) simpleArrayMap.get(classLoader);
        if (classMap == null) {
            classMap = new SimpleArrayMap<>();
            simpleArrayMap.put(classLoader, classMap);
        }
        Class<?> clazz = (Class) classMap.get(className);
        if (clazz == null) {
            Class<?> clazz2 = Class.forName(className, false, classLoader);
            classMap.put(className, clazz2);
            return clazz2;
        }
        return clazz;
    }

    static boolean isFragmentClass(ClassLoader classLoader, String className) {
        try {
            Class<?> clazz = loadClass(classLoader, className);
            return Fragment.class.isAssignableFrom(clazz);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Class loadFragmentClass(ClassLoader classLoader, String className) {
        try {
            Class<?> clazz = loadClass(classLoader, className);
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists", e);
        } catch (ClassCastException e2) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class is a valid subclass of Fragment", e2);
        }
    }

    public Fragment instantiate(ClassLoader classLoader, String className) {
        try {
            Class<? extends Fragment> cls = loadFragmentClass(classLoader, className);
            return (Fragment) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (NoSuchMethodException e2) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": could not find Fragment constructor", e2);
        } catch (InstantiationException e3) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        } catch (InvocationTargetException e4) {
            throw new Fragment.InstantiationException("Unable to instantiate fragment " + className + ": calling Fragment constructor caused an exception", e4);
        }
    }
}
