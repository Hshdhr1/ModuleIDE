package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.internal.reflect.ReflectionAccessor;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public final class ConstructorConstructor {
    private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();
    private final Map instanceCreators;

    public ConstructorConstructor(Map map) {
        this.instanceCreators = map;
    }

    public ObjectConstructor get(TypeToken typeToken) {
        Type type = typeToken.getType();
        Class rawType = typeToken.getRawType();
        InstanceCreator instanceCreator = (InstanceCreator) this.instanceCreators.get(type);
        if (instanceCreator != null) {
            return new 1(instanceCreator, type);
        }
        InstanceCreator instanceCreator2 = (InstanceCreator) this.instanceCreators.get(rawType);
        if (instanceCreator2 != null) {
            return new 2(instanceCreator2, type);
        }
        ObjectConstructor newDefaultConstructor = newDefaultConstructor(rawType);
        if (newDefaultConstructor == null) {
            ObjectConstructor newDefaultImplementationConstructor = newDefaultImplementationConstructor(type, rawType);
            return newDefaultImplementationConstructor != null ? newDefaultImplementationConstructor : newUnsafeAllocator(type, rawType);
        }
        return newDefaultConstructor;
    }

    class 1 implements ObjectConstructor {
        final /* synthetic */ Type val$type;
        final /* synthetic */ InstanceCreator val$typeCreator;

        1(InstanceCreator instanceCreator, Type type) {
            this.val$typeCreator = instanceCreator;
            this.val$type = type;
        }

        public Object construct() {
            return this.val$typeCreator.createInstance(this.val$type);
        }
    }

    class 2 implements ObjectConstructor {
        final /* synthetic */ InstanceCreator val$rawTypeCreator;
        final /* synthetic */ Type val$type;

        2(InstanceCreator instanceCreator, Type type) {
            this.val$rawTypeCreator = instanceCreator;
            this.val$type = type;
        }

        public Object construct() {
            return this.val$rawTypeCreator.createInstance(this.val$type);
        }
    }

    private ObjectConstructor newDefaultConstructor(Class cls) {
        try {
            AccessibleObject declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                this.accessor.makeAccessible(declaredConstructor);
            }
            return new 3(declaredConstructor);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    class 3 implements ObjectConstructor {
        final /* synthetic */ Constructor val$constructor;

        3(Constructor constructor) {
            this.val$constructor = constructor;
        }

        public Object construct() {
            try {
                return this.val$constructor.newInstance((Object[]) null);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", e.getTargetException());
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            } catch (InstantiationException e3) {
                throw new RuntimeException("Failed to invoke " + this.val$constructor + " with no args", e3);
            }
        }
    }

    private ObjectConstructor newDefaultImplementationConstructor(Type type, Class cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            if (SortedSet.class.isAssignableFrom(cls)) {
                return new 4();
            }
            if (EnumSet.class.isAssignableFrom(cls)) {
                return new 5(type);
            }
            if (Set.class.isAssignableFrom(cls)) {
                return new 6();
            }
            if (Queue.class.isAssignableFrom(cls)) {
                return new 7();
            }
            return new 8();
        }
        if (Map.class.isAssignableFrom(cls)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(cls)) {
                return new 9();
            }
            if (ConcurrentMap.class.isAssignableFrom(cls)) {
                return new 10();
            }
            if (SortedMap.class.isAssignableFrom(cls)) {
                return new 11();
            }
            if ((type instanceof ParameterizedType) && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType) type).getActualTypeArguments()[0]).getRawType())) {
                return new 12();
            }
            return new 13();
        }
        return null;
    }

    class 4 implements ObjectConstructor {
        4() {
        }

        public Object construct() {
            return new TreeSet();
        }
    }

    class 5 implements ObjectConstructor {
        final /* synthetic */ Type val$type;

        5(Type type) {
            this.val$type = type;
        }

        public Object construct() {
            if (this.val$type instanceof ParameterizedType) {
                Class cls = this.val$type.getActualTypeArguments()[0];
                if (cls instanceof Class) {
                    return EnumSet.noneOf(cls);
                }
                throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
            }
            throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
        }
    }

    class 6 implements ObjectConstructor {
        6() {
        }

        public Object construct() {
            return new LinkedHashSet();
        }
    }

    class 7 implements ObjectConstructor {
        7() {
        }

        public Object construct() {
            return new ArrayDeque();
        }
    }

    class 8 implements ObjectConstructor {
        8() {
        }

        public Object construct() {
            return new ArrayList();
        }
    }

    class 9 implements ObjectConstructor {
        9() {
        }

        public Object construct() {
            return new ConcurrentSkipListMap();
        }
    }

    class 10 implements ObjectConstructor {
        10() {
        }

        public Object construct() {
            return new ConcurrentHashMap();
        }
    }

    class 11 implements ObjectConstructor {
        11() {
        }

        public Object construct() {
            return new TreeMap();
        }
    }

    class 12 implements ObjectConstructor {
        12() {
        }

        public Object construct() {
            return new LinkedHashMap();
        }
    }

    class 13 implements ObjectConstructor {
        13() {
        }

        public Object construct() {
            return new LinkedTreeMap();
        }
    }

    class 14 implements ObjectConstructor {
        private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
        final /* synthetic */ Class val$rawType;
        final /* synthetic */ Type val$type;

        14(Class cls, Type type) {
            this.val$rawType = cls;
            this.val$type = type;
        }

        public Object construct() {
            try {
                Object newInstance = this.unsafeAllocator.newInstance(this.val$rawType);
                return newInstance;
            } catch (Exception e) {
                throw new RuntimeException("Unable to invoke no-args constructor for " + this.val$type + ". Registering an InstanceCreator with Gson for this type may fix this problem.", e);
            }
        }
    }

    private ObjectConstructor newUnsafeAllocator(Type type, Class cls) {
        return new 14(cls, type);
    }

    public String toString() {
        return this.instanceCreators.toString();
    }
}
