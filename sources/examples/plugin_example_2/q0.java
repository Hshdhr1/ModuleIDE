package ru.yufic.exteraPlugins;

import android.content.DialogInterface;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class q0 implements DialogInterface.OnClickListener {
    final /* synthetic */ r0 a;

    q0(r0 r0Var) {
        this.a = r0Var;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.P(r0.a(this.a)).setSelection((int) (EditActivity.S(r0.a(this.a)) + 1.0d));
    }
}
