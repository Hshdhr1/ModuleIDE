package ru.yufic.exteraPlugins;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class c6 implements SwipeRefreshLayout.OnRefreshListener {
    final /* synthetic */ VersionsControlActivity a;

    c6(VersionsControlActivity versionsControlActivity) {
        this.a = versionsControlActivity;
    }

    public void onRefresh() {
        try {
            VersionsControlActivity versionsControlActivity = this.a;
            VersionsControlActivity.l(versionsControlActivity, a.l(a.f(versionsControlActivity.getApplicationContext()).concat("/list")));
            if (!VersionsControlActivity.f(this.a).trim().equals("")) {
                VersionsControlActivity.k(this.a, (ArrayList) new Gson().fromJson(VersionsControlActivity.f(this.a), new a6(this).getType()));
            }
            Collections.reverse(VersionsControlActivity.e(this.a));
            VersionsControlActivity.o(this.a, (ArrayList) new Gson().fromJson(((HashMap) VersionsControlActivity.e(this.a).get((int) Double.parseDouble(this.a.getIntent().getStringExtra("position")))).get("list").toString(), new b6(this).getType()));
            Collections.reverse(VersionsControlActivity.j(this.a));
            VersionsControlActivity.d(this.a).invalidateViews();
            VersionsControlActivity.d(this.a).getAdapter().notifyDataSetChanged();
        } catch (Exception unused) {
        }
        VersionsControlActivity.h(this.a).setRefreshing(false);
    }
}
