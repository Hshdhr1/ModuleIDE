package ru.yufic.exteraPlugins;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class f2 implements Callback {
    final /* synthetic */ g2 a;

    f2(g2 g2Var) {
        this.a = g2Var;
    }

    public static /* synthetic */ void a(f2 f2Var, Response response, String str) {
        h2.a(g2.a(f2Var.a)).h0("HTTP " + response.code() + ": " + str);
    }

    public static /* synthetic */ void b(f2 f2Var, String str) {
        h2.a(g2.a(f2Var.a)).h0(str);
    }

    public static /* synthetic */ void c(f2 f2Var, String str) {
        h2.a(g2.a(f2Var.a)).i0(str);
    }

    public void onFailure(Call call, IOException iOException) {
        h2.a(g2.a(this.a)).runOnUiThread(new e2(this, iOException.getMessage() != null ? iOException.getMessage() : "Unknown error"));
    }

    public void onResponse(Call call, Response response) {
        String string = response.body() != null ? response.body().string() : "";
        if (response.isSuccessful()) {
            h2.a(g2.a(this.a)).runOnUiThread(new c2(this, string));
        } else {
            h2.a(g2.a(this.a)).runOnUiThread(new d2(this, response, string));
        }
    }
}
