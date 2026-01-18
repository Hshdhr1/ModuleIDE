package androidx.loader.app;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.collection.SparseArrayCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes32.dex */
class LoaderManagerImpl extends LoaderManager {
    static boolean DEBUG = false;
    private static final String TAG = "LoaderManager";
    private final LifecycleOwner mLifecycleOwner;
    private final LoaderViewModel mLoaderViewModel;

    public static class LoaderInfo extends MutableLiveData implements Loader.OnLoadCompleteListener {
        private final Bundle mArgs;
        private final int mId;
        private LifecycleOwner mLifecycleOwner;
        private final Loader mLoader;
        private LoaderObserver mObserver;
        private Loader mPriorLoader;

        LoaderInfo(int id, Bundle args, Loader loader, Loader loader2) {
            this.mId = id;
            this.mArgs = args;
            this.mLoader = loader;
            this.mPriorLoader = loader2;
            loader.registerListener(id, this);
        }

        Loader getLoader() {
            return this.mLoader;
        }

        protected void onActive() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            this.mLoader.startLoading();
        }

        protected void onInactive() {
            if (LoaderManagerImpl.DEBUG) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            this.mLoader.stopLoading();
        }

        Loader setCallback(LifecycleOwner owner, LoaderManager.LoaderCallbacks loaderCallbacks) {
            LoaderObserver loaderObserver = new LoaderObserver(this.mLoader, loaderCallbacks);
            observe(owner, loaderObserver);
            Observer observer = this.mObserver;
            if (observer != null) {
                removeObserver(observer);
            }
            this.mLifecycleOwner = owner;
            this.mObserver = loaderObserver;
            return this.mLoader;
        }

        void markForRedelivery() {
            LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
            LoaderObserver loaderObserver = this.mObserver;
            if (lifecycleOwner != null && loaderObserver != null) {
                super.removeObserver(loaderObserver);
                observe(lifecycleOwner, loaderObserver);
            }
        }

        boolean isCallbackWaitingForData() {
            LoaderObserver loaderObserver;
            return (!hasActiveObservers() || (loaderObserver = this.mObserver) == null || loaderObserver.hasDeliveredData()) ? false : true;
        }

        public void removeObserver(Observer observer) {
            super.removeObserver(observer);
            this.mLifecycleOwner = null;
            this.mObserver = null;
        }

