package androidx.lifecycle;

import android.app.Application;
import java.lang.reflect.InvocationTargetException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes15.dex */
public class ViewModelProvider {
    private static final String DEFAULT_KEY = "androidx.lifecycle.ViewModelProvider.DefaultKey";
    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;

    public interface Factory {
        ViewModel create(Class cls);
    }

    static class OnRequeryFactory {
        OnRequeryFactory() {
        }

        void onRequery(ViewModel viewModel) {
        }
    }

    static abstract class KeyedFactory extends OnRequeryFactory implements Factory {
        public abstract ViewModel create(String str, Class cls);

        KeyedFactory() {
        }

        public ViewModel create(Class cls) {
            throw new UnsupportedOperationException("create(String, Class<?>) must be called on implementaions of KeyedFactory");
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ViewModelProvider(ViewModelStoreOwner owner) {
        Factory newInstanceFactory;
        ViewModelStore viewModelStore = owner.getViewModelStore();
        if (owner instanceof HasDefaultViewModelProviderFactory) {
            newInstanceFactory = ((HasDefaultViewModelProviderFactory) owner).getDefaultViewModelProviderFactory();
        } else {
            newInstanceFactory = NewInstanceFactory.getInstance();
        }
        this(viewModelStore, newInstanceFactory);
    }

    public ViewModelProvider(ViewModelStoreOwner owner, Factory factory) {
        this(owner.getViewModelStore(), factory);
    }

    public ViewModelProvider(ViewModelStore store, Factory factory) {
        this.mFactory = factory;
        this.mViewModelStore = store;
    }

    public ViewModel get(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return get("androidx.lifecycle.ViewModelProvider.DefaultKey:" + canonicalName, cls);
    }

    public ViewModel get(String key, Class cls) {
        ViewModel viewModel;
        ViewModel viewModel2 = this.mViewModelStore.get(key);
        if (cls.isInstance(viewModel2)) {
            Object obj = this.mFactory;
            if (obj instanceof OnRequeryFactory) {
                ((OnRequeryFactory) obj).onRequery(viewModel2);
            }
            return viewModel2;
        }
        Factory factory = this.mFactory;
        if (factory instanceof KeyedFactory) {
            viewModel = ((KeyedFactory) factory).create(key, cls);
        } else {
            viewModel = factory.create(cls);
        }
        this.mViewModelStore.put(key, viewModel);
        return viewModel;
    }

    public static class NewInstanceFactory implements Factory {
        private static NewInstanceFactory sInstance;

        static NewInstanceFactory getInstance() {
            if (sInstance == null) {
                sInstance = new NewInstanceFactory();
            }
            return sInstance;
        }

        public ViewModel create(Class cls) {
            try {
                return (ViewModel) cls.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + cls, e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Cannot create an instance of " + cls, e2);
            }
        }
    }

    public static class AndroidViewModelFactory extends NewInstanceFactory {
        private static AndroidViewModelFactory sInstance;
        private Application mApplication;

        public static AndroidViewModelFactory getInstance(Application application) {
            if (sInstance == null) {
                sInstance = new AndroidViewModelFactory(application);
            }
            return sInstance;
        }

        public AndroidViewModelFactory(Application application) {
            this.mApplication = application;
        }

        public ViewModel create(Class cls) {
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                try {
                    return (ViewModel) cls.getConstructor(new Class[]{Application.class}).newInstance(new Object[]{this.mApplication});
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e);
                } catch (IllegalAccessException e2) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e2);
                } catch (NoSuchMethodException e3) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e3);
                } catch (InstantiationException e4) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e4);
                }
            }
            return super.create(cls);
        }
    }
}
