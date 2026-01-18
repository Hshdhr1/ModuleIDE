package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r5 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    r5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        SettingsActivity.F(this.a).edit().remove("start1").commit();
        SettingsActivity.j(this.a).setClass(this.a.getApplicationContext(), SplashActivity.class);
        SettingsActivity settingsActivity = this.a;
        settingsActivity.startActivity(SettingsActivity.j(settingsActivity));
        this.a.overridePendingTransition(17432576, 17432577);
        this.a.finish();
    }
}
