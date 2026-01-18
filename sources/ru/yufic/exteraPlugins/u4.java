package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class u4 implements Runnable {
    final /* synthetic */ SettingsActivity a;

    u4(SettingsActivity settingsActivity) {
        this.a = settingsActivity;
    }

    static /* synthetic */ SettingsActivity a(u4 u4Var) {
        return u4Var.a;
    }

    public void run() {
        SettingsActivity.G(this.a).setText("/\\_/\\\n( o.o )");
        ImageView a = SettingsActivity.a(this.a);
        int W = SettingsActivity.W(this.a, 2130903278);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        a.setColorFilter(W, mode);
        SettingsActivity.b(this.a).setColorFilter(SettingsActivity.W(this.a, 2130903272), mode);
        SettingsActivity.q(this.a).setClipToOutline(true);
        SettingsActivity.q(this.a).setOutlineProvider(new t4(this));
        SettingsActivity.q(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{SettingsActivity.W(this.a, 2130903282)}), SettingsActivity.q(this.a).getBackground(), (Drawable) null));
        SettingsActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{SettingsActivity.W(this.a, 2130903282)}), SettingsActivity.a(this.a).getBackground(), (Drawable) null));
        if (this.a.getIntent().hasExtra("proj")) {
            SettingsActivity.p(this.a).setVisibility(8);
            SettingsActivity.i(this.a).setVisibility(8);
            SettingsActivity.n(this.a).setVisibility(8);
            SettingsActivity.o(this.a).setVisibility(8);
            SettingsActivity.h(this.a).setVisibility(8);
        }
    }
}
