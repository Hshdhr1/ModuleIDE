package org.antlr.v4.runtime.misc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class IntegerList {
    private static int[] EMPTY_DATA = new int[0];
    private static final int INITIAL_SIZE = 4;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private int[] _data;
    private int _size;

    public IntegerList() {
        this._data = EMPTY_DATA;
    }

    public IntegerList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        if (capacity == 0) {
            this._data = EMPTY_DATA;
        } else {
            this._data = new int[capacity];
        }
    }

    public IntegerList(IntegerList list) {
        this._data = (int[]) list._data.clone();
        this._size = list._size;
    }

    public IntegerList(Collection collection) {
        this(collection.size());
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            Integer value = (Integer) i$.next();
            add(value.intValue());
        }
    }

    public final void add(int value) {
        if (this._data.length == this._size) {
            ensureCapacity(this._size + 1);
        }
        this._data[this._size] = value;
        this._size++;
    }

    public final void addAll(int[] array) {
        ensureCapacity(this._size + array.length);
        System.arraycopy(array, 0, this._data, this._size, array.length);
        this._size += array.length;
    }

    public final void addAll(IntegerList list) {
        ensureCapacity(this._size + list._size);
        System.arraycopy(list._data, 0, this._data, this._size, list._size);
        this._size += list._size;
    }

    public final void addAll(Collection collection) {
        ensureCapacity(this._size + collection.size());
        int current = 0;
        Iterator i$ = collection.iterator();
        while (i$.hasNext()) {
            int x = ((Integer) i$.next()).intValue();
            this._data[this._size + current] = x;
            current++;
        }
        this._size += collection.size();
    }

    public final int get(int index) {
        if (index < 0 || index >= this._size) {
            throw new IndexOutOfBoundsException();
        }
        return this._data[index];
    }

    public final boolean contains(int value) {
        for (int i = 0; i < this._size; i++) {
            if (this._data[i] == value) {
                return true;
            }
        }
        return false;
    }

    public final int set(int index, int value) {
        if (index < 0 || index >= this._size) {
            throw new IndexOutOfBoundsException();
        }
        int previous = this._data[index];
        this._data[index] = value;
        return previous;
    }

    public final int removeAt(int index) {
        int value = get(index);
        System.arraycopy(this._data, index + 1, this._data, index, (this._size - index) - 1);
        this._data[this._size - 1] = 0;
        this._size--;
        return value;
    }

    public final void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex < 0 || fromIndex > this._size || toIndex > this._size) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        System.arraycopy(this._data, toIndex, this._data, fromIndex, this._size - toIndex);
        Arrays.fill(this._data, this._size - (toIndex - fromIndex), this._size, 0);
        this._size -= toIndex - fromIndex;
    }

    public final boolean isEmpty() {
        return this._size == 0;
    }

    public final int size() {
        return this._size;
    }

    public final void trimToSize() {
        if (this._data.length != this._size) {
            this._data = Arrays.copyOf(this._data, this._size);
        }
    }

    public final void clear() {
        Arrays.fill(this._data, 0, this._size, 0);
        this._size = 0;
    }

    public final int[] toArray() {
        return this._size == 0 ? EMPTY_DATA : Arrays.copyOf(this._data, this._size);
    }

    public final void sort() {
        Arrays.sort(this._data, 0, this._size);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntegerList)) {
            return false;
        }
        IntegerList other = (IntegerList) o;
        if (this._size != other._size) {
            return false;
        }
        for (int i = 0; i < this._size; i++) {
            if (this._data[i] != other._data[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < this._size; i++) {
            hashCode = (hashCode * 31) + this._data[i];
        }
        return hashCode;
    }

    public String toString() {
        return Arrays.toString(toArray());
    }

    public final int binarySearch(int key) {
        return Arrays.binarySearch(this._data, 0, this._size, key);
    }

    public final int binarySearch(int fromIndex, int toIndex, int key) {
        if (fromIndex < 0 || toIndex < 0 || fromIndex > this._size || toIndex > this._size) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        return Arrays.binarySearch(this._data, fromIndex, toIndex, key);
    }

    private void ensureCapacity(int capacity) {
        int newLength;
        if (capacity < 0 || capacity > 2147483639) {
            throw new OutOfMemoryError();
        }
        if (this._data.length == 0) {
            newLength = 4;
        } else {
            newLength = this._data.length;
        }
        while (newLength < capacity) {
            newLength *= 2;
            if (newLength < 0 || newLength > 2147483639) {
                newLength = 2147483639;
            }
        }
        this._data = Arrays.copyOf(this._data, newLength);
    }

    public final char[] toCharArray() {
        char[] resultArray = new char[this._size];
        int resultIdx = 0;
        boolean calculatedPreciseResultSize = false;
        for (int i = 0; i < this._size; i++) {
            int codePoint = this._data[i];
            if (!calculatedPreciseResultSize && Character.isSupplementaryCodePoint(codePoint)) {
                resultArray = Arrays.copyOf(resultArray, charArraySize());
                calculatedPreciseResultSize = true;
            }
            int charsWritten = Character.toChars(codePoint, resultArray, resultIdx);
            resultIdx += charsWritten;
        }
        return resultArray;
    }

    private int charArraySize() {
        int result = 0;
        for (int i = 0; i < this._size; i++) {
            result += Character.charCount(this._data[i]);
        }
        return result;
    }
}
