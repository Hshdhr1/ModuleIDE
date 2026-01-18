package org.antlr.v4.runtime.misc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class Array2DHashSet implements Set {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int INITAL_BUCKET_CAPACITY = 8;
    public static final int INITAL_CAPACITY = 16;
    public static final double LOAD_FACTOR = 0.75d;
    protected Object[][] buckets;
    protected final AbstractEqualityComparator comparator;
    protected int currentPrime;
    protected int initialBucketCapacity;
    protected int n;
    protected int threshold;

    static {
        $assertionsDisabled = !Array2DHashSet.class.desiredAssertionStatus();
    }

    public Array2DHashSet() {
        this(null, 16, 8);
    }

    public Array2DHashSet(AbstractEqualityComparator abstractEqualityComparator) {
        this(abstractEqualityComparator, 16, 8);
    }

    public Array2DHashSet(AbstractEqualityComparator abstractEqualityComparator, int initialCapacity, int initialBucketCapacity) {
        this.n = 0;
        this.threshold = (int) Math.floor(12.0d);
        this.currentPrime = 1;
        this.initialBucketCapacity = 8;
        this.comparator = abstractEqualityComparator == null ? ObjectEqualityComparator.INSTANCE : abstractEqualityComparator;
        this.buckets = createBuckets(initialCapacity);
        this.initialBucketCapacity = initialBucketCapacity;
    }

    public final Object getOrAdd(Object obj) {
        if (this.n > this.threshold) {
            expand();
        }
        return getOrAddImpl(obj);
    }

    protected Object getOrAddImpl(Object obj) {
        int b = getBucket(obj);
        Object[] objArr = this.buckets[b];
        if (objArr == null) {
            Object[] createBucket = createBucket(this.initialBucketCapacity);
            createBucket[0] = obj;
            this.buckets[b] = createBucket;
            this.n++;
            return obj;
        }
        for (int i = 0; i < objArr.length; i++) {
            Object obj2 = objArr[i];
            if (obj2 == null) {
                objArr[i] = obj;
                this.n++;
                return obj;
            }
            if (this.comparator.equals(obj2, obj)) {
                return obj2;
            }
        }
        int oldLength = objArr.length;
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length * 2);
        this.buckets[b] = copyOf;
        copyOf[oldLength] = obj;
        this.n++;
        return obj;
    }

    public Object get(Object obj) {
        if (obj != null) {
            int b = getBucket(obj);
            Object[] objArr = this.buckets[b];
            if (objArr == null) {
                return null;
            }
            for (Object obj2 : objArr) {
                if (obj2 == null) {
                    return null;
                }
                if (this.comparator.equals(obj2, obj)) {
                    return obj2;
                }
            }
            return null;
        }
        return obj;
    }

    protected final int getBucket(Object obj) {
        int hash = this.comparator.hashCode(obj);
        int b = hash & (this.buckets.length - 1);
        return b;
    }

    public int hashCode() {
        int hash = MurmurHash.initialize();
        Object[][] arr$ = this.buckets;
        for (Object[] objArr : arr$) {
            if (objArr != null) {
                for (Object obj : objArr) {
                    if (obj != null) {
                        hash = MurmurHash.update(hash, this.comparator.hashCode(obj));
                    }
                }
            }
        }
        return MurmurHash.finish(hash, size());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Array2DHashSet)) {
            return false;
        }
        Array2DHashSet<?> other = (Array2DHashSet) o;
        if (other.size() == size()) {
            return containsAll(other);
        }
        return false;
    }

    protected void expand() {
        Object[] objArr;
        Object[][] objArr2 = this.buckets;
        this.currentPrime += 4;
        int newCapacity = this.buckets.length * 2;
        Object[][] createBuckets = createBuckets(newCapacity);
        int[] newBucketLengths = new int[createBuckets.length];
        this.buckets = createBuckets;
        this.threshold = (int) (newCapacity * 0.75d);
        int oldSize = size();
        for (Object[] objArr3 : objArr2) {
            if (objArr3 != null) {
                for (Object obj : objArr3) {
                    if (obj != null) {
                        int b = getBucket(obj);
                        int bucketLength = newBucketLengths[b];
                        if (bucketLength == 0) {
                            objArr = createBucket(this.initialBucketCapacity);
                            createBuckets[b] = objArr;
                        } else {
                            objArr = createBuckets[b];
                            if (bucketLength == objArr.length) {
                                objArr = Arrays.copyOf(objArr, objArr.length * 2);
                                createBuckets[b] = objArr;
                            }
                        }
                        objArr[bucketLength] = obj;
                        newBucketLengths[b] = newBucketLengths[b] + 1;
                    }
                }
            }
        }
        if (!$assertionsDisabled && this.n != oldSize) {
            throw new AssertionError();
        }
    }

    public final boolean add(Object obj) {
        return getOrAdd(obj) == obj;
    }

    public final int size() {
        return this.n;
    }

    public final boolean isEmpty() {
        return this.n == 0;
    }

    public final boolean contains(Object o) {
        return containsFast(asElementType(o));
    }

    public boolean containsFast(Object obj) {
        return (obj == null || get(obj) == null) ? false : true;
    }

    public Iterator iterator() {
        return new SetIterator(toArray());
    }

    public Object[] toArray() {
        Object[] createBucket = createBucket(size());
        int i = 0;
        Object[][] arr$ = this.buckets;
        for (Object[] objArr : arr$) {
            if (objArr != null) {
                int len$ = objArr.length;
                int i$ = 0;
                int i2 = i;
                while (true) {
                    if (i$ >= len$) {
                        i = i2;
                        break;
                    }
                    Object obj = objArr[i$];
                    if (obj == null) {
                        i = i2;
                        break;
                    }
                    createBucket[i2] = obj;
                    i$++;
                    i2++;
                }
            }
        }
        return createBucket;
    }

    public Object[] toArray(Object[] objArr) {
        if (objArr.length < size()) {
            objArr = Arrays.copyOf(objArr, size());
        }
        int i = 0;
        Object[][] arr$ = this.buckets;
        for (Object[] objArr2 : arr$) {
            if (objArr2 != null) {
                int len$ = objArr2.length;
                int i$ = 0;
                int i2 = i;
                while (true) {
                    if (i$ >= len$) {
                        i = i2;
                        break;
                    }
                    Object obj = objArr2[i$];
                    if (obj == null) {
                        i = i2;
                        break;
                    }
                    objArr[i2] = obj;
                    i$++;
                    i2++;
                }
            }
        }
        return objArr;
    }

    public final boolean remove(Object o) {
        return removeFast(asElementType(o));
    }

    public boolean removeFast(Object obj) {
        Object obj2;
        if (obj == null) {
            return false;
        }
        int b = getBucket(obj);
        Object[] objArr = this.buckets[b];
        if (objArr == null) {
            return false;
        }
        for (int i = 0; i < objArr.length && (obj2 = objArr[i]) != null; i++) {
            if (this.comparator.equals(obj2, obj)) {
                System.arraycopy(objArr, i + 1, objArr, i, (objArr.length - i) - 1);
                objArr[objArr.length - 1] = null;
                this.n--;
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0013, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean containsAll(java.util.Collection r12) {
        /*
            r11 = this;
            r9 = 0
            boolean r10 = r12 instanceof org.antlr.v4.runtime.misc.Array2DHashSet
            if (r10 == 0) goto L2e
            r8 = r12
            org.antlr.v4.runtime.misc.Array2DHashSet r8 = (org.antlr.v4.runtime.misc.Array2DHashSet) r8
            java.lang.Object[][] r0 = r8.buckets
            int r5 = r0.length
            r3 = 0
            r4 = r3
        Ld:
            if (r4 >= r5) goto L47
            r2 = r0[r4]
            if (r2 != 0) goto L17
        L13:
            int r3 = r4 + 1
            r4 = r3
            goto Ld
        L17:
            r1 = r2
            int r6 = r1.length
            r3 = 0
        L1a:
            if (r3 >= r6) goto L13
            r7 = r1[r3]
            if (r7 == 0) goto L13
            java.lang.Object r10 = r11.asElementType(r7)
            boolean r10 = r11.containsFast(r10)
            if (r10 != 0) goto L2b
        L2a:
            return r9
        L2b:
            int r3 = r3 + 1
            goto L1a
        L2e:
            java.util.Iterator r3 = r12.iterator()
        L32:
            boolean r10 = r3.hasNext()
            if (r10 == 0) goto L47
            java.lang.Object r7 = r3.next()
            java.lang.Object r10 = r11.asElementType(r7)
            boolean r10 = r11.containsFast(r10)
            if (r10 != 0) goto L32
            goto L2a
        L47:
            r9 = 1
            goto L2a
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.v4.runtime.misc.Array2DHashSet.containsAll(java.util.Collection):boolean");
    }

    public boolean addAll(Collection collection) {
        boolean changed = false;
        for (Object obj : collection) {
            if (getOrAdd(obj) != obj) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean retainAll(Collection collection) {
        int newsize = 0;
        Object[][] arr$ = this.buckets;
        for (Object[] objArr : arr$) {
            if (objArr != null) {
                int i = 0;
                int j = 0;
                while (i < objArr.length && objArr[i] != null) {
                    if (collection.contains(objArr[i])) {
                        if (i != j) {
                            objArr[j] = objArr[i];
                        }
                        j++;
                        newsize++;
                    }
                    i++;
                }
                newsize += j;
                while (j < i) {
                    objArr[j] = null;
                    j++;
                }
            }
        }
        boolean changed = newsize != this.n;
        this.n = newsize;
        return changed;
    }

    public boolean removeAll(Collection collection) {
        boolean changed = false;
        for (Object o : collection) {
            changed |= removeFast(asElementType(o));
        }
        return changed;
    }

    public void clear() {
        this.buckets = createBuckets(16);
        this.n = 0;
        this.threshold = (int) Math.floor(12.0d);
    }

    public String toString() {
        if (size() == 0) {
            return "{}";
        }
        StringBuilder buf = new StringBuilder();
        buf.append('{');
        boolean first = true;
        Object[][] arr$ = this.buckets;
        for (Object[] objArr : arr$) {
            if (objArr != null) {
                for (Object obj : objArr) {
                    if (obj != null) {
                        if (first) {
                            first = false;
                        } else {
                            buf.append(", ");
                        }
                        buf.append(obj.toString());
                    }
                }
            }
        }
        buf.append('}');
        return buf.toString();
    }

    public String toTableString() {
        StringBuilder buf = new StringBuilder();
        Object[][] arr$ = this.buckets;
        for (Object[] objArr : arr$) {
            if (objArr == null) {
                buf.append("null\n");
            } else {
                buf.append('[');
                boolean first = true;
                for (Object obj : objArr) {
                    if (first) {
                        first = false;
                    } else {
                        buf.append(" ");
                    }
                    if (obj == null) {
                        buf.append("_");
                    } else {
                        buf.append(obj.toString());
                    }
                }
                buf.append("]\n");
            }
        }
        return buf.toString();
    }

    protected Object asElementType(Object o) {
        return o;
    }

    protected Object[][] createBuckets(int capacity) {
        return new Object[capacity][];
    }

    protected Object[] createBucket(int capacity) {
        return new Object[capacity];
    }

    protected class SetIterator implements Iterator {
        final Object[] data;
        int nextIndex = 0;
        boolean removed = true;

        public SetIterator(Object[] objArr) {
            this.data = objArr;
        }

        public boolean hasNext() {
            return this.nextIndex < this.data.length;
        }

        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.removed = false;
            Object[] objArr = this.data;
            int i = this.nextIndex;
            this.nextIndex = i + 1;
            return objArr[i];
        }

        public void remove() {
            if (this.removed) {
                throw new IllegalStateException();
            }
            Array2DHashSet.this.remove(this.data[this.nextIndex - 1]);
            this.removed = true;
        }
    }
}
