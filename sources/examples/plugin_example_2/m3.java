package ru.yufic.exteraPlugins;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class m3 implements Runnable {
    final /* synthetic */ n3 a;

    m3(n3 n3Var) {
        this.a = n3Var;
    }

    public void run() {
        MainActivity.t(q3.a(n3.a(this.a))).setVisibility(0);
        MainActivity.B(q3.a(n3.a(this.a))).setVisibility(8);
        MainActivity.B(q3.a(n3.a(this.a))).setRefreshing(false);
    }
}
