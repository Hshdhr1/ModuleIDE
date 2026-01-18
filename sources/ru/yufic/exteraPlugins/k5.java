package ru.yufic.exteraPlugins;

import android.net.Uri;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k5 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    k5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        SettingsActivity.t(this.a).setAction("android.intent.action.VIEW");
        SettingsActivity.t(this.a).setData(Uri.parse("https://neurix.ru"));
        SettingsActivity settingsActivity = this.a;
        settingsActivity.startActivity(SettingsActivity.t(settingsActivity));
    }
}
