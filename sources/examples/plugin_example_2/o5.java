package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class o5 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    o5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
        materialAlertDialogBuilder.setTitle("Статистика");
        materialAlertDialogBuilder.setIcon(2131165347);
        try {
            SettingsActivity.N(this.a, 0.0d);
            SettingsActivity.V(this.a, 0.0d);
            for (int i = 0; i < SettingsActivity.x(this.a).size(); i++) {
                SettingsActivity.U(this.a, (ArrayList) new Gson().fromJson(((HashMap) SettingsActivity.x(this.a).get((int) SettingsActivity.s(this.a))).get("list").toString(), new l5(this).getType()));
                SettingsActivity settingsActivity = this.a;
                SettingsActivity.V(settingsActivity, SettingsActivity.I(settingsActivity) + SettingsActivity.H(this.a).size());
                SettingsActivity settingsActivity2 = this.a;
                SettingsActivity.N(settingsActivity2, SettingsActivity.s(settingsActivity2) + 1.0d);
            }
            SettingsActivity settingsActivity3 = this.a;
            SettingsActivity.T(settingsActivity3, String.valueOf((long) SettingsActivity.I(settingsActivity3)));
        } catch (Exception unused) {
            SettingsActivity.T(this.a, "N\\A");
        }
        materialAlertDialogBuilder.setMessage("Кол-во плагинов: ".concat(String.valueOf(SettingsActivity.x(this.a).size()).concat("\nКол-во версий: ".concat(SettingsActivity.E(this.a)))));
        materialAlertDialogBuilder.setPositiveButton("OK", new m5(this));
        materialAlertDialogBuilder.setNegativeButton(this.a.getString(2131689509), new n5(this));
        materialAlertDialogBuilder.show();
    }
}
