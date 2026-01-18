package ru.yufic.exteraPlugins;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.color.MaterialColors;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class VersionsControlActivity extends AppCompatActivity {
    private LinearLayout f;
    private LinearLayout g;
    private LinearLayout h;
    private LinearLayout i;
    private ImageView j;
    private LinearLayout k;
    private LinearLayout l;
    private TextView m;
    private TextView n;
    private ImageView o;
    private SwipeRefreshLayout p;
    private ListView q;
    private AlertDialog.Builder r;
    private String a = "";
    private boolean b = false;
    private HashMap c = new HashMap();
    private ArrayList d = new ArrayList();
    private ArrayList e = new ArrayList();
    private Intent s = new Intent();

    static /* bridge */ /* synthetic */ ImageView a(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.j;
    }

    static /* bridge */ /* synthetic */ ImageView b(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.o;
    }

    static /* bridge */ /* synthetic */ Intent c(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.s;
    }

    static /* bridge */ /* synthetic */ ListView d(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.q;
    }

    static /* bridge */ /* synthetic */ ArrayList e(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.d;
    }

    static /* bridge */ /* synthetic */ String f(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.a;
    }

    static /* bridge */ /* synthetic */ boolean g(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.b;
    }

    static /* bridge */ /* synthetic */ SwipeRefreshLayout h(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.p;
    }

    static /* bridge */ /* synthetic */ HashMap i(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.c;
    }

    static /* bridge */ /* synthetic */ ArrayList j(VersionsControlActivity versionsControlActivity) {
        return versionsControlActivity.e;
    }

    static /* bridge */ /* synthetic */ void k(VersionsControlActivity versionsControlActivity, ArrayList arrayList) {
        versionsControlActivity.d = arrayList;
    }

    static /* bridge */ /* synthetic */ void l(VersionsControlActivity versionsControlActivity, String str) {
        versionsControlActivity.a = str;
    }

    static /* bridge */ /* synthetic */ void m(VersionsControlActivity versionsControlActivity, boolean z) {
        versionsControlActivity.b = z;
    }

    static /* bridge */ /* synthetic */ void n(VersionsControlActivity versionsControlActivity, HashMap hashMap) {
        versionsControlActivity.c = hashMap;
    }

    static /* bridge */ /* synthetic */ void o(VersionsControlActivity versionsControlActivity, ArrayList arrayList) {
        versionsControlActivity.e = arrayList;
    }

    static /* bridge */ /* synthetic */ int p(VersionsControlActivity versionsControlActivity, int i) {
        return versionsControlActivity.q(i);
    }

    private int q(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void r(Bundle bundle) {
        this.f = findViewById(2131231314);
        this.g = findViewById(2131231028);
        this.h = findViewById(2131230750);
        this.i = findViewById(2131230980);
        this.j = findViewById(2131230731);
        this.k = findViewById(2131231047);
        this.l = findViewById(2131231048);
        this.m = findViewById(2131230730);
        this.n = findViewById(2131231296);
        this.o = findViewById(2131230736);
        this.p = findViewById(2131231247);
        this.q = findViewById(2131231051);
        this.r = new AlertDialog.Builder(this);
        this.j.setOnClickListener(new y5(this));
        this.o.setOnClickListener(new z5(this));
        this.p.setOnRefreshListener(new c6(this));
    }

    private void s() {
        runOnUiThread(new d6(this));
        String l = a.l(a.f(getApplicationContext()).concat("/list"));
        this.a = l;
        if (!l.trim().equals("")) {
            this.d = (ArrayList) new Gson().fromJson(this.a, new e6(this).getType());
        }
        Collections.reverse(this.d);
        ArrayList arrayList = (ArrayList) new Gson().fromJson(((HashMap) this.d.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("list").toString(), new f6(this).getType());
        this.e = arrayList;
        Collections.reverse(arrayList);
        this.q.setAdapter(new q6(this, this.e));
        this.n.setText(((HashMap) this.d.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString());
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(17432576, 17432577);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427464);
        r(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            s();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            s();
        }
    }

    public void onResume() {
        super.onResume();
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.a = l;
            if (!l.trim().equals("")) {
                this.d = (ArrayList) new Gson().fromJson(this.a, new g6(this).getType());
            }
            Collections.reverse(this.d);
            ArrayList arrayList = (ArrayList) new Gson().fromJson(((HashMap) this.d.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("list").toString(), new h6(this).getType());
            this.e = arrayList;
            Collections.reverse(arrayList);
            this.q.invalidateViews();
            this.q.getAdapter().notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }
}
