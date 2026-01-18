package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class d implements View.OnClickListener {
    final /* synthetic */ CreateActivity a;

    d(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
