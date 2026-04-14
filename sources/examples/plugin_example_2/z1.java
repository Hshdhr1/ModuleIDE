package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class z1 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    z1(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        if (EditActivity.f(this.a).isDrawerOpen(8388611)) {
            EditActivity.f(this.a).closeDrawer(8388611);
        } else {
            EditActivity.f(this.a).openDrawer(8388611);
        }
    }
}
