package ru.yufic.exteraPlugins;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class o0 extends WebChromeClient {
    final /* synthetic */ DocumActivity a;

    o0(DocumActivity documActivity) {
        this.a = documActivity;
    }

    public void onProgressChanged(WebView webView, int i) {
        DocumActivity.G(this.a).setProgress(i);
    }
}
