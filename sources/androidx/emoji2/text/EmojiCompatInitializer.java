package androidx.emoji2.text;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.core.os.TraceCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleInitializer;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes27.dex */
public class EmojiCompatInitializer implements Initializer {
    private static final long STARTUP_THREAD_CREATION_DELAY_MS = 500;
    private static final String S_INITIALIZER_THREAD_NAME = "EmojiCompatInitializer";

    public Boolean create(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            EmojiCompat.init(new BackgroundDefaultConfig(context));
            delayUntilFirstResume(context);
            return true;
        }
        return false;
    }

    void delayUntilFirstResume(Context context) {
        AppInitializer appInitializer = AppInitializer.getInstance(context);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) appInitializer.initializeComponent(ProcessLifecycleInitializer.class);
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        lifecycle.addObserver(new 1(lifecycle));
    }

    class 1 implements DefaultLifecycleObserver {
        final /* synthetic */ Lifecycle val$lifecycle;

        public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
            DefaultLifecycleObserver.-CC.$default$onCreate(this, lifecycleOwner);
        }

        public /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
            DefaultLifecycleObserver.-CC.$default$onDestroy(this, lifecycleOwner);
        }

        public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
            DefaultLifecycleObserver.-CC.$default$onPause(this, lifecycleOwner);
        }

        public /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
            DefaultLifecycleObserver.-CC.$default$onStart(this, lifecycleOwner);
        }

        public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
            DefaultLifecycleObserver.-CC.$default$onStop(this, lifecycleOwner);
        }

        1(Lifecycle lifecycle) {
            this.val$lifecycle = lifecycle;
        }

        public void onResume(LifecycleOwner owner) {
            EmojiCompatInitializer.this.loadEmojiCompatAfterDelay();
            this.val$lifecycle.removeObserver(this);
        }
    }

    void loadEmojiCompatAfterDelay() {
        Handler mainHandler = ConcurrencyHelpers.mainHandlerAsync();
        mainHandler.postDelayed(new LoadEmojiCompatRunnable(), 500L);
    }

    public List dependencies() {
        return Collections.singletonList(ProcessLifecycleInitializer.class);
    }

    static class LoadEmojiCompatRunnable implements Runnable {
        LoadEmojiCompatRunnable() {
        }

        public void run() {
            try {
                TraceCompat.beginSection("EmojiCompat.EmojiCompatInitializer.run");
                if (EmojiCompat.isConfigured()) {
                    EmojiCompat.get().load();
                }
            } finally {
                TraceCompat.endSection();
            }
        }
    }

    static class BackgroundDefaultConfig extends EmojiCompat.Config {
        protected BackgroundDefaultConfig(Context context) {
            super(new BackgroundDefaultLoader(context));
            setMetadataLoadStrategy(1);
        }
    }

    static class BackgroundDefaultLoader implements EmojiCompat.MetadataRepoLoader {
        private final Context mContext;

        BackgroundDefaultLoader(Context context) {
            this.mContext = context.getApplicationContext();
        }

        public void load(EmojiCompat.MetadataRepoLoaderCallback loaderCallback) {
            ThreadPoolExecutor executor = ConcurrencyHelpers.createBackgroundPriorityExecutor("EmojiCompatInitializer");
            executor.execute(new EmojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0(this, loaderCallback, executor));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: doLoad, reason: merged with bridge method [inline-methods] */
        public void lambda$load$0$androidx-emoji2-text-EmojiCompatInitializer$BackgroundDefaultLoader(EmojiCompat.MetadataRepoLoaderCallback loaderCallback, ThreadPoolExecutor executor) {
            try {
                FontRequestEmojiCompatConfig config = DefaultEmojiCompatConfig.create(this.mContext);
                if (config == null) {
                    throw new RuntimeException("EmojiCompat font provider not available on this device.");
                }
                config.setLoadingExecutor(executor);
                config.getMetadataRepoLoader().load(new 1(loaderCallback, executor));
            } catch (Throwable t) {
                loaderCallback.onFailed(t);
                executor.shutdown();
            }
        }

        class 1 extends EmojiCompat.MetadataRepoLoaderCallback {
            final /* synthetic */ ThreadPoolExecutor val$executor;
            final /* synthetic */ EmojiCompat.MetadataRepoLoaderCallback val$loaderCallback;

            1(EmojiCompat.MetadataRepoLoaderCallback metadataRepoLoaderCallback, ThreadPoolExecutor threadPoolExecutor) {
                this.val$loaderCallback = metadataRepoLoaderCallback;
                this.val$executor = threadPoolExecutor;
            }

            public void onLoaded(MetadataRepo metadataRepo) {
                try {
                    this.val$loaderCallback.onLoaded(metadataRepo);
                } finally {
                    this.val$executor.shutdown();
                }
            }

            public void onFailed(Throwable throwable) {
                try {
                    this.val$loaderCallback.onFailed(throwable);
                } finally {
                    this.val$executor.shutdown();
                }
            }
        }
    }
}
