package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class o implements View.OnClickListener {
    final /* synthetic */ DocumActivity a;

    o(DocumActivity documActivity) {
        this.a = documActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
