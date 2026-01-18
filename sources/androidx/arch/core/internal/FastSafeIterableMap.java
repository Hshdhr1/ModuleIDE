package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes11.dex */
public class FastSafeIterableMap extends SafeIterableMap {
    private HashMap mHashMap = new HashMap();

    protected SafeIterableMap.Entry get(Object obj) {
        return (SafeIterableMap.Entry) this.mHashMap.get(obj);
    }

    public Object putIfAbsent(@NonNull Object obj, @NonNull Object obj2) {
        SafeIterableMap.Entry entry = get(obj);
        if (entry == null) {
            this.mHashMap.put(obj, put(obj, obj2));
            return null;
        }
        return entry.mValue;
    }

    public Object remove(@NonNull Object obj) {
        Object remove = super.remove(obj);
        this.mHashMap.remove(obj);
        return remove;
    }

    public boolean contains(Object obj) {
        return this.mHashMap.containsKey(obj);
    }

    public Map.Entry ceil(Object obj) {
        if (contains(obj)) {
            return ((SafeIterableMap.Entry) this.mHashMap.get(obj)).mPrevious;
        }
        return null;
    }
}
