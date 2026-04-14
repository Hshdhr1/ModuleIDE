package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class e4 implements Runnable {
    final /* synthetic */ MainActivity a;

    e4(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    static /* synthetic */ MainActivity a(e4 e4Var) {
        return e4Var.a;
    }

    public void run() {
        this.a.getSupportActionBar().hide();
        MainActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.a(this.a).getBackground(), (Drawable) null));
        ImageView a = MainActivity.a(this.a);
        int O = MainActivity.O(this.a, 2130903276);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        a.setColorFilter(O, mode);
        MainActivity.s(this.a).setColorFilter(MainActivity.O(this.a, 2130903276), mode);
        MainActivity.b(this.a).setColorFilter(MainActivity.O(this.a, 2130903276), mode);
        MainActivity.b(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.b(this.a).getBackground(), (Drawable) null));
        MainActivity.u(this.a).setOnScrollListener(new v3(this));
        MainActivity.I(this.a, false);
        try {
            MainActivity.o(this.a).setText("YUFIC — v".concat(this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 0).versionName.concat(" (".concat(String.valueOf(this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 0).versionCode).concat(")")))));
        } catch (Exception unused) {
            MainActivity.o(this.a).setText("YUFIC — v".concat("?.?.?"));
        }
        ImageView d = MainActivity.d(this.a);
        int O2 = MainActivity.O(this.a, 2130903272);
        PorterDuff.Mode mode2 = PorterDuff.Mode.SRC_IN;
        d.setColorFilter(O2, mode2);
        MainActivity.j(this.a).setClipToOutline(true);
        MainActivity.j(this.a).setOutlineProvider(new w3(this));
        MainActivity.k(this.a).setClipToOutline(true);
        MainActivity.k(this.a).setOutlineProvider(new x3(this));
        MainActivity.e(this.a).setColorFilter(MainActivity.O(this.a, 2130903272), mode2);
        MainActivity.k(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.k(this.a).getBackground(), (Drawable) null));
        MainActivity.k(this.a).setOnClickListener(new y3(this));
        MainActivity.l(this.a).setClipToOutline(true);
        MainActivity.l(this.a).setOutlineProvider(new z3(this));
        MainActivity.f(this.a).setColorFilter(MainActivity.O(this.a, 2130903272), mode2);
        MainActivity.l(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.l(this.a).getBackground(), (Drawable) null));
        MainActivity.l(this.a).setOnClickListener(new a4(this));
        MainActivity.m(this.a).setClipToOutline(true);
        MainActivity.m(this.a).setOutlineProvider(new b4(this));
        MainActivity.g(this.a).setColorFilter(MainActivity.O(this.a, 2130903272), mode2);
        MainActivity.m(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.m(this.a).getBackground(), (Drawable) null));
        MainActivity.m(this.a).setOnClickListener(new c4(this));
        MainActivity.i(this.a).setOnClickListener(new d4(this));
        MainActivity.n(this.a).setClipToOutline(true);
        MainActivity.n(this.a).setOutlineProvider(new t3(this));
        MainActivity.h(this.a).setColorFilter(MainActivity.O(this.a, 2130903272), mode2);
        MainActivity.n(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.a, 2130903282)}), MainActivity.n(this.a).getBackground(), (Drawable) null));
        MainActivity.n(this.a).setOnClickListener(new u3(this));
    }
}
