package androidx.core.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class Pools {

    public interface Pool {
        Object acquire();

        boolean release(Object obj);
    }

    private Pools() {
    }

    public static class SimplePool implements Pool {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int maxPoolSize) {
            if (maxPoolSize <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.mPool = new Object[maxPoolSize];
        }

        public Object acquire() {
            int i = this.mPoolSize;
            if (i <= 0) {
                return null;
            }
            int lastPooledIndex = i - 1;
            Object[] objArr = this.mPool;
            Object obj = objArr[lastPooledIndex];
            objArr[lastPooledIndex] = null;
            this.mPoolSize = i - 1;
            return obj;
        }

        public boolean release(Object obj) {
            if (isInPool(obj)) {
                throw new IllegalStateException("Already in the pool!");
            }
            int i = this.mPoolSize;
            Object[] objArr = this.mPool;
            if (i < objArr.length) {
                objArr[i] = obj;
                this.mPoolSize = i + 1;
                return true;
            }
            return false;
        }

        private boolean isInPool(Object obj) {
            for (int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == obj) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool extends SimplePool {
        private final Object mLock;

        public SynchronizedPool(int maxPoolSize) {
            super(maxPoolSize);
            this.mLock = new Object();
        }

        public Object acquire() {
            Object acquire;
            synchronized (this.mLock) {
                acquire = super.acquire();
            }
            return acquire;
        }

        public boolean release(Object obj) {
            boolean release;
            synchronized (this.mLock) {
                release = super.release(obj);
            }
            return release;
        }
    }
}
