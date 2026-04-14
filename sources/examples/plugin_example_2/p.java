package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class p implements View.OnClickListener {
    final /* synthetic */ DocumActivity a;

    p(DocumActivity documActivity) {
        this.a = documActivity;
    }

    public void onClick(View view) {
        if (DocumActivity.c(this.a).isDrawerOpen(8388611)) {
            DocumActivity.c(this.a).closeDrawer(8388611);
        } else {
            DocumActivity.c(this.a).openDrawer(8388611);
        }
    }
}
