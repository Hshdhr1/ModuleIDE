package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes8.dex */
public final class CallbackToFutureAdapter {

    public interface Resolver {
        Object attachCompleter(Completer completer) throws Exception;
    }

    private CallbackToFutureAdapter() {
    }

    public static ListenableFuture getFuture(Resolver resolver) {
        Completer completer = new Completer();
        SafeFuture safeFuture = new SafeFuture(completer);
        completer.future = safeFuture;
        completer.tag = resolver.getClass();
        try {
            Object tag = resolver.attachCompleter(completer);
            if (tag != null) {
                completer.tag = tag;
            }
        } catch (Exception e) {
            safeFuture.setException(e);
        }
        return safeFuture;
    }

    private static final class SafeFuture implements ListenableFuture {
        final WeakReference completerWeakReference;
        private final AbstractResolvableFuture delegate = new 1();

        SafeFuture(Completer completer) {
            this.completerWeakReference = new WeakReference(completer);
        }

        class 1 extends AbstractResolvableFuture {
            1() {
            }

            protected String pendingToString() {
                Completer completer = (Completer) SafeFuture.this.completerWeakReference.get();
                if (completer == null) {
                    return "Completer object has been garbage collected, future will fail soon";
                }
                return "tag=[" + completer.tag + "]";
            }
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            Completer completer = (Completer) this.completerWeakReference.get();
            boolean cancelled = this.delegate.cancel(mayInterruptIfRunning);
            if (cancelled && completer != null) {
                completer.fireCancellationListeners();
            }
            return cancelled;
        }

        boolean cancelWithoutNotifyingCompleter(boolean shouldInterrupt) {
            return this.delegate.cancel(shouldInterrupt);
        }

        boolean set(Object obj) {
            return this.delegate.set(obj);
        }

        boolean setException(Throwable t) {
            return this.delegate.setException(t);
        }

        public boolean isCancelled() {
            return this.delegate.isCancelled();
        }

        public boolean isDone() {
            return this.delegate.isDone();
        }

        public Object get() throws InterruptedException, ExecutionException {
            return this.delegate.get();
        }

        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.delegate.get(timeout, unit);
        }

        public void addListener(Runnable listener, Executor executor) {
            this.delegate.addListener(listener, executor);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    public static final class Completer {
        private boolean attemptedSetting;
        private ResolvableFuture cancellationFuture = ResolvableFuture.create();
        SafeFuture future;
        Object tag;

        Completer() {
        }

        public boolean set(Object obj) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            boolean wasSet = safeFuture != null && safeFuture.set(obj);
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public boolean setException(Throwable t) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            boolean wasSet = safeFuture != null && safeFuture.setException(t);
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public boolean setCancelled() {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            boolean wasSet = safeFuture != null && safeFuture.cancelWithoutNotifyingCompleter(true);
            if (wasSet) {
                setCompletedNormally();
            }
            return wasSet;
        }

        public void addCancellationListener(Runnable runnable, Executor executor) {
            ListenableFuture<?> localCancellationFuture = this.cancellationFuture;
            if (localCancellationFuture != null) {
                localCancellationFuture.addListener(runnable, executor);
            }
        }

        void fireCancellationListeners() {
            this.tag = null;
            this.future = null;
            this.cancellationFuture.set(null);
        }

        private void setCompletedNormally() {
            this.tag = null;
            this.future = null;
            this.cancellationFuture = null;
        }

        protected void finalize() {
            ResolvableFuture<Void> localCancellationFuture;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null && !safeFuture.isDone()) {
                safeFuture.setException(new FutureGarbageCollectedException("The completer object was garbage collected - this future would otherwise never complete. The tag was: " + this.tag));
            }
            if (!this.attemptedSetting && (localCancellationFuture = this.cancellationFuture) != null) {
                localCancellationFuture.set(null);
            }
        }
    }

    static final class FutureGarbageCollectedException extends Throwable {
        FutureGarbageCollectedException(String message) {
            super(message);
        }

        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
