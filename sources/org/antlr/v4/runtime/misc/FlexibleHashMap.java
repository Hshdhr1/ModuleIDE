package org.antlr.v4.runtime.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class FlexibleHashMap implements Map {
    public static final int INITAL_BUCKET_CAPACITY = 8;
    public static final int INITAL_CAPACITY = 16;
    public static final double LOAD_FACTOR = 0.75d;
    protected LinkedList[] buckets;
    protected final AbstractEqualityComparator comparator;
    protected int currentPrime;
    protected int initialBucketCapacity;
    protected int n;
    protected int threshold;

    public static class Entry {
        public final Object key;
        public Object value;

        public Entry(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        public String toString() {
            return this.key.toString() + ":" + this.value.toString();
        }
    }

    public FlexibleHashMap() {
        this(null, 16, 8);
    }

    public FlexibleHashMap(AbstractEqualityComparator abstractEqualityComparator) {
        this(abstractEqualityComparator, 16, 8);
    }

    public FlexibleHashMap(AbstractEqualityComparator abstractEqualityComparator, int initialCapacity, int initialBucketCapacity) {
        this.n = 0;
        this.threshold = 12;
        this.currentPrime = 1;
        this.initialBucketCapacity = 8;
        this.comparator = abstractEqualityComparator == null ? ObjectEqualityComparator.INSTANCE : abstractEqualityComparator;
        this.buckets = createEntryListArray(initialBucketCapacity);
        this.initialBucketCapacity = initialBucketCapacity;
    }

    private static LinkedList[] createEntryListArray(int length) {
        return new LinkedList[length];
    }

    protected int getBucket(Object obj) {
        int hash = this.comparator.hashCode(obj);
        int b = hash & (this.buckets.length - 1);
        return b;
    }

    public Object get(Object key) {
        if (key == null) {
            return null;
        }
        int b = getBucket(key);
        LinkedList linkedList = this.buckets[b];
        if (linkedList == null) {
            return null;
        }
        Iterator i$ = linkedList.iterator();
        while (i$.hasNext()) {
            Entry entry = (Entry) i$.next();
            if (this.comparator.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    public Object put(Object obj, Object obj2) {
        if (obj == null) {
            return null;
        }
        if (this.n > this.threshold) {
            expand();
        }
        int b = getBucket(obj);
        LinkedList linkedList = this.buckets[b];
        if (linkedList == null) {
            LinkedList[] linkedListArr = this.buckets;
            linkedList = new LinkedList();
            linkedListArr[b] = linkedList;
        }
        Iterator i$ = linkedList.iterator();
        while (i$.hasNext()) {
            Entry entry = (Entry) i$.next();
            if (this.comparator.equals(entry.key, obj)) {
                Object obj3 = entry.value;
                entry.value = obj2;
                this.n++;
                return obj3;
            }
        }
        linkedList.add(new Entry(obj, obj2));
        this.n++;
        return null;
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    public Collection values() {
        ArrayList arrayList = new ArrayList(size());
        LinkedList[] arr$ = this.buckets;
        for (LinkedList linkedList : arr$) {
            if (linkedList != null) {
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext()) {
                    arrayList.add(((Entry) i$.next()).value);
                }
            }
        }
        return arrayList;
    }

    public Set entrySet() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public int hashCode() {
        Entry entry;
        int hash = MurmurHash.initialize();
        LinkedList[] arr$ = this.buckets;
        for (LinkedList linkedList : arr$) {
            if (linkedList != null) {
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext() && (entry = (Entry) i$.next()) != null) {
                    hash = MurmurHash.update(hash, this.comparator.hashCode(entry.key));
                }
            }
        }
        return MurmurHash.finish(hash, size());
    }

    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    protected void expand() {
        Entry entry;
        LinkedList[] linkedListArr = this.buckets;
        this.currentPrime += 4;
        int newCapacity = this.buckets.length * 2;
        this.buckets = createEntryListArray(newCapacity);
        this.threshold = (int) (newCapacity * 0.75d);
        int oldSize = size();
        for (LinkedList linkedList : linkedListArr) {
            if (linkedList != null) {
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext() && (entry = (Entry) i$.next()) != null) {
                    put(entry.key, entry.value);
                }
            }
        }
        this.n = oldSize;
    }

    public int size() {
        return this.n;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public void clear() {
        this.buckets = createEntryListArray(16);
        this.n = 0;
    }

    public String toString() {
        Entry entry;
        if (size() == 0) {
            return "{}";
        }
        StringBuilder buf = new StringBuilder();
        buf.append('{');
        boolean first = true;
        LinkedList[] arr$ = this.buckets;
        for (LinkedList linkedList : arr$) {
            if (linkedList != null) {
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext() && (entry = (Entry) i$.next()) != null) {
                    if (first) {
                        first = false;
                    } else {
                        buf.append(", ");
                    }
                    buf.append(entry.toString());
                }
            }
        }
        buf.append('}');
        return buf.toString();
    }

    public String toTableString() {
        StringBuilder buf = new StringBuilder();
        LinkedList[] arr$ = this.buckets;
        for (LinkedList linkedList : arr$) {
            if (linkedList == null) {
                buf.append("null\n");
            } else {
                buf.append('[');
                boolean first = true;
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext()) {
                    Entry entry = (Entry) i$.next();
                    if (first) {
                        first = false;
                    } else {
                        buf.append(" ");
                    }
                    if (entry == null) {
                        buf.append("_");
                    } else {
                        buf.append(entry.toString());
                    }
                }
                buf.append("]\n");
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        FlexibleHashMap<String, Integer> map = new FlexibleHashMap();
        map.put("hi", 1);
        map.put("mom", 2);
        map.put("foo", 3);
        map.put("ach", 4);
        map.put("cbba", 5);
        map.put("d", 6);
        map.put("edf", 7);
        map.put("mom", 8);
        map.put("hi", 9);
        System.out.println(map);
        System.out.println(map.toTableString());
    }
}
