package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes15.dex */
public abstract class ViewModel {
    private final Map mBagOfTags = new HashMap();
    private volatile boolean mCleared = false;

    protected void onCleared() {
    }

    final void clear() {
        this.mCleared = true;
        Map map = this.mBagOfTags;
        if (map != null) {
            synchronized (map) {
                for (Object value : this.mBagOfTags.values()) {
                    closeWithRuntimeException(value);
                }
            }
        }
        onCleared();
    }

    Object setTagIfAbsent(String key, Object obj) {
        Object obj2;
        synchronized (this.mBagOfTags) {
            obj2 = this.mBagOfTags.get(key);
            if (obj2 == null) {
                this.mBagOfTags.put(key, obj);
            }
        }
        Object obj3 = obj2 == null ? obj : obj2;
        if (this.mCleared) {
            closeWithRuntimeException(obj3);
        }
        return obj3;
    }

    Object getTag(String key) {
        Object obj;
        Map map = this.mBagOfTags;
        if (map == null) {
            return null;
        }
        synchronized (map) {
            obj = this.mBagOfTags.get(key);
        }
        return obj;
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
