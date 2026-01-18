package ru.yufic.exteraPlugins;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class MainActivity extends AppCompatActivity {
    private TextView A;
    private MaterialButton B;
    private ListView C;
    private LinearLayout D;
    private LinearLayout E;
    private LinearLayout F;
    private ScrollView G;
    private LinearLayout H;
    private LinearLayout I;
    private LinearLayout J;
    private LinearLayout K;
    private LinearLayout L;
    private LinearLayout M;
    private ImageView N;
    private TextView O;
    private TextView P;
    private ImageView Q;
    private TextView R;
    private ImageView S;
    private TextView T;
    private LinearLayout U;
    private LinearLayout V;
    private LinearLayout W;
    private ImageView X;
    private TextView Y;
    private ImageView Z;
    private TextView a0;
    private MaterialToolbar b;
    private TextView b0;
    private AppBarLayout c;
    private CoordinatorLayout d;
    private FloatingActionButton e;
    private DrawerLayout f;
    private TimerTask f0;
    private AlertDialog.Builder g0;
    private AlertDialog.Builder h0;
    private AlertDialog.Builder i0;
    private LinearLayout o;
    private LinearLayout p;
    private LinearLayout q;
    private LinearLayout r;
    private ImageView s;
    private LinearLayout t;
    private LinearLayout u;
    private TextView v;
    private ImageView w;
    private LinearLayout x;
    private SwipeRefreshLayout y;
    private ImageView z;
    private Timer a = new Timer();
    private String g = "";
    private HashMap h = new HashMap();
    private boolean i = false;
    private HashMap j = new HashMap();
    private double k = 0.0d;
    private boolean l = false;
    private ArrayList m = new ArrayList();
    private ArrayList n = new ArrayList();
    private Intent c0 = new Intent();
    private Intent d0 = new Intent();
    private Intent e0 = new Intent();

    static /* bridge */ /* synthetic */ boolean A(MainActivity mainActivity) {
        return mainActivity.l;
    }

    static /* bridge */ /* synthetic */ SwipeRefreshLayout B(MainActivity mainActivity) {
        return mainActivity.y;
    }

    static /* bridge */ /* synthetic */ HashMap C(MainActivity mainActivity) {
        return mainActivity.h;
    }

    static /* bridge */ /* synthetic */ HashMap D(MainActivity mainActivity) {
        return mainActivity.j;
    }

    static /* bridge */ /* synthetic */ ArrayList E(MainActivity mainActivity) {
        return mainActivity.n;
    }

    static /* bridge */ /* synthetic */ TimerTask F(MainActivity mainActivity) {
        return mainActivity.f0;
    }

    static /* bridge */ /* synthetic */ void G(MainActivity mainActivity, double d) {
        mainActivity.k = d;
    }

    static /* bridge */ /* synthetic */ void H(MainActivity mainActivity, ArrayList arrayList) {
        mainActivity.m = arrayList;
    }

    static /* bridge */ /* synthetic */ void I(MainActivity mainActivity, boolean z) {
        mainActivity.i = z;
    }

    static /* bridge */ /* synthetic */ void J(MainActivity mainActivity, boolean z) {
        mainActivity.l = z;
    }

    static /* bridge */ /* synthetic */ void K(MainActivity mainActivity, HashMap hashMap) {
        mainActivity.h = hashMap;
    }

    static /* bridge */ /* synthetic */ void L(MainActivity mainActivity, HashMap hashMap) {
        mainActivity.j = hashMap;
    }

    static /* bridge */ /* synthetic */ void M(MainActivity mainActivity, ArrayList arrayList) {
        mainActivity.n = arrayList;
    }

    static /* bridge */ /* synthetic */ void N(MainActivity mainActivity, TimerTask timerTask) {
        mainActivity.f0 = timerTask;
    }

    static /* bridge */ /* synthetic */ int O(MainActivity mainActivity, int i) {
        return mainActivity.Q(i);
    }

    private int Q(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void R(Bundle bundle) {
        this.c = findViewById(2131230751);
        this.d = findViewById(2131230752);
        MaterialToolbar findViewById = findViewById(2131230756);
        this.b = findViewById;
        setSupportActionBar(findViewById);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.b.setNavigationOnClickListener(new g3(this));
        this.e = findViewById(2131230754);
        this.f = findViewById(2131230753);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.f, this.b, 2131689504, 2131689504);
        this.f.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        LinearLayout findViewById2 = findViewById(2131230755);
        this.o = findViewById(2131231314);
        this.p = findViewById(2131231028);
        this.q = findViewById(2131230750);
        this.r = findViewById(2131230980);
        this.s = findViewById(2131230731);
        this.t = findViewById(2131231047);
        this.u = findViewById(2131231048);
        this.v = findViewById(2131230730);
        this.w = findViewById(2131230736);
        this.x = findViewById(2131231046);
        this.y = findViewById(2131231247);
        this.z = findViewById(2131231000);
        this.A = findViewById(2131231296);
        this.B = findViewById(2131230850);
        this.C = findViewById(2131231051);
        this.D = findViewById2.findViewById(2131231125);
        this.E = findViewById2.findViewById(2131231028);
        this.F = findViewById2.findViewById(2131231333);
        this.G = findViewById2.findViewById(2131231341);
        this.H = findViewById2.findViewById(2131231037);
        this.I = findViewById2.findViewById(2131231036);
        this.J = findViewById2.findViewById(2131231038);
        this.K = findViewById2.findViewById(2131231148);
        this.L = findViewById2.findViewById(2131231149);
        this.M = findViewById2.findViewById(2131231152);
        this.N = findViewById2.findViewById(2131231000);
        this.O = findViewById2.findViewById(2131231296);
        this.P = findViewById2.findViewById(2131231297);
        this.Q = findViewById2.findViewById(2131231001);
        this.R = findViewById2.findViewById(2131231298);
        this.S = findViewById2.findViewById(2131231004);
        this.T = findViewById2.findViewById(2131231303);
        this.U = findViewById2.findViewById(2131231150);
        this.V = findViewById2.findViewById(2131231332);
        this.W = findViewById2.findViewById(2131231151);
        this.X = findViewById2.findViewById(2131231002);
        this.Y = findViewById2.findViewById(2131231299);
        this.Z = findViewById2.findViewById(2131231003);
        this.a0 = findViewById2.findViewById(2131231302);
        this.b0 = findViewById2.findViewById(2131231301);
        this.g0 = new AlertDialog.Builder(this);
        this.h0 = new AlertDialog.Builder(this);
        this.i0 = new AlertDialog.Builder(this);
        this.s.setOnClickListener(new h3(this));
        this.w.setOnClickListener(new i3(this));
        this.y.setOnRefreshListener(new k3(this));
        this.B.setOnClickListener(new q3(this));
        this.e.setOnLongClickListener(new r3(this));
        this.e.setOnClickListener(new s3(this));
    }

    private void S() {
        runOnUiThread(new e4(this));
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.g = l;
            if (!l.trim().equals("")) {
                this.m = (ArrayList) new Gson().fromJson(this.g, new f4(this).getType());
            }
            Collections.reverse(this.m);
            if (this.m.size() == 0) {
                this.x.setVisibility(0);
                this.y.setVisibility(8);
            } else {
                this.C.setAdapter(new q4(this, this.m));
                this.C.getAdapter().notifyDataSetChanged();
                this.x.setVisibility(8);
                this.y.setVisibility(0);
            }
        } catch (Exception unused) {
            this.x.setVisibility(0);
            this.y.setVisibility(8);
        }
    }

    static /* bridge */ /* synthetic */ ImageView a(MainActivity mainActivity) {
        return mainActivity.s;
    }

    static /* bridge */ /* synthetic */ ImageView b(MainActivity mainActivity) {
        return mainActivity.w;
    }

    static /* bridge */ /* synthetic */ DrawerLayout c(MainActivity mainActivity) {
        return mainActivity.f;
    }

    static /* bridge */ /* synthetic */ ImageView d(MainActivity mainActivity) {
        return mainActivity.N;
    }

    static /* bridge */ /* synthetic */ ImageView e(MainActivity mainActivity) {
        return mainActivity.Q;
    }

    static /* bridge */ /* synthetic */ ImageView f(MainActivity mainActivity) {
        return mainActivity.X;
    }

    static /* bridge */ /* synthetic */ ImageView g(MainActivity mainActivity) {
        return mainActivity.Z;
    }

    static /* bridge */ /* synthetic */ ImageView h(MainActivity mainActivity) {
        return mainActivity.S;
    }

    static /* bridge */ /* synthetic */ LinearLayout i(MainActivity mainActivity) {
        return mainActivity.E;
    }

    static /* bridge */ /* synthetic */ LinearLayout j(MainActivity mainActivity) {
        return mainActivity.K;
    }

    static /* bridge */ /* synthetic */ LinearLayout k(MainActivity mainActivity) {
        return mainActivity.L;
    }

    static /* bridge */ /* synthetic */ LinearLayout l(MainActivity mainActivity) {
        return mainActivity.U;
    }

    static /* bridge */ /* synthetic */ LinearLayout m(MainActivity mainActivity) {
        return mainActivity.W;
    }

    static /* bridge */ /* synthetic */ LinearLayout n(MainActivity mainActivity) {
        return mainActivity.M;
    }

    static /* bridge */ /* synthetic */ TextView o(MainActivity mainActivity) {
        return mainActivity.b0;
    }

    static /* bridge */ /* synthetic */ FloatingActionButton p(MainActivity mainActivity) {
        return mainActivity.e;
    }

    static /* bridge */ /* synthetic */ Timer q(MainActivity mainActivity) {
        return mainActivity.a;
    }

    static /* bridge */ /* synthetic */ Intent r(MainActivity mainActivity) {
        return mainActivity.c0;
    }

    static /* bridge */ /* synthetic */ ImageView s(MainActivity mainActivity) {
        return mainActivity.z;
    }

    static /* bridge */ /* synthetic */ LinearLayout t(MainActivity mainActivity) {
        return mainActivity.x;
    }

    static /* bridge */ /* synthetic */ ListView u(MainActivity mainActivity) {
        return mainActivity.C;
    }

    static /* bridge */ /* synthetic */ double v(MainActivity mainActivity) {
        return mainActivity.k;
    }

    static /* bridge */ /* synthetic */ Intent w(MainActivity mainActivity) {
        return mainActivity.d0;
    }

    static /* bridge */ /* synthetic */ Intent x(MainActivity mainActivity) {
        return mainActivity.e0;
    }

    static /* bridge */ /* synthetic */ ArrayList y(MainActivity mainActivity) {
        return mainActivity.m;
    }

    static /* bridge */ /* synthetic */ boolean z(MainActivity mainActivity) {
        return mainActivity.i;
    }

    public void P(double d, ArrayList arrayList) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Изменить ID");
        materialAlertDialogBuilder.setIcon(2131165319);
        materialAlertDialogBuilder.setMessage("Модете измените идентификатор плагина");
        materialAlertDialogBuilder.setCancelable(false);
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        materialAlertDialogBuilder.setView(editText);
        editText.setTextColor(Q(2130903278));
        editText.setHintTextColor(Q(2130903275));
        editText.setHint(getString(2131689539));
        editText.setText(((HashMap) arrayList.get((int) d)).get("plugin_id").toString());
        materialAlertDialogBuilder.setPositiveButton(getString(2131689628), new e3(this, arrayList, editText, d));
        materialAlertDialogBuilder.setNegativeButton(getString(2131689509), new f3(this));
        materialAlertDialogBuilder.show();
    }

    public void onBackPressed() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Покидаете PluginIDE?");
        materialAlertDialogBuilder.setIcon(2131165347);
        materialAlertDialogBuilder.setMessage("Вы действительно хотите выйти из PluginIDE?");
        materialAlertDialogBuilder.setPositiveButton("Выйти", new b3(this));
        materialAlertDialogBuilder.setNeutralButton(getString(2131689509), new c3(this));
        materialAlertDialogBuilder.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427388);
        R(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            S();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            S();
        }
    }

    public void onResume() {
        super.onResume();
        try {
            this.y.setRefreshing(true);
            this.g = a.l(a.f(getApplicationContext()).concat("/list"));
            ArrayList arrayList = (ArrayList) new Gson().fromJson(this.g, new d3(this).getType());
            this.m = arrayList;
            if (arrayList.size() != 0) {
                this.C.getAdapter().notifyDataSetChanged();
                this.x.setVisibility(8);
                this.y.setVisibility(0);
            } else {
                this.x.setVisibility(0);
                this.y.setVisibility(8);
            }
        } catch (Exception unused) {
            this.x.setVisibility(0);
            this.y.setVisibility(8);
        }
        this.y.setRefreshing(false);
    }
}
