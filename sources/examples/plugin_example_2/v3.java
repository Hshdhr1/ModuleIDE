package ru.yufic.exteraPlugins;

import android.widget.AbsListView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class v3 implements AbsListView.OnScrollListener {
    private int a;
    final /* synthetic */ e4 b;

    v3(e4 e4Var) {
        this.b = e4Var;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (this.a < i) {
            MainActivity.p(e4.a(this.b)).hide();
        }
        if (this.a > i) {
            MainActivity.p(e4.a(this.b)).show();
        }
        this.a = i;
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }
}
