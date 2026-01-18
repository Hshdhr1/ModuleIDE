package ru.yufic.exteraPlugins;

import android.content.DialogInterface;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r2 implements DialogInterface.OnClickListener {
    final /* synthetic */ t2 a;

    r2(t2 t2Var) {
        this.a = t2Var;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.Y(t2.a(this.a), true);
        EditActivity.P(t2.a(this.a)).setSelection((int) (EditActivity.S(t2.a(this.a)) + 1.0d));
    }
}
