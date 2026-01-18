package ru.yufic.exteraPlugins;

import android.widget.ListView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class k3 implements SwipeRefreshLayout.OnRefreshListener {
    final /* synthetic */ MainActivity a;

    k3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onRefresh() {
        try {
            MainActivity.H(this.a, (ArrayList) new Gson().fromJson(a.l(a.f(this.a.getApplicationContext()).concat("/list")), new j3(this).getType()));
            Collections.reverse(MainActivity.y(this.a));
            if (MainActivity.y(this.a).size() != 0) {
                ListView u = MainActivity.u(this.a);
                MainActivity mainActivity = this.a;
                u.setAdapter(new q4(mainActivity, MainActivity.y(mainActivity)));
                MainActivity.u(this.a).getAdapter().notifyDataSetChanged();
                MainActivity.t(this.a).setVisibility(8);
                MainActivity.B(this.a).setVisibility(0);
            } else {
                MainActivity.t(this.a).setVisibility(0);
                MainActivity.B(this.a).setVisibility(8);
            }
        } catch (Exception unused) {
            MainActivity.t(this.a).setVisibility(0);
            MainActivity.B(this.a).setVisibility(8);
        }
        MainActivity.B(this.a).setRefreshing(false);
    }
}
