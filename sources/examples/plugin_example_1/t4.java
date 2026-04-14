package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class t4 extends ViewOutlineProvider {
    final /* synthetic */ u4 a;

    t4(u4 u4Var) {
        this.a = u4Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((u4.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 8.0f) + 0.5f));
    }
}
