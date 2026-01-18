package androidx.core.provider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class SelfDestructiveThread {
    private static final int MSG_DESTRUCTION = 0;
    private static final int MSG_INVOKE_RUNNABLE = 1;
    private final int mDestructAfterMillisec;
    private Handler mHandler;
    private final int mPriority;
    private HandlerThread mThread;
    private final String mThreadName;
    private final Object mLock = new Object();
    private Handler.Callback mCallback = new 1();
    private int mGeneration = 0;

    public interface ReplyCallback {
        void onReply(Object obj);
    }

    class 1 implements Handler.Callback {
        1() {
        }

        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SelfDestructiveThread.this.onDestruction();
                    break;
                case 1:
                    SelfDestructiveThread.this.onInvokeRunnable((Runnable) msg.obj);
                    break;
            }
            return true;
        }
    }

    public SelfDestructiveThread(String threadName, int priority, int destructAfterMillisec) {
        this.mThreadName = threadName;
        this.mPriority = priority;
        this.mDestructAfterMillisec = destructAfterMillisec;
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mThread != null;
        }
        return z;
    }

    public int getGeneration() {
        int i;
        synchronized (this.mLock) {
            i = this.mGeneration;
        }
        return i;
    }

    private void post(Runnable runnable) {
        synchronized (this.mLock) {
            if (this.mThread == null) {
                HandlerThread handlerThread = new HandlerThread(this.mThreadName, this.mPriority);
                this.mThread = handlerThread;
                handlerThread.start();
                this.mHandler = new Handler(this.mThread.getLooper(), this.mCallback);
                this.mGeneration++;
            }
            this.mHandler.removeMessages(0);
            Handler handler = this.mHandler;
            handler.sendMessage(handler.obtainMessage(1, runnable));
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ Callable val$callable;
        final /* synthetic */ Handler val$calleeHandler;
        final /* synthetic */ ReplyCallback val$reply;

        2(Callable callable, Handler handler, ReplyCallback replyCallback) {
            this.val$callable = callable;
            this.val$calleeHandler = handler;
            this.val$reply = replyCallback;
        }

        public void run() {
            Object obj;
            try {
                obj = this.val$callable.call();
            } catch (Exception e) {
                obj = null;
            }
            this.val$calleeHandler.post(new 1(obj));
        }

        class 1 implements Runnable {
            final /* synthetic */ Object val$result;

            1(Object obj) {
                this.val$result = obj;
            }

            public void run() {
                2.this.val$reply.onReply(this.val$result);
            }
        }
    }

    public void postAndReply(Callable callable, ReplyCallback replyCallback) {
        Handler calleeHandler = CalleeHandler.create();
        post(new 2(callable, calleeHandler, replyCallback));
    }

    public Object postAndWait(Callable callable, int timeoutMillis) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean running = new AtomicBoolean(true);
        post(new 3(atomicReference, callable, lock, running, cond));
        lock.lock();
        try {
            if (!running.get()) {
                return atomicReference.get();
            }
            long remaining = TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
            do {
                try {
                    remaining = cond.awaitNanos(remaining);
                } catch (InterruptedException e) {
                }
                if (!running.get()) {
                    return atomicReference.get();
                }
            } while (remaining > 0);
            throw new InterruptedException("timeout");
        } finally {
            lock.unlock();
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ Callable val$callable;
        final /* synthetic */ Condition val$cond;
        final /* synthetic */ AtomicReference val$holder;
        final /* synthetic */ ReentrantLock val$lock;
        final /* synthetic */ AtomicBoolean val$running;

        3(AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
            this.val$holder = atomicReference;
            this.val$callable = callable;
            this.val$lock = reentrantLock;
            this.val$running = atomicBoolean;
            this.val$cond = condition;
        }

        public void run() {
            try {
                this.val$holder.set(this.val$callable.call());
            } catch (Exception e) {
            }
            this.val$lock.lock();
            try {
                this.val$running.set(false);
                this.val$cond.signal();
            } finally {
                this.val$lock.unlock();
            }
        }
    }

    void onInvokeRunnable(Runnable runnable) {
        runnable.run();
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(0), this.mDestructAfterMillisec);
        }
    }

    void onDestruction() {
        synchronized (this.mLock) {
            if (this.mHandler.hasMessages(1)) {
                return;
            }
            this.mThread.quit();
            this.mThread = null;
            this.mHandler = null;
        }
    }
}
