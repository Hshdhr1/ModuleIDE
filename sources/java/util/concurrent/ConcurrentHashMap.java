package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ConcurrentHashMap extends AbstractMap implements ConcurrentMap, Serializable {
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    static final int HASH_BITS = Integer.MAX_VALUE;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int MAX_RESIZERS = 65535;
    private static final int MIN_TRANSFER_STRIDE = 16;
    static final int MIN_TREEIFY_CAPACITY = 64;
    static final int MOVED = -1;
    static final int RESERVED = -3;
    private static final int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 16;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    static final int TREEBIN = -2;
    static final int TREEIFY_THRESHOLD = 8;
    private static final Unsafe U;
    static final int UNTREEIFY_THRESHOLD = 6;
    private static final long serialVersionUID = 7249069246763182397L;
    private volatile transient long baseCount;
    private volatile transient int cellsBusy;
    private volatile transient CounterCell[] counterCells;
    private transient EntrySetView entrySet;
    private transient KeySetView keySet;
    private volatile transient Node[] nextTable;
    private volatile transient int sizeCtl;
    volatile transient Node[] table;
    private volatile transient int transferIndex;
    private transient ValuesView values;
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};

    static final int spread(int i) {
        return (i ^ (i >>> 16)) & Integer.MAX_VALUE;
    }

    static {
        Unsafe unsafe = Unsafe.getUnsafe();
        U = unsafe;
        SIZECTL = unsafe.objectFieldOffset(ConcurrentHashMap.class, "sizeCtl");
        TRANSFERINDEX = unsafe.objectFieldOffset(ConcurrentHashMap.class, "transferIndex");
        BASECOUNT = unsafe.objectFieldOffset(ConcurrentHashMap.class, "baseCount");
        CELLSBUSY = unsafe.objectFieldOffset(ConcurrentHashMap.class, "cellsBusy");
        CELLVALUE = unsafe.objectFieldOffset(CounterCell.class, "value");
        ABASE = unsafe.arrayBaseOffset(Node[].class);
        int arrayIndexScale = unsafe.arrayIndexScale(Node[].class);
        if (((arrayIndexScale - 1) & arrayIndexScale) != 0) {
            throw new ExceptionInInitializerError("array index scale not a power of two");
        }
        ASHIFT = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
    }

    static class Node implements Map.Entry {
        final int hash;
        final Object key;
        volatile Node next;
        volatile Object val;

        Node(int i, Object obj, Object obj2) {
            this.hash = i;
            this.key = obj;
            this.val = obj2;
        }

        Node(int i, Object obj, Object obj2, Node node) {
            this(i, obj, obj2);
            this.next = node;
        }

        public final Object getKey() {
            return this.key;
        }

        public final Object getValue() {
            return this.val;
        }

        public final int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        public final String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }

        public final Object setValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
                return false;
            }
            Object obj2 = this.key;
            if (key != obj2 && !key.equals(obj2)) {
                return false;
            }
            Object obj3 = this.val;
            return value == obj3 || value.equals(obj3);
        }

        Node find(int i, Object obj) {
            Object obj2;
            if (obj == null) {
                return null;
            }
            Node node = this;
            do {
                if (node.hash == i && ((obj2 = node.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                    return node;
                }
                node = node.next;
            } while (node != null);
            return null;
        }
    }

    private static final int tableSizeFor(int i) {
        int numberOfLeadingZeros = (-1) >>> Integer.numberOfLeadingZeros(i - 1);
        if (numberOfLeadingZeros < 0) {
            return 1;
        }
        if (numberOfLeadingZeros >= 1073741824) {
            return 1073741824;
        }
        return numberOfLeadingZeros + 1;
    }

    static Class comparableClassFor(Object obj) {
        Type[] actualTypeArguments;
        if (!(obj instanceof Comparable)) {
            return null;
        }
        Type type = obj.getClass();
        if (type != String.class) {
            ParameterizedType[] genericInterfaces = type.getGenericInterfaces();
            if (genericInterfaces == null) {
                return null;
            }
            for (ParameterizedType parameterizedType : genericInterfaces) {
                if (parameterizedType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType2 = parameterizedType;
                    if (parameterizedType2.getRawType() == Comparable.class && (actualTypeArguments = parameterizedType2.getActualTypeArguments()) != null && actualTypeArguments.length == 1 && actualTypeArguments[0] == type) {
                    }
                }
            }
            return null;
        }
        return type;
    }

    static int compareComparables(Class cls, Object obj, Object obj2) {
        if (obj2 == null || obj2.getClass() != cls) {
            return 0;
        }
        return ((Comparable) obj).compareTo(obj2);
    }

    static final Node tabAt(Node[] nodeArr, int i) {
        return (Node) U.getObjectAcquire(nodeArr, (i << ASHIFT) + ABASE);
    }

    static final boolean casTabAt(Node[] nodeArr, int i, Node node, Node node2) {
        return U.compareAndSetObject(nodeArr, (i << ASHIFT) + ABASE, node, node2);
    }

    static final void setTabAt(Node[] nodeArr, int i, Node node) {
        U.putObjectRelease(nodeArr, (i << ASHIFT) + ABASE, node);
    }

    public ConcurrentHashMap() {
    }

    public ConcurrentHashMap(int i) {
        this(i, 0.75f, 1);
    }

    public ConcurrentHashMap(Map map) {
        this.sizeCtl = 16;
        putAll(map);
    }

    public ConcurrentHashMap(int i, float f) {
        this(i, f, 1);
    }

    public ConcurrentHashMap(int i, float f, int i2) {
        if (f <= 0.0f || i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        double d = (i < i2 ? i2 : i) / f;
        Double.isNaN(d);
        long j = (long) (d + 1.0d);
        this.sizeCtl = j >= 1073741824 ? 1073741824 : tableSizeFor((int) j);
    }

    public int size() {
        long sumCount = sumCount();
        if (sumCount < 0) {
            return 0;
        }
        if (sumCount > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) sumCount;
    }

    public boolean isEmpty() {
        return sumCount() <= 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x004e, code lost:
    
        return r1.val;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object get(java.lang.Object r5) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            int r0 = spread(r0)
            java.util.concurrent.ConcurrentHashMap$Node[] r1 = r4.table
            r2 = 0
            if (r1 == 0) goto L4f
            int r3 = r1.length
            if (r3 <= 0) goto L4f
            int r3 = r3 + (-1)
            r3 = r3 & r0
            java.util.concurrent.ConcurrentHashMap$Node r1 = tabAt(r1, r3)
            if (r1 == 0) goto L4f
            int r3 = r1.hash
            if (r3 != r0) goto L2c
            java.lang.Object r3 = r1.key
            if (r3 == r5) goto L29
            if (r3 == 0) goto L38
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L38
        L29:
            java.lang.Object r5 = r1.val
            return r5
        L2c:
            if (r3 >= 0) goto L38
            java.util.concurrent.ConcurrentHashMap$Node r5 = r1.find(r0, r5)
            if (r5 == 0) goto L37
            java.lang.Object r5 = r5.val
            return r5
        L37:
            return r2
        L38:
            java.util.concurrent.ConcurrentHashMap$Node r1 = r1.next
            if (r1 == 0) goto L4f
            int r3 = r1.hash
            if (r3 != r0) goto L38
            java.lang.Object r3 = r1.key
            if (r3 == r5) goto L4c
            if (r3 == 0) goto L38
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L38
        L4c:
            java.lang.Object r5 = r1.val
            return r5
        L4f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.get(java.lang.Object):java.lang.Object");
    }

    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(Object obj) {
        obj.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                Object obj2 = advance.val;
                if (obj2 == obj) {
                    return true;
                }
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object put(Object obj, Object obj2) {
        return putVal(obj, obj2, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b1, code lost:
    
        addCount(1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b6, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00a2, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.lang.Object putVal(java.lang.Object r9, java.lang.Object r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.putVal(java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    public void putAll(Map map) {
        tryPresize(map.size());
        for (Map.Entry entry : map.entrySet()) {
            putVal(entry.getKey(), entry.getValue(), false);
        }
    }

    public Object remove(Object obj) {
        return replaceNode(obj, null, null);
    }

    final Object replaceNode(Object obj, Object obj2, Object obj3) {
        int length;
        int i;
        Node tabAt;
        boolean z;
        Object obj4;
        TreeNode findTreeNode;
        Object obj5;
        int spread = spread(obj.hashCode());
        Node[] nodeArr = this.table;
        while (true) {
            if (nodeArr == null || (length = nodeArr.length) == 0 || (tabAt = tabAt(nodeArr, (i = (length - 1) & spread))) == null) {
                break;
            }
            int i2 = tabAt.hash;
            if (i2 == -1) {
                nodeArr = helpTransfer(nodeArr, tabAt);
            } else {
                synchronized (tabAt) {
                    if (tabAt(nodeArr, i) == tabAt) {
                        z = true;
                        if (i2 >= 0) {
                            Node node = null;
                            Node node2 = tabAt;
                            while (true) {
                                if (node2.hash != spread || ((obj5 = node2.key) != obj && (obj5 == null || !obj.equals(obj5)))) {
                                    Node node3 = node2.next;
                                    if (node3 == null) {
                                        break;
                                    }
                                    node = node2;
                                    node2 = node3;
                                }
                            }
                            obj4 = node2.val;
                            if (obj3 != null && obj3 != obj4 && (obj4 == null || !obj3.equals(obj4))) {
                                obj4 = null;
                            } else if (obj2 != null) {
                                node2.val = obj2;
                            } else if (node != null) {
                                node.next = node2.next;
                            } else {
                                setTabAt(nodeArr, i, node2.next);
                            }
                        } else if (tabAt instanceof TreeBin) {
                            TreeBin treeBin = (TreeBin) tabAt;
                            TreeNode treeNode = treeBin.root;
                            if (treeNode != null && (findTreeNode = treeNode.findTreeNode(spread, obj, null)) != null) {
                                obj4 = findTreeNode.val;
                                if (obj3 == null || obj3 == obj4 || (obj4 != null && obj3.equals(obj4))) {
                                    if (obj2 != null) {
                                        findTreeNode.val = obj2;
                                    } else if (treeBin.removeTreeNode(findTreeNode)) {
                                        setTabAt(nodeArr, i, untreeify(treeBin.first));
                                    }
                                }
                            }
                            obj4 = null;
                        } else if (tabAt instanceof ReservationNode) {
                            throw new IllegalStateException("Recursive update");
                        }
                    }
                    z = false;
                    obj4 = null;
                }
                if (z) {
                    if (obj4 != null) {
                        if (obj2 == null) {
                            addCount(-1L, -1);
                        }
                        return obj4;
                    }
                }
            }
        }
        return null;
    }

    public void clear() {
        Node tabAt;
        Node node;
        Node[] nodeArr = this.table;
        long j = 0;
        loop0: while (true) {
            int i = 0;
            while (nodeArr != null && i < nodeArr.length) {
                tabAt = tabAt(nodeArr, i);
                if (tabAt == null) {
                    i++;
                } else {
                    int i2 = tabAt.hash;
                    if (i2 == -1) {
                        break;
                    }
                    synchronized (tabAt) {
                        if (tabAt(nodeArr, i) == tabAt) {
                            if (i2 >= 0) {
                                node = tabAt;
                            } else {
                                node = tabAt instanceof TreeBin ? ((TreeBin) tabAt).first : null;
                            }
                            while (node != null) {
                                j--;
                                node = node.next;
                            }
                            setTabAt(nodeArr, i, null);
                            i++;
                        }
                    }
                }
            }
            nodeArr = helpTransfer(nodeArr, tabAt);
        }
        if (j != 0) {
            addCount(j, -1);
        }
    }

    public Set keySet() {
        KeySetView keySetView = this.keySet;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView keySetView2 = new KeySetView(this, null);
        this.keySet = keySetView2;
        return keySetView2;
    }

    public Collection values() {
        ValuesView valuesView = this.values;
        if (valuesView != null) {
            return valuesView;
        }
        ValuesView valuesView2 = new ValuesView(this);
        this.values = valuesView2;
        return valuesView2;
    }

    public Set entrySet() {
        EntrySetView entrySetView = this.entrySet;
        if (entrySetView != null) {
            return entrySetView;
        }
        EntrySetView entrySetView2 = new EntrySetView(this);
        this.entrySet = entrySetView2;
        return entrySetView2;
    }

    public int hashCode() {
        Node[] nodeArr = this.table;
        int i = 0;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                i += advance.val.hashCode() ^ advance.key.hashCode();
            }
        }
        return i;
    }

    public String toString() {
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        StringBuilder sb = new StringBuilder("{");
        Node advance = traverser.advance();
        if (advance != null) {
            while (true) {
                Object obj = advance.key;
                Object obj2 = advance.val;
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                sb.append('=');
                if (obj2 == this) {
                    obj2 = "(this Map)";
                }
                sb.append(obj2);
                advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        Object value;
        Object obj2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        Traverser traverser = new Traverser(nodeArr, length, 0, length);
        while (true) {
            Node advance = traverser.advance();
            if (advance != null) {
                Object obj3 = advance.val;
                Object obj4 = map.get(advance.key);
                if (obj4 == null || (obj4 != obj3 && !obj4.equals(obj3))) {
                    break;
                }
            } else {
                for (Map.Entry entry : map.entrySet()) {
                    Object key = entry.getKey();
                    if (key == null || (value = entry.getValue()) == null || (obj2 = get(key)) == null || (value != obj2 && !value.equals(obj2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    static class Segment extends ReentrantLock implements Serializable {
        private static final long serialVersionUID = 2249069246763182397L;
        final float loadFactor;

        Segment(float f) {
            this.loadFactor = f;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int i = 1;
        int i2 = 0;
        while (i < 16) {
            i2++;
            i <<= 1;
        }
        int i3 = 32 - i2;
        int i4 = i - 1;
        Segment[] segmentArr = new Segment[16];
        for (int i5 = 0; i5 < 16; i5++) {
            segmentArr[i5] = new Segment(0.75f);
        }
        ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("segments", segmentArr);
        putFields.put("segmentShift", i3);
        putFields.put("segmentMask", i4);
        objectOutputStream.writeFields();
        Node[] nodeArr = this.table;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                objectOutputStream.writeObject(advance.key);
                objectOutputStream.writeObject(advance.val);
            }
        }
        objectOutputStream.writeObject((Object) null);
        objectOutputStream.writeObject((Object) null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        long j;
        long j2;
        Object obj;
        this.sizeCtl = -1;
        objectInputStream.defaultReadObject();
        long j3 = 0;
        long j4 = 0;
        Node node = null;
        while (true) {
            Object readObject = objectInputStream.readObject();
            Object readObject2 = objectInputStream.readObject();
            j = 1;
            if (readObject == null || readObject2 == null) {
                break;
            }
            j4++;
            node = new Node(spread(readObject.hashCode()), readObject, readObject2, node);
        }
        if (j4 == 0) {
            this.sizeCtl = 0;
            return;
        }
        double d = j4 / 0.75f;
        Double.isNaN(d);
        long j5 = (long) (d + 1.0d);
        int tableSizeFor = j5 >= 1073741824 ? 1073741824 : tableSizeFor((int) j5);
        Node[] nodeArr = new Node[tableSizeFor];
        int i = tableSizeFor - 1;
        while (node != null) {
            Node node2 = node.next;
            int i2 = node.hash;
            int i3 = i2 & i;
            Node tabAt = tabAt(nodeArr, i3);
            boolean z = true;
            if (tabAt == null) {
                j2 = j;
            } else {
                Object obj2 = node.key;
                if (tabAt.hash < 0) {
                    if (((TreeBin) tabAt).putTreeVal(i2, obj2, node.val) == null) {
                        j3 += j;
                    }
                    j2 = j;
                } else {
                    j2 = j;
                    int i4 = 0;
                    for (Node node3 = tabAt; node3 != null; node3 = node3.next) {
                        if (node3.hash == i2 && ((obj = node3.key) == obj2 || (obj != null && obj2.equals(obj)))) {
                            z = false;
                            break;
                        }
                        i4++;
                    }
                    if (z && i4 >= 8) {
                        j3 += j2;
                        node.next = tabAt;
                        Node node4 = node;
                        TreeNode treeNode = null;
                        TreeNode treeNode2 = null;
                        while (node4 != null) {
                            TreeNode treeNode3 = new TreeNode(node4.hash, node4.key, node4.val, null, null);
                            treeNode3.prev = treeNode2;
                            if (treeNode2 == null) {
                                treeNode = treeNode3;
                            } else {
                                treeNode2.next = treeNode3;
                            }
                            node4 = node4.next;
                            treeNode2 = treeNode3;
                        }
                        setTabAt(nodeArr, i3, new TreeBin(treeNode));
                    }
                }
                z = false;
            }
            if (z) {
                j3 += j2;
                node.next = tabAt;
                setTabAt(nodeArr, i3, node);
            }
            node = node2;
            j = j2;
        }
        this.table = nodeArr;
        this.sizeCtl = tableSizeFor - (tableSizeFor >>> 2);
        this.baseCount = j3;
    }

    public Object putIfAbsent(Object obj, Object obj2) {
        return putVal(obj, obj2, true);
    }

    public boolean remove(Object obj, Object obj2) {
        obj.getClass();
        return (obj2 == null || replaceNode(obj, null, obj2) == null) ? false : true;
    }

    public boolean replace(Object obj, Object obj2, Object obj3) {
        if (obj == null || obj2 == null || obj3 == null) {
            throw null;
        }
        return replaceNode(obj, obj3, obj2) != null;
    }

    public Object replace(Object obj, Object obj2) {
        if (obj == null) {
            throw null;
        }
        if (obj2 == null) {
            throw null;
        }
        return replaceNode(obj, obj2, null);
    }

    public Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 == null ? obj2 : obj3;
    }

    public void forEach(BiConsumer biConsumer) {
        biConsumer.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr == null) {
            return;
        }
        Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
        while (true) {
            Node advance = traverser.advance();
            if (advance == null) {
                return;
            } else {
                biConsumer.accept(advance.key, advance.val);
            }
        }
    }

    public void replaceAll(BiFunction biFunction) {
        biFunction.getClass();
        Node[] nodeArr = this.table;
        if (nodeArr == null) {
            return;
        }
        Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
        while (true) {
            Node advance = traverser.advance();
            if (advance == null) {
                return;
            }
            Object obj = advance.val;
            Object obj2 = advance.key;
            do {
                Object apply = biFunction.apply(obj2, obj);
                apply.getClass();
                if (replaceNode(obj2, apply, obj) == null) {
                    obj = get(obj2);
                }
            } while (obj != null);
        }
    }

    boolean removeEntryIf(Predicate predicate) {
        predicate.getClass();
        Node[] nodeArr = this.table;
        boolean z = false;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                Object obj = advance.key;
                Object obj2 = advance.val;
                if (predicate.test(new AbstractMap.SimpleImmutableEntry(obj, obj2)) && replaceNode(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    boolean removeValueIf(Predicate predicate) {
        predicate.getClass();
        Node[] nodeArr = this.table;
        boolean z = false;
        if (nodeArr != null) {
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    break;
                }
                Object obj = advance.key;
                Object obj2 = advance.val;
                if (predicate.test(obj2) && replaceNode(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f3, code lost:
    
        if (r5 == null) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00f5, code lost:
    
        addCount(1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00fa, code lost:
    
        return r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object computeIfAbsent(java.lang.Object r13, java.util.function.Function r14) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.computeIfAbsent(java.lang.Object, java.util.function.Function):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x00a6, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object computeIfPresent(java.lang.Object r14, java.util.function.BiFunction r15) {
        /*
            r13 = this;
            r0 = 0
            if (r14 == 0) goto Lba
            if (r15 == 0) goto Lba
            int r1 = r14.hashCode()
            int r1 = spread(r1)
            java.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.table
            r3 = 0
            r5 = r0
            r4 = 0
        L12:
            if (r2 == 0) goto Lb4
            int r6 = r2.length
            if (r6 != 0) goto L19
            goto Lb4
        L19:
            int r6 = r6 + (-1)
            r6 = r6 & r1
            java.util.concurrent.ConcurrentHashMap$Node r7 = tabAt(r2, r6)
            if (r7 != 0) goto L24
            goto Laa
        L24:
            int r8 = r7.hash
            r9 = -1
            if (r8 != r9) goto L2e
            java.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.helpTransfer(r2, r7)
            goto L12
        L2e:
            monitor-enter(r7)
            java.util.concurrent.ConcurrentHashMap$Node r10 = tabAt(r2, r6)     // Catch: java.lang.Throwable -> Lb1
            if (r10 != r7) goto La7
            if (r8 < 0) goto L6c
            r4 = 1
            r10 = r0
            r8 = r7
        L3a:
            int r11 = r8.hash     // Catch: java.lang.Throwable -> Lb1
            if (r11 != r1) goto L61
            java.lang.Object r11 = r8.key     // Catch: java.lang.Throwable -> Lb1
            if (r11 == r14) goto L4a
            if (r11 == 0) goto L61
            boolean r11 = r14.equals(r11)     // Catch: java.lang.Throwable -> Lb1
            if (r11 == 0) goto L61
        L4a:
            java.lang.Object r5 = r8.val     // Catch: java.lang.Throwable -> Lb1
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> Lb1
            if (r5 == 0) goto L55
            r8.val = r5     // Catch: java.lang.Throwable -> Lb1
            goto La7
        L55:
            java.util.concurrent.ConcurrentHashMap$Node r3 = r8.next     // Catch: java.lang.Throwable -> Lb1
            if (r10 == 0) goto L5c
            r10.next = r3     // Catch: java.lang.Throwable -> Lb1
            goto L5f
        L5c:
            setTabAt(r2, r6, r3)     // Catch: java.lang.Throwable -> Lb1
        L5f:
            r3 = -1
            goto La7
        L61:
            java.util.concurrent.ConcurrentHashMap$Node r10 = r8.next     // Catch: java.lang.Throwable -> Lb1
            if (r10 != 0) goto L66
            goto La7
        L66:
            int r4 = r4 + 1
            r12 = r10
            r10 = r8
            r8 = r12
            goto L3a
        L6c:
            boolean r8 = r7 instanceof java.util.concurrent.ConcurrentHashMap.TreeBin     // Catch: java.lang.Throwable -> Lb1
            if (r8 == 0) goto L9a
            r4 = r7
            java.util.concurrent.ConcurrentHashMap$TreeBin r4 = (java.util.concurrent.ConcurrentHashMap.TreeBin) r4     // Catch: java.lang.Throwable -> Lb1
            java.util.concurrent.ConcurrentHashMap$TreeNode r8 = r4.root     // Catch: java.lang.Throwable -> Lb1
            if (r8 == 0) goto L98
            java.util.concurrent.ConcurrentHashMap$TreeNode r8 = r8.findTreeNode(r1, r14, r0)     // Catch: java.lang.Throwable -> Lb1
            if (r8 == 0) goto L98
            java.lang.Object r5 = r8.val     // Catch: java.lang.Throwable -> Lb1
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> Lb1
            if (r5 == 0) goto L88
            r8.val = r5     // Catch: java.lang.Throwable -> Lb1
            goto L98
        L88:
            boolean r3 = r4.removeTreeNode(r8)     // Catch: java.lang.Throwable -> Lb1
            if (r3 == 0) goto L97
            java.util.concurrent.ConcurrentHashMap$TreeNode r3 = r4.first     // Catch: java.lang.Throwable -> Lb1
            java.util.concurrent.ConcurrentHashMap$Node r3 = untreeify(r3)     // Catch: java.lang.Throwable -> Lb1
            setTabAt(r2, r6, r3)     // Catch: java.lang.Throwable -> Lb1
        L97:
            r3 = -1
        L98:
            r4 = 2
            goto La7
        L9a:
            boolean r6 = r7 instanceof java.util.concurrent.ConcurrentHashMap.ReservationNode     // Catch: java.lang.Throwable -> Lb1
            if (r6 != 0) goto L9f
            goto La7
        L9f:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lb1
            java.lang.String r15 = "Recursive update"
            r14.<init>(r15)     // Catch: java.lang.Throwable -> Lb1
            throw r14     // Catch: java.lang.Throwable -> Lb1
        La7:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> Lb1
            if (r4 == 0) goto L12
        Laa:
            if (r3 == 0) goto Lb0
            long r14 = (long) r3
            r13.addCount(r14, r4)
        Lb0:
            return r5
        Lb1:
            r14 = move-exception
            monitor-exit(r7)     // Catch: java.lang.Throwable -> Lb1
            throw r14
        Lb4:
            java.util.concurrent.ConcurrentHashMap$Node[] r2 = r13.initTable()
            goto L12
        Lba:
            goto Lbc
        Lbb:
            throw r0
        Lbc:
            goto Lbb
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.computeIfPresent(java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:89:0x010b, code lost:
    
        if (r4 == 0) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x010d, code lost:
    
        addCount(r4, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0111, code lost:
    
        return r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object compute(java.lang.Object r14, java.util.function.BiFunction r15) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.compute(java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x00d7, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object merge(java.lang.Object r18, java.lang.Object r19, java.util.function.BiFunction r20) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    public boolean contains(Object obj) {
        return containsValue(obj);
    }

    public Enumeration keys() {
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        return new KeyIterator(nodeArr, length, 0, length, this);
    }

    public Enumeration elements() {
        Node[] nodeArr = this.table;
        int length = nodeArr == null ? 0 : nodeArr.length;
        return new ValueIterator(nodeArr, length, 0, length, this);
    }

    public long mappingCount() {
        long sumCount = sumCount();
        if (sumCount < 0) {
            return 0L;
        }
        return sumCount;
    }

    public static KeySetView newKeySet() {
        return new KeySetView(new ConcurrentHashMap(), Boolean.TRUE);
    }

    public static KeySetView newKeySet(int i) {
        return new KeySetView(new ConcurrentHashMap(i), Boolean.TRUE);
    }

    public KeySetView keySet(Object obj) {
        obj.getClass();
        return new KeySetView(this, obj);
    }

    static final class ForwardingNode extends Node {
        final Node[] nextTable;

        ForwardingNode(Node[] nodeArr) {
            super(-1, null, null);
            this.nextTable = nodeArr;
        }

        Node find(int i, Object obj) {
            int length;
            Node tabAt;
            Object obj2;
            Node[] nodeArr = this.nextTable;
            loop0: while (obj != null && nodeArr != null && (length = nodeArr.length) != 0 && (tabAt = ConcurrentHashMap.tabAt(nodeArr, (length - 1) & i)) != null) {
                do {
                    int i2 = tabAt.hash;
                    if (i2 == i && ((obj2 = tabAt.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                        return tabAt;
                    }
                    if (i2 < 0) {
                        if (tabAt instanceof ForwardingNode) {
                            nodeArr = ((ForwardingNode) tabAt).nextTable;
                        } else {
                            return tabAt.find(i, obj);
                        }
                    } else {
                        tabAt = tabAt.next;
                    }
                } while (tabAt != null);
            }
            return null;
        }
    }

    static final class ReservationNode extends Node {
        Node find(int i, Object obj) {
            return null;
        }

        ReservationNode() {
            super(-3, null, null);
        }
    }

    static final int resizeStamp(int i) {
        return Integer.numberOfLeadingZeros(i) | 32768;
    }

    private final Node[] initTable() {
        while (true) {
            Node[] nodeArr = this.table;
            if (nodeArr != null && nodeArr.length != 0) {
                return nodeArr;
            }
            int i = this.sizeCtl;
            if (i < 0) {
                Thread.yield();
            } else if (U.compareAndSetInt(this, SIZECTL, i, -1)) {
                try {
                    Node[] nodeArr2 = this.table;
                    if (nodeArr2 == null || nodeArr2.length == 0) {
                        int i2 = i > 0 ? i : 16;
                        Node[] nodeArr3 = new Node[i2];
                        this.table = nodeArr3;
                        i = i2 - (i2 >>> 2);
                        nodeArr2 = nodeArr3;
                    }
                    return nodeArr2;
                } finally {
                    this.sizeCtl = i;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void addCount(long r21, int r23) {
        /*
            r20 = this;
            r1 = r20
            r8 = r21
            r10 = r23
            java.util.concurrent.ConcurrentHashMap$CounterCell[] r11 = r1.counterCells
            if (r11 != 0) goto L18
            jdk.internal.misc.Unsafe r0 = java.util.concurrent.ConcurrentHashMap.U
            long r2 = java.util.concurrent.ConcurrentHashMap.BASECOUNT
            long r4 = r1.baseCount
            long r6 = r4 + r8
            boolean r0 = r0.compareAndSetLong(r1, r2, r4, r6)
            if (r0 != 0) goto L41
        L18:
            r0 = 1
            if (r11 == 0) goto L9a
            int r2 = r11.length
            int r2 = r2 - r0
            if (r2 < 0) goto L9a
            int r3 = java.util.concurrent.ThreadLocalRandom.getProbe()
            r2 = r2 & r3
            r13 = r11[r2]
            if (r13 == 0) goto L9a
            jdk.internal.misc.Unsafe r12 = java.util.concurrent.ConcurrentHashMap.U
            long r14 = java.util.concurrent.ConcurrentHashMap.CELLVALUE
            long r2 = r13.value
            long r18 = r2 + r8
            r16 = r2
            boolean r2 = r12.compareAndSetLong(r13, r14, r16, r18)
            if (r2 != 0) goto L3a
            r0 = r2
            goto L9a
        L3a:
            if (r10 > r0) goto L3d
            goto L99
        L3d:
            long r6 = r1.sumCount()
        L41:
            if (r10 < 0) goto L99
        L43:
            int r4 = r1.sizeCtl
            long r2 = (long) r4
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 < 0) goto L99
            java.util.concurrent.ConcurrentHashMap$Node[] r6 = r1.table
            if (r6 == 0) goto L99
            int r0 = r6.length
            r2 = 1073741824(0x40000000, float:2.0)
            if (r0 >= r2) goto L99
            int r0 = resizeStamp(r0)
            if (r4 >= 0) goto L80
            int r2 = r4 >>> 16
            if (r2 != r0) goto L99
            int r2 = r0 + 1
            if (r4 == r2) goto L99
            r2 = 65535(0xffff, float:9.1834E-41)
            int r0 = r0 + r2
            if (r4 == r0) goto L99
            java.util.concurrent.ConcurrentHashMap$Node[] r7 = r1.nextTable
            if (r7 == 0) goto L99
            int r0 = r1.transferIndex
            if (r0 > 0) goto L70
            goto L99
        L70:
            jdk.internal.misc.Unsafe r0 = java.util.concurrent.ConcurrentHashMap.U
            long r2 = java.util.concurrent.ConcurrentHashMap.SIZECTL
            int r5 = r4 + 1
            boolean r0 = r0.compareAndSetInt(r1, r2, r4, r5)
            if (r0 == 0) goto L94
            r1.transfer(r6, r7)
            goto L94
        L80:
            r2 = r0
            jdk.internal.misc.Unsafe r0 = java.util.concurrent.ConcurrentHashMap.U
            r5 = r2
            long r2 = java.util.concurrent.ConcurrentHashMap.SIZECTL
            int r5 = r5 << 16
            int r5 = r5 + 2
            boolean r0 = r0.compareAndSetInt(r1, r2, r4, r5)
            if (r0 == 0) goto L94
            r0 = 0
            r1.transfer(r6, r0)
        L94:
            long r6 = r1.sumCount()
            goto L43
        L99:
            return
        L9a:
            r1.fullAddCount(r8, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.addCount(long, int):void");
    }

    final Node[] helpTransfer(Node[] nodeArr, Node node) {
        Node[] nodeArr2;
        int i;
        if (nodeArr != null && (node instanceof ForwardingNode) && (nodeArr2 = ((ForwardingNode) node).nextTable) != null) {
            int resizeStamp = resizeStamp(nodeArr.length);
            while (nodeArr2 == this.nextTable && this.table == nodeArr && (i = this.sizeCtl) < 0 && (i >>> 16) == resizeStamp && i != resizeStamp + 1 && i != 65535 + resizeStamp && this.transferIndex > 0) {
                if (U.compareAndSetInt(this, SIZECTL, i, i + 1)) {
                    transfer(nodeArr, nodeArr2);
                    return nodeArr2;
                }
            }
            return nodeArr2;
        }
        return this.table;
    }

    private final void tryPresize(int i) {
        int length;
        int tableSizeFor = i >= 536870912 ? 1073741824 : tableSizeFor(i + (i >>> 1) + 1);
        while (true) {
            int i2 = this.sizeCtl;
            if (i2 >= 0) {
                Node[] nodeArr = this.table;
                if (nodeArr != null && (length = nodeArr.length) != 0) {
                    if (tableSizeFor <= i2 || length >= 1073741824) {
                        break;
                    } else if (nodeArr == this.table) {
                        if (U.compareAndSetInt(this, SIZECTL, i2, (resizeStamp(length) << 16) + 2)) {
                            transfer(nodeArr, null);
                        }
                    }
                } else {
                    int i3 = i2 > tableSizeFor ? i2 : tableSizeFor;
                    if (U.compareAndSetInt(this, SIZECTL, i2, -1)) {
                        try {
                            if (this.table == nodeArr) {
                                this.table = new Node[i3];
                                i2 = i3 - (i3 >>> 2);
                            }
                        } finally {
                            this.sizeCtl = i2;
                        }
                    } else {
                        continue;
                    }
                }
            } else {
                break;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11, types: [java.util.concurrent.ConcurrentHashMap$Node] */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.util.concurrent.ConcurrentHashMap$Node] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.util.concurrent.ConcurrentHashMap$Node] */
    /* JADX WARN: Type inference failed for: r8v13, types: [java.util.concurrent.ConcurrentHashMap$Node] */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.util.concurrent.ConcurrentHashMap$Node] */
    private final void transfer(Node[] nodeArr, Node[] nodeArr2) {
        Node[] nodeArr3;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Node treeBin;
        Node treeBin2;
        TreeNode treeNode;
        int i6;
        ConcurrentHashMap concurrentHashMap = this;
        int length = nodeArr.length;
        int i7 = NCPU;
        int i8 = i7 > 1 ? (length >>> 3) / i7 : length;
        int i9 = i8 < 16 ? 16 : i8;
        if (nodeArr2 == null) {
            try {
                Node[] nodeArr4 = new Node[length << 1];
                concurrentHashMap.nextTable = nodeArr4;
                concurrentHashMap.transferIndex = length;
                nodeArr3 = nodeArr4;
            } catch (Throwable unused) {
                concurrentHashMap.sizeCtl = Integer.MAX_VALUE;
                return;
            }
        } else {
            nodeArr3 = nodeArr2;
        }
        int length2 = nodeArr3.length;
        ForwardingNode forwardingNode = new ForwardingNode(nodeArr3);
        int i10 = 0;
        int i11 = 0;
        boolean z = true;
        boolean z2 = false;
        while (true) {
            if (z) {
                int i12 = i10 - 1;
                if (i12 >= i11 || z2) {
                    i11 = i11;
                    i10 = i12;
                } else {
                    int i13 = concurrentHashMap.transferIndex;
                    if (i13 <= 0) {
                        i10 = -1;
                    } else {
                        Unsafe unsafe = U;
                        int i14 = i11;
                        long j = TRANSFERINDEX;
                        if (i13 > i9) {
                            i = i12;
                            i2 = i13 - i9;
                        } else {
                            i = i12;
                            i2 = 0;
                        }
                        boolean compareAndSetInt = unsafe.compareAndSetInt(concurrentHashMap, j, i13, i2);
                        i11 = i2;
                        if (compareAndSetInt) {
                            i10 = i13 - 1;
                        } else {
                            i11 = i14;
                            i10 = i;
                        }
                    }
                }
                z = false;
            } else {
                int i15 = i11;
                TreeNode treeNode2 = null;
                if (i10 < 0 || i10 >= length || (i5 = i10 + length) >= length2) {
                    i3 = length;
                    i4 = i9;
                    if (z2) {
                        concurrentHashMap.nextTable = null;
                        concurrentHashMap.table = nodeArr3;
                        concurrentHashMap.sizeCtl = (i3 << 1) - (i3 >>> 1);
                        return;
                    }
                    int i16 = i10;
                    Unsafe unsafe2 = U;
                    long j2 = SIZECTL;
                    int i17 = concurrentHashMap.sizeCtl;
                    if (!unsafe2.compareAndSetInt(concurrentHashMap, j2, i17, i17 - 1)) {
                        i10 = i16;
                    } else {
                        if (i17 - 2 != (resizeStamp(i3) << 16)) {
                            return;
                        }
                        i10 = i3;
                        z = true;
                        z2 = true;
                    }
                } else {
                    ?? tabAt = tabAt(nodeArr, i10);
                    if (tabAt == 0) {
                        z = casTabAt(nodeArr, i10, null, forwardingNode);
                        i3 = length;
                        i4 = i9;
                    } else {
                        int i18 = tabAt.hash;
                        if (i18 == -1) {
                            i3 = length;
                            i4 = i9;
                            z = true;
                        } else {
                            synchronized (tabAt) {
                                if (tabAt(nodeArr, i10) == tabAt) {
                                    if (i18 >= 0) {
                                        int i19 = i18 & length;
                                        TreeNode treeNode3 = tabAt;
                                        for (TreeNode treeNode4 = tabAt.next; treeNode4 != null; treeNode4 = treeNode4.next) {
                                            int i20 = treeNode4.hash & length;
                                            if (i20 != i19) {
                                                treeNode3 = treeNode4;
                                                i19 = i20;
                                            }
                                        }
                                        if (i19 == 0) {
                                            treeNode = null;
                                            treeNode2 = treeNode3;
                                        } else {
                                            treeNode = treeNode3;
                                        }
                                        Node node = tabAt;
                                        while (node != treeNode3) {
                                            int i21 = node.hash;
                                            Object obj = node.key;
                                            int i22 = length;
                                            Object obj2 = node.val;
                                            if ((i21 & i22) == 0) {
                                                i6 = i9;
                                                treeNode2 = new Node(i21, obj, obj2, treeNode2);
                                            } else {
                                                i6 = i9;
                                                treeNode = new Node(i21, obj, obj2, treeNode);
                                            }
                                            node = node.next;
                                            length = i22;
                                            i9 = i6;
                                        }
                                        i3 = length;
                                        i4 = i9;
                                        setTabAt(nodeArr3, i10, treeNode2);
                                        setTabAt(nodeArr3, i5, treeNode);
                                        setTabAt(nodeArr, i10, forwardingNode);
                                    } else {
                                        i3 = length;
                                        i4 = i9;
                                        if (tabAt instanceof TreeBin) {
                                            TreeBin treeBin3 = (TreeBin) tabAt;
                                            TreeNode treeNode5 = null;
                                            TreeNode treeNode6 = null;
                                            Node node2 = treeBin3.first;
                                            int i23 = 0;
                                            int i24 = 0;
                                            TreeNode treeNode7 = null;
                                            while (node2 != null) {
                                                TreeBin treeBin4 = treeBin3;
                                                int i25 = node2.hash;
                                                TreeNode treeNode8 = new TreeNode(i25, node2.key, node2.val, null, null);
                                                if ((i25 & i3) == 0) {
                                                    treeNode8.prev = treeNode6;
                                                    if (treeNode6 == null) {
                                                        treeNode2 = treeNode8;
                                                    } else {
                                                        treeNode6.next = treeNode8;
                                                    }
                                                    i23++;
                                                    treeNode6 = treeNode8;
                                                } else {
                                                    treeNode8.prev = treeNode5;
                                                    if (treeNode5 == null) {
                                                        treeNode7 = treeNode8;
                                                    } else {
                                                        treeNode5.next = treeNode8;
                                                    }
                                                    i24++;
                                                    treeNode5 = treeNode8;
                                                }
                                                node2 = node2.next;
                                                treeBin3 = treeBin4;
                                            }
                                            TreeBin treeBin5 = treeBin3;
                                            if (i23 <= 6) {
                                                treeBin = untreeify(treeNode2);
                                            } else {
                                                treeBin = i24 != 0 ? new TreeBin(treeNode2) : treeBin5;
                                            }
                                            if (i24 <= 6) {
                                                treeBin2 = untreeify(treeNode7);
                                            } else {
                                                treeBin2 = i23 != 0 ? new TreeBin(treeNode7) : treeBin5;
                                            }
                                            setTabAt(nodeArr3, i10, treeBin);
                                            setTabAt(nodeArr3, i5, treeBin2);
                                            setTabAt(nodeArr, i10, forwardingNode);
                                        }
                                    }
                                    z = true;
                                } else {
                                    i3 = length;
                                    i4 = i9;
                                }
                            }
                        }
                    }
                }
                concurrentHashMap = this;
                i11 = i15;
                length = i3;
                i9 = i4;
            }
        }
    }

    static final class CounterCell {
        volatile long value;

        CounterCell(long j) {
            this.value = j;
        }
    }

    final long sumCount() {
        CounterCell[] counterCellArr = this.counterCells;
        long j = this.baseCount;
        if (counterCellArr != null) {
            for (CounterCell counterCell : counterCellArr) {
                if (counterCell != null) {
                    j += counterCell.value;
                }
            }
        }
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0098, code lost:
    
        if (r1.counterCells != r6) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x009a, code lost:
    
        r1.counterCells = (java.util.concurrent.ConcurrentHashMap.CounterCell[]) java.util.Arrays.copyOf(r6, r7 << 1);
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x001c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void fullAddCount(long r24, boolean r26) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.fullAddCount(long, boolean):void");
    }

    private final void treeifyBin(Node[] nodeArr, int i) {
        if (nodeArr != null) {
            int length = nodeArr.length;
            if (length < 64) {
                tryPresize(length << 1);
                return;
            }
            Node tabAt = tabAt(nodeArr, i);
            if (tabAt == null || tabAt.hash < 0) {
                return;
            }
            synchronized (tabAt) {
                if (tabAt(nodeArr, i) == tabAt) {
                    TreeNode treeNode = null;
                    TreeNode treeNode2 = null;
                    Node node = tabAt;
                    while (node != null) {
                        TreeNode treeNode3 = new TreeNode(node.hash, node.key, node.val, null, null);
                        treeNode3.prev = treeNode2;
                        if (treeNode2 == null) {
                            treeNode = treeNode3;
                        } else {
                            treeNode2.next = treeNode3;
                        }
                        node = node.next;
                        treeNode2 = treeNode3;
                    }
                    setTabAt(nodeArr, i, new TreeBin(treeNode));
                }
            }
        }
    }

    static Node untreeify(Node node) {
        Node node2 = null;
        Node node3 = null;
        while (node != null) {
            Node node4 = new Node(node.hash, node.key, node.val);
            if (node3 == null) {
                node2 = node4;
            } else {
                node3.next = node4;
            }
            node = node.next;
            node3 = node4;
        }
        return node2;
    }

    static final class TreeNode extends Node {
        TreeNode left;
        TreeNode parent;
        TreeNode prev;
        boolean red;
        TreeNode right;

        TreeNode(int i, Object obj, Object obj2, Node node, TreeNode treeNode) {
            super(i, obj, obj2, node);
            this.parent = treeNode;
        }

        Node find(int i, Object obj) {
            return findTreeNode(i, obj, null);
        }

        final TreeNode findTreeNode(int i, Object obj, Class cls) {
            int compareComparables;
            if (obj == null) {
                return null;
            }
            TreeNode treeNode = this;
            do {
                TreeNode treeNode2 = treeNode.left;
                TreeNode treeNode3 = treeNode.right;
                int i2 = treeNode.hash;
                if (i2 <= i) {
                    if (i2 >= i) {
                        Object obj2 = treeNode.key;
                        if (obj2 == obj || (obj2 != null && obj.equals(obj2))) {
                            return treeNode;
                        }
                        if (treeNode2 != null) {
                            if (treeNode3 != null) {
                                if ((cls == null && (cls = ConcurrentHashMap.comparableClassFor(obj)) == null) || (compareComparables = ConcurrentHashMap.compareComparables(cls, obj, obj2)) == 0) {
                                    TreeNode findTreeNode = treeNode3.findTreeNode(i, obj, cls);
                                    if (findTreeNode != null) {
                                        return findTreeNode;
                                    }
                                } else if (compareComparables >= 0) {
                                    treeNode2 = treeNode3;
                                }
                            }
                            treeNode = treeNode2;
                        }
                    }
                    treeNode = treeNode3;
                } else {
                    treeNode = treeNode2;
                }
            } while (treeNode != null);
            return null;
        }
    }

    static final class TreeBin extends Node {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long LOCKSTATE;
        static final int READER = 4;
        private static final Unsafe U;
        static final int WAITER = 2;
        static final int WRITER = 1;
        volatile TreeNode first;
        volatile int lockState;
        TreeNode root;
        volatile Thread waiter;

        static {
            Unsafe unsafe = Unsafe.getUnsafe();
            U = unsafe;
            LOCKSTATE = unsafe.objectFieldOffset(TreeBin.class, "lockState");
        }

        static int tieBreakOrder(Object obj, Object obj2) {
            int compareTo;
            return (obj == null || obj2 == null || (compareTo = obj.getClass().getName().compareTo(obj2.getClass().getName())) == 0) ? System.identityHashCode(obj) <= System.identityHashCode(obj2) ? -1 : 1 : compareTo;
        }

        TreeBin(TreeNode treeNode) {
            int compareComparables;
            int tieBreakOrder;
            super(-2, null, null);
            this.first = treeNode;
            TreeNode treeNode2 = null;
            while (treeNode != null) {
                TreeNode treeNode3 = (TreeNode) treeNode.next;
                treeNode.right = null;
                treeNode.left = null;
                if (treeNode2 == null) {
                    treeNode.parent = null;
                    treeNode.red = false;
                } else {
                    Object obj = treeNode.key;
                    int i = treeNode.hash;
                    TreeNode treeNode4 = treeNode2;
                    Class cls = null;
                    while (true) {
                        Object obj2 = treeNode4.key;
                        int i2 = treeNode4.hash;
                        if (i2 > i) {
                            tieBreakOrder = -1;
                        } else if (i2 < i) {
                            tieBreakOrder = 1;
                        } else {
                            tieBreakOrder = ((cls == null && (cls = ConcurrentHashMap.comparableClassFor(obj)) == null) || (compareComparables = ConcurrentHashMap.compareComparables(cls, obj, obj2)) == 0) ? tieBreakOrder(obj, obj2) : compareComparables;
                        }
                        TreeNode treeNode5 = tieBreakOrder <= 0 ? treeNode4.left : treeNode4.right;
                        if (treeNode5 == null) {
                            break;
                        } else {
                            treeNode4 = treeNode5;
                        }
                    }
                    treeNode.parent = treeNode4;
                    if (tieBreakOrder <= 0) {
                        treeNode4.left = treeNode;
                    } else {
                        treeNode4.right = treeNode;
                    }
                    treeNode = balanceInsertion(treeNode2, treeNode);
                }
                treeNode2 = treeNode;
                treeNode = treeNode3;
            }
            this.root = treeNode2;
        }

        private final void lockRoot() {
            if (U.compareAndSetInt(this, LOCKSTATE, 0, 1)) {
                return;
            }
            contendedLock();
        }

        private final void unlockRoot() {
            this.lockState = 0;
        }

        private final void contendedLock() {
            boolean z = false;
            while (true) {
                int i = this.lockState;
                if ((i & (-3)) == 0) {
                    if (U.compareAndSetInt(this, LOCKSTATE, i, 1)) {
                        break;
                    }
                } else if ((i & 2) == 0) {
                    if (U.compareAndSetInt(this, LOCKSTATE, i, i | 2)) {
                        this.waiter = Thread.currentThread();
                        z = true;
                    }
                } else if (z) {
                    LockSupport.park(this);
                }
            }
            if (z) {
                this.waiter = null;
            }
        }

        final Node find(int i, Object obj) {
            Object obj2;
            Thread thread;
            TreeNode treeNode = null;
            if (obj != null) {
                Node node = this.first;
                while (node != null) {
                    int i2 = this.lockState;
                    if ((i2 & 3) != 0) {
                        if (node.hash == i && ((obj2 = node.key) == obj || (obj2 != null && obj.equals(obj2)))) {
                            return node;
                        }
                        node = node.next;
                    } else {
                        Unsafe unsafe = U;
                        long j = LOCKSTATE;
                        if (unsafe.compareAndSetInt(this, j, i2, i2 + 4)) {
                            try {
                                TreeNode treeNode2 = this.root;
                                if (treeNode2 != null) {
                                    treeNode = treeNode2.findTreeNode(i, obj, null);
                                }
                                if (unsafe.getAndAddInt(this, j, -4) == 6 && (thread = this.waiter) != null) {
                                    LockSupport.unpark(thread);
                                }
                                return treeNode;
                            } finally {
                            }
                        }
                    }
                }
            }
            return null;
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0096, code lost:
        
            return null;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final java.util.concurrent.ConcurrentHashMap.TreeNode putTreeVal(int r11, java.lang.Object r12, java.lang.Object r13) {
            /*
                r10 = this;
                java.util.concurrent.ConcurrentHashMap$TreeNode r0 = r10.root
                r7 = 0
                r1 = 0
                r6 = r0
                r0 = r7
            L6:
                if (r6 != 0) goto L18
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = new java.util.concurrent.ConcurrentHashMap$TreeNode
                r5 = 0
                r6 = 0
                r2 = r11
                r3 = r12
                r4 = r13
                r1.<init>(r2, r3, r4, r5, r6)
                r10.root = r1
                r10.first = r1
                goto L96
            L18:
                int r4 = r6.hash
                r8 = 1
                if (r4 <= r11) goto L20
                r4 = -1
                r9 = -1
                goto L61
            L20:
                if (r4 >= r11) goto L24
                r9 = 1
                goto L61
            L24:
                java.lang.Object r4 = r6.key
                if (r4 == r12) goto L9f
                if (r4 == 0) goto L32
                boolean r5 = r12.equals(r4)
                if (r5 == 0) goto L32
                goto L9f
            L32:
                if (r0 != 0) goto L3a
                java.lang.Class r0 = java.util.concurrent.ConcurrentHashMap.comparableClassFor(r12)
                if (r0 == 0) goto L40
            L3a:
                int r5 = java.util.concurrent.ConcurrentHashMap.compareComparables(r0, r12, r4)
                if (r5 != 0) goto L60
            L40:
                if (r1 != 0) goto L5a
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = r6.left
                if (r1 == 0) goto L4e
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = r1.findTreeNode(r11, r12, r0)
                if (r1 != 0) goto L4d
                goto L4e
            L4d:
                return r1
            L4e:
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = r6.right
                if (r1 == 0) goto L59
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = r1.findTreeNode(r11, r12, r0)
                if (r1 == 0) goto L59
                return r1
            L59:
                r1 = 1
            L5a:
                int r4 = tieBreakOrder(r12, r4)
                r9 = r4
                goto L61
            L60:
                r9 = r5
            L61:
                if (r9 > 0) goto L66
                java.util.concurrent.ConcurrentHashMap$TreeNode r4 = r6.left
                goto L68
            L66:
                java.util.concurrent.ConcurrentHashMap$TreeNode r4 = r6.right
            L68:
                if (r4 != 0) goto L9c
                java.util.concurrent.ConcurrentHashMap$TreeNode r5 = r10.first
                java.util.concurrent.ConcurrentHashMap$TreeNode r1 = new java.util.concurrent.ConcurrentHashMap$TreeNode
                r2 = r11
                r3 = r12
                r4 = r13
                r1.<init>(r2, r3, r4, r5, r6)
                r10.first = r1
                if (r5 == 0) goto L7a
                r5.prev = r1
            L7a:
                if (r9 > 0) goto L7f
                r6.left = r1
                goto L81
            L7f:
                r6.right = r1
            L81:
                boolean r0 = r6.red
                if (r0 != 0) goto L88
                r1.red = r8
                goto L96
            L88:
                r10.lockRoot()
                java.util.concurrent.ConcurrentHashMap$TreeNode r0 = r10.root     // Catch: java.lang.Throwable -> L97
                java.util.concurrent.ConcurrentHashMap$TreeNode r0 = balanceInsertion(r0, r1)     // Catch: java.lang.Throwable -> L97
                r10.root = r0     // Catch: java.lang.Throwable -> L97
                r10.unlockRoot()
            L96:
                return r7
            L97:
                r0 = move-exception
                r10.unlockRoot()
                throw r0
            L9c:
                r6 = r4
                goto L6
            L9f:
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.TreeBin.putTreeVal(int, java.lang.Object, java.lang.Object):java.util.concurrent.ConcurrentHashMap$TreeNode");
        }

        /* JADX WARN: Removed duplicated region for block: B:70:0x008e A[PHI: r0
          0x008e: PHI (r0v4 java.util.concurrent.ConcurrentHashMap$TreeNode) = (r0v3 java.util.concurrent.ConcurrentHashMap$TreeNode), (r0v12 java.util.concurrent.ConcurrentHashMap$TreeNode) binds: [B:86:0x008a, B:41:0x0083] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final boolean removeTreeNode(java.util.concurrent.ConcurrentHashMap.TreeNode r10) {
            /*
                Method dump skipped, instructions count: 211
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentHashMap.TreeBin.removeTreeNode(java.util.concurrent.ConcurrentHashMap$TreeNode):boolean");
        }

        static TreeNode rotateLeft(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.right) != null) {
                TreeNode treeNode4 = treeNode3.left;
                treeNode2.right = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.left == treeNode2) {
                    treeNode5.left = treeNode3;
                } else {
                    treeNode5.right = treeNode3;
                }
                treeNode3.left = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static TreeNode rotateRight(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            if (treeNode2 != null && (treeNode3 = treeNode2.left) != null) {
                TreeNode treeNode4 = treeNode3.right;
                treeNode2.left = treeNode4;
                if (treeNode4 != null) {
                    treeNode4.parent = treeNode2;
                }
                TreeNode treeNode5 = treeNode2.parent;
                treeNode3.parent = treeNode5;
                if (treeNode5 == null) {
                    treeNode3.red = false;
                    treeNode = treeNode3;
                } else if (treeNode5.right == treeNode2) {
                    treeNode5.right = treeNode3;
                } else {
                    treeNode5.left = treeNode3;
                }
                treeNode3.right = treeNode2;
                treeNode2.parent = treeNode3;
            }
            return treeNode;
        }

        static TreeNode balanceInsertion(TreeNode treeNode, TreeNode treeNode2) {
            TreeNode treeNode3;
            treeNode2.red = true;
            while (true) {
                TreeNode treeNode4 = treeNode2.parent;
                if (treeNode4 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (!treeNode4.red || (treeNode3 = treeNode4.parent) == null) {
                    break;
                }
                TreeNode treeNode5 = treeNode3.left;
                if (treeNode4 == treeNode5) {
                    TreeNode treeNode6 = treeNode3.right;
                    if (treeNode6 != null && treeNode6.red) {
                        treeNode6.red = false;
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode2 = treeNode3;
                    } else {
                        if (treeNode2 == treeNode4.right) {
                            treeNode = rotateLeft(treeNode, treeNode4);
                            TreeNode treeNode7 = treeNode4.parent;
                            treeNode3 = treeNode7 == null ? null : treeNode7.parent;
                            treeNode4 = treeNode7;
                            treeNode2 = treeNode4;
                        }
                        if (treeNode4 != null) {
                            treeNode4.red = false;
                            if (treeNode3 != null) {
                                treeNode3.red = true;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                        }
                    }
                } else if (treeNode5 != null && treeNode5.red) {
                    treeNode5.red = false;
                    treeNode4.red = false;
                    treeNode3.red = true;
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode2 == treeNode4.left) {
                        treeNode = rotateRight(treeNode, treeNode4);
                        TreeNode treeNode8 = treeNode4.parent;
                        treeNode3 = treeNode8 == null ? null : treeNode8.parent;
                        treeNode4 = treeNode8;
                        treeNode2 = treeNode4;
                    }
                    if (treeNode4 != null) {
                        treeNode4.red = false;
                        if (treeNode3 != null) {
                            treeNode3.red = true;
                            treeNode = rotateLeft(treeNode, treeNode3);
                        }
                    }
                }
            }
            return treeNode;
        }

        static TreeNode balanceDeletion(TreeNode treeNode, TreeNode treeNode2) {
            while (treeNode2 != null && treeNode2 != treeNode) {
                TreeNode treeNode3 = treeNode2.parent;
                if (treeNode3 == null) {
                    treeNode2.red = false;
                    return treeNode2;
                }
                if (treeNode2.red) {
                    treeNode2.red = false;
                    return treeNode;
                }
                TreeNode treeNode4 = treeNode3.left;
                if (treeNode4 == treeNode2) {
                    TreeNode treeNode5 = treeNode3.right;
                    if (treeNode5 != null && treeNode5.red) {
                        treeNode5.red = false;
                        treeNode3.red = true;
                        treeNode = rotateLeft(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode5 = treeNode3 == null ? null : treeNode3.right;
                    }
                    if (treeNode5 != null) {
                        TreeNode treeNode6 = treeNode5.left;
                        TreeNode treeNode7 = treeNode5.right;
                        if ((treeNode7 == null || !treeNode7.red) && (treeNode6 == null || !treeNode6.red)) {
                            treeNode5.red = true;
                        } else {
                            if (treeNode7 == null || !treeNode7.red) {
                                if (treeNode6 != null) {
                                    treeNode6.red = false;
                                }
                                treeNode5.red = true;
                                treeNode = rotateRight(treeNode, treeNode5);
                                treeNode3 = treeNode2.parent;
                                treeNode5 = treeNode3 != null ? treeNode3.right : null;
                            }
                            if (treeNode5 != null) {
                                treeNode5.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode treeNode8 = treeNode5.right;
                                if (treeNode8 != null) {
                                    treeNode8.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateLeft(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                } else {
                    if (treeNode4 != null && treeNode4.red) {
                        treeNode4.red = false;
                        treeNode3.red = true;
                        treeNode = rotateRight(treeNode, treeNode3);
                        treeNode3 = treeNode2.parent;
                        treeNode4 = treeNode3 == null ? null : treeNode3.left;
                    }
                    if (treeNode4 != null) {
                        TreeNode treeNode9 = treeNode4.left;
                        TreeNode treeNode10 = treeNode4.right;
                        if ((treeNode9 == null || !treeNode9.red) && (treeNode10 == null || !treeNode10.red)) {
                            treeNode4.red = true;
                        } else {
                            if (treeNode9 == null || !treeNode9.red) {
                                if (treeNode10 != null) {
                                    treeNode10.red = false;
                                }
                                treeNode4.red = true;
                                treeNode = rotateLeft(treeNode, treeNode4);
                                treeNode3 = treeNode2.parent;
                                treeNode4 = treeNode3 != null ? treeNode3.left : null;
                            }
                            if (treeNode4 != null) {
                                treeNode4.red = treeNode3 == null ? false : treeNode3.red;
                                TreeNode treeNode11 = treeNode4.left;
                                if (treeNode11 != null) {
                                    treeNode11.red = false;
                                }
                            }
                            if (treeNode3 != null) {
                                treeNode3.red = false;
                                treeNode = rotateRight(treeNode, treeNode3);
                            }
                            treeNode2 = treeNode;
                        }
                    }
                    treeNode2 = treeNode3;
                }
            }
            return treeNode;
        }

        static boolean checkInvariants(TreeNode treeNode) {
            TreeNode treeNode2 = treeNode.parent;
            TreeNode treeNode3 = treeNode.left;
            TreeNode treeNode4 = treeNode.right;
            TreeNode treeNode5 = treeNode.prev;
            TreeNode treeNode6 = (TreeNode) treeNode.next;
            if (treeNode5 != null && treeNode5.next != treeNode) {
                return false;
            }
            if (treeNode6 != null && treeNode6.prev != treeNode) {
                return false;
            }
            if (treeNode2 != null && treeNode != treeNode2.left && treeNode != treeNode2.right) {
                return false;
            }
            if (treeNode3 != null && (treeNode3.parent != treeNode || treeNode3.hash > treeNode.hash)) {
                return false;
            }
            if (treeNode4 != null && (treeNode4.parent != treeNode || treeNode4.hash < treeNode.hash)) {
                return false;
            }
            if (treeNode.red && treeNode3 != null && treeNode3.red && treeNode4 != null && treeNode4.red) {
                return false;
            }
            if (treeNode3 == null || checkInvariants(treeNode3)) {
                return treeNode4 == null || checkInvariants(treeNode4);
            }
            return false;
        }
    }

    static final class TableStack {
        int index;
        int length;
        TableStack next;
        Node[] tab;

        TableStack() {
        }
    }

    static class Traverser {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int index;
        Node next = null;
        TableStack spare;
        TableStack stack;
        Node[] tab;

        Traverser(Node[] nodeArr, int i, int i2, int i3) {
            this.tab = nodeArr;
            this.baseSize = i;
            this.index = i2;
            this.baseIndex = i2;
            this.baseLimit = i3;
        }

        final Node advance() {
            Node[] nodeArr;
            int length;
            int i;
            Node node = this.next;
            if (node != null) {
                node = node.next;
            }
            while (node == null) {
                if (this.baseIndex >= this.baseLimit || (nodeArr = this.tab) == null || (length = nodeArr.length) <= (i = this.index) || i < 0) {
                    this.next = null;
                    return null;
                }
                Node tabAt = ConcurrentHashMap.tabAt(nodeArr, i);
                if (tabAt == null || tabAt.hash >= 0) {
                    node = tabAt;
                } else if (tabAt instanceof ForwardingNode) {
                    this.tab = ((ForwardingNode) tabAt).nextTable;
                    pushState(nodeArr, i, length);
                    node = null;
                } else {
                    node = tabAt instanceof TreeBin ? ((TreeBin) tabAt).first : null;
                }
                if (this.stack != null) {
                    recoverState(length);
                } else {
                    int i2 = i + this.baseSize;
                    this.index = i2;
                    if (i2 >= length) {
                        int i3 = this.baseIndex + 1;
                        this.baseIndex = i3;
                        this.index = i3;
                    }
                }
            }
            this.next = node;
            return node;
        }

        private void pushState(Node[] nodeArr, int i, int i2) {
            TableStack tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack();
            }
            tableStack.tab = nodeArr;
            tableStack.length = i2;
            tableStack.index = i;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int i) {
            TableStack tableStack;
            while (true) {
                tableStack = this.stack;
                if (tableStack == null) {
                    break;
                }
                int i2 = this.index;
                int i3 = tableStack.length;
                int i4 = i2 + i3;
                this.index = i4;
                if (i4 < i) {
                    break;
                }
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
                i = i3;
            }
            if (tableStack == null) {
                int i5 = this.index + this.baseSize;
                this.index = i5;
                if (i5 >= i) {
                    int i6 = this.baseIndex + 1;
                    this.baseIndex = i6;
                    this.index = i6;
                }
            }
        }
    }

    static class BaseIterator extends Traverser {
        Node lastReturned;
        final ConcurrentHashMap map;

        BaseIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3);
            this.map = concurrentHashMap;
            advance();
        }

        public final boolean hasNext() {
            return this.next != null;
        }

        public final boolean hasMoreElements() {
            return this.next != null;
        }

        public final void remove() {
            Node node = this.lastReturned;
            if (node == null) {
                throw new IllegalStateException();
            }
            this.lastReturned = null;
            this.map.replaceNode(node.key, null, null);
        }
    }

    static final class KeyIterator extends BaseIterator implements Iterator, Enumeration {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        KeyIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        public final Object next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.key;
            this.lastReturned = node;
            advance();
            return obj;
        }

        public final Object nextElement() {
            return next();
        }
    }

    static final class ValueIterator extends BaseIterator implements Iterator, Enumeration {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        ValueIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        public final Object next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.val;
            this.lastReturned = node;
            advance();
            return obj;
        }

        public final Object nextElement() {
            return next();
        }
    }

    static final class EntryIterator extends BaseIterator implements Iterator {
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        EntryIterator(Node[] nodeArr, int i, int i2, int i3, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3, concurrentHashMap);
        }

        public final Map.Entry next() {
            Node node = this.next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = node.key;
            Object obj2 = node.val;
            this.lastReturned = node;
            advance();
            return new MapEntry(obj, obj2, this.map);
        }
    }

    static final class MapEntry implements Map.Entry {
        final Object key;
        final ConcurrentHashMap map;
        Object val;

        MapEntry(Object obj, Object obj2, ConcurrentHashMap concurrentHashMap) {
            this.key = obj;
            this.val = obj2;
            this.map = concurrentHashMap;
        }

        public Object getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.val;
        }

        public int hashCode() {
            return this.key.hashCode() ^ this.val.hashCode();
        }

        public String toString() {
            return Helpers.mapEntryToString(this.key, this.val);
        }

        public boolean equals(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
                return false;
            }
            Object obj2 = this.key;
            if (key != obj2 && !key.equals(obj2)) {
                return false;
            }
            Object obj3 = this.val;
            return value == obj3 || value.equals(obj3);
        }

        public Object setValue(Object obj) {
            obj.getClass();
            Object obj2 = this.val;
            this.val = obj;
            this.map.put(this.key, obj);
            return obj2;
        }
    }

    static final class KeySpliterator extends Traverser implements Spliterator {
        long est;

        public int characteristics() {
            return 4353;
        }

        public /* synthetic */ Comparator getComparator() {
            return Spliterator.-CC.$default$getComparator(this);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        KeySpliterator(Node[] nodeArr, int i, int i2, int i3, long j) {
            super(nodeArr, i, i2, i3);
            this.est = j;
        }

        public KeySpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new KeySpliterator(nodeArr, i4, i3, i2, j);
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(advance.key);
                }
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node advance = advance();
            if (advance == null) {
                return false;
            }
            consumer.accept(advance.key);
            return true;
        }

        public long estimateSize() {
            return this.est;
        }
    }

    static final class ValueSpliterator extends Traverser implements Spliterator {
        long est;

        public int characteristics() {
            return 4352;
        }

        public /* synthetic */ Comparator getComparator() {
            return Spliterator.-CC.$default$getComparator(this);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        ValueSpliterator(Node[] nodeArr, int i, int i2, int i3, long j) {
            super(nodeArr, i, i2, i3);
            this.est = j;
        }

        public ValueSpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new ValueSpliterator(nodeArr, i4, i3, i2, j);
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(advance.val);
                }
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node advance = advance();
            if (advance == null) {
                return false;
            }
            consumer.accept(advance.val);
            return true;
        }

        public long estimateSize() {
            return this.est;
        }
    }

    static final class EntrySpliterator extends Traverser implements Spliterator {
        long est;
        final ConcurrentHashMap map;

        public int characteristics() {
            return 4353;
        }

        public /* synthetic */ Comparator getComparator() {
            return Spliterator.-CC.$default$getComparator(this);
        }

        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.-CC.$default$getExactSizeIfKnown(this);
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        EntrySpliterator(Node[] nodeArr, int i, int i2, int i3, long j, ConcurrentHashMap concurrentHashMap) {
            super(nodeArr, i, i2, i3);
            this.map = concurrentHashMap;
            this.est = j;
        }

        public EntrySpliterator trySplit() {
            int i = this.baseIndex;
            int i2 = this.baseLimit;
            int i3 = (i + i2) >>> 1;
            if (i3 <= i) {
                return null;
            }
            Node[] nodeArr = this.tab;
            int i4 = this.baseSize;
            this.baseLimit = i3;
            long j = this.est >>> 1;
            this.est = j;
            return new EntrySpliterator(nodeArr, i4, i3, i2, j, this.map);
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(new MapEntry(advance.key, advance.val, this.map));
                }
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            consumer.getClass();
            Node advance = advance();
            if (advance == null) {
                return false;
            }
            consumer.accept(new MapEntry(advance.key, advance.val, this.map));
            return true;
        }

        public long estimateSize() {
            return this.est;
        }
    }

    final int batchFor(long j) {
        if (j == Long.MAX_VALUE) {
            return 0;
        }
        long sumCount = sumCount();
        if (sumCount <= 1 || sumCount < j) {
            return 0;
        }
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism() << 2;
        if (j > 0) {
            long j2 = sumCount / j;
            if (j2 < commonPoolParallelism) {
                return (int) j2;
            }
        }
        return commonPoolParallelism;
    }

    public void forEach(long j, BiConsumer biConsumer) {
        biConsumer.getClass();
        new ForEachMappingTask(null, batchFor(j), 0, 0, this.table, biConsumer).invoke();
    }

    public void forEach(long j, BiFunction biFunction, Consumer consumer) {
        if (biFunction == null || consumer == null) {
            throw null;
        }
        new ForEachTransformedMappingTask(null, batchFor(j), 0, 0, this.table, biFunction, consumer).invoke();
    }

    public Object search(long j, BiFunction biFunction) {
        biFunction.getClass();
        return new SearchMappingsTask(null, batchFor(j), 0, 0, this.table, biFunction, new AtomicReference()).invoke();
    }

    public Object reduce(long j, BiFunction biFunction, BiFunction biFunction2) {
        if (biFunction == null || biFunction2 == null) {
            throw null;
        }
        return new MapReduceMappingsTask(null, batchFor(j), 0, 0, this.table, null, biFunction, biFunction2).invoke();
    }

    public double reduceToDouble(long j, ToDoubleBiFunction toDoubleBiFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleBiFunction == null || doubleBinaryOperator == null) {
            throw null;
        }
        return ((Double) new MapReduceMappingsToDoubleTask(null, batchFor(j), 0, 0, this.table, null, toDoubleBiFunction, d, doubleBinaryOperator).invoke()).doubleValue();
    }

    public long reduceToLong(long j, ToLongBiFunction toLongBiFunction, long j2, LongBinaryOperator longBinaryOperator) {
        if (toLongBiFunction == null || longBinaryOperator == null) {
            throw null;
        }
        return ((Long) new MapReduceMappingsToLongTask(null, batchFor(j), 0, 0, this.table, null, toLongBiFunction, j2, longBinaryOperator).invoke()).longValue();
    }

    public int reduceToInt(long j, ToIntBiFunction toIntBiFunction, int i, IntBinaryOperator intBinaryOperator) {
        if (toIntBiFunction == null || intBinaryOperator == null) {
            throw null;
        }
        return ((Integer) new MapReduceMappingsToIntTask(null, batchFor(j), 0, 0, this.table, null, toIntBiFunction, i, intBinaryOperator).invoke()).intValue();
    }

    public void forEachKey(long j, Consumer consumer) {
        consumer.getClass();
        new ForEachKeyTask(null, batchFor(j), 0, 0, this.table, consumer).invoke();
    }

    public void forEachKey(long j, Function function, Consumer consumer) {
        if (function == null || consumer == null) {
            throw null;
        }
        new ForEachTransformedKeyTask(null, batchFor(j), 0, 0, this.table, function, consumer).invoke();
    }

    public Object searchKeys(long j, Function function) {
        function.getClass();
        return new SearchKeysTask(null, batchFor(j), 0, 0, this.table, function, new AtomicReference()).invoke();
    }

    public Object reduceKeys(long j, BiFunction biFunction) {
        biFunction.getClass();
        return new ReduceKeysTask(null, batchFor(j), 0, 0, this.table, null, biFunction).invoke();
    }

    public Object reduceKeys(long j, Function function, BiFunction biFunction) {
        if (function == null || biFunction == null) {
            throw null;
        }
        return new MapReduceKeysTask(null, batchFor(j), 0, 0, this.table, null, function, biFunction).invoke();
    }

    public double reduceKeysToDouble(long j, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction == null || doubleBinaryOperator == null) {
            throw null;
        }
        return ((Double) new MapReduceKeysToDoubleTask(null, batchFor(j), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke()).doubleValue();
    }

    public long reduceKeysToLong(long j, ToLongFunction toLongFunction, long j2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction == null || longBinaryOperator == null) {
            throw null;
        }
        return ((Long) new MapReduceKeysToLongTask(null, batchFor(j), 0, 0, this.table, null, toLongFunction, j2, longBinaryOperator).invoke()).longValue();
    }

    public int reduceKeysToInt(long j, ToIntFunction toIntFunction, int i, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction == null || intBinaryOperator == null) {
            throw null;
        }
        return ((Integer) new MapReduceKeysToIntTask(null, batchFor(j), 0, 0, this.table, null, toIntFunction, i, intBinaryOperator).invoke()).intValue();
    }

    public void forEachValue(long j, Consumer consumer) {
        consumer.getClass();
        new ForEachValueTask(null, batchFor(j), 0, 0, this.table, consumer).invoke();
    }

    public void forEachValue(long j, Function function, Consumer consumer) {
        if (function == null || consumer == null) {
            throw null;
        }
        new ForEachTransformedValueTask(null, batchFor(j), 0, 0, this.table, function, consumer).invoke();
    }

    public Object searchValues(long j, Function function) {
        function.getClass();
        return new SearchValuesTask(null, batchFor(j), 0, 0, this.table, function, new AtomicReference()).invoke();
    }

    public Object reduceValues(long j, BiFunction biFunction) {
        biFunction.getClass();
        return new ReduceValuesTask(null, batchFor(j), 0, 0, this.table, null, biFunction).invoke();
    }

    public Object reduceValues(long j, Function function, BiFunction biFunction) {
        if (function == null || biFunction == null) {
            throw null;
        }
        return new MapReduceValuesTask(null, batchFor(j), 0, 0, this.table, null, function, biFunction).invoke();
    }

    public double reduceValuesToDouble(long j, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction == null || doubleBinaryOperator == null) {
            throw null;
        }
        return ((Double) new MapReduceValuesToDoubleTask(null, batchFor(j), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke()).doubleValue();
    }

    public long reduceValuesToLong(long j, ToLongFunction toLongFunction, long j2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction == null || longBinaryOperator == null) {
            throw null;
        }
        return ((Long) new MapReduceValuesToLongTask(null, batchFor(j), 0, 0, this.table, null, toLongFunction, j2, longBinaryOperator).invoke()).longValue();
    }

    public int reduceValuesToInt(long j, ToIntFunction toIntFunction, int i, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction == null || intBinaryOperator == null) {
            throw null;
        }
        return ((Integer) new MapReduceValuesToIntTask(null, batchFor(j), 0, 0, this.table, null, toIntFunction, i, intBinaryOperator).invoke()).intValue();
    }

    public void forEachEntry(long j, Consumer consumer) {
        consumer.getClass();
        new ForEachEntryTask(null, batchFor(j), 0, 0, this.table, consumer).invoke();
    }

    public void forEachEntry(long j, Function function, Consumer consumer) {
        if (function == null || consumer == null) {
            throw null;
        }
        new ForEachTransformedEntryTask(null, batchFor(j), 0, 0, this.table, function, consumer).invoke();
    }

    public Object searchEntries(long j, Function function) {
        function.getClass();
        return new SearchEntriesTask(null, batchFor(j), 0, 0, this.table, function, new AtomicReference()).invoke();
    }

    public Map.Entry reduceEntries(long j, BiFunction biFunction) {
        biFunction.getClass();
        return (Map.Entry) new ReduceEntriesTask(null, batchFor(j), 0, 0, this.table, null, biFunction).invoke();
    }

    public Object reduceEntries(long j, Function function, BiFunction biFunction) {
        if (function == null || biFunction == null) {
            throw null;
        }
        return new MapReduceEntriesTask(null, batchFor(j), 0, 0, this.table, null, function, biFunction).invoke();
    }

    public double reduceEntriesToDouble(long j, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
        if (toDoubleFunction == null || doubleBinaryOperator == null) {
            throw null;
        }
        return ((Double) new MapReduceEntriesToDoubleTask(null, batchFor(j), 0, 0, this.table, null, toDoubleFunction, d, doubleBinaryOperator).invoke()).doubleValue();
    }

    public long reduceEntriesToLong(long j, ToLongFunction toLongFunction, long j2, LongBinaryOperator longBinaryOperator) {
        if (toLongFunction == null || longBinaryOperator == null) {
            throw null;
        }
        return ((Long) new MapReduceEntriesToLongTask(null, batchFor(j), 0, 0, this.table, null, toLongFunction, j2, longBinaryOperator).invoke()).longValue();
    }

    public int reduceEntriesToInt(long j, ToIntFunction toIntFunction, int i, IntBinaryOperator intBinaryOperator) {
        if (toIntFunction == null || intBinaryOperator == null) {
            throw null;
        }
        return ((Integer) new MapReduceEntriesToIntTask(null, batchFor(j), 0, 0, this.table, null, toIntFunction, i, intBinaryOperator).invoke()).intValue();
    }

    static abstract class CollectionView implements Collection, Serializable {
        private static final String OOME_MSG = "Required array size too large";
        private static final long serialVersionUID = 7249069246763182397L;
        final ConcurrentHashMap map;

        public abstract boolean contains(Object obj);

        public /* synthetic */ void forEach(Consumer consumer) {
            Collection.-CC.$default$forEach(this, consumer);
        }

        public abstract Iterator iterator();

        public /* synthetic */ Stream parallelStream() {
            return Collection.-CC.$default$parallelStream(this);
        }

        public abstract boolean remove(Object obj);

        public /* synthetic */ boolean removeIf(Predicate predicate) {
            return Collection.-CC.$default$removeIf(this, predicate);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Collection.-CC.$default$spliterator(this);
        }

        public /* synthetic */ Stream stream() {
            return Collection.-CC.$default$stream(this);
        }

        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return Collection.-CC.$default$toArray(this, intFunction);
        }

        CollectionView(ConcurrentHashMap concurrentHashMap) {
            this.map = concurrentHashMap;
        }

        public ConcurrentHashMap getMap() {
            return this.map;
        }

        public final void clear() {
            this.map.clear();
        }

        public final int size() {
            return this.map.size();
        }

        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        public final Object[] toArray() {
            long mappingCount = this.map.mappingCount();
            if (mappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) mappingCount;
            Object[] objArr = new Object[i];
            Iterator it = iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Object next = it.next();
                if (i2 == i) {
                    if (i >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    int i3 = i < 1073741819 ? (i >>> 1) + 1 + i : 2147483639;
                    objArr = Arrays.copyOf(objArr, i3);
                    i = i3;
                }
                objArr[i2] = next;
                i2++;
            }
            return i2 == i ? objArr : Arrays.copyOf(objArr, i2);
        }

        public final Object[] toArray(Object[] objArr) {
            long mappingCount = this.map.mappingCount();
            if (mappingCount > 2147483639) {
                throw new OutOfMemoryError("Required array size too large");
            }
            int i = (int) mappingCount;
            Object[] objArr2 = objArr.length >= i ? objArr : (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
            int length = objArr2.length;
            Iterator it = iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Object next = it.next();
                if (i2 == length) {
                    if (length >= 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    int i3 = length < 1073741819 ? (length >>> 1) + 1 + length : 2147483639;
                    objArr2 = Arrays.copyOf(objArr2, i3);
                    length = i3;
                }
                objArr2[i2] = next;
                i2++;
            }
            if (objArr != objArr2 || i2 >= length) {
                return i2 == length ? objArr2 : Arrays.copyOf(objArr2, i2);
            }
            objArr2[i2] = null;
            return objArr2;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            Iterator it = iterator();
            if (it.hasNext()) {
                while (true) {
                    Object next = it.next();
                    if (next == this) {
                        next = "(this Collection)";
                    }
                    sb.append(next);
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(", ");
                }
            }
            sb.append(']');
            return sb.toString();
        }

        public final boolean containsAll(Collection collection) {
            if (collection == this) {
                return true;
            }
            for (Object obj : collection) {
                if (obj == null || !contains(obj)) {
                    return false;
                }
            }
            return true;
        }

        public boolean removeAll(Collection collection) {
            collection.getClass();
            Node[] nodeArr = this.map.table;
            boolean z = false;
            if (nodeArr == null) {
                return false;
            }
            if ((collection instanceof Set) && collection.size() > nodeArr.length) {
                Iterator it = iterator();
                while (it.hasNext()) {
                    if (collection.contains(it.next())) {
                        it.remove();
                        z = true;
                    }
                }
                return z;
            }
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                z |= remove(it2.next());
            }
            return z;
        }

        public final boolean retainAll(Collection collection) {
            collection.getClass();
            Iterator it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
    }

    public static class KeySetView extends CollectionView implements Set, Serializable {
        private static final long serialVersionUID = 7249069246763182397L;
        private final Object value;

        public /* bridge */ /* synthetic */ ConcurrentHashMap getMap() {
            return super.getMap();
        }

        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        KeySetView(ConcurrentHashMap concurrentHashMap, Object obj) {
            super(concurrentHashMap);
            this.value = obj;
        }

        public Object getMappedValue() {
            return this.value;
        }

        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return this.map.remove(obj) != null;
        }

        public Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new KeyIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        public boolean add(Object obj) {
            Object obj2 = this.value;
            if (obj2 != null) {
                return this.map.putVal(obj, obj2, true) == null;
            }
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            Object obj = this.value;
            if (obj == null) {
                throw new UnsupportedOperationException();
            }
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (this.map.putVal(it.next(), obj, true) == null) {
                    z = true;
                }
            }
            return z;
        }

        public int hashCode() {
            Iterator it = iterator();
            int i = 0;
            while (it.hasNext()) {
                i += it.next().hashCode();
            }
            return i;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            if (set != this) {
                return containsAll(set) && set.containsAll(this);
            }
            return true;
        }

        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long sumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new KeySpliterator(nodeArr, length, 0, length, sumCount < 0 ? 0L : sumCount);
        }

        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(advance.key);
                }
            }
        }
    }

    static final class ValuesView extends CollectionView implements Collection, Serializable {
        private static final long serialVersionUID = 2249069246763182397L;

        ValuesView(ConcurrentHashMap concurrentHashMap) {
            super(concurrentHashMap);
        }

        public final boolean contains(Object obj) {
            return this.map.containsValue(obj);
        }

        public final boolean remove(Object obj) {
            if (obj == null) {
                return false;
            }
            Iterator it = iterator();
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        public final Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new ValueIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        public final boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection collection) {
            collection.getClass();
            Iterator it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public boolean removeIf(Predicate predicate) {
            return this.map.removeValueIf(predicate);
        }

        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long sumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new ValueSpliterator(nodeArr, length, 0, length, sumCount < 0 ? 0L : sumCount);
        }

        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(advance.val);
                }
            }
        }
    }

    static final class EntrySetView extends CollectionView implements Set, Serializable {
        private static final long serialVersionUID = 2249069246763182397L;

        EntrySetView(ConcurrentHashMap concurrentHashMap) {
            super(concurrentHashMap);
        }

        public boolean contains(Object obj) {
            Map.Entry entry;
            Object key;
            Object obj2;
            Object value;
            if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (obj2 = this.map.get(key)) == null || (value = entry.getValue()) == null) {
                return false;
            }
            return value == obj2 || value.equals(obj2);
        }

        public boolean remove(Object obj) {
            Map.Entry entry;
            Object key;
            Object value;
            return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && this.map.remove(key, value);
        }

        public Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new EntryIterator(nodeArr, length, 0, length, concurrentHashMap);
        }

        public boolean add(Map.Entry entry) {
            return this.map.putVal(entry.getKey(), entry.getValue(), false) == null;
        }

        public boolean addAll(Collection collection) {
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (add((Map.Entry) it.next())) {
                    z = true;
                }
            }
            return z;
        }

        public boolean removeIf(Predicate predicate) {
            return this.map.removeEntryIf(predicate);
        }

        public final int hashCode() {
            Node[] nodeArr = this.map.table;
            int i = 0;
            if (nodeArr != null) {
                Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
                while (true) {
                    Node advance = traverser.advance();
                    if (advance == null) {
                        break;
                    }
                    i += advance.hashCode();
                }
            }
            return i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            if (set != this) {
                return containsAll(set) && set.containsAll(this);
            }
            return true;
        }

        public Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.map;
            long sumCount = concurrentHashMap.sumCount();
            Node[] nodeArr = concurrentHashMap.table;
            int length = nodeArr == null ? 0 : nodeArr.length;
            return new EntrySpliterator(nodeArr, length, 0, length, sumCount >= 0 ? sumCount : 0L, concurrentHashMap);
        }

        public void forEach(Consumer consumer) {
            consumer.getClass();
            Node[] nodeArr = this.map.table;
            if (nodeArr == null) {
                return;
            }
            Traverser traverser = new Traverser(nodeArr, nodeArr.length, 0, nodeArr.length);
            while (true) {
                Node advance = traverser.advance();
                if (advance == null) {
                    return;
                } else {
                    consumer.accept(new MapEntry(advance.key, advance.val, this.map));
                }
            }
        }
    }

    static abstract class BulkTask extends CountedCompleter {
        int baseIndex;
        int baseLimit;
        final int baseSize;
        int batch;
        int index;
        Node next;
        TableStack spare;
        TableStack stack;
        Node[] tab;

        BulkTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr) {
            super(bulkTask);
            this.batch = i;
            this.baseIndex = i2;
            this.index = i2;
            this.tab = nodeArr;
            if (nodeArr == null) {
                this.baseLimit = 0;
                this.baseSize = 0;
            } else if (bulkTask == null) {
                int length = nodeArr.length;
                this.baseLimit = length;
                this.baseSize = length;
            } else {
                this.baseLimit = i3;
                this.baseSize = bulkTask.baseSize;
            }
        }

        final Node advance() {
            Node[] nodeArr;
            int length;
            int i;
            Node node = this.next;
            if (node != null) {
                node = node.next;
            }
            while (node == null) {
                if (this.baseIndex >= this.baseLimit || (nodeArr = this.tab) == null || (length = nodeArr.length) <= (i = this.index) || i < 0) {
                    this.next = null;
                    return null;
                }
                Node tabAt = ConcurrentHashMap.tabAt(nodeArr, i);
                if (tabAt == null || tabAt.hash >= 0) {
                    node = tabAt;
                } else if (tabAt instanceof ForwardingNode) {
                    this.tab = ((ForwardingNode) tabAt).nextTable;
                    pushState(nodeArr, i, length);
                    node = null;
                } else {
                    node = tabAt instanceof TreeBin ? ((TreeBin) tabAt).first : null;
                }
                if (this.stack != null) {
                    recoverState(length);
                } else {
                    int i2 = i + this.baseSize;
                    this.index = i2;
                    if (i2 >= length) {
                        int i3 = this.baseIndex + 1;
                        this.baseIndex = i3;
                        this.index = i3;
                    }
                }
            }
            this.next = node;
            return node;
        }

        private void pushState(Node[] nodeArr, int i, int i2) {
            TableStack tableStack = this.spare;
            if (tableStack != null) {
                this.spare = tableStack.next;
            } else {
                tableStack = new TableStack();
            }
            tableStack.tab = nodeArr;
            tableStack.length = i2;
            tableStack.index = i;
            tableStack.next = this.stack;
            this.stack = tableStack;
        }

        private void recoverState(int i) {
            TableStack tableStack;
            while (true) {
                tableStack = this.stack;
                if (tableStack == null) {
                    break;
                }
                int i2 = this.index;
                int i3 = tableStack.length;
                int i4 = i2 + i3;
                this.index = i4;
                if (i4 < i) {
                    break;
                }
                this.index = tableStack.index;
                this.tab = tableStack.tab;
                tableStack.tab = null;
                TableStack tableStack2 = tableStack.next;
                tableStack.next = this.spare;
                this.stack = tableStack2;
                this.spare = tableStack;
                i = i3;
            }
            if (tableStack == null) {
                int i5 = this.index + this.baseSize;
                this.index = i5;
                if (i5 >= i) {
                    int i6 = this.baseIndex + 1;
                    this.baseIndex = i6;
                    this.index = i6;
                }
            }
        }
    }

    static final class ForEachKeyTask extends BulkTask {
        final Consumer action;

        ForEachKeyTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer = this.action;
            if (consumer == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachKeyTask(this, i4, i3, i2, this.tab, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    consumer.accept(advance.key);
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachValueTask extends BulkTask {
        final Consumer action;

        ForEachValueTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer = this.action;
            if (consumer == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachValueTask(this, i4, i3, i2, this.tab, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    consumer.accept(advance.val);
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachEntryTask extends BulkTask {
        final Consumer action;

        ForEachEntryTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer = this.action;
            if (consumer == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachEntryTask(this, i4, i3, i2, this.tab, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    consumer.accept(advance);
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachMappingTask extends BulkTask {
        final BiConsumer action;

        ForEachMappingTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, BiConsumer biConsumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.action = biConsumer;
        }

        public final void compute() {
            BiConsumer biConsumer = this.action;
            if (biConsumer == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachMappingTask(this, i4, i3, i2, this.tab, biConsumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    biConsumer.accept(advance.key, advance.val);
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachTransformedKeyTask extends BulkTask {
        final Consumer action;
        final Function transformer;

        ForEachTransformedKeyTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.transformer = function;
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer;
            Function function = this.transformer;
            if (function == null || (consumer = this.action) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachTransformedKeyTask(this, i4, i3, i2, this.tab, function, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    Object apply = function.apply(advance.key);
                    if (apply != null) {
                        consumer.accept(apply);
                    }
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachTransformedValueTask extends BulkTask {
        final Consumer action;
        final Function transformer;

        ForEachTransformedValueTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.transformer = function;
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer;
            Function function = this.transformer;
            if (function == null || (consumer = this.action) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachTransformedValueTask(this, i4, i3, i2, this.tab, function, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    Object apply = function.apply(advance.val);
                    if (apply != null) {
                        consumer.accept(apply);
                    }
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachTransformedEntryTask extends BulkTask {
        final Consumer action;
        final Function transformer;

        ForEachTransformedEntryTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.transformer = function;
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer;
            Function function = this.transformer;
            if (function == null || (consumer = this.action) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachTransformedEntryTask(this, i4, i3, i2, this.tab, function, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    Object apply = function.apply(advance);
                    if (apply != null) {
                        consumer.accept(apply);
                    }
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class ForEachTransformedMappingTask extends BulkTask {
        final Consumer action;
        final BiFunction transformer;

        ForEachTransformedMappingTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, BiFunction biFunction, Consumer consumer) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.transformer = biFunction;
            this.action = consumer;
        }

        public final void compute() {
            Consumer consumer;
            BiFunction biFunction = this.transformer;
            if (biFunction == null || (consumer = this.action) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new ForEachTransformedMappingTask(this, i4, i3, i2, this.tab, biFunction, consumer).fork();
            }
            while (true) {
                Node advance = advance();
                if (advance != null) {
                    Object apply = biFunction.apply(advance.key, advance.val);
                    if (apply != null) {
                        consumer.accept(apply);
                    }
                } else {
                    propagateCompletion();
                    return;
                }
            }
        }
    }

    static final class SearchKeysTask extends BulkTask {
        final AtomicReference result;
        final Function searchFunction;

        SearchKeysTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, AtomicReference atomicReference) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        public final Object getRawResult() {
            return this.result.get();
        }

        public final void compute() {
            AtomicReference atomicReference;
            Function function = this.searchFunction;
            if (function == null || (atomicReference = this.result) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                if (atomicReference.get() != null) {
                    return;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new SearchKeysTask(this, i4, i3, i2, this.tab, function, atomicReference).fork();
            }
            while (atomicReference.get() == null) {
                Node advance = advance();
                if (advance == null) {
                    propagateCompletion();
                    return;
                }
                Object apply = function.apply(advance.key);
                if (apply != null) {
                    if (ConcurrentHashMap$SearchKeysTask$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, apply)) {
                        quietlyCompleteRoot();
                        return;
                    }
                    return;
                }
            }
        }
    }

    static final class SearchValuesTask extends BulkTask {
        final AtomicReference result;
        final Function searchFunction;

        SearchValuesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, AtomicReference atomicReference) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        public final Object getRawResult() {
            return this.result.get();
        }

        public final void compute() {
            AtomicReference atomicReference;
            Function function = this.searchFunction;
            if (function == null || (atomicReference = this.result) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                if (atomicReference.get() != null) {
                    return;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new SearchValuesTask(this, i4, i3, i2, this.tab, function, atomicReference).fork();
            }
            while (atomicReference.get() == null) {
                Node advance = advance();
                if (advance == null) {
                    propagateCompletion();
                    return;
                }
                Object apply = function.apply(advance.val);
                if (apply != null) {
                    if (ConcurrentHashMap$SearchValuesTask$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, apply)) {
                        quietlyCompleteRoot();
                        return;
                    }
                    return;
                }
            }
        }
    }

    static final class SearchEntriesTask extends BulkTask {
        final AtomicReference result;
        final Function searchFunction;

        SearchEntriesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, Function function, AtomicReference atomicReference) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.searchFunction = function;
            this.result = atomicReference;
        }

        public final Object getRawResult() {
            return this.result.get();
        }

        public final void compute() {
            AtomicReference atomicReference;
            Function function = this.searchFunction;
            if (function == null || (atomicReference = this.result) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                if (atomicReference.get() != null) {
                    return;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new SearchEntriesTask(this, i4, i3, i2, this.tab, function, atomicReference).fork();
            }
            while (atomicReference.get() == null) {
                Node advance = advance();
                if (advance == null) {
                    propagateCompletion();
                    return;
                }
                Object apply = function.apply(advance);
                if (apply != null) {
                    if (ConcurrentHashMap$SearchEntriesTask$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, apply)) {
                        quietlyCompleteRoot();
                        return;
                    }
                    return;
                }
            }
        }
    }

    static final class SearchMappingsTask extends BulkTask {
        final AtomicReference result;
        final BiFunction searchFunction;

        SearchMappingsTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, BiFunction biFunction, AtomicReference atomicReference) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.searchFunction = biFunction;
            this.result = atomicReference;
        }

        public final Object getRawResult() {
            return this.result.get();
        }

        public final void compute() {
            AtomicReference atomicReference;
            BiFunction biFunction = this.searchFunction;
            if (biFunction == null || (atomicReference = this.result) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                if (atomicReference.get() != null) {
                    return;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                new SearchMappingsTask(this, i4, i3, i2, this.tab, biFunction, atomicReference).fork();
            }
            while (atomicReference.get() == null) {
                Node advance = advance();
                if (advance == null) {
                    propagateCompletion();
                    return;
                }
                Object apply = biFunction.apply(advance.key, advance.val);
                if (apply != null) {
                    if (ConcurrentHashMap$SearchMappingsTask$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, apply)) {
                        quietlyCompleteRoot();
                        return;
                    }
                    return;
                }
            }
        }
    }

    static final class ReduceKeysTask extends BulkTask {
        ReduceKeysTask nextRight;
        final BiFunction reducer;
        Object result;
        ReduceKeysTask rights;

        ReduceKeysTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, ReduceKeysTask reduceKeysTask, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = reduceKeysTask;
            this.reducer = biFunction;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction = this.reducer;
            if (biFunction != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ReduceKeysTask reduceKeysTask = new ReduceKeysTask(this, i4, i3, i2, this.tab, this.rights, biFunction);
                    this.rights = reduceKeysTask;
                    reduceKeysTask.fork();
                }
                Object obj = null;
                while (true) {
                    Node advance = advance();
                    if (advance == null) {
                        break;
                    }
                    Object obj2 = advance.key;
                    if (obj == null) {
                        obj = obj2;
                    } else if (obj2 != null) {
                        obj = biFunction.apply(obj, obj2);
                    }
                }
                this.result = obj;
                for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ReduceKeysTask reduceKeysTask2 = (ReduceKeysTask) firstComplete;
                    ReduceKeysTask reduceKeysTask3 = reduceKeysTask2.rights;
                    while (reduceKeysTask3 != null) {
                        Object obj3 = reduceKeysTask3.result;
                        if (obj3 != null) {
                            Object obj4 = reduceKeysTask2.result;
                            if (obj4 != null) {
                                obj3 = biFunction.apply(obj4, obj3);
                            }
                            reduceKeysTask2.result = obj3;
                        }
                        reduceKeysTask3 = reduceKeysTask3.nextRight;
                        reduceKeysTask2.rights = reduceKeysTask3;
                    }
                }
            }
        }
    }

    static final class ReduceValuesTask extends BulkTask {
        ReduceValuesTask nextRight;
        final BiFunction reducer;
        Object result;
        ReduceValuesTask rights;

        ReduceValuesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, ReduceValuesTask reduceValuesTask, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = reduceValuesTask;
            this.reducer = biFunction;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction = this.reducer;
            if (biFunction != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ReduceValuesTask reduceValuesTask = new ReduceValuesTask(this, i4, i3, i2, this.tab, this.rights, biFunction);
                    this.rights = reduceValuesTask;
                    reduceValuesTask.fork();
                }
                Object obj = null;
                while (true) {
                    Node advance = advance();
                    if (advance == null) {
                        break;
                    }
                    Object obj2 = advance.val;
                    obj = obj == null ? obj2 : biFunction.apply(obj, obj2);
                }
                this.result = obj;
                for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ReduceValuesTask reduceValuesTask2 = (ReduceValuesTask) firstComplete;
                    ReduceValuesTask reduceValuesTask3 = reduceValuesTask2.rights;
                    while (reduceValuesTask3 != null) {
                        Object obj3 = reduceValuesTask3.result;
                        if (obj3 != null) {
                            Object obj4 = reduceValuesTask2.result;
                            if (obj4 != null) {
                                obj3 = biFunction.apply(obj4, obj3);
                            }
                            reduceValuesTask2.result = obj3;
                        }
                        reduceValuesTask3 = reduceValuesTask3.nextRight;
                        reduceValuesTask2.rights = reduceValuesTask3;
                    }
                }
            }
        }
    }

    static final class ReduceEntriesTask extends BulkTask {
        ReduceEntriesTask nextRight;
        final BiFunction reducer;
        Map.Entry result;
        ReduceEntriesTask rights;

        ReduceEntriesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, ReduceEntriesTask reduceEntriesTask, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = reduceEntriesTask;
            this.reducer = biFunction;
        }

        public final Map.Entry getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction = this.reducer;
            if (biFunction != null) {
                int i = this.baseIndex;
                while (this.batch > 0) {
                    int i2 = this.baseLimit;
                    int i3 = (i2 + i) >>> 1;
                    if (i3 <= i) {
                        break;
                    }
                    addToPendingCount(1);
                    int i4 = this.batch >>> 1;
                    this.batch = i4;
                    this.baseLimit = i3;
                    ReduceEntriesTask reduceEntriesTask = new ReduceEntriesTask(this, i4, i3, i2, this.tab, this.rights, biFunction);
                    this.rights = reduceEntriesTask;
                    reduceEntriesTask.fork();
                }
                Map.Entry entry = null;
                while (true) {
                    Node advance = advance();
                    if (advance == null) {
                        break;
                    } else {
                        entry = entry == null ? advance : (Map.Entry) biFunction.apply(entry, advance);
                    }
                }
                this.result = entry;
                for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                    ReduceEntriesTask reduceEntriesTask2 = (ReduceEntriesTask) firstComplete;
                    ReduceEntriesTask reduceEntriesTask3 = reduceEntriesTask2.rights;
                    while (reduceEntriesTask3 != null) {
                        Map.Entry entry2 = reduceEntriesTask3.result;
                        if (entry2 != null) {
                            Map.Entry entry3 = reduceEntriesTask2.result;
                            if (entry3 != null) {
                                entry2 = (Map.Entry) biFunction.apply(entry3, entry2);
                            }
                            reduceEntriesTask2.result = entry2;
                        }
                        reduceEntriesTask3 = reduceEntriesTask3.nextRight;
                        reduceEntriesTask2.rights = reduceEntriesTask3;
                    }
                }
            }
        }
    }

    static final class MapReduceKeysTask extends BulkTask {
        MapReduceKeysTask nextRight;
        final BiFunction reducer;
        Object result;
        MapReduceKeysTask rights;
        final Function transformer;

        MapReduceKeysTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceKeysTask mapReduceKeysTask, Function function, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceKeysTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction;
            Function function = this.transformer;
            if (function == null || (biFunction = this.reducer) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceKeysTask mapReduceKeysTask = new MapReduceKeysTask(this, i4, i3, i2, this.tab, this.rights, function, biFunction);
                this.rights = mapReduceKeysTask;
                mapReduceKeysTask.fork();
            }
            Object obj = null;
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                }
                Object apply = function.apply(advance.key);
                if (apply != null) {
                    if (obj != null) {
                        apply = biFunction.apply(obj, apply);
                    }
                    obj = apply;
                }
            }
            this.result = obj;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceKeysTask mapReduceKeysTask2 = (MapReduceKeysTask) firstComplete;
                MapReduceKeysTask mapReduceKeysTask3 = mapReduceKeysTask2.rights;
                while (mapReduceKeysTask3 != null) {
                    Object obj2 = mapReduceKeysTask3.result;
                    if (obj2 != null) {
                        Object obj3 = mapReduceKeysTask2.result;
                        if (obj3 != null) {
                            obj2 = biFunction.apply(obj3, obj2);
                        }
                        mapReduceKeysTask2.result = obj2;
                    }
                    mapReduceKeysTask3 = mapReduceKeysTask3.nextRight;
                    mapReduceKeysTask2.rights = mapReduceKeysTask3;
                }
            }
        }
    }

    static final class MapReduceValuesTask extends BulkTask {
        MapReduceValuesTask nextRight;
        final BiFunction reducer;
        Object result;
        MapReduceValuesTask rights;
        final Function transformer;

        MapReduceValuesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceValuesTask mapReduceValuesTask, Function function, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceValuesTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction;
            Function function = this.transformer;
            if (function == null || (biFunction = this.reducer) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceValuesTask mapReduceValuesTask = new MapReduceValuesTask(this, i4, i3, i2, this.tab, this.rights, function, biFunction);
                this.rights = mapReduceValuesTask;
                mapReduceValuesTask.fork();
            }
            Object obj = null;
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                }
                Object apply = function.apply(advance.val);
                if (apply != null) {
                    if (obj != null) {
                        apply = biFunction.apply(obj, apply);
                    }
                    obj = apply;
                }
            }
            this.result = obj;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceValuesTask mapReduceValuesTask2 = (MapReduceValuesTask) firstComplete;
                MapReduceValuesTask mapReduceValuesTask3 = mapReduceValuesTask2.rights;
                while (mapReduceValuesTask3 != null) {
                    Object obj2 = mapReduceValuesTask3.result;
                    if (obj2 != null) {
                        Object obj3 = mapReduceValuesTask2.result;
                        if (obj3 != null) {
                            obj2 = biFunction.apply(obj3, obj2);
                        }
                        mapReduceValuesTask2.result = obj2;
                    }
                    mapReduceValuesTask3 = mapReduceValuesTask3.nextRight;
                    mapReduceValuesTask2.rights = mapReduceValuesTask3;
                }
            }
        }
    }

    static final class MapReduceEntriesTask extends BulkTask {
        MapReduceEntriesTask nextRight;
        final BiFunction reducer;
        Object result;
        MapReduceEntriesTask rights;
        final Function transformer;

        MapReduceEntriesTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceEntriesTask mapReduceEntriesTask, Function function, BiFunction biFunction) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceEntriesTask;
            this.transformer = function;
            this.reducer = biFunction;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction;
            Function function = this.transformer;
            if (function == null || (biFunction = this.reducer) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceEntriesTask mapReduceEntriesTask = new MapReduceEntriesTask(this, i4, i3, i2, this.tab, this.rights, function, biFunction);
                this.rights = mapReduceEntriesTask;
                mapReduceEntriesTask.fork();
            }
            Object obj = null;
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                }
                Object apply = function.apply(advance);
                if (apply != null) {
                    if (obj != null) {
                        apply = biFunction.apply(obj, apply);
                    }
                    obj = apply;
                }
            }
            this.result = obj;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceEntriesTask mapReduceEntriesTask2 = (MapReduceEntriesTask) firstComplete;
                MapReduceEntriesTask mapReduceEntriesTask3 = mapReduceEntriesTask2.rights;
                while (mapReduceEntriesTask3 != null) {
                    Object obj2 = mapReduceEntriesTask3.result;
                    if (obj2 != null) {
                        Object obj3 = mapReduceEntriesTask2.result;
                        if (obj3 != null) {
                            obj2 = biFunction.apply(obj3, obj2);
                        }
                        mapReduceEntriesTask2.result = obj2;
                    }
                    mapReduceEntriesTask3 = mapReduceEntriesTask3.nextRight;
                    mapReduceEntriesTask2.rights = mapReduceEntriesTask3;
                }
            }
        }
    }

    static final class MapReduceMappingsTask extends BulkTask {
        MapReduceMappingsTask nextRight;
        final BiFunction reducer;
        Object result;
        MapReduceMappingsTask rights;
        final BiFunction transformer;

        MapReduceMappingsTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceMappingsTask mapReduceMappingsTask, BiFunction biFunction, BiFunction biFunction2) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceMappingsTask;
            this.transformer = biFunction;
            this.reducer = biFunction2;
        }

        public final Object getRawResult() {
            return this.result;
        }

        public final void compute() {
            BiFunction biFunction;
            BiFunction biFunction2 = this.transformer;
            if (biFunction2 == null || (biFunction = this.reducer) == null) {
                return;
            }
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceMappingsTask mapReduceMappingsTask = new MapReduceMappingsTask(this, i4, i3, i2, this.tab, this.rights, biFunction2, biFunction);
                this.rights = mapReduceMappingsTask;
                mapReduceMappingsTask.fork();
            }
            Object obj = null;
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                }
                Object apply = biFunction2.apply(advance.key, advance.val);
                if (apply != null) {
                    if (obj != null) {
                        apply = biFunction.apply(obj, apply);
                    }
                    obj = apply;
                }
            }
            this.result = obj;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceMappingsTask mapReduceMappingsTask2 = (MapReduceMappingsTask) firstComplete;
                MapReduceMappingsTask mapReduceMappingsTask3 = mapReduceMappingsTask2.rights;
                while (mapReduceMappingsTask3 != null) {
                    Object obj2 = mapReduceMappingsTask3.result;
                    if (obj2 != null) {
                        Object obj3 = mapReduceMappingsTask2.result;
                        if (obj3 != null) {
                            obj2 = biFunction.apply(obj3, obj2);
                        }
                        mapReduceMappingsTask2.result = obj2;
                    }
                    mapReduceMappingsTask3 = mapReduceMappingsTask3.nextRight;
                    mapReduceMappingsTask2.rights = mapReduceMappingsTask3;
                }
            }
        }
    }

    static final class MapReduceKeysToDoubleTask extends BulkTask {
        final double basis;
        MapReduceKeysToDoubleTask nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceKeysToDoubleTask rights;
        final ToDoubleFunction transformer;

        MapReduceKeysToDoubleTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceKeysToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            ToDoubleFunction toDoubleFunction = this.transformer;
            if (toDoubleFunction == null || (doubleBinaryOperator = this.reducer) == null) {
                return;
            }
            double d = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask = new MapReduceKeysToDoubleTask(this, i4, i3, i2, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator);
                this.rights = mapReduceKeysToDoubleTask;
                mapReduceKeysToDoubleTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(advance.key));
                }
            }
            this.result = d;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask2 = (MapReduceKeysToDoubleTask) firstComplete;
                MapReduceKeysToDoubleTask mapReduceKeysToDoubleTask3 = mapReduceKeysToDoubleTask2.rights;
                while (mapReduceKeysToDoubleTask3 != null) {
                    mapReduceKeysToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceKeysToDoubleTask2.result, mapReduceKeysToDoubleTask3.result);
                    mapReduceKeysToDoubleTask3 = mapReduceKeysToDoubleTask3.nextRight;
                    mapReduceKeysToDoubleTask2.rights = mapReduceKeysToDoubleTask3;
                }
            }
        }
    }

    static final class MapReduceValuesToDoubleTask extends BulkTask {
        final double basis;
        MapReduceValuesToDoubleTask nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceValuesToDoubleTask rights;
        final ToDoubleFunction transformer;

        MapReduceValuesToDoubleTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceValuesToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            ToDoubleFunction toDoubleFunction = this.transformer;
            if (toDoubleFunction == null || (doubleBinaryOperator = this.reducer) == null) {
                return;
            }
            double d = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask = new MapReduceValuesToDoubleTask(this, i4, i3, i2, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator);
                this.rights = mapReduceValuesToDoubleTask;
                mapReduceValuesToDoubleTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(advance.val));
                }
            }
            this.result = d;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask2 = (MapReduceValuesToDoubleTask) firstComplete;
                MapReduceValuesToDoubleTask mapReduceValuesToDoubleTask3 = mapReduceValuesToDoubleTask2.rights;
                while (mapReduceValuesToDoubleTask3 != null) {
                    mapReduceValuesToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceValuesToDoubleTask2.result, mapReduceValuesToDoubleTask3.result);
                    mapReduceValuesToDoubleTask3 = mapReduceValuesToDoubleTask3.nextRight;
                    mapReduceValuesToDoubleTask2.rights = mapReduceValuesToDoubleTask3;
                }
            }
        }
    }

    static final class MapReduceEntriesToDoubleTask extends BulkTask {
        final double basis;
        MapReduceEntriesToDoubleTask nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceEntriesToDoubleTask rights;
        final ToDoubleFunction transformer;

        MapReduceEntriesToDoubleTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask, ToDoubleFunction toDoubleFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceEntriesToDoubleTask;
            this.transformer = toDoubleFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            ToDoubleFunction toDoubleFunction = this.transformer;
            if (toDoubleFunction == null || (doubleBinaryOperator = this.reducer) == null) {
                return;
            }
            double d = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask = new MapReduceEntriesToDoubleTask(this, i4, i3, i2, this.tab, this.rights, toDoubleFunction, d, doubleBinaryOperator);
                this.rights = mapReduceEntriesToDoubleTask;
                mapReduceEntriesToDoubleTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    d = doubleBinaryOperator.applyAsDouble(d, toDoubleFunction.applyAsDouble(advance));
                }
            }
            this.result = d;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask2 = (MapReduceEntriesToDoubleTask) firstComplete;
                MapReduceEntriesToDoubleTask mapReduceEntriesToDoubleTask3 = mapReduceEntriesToDoubleTask2.rights;
                while (mapReduceEntriesToDoubleTask3 != null) {
                    mapReduceEntriesToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceEntriesToDoubleTask2.result, mapReduceEntriesToDoubleTask3.result);
                    mapReduceEntriesToDoubleTask3 = mapReduceEntriesToDoubleTask3.nextRight;
                    mapReduceEntriesToDoubleTask2.rights = mapReduceEntriesToDoubleTask3;
                }
            }
        }
    }

    static final class MapReduceMappingsToDoubleTask extends BulkTask {
        final double basis;
        MapReduceMappingsToDoubleTask nextRight;
        final DoubleBinaryOperator reducer;
        double result;
        MapReduceMappingsToDoubleTask rights;
        final ToDoubleBiFunction transformer;

        MapReduceMappingsToDoubleTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask, ToDoubleBiFunction toDoubleBiFunction, double d, DoubleBinaryOperator doubleBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceMappingsToDoubleTask;
            this.transformer = toDoubleBiFunction;
            this.basis = d;
            this.reducer = doubleBinaryOperator;
        }

        public final Double getRawResult() {
            return Double.valueOf(this.result);
        }

        public final void compute() {
            DoubleBinaryOperator doubleBinaryOperator;
            ToDoubleBiFunction toDoubleBiFunction = this.transformer;
            if (toDoubleBiFunction == null || (doubleBinaryOperator = this.reducer) == null) {
                return;
            }
            double d = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask = new MapReduceMappingsToDoubleTask(this, i4, i3, i2, this.tab, this.rights, toDoubleBiFunction, d, doubleBinaryOperator);
                this.rights = mapReduceMappingsToDoubleTask;
                mapReduceMappingsToDoubleTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    d = doubleBinaryOperator.applyAsDouble(d, toDoubleBiFunction.applyAsDouble(advance.key, advance.val));
                }
            }
            this.result = d;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask2 = (MapReduceMappingsToDoubleTask) firstComplete;
                MapReduceMappingsToDoubleTask mapReduceMappingsToDoubleTask3 = mapReduceMappingsToDoubleTask2.rights;
                while (mapReduceMappingsToDoubleTask3 != null) {
                    mapReduceMappingsToDoubleTask2.result = doubleBinaryOperator.applyAsDouble(mapReduceMappingsToDoubleTask2.result, mapReduceMappingsToDoubleTask3.result);
                    mapReduceMappingsToDoubleTask3 = mapReduceMappingsToDoubleTask3.nextRight;
                    mapReduceMappingsToDoubleTask2.rights = mapReduceMappingsToDoubleTask3;
                }
            }
        }
    }

    static final class MapReduceKeysToLongTask extends BulkTask {
        final long basis;
        MapReduceKeysToLongTask nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceKeysToLongTask rights;
        final ToLongFunction transformer;

        MapReduceKeysToLongTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceKeysToLongTask mapReduceKeysToLongTask, ToLongFunction toLongFunction, long j, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceKeysToLongTask;
            this.transformer = toLongFunction;
            this.basis = j;
            this.reducer = longBinaryOperator;
        }

        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            ToLongFunction toLongFunction = this.transformer;
            if (toLongFunction == null || (longBinaryOperator = this.reducer) == null) {
                return;
            }
            long j = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceKeysToLongTask mapReduceKeysToLongTask = new MapReduceKeysToLongTask(this, i4, i3, i2, this.tab, this.rights, toLongFunction, j, longBinaryOperator);
                this.rights = mapReduceKeysToLongTask;
                mapReduceKeysToLongTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    j = longBinaryOperator.applyAsLong(j, toLongFunction.applyAsLong(advance.key));
                }
            }
            this.result = j;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceKeysToLongTask mapReduceKeysToLongTask2 = (MapReduceKeysToLongTask) firstComplete;
                MapReduceKeysToLongTask mapReduceKeysToLongTask3 = mapReduceKeysToLongTask2.rights;
                while (mapReduceKeysToLongTask3 != null) {
                    mapReduceKeysToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceKeysToLongTask2.result, mapReduceKeysToLongTask3.result);
                    mapReduceKeysToLongTask3 = mapReduceKeysToLongTask3.nextRight;
                    mapReduceKeysToLongTask2.rights = mapReduceKeysToLongTask3;
                }
            }
        }
    }

    static final class MapReduceValuesToLongTask extends BulkTask {
        final long basis;
        MapReduceValuesToLongTask nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceValuesToLongTask rights;
        final ToLongFunction transformer;

        MapReduceValuesToLongTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceValuesToLongTask mapReduceValuesToLongTask, ToLongFunction toLongFunction, long j, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceValuesToLongTask;
            this.transformer = toLongFunction;
            this.basis = j;
            this.reducer = longBinaryOperator;
        }

        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            ToLongFunction toLongFunction = this.transformer;
            if (toLongFunction == null || (longBinaryOperator = this.reducer) == null) {
                return;
            }
            long j = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceValuesToLongTask mapReduceValuesToLongTask = new MapReduceValuesToLongTask(this, i4, i3, i2, this.tab, this.rights, toLongFunction, j, longBinaryOperator);
                this.rights = mapReduceValuesToLongTask;
                mapReduceValuesToLongTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    j = longBinaryOperator.applyAsLong(j, toLongFunction.applyAsLong(advance.val));
                }
            }
            this.result = j;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceValuesToLongTask mapReduceValuesToLongTask2 = (MapReduceValuesToLongTask) firstComplete;
                MapReduceValuesToLongTask mapReduceValuesToLongTask3 = mapReduceValuesToLongTask2.rights;
                while (mapReduceValuesToLongTask3 != null) {
                    mapReduceValuesToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceValuesToLongTask2.result, mapReduceValuesToLongTask3.result);
                    mapReduceValuesToLongTask3 = mapReduceValuesToLongTask3.nextRight;
                    mapReduceValuesToLongTask2.rights = mapReduceValuesToLongTask3;
                }
            }
        }
    }

    static final class MapReduceEntriesToLongTask extends BulkTask {
        final long basis;
        MapReduceEntriesToLongTask nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceEntriesToLongTask rights;
        final ToLongFunction transformer;

        MapReduceEntriesToLongTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceEntriesToLongTask mapReduceEntriesToLongTask, ToLongFunction toLongFunction, long j, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceEntriesToLongTask;
            this.transformer = toLongFunction;
            this.basis = j;
            this.reducer = longBinaryOperator;
        }

        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            ToLongFunction toLongFunction = this.transformer;
            if (toLongFunction == null || (longBinaryOperator = this.reducer) == null) {
                return;
            }
            long j = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceEntriesToLongTask mapReduceEntriesToLongTask = new MapReduceEntriesToLongTask(this, i4, i3, i2, this.tab, this.rights, toLongFunction, j, longBinaryOperator);
                this.rights = mapReduceEntriesToLongTask;
                mapReduceEntriesToLongTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    j = longBinaryOperator.applyAsLong(j, toLongFunction.applyAsLong(advance));
                }
            }
            this.result = j;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceEntriesToLongTask mapReduceEntriesToLongTask2 = (MapReduceEntriesToLongTask) firstComplete;
                MapReduceEntriesToLongTask mapReduceEntriesToLongTask3 = mapReduceEntriesToLongTask2.rights;
                while (mapReduceEntriesToLongTask3 != null) {
                    mapReduceEntriesToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceEntriesToLongTask2.result, mapReduceEntriesToLongTask3.result);
                    mapReduceEntriesToLongTask3 = mapReduceEntriesToLongTask3.nextRight;
                    mapReduceEntriesToLongTask2.rights = mapReduceEntriesToLongTask3;
                }
            }
        }
    }

    static final class MapReduceMappingsToLongTask extends BulkTask {
        final long basis;
        MapReduceMappingsToLongTask nextRight;
        final LongBinaryOperator reducer;
        long result;
        MapReduceMappingsToLongTask rights;
        final ToLongBiFunction transformer;

        MapReduceMappingsToLongTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceMappingsToLongTask mapReduceMappingsToLongTask, ToLongBiFunction toLongBiFunction, long j, LongBinaryOperator longBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceMappingsToLongTask;
            this.transformer = toLongBiFunction;
            this.basis = j;
            this.reducer = longBinaryOperator;
        }

        public final Long getRawResult() {
            return Long.valueOf(this.result);
        }

        public final void compute() {
            LongBinaryOperator longBinaryOperator;
            ToLongBiFunction toLongBiFunction = this.transformer;
            if (toLongBiFunction == null || (longBinaryOperator = this.reducer) == null) {
                return;
            }
            long j = this.basis;
            int i = this.baseIndex;
            while (this.batch > 0) {
                int i2 = this.baseLimit;
                int i3 = (i2 + i) >>> 1;
                if (i3 <= i) {
                    break;
                }
                addToPendingCount(1);
                int i4 = this.batch >>> 1;
                this.batch = i4;
                this.baseLimit = i3;
                MapReduceMappingsToLongTask mapReduceMappingsToLongTask = new MapReduceMappingsToLongTask(this, i4, i3, i2, this.tab, this.rights, toLongBiFunction, j, longBinaryOperator);
                this.rights = mapReduceMappingsToLongTask;
                mapReduceMappingsToLongTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    j = longBinaryOperator.applyAsLong(j, toLongBiFunction.applyAsLong(advance.key, advance.val));
                }
            }
            this.result = j;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceMappingsToLongTask mapReduceMappingsToLongTask2 = (MapReduceMappingsToLongTask) firstComplete;
                MapReduceMappingsToLongTask mapReduceMappingsToLongTask3 = mapReduceMappingsToLongTask2.rights;
                while (mapReduceMappingsToLongTask3 != null) {
                    mapReduceMappingsToLongTask2.result = longBinaryOperator.applyAsLong(mapReduceMappingsToLongTask2.result, mapReduceMappingsToLongTask3.result);
                    mapReduceMappingsToLongTask3 = mapReduceMappingsToLongTask3.nextRight;
                    mapReduceMappingsToLongTask2.rights = mapReduceMappingsToLongTask3;
                }
            }
        }
    }

    static final class MapReduceKeysToIntTask extends BulkTask {
        final int basis;
        MapReduceKeysToIntTask nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceKeysToIntTask rights;
        final ToIntFunction transformer;

        MapReduceKeysToIntTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceKeysToIntTask mapReduceKeysToIntTask, ToIntFunction toIntFunction, int i4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceKeysToIntTask;
            this.transformer = toIntFunction;
            this.basis = i4;
            this.reducer = intBinaryOperator;
        }

        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            ToIntFunction toIntFunction = this.transformer;
            if (toIntFunction == null || (intBinaryOperator = this.reducer) == null) {
                return;
            }
            int i = this.basis;
            int i2 = this.baseIndex;
            while (this.batch > 0) {
                int i3 = this.baseLimit;
                int i4 = (i3 + i2) >>> 1;
                if (i4 <= i2) {
                    break;
                }
                addToPendingCount(1);
                int i5 = this.batch >>> 1;
                this.batch = i5;
                this.baseLimit = i4;
                MapReduceKeysToIntTask mapReduceKeysToIntTask = new MapReduceKeysToIntTask(this, i5, i4, i3, this.tab, this.rights, toIntFunction, i, intBinaryOperator);
                this.rights = mapReduceKeysToIntTask;
                mapReduceKeysToIntTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(advance.key));
                }
            }
            this.result = i;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceKeysToIntTask mapReduceKeysToIntTask2 = (MapReduceKeysToIntTask) firstComplete;
                MapReduceKeysToIntTask mapReduceKeysToIntTask3 = mapReduceKeysToIntTask2.rights;
                while (mapReduceKeysToIntTask3 != null) {
                    mapReduceKeysToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceKeysToIntTask2.result, mapReduceKeysToIntTask3.result);
                    mapReduceKeysToIntTask3 = mapReduceKeysToIntTask3.nextRight;
                    mapReduceKeysToIntTask2.rights = mapReduceKeysToIntTask3;
                }
            }
        }
    }

    static final class MapReduceValuesToIntTask extends BulkTask {
        final int basis;
        MapReduceValuesToIntTask nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceValuesToIntTask rights;
        final ToIntFunction transformer;

        MapReduceValuesToIntTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceValuesToIntTask mapReduceValuesToIntTask, ToIntFunction toIntFunction, int i4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceValuesToIntTask;
            this.transformer = toIntFunction;
            this.basis = i4;
            this.reducer = intBinaryOperator;
        }

        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            ToIntFunction toIntFunction = this.transformer;
            if (toIntFunction == null || (intBinaryOperator = this.reducer) == null) {
                return;
            }
            int i = this.basis;
            int i2 = this.baseIndex;
            while (this.batch > 0) {
                int i3 = this.baseLimit;
                int i4 = (i3 + i2) >>> 1;
                if (i4 <= i2) {
                    break;
                }
                addToPendingCount(1);
                int i5 = this.batch >>> 1;
                this.batch = i5;
                this.baseLimit = i4;
                MapReduceValuesToIntTask mapReduceValuesToIntTask = new MapReduceValuesToIntTask(this, i5, i4, i3, this.tab, this.rights, toIntFunction, i, intBinaryOperator);
                this.rights = mapReduceValuesToIntTask;
                mapReduceValuesToIntTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(advance.val));
                }
            }
            this.result = i;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceValuesToIntTask mapReduceValuesToIntTask2 = (MapReduceValuesToIntTask) firstComplete;
                MapReduceValuesToIntTask mapReduceValuesToIntTask3 = mapReduceValuesToIntTask2.rights;
                while (mapReduceValuesToIntTask3 != null) {
                    mapReduceValuesToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceValuesToIntTask2.result, mapReduceValuesToIntTask3.result);
                    mapReduceValuesToIntTask3 = mapReduceValuesToIntTask3.nextRight;
                    mapReduceValuesToIntTask2.rights = mapReduceValuesToIntTask3;
                }
            }
        }
    }

    static final class MapReduceEntriesToIntTask extends BulkTask {
        final int basis;
        MapReduceEntriesToIntTask nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceEntriesToIntTask rights;
        final ToIntFunction transformer;

        MapReduceEntriesToIntTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceEntriesToIntTask mapReduceEntriesToIntTask, ToIntFunction toIntFunction, int i4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceEntriesToIntTask;
            this.transformer = toIntFunction;
            this.basis = i4;
            this.reducer = intBinaryOperator;
        }

        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            ToIntFunction toIntFunction = this.transformer;
            if (toIntFunction == null || (intBinaryOperator = this.reducer) == null) {
                return;
            }
            int i = this.basis;
            int i2 = this.baseIndex;
            while (this.batch > 0) {
                int i3 = this.baseLimit;
                int i4 = (i3 + i2) >>> 1;
                if (i4 <= i2) {
                    break;
                }
                addToPendingCount(1);
                int i5 = this.batch >>> 1;
                this.batch = i5;
                this.baseLimit = i4;
                MapReduceEntriesToIntTask mapReduceEntriesToIntTask = new MapReduceEntriesToIntTask(this, i5, i4, i3, this.tab, this.rights, toIntFunction, i, intBinaryOperator);
                this.rights = mapReduceEntriesToIntTask;
                mapReduceEntriesToIntTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    i = intBinaryOperator.applyAsInt(i, toIntFunction.applyAsInt(advance));
                }
            }
            this.result = i;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceEntriesToIntTask mapReduceEntriesToIntTask2 = (MapReduceEntriesToIntTask) firstComplete;
                MapReduceEntriesToIntTask mapReduceEntriesToIntTask3 = mapReduceEntriesToIntTask2.rights;
                while (mapReduceEntriesToIntTask3 != null) {
                    mapReduceEntriesToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceEntriesToIntTask2.result, mapReduceEntriesToIntTask3.result);
                    mapReduceEntriesToIntTask3 = mapReduceEntriesToIntTask3.nextRight;
                    mapReduceEntriesToIntTask2.rights = mapReduceEntriesToIntTask3;
                }
            }
        }
    }

    static final class MapReduceMappingsToIntTask extends BulkTask {
        final int basis;
        MapReduceMappingsToIntTask nextRight;
        final IntBinaryOperator reducer;
        int result;
        MapReduceMappingsToIntTask rights;
        final ToIntBiFunction transformer;

        MapReduceMappingsToIntTask(BulkTask bulkTask, int i, int i2, int i3, Node[] nodeArr, MapReduceMappingsToIntTask mapReduceMappingsToIntTask, ToIntBiFunction toIntBiFunction, int i4, IntBinaryOperator intBinaryOperator) {
            super(bulkTask, i, i2, i3, nodeArr);
            this.nextRight = mapReduceMappingsToIntTask;
            this.transformer = toIntBiFunction;
            this.basis = i4;
            this.reducer = intBinaryOperator;
        }

        public final Integer getRawResult() {
            return Integer.valueOf(this.result);
        }

        public final void compute() {
            IntBinaryOperator intBinaryOperator;
            ToIntBiFunction toIntBiFunction = this.transformer;
            if (toIntBiFunction == null || (intBinaryOperator = this.reducer) == null) {
                return;
            }
            int i = this.basis;
            int i2 = this.baseIndex;
            while (this.batch > 0) {
                int i3 = this.baseLimit;
                int i4 = (i3 + i2) >>> 1;
                if (i4 <= i2) {
                    break;
                }
                addToPendingCount(1);
                int i5 = this.batch >>> 1;
                this.batch = i5;
                this.baseLimit = i4;
                MapReduceMappingsToIntTask mapReduceMappingsToIntTask = new MapReduceMappingsToIntTask(this, i5, i4, i3, this.tab, this.rights, toIntBiFunction, i, intBinaryOperator);
                this.rights = mapReduceMappingsToIntTask;
                mapReduceMappingsToIntTask.fork();
            }
            while (true) {
                Node advance = advance();
                if (advance == null) {
                    break;
                } else {
                    i = intBinaryOperator.applyAsInt(i, toIntBiFunction.applyAsInt(advance.key, advance.val));
                }
            }
            this.result = i;
            for (CountedCompleter firstComplete = firstComplete(); firstComplete != null; firstComplete = firstComplete.nextComplete()) {
                MapReduceMappingsToIntTask mapReduceMappingsToIntTask2 = (MapReduceMappingsToIntTask) firstComplete;
                MapReduceMappingsToIntTask mapReduceMappingsToIntTask3 = mapReduceMappingsToIntTask2.rights;
                while (mapReduceMappingsToIntTask3 != null) {
                    mapReduceMappingsToIntTask2.result = intBinaryOperator.applyAsInt(mapReduceMappingsToIntTask2.result, mapReduceMappingsToIntTask3.result);
                    mapReduceMappingsToIntTask3 = mapReduceMappingsToIntTask3.nextRight;
                    mapReduceMappingsToIntTask2.rights = mapReduceMappingsToIntTask3;
                }
            }
        }
    }
}
