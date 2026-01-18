package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes16.dex */
public final class SavedStateViewModelFactory extends ViewModelProvider.KeyedFactory {
    private static final Class[] ANDROID_VIEWMODEL_SIGNATURE = {Application.class, SavedStateHandle.class};
    private static final Class[] VIEWMODEL_SIGNATURE = {SavedStateHandle.class};
    private final Application mApplication;
    private final Bundle mDefaultArgs;
    private final ViewModelProvider.Factory mFactory;
    private final Lifecycle mLifecycle;
    private final SavedStateRegistry mSavedStateRegistry;

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner) {
        this(application, owner, null);
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner, Bundle defaultArgs) {
        ViewModelProvider.AndroidViewModelFactory newInstanceFactory;
        this.mSavedStateRegistry = owner.getSavedStateRegistry();
        this.mLifecycle = owner.getLifecycle();
        this.mDefaultArgs = defaultArgs;
        this.mApplication = application;
        if (application != null) {
            newInstanceFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        } else {
            newInstanceFactory = ViewModelProvider.NewInstanceFactory.getInstance();
        }
        this.mFactory = newInstanceFactory;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046 A[Catch: InvocationTargetException -> 0x005a, InstantiationException -> 0x0076, IllegalAccessException -> 0x0093, TryCatch #2 {InvocationTargetException -> 0x005a, IllegalAccessException -> 0x0093, InstantiationException -> 0x0076, blocks: (B:26:0x0030, B:28:0x0034, B:13:0x0054, B:12:0x0046), top: B:25:0x0030 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.lifecycle.ViewModel create(java.lang.String r8, java.lang.Class r9) {
        /*
            r7 = this;
            java.lang.Class<androidx.lifecycle.AndroidViewModel> r0 = androidx.lifecycle.AndroidViewModel.class
            boolean r0 = r0.isAssignableFrom(r9)
            if (r0 == 0) goto L13
            android.app.Application r1 = r7.mApplication
            if (r1 == 0) goto L13
            java.lang.Class[] r1 = androidx.lifecycle.SavedStateViewModelFactory.ANDROID_VIEWMODEL_SIGNATURE
            java.lang.reflect.Constructor r1 = findMatchingConstructor(r9, r1)
            goto L19
        L13:
            java.lang.Class[] r1 = androidx.lifecycle.SavedStateViewModelFactory.VIEWMODEL_SIGNATURE
            java.lang.reflect.Constructor r1 = findMatchingConstructor(r9, r1)
        L19:
            if (r1 != 0) goto L22
            androidx.lifecycle.ViewModelProvider$Factory r2 = r7.mFactory
            androidx.lifecycle.ViewModel r2 = r2.create(r9)
            return r2
        L22:
            androidx.savedstate.SavedStateRegistry r2 = r7.mSavedStateRegistry
            androidx.lifecycle.Lifecycle r3 = r7.mLifecycle
            android.os.Bundle r4 = r7.mDefaultArgs
            androidx.lifecycle.SavedStateHandleController r2 = androidx.lifecycle.SavedStateHandleController.create(r2, r3, r8, r4)
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L46
            android.app.Application r5 = r7.mApplication     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            if (r5 == 0) goto L46
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            r6[r3] = r5     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            androidx.lifecycle.SavedStateHandle r3 = r2.getHandle()     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            r6[r4] = r3     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            java.lang.Object r3 = r1.newInstance(r6)     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            androidx.lifecycle.ViewModel r3 = (androidx.lifecycle.ViewModel) r3     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            goto L54
        L46:
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            androidx.lifecycle.SavedStateHandle r5 = r2.getHandle()     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            r4[r3] = r5     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            java.lang.Object r3 = r1.newInstance(r4)     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            androidx.lifecycle.ViewModel r3 = (androidx.lifecycle.ViewModel) r3     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
        L54:
            java.lang.String r4 = "androidx.lifecycle.savedstate.vm.tag"
            r3.setTagIfAbsent(r4, r2)     // Catch: java.lang.reflect.InvocationTargetException -> L5a java.lang.InstantiationException -> L76 java.lang.IllegalAccessException -> L93
            return r3
        L5a:
            r3 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "An exception happened in constructor of "
            r5.append(r6)
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            java.lang.Throwable r6 = r3.getCause()
            r4.<init>(r5, r6)
            throw r4
        L76:
            r3 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "A "
            r5.append(r6)
            r5.append(r9)
            java.lang.String r6 = " cannot be instantiated."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r3)
            throw r4
        L93:
            r3 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to access "
            r5.append(r6)
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5, r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.SavedStateViewModelFactory.create(java.lang.String, java.lang.Class):androidx.lifecycle.ViewModel");
    }

    public ViewModel create(Class modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return create(canonicalName, modelClass);
    }

    private static Constructor findMatchingConstructor(Class modelClass, Class[] signature) {
        for (Constructor<?> constructor : modelClass.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (Arrays.equals(signature, parameterTypes)) {
                return constructor;
            }
        }
        return null;
    }

    void onRequery(ViewModel viewModel) {
        SavedStateHandleController.attachHandleIfNeeded(viewModel, this.mSavedStateRegistry, this.mLifecycle);
    }
}
