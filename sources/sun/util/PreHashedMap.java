package sun.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class PreHashedMap extends AbstractMap {
    private final Object[] ht;
    private final int mask;
    private final int rows;
    private final int shift;
    private final int size;

    static /* bridge */ /* synthetic */ Object[] -$$Nest$fgetht(PreHashedMap preHashedMap) {
        return preHashedMap.ht;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgetrows(PreHashedMap preHashedMap) {
        return preHashedMap.rows;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgetsize(PreHashedMap preHashedMap) {
        return preHashedMap.size;
    }

    private Object toV(Object obj) {
        return obj;
    }

    protected abstract void init(Object[] objArr);

    protected PreHashedMap(int i, int i2, int i3, int i4) {
        this.rows = i;
        this.size = i2;
        this.shift = i3;
        this.mask = i4;
        Object[] objArr = new Object[i];
        this.ht = objArr;
        init(objArr);
    }

    public Object get(Object obj) {
        Object[] objArr = (Object[]) this.ht[(obj.hashCode() >> this.shift) & this.mask];
        if (objArr == null) {
            return null;
        }
        while (!objArr[0].equals(obj)) {
            if (objArr.length < 3) {
                return null;
            }
            objArr = objArr[2];
        }
        return toV(objArr[1]);
    }

    public Object put(String str, Object obj) {
        Object[] objArr = (Object[]) this.ht[(str.hashCode() >> this.shift) & this.mask];
        if (objArr == null) {
            throw new UnsupportedOperationException(str);
        }
        while (!objArr[0].equals(str)) {
            if (objArr.length < 3) {
                throw new UnsupportedOperationException(str);
            }
            objArr = (Object[]) objArr[2];
        }
        Object v = toV(objArr[1]);
        objArr[1] = obj;
        return v;
    }

    class 1 extends AbstractSet {
        1() {
        }

        public int size() {
            return PreHashedMap.-$$Nest$fgetsize(PreHashedMap.this);
        }

        class 1 implements Iterator {
            private int i = -1;
            Object[] a = null;
            String cur = null;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            1() {
            }

            private boolean findNext() {
                Object[] objArr = this.a;
                if (objArr != null) {
                    if (objArr.length == 3) {
                        Object[] objArr2 = (Object[]) objArr[2];
                        this.a = objArr2;
                        this.cur = (String) objArr2[0];
                        return true;
                    }
                    this.i++;
                    this.a = null;
                }
                this.cur = null;
                if (this.i >= PreHashedMap.-$$Nest$fgetrows(PreHashedMap.this)) {
                    return false;
                }
                if (this.i < 0 || PreHashedMap.-$$Nest$fgetht(PreHashedMap.this)[this.i] == null) {
                    do {
                        int i = this.i + 1;
                        this.i = i;
                        if (i >= PreHashedMap.-$$Nest$fgetrows(PreHashedMap.this)) {
                            return false;
                        }
                    } while (PreHashedMap.-$$Nest$fgetht(PreHashedMap.this)[this.i] == null);
                }
                Object[] objArr3 = (Object[]) PreHashedMap.-$$Nest$fgetht(PreHashedMap.this)[this.i];
                this.a = objArr3;
                this.cur = (String) objArr3[0];
                return true;
            }

            public boolean hasNext() {
                if (this.cur != null) {
                    return true;
                }
                return findNext();
            }

            public String next() {
                if (this.cur == null && !findNext()) {
                    throw new NoSuchElementException();
                }
                String str = this.cur;
                this.cur = null;
                return str;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public Iterator iterator() {
            return new 1();
        }
    }

    public Set keySet() {
        return new 1();
    }

    class 2 extends AbstractSet {
        2() {
        }

        public int size() {
            return PreHashedMap.-$$Nest$fgetsize(PreHashedMap.this);
        }

        class 1 implements Iterator {
            final Iterator i;

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.-CC.$default$forEachRemaining(this, consumer);
            }

            1() {
                this.i = PreHashedMap.this.keySet().iterator();
            }

            public boolean hasNext() {
                return this.i.hasNext();
            }

            class 1 implements Map.Entry {
                String k;

                1() {
                    this.k = (String) 1.this.i.next();
                }

                public String getKey() {
                    return this.k;
                }

                public Object getValue() {
                    return PreHashedMap.this.get(this.k);
                }

                public int hashCode() {
                    Object obj = PreHashedMap.this.get(this.k);
                    return this.k.hashCode() + (obj == null ? 0 : obj.hashCode());
                }

                public boolean equals(Object obj) {
                    if (obj == this) {
                        return true;
                    }
                    if (!(obj instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry entry = (Map.Entry) obj;
                    if (getKey() != null ? getKey().equals(entry.getKey()) : entry.getKey() == null) {
                        if (getValue() != null ? getValue().equals(entry.getValue()) : entry.getValue() == null) {
                            return true;
                        }
                    }
                    return false;
                }

                public Object setValue(Object obj) {
                    throw new UnsupportedOperationException();
                }
            }

            public Map.Entry next() {
                return new 1();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public Iterator iterator() {
            return new 1();
        }
    }

    public Set entrySet() {
        return new 2();
    }
}
