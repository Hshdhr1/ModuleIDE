package ru.yufic.exteraPlugins;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class DocumActivity extends AppCompatActivity {
    private LinearLayout A;
    private LinearLayout B;
    private LinearLayout C;
    private LinearLayout D;
    private LinearLayout E;
    private LinearLayout F;
    private LinearLayout G;
    private LinearLayout H;
    private LinearLayout I;
    private ImageView J;
    private TextView K;
    private ImageView L;
    private TextView M;
    private ImageView N;
    private TextView O;
    private ImageView P;
    private TextView Q;
    private ImageView R;
    private TextView S;
    private ImageView T;
    private TextView U;
    private ImageView V;
    private TextView W;
    private ImageView X;
    private TextView Y;
    private ImageView Z;
    private MaterialToolbar a;
    private TextView a0;
    private AppBarLayout b;
    private Intent b0 = new Intent();
    private CoordinatorLayout c;
    private DrawerLayout d;
    private LinearLayout e;
    private LinearLayout f;
    private LinearLayout g;
    private LinearLayout h;
    private ImageView i;
    private LinearLayout j;
    private LinearLayout k;
    private TextView l;
    private TextView m;
    private ImageView n;
    private WebView o;
    private ProgressBar p;
    private LinearLayout q;
    private LinearLayout r;
    private LinearLayout s;
    private LinearLayout t;
    private ScrollView u;
    private LinearLayout v;
    private TextView w;
    private LinearLayout x;
    private ImageView y;
    private LinearLayout z;

    static /* bridge */ /* synthetic */ TextView A(DocumActivity documActivity) {
        return documActivity.Q;
    }

    static /* bridge */ /* synthetic */ TextView B(DocumActivity documActivity) {
        return documActivity.S;
    }

    static /* bridge */ /* synthetic */ TextView C(DocumActivity documActivity) {
        return documActivity.U;
    }

    static /* bridge */ /* synthetic */ TextView D(DocumActivity documActivity) {
        return documActivity.W;
    }

    static /* bridge */ /* synthetic */ TextView E(DocumActivity documActivity) {
        return documActivity.Y;
    }

    static /* bridge */ /* synthetic */ TextView F(DocumActivity documActivity) {
        return documActivity.a0;
    }

    static /* bridge */ /* synthetic */ ProgressBar G(DocumActivity documActivity) {
        return documActivity.p;
    }

    static /* bridge */ /* synthetic */ TextView H(DocumActivity documActivity) {
        return documActivity.m;
    }

    static /* bridge */ /* synthetic */ Intent I(DocumActivity documActivity) {
        return documActivity.b0;
    }

    static /* bridge */ /* synthetic */ WebView J(DocumActivity documActivity) {
        return documActivity.o;
    }

    static /* bridge */ /* synthetic */ int K(DocumActivity documActivity, int i) {
        return documActivity.L(i);
    }

    private int L(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void M(Bundle bundle) {
        this.b = findViewById(2131230751);
        this.c = findViewById(2131230752);
        MaterialToolbar findViewById = findViewById(2131230756);
        this.a = findViewById;
        setSupportActionBar(findViewById);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.a.setNavigationOnClickListener(new o(this));
        this.d = findViewById(2131230753);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.d, this.a, 2131689504, 2131689504);
        this.d.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        LinearLayout findViewById2 = findViewById(2131230755);
        this.e = findViewById(2131231314);
        this.f = findViewById(2131231028);
        this.g = findViewById(2131230750);
        this.h = findViewById(2131230980);
        this.i = findViewById(2131230731);
        this.j = findViewById(2131231047);
        this.k = findViewById(2131231048);
        this.l = findViewById(2131230730);
        this.m = findViewById(2131231296);
        this.n = findViewById(2131230736);
        WebView findViewById3 = findViewById(2131231342);
        this.o = findViewById3;
        findViewById3.getSettings().setJavaScriptEnabled(true);
        this.o.getSettings().setSupportZoom(true);
        this.p = findViewById(2131231168);
        this.q = findViewById2.findViewById(2131231043);
        this.r = findViewById2.findViewById(2131230722);
        this.s = findViewById2.findViewById(2131230980);
        this.t = findViewById2.findViewById(2131231036);
        this.u = findViewById2.findViewById(2131231341);
        this.v = findViewById2.findViewById(2131231035);
        this.w = findViewById2.findViewById(2131231296);
        this.x = findViewById2.findViewById(2131231037);
        this.y = findViewById2.findViewById(2131230833);
        this.z = findViewById2.findViewById(2131231038);
        this.A = findViewById2.findViewById(2131231138);
        this.B = findViewById2.findViewById(2131231139);
        this.C = findViewById2.findViewById(2131231140);
        this.D = findViewById2.findViewById(2131231141);
        this.E = findViewById2.findViewById(2131231142);
        this.F = findViewById2.findViewById(2131231143);
        this.G = findViewById2.findViewById(2131231144);
        this.H = findViewById2.findViewById(2131231145);
        this.I = findViewById2.findViewById(2131231146);
        this.J = findViewById2.findViewById(2131230984);
        this.K = findViewById2.findViewById(2131231248);
        this.L = findViewById2.findViewById(2131230985);
        this.M = findViewById2.findViewById(2131231249);
        this.N = findViewById2.findViewById(2131230986);
        this.O = findViewById2.findViewById(2131231250);
        this.P = findViewById2.findViewById(2131230987);
        this.Q = findViewById2.findViewById(2131231251);
        this.R = findViewById2.findViewById(2131230988);
        this.S = findViewById2.findViewById(2131231252);
        this.T = findViewById2.findViewById(2131230989);
        this.U = findViewById2.findViewById(2131231253);
        this.V = findViewById2.findViewById(2131230990);
        this.W = findViewById2.findViewById(2131231254);
        this.X = findViewById2.findViewById(2131230991);
        this.Y = findViewById2.findViewById(2131231255);
        this.Z = findViewById2.findViewById(2131230992);
        this.a0 = findViewById2.findViewById(2131231256);
        this.i.setOnClickListener(new p(this));
        this.n.setOnClickListener(new q(this));
        this.o.setWebViewClient(new s(this));
    }

    private void N() {
        runOnUiThread(new n0(this));
        this.o.setWebChromeClient(new o0(this));
        this.o.loadUrl("file:///android_asset/".concat("doc/first-plugin.html"));
    }

    static /* bridge */ /* synthetic */ ImageView a(DocumActivity documActivity) {
        return documActivity.i;
    }

    static /* bridge */ /* synthetic */ ImageView b(DocumActivity documActivity) {
        return documActivity.n;
    }

    static /* bridge */ /* synthetic */ DrawerLayout c(DocumActivity documActivity) {
        return documActivity.d;
    }

    static /* bridge */ /* synthetic */ ImageView d(DocumActivity documActivity) {
        return documActivity.y;
    }

    static /* bridge */ /* synthetic */ ImageView e(DocumActivity documActivity) {
        return documActivity.J;
    }

    static /* bridge */ /* synthetic */ ImageView f(DocumActivity documActivity) {
        return documActivity.L;
    }

    static /* bridge */ /* synthetic */ ImageView g(DocumActivity documActivity) {
        return documActivity.N;
    }

    static /* bridge */ /* synthetic */ ImageView h(DocumActivity documActivity) {
        return documActivity.P;
    }

    static /* bridge */ /* synthetic */ ImageView i(DocumActivity documActivity) {
        return documActivity.R;
    }

    static /* bridge */ /* synthetic */ ImageView j(DocumActivity documActivity) {
        return documActivity.T;
    }

    static /* bridge */ /* synthetic */ ImageView k(DocumActivity documActivity) {
        return documActivity.V;
    }

    static /* bridge */ /* synthetic */ ImageView l(DocumActivity documActivity) {
        return documActivity.X;
    }

    static /* bridge */ /* synthetic */ ImageView m(DocumActivity documActivity) {
        return documActivity.Z;
    }

    static /* bridge */ /* synthetic */ LinearLayout n(DocumActivity documActivity) {
        return documActivity.q;
    }

    static /* bridge */ /* synthetic */ LinearLayout o(DocumActivity documActivity) {
        return documActivity.A;
    }

    static /* bridge */ /* synthetic */ LinearLayout p(DocumActivity documActivity) {
        return documActivity.B;
    }

    static /* bridge */ /* synthetic */ LinearLayout q(DocumActivity documActivity) {
        return documActivity.C;
    }

    static /* bridge */ /* synthetic */ LinearLayout r(DocumActivity documActivity) {
        return documActivity.D;
    }

    static /* bridge */ /* synthetic */ LinearLayout s(DocumActivity documActivity) {
        return documActivity.E;
    }

    static /* bridge */ /* synthetic */ LinearLayout t(DocumActivity documActivity) {
        return documActivity.F;
    }

    static /* bridge */ /* synthetic */ LinearLayout u(DocumActivity documActivity) {
        return documActivity.G;
    }

    static /* bridge */ /* synthetic */ LinearLayout v(DocumActivity documActivity) {
        return documActivity.H;
    }

    static /* bridge */ /* synthetic */ LinearLayout w(DocumActivity documActivity) {
        return documActivity.I;
    }

    static /* bridge */ /* synthetic */ TextView x(DocumActivity documActivity) {
        return documActivity.K;
    }

    static /* bridge */ /* synthetic */ TextView y(DocumActivity documActivity) {
        return documActivity.M;
    }

    static /* bridge */ /* synthetic */ TextView z(DocumActivity documActivity) {
        return documActivity.O;
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(17432576, 17432577);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427379);
        M(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1000);
        } else {
            N();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            N();
        }
    }
}
