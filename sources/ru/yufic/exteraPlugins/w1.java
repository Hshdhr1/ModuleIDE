package ru.yufic.exteraPlugins;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class w1 implements Callback {
    final /* synthetic */ EditActivity a;

    w1(EditActivity editActivity) {
        this.a = editActivity;
    }

    public static /* synthetic */ void a(w1 w1Var, Response response, String str) {
        w1Var.a.h0("HTTP " + response.code() + ": " + str);
    }

    public static /* synthetic */ void b(w1 w1Var, String str) {
        w1Var.a.i0(str);
    }

    public static /* synthetic */ void c(w1 w1Var, String str) {
        w1Var.a.h0(str);
    }

    public void onFailure(Call call, IOException iOException) {
        this.a.runOnUiThread(new v1(this, iOException.getMessage() != null ? iOException.getMessage() : "Unknown error"));
    }

    public void onResponse(Call call, Response response) {
        String string = response.body() != null ? response.body().string() : "";
        if (response.isSuccessful()) {
            this.a.runOnUiThread(new t1(this, string));
        } else {
            this.a.runOnUiThread(new u1(this, response, string));
        }
    }
}
