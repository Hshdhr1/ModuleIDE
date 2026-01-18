package androidx.savedstate;

import android.os.Bundle;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.Recreator;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes17.dex */
public final class SavedStateRegistry {
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private Recreator.SavedStateProvider mRecreatorProvider;
    private boolean mRestored;
    private Bundle mRestoredState;
    private SafeIterableMap mComponents = new SafeIterableMap();
    boolean mAllowingSavingState = true;

    public interface AutoRecreated {
        void onRecreated(SavedStateRegistryOwner savedStateRegistryOwner);
    }

    public interface SavedStateProvider {
        Bundle saveState();
    }

    SavedStateRegistry() {
    }

    public Bundle consumeRestoredStateForKey(String key) {
        if (!this.mRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
        }
        Bundle bundle = this.mRestoredState;
        if (bundle == null) {
            return null;
        }
        Bundle result = bundle.getBundle(key);
        this.mRestoredState.remove(key);
        if (this.mRestoredState.isEmpty()) {
            this.mRestoredState = null;
        }
        return result;
    }

    public void registerSavedStateProvider(String key, SavedStateProvider provider) {
        SavedStateProvider previous = (SavedStateProvider) this.mComponents.putIfAbsent(key, provider);
        if (previous != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    public void unregisterSavedStateProvider(String key) {
        this.mComponents.remove(key);
    }

    public boolean isRestored() {
        return this.mRestored;
    }

    public void runOnNextRecreation(Class cls) {
        if (!this.mAllowingSavingState) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mRecreatorProvider == null) {
            this.mRecreatorProvider = new Recreator.SavedStateProvider(this);
        }
        try {
            cls.getDeclaredConstructor(new Class[0]);
            this.mRecreatorProvider.add(cls.getName());
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class" + cls.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
        }
    }

    void performRestore(Lifecycle lifecycle, Bundle savedState) {
        if (this.mRestored) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        if (savedState != null) {
            this.mRestoredState = savedState.getBundle("androidx.lifecycle.BundlableSavedStateRegistry.key");
        }
        lifecycle.addObserver(new 1());
        this.mRestored = true;
    }

    class 1 implements GenericLifecycleObserver {
        1() {
        }

        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                SavedStateRegistry.this.mAllowingSavingState = true;
            } else if (event == Lifecycle.Event.ON_STOP) {
                SavedStateRegistry.this.mAllowingSavingState = false;
            }
        }
    }

    void performSave(Bundle outBundle) {
        Bundle components = new Bundle();
        Bundle bundle = this.mRestoredState;
        if (bundle != null) {
            components.putAll(bundle);
        }
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mComponents.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry<String, SavedStateProvider> entry1 = (Map.Entry) iteratorWithAdditions.next();
            components.putBundle((String) entry1.getKey(), ((SavedStateProvider) entry1.getValue()).saveState());
        }
        outBundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", components);
    }
}
