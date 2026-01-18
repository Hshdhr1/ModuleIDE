package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class r3 implements View.OnLongClickListener {
    final /* synthetic */ MainActivity a;

    r3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public boolean onLongClick(View view) {
        MainActivity.r(this.a).setClass(this.a.getApplicationContext(), EditActivity.class);
        MainActivity mainActivity = this.a;
        mainActivity.startActivity(MainActivity.r(mainActivity));
        return true;
    }
}
