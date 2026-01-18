package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class PollSelectorImpl extends SelectorImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final short EVENT_OFFSET = 4;
    private static final short FD_OFFSET = 0;
    private static final int INITIAL_CAPACITY = 16;
    private static final short REVENT_OFFSET = 6;
    private static final short SIZE_POLLFD = 8;
    private final int fd0;
    private final int fd1;
    private final Object interruptLock;
    private boolean interruptTriggered;
    private AllocatedNativeObject pollArray;
    private int pollArrayCapacity;
    private int pollArraySize;
    private final List pollKeys;
    private final Deque updateKeys;
    private final Object updateLock;

    private static native int poll(long j, int i, int i2);

    PollSelectorImpl(SelectorProvider selectorProvider) throws IOException {
        super(selectorProvider);
        this.pollArrayCapacity = 16;
        this.pollKeys = new ArrayList();
        this.updateLock = new Object();
        this.updateKeys = new ArrayDeque();
        this.interruptLock = new Object();
        this.pollArray = new AllocatedNativeObject(this.pollArrayCapacity * 8, false);
        try {
            long makePipe = IOUtil.makePipe(false);
            int i = (int) (makePipe >>> 32);
            this.fd0 = i;
            this.fd1 = (int) makePipe;
            synchronized (this) {
                setFirst(i, Net.POLLIN);
            }
        } catch (IOException e) {
            this.pollArray.free();
            throw e;
        }
    }

    private void ensureOpen() {
        if (!isOpen()) {
            throw new ClosedSelectorException();
        }
    }

    protected int doSelect(Consumer consumer, long j) throws IOException {
        int poll;
        int min = (int) Math.min(j, 2147483647L);
        boolean z = min != 0;
        boolean z2 = min > 0;
        processUpdateQueue();
        processDeregisterQueue();
        try {
            begin(z);
            do {
                long nanoTime = z2 ? System.nanoTime() : 0L;
                poll = poll(this.pollArray.address(), this.pollArraySize, min);
                if (poll == -3 && z2) {
                    min = (int) (min - TimeUnit.MILLISECONDS.convert(System.nanoTime() - nanoTime, TimeUnit.NANOSECONDS));
                    if (min <= 0) {
                        poll = 0;
                    }
                }
            } while (poll == -3);
            end(z);
            processDeregisterQueue();
            return processEvents(consumer);
        } catch (Throwable th) {
            end(z);
            throw th;
        }
    }

    private void processUpdateQueue() {
        synchronized (this.updateLock) {
            while (true) {
                SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl) this.updateKeys.pollFirst();
                if (selectionKeyImpl != null) {
                    int translateInterestOps = selectionKeyImpl.translateInterestOps();
                    if (selectionKeyImpl.isValid()) {
                        if (selectionKeyImpl.getIndex() > 0) {
                            if (translateInterestOps == 0) {
                                remove(selectionKeyImpl);
                            } else {
                                update(selectionKeyImpl, translateInterestOps);
                            }
                        } else if (translateInterestOps != 0) {
                            add(selectionKeyImpl, translateInterestOps);
                        }
                    }
                }
            }
        }
    }

    private int processEvents(Consumer consumer) throws IOException {
        int i = 0;
        for (int i2 = 1; i2 < this.pollArraySize; i2++) {
            int reventOps = getReventOps(i2);
            if (reventOps != 0) {
                SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl) this.pollKeys.get(i2);
                if (selectionKeyImpl.isValid()) {
                    i += processReadyEvents(reventOps, selectionKeyImpl, consumer);
                }
            }
        }
        if (getReventOps(0) != 0) {
            clearInterrupt();
        }
        return i;
    }

    protected void implClose() throws IOException {
        synchronized (this.interruptLock) {
            this.interruptTriggered = true;
        }
        this.pollArray.free();
        FileDispatcherImpl.closeIntFD(this.fd0);
        FileDispatcherImpl.closeIntFD(this.fd1);
    }

    protected void implRegister(SelectionKeyImpl selectionKeyImpl) {
        ensureOpen();
    }

    protected void implDereg(SelectionKeyImpl selectionKeyImpl) throws IOException {
        if (selectionKeyImpl.getIndex() > 0) {
            remove(selectionKeyImpl);
        }
    }

    public void setEventOps(SelectionKeyImpl selectionKeyImpl) {
        ensureOpen();
        synchronized (this.updateLock) {
            this.updateKeys.addLast(selectionKeyImpl);
        }
    }

    public Selector wakeup() {
        synchronized (this.interruptLock) {
            if (!this.interruptTriggered) {
                try {
                    IOUtil.write1(this.fd1, (byte) 0);
                    this.interruptTriggered = true;
                } catch (IOException e) {
                    throw new InternalError(e);
                }
            }
        }
        return this;
    }

    private void clearInterrupt() throws IOException {
        synchronized (this.interruptLock) {
            IOUtil.drain(this.fd0);
            this.interruptTriggered = false;
        }
    }

    private void setFirst(int i, int i2) {
        putDescriptor(0, i);
        putEventOps(0, i2);
        this.pollArraySize = 1;
        this.pollKeys.add(null);
    }

    private void add(SelectionKeyImpl selectionKeyImpl, int i) {
        expandIfNeeded();
        int i2 = this.pollArraySize;
        putDescriptor(i2, selectionKeyImpl.getFDVal());
        putEventOps(i2, i);
        putReventOps(i2, 0);
        selectionKeyImpl.setIndex(i2);
        this.pollArraySize++;
        this.pollKeys.add(selectionKeyImpl);
    }

    private void update(SelectionKeyImpl selectionKeyImpl, int i) {
        putEventOps(selectionKeyImpl.getIndex(), i);
    }

    private void remove(SelectionKeyImpl selectionKeyImpl) {
        int index = selectionKeyImpl.getIndex();
        int i = this.pollArraySize - 1;
        if (i != index) {
            SelectionKeyImpl selectionKeyImpl2 = (SelectionKeyImpl) this.pollKeys.get(i);
            int descriptor = getDescriptor(i);
            int eventOps = getEventOps(i);
            int reventOps = getReventOps(i);
            putDescriptor(index, descriptor);
            putEventOps(index, eventOps);
            putReventOps(index, reventOps);
            this.pollKeys.set(index, selectionKeyImpl2);
            selectionKeyImpl2.setIndex(index);
        }
        this.pollKeys.remove(i);
        this.pollArraySize--;
        selectionKeyImpl.setIndex(0);
    }

    private void expandIfNeeded() {
        int i = this.pollArraySize;
        int i2 = this.pollArrayCapacity;
        if (i == i2) {
            int i3 = i2 * 8;
            int i4 = i2 + 16;
            AllocatedNativeObject allocatedNativeObject = new AllocatedNativeObject(i4 * 8, false);
            Unsafe.getUnsafe().copyMemory(this.pollArray.address(), allocatedNativeObject.address(), i3);
            this.pollArray.free();
            this.pollArray = allocatedNativeObject;
            this.pollArrayCapacity = i4;
        }
    }

    private void putDescriptor(int i, int i2) {
        this.pollArray.putInt(i * 8, i2);
    }

    private int getDescriptor(int i) {
        return this.pollArray.getInt(i * 8);
    }

    private void putEventOps(int i, int i2) {
        this.pollArray.putShort((i * 8) + 4, (short) i2);
    }

    private int getEventOps(int i) {
        return this.pollArray.getShort((i * 8) + 4);
    }

    private void putReventOps(int i, int i2) {
        this.pollArray.putShort((i * 8) + 6, (short) i2);
    }

    private int getReventOps(int i) {
        return this.pollArray.getShort((i * 8) + 6);
    }

    static {
        IOUtil.load();
    }
}
