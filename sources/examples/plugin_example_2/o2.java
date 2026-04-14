package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class o2 implements DialogInterface.OnClickListener {
    final /* synthetic */ t2 a;
    private final /* synthetic */ EditText b;

    o2(t2 t2Var, EditText editText) {
        this.a = t2Var;
        this.b = editText;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        EditActivity.b0(t2.a(this.a), true);
        EditActivity.X(t2.a(this.a), 0.0d);
        for (int i2 = 0; i2 < EditActivity.T(t2.a(this.a)).size(); i2++) {
            if (((HashMap) EditActivity.T(t2.a(this.a)).get((int) EditActivity.H(t2.a(this.a)))).get("version").toString().equals(this.b.getText().toString())) {
                EditActivity.b0(t2.a(this.a), false);
                d.b(t2.a(this.a).getApplicationContext(), "Такая версия уже существует!");
                EditActivity.P(t2.a(this.a)).setSelection((int) (EditActivity.S(t2.a(this.a)) + 1.0d));
            }
            EditActivity a = t2.a(this.a);
            EditActivity.X(a, EditActivity.H(a) + 1.0d);
        }
        if (EditActivity.Q(t2.a(this.a))) {
            EditActivity.c0(t2.a(this.a), new HashMap());
            EditActivity.R(t2.a(this.a)).put("version", this.b.getText().toString());
            EditActivity.R(t2.a(this.a)).put("code", EditActivity.x(t2.a(this.a)).getText().toString());
            EditActivity.R(t2.a(this.a)).put("plugin_name", ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("plugin_name").toString());
            EditActivity.R(t2.a(this.a)).put("plugin_author", ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("plugin_author").toString());
            EditActivity.R(t2.a(this.a)).put("plugin_desc", ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("plugin_desc").toString());
            Collections.reverse(EditActivity.U(t2.a(this.a)));
            EditActivity.U(t2.a(this.a)).add(EditActivity.R(t2.a(this.a)));
            ((HashMap) EditActivity.L(t2.a(this.a)).get((int) Double.parseDouble(t2.a(this.a).getIntent().getStringExtra("position")))).put("list", new Gson().toJson(EditActivity.U(t2.a(this.a))));
            Collections.reverse(EditActivity.L(t2.a(this.a)));
            a.m(a.f(t2.a(this.a).getApplicationContext()).concat("/list"), new Gson().toJson(EditActivity.L(t2.a(this.a))));
            Collections.reverse(EditActivity.U(t2.a(this.a)));
            Collections.reverse(EditActivity.L(t2.a(this.a)));
            EditActivity.T(t2.a(this.a)).clear();
            EditActivity.W(t2.a(this.a), 0.0d);
            HashMap hashMap = new HashMap();
            hashMap.put("version", t2.a(this.a).getString(2131689518));
            EditActivity.T(t2.a(this.a)).add(hashMap);
            for (int i3 = 0; i3 < EditActivity.U(t2.a(this.a)).size(); i3++) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("version", ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.F(t2.a(this.a)))).get("version").toString());
                EditActivity.T(t2.a(this.a)).add(hashMap2);
                EditActivity a2 = t2.a(this.a);
                EditActivity.W(a2, EditActivity.F(a2) + 1.0d);
            }
            EditActivity.P(t2.a(this.a)).setAdapter(new w2(t2.a(this.a), EditActivity.T(t2.a(this.a))));
            EditActivity.P(t2.a(this.a)).setSelection(1);
            t2.a(this.a).l0();
            EditActivity.o(t2.a(this.a)).setText(((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("plugin_name").toString().concat(" • ".concat(((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("plugin_author").toString())));
        }
    }
}
