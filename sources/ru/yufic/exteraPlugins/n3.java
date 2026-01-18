package ru.yufic.exteraPlugins;

import java.util.TimerTask;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n3 extends TimerTask {
    final /* synthetic */ q3 a;

    n3(q3 q3Var) {
        this.a = q3Var;
    }

    static /* synthetic */ q3 a(n3 n3Var) {
        return n3Var.a;
    }

    public void run() {
        q3.a(this.a).runOnUiThread(new m3(this));
    }
}
