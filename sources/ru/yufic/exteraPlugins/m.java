package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class m implements View.OnClickListener {
    final /* synthetic */ DebugActivity a;

    m(DebugActivity debugActivity) {
        this.a = debugActivity;
    }

    public void onClick(View view) {
        DebugActivity debugActivity = this.a;
        debugActivity.getApplicationContext();
        ((ClipboardManager) debugActivity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", DebugActivity.a(this.a)));
    }
}
