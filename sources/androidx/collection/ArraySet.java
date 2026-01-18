package androidx.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes5.dex */
public final class ArraySet implements Collection, Set {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";
    private static Object[] sBaseCache;
    private static int sBaseCacheSize;
    private static Object[] sTwiceBaseCache;
    private static int sTwiceBaseCacheSize;
    Object[] mArray;
    private int[] mHashes;
    int mSize;
    private static final Object sBaseCacheLock = new Object();
    private static final Object sTwiceBaseCacheLock = new Object();

    private int binarySearch(int hash) {
        try {
            return ContainerHelpers.binarySearch(this.mHashes, this.mSize, hash);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }

    private int indexOf(Object key, int hash) {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearch(hash);
        if (index < 0) {
            return index;
        }
        if (key.equals(this.mArray[index])) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == hash) {
            if (key.equals(this.mArray[end])) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == hash; i--) {
            if (key.equals(this.mArray[i])) {
                return i;
            }
        }
        int i2 = end ^ (-1);
        return i2;
    }

    private int indexOfNull() {
        int N = this.mSize;
        if (N == 0) {
            return -1;
        }
        int index = binarySearch(0);
        if (index < 0) {
            return index;
        }
        if (this.mArray[index] == null) {
            return index;
        }
        int end = index + 1;
        while (end < N && this.mHashes[end] == 0) {
            if (this.mArray[end] == null) {
                return end;
            }
            end++;
        }
        for (int i = index - 1; i >= 0 && this.mHashes[i] == 0; i--) {
            if (this.mArray[i] == null) {
                return i;
            }
        }
        int i2 = end ^ (-1);
        return i2;
    }

    private void allocArrays(int size) {
        int[] iArr;
        int[] iArr2;
        if (size == 8) {
            synchronized (sTwiceBaseCacheLock) {
                Object[] array = sTwiceBaseCache;
                if (array != null) {
                    try {
                        this.mArray = array;
                        sTwiceBaseCache = (Object[]) array[0];
                        iArr2 = (int[]) array[1];
                        this.mHashes = iArr2;
                    } catch (ClassCastException e) {
                    }
                    if (iArr2 != null) {
                        array[1] = null;
                        array[0] = null;
                        sTwiceBaseCacheSize--;
                        return;
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + array[0] + " [1]=" + array[1]);
                    sTwiceBaseCache = null;
                    sTwiceBaseCacheSize = 0;
                }
            }
        } else if (size == 4) {
            synchronized (sBaseCacheLock) {
                Object[] array2 = sBaseCache;
                if (array2 != null) {
                    try {
                        this.mArray = array2;
                        sBaseCache = (Object[]) array2[0];
                        iArr = (int[]) array2[1];
                        this.mHashes = iArr;
                    } catch (ClassCastException e2) {
                    }
                    if (iArr != null) {
                        array2[1] = null;
                        array2[0] = null;
                        sBaseCacheSize--;
                        return;
                    }
                    System.out.println("ArraySet Found corrupt ArraySet cache: [0]=" + array2[0] + " [1]=" + array2[1]);
                    sBaseCache = null;
                    sBaseCacheSize = 0;
                }
            }
        }
        this.mHashes = new int[size];
        this.mArray = new Object[size];
    }

