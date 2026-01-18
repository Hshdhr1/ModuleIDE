package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class b1 implements View.OnClickListener {
    final /* synthetic */ i1 a;

    b1(i1 i1Var) {
        this.a = i1Var;
    }

    public void onClick(View view) {
        EditActivity.f(i1.a(this.a)).closeDrawer(8388611);
        i1.a(this.a).onBackPressed();
    }
}
