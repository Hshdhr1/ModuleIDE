package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class h3 implements View.OnClickListener {
    final /* synthetic */ MainActivity a;

    h3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        if (MainActivity.c(this.a).isDrawerOpen(8388611)) {
            MainActivity.c(this.a).closeDrawer(8388611);
        } else {
            MainActivity.c(this.a).openDrawer(8388611);
        }
    }
}
