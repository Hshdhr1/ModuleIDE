package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class c5 implements DialogInterface.OnClickListener {
    final /* synthetic */ SettingsActivity a;
    private final /* synthetic */ boolean b;

    c5(SettingsActivity settingsActivity, boolean z) {
        this.a = settingsActivity;
        this.b = z;
    }

    static /* synthetic */ SettingsActivity a(c5 c5Var) {
        return c5Var.a;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
        materialAlertDialogBuilder.setIcon(2131165347);
        if (this.b) {
            materialAlertDialogBuilder.setMessage("Придумайте пароль для экспорта.\nВНИМАНИЕ! Не потеряйте его! Без него вы не востановите резервную копию!");
            materialAlertDialogBuilder.setTitle("Параметры экспорта");
        } else {
            materialAlertDialogBuilder.setTitle("Параметры импорта");
            materialAlertDialogBuilder.setMessage("Введите пароль от выбранной резервной копии.\nЕсли вы его не знаете - вы не восстановите резервную копию.");
        }
        materialAlertDialogBuilder.setCancelable(false);
        EditText editText = new EditText(this.a);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        materialAlertDialogBuilder.setView(editText);
        editText.setTextColor(SettingsActivity.W(this.a, 2130903278));
        editText.setHintTextColor(SettingsActivity.W(this.a, 2130903275));
        editText.setHint("Пароль");
        materialAlertDialogBuilder.setPositiveButton("Продолжить", new a5(this, editText, this.b));
        materialAlertDialogBuilder.setNegativeButton(this.a.getString(2131689509), new b5(this));
        materialAlertDialogBuilder.show();
    }
}
