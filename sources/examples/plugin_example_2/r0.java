package ru.yufic.exteraPlugins;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    r0(EditActivity editActivity) {
        this.a = editActivity;
    }

    static /* synthetic */ EditActivity a(r0 r0Var) {
        return r0Var.a;
    }

    public void onClick(View view) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
        materialAlertDialogBuilder.setTitle("Поиск & замена");
        materialAlertDialogBuilder.setIcon(2131165325);
        materialAlertDialogBuilder.setCancelable(false);
        EditText editText = new EditText(this.a);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        materialAlertDialogBuilder.setView(editText);
        editText.setTextColor(EditActivity.e0(this.a, 2130903278));
        editText.setHintTextColor(EditActivity.e0(this.a, 2130903275));
        editText.setHint("Версия - только цифры (1.0.1)");
        EditActivity.G(this.a).setPositiveButton("Заменить всё", new p0(this, editText));
        EditActivity.G(this.a).setNegativeButton(this.a.getString(2131689509), new q0(this));
        EditActivity.G(this.a).show();
    }
}
