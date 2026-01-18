package sun.nio.ch;

import java.security.AccessController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import jdk.internal.misc.InnocuousThread;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ThreadPool {
    private static final String DEFAULT_THREAD_POOL_INITIAL_SIZE = "java.nio.channels.DefaultThreadPool.initialSize";
    private static final String DEFAULT_THREAD_POOL_THREAD_FACTORY = "java.nio.channels.DefaultThreadPool.threadFactory";
    private final ExecutorService executor;
    private final boolean isFixed;
    private final int poolSize;

    private ThreadPool(ExecutorService executorService, boolean z, int i) {
        this.executor = executorService;
        this.isFixed = z;
        this.poolSize = i;
    }

    ExecutorService executor() {
        return this.executor;
    }

    boolean isFixedThreadPool() {
        return this.isFixed;
    }

    int poolSize() {
        return this.poolSize;
    }

    static ThreadFactory defaultThreadFactory() {
        if (System.getSecurityManager() == null) {
            return new ThreadPool$$ExternalSyntheticLambda1();
        }
        return new ThreadPool$$ExternalSyntheticLambda2();
    }

    static /* synthetic */ Thread lambda$defaultThreadFactory$0(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }

    static /* synthetic */ Thread lambda$defaultThreadFactory$2(Runnable runnable) {
        return (Thread) AccessController.doPrivileged(new ThreadPool$$ExternalSyntheticLambda0(runnable));
    }

    static /* synthetic */ Thread lambda$defaultThreadFactory$1(Runnable runnable) {
        Thread newThread = InnocuousThread.newThread(runnable);
        newThread.setDaemon(true);
        return newThread;
    }

    private static class DefaultThreadPoolHolder {
        static final ThreadPool defaultThreadPool = ThreadPool.createDefault();

        private DefaultThreadPoolHolder() {
        }
    }

    static ThreadPool getDefault() {
        return DefaultThreadPoolHolder.defaultThreadPool;
    }

    static ThreadPool createDefault() {
        int defaultThreadPoolInitialSize = getDefaultThreadPoolInitialSize();
        if (defaultThreadPoolInitialSize < 0) {
            defaultThreadPoolInitialSize = Runtime.getRuntime().availableProcessors();
        }
        ThreadFactory defaultThreadPoolThreadFactory = getDefaultThreadPoolThreadFactory();
        if (defaultThreadPoolThreadFactory == null) {
            defaultThreadPoolThreadFactory = defaultThreadFactory();
        }
        return new ThreadPool(Executors.newCachedThreadPool(defaultThreadPoolThreadFactory), false, defaultThreadPoolInitialSize);
    }

    static ThreadPool create(int i, ThreadFactory threadFactory) {
        if (i <= 0) {
            throw new IllegalArgumentException("'nThreads' must be > 0");
        }
        return new ThreadPool(Executors.newFixedThreadPool(i, threadFactory), true, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static sun.nio.ch.ThreadPool wrap(java.util.concurrent.ExecutorService r3, int r4) {
        /*
            if (r3 == 0) goto L27
            boolean r0 = r3 instanceof java.util.concurrent.ThreadPoolExecutor
            r1 = 0
            if (r0 == 0) goto L1e
            r0 = r3
            java.util.concurrent.ThreadPoolExecutor r0 = (java.util.concurrent.ThreadPoolExecutor) r0
            int r0 = r0.getMaximumPoolSize()
            r2 = 2147483647(0x7fffffff, float:NaN)
            if (r0 != r2) goto L21
            if (r4 >= 0) goto L20
            java.lang.Runtime r4 = java.lang.Runtime.getRuntime()
            int r4 = r4.availableProcessors()
            goto L21
        L1e:
            if (r4 >= 0) goto L21
        L20:
            r4 = 0
        L21:
            sun.nio.ch.ThreadPool r0 = new sun.nio.ch.ThreadPool
            r0.<init>(r3, r1, r4)
            return r0
        L27:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r4 = "'executor' is null"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.ThreadPool.wrap(java.util.concurrent.ExecutorService, int):sun.nio.ch.ThreadPool");
    }

    private static int getDefaultThreadPoolInitialSize() {
        String str = (String) AccessController.doPrivileged(new GetPropertyAction("java.nio.channels.DefaultThreadPool.initialSize"));
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new Error("Value of property 'java.nio.channels.DefaultThreadPool.initialSize' is invalid: " + e);
        }
    }

    private static ThreadFactory getDefaultThreadPoolThreadFactory() {
        String str = (String) AccessController.doPrivileged(new GetPropertyAction("java.nio.channels.DefaultThreadPool.threadFactory"));
        if (str == null) {
            return null;
        }
        try {
            return (ThreadFactory) Class.forName(str, true, ClassLoader.getSystemClassLoader()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new Error(e);
        }
    }
}
