package androidx.lifecycle;

import android.content.Context;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes28.dex */
public final class ProcessLifecycleInitializer implements Initializer {
    public LifecycleOwner create(Context context) {
        LifecycleDispatcher.init(context);
        ProcessLifecycleOwner.init(context);
        return ProcessLifecycleOwner.get();
    }

    public List dependencies() {
        return Collections.emptyList();
    }
}
