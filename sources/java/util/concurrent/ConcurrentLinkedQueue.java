package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ConcurrentLinkedQueue extends AbstractQueue implements Queue, Serializable {
    private static final VarHandle HEAD;
    static final VarHandle ITEM;
    private static final int MAX_HOPS = 8;
    static final VarHandle NEXT;
    private static final VarHandle TAIL;
    private static final long serialVersionUID = 196745693267521676L;
    volatile transient Node head;
    private volatile transient Node tail;

    static /* synthetic */ boolean lambda$clear$2(Object obj) {
        return true;
    }

    public /* synthetic */ Stream parallelStream() {
        return Collection.-CC.$default$parallelStream(this);
    }

    public /* synthetic */ Stream stream() {
        return Collection.-CC.$default$stream(this);
    }

    public /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return Collection.-CC.$default$toArray(this, intFunction);
    }

    static final class Node {
        volatile Object item;
        volatile Node next;

        Node(Object obj) {
            VarHandle varHandle = ConcurrentLinkedQueue.ITEM;
            ConcurrentLinkedQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        Node() {
        }

        void appendRelaxed(Node node) {
            VarHandle varHandle = ConcurrentLinkedQueue.NEXT;
            ConcurrentLinkedQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        boolean casItem(Object obj, Object obj2) {
            VarHandle varHandle = ConcurrentLinkedQueue.ITEM;
            ConcurrentLinkedQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }
    }

    public ConcurrentLinkedQueue() {
        Node node = new Node();
        this.tail = node;
        this.head = node;
    }

    public ConcurrentLinkedQueue(Collection collection) {
        Node node = null;
        Node node2 = null;
        for (Object obj : collection) {
            obj.getClass();
            Node node3 = new Node(obj);
            if (node == null) {
                node = node3;
            } else {
                node2.appendRelaxed(node3);
            }
            node2 = node3;
        }
        if (node == null) {
            node = new Node();
            node2 = node;
        }
        this.head = node;
        this.tail = node2;
    }

    public boolean add(Object obj) {
        return offer(obj);
    }

    final void updateHead(Node node, Node node2) {
        if (node != node2) {
            ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    final Node succ(Node node) {
        Node node2 = node.next;
        return node == node2 ? this.head : node2;
    }

    private boolean tryCasSuccessor(Node node, Node node2, Node node3) {
        if (node != null) {
            ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }
        ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.concurrent.ConcurrentLinkedQueue.Node skipDeadNodes(java.util.concurrent.ConcurrentLinkedQueue.Node r1, java.util.concurrent.ConcurrentLinkedQueue.Node r2, java.util.concurrent.ConcurrentLinkedQueue.Node r3, java.util.concurrent.ConcurrentLinkedQueue.Node r4) {
        /*
            r0 = this;
            if (r4 != 0) goto L6
            if (r2 != r3) goto L5
            goto L14
        L5:
            r4 = r3
        L6:
            boolean r2 = r0.tryCasSuccessor(r1, r2, r4)
            if (r2 == 0) goto L15
            if (r1 == 0) goto L14
            java.lang.String r1 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m(r1)
            return r3
        L14:
            return r1
        L15:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedQueue.skipDeadNodes(java.util.concurrent.ConcurrentLinkedQueue$Node, java.util.concurrent.ConcurrentLinkedQueue$Node, java.util.concurrent.ConcurrentLinkedQueue$Node, java.util.concurrent.ConcurrentLinkedQueue$Node):java.util.concurrent.ConcurrentLinkedQueue$Node");
    }

    public boolean offer(Object obj) {
        Node node;
        obj.getClass();
        new Node(obj);
        Node node2 = this.tail;
        Node node3 = node2;
        while (true) {
            Node node4 = node2.next;
            if (node4 == null) {
                ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            } else if (node2 == node4) {
                Node node5 = this.tail;
                Node node6 = node3 != node5 ? node5 : this.head;
                node3 = node5;
                node2 = node6;
            } else if (node2 == node3) {
                node = node3;
                node3 = node;
                node2 = node4;
            } else {
                node = this.tail;
                if (node3 != node) {
                    node4 = node;
                    node3 = node;
                    node2 = node4;
                } else {
                    node3 = node;
                    node = node3;
                    node3 = node;
                    node2 = node4;
                }
            }
        }
    }

    public Object poll() {
        while (true) {
            Node node = this.head;
            Node node2 = node;
            while (true) {
                Object obj = node2.item;
                if (obj != null && node2.casItem(obj, null)) {
                    if (node2 != node) {
                        Node node3 = node2.next;
                        if (node3 != null) {
                            node2 = node3;
                        }
                        updateHead(node, node2);
                    }
                    return obj;
                }
                Node node4 = node2.next;
                if (node4 == null) {
                    updateHead(node, node2);
                    return null;
                }
                if (node2 == node4) {
                    break;
                }
                node2 = node4;
            }
        }
    }

    public Object peek() {
        Node node;
        Node node2;
        Object obj;
        Node node3;
        loop0: while (true) {
            node = this.head;
            node2 = node;
            while (true) {
                obj = node2.item;
                if (obj != null || (node3 = node2.next) == null) {
                    break loop0;
                }
                if (node2 == node3) {
                    break;
                }
                node2 = node3;
            }
        }
        updateHead(node, node2);
        return obj;
    }

    Node first() {
        Node node;
        Node node2;
        boolean z;
        Node node3;
        loop0: while (true) {
            node = this.head;
            node2 = node;
            while (true) {
                z = node2.item != null;
                if (z || (node3 = node2.next) == null) {
                    break loop0;
                }
                if (node2 == node3) {
                    break;
                }
                node2 = node3;
            }
        }
        updateHead(node, node2);
        if (z) {
            return node2;
        }
        return null;
    }

    public boolean isEmpty() {
        return first() == null;
    }

    public int size() {
        while (true) {
            Node first = first();
            int i = 0;
            while (first != null) {
                if (first.item != null && (i = i + 1) == Integer.MAX_VALUE) {
                    return i;
                }
                Node node = first.next;
                if (first == node) {
                    break;
                }
                first = node;
            }
            return i;
        }
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        while (true) {
            Node node = this.head;
            Node node2 = null;
            while (node != null) {
                Node node3 = node.next;
                Object obj2 = node.item;
                if (obj2 != null) {
                    if (obj.equals(obj2)) {
                        return true;
                    }
                    node2 = node;
                } else {
                    Node node4 = node;
                    while (node3 != null && node3.item == null) {
                        if (node4 == node3) {
                            break;
                        }
                        node4 = node3;
                        node3 = node3.next;
                    }
                    node2 = skipDeadNodes(node2, node, node4, node3);
                }
                node = node3;
            }
            return false;
        }
    }

    public boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        while (true) {
            Node node = this.head;
            Node node2 = null;
            while (node != null) {
                Node node3 = node.next;
                Object obj2 = node.item;
                if (obj2 != null) {
                    if (obj.equals(obj2) && node.casItem(obj2, null)) {
                        skipDeadNodes(node2, node, node, node3);
                        return true;
                    }
                    node2 = node;
                } else {
                    Node node4 = node;
                    while (node3 != null && node3.item == null) {
                        if (node4 == node3) {
                            break;
                        }
                        node4 = node3;
                        node3 = node3.next;
                    }
                    node2 = skipDeadNodes(node2, node, node4, node3);
                }
                node = node3;
            }
            return false;
        }
    }

    public boolean addAll(Collection collection) {
        Node node;
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        Node node2 = null;
        Node node3 = null;
        for (Object obj : collection) {
            obj.getClass();
            Node node4 = new Node(obj);
            if (node2 == null) {
                node2 = node4;
            } else {
                node3.appendRelaxed(node4);
            }
            node3 = node4;
        }
        if (node2 == null) {
            return false;
        }
        Node node5 = this.tail;
        Node node6 = node5;
        while (true) {
            Node node7 = node5.next;
            if (node7 == null) {
                ConcurrentLinkedQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            } else if (node5 == node7) {
                Node node8 = this.tail;
                Node node9 = node6 != node8 ? node8 : this.head;
                node6 = node8;
                node5 = node9;
            } else if (node5 == node6) {
                node = node6;
                node6 = node;
                node5 = node7;
            } else {
                node = this.tail;
                if (node6 != node) {
                    node7 = node;
                    node6 = node;
                    node5 = node7;
                } else {
                    node6 = node;
                    node = node6;
                    node6 = node;
                    node5 = node7;
                }
            }
        }
    }

    public String toString() {
        int i;
        int i2;
        String[] strArr = null;
        loop0: while (true) {
            Node first = first();
            i = 0;
            i2 = 0;
            while (first != null) {
                Object obj = first.item;
                if (obj != null) {
                    if (strArr == null) {
                        strArr = new String[4];
                    } else if (i == strArr.length) {
                        strArr = (String[]) Arrays.copyOf(strArr, i * 2);
                    }
                    String obj2 = obj.toString();
                    strArr[i] = obj2;
                    i2 += obj2.length();
                    i++;
                }
                Node node = first.next;
                if (first == node) {
                    break;
                }
                first = node;
            }
        }
        if (i == 0) {
            return "[]";
        }
        return Helpers.toString(strArr, i, i2);
    }

    private Object[] toArrayInternal(Object[] objArr) {
        int i;
        Object[] objArr2 = objArr;
        loop0: while (true) {
            Node first = first();
            i = 0;
            while (first != null) {
                Object obj = first.item;
                if (obj != null) {
                    if (objArr2 == null) {
                        objArr2 = new Object[4];
                    } else if (i == objArr2.length) {
                        objArr2 = Arrays.copyOf(objArr2, (i + 4) * 2);
                    }
                    objArr2[i] = obj;
                    i++;
                }
                Node node = first.next;
                if (first == node) {
                    break;
                }
                first = node;
            }
        }
        if (objArr2 == null) {
            return new Object[0];
        }
        if (objArr == null || i > objArr.length) {
            return i == objArr2.length ? objArr2 : Arrays.copyOf(objArr2, i);
        }
        if (objArr != objArr2) {
            System.arraycopy(objArr2, 0, objArr, 0, i);
        }
        if (i < objArr.length) {
            objArr[i] = null;
        }
        return objArr;
    }

    public Object[] toArray() {
        return toArrayInternal(null);
    }

    public Object[] toArray(Object[] objArr) {
        objArr.getClass();
        return toArrayInternal(objArr);
    }

    public Iterator iterator() {
        return new Itr();
    }

    private class Itr implements Iterator {
        private Node lastRet;
        private Object nextItem;
        private Node nextNode;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        Itr() {
            Node node;
            Node node2;
            loop0: while (true) {
                node = ConcurrentLinkedQueue.this.head;
                node2 = node;
                while (true) {
                    Object obj = node2.item;
                    if (obj != null) {
                        this.nextNode = node2;
                        this.nextItem = obj;
                        break loop0;
                    }
                    Node node3 = node2.next;
                    if (node3 == null) {
                        break loop0;
                    } else if (node2 == node3) {
                        break;
                    } else {
                        node2 = node3;
                    }
                }
            }
            ConcurrentLinkedQueue.this.updateHead(node, node2);
        }

        public boolean hasNext() {
            return this.nextItem != null;
        }

        public Object next() {
            Node node = this.nextNode;
            if (node == null) {
                throw new NoSuchElementException();
            }
            this.lastRet = node;
            Node succ = ConcurrentLinkedQueue.this.succ(node);
            Object obj = null;
            while (succ != null) {
                obj = succ.item;
                if (obj != null) {
                    break;
                }
                succ = ConcurrentLinkedQueue.this.succ(succ);
                if (succ != null) {
                    VarHandle varHandle = ConcurrentLinkedQueue.NEXT;
                    ConcurrentLinkedQueue$Itr$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                }
            }
            this.nextNode = succ;
            Object obj2 = this.nextItem;
            this.nextItem = obj;
            return obj2;
        }

        public void remove() {
            Node node = this.lastRet;
            if (node == null) {
                throw new IllegalStateException();
            }
            node.item = null;
            this.lastRet = null;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Node first = first();
        while (first != null) {
            Object obj = first.item;
            if (obj != null) {
                objectOutputStream.writeObject(obj);
            }
            first = succ(first);
        }
        objectOutputStream.writeObject((Object) null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Node node = null;
        Node node2 = null;
        while (true) {
            Object readObject = objectInputStream.readObject();
            if (readObject == null) {
                break;
            }
            Node node3 = new Node(readObject);
            if (node == null) {
                node = node3;
            } else {
                node2.appendRelaxed(node3);
            }
            node2 = node3;
        }
        if (node == null) {
            node = new Node();
            node2 = node;
        }
        this.head = node;
        this.tail = node2;
    }

    final class CLQSpliterator implements Spliterator {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node current;
        boolean exhausted;

        public int characteristics() {
            return 4368;
        }

        public long estimateSize() {
            return Long.MAX_VALUE;
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

        CLQSpliterator() {
        }

        public Spliterator trySplit() {
            Node node;
            Node current = current();
            if (current == null || (node = current.next) == null) {
                return null;
            }
            int min = Math.min(this.batch + 1, 33554432);
            this.batch = min;
            Object[] objArr = null;
            int i = 0;
            do {
                Object obj = current.item;
                if (obj != null) {
                    if (objArr == null) {
                        objArr = new Object[min];
                    }
                    objArr[i] = obj;
                    i++;
                }
                current = current == node ? ConcurrentLinkedQueue.this.first() : node;
                if (current == null || (node = current.next) == null) {
                    break;
                }
            } while (i < min);
            setCurrent(current);
            if (i == 0) {
                return null;
            }
            return Spliterators.spliterator(objArr, 0, i, 4368);
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            Node current = current();
            if (current != null) {
                this.current = null;
                this.exhausted = true;
                ConcurrentLinkedQueue.this.forEachFrom(consumer, current);
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            Object obj;
            consumer.getClass();
            Node current = current();
            if (current == null) {
                return false;
            }
            do {
                obj = current.item;
                Node node = current.next;
                current = current == node ? ConcurrentLinkedQueue.this.first() : node;
                if (obj != null) {
                    break;
                }
            } while (current != null);
            setCurrent(current);
            if (obj == null) {
                return false;
            }
            consumer.accept(obj);
            return true;
        }

        private void setCurrent(Node node) {
            this.current = node;
            if (node == null) {
                this.exhausted = true;
            }
        }

        private Node current() {
            Node node = this.current;
            if (node != null || this.exhausted) {
                return node;
            }
            Node first = ConcurrentLinkedQueue.this.first();
            setCurrent(first);
            return first;
        }
    }

    public Spliterator spliterator() {
        return new CLQSpliterator();
    }

    public boolean removeIf(Predicate predicate) {
        predicate.getClass();
        return bulkRemove(predicate);
    }

    public boolean removeAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new ConcurrentLinkedQueue$$ExternalSyntheticLambda2(collection));
    }

    static /* synthetic */ boolean lambda$removeAll$0(Collection collection, Object obj) {
        return collection.contains(obj);
    }

    public boolean retainAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new ConcurrentLinkedQueue$$ExternalSyntheticLambda1(collection));
    }

    static /* synthetic */ boolean lambda$retainAll$1(Collection collection, Object obj) {
        return !collection.contains(obj);
    }

    public void clear() {
        bulkRemove(new ConcurrentLinkedQueue$$ExternalSyntheticLambda3());
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0042 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean bulkRemove(java.util.function.Predicate r14) {
        /*
            r13 = this;
            r0 = 0
            r1 = 0
        L2:
            java.util.concurrent.ConcurrentLinkedQueue$Node r2 = r13.head
            r3 = 0
            r4 = 8
            r5 = r2
            r7 = r3
            r6 = 8
        Lb:
            if (r2 == 0) goto L44
            java.util.concurrent.ConcurrentLinkedQueue$Node r8 = r2.next
            java.lang.Object r9 = r2.item
            r10 = 1
            if (r9 == 0) goto L16
            r11 = 1
            goto L17
        L16:
            r11 = 0
        L17:
            if (r11 == 0) goto L27
            boolean r12 = r14.test(r9)
            if (r12 == 0) goto L27
            boolean r9 = r2.casItem(r9, r3)
            if (r9 == 0) goto L26
            r1 = 1
        L26:
            r11 = 0
        L27:
            if (r11 != 0) goto L33
            if (r8 == 0) goto L33
            int r6 = r6 + (-1)
            if (r6 != 0) goto L30
            goto L33
        L30:
            if (r2 != r8) goto L42
            goto L2
        L33:
            if (r5 == r2) goto L3c
            boolean r5 = r13.tryCasSuccessor(r7, r5, r2)
            if (r5 == 0) goto L3e
            r5 = r2
        L3c:
            if (r11 == 0) goto L42
        L3e:
            r7 = r2
            r5 = r8
            r6 = 8
        L42:
            r2 = r8
            goto Lb
        L44:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedQueue.bulkRemove(java.util.function.Predicate):boolean");
    }

    void forEachFrom(Consumer consumer, Node node) {
        while (true) {
            Node node2 = null;
            while (node != null) {
                Node node3 = node.next;
                Object obj = node.item;
                if (obj != null) {
                    consumer.accept(obj);
                } else {
                    Node node4 = node;
                    while (node3 != null && node3.item == null) {
                        if (node4 == node3) {
                            break;
                        }
                        node4 = node3;
                        node3 = node3.next;
                    }
                    node = skipDeadNodes(node2, node, node4, node3);
                }
                node2 = node;
                node = node3;
            }
            return;
            node = this.head;
        }
    }

    public void forEach(Consumer consumer) {
        consumer.getClass();
        forEachFrom(consumer, this.head);
    }

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            HEAD = lookup.findVarHandle(ConcurrentLinkedQueue.class, "head", Node.class);
            TAIL = lookup.findVarHandle(ConcurrentLinkedQueue.class, "tail", Node.class);
            ITEM = lookup.findVarHandle(Node.class, "item", Object.class);
            NEXT = lookup.findVarHandle(Node.class, "next", Node.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
