package ru.yufic.exteraPlugins;

import android.net.Uri;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r4 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    r4(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        SettingsActivity.k(this.a).setAction("android.intent.action.VIEW");
        if (SettingsActivity.F(this.a).getString("api", "").equals("gemini")) {
            d.b(this.a.getApplicationContext(), "Возможно вам потребуется ВПН");
            SettingsActivity.k(this.a).setData(Uri.parse("https://aistudio.google.com/apikey"));
        } else {
            SettingsActivity.k(this.a).setData(Uri.parse("https://auth.pollinations.ai"));
        }
        SettingsActivity settingsActivity = this.a;
        settingsActivity.startActivity(SettingsActivity.k(settingsActivity));
    }
}
