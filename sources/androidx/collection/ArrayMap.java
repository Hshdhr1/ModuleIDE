package androidx.collection;

import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes5.dex */
public class ArrayMap extends SimpleArrayMap implements Map {
    EntrySet mEntrySet;
    KeySet mKeySet;
    ValueCollection mValues;

    public ArrayMap() {
    }

    public ArrayMap(int capacity) {
        super(capacity);
    }

    public ArrayMap(SimpleArrayMap map) {
        super(map);
    }

    public boolean containsAll(Collection collection) {
        for (Object o : collection) {
            if (!containsKey(o)) {
                return false;
            }
        }
        return true;
    }

    public void putAll(Map map) {
        ensureCapacity(this.mSize + map.size());
        for (Map.Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(Collection collection) {
        int oldSize = this.mSize;
        for (Object o : collection) {
            remove(o);
        }
        return oldSize != this.mSize;
    }

    public boolean retainAll(Collection collection) {
        int oldSize = this.mSize;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(keyAt(i))) {
                removeAt(i);
            }
        }
        int i2 = this.mSize;
        return oldSize != i2;
    }

    public Set entrySet() {
        EntrySet entrySet = this.mEntrySet;
        if (entrySet == null) {
            EntrySet entrySet2 = new EntrySet();
            this.mEntrySet = entrySet2;
            return entrySet2;
        }
        return entrySet;
    }

    public Set keySet() {
        KeySet keySet = this.mKeySet;
        if (keySet == null) {
            KeySet keySet2 = new KeySet();
            this.mKeySet = keySet2;
            return keySet2;
        }
        return keySet;
    }

    public Collection values() {
        ValueCollection valueCollection = this.mValues;
        if (valueCollection == null) {
            ValueCollection valueCollection2 = new ValueCollection();
            this.mValues = valueCollection2;
            return valueCollection2;
        }
        return valueCollection;
    }

    final class EntrySet extends AbstractSet {
        EntrySet() {
        }

        public Iterator iterator() {
            return ArrayMap.this.new MapIterator();
        }

        public int size() {
            return ArrayMap.this.mSize;
        }
    }

    final class KeySet implements Set {
        KeySet() {
        }

        public boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            ArrayMap.this.clear();
        }

        public boolean contains(Object object) {
            return ArrayMap.this.containsKey(object);
        }

        public boolean containsAll(Collection collection) {
            return ArrayMap.this.containsAll(collection);
        }

        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        public Iterator iterator() {
            return ArrayMap.this.new KeyIterator();
        }

        public boolean remove(Object object) {
            int index = ArrayMap.this.indexOfKey(object);
            if (index >= 0) {
                ArrayMap.this.removeAt(index);
                return true;
            }
            return false;
        }

        public boolean removeAll(Collection collection) {
            return ArrayMap.this.removeAll(collection);
        }

        public boolean retainAll(Collection collection) {
            return ArrayMap.this.retainAll(collection);
        }

        public int size() {
            return ArrayMap.this.mSize;
        }

        public Object[] toArray() {
            int N = ArrayMap.this.mSize;
            Object[] result = new Object[N];
            for (int i = 0; i < N; i++) {
                result[i] = ArrayMap.this.keyAt(i);
            }
            return result;
        }

        public Object[] toArray(Object[] objArr) {
            return ArrayMap.this.toArrayHelper(objArr, 0);
        }

        public boolean equals(Object object) {
            return ArrayMap.equalsSetHelper(this, object);
        }

