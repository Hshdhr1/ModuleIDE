package ru.yufic.exteraPlugins;

import android.content.DialogInterface;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class e3 implements DialogInterface.OnClickListener {
    final /* synthetic */ MainActivity a;
    private final /* synthetic */ ArrayList b;
    private final /* synthetic */ EditText c;
    private final /* synthetic */ double d;

    e3(MainActivity mainActivity, ArrayList arrayList, EditText editText, double d) {
        this.a = mainActivity;
        this.b = arrayList;
        this.c = editText;
        this.d = d;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        MainActivity.J(this.a, true);
        MainActivity.G(this.a, 0.0d);
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            if (((HashMap) this.b.get((int) MainActivity.v(this.a))).get("plugin_id").toString().equals(this.c.getText().toString())) {
                MainActivity.J(this.a, false);
                d.b(this.a.getApplicationContext(), this.a.getString(2131689540));
            }
            MainActivity mainActivity = this.a;
            MainActivity.G(mainActivity, MainActivity.v(mainActivity) + 1.0d);
        }
        if (MainActivity.A(this.a)) {
            ((HashMap) this.b.get((int) this.d)).put("plugin_id", this.c.getText().toString());
            Collections.reverse(this.b);
            a.m(a.f(this.a.getApplicationContext()).concat("/list"), new Gson().toJson(this.b));
            Collections.reverse(this.b);
            MainActivity.u(this.a).getAdapter().notifyDataSetChanged();
        }
    }
}
