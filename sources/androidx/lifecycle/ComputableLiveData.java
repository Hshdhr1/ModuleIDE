package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes33.dex */
public abstract class ComputableLiveData {
    final AtomicBoolean mComputing;
    final Executor mExecutor;
    final AtomicBoolean mInvalid;
    final Runnable mInvalidationRunnable;
    final LiveData mLiveData;
    final Runnable mRefreshRunnable;

    protected abstract Object compute();

    public ComputableLiveData() {
        this(ArchTaskExecutor.getIOThreadExecutor());
    }

    public ComputableLiveData(Executor executor) {
        this.mInvalid = new AtomicBoolean(true);
        this.mComputing = new AtomicBoolean(false);
        this.mRefreshRunnable = new 2();
        this.mInvalidationRunnable = new 3();
        this.mExecutor = executor;
        this.mLiveData = new 1();
    }

    class 1 extends LiveData {
        1() {
        }

        protected void onActive() {
            ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
        }
    }

    public LiveData getLiveData() {
        return this.mLiveData;
    }

    class 2 implements Runnable {
        2() {
        }

        public void run() {
            do {
                boolean computed = false;
                if (ComputableLiveData.this.mComputing.compareAndSet(false, true)) {
                    Object obj = null;
                    while (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
                        try {
                            computed = true;
                            obj = ComputableLiveData.this.compute();
                        } finally {
                            ComputableLiveData.this.mComputing.set(false);
                        }
                    }
                    if (computed) {
                        ComputableLiveData.this.mLiveData.postValue(obj);
                    }
                }
                if (!computed) {
                    return;
                }
            } while (ComputableLiveData.this.mInvalid.get());
        }
    }

    class 3 implements Runnable {
        3() {
        }

        public void run() {
            boolean isActive = ComputableLiveData.this.mLiveData.hasActiveObservers();
            if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && isActive) {
                ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
            }
        }
    }

    public void invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable);
    }
}
