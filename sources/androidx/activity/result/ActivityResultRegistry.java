package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public abstract class ActivityResultRegistry {
    private static final int INITIAL_REQUEST_CODE_VALUE = 65536;
    private static final String KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS = "KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS";
    private static final String KEY_COMPONENT_ACTIVITY_PENDING_RESULTS = "KEY_COMPONENT_ACTIVITY_PENDING_RESULT";
    private static final String KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT = "KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS = "KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS";
    private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_RCS = "KEY_COMPONENT_ACTIVITY_REGISTERED_RCS";
    private static final String LOG_TAG = "ActivityResultRegistry";
    private Random mRandom = new Random();
    private final Map mRcToKey = new HashMap();
    final Map mKeyToRc = new HashMap();
    private final Map mKeyToLifecycleContainers = new HashMap();
    ArrayList mLaunchedKeys = new ArrayList();
    final transient Map mKeyToCallback = new HashMap();
    final Map mParsedPendingResults = new HashMap();
    final Bundle mPendingResults = new Bundle();

    public abstract void onLaunch(int requestCode, ActivityResultContract contract, Object input, ActivityOptionsCompat options);

    public final ActivityResultLauncher register(final String key, final LifecycleOwner lifecycleOwner, final ActivityResultContract contract, final ActivityResultCallback callback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException("LifecycleOwner " + lifecycleOwner + " is attempting to register while current state is " + lifecycle.getCurrentState() + ". LifecycleOwners must call register before they are STARTED.");
        }
        int requestCode = registerKey(key);
        LifecycleContainer lifecycleContainer = (LifecycleContainer) this.mKeyToLifecycleContainers.get(key);
        if (lifecycleContainer == null) {
            lifecycleContainer = new LifecycleContainer(lifecycle);
        }
        LifecycleEventObserver observer = new 1(key, callback, contract);
        lifecycleContainer.addObserver(observer);
        this.mKeyToLifecycleContainers.put(key, lifecycleContainer);
        return new 2(key, requestCode, contract);
    }

    class 1 implements LifecycleEventObserver {
        final /* synthetic */ ActivityResultCallback val$callback;
        final /* synthetic */ ActivityResultContract val$contract;
        final /* synthetic */ String val$key;

        1(final String val$key, final ActivityResultCallback val$callback, final ActivityResultContract val$contract) {
            this.val$key = val$key;
            this.val$callback = val$callback;
            this.val$contract = val$contract;
        }

        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (!Lifecycle.Event.ON_START.equals(event)) {
                if (Lifecycle.Event.ON_STOP.equals(event)) {
                    ActivityResultRegistry.this.mKeyToCallback.remove(this.val$key);
                    return;
                } else {
                    if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                        ActivityResultRegistry.this.unregister(this.val$key);
                        return;
                    }
                    return;
                }
            }
            ActivityResultRegistry.this.mKeyToCallback.put(this.val$key, new CallbackAndContract(this.val$callback, this.val$contract));
            if (ActivityResultRegistry.this.mParsedPendingResults.containsKey(this.val$key)) {
                Object obj = ActivityResultRegistry.this.mParsedPendingResults.get(this.val$key);
                ActivityResultRegistry.this.mParsedPendingResults.remove(this.val$key);
                this.val$callback.onActivityResult(obj);
            }
            ActivityResult pendingResult = (ActivityResult) ActivityResultRegistry.this.mPendingResults.getParcelable(this.val$key);
            if (pendingResult != null) {
                ActivityResultRegistry.this.mPendingResults.remove(this.val$key);
                this.val$callback.onActivityResult(this.val$contract.parseResult(pendingResult.getResultCode(), pendingResult.getData()));
            }
        }
    }

    class 2 extends ActivityResultLauncher {
        final /* synthetic */ ActivityResultContract val$contract;
        final /* synthetic */ String val$key;
        final /* synthetic */ int val$requestCode;

        2(final String val$key, final int val$requestCode, final ActivityResultContract val$contract) {
            this.val$key = val$key;
            this.val$requestCode = val$requestCode;
            this.val$contract = val$contract;
        }

        public void launch(Object input, ActivityOptionsCompat options) {
            ActivityResultRegistry.this.mLaunchedKeys.add(this.val$key);
            Integer innerCode = (Integer) ActivityResultRegistry.this.mKeyToRc.get(this.val$key);
            ActivityResultRegistry.this.onLaunch(innerCode != null ? innerCode.intValue() : this.val$requestCode, this.val$contract, input, options);
        }

        public void unregister() {
            ActivityResultRegistry.this.unregister(this.val$key);
        }

        public ActivityResultContract getContract() {
            return this.val$contract;
        }
    }

    public final ActivityResultLauncher register(final String key, final ActivityResultContract contract, final ActivityResultCallback callback) {
        int requestCode = registerKey(key);
        this.mKeyToCallback.put(key, new CallbackAndContract(callback, contract));
        if (this.mParsedPendingResults.containsKey(key)) {
            Object obj = this.mParsedPendingResults.get(key);
            this.mParsedPendingResults.remove(key);
            callback.onActivityResult(obj);
        }
        ActivityResult pendingResult = (ActivityResult) this.mPendingResults.getParcelable(key);
        if (pendingResult != null) {
            this.mPendingResults.remove(key);
            callback.onActivityResult(contract.parseResult(pendingResult.getResultCode(), pendingResult.getData()));
        }
        return new 3(key, requestCode, contract);
    }

    class 3 extends ActivityResultLauncher {
        final /* synthetic */ ActivityResultContract val$contract;
        final /* synthetic */ String val$key;
        final /* synthetic */ int val$requestCode;

        3(final String val$key, final int val$requestCode, final ActivityResultContract val$contract) {
            this.val$key = val$key;
            this.val$requestCode = val$requestCode;
            this.val$contract = val$contract;
        }

        public void launch(Object input, ActivityOptionsCompat options) {
            ActivityResultRegistry.this.mLaunchedKeys.add(this.val$key);
            Integer innerCode = (Integer) ActivityResultRegistry.this.mKeyToRc.get(this.val$key);
            ActivityResultRegistry.this.onLaunch(innerCode != null ? innerCode.intValue() : this.val$requestCode, this.val$contract, input, options);
        }

        public void unregister() {
            ActivityResultRegistry.this.unregister(this.val$key);
        }

        public ActivityResultContract getContract() {
            return this.val$contract;
        }
    }

    final void unregister(String key) {
        Integer rc;
        if (!this.mLaunchedKeys.contains(key) && (rc = (Integer) this.mKeyToRc.remove(key)) != null) {
            this.mRcToKey.remove(rc);
        }
        this.mKeyToCallback.remove(key);
        if (this.mParsedPendingResults.containsKey(key)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + key + ": " + this.mParsedPendingResults.get(key));
            this.mParsedPendingResults.remove(key);
        }
        if (this.mPendingResults.containsKey(key)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + key + ": " + this.mPendingResults.getParcelable(key));
            this.mPendingResults.remove(key);
        }
        LifecycleContainer lifecycleContainer = (LifecycleContainer) this.mKeyToLifecycleContainers.get(key);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
            this.mKeyToLifecycleContainers.remove(key);
        }
    }

    public final void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList(this.mKeyToRc.values()));
        outState.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList(this.mKeyToRc.keySet()));
        outState.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList(this.mLaunchedKeys));
        outState.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) this.mPendingResults.clone());
        outState.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", this.mRandom);
    }

    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        ArrayList<Integer> rcs = savedInstanceState.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
        ArrayList<String> keys = savedInstanceState.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
        if (keys == null || rcs == null) {
            return;
        }
        this.mLaunchedKeys = savedInstanceState.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
        this.mRandom = savedInstanceState.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT");
        this.mPendingResults.putAll(savedInstanceState.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT"));
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if (this.mKeyToRc.containsKey(key)) {
                Integer newRequestCode = (Integer) this.mKeyToRc.remove(key);
                if (!this.mPendingResults.containsKey(key)) {
                    this.mRcToKey.remove(newRequestCode);
                }
            }
            bindRcKey(((Integer) rcs.get(i)).intValue(), (String) keys.get(i));
        }
    }

    public final boolean dispatchResult(int requestCode, int resultCode, Intent data) {
        String key = (String) this.mRcToKey.get(Integer.valueOf(requestCode));
        if (key == null) {
            return false;
        }
        this.mLaunchedKeys.remove(key);
        doDispatch(key, resultCode, data, (CallbackAndContract) this.mKeyToCallback.get(key));
        return true;
    }

    public final boolean dispatchResult(int requestCode, Object result) {
        String key = (String) this.mRcToKey.get(Integer.valueOf(requestCode));
        if (key == null) {
            return false;
        }
        this.mLaunchedKeys.remove(key);
        CallbackAndContract<?> callbackAndContract = (CallbackAndContract) this.mKeyToCallback.get(key);
        if (callbackAndContract == null || callbackAndContract.mCallback == null) {
            this.mPendingResults.remove(key);
            this.mParsedPendingResults.put(key, result);
            return true;
        }
        callbackAndContract.mCallback.onActivityResult(result);
        return true;
    }

    private void doDispatch(String key, int resultCode, Intent data, CallbackAndContract callbackAndContract) {
        if (callbackAndContract != null && callbackAndContract.mCallback != null) {
            callbackAndContract.mCallback.onActivityResult(callbackAndContract.mContract.parseResult(resultCode, data));
        } else {
            this.mParsedPendingResults.remove(key);
            this.mPendingResults.putParcelable(key, new ActivityResult(resultCode, data));
        }
    }

    private int registerKey(String key) {
        Integer existing = (Integer) this.mKeyToRc.get(key);
        if (existing != null) {
            return existing.intValue();
        }
        int rc = generateRandomNumber();
        bindRcKey(rc, key);
        return rc;
    }

    private int generateRandomNumber() {
        int number = this.mRandom.nextInt(2147418112) + 65536;
        while (this.mRcToKey.containsKey(Integer.valueOf(number))) {
            number = this.mRandom.nextInt(2147418112) + 65536;
        }
        return number;
    }

    private void bindRcKey(int rc, String key) {
        this.mRcToKey.put(Integer.valueOf(rc), key);
        this.mKeyToRc.put(key, Integer.valueOf(rc));
    }

    private static class CallbackAndContract {
        final ActivityResultCallback mCallback;
        final ActivityResultContract mContract;

        CallbackAndContract(ActivityResultCallback callback, ActivityResultContract contract) {
            this.mCallback = callback;
            this.mContract = contract;
        }
    }

    private static class LifecycleContainer {
        final Lifecycle mLifecycle;
        private final ArrayList mObservers = new ArrayList();

        LifecycleContainer(Lifecycle lifecycle) {
            this.mLifecycle = lifecycle;
        }

        void addObserver(LifecycleEventObserver observer) {
            this.mLifecycle.addObserver(observer);
            this.mObservers.add(observer);
        }

        void clearObservers() {
            Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                LifecycleEventObserver observer = (LifecycleEventObserver) it.next();
                this.mLifecycle.removeObserver(observer);
            }
            this.mObservers.clear();
        }
    }
}
