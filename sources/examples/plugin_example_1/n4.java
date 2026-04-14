package ru.yufic.exteraPlugins;

import android.view.View;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n4 implements View.OnClickListener {
    final /* synthetic */ q4 a;
    private final /* synthetic */ int b;

    n4(q4 q4Var, int i) {
        this.a = q4Var;
        this.b = i;
    }

    public void onClick(View view) {
        MainActivity.L(q4.a(this.a), (HashMap) this.a.a.get(this.b));
        this.a.a.remove(this.b);
        q4 q4Var = this.a;
        q4Var.a.add(this.b + 1, MainActivity.D(q4.a(q4Var)));
        Collections.reverse(this.a.a);
        a.m(a.f(q4.a(this.a).getApplicationContext()).concat("/list"), new Gson().toJson(this.a.a));
        Collections.reverse(this.a.a);
        this.a.notifyDataSetChanged();
    }
}