        Loader destroy(boolean reset) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v("LoaderManager", "  Destroying: " + this);
            }
            this.mLoader.cancelLoad();
            this.mLoader.abandon();
            LoaderObserver loaderObserver = this.mObserver;
            if (loaderObserver != null) {
                removeObserver(loaderObserver);
                if (reset) {
                    loaderObserver.reset();
                }
            }
            this.mLoader.unregisterListener(this);
            if ((loaderObserver != null && !loaderObserver.hasDeliveredData()) || reset) {
                this.mLoader.reset();
                return this.mPriorLoader;
            }
            return this.mLoader;
        }

        public void onLoadComplete(Loader loader, Object obj) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v("LoaderManager", "onLoadComplete: " + this);
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                setValue(obj);
                return;
            }
            if (LoaderManagerImpl.DEBUG) {
                Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread");
            }
            postValue(obj);
        }

        public void setValue(Object obj) {
            super.setValue(obj);
            Loader loader = this.mPriorLoader;
            if (loader != null) {
                loader.reset();
                this.mPriorLoader = null;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.mId);
            sb.append(" : ");
            Class cls = this.mLoader.getClass();
            sb.append(cls.getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(cls)));
            sb.append("}}");
            return sb.toString();
        }

        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            writer.print(prefix);
            writer.print("mId=");
            writer.print(this.mId);
            writer.print(" mArgs=");
            writer.println(this.mArgs);
            writer.print(prefix);
            writer.print("mLoader=");
            writer.println(this.mLoader);
            this.mLoader.dump(prefix + "  ", fd, writer, args);
            if (this.mObserver != null) {
                writer.print(prefix);
                writer.print("mCallbacks=");
                writer.println(this.mObserver);
                this.mObserver.dump(prefix + "  ", writer);
            }
            writer.print(prefix);
            writer.print("mData=");
            writer.println(getLoader().dataToString(getValue()));
            writer.print(prefix);
            writer.print("mStarted=");
            writer.println(hasActiveObservers());
        }
    }

    static class LoaderObserver implements Observer {
        private final LoaderManager.LoaderCallbacks mCallback;
        private boolean mDeliveredData = false;
        private final Loader mLoader;

        LoaderObserver(Loader loader, LoaderManager.LoaderCallbacks loaderCallbacks) {
            this.mLoader = loader;
            this.mCallback = loaderCallbacks;
        }

        public void onChanged(Object obj) {
            if (LoaderManagerImpl.DEBUG) {
                Log.v("LoaderManager", "  onLoadFinished in " + this.mLoader + ": " + this.mLoader.dataToString(obj));
            }
            this.mDeliveredData = true;
            this.mCallback.onLoadFinished(this.mLoader, obj);
        }

        boolean hasDeliveredData() {
            return this.mDeliveredData;
        }

        void reset() {
            if (this.mDeliveredData) {
                if (LoaderManagerImpl.DEBUG) {
                    Log.v("LoaderManager", "  Resetting: " + this.mLoader);
                }
                this.mCallback.onLoaderReset(this.mLoader);
            }
        }

        public String toString() {
            return this.mCallback.toString();
        }

        public void dump(String prefix, PrintWriter writer) {
            writer.print(prefix);
            writer.print("mDeliveredData=");
            writer.println(this.mDeliveredData);
        }
    }

    static class LoaderViewModel extends ViewModel {
        private static final ViewModelProvider.Factory FACTORY = new 1();
        private SparseArrayCompat mLoaders = new SparseArrayCompat();
        private boolean mCreatingLoader = false;

        static class 1 implements ViewModelProvider.Factory {
            1() {
            }

            public ViewModel create(Class cls) {
                return new LoaderViewModel();
            }
        }

        LoaderViewModel() {
        }

        static LoaderViewModel getInstance(ViewModelStore viewModelStore) {
            return (LoaderViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(LoaderViewModel.class);
        }

        void startCreatingLoader() {
            this.mCreatingLoader = true;
        }

        boolean isCreatingLoader() {
            return this.mCreatingLoader;
        }

        void finishCreatingLoader() {
            this.mCreatingLoader = false;
        }

        void putLoader(int id, LoaderInfo info) {
            this.mLoaders.put(id, info);
        }

        LoaderInfo getLoader(int id) {
            return (LoaderInfo) this.mLoaders.get(id);
        }

        void removeLoader(int id) {
            this.mLoaders.remove(id);
        }

        boolean hasRunningLoaders() {
            int size = this.mLoaders.size();
            for (int index = 0; index < size; index++) {
                LoaderInfo info = (LoaderInfo) this.mLoaders.valueAt(index);
                if (info.isCallbackWaitingForData()) {
                    return true;
                }
            }
            return false;
        }

        void markForRedelivery() {
            int size = this.mLoaders.size();
            for (int index = 0; index < size; index++) {
                LoaderInfo info = (LoaderInfo) this.mLoaders.valueAt(index);
                info.markForRedelivery();
            }
        }

        protected void onCleared() {
            super.onCleared();
            int size = this.mLoaders.size();
            for (int index = 0; index < size; index++) {
                LoaderInfo info = (LoaderInfo) this.mLoaders.valueAt(index);
                info.destroy(true);
            }
            this.mLoaders.clear();
        }

        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            if (this.mLoaders.size() > 0) {
                writer.print(prefix);
                writer.println("Loaders:");
                String innerPrefix = prefix + "    ";
                for (int i = 0; i < this.mLoaders.size(); i++) {
                    LoaderInfo info = (LoaderInfo) this.mLoaders.valueAt(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(this.mLoaders.keyAt(i));
                    writer.print(": ");
                    writer.println(info.toString());
                    info.dump(innerPrefix, fd, writer, args);
                }
            }
        }
    }

    LoaderManagerImpl(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mLoaderViewModel = LoaderViewModel.getInstance(viewModelStore);
    }

    private Loader createAndInstallLoader(int id, Bundle args, LoaderManager.LoaderCallbacks loaderCallbacks, Loader loader) {
        try {
            this.mLoaderViewModel.startCreatingLoader();
            Loader onCreateLoader = loaderCallbacks.onCreateLoader(id, args);
            if (onCreateLoader == null) {
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be null");
            }
            if (onCreateLoader.getClass().isMemberClass() && !Modifier.isStatic(onCreateLoader.getClass().getModifiers())) {
                throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + onCreateLoader);
            }
            LoaderInfo loaderInfo = new LoaderInfo(id, args, onCreateLoader, loader);
            if (DEBUG) {
                Log.v("LoaderManager", "  Created new loader " + loaderInfo);
            }
            this.mLoaderViewModel.putLoader(id, loaderInfo);
            this.mLoaderViewModel.finishCreatingLoader();
            return loaderInfo.setCallback(this.mLifecycleOwner, loaderCallbacks);
        } catch (Throwable th) {
            this.mLoaderViewModel.finishCreatingLoader();
            throw th;
        }
    }

    public Loader initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks loaderCallbacks) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("initLoader must be called on the main thread");
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(id);
        if (DEBUG) {
            Log.v("LoaderManager", "initLoader in " + this + ": args=" + args);
        }
        if (loader == null) {
            return createAndInstallLoader(id, args, loaderCallbacks, null);
        }
        if (DEBUG) {
            Log.v("LoaderManager", "  Re-using existing loader " + loader);
        }
        return loader.setCallback(this.mLifecycleOwner, loaderCallbacks);
    }

    public Loader restartLoader(int id, Bundle args, LoaderManager.LoaderCallbacks loaderCallbacks) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("restartLoader must be called on the main thread");
        }
        if (DEBUG) {
            Log.v("LoaderManager", "restartLoader in " + this + ": args=" + args);
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(id);
        Loader loader2 = null;
        if (loader != null) {
            loader2 = loader.destroy(false);
        }
        return createAndInstallLoader(id, args, loaderCallbacks, loader2);
    }

    public void destroyLoader(int id) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("destroyLoader must be called on the main thread");
        }
        if (DEBUG) {
            Log.v("LoaderManager", "destroyLoader in " + this + " of " + id);
        }
        LoaderInfo info = this.mLoaderViewModel.getLoader(id);
        if (info != null) {
            info.destroy(true);
            this.mLoaderViewModel.removeLoader(id);
        }
    }

    public Loader getLoader(int id) {
        if (this.mLoaderViewModel.isCreatingLoader()) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loader = this.mLoaderViewModel.getLoader(id);
        if (loader != null) {
            return loader.getLoader();
        }
        return null;
    }

    public void markForRedelivery() {
        this.mLoaderViewModel.markForRedelivery();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Class cls = this.mLifecycleOwner.getClass();
        sb.append(cls.getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(cls)));
        sb.append("}}");
        return sb.toString();
    }

    @Deprecated
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        this.mLoaderViewModel.dump(prefix, fd, writer, args);
    }

    public boolean hasRunningLoaders() {
        return this.mLoaderViewModel.hasRunningLoaders();
    }
}
