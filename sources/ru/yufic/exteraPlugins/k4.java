package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import com.google.gson.Gson;
import java.util.Collections;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k4 implements DialogInterface.OnClickListener {
    final /* synthetic */ m4 a;
    private final /* synthetic */ int b;

    k4(m4 m4Var, int i) {
        this.a = m4Var;
        this.b = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        m4.a(this.a).a.remove(this.b);
        Collections.reverse(m4.a(this.a).a);
        a.m(a.f(q4.a(m4.a(this.a)).getApplicationContext()).concat("/list"), new Gson().toJson(m4.a(this.a).a));
        Collections.reverse(m4.a(this.a).a);
        m4.a(this.a).notifyDataSetChanged();
    }
}
