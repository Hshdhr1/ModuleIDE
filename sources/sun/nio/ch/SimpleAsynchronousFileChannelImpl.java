package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class SimpleAsynchronousFileChannelImpl extends AsynchronousFileChannelImpl {
    private static final FileDispatcher nd = new FileDispatcherImpl();
    private final NativeThreadSet threads;

    static /* bridge */ /* synthetic */ NativeThreadSet -$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl simpleAsynchronousFileChannelImpl) {
        return simpleAsynchronousFileChannelImpl.threads;
    }

    static /* bridge */ /* synthetic */ FileDispatcher -$$Nest$sfgetnd() {
        return nd;
    }

    private static class DefaultExecutorHolder {
        static final ExecutorService defaultExecutor = ThreadPool.createDefault().executor();

        private DefaultExecutorHolder() {
        }
    }

    SimpleAsynchronousFileChannelImpl(FileDescriptor fileDescriptor, boolean z, boolean z2, ExecutorService executorService) {
        super(fileDescriptor, z, z2, executorService);
        this.threads = new NativeThreadSet(2);
    }

    public static AsynchronousFileChannel open(FileDescriptor fileDescriptor, boolean z, boolean z2, ThreadPool threadPool) {
        return new SimpleAsynchronousFileChannelImpl(fileDescriptor, z, z2, threadPool == null ? DefaultExecutorHolder.defaultExecutor : threadPool.executor());
    }

    public void close() throws IOException {
        synchronized (this.fdObj) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            invalidateAllLocks();
            this.threads.signalAndWait();
            this.closeLock.writeLock().lock();
            this.closeLock.writeLock().unlock();
            nd.close(this.fdObj);
        }
    }

    public long size() throws IOException {
        long j;
        int add = this.threads.add();
        try {
            try {
                begin();
                j = 0;
                do {
                    try {
                        j = nd.size(this.fdObj);
                        if (j != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        end(j >= 0);
                        throw th;
                    }
                } while (isOpen());
                end(j >= 0);
                return j;
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
        } finally {
            this.threads.remove(add);
        }
    }

    public AsynchronousFileChannel truncate(long j) throws IOException {
        long j2;
        if (j < 0) {
            throw new IllegalArgumentException("Negative size");
        }
        if (!this.writing) {
            throw new NonWritableChannelException();
        }
        int add = this.threads.add();
        try {
            try {
                begin();
                j2 = 0;
                do {
                    try {
                        j2 = nd.size(this.fdObj);
                        if (j2 != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        end(j2 > 0);
                        throw th;
                    }
                } while (isOpen());
                if (j < j2 && isOpen()) {
                    do {
                        j2 = nd.truncate(this.fdObj, j);
                        if (j2 != -3) {
                            break;
                        }
                    } while (isOpen());
                }
                end(j2 > 0);
                return this;
            } finally {
                this.threads.remove(add);
            }
        } catch (Throwable th2) {
            th = th2;
            j2 = 0;
        }
    }

    public void force(boolean z) throws IOException {
        int i;
        int add = this.threads.add();
        try {
            try {
                begin();
                i = 0;
                do {
                    try {
                        i = nd.force(this.fdObj, z);
                        if (i != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        end(i >= 0);
                        throw th;
                    }
                } while (isOpen());
                end(i >= 0);
            } finally {
                this.threads.remove(add);
            }
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
    }

    Future implLock(long j, long j2, boolean z, Object obj, CompletionHandler completionHandler) {
        if (z && !this.reading) {
            throw new NonReadableChannelException();
        }
        if (!z && !this.writing) {
            throw new NonWritableChannelException();
        }
        FileLockImpl addToFileLockTable = addToFileLockTable(j, j2, z);
        if (addToFileLockTable == null) {
            ClosedChannelException closedChannelException = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure(closedChannelException);
            }
            Invoker.invokeIndirectly(completionHandler, obj, (Object) null, (Throwable) closedChannelException, (Executor) this.executor);
            return null;
        }
        PendingFuture pendingFuture = completionHandler == null ? new PendingFuture(this) : null;
        try {
            this.executor.execute(new 1(j, j2, z, addToFileLockTable, completionHandler, pendingFuture, obj));
            return pendingFuture;
        } catch (Throwable th) {
            removeFromFileLockTable(addToFileLockTable);
            throw th;
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ Object val$attachment;
        final /* synthetic */ FileLockImpl val$fli;
        final /* synthetic */ CompletionHandler val$handler;
        final /* synthetic */ long val$position;
        final /* synthetic */ PendingFuture val$result;
        final /* synthetic */ boolean val$shared;
        final /* synthetic */ long val$size;

        1(long j, long j2, boolean z, FileLockImpl fileLockImpl, CompletionHandler completionHandler, PendingFuture pendingFuture, Object obj) {
            this.val$position = j;
            this.val$size = j2;
            this.val$shared = z;
            this.val$fli = fileLockImpl;
            this.val$handler = completionHandler;
            this.val$result = pendingFuture;
            this.val$attachment = obj;
        }

        public void run() {
            int lock;
            int add = SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).add();
            try {
                try {
                    try {
                        SimpleAsynchronousFileChannelImpl.this.begin();
                        do {
                            lock = SimpleAsynchronousFileChannelImpl.-$$Nest$sfgetnd().lock(SimpleAsynchronousFileChannelImpl.this.fdObj, true, this.val$position, this.val$size, this.val$shared);
                            if (lock != 2) {
                                break;
                            }
                        } while (SimpleAsynchronousFileChannelImpl.this.isOpen());
                    } catch (IOException e) {
                        e = e;
                        SimpleAsynchronousFileChannelImpl.this.removeFromFileLockTable(this.val$fli);
                        if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                            e = new AsynchronousCloseException();
                        }
                    }
                    if (lock != 0 || !SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        throw new AsynchronousCloseException();
                    }
                    e = null;
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    CompletionHandler completionHandler = this.val$handler;
                    if (completionHandler == null) {
                        this.val$result.setResult(this.val$fli, e);
                    } else {
                        Invoker.invokeUnchecked(completionHandler, this.val$attachment, this.val$fli, e);
                    }
                } catch (Throwable th) {
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    throw th;
                }
            } finally {
                SimpleAsynchronousFileChannelImpl.this.end();
            }
        }
    }

    public FileLock tryLock(long j, long j2, boolean z) throws IOException {
        int lock;
        if (z && !this.reading) {
            throw new NonReadableChannelException();
        }
        if (!z && !this.writing) {
            throw new NonWritableChannelException();
        }
        long j3 = j;
        long j4 = j2;
        boolean z2 = z;
        FileLockImpl addToFileLockTable = addToFileLockTable(j3, j4, z2);
        if (addToFileLockTable == null) {
            throw new ClosedChannelException();
        }
        int add = this.threads.add();
        try {
            begin();
            do {
                long j5 = j4;
                boolean z3 = z2;
                long j6 = j3;
                lock = nd.lock(this.fdObj, false, j6, j5, z3);
                j3 = j6;
                j4 = j5;
                z2 = z3;
                if (lock != 2) {
                    break;
                }
            } while (isOpen());
            if (lock == 0 && isOpen()) {
                end();
                this.threads.remove(add);
                return addToFileLockTable;
            }
            if (lock != -1) {
                if (lock == 2) {
                    throw new AsynchronousCloseException();
                }
                throw new AssertionError();
            }
            removeFromFileLockTable(addToFileLockTable);
            end();
            this.threads.remove(add);
            return null;
        } catch (Throwable th) {
            removeFromFileLockTable(addToFileLockTable);
            end();
            this.threads.remove(add);
            throw th;
        }
    }

    protected void implRelease(FileLockImpl fileLockImpl) throws IOException {
        nd.release(this.fdObj, fileLockImpl.position(), fileLockImpl.size());
    }

    Future implRead(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler) {
        if (j < 0) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.reading) {
            throw new NonReadableChannelException();
        }
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("Read-only buffer");
        }
        if (!isOpen() || byteBuffer.remaining() == 0) {
            Throwable closedChannelException = isOpen() ? null : new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withResult(0, closedChannelException);
            }
            Invoker.invokeIndirectly(completionHandler, obj, (Object) 0, closedChannelException, (Executor) this.executor);
            return null;
        }
        PendingFuture pendingFuture = completionHandler == null ? new PendingFuture(this) : null;
        this.executor.execute(new 2(byteBuffer, j, completionHandler, pendingFuture, obj));
        return pendingFuture;
    }

    class 2 implements Runnable {
        final /* synthetic */ Object val$attachment;
        final /* synthetic */ ByteBuffer val$dst;
        final /* synthetic */ CompletionHandler val$handler;
        final /* synthetic */ long val$position;
        final /* synthetic */ PendingFuture val$result;

        2(ByteBuffer byteBuffer, long j, CompletionHandler completionHandler, PendingFuture pendingFuture, Object obj) {
            this.val$dst = byteBuffer;
            this.val$position = j;
            this.val$handler = completionHandler;
            this.val$result = pendingFuture;
            this.val$attachment = obj;
        }

        public void run() {
            Throwable th;
            int add = SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).add();
            int i = 0;
            try {
                try {
                    SimpleAsynchronousFileChannelImpl.this.begin();
                    do {
                        i = IOUtil.read(SimpleAsynchronousFileChannelImpl.this.fdObj, this.val$dst, this.val$position, SimpleAsynchronousFileChannelImpl.-$$Nest$sfgetnd());
                        if (i != -3) {
                            break;
                        }
                    } while (SimpleAsynchronousFileChannelImpl.this.isOpen());
                    if (i < 0 && !SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        throw new AsynchronousCloseException();
                    }
                    SimpleAsynchronousFileChannelImpl.this.end();
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    th = null;
                } catch (IOException e) {
                    e = e;
                    if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        e = new AsynchronousCloseException();
                    }
                    SimpleAsynchronousFileChannelImpl.this.end();
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    th = e;
                }
                CompletionHandler completionHandler = this.val$handler;
                if (completionHandler == null) {
                    this.val$result.setResult(Integer.valueOf(i), th);
                } else {
                    Invoker.invokeUnchecked(completionHandler, this.val$attachment, Integer.valueOf(i), th);
                }
            } catch (Throwable th2) {
                SimpleAsynchronousFileChannelImpl.this.end();
                SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                throw th2;
            }
        }
    }

    Future implWrite(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler) {
        if (j < 0) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.writing) {
            throw new NonWritableChannelException();
        }
        if (!isOpen() || byteBuffer.remaining() == 0) {
            Throwable closedChannelException = isOpen() ? null : new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withResult(0, closedChannelException);
            }
            Invoker.invokeIndirectly(completionHandler, obj, (Object) 0, closedChannelException, (Executor) this.executor);
            return null;
        }
        PendingFuture pendingFuture = completionHandler == null ? new PendingFuture(this) : null;
        this.executor.execute(new 3(byteBuffer, j, completionHandler, pendingFuture, obj));
        return pendingFuture;
    }

    class 3 implements Runnable {
        final /* synthetic */ Object val$attachment;
        final /* synthetic */ CompletionHandler val$handler;
        final /* synthetic */ long val$position;
        final /* synthetic */ PendingFuture val$result;
        final /* synthetic */ ByteBuffer val$src;

        3(ByteBuffer byteBuffer, long j, CompletionHandler completionHandler, PendingFuture pendingFuture, Object obj) {
            this.val$src = byteBuffer;
            this.val$position = j;
            this.val$handler = completionHandler;
            this.val$result = pendingFuture;
            this.val$attachment = obj;
        }

        public void run() {
            Throwable th;
            int add = SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).add();
            int i = 0;
            try {
                try {
                    SimpleAsynchronousFileChannelImpl.this.begin();
                    do {
                        i = IOUtil.write(SimpleAsynchronousFileChannelImpl.this.fdObj, this.val$src, this.val$position, SimpleAsynchronousFileChannelImpl.-$$Nest$sfgetnd());
                        if (i != -3) {
                            break;
                        }
                    } while (SimpleAsynchronousFileChannelImpl.this.isOpen());
                    if (i < 0 && !SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        throw new AsynchronousCloseException();
                    }
                    SimpleAsynchronousFileChannelImpl.this.end();
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    th = null;
                } catch (IOException e) {
                    e = e;
                    if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        e = new AsynchronousCloseException();
                    }
                    SimpleAsynchronousFileChannelImpl.this.end();
                    SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                    th = e;
                }
                CompletionHandler completionHandler = this.val$handler;
                if (completionHandler == null) {
                    this.val$result.setResult(Integer.valueOf(i), th);
                } else {
                    Invoker.invokeUnchecked(completionHandler, this.val$attachment, Integer.valueOf(i), th);
                }
            } catch (Throwable th2) {
                SimpleAsynchronousFileChannelImpl.this.end();
                SimpleAsynchronousFileChannelImpl.-$$Nest$fgetthreads(SimpleAsynchronousFileChannelImpl.this).remove(add);
                throw th2;
            }
        }
    }
}
