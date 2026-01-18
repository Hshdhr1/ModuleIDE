package sun.nio.ch;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class CompletedFuture implements Future {
    private final Throwable exc;
    private final Object result;

    public boolean cancel(boolean z) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    private CompletedFuture(Object obj, Throwable th) {
        this.result = obj;
        this.exc = th;
    }

    static CompletedFuture withResult(Object obj) {
        return new CompletedFuture(obj, null);
    }

    static CompletedFuture withFailure(Throwable th) {
        if (!(th instanceof IOException) && !(th instanceof SecurityException)) {
            th = new IOException(th);
        }
        return new CompletedFuture(null, th);
    }

    static CompletedFuture withResult(Object obj, Throwable th) {
        if (th == null) {
            return withResult(obj);
        }
        return withFailure(th);
    }

    public Object get() throws ExecutionException {
        if (this.exc != null) {
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }

    public Object get(long j, TimeUnit timeUnit) throws ExecutionException {
        timeUnit.getClass();
        if (this.exc != null) {
            throw new ExecutionException(this.exc);
        }
        return this.result;
    }
}
