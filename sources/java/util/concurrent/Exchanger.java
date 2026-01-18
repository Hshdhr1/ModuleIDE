package java.util.concurrent;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Exchanger {
    private static final VarHandle AA;
    private static final int ASHIFT = 5;
    private static final VarHandle BOUND;
    static final int FULL;
    private static final VarHandle MATCH;
    private static final int MMASK = 255;
    private static final int NCPU;
    private static final Object NULL_ITEM;
    private static final int SEQ = 256;
    private static final VarHandle SLOT;
    private static final int SPINS = 1024;
    private static final Object TIMED_OUT;
    private volatile Node[] arena;
    private volatile int bound;
    private final Participant participant = new Participant();
    private volatile Node slot;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        NCPU = availableProcessors;
        FULL = availableProcessors >= 510 ? 255 : availableProcessors >>> 1;
        NULL_ITEM = new Object();
        TIMED_OUT = new Object();
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            BOUND = lookup.findVarHandle(Exchanger.class, "bound", Integer.TYPE);
            SLOT = lookup.findVarHandle(Exchanger.class, "slot", Node.class);
            MATCH = lookup.findVarHandle(Node.class, "match", Object.class);
            AA = MethodHandles.arrayElementVarHandle(Node[].class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static final class Node {
        int bound;
        int collides;
        int hash;
        int index;
        Object item;
        volatile Object match;
        volatile Thread parked;

        Node() {
        }
    }

    static final class Participant extends ThreadLocal {
        Participant() {
        }

        public Node initialValue() {
            return new Node();
        }
    }

    private final Object arenaExchange(Object obj, boolean z, long j) {
        int length = this.arena.length;
        Node node = (Node) this.participant.get();
        int i = node.index;
        while (true) {
            int i2 = (i << 5) + 31;
            Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
            int i3 = this.bound;
            int i4 = i3 & 255;
            if (i <= i4) {
                node.item = obj;
                Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                node.item = null;
            } else {
                if (node.bound != i3) {
                    node.bound = i3;
                    node.collides = 0;
                    if (i == i4 && i4 != 0) {
                        i4--;
                    }
                } else {
                    int i5 = node.collides;
                    if (i5 >= i4 && i4 != FULL) {
                        Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                    }
                    node.collides = i5 + 1;
                    if (i != 0) {
                        i4 = i - 1;
                    }
                }
                node.index = i4;
                i = i4;
            }
        }
    }

    private final Object slotExchange(Object obj, boolean z, long j) {
        Node node = (Node) this.participant.get();
        if (Thread.currentThread().isInterrupted()) {
            return null;
        }
        while (true) {
            if (this.slot != null) {
                Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                if (NCPU > 1 && this.bound == 0) {
                    Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                }
            } else {
                if (this.arena != null) {
                    return null;
                }
                node.item = obj;
                Exchanger$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
                node.item = null;
            }
        }
    }

    public Object exchange(Object obj) throws InterruptedException {
        Object arenaExchange;
        if (obj == null) {
            obj = NULL_ITEM;
        }
        if ((this.arena != null || (arenaExchange = slotExchange(obj, false, 0L)) == null) && (Thread.interrupted() || (arenaExchange = arenaExchange(obj, false, 0L)) == null)) {
            throw new InterruptedException();
        }
        if (arenaExchange == NULL_ITEM) {
            return null;
        }
        return arenaExchange;
    }

    public Object exchange(Object obj, long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        Object arenaExchange;
        if (obj == null) {
            obj = NULL_ITEM;
        }
        long nanos = timeUnit.toNanos(j);
        if ((this.arena != null || (arenaExchange = slotExchange(obj, true, nanos)) == null) && (Thread.interrupted() || (arenaExchange = arenaExchange(obj, true, nanos)) == null)) {
            throw new InterruptedException();
        }
        if (arenaExchange == TIMED_OUT) {
            throw new TimeoutException();
        }
        if (arenaExchange == NULL_ITEM) {
            return null;
        }
        return arenaExchange;
    }
}
