package ru.yufic.exteraPlugins;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class o3 implements Runnable {
    final /* synthetic */ p3 a;

    o3(p3 p3Var) {
        this.a = p3Var;
    }

    public void run() {
        MainActivity.t(q3.a(p3.a(this.a))).setVisibility(0);
        MainActivity.B(q3.a(p3.a(this.a))).setVisibility(8);
        MainActivity.B(q3.a(p3.a(this.a))).setRefreshing(false);
    }
}
