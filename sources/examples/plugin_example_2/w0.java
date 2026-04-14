package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class w0 implements View.OnClickListener {
    final /* synthetic */ EditActivity a;

    w0(EditActivity editActivity) {
        this.a = editActivity;
    }

    public void onClick(View view) {
        try {
            ((HashMap) EditActivity.U(this.a).get((int) EditActivity.S(this.a))).put("code", EditActivity.x(this.a).getText().toString());
            Collections.reverse(EditActivity.U(this.a));
            ((HashMap) EditActivity.L(this.a).get((int) Double.parseDouble(this.a.getIntent().getStringExtra("position")))).put("list", new Gson().toJson(EditActivity.U(this.a)));
            Collections.reverse(EditActivity.U(this.a));
            Collections.reverse(EditActivity.L(this.a));
            a.m(a.f(this.a.getApplicationContext()).concat("/list"), new Gson().toJson(EditActivity.L(this.a)));
            Collections.reverse(EditActivity.L(this.a));
            EditActivity editActivity = this.a;
            EditActivity.Z(editActivity, EditActivity.x(editActivity).getText().toString());
            d.b(this.a.getApplicationContext(), "Версия сохранена!");
        } catch (Exception e) {
            d.b(this.a.getApplicationContext(), "Ошибка редактирования. Файл не сохранён!");
            EditActivity editActivity2 = this.a;
            editActivity2.getApplicationContext();
            ((ClipboardManager) editActivity2.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
    }
}
