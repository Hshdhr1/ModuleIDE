package androidx.startup;

import android.util.Log;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes29.dex */
public final class StartupLogger {
    static final boolean DEBUG = false;
    private static final String TAG = "StartupLogger";

    private StartupLogger() {
    }

    public static void i(String message) {
        Log.i("StartupLogger", message);
    }

    public static void e(String message, Throwable throwable) {
        Log.e("StartupLogger", message, throwable);
    }
}
