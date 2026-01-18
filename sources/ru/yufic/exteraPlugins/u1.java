package ru.yufic.exteraPlugins;

import okhttp3.Response;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public final /* synthetic */ class u1 implements Runnable {
    public final /* synthetic */ w1 a;
    public final /* synthetic */ Response b;
    public final /* synthetic */ String c;

    public /* synthetic */ u1(w1 w1Var, Response response, String str) {
        this.a = w1Var;
        this.b = response;
        this.c = str;
    }

    public final void run() {
        w1.a(this.a, this.b, this.c);
    }
}
