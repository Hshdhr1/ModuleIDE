package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import android.widget.EditText;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class x1 implements DialogInterface.OnClickListener {
    final /* synthetic */ EditActivity a;
    private final /* synthetic */ EditText b;

    x1(EditActivity editActivity, EditText editText) {
        this.a = editActivity;
        this.b = editText;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.j0(this.b.getText().toString());
    }
}
