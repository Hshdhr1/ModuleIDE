package androidx.core.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public static boolean hasStartedFromLauncher(Context context) {
        return context.getSharedPreferences("android.support.AppLaunchChecker", 0).getBoolean("startedFromLauncher", false);
    }

    public static void onActivityCreate(Activity activity) {
        Intent launchIntent;
        SharedPreferences sp = activity.getSharedPreferences("android.support.AppLaunchChecker", 0);
        if (!sp.getBoolean("startedFromLauncher", false) && (launchIntent = activity.getIntent()) != null && "android.intent.action.MAIN".equals(launchIntent.getAction())) {
            if (launchIntent.hasCategory("android.intent.category.LAUNCHER") || launchIntent.hasCategory("android.intent.category.LEANBACK_LAUNCHER")) {
                sp.edit().putBoolean("startedFromLauncher", true).apply();
            }
        }
    }

    @Deprecated
    public AppLaunchChecker() {
    }
}
