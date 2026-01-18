package java.util;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.AbstractList;
import java.util.ImmutableCollections;
import java.util.function.UnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface List extends Collection {
    void add(int i, Object obj);

    boolean add(Object obj);

    boolean addAll(int i, Collection collection);

    boolean addAll(Collection collection);

    void clear();

    boolean contains(Object obj);

    boolean containsAll(Collection collection);

    boolean equals(Object obj);

    Object get(int i);

    int hashCode();

    int indexOf(Object obj);

    boolean isEmpty();

    Iterator iterator();

    int lastIndexOf(Object obj);

    ListIterator listIterator();

    ListIterator listIterator(int i);

    Object remove(int i);

    boolean remove(Object obj);

    boolean removeAll(Collection collection);

    void replaceAll(UnaryOperator unaryOperator);

    boolean retainAll(Collection collection);

    Object set(int i, Object obj);

    int size();

    void sort(Comparator comparator);

    Spliterator spliterator();

    List subList(int i, int i2);

    Object[] toArray();

    Object[] toArray(Object[] objArr);

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static void $default$replaceAll(List _this, UnaryOperator unaryOperator) {
            unaryOperator.getClass();
            ListIterator listIterator = _this.listIterator();
            while (listIterator.hasNext()) {
                listIterator.set(unaryOperator.apply(listIterator.next()));
            }
        }

        public static void $default$sort(List _this, Comparator comparator) {
            Object[] array = _this.toArray();
            Arrays.sort(array, comparator);
            ListIterator listIterator = _this.listIterator();
            for (Object obj : array) {
                listIterator.next();
                listIterator.set(obj);
            }
        }

        public static Spliterator $default$spliterator(List _this) {
            if (_this instanceof RandomAccess) {
                return new AbstractList.RandomAccessSpliterator(_this);
            }
            return Spliterators.spliterator(_this, 16);
        }

        public static List of() {
            return ImmutableCollections.emptyList();
        }

        public static List of(Object obj) {
            return new ImmutableCollections.List12(obj);
        }

        public static List of(Object obj, Object obj2) {
            return new ImmutableCollections.List12(obj, obj2);
        }

        public static List of(Object obj, Object obj2, Object obj3) {
            return new ImmutableCollections.ListN(obj, obj2, obj3);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5, obj6);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5, obj6, obj7);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9);
        }

        public static List of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
            return new ImmutableCollections.ListN(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10);
        }

        @SafeVarargs
        public static List of(Object... objArr) {
            int length = objArr.length;
            if (length == 0) {
                return ImmutableCollections.emptyList();
            }
            if (length == 1) {
                return new ImmutableCollections.List12(objArr[0]);
            }
            if (length == 2) {
                return new ImmutableCollections.List12(objArr[0], objArr[1]);
            }
            return new ImmutableCollections.ListN(objArr);
        }

        public static List copyOf(Collection collection) {
            return ImmutableCollections.listCopy(collection);
        }
    }
}
