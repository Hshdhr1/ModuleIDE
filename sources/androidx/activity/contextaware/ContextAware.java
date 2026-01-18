package androidx.activity.contextaware;

import android.content.Context;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public interface ContextAware {
    void addOnContextAvailableListener(OnContextAvailableListener listener);

    Context peekAvailableContext();

    void removeOnContextAvailableListener(OnContextAvailableListener listener);
}
