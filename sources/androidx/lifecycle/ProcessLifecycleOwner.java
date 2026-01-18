package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ReportFragment;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes28.dex */
public class ProcessLifecycleOwner implements LifecycleOwner {
    static final long TIMEOUT_MS = 700;
    private static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    private Handler mHandler;
    private int mStartedCounter = 0;
    private int mResumedCounter = 0;
    private boolean mPauseSent = true;
    private boolean mStopSent = true;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private Runnable mDelayedPauseRunnable = new 1();
    ReportFragment.ActivityInitializationListener mInitializationListener = new 2();

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            ProcessLifecycleOwner.this.dispatchPauseIfNeeded();
            ProcessLifecycleOwner.this.dispatchStopIfNeeded();
        }
    }

    class 2 implements ReportFragment.ActivityInitializationListener {
        2() {
        }

        public void onCreate() {
        }

        public void onStart() {
            ProcessLifecycleOwner.this.activityStarted();
        }

        public void onResume() {
            ProcessLifecycleOwner.this.activityResumed();
        }
    }

    public static LifecycleOwner get() {
        return sInstance;
    }

    static void init(Context context) {
        sInstance.attach(context);
    }

    void activityStarted() {
        int i = this.mStartedCounter + 1;
        this.mStartedCounter = i;
        if (i == 1 && this.mStopSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
            this.mStopSent = false;
        }
    }

    void activityResumed() {
        int i = this.mResumedCounter + 1;
        this.mResumedCounter = i;
        if (i == 1) {
            if (this.mPauseSent) {
                this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                this.mPauseSent = false;
            } else {
                this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
            }
        }
    }

    void activityPaused() {
        int i = this.mResumedCounter - 1;
        this.mResumedCounter = i;
        if (i == 0) {
            this.mHandler.postDelayed(this.mDelayedPauseRunnable, 700L);
        }
    }

    void activityStopped() {
        this.mStartedCounter--;
        dispatchStopIfNeeded();
    }

    void dispatchPauseIfNeeded() {
        if (this.mResumedCounter == 0) {
            this.mPauseSent = true;
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
    }

    void dispatchStopIfNeeded() {
        if (this.mStartedCounter == 0 && this.mPauseSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
            this.mStopSent = true;
        }
    }

    private ProcessLifecycleOwner() {
    }

    void attach(Context context) {
        this.mHandler = new Handler();
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        Application app = context.getApplicationContext();
        app.registerActivityLifecycleCallbacks(new 3());
    }

    class 3 extends EmptyActivityLifecycleCallbacks {
        3() {
        }

        class 1 extends EmptyActivityLifecycleCallbacks {
            1() {
            }

            public void onActivityPostStarted(Activity activity) {
                ProcessLifecycleOwner.this.activityStarted();
            }

            public void onActivityPostResumed(Activity activity) {
                ProcessLifecycleOwner.this.activityResumed();
            }
        }

        public void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
            activity.registerActivityLifecycleCallbacks(new 1());
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (Build.VERSION.SDK_INT < 29) {
                ReportFragment.get(activity).setProcessListener(ProcessLifecycleOwner.this.mInitializationListener);
            }
        }

        public void onActivityPaused(Activity activity) {
            ProcessLifecycleOwner.this.activityPaused();
        }

        public void onActivityStopped(Activity activity) {
            ProcessLifecycleOwner.this.activityStopped();
        }
    }

    public Lifecycle getLifecycle() {
        return this.mRegistry;
    }
}
