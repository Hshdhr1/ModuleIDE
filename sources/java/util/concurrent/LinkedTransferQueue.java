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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class LinkedTransferQueue extends AbstractQueue implements TransferQueue, Serializable {
    private static final int ASYNC = 1;
    private static final int CHAINED_SPINS = 64;
    private static final int FRONT_SPINS = 128;
    private static final VarHandle HEAD;
    static final VarHandle ITEM;
    private static final int MAX_HOPS = 8;
    private static final boolean MP;
    static final VarHandle NEXT;
    private static final int NOW = 0;
    private static final VarHandle SWEEPVOTES;
    static final int SWEEP_THRESHOLD = 32;
    private static final int SYNC = 2;
    private static final VarHandle TAIL;
    private static final int TIMED = 3;
    static final VarHandle WAITER;
    private static final long serialVersionUID = -3223113410248163686L;
    volatile transient Node head;
    private volatile transient int sweepVotes;
    private volatile transient Node tail;

    static /* bridge */ /* synthetic */ boolean -$$Nest$mtryCasSuccessor(LinkedTransferQueue linkedTransferQueue, Node node, Node node2, Node node3) {
        return linkedTransferQueue.tryCasSuccessor(node, node2, node3);
    }

    static /* synthetic */ boolean lambda$clear$2(Object obj) {
        return true;
    }

    public /* synthetic */ Stream parallelStream() {
        return Collection.-CC.$default$parallelStream(this);
    }

    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    public /* synthetic */ Stream stream() {
        return Collection.-CC.$default$stream(this);
    }

    public /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return Collection.-CC.$default$toArray(this, intFunction);
    }

    static {
        MP = Runtime.getRuntime().availableProcessors() > 1;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            HEAD = lookup.findVarHandle(LinkedTransferQueue.class, "head", Node.class);
            TAIL = lookup.findVarHandle(LinkedTransferQueue.class, "tail", Node.class);
            SWEEPVOTES = lookup.findVarHandle(LinkedTransferQueue.class, "sweepVotes", Integer.TYPE);
            ITEM = lookup.findVarHandle(Node.class, "item", Object.class);
            NEXT = lookup.findVarHandle(Node.class, "next", Node.class);
            WAITER = lookup.findVarHandle(Node.class, "waiter", Thread.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static final class Node {
        private static final long serialVersionUID = -3375979862319811754L;
        final boolean isData;
        volatile Object item;
        volatile Node next;
        volatile Thread waiter;

        Node(Object obj) {
            VarHandle varHandle = LinkedTransferQueue.ITEM;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            this.isData = obj != null;
        }

        Node() {
            this.isData = true;
        }

        final boolean casNext(Node node, Node node2) {
            VarHandle varHandle = LinkedTransferQueue.NEXT;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final boolean casItem(Object obj, Object obj2) {
            VarHandle varHandle = LinkedTransferQueue.ITEM;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        final void selfLink() {
            VarHandle varHandle = LinkedTransferQueue.NEXT;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        final void appendRelaxed(Node node) {
            VarHandle varHandle = LinkedTransferQueue.NEXT;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        final void forgetContents() {
            if (!this.isData) {
                VarHandle varHandle = LinkedTransferQueue.ITEM;
                LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
            VarHandle varHandle2 = LinkedTransferQueue.WAITER;
            LinkedTransferQueue$Node$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }

        final boolean isMatched() {
            return this.isData == (this.item == null);
        }

        final boolean tryMatch(Object obj, Object obj2) {
            if (!casItem(obj, obj2)) {
                return false;
            }
            LockSupport.unpark(this.waiter);
            return true;
        }

        final boolean cannotPrecede(boolean z) {
            boolean z2 = this.isData;
            if (z2 != z) {
                if (z2 != (this.item == null)) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean casTail(Node node, Node node2) {
        LinkedTransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    private boolean casHead(Node node, Node node2) {
        LinkedTransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return false;
    }

    private int incSweepVotes() {
        LinkedTransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return 1;
    }

    private boolean tryCasSuccessor(Node node, Node node2, Node node3) {
        if (node != null) {
            return node.casNext(node2, node3);
        }
        if (!casHead(node2, node3)) {
            return false;
        }
        node2.selfLink();
        return true;
    }

    private Node skipDeadNodes(Node node, Node node2, Node node3, Node node4) {
        if (node4 != null) {
            if (tryCasSuccessor(node, node2, node4) || (node != null && node.isMatched())) {
                return node3;
            }
        } else if (node2 != node3) {
            node4 = node3;
            if (tryCasSuccessor(node, node2, node4)) {
            }
            return node3;
        }
        return node;
    }

    private void skipDeadNodesNearHead(Node node, Node node2) {
        while (true) {
            Node node3 = node2.next;
            if (node3 == null) {
                break;
            }
            if (!node3.isMatched()) {
                node2 = node3;
                break;
            } else if (node2 == node3) {
                return;
            } else {
                node2 = node3;
            }
        }
        if (casHead(node, node2)) {
            node.selfLink();
        }
    }

    private Object xfer(Object obj, boolean z, int i, long j) {
        Node node;
        Node node2;
        Node node3;
        if (z) {
            obj.getClass();
        }
        Node node4 = null;
        Node node5 = null;
        Node node6 = null;
        loop0: while (true) {
            Node node7 = this.tail;
            if (node4 == node7 || node7.isData != z) {
                node = this.head;
                node2 = node;
            } else {
                node2 = node5;
                node = node7;
            }
            do {
                node3 = node;
                while (true) {
                    if (node3.isData != z) {
                        Object obj2 = node3.item;
                        if (z == (obj2 == null)) {
                            if (node2 == null) {
                                node2 = this.head;
                            }
                            if (node3.tryMatch(obj2, obj)) {
                                if (node2 != node3) {
                                    skipDeadNodesNearHead(node2, node3);
                                }
                                return obj2;
                            }
                        }
                    }
                    node = node3.next;
                    if (node != null) {
                        break;
                    }
                    if (i == 0) {
                        break loop0;
                    }
                    if (node6 == null) {
                        node6 = new Node(obj);
                    }
                    if (node3.casNext(null, node6)) {
                        if (node3 != node7) {
                            casTail(node7, node6);
                        }
                        if (i != 1) {
                            return awaitMatch(node6, node3, obj, i == 3, j);
                        }
                    }
                }
            } while (node3 != node);
            node5 = node2;
            node4 = node7;
        }
        return obj;
    }

    private Object awaitMatch(Node node, Node node2, Object obj, boolean z, long j) {
        long nanoTime = z ? System.nanoTime() + j : 0L;
        Thread currentThread = Thread.currentThread();
        int i = -1;
        ThreadLocalRandom threadLocalRandom = null;
        while (true) {
            Object obj2 = node.item;
            if (obj2 != obj) {
                node.forgetContents();
                return obj2;
            }
            if (currentThread.isInterrupted() || (z && j <= 0)) {
                if (node.casItem(obj, node.isData ? null : node)) {
                    unsplice(node2, node);
                    return obj;
                }
            } else if (i < 0) {
                i = spinsFor(node2, node.isData);
                if (i > 0) {
                    threadLocalRandom = ThreadLocalRandom.current();
                }
            } else if (i > 0) {
                i--;
                if (threadLocalRandom.nextInt(64) == 0) {
                    Thread.yield();
                }
            } else if (node.waiter == null) {
                node.waiter = currentThread;
            } else if (z) {
                j = nanoTime - System.nanoTime();
                if (j > 0) {
                    LockSupport.parkNanos(this, j);
                }
            } else {
                LockSupport.park(this);
            }
        }
    }

    private static int spinsFor(Node node, boolean z) {
        if (!MP || node == null) {
            return 0;
        }
        if (node.isData != z) {
            return 192;
        }
        if (node.isMatched()) {
            return 128;
        }
        return node.waiter == null ? 64 : 0;
    }

    final Node firstDataNode() {
        Node node;
        Node node2;
        Node node3;
        loop0: while (true) {
            node = this.head;
            node2 = node;
            while (node2 != null) {
                if (node2.item != null) {
                    if (node2.isData) {
                        node3 = node2;
                        break loop0;
                    }
                } else {
                    if (!node2.isData) {
                        break loop0;
                    }
                    break loop0;
                    break loop0;
                }
                Node node4 = node2.next;
                if (node4 == null) {
                    break loop0;
                }
                if (node2 == node4) {
                    break;
                }
                node2 = node4;
            }
        }
        node3 = null;
        if (node2 != node && casHead(node, node2)) {
            node.selfLink();
        }
        return node3;
    }

    private int countOfMode(boolean z) {
        while (true) {
            Node node = this.head;
            int i = 0;
            while (node != null) {
                if (!node.isMatched()) {
                    if (node.isData != z) {
                        return 0;
                    }
                    i++;
                    if (i == Integer.MAX_VALUE) {
                        return i;
                    }
                }
                Node node2 = node.next;
                if (node == node2) {
                    break;
                }
                node = node2;
            }
            return i;
        }
    }

    public String toString() {
        int i;
        int i2;
        String[] strArr = null;
        loop0: while (true) {
            Node node = this.head;
            i = 0;
            i2 = 0;
            while (node != null) {
                Object obj = node.item;
                if (!node.isData) {
                    if (obj == null) {
                        break loop0;
                    }
                } else if (obj != null) {
                    if (strArr == null) {
                        strArr = new String[4];
                    } else if (i2 == strArr.length) {
                        strArr = (String[]) Arrays.copyOf(strArr, i2 * 2);
                    }
                    String obj2 = obj.toString();
                    strArr[i2] = obj2;
                    i += obj2.length();
                    i2++;
                }
                Node node2 = node.next;
                if (node == node2) {
                    break;
                }
                node = node2;
            }
        }
        if (i2 == 0) {
            return "[]";
        }
        return Helpers.toString(strArr, i2, i);
    }

    private Object[] toArrayInternal(Object[] objArr) {
        int i;
        Object[] objArr2 = objArr;
        loop0: while (true) {
            Node node = this.head;
            i = 0;
            while (node != null) {
                Object obj = node.item;
                if (!node.isData) {
                    if (obj == null) {
                        break loop0;
                    }
                } else if (obj != null) {
                    if (objArr2 == null) {
                        objArr2 = new Object[4];
                    } else if (i == objArr2.length) {
                        objArr2 = Arrays.copyOf(objArr2, (i + 4) * 2);
                    }
                    objArr2[i] = obj;
                    i++;
                }
                Node node2 = node.next;
                if (node == node2) {
                    break;
                }
                node = node2;
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

    final class Itr implements Iterator {
        private Node ancestor;
        private Node lastRet;
        private Object nextItem;
        private Node nextNode;

        private void advance(Node node) {
            Node node2 = node == null ? LinkedTransferQueue.this.head : node.next;
            Node node3 = node2;
            while (node2 != null) {
                Object obj = node2.item;
                if (obj != null && node2.isData) {
                    this.nextNode = node2;
                    this.nextItem = obj;
                    if (node3 != node2) {
                        LinkedTransferQueue.-$$Nest$mtryCasSuccessor(LinkedTransferQueue.this, node, node3, node2);
                        return;
                    }
                    return;
                }
                if (!node2.isData && obj == null) {
                    break;
                }
                if (node3 != node2) {
                    if (LinkedTransferQueue.-$$Nest$mtryCasSuccessor(LinkedTransferQueue.this, node, node3, node2)) {
                        node3 = node2;
                    } else {
                        node3 = node2.next;
                        node = node2;
                        node2 = node3;
                    }
                }
                Node node4 = node2.next;
                if (node2 == node4) {
                    node2 = LinkedTransferQueue.this.head;
                    node3 = node2;
                    node = null;
                } else {
                    node2 = node4;
                }
            }
            this.nextItem = null;
            this.nextNode = null;
        }

        Itr() {
            advance(null);
        }

        public final boolean hasNext() {
            return this.nextNode != null;
        }

        public final Object next() {
            Node node = this.nextNode;
            if (node == null) {
                throw new NoSuchElementException();
            }
            Object obj = this.nextItem;
            this.lastRet = node;
            advance(node);
            return obj;
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            Node node = null;
            while (true) {
                Node node2 = this.nextNode;
                if (node2 == null) {
                    break;
                }
                consumer.accept(this.nextItem);
                advance(node2);
                node = node2;
            }
            if (node != null) {
                this.lastRet = node;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x005c A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0056 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void remove() {
            /*
                r8 = this;
                java.util.concurrent.LinkedTransferQueue$Node r0 = r8.lastRet
                if (r0 == 0) goto L6b
                r1 = 0
                r8.lastRet = r1
                java.lang.Object r2 = r0.item
                if (r2 != 0) goto Ld
                goto L6a
            Ld:
                java.util.concurrent.LinkedTransferQueue$Node r2 = r8.ancestor
                if (r2 != 0) goto L16
                java.util.concurrent.LinkedTransferQueue r3 = java.util.concurrent.LinkedTransferQueue.this
                java.util.concurrent.LinkedTransferQueue$Node r3 = r3.head
                goto L18
            L16:
                java.util.concurrent.LinkedTransferQueue$Node r3 = r2.next
            L18:
                r4 = r3
            L19:
                if (r3 == 0) goto L6a
                if (r3 != r0) goto L34
                java.lang.Object r0 = r3.item
                if (r0 == 0) goto L24
                r3.tryMatch(r0, r1)
            L24:
                java.util.concurrent.LinkedTransferQueue$Node r0 = r3.next
                if (r0 != 0) goto L29
                goto L2a
            L29:
                r3 = r0
            L2a:
                if (r4 == r3) goto L31
                java.util.concurrent.LinkedTransferQueue r0 = java.util.concurrent.LinkedTransferQueue.this
                java.util.concurrent.LinkedTransferQueue.-$$Nest$mtryCasSuccessor(r0, r2, r4, r3)
            L31:
                r8.ancestor = r2
                return
            L34:
                java.lang.Object r5 = r3.item
                if (r5 == 0) goto L3e
                boolean r6 = r3.isData
                if (r6 == 0) goto L3e
                r6 = 1
                goto L3f
            L3e:
                r6 = 0
            L3f:
                if (r6 == 0) goto L42
                goto L49
            L42:
                boolean r7 = r3.isData
                if (r7 != 0) goto L49
                if (r5 != 0) goto L49
                goto L6a
            L49:
                if (r4 == r3) goto L54
                java.util.concurrent.LinkedTransferQueue r5 = java.util.concurrent.LinkedTransferQueue.this
                boolean r4 = java.util.concurrent.LinkedTransferQueue.-$$Nest$mtryCasSuccessor(r5, r2, r4, r3)
                if (r4 == 0) goto L56
                r4 = r3
            L54:
                if (r6 == 0) goto L5c
            L56:
                java.util.concurrent.LinkedTransferQueue$Node r2 = r3.next
                r4 = r2
                r2 = r3
                r3 = r4
                goto L19
            L5c:
                java.util.concurrent.LinkedTransferQueue$Node r5 = r3.next
                if (r3 != r5) goto L68
                java.util.concurrent.LinkedTransferQueue r2 = java.util.concurrent.LinkedTransferQueue.this
                java.util.concurrent.LinkedTransferQueue$Node r2 = r2.head
                r3 = r2
                r4 = r3
                r2 = r1
                goto L19
            L68:
                r3 = r5
                goto L19
            L6a:
                return
            L6b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                r0.<init>()
                goto L72
            L71:
                throw r0
            L72:
                goto L71
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.LinkedTransferQueue.Itr.remove():void");
        }
    }

    final class LTQSpliterator implements Spliterator {
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

        LTQSpliterator() {
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
            while (true) {
                Object obj = current.item;
                if (current.isData) {
                    if (obj != null) {
                        if (objArr == null) {
                            objArr = new Object[min];
                        }
                        objArr[i] = obj;
                        i++;
                    }
                } else if (obj == null) {
                    current = null;
                    break;
                }
                current = current == node ? LinkedTransferQueue.this.firstDataNode() : node;
                if (current == null || (node = current.next) == null || i >= min) {
                    break;
                }
            }
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
                LinkedTransferQueue.this.forEachFrom(consumer, current);
            }
        }

        public boolean tryAdvance(Consumer consumer) {
            Object obj;
            Node node;
            consumer.getClass();
            Node current = current();
            if (current == null) {
                return false;
            }
            while (true) {
                obj = current.item;
                boolean z = current.isData;
                node = current.next;
                if (current == node) {
                    node = LinkedTransferQueue.this.head;
                }
                if (z) {
                    if (obj == null) {
                        break;
                        break;
                    }
                    break;
                }
                if (obj == null) {
                    node = null;
                }
                if (node == null) {
                    obj = null;
                    break;
                }
                current = node;
            }
            setCurrent(node);
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
            Node firstDataNode = LinkedTransferQueue.this.firstDataNode();
            setCurrent(firstDataNode);
            return firstDataNode;
        }
    }

    public Spliterator spliterator() {
        return new LTQSpliterator();
    }

    final void unsplice(Node node, Node node2) {
        node2.waiter = null;
        if (node == null || node.next != node2) {
            return;
        }
        Node node3 = node2.next;
        if (node3 != null && (node3 == node2 || !node.casNext(node2, node3) || !node.isMatched())) {
            return;
        }
        while (true) {
            Node node4 = this.head;
            if (node4 == node || node4 == node2) {
                return;
            }
            if (node4.isMatched()) {
                Node node5 = node4.next;
                if (node5 == null) {
                    return;
                }
                if (node5 != node4 && casHead(node4, node5)) {
                    node4.selfLink();
                }
            } else {
                if (node.next == node || node2.next == node2 || (incSweepVotes() & 31) != 0) {
                    return;
                }
                sweep();
                return;
            }
        }
    }

    private void sweep() {
        Node node = this.head;
        while (node != null) {
            Node node2 = node.next;
            if (node2 == null) {
                return;
            }
            if (node2.isMatched()) {
                Node node3 = node2.next;
                if (node3 == null) {
                    return;
                }
                if (node2 == node3) {
                    node = this.head;
                } else {
                    node.casNext(node2, node3);
                }
            } else {
                node = node2;
            }
        }
    }

    public LinkedTransferQueue() {
        Node node = new Node();
        this.tail = node;
        this.head = node;
    }

    public LinkedTransferQueue(Collection collection) {
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

    public void put(Object obj) {
        xfer(obj, true, 1, 0L);
    }

    public boolean offer(Object obj, long j, TimeUnit timeUnit) {
        xfer(obj, true, 1, 0L);
        return true;
    }

    public boolean offer(Object obj) {
        xfer(obj, true, 1, 0L);
        return true;
    }

    public boolean add(Object obj) {
        xfer(obj, true, 1, 0L);
        return true;
    }

    public boolean tryTransfer(Object obj) {
        return xfer(obj, true, 0, 0L) == null;
    }

    public void transfer(Object obj) throws InterruptedException {
        if (xfer(obj, true, 2, 0L) == null) {
            return;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    public boolean tryTransfer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException {
        if (xfer(obj, true, 3, timeUnit.toNanos(j)) == null) {
            return true;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return false;
    }

    public Object take() throws InterruptedException {
        Object xfer = xfer(null, false, 2, 0L);
        if (xfer != null) {
            return xfer;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    public Object poll(long j, TimeUnit timeUnit) throws InterruptedException {
        Object xfer = xfer(null, false, 3, timeUnit.toNanos(j));
        if (xfer == null && Thread.interrupted()) {
            throw new InterruptedException();
        }
        return xfer;
    }

    public Object poll() {
        return xfer(null, false, 0, 0L);
    }

    public int drainTo(Collection collection) {
        collection.getClass();
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        while (true) {
            Object poll = poll();
            if (poll == null) {
                return i;
            }
            collection.add(poll);
            i++;
        }
    }

    public int drainTo(Collection collection, int i) {
        collection.getClass();
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        int i2 = 0;
        while (i2 < i) {
            Object poll = poll();
            if (poll == null) {
                break;
            }
            collection.add(poll);
            i2++;
        }
        return i2;
    }

    public Iterator iterator() {
        return new Itr();
    }

    public Object peek() {
        while (true) {
            Node node = this.head;
            while (node != null) {
                Object obj = node.item;
                if (node.isData) {
                    if (obj != null) {
                        return obj;
                    }
                } else if (obj == null) {
                    return null;
                }
                Node node2 = node.next;
                if (node == node2) {
                    break;
                }
                node = node2;
            }
            return null;
        }
    }

    public boolean isEmpty() {
        return firstDataNode() == null;
    }

    public boolean hasWaitingConsumer() {
        while (true) {
            Node node = this.head;
            while (node != null) {
                Object obj = node.item;
                if (node.isData) {
                    if (obj != null) {
                        return false;
                    }
                } else if (obj == null) {
                    return true;
                }
                Node node2 = node.next;
                if (node == node2) {
                    break;
                }
                node = node2;
            }
            return false;
        }
    }

    public int size() {
        return countOfMode(true);
    }

    public int getWaitingConsumerCount() {
        return countOfMode(false);
    }

    public boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        loop0: while (true) {
            Node node = this.head;
            Node node2 = null;
            while (node != null) {
                Node node3 = node.next;
                Object obj2 = node.item;
                if (obj2 != null) {
                    if (node.isData) {
                        if (obj.equals(obj2) && node.tryMatch(obj2, null)) {
                            skipDeadNodes(node2, node, node, node3);
                            return true;
                        }
                        node2 = node;
                    }
                    node = node3;
                } else if (!node.isData) {
                    break loop0;
                }
                Node node4 = node;
                while (node3 != null && node3.isMatched()) {
                    if (node4 == node3) {
                        break;
                    }
                    node4 = node3;
                    node3 = node3.next;
                }
                node2 = skipDeadNodes(node2, node, node4, node3);
                node = node3;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x003b, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean contains(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L4
            return r0
        L4:
            java.util.concurrent.LinkedTransferQueue$Node r1 = r7.head
            r2 = 0
        L7:
            if (r1 == 0) goto L3b
            java.util.concurrent.LinkedTransferQueue$Node r3 = r1.next
            java.lang.Object r4 = r1.item
            if (r4 == 0) goto L1e
            boolean r5 = r1.isData
            if (r5 == 0) goto L23
            boolean r2 = r8.equals(r4)
            if (r2 == 0) goto L1b
            r8 = 1
            return r8
        L1b:
            r2 = r1
        L1c:
            r1 = r3
            goto L7
        L1e:
            boolean r4 = r1.isData
            if (r4 != 0) goto L23
            goto L3b
        L23:
            r4 = r1
        L24:
            if (r3 == 0) goto L36
            boolean r5 = r3.isMatched()
            if (r5 != 0) goto L2d
            goto L36
        L2d:
            if (r4 != r3) goto L30
            goto L4
        L30:
            java.util.concurrent.LinkedTransferQueue$Node r4 = r3.next
            r6 = r4
            r4 = r3
            r3 = r6
            goto L24
        L36:
            java.util.concurrent.LinkedTransferQueue$Node r2 = r7.skipDeadNodes(r2, r1, r4, r3)
            goto L1c
        L3b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.LinkedTransferQueue.contains(java.lang.Object):boolean");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Iterator it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
        objectOutputStream.writeObject((Object) null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
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

    public boolean removeIf(Predicate predicate) {
        predicate.getClass();
        return bulkRemove(predicate);
    }

    public boolean removeAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new LinkedTransferQueue$$ExternalSyntheticLambda2(collection));
    }

    static /* synthetic */ boolean lambda$removeAll$0(Collection collection, Object obj) {
        return collection.contains(obj);
    }

    public boolean retainAll(Collection collection) {
        collection.getClass();
        return bulkRemove(new LinkedTransferQueue$$ExternalSyntheticLambda3(collection));
    }

    static /* synthetic */ boolean lambda$retainAll$1(Collection collection, Object obj) {
        return !collection.contains(obj);
    }

    public void clear() {
        bulkRemove(new LinkedTransferQueue$$ExternalSyntheticLambda1());
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x004e A[SYNTHETIC] */
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
            java.util.concurrent.LinkedTransferQueue$Node r2 = r13.head
            r3 = 0
            r4 = 8
            r5 = r2
            r7 = r3
            r6 = 8
        Lb:
            if (r2 == 0) goto L50
            java.util.concurrent.LinkedTransferQueue$Node r8 = r2.next
            java.lang.Object r9 = r2.item
            r10 = 1
            if (r9 == 0) goto L1a
            boolean r11 = r2.isData
            if (r11 == 0) goto L1a
            r11 = 1
            goto L1b
        L1a:
            r11 = 0
        L1b:
            if (r11 == 0) goto L2c
            boolean r12 = r14.test(r9)
            if (r12 == 0) goto L33
            boolean r9 = r2.tryMatch(r9, r3)
            if (r9 == 0) goto L2a
            r1 = 1
        L2a:
            r11 = 0
            goto L33
        L2c:
            boolean r10 = r2.isData
            if (r10 != 0) goto L33
            if (r9 != 0) goto L33
            goto L50
        L33:
            if (r11 != 0) goto L3f
            if (r8 == 0) goto L3f
            int r6 = r6 + (-1)
            if (r6 != 0) goto L3c
            goto L3f
        L3c:
            if (r2 != r8) goto L4e
            goto L2
        L3f:
            if (r5 == r2) goto L48
            boolean r5 = r13.tryCasSuccessor(r7, r5, r2)
            if (r5 == 0) goto L4a
            r5 = r2
        L48:
            if (r11 == 0) goto L4e
        L4a:
            r7 = r2
            r5 = r8
            r6 = 8
        L4e:
            r2 = r8
            goto Lb
        L50:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.LinkedTransferQueue.bulkRemove(java.util.function.Predicate):boolean");
    }

    void forEachFrom(Consumer consumer, Node node) {
        while (true) {
            Node node2 = null;
            while (node != null) {
                Node node3 = node.next;
                Object obj = node.item;
                if (obj != null) {
                    if (node.isData) {
                        consumer.accept(obj);
                    }
                    node2 = node;
                    node = node3;
                } else if (!node.isData) {
                    return;
                }
                Node node4 = node;
                while (node3 != null && node3.isMatched()) {
                    if (node4 == node3) {
                        break;
                    }
                    node4 = node3;
                    node3 = node3.next;
                }
                node = skipDeadNodes(node2, node, node4, node3);
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
}
