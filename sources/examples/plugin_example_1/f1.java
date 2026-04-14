package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class f1 extends ViewOutlineProvider {
    final /* synthetic */ i1 a;

    f1(i1 i1Var) {
        this.a = i1Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((i1.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 18.0f) + 0.5f));
    }
}
