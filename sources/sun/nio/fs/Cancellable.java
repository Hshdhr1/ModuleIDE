package sun.nio.fs;

import java.util.concurrent.ExecutionException;
import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class Cancellable implements Runnable {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private boolean completed;
    private Throwable exception;
    private final Object lock = new Object();
    private final long pollingAddress;

    protected int cancelValue() {
        return Integer.MAX_VALUE;
    }

    abstract void implRun() throws Throwable;

    protected Cancellable() {
        Unsafe unsafe2 = unsafe;
        long allocateMemory = unsafe2.allocateMemory(4L);
        this.pollingAddress = allocateMemory;
        unsafe2.putIntVolatile((Object) null, allocateMemory, 0);
    }

    protected long addressToPollForCancel() {
        return this.pollingAddress;
    }

    final void cancel() {
        synchronized (this.lock) {
            if (!this.completed) {
                unsafe.putIntVolatile((Object) null, this.pollingAddress, cancelValue());
            }
        }
    }

    private Throwable exception() {
        Throwable th;
        synchronized (this.lock) {
            th = this.exception;
        }
        return th;
    }

    public final void run() {
        try {
            implRun();
            synchronized (this.lock) {
                this.completed = true;
                unsafe.freeMemory(this.pollingAddress);
            }
        } catch (Throwable th) {
            try {
                synchronized (this.lock) {
                    this.exception = th;
                    synchronized (this.lock) {
                        this.completed = true;
                        unsafe.freeMemory(this.pollingAddress);
                    }
                }
            } catch (Throwable th2) {
                synchronized (this.lock) {
                    this.completed = true;
                    unsafe.freeMemory(this.pollingAddress);
                    throw th2;
                }
            }
        }
    }

    static void runInterruptibly(Cancellable cancellable) throws ExecutionException {
        Thread thread = new Thread((ThreadGroup) null, cancellable, "NIO-Task", 0L, false);
        thread.start();
        boolean z = false;
        while (thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException unused) {
                cancellable.cancel();
                z = true;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        Throwable exception = cancellable.exception();
        if (exception != null) {
            throw new ExecutionException(exception);
        }
    }
}
