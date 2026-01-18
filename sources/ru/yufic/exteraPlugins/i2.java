package ru.yufic.exteraPlugins;

import android.view.View;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class i2 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    i2(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        EditActivity.w(this.a).putExtra("edit", "");
        EditActivity.w(this.a).putExtra("position", this.a.getIntent().getStringExtra("position"));
        EditActivity.w(this.a).putExtra("version", String.valueOf((long) EditActivity.S(this.a)));
        EditActivity.w(this.a).putExtra("ver", ((HashMap) EditActivity.T(this.a).get(((int) EditActivity.S(this.a)) + 1)).get("version").toString());
        EditActivity.w(this.a).setClass(this.a.getApplicationContext(), CreateActivity.class);
        EditActivity editActivity = this.a;
        editActivity.startActivity(EditActivity.w(editActivity));
        this.a.overridePendingTransition(17432576, 17432577);
    }
}
