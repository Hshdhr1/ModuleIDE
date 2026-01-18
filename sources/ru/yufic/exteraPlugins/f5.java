package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import com.google.gson.Gson;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class f5 implements DialogInterface.OnClickListener {
    final /* synthetic */ h5 a;

    f5(h5 h5Var) {
        this.a = h5Var;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            a.m(a.f(h5.a(this.a).getApplicationContext()).concat("/list"), SettingsActivity.e(h5.a(this.a)));
            d.b(h5.a(this.a).getApplicationContext(), "Успешно импортировано!!");
            try {
                SettingsActivity.Q(h5.a(this.a), (ArrayList) new Gson().fromJson(SettingsActivity.e(h5.a(this.a)), new e5(this).getType()));
                SettingsActivity.c(h5.a(this.a)).setText(String.valueOf(SettingsActivity.x(h5.a(this.a)).size()));
            } catch (Exception unused) {
                SettingsActivity.c(h5.a(this.a)).setText("N/A");
            }
        } catch (Exception unused2) {
            d.b(h5.a(this.a).getApplicationContext(), "Не удалось прочитать файл!");
        }
    }
}
