package ru.yufic.exteraPlugins;

import okhttp3.Response;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public final /* synthetic */ class d2 implements Runnable {
    public final /* synthetic */ f2 a;
    public final /* synthetic */ Response b;
    public final /* synthetic */ String c;

    public /* synthetic */ d2(f2 f2Var, Response response, String str) {
        this.a = f2Var;
        this.b = response;
        this.c = str;
    }

    public final void run() {
        f2.a(this.a, this.b, this.c);
    }
}
