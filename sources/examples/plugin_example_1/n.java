package ru.yufic.exteraPlugins;

import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n implements View.OnClickListener {
    final /* synthetic */ DebugActivity a;

    n(DebugActivity debugActivity) {
        this.a = debugActivity;
    }

    public void onClick(View view) {
        DebugActivity.b(this.a).setClass(this.a.getApplicationContext(), MainActivity.class);
        DebugActivity debugActivity = this.a;
        debugActivity.startActivity(DebugActivity.b(debugActivity));
        this.a.finish();
        this.a.overridePendingTransition(17432576, 17432577);
    }
}
