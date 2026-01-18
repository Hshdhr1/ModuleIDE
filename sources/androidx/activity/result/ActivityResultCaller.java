package androidx.activity.result;

import androidx.activity.result.contract.ActivityResultContract;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes3.dex */
public interface ActivityResultCaller {
    ActivityResultLauncher registerForActivityResult(ActivityResultContract contract, ActivityResultCallback callback);

    ActivityResultLauncher registerForActivityResult(ActivityResultContract contract, ActivityResultRegistry registry, ActivityResultCallback callback);
}
