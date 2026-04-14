package ru.yufic.exteraPlugins;

import android.text.Editable;
import android.text.TextWatcher;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class j implements TextWatcher {
    final /* synthetic */ CreateActivity a;

    j(CreateActivity createActivity) {
        this.a = createActivity;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        if (this.a.getIntent().hasExtra("edit")) {
            return;
        }
        CreateActivity.s(this.a).edit().putString("eDESC", charSequence2).commit();
    }
}
