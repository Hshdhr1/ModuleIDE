package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class z2 implements View.OnClickListener {
    final /* synthetic */ a3 a;
    private final /* synthetic */ int b;

    z2(a3 a3Var, int i) {
        this.a = a3Var;
        this.b = i;
    }

    public void onClick(View view) {
        IconsActivity a = a3.a(this.a);
        a3.a(this.a).getApplicationContext();
        ((ClipboardManager) a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", ((HashMap) this.a.a.get(this.b)).get("name").toString()));
    }
}
