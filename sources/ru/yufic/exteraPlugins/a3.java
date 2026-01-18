package ru.yufic.exteraPlugins;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class a3 extends BaseAdapter {
    ArrayList a;
    final /* synthetic */ IconsActivity b;

    public a3(IconsActivity iconsActivity, ArrayList arrayList) {
        this.b = iconsActivity;
        this.a = arrayList;
    }

    static /* synthetic */ IconsActivity a(a3 a3Var) {
        return a3Var.b;
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
        ImageView findViewById;
        String str;
        InputStream open;
        XmlPullParser newPullParser;
        int next;
        LayoutInflater layoutInflater = this.b.getLayoutInflater();
        if (view == null) {
            view = layoutInflater.inflate(2131427382, (ViewGroup) null);
        }
        LinearLayout findViewById2 = view.findViewById(2131231028);
        view.findViewById(2131230980);
        ImageView findViewById3 = view.findViewById(2131231000);
        view.findViewById(2131231296).setText(((HashMap) this.a.get(i)).get("name").toString());
        try {
            findViewById = this.b.findViewById(2131231000);
            str = "icons/solar/" + ((HashMap) this.a.get(i)).get("path").toString();
            open = this.b.getAssets().open(str);
            newPullParser = Xml.newPullParser();
            newPullParser.setInput(open, "utf-8");
            do {
                next = newPullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!newPullParser.getName().equals("vector")) {
            throw new IllegalArgumentException("Не <vector> файл: " + str);
        }
        Drawable createFromXml = VectorDrawableCompat.createFromXml(this.b.getResources(), newPullParser, this.b.getTheme());
        open.close();
        findViewById.setImageDrawable(createFromXml);
        findViewById3.setColorFilter(IconsActivity.a(this.b, 2130903282), PorterDuff.Mode.SRC_IN);
        findViewById2.setOnClickListener(new z2(this, i));
        return view;
    }
}
