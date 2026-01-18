package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
final class ActivityRecreator {
    private static final String LOG_TAG = "ActivityRecreator";
    protected static final Class activityThreadClass;
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    protected static final Field mainThreadField;
    protected static final Method performStopActivity2ParamsMethod;
    protected static final Method performStopActivity3ParamsMethod;
    protected static final Method requestRelaunchActivityMethod;
    protected static final Field tokenField;

    private ActivityRecreator() {
    }

    static {
        Class activityThreadClass2 = getActivityThreadClass();
        activityThreadClass = activityThreadClass2;
        mainThreadField = getMainThreadField();
        tokenField = getTokenField();
        performStopActivity3ParamsMethod = getPerformStopActivity3Params(activityThreadClass2);
        performStopActivity2ParamsMethod = getPerformStopActivity2Params(activityThreadClass2);
        requestRelaunchActivityMethod = getRequestRelaunchActivityMethod(activityThreadClass2);
    }

    static boolean recreate(Activity activity) {
        Object activityThread;
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return true;
        }
        if (needsRelaunchCall() && requestRelaunchActivityMethod == null) {
            return false;
        }
        if (performStopActivity2ParamsMethod == null && performStopActivity3ParamsMethod == null) {
            return false;
        }
        try {
            Object token = tokenField.get(activity);
            if (token == null || (activityThread = mainThreadField.get(activity)) == null) {
                return false;
            }
            Application application = activity.getApplication();
            LifecycleCheckCallbacks callbacks = new LifecycleCheckCallbacks(activity);
            application.registerActivityLifecycleCallbacks(callbacks);
            Handler handler = mainHandler;
            handler.post(new 1(callbacks, token));
            try {
                if (needsRelaunchCall()) {
                    requestRelaunchActivityMethod.invoke(activityThread, new Object[]{token, null, null, 0, false, null, null, false, false});
                } else {
                    activity.recreate();
                }
                handler.post(new 2(application, callbacks));
                return true;
            } catch (Throwable th) {
                mainHandler.post(new 2(application, callbacks));
                throw th;
            }
        } catch (Throwable th2) {
            return false;
        }
    }

    class 1 implements Runnable {
        final /* synthetic */ LifecycleCheckCallbacks val$callbacks;
        final /* synthetic */ Object val$token;

        1(LifecycleCheckCallbacks lifecycleCheckCallbacks, Object obj) {
            this.val$callbacks = lifecycleCheckCallbacks;
            this.val$token = obj;
        }

        public void run() {
            this.val$callbacks.currentlyRecreatingToken = this.val$token;
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ Application val$application;
        final /* synthetic */ LifecycleCheckCallbacks val$callbacks;

        2(Application application, LifecycleCheckCallbacks lifecycleCheckCallbacks) {
            this.val$application = application;
            this.val$callbacks = lifecycleCheckCallbacks;
        }

        public void run() {
            this.val$application.unregisterActivityLifecycleCallbacks(this.val$callbacks);
        }
    }

    private static final class LifecycleCheckCallbacks implements Application.ActivityLifecycleCallbacks {
        Object currentlyRecreatingToken;
        private Activity mActivity;
        private final int mRecreatingHashCode;
        private boolean mStarted = false;
        private boolean mDestroyed = false;
        private boolean mStopQueued = false;

        LifecycleCheckCallbacks(Activity aboutToRecreate) {
            this.mActivity = aboutToRecreate;
            this.mRecreatingHashCode = aboutToRecreate.hashCode();
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
            if (this.mActivity == activity) {
                this.mStarted = true;
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
            if (this.mDestroyed && !this.mStopQueued && !this.mStarted && ActivityRecreator.queueOnStopIfNecessary(this.currentlyRecreatingToken, this.mRecreatingHashCode, activity)) {
                this.mStopQueued = true;
                this.currentlyRecreatingToken = null;
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.mActivity == activity) {
                this.mActivity = null;
                this.mDestroyed = true;
            }
        }
    }

    protected static boolean queueOnStopIfNecessary(Object currentlyRecreatingToken, int currentlyRecreatingHashCode, Activity activity) {
        try {
            Object token = tokenField.get(activity);
            if (token == currentlyRecreatingToken && activity.hashCode() == currentlyRecreatingHashCode) {
                Object activityThread = mainThreadField.get(activity);
                mainHandler.postAtFrontOfQueue(new 3(activityThread, token));
                return true;
            }
            return false;
        } catch (Throwable t) {
            Log.e("ActivityRecreator", "Exception while fetching field values", t);
            return false;
        }
    }

    class 3 implements Runnable {
        final /* synthetic */ Object val$activityThread;
        final /* synthetic */ Object val$token;

        3(Object obj, Object obj2) {
            this.val$activityThread = obj;
            this.val$token = obj2;
        }

        public void run() {
            try {
                if (ActivityRecreator.performStopActivity3ParamsMethod != null) {
                    ActivityRecreator.performStopActivity3ParamsMethod.invoke(this.val$activityThread, new Object[]{this.val$token, false, "AppCompat recreation"});
                } else {
                    ActivityRecreator.performStopActivity2ParamsMethod.invoke(this.val$activityThread, new Object[]{this.val$token, false});
                }
            } catch (RuntimeException e) {
                if (e.getClass() == RuntimeException.class && e.getMessage() != null && e.getMessage().startsWith("Unable to stop")) {
                    throw e;
                }
            } catch (Throwable t) {
                Log.e("ActivityRecreator", "Exception while invoking performStopActivity", t);
            }
        }
    }

    private static Method getPerformStopActivity3Params(Class cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method performStop = cls.getDeclaredMethod("performStopActivity", new Class[]{IBinder.class, Boolean.TYPE, String.class});
            performStop.setAccessible(true);
            return performStop;
        } catch (Throwable th) {
            return null;
        }
    }

    private static Method getPerformStopActivity2Params(Class cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method performStop = cls.getDeclaredMethod("performStopActivity", new Class[]{IBinder.class, Boolean.TYPE});
            performStop.setAccessible(true);
            return performStop;
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean needsRelaunchCall() {
        return Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27;
    }

    private static Method getRequestRelaunchActivityMethod(Class cls) {
        if (!needsRelaunchCall() || cls == null) {
            return null;
        }
        try {
            Method relaunch = cls.getDeclaredMethod("requestRelaunchActivity", new Class[]{IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE});
            relaunch.setAccessible(true);
            return relaunch;
        } catch (Throwable th) {
            return null;
        }
    }

    private static Field getMainThreadField() {
        try {
            Field mainThreadField2 = Activity.class.getDeclaredField("mMainThread");
            mainThreadField2.setAccessible(true);
            return mainThreadField2;
        } catch (Throwable th) {
            return null;
        }
    }

    private static Field getTokenField() {
        try {
            Field tokenField2 = Activity.class.getDeclaredField("mToken");
            tokenField2.setAccessible(true);
            return tokenField2;
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class getActivityThreadClass() {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (Throwable th) {
            return null;
        }
    }
}
