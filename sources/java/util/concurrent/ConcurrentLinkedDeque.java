package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ConcurrentLinkedDeque extends AbstractCollection implements Deque, Serializable {
    private static final VarHandle HEAD;
    private static final int HOPS = 2;
    private static final VarHandle ITEM;
    private static final VarHandle NEXT;
    private static final Node NEXT_TERMINATOR;
    private static final VarHandle PREV;
    private static final Node PREV_TERMINATOR;
    private static final VarHandle TAIL;
    private static final long serialVersionUID = 876323262645176354L;
    private volatile transient Node head;
    private volatile transient Node tail;

    public /* synthetic */ Stream parallelStream() {
        return Collection.-CC.$default$parallelStream(this);
    }

    public /* synthetic */ Stream stream() {
        return Collection.-CC.$default$stream(this);
    }

    public /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return Collection.-CC.$default$toArray(this, intFunction);
    }

    Node prevTerminator() {
        return PREV_TERMINATOR;
    }

    Node nextTerminator() {
        return NEXT_TERMINATOR;
    }

    static final class Node {
        volatile Object item;
        volatile Node next;
        volatile Node prev;

        Node() {
        }
    }

    static Node newNode(Object obj) {
        Node node = new Node();
        ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return node;
    }

    private void linkFirst(Object obj) {
        obj.getClass();
        newNode(obj);
        while (true) {
            Node node = this.head;
            Node node2 = node;
            while (true) {
                Node node3 = node.prev;
                if (node3 != null) {
                    node = node3.prev;
                    if (node != null) {
                        Node node4 = this.head;
                        if (node2 != node4) {
                            node = node4;
                        }
                        node2 = node4;
                    } else {
                        node = node3;
                    }
                }
                if (node.next == node) {
                    break;
                }
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    private void linkLast(Object obj) {
        obj.getClass();
        newNode(obj);
        while (true) {
            Node node = this.tail;
            Node node2 = node;
            while (true) {
                Node node3 = node.next;
                if (node3 != null) {
                    node = node3.next;
                    if (node != null) {
                        Node node4 = this.tail;
                        if (node2 != node4) {
                            node = node4;
                        }
                        node2 = node4;
                    } else {
                        node = node3;
                    }
                }
                if (node.prev == node) {
                    break;
                }
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    void unlink(Node node) {
        boolean z;
        Node node2 = node.prev;
        Node node3 = node.next;
        if (node2 == null) {
            unlinkFirst(node, node3);
            return;
        }
        if (node3 == null) {
            unlinkLast(node, node2);
            return;
        }
        boolean z2 = true;
        int i = 1;
        while (true) {
            if (node2.item != null) {
                z = false;
                break;
            }
            Node node4 = node2.prev;
            if (node4 == null) {
                if (node2.next == node2) {
                    return;
                } else {
                    z = true;
                }
            } else {
                if (node2 == node4) {
                    return;
                }
                i++;
                node2 = node4;
            }
        }
        while (true) {
            if (node3.item != null) {
                z2 = false;
                break;
            }
            Node node5 = node3.next;
            if (node5 == null) {
                if (node3.prev == node3) {
                    return;
                }
            } else {
                if (node3 == node5) {
                    return;
                }
                i++;
                node3 = node5;
            }
        }
        if (i >= 2 || (!z && !z2)) {
            skipDeletedSuccessors(node2);
            skipDeletedPredecessors(node3);
            if ((z || z2) && node2.next == node3 && node3.prev == node2) {
                if (z) {
                    if (node2.prev != null) {
                        return;
                    }
                } else if (node2.item == null) {
                    return;
                }
                if (z2) {
                    if (node3.next != null) {
                        return;
                    }
                } else if (node3.item == null) {
                    return;
                }
                updateHead();
                updateTail();
                if (z) {
                    prevTerminator();
                }
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                if (z2) {
                    nextTerminator();
                }
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    private void unlinkFirst(Node node, Node node2) {
        Node node3;
        Node node4 = null;
        while (node2.item == null && (node3 = node2.next) != null) {
            if (node2 == node3) {
                return;
            }
            node4 = node2;
            node2 = node3;
        }
        if (node4 == null || node2.prev == node2) {
            return;
        }
        ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
    }

    private void unlinkLast(Node node, Node node2) {
        Node node3;
        Node node4 = null;
        while (node2.item == null && (node3 = node2.prev) != null) {
            if (node2 == node3) {
                return;
            }
            node4 = node2;
            node2 = node3;
        }
        if (node4 == null || node2.next == node2) {
            return;
        }
        ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
    }

    private final void updateHead() {
        Node node;
        while (true) {
            Node node2 = this.head;
            if (node2.item != null || (node = node2.prev) == null) {
                return;
            }
            do {
                Node node3 = node.prev;
                if (node3 == null || (node = node3.prev) == null) {
                    ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    break;
                }
            } while (node2 == this.head);
        }
    }

    private final void updateTail() {
        Node node;
        while (true) {
            Node node2 = this.tail;
            if (node2.item != null || (node = node2.next) == null) {
                return;
            }
            do {
                Node node3 = node.next;
                if (node3 == null || (node = node3.next) == null) {
                    ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    break;
                }
            } while (node2 == this.tail);
        }
    }

    private void skipDeletedPredecessors(Node node) {
        while (true) {
            Node node2 = node.prev;
            Node node3 = node2;
            while (true) {
                if (node3.item != null) {
                    break;
                }
                Node node4 = node3.prev;
                if (node4 == null) {
                    if (node3.next != node3) {
                        break;
                    }
                } else if (node3 == node4) {
                    break;
                } else {
                    node3 = node4;
                }
            }
            if (node2 == node3) {
                return;
            }
            ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            if (node.item == null && node.next != null) {
                return;
            }
        }
    }

    private void skipDeletedSuccessors(Node node) {
        while (true) {
            Node node2 = node.next;
            Node node3 = node2;
            while (true) {
                if (node3.item != null) {
                    break;
                }
                Node node4 = node3.next;
                if (node4 == null) {
                    if (node3.prev != node3) {
                        break;
                    }
                } else if (node3 == node4) {
                    break;
                } else {
                    node3 = node4;
                }
            }
            if (node2 == node3) {
                return;
            }
            ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            if (node.item == null && node.prev != null) {
                return;
            }
        }
    }

    final Node succ(Node node) {
        Node node2 = node.next;
        return node == node2 ? first() : node2;
    }

    final Node pred(Node node) {
        Node node2 = node.prev;
        return node == node2 ? last() : node2;
    }

    Node first() {
        while (true) {
            Node node = this.head;
            Node node2 = node;
            while (true) {
                Node node3 = node.prev;
                if (node3 != null) {
                    node = node3.prev;
                    if (node == null) {
                        node = node3;
                        break;
                    }
                    Node node4 = this.head;
                    if (node2 != node4) {
                        node = node4;
                    }
                    node2 = node4;
                } else {
                    break;
                }
            }
            if (node == node2) {
                return node;
            }
            ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    Node last() {
        while (true) {
            Node node = this.tail;
            Node node2 = node;
            while (true) {
                Node node3 = node.next;
                if (node3 != null) {
                    node = node3.next;
                    if (node == null) {
                        node = node3;
                        break;
                    }
                    Node node4 = this.tail;
                    if (node2 != node4) {
                        node = node4;
                    }
                    node2 = node4;
                } else {
                    break;
                }
            }
            if (node == node2) {
                return node;
            }
            ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    private Object screenNullResult(Object obj) {
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException();
    }

    public ConcurrentLinkedDeque() {
        Node node = new Node();
        this.tail = node;
        this.head = node;
    }

    public ConcurrentLinkedDeque(Collection collection) {
        Node node = null;
        Node node2 = null;
        for (Object obj : collection) {
            obj.getClass();
            node2 = newNode(obj);
            if (node == null) {
                node = node2;
            } else {
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
        initHeadTail(node, node2);
    }

    private void initHeadTail(Node node, Node node2) {
        if (node == node2) {
            if (node == null) {
                node = new Node();
                node2 = node;
            } else {
                node2 = new Node();
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
        this.head = node;
        this.tail = node2;
    }

    public void addFirst(Object obj) {
        linkFirst(obj);
    }

    public void addLast(Object obj) {
        linkLast(obj);
    }

    public boolean offerFirst(Object obj) {
        linkFirst(obj);
        return true;
    }

    public boolean offerLast(Object obj) {
        linkLast(obj);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0000, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object peekFirst() {
        /*
            r4 = this;
        L0:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r4.first()
            r1 = r0
        L5:
            java.lang.Object r2 = r1.item
            if (r2 != 0) goto L13
            java.util.concurrent.ConcurrentLinkedDeque$Node r3 = r1.next
            if (r1 != r3) goto Le
            goto L0
        Le:
            if (r3 != 0) goto L11
            goto L13
        L11:
            r1 = r3
            goto L5
        L13:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r0.prev
            if (r0 == 0) goto L18
            goto L0
        L18:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedDeque.peekFirst():java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0000, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object peekLast() {
        /*
            r4 = this;
        L0:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r4.last()
            r1 = r0
        L5:
            java.lang.Object r2 = r1.item
            if (r2 != 0) goto L13
            java.util.concurrent.ConcurrentLinkedDeque$Node r3 = r1.prev
            if (r1 != r3) goto Le
            goto L0
        Le:
            if (r3 != 0) goto L11
            goto L13
        L11:
            r1 = r3
            goto L5
        L13:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r0.next
            if (r0 == 0) goto L18
            goto L0
        L18:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedDeque.peekLast():java.lang.Object");
    }

    public Object getFirst() {
        return screenNullResult(peekFirst());
    }

    public Object getLast() {
        return screenNullResult(peekLast());
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0000, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0000, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object pollFirst() {
        /*
            r3 = this;
        L0:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r3.first()
            r1 = r0
        L5:
            java.lang.Object r2 = r1.item
            if (r2 == 0) goto L13
            java.util.concurrent.ConcurrentLinkedDeque$Node r2 = r0.prev
            if (r2 == 0) goto Le
            goto L0
        Le:
            java.lang.String r2 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m(r2)
        L13:
            java.util.concurrent.ConcurrentLinkedDeque$Node r2 = r1.next
            if (r1 != r2) goto L18
            goto L0
        L18:
            if (r2 != 0) goto L21
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r0.prev
            if (r0 == 0) goto L1f
            goto L0
        L1f:
            r0 = 0
            return r0
        L21:
            r1 = r2
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedDeque.pollFirst():java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0000, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0000, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object pollLast() {
        /*
            r3 = this;
        L0:
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r3.last()
            r1 = r0
        L5:
            java.lang.Object r2 = r1.item
            if (r2 == 0) goto L13
            java.util.concurrent.ConcurrentLinkedDeque$Node r2 = r0.next
            if (r2 == 0) goto Le
            goto L0
        Le:
            java.lang.String r2 = "Instruction is unrepresentable in DEX V35: invoke-polymorphic"
            java.util.concurrent.ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m(r2)
        L13:
            java.util.concurrent.ConcurrentLinkedDeque$Node r2 = r1.prev
            if (r1 != r2) goto L18
            goto L0
        L18:
            if (r2 != 0) goto L21
            java.util.concurrent.ConcurrentLinkedDeque$Node r0 = r0.next
            if (r0 == 0) goto L1f
            goto L0
        L1f:
            r0 = 0
            return r0
        L21:
            r1 = r2
            goto L5
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.ConcurrentLinkedDeque.pollLast():java.lang.Object");
    }

    public Object removeFirst() {
        return screenNullResult(pollFirst());
    }

    public Object removeLast() {
        return screenNullResult(pollLast());
    }

    public boolean offer(Object obj) {
        return offerLast(obj);
    }

    public boolean add(Object obj) {
        return offerLast(obj);
    }

    public Object poll() {
        return pollFirst();
    }

    public Object peek() {
        return peekFirst();
    }

    public Object remove() {
        return removeFirst();
    }

    public Object pop() {
        return removeFirst();
    }

    public Object element() {
        return getFirst();
    }

    public void push(Object obj) {
        addFirst(obj);
    }

    public boolean removeFirstOccurrence(Object obj) {
        obj.getClass();
        Node first = first();
        while (first != null) {
            Object obj2 = first.item;
            if (obj2 != null && obj.equals(obj2)) {
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            first = succ(first);
        }
        return false;
    }

    public boolean removeLastOccurrence(Object obj) {
        obj.getClass();
        Node last = last();
        while (last != null) {
            Object obj2 = last.item;
            if (obj2 != null && obj.equals(obj2)) {
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            last = pred(last);
        }
        return false;
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        Node first = first();
        while (first != null) {
            Object obj2 = first.item;
            if (obj2 != null && obj.equals(obj2)) {
                return true;
            }
            first = succ(first);
        }
        return false;
    }

    public boolean isEmpty() {
        return peekFirst() == null;
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

    public boolean remove(Object obj) {
        return removeFirstOccurrence(obj);
    }

    public boolean addAll(Collection collection) {
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        Node node = null;
        for (Object obj : collection) {
            obj.getClass();
            Node newNode = newNode(obj);
            if (node == null) {
                node = newNode;
            } else {
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
        if (node == null) {
            return false;
        }
        while (true) {
            Node node2 = this.tail;
            Node node3 = node2;
            while (true) {
                Node node4 = node2.next;
                if (node4 != null) {
                    node2 = node4.next;
                    if (node2 != null) {
                        Node node5 = this.tail;
                        if (node3 != node5) {
                            node2 = node5;
                        }
                        node3 = node5;
                    } else {
                        node2 = node4;
                    }
                }
                if (node2.prev == node2) {
                    break;
                }
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }
    }

    public void clear() {
        while (pollFirst() != null) {
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

    public Iterator descendingIterator() {
        return new DescendingItr();
    }

    private abstract class AbstractItr implements Iterator {
        private Node lastRet;
        private Object nextItem;
        private Node nextNode;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        abstract Node nextNode(Node node);

        abstract Node startNode();

        AbstractItr() {
            advance();
        }

        private void advance() {
            Node node = this.nextNode;
            this.lastRet = node;
            Node startNode = node == null ? startNode() : nextNode(node);
            while (startNode != null) {
                Object obj = startNode.item;
                if (obj == null) {
                    startNode = nextNode(startNode);
                } else {
                    this.nextNode = startNode;
                    this.nextItem = obj;
                    return;
                }
            }
            this.nextNode = null;
            this.nextItem = null;
        }

        public boolean hasNext() {
            return this.nextItem != null;
        }

        public Object next() {
            Object obj = this.nextItem;
            if (obj == null) {
                throw new NoSuchElementException();
            }
            advance();
            return obj;
        }

        public void remove() {
            Node node = this.lastRet;
            if (node == null) {
                throw new IllegalStateException();
            }
            node.item = null;
            ConcurrentLinkedDeque.this.unlink(node);
            this.lastRet = null;
        }
    }

    private class Itr extends AbstractItr {
        Itr() {
            super();
        }

        Node startNode() {
            return ConcurrentLinkedDeque.this.first();
        }

        Node nextNode(Node node) {
            return ConcurrentLinkedDeque.this.succ(node);
        }
    }

    private class DescendingItr extends AbstractItr {
        DescendingItr() {
            super();
        }

        Node startNode() {
            return ConcurrentLinkedDeque.this.last();
        }

        Node nextNode(Node node) {
            return ConcurrentLinkedDeque.this.pred(node);
        }
    }

    final class CLDSpliterator implements Spliterator {
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

        CLDSpliterator() {
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
                current = current == node ? ConcurrentLinkedDeque.this.first() : node;
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
                do {
                    Object obj = current.item;
                    if (obj != null) {
                        consumer.accept(obj);
                    }
                    Node node = current.next;
                    current = current == node ? ConcurrentLinkedDeque.this.first() : node;
                } while (current != null);
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
                current = current == node ? ConcurrentLinkedDeque.this.first() : node;
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
            Node first = ConcurrentLinkedDeque.this.first();
            setCurrent(first);
            return first;
        }
    }

    public Spliterator spliterator() {
        return new CLDSpliterator();
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
            if (readObject != null) {
                node2 = newNode(readObject);
                if (node == null) {
                    node = node2;
                } else {
                    ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                }
            } else {
                initHeadTail(node, node2);
                return;
            }
        }
    }

    public boolean removeIf(Predicate predicate) {
        predicate.getClass();
        return bulkRemove(predicate);
    }

    public boolean removeAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new ConcurrentLinkedDeque$$ExternalSyntheticLambda1(collection));
    }

    static /* synthetic */ boolean lambda$removeAll$0(Collection collection, Object obj) {
        return collection.contains(obj);
    }

    public boolean retainAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new ConcurrentLinkedDeque$$ExternalSyntheticLambda2(collection));
    }

    static /* synthetic */ boolean lambda$retainAll$1(Collection collection, Object obj) {
        return !collection.contains(obj);
    }

    private boolean bulkRemove(Predicate predicate) {
        Node first = first();
        while (first != null) {
            Node succ = succ(first);
            Object obj = first.item;
            if (obj != null && predicate.test(obj)) {
                ConcurrentLinkedDeque$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            first = succ;
        }
        return false;
    }

    public void forEach(Consumer consumer) {
        consumer.getClass();
        Node first = first();
        while (first != null) {
            Object obj = first.item;
            if (obj != null) {
                consumer.accept(obj);
            }
            first = succ(first);
        }
    }

    static {
        Node node = new Node();
        PREV_TERMINATOR = node;
        node.next = node;
        Node node2 = new Node();
        NEXT_TERMINATOR = node2;
        node2.prev = node2;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            HEAD = lookup.findVarHandle(ConcurrentLinkedDeque.class, "head", Node.class);
            TAIL = lookup.findVarHandle(ConcurrentLinkedDeque.class, "tail", Node.class);
            PREV = lookup.findVarHandle(Node.class, "prev", Node.class);
            NEXT = lookup.findVarHandle(Node.class, "next", Node.class);
            ITEM = lookup.findVarHandle(Node.class, "item", Object.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
