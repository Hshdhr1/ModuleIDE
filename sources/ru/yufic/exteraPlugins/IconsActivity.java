package ru.yufic.exteraPlugins;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.color.MaterialColors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class IconsActivity extends AppCompatActivity {
    private HashMap a = new HashMap();
    private double b = 0.0d;
    private ArrayList c = new ArrayList();
    private ArrayList d = new ArrayList();
    private LinearLayout e;
    private LinearLayout f;
    private LinearLayout g;
    private LinearLayout h;
    private ImageView i;
    private LinearLayout j;
    private LinearLayout k;
    private TextView l;
    private ImageView m;
    private ListView n;

    static /* bridge */ /* synthetic */ int a(IconsActivity iconsActivity, int i) {
        return iconsActivity.b(i);
    }

    private int b(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void c(Bundle bundle) {
        this.e = findViewById(2131231314);
        this.f = findViewById(2131230840);
        this.g = findViewById(2131230750);
        this.h = findViewById(2131230980);
        this.i = findViewById(2131230731);
        this.j = findViewById(2131231047);
        this.k = findViewById(2131231048);
        this.l = findViewById(2131230730);
        this.m = findViewById(2131230736);
        this.n = findViewById(2131231051);
    }

    private void d() {
        this.i.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{b(2130903282)}), this.i.getBackground(), (Drawable) null));
        this.i.setColorFilter(b(2130903278), PorterDuff.Mode.SRC_IN);
        this.m.setVisibility(4);
        try {
            this.c = new ArrayList(Arrays.asList(getAssets().list("icons/solar")));
        } catch (Exception unused) {
        }
        this.b = 0.0d;
        for (int i = 0; i < this.c.size(); i++) {
            HashMap hashMap = new HashMap();
            this.a = hashMap;
            hashMap.put("name", Uri.parse((String) this.c.get((int) this.b)).getLastPathSegment().replace(".xml", ""));
            this.a.put("path", this.c.get((int) this.b));
            this.b += 1.0d;
            this.d.add(this.a);
        }
        this.n.setAdapter(new a3(this, this.d));
        this.n.getAdapter().notifyDataSetChanged();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427381);
        c(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1000);
        } else {
            d();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            d();
        }
    }
}
