package ru.yufic.exteraPlugins;

import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class q3 implements View.OnClickListener {
    final /* synthetic */ MainActivity a;

    q3(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    static /* synthetic */ MainActivity a(q3 q3Var) {
        return q3Var.a;
    }

    public void onClick(View view) {
        MainActivity.t(this.a).setVisibility(8);
        MainActivity.B(this.a).setVisibility(0);
        MainActivity.B(this.a).setRefreshing(true);
        try {
            MainActivity.H(this.a, (ArrayList) new Gson().fromJson(a.l(a.f(this.a.getApplicationContext()).concat("/list")), new l3(this).getType()));
            Collections.reverse(MainActivity.y(this.a));
            if (MainActivity.y(this.a).size() == 0) {
                MainActivity.N(this.a, new n3(this));
                MainActivity.q(this.a).schedule(MainActivity.F(this.a), 1000L);
                return;
            }
            ListView u = MainActivity.u(this.a);
            MainActivity mainActivity = this.a;
            u.setAdapter(new q4(mainActivity, MainActivity.y(mainActivity)));
            MainActivity.u(this.a).getAdapter().notifyDataSetChanged();
            MainActivity.t(this.a).setVisibility(8);
            MainActivity.B(this.a).setVisibility(0);
            MainActivity.B(this.a).setRefreshing(false);
        } catch (Exception unused) {
            MainActivity.N(this.a, new p3(this));
            MainActivity.q(this.a).schedule(MainActivity.F(this.a), 1000L);
        }
    }
}
