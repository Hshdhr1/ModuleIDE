package ru.yufic.exteraPlugins;

import androidx.core.text.PrecomputedTextCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class a2 implements Runnable {
    final /* synthetic */ b2 a;
    private final /* synthetic */ PrecomputedTextCompat b;

    a2(b2 b2Var, PrecomputedTextCompat precomputedTextCompat) {
        this.a = b2Var;
        this.b = precomputedTextCompat;
    }

    public void run() {
        EditActivity.x(b2.a(this.a)).setTextContent(this.b);
    }
}
