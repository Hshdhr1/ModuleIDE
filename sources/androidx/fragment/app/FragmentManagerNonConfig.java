package androidx.fragment.app;

import java.util.Collection;
import java.util.Map;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public class FragmentManagerNonConfig {
    private final Map mChildNonConfigs;
    private final Collection mFragments;
    private final Map mViewModelStores;

    FragmentManagerNonConfig(Collection collection, Map map, Map map2) {
        this.mFragments = collection;
        this.mChildNonConfigs = map;
        this.mViewModelStores = map2;
    }

    boolean isRetaining(Fragment f) {
        Collection collection = this.mFragments;
        if (collection == null) {
            return false;
        }
        return collection.contains(f);
    }

    Collection getFragments() {
        return this.mFragments;
    }

    Map getChildNonConfigs() {
        return this.mChildNonConfigs;
    }

    Map getViewModelStores() {
        return this.mViewModelStores;
    }
}
