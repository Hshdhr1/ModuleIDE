package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s2 implements DialogInterface.OnClickListener {
    final /* synthetic */ t2 a;
    private final /* synthetic */ int b;

    s2(t2 t2Var, int i) {
        this.a = t2Var;
        this.b = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.d0(t2.a(this.a), this.b - 1);
        try {
            EditActivity.Z(t2.a(this.a), ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("code").toString());
            t2.a(this.a).k0(EditActivity.J(t2.a(this.a)));
            t2.a(this.a).l0();
        } catch (Exception e) {
            EditActivity a = t2.a(this.a);
            t2.a(this.a).getApplicationContext();
            ((ClipboardManager) a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }
}
