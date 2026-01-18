package androidx.constraintlayout.core;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
final class Pools {
    private static final boolean DEBUG = false;

    interface Pool {
        Object acquire();

        boolean release(Object obj);

        void releaseAll(Object[] objArr, int i);
    }

    private Pools() {
    }

    static class SimplePool implements Pool {
        private final Object[] mPool;
        private int mPoolSize;

        SimplePool(int maxPoolSize) {
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
            int i = this.mPoolSize;
            Object[] objArr = this.mPool;
            if (i < objArr.length) {
                objArr[i] = obj;
                this.mPoolSize = i + 1;
                return true;
            }
            return false;
        }

        public void releaseAll(Object[] objArr, int count) {
            if (count > objArr.length) {
                count = objArr.length;
            }
            for (int i = 0; i < count; i++) {
                Object obj = objArr[i];
                int i2 = this.mPoolSize;
                Object[] objArr2 = this.mPool;
                if (i2 < objArr2.length) {
                    objArr2[i2] = obj;
                    this.mPoolSize = i2 + 1;
                }
            }
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
}
