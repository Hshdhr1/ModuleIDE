package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class p0 implements DialogInterface.OnClickListener {
    final /* synthetic */ r0 a;
    private final /* synthetic */ EditText b;

    p0(r0 r0Var, EditText editText) {
        this.a = r0Var;
        this.b = editText;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.c0(r0.a(this.a), new HashMap());
        EditActivity.R(r0.a(this.a)).put("version", this.b.getText().toString());
        EditActivity.R(r0.a(this.a)).put("code", EditActivity.x(r0.a(this.a)).getText().toString());
        EditActivity.R(r0.a(this.a)).put("plugin_name", ((HashMap) EditActivity.U(r0.a(this.a)).get((int) EditActivity.S(r0.a(this.a)))).get("plugin_name").toString());
        EditActivity.R(r0.a(this.a)).put("plugin_author", ((HashMap) EditActivity.U(r0.a(this.a)).get((int) EditActivity.S(r0.a(this.a)))).get("plugin_author").toString());
        EditActivity.R(r0.a(this.a)).put("plugin_desc", ((HashMap) EditActivity.U(r0.a(this.a)).get((int) EditActivity.S(r0.a(this.a)))).get("plugin_desc").toString());
        Collections.reverse(EditActivity.U(r0.a(this.a)));
        EditActivity.U(r0.a(this.a)).add(EditActivity.R(r0.a(this.a)));
        ((HashMap) EditActivity.L(r0.a(this.a)).get((int) Double.parseDouble(r0.a(this.a).getIntent().getStringExtra("position")))).put("list", new Gson().toJson(EditActivity.U(r0.a(this.a))));
        Collections.reverse(EditActivity.L(r0.a(this.a)));
        a.m(a.f(r0.a(this.a).getApplicationContext()).concat("/list"), new Gson().toJson(EditActivity.L(r0.a(this.a))));
        Collections.reverse(EditActivity.U(r0.a(this.a)));
        Collections.reverse(EditActivity.L(r0.a(this.a)));
        EditActivity.T(r0.a(this.a)).clear();
        EditActivity.W(r0.a(this.a), 0.0d);
        HashMap hashMap = new HashMap();
        hashMap.put("version", r0.a(this.a).getString(2131689518));
        EditActivity.T(r0.a(this.a)).add(hashMap);
        for (int i2 = 0; i2 < EditActivity.U(r0.a(this.a)).size(); i2++) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("version", ((HashMap) EditActivity.U(r0.a(this.a)).get((int) EditActivity.F(r0.a(this.a)))).get("version").toString());
            EditActivity.T(r0.a(this.a)).add(hashMap2);
            EditActivity a = r0.a(this.a);
            EditActivity.W(a, EditActivity.F(a) + 1.0d);
        }
        EditActivity.P(r0.a(this.a)).setAdapter(new w2(r0.a(this.a), EditActivity.T(r0.a(this.a))));
        EditActivity.P(r0.a(this.a)).setSelection(1);
    }
}
