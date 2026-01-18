package androidx.lifecycle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes15.dex */
public class ViewModelStore {
    private final HashMap mMap = new HashMap();

    final void put(String key, ViewModel viewModel) {
        ViewModel oldViewModel = (ViewModel) this.mMap.put(key, viewModel);
        if (oldViewModel != null) {
            oldViewModel.onCleared();
        }
    }

    final ViewModel get(String key) {
        return (ViewModel) this.mMap.get(key);
    }

    Set keys() {
        return new HashSet(this.mMap.keySet());
    }

    public final void clear() {
        for (ViewModel vm : this.mMap.values()) {
            vm.clear();
        }
        this.mMap.clear();
    }
}
