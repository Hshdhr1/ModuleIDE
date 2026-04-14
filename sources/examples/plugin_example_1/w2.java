package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class w2 extends BaseAdapter {
    ArrayList a;
    final /* synthetic */ EditActivity b;

    public w2(EditActivity editActivity, ArrayList arrayList) {
        this.b = editActivity;
        this.a = arrayList;
    }

    /* renamed from: a, reason: merged with bridge method [inline-methods] */
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
        LayoutInflater layoutInflater = this.b.getLayoutInflater();
        if (view == null) {
            view = layoutInflater.inflate(2131427465, (ViewGroup) null);
        }
        TextView findViewById = view.findViewById(2131231296);
        findViewById.setText(((HashMap) this.a.get(i)).get("version").toString());
        findViewById.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{EditActivity.e0(this.b, 2130903289)}), findViewById.getBackground(), (Drawable) null));
        return view;
    }
}
