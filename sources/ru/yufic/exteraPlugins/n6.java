package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n6 implements View.OnClickListener {
    final /* synthetic */ q6 a;
    private final /* synthetic */ int b;

    n6(q6 q6Var, int i) {
        this.a = q6Var;
        this.b = i;
    }

    static /* synthetic */ q6 a(n6 n6Var) {
        return n6Var.a;
    }

    public void onClick(View view) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(q6.a(this.a));
        materialAlertDialogBuilder.setTitle(q6.a(this.a).getString(2131689535));
        materialAlertDialogBuilder.setIcon(2131165321);
        materialAlertDialogBuilder.setMessage(q6.a(this.a).getString(2131689516));
        materialAlertDialogBuilder.setPositiveButton(q6.a(this.a).getString(2131689520), new l6(this, this.b));
        materialAlertDialogBuilder.setNegativeButton(q6.a(this.a).getString(2131689509), new m6(this));
        materialAlertDialogBuilder.show();
    }
}
