package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class q2 implements DialogInterface.OnClickListener {
    final /* synthetic */ t2 a;
    private final /* synthetic */ int b;

    q2(t2 t2Var, int i) {
        this.a = t2Var;
        this.b = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).put("code", EditActivity.x(t2.a(this.a)).getText().toString());
            Collections.reverse(EditActivity.U(t2.a(this.a)));
            ((HashMap) EditActivity.L(t2.a(this.a)).get((int) Double.parseDouble(t2.a(this.a).getIntent().getStringExtra("position")))).put("list", new Gson().toJson(EditActivity.U(t2.a(this.a))));
            Collections.reverse(EditActivity.U(t2.a(this.a)));
            Collections.reverse(EditActivity.L(t2.a(this.a)));
            a.m(a.f(t2.a(this.a).getApplicationContext()).concat("/list"), new Gson().toJson(EditActivity.L(t2.a(this.a))));
            Collections.reverse(EditActivity.L(t2.a(this.a)));
            EditActivity.Z(t2.a(this.a), EditActivity.x(t2.a(this.a)).getText().toString());
            d.b(t2.a(this.a).getApplicationContext(), "Версия сохранена!");
            EditActivity.d0(t2.a(this.a), this.b - 1);
            try {
                EditActivity.Z(t2.a(this.a), ((HashMap) EditActivity.U(t2.a(this.a)).get((int) EditActivity.S(t2.a(this.a)))).get("code").toString());
                t2.a(this.a).k0(EditActivity.J(t2.a(this.a)));
                t2.a(this.a).l0();
            } catch (Exception e) {
                EditActivity a = t2.a(this.a);
                t2.a(this.a).getApplicationContext();
                ((ClipboardManager) a.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
            }
        } catch (Exception e2) {
            d.b(t2.a(this.a).getApplicationContext(), "Ошибка редактирования. Файл не сохранён!");
            EditActivity a2 = t2.a(this.a);
            t2.a(this.a).getApplicationContext();
            ((ClipboardManager) a2.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e2.getMessage()));
        }
    }
}
