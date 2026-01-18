package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes13.dex */
public class Lifecycling {
    private static final int GENERATED_CALLBACK = 2;
    private static final int REFLECTIVE_CALLBACK = 1;
    private static Map sCallbackCache = new HashMap();
    private static Map sClassToAdapters = new HashMap();

    class 1 implements GenericLifecycleObserver {
        final /* synthetic */ LifecycleEventObserver val$observer;

        1(LifecycleEventObserver lifecycleEventObserver) {
            this.val$observer = lifecycleEventObserver;
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            this.val$observer.onStateChanged(source, event);
        }
    }

    @Deprecated
    static GenericLifecycleObserver getCallback(Object object) {
        LifecycleEventObserver observer = lifecycleEventObserver(object);
        return new 1(observer);
    }

    static LifecycleEventObserver lifecycleEventObserver(Object object) {
        boolean isLifecycleEventObserver = object instanceof LifecycleEventObserver;
        boolean isFullLifecycleObserver = object instanceof FullLifecycleObserver;
        if (isLifecycleEventObserver && isFullLifecycleObserver) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver) object, (LifecycleEventObserver) object);
        }
        if (isFullLifecycleObserver) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver) object, null);
        }
        if (isLifecycleEventObserver) {
            return (LifecycleEventObserver) object;
        }
        Class<?> klass = object.getClass();
        int type = getObserverConstructorType(klass);
        if (type == 2) {
            List<Constructor<? extends GeneratedAdapter>> constructors = (List) sClassToAdapters.get(klass);
            if (constructors.size() == 1) {
                GeneratedAdapter generatedAdapter = createGeneratedAdapter((Constructor) constructors.get(0), object);
                return new SingleGeneratedAdapterObserver(generatedAdapter);
            }
            GeneratedAdapter[] adapters = new GeneratedAdapter[constructors.size()];
            for (int i = 0; i < constructors.size(); i++) {
                adapters[i] = createGeneratedAdapter((Constructor) constructors.get(i), object);
            }
            return new CompositeGeneratedAdaptersObserver(adapters);
        }
        return new ReflectiveGenericLifecycleObserver(object);
    }

    private static GeneratedAdapter createGeneratedAdapter(Constructor constructor, Object object) {
        try {
            return (GeneratedAdapter) constructor.newInstance(new Object[]{object});
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (IllegalAccessException e3) {
            throw new RuntimeException(e3);
        }
    }

    private static Constructor generatedConstructor(Class cls) {
        try {
            Package aPackage = cls.getPackage();
            String name = cls.getCanonicalName();
            String fullPackage = aPackage != null ? aPackage.getName() : "";
            String adapterName = getAdapterName(fullPackage.isEmpty() ? name : name.substring(fullPackage.length() + 1));
            Class<? extends GeneratedAdapter> aClass = Class.forName(fullPackage.isEmpty() ? adapterName : fullPackage + "." + adapterName);
            Constructor<? extends GeneratedAdapter> constructor = aClass.getDeclaredConstructor(new Class[]{cls});
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e2) {
            return null;
        }
    }

    private static int getObserverConstructorType(Class cls) {
        Integer callbackCache = (Integer) sCallbackCache.get(cls);
        if (callbackCache != null) {
            return callbackCache.intValue();
        }
        int type = resolveObserverCallbackType(cls);
        sCallbackCache.put(cls, Integer.valueOf(type));
        return type;
    }

    private static int resolveObserverCallbackType(Class cls) {
        if (cls.getCanonicalName() == null) {
            return 1;
        }
        Constructor<? extends GeneratedAdapter> constructor = generatedConstructor(cls);
        if (constructor != null) {
            sClassToAdapters.put(cls, Collections.singletonList(constructor));
            return 2;
        }
        boolean hasLifecycleMethods = ClassesInfoCache.sInstance.hasLifecycleMethods(cls);
        if (hasLifecycleMethods) {
            return 1;
        }
        Class<?> superclass = cls.getSuperclass();
        ArrayList arrayList = null;
        if (isLifecycleParent(superclass)) {
            if (getObserverConstructorType(superclass) == 1) {
                return 1;
            }
            arrayList = new ArrayList((Collection) sClassToAdapters.get(superclass));
        }
        for (Class<?> intrface : cls.getInterfaces()) {
            if (isLifecycleParent(intrface)) {
                if (getObserverConstructorType(intrface) == 1) {
                    return 1;
                }
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.addAll((Collection) sClassToAdapters.get(intrface));
            }
        }
        if (arrayList == null) {
            return 1;
        }
        sClassToAdapters.put(cls, arrayList);
        return 2;
    }

    private static boolean isLifecycleParent(Class cls) {
        return cls != null && LifecycleObserver.class.isAssignableFrom(cls);
    }

    public static String getAdapterName(String className) {
        return className.replace(".", "_") + "_LifecycleAdapter";
    }

    private Lifecycling() {
    }
}
