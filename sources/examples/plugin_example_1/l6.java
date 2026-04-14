package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class l6 implements DialogInterface.OnClickListener {
    final /* synthetic */ n6 a;
    private final /* synthetic */ int b;

    l6(n6 n6Var, int i) {
        this.a = n6Var;
        this.b = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        n6.a(this.a).a.remove(this.b);
        Collections.reverse(n6.a(this.a).a);
        ((HashMap) VersionsControlActivity.e(q6.a(n6.a(this.a))).get((int) Double.parseDouble(q6.a(n6.a(this.a)).getIntent().getStringExtra("position")))).put("list", new Gson().toJson(n6.a(this.a).a));
        Collections.reverse(VersionsControlActivity.e(q6.a(n6.a(this.a))));
        a.m(a.f(q6.a(n6.a(this.a)).getApplicationContext()).concat("/list"), new Gson().toJson(VersionsControlActivity.e(q6.a(n6.a(this.a)))));
        Collections.reverse(VersionsControlActivity.e(q6.a(n6.a(this.a))));
        Collections.reverse(n6.a(this.a).a);
        n6.a(this.a).notifyDataSetChanged();
    }
}
