package ru.yufic.exteraPlugins;

import android.net.Uri;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class c4 implements View.OnClickListener {
    final /* synthetic */ e4 a;

    c4(e4 e4Var) {
        this.a = e4Var;
    }

    public void onClick(View view) {
        MainActivity.w(e4.a(this.a)).setAction("android.intent.action.VIEW");
        MainActivity.w(e4.a(this.a)).setData(Uri.parse("https://yufic.ru"));
        e4.a(this.a).startActivity(MainActivity.w(e4.a(this.a)));
        e4.a(this.a).overridePendingTransition(17432576, 17432577);
        MainActivity.c(e4.a(this.a)).closeDrawer(8388611);
    }
}
