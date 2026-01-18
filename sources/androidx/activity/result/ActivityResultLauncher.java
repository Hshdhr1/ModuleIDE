package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public abstract class ActivityResultLauncher {
    public abstract ActivityResultContract getContract();

    public abstract void launch(Object input, ActivityOptionsCompat options);

    public abstract void unregister();

    public void launch(Object input) {
        launch(input, null);
    }
}
