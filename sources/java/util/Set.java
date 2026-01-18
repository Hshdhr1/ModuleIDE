package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.ImmutableCollections;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Set extends Collection {
    boolean add(Object obj);

    boolean addAll(Collection collection);

    void clear();

    boolean contains(Object obj);

    boolean containsAll(Collection collection);

    boolean equals(Object obj);

    int hashCode();

    boolean isEmpty();

    Iterator iterator();

    boolean remove(Object obj);

    boolean removeAll(Collection collection);

    boolean retainAll(Collection collection);

    int size();

    Spliterator spliterator();

    Object[] toArray();

    Object[] toArray(Object[] objArr);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Spliterator $default$spliterator(Set _this) {
            return Spliterators.spliterator(_this, 1);
        }

        public static Set of() {
            return ImmutableCollections.emptySet();
        }

        public static Set of(Object obj) {
            return new ImmutableCollections.Set12(obj);
        }

        public static Set of(Object obj, Object obj2) {
            return new ImmutableCollections.Set12(obj, obj2);
        }

        public static Set of(Object obj, Object obj2, Object obj3) {
            return new ImmutableCollections.SetN(obj, obj2, obj3);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5, obj6);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5, obj6, obj7);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9);
        }

        public static Set of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
            return new ImmutableCollections.SetN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10);
        }

        @SafeVarargs
        public static Set of(Object... objArr) {
            int length = objArr.length;
            if (length == 0) {
                return ImmutableCollections.emptySet();
            }
            if (length == 1) {
                return new ImmutableCollections.Set12(objArr[0]);
            }
            if (length == 2) {
                return new ImmutableCollections.Set12(objArr[0], objArr[1]);
            }
            return new ImmutableCollections.SetN(objArr);
        }

        public static Set copyOf(Collection collection) {
            if (collection instanceof ImmutableCollections.AbstractImmutableSet) {
                return (Set) collection;
            }
            return Set$$ExternalSyntheticBackport0.m(new HashSet(collection).toArray());
        }
    }
}
