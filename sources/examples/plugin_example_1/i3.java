package ru.yufic.exteraPlugins;

import android.graphics.PorterDuff;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class i3 implements View.OnClickListener {
    final /* synthetic */ MainActivity a;

    i3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        if (MainActivity.z(this.a)) {
            MainActivity.I(this.a, false);
            MainActivity.b(this.a).setColorFilter(MainActivity.O(this.a, 2130903278), PorterDuff.Mode.SRC_IN);
        } else {
            MainActivity.I(this.a, true);
            MainActivity.b(this.a).setColorFilter(MainActivity.O(this.a, 2130903282), PorterDuff.Mode.SRC_IN);
        }
        MainActivity.u(this.a).invalidateViews();
    }
}
