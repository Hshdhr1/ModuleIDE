package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class w5 implements View.OnClickListener {
    final /* synthetic */ SplashActivity a;

    w5(SplashActivity splashActivity) {
        this.a = splashActivity;
    }

    public void onClick(View view) {
        SplashActivity.b(this.a).edit().putString("start1", "").commit();
        SplashActivity.a(this.a).setClass(this.a.getApplicationContext(), MainActivity.class);
        SplashActivity splashActivity = this.a;
        splashActivity.startActivity(SplashActivity.a(splashActivity));
        this.a.overridePendingTransition(17432576, 17432577);
    }
}
