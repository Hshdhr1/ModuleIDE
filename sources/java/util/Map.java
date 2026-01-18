package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.ImmutableCollections;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Map {
    void clear();

    Object compute(Object obj, BiFunction biFunction);

    Object computeIfAbsent(Object obj, Function function);

    Object computeIfPresent(Object obj, BiFunction biFunction);

    boolean containsKey(Object obj);

    boolean containsValue(Object obj);

    Set entrySet();

    boolean equals(Object obj);

    void forEach(BiConsumer biConsumer);

    Object get(Object obj);

    Object getOrDefault(Object obj, Object obj2);

    int hashCode();

    boolean isEmpty();

    Set keySet();

    Object merge(Object obj, Object obj2, BiFunction biFunction);

    Object put(Object obj, Object obj2);

    void putAll(Map map);

    Object putIfAbsent(Object obj, Object obj2);

    Object remove(Object obj);

    boolean remove(Object obj, Object obj2);

    Object replace(Object obj, Object obj2);

    boolean replace(Object obj, Object obj2, Object obj3);

    void replaceAll(BiFunction biFunction);

    int size();

    Collection values();

    public interface Entry {
        boolean equals(Object obj);

        Object getKey();

        Object getValue();

        int hashCode();

        Object setValue(Object obj);

        @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
        public final /* synthetic */ class -CC {
            public static Comparator comparingByKey() {
                return new Map$Entry$$ExternalSyntheticLambda2();
            }

            public static /* synthetic */ int lambda$comparingByKey$bbdbfea9$1(Entry entry, Entry entry2) {
                return ((Comparable) entry.getKey()).compareTo(entry2.getKey());
            }

            public static Comparator comparingByValue() {
                return new Map$Entry$$ExternalSyntheticLambda1();
            }

            public static /* synthetic */ int lambda$comparingByValue$1065357e$1(Entry entry, Entry entry2) {
                return ((Comparable) entry.getValue()).compareTo(entry2.getValue());
            }

            public static Comparator comparingByKey(Comparator comparator) {
                comparator.getClass();
                return new Map$Entry$$ExternalSyntheticLambda3(comparator);
            }

            public static /* synthetic */ int lambda$comparingByKey$6d558cbf$1(Comparator comparator, Entry entry, Entry entry2) {
                return comparator.compare(entry.getKey(), entry2.getKey());
            }

            public static Comparator comparingByValue(Comparator comparator) {
                comparator.getClass();
                return new Map$Entry$$ExternalSyntheticLambda0(comparator);
            }

            public static /* synthetic */ int lambda$comparingByValue$827a17d5$1(Comparator comparator, Entry entry, Entry entry2) {
                return comparator.compare(entry.getValue(), entry2.getValue());
            }
        }
    }

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Object $default$getOrDefault(Map _this, Object obj, Object obj2) {
            Object obj3 = _this.get(obj);
            return (obj3 != null || _this.containsKey(obj)) ? obj3 : obj2;
        }

        public static void $default$forEach(Map _this, BiConsumer biConsumer) {
            biConsumer.getClass();
            for (Entry entry : _this.entrySet()) {
                try {
                    biConsumer.accept(entry.getKey(), entry.getValue());
                } catch (IllegalStateException e) {
                    throw new ConcurrentModificationException(e);
                }
            }
        }

        public static void $default$replaceAll(Map _this, BiFunction biFunction) {
            biFunction.getClass();
            for (Entry entry : _this.entrySet()) {
                try {
                    try {
                        entry.setValue(biFunction.apply(entry.getKey(), entry.getValue()));
                    } catch (IllegalStateException e) {
                        throw new ConcurrentModificationException(e);
                    }
                } catch (IllegalStateException e2) {
                    throw new ConcurrentModificationException(e2);
                }
            }
        }

        public static Object $default$putIfAbsent(Map _this, Object obj, Object obj2) {
            Object obj3 = _this.get(obj);
            return obj3 == null ? _this.put(obj, obj2) : obj3;
        }

        public static boolean $default$remove(Map _this, Object obj, Object obj2) {
            Object obj3 = _this.get(obj);
            if (!Map$$ExternalSyntheticBackport0.m(obj3, obj2)) {
                return false;
            }
            if (obj3 == null && !_this.containsKey(obj)) {
                return false;
            }
            _this.remove(obj);
            return true;
        }

        public static boolean $default$replace(Map _this, Object obj, Object obj2, Object obj3) {
            Object obj4 = _this.get(obj);
            if (!Map$$ExternalSyntheticBackport0.m(obj4, obj2)) {
                return false;
            }
            if (obj4 == null && !_this.containsKey(obj)) {
                return false;
            }
            _this.put(obj, obj3);
            return true;
        }

        public static Object $default$replace(Map _this, Object obj, Object obj2) {
            Object obj3 = _this.get(obj);
            return (obj3 != null || _this.containsKey(obj)) ? _this.put(obj, obj2) : obj3;
        }

        public static Object $default$computeIfAbsent(Map _this, Object obj, Function function) {
            Object apply;
            function.getClass();
            Object obj2 = _this.get(obj);
            if (obj2 != null || (apply = function.apply(obj)) == null) {
                return obj2;
            }
            _this.put(obj, apply);
            return apply;
        }

        public static Object $default$computeIfPresent(Map _this, Object obj, BiFunction biFunction) {
            biFunction.getClass();
            Object obj2 = _this.get(obj);
            if (obj2 != null) {
                Object apply = biFunction.apply(obj, obj2);
                if (apply != null) {
                    _this.put(obj, apply);
                    return apply;
                }
                _this.remove(obj);
            }
            return null;
        }

        public static Object $default$compute(Map _this, Object obj, BiFunction biFunction) {
            biFunction.getClass();
            Object obj2 = _this.get(obj);
            Object apply = biFunction.apply(obj, obj2);
            if (apply == null) {
                if (obj2 == null && !_this.containsKey(obj)) {
                    return null;
                }
                _this.remove(obj);
                return null;
            }
            _this.put(obj, apply);
            return apply;
        }

        public static Object $default$merge(Map _this, Object obj, Object obj2, BiFunction biFunction) {
            biFunction.getClass();
            obj2.getClass();
            Object obj3 = _this.get(obj);
            if (obj3 != null) {
                obj2 = biFunction.apply(obj3, obj2);
            }
            if (obj2 == null) {
                _this.remove(obj);
                return obj2;
            }
            _this.put(obj, obj2);
            return obj2;
        }

        public static Map of() {
            return ImmutableCollections.emptyMap();
        }

        public static Map of(Object obj, Object obj2) {
            return new ImmutableCollections.Map1(obj, obj2);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14, obj15, obj16);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16, Object obj17, Object obj18) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14, obj15, obj16, obj17, obj18);
        }

        public static Map of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16, Object obj17, Object obj18, Object obj19, Object obj20) {
            return new ImmutableCollections.MapN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10, obj11, obj12, obj13, obj14, obj15, obj16, obj17, obj18, obj19, obj20);
        }

        @SafeVarargs
        public static Map ofEntries(Entry... entryArr) {
            if (entryArr.length == 0) {
                return ImmutableCollections.emptyMap();
            }
            if (entryArr.length == 1) {
                return new ImmutableCollections.Map1(entryArr[0].getKey(), entryArr[0].getValue());
            }
            Object[] objArr = new Object[entryArr.length << 1];
            int i = 0;
            for (Entry entry : entryArr) {
                int i2 = i + 1;
                objArr[i] = entry.getKey();
                i += 2;
                objArr[i2] = entry.getValue();
            }
            return new ImmutableCollections.MapN(objArr);
        }

        public static Entry entry(Object obj, Object obj2) {
            return new KeyValueHolder(obj, obj2);
        }

        public static Map copyOf(Map map) {
            return map instanceof ImmutableCollections.AbstractImmutableMap ? map : Map$$ExternalSyntheticBackport1.m((Entry[]) map.entrySet().toArray(new Entry[0]));
        }
    }
}
