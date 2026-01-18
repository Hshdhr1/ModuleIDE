package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class n2 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    n2(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        try {
            EditActivity editActivity = this.a;
            EditActivity.a0(editActivity, a.f(editActivity.getApplicationContext()).concat("/".concat(((HashMap) EditActivity.L(this.a).get((int) Double.parseDouble(this.a.getIntent().getStringExtra("position")))).get("plugin_id").toString().concat("_").concat(((HashMap) EditActivity.T(this.a).get(((int) EditActivity.S(this.a)) + 1)).get("version").toString()).concat(".plugin"))));
            String K = EditActivity.K(this.a);
            EditActivity editActivity2 = this.a;
            a.m(K, editActivity2.g0(EditActivity.x(editActivity2).getText().toString()));
            File file = new File(this.a.getExternalCacheDir(), new File(EditActivity.K(this.a)).getName());
            a.b(EditActivity.K(this.a), file.getAbsolutePath());
            EditActivity editActivity3 = this.a;
            Uri uriForFile = FileProvider.getUriForFile(editActivity3, String.valueOf(editActivity3.getPackageName()) + ".provider", file);
            EditActivity.B(this.a).setAction("android.intent.action.SEND");
            EditActivity.B(this.a).setType("*/*");
            EditActivity.B(this.a).putExtra("android.intent.extra.STREAM", uriForFile);
            EditActivity.B(this.a).addFlags(1);
            EditActivity editActivity4 = this.a;
            editActivity4.startActivity(Intent.createChooser(EditActivity.B(editActivity4), ((HashMap) EditActivity.L(this.a).get((int) Double.parseDouble(this.a.getIntent().getStringExtra("position")))).get("plugin_id").toString().concat("_").concat(((HashMap) EditActivity.T(this.a).get(((int) EditActivity.S(this.a)) + 1)).get("version").toString())));
        } catch (Exception e) {
            EditActivity editActivity5 = this.a;
            editActivity5.getApplicationContext();
            ((ClipboardManager) editActivity5.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }
}
