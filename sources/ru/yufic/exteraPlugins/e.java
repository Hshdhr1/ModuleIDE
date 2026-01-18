package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class e implements View.OnClickListener {
    final /* synthetic */ CreateActivity a;

    e(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void onClick(View view) {
        CreateActivity.h(this.a).setText("");
        CreateActivity.i(this.a).setText("");
        CreateActivity.f(this.a).setText("");
        CreateActivity.g(this.a).setText("");
        CreateActivity.c(this.a).setText(this.a.getString(2131689629));
        CreateActivity.A(this.a, false);
        CreateActivity.x(this.a, "");
    }
}