    private static void freeArrays(int[] hashes, Object[] array, int size) {
        if (hashes.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCacheSize < 10) {
                    array[0] = sTwiceBaseCache;
                    array[1] = hashes;
                    for (int i = size - 1; i >= 2; i--) {
                        array[i] = null;
                    }
                    sTwiceBaseCache = array;
                    sTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (hashes.length == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCacheSize < 10) {
                    array[0] = sBaseCache;
                    array[1] = hashes;
                    for (int i2 = size - 1; i2 >= 2; i2--) {
                        array[i2] = null;
                    }
                    sBaseCache = array;
                    sBaseCacheSize++;
                }
            }
        }
    }

    public ArraySet() {
        this(0);
    }

    public ArraySet(int capacity) {
        if (capacity == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(capacity);
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet arraySet) {
        this();
        if (arraySet != null) {
            addAll(arraySet);
        }
    }

    public ArraySet(Collection collection) {
        this();
        if (collection != null) {
            addAll(collection);
        }
    }

    public ArraySet(Object[] objArr) {
        this();
        if (objArr != null) {
            for (Object obj : objArr) {
                add(obj);
            }
        }
    }

    public void clear() {
        if (this.mSize != 0) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            int osize = this.mSize;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            freeArrays(ohashes, oarray, osize);
        }
        if (this.mSize != 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int minimumCapacity) {
        int oSize = this.mSize;
        if (this.mHashes.length < minimumCapacity) {
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(minimumCapacity);
            int i = this.mSize;
            if (i > 0) {
                System.arraycopy(ohashes, 0, this.mHashes, 0, i);
                System.arraycopy(oarray, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(ohashes, oarray, this.mSize);
        }
        if (this.mSize != oSize) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean contains(Object key) {
        return indexOf(key) >= 0;
    }

    public int indexOf(Object key) {
        return key == null ? indexOfNull() : indexOf(key, key.hashCode());
    }

    public Object valueAt(int index) {
        return this.mArray[index];
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public boolean add(Object obj) {
        int hash;
        int index;
        int oSize = this.mSize;
        if (obj == null) {
            hash = 0;
            index = indexOfNull();
        } else {
            hash = obj.hashCode();
            index = indexOf(obj, hash);
        }
        if (index >= 0) {
            return false;
        }
        int index2 = index ^ (-1);
        if (oSize >= this.mHashes.length) {
            int n = 4;
            if (oSize >= 8) {
                n = (oSize >> 1) + oSize;
            } else if (oSize >= 4) {
                n = 8;
            }
            int[] ohashes = this.mHashes;
            Object[] oarray = this.mArray;
            allocArrays(n);
            if (oSize != this.mSize) {
                throw new ConcurrentModificationException();
            }
            int[] iArr = this.mHashes;
            if (iArr.length > 0) {
                System.arraycopy(ohashes, 0, iArr, 0, ohashes.length);
                System.arraycopy(oarray, 0, this.mArray, 0, oarray.length);
            }
            freeArrays(ohashes, oarray, oSize);
        }
        if (index2 < oSize) {
            int[] iArr2 = this.mHashes;
            System.arraycopy(iArr2, index2, iArr2, index2 + 1, oSize - index2);
            Object[] objArr = this.mArray;
            System.arraycopy(objArr, index2, objArr, index2 + 1, oSize - index2);
        }
        int i = this.mSize;
        if (oSize == i) {
            int[] iArr3 = this.mHashes;
            if (index2 < iArr3.length) {
                iArr3[index2] = hash;
                this.mArray[index2] = obj;
                this.mSize = i + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    public void addAll(ArraySet arraySet) {
        int N = arraySet.mSize;
        ensureCapacity(this.mSize + N);
        if (this.mSize == 0) {
            if (N > 0) {
                System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, N);
                System.arraycopy(arraySet.mArray, 0, this.mArray, 0, N);
                if (this.mSize != 0) {
                    throw new ConcurrentModificationException();
                }
                this.mSize = N;
                return;
            }
            return;
        }
        for (int i = 0; i < N; i++) {
            add(arraySet.valueAt(i));
        }
    }

    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index >= 0) {
            removeAt(index);
            return true;
        }
        return false;
    }

    public Object removeAt(int index) {
        int i;
        int oSize = this.mSize;
        Object old = this.mArray[index];
        if (oSize <= 1) {
            clear();
        } else {
            int nSize = oSize - 1;
            int[] iArr = this.mHashes;
            if (iArr.length > 8 && (i = this.mSize) < iArr.length / 3) {
                int n = i > 8 ? i + (i >> 1) : 8;
                int[] ohashes = this.mHashes;
                Object[] oarray = this.mArray;
                allocArrays(n);
                if (index > 0) {
                    System.arraycopy(ohashes, 0, this.mHashes, 0, index);
                    System.arraycopy(oarray, 0, this.mArray, 0, index);
                }
                if (index < nSize) {
                    System.arraycopy(ohashes, index + 1, this.mHashes, index, nSize - index);
                    System.arraycopy(oarray, index + 1, this.mArray, index, nSize - index);
                }
            } else {
                if (index < nSize) {
                    System.arraycopy(iArr, index + 1, iArr, index, nSize - index);
                    Object[] objArr = this.mArray;
                    System.arraycopy(objArr, index + 1, objArr, index, nSize - index);
                }
                this.mArray[nSize] = null;
            }
            if (oSize != this.mSize) {
                throw new ConcurrentModificationException();
            }
            this.mSize = nSize;
        }
        return old;
    }

    public boolean removeAll(ArraySet arraySet) {
        int N = arraySet.mSize;
        int originalSize = this.mSize;
        for (int i = 0; i < N; i++) {
            remove(arraySet.valueAt(i));
        }
        int i2 = this.mSize;
        return originalSize != i2;
    }

    public int size() {
        return this.mSize;
    }

    public Object[] toArray() {
        int i = this.mSize;
        Object[] result = new Object[i];
        System.arraycopy(this.mArray, 0, result, 0, i);
        return result;
    }

    public Object[] toArray(Object[] objArr) {
        if (objArr.length < this.mSize) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, objArr, 0, this.mSize);
        int length = objArr.length;
        int i = this.mSize;
        if (length > i) {
            objArr[i] = null;
        }
        return objArr;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> set = (Set) object;
        if (size() != set.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                if (!set.contains(valueAt(i))) {
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e2) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int[] hashes = this.mHashes;
        int result = 0;
        int s = this.mSize;
        for (int i = 0; i < s; i++) {
            result += hashes[i];
        }
        return result;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 14);
        buffer.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Set)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }

    public Iterator iterator() {
        return new ElementIterator();
    }

    private class ElementIterator extends IndexBasedArrayIterator {
        ElementIterator() {
            super(ArraySet.this.mSize);
        }

        protected Object elementAt(int index) {
            return ArraySet.this.valueAt(index);
        }

        protected void removeAt(int index) {
            ArraySet.this.removeAt(index);
        }
    }

    public boolean containsAll(Collection collection) {
        for (Object item : collection) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection collection) {
        ensureCapacity(this.mSize + collection.size());
        boolean added = false;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            added |= add(it.next());
        }
        return added;
    }

    public boolean removeAll(Collection collection) {
        boolean removed = false;
        for (Object value : collection) {
            removed |= remove(value);
        }
        return removed;
    }

    public boolean retainAll(Collection collection) {
        boolean removed = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                removed = true;
            }
        }
        return removed;
    }
}
