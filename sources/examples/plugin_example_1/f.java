package ru.yufic.exteraPlugins;

import android.os.Build;
import android.os.Environment;
import android.view.View;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class f implements View.OnClickListener {
    final /* synthetic */ CreateActivity a;

    f(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void onClick(View view) {
        if (Build.VERSION.SDK_INT <= 29) {
            a.m("", "");
        } else if (!Environment.isExternalStorageManager()) {
            CreateActivity.o(this.a).setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
            CreateActivity createActivity = this.a;
            createActivity.startActivity(CreateActivity.o(createActivity));
        }
        d.b(this.a.getApplicationContext(), "Выберите файл формата .plugin или .py");
        CreateActivity createActivity2 = this.a;
        createActivity2.startActivityForResult(CreateActivity.j(createActivity2), 101);
    }
}
