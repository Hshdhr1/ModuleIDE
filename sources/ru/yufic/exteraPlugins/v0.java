package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class v0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    v0(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        if (EditActivity.x(this.a).canRedo()) {
            EditActivity.x(this.a).redo();
        }
    }
}
