package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AsynchronousFileChannelImpl extends AsynchronousFileChannel {
    protected final ReadWriteLock closeLock = new ReentrantReadWriteLock();
    protected volatile boolean closed;
    protected final ExecutorService executor;
    protected final FileDescriptor fdObj;
    private volatile FileLockTable fileLockTable;
    protected final boolean reading;
    protected final boolean writing;

    abstract Future implLock(long j, long j2, boolean z, Object obj, CompletionHandler completionHandler);

    abstract Future implRead(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler);

    protected abstract void implRelease(FileLockImpl fileLockImpl) throws IOException;

    abstract Future implWrite(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler);

    protected AsynchronousFileChannelImpl(FileDescriptor fileDescriptor, boolean z, boolean z2, ExecutorService executorService) {
        this.fdObj = fileDescriptor;
        this.reading = z;
        this.writing = z2;
        this.executor = executorService;
    }

    final ExecutorService executor() {
        return this.executor;
    }

    public final boolean isOpen() {
        return !this.closed;
    }

    protected final void begin() throws IOException {
        this.closeLock.readLock().lock();
        if (this.closed) {
            throw new ClosedChannelException();
        }
    }

    protected final void end() {
        this.closeLock.readLock().unlock();
    }

    protected final void end(boolean z) throws IOException {
        end();
        if (!z && !isOpen()) {
            throw new AsynchronousCloseException();
        }
    }

    public final Future lock(long j, long j2, boolean z) {
        return implLock(j, j2, z, null, null);
    }

    public final void lock(long j, long j2, boolean z, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        implLock(j, j2, z, obj, completionHandler);
    }

    final void ensureFileLockTableInitialized() throws IOException {
        if (this.fileLockTable == null) {
            synchronized (this) {
                if (this.fileLockTable == null) {
                    this.fileLockTable = new FileLockTable(this, this.fdObj);
                }
            }
        }
    }

    final void invalidateAllLocks() throws IOException {
        if (this.fileLockTable != null) {
            for (FileLock fileLock : this.fileLockTable.removeAll()) {
                synchronized (fileLock) {
                    if (fileLock.isValid()) {
                        FileLockImpl fileLockImpl = (FileLockImpl) fileLock;
                        implRelease(fileLockImpl);
                        fileLockImpl.invalidate();
                    }
                }
            }
        }
    }

    protected final FileLockImpl addToFileLockTable(long j, long j2, boolean z) {
        try {
            this.closeLock.readLock().lock();
            try {
                if (!this.closed) {
                    try {
                        ensureFileLockTableInitialized();
                        FileLockImpl fileLockImpl = new FileLockImpl(this, j, j2, z);
                        this.fileLockTable.add(fileLockImpl);
                        end();
                        return fileLockImpl;
                    } catch (IOException e) {
                        throw new AssertionError(e);
                    }
                }
                end();
                return null;
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                end();
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    protected final void removeFromFileLockTable(FileLockImpl fileLockImpl) {
        this.fileLockTable.remove(fileLockImpl);
    }

    final void release(FileLockImpl fileLockImpl) throws IOException {
        try {
            begin();
            implRelease(fileLockImpl);
            removeFromFileLockTable(fileLockImpl);
        } finally {
            end();
        }
    }

    public final Future read(ByteBuffer byteBuffer, long j) {
        return implRead(byteBuffer, j, null, null);
    }

    public final void read(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        implRead(byteBuffer, j, obj, completionHandler);
    }

    public final Future write(ByteBuffer byteBuffer, long j) {
        return implWrite(byteBuffer, j, null, null);
    }

    public final void write(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler) {
        if (completionHandler == null) {
            throw new NullPointerException("'handler' is null");
        }
        implWrite(byteBuffer, j, obj, completionHandler);
    }
}
