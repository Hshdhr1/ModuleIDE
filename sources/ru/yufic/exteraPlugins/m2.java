package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class m2 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    m2(EditActivity editActivity) {
        this.a = editActivity;
    }

    static /* synthetic */ EditActivity a(m2 m2Var) {
        return m2Var.a;
    }

    public void onClick(View view) {
        if (EditActivity.N(this.a).getString("api", "").equals("gemini")) {
            EditActivity editActivity = this.a;
            EditActivity.V(editActivity, EditActivity.N(editActivity).getString("apiKeyG", ""));
        } else {
            EditActivity editActivity2 = this.a;
            EditActivity.V(editActivity2, EditActivity.N(editActivity2).getString("apiKey", ""));
        }
        if (!EditActivity.q(this.a).trim().equals("")) {
            this.a.f0("");
            return;
        }
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
        materialAlertDialogBuilder.setTitle(this.a.getString(2131689502));
        materialAlertDialogBuilder.setIcon(2131165346);
        materialAlertDialogBuilder.setMessage("Введите свой бесплатный Api ключ, или смените модель ИИ.");
        materialAlertDialogBuilder.setPositiveButton("Настройки", new k2(this));
        materialAlertDialogBuilder.setNeutralButton(this.a.getString(2131689509), new l2(this));
        materialAlertDialogBuilder.show();
    }
}
