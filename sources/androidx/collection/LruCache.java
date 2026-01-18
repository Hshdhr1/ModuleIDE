package androidx.collection;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes5.dex */
public class LruCache {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public void resize(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = maxSize;
        }
        trimToSize(maxSize);
    }

    public final Object get(Object obj) {
        Object put;
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            Object obj2 = this.map.get(obj);
            if (obj2 != null) {
                this.hitCount++;
                return obj2;
            }
            this.missCount++;
            Object create = create(obj);
            if (create == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                put = this.map.put(obj, create);
                if (put != null) {
                    this.map.put(obj, put);
                } else {
                    this.size += safeSizeOf(obj, create);
                }
            }
            if (put != null) {
                entryRemoved(false, obj, create, put);
                return put;
            }
            trimToSize(this.maxSize);
            return create;
        }
    }

    public final Object put(Object obj, Object obj2) {
        Object put;
        if (obj == null || obj2 == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(obj, obj2);
            put = this.map.put(obj, obj2);
            if (put != null) {
                this.size -= safeSizeOf(obj, put);
            }
        }
        if (put != null) {
            entryRemoved(false, obj, put, obj2);
        }
        trimToSize(this.maxSize);
        return put;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0070, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void trimToSize(int r6) {
        /*
            r5 = this;
        L0:
            monitor-enter(r5)
            int r0 = r5.size     // Catch: java.lang.Throwable -> L71
            if (r0 < 0) goto L52
            java.util.LinkedHashMap r0 = r5.map     // Catch: java.lang.Throwable -> L71
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L71
            if (r0 == 0) goto L11
            int r0 = r5.size     // Catch: java.lang.Throwable -> L71
            if (r0 != 0) goto L52
        L11:
            int r0 = r5.size     // Catch: java.lang.Throwable -> L71
            if (r0 <= r6) goto L50
            java.util.LinkedHashMap r0 = r5.map     // Catch: java.lang.Throwable -> L71
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L71
            if (r0 == 0) goto L1e
            goto L50
        L1e:
            java.util.LinkedHashMap r0 = r5.map     // Catch: java.lang.Throwable -> L71
            java.util.Set r0 = r0.entrySet()     // Catch: java.lang.Throwable -> L71
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L71
            java.lang.Object r0 = r0.next()     // Catch: java.lang.Throwable -> L71
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: java.lang.Throwable -> L71
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L71
            java.lang.Object r2 = r0.getValue()     // Catch: java.lang.Throwable -> L71
            java.util.LinkedHashMap r3 = r5.map     // Catch: java.lang.Throwable -> L71
            r3.remove(r1)     // Catch: java.lang.Throwable -> L71
            int r3 = r5.size     // Catch: java.lang.Throwable -> L71
            int r4 = r5.safeSizeOf(r1, r2)     // Catch: java.lang.Throwable -> L71
            int r3 = r3 - r4
            r5.size = r3     // Catch: java.lang.Throwable -> L71
            int r3 = r5.evictionCount     // Catch: java.lang.Throwable -> L71
            r4 = 1
            int r3 = r3 + r4
            r5.evictionCount = r3     // Catch: java.lang.Throwable -> L71
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L71
            r0 = 0
            r5.entryRemoved(r4, r1, r2, r0)
            goto L0
        L50:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L71
            return
        L52:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L71
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L71
            r1.<init>()     // Catch: java.lang.Throwable -> L71
            java.lang.Class r2 = r5.getClass()     // Catch: java.lang.Throwable -> L71
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L71
            r1.append(r2)     // Catch: java.lang.Throwable -> L71
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            r1.append(r2)     // Catch: java.lang.Throwable -> L71
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L71
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L71
            throw r0     // Catch: java.lang.Throwable -> L71
        L71:
            r0 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L71
            goto L75
        L74:
            throw r0
        L75:
            goto L74
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LruCache.trimToSize(int):void");
    }

    public final Object remove(Object obj) {
        Object remove;
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.map.remove(obj);
            if (remove != null) {
                this.size -= safeSizeOf(obj, remove);
            }
        }
        if (remove != null) {
            entryRemoved(false, obj, remove, null);
        }
        return remove;
    }

    protected void entryRemoved(boolean evicted, Object obj, Object obj2, Object obj3) {
    }

    protected Object create(Object obj) {
        return null;
    }

    private int safeSizeOf(Object obj, Object obj2) {
        int result = sizeOf(obj, obj2);
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + obj + "=" + obj2);
        }
        return result;
    }

    protected int sizeOf(Object obj, Object obj2) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int hitPercent;
        int i = this.hitCount;
        int accesses = this.missCount + i;
        hitPercent = accesses != 0 ? (i * 100) / accesses : 0;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
    }
}
