package ru.yufic.exteraPlugins;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class TempActivity extends AppCompatActivity {
    private RelativeLayout a;
    private LightningStripesView b;
    private LinearLayout c;
    private RelativeLayout d;
    private ImageView e;
    private ImageView f;

    private int a(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void b(Bundle bundle) {
        this.a = findViewById(2131231032);
        this.b = (LightningStripesView) findViewById(2131231031);
        this.c = findViewById(2131231033);
        this.d = findViewById(2131231175);
        this.e = findViewById(2131231005);
        this.f = findViewById(2131231002);
    }

    private void c() {
        this.f.setColorFilter(a(2130903272), PorterDuff.Mode.SRC_IN);
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        window.getDecorView().setSystemUiVisibility(1792);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427446);
        b(bundle);
        c();
    }
}
