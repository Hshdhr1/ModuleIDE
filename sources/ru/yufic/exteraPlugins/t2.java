package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class t2 implements AdapterView.OnItemSelectedListener {
    final /* synthetic */ EditActivity a;

    t2(EditActivity editActivity) {
        this.a = editActivity;
    }

    static /* synthetic */ EditActivity a(t2 t2Var) {
        return t2Var.a;
    }

    public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        if (i == 0) {
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
            materialAlertDialogBuilder.setTitle("Новая версия");
            materialAlertDialogBuilder.setIcon(2131165347);
            materialAlertDialogBuilder.setMessage("Создание новой версии");
            materialAlertDialogBuilder.setCancelable(false);
            EditText editText = new EditText(this.a);
            editText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            materialAlertDialogBuilder.setView(editText);
            editText.setTextColor(EditActivity.e0(this.a, 2130903278));
            editText.setHintTextColor(EditActivity.e0(this.a, 2130903275));
            editText.setHint("Версия - только цифры (1.0.1)");
            editText.setText(((HashMap) EditActivity.T(this.a).get(((int) EditActivity.S(this.a)) + 1)).get("version").toString());
            materialAlertDialogBuilder.setPositiveButton(this.a.getString(2131689518), new o2(this, editText));
            materialAlertDialogBuilder.setNeutralButton(this.a.getString(2131689509), new p2(this));
            materialAlertDialogBuilder.show();
            return;
        }
        if (EditActivity.I(this.a)) {
            EditActivity.Y(this.a, false);
            return;
        }
        if (!this.a.getString(2131689545).equals(EditActivity.x(this.a).getText().toString()) && !EditActivity.J(this.a).equals(EditActivity.x(this.a).getText().toString())) {
            MaterialAlertDialogBuilder materialAlertDialogBuilder2 = new MaterialAlertDialogBuilder(this.a);
            materialAlertDialogBuilder2.setTitle("Не сохранено!");
            materialAlertDialogBuilder2.setIcon(2131165320);
            materialAlertDialogBuilder2.setMessage("Вы не сохранили изменения. Хотите сохранить? Перед переключением на другую версию?");
            materialAlertDialogBuilder2.setPositiveButton(this.a.getString(2131689628), new q2(this, i));
            materialAlertDialogBuilder2.setNegativeButton("Отмена", new r2(this));
            materialAlertDialogBuilder2.setNeutralButton("Удалить", new s2(this, i));
            materialAlertDialogBuilder2.show();
            return;
        }
        EditActivity.d0(this.a, i - 1);
        try {
            EditActivity editActivity = this.a;
            EditActivity.Z(editActivity, ((HashMap) EditActivity.U(editActivity).get((int) EditActivity.S(this.a))).get("code").toString());
            EditActivity editActivity2 = this.a;
            editActivity2.k0(EditActivity.J(editActivity2));
            this.a.l0();
        } catch (Exception e) {
            EditActivity editActivity3 = this.a;
            editActivity3.getApplicationContext();
            ((ClipboardManager) editActivity3.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }

    public void onNothingSelected(AdapterView adapterView) {
    }
}
