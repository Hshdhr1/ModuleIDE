package ru.yufic.exteraPlugins;

import android.widget.CompoundButton;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s5 implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ SettingsActivity a;

    s5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            SettingsActivity.A(this.a).setChecked(false);
            SettingsActivity.B(this.a).setChecked(false);
            SettingsActivity.F(this.a).edit().putString("api", "").commit();
            SettingsActivity.F(this.a).edit().putString("model", "").commit();
            SettingsActivity.f(this.a).setText(SettingsActivity.F(this.a).getString("apiKey", ""));
        }
    }
}
