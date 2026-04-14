package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class v2 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    v2(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        EditActivity.z(this.a).setClass(this.a.getApplicationContext(), IconsActivity.class);
        EditActivity editActivity = this.a;
        editActivity.startActivity(EditActivity.z(editActivity));
        this.a.overridePendingTransition(17432576, 17432577);
    }
}
