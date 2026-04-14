package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class t3 extends ViewOutlineProvider {
    final /* synthetic */ e4 a;

    t3(e4 e4Var) {
        this.a = e4Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((e4.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 18.0f) + 0.5f));
    }
}
