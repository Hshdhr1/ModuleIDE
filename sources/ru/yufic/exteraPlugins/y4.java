package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import com.google.gson.Gson;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class y4 implements DialogInterface.OnClickListener {
    final /* synthetic */ a5 a;

    y4(a5 a5Var) {
        this.a = a5Var;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            a.m(a.f(c5.a(a5.a(this.a)).getApplicationContext()).concat("/list"), SettingsActivity.e(c5.a(a5.a(this.a))));
            d.b(c5.a(a5.a(this.a)).getApplicationContext(), "Успешно импортировано!!");
            try {
                SettingsActivity.S(c5.a(a5.a(this.a)), a.l(a.f(c5.a(a5.a(this.a)).getApplicationContext()).concat("/list")));
                SettingsActivity.Q(c5.a(a5.a(this.a)), (ArrayList) new Gson().fromJson(SettingsActivity.C(c5.a(a5.a(this.a))), new x4(this).getType()));
                SettingsActivity.c(c5.a(a5.a(this.a))).setText(String.valueOf(SettingsActivity.x(c5.a(a5.a(this.a))).size()));
            } catch (Exception unused) {
                SettingsActivity.c(c5.a(a5.a(this.a))).setText("N/A");
            }
        } catch (Exception unused2) {
            d.b(c5.a(a5.a(this.a)).getApplicationContext(), "Не удалось прочитать файл!");
        }
    }
}
