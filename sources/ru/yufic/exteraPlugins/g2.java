package ru.yufic.exteraPlugins;

import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class g2 implements Runnable {
    final /* synthetic */ h2 a;

    g2(h2 h2Var) {
        this.a = h2Var;
    }

    static /* synthetic */ h2 a(g2 g2Var) {
        return g2Var.a;
    }

    public void run() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        OkHttpClient build = builder.connectTimeout(10L, timeUnit).writeTimeout(10L, timeUnit).readTimeout(10L, timeUnit).build();
        Request.Builder post = new Request.Builder().url("https://api.neurix.ru/plugin/pide/chat").addHeader("Accept", "application/json").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(EditActivity.r(h2.a(this.a)))));
        if (EditActivity.p(h2.a(this.a)) != null && !EditActivity.p(h2.a(this.a)).trim().isEmpty()) {
            post.addHeader("Authorization", "Bearer " + EditActivity.p(h2.a(this.a)));
        }
        build.newCall(post.build()).enqueue(new f2(this));
    }
}
