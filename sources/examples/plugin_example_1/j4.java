package ru.yufic.exteraPlugins;

import android.view.View;
import android.widget.TextView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class j4 implements View.OnClickListener {
    final /* synthetic */ q4 a;
    private final /* synthetic */ TextView b;
    private final /* synthetic */ TextView c;
    private final /* synthetic */ int d;

    j4(q4 q4Var, TextView textView, TextView textView2, int i) {
        this.a = q4Var;
        this.b = textView;
        this.c = textView2;
        this.d = i;
    }

    public void onClick(View view) {
        MainActivity.x(q4.a(this.a)).putExtra("id", this.b.getText().toString());
        MainActivity.x(q4.a(this.a)).putExtra("name", this.c.getText().toString());
        MainActivity.x(q4.a(this.a)).putExtra("position", String.valueOf(this.d));
        MainActivity.x(q4.a(this.a)).setClass(q4.a(this.a).getApplicationContext(), EditActivity.class);
        q4.a(this.a).startActivity(MainActivity.x(q4.a(this.a)));
        q4.a(this.a).overridePendingTransition(17432576, 17432577);
    }
}
