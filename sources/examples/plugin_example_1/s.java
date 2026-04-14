package ru.yufic.exteraPlugins;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class s extends WebViewClient {
    final /* synthetic */ DocumActivity a;

    s(DocumActivity documActivity) {
        this.a = documActivity;
    }

    public void onPageFinished(WebView webView, String str) {
        DocumActivity.G(this.a).setVisibility(8);
        DocumActivity.J(this.a).setVisibility(0);
        super.onPageFinished(webView, str);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        DocumActivity.G(this.a).setVisibility(0);
        super.onPageStarted(webView, str, bitmap);
    }
}
