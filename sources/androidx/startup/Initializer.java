package androidx.startup;

import android.content.Context;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes29.dex */
public interface Initializer {
    Object create(Context context);

    List dependencies();
}
