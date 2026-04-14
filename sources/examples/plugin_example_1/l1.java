package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class l1 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    l1(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
