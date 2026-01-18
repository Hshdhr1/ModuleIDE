package ru.yufic.exteraPlugins;

import android.widget.CompoundButton;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class t5 implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ SettingsActivity a;

    t5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            SettingsActivity.z(this.a).setChecked(false);
            SettingsActivity.B(this.a).setChecked(false);
            SettingsActivity.F(this.a).edit().putString("api", "gemini").commit();
            SettingsActivity.F(this.a).edit().putString("model", "gemini-2.5-pro").commit();
            SettingsActivity.f(this.a).setText(SettingsActivity.F(this.a).getString("apiKeyG", ""));
        }
    }
}
