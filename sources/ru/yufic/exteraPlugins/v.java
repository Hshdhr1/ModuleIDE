package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class v implements View.OnClickListener {
    final /* synthetic */ n0 a;

    v(n0 n0Var) {
        this.a = n0Var;
    }

    public void onClick(View view) {
        DocumActivity.H(n0.a(this.a)).setText(DocumActivity.B(n0.a(this.a)).getText().toString());
        DocumActivity.J(n0.a(this.a)).loadUrl("file:///android_asset/".concat("doc/client-utils.html"));
        DocumActivity.c(n0.a(this.a)).closeDrawer(8388611);
    }
}
