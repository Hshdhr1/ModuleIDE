package ru.yufic.exteraPlugins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class DebugActivity extends AppCompatActivity {
    private LinearLayout b;
    private LinearLayout c;
    private ImageView d;
    private TextView e;
    private LinearLayout f;
    private LinearLayout g;
    private LinearLayout h;
    private MaterialButton i;
    private MaterialButton j;
    private String a = "";
    private Intent k = new Intent();

    static /* bridge */ /* synthetic */ String a(DebugActivity debugActivity) {
        return debugActivity.a;
    }

    static /* bridge */ /* synthetic */ Intent b(DebugActivity debugActivity) {
        return debugActivity.k;
    }

    private void c(Bundle bundle) {
        this.b = findViewById(2131231028);
        this.c = findViewById(2131231037);
        this.d = findViewById(2131231000);
        this.e = findViewById(2131231296);
        this.f = findViewById(2131231036);
        this.g = findViewById(2131230980);
        this.h = findViewById(2131231035);
        this.i = findViewById(2131230850);
        this.j = findViewById(2131231075);
        this.i.setOnClickListener(new m(this));
        this.j.setOnClickListener(new n(this));
    }

    private void d() {
        String[] strArr = {"StringIndexOutOfBoundsException", "IndexOutOfBoundsException", "ArithmeticException", "NumberFormatException", "ActivityNotFoundException"};
        String[] strArr2 = {"Invalid string operation\n", "Invalid list operation\n", "Invalid arithmetical operation\n", "Invalid toNumber block operation\n", "Invalid intent operation"};
        Intent intent = getIntent();
        String str = "";
        if (intent != null) {
            String stringExtra = intent.getStringExtra("error");
            String[] split = stringExtra.split("\n");
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                }
                try {
                    if (split[0].contains(strArr[i])) {
                        String str2 = strArr2[i];
                        int indexOf = split[0].indexOf(strArr[i]) + strArr[i].length();
                        StringBuilder sb = new StringBuilder(String.valueOf(str2));
                        String str3 = split[0];
                        sb.append(str3.substring(indexOf, str3.length()));
                        str = sb.toString();
                        break;
                    }
                    i++;
                } catch (Exception unused) {
                }
            }
            if (str.isEmpty()) {
                str = stringExtra;
            }
        }
        this.a = str;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427362);
        c(bundle);
        d();
    }
}
