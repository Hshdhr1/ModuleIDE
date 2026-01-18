package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class MenuHostHelper {
    private final Runnable mOnInvalidateMenuCallback;
    private final CopyOnWriteArrayList mMenuProviders = new CopyOnWriteArrayList();
    private final Map mProviderToLifecycleContainers = new HashMap();

    public MenuHostHelper(Runnable onInvalidateMenuCallback) {
        this.mOnInvalidateMenuCallback = onInvalidateMenuCallback;
    }

    public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
        Iterator it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            MenuProvider menuProvider = (MenuProvider) it.next();
            menuProvider.onCreateMenu(menu, menuInflater);
        }
    }

    public boolean onMenuItemSelected(MenuItem item) {
        Iterator it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            MenuProvider menuProvider = (MenuProvider) it.next();
            if (menuProvider.onMenuItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    public void addMenuProvider(MenuProvider provider) {
        this.mMenuProviders.add(provider);
        this.mOnInvalidateMenuCallback.run();
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner) {
        addMenuProvider(provider);
        Lifecycle lifecycle = owner.getLifecycle();
        LifecycleContainer lifecycleContainer = (LifecycleContainer) this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        LifecycleEventObserver observer = new MenuHostHelper$$ExternalSyntheticLambda0(this, provider);
        this.mProviderToLifecycleContainers.put(provider, new LifecycleContainer(lifecycle, observer));
    }

    public /* synthetic */ void lambda$addMenuProvider$0$MenuHostHelper(MenuProvider provider, LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            removeMenuProvider(provider);
        }
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner, Lifecycle.State state) {
        Lifecycle lifecycle = owner.getLifecycle();
        LifecycleContainer lifecycleContainer = (LifecycleContainer) this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        LifecycleEventObserver observer = new MenuHostHelper$$ExternalSyntheticLambda1(this, state, provider);
        this.mProviderToLifecycleContainers.put(provider, new LifecycleContainer(lifecycle, observer));
    }

    public /* synthetic */ void lambda$addMenuProvider$1$MenuHostHelper(Lifecycle.State state, MenuProvider provider, LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.upTo(state)) {
            addMenuProvider(provider);
            return;
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            removeMenuProvider(provider);
        } else if (event == Lifecycle.Event.downFrom(state)) {
            this.mMenuProviders.remove(provider);
            this.mOnInvalidateMenuCallback.run();
        }
    }

    public void removeMenuProvider(MenuProvider provider) {
        this.mMenuProviders.remove(provider);
        LifecycleContainer lifecycleContainer = (LifecycleContainer) this.mProviderToLifecycleContainers.remove(provider);
        if (lifecycleContainer != null) {
            lifecycleContainer.clearObservers();
        }
        this.mOnInvalidateMenuCallback.run();
    }

    private static class LifecycleContainer {
        final Lifecycle mLifecycle;
        private LifecycleEventObserver mObserver;

        LifecycleContainer(Lifecycle lifecycle, LifecycleEventObserver observer) {
            this.mLifecycle = lifecycle;
            this.mObserver = observer;
            lifecycle.addObserver(observer);
        }

        void clearObservers() {
            this.mLifecycle.removeObserver(this.mObserver);
            this.mObserver = null;
        }
    }
}
