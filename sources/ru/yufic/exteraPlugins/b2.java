package ru.yufic.exteraPlugins;

import androidx.core.text.PrecomputedTextCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class b2 implements Runnable {
    final /* synthetic */ EditActivity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ PrecomputedTextCompat.Params c;

    b2(EditActivity editActivity, String str, PrecomputedTextCompat.Params params) {
        this.a = editActivity;
        this.b = str;
        this.c = params;
    }

    static /* synthetic */ EditActivity a(b2 b2Var) {
        return b2Var.a;
    }

    public void run() {
        EditActivity.D(this.a).post(new a2(this, PrecomputedTextCompat.create(this.b, this.c)));
    }
}
