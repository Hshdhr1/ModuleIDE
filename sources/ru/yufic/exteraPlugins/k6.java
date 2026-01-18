package ru.yufic.exteraPlugins;

import android.view.View;
import android.widget.TextView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k6 implements View.OnClickListener {
    final /* synthetic */ q6 a;
    private final /* synthetic */ int b;
    private final /* synthetic */ TextView c;

    k6(q6 q6Var, int i, TextView textView) {
        this.a = q6Var;
        this.b = i;
        this.c = textView;
    }

    public void onClick(View view) {
        VersionsControlActivity.c(q6.a(this.a)).putExtra("edit", "");
        VersionsControlActivity.c(q6.a(this.a)).putExtra("position", q6.a(this.a).getIntent().getStringExtra("position"));
        VersionsControlActivity.c(q6.a(this.a)).putExtra("version", String.valueOf(this.b));
        VersionsControlActivity.c(q6.a(this.a)).putExtra("ver", this.c.getText().toString());
        VersionsControlActivity.c(q6.a(this.a)).setClass(q6.a(this.a).getApplicationContext(), CreateActivity.class);
        q6.a(this.a).startActivity(VersionsControlActivity.c(q6.a(this.a)));
        q6.a(this.a).overridePendingTransition(17432576, 17432577);
    }
}
