package sun.nio.ch;

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectionKey;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class SelectionKeyImpl extends AbstractSelectionKey {
    private static final VarHandle INTERESTOPS = ConstantBootstraps.fieldVarHandle(MethodHandles.lookup(), "interestOps", VarHandle.class, SelectionKeyImpl.class, Integer.TYPE);
    private final SelChImpl channel;
    private int index;
    private volatile int interestOps;
    int lastPolled;
    private volatile int readyOps;
    private int registeredEvents;
    private final SelectorImpl selector;

    SelectionKeyImpl(SelChImpl selChImpl, SelectorImpl selectorImpl) {
        this.channel = selChImpl;
        this.selector = selectorImpl;
    }

    private void ensureValid() {
        if (!isValid()) {
            throw new CancelledKeyException();
        }
    }

    int getFDVal() {
        return this.channel.getFDVal();
    }

    public SelectableChannel channel() {
        return this.channel;
    }

    public Selector selector() {
        return this.selector;
    }

    public int interestOps() {
        ensureValid();
        return this.interestOps;
    }

    public SelectionKey interestOps(int i) {
        ensureValid();
        if (((channel().validOps() ^ (-1)) & i) != 0) {
            throw new IllegalArgumentException();
        }
        SelectionKeyImpl$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        if (i != 0) {
            this.selector.setEventOps(this);
        }
        return this;
    }

    public int interestOpsOr(int i) {
        ensureValid();
        if (((channel().validOps() ^ (-1)) & i) != 0) {
            throw new IllegalArgumentException();
        }
        SelectionKeyImpl$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        if (i != 0) {
            this.selector.setEventOps(this);
        }
        return 0;
    }

    public int interestOpsAnd(int i) {
        ensureValid();
        SelectionKeyImpl$$ExternalSyntheticThrowRTE0.m("Instruction is unrepresentable in DEX V35: invoke-polymorphic");
        return 0;
    }

    public int readyOps() {
        ensureValid();
        return this.readyOps;
    }

    public void nioReadyOps(int i) {
        this.readyOps = i;
    }

    public int nioReadyOps() {
        return this.readyOps;
    }

    public SelectionKey nioInterestOps(int i) {
        if (((channel().validOps() ^ (-1)) & i) != 0) {
            throw new IllegalArgumentException();
        }
        this.interestOps = i;
        this.selector.setEventOps(this);
        return this;
    }

    public int nioInterestOps() {
        return this.interestOps;
    }

    int translateInterestOps() {
        return this.channel.translateInterestOps(this.interestOps);
    }

    boolean translateAndSetReadyOps(int i) {
        return this.channel.translateAndSetReadyOps(i, this);
    }

    boolean translateAndUpdateReadyOps(int i) {
        return this.channel.translateAndUpdateReadyOps(i, this);
    }

    void registeredEvents(int i) {
        this.registeredEvents = i;
    }

    int registeredEvents() {
        return this.registeredEvents;
    }

    int getIndex() {
        return this.index;
    }

    void setIndex(int i) {
        this.index = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("channel=");
        sb.append(this.channel);
        sb.append(", selector=");
        sb.append(this.selector);
        if (isValid()) {
            sb.append(", interestOps=");
            sb.append(this.interestOps);
            sb.append(", readyOps=");
            sb.append(this.readyOps);
        } else {
            sb.append(", invalid");
        }
        return sb.toString();
    }
}
