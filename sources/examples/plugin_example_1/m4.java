package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class m4 implements View.OnClickListener {
    final /* synthetic */ q4 a;
    private final /* synthetic */ int b;

    m4(q4 q4Var, int i) {
        this.a = q4Var;
        this.b = i;
    }

    static /* synthetic */ q4 a(m4 m4Var) {
        return m4Var.a;
    }

    public void onClick(View view) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(q4.a(this.a));
        materialAlertDialogBuilder.setTitle(q4.a(this.a).getString(2131689535));
        materialAlertDialogBuilder.setIcon(2131165321);
        materialAlertDialogBuilder.setMessage(q4.a(this.a).getString(2131689516));
        materialAlertDialogBuilder.setPositiveButton(q4.a(this.a).getString(2131689520), new k4(this, this.b));
        materialAlertDialogBuilder.setNegativeButton(q4.a(this.a).getString(2131689509), new l4(this));
        materialAlertDialogBuilder.show();
    }
}
