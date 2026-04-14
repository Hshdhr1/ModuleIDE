package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class a implements Runnable {
    final /* synthetic */ CreateActivity a;

    a(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void run() {
        CreateActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{CreateActivity.E(this.a, 2130903282)}), CreateActivity.a(this.a).getBackground(), (Drawable) null));
        CreateActivity.b(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{CreateActivity.E(this.a, 2130903282)}), CreateActivity.b(this.a).getBackground(), (Drawable) null));
        ImageView a = CreateActivity.a(this.a);
        int E = CreateActivity.E(this.a, 2130903276);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        a.setColorFilter(E, mode);
        CreateActivity.b(this.a).setColorFilter(CreateActivity.E(this.a, 2130903276), mode);
        CreateActivity.n(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{CreateActivity.E(this.a, 2130903282)}), CreateActivity.n(this.a).getBackground(), (Drawable) null));
        CreateActivity.n(this.a).setColorFilter(CreateActivity.E(this.a, 2130903276), mode);
    }
}
