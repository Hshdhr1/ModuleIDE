package ru.yufic.exteraPlugins;

import android.app.Application;
import android.content.Context;
import com.google.android.material.color.DynamicColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class SketchApplication extends Application {
    private static Context a;

    public void onCreate() {
        a = getApplicationContext();
        DynamicColors.applyToActivitiesIfAvailable(this);
        Thread.setDefaultUncaughtExceptionHandler(new v5(this));
        super.onCreate();
    }
}
