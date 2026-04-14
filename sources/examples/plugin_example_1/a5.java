package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import androidx.core.content.FileProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class a5 implements DialogInterface.OnClickListener {
    final /* synthetic */ c5 a;
    private final /* synthetic */ EditText b;
    private final /* synthetic */ boolean c;

    a5(c5 c5Var, EditText editText, boolean z) {
        this.a = c5Var;
        this.b = editText;
        this.c = z;
    }

    static /* synthetic */ c5 a(a5 a5Var) {
        return a5Var.a;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        SettingsActivity.L(c5.a(this.a), this.b.getText().toString());
        if (!this.c) {
            try {
                SettingsActivity.K(c5.a(this.a), c5.a(this.a).Y(SettingsActivity.D(c5.a(this.a)), SettingsActivity.m(c5.a(this.a))));
                if (SettingsActivity.e(c5.a(this.a)).equals("invalid key")) {
                    d.b(c5.a(this.a).getApplicationContext(), "Не верный пароль!");
                    SettingsActivity.O(c5.a(this.a), true);
                } else {
                    SettingsActivity.M(c5.a(this.a), (ArrayList) new Gson().fromJson(SettingsActivity.e(c5.a(this.a)), new w4(this).getType()));
                    SettingsActivity.R(c5.a(this.a), SettingsActivity.r(c5.a(this.a)).size());
                }
            } catch (Exception unused) {
                d.b(c5.a(this.a).getApplicationContext(), "Не правильный файл!");
                SettingsActivity.O(c5.a(this.a), true);
            }
            if (SettingsActivity.u(c5.a(this.a))) {
                return;
            }
            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(c5.a(this.a));
            materialAlertDialogBuilder.setTitle("Импорт");
            materialAlertDialogBuilder.setIcon(2131165347);
            materialAlertDialogBuilder.setMessage("Вы бействительно хотите БЕЗВОЗВРАТНО перезаписать список плагинов, на новый?\n\nВ резервной копии: ".concat(String.valueOf((long) SettingsActivity.y(c5.a(this.a))).concat(" проектов.")));
            materialAlertDialogBuilder.setCancelable(false);
            materialAlertDialogBuilder.setPositiveButton(c5.a(this.a).getString(2131689542), new y4(this));
            materialAlertDialogBuilder.setNegativeButton(c5.a(this.a).getString(2131689509), new z4(this));
            materialAlertDialogBuilder.show();
            return;
        }
        try {
            SettingsActivity.J(c5.a(this.a), Calendar.getInstance());
            SettingsActivity.P(c5.a(this.a), a.f(c5.a(this.a).getApplicationContext()).concat("/".concat(new SimpleDateFormat("yyyy.MM.dd_HH_mm_ss").format(SettingsActivity.d(c5.a(this.a)).getTime()).concat(".piex"))));
            a.m(SettingsActivity.v(c5.a(this.a)), c5.a(this.a).b0(SettingsActivity.C(c5.a(this.a)), SettingsActivity.m(c5.a(this.a))));
            File file = new File(c5.a(this.a).getExternalCacheDir(), new File(SettingsActivity.v(c5.a(this.a))).getName());
            a.b(SettingsActivity.v(c5.a(this.a)), file.getAbsolutePath());
            Uri uriForFile = FileProvider.getUriForFile(c5.a(this.a), String.valueOf(c5.a(this.a).getPackageName()) + ".provider", file);
            SettingsActivity.g(c5.a(this.a)).setAction("android.intent.action.SEND");
            SettingsActivity.g(c5.a(this.a)).setType("*/*");
            SettingsActivity.g(c5.a(this.a)).putExtra("android.intent.extra.STREAM", uriForFile);
            SettingsActivity.g(c5.a(this.a)).addFlags(1);
            c5.a(this.a).startActivity(Intent.createChooser(SettingsActivity.g(c5.a(this.a)), new SimpleDateFormat("yyyy.MM.dd_HH_mm_ss").format(SettingsActivity.d(c5.a(this.a)).getTime()).concat(".piex")));
            a.d(SettingsActivity.v(c5.a(this.a)));
        } catch (Exception e) {
            SettingsActivity a = c5.a(this.a);
            c5.a(this.a).getApplicationContext();
            ((ClipboardManager) a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }
}
