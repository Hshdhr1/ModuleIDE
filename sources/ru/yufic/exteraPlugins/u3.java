package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class u3 implements View.OnClickListener {
    final /* synthetic */ e4 a;

    u3(e4 e4Var) {
        this.a = e4Var;
    }

    public void onClick(View view) {
        MainActivity.r(e4.a(this.a)).setClass(e4.a(this.a).getApplicationContext(), DocumActivity.class);
        e4.a(this.a).startActivity(MainActivity.r(e4.a(this.a)));
        e4.a(this.a).overridePendingTransition(17432576, 17432577);
        MainActivity.c(e4.a(this.a)).closeDrawer(8388611);
    }
}
