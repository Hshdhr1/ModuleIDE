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
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class q4 extends BaseAdapter {
    ArrayList a;
    final /* synthetic */ MainActivity b;

    public q4(MainActivity mainActivity, ArrayList arrayList) {
        this.b = mainActivity;
        this.a = arrayList;
    }

    static /* synthetic */ MainActivity a(q4 q4Var) {
        return q4Var.b;
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
        View inflate = view == null ? this.b.getLayoutInflater().inflate(2131427439, (ViewGroup) null) : view;
        LinearLayout findViewById = inflate.findViewById(2131230739);
        LinearLayout findViewById2 = inflate.findViewById(2131231028);
        LinearLayout findViewById3 = inflate.findViewById(2131231044);
        TextView findViewById4 = inflate.findViewById(2131231298);
        inflate.findViewById(2131231036);
        inflate.findViewById(2131231040);
        TextView findViewById5 = inflate.findViewById(2131231307);
        inflate.findViewById(2131231037);
        TextView findViewById6 = inflate.findViewById(2131231306);
        inflate.findViewById(2131231297);
        TextView findViewById7 = inflate.findViewById(2131231304);
        ImageView findViewById8 = inflate.findViewById(2131230726);
        ImageView findViewById9 = inflate.findViewById(2131230725);
        ImageView findViewById10 = inflate.findViewById(2131230928);
        ImageView findViewById11 = inflate.findViewById(2131230728);
        inflate.findViewById(2131231277);
        inflate.findViewById(2131231300);
        MainActivity.K(this.b, (HashMap) this.a.get(i));
        View view2 = inflate;
        MainActivity.M(this.b, (ArrayList) new Gson().fromJson(MainActivity.C(this.b).get("list").toString(), new g4(this).getType()));
        findViewById5.setText(((HashMap) MainActivity.E(this.b).get(MainActivity.E(this.b).size() - 1)).get("plugin_name").toString());
        findViewById7.setText(((HashMap) MainActivity.E(this.b).get(MainActivity.E(this.b).size() - 1)).get("plugin_author").toString());
        findViewById6.setText(((HashMap) this.a.get(i)).get("plugin_id").toString());
        findViewById4.setText(((HashMap) MainActivity.E(this.b).get(MainActivity.E(this.b).size() - 1)).get("version").toString());
        findViewById2.setClipToOutline(true);
        findViewById2.setOutlineProvider(new h4(this));
        findViewById2.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.b, 2130903289)}), findViewById2.getBackground(), (Drawable) null));
        if (i + 1 == this.a.size()) {
            findViewById3.setVisibility(0);
        } else {
            findViewById3.setVisibility(8);
        }
        if (MainActivity.z(this.b)) {
            findViewById9.setAlpha(1.0f);
            findViewById8.setAlpha(1.0f);
            findViewById9.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.b, 2130903289)}), findViewById9.getBackground(), (Drawable) null));
            findViewById8.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.b, 2130903289)}), findViewById8.getBackground(), (Drawable) null));
            findViewById9.setEnabled(true);
            findViewById9.setVisibility(0);
            if (i == this.a.size() - 1) {
                findViewById9.setEnabled(false);
                findViewById9.setAlpha(0.3f);
            }
            findViewById8.setEnabled(true);
            findViewById8.setVisibility(0);
            if (i == 0) {
                findViewById8.setEnabled(false);
                findViewById8.setAlpha(0.3f);
            }
            findViewById11.setVisibility(8);
            findViewById10.setVisibility(8);
            int O = MainActivity.O(this.b, 2130903278);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            findViewById9.setColorFilter(O, mode);
            findViewById8.setColorFilter(MainActivity.O(this.b, 2130903278), mode);
        } else {
            findViewById11.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.b, 2130903289)}), findViewById11.getBackground(), (Drawable) null));
            findViewById10.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{MainActivity.O(this.b, 2130903289)}), findViewById10.getBackground(), (Drawable) null));
            findViewById9.setVisibility(8);
            findViewById8.setVisibility(8);
            findViewById11.setVisibility(0);
            findViewById10.setVisibility(0);
            findViewById10.setColorFilter(MainActivity.O(this.b, 2130903278), PorterDuff.Mode.SRC_IN);
        }
        findViewById.setOnClickListener(new i4(this));
        findViewById2.setOnClickListener(new j4(this, findViewById6, findViewById5, i));
        findViewById11.setOnClickListener(new m4(this, i));
        findViewById9.setOnClickListener(new n4(this, i));
        findViewById8.setOnClickListener(new o4(this, i));
        findViewById10.setOnClickListener(new p4(this, i));
        return view2;
    }
}
