package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s3 implements View.OnClickListener {
    final /* synthetic */ MainActivity a;

    s3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        MainActivity.r(this.a).setClass(this.a.getApplicationContext(), CreateActivity.class);
        MainActivity mainActivity = this.a;
        mainActivity.startActivity(MainActivity.r(mainActivity));
        this.a.overridePendingTransition(17432576, 17432577);
    }
}
