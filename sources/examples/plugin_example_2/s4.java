package ru.yufic.exteraPlugins;

import android.text.Editable;
import android.text.TextWatcher;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s4 implements TextWatcher {
    final /* synthetic */ SettingsActivity a;

    s4(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        if (SettingsActivity.F(this.a).getString("api", "").equals("gemini")) {
            SettingsActivity.F(this.a).edit().putString("apiKeyG", charSequence2.trim()).commit();
        } else {
            SettingsActivity.F(this.a).edit().putString("apiKey", charSequence2.trim()).commit();
        }
    }
}
