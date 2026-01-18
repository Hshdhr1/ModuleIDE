package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class l implements View.OnClickListener {
    final /* synthetic */ CreateActivity a;

    l(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void onClick(View view) {
        String str = "";
        boolean z = true;
        if (CreateActivity.m(this.a)) {
            CreateActivity.z(this.a, true);
            CreateActivity.y(this.a, false);
            if (CreateActivity.h(this.a).getText().toString().trim().equals("")) {
                CreateActivity.h(this.a).setError(this.a.getString(2131689627));
            } else {
                CreateActivity.B(this.a, 0.0d);
                int i = 0;
                while (i < CreateActivity.p(this.a).size()) {
                    String str2 = str;
                    if (((HashMap) CreateActivity.p(this.a).get((int) CreateActivity.q(this.a))).get("plugin_id").toString().equals(CreateActivity.h(this.a).getText().toString())) {
                        CreateActivity.z(this.a, false);
                    }
                    CreateActivity createActivity = this.a;
                    CreateActivity.B(createActivity, CreateActivity.q(createActivity) + 1.0d);
                    i++;
                    str = str2;
                    z = true;
                }
                if (!CreateActivity.l(this.a)) {
                    CreateActivity.h(this.a).setError(this.a.getString(2131689540));
                } else if (CreateActivity.i(this.a).getText().toString().trim().equals(str)) {
                    CreateActivity.i(this.a).setError(this.a.getString(2131689627));
                } else if (CreateActivity.f(this.a).getText().toString().trim().equals(str)) {
                    CreateActivity.f(this.a).setError(this.a.getString(2131689627));
                } else {
                    CreateActivity.y(this.a, z);
                }
            }
            if (CreateActivity.e(this.a)) {
                try {
                    CreateActivity.D(this.a, new HashMap());
                    CreateActivity.t(this.a).put("version", CreateActivity.u(this.a));
                    CreateActivity.t(this.a).put("code", CreateActivity.d(this.a));
                    CreateActivity.t(this.a).put("plugin_name", CreateActivity.i(this.a).getText().toString());
                    CreateActivity.t(this.a).put("plugin_author", CreateActivity.f(this.a).getText().toString());
                    CreateActivity.t(this.a).put("plugin_desc", CreateActivity.g(this.a).getText().toString());
                    CreateActivity.w(this.a).add(CreateActivity.t(this.a));
                    CreateActivity.C(this.a, new HashMap());
                    CreateActivity.r(this.a).put("plugin_id", CreateActivity.h(this.a).getText().toString());
                    CreateActivity.r(this.a).put("list", new Gson().toJson(CreateActivity.w(this.a)));
                    CreateActivity.p(this.a).add(CreateActivity.r(this.a));
                    a.m(a.f(this.a.getApplicationContext()).concat("/list"), new Gson().toJson(CreateActivity.p(this.a)));
                    d.b(this.a.getApplicationContext(), this.a.getString(2131689625));
                    CreateActivity.k(this.a).setClass(this.a.getApplicationContext(), MainActivity.class);
                    CreateActivity createActivity2 = this.a;
                    createActivity2.startActivity(CreateActivity.k(createActivity2));
                    this.a.finish();
                    this.a.overridePendingTransition(17432576, 17432577);
                    return;
                } catch (Exception e) {
                    CreateActivity createActivity3 = this.a;
                    createActivity3.getApplicationContext();
                    ((ClipboardManager) createActivity3.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
                    return;
                }
            }
            return;
        }
        if (this.a.getIntent().hasExtra("edit")) {
            ((HashMap) CreateActivity.w(this.a).get((int) CreateActivity.v(this.a))).put("plugin_name", CreateActivity.i(this.a).getText().toString());
            ((HashMap) CreateActivity.w(this.a).get((int) CreateActivity.v(this.a))).put("plugin_author", CreateActivity.f(this.a).getText().toString());
            ((HashMap) CreateActivity.w(this.a).get((int) CreateActivity.v(this.a))).put("plugin_desc", CreateActivity.g(this.a).getText().toString());
            Collections.reverse(CreateActivity.w(this.a));
            ((HashMap) CreateActivity.p(this.a).get((int) Double.parseDouble(this.a.getIntent().getStringExtra("position")))).put("list", new Gson().toJson(CreateActivity.w(this.a)));
            Collections.reverse(CreateActivity.p(this.a));
            a.m(a.f(this.a.getApplicationContext()).concat("/list"), new Gson().toJson(CreateActivity.p(this.a)));
            Collections.reverse(CreateActivity.w(this.a));
            Collections.reverse(CreateActivity.p(this.a));
            this.a.finish();
            this.a.overridePendingTransition(17432576, 17432577);
            return;
        }
        CreateActivity.z(this.a, true);
        CreateActivity.y(this.a, false);
        Object obj = "";
        if (CreateActivity.h(this.a).getText().toString().trim().equals(obj)) {
            CreateActivity.h(this.a).setError(this.a.getString(2131689627));
        } else {
            CreateActivity.B(this.a, 0.0d);
            int i2 = 0;
            while (i2 < CreateActivity.p(this.a).size()) {
                Object obj2 = obj;
                if (((HashMap) CreateActivity.p(this.a).get((int) CreateActivity.q(this.a))).get("plugin_id").toString().equals(CreateActivity.h(this.a).getText().toString())) {
                    CreateActivity.z(this.a, false);
                }
                CreateActivity createActivity4 = this.a;
                CreateActivity.B(createActivity4, CreateActivity.q(createActivity4) + 1.0d);
                i2++;
                obj = obj2;
            }
            if (!CreateActivity.l(this.a)) {
                CreateActivity.h(this.a).setError(this.a.getString(2131689540));
            } else if (CreateActivity.i(this.a).getText().toString().trim().equals(obj)) {
                CreateActivity.i(this.a).setError(this.a.getString(2131689627));
            } else if (CreateActivity.f(this.a).getText().toString().trim().equals(obj)) {
                CreateActivity.f(this.a).setError(this.a.getString(2131689627));
            } else {
                CreateActivity.y(this.a, true);
            }
        }
        if (CreateActivity.e(this.a)) {
            try {
                CreateActivity.D(this.a, new HashMap());
                CreateActivity.t(this.a).put("version", "1.0.0");
                CreateActivity.t(this.a).put("code", "__icon__ = \"sPluginIDE/0\" # иконка в списке плагинов\n__min_version__ = \"11.12.0\" # минимальная версия exteraGram\n\nclass ExamplePlugin(BasePlugin):\n    # просто выводим в лог сообщение о загрузке\n    def on_plugin_load(self):\n        self.log(\"Плагин успешно загружен!\")");
                CreateActivity.t(this.a).put("plugin_name", CreateActivity.i(this.a).getText().toString());
                CreateActivity.t(this.a).put("plugin_author", CreateActivity.f(this.a).getText().toString());
                CreateActivity.t(this.a).put("plugin_desc", CreateActivity.g(this.a).getText().toString());
                CreateActivity.w(this.a).add(CreateActivity.t(this.a));
                CreateActivity.C(this.a, new HashMap());
                CreateActivity.r(this.a).put("plugin_id", CreateActivity.h(this.a).getText().toString());
                CreateActivity.r(this.a).put("list", new Gson().toJson(CreateActivity.w(this.a)));
                CreateActivity.p(this.a).add(CreateActivity.r(this.a));
                a.m(a.f(this.a.getApplicationContext()).concat("/list"), new Gson().toJson(CreateActivity.p(this.a)));
                d.b(this.a.getApplicationContext(), this.a.getString(2131689519));
                CreateActivity.k(this.a).setClass(this.a.getApplicationContext(), MainActivity.class);
                CreateActivity createActivity5 = this.a;
                createActivity5.startActivity(CreateActivity.k(createActivity5));
                this.a.finish();
                this.a.overridePendingTransition(17432576, 17432577);
            } catch (Exception e2) {
                CreateActivity createActivity6 = this.a;
                createActivity6.getApplicationContext();
                ((ClipboardManager) createActivity6.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e2.getMessage()));
            }
        }
    }
}
