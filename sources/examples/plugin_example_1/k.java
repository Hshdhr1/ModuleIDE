package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k implements View.OnClickListener {
    final /* synthetic */ CreateActivity a;

    k(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
