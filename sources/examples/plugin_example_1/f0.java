package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class f0 implements View.OnClickListener {
    final /* synthetic */ n0 a;

    f0(n0 n0Var) {
        this.a = n0Var;
    }

    public void onClick(View view) {
        n0.a(this.a).onBackPressed();
    }
}
