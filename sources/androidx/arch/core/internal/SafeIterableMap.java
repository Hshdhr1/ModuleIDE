package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes11.dex */
public class SafeIterableMap implements Iterable {
    private Entry mEnd;
    private WeakHashMap mIterators = new WeakHashMap();
    private int mSize = 0;
    Entry mStart;

    interface SupportRemove {
        void supportRemove(@NonNull Entry entry);
    }

    protected Entry get(Object obj) {
        Entry entry;
        Entry entry2 = this.mStart;
        while (true) {
            entry = entry2;
            if (entry == null || entry.mKey.equals(obj)) {
                break;
            }
            entry2 = entry.mNext;
        }
        return entry;
    }

    public Object putIfAbsent(@NonNull Object obj, @NonNull Object obj2) {
        Entry entry = get(obj);
        if (entry != null) {
            return entry.mValue;
        }
        put(obj, obj2);
        return null;
    }

    protected Entry put(@NonNull Object obj, @NonNull Object obj2) {
        Entry entry = new Entry(obj, obj2);
        this.mSize++;
        if (this.mEnd == null) {
            this.mStart = entry;
            this.mEnd = this.mStart;
            return entry;
        }
        this.mEnd.mNext = entry;
        entry.mPrevious = this.mEnd;
        this.mEnd = entry;
        return entry;
    }

    public Object remove(@NonNull Object obj) {
        Entry entry = get(obj);
        if (entry == null) {
            return null;
        }
        this.mSize--;
        if (!this.mIterators.isEmpty()) {
            Iterator it = this.mIterators.keySet().iterator();
            while (it.hasNext()) {
                ((SupportRemove) it.next()).supportRemove(entry);
            }
        }
        if (entry.mPrevious != null) {
            entry.mPrevious.mNext = entry.mNext;
        } else {
            this.mStart = entry.mNext;
        }
        if (entry.mNext != null) {
            entry.mNext.mPrevious = entry.mPrevious;
        } else {
            this.mEnd = entry.mPrevious;
        }
        entry.mNext = null;
        entry.mPrevious = null;
        return entry.mValue;
    }

    public int size() {
        return this.mSize;
    }

    @NonNull
    public Iterator iterator() {
        AscendingIterator ascendingIterator = new AscendingIterator(this.mStart, this.mEnd);
        this.mIterators.put(ascendingIterator, false);
        return ascendingIterator;
    }

    public Iterator descendingIterator() {
        DescendingIterator descendingIterator = new DescendingIterator(this.mEnd, this.mStart);
        this.mIterators.put(descendingIterator, false);
        return descendingIterator;
    }

    public IteratorWithAdditions iteratorWithAdditions() {
        IteratorWithAdditions iteratorWithAdditions = new IteratorWithAdditions();
        this.mIterators.put(iteratorWithAdditions, false);
        return iteratorWithAdditions;
    }

    public Map.Entry eldest() {
        return this.mStart;
    }

    public Map.Entry newest() {
        return this.mEnd;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (size() != safeIterableMap.size()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = safeIterableMap.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object next = it2.next();
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    public int hashCode() {
        int i = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            i += ((Map.Entry) it.next()).hashCode();
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator it = iterator();
        while (it.hasNext()) {
            sb.append(((Map.Entry) it.next()).toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static abstract class ListIterator implements Iterator, SupportRemove {
        Entry mExpectedEnd;
        Entry mNext;

        abstract Entry backward(Entry entry);

        abstract Entry forward(Entry entry);

        ListIterator(Entry entry, Entry entry2) {
            this.mExpectedEnd = entry2;
            this.mNext = entry;
        }

        public boolean hasNext() {
            return this.mNext != null;
        }

        public void supportRemove(@NonNull Entry entry) {
            if (this.mExpectedEnd == entry && entry == this.mNext) {
                this.mNext = null;
                this.mExpectedEnd = null;
            }
            if (this.mExpectedEnd == entry) {
                this.mExpectedEnd = backward(this.mExpectedEnd);
            }
            if (this.mNext == entry) {
                this.mNext = nextNode();
            }
        }

        private Entry nextNode() {
            if (this.mNext != this.mExpectedEnd && this.mExpectedEnd != null) {
                return forward(this.mNext);
            }
            return null;
        }

        public Map.Entry next() {
            Entry entry = this.mNext;
            this.mNext = nextNode();
            return entry;
        }
    }

    static class AscendingIterator extends ListIterator {
        AscendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        Entry forward(Entry entry) {
            return entry.mNext;
        }

        Entry backward(Entry entry) {
            return entry.mPrevious;
        }
    }

    private static class DescendingIterator extends ListIterator {
        DescendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        Entry forward(Entry entry) {
            return entry.mPrevious;
        }

        Entry backward(Entry entry) {
            return entry.mNext;
        }
    }

    private class IteratorWithAdditions implements Iterator, SupportRemove {
        private boolean mBeforeStart = true;
        private Entry mCurrent;

        IteratorWithAdditions() {
        }

        public void supportRemove(@NonNull Entry entry) {
            if (entry == this.mCurrent) {
                this.mCurrent = this.mCurrent.mPrevious;
                this.mBeforeStart = this.mCurrent == null;
            }
        }

        public boolean hasNext() {
            if (this.mBeforeStart) {
                return SafeIterableMap.this.mStart != null;
            }
            return (this.mCurrent == null || this.mCurrent.mNext == null) ? false : true;
        }

        public Map.Entry next() {
            if (this.mBeforeStart) {
                this.mBeforeStart = false;
                this.mCurrent = SafeIterableMap.this.mStart;
            } else {
                this.mCurrent = this.mCurrent != null ? this.mCurrent.mNext : null;
            }
            return this.mCurrent;
        }
    }

    static class Entry implements Map.Entry {

        @NonNull
        final Object mKey;
        Entry mNext;
        Entry mPrevious;

        @NonNull
        final Object mValue;

        Entry(@NonNull Object obj, @NonNull Object obj2) {
            this.mKey = obj;
            this.mValue = obj2;
        }

        @NonNull
        public Object getKey() {
            return this.mKey;
        }

        @NonNull
        public Object getValue() {
            return this.mValue;
        }

        public Object setValue(Object obj) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return this.mKey + "=" + this.mValue;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.mKey.equals(entry.mKey) && this.mValue.equals(entry.mValue);
        }

        public int hashCode() {
            return this.mKey.hashCode() ^ this.mValue.hashCode();
        }
    }
}
