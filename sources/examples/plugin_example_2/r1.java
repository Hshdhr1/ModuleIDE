package ru.yufic.exteraPlugins;

import android.content.DialogInterface;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r1 implements DialogInterface.OnClickListener {
    final /* synthetic */ EditActivity a;

    r1(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.finish();
    }
}
