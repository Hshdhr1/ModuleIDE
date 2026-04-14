package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class d6 implements Runnable {
    final /* synthetic */ VersionsControlActivity a;

    d6(VersionsControlActivity versionsControlActivity) {
        this.a = versionsControlActivity;
    }

    public void run() {
        VersionsControlActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.a, 2130903282)}), VersionsControlActivity.a(this.a).getBackground(), (Drawable) null));
        VersionsControlActivity.b(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.a, 2130903282)}), VersionsControlActivity.b(this.a).getBackground(), (Drawable) null));
        ImageView a = VersionsControlActivity.a(this.a);
        int p = VersionsControlActivity.p(this.a, 2130903278);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        a.setColorFilter(p, mode);
        VersionsControlActivity.b(this.a).setColorFilter(VersionsControlActivity.p(this.a, 2130903278), mode);
        VersionsControlActivity.m(this.a, false);
    }
}
