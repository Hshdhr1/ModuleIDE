package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Pipe;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SourceChannelImpl extends Pipe.SourceChannel implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CLOSING = 1;
    private static final int ST_INUSE = 0;
    private static final int ST_KILLED = 3;
    private static final int ST_KILLPENDING = 2;
    private static final NativeDispatcher nd = new FileDispatcherImpl();
    private final FileDescriptor fd;
    private final int fdVal;
    private final ReentrantLock readLock;
    private int state;
    private final Object stateLock;
    private long thread;

    public FileDescriptor getFD() {
        return this.fd;
    }

    public int getFDVal() {
        return this.fdVal;
    }

    SourceChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor) {
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.stateLock = new Object();
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
    }

    protected void implCloseSelectableChannel() throws IOException {
        boolean isBlocking;
        synchronized (this.stateLock) {
            this.state = 1;
            isBlocking = isBlocking();
        }
        boolean z = false;
        if (isBlocking) {
            synchronized (this.stateLock) {
                long j = this.thread;
                if (j != 0) {
                    nd.preClose(this.fd);
                    NativeThread.signal(j);
                    while (this.thread != 0) {
                        try {
                            this.stateLock.wait();
                        } catch (InterruptedException unused) {
                            z = true;
                        }
                    }
                }
            }
        } else {
            this.readLock.lock();
            this.readLock.unlock();
        }
        synchronized (this.stateLock) {
            this.state = 2;
        }
        if (!isRegistered()) {
            kill();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public void kill() throws IOException {
        synchronized (this.stateLock) {
            if (this.state == 2) {
                this.state = 3;
                nd.close(this.fd);
            }
        }
    }

    protected void implConfigureBlocking(boolean z) throws IOException {
        this.readLock.lock();
        try {
            synchronized (this.stateLock) {
                IOUtil.configureBlocking(this.fd, z);
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
        int nioInterestOps = selectionKeyImpl.nioInterestOps();
        int nioReadyOps = selectionKeyImpl.nioReadyOps();
        if ((Net.POLLNVAL & i) != 0) {
            throw new Error("POLLNVAL detected");
        }
        if (((Net.POLLERR | Net.POLLHUP) & i) != 0) {
            selectionKeyImpl.nioReadyOps(nioInterestOps);
            return ((nioReadyOps ^ (-1)) & nioInterestOps) != 0;
        }
        if ((i & Net.POLLIN) != 0 && (nioInterestOps & 1) != 0) {
            i2 |= 1;
        }
        selectionKeyImpl.nioReadyOps(i2);
        return ((nioReadyOps ^ (-1)) & i2) != 0;
    }

    public boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, selectionKeyImpl.nioReadyOps(), selectionKeyImpl);
    }

    public boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, 0, selectionKeyImpl);
    }

    public int translateInterestOps(int i) {
        if (i == 1) {
            return Net.POLLIN;
        }
        return 0;
    }

    private void beginRead(boolean z) throws ClosedChannelException {
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            if (!isOpen()) {
                throw new ClosedChannelException();
            }
            if (z) {
                this.thread = NativeThread.current();
            }
        }
    }

    private void endRead(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.thread = 0L;
                if (this.state == 1) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        int i;
        byteBuffer.getClass();
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking);
                i = 0;
                do {
                    try {
                        i = IOUtil.read(this.fd, byteBuffer, -1L, nd);
                        if (i != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endRead(isBlocking, i > 0);
                        throw th;
                    }
                } while (isOpen());
                endRead(isBlocking, i > 0);
                return IOStatus.normalize(i);
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        SourceChannelImpl$$ExternalSyntheticBackport0.m(i, i2, byteBufferArr.length);
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking);
                j = 0;
                do {
                    try {
                        j = IOUtil.read(this.fd, byteBufferArr, i, i2, nd);
                        if (j != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endRead(isBlocking, j > 0);
                        throw th;
                    }
                } while (isOpen());
                endRead(isBlocking, j > 0);
                return IOStatus.normalize(j);
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public long read(ByteBuffer[] byteBufferArr) throws IOException {
        return read(byteBufferArr, 0, byteBufferArr.length);
    }
}
