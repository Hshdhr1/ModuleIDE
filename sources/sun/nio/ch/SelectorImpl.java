package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.IllegalSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class SelectorImpl extends AbstractSelector {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean inSelect;
    private final Set keys;
    private final Set publicKeys;
    private final Set publicSelectedKeys;
    private final Set selectedKeys;

    protected abstract int doSelect(Consumer consumer, long j) throws IOException;

    protected abstract void implClose() throws IOException;

    protected abstract void implDereg(SelectionKeyImpl selectionKeyImpl) throws IOException;

    protected abstract void setEventOps(SelectionKeyImpl selectionKeyImpl);

    protected SelectorImpl(SelectorProvider selectorProvider) {
        super(selectorProvider);
        ConcurrentHashMap.KeySetView newKeySet = ConcurrentHashMap.newKeySet();
        this.keys = newKeySet;
        HashSet hashSet = new HashSet();
        this.selectedKeys = hashSet;
        this.publicKeys = Collections.unmodifiableSet(newKeySet);
        this.publicSelectedKeys = Util.ungrowableSet(hashSet);
    }

    private void ensureOpen() {
        if (!isOpen()) {
            throw new ClosedSelectorException();
        }
    }

    public final Set keys() {
        ensureOpen();
        return this.publicKeys;
    }

    public final Set selectedKeys() {
        ensureOpen();
        return this.publicSelectedKeys;
    }

    protected final void begin(boolean z) {
        if (z) {
            begin();
        }
    }

    protected final void end(boolean z) {
        if (z) {
            end();
        }
    }

    private int lockAndDoSelect(Consumer consumer, long j) throws IOException {
        int doSelect;
        synchronized (this) {
            ensureOpen();
            if (this.inSelect) {
                throw new IllegalStateException("select in progress");
            }
            this.inSelect = true;
            try {
                synchronized (this.publicSelectedKeys) {
                    doSelect = doSelect(consumer, j);
                }
            } finally {
                this.inSelect = false;
            }
        }
        return doSelect;
    }

    public final int select(long j) throws IOException {
        if (j < 0) {
            throw new IllegalArgumentException("Negative timeout");
        }
        if (j == 0) {
            j = -1;
        }
        return lockAndDoSelect(null, j);
    }

    public final int select() throws IOException {
        return lockAndDoSelect(null, -1L);
    }

    public final int selectNow() throws IOException {
        return lockAndDoSelect(null, 0L);
    }

    public final int select(Consumer consumer, long j) throws IOException {
        consumer.getClass();
        if (j < 0) {
            throw new IllegalArgumentException("Negative timeout");
        }
        if (j == 0) {
            j = -1;
        }
        return lockAndDoSelect(consumer, j);
    }

    public final int select(Consumer consumer) throws IOException {
        consumer.getClass();
        return lockAndDoSelect(consumer, -1L);
    }

    public final int selectNow(Consumer consumer) throws IOException {
        consumer.getClass();
        return lockAndDoSelect(consumer, 0L);
    }

    public final void implCloseSelector() throws IOException {
        wakeup();
        synchronized (this) {
            implClose();
            synchronized (this.publicSelectedKeys) {
                Iterator it = this.keys.iterator();
                while (it.hasNext()) {
                    SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl) it.next();
                    deregister(selectionKeyImpl);
                    SelChImpl channel = selectionKeyImpl.channel();
                    if (!channel.isOpen() && !channel.isRegistered()) {
                        channel.kill();
                    }
                    this.selectedKeys.remove(selectionKeyImpl);
                    it.remove();
                }
            }
        }
    }

    protected final SelectionKey register(AbstractSelectableChannel abstractSelectableChannel, int i, Object obj) {
        if (!(abstractSelectableChannel instanceof SelChImpl)) {
            throw new IllegalSelectorException();
        }
        SelectionKeyImpl selectionKeyImpl = new SelectionKeyImpl((SelChImpl) abstractSelectableChannel, this);
        selectionKeyImpl.attach(obj);
        implRegister(selectionKeyImpl);
        this.keys.add(selectionKeyImpl);
        try {
            selectionKeyImpl.interestOps(i);
            return selectionKeyImpl;
        } catch (ClosedSelectorException e) {
            this.keys.remove(selectionKeyImpl);
            selectionKeyImpl.cancel();
            throw e;
        }
    }

    protected void implRegister(SelectionKeyImpl selectionKeyImpl) {
        ensureOpen();
    }

    protected final void processDeregisterQueue() throws IOException {
        Set cancelledKeys = cancelledKeys();
        synchronized (cancelledKeys) {
            if (!cancelledKeys.isEmpty()) {
                Iterator it = cancelledKeys.iterator();
                while (it.hasNext()) {
                    SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl) it.next();
                    it.remove();
                    implDereg(selectionKeyImpl);
                    this.selectedKeys.remove(selectionKeyImpl);
                    this.keys.remove(selectionKeyImpl);
                    deregister(selectionKeyImpl);
                    SelChImpl channel = selectionKeyImpl.channel();
                    if (!channel.isOpen() && !channel.isRegistered()) {
                        channel.kill();
                    }
                }
            }
        }
    }

    protected final int processReadyEvents(int i, SelectionKeyImpl selectionKeyImpl, Consumer consumer) {
        if (consumer != null) {
            selectionKeyImpl.translateAndSetReadyOps(i);
            if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) == 0) {
                return 0;
            }
            consumer.accept(selectionKeyImpl);
            ensureOpen();
            return 1;
        }
        if (this.selectedKeys.contains(selectionKeyImpl)) {
            return selectionKeyImpl.translateAndUpdateReadyOps(i) ? 1 : 0;
        }
        selectionKeyImpl.translateAndSetReadyOps(i);
        if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) == 0) {
            return 0;
        }
        this.selectedKeys.add(selectionKeyImpl);
        return 1;
    }
}
