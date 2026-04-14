package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k0 extends ViewOutlineProvider {
    final /* synthetic */ n0 a;

    k0(n0 n0Var) {
        this.a = n0Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((n0.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 18.0f) + 0.5f));
    }
}
