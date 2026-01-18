package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes42.dex */
public final class AsyncDifferConfig {

    @NonNull
    private final Executor mBackgroundThreadExecutor;

    @NonNull
    private final DiffUtil.ItemCallback mDiffCallback;

    @Nullable
    private final Executor mMainThreadExecutor;

    AsyncDifferConfig(@Nullable Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @NonNull DiffUtil.ItemCallback itemCallback) {
        this.mMainThreadExecutor = mainThreadExecutor;
        this.mBackgroundThreadExecutor = backgroundThreadExecutor;
        this.mDiffCallback = itemCallback;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public DiffUtil.ItemCallback getDiffCallback() {
        return this.mDiffCallback;
    }

    public static final class Builder {
        private Executor mBackgroundThreadExecutor;
        private final DiffUtil.ItemCallback mDiffCallback;

        @Nullable
        private Executor mMainThreadExecutor;
        private static final Object sExecutorLock = new Object();
        private static Executor sDiffExecutor = null;

        public Builder(@NonNull DiffUtil.ItemCallback itemCallback) {
            this.mDiffCallback = itemCallback;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public Builder setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }

        @NonNull
        public Builder setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NonNull
        public AsyncDifferConfig build() {
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    if (sDiffExecutor == null) {
                        sDiffExecutor = Executors.newFixedThreadPool(2);
                    }
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            return new AsyncDifferConfig(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }
}
