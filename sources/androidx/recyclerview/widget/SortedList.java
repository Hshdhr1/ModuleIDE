package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public class SortedList {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    Object[] mData;
    private int mNewDataStart;
    private Object[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class mTClass;

    public SortedList(@NonNull Class cls, @NonNull Callback callback) {
        this(cls, callback, 10);
    }

    public SortedList(@NonNull Class cls, @NonNull Callback callback, int initialCapacity) {
        this.mTClass = cls;
        this.mData = (Object[]) Array.newInstance(cls, initialCapacity);
        this.mCallback = callback;
        this.mSize = 0;
    }

    public int size() {
        return this.mSize;
    }

    public int add(Object obj) {
        throwIfInMutationOperation();
        return add(obj, true);
    }

    public void addAll(@NonNull Object[] objArr, boolean mayModifyInput) {
        throwIfInMutationOperation();
        if (objArr.length != 0) {
            if (mayModifyInput) {
                addAllInternal(objArr);
            } else {
                addAllInternal(copyArray(objArr));
            }
        }
    }

    public void addAll(@NonNull Object... objArr) {
        addAll(objArr, false);
    }

    public void addAll(@NonNull Collection collection) {
        addAll(collection.toArray((Object[]) Array.newInstance(this.mTClass, collection.size())), true);
    }

    public void replaceAll(@NonNull Object[] objArr, boolean mayModifyInput) {
        throwIfInMutationOperation();
        if (mayModifyInput) {
            replaceAllInternal(objArr);
        } else {
            replaceAllInternal(copyArray(objArr));
        }
    }

    public void replaceAll(@NonNull Object... objArr) {
        replaceAll(objArr, false);
    }

    public void replaceAll(@NonNull Collection collection) {
        replaceAll(collection.toArray((Object[]) Array.newInstance(this.mTClass, collection.size())), true);
    }

    private void addAllInternal(Object[] objArr) {
        if (objArr.length >= 1) {
            int newSize = sortAndDedup(objArr);
            if (this.mSize == 0) {
                this.mData = objArr;
                this.mSize = newSize;
                this.mCallback.onInserted(0, newSize);
                return;
            }
            merge(objArr, newSize);
        }
    }

    private void replaceAllInternal(@NonNull Object[] objArr) {
        boolean forceBatchedUpdates = !(this.mCallback instanceof BatchedCallback);
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        this.mOldData = this.mData;
        this.mNewDataStart = 0;
        int newSize = sortAndDedup(objArr);
        this.mData = (Object[]) Array.newInstance(this.mTClass, newSize);
        while (true) {
            if (this.mNewDataStart >= newSize && this.mOldDataStart >= this.mOldDataSize) {
                break;
            }
            if (this.mOldDataStart >= this.mOldDataSize) {
                int insertIndex = this.mNewDataStart;
                int itemCount = newSize - this.mNewDataStart;
                System.arraycopy(objArr, insertIndex, this.mData, insertIndex, itemCount);
                this.mNewDataStart += itemCount;
                this.mSize += itemCount;
                this.mCallback.onInserted(insertIndex, itemCount);
                break;
            }
            if (this.mNewDataStart >= newSize) {
                int itemCount2 = this.mOldDataSize - this.mOldDataStart;
                this.mSize -= itemCount2;
                this.mCallback.onRemoved(this.mNewDataStart, itemCount2);
                break;
            }
            Object obj = this.mOldData[this.mOldDataStart];
            Object obj2 = objArr[this.mNewDataStart];
            int result = this.mCallback.compare(obj, obj2);
            if (result < 0) {
                replaceAllRemove();
            } else if (result > 0) {
                replaceAllInsert(obj2);
            } else if (!this.mCallback.areItemsTheSame(obj, obj2)) {
                replaceAllRemove();
                replaceAllInsert(obj2);
            } else {
                this.mData[this.mNewDataStart] = obj2;
                this.mOldDataStart++;
                this.mNewDataStart++;
                if (!this.mCallback.areContentsTheSame(obj, obj2)) {
                    this.mCallback.onChanged(this.mNewDataStart - 1, 1, this.mCallback.getChangePayload(obj, obj2));
                }
            }
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private void replaceAllInsert(Object obj) {
        this.mData[this.mNewDataStart] = obj;
        this.mNewDataStart++;
        this.mSize++;
        this.mCallback.onInserted(this.mNewDataStart - 1, 1);
    }

    private void replaceAllRemove() {
        this.mSize--;
        this.mOldDataStart++;
        this.mCallback.onRemoved(this.mNewDataStart, 1);
    }

    private int sortAndDedup(@NonNull Object[] objArr) {
        if (objArr.length == 0) {
            return 0;
        }
        Arrays.sort(objArr, this.mCallback);
        int rangeStart = 0;
        int rangeEnd = 1;
        for (int i = 1; i < objArr.length; i++) {
            Object obj = objArr[i];
            int compare = this.mCallback.compare(objArr[rangeStart], obj);
            if (compare == 0) {
                int sameItemPos = findSameItem(obj, objArr, rangeStart, rangeEnd);
                if (sameItemPos != -1) {
                    objArr[sameItemPos] = obj;
                } else {
                    if (rangeEnd != i) {
                        objArr[rangeEnd] = obj;
                    }
                    rangeEnd++;
                }
            } else {
                if (rangeEnd != i) {
                    objArr[rangeEnd] = obj;
                }
                rangeStart = rangeEnd;
                rangeEnd++;
            }
        }
        return rangeEnd;
    }

    private int findSameItem(Object obj, Object[] objArr, int from, int to) {
        for (int pos = from; pos < to; pos++) {
            if (this.mCallback.areItemsTheSame(objArr[pos], obj)) {
                return pos;
            }
        }
        return -1;
    }

    private void merge(Object[] objArr, int newDataSize) {
        boolean forceBatchedUpdates = !(this.mCallback instanceof BatchedCallback);
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        int mergedCapacity = this.mSize + newDataSize + 10;
        this.mData = (Object[]) Array.newInstance(this.mTClass, mergedCapacity);
        this.mNewDataStart = 0;
        int newDataStart = 0;
        while (true) {
            if (this.mOldDataStart >= this.mOldDataSize && newDataStart >= newDataSize) {
                break;
            }
            if (this.mOldDataStart == this.mOldDataSize) {
                int itemCount = newDataSize - newDataStart;
                System.arraycopy(objArr, newDataStart, this.mData, this.mNewDataStart, itemCount);
                this.mNewDataStart += itemCount;
                this.mSize += itemCount;
                this.mCallback.onInserted(this.mNewDataStart - itemCount, itemCount);
                break;
            }
            if (newDataStart == newDataSize) {
                int itemCount2 = this.mOldDataSize - this.mOldDataStart;
                System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mNewDataStart, itemCount2);
                this.mNewDataStart += itemCount2;
                break;
            }
            Object obj = this.mOldData[this.mOldDataStart];
            Object obj2 = objArr[newDataStart];
            int compare = this.mCallback.compare(obj, obj2);
            if (compare > 0) {
                Object[] objArr2 = this.mData;
                int i = this.mNewDataStart;
                this.mNewDataStart = i + 1;
                objArr2[i] = obj2;
                this.mSize++;
                newDataStart++;
                this.mCallback.onInserted(this.mNewDataStart - 1, 1);
            } else if (compare == 0 && this.mCallback.areItemsTheSame(obj, obj2)) {
                Object[] objArr3 = this.mData;
                int i2 = this.mNewDataStart;
                this.mNewDataStart = i2 + 1;
                objArr3[i2] = obj2;
                newDataStart++;
                this.mOldDataStart++;
                if (!this.mCallback.areContentsTheSame(obj, obj2)) {
                    this.mCallback.onChanged(this.mNewDataStart - 1, 1, this.mCallback.getChangePayload(obj, obj2));
                }
            } else {
                Object[] objArr4 = this.mData;
                int i3 = this.mNewDataStart;
                this.mNewDataStart = i3 + 1;
                objArr4[i3] = obj;
                this.mOldDataStart++;
            }
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private void throwIfInMutationOperation() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public void beginBatchedUpdates() {
        throwIfInMutationOperation();
        if (!(this.mCallback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(this.mCallback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void endBatchedUpdates() {
        throwIfInMutationOperation();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback) this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    private int add(Object obj, boolean notify) {
        int index = findIndexOf(obj, this.mData, 0, this.mSize, 1);
        if (index == -1) {
            index = 0;
        } else if (index < this.mSize) {
            Object obj2 = this.mData[index];
            if (this.mCallback.areItemsTheSame(obj2, obj)) {
                if (this.mCallback.areContentsTheSame(obj2, obj)) {
                    this.mData[index] = obj;
                    return index;
                }
                this.mData[index] = obj;
                this.mCallback.onChanged(index, 1, this.mCallback.getChangePayload(obj2, obj));
                return index;
            }
        }
        addToData(index, obj);
        if (notify) {
            this.mCallback.onInserted(index, 1);
        }
        return index;
    }

    public boolean remove(Object obj) {
        throwIfInMutationOperation();
        return remove(obj, true);
    }

    public Object removeItemAt(int index) {
        throwIfInMutationOperation();
        Object obj = get(index);
        removeItemAtIndex(index, true);
        return obj;
    }

    private boolean remove(Object obj, boolean notify) {
        int index = findIndexOf(obj, this.mData, 0, this.mSize, 2);
        if (index == -1) {
            return false;
        }
        removeItemAtIndex(index, notify);
        return true;
    }

    private void removeItemAtIndex(int index, boolean notify) {
        System.arraycopy(this.mData, index + 1, this.mData, index, (this.mSize - index) - 1);
        this.mSize--;
        this.mData[this.mSize] = null;
        if (notify) {
            this.mCallback.onRemoved(index, 1);
        }
    }

    public void updateItemAt(int index, Object obj) {
        throwIfInMutationOperation();
        Object obj2 = get(index);
        boolean contentsChanged = obj2 == obj || !this.mCallback.areContentsTheSame(obj2, obj);
        if (obj2 != obj) {
            int cmp = this.mCallback.compare(obj2, obj);
            if (cmp == 0) {
                this.mData[index] = obj;
                if (contentsChanged) {
                    this.mCallback.onChanged(index, 1, this.mCallback.getChangePayload(obj2, obj));
                    return;
                }
                return;
            }
        }
        if (contentsChanged) {
            this.mCallback.onChanged(index, 1, this.mCallback.getChangePayload(obj2, obj));
        }
        removeItemAtIndex(index, false);
        int newIndex = add(obj, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public void recalculatePositionOfItemAt(int index) {
        throwIfInMutationOperation();
        Object obj = get(index);
        removeItemAtIndex(index, false);
        int newIndex = add(obj, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public Object get(int index) throws IndexOutOfBoundsException {
        if (index >= this.mSize || index < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + index + " but size is " + this.mSize);
        }
        return (this.mOldData == null || index < this.mNewDataStart) ? this.mData[index] : this.mOldData[(index - this.mNewDataStart) + this.mOldDataStart];
    }

    public int indexOf(Object obj) {
        if (this.mOldData != null) {
            int index = findIndexOf(obj, this.mData, 0, this.mNewDataStart, 4);
            if (index == -1) {
                int index2 = findIndexOf(obj, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
                if (index2 != -1) {
                    return (index2 - this.mOldDataStart) + this.mNewDataStart;
                }
                return -1;
            }
            return index;
        }
        return findIndexOf(obj, this.mData, 0, this.mSize, 4);
    }

    private int findIndexOf(Object obj, Object[] objArr, int left, int right, int reason) {
        while (left < right) {
            int middle = (left + right) / 2;
            Object obj2 = objArr[middle];
            int cmp = this.mCallback.compare(obj2, obj);
            if (cmp < 0) {
                left = middle + 1;
            } else {
                if (cmp == 0) {
                    if (!this.mCallback.areItemsTheSame(obj2, obj)) {
                        int exact = linearEqualitySearch(obj, middle, left, right);
                        return (reason == 1 && exact == -1) ? middle : exact;
                    }
                    return middle;
                }
                right = middle;
            }
        }
        if (reason != 1) {
            left = -1;
        }
        return left;
    }

    private int linearEqualitySearch(Object obj, int middle, int left, int right) {
        int next = middle - 1;
        for (int next2 = next; next2 >= left; next2--) {
            Object obj2 = this.mData[next2];
            int cmp = this.mCallback.compare(obj2, obj);
            if (cmp != 0) {
                break;
            }
            if (this.mCallback.areItemsTheSame(obj2, obj)) {
                return next2;
            }
        }
        int next3 = middle + 1;
        for (int next4 = next3; next4 < right; next4++) {
            Object obj3 = this.mData[next4];
            int cmp2 = this.mCallback.compare(obj3, obj);
            if (cmp2 != 0) {
                break;
            }
            if (this.mCallback.areItemsTheSame(obj3, obj)) {
                return next4;
            }
        }
        return -1;
    }

    private void addToData(int index, Object obj) {
        if (index > this.mSize) {
            throw new IndexOutOfBoundsException("cannot add item to " + index + " because size is " + this.mSize);
        }
        if (this.mSize == this.mData.length) {
            Object[] objArr = (Object[]) Array.newInstance(this.mTClass, this.mData.length + 10);
            System.arraycopy(this.mData, 0, objArr, 0, index);
            objArr[index] = obj;
            System.arraycopy(this.mData, index, objArr, index + 1, this.mSize - index);
            this.mData = objArr;
        } else {
            System.arraycopy(this.mData, index, this.mData, index + 1, this.mSize - index);
            this.mData[index] = obj;
        }
        this.mSize++;
    }

    private Object[] copyArray(Object[] objArr) {
        Object[] objArr2 = (Object[]) Array.newInstance(this.mTClass, objArr.length);
        System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        return objArr2;
    }

    public void clear() {
        throwIfInMutationOperation();
        if (this.mSize != 0) {
            int prevSize = this.mSize;
            Arrays.fill(this.mData, 0, prevSize, (Object) null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, prevSize);
        }
    }

    public static abstract class Callback implements Comparator, ListUpdateCallback {
        public abstract boolean areContentsTheSame(Object obj, Object obj2);

        public abstract boolean areItemsTheSame(Object obj, Object obj2);

        public abstract int compare(Object obj, Object obj2);

        public abstract void onChanged(int i, int i2);

        public void onChanged(int position, int count, Object payload) {
            onChanged(position, count);
        }

        @Nullable
        public Object getChangePayload(Object obj, Object obj2) {
            return null;
        }
    }

    public static class BatchedCallback extends Callback {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback;
        final Callback mWrappedCallback;

        public BatchedCallback(Callback callback) {
            this.mWrappedCallback = callback;
            this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
        }

        public int compare(Object obj, Object obj2) {
            return this.mWrappedCallback.compare(obj, obj2);
        }

        public void onInserted(int position, int count) {
            this.mBatchingListUpdateCallback.onInserted(position, count);
        }

        public void onRemoved(int position, int count) {
            this.mBatchingListUpdateCallback.onRemoved(position, count);
        }

        public void onMoved(int fromPosition, int toPosition) {
            this.mBatchingListUpdateCallback.onMoved(fromPosition, toPosition);
        }

        public void onChanged(int position, int count) {
            this.mBatchingListUpdateCallback.onChanged(position, count, null);
        }

        public void onChanged(int position, int count, Object payload) {
            this.mBatchingListUpdateCallback.onChanged(position, count, payload);
        }

        public boolean areContentsTheSame(Object obj, Object obj2) {
            return this.mWrappedCallback.areContentsTheSame(obj, obj2);
        }

        public boolean areItemsTheSame(Object obj, Object obj2) {
            return this.mWrappedCallback.areItemsTheSame(obj, obj2);
        }

        @Nullable
        public Object getChangePayload(Object obj, Object obj2) {
            return this.mWrappedCallback.getChangePayload(obj, obj2);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }
    }
}
