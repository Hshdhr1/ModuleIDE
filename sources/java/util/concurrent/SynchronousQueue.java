package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class SynchronousQueue extends AbstractQueue implements BlockingQueue, Serializable {
    static final int MAX_TIMED_SPINS;
    static final int MAX_UNTIMED_SPINS;
    static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000;
    private static final long serialVersionUID = -3223113410248163686L;
    private ReentrantLock qlock;
    private volatile transient Transferer transferer;
    private WaitQueue waitingConsumers;
    private WaitQueue waitingProducers;

    public void clear() {
    }

    public boolean contains(Object obj) {
        return false;
    }

    public /* synthetic */ void forEach(Consumer consumer) {
        Collection.-CC.$default$forEach(this, consumer);
    }

    public boolean isEmpty() {
        return true;
    }

    public /* synthetic */ Stream parallelStream() {
        return Collection.-CC.$default$parallelStream(this);
    }

    public Object peek() {
        return null;
    }

    public int remainingCapacity() {
        return 0;
    }

    public boolean remove(Object obj) {
        return false;
    }

    public boolean removeAll(Collection collection) {
        return false;
    }

    public /* synthetic */ boolean removeIf(Predicate predicate) {
        return Collection.-CC.$default$removeIf(this, predicate);
    }

    public boolean retainAll(Collection collection) {
        return false;
    }

    public int size() {
        return 0;
    }

    public /* synthetic */ Stream stream() {
        return Collection.-CC.$default$stream(this);
    }

    public /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return Collection.-CC.$default$toArray(this, intFunction);
    }

    static abstract class Transferer {
        abstract Object transfer(Object obj, boolean z, long j);

        Transferer() {
        }
    }

    static {
        int i = Runtime.getRuntime().availableProcessors() < 2 ? 0 : 32;
        MAX_TIMED_SPINS = i;
        MAX_UNTIMED_SPINS = i * 16;
    }

    static final class TransferStack extends Transferer {
        static final int DATA = 1;
        static final int FULFILLING = 2;
        static final int REQUEST = 0;
        private static final VarHandle SHEAD;
        volatile SNode head;

        static boolean isFulfilling(int i) {
            return (i & 2) != 0;
        }

        TransferStack() {
        }

        static final class SNode {
            private static final VarHandle SMATCH;
            private static final VarHandle SNEXT;
            Object item;
            volatile SNode match;
            int mode;
            volatile SNode next;
            volatile Thread waiter;

            SNode(Object obj) {
                this.item = obj;
            }

            boolean casNext(SNode sNode, SNode sNode2) {
                if (sNode != this.next) {
                    return false;
                }
                SynchronousQueue$TransferStack$SNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                return false;
            }

            boolean tryMatch(SNode sNode) {
                if (this.match == null) {
                    SynchronousQueue$TransferStack$SNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                }
                return this.match == sNode;
            }

            void tryCancel() {
                SynchronousQueue$TransferStack$SNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }

            boolean isCancelled() {
                return this.match == this;
            }

            static {
                try {
                    MethodHandles.Lookup lookup = MethodHandles.lookup();
                    SMATCH = lookup.findVarHandle(SNode.class, "match", SNode.class);
                    SNEXT = lookup.findVarHandle(SNode.class, "next", SNode.class);
                } catch (ReflectiveOperationException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        }

        boolean casHead(SNode sNode, SNode sNode2) {
            if (sNode != this.head) {
                return false;
            }
            SynchronousQueue$TransferStack$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        static SNode snode(SNode sNode, Object obj, SNode sNode2, int i) {
            if (sNode == null) {
                sNode = new SNode(obj);
            }
            sNode.mode = i;
            sNode.next = sNode2;
            return sNode;
        }

        /* JADX WARN: Code restructure failed: missing block: B:69:0x0030, code lost:
        
            r3 = r2.next;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x0032, code lost:
        
            if (r3 != null) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x0038, code lost:
        
            r4 = r3.next;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x003e, code lost:
        
            if (r3.tryMatch(r2) == false) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x004b, code lost:
        
            r2.casNext(r3, r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x0040, code lost:
        
            casHead(r2, r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x0043, code lost:
        
            if (r0 != 0) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x0047, code lost:
        
            return r3.item;
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x004a, code lost:
        
            return r2.item;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.Object transfer(java.lang.Object r8, boolean r9, long r10) {
            /*
                r7 = this;
                if (r8 != 0) goto L4
                r0 = 0
                goto L5
            L4:
                r0 = 1
            L5:
                r1 = 0
            L6:
                r2 = r1
            L7:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r3 = r7.head
                if (r3 == 0) goto L67
                int r4 = r3.mode
                if (r4 != r0) goto L10
                goto L67
            L10:
                int r4 = r3.mode
                boolean r4 = isFulfilling(r4)
                if (r4 != 0) goto L4f
                boolean r4 = r3.isCancelled()
                if (r4 == 0) goto L24
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r4 = r3.next
                r7.casHead(r3, r4)
                goto L7
            L24:
                r4 = r0 | 2
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r2 = snode(r2, r8, r3, r4)
                boolean r3 = r7.casHead(r3, r2)
                if (r3 == 0) goto L7
            L30:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r3 = r2.next
                if (r3 != 0) goto L38
                r7.casHead(r2, r1)
                goto L6
            L38:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r4 = r3.next
                boolean r5 = r3.tryMatch(r2)
                if (r5 == 0) goto L4b
                r7.casHead(r2, r4)
                if (r0 != 0) goto L48
                java.lang.Object r8 = r3.item
                return r8
            L48:
                java.lang.Object r8 = r2.item
                return r8
            L4b:
                r2.casNext(r3, r4)
                goto L30
            L4f:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r4 = r3.next
                if (r4 != 0) goto L57
                r7.casHead(r3, r1)
                goto L7
            L57:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r5 = r4.next
                boolean r6 = r4.tryMatch(r3)
                if (r6 == 0) goto L63
                r7.casHead(r3, r5)
                goto L7
            L63:
                r3.casNext(r4, r5)
                goto L7
            L67:
                if (r9 == 0) goto L7e
                r4 = 0
                int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
                if (r6 > 0) goto L7e
                if (r3 == 0) goto L7d
                boolean r4 = r3.isCancelled()
                if (r4 == 0) goto L7d
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r4 = r3.next
                r7.casHead(r3, r4)
                goto L7
            L7d:
                return r1
            L7e:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r2 = snode(r2, r8, r3, r0)
                boolean r3 = r7.casHead(r3, r2)
                if (r3 == 0) goto L7
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r8 = r7.awaitFulfill(r2, r9, r10)
                if (r8 != r2) goto L92
                r7.clean(r2)
                return r1
            L92:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r9 = r7.head
                if (r9 == 0) goto L9f
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r10 = r9.next
                if (r10 != r2) goto L9f
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r10 = r2.next
                r7.casHead(r9, r10)
            L9f:
                if (r0 != 0) goto La4
                java.lang.Object r8 = r8.item
                return r8
            La4:
                java.lang.Object r8 = r2.item
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.SynchronousQueue.TransferStack.transfer(java.lang.Object, boolean, long):java.lang.Object");
        }

        /* JADX WARN: Code restructure failed: missing block: B:55:0x001f, code lost:
        
            r5 = 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.util.concurrent.SynchronousQueue.TransferStack.SNode awaitFulfill(java.util.concurrent.SynchronousQueue.TransferStack.SNode r11, boolean r12, long r13) {
            /*
                r10 = this;
                r0 = 0
                if (r12 == 0) goto La
                long r2 = java.lang.System.nanoTime()
                long r2 = r2 + r13
                goto Lb
            La:
                r2 = r0
            Lb:
                java.lang.Thread r4 = java.lang.Thread.currentThread()
                boolean r5 = r10.shouldSpin(r11)
                r6 = 0
                if (r5 == 0) goto L1e
                if (r12 == 0) goto L1b
                int r5 = java.util.concurrent.SynchronousQueue.MAX_TIMED_SPINS
                goto L1f
            L1b:
                int r5 = java.util.concurrent.SynchronousQueue.MAX_UNTIMED_SPINS
                goto L1f
            L1e:
                r5 = 0
            L1f:
                boolean r7 = r4.isInterrupted()
                if (r7 == 0) goto L28
                r11.tryCancel()
            L28:
                java.util.concurrent.SynchronousQueue$TransferStack$SNode r7 = r11.match
                if (r7 == 0) goto L2d
                return r7
            L2d:
                if (r12 == 0) goto L3d
                long r13 = java.lang.System.nanoTime()
                long r13 = r2 - r13
                int r7 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
                if (r7 > 0) goto L3d
                r11.tryCancel()
                goto L1f
            L3d:
                if (r5 <= 0) goto L4b
                java.lang.Thread.onSpinWait()
                boolean r7 = r10.shouldSpin(r11)
                if (r7 == 0) goto L1e
                int r5 = r5 + (-1)
                goto L1f
            L4b:
                java.lang.Thread r7 = r11.waiter
                if (r7 != 0) goto L52
                r11.waiter = r4
                goto L1f
            L52:
                if (r12 != 0) goto L58
                java.util.concurrent.locks.LockSupport.park(r10)
                goto L1f
            L58:
                r7 = 1000(0x3e8, double:4.94E-321)
                int r9 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
                if (r9 <= 0) goto L1f
                java.util.concurrent.locks.LockSupport.parkNanos(r10, r13)
                goto L1f
            */
            throw new UnsupportedOperationException("Method not decompiled: java.util.concurrent.SynchronousQueue.TransferStack.awaitFulfill(java.util.concurrent.SynchronousQueue$TransferStack$SNode, boolean, long):java.util.concurrent.SynchronousQueue$TransferStack$SNode");
        }

        boolean shouldSpin(SNode sNode) {
            SNode sNode2 = this.head;
            return sNode2 == sNode || sNode2 == null || isFulfilling(sNode2.mode);
        }

        void clean(SNode sNode) {
            SNode sNode2;
            sNode.item = null;
            sNode.waiter = null;
            SNode sNode3 = sNode.next;
            if (sNode3 != null && sNode3.isCancelled()) {
                sNode3 = sNode3.next;
            }
            while (true) {
                sNode2 = this.head;
                if (sNode2 == null || sNode2 == sNode3 || !sNode2.isCancelled()) {
                    break;
                } else {
                    casHead(sNode2, sNode2.next);
                }
            }
            while (sNode2 != null && sNode2 != sNode3) {
                SNode sNode4 = sNode2.next;
                if (sNode4 == null || !sNode4.isCancelled()) {
                    sNode2 = sNode4;
                } else {
                    sNode2.casNext(sNode4, sNode4.next);
                }
            }
        }

        static {
            try {
                SHEAD = MethodHandles.lookup().findVarHandle(TransferStack.class, "head", SNode.class);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    static final class TransferQueue extends Transferer {
        private static final VarHandle QCLEANME;
        private static final VarHandle QHEAD;
        private static final VarHandle QTAIL;
        volatile transient QNode cleanMe;
        volatile transient QNode head;
        volatile transient QNode tail;

        static final class QNode {
            private static final VarHandle QITEM;
            private static final VarHandle QNEXT;
            final boolean isData;
            volatile Object item;
            volatile QNode next;
            volatile Thread waiter;

            QNode(Object obj, boolean z) {
                this.item = obj;
                this.isData = z;
            }

            boolean casNext(QNode qNode, QNode qNode2) {
                if (this.next != qNode) {
                    return false;
                }
                SynchronousQueue$TransferQueue$QNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                return false;
            }

            boolean casItem(Object obj, Object obj2) {
                if (this.item != obj) {
                    return false;
                }
                SynchronousQueue$TransferQueue$QNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                return false;
            }

            void tryCancel(Object obj) {
                SynchronousQueue$TransferQueue$QNode$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }

            boolean isCancelled() {
                return this.item == this;
            }

            boolean isOffList() {
                return this.next == this;
            }

            static {
                try {
                    MethodHandles.Lookup lookup = MethodHandles.lookup();
                    QITEM = lookup.findVarHandle(QNode.class, "item", Object.class);
                    QNEXT = lookup.findVarHandle(QNode.class, "next", QNode.class);
                } catch (ReflectiveOperationException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        }

        TransferQueue() {
            QNode qNode = new QNode(null, false);
            this.head = qNode;
            this.tail = qNode;
        }

        void advanceHead(QNode qNode, QNode qNode2) {
            if (qNode == this.head) {
                SynchronousQueue$TransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }

        void advanceTail(QNode qNode, QNode qNode2) {
            if (this.tail == qNode) {
                SynchronousQueue$TransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            }
        }

        boolean casCleanMe(QNode qNode, QNode qNode2) {
            if (this.cleanMe != qNode) {
                return false;
            }
            SynchronousQueue$TransferQueue$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            return false;
        }

        Object transfer(Object obj, boolean z, long j) {
            boolean z2 = obj != null;
            QNode qNode = null;
            while (true) {
                QNode qNode2 = this.tail;
                QNode qNode3 = this.head;
                if (qNode2 != null && qNode3 != null) {
                    if (qNode3 == qNode2 || qNode2.isData == z2) {
                        QNode qNode4 = qNode2.next;
                        if (qNode2 != this.tail) {
                            continue;
                        } else if (qNode4 != null) {
                            advanceTail(qNode2, qNode4);
                        } else {
                            if (z && j <= 0) {
                                return null;
                            }
                            if (qNode == null) {
                                qNode = new QNode(obj, z2);
                            }
                            if (qNode2.casNext(null, qNode)) {
                                advanceTail(qNode2, qNode);
                                QNode qNode5 = qNode;
                                Object awaitFulfill = awaitFulfill(qNode5, obj, z, j);
                                if (awaitFulfill == qNode5) {
                                    clean(qNode2, qNode5);
                                    return null;
                                }
                                if (!qNode5.isOffList()) {
                                    advanceHead(qNode2, qNode5);
                                    if (awaitFulfill != null) {
                                        qNode5.item = qNode5;
                                    }
                                    qNode5.waiter = null;
                                }
                                if (awaitFulfill != null) {
                                    return awaitFulfill;
                                }
                            }
                        }
                    } else {
                        QNode qNode6 = qNode3.next;
                        if (qNode2 == this.tail && qNode6 != null && qNode3 == this.head) {
                            Object obj2 = qNode6.item;
                            if (z2 == (obj2 != null) || obj2 == qNode6 || !qNode6.casItem(obj2, obj)) {
                                advanceHead(qNode3, qNode6);
                            } else {
                                advanceHead(qNode3, qNode6);
                                LockSupport.unpark(qNode6.waiter);
                                if (obj2 != null) {
                                    return obj2;
                                }
                            }
                        }
                    }
                }
            }
            return obj;
        }

        Object awaitFulfill(QNode qNode, Object obj, boolean z, long j) {
            int i;
            long nanoTime = z ? System.nanoTime() + j : 0L;
            Thread currentThread = Thread.currentThread();
            if (this.head.next == qNode) {
                i = z ? SynchronousQueue.MAX_TIMED_SPINS : SynchronousQueue.MAX_UNTIMED_SPINS;
            } else {
                i = 0;
            }
            while (true) {
                if (currentThread.isInterrupted()) {
                    qNode.tryCancel(obj);
                }
                Object obj2 = qNode.item;
                if (obj2 != obj) {
                    return obj2;
                }
                if (z) {
                    j = nanoTime - System.nanoTime();
                    if (j <= 0) {
                        qNode.tryCancel(obj);
                    }
                }
                if (i > 0) {
                    i--;
                    Thread.onSpinWait();
                } else if (qNode.waiter == null) {
                    qNode.waiter = currentThread;
                } else if (!z) {
                    LockSupport.park(this);
                } else if (j > 1000) {
                    LockSupport.parkNanos(this, j);
                }
            }
        }

        void clean(QNode qNode, QNode qNode2) {
            QNode qNode3;
            QNode qNode4;
            qNode2.waiter = null;
            while (qNode.next == qNode2) {
                QNode qNode5 = this.head;
                QNode qNode6 = qNode5.next;
                if (qNode6 != null && qNode6.isCancelled()) {
                    advanceHead(qNode5, qNode6);
                } else {
                    QNode qNode7 = this.tail;
                    if (qNode7 == qNode5) {
                        return;
                    }
                    QNode qNode8 = qNode7.next;
                    if (qNode7 != this.tail) {
                        continue;
                    } else if (qNode8 != null) {
                        advanceTail(qNode7, qNode8);
                    } else {
                        if (qNode2 != qNode7 && ((qNode4 = qNode2.next) == qNode2 || qNode.casNext(qNode2, qNode4))) {
                            return;
                        }
                        QNode qNode9 = this.cleanMe;
                        if (qNode9 != null) {
                            QNode qNode10 = qNode9.next;
                            if (qNode10 == null || qNode10 == qNode9 || !qNode10.isCancelled() || (qNode10 != qNode7 && (qNode3 = qNode10.next) != null && qNode3 != qNode10 && qNode9.casNext(qNode10, qNode3))) {
                                casCleanMe(qNode9, null);
                            }
                            if (qNode9 == qNode) {
                                return;
                            }
                        } else if (casCleanMe(null, qNode)) {
                            return;
                        }
                    }
                }
            }
        }

        static {
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                QHEAD = lookup.findVarHandle(TransferQueue.class, "head", QNode.class);
                QTAIL = lookup.findVarHandle(TransferQueue.class, "tail", QNode.class);
                QCLEANME = lookup.findVarHandle(TransferQueue.class, "cleanMe", QNode.class);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public SynchronousQueue() {
        this(false);
    }

    public SynchronousQueue(boolean z) {
        this.transferer = z ? new TransferQueue() : new TransferStack();
    }

    public void put(Object obj) throws InterruptedException {
        obj.getClass();
        if (this.transferer.transfer(obj, false, 0L) != null) {
            return;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    public boolean offer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException {
        obj.getClass();
        if (this.transferer.transfer(obj, true, timeUnit.toNanos(j)) != null) {
            return true;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return false;
    }

    public boolean offer(Object obj) {
        obj.getClass();
        return this.transferer.transfer(obj, true, 0L) != null;
    }

    public Object take() throws InterruptedException {
        Object transfer = this.transferer.transfer(null, false, 0L);
        if (transfer != null) {
            return transfer;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    public Object poll(long j, TimeUnit timeUnit) throws InterruptedException {
        Object transfer = this.transferer.transfer(null, true, timeUnit.toNanos(j));
        if (transfer == null && Thread.interrupted()) {
            throw new InterruptedException();
        }
        return transfer;
    }

    public Object poll() {
        return this.transferer.transfer(null, true, 0L);
    }

    public boolean containsAll(Collection collection) {
        return collection.isEmpty();
    }

    public Iterator iterator() {
        return SynchronousQueue$$ExternalSyntheticBackport0.m();
    }

    public Spliterator spliterator() {
        return Spliterators.emptySpliterator();
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public Object[] toArray(Object[] objArr) {
        if (objArr.length > 0) {
            objArr[0] = null;
        }
        return objArr;
    }

    public String toString() {
        return "[]";
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

    static class WaitQueue implements Serializable {
        WaitQueue() {
        }
    }

    static class LifoWaitQueue extends WaitQueue {
        private static final long serialVersionUID = -3633113410248163686L;

        LifoWaitQueue() {
        }
    }

    static class FifoWaitQueue extends WaitQueue {
        private static final long serialVersionUID = -3623113410248163686L;

        FifoWaitQueue() {
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.transferer instanceof TransferQueue) {
            this.qlock = new ReentrantLock(true);
            this.waitingProducers = new FifoWaitQueue();
            this.waitingConsumers = new FifoWaitQueue();
        } else {
            this.qlock = new ReentrantLock();
            this.waitingProducers = new LifoWaitQueue();
            this.waitingConsumers = new LifoWaitQueue();
        }
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.waitingProducers instanceof FifoWaitQueue) {
            this.transferer = new TransferQueue();
        } else {
            this.transferer = new TransferStack();
        }
    }
}
