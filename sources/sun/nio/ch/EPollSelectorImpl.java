package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class EPollSelectorImpl extends SelectorImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int NUM_EPOLLEVENTS = Math.min(IOUtil.fdLimit(), 1024);
    private final int epfd;
    private final int fd0;
    private final int fd1;
    private final Map fdToKey;
    private final Object interruptLock;
    private boolean interruptTriggered;
    private final long pollArrayAddress;
    private final Deque updateKeys;
    private final Object updateLock;

    EPollSelectorImpl(SelectorProvider selectorProvider) throws IOException {
        super(selectorProvider);
        this.fdToKey = new HashMap();
        this.updateLock = new Object();
        this.updateKeys = new ArrayDeque();
        this.interruptLock = new Object();
        int create = EPoll.create();
        this.epfd = create;
        this.pollArrayAddress = EPoll.allocatePollArray(NUM_EPOLLEVENTS);
        try {
            long makePipe = IOUtil.makePipe(false);
            int i = (int) (makePipe >>> 32);
            this.fd0 = i;
            this.fd1 = (int) makePipe;
            EPoll.ctl(create, 1, i, 1);
        } catch (IOException e) {
            EPoll.freePollArray(this.pollArrayAddress);
            FileDispatcherImpl.closeIntFD(this.epfd);
            throw e;
        }
    }

    private void ensureOpen() {
        if (!isOpen()) {
            throw new ClosedSelectorException();
        }
    }

    protected int doSelect(Consumer consumer, long j) throws IOException {
        int wait;
        int min = (int) Math.min(j, 2147483647L);
        boolean z = min != 0;
        boolean z2 = min > 0;
        processUpdateQueue();
        processDeregisterQueue();
        try {
            begin(z);
            do {
                long nanoTime = z2 ? System.nanoTime() : 0L;
                wait = EPoll.wait(this.epfd, this.pollArrayAddress, NUM_EPOLLEVENTS, min);
                if (wait == -3 && z2) {
                    min = (int) (min - TimeUnit.MILLISECONDS.convert(System.nanoTime() - nanoTime, TimeUnit.NANOSECONDS));
                    if (min <= 0) {
                        wait = 0;
                    }
                }
            } while (wait == -3);
            end(z);
            processDeregisterQueue();
            return processEvents(wait, consumer);
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
                    if (selectionKeyImpl.isValid()) {
                        int fDVal = selectionKeyImpl.getFDVal();
                        int translateInterestOps = selectionKeyImpl.translateInterestOps();
                        int registeredEvents = selectionKeyImpl.registeredEvents();
                        if (translateInterestOps != registeredEvents) {
                            if (translateInterestOps == 0) {
                                EPoll.ctl(this.epfd, 2, fDVal, 0);
                            } else if (registeredEvents == 0) {
                                EPoll.ctl(this.epfd, 1, fDVal, translateInterestOps);
                            } else {
                                EPoll.ctl(this.epfd, 3, fDVal, translateInterestOps);
                            }
                            selectionKeyImpl.registeredEvents(translateInterestOps);
                        }
                    }
                }
            }
        }
    }

    private int processEvents(int i, Consumer consumer) throws IOException {
        boolean z = false;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            long event = EPoll.getEvent(this.pollArrayAddress, i3);
            int descriptor = EPoll.getDescriptor(event);
            if (descriptor == this.fd0) {
                z = true;
            } else {
                SelectionKeyImpl selectionKeyImpl = (SelectionKeyImpl) this.fdToKey.get(Integer.valueOf(descriptor));
                if (selectionKeyImpl != null) {
                    i2 += processReadyEvents(EPoll.getEvents(event), selectionKeyImpl, consumer);
                }
            }
        }
        if (z) {
            clearInterrupt();
        }
        return i2;
    }

    protected void implClose() throws IOException {
        synchronized (this.interruptLock) {
            this.interruptTriggered = true;
        }
        FileDispatcherImpl.closeIntFD(this.epfd);
        EPoll.freePollArray(this.pollArrayAddress);
        FileDispatcherImpl.closeIntFD(this.fd0);
        FileDispatcherImpl.closeIntFD(this.fd1);
    }

    protected void implDereg(SelectionKeyImpl selectionKeyImpl) throws IOException {
        int fDVal = selectionKeyImpl.getFDVal();
        if (this.fdToKey.remove(Integer.valueOf(fDVal)) == null || selectionKeyImpl.registeredEvents() == 0) {
            return;
        }
        EPoll.ctl(this.epfd, 2, fDVal, 0);
        selectionKeyImpl.registeredEvents(0);
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
}
