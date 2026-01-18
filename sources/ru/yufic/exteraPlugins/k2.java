package ru.yufic.exteraPlugins;

import android.content.DialogInterface;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k2 implements DialogInterface.OnClickListener {
    final /* synthetic */ m2 a;

    k2(m2 m2Var) {
        this.a = m2Var;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.z(m2.a(this.a)).putExtra("proj", "");
        EditActivity.z(m2.a(this.a)).setClass(m2.a(this.a).getApplicationContext(), SettingsActivity.class);
        m2.a(this.a).startActivity(EditActivity.z(m2.a(this.a)));
        m2.a(this.a).overridePendingTransition(17432576, 17432577);
    }
}
