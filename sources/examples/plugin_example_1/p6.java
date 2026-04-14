package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class p6 implements View.OnClickListener {
    final /* synthetic */ q6 a;
    private final /* synthetic */ int b;

    p6(q6 q6Var, int i) {
        this.a = q6Var;
        this.b = i;
    }

    public void onClick(View view) {
        VersionsControlActivity.n(q6.a(this.a), (HashMap) this.a.a.get(this.b));
        this.a.a.remove(this.b);
        q6 q6Var = this.a;
        q6Var.a.add(this.b - 1, VersionsControlActivity.i(q6.a(q6Var)));
        Collections.reverse(this.a.a);
        ((HashMap) VersionsControlActivity.e(q6.a(this.a)).get((int) Double.parseDouble(q6.a(this.a).getIntent().getStringExtra("position")))).put("list", new Gson().toJson(this.a.a));
        Collections.reverse(VersionsControlActivity.e(q6.a(this.a)));
        a.m(a.f(q6.a(this.a).getApplicationContext()).concat("/list"), new Gson().toJson(VersionsControlActivity.e(q6.a(this.a))));
        Collections.reverse(VersionsControlActivity.e(q6.a(this.a)));
        Collections.reverse(this.a.a);
        this.a.notifyDataSetChanged();
    }
}