        public int hashCode() {
            int result = 0;
            for (int i = ArrayMap.this.mSize - 1; i >= 0; i--) {
                Object keyAt = ArrayMap.this.keyAt(i);
                result += keyAt == null ? 0 : keyAt.hashCode();
            }
            return result;
        }
    }

    final class ValueCollection implements Collection {
        ValueCollection() {
        }

        public boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            ArrayMap.this.clear();
        }

        public boolean contains(Object object) {
            return ArrayMap.this.indexOfValue(object) >= 0;
        }

        public boolean containsAll(Collection collection) {
            for (Object o : collection) {
                if (!contains(o)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        public Iterator iterator() {
            return ArrayMap.this.new ValueIterator();
        }

        public boolean remove(Object object) {
            int index = ArrayMap.this.indexOfValue(object);
            if (index >= 0) {
                ArrayMap.this.removeAt(index);
                return true;
            }
            return false;
        }

        public boolean removeAll(Collection collection) {
            int N = ArrayMap.this.mSize;
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (collection.contains(ArrayMap.this.valueAt(i))) {
                    ArrayMap.this.removeAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public boolean retainAll(Collection collection) {
            int N = ArrayMap.this.mSize;
            boolean changed = false;
            int i = 0;
            while (i < N) {
                if (!collection.contains(ArrayMap.this.valueAt(i))) {
                    ArrayMap.this.removeAt(i);
                    i--;
                    N--;
                    changed = true;
                }
                i++;
            }
            return changed;
        }

        public int size() {
            return ArrayMap.this.mSize;
        }

        public Object[] toArray() {
            int N = ArrayMap.this.mSize;
            Object[] result = new Object[N];
            for (int i = 0; i < N; i++) {
                result[i] = ArrayMap.this.valueAt(i);
            }
            return result;
        }

        public Object[] toArray(Object[] objArr) {
            return ArrayMap.this.toArrayHelper(objArr, 1);
        }
    }

    final class KeyIterator extends IndexBasedArrayIterator {
        KeyIterator() {
            super(ArrayMap.this.mSize);
        }

        protected Object elementAt(int index) {
            return ArrayMap.this.keyAt(index);
        }

        protected void removeAt(int index) {
            ArrayMap.this.removeAt(index);
        }
    }

    final class ValueIterator extends IndexBasedArrayIterator {
        ValueIterator() {
            super(ArrayMap.this.mSize);
        }

        protected Object elementAt(int index) {
            return ArrayMap.this.valueAt(index);
        }

        protected void removeAt(int index) {
            ArrayMap.this.removeAt(index);
        }
    }

    final class MapIterator implements Iterator, Map.Entry {
        int mEnd;
        boolean mEntryValid;
        int mIndex = -1;

        MapIterator() {
            this.mEnd = ArrayMap.this.mSize - 1;
        }

        public boolean hasNext() {
            return this.mIndex < this.mEnd;
        }

        public Map.Entry next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }

        public void remove() {
            if (!this.mEntryValid) {
                throw new IllegalStateException();
            }
            ArrayMap.this.removeAt(this.mIndex);
            this.mIndex--;
            this.mEnd--;
            this.mEntryValid = false;
        }

        public Object getKey() {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            return ArrayMap.this.keyAt(this.mIndex);
        }

        public Object getValue() {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            return ArrayMap.this.valueAt(this.mIndex);
        }

        public Object setValue(Object obj) {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            return ArrayMap.this.setValueAt(this.mIndex, obj);
        }

        public boolean equals(Object o) {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            return ContainerHelpers.equal(e.getKey(), ArrayMap.this.keyAt(this.mIndex)) && ContainerHelpers.equal(e.getValue(), ArrayMap.this.valueAt(this.mIndex));
        }

        public int hashCode() {
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object keyAt = ArrayMap.this.keyAt(this.mIndex);
            Object valueAt = ArrayMap.this.valueAt(this.mIndex);
            return (valueAt != null ? valueAt.hashCode() : 0) ^ (keyAt == null ? 0 : keyAt.hashCode());
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    Object[] toArrayHelper(Object[] objArr, int offset) {
        int N = this.mSize;
        if (objArr.length < N) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), N);
        }
        for (int i = 0; i < N; i++) {
            objArr[i] = this.mArray[(i << 1) + offset];
        }
        int i2 = objArr.length;
        if (i2 > N) {
            objArr[N] = null;
        }
        return objArr;
    }

    static boolean equalsSetHelper(Set set, Object object) {
        if (set == object) {
            return true;
        }
        if (object instanceof Set) {
            Set<?> s = (Set) object;
            try {
                if (set.size() == s.size()) {
                    if (set.containsAll(s)) {
                        return true;
                    }
                }
                return false;
            } catch (NullPointerException e) {
            } catch (ClassCastException e2) {
            }
        }
        return false;
    }
}
