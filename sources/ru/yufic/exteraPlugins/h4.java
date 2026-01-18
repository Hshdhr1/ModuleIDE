package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class h4 extends ViewOutlineProvider {
    final /* synthetic */ q4 a;

    h4(q4 q4Var) {
        this.a = q4Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((q4.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 18.0f) + 0.5f));
    }
}
