package ru.yufic.exteraPlugins;

import android.content.Intent;
import android.os.Process;
import android.util.Log;
import java.lang.Thread;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class v5 implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ SketchApplication a;

    v5(SketchApplication sketchApplication) {
        this.a = sketchApplication;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        Intent intent = new Intent(this.a.getApplicationContext(), DebugActivity.class);
        intent.setFlags(268468224);
        intent.putExtra("error", Log.getStackTraceString(th));
        this.a.startActivity(intent);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
