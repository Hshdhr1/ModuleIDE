package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class b0 implements View.OnClickListener {
    final /* synthetic */ n0 a;

    b0(n0 n0Var) {
        this.a = n0Var;
    }

    public void onClick(View view) {
        DocumActivity.H(n0.a(this.a)).setText(DocumActivity.E(n0.a(this.a)).getText().toString());
        DocumActivity.J(n0.a(this.a)).loadUrl("file:///android_asset/".concat("doc/bulletin-helper.html"));
        DocumActivity.c(n0.a(this.a)).closeDrawer(8388611);
    }
}
