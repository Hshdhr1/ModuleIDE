package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class j5 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity a;

    j5(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
