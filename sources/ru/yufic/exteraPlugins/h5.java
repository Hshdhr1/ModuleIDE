package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class h5 implements DialogInterface.OnClickListener {
    final /* synthetic */ SettingsActivity a;
    private final /* synthetic */ boolean b;

    h5(SettingsActivity settingsActivity, boolean z) {
        this.a = settingsActivity;
        this.b = z;
    }

    static /* synthetic */ SettingsActivity a(h5 h5Var) {
        return h5Var.a;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (!this.b) {
            try {
                SettingsActivity settingsActivity = this.a;
                SettingsActivity.K(settingsActivity, settingsActivity.X(SettingsActivity.D(settingsActivity), "PluginIDEyufic"));
                SettingsActivity.M(this.a, (ArrayList) new Gson().fromJson(SettingsActivity.e(this.a), new d5(this).getType()));
                SettingsActivity.R(this.a, SettingsActivity.r(r8).size());
            } catch (Exception unused) {
                d.b(this.a.getApplicationContext(), "Не правильный файл!");
                SettingsActivity.O(this.a, true);
            }
            if (SettingsActivity.u(this.a)) {
                return;
            }
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this.a);
            materialAlertDialogBuilder.setTitle("Импорт");
            materialAlertDialogBuilder.setIcon(2131165347);
            materialAlertDialogBuilder.setMessage("Вы бействительно хотите БЕЗВОЗВРАТНО перезаписать список плагинов, на новый?\n\nВ резервной копии: ".concat(String.valueOf((long) SettingsActivity.y(this.a)).concat(" проектов.")));
            materialAlertDialogBuilder.setCancelable(false);
            materialAlertDialogBuilder.setPositiveButton(this.a.getString(2131689542), new f5(this));
            materialAlertDialogBuilder.setNegativeButton(this.a.getString(2131689509), new g5(this));
            materialAlertDialogBuilder.show();
            return;
        }
        try {
            SettingsActivity.J(this.a, Calendar.getInstance());
            SettingsActivity settingsActivity2 = this.a;
            SettingsActivity.P(settingsActivity2, a.f(settingsActivity2.getApplicationContext()).concat("/".concat(new SimpleDateFormat("yyyy.MM.dd_HH_mm_ss").format(SettingsActivity.d(this.a).getTime()).concat(".piex"))));
            String v = SettingsActivity.v(this.a);
            SettingsActivity settingsActivity3 = this.a;
            a.m(v, settingsActivity3.a0(SettingsActivity.C(settingsActivity3), "PluginIDEyufic"));
            File file = new File(this.a.getExternalCacheDir(), new File(SettingsActivity.v(this.a)).getName());
            a.b(SettingsActivity.v(this.a), file.getAbsolutePath());
            SettingsActivity settingsActivity4 = this.a;
            Uri uriForFile = FileProvider.getUriForFile(settingsActivity4, String.valueOf(settingsActivity4.getPackageName()) + ".provider", file);
            SettingsActivity.g(this.a).setAction("android.intent.action.SEND");
            SettingsActivity.g(this.a).setType("*/*");
            SettingsActivity.g(this.a).putExtra("android.intent.extra.STREAM", uriForFile);
            SettingsActivity.g(this.a).addFlags(1);
            SettingsActivity settingsActivity5 = this.a;
            settingsActivity5.startActivity(Intent.createChooser(SettingsActivity.g(settingsActivity5), new SimpleDateFormat("yyyy.MM.dd_HH_mm_ss").format(SettingsActivity.d(this.a).getTime()).concat(".piex")));
            a.d(SettingsActivity.v(this.a));
        } catch (Exception e) {
            SettingsActivity settingsActivity6 = this.a;
            settingsActivity6.getApplicationContext();
            ((ClipboardManager) settingsActivity6.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }
}
