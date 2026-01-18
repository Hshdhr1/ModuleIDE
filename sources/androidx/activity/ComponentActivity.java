package androidx.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.contextaware.ContextAware;
import androidx.activity.contextaware.ContextAwareHelper;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import androidx.tracing.Trace;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public class ComponentActivity extends androidx.core.app.ComponentActivity implements ContextAware, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, ActivityResultCaller {
    private static final String ACTIVITY_RESULT_TAG = "android:support:activity-result";
    private final ActivityResultRegistry mActivityResultRegistry;
    private int mContentLayoutId;
    final ContextAwareHelper mContextAwareHelper;
    private ViewModelProvider.Factory mDefaultFactory;
    private final LifecycleRegistry mLifecycleRegistry;
    private final AtomicInteger mNextLocalRequestCode;
    private final OnBackPressedDispatcher mOnBackPressedDispatcher;
    final SavedStateRegistryController mSavedStateRegistryController;
    private ViewModelStore mViewModelStore;

    static /* synthetic */ void access$001(ComponentActivity x0) {
        super.onBackPressed();
    }

    static final class NonConfigurationInstances {
        Object custom;
        ViewModelStore viewModelStore;

        NonConfigurationInstances() {
        }
    }

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            try {
                ComponentActivity.access$001(ComponentActivity.this);
            } catch (IllegalStateException e) {
                if (!TextUtils.equals(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                    throw e;
                }
            }
        }
    }

    class 2 extends ActivityResultRegistry {
        2() {
        }

        public void onLaunch(final int requestCode, ActivityResultContract contract, Object input, ActivityOptionsCompat options) {
            Bundle optionsBundle;
            Bundle optionsBundle2;
            ComponentActivity activity = ComponentActivity.this;
            ActivityResultContract.SynchronousResult synchronousResult = contract.getSynchronousResult(activity, input);
            if (synchronousResult != null) {
                new Handler(Looper.getMainLooper()).post(new 1(requestCode, synchronousResult));
                return;
            }
            Intent intent = contract.createIntent(activity, input);
            if (intent.getExtras() != null && intent.getExtras().getClassLoader() == null) {
                intent.setExtrasClassLoader(activity.getClassLoader());
            }
            if (intent.hasExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE")) {
                Bundle optionsBundle3 = intent.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                intent.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                optionsBundle = optionsBundle3;
            } else if (options == null) {
                optionsBundle = null;
            } else {
                Bundle optionsBundle4 = options.toBundle();
                optionsBundle = optionsBundle4;
            }
            if ("androidx.activity.result.contract.action.REQUEST_PERMISSIONS".equals(intent.getAction())) {
                String[] permissions = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
                if (permissions == null) {
                    permissions = new String[0];
                }
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return;
            }
            if ("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST".equals(intent.getAction())) {
                IntentSenderRequest request = (IntentSenderRequest) intent.getParcelableExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST");
                try {
                    optionsBundle2 = optionsBundle;
                    try {
                        ActivityCompat.startIntentSenderForResult(activity, request.getIntentSender(), requestCode, request.getFillInIntent(), request.getFlagsMask(), request.getFlagsValues(), 0, optionsBundle);
                    } catch (IntentSender.SendIntentException e) {
                        e = e;
                        new Handler(Looper.getMainLooper()).post(new 2(requestCode, e));
                        return;
                    }
                } catch (IntentSender.SendIntentException e2) {
                    e = e2;
                    optionsBundle2 = optionsBundle;
                }
                return;
            }
            ActivityCompat.startActivityForResult(activity, intent, requestCode, optionsBundle);
        }

        class 1 implements Runnable {
            final /* synthetic */ int val$requestCode;
            final /* synthetic */ ActivityResultContract.SynchronousResult val$synchronousResult;

            1(final int val$requestCode, final ActivityResultContract.SynchronousResult val$synchronousResult) {
                this.val$requestCode = val$requestCode;
                this.val$synchronousResult = val$synchronousResult;
            }

            public void run() {
                2.this.dispatchResult(this.val$requestCode, this.val$synchronousResult.getValue());
            }
        }

        class 2 implements Runnable {
            final /* synthetic */ IntentSender.SendIntentException val$e;
            final /* synthetic */ int val$requestCode;

            2(final int val$requestCode, final IntentSender.SendIntentException val$e) {
                this.val$requestCode = val$requestCode;
                this.val$e = val$e;
            }

            public void run() {
                2.this.dispatchResult(this.val$requestCode, 0, new Intent().setAction("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST").putExtra("androidx.activity.result.contract.extra.SEND_INTENT_EXCEPTION", this.val$e));
            }
        }
    }

    public ComponentActivity() {
        this.mContextAwareHelper = new ContextAwareHelper();
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        this.mOnBackPressedDispatcher = new OnBackPressedDispatcher(new 1());
        this.mNextLocalRequestCode = new AtomicInteger();
        this.mActivityResultRegistry = new 2();
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle == null) {
            throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getLifecycle().addObserver(new 3());
        }
        getLifecycle().addObserver(new 4());
        getLifecycle().addObserver(new 5());
        if (19 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT <= 23) {
            getLifecycle().addObserver(new ImmLeaksCleaner(this));
        }
        getSavedStateRegistry().registerSavedStateProvider("android:support:activity-result", new ComponentActivity$$ExternalSyntheticLambda1(this));
        addOnContextAvailableListener(new ComponentActivity$$ExternalSyntheticLambda0(this));
    }

    class 3 implements LifecycleEventObserver {
        3() {
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_STOP) {
                Window window = ComponentActivity.this.getWindow();
                View decor = window != null ? window.peekDecorView() : null;
                if (decor != null) {
                    Api19Impl.cancelPendingInputEvents(decor);
                }
            }
        }
    }

    class 4 implements LifecycleEventObserver {
        4() {
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                ComponentActivity.this.mContextAwareHelper.clearAvailableContext();
                if (!ComponentActivity.this.isChangingConfigurations()) {
                    ComponentActivity.this.getViewModelStore().clear();
                }
            }
        }
    }

    class 5 implements LifecycleEventObserver {
        5() {
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            ComponentActivity.this.ensureViewModelStore();
            ComponentActivity.this.getLifecycle().removeObserver(this);
        }
    }

    public /* synthetic */ Bundle lambda$new$0$ComponentActivity() {
        Bundle outState = new Bundle();
        this.mActivityResultRegistry.onSaveInstanceState(outState);
        return outState;
    }

    public /* synthetic */ void lambda$new$1$ComponentActivity(Context context) {
        Bundle savedInstanceState = getSavedStateRegistry().consumeRestoredStateForKey("android:support:activity-result");
        if (savedInstanceState != null) {
            this.mActivityResultRegistry.onRestoreInstanceState(savedInstanceState);
        }
    }

    public ComponentActivity(int contentLayoutId) {
        this();
        this.mContentLayoutId = contentLayoutId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mSavedStateRegistryController.performRestore(savedInstanceState);
        this.mContextAwareHelper.dispatchOnContextAvailable(this);
        super.onCreate(savedInstanceState);
        ReportFragment.injectIfNeededIn(this);
        int i = this.mContentLayoutId;
        if (i != 0) {
            setContentView(i);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        LifecycleRegistry lifecycle = getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            lifecycle.setCurrentState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(outState);
        this.mSavedStateRegistryController.performSave(outState);
    }

    public final Object onRetainNonConfigurationInstance() {
        NonConfigurationInstances nc;
        Object custom = onRetainCustomNonConfigurationInstance();
        ViewModelStore viewModelStore = this.mViewModelStore;
        if (viewModelStore == null && (nc = (NonConfigurationInstances) getLastNonConfigurationInstance()) != null) {
            viewModelStore = nc.viewModelStore;
        }
        if (viewModelStore == null && custom == null) {
            return null;
        }
        NonConfigurationInstances nci = new NonConfigurationInstances();
        nci.custom = custom;
        nci.viewModelStore = viewModelStore;
        return nci;
    }

    @Deprecated
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    @Deprecated
    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
        if (nc != null) {
            return nc.custom;
        }
        return null;
    }

    public void setContentView(int layoutResID) {
        initViewTreeOwners();
        super.setContentView(layoutResID);
    }

    public void setContentView(View view) {
        initViewTreeOwners();
        super.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        initViewTreeOwners();
        super.setContentView(view, params);
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        initViewTreeOwners();
        super.addContentView(view, params);
    }

    private void initViewTreeOwners() {
        ViewTreeLifecycleOwner.set(getWindow().getDecorView(), this);
        ViewTreeViewModelStoreOwner.set(getWindow().getDecorView(), this);
        ViewTreeSavedStateRegistryOwner.set(getWindow().getDecorView(), this);
    }

    public Context peekAvailableContext() {
        return this.mContextAwareHelper.peekAvailableContext();
    }

    public final void addOnContextAvailableListener(OnContextAvailableListener listener) {
        this.mContextAwareHelper.addOnContextAvailableListener(listener);
    }

    public final void removeOnContextAvailableListener(OnContextAvailableListener listener) {
        this.mContextAwareHelper.removeOnContextAvailableListener(listener);
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public ViewModelStore getViewModelStore() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        ensureViewModelStore();
        return this.mViewModelStore;
    }

    void ensureViewModelStore() {
        if (this.mViewModelStore == null) {
            NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
            if (nc != null) {
                this.mViewModelStore = nc.viewModelStore;
            }
            if (this.mViewModelStore == null) {
                this.mViewModelStore = new ViewModelStore();
            }
        }
    }

    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.mDefaultFactory == null) {
            this.mDefaultFactory = new SavedStateViewModelFactory(getApplication(), this, getIntent() != null ? getIntent().getExtras() : null);
        }
        return this.mDefaultFactory;
    }

    public void onBackPressed() {
        this.mOnBackPressedDispatcher.onBackPressed();
    }

    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.mOnBackPressedDispatcher;
    }

    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    @Deprecated
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Deprecated
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Deprecated
    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @Deprecated
    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Deprecated
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!this.mActivityResultRegistry.dispatchResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Deprecated
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (!this.mActivityResultRegistry.dispatchResult(requestCode, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", permissions).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", grantResults)) && Build.VERSION.SDK_INT >= 23) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public final ActivityResultLauncher registerForActivityResult(final ActivityResultContract contract, final ActivityResultRegistry registry, final ActivityResultCallback callback) {
        return registry.register("activity_rq#" + this.mNextLocalRequestCode.getAndIncrement(), this, contract, callback);
    }

    public final ActivityResultLauncher registerForActivityResult(ActivityResultContract contract, ActivityResultCallback callback) {
        return registerForActivityResult(contract, this.mActivityResultRegistry, callback);
    }

    public final ActivityResultRegistry getActivityResultRegistry() {
        return this.mActivityResultRegistry;
    }

    public void reportFullyDrawn() {
        try {
            if (Trace.isEnabled()) {
                Trace.beginSection("reportFullyDrawn() for ComponentActivity");
            }
            if (Build.VERSION.SDK_INT > 19) {
                super.reportFullyDrawn();
            } else if (Build.VERSION.SDK_INT == 19 && ContextCompat.checkSelfPermission(this, "android.permission.UPDATE_DEVICE_STATS") == 0) {
                super.reportFullyDrawn();
            }
        } finally {
            Trace.endSection();
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static void cancelPendingInputEvents(View view) {
            view.cancelPendingInputEvents();
        }
    }
}
