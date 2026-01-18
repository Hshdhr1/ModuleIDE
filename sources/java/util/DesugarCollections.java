package java.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarCollections {
    private static final Field COLLECTION_FIELD;
    private static final Field MUTEX_FIELD;
    public static final Class SYNCHRONIZED_COLLECTION;
    private static final Constructor SYNCHRONIZED_COLLECTION_CONSTRUCTOR;
    static final Class SYNCHRONIZED_LIST;
    private static final Constructor SYNCHRONIZED_SET_CONSTRUCTOR;

    static /* bridge */ /* synthetic */ Constructor -$$Nest$sfgetSYNCHRONIZED_COLLECTION_CONSTRUCTOR() {
        return SYNCHRONIZED_COLLECTION_CONSTRUCTOR;
    }

    static /* bridge */ /* synthetic */ Constructor -$$Nest$sfgetSYNCHRONIZED_SET_CONSTRUCTOR() {
        return SYNCHRONIZED_SET_CONSTRUCTOR;
    }

    private DesugarCollections() {
    }

    static {
        Class cls = Collections.synchronizedCollection(new ArrayList()).getClass();
        SYNCHRONIZED_COLLECTION = cls;
        SYNCHRONIZED_LIST = Collections.synchronizedList(new LinkedList()).getClass();
        Field field = getField(cls, "mutex");
        MUTEX_FIELD = field;
        if (field != null) {
            field.setAccessible(true);
        }
        Field field2 = getField(cls, "c");
        COLLECTION_FIELD = field2;
        if (field2 != null) {
            field2.setAccessible(true);
        }
        Constructor constructor = getConstructor(Collections.synchronizedSet(new HashSet()).getClass(), Set.class, Object.class);
        SYNCHRONIZED_SET_CONSTRUCTOR = constructor;
        if (constructor != null) {
            constructor.setAccessible(true);
        }
        Constructor constructor2 = getConstructor(cls, Collection.class, Object.class);
        SYNCHRONIZED_COLLECTION_CONSTRUCTOR = constructor2;
        if (constructor2 != null) {
            constructor2.setAccessible(true);
        }
    }

    private static Field getField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            return null;
        }
    }

    private static Constructor getConstructor(Class cls, Class... clsArr) {
        try {
            return cls.getDeclaredConstructor(clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    static boolean removeIf(Collection collection, Predicate predicate) {
        boolean removeIf;
        Field field = MUTEX_FIELD;
        if (field == null) {
            try {
                return ((Collection) COLLECTION_FIELD.get(collection)).removeIf(predicate);
            } catch (IllegalAccessException e) {
                throw new Error("Runtime illegal access in synchronized collection removeIf fall-back.", e);
            }
        }
        try {
            synchronized (field.get(collection)) {
                removeIf = ((Collection) COLLECTION_FIELD.get(collection)).removeIf(predicate);
            }
            return removeIf;
        } catch (IllegalAccessException e2) {
            throw new Error("Runtime illegal access in synchronized collection removeIf.", e2);
        }
    }

    public static void forEach(Iterable iterable, Consumer consumer) {
        Field field = MUTEX_FIELD;
        if (field == null) {
            try {
                ((Collection) COLLECTION_FIELD.get(iterable)).forEach(consumer);
            } catch (IllegalAccessException e) {
                throw new Error("Runtime illegal access in synchronized collection forEach fall-back.", e);
            }
        } else {
            try {
                synchronized (field.get(iterable)) {
                    ((Collection) COLLECTION_FIELD.get(iterable)).forEach(consumer);
                }
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized collection forEach.", e2);
            }
        }
    }

    public static void forEachIterable(Iterable iterable, Consumer consumer) {
        consumer.getClass();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    static void replaceAll(List list, UnaryOperator unaryOperator) {
        Field field = MUTEX_FIELD;
        if (field == null) {
            try {
                ((List) COLLECTION_FIELD.get(list)).replaceAll(unaryOperator);
            } catch (IllegalAccessException e) {
                throw new Error("Runtime illegal access in synchronized list replaceAll fall-back.", e);
            }
        } else {
            try {
                synchronized (field.get(list)) {
                    ((List) COLLECTION_FIELD.get(list)).replaceAll(unaryOperator);
                }
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized list replaceAll.", e2);
            }
        }
    }

    static void sort(List list, Comparator comparator) {
        Field field = MUTEX_FIELD;
        if (field == null) {
            try {
                ((List) COLLECTION_FIELD.get(list)).sort(comparator);
            } catch (IllegalAccessException e) {
                throw new Error("Runtime illegal access in synchronized collection sort fall-back.", e);
            }
        } else {
            try {
                synchronized (field.get(list)) {
                    ((List) COLLECTION_FIELD.get(list)).sort(comparator);
                }
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized list sort.", e2);
            }
        }
    }

    public static Map synchronizedMap(Map map) {
        return new SynchronizedMap(map);
    }

    private static class SynchronizedMap implements Map, Serializable {
        private static final long serialVersionUID = 1978198479659022715L;
        private transient Set entrySet;
        private transient Set keySet;
        private final Map m;
        final Object mutex;
        private transient Collection values;

        SynchronizedMap(Map map) {
            map.getClass();
            this.m = map;
            this.mutex = this;
        }

        SynchronizedMap(Map map, Object obj) {
            this.m = map;
            this.mutex = obj;
        }

        public int size() {
            int size;
            synchronized (this.mutex) {
                size = this.m.size();
            }
            return size;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.mutex) {
                isEmpty = this.m.isEmpty();
            }
            return isEmpty;
        }

        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.mutex) {
                containsKey = this.m.containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.mutex) {
                containsValue = this.m.containsValue(obj);
            }
            return containsValue;
        }

        public Object get(Object obj) {
            Object obj2;
            synchronized (this.mutex) {
                obj2 = this.m.get(obj);
            }
            return obj2;
        }

        public Object put(Object obj, Object obj2) {
            Object put;
            synchronized (this.mutex) {
                put = this.m.put(obj, obj2);
            }
            return put;
        }

        public Object remove(Object obj) {
            Object remove;
            synchronized (this.mutex) {
                remove = this.m.remove(obj);
            }
            return remove;
        }

        public void putAll(Map map) {
            synchronized (this.mutex) {
                this.m.putAll(map);
            }
        }

        public void clear() {
            synchronized (this.mutex) {
                this.m.clear();
            }
        }

        private Set instantiateSet(Set set, Object obj) {
            if (DesugarCollections.-$$Nest$sfgetSYNCHRONIZED_SET_CONSTRUCTOR() == null) {
                return Collections.synchronizedSet(set);
            }
            try {
                return (Set) DesugarCollections.-$$Nest$sfgetSYNCHRONIZED_SET_CONSTRUCTOR().newInstance(new Object[]{set, obj});
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new Error("Unable to instantiate a synchronized list.", e);
            }
        }

        private Collection instantiateCollection(Collection collection, Object obj) {
            if (DesugarCollections.-$$Nest$sfgetSYNCHRONIZED_COLLECTION_CONSTRUCTOR() == null) {
                return Collections.synchronizedCollection(collection);
            }
            try {
                return (Collection) DesugarCollections.-$$Nest$sfgetSYNCHRONIZED_COLLECTION_CONSTRUCTOR().newInstance(new Object[]{collection, obj});
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new Error("Unable to instantiate a synchronized list.", e);
            }
        }

        public Set keySet() {
            Set set;
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = instantiateSet(this.m.keySet(), this.mutex);
                }
                set = this.keySet;
            }
            return set;
        }

        public Set entrySet() {
            Set set;
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = instantiateSet(this.m.entrySet(), this.mutex);
                }
                set = this.entrySet;
            }
            return set;
        }

        public Collection values() {
            Collection collection;
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = instantiateCollection(this.m.values(), this.mutex);
                }
                collection = this.values;
            }
            return collection;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                equals = this.m.equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.mutex) {
                hashCode = this.m.hashCode();
            }
            return hashCode;
        }

        public String toString() {
            String obj;
            synchronized (this.mutex) {
                obj = this.m.toString();
            }
            return obj;
        }

        public Object getOrDefault(Object obj, Object obj2) {
            Object orDefault;
            synchronized (this.mutex) {
                orDefault = this.m.getOrDefault(obj, obj2);
            }
            return orDefault;
        }

        public void forEach(BiConsumer biConsumer) {
            synchronized (this.mutex) {
                this.m.forEach(biConsumer);
            }
        }

        public void replaceAll(BiFunction biFunction) {
            synchronized (this.mutex) {
                this.m.replaceAll(biFunction);
            }
        }

        public Object putIfAbsent(Object obj, Object obj2) {
            Object putIfAbsent;
            synchronized (this.mutex) {
                putIfAbsent = this.m.putIfAbsent(obj, obj2);
            }
            return putIfAbsent;
        }

        public boolean remove(Object obj, Object obj2) {
            boolean remove;
            synchronized (this.mutex) {
                remove = this.m.remove(obj, obj2);
            }
            return remove;
        }

        public boolean replace(Object obj, Object obj2, Object obj3) {
            boolean replace;
            synchronized (this.mutex) {
                replace = this.m.replace(obj, obj2, obj3);
            }
            return replace;
        }

        public Object replace(Object obj, Object obj2) {
            Object replace;
            synchronized (this.mutex) {
                replace = this.m.replace(obj, obj2);
            }
            return replace;
        }

        public Object computeIfAbsent(Object obj, Function function) {
            Object computeIfAbsent;
            synchronized (this.mutex) {
                computeIfAbsent = this.m.computeIfAbsent(obj, function);
            }
            return computeIfAbsent;
        }

        public Object computeIfPresent(Object obj, BiFunction biFunction) {
            Object computeIfPresent;
            synchronized (this.mutex) {
                computeIfPresent = this.m.computeIfPresent(obj, biFunction);
            }
            return computeIfPresent;
        }

        public Object compute(Object obj, BiFunction biFunction) {
            Object compute;
            synchronized (this.mutex) {
                compute = this.m.compute(obj, biFunction);
            }
            return compute;
        }

        public Object merge(Object obj, Object obj2, BiFunction biFunction) {
            Object merge;
            synchronized (this.mutex) {
                merge = this.m.merge(obj, obj2, biFunction);
            }
            return merge;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }

    public static SortedMap synchronizedSortedMap(SortedMap sortedMap) {
        return new SynchronizedSortedMap(sortedMap);
    }

    static class SynchronizedSortedMap extends SynchronizedMap implements SortedMap {
        private static final long serialVersionUID = -8798146769416483793L;
        private final SortedMap sm;

        SynchronizedSortedMap(SortedMap sortedMap) {
            super(sortedMap);
            this.sm = sortedMap;
        }

        SynchronizedSortedMap(SortedMap sortedMap, Object obj) {
            super(sortedMap, obj);
            this.sm = sortedMap;
        }

        public Comparator comparator() {
            Comparator comparator;
            synchronized (this.mutex) {
                comparator = this.sm.comparator();
            }
            return comparator;
        }

        public SortedMap subMap(Object obj, Object obj2) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(this.sm.subMap(obj, obj2), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public SortedMap headMap(Object obj) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(this.sm.headMap(obj), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public SortedMap tailMap(Object obj) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.mutex) {
                synchronizedSortedMap = new SynchronizedSortedMap(this.sm.tailMap(obj), this.mutex);
            }
            return synchronizedSortedMap;
        }

        public Object firstKey() {
            Object firstKey;
            synchronized (this.mutex) {
                firstKey = this.sm.firstKey();
            }
            return firstKey;
        }

        public Object lastKey() {
            Object lastKey;
            synchronized (this.mutex) {
                lastKey = this.sm.lastKey();
            }
            return lastKey;
        }
    }
}
