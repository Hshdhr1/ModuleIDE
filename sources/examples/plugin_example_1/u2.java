package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class u2 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    u2(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        EditActivity.x(this.a).selectAll();
    }
}
