package ru.yufic.exteraPlugins;

import java.util.TimerTask;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class h2 extends TimerTask {
    final /* synthetic */ EditActivity a;

    h2(EditActivity editActivity) {
        this.a = editActivity;
    }

    static /* synthetic */ EditActivity a(h2 h2Var) {
        return h2Var.a;
    }

    public void run() {
        this.a.runOnUiThread(new g2(this));
    }
}
