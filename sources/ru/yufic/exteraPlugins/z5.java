package ru.yufic.exteraPlugins;

import android.graphics.PorterDuff;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class z5 implements View.OnClickListener {
    final /* synthetic */ VersionsControlActivity a;

    z5(VersionsControlActivity versionsControlActivity) {
        this.a = versionsControlActivity;
    }

    public void onClick(View view) {
        if (VersionsControlActivity.g(this.a)) {
            VersionsControlActivity.m(this.a, false);
            VersionsControlActivity.b(this.a).setColorFilter(VersionsControlActivity.p(this.a, 2130903278), PorterDuff.Mode.SRC_IN);
        } else {
            VersionsControlActivity.m(this.a, true);
            VersionsControlActivity.b(this.a).setColorFilter(VersionsControlActivity.p(this.a, 2130903282), PorterDuff.Mode.SRC_IN);
        }
        VersionsControlActivity.d(this.a).invalidateViews();
    }
}
