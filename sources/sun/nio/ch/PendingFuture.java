package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class PendingFuture implements Future {
    private final Object attachment;
    private final AsynchronousChannel channel;
    private volatile Object context;
    private volatile Throwable exc;
    private final CompletionHandler handler;
    private volatile boolean haveResult;
    private CountDownLatch latch;
    private volatile Object result;
    private Future timeoutTask;

    PendingFuture(AsynchronousChannel asynchronousChannel, CompletionHandler completionHandler, Object obj, Object obj2) {
        this.channel = asynchronousChannel;
        this.handler = completionHandler;
        this.attachment = obj;
        this.context = obj2;
    }

    PendingFuture(AsynchronousChannel asynchronousChannel, CompletionHandler completionHandler, Object obj) {
        this.channel = asynchronousChannel;
        this.handler = completionHandler;
        this.attachment = obj;
    }

    PendingFuture(AsynchronousChannel asynchronousChannel) {
        this(asynchronousChannel, null, null);
    }

    PendingFuture(AsynchronousChannel asynchronousChannel, Object obj) {
        this(asynchronousChannel, null, null, obj);
    }

    AsynchronousChannel channel() {
        return this.channel;
    }

    CompletionHandler handler() {
        return this.handler;
    }

    Object attachment() {
        return this.attachment;
    }

    void setContext(Object obj) {
        this.context = obj;
    }

    Object getContext() {
        return this.context;
    }

    void setTimeoutTask(Future future) {
        synchronized (this) {
            if (this.haveResult) {
                future.cancel(false);
            } else {
                this.timeoutTask = future;
            }
        }
    }

    private boolean prepareForWait() {
        synchronized (this) {
            if (this.haveResult) {
                return false;
            }
            if (this.latch == null) {
                this.latch = new CountDownLatch(1);
            }
            return true;
        }
    }

    void setResult(Object obj) {
        synchronized (this) {
            if (this.haveResult) {
                return;
            }
            this.result = obj;
            this.haveResult = true;
            Future future = this.timeoutTask;
            if (future != null) {
                future.cancel(false);
            }
            CountDownLatch countDownLatch = this.latch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    void setFailure(Throwable th) {
        if (!(th instanceof IOException) && !(th instanceof SecurityException)) {
            th = new IOException(th);
        }
        synchronized (this) {
            if (this.haveResult) {
                return;
            }
            this.exc = th;
            this.haveResult = true;
            Future future = this.timeoutTask;
            if (future != null) {
                future.cancel(false);
            }
            CountDownLatch countDownLatch = this.latch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    void setResult(Object obj, Throwable th) {
        if (th == null) {
            setResult(obj);
        } else {
            setFailure(th);
        }
    }

    public Object get() throws ExecutionException, InterruptedException {
        if (!this.haveResult && prepareForWait()) {
            this.latch.await();
        }
        if (this.exc != null) {
            if (this.exc instanceof CancellationException) {
                throw new CancellationException();
            }
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }

    public Object get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!this.haveResult && prepareForWait() && !this.latch.await(j, timeUnit)) {
            throw new TimeoutException();
        }
        if (this.exc != null) {
            if (this.exc instanceof CancellationException) {
                throw new CancellationException();
            }
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }

    Throwable exception() {
        if (this.exc instanceof CancellationException) {
            return null;
        }
        return this.exc;
    }

    Object value() {
        return this.result;
    }

    public boolean isCancelled() {
        return this.exc instanceof CancellationException;
    }

    public boolean isDone() {
        return this.haveResult;
    }

    public boolean cancel(boolean z) {
        synchronized (this) {
            if (this.haveResult) {
                return false;
            }
            if (channel() instanceof Cancellable) {
                ((Cancellable) channel()).onCancel(this);
            }
            this.exc = new CancellationException();
            this.haveResult = true;
            Future future = this.timeoutTask;
            if (future != null) {
                future.cancel(false);
            }
            if (z) {
                try {
                    channel().close();
                } catch (IOException unused) {
                }
            }
            CountDownLatch countDownLatch = this.latch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            return true;
        }
    }
}
