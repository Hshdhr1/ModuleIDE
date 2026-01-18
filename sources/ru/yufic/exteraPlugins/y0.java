package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class y0 implements View.OnClickListener {
    final /* synthetic */ i1 a;

    y0(i1 i1Var) {
        this.a = i1Var;
    }

    public void onClick(View view) {
        EditActivity.f(i1.a(this.a)).closeDrawer(8388611);
        EditActivity.z(i1.a(this.a)).putExtra("proj", "");
        EditActivity.z(i1.a(this.a)).setClass(i1.a(this.a).getApplicationContext(), SettingsActivity.class);
        i1.a(this.a).startActivity(EditActivity.z(i1.a(this.a)));
        i1.a(this.a).overridePendingTransition(17432576, 17432577);
    }
}
