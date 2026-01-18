package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Phaser {
    private static final long COUNTS_MASK = 4294967295L;
    private static final int EMPTY = 1;
    private static final int MAX_PARTIES = 65535;
    private static final int MAX_PHASE = Integer.MAX_VALUE;
    private static final int NCPU;
    private static final int ONE_ARRIVAL = 1;
    private static final int ONE_DEREGISTER = 65537;
    private static final int ONE_PARTY = 65536;
    private static final long PARTIES_MASK = 4294901760L;
    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    static final int SPINS_PER_ARRIVAL;
    private static final VarHandle STATE;
    private static final long TERMINATION_BIT = Long.MIN_VALUE;
    private static final int UNARRIVED_MASK = 65535;
    private final AtomicReference evenQ;
    private final AtomicReference oddQ;
    private final Phaser parent;
    private final Phaser root;
    private volatile long state;

    private static int arrivedOf(long j) {
        int i = (int) j;
        if (i == 1) {
            return 0;
        }
        return (i >>> 16) - (i & 65535);
    }

    private static int partiesOf(long j) {
        return ((int) j) >>> 16;
    }

    private static int phaseOf(long j) {
        return (int) (j >>> 32);
    }

    private static int unarrivedOf(long j) {
        int i = (int) j;
        if (i == 1) {
            return 0;
        }
        return 65535 & i;
    }

    protected boolean onAdvance(int i, int i2) {
        return i2 == 0;
    }

    private String badArrive(long j) {
        return "Attempted arrival of unregistered party for " + stateToString(j);
    }

    private String badRegister(long j) {
        return "Attempt to register more than 65535 parties for " + stateToString(j);
    }

    private int doArrive(int i) {
        Phaser phaser = this.root;
        while (true) {
            long reconcileState = phaser == this ? this.state : reconcileState();
            int i2 = (int) (reconcileState >>> 32);
            if (i2 < 0) {
                return i2;
            }
            int i3 = (int) reconcileState;
            if ((i3 == 1 ? 0 : i3 & 65535) <= 0) {
                throw new IllegalStateException(badArrive(reconcileState));
            }
            Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    private int doRegister(int i) {
        Phaser phaser = this.parent;
        while (true) {
            long reconcileState = phaser == null ? this.state : reconcileState();
            int i2 = (int) reconcileState;
            int i3 = i2 & 65535;
            if (i > 65535 - (i2 >>> 16)) {
                throw new IllegalStateException(badRegister(reconcileState));
            }
            int i4 = (int) (reconcileState >>> 32);
            if (i4 < 0) {
                return i4;
            }
            if (i2 != 1) {
                if (phaser == null || reconcileState() == reconcileState) {
                    if (i3 == 0) {
                        this.root.internalAwaitAdvance(i4, null);
                    } else {
                        Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    }
                }
            } else if (phaser == null) {
                Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            } else {
                synchronized (this) {
                    if (this.state == reconcileState) {
                        int doRegister = phaser.doRegister(1);
                        if (doRegister < 0) {
                            return doRegister;
                        }
                        while (true) {
                            Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                            long j = this.root.state;
                        }
                    }
                }
            }
        }
    }

    private long reconcileState() {
        Phaser phaser = this.root;
        long j = this.state;
        if (phaser != this) {
            while (((int) (phaser.state >>> 32)) != ((int) (j >>> 32))) {
                Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                j = this.state;
            }
        }
        return j;
    }

    public Phaser() {
        this(null, 0);
    }

    public Phaser(int i) {
        this(null, i);
    }

    public Phaser(Phaser phaser) {
        this(phaser, 0);
    }

    public Phaser(Phaser phaser, int i) {
        long j;
        if ((i >>> 16) != 0) {
            throw new IllegalArgumentException("Illegal number of parties");
        }
        this.parent = phaser;
        int i2 = 0;
        if (phaser != null) {
            Phaser phaser2 = phaser.root;
            this.root = phaser2;
            this.evenQ = phaser2.evenQ;
            this.oddQ = phaser2.oddQ;
            if (i != 0) {
                i2 = phaser.doRegister(1);
            }
        } else {
            this.root = this;
            this.evenQ = new AtomicReference();
            this.oddQ = new AtomicReference();
        }
        if (i == 0) {
            j = 1;
        } else {
            long j2 = i;
            j = j2 | (i2 << 32) | (j2 << 16);
        }
        this.state = j;
    }

    public int register() {
        return doRegister(1);
    }

    public int bulkRegister(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i == 0) {
            return getPhase();
        }
        return doRegister(i);
    }

    public int arrive() {
        return doArrive(1);
    }

    public int arriveAndDeregister() {
        return doArrive(65537);
    }

    public int arriveAndAwaitAdvance() {
        Phaser phaser = this.root;
        while (true) {
            long reconcileState = phaser == this ? this.state : reconcileState();
            int i = (int) (reconcileState >>> 32);
            if (i < 0) {
                return i;
            }
            int i2 = (int) reconcileState;
            if ((i2 == 1 ? 0 : i2 & 65535) <= 0) {
                throw new IllegalStateException(badArrive(reconcileState));
            }
            Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    public int awaitAdvance(int i) {
        Phaser phaser = this.root;
        int reconcileState = (int) ((phaser == this ? this.state : reconcileState()) >>> 32);
        return i < 0 ? i : reconcileState == i ? phaser.internalAwaitAdvance(i, null) : reconcileState;
    }

    public int awaitAdvanceInterruptibly(int i) throws InterruptedException {
        Phaser phaser = this.root;
        int reconcileState = (int) ((phaser == this ? this.state : reconcileState()) >>> 32);
        if (i < 0) {
            return i;
        }
        if (reconcileState != i) {
            return reconcileState;
        }
        QNode qNode = new QNode(this, i, true, false, 0L);
        int internalAwaitAdvance = phaser.internalAwaitAdvance(i, qNode);
        if (qNode.wasInterrupted) {
            throw new InterruptedException();
        }
        return internalAwaitAdvance;
    }

    public int awaitAdvanceInterruptibly(int i, long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j);
        Phaser phaser = this.root;
        int reconcileState = (int) ((phaser == this ? this.state : reconcileState()) >>> 32);
        if (i < 0) {
            return i;
        }
        if (reconcileState != i) {
            return reconcileState;
        }
        QNode qNode = new QNode(this, i, true, true, nanos);
        int internalAwaitAdvance = phaser.internalAwaitAdvance(i, qNode);
        if (qNode.wasInterrupted) {
            throw new InterruptedException();
        }
        if (internalAwaitAdvance != i) {
            return internalAwaitAdvance;
        }
        throw new TimeoutException();
    }

    public void forceTermination() {
        Phaser phaser = this.root;
        while (phaser.state >= 0) {
            Phaser$$ExternalSyntheticThrowRTE1.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        }
    }

    public final int getPhase() {
        return (int) (this.root.state >>> 32);
    }

    public int getRegisteredParties() {
        return partiesOf(this.state);
    }

    public int getArrivedParties() {
        return arrivedOf(reconcileState());
    }

    public int getUnarrivedParties() {
        return unarrivedOf(reconcileState());
    }

    public Phaser getParent() {
        return this.parent;
    }

    public Phaser getRoot() {
        return this.root;
    }

    public boolean isTerminated() {
        return this.root.state < 0;
    }

    public String toString() {
        return stateToString(reconcileState());
    }

    private String stateToString(long j) {
        return super.toString() + "[phase = " + phaseOf(j) + " parties = " + partiesOf(j) + " arrived = " + arrivedOf(j) + "]";
    }

    private void releaseWaiters(int i) {
        Thread thread;
        AtomicReference atomicReference = (i & 1) == 0 ? this.evenQ : this.oddQ;
        while (true) {
            QNode qNode = (QNode) atomicReference.get();
            if (qNode == null || qNode.phase == ((int) (this.root.state >>> 32))) {
                return;
            }
            if (Phaser$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, qNode, qNode.next) && (thread = qNode.thread) != null) {
                qNode.thread = null;
                LockSupport.unpark(thread);
            }
        }
    }

    private int abortWait(int i) {
        int i2;
        Thread thread;
        AtomicReference atomicReference = (i & 1) == 0 ? this.evenQ : this.oddQ;
        while (true) {
            QNode qNode = (QNode) atomicReference.get();
            i2 = (int) (this.root.state >>> 32);
            if (qNode == null || ((thread = qNode.thread) != null && qNode.phase == i2)) {
                break;
            }
            if (Phaser$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, qNode, qNode.next) && thread != null) {
                qNode.thread = null;
                LockSupport.unpark(thread);
            }
        }
        return i2;
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        NCPU = availableProcessors;
        SPINS_PER_ARRIVAL = availableProcessors < 2 ? 1 : 256;
        try {
            STATE = MethodHandles.lookup().findVarHandle(Phaser.class, "state", Long.TYPE);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private int internalAwaitAdvance(int i, QNode qNode) {
        int i2;
        int i3;
        Phaser phaser;
        releaseWaiters(i - 1);
        int i4 = SPINS_PER_ARRIVAL;
        int i5 = 0;
        boolean z = false;
        while (true) {
            long j = this.state;
            i2 = (int) (j >>> 32);
            if (i2 != i) {
                i3 = i;
                phaser = this;
                break;
            }
            if (qNode == null) {
                int i6 = 65535 & ((int) j);
                if (i6 != i5) {
                    if (i6 < NCPU) {
                        i4 += SPINS_PER_ARRIVAL;
                    }
                    i5 = i6;
                }
                boolean interrupted = Thread.interrupted();
                if (interrupted || i4 - 1 < 0) {
                    i3 = i;
                    QNode qNode2 = new QNode(this, i3, false, false, 0L);
                    qNode2.wasInterrupted = interrupted;
                    qNode = qNode2;
                } else {
                    Thread.onSpinWait();
                    i3 = i;
                }
            } else {
                i3 = i;
                phaser = this;
                if (qNode.isReleasable()) {
                    break;
                }
                if (!z) {
                    AtomicReference atomicReference = (i3 & 1) == 0 ? phaser.evenQ : phaser.oddQ;
                    QNode qNode3 = (QNode) atomicReference.get();
                    qNode.next = qNode3;
                    if ((qNode3 == null || qNode3.phase == i3) && ((int) (phaser.state >>> 32)) == i3) {
                        z = Phaser$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, qNode3, qNode);
                    }
                } else {
                    try {
                        ForkJoinPool.managedBlock(qNode);
                    } catch (InterruptedException unused) {
                        qNode.wasInterrupted = true;
                    }
                }
            }
            i = i3;
        }
        if (qNode != null) {
            if (qNode.thread != null) {
                qNode.thread = null;
            }
            if (qNode.wasInterrupted && !qNode.interruptible) {
                Thread.currentThread().interrupt();
            }
            if (i2 == i3 && (i2 = (int) (phaser.state >>> 32)) == i3) {
                return abortWait(i3);
            }
        }
        releaseWaiters(i3);
        return i2;
    }

    static final class QNode implements ForkJoinPool.ManagedBlocker {
        final long deadline;
        final boolean interruptible;
        long nanos;
        QNode next;
        final int phase;
        final Phaser phaser;
        volatile Thread thread;
        final boolean timed;
        boolean wasInterrupted;

        QNode(Phaser phaser, int i, boolean z, boolean z2, long j) {
            this.phaser = phaser;
            this.phase = i;
            this.interruptible = z;
            this.nanos = j;
            this.timed = z2;
            this.deadline = z2 ? System.nanoTime() + j : 0L;
            this.thread = Thread.currentThread();
        }

        public boolean isReleasable() {
            if (this.thread == null) {
                return true;
            }
            if (this.phaser.getPhase() != this.phase) {
                this.thread = null;
                return true;
            }
            if (Thread.interrupted()) {
                this.wasInterrupted = true;
            }
            if (this.wasInterrupted && this.interruptible) {
                this.thread = null;
                return true;
            }
            if (!this.timed) {
                return false;
            }
            if (this.nanos > 0) {
                long nanoTime = this.deadline - System.nanoTime();
                this.nanos = nanoTime;
                if (nanoTime > 0) {
                    return false;
                }
            }
            this.thread = null;
            return true;
        }

        public boolean block() {
            while (!isReleasable()) {
                if (this.timed) {
                    LockSupport.parkNanos(this, this.nanos);
                } else {
                    LockSupport.park(this);
                }
            }
            return true;
        }
    }
}
