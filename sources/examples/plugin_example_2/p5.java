package ru.yufic.exteraPlugins;

import android.os.Build;
import android.os.Environment;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class p5 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    p5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        if (Build.VERSION.SDK_INT <= 29) {
            a.m("", "");
        } else if (!Environment.isExternalStorageManager()) {
            SettingsActivity.l(this.a).setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
            SettingsActivity settingsActivity = this.a;
            settingsActivity.startActivity(SettingsActivity.l(settingsActivity));
        }
        d.b(this.a.getApplicationContext(), "Выберите файл формата .piex");
        SettingsActivity settingsActivity2 = this.a;
        settingsActivity2.startActivityForResult(SettingsActivity.w(settingsActivity2), 101);
    }
}
