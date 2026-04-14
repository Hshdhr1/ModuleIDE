package ru.yufic.exteraPlugins;

import android.net.Uri;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class q implements View.OnClickListener {
    final /* synthetic */ DocumActivity a;

    q(DocumActivity documActivity) {
        this.a = documActivity;
    }

    public void onClick(View view) {
        DocumActivity.I(this.a).setAction("android.intent.action.VIEW");
        DocumActivity.I(this.a).setData(Uri.parse("https://plugins.exteragram.app/docs/plugin-class"));
        DocumActivity documActivity = this.a;
        documActivity.startActivity(DocumActivity.I(documActivity));
    }
}
