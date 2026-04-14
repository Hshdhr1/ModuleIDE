package ru.yufic.exteraPlugins;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class i6 extends ViewOutlineProvider {
    final /* synthetic */ q6 a;

    i6(q6 q6Var) {
        this.a = q6Var;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (int) ((q6.a(this.a).getApplicationContext().getResources().getDisplayMetrics().density * 18.0f) + 0.5f));
    }
}
