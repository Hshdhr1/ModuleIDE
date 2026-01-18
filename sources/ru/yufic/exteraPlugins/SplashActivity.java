package ru.yufic.exteraPlugins;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class SplashActivity extends AppCompatActivity {
    private RelativeLayout b;
    private LightningStripesView c;
    private LinearLayout d;
    private LinearLayout e;
    private RelativeLayout f;
    private LinearLayout g;
    private LinearLayout h;
    private LinearLayout i;
    private ImageView j;
    private ImageView k;
    private LinearLayout l;
    private TextView m;
    private LinearLayout n;
    private TextView o;
    private LinearLayout p;
    private TextView q;
    private LinearLayout r;
    private LinearLayout s;
    private LinearLayout t;
    private MaterialButton u;
    private LinearLayout v;
    private SharedPreferences y;
    private double a = 0.0d;
    private Intent w = new Intent();
    private ObjectAnimator x = new ObjectAnimator();

    static /* bridge */ /* synthetic */ Intent a(SplashActivity splashActivity) {
        return splashActivity.w;
    }

    static /* bridge */ /* synthetic */ SharedPreferences b(SplashActivity splashActivity) {
        return splashActivity.y;
    }

    private int c(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void d(Bundle bundle) {
        this.b = findViewById(2131231043);
        this.c = (LightningStripesView) findViewById(2131231031);
        this.d = findViewById(2131231032);
        this.e = findViewById(2131231034);
        this.f = findViewById(2131231175);
        this.g = findViewById(2131231035);
        this.h = findViewById(2131231042);
        this.i = findViewById(2131231033);
        this.j = findViewById(2131231005);
        this.k = findViewById(2131231002);
        this.l = findViewById(2131231037);
        this.m = findViewById(2131231296);
        this.n = findViewById(2131231036);
        this.o = findViewById(2131231297);
        this.p = findViewById(2131231030);
        this.q = findViewById(2131231298);
        this.r = findViewById(2131231029);
        this.s = findViewById(2131231039);
        this.t = findViewById(2131231041);
        this.u = findViewById(2131230850);
        this.v = findViewById(2131231040);
        this.y = getSharedPreferences("sp", 0);
        this.u.setOnClickListener(new w5(this));
        this.x.addListener(new x5(this));
    }

    private void e() {
        if (this.y.contains("start1")) {
            this.w.setClass(getApplicationContext(), MainActivity.class);
            startActivity(this.w);
            return;
        }
        this.x.setTarget(this.j);
        this.x.setPropertyName("rotation");
        this.x.setFloatValues(new float[]{0.0f, 360.0f});
        this.x.setDuration(7000L);
        this.x.setRepeatMode(2);
        this.x.setRepeatCount(-1);
        this.x.start();
        this.k.setColorFilter(c(2130903272), PorterDuff.Mode.SRC_IN);
        this.q.setText(getString(2131689503));
        if ((getResources().getConfiguration().uiMode & 48) == 32) {
            this.b.setBackgroundColor(-16777216);
        } else {
            this.b.setBackgroundColor(-1);
            getWindow().getDecorView().setSystemUiVisibility(8192);
            getWindow().setStatusBarColor(-1);
        }
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        window.getDecorView().setSystemUiVisibility(1792);
        this.a = 5.0d;
        this.u.setElevation((float) 5.0d);
        this.m.setElevation((float) this.a);
        this.o.setElevation((float) this.a);
        this.q.setElevation((float) this.a);
        this.f.setElevation((float) this.a);
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427444);
        d(bundle);
        e();
    }
}
