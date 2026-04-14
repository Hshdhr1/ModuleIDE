package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class y5 implements View.OnClickListener {
    final /* synthetic */ VersionsControlActivity a;

    y5(VersionsControlActivity versionsControlActivity) {
        this.a = versionsControlActivity;
    }

    public void onClick(View view) {
        this.a.onBackPressed();
    }
}
