package androidx.core.provider;

import android.os.Handler;
import android.os.Process;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
class RequestExecutor {
    private RequestExecutor() {
    }

    static void execute(Executor executor, Callable callable, Consumer consumer) {
        Handler calleeHandler = CalleeHandler.create();
        executor.execute(new ReplyRunnable(calleeHandler, callable, consumer));
    }

    static Object submit(ExecutorService executor, Callable callable, int timeoutMillis) throws InterruptedException {
        try {
            return executor.submit(callable).get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new InterruptedException("timeout");
        } catch (InterruptedException e2) {
            throw e2;
        } catch (ExecutionException e3) {
            throw new RuntimeException(e3);
        }
    }

    static ThreadPoolExecutor createDefaultExecutor(String threadName, int threadPriority, int keepAliveTimeInMillis) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadName, threadPriority);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, keepAliveTimeInMillis, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), threadFactory);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    static Executor createHandlerExecutor(Handler handler) {
        return new HandlerExecutor(handler);
    }

    private static class HandlerExecutor implements Executor {
        private final Handler mHandler;

        HandlerExecutor(Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(Runnable command) {
            if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    private static class ReplyRunnable implements Runnable {
        private Callable mCallable;
        private Consumer mConsumer;
        private Handler mHandler;

        ReplyRunnable(Handler handler, Callable callable, Consumer consumer) {
            this.mCallable = callable;
            this.mConsumer = consumer;
            this.mHandler = handler;
        }

        public void run() {
            Object obj;
            try {
                obj = this.mCallable.call();
            } catch (Exception e) {
                obj = null;
            }
            this.mHandler.post(new 1(this.mConsumer, obj));
        }

        class 1 implements Runnable {
            final /* synthetic */ Consumer val$consumer;
            final /* synthetic */ Object val$result;

            1(Consumer consumer, Object obj) {
                this.val$consumer = consumer;
                this.val$result = obj;
            }

            public void run() {
                this.val$consumer.accept(this.val$result);
            }
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private int mPriority;
        private String mThreadName;

        DefaultThreadFactory(String threadName, int priority) {
            this.mThreadName = threadName;
            this.mPriority = priority;
        }

        public Thread newThread(Runnable runnable) {
            return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
        }

        private static class ProcessPriorityThread extends Thread {
            private final int mPriority;

            ProcessPriorityThread(Runnable target, String name, int priority) {
                super(target, name);
                this.mPriority = priority;
            }

            public void run() {
                Process.setThreadPriority(this.mPriority);
                super.run();
            }
        }
    }
}
