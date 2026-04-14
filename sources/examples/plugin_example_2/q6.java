package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class q6 extends BaseAdapter {
    ArrayList a;
    final /* synthetic */ VersionsControlActivity b;

    public q6(VersionsControlActivity versionsControlActivity, ArrayList arrayList) {
        this.b = versionsControlActivity;
        this.a = arrayList;
    }

    static /* synthetic */ VersionsControlActivity a(q6 q6Var) {
        return q6Var.b;
    }

    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public HashMap getItem(int i) {
        return (HashMap) this.a.get(i);
    }

    public int getCount() {
        return this.a.size();
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        float f;
        View inflate = view == null ? this.b.getLayoutInflater().inflate(2131427463, (ViewGroup) null) : view;
        LinearLayout findViewById = inflate.findViewById(2131230739);
        LinearLayout findViewById2 = inflate.findViewById(2131231028);
        TextView findViewById3 = inflate.findViewById(2131231298);
        inflate.findViewById(2131231036);
        inflate.findViewById(2131231040);
        TextView findViewById4 = inflate.findViewById(2131231307);
        inflate.findViewById(2131231037);
        TextView findViewById5 = inflate.findViewById(2131231304);
        ImageView findViewById6 = inflate.findViewById(2131230726);
        ImageView findViewById7 = inflate.findViewById(2131230725);
        ImageView findViewById8 = inflate.findViewById(2131231001);
        ImageView findViewById9 = inflate.findViewById(2131230728);
        findViewById3.setText(((HashMap) this.a.get(i)).get("version").toString());
        findViewById4.setText(((HashMap) this.a.get(i)).get("plugin_name").toString());
        findViewById5.setText(((HashMap) this.a.get(i)).get("plugin_author").toString());
        findViewById2.setClipToOutline(true);
        findViewById2.setOutlineProvider(new i6(this));
        if (VersionsControlActivity.g(this.b)) {
            findViewById7.setAlpha(1.0f);
            findViewById6.setAlpha(1.0f);
            findViewById7.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.b, 2130903289)}), findViewById7.getBackground(), (Drawable) null));
            findViewById6.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.b, 2130903289)}), findViewById6.getBackground(), (Drawable) null));
            findViewById7.setEnabled(true);
            findViewById7.setVisibility(0);
            if (i == this.a.size() - 1) {
                findViewById7.setEnabled(false);
                f = 0.3f;
                findViewById7.setAlpha(0.3f);
            } else {
                f = 0.3f;
            }
            findViewById6.setEnabled(true);
            findViewById6.setVisibility(0);
            if (i == 0) {
                findViewById6.setEnabled(false);
                findViewById6.setAlpha(f);
            }
            findViewById9.setVisibility(8);
            findViewById8.setVisibility(8);
            int p = VersionsControlActivity.p(this.b, 2130903278);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            findViewById7.setColorFilter(p, mode);
            findViewById6.setColorFilter(VersionsControlActivity.p(this.b, 2130903278), mode);
        } else {
            findViewById9.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.b, 2130903289)}), findViewById9.getBackground(), (Drawable) null));
            findViewById8.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{VersionsControlActivity.p(this.b, 2130903289)}), findViewById8.getBackground(), (Drawable) null));
            findViewById7.setVisibility(8);
            findViewById6.setVisibility(8);
            findViewById8.setVisibility(0);
            findViewById8.setColorFilter(VersionsControlActivity.p(this.b, 2130903278), PorterDuff.Mode.SRC_IN);
            findViewById9.setVisibility(0);
            if (this.a.size() == 1) {
                findViewById9.setEnabled(false);
                findViewById9.setAlpha(0.3f);
            } else {
                findViewById9.setEnabled(true);
                findViewById9.setAlpha(1.0f);
            }
        }
        findViewById.setOnClickListener(new j6(this));
        findViewById8.setOnClickListener(new k6(this, i, findViewById3));
        findViewById9.setOnClickListener(new n6(this, i));
        findViewById7.setOnClickListener(new o6(this, i));
        findViewById6.setOnClickListener(new p6(this, i));
        return inflate;
    }
}
