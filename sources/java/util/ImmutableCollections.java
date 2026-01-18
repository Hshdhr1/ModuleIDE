package java.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class ImmutableCollections {
    static final int EXPAND_FACTOR = 2;
    static final int SALT;

    static {
        long nanoTime = System.nanoTime();
        SALT = (int) (nanoTime ^ (nanoTime >>> 32));
    }

    private ImmutableCollections() {
    }

    static UnsupportedOperationException uoe() {
        return new UnsupportedOperationException();
    }

    static abstract class AbstractImmutableCollection extends AbstractCollection {
        AbstractImmutableCollection() {
        }

        public boolean add(Object obj) {
            throw ImmutableCollections.uoe();
        }

        public boolean addAll(Collection collection) {
            throw ImmutableCollections.uoe();
        }

        public void clear() {
            throw ImmutableCollections.uoe();
        }

        public boolean remove(Object obj) {
            throw ImmutableCollections.uoe();
        }

        public boolean removeAll(Collection collection) {
            throw ImmutableCollections.uoe();
        }

        public boolean removeIf(Predicate predicate) {
            throw ImmutableCollections.uoe();
        }

        public boolean retainAll(Collection collection) {
            throw ImmutableCollections.uoe();
        }
    }

    static List listCopy(Collection collection) {
        if ((collection instanceof AbstractImmutableList) && collection.getClass() != SubList.class) {
            return (List) collection;
        }
        return ImmutableCollections$$ExternalSyntheticBackport0.m(collection.toArray());
    }

    static List emptyList() {
        return ListN.EMPTY_LIST;
    }

    static abstract class AbstractImmutableList extends AbstractImmutableCollection implements List, RandomAccess {
        public /* synthetic */ void forEach(Consumer consumer) {
            Collection.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Stream parallelStream() {
            return Collection.-CC.$default$parallelStream(this);
        }

        public /* synthetic */ Spliterator spliterator() {
            return List.-CC.$default$spliterator(this);
        }

        public /* synthetic */ Stream stream() {
            return Collection.-CC.$default$stream(this);
        }

        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return Collection.-CC.$default$toArray(this, intFunction);
        }

        AbstractImmutableList() {
        }

        public void add(int i, Object obj) {
            throw ImmutableCollections.uoe();
        }

        public boolean addAll(int i, Collection collection) {
            throw ImmutableCollections.uoe();
        }

        public Object remove(int i) {
            throw ImmutableCollections.uoe();
        }

        public void replaceAll(UnaryOperator unaryOperator) {
            throw ImmutableCollections.uoe();
        }

        public Object set(int i, Object obj) {
            throw ImmutableCollections.uoe();
        }

        public void sort(Comparator comparator) {
            throw ImmutableCollections.uoe();
        }

        public List subList(int i, int i2) {
            subListRangeCheck(i, i2, size());
            return SubList.fromList(this, i, i2);
        }

        static void subListRangeCheck(int i, int i2, int i3) {
            if (i < 0) {
                throw new IndexOutOfBoundsException("fromIndex = " + i);
            }
            if (i2 > i3) {
                throw new IndexOutOfBoundsException("toIndex = " + i2);
            }
            if (i <= i2) {
                return;
            }
            throw new IllegalArgumentException("fromIndex(" + i + ") > toIndex(" + i2 + ")");
        }

        public Iterator iterator() {
            return new ListItr(this, size());
        }

        public ListIterator listIterator() {
            return listIterator(0);
        }

        public ListIterator listIterator(int i) {
            int size = size();
            if (i < 0 || i > size) {
                throw outOfBounds(i);
            }
            return new ListItr(this, size, i);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof List)) {
                return false;
            }
            Iterator it = ((List) obj).iterator();
            int size = size();
            for (int i = 0; i < size; i++) {
                if (!it.hasNext() || !get(i).equals(it.next())) {
                    return false;
                }
            }
            return !it.hasNext();
        }

        public int indexOf(Object obj) {
            obj.getClass();
            int size = size();
            for (int i = 0; i < size; i++) {
                if (obj.equals(get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object obj) {
            obj.getClass();
            for (int size = size() - 1; size >= 0; size--) {
                if (obj.equals(get(size))) {
                    return size;
                }
            }
            return -1;
        }

        public int hashCode() {
            int size = size();
            int i = 1;
            for (int i2 = 0; i2 < size; i2++) {
                i = (i * 31) + get(i2).hashCode();
            }
            return i;
        }

        public boolean contains(Object obj) {
            return indexOf(obj) >= 0;
        }

        IndexOutOfBoundsException outOfBounds(int i) {
            return new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
    }

    static final class ListItr implements ListIterator {
        private int cursor;
        private final boolean isListIterator;
        private final List list;
        private final int size;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        ListItr(List list, int i) {
            this.list = list;
            this.size = i;
            this.cursor = 0;
            this.isListIterator = false;
        }

        ListItr(List list, int i, int i2) {
            this.list = list;
            this.size = i;
            this.cursor = i2;
            this.isListIterator = true;
        }

        public boolean hasNext() {
            return this.cursor != this.size;
        }

        public Object next() {
            try {
                int i = this.cursor;
                Object obj = this.list.get(i);
                this.cursor = i + 1;
                return obj;
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw ImmutableCollections.uoe();
        }

        public boolean hasPrevious() {
            if (this.isListIterator) {
                return this.cursor != 0;
            }
            throw ImmutableCollections.uoe();
        }

        public Object previous() {
            if (!this.isListIterator) {
                throw ImmutableCollections.uoe();
            }
            try {
                int i = this.cursor - 1;
                Object obj = this.list.get(i);
                this.cursor = i;
                return obj;
            } catch (IndexOutOfBoundsException unused) {
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            if (!this.isListIterator) {
                throw ImmutableCollections.uoe();
            }
            return this.cursor;
        }

        public int previousIndex() {
            if (!this.isListIterator) {
                throw ImmutableCollections.uoe();
            }
            return this.cursor - 1;
        }

        public void set(Object obj) {
            throw ImmutableCollections.uoe();
        }

        public void add(Object obj) {
            throw ImmutableCollections.uoe();
        }
    }

    static final class SubList extends AbstractImmutableList implements RandomAccess {
        private final int offset;
        private final List root;
        private final int size;

        private SubList(List list, int i, int i2) {
            this.root = list;
            this.offset = i;
            this.size = i2;
        }

        static SubList fromSubList(SubList subList, int i, int i2) {
            return new SubList(subList.root, subList.offset + i, i2 - i);
        }

        static SubList fromList(List list, int i, int i2) {
            return new SubList(list, i, i2 - i);
        }

        public Object get(int i) {
            ImmutableCollections$SubList$$ExternalSyntheticBackport0.m(i, this.size);
            return this.root.get(this.offset + i);
        }

        public int size() {
            return this.size;
        }

        public Iterator iterator() {
            return new ListItr(this, size());
        }

        public ListIterator listIterator(int i) {
            rangeCheck(i);
            return new ListItr(this, size(), i);
        }

        public List subList(int i, int i2) {
            subListRangeCheck(i, i2, this.size);
            return fromSubList(this, i, i2);
        }

        private void rangeCheck(int i) {
            if (i < 0 || i > this.size) {
                throw outOfBounds(i);
            }
        }
    }

    static final class List12 extends AbstractImmutableList implements Serializable {
        private final Object e0;
        private final Object e1;

        List12(Object obj) {
            obj.getClass();
            this.e0 = obj;
            this.e1 = null;
        }

        List12(Object obj, Object obj2) {
            obj.getClass();
            this.e0 = obj;
            obj2.getClass();
            this.e1 = obj2;
        }

        public int size() {
            return this.e1 != null ? 2 : 1;
        }

        public Object get(int i) {
            Object obj;
            if (i == 0) {
                return this.e0;
            }
            if (i != 1 || (obj = this.e1) == null) {
                throw outOfBounds(i);
            }
            return obj;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            if (this.e1 == null) {
                return new CollSer(1, this.e0);
            }
            return new CollSer(1, this.e0, this.e1);
        }
    }

    static final class ListN extends AbstractImmutableList implements Serializable {
        static List EMPTY_LIST;
        private final Object[] elements;

        static {
            if (EMPTY_LIST == null) {
                EMPTY_LIST = new ListN(new Object[0]);
            }
        }

        @SafeVarargs
        ListN(Object... objArr) {
            Object[] objArr2 = new Object[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                obj.getClass();
                objArr2[i] = obj;
            }
            this.elements = objArr2;
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public int size() {
            return this.elements.length;
        }

        public Object get(int i) {
            return this.elements[i];
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            return new CollSer(1, this.elements);
        }
    }

    static abstract class AbstractImmutableSet extends AbstractImmutableCollection implements Set {
        public /* synthetic */ void forEach(Consumer consumer) {
            Collection.-CC.$default$forEach(this, consumer);
        }

        public abstract int hashCode();

        public /* synthetic */ Stream parallelStream() {
            return Collection.-CC.$default$parallelStream(this);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Set.-CC.$default$spliterator(this);
        }

        public /* synthetic */ Stream stream() {
            return Collection.-CC.$default$stream(this);
        }

        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return Collection.-CC.$default$toArray(this, intFunction);
        }

        AbstractImmutableSet() {
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Set)) {
                return false;
            }
            Collection collection = (Collection) obj;
            if (collection.size() != size()) {
                return false;
            }
            for (Object obj2 : collection) {
                if (obj2 == null || !contains(obj2)) {
                    return false;
                }
            }
            return true;
        }
    }

    static Set emptySet() {
        return SetN.EMPTY_SET;
    }

    static final class Set12 extends AbstractImmutableSet implements Serializable {
        final Object e0;
        final Object e1;

        Set12(Object obj) {
            obj.getClass();
            this.e0 = obj;
            this.e1 = null;
        }

        Set12(Object obj, Object obj2) {
            obj2.getClass();
            if (obj.equals(obj2)) {
                throw new IllegalArgumentException("duplicate element: " + obj);
            }
            this.e0 = obj;
            this.e1 = obj2;
        }

        public int size() {
            return this.e1 == null ? 1 : 2;
        }

        public boolean contains(Object obj) {
            return obj.equals(this.e0) || obj.equals(this.e1);
        }

        public int hashCode() {
            int hashCode = this.e0.hashCode();
            Object obj = this.e1;
            return hashCode + (obj == null ? 0 : obj.hashCode());
        }

        class 1 implements Iterator {
            private int idx;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            public /* synthetic */ void remove() {
                Iterator.-CC.$default$remove(this);
            }

            1() {
                this.idx = Set12.this.size();
            }

            public boolean hasNext() {
                return this.idx > 0;
            }

            public Object next() {
                int i = this.idx;
                if (i == 1) {
                    this.idx = 0;
                    return (ImmutableCollections.SALT >= 0 || Set12.this.e1 == null) ? Set12.this.e0 : Set12.this.e1;
                }
                if (i == 2) {
                    this.idx = 1;
                    return ImmutableCollections.SALT >= 0 ? Set12.this.e1 : Set12.this.e0;
                }
                throw new NoSuchElementException();
            }
        }

        public Iterator iterator() {
            return new 1();
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            if (this.e1 == null) {
                return new CollSer(2, this.e0);
            }
            return new CollSer(2, this.e0, this.e1);
        }
    }

    static final class SetN extends AbstractImmutableSet implements Serializable {
        static Set EMPTY_SET;
        final Object[] elements;
        final int size;

        static {
            if (EMPTY_SET == null) {
                EMPTY_SET = new SetN(new Object[0]);
            }
        }

        @SafeVarargs
        SetN(Object... objArr) {
            this.size = objArr.length;
            this.elements = new Object[objArr.length * 2];
            for (Object obj : objArr) {
                int probe = probe(obj);
                if (probe >= 0) {
                    throw new IllegalArgumentException("duplicate element: " + obj);
                }
                this.elements[-(probe + 1)] = obj;
            }
        }

        public int size() {
            return this.size;
        }

        public boolean contains(Object obj) {
            obj.getClass();
            return this.size > 0 && probe(obj) >= 0;
        }

        private final class SetNIterator implements Iterator {
            private int idx;
            private int remaining;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            public /* synthetic */ void remove() {
                Iterator.-CC.$default$remove(this);
            }

            SetNIterator() {
                int size = SetN.this.size();
                this.remaining = size;
                if (size > 0) {
                    this.idx = ImmutableCollections$SetN$SetNIterator$$ExternalSyntheticBackport0.m(ImmutableCollections.SALT, SetN.this.elements.length);
                }
            }

            public boolean hasNext() {
                return this.remaining > 0;
            }

            private int nextIndex() {
                int i;
                int i2 = this.idx;
                if (ImmutableCollections.SALT >= 0) {
                    i = i2 + 1;
                    if (i >= SetN.this.elements.length) {
                        i = 0;
                    }
                } else {
                    i = i2 - 1;
                    if (i < 0) {
                        i = SetN.this.elements.length - 1;
                    }
                }
                this.idx = i;
                return i;
            }

            public Object next() {
                Object obj;
                if (hasNext()) {
                    do {
                        obj = SetN.this.elements[nextIndex()];
                    } while (obj == null);
                    this.remaining--;
                    return obj;
                }
                throw new NoSuchElementException();
            }
        }

        public Iterator iterator() {
            return new SetNIterator();
        }

        public int hashCode() {
            int i = 0;
            for (Object obj : this.elements) {
                if (obj != null) {
                    i += obj.hashCode();
                }
            }
            return i;
        }

        private int probe(Object obj) {
            int m = ImmutableCollections$SetN$$ExternalSyntheticBackport0.m(obj.hashCode(), this.elements.length);
            while (true) {
                Object obj2 = this.elements[m];
                if (obj2 == null) {
                    return (-m) - 1;
                }
                if (obj.equals(obj2)) {
                    return m;
                }
                m++;
                if (m == this.elements.length) {
                    m = 0;
                }
            }
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            Object[] objArr = new Object[this.size];
            int i = 0;
            for (Object obj : this.elements) {
                if (obj != null) {
                    objArr[i] = obj;
                    i++;
                }
            }
            return new CollSer(2, objArr);
        }
    }

    static Map emptyMap() {
        return MapN.EMPTY_MAP;
    }

    static abstract class AbstractImmutableMap extends AbstractMap implements Serializable {
        AbstractImmutableMap() {
        }

        public void clear() {
            throw ImmutableCollections.uoe();
        }

        public Object compute(Object obj, BiFunction biFunction) {
            throw ImmutableCollections.uoe();
        }

        public Object computeIfAbsent(Object obj, Function function) {
            throw ImmutableCollections.uoe();
        }

        public Object computeIfPresent(Object obj, BiFunction biFunction) {
            throw ImmutableCollections.uoe();
        }

        public Object merge(Object obj, Object obj2, BiFunction biFunction) {
            throw ImmutableCollections.uoe();
        }

        public Object put(Object obj, Object obj2) {
            throw ImmutableCollections.uoe();
        }

        public void putAll(Map map) {
            throw ImmutableCollections.uoe();
        }

        public Object putIfAbsent(Object obj, Object obj2) {
            throw ImmutableCollections.uoe();
        }

        public Object remove(Object obj) {
            throw ImmutableCollections.uoe();
        }

        public boolean remove(Object obj, Object obj2) {
            throw ImmutableCollections.uoe();
        }

        public Object replace(Object obj, Object obj2) {
            throw ImmutableCollections.uoe();
        }

        public boolean replace(Object obj, Object obj2, Object obj3) {
            throw ImmutableCollections.uoe();
        }

        public void replaceAll(BiFunction biFunction) {
            throw ImmutableCollections.uoe();
        }
    }

    static final class Map1 extends AbstractImmutableMap {
        private final Object k0;
        private final Object v0;

        Map1(Object obj, Object obj2) {
            obj.getClass();
            this.k0 = obj;
            obj2.getClass();
            this.v0 = obj2;
        }

        public Set entrySet() {
            return ImmutableCollections$Map1$$ExternalSyntheticBackport1.m(new KeyValueHolder(this.k0, this.v0));
        }

        public Object get(Object obj) {
            if (obj.equals(this.k0)) {
                return this.v0;
            }
            return null;
        }

        public boolean containsKey(Object obj) {
            return obj.equals(this.k0);
        }

        public boolean containsValue(Object obj) {
            return obj.equals(this.v0);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            return new CollSer(3, this.k0, this.v0);
        }

        public int hashCode() {
            return this.k0.hashCode() ^ this.v0.hashCode();
        }
    }

    static final class MapN extends AbstractImmutableMap {
        static Map EMPTY_MAP;
        final int size;
        final Object[] table;

        static {
            if (EMPTY_MAP == null) {
                EMPTY_MAP = new MapN(new Object[0]);
            }
        }

        MapN(Object... objArr) {
            if ((objArr.length & 1) != 0) {
                throw new InternalError("length is odd");
            }
            this.size = objArr.length >> 1;
            this.table = new Object[((objArr.length * 2) + 1) & (-2)];
            for (int i = 0; i < objArr.length; i += 2) {
                Object obj = objArr[i];
                obj.getClass();
                Object obj2 = objArr[i + 1];
                obj2.getClass();
                int probe = probe(obj);
                if (probe >= 0) {
                    throw new IllegalArgumentException("duplicate key: " + obj);
                }
                int i2 = -(probe + 1);
                Object[] objArr2 = this.table;
                objArr2[i2] = obj;
                objArr2[i2 + 1] = obj2;
            }
        }

        public boolean containsKey(Object obj) {
            obj.getClass();
            return this.size > 0 && probe(obj) >= 0;
        }

        public boolean containsValue(Object obj) {
            obj.getClass();
            int i = 1;
            while (true) {
                Object[] objArr = this.table;
                if (i >= objArr.length) {
                    return false;
                }
                Object obj2 = objArr[i];
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
                i += 2;
            }
        }

        public int hashCode() {
            int i = 0;
            int i2 = 0;
            while (true) {
                Object[] objArr = this.table;
                if (i >= objArr.length) {
                    return i2;
                }
                Object obj = objArr[i];
                if (obj != null) {
                    i2 += obj.hashCode() ^ this.table[i + 1].hashCode();
                }
                i += 2;
            }
        }

        public Object get(Object obj) {
            if (this.size == 0) {
                obj.getClass();
                return null;
            }
            int probe = probe(obj);
            if (probe >= 0) {
                return this.table[probe + 1];
            }
            return null;
        }

        public int size() {
            return this.size;
        }

        class MapNIterator implements Iterator {
            private int idx;
            private int remaining;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            public /* synthetic */ void remove() {
                Iterator.-CC.$default$remove(this);
            }

            MapNIterator() {
                int size = MapN.this.size();
                this.remaining = size;
                if (size > 0) {
                    this.idx = ImmutableCollections$MapN$MapNIterator$$ExternalSyntheticBackport0.m(ImmutableCollections.SALT, MapN.this.table.length >> 1) << 1;
                }
            }

            public boolean hasNext() {
                return this.remaining > 0;
            }

            private int nextIndex() {
                int i;
                int i2 = this.idx;
                if (ImmutableCollections.SALT >= 0) {
                    i = i2 + 2;
                    if (i >= MapN.this.table.length) {
                        i = 0;
                    }
                } else {
                    i = i2 - 2;
                    if (i < 0) {
                        i = MapN.this.table.length - 2;
                    }
                }
                this.idx = i;
                return i;
            }

            public Map.Entry next() {
                if (hasNext()) {
                    while (MapN.this.table[nextIndex()] == null) {
                    }
                    this.remaining--;
                    return new KeyValueHolder(MapN.this.table[this.idx], MapN.this.table[this.idx + 1]);
                }
                throw new NoSuchElementException();
            }
        }

        class 1 extends AbstractSet {
            1() {
            }

            public int size() {
                return MapN.this.size;
            }

            public Iterator iterator() {
                return MapN.this.new MapNIterator();
            }
        }

        public Set entrySet() {
            return new 1();
        }

        private int probe(Object obj) {
            int m = ImmutableCollections$MapN$$ExternalSyntheticBackport0.m(obj.hashCode(), this.table.length >> 1) << 1;
            while (true) {
                Object obj2 = this.table[m];
                if (obj2 == null) {
                    return (-m) - 1;
                }
                if (obj.equals(obj2)) {
                    return m;
                }
                m += 2;
                if (m == this.table.length) {
                    m = 0;
                }
            }
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            throw new InvalidObjectException("not serial proxy");
        }

        private Object writeReplace() {
            Object[] objArr = new Object[this.size * 2];
            int length = this.table.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2 += 2) {
                Object[] objArr2 = this.table;
                Object obj = objArr2[i2];
                if (obj != null) {
                    int i3 = i + 1;
                    objArr[i] = obj;
                    i += 2;
                    objArr[i3] = objArr2[i2 + 1];
                }
            }
            return new CollSer(3, objArr);
        }
    }
}
