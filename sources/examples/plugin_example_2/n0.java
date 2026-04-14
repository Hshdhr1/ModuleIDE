package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.ImageView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n0 implements Runnable {
    final /* synthetic */ DocumActivity a;

    n0(DocumActivity documActivity) {
        this.a = documActivity;
    }

    static /* synthetic */ DocumActivity a(n0 n0Var) {
        return n0Var.a;
    }

    public void run() {
        this.a.getSupportActionBar().hide();
        DocumActivity.a(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.a(this.a).getBackground(), (Drawable) null));
        DocumActivity.b(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.b(this.a).getBackground(), (Drawable) null));
        ImageView a = DocumActivity.a(this.a);
        int K = DocumActivity.K(this.a, 2130903278);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        a.setColorFilter(K, mode);
        DocumActivity.b(this.a).setColorFilter(DocumActivity.K(this.a, 2130903278), mode);
        DocumActivity.J(this.a).setVisibility(4);
        DocumActivity.G(this.a).setVisibility(4);
        DocumActivity.d(this.a).setColorFilter(DocumActivity.K(this.a, 2130903278), mode);
        DocumActivity.n(this.a).setOnClickListener(new d0(this));
        DocumActivity.d(this.a).setOnClickListener(new f0(this));
        DocumActivity.e(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.o(this.a).setClipToOutline(true);
        DocumActivity.o(this.a).setOutlineProvider(new g0(this));
        DocumActivity.o(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.o(this.a).getBackground(), (Drawable) null));
        DocumActivity.o(this.a).setOnClickListener(new h0(this));
        DocumActivity.f(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.p(this.a).setClipToOutline(true);
        DocumActivity.p(this.a).setOutlineProvider(new i0(this));
        DocumActivity.p(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.p(this.a).getBackground(), (Drawable) null));
        DocumActivity.p(this.a).setOnClickListener(new j0(this));
        DocumActivity.g(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.q(this.a).setClipToOutline(true);
        DocumActivity.q(this.a).setOutlineProvider(new k0(this));
        DocumActivity.q(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.q(this.a).getBackground(), (Drawable) null));
        DocumActivity.q(this.a).setOnClickListener(new l0(this));
        DocumActivity.h(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.r(this.a).setClipToOutline(true);
        DocumActivity.r(this.a).setOutlineProvider(new m0(this));
        DocumActivity.r(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.r(this.a).getBackground(), (Drawable) null));
        DocumActivity.r(this.a).setOnClickListener(new t(this));
        DocumActivity.i(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.s(this.a).setClipToOutline(true);
        DocumActivity.s(this.a).setOutlineProvider(new u(this));
        DocumActivity.s(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.s(this.a).getBackground(), (Drawable) null));
        DocumActivity.s(this.a).setOnClickListener(new v(this));
        DocumActivity.j(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.t(this.a).setClipToOutline(true);
        DocumActivity.t(this.a).setOutlineProvider(new w(this));
        DocumActivity.t(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.t(this.a).getBackground(), (Drawable) null));
        DocumActivity.t(this.a).setOnClickListener(new x(this));
        DocumActivity.k(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.u(this.a).setClipToOutline(true);
        DocumActivity.u(this.a).setOutlineProvider(new y(this));
        DocumActivity.u(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.u(this.a).getBackground(), (Drawable) null));
        DocumActivity.u(this.a).setOnClickListener(new z(this));
        DocumActivity.l(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.v(this.a).setClipToOutline(true);
        DocumActivity.v(this.a).setOutlineProvider(new a0(this));
        DocumActivity.v(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.v(this.a).getBackground(), (Drawable) null));
        DocumActivity.v(this.a).setOnClickListener(new b0(this));
        DocumActivity.m(this.a).setColorFilter(DocumActivity.K(this.a, 2130903272), mode);
        DocumActivity.w(this.a).setClipToOutline(true);
        DocumActivity.w(this.a).setOutlineProvider(new c0(this));
        DocumActivity.w(this.a).setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{DocumActivity.K(this.a, 2130903282)}), DocumActivity.w(this.a).getBackground(), (Drawable) null));
        DocumActivity.w(this.a).setOnClickListener(new e0(this));
    }
}
