package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class g3 implements View.OnClickListener {
    final /* synthetic */ MainActivity a;

    g3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
