package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class u0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    u0(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        if (EditActivity.x(this.a).canUndo()) {
            EditActivity.x(this.a).undo();
        }
        EditActivity.E(this.a).clear();
    }
}
