package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class i1 implements Runnable {
    final /* synthetic */ EditActivity a;

    i1(EditActivity editActivity) {
        this.a = editActivity;
    }

    static /* synthetic */ EditActivity a(i1 i1Var) {
        return i1Var.a;
    }

    public void run() {
        this.a.getSupportActionBar().hide();
        EditActivity.b(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.b(this.a).getBackground(), (Drawable) null));
        EditActivity.e(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.e(this.a).getBackground(), (Drawable) null));
        EditActivity.d(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.d(this.a).getBackground(), (Drawable) null));
        EditActivity.c(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.c(this.a).getBackground(), (Drawable) null));
        ImageView b = EditActivity.b(this.a);
        int e0 = EditActivity.e0(this.a, 2130903278);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        b.setColorFilter(e0, mode);
        EditActivity.d(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.c(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.e(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.u(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.u(this.a).getBackground(), (Drawable) null));
        EditActivity.s(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.s(this.a).getBackground(), (Drawable) null));
        EditActivity.u(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.s(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.O(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.O(this.a).getBackground(), (Drawable) null));
        EditActivity.O(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.t(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.t(this.a).getBackground(), (Drawable) null));
        EditActivity.t(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.y(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.y(this.a).getBackground(), (Drawable) null));
        EditActivity.y(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.M(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.M(this.a).getBackground(), (Drawable) null));
        EditActivity.M(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.A(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.A(this.a).getBackground(), (Drawable) null));
        EditActivity.A(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.v(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.v(this.a).getBackground(), (Drawable) null));
        EditActivity.v(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.a(this.a).getBackground(), (Drawable) null));
        EditActivity.a(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.y(this.a).setVisibility(8);
        EditActivity.A(this.a).setVisibility(8);
        EditActivity.a(this.a).setVisibility(8);
        EditActivity.P(this.a).setClipToOutline(true);
        EditActivity.P(this.a).setOutlineProvider(new z0(this));
        EditActivity.C(this.a).setClipToOutline(true);
        EditActivity.C(this.a).setOutlineProvider(new a1(this));
        EditActivity.g(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.g(this.a).getBackground(), (Drawable) null));
        EditActivity.g(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.g(this.a).setOnClickListener(new b1(this));
        EditActivity.k(this.a).setOnClickListener(new c1(this));
        EditActivity.h(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.m(this.a).setClipToOutline(true);
        EditActivity.m(this.a).setOutlineProvider(new d1(this));
        EditActivity.m(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.m(this.a).getBackground(), (Drawable) null));
        EditActivity.m(this.a).setOnClickListener(new e1(this));
        EditActivity.i(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.n(this.a).setClipToOutline(true);
        EditActivity.n(this.a).setOutlineProvider(new f1(this));
        EditActivity.n(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.n(this.a).getBackground(), (Drawable) null));
        EditActivity.n(this.a).setOnClickListener(new g1(this));
        EditActivity.j(this.a).setColorFilter(EditActivity.e0(this.a, 2130903278), mode);
        EditActivity.l(this.a).setClipToOutline(true);
        EditActivity.l(this.a).setOutlineProvider(new h1(this));
        EditActivity.l(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.a, 2130903282)}), EditActivity.l(this.a).getBackground(), (Drawable) null));
        EditActivity.l(this.a).setOnClickListener(new y0(this));
    }
}
