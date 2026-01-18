package ru.yufic.exteraPlugins;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class CreateActivity extends AppCompatActivity {
    private LinearLayout A;
    private LinearLayout B;
    private ImageView C;
    private TextView D;
    private TextView E;
    private ImageView F;
    private ImageView G;
    private ScrollView H;
    private LinearLayout I;
    private LinearLayout J;
    private LinearLayout K;
    private TextInputLayout L;
    private TextInputLayout M;
    private TextInputLayout N;
    private TextInputLayout O;
    private EditText P;
    private EditText Q;
    private EditText R;
    private EditText S;
    private MaterialButton T;
    private MaterialButton U;
    private SharedPreferences V;
    private LinearLayout v;
    private LinearLayout w;
    private LinearLayout x;
    private LinearLayout y;
    private LinearLayout z;
    public final int a = 101;
    private HashMap b = new HashMap();
    private boolean c = false;
    private String d = "";
    private boolean e = false;
    private double f = 0.0d;
    private HashMap g = new HashMap();
    private HashMap h = new HashMap();
    private HashMap i = new HashMap();
    private double j = 0.0d;
    private String k = "";
    private String l = "";
    private String m = "";
    private String n = "";
    private String o = "";
    private String p = "";
    private String q = "";
    private boolean r = false;
    private boolean s = false;
    private ArrayList t = new ArrayList();
    private ArrayList u = new ArrayList();
    private Intent W = new Intent();
    private Intent X = new Intent("android.intent.action.GET_CONTENT");
    private Intent Y = new Intent();

    static /* bridge */ /* synthetic */ void A(CreateActivity createActivity, boolean z) {
        createActivity.r = z;
    }

    static /* bridge */ /* synthetic */ void B(CreateActivity createActivity, double d) {
        createActivity.f = d;
    }

    static /* bridge */ /* synthetic */ void C(CreateActivity createActivity, HashMap hashMap) {
        createActivity.b = hashMap;
    }

    static /* bridge */ /* synthetic */ void D(CreateActivity createActivity, HashMap hashMap) {
        createActivity.g = hashMap;
    }

    static /* bridge */ /* synthetic */ int E(CreateActivity createActivity, int i) {
        return createActivity.G(i);
    }

    private int G(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    private void H(Bundle bundle) {
        this.v = findViewById(2131231314);
        this.w = findViewById(2131231028);
        this.x = findViewById(2131230750);
        this.y = findViewById(2131230980);
        this.z = findViewById(2131231037);
        this.A = findViewById(2131231047);
        this.B = findViewById(2131231048);
        this.C = findViewById(2131230731);
        this.D = findViewById(2131230730);
        this.E = findViewById(2131230737);
        this.F = findViewById(2131230732);
        this.G = findViewById(2131231007);
        this.H = findViewById(2131231341);
        this.I = findViewById(2131230981);
        this.J = findViewById(2131231036);
        this.K = findViewById(2131231035);
        this.L = findViewById(2131231292);
        this.M = findViewById(2131231293);
        this.N = findViewById(2131231294);
        this.O = findViewById(2131231295);
        this.P = findViewById(2131230934);
        this.Q = findViewById(2131230935);
        this.R = findViewById(2131230932);
        this.S = findViewById(2131230933);
        this.T = findViewById(2131230848);
        this.U = findViewById(2131230850);
        this.V = getSharedPreferences("sp", 0);
        this.X.setType("*/*");
        this.X.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        this.C.setOnClickListener(new d(this));
        this.F.setOnClickListener(new e(this));
        this.G.setOnClickListener(new f(this));
        this.P.addTextChangedListener(new g(this));
        this.Q.addTextChangedListener(new h(this));
        this.R.addTextChangedListener(new i(this));
        this.S.addTextChangedListener(new j(this));
        this.T.setOnClickListener(new k(this));
        this.U.setOnClickListener(new l(this));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Attempt to invoke interface method 'java.util.Iterator java.util.List.iterator()' on a null object reference
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(Unknown Source:139)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(Unknown Source:6)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(Unknown Source:6)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(Unknown Source:34)
     */
    private void I() {
        runOnUiThread(new a(this));
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.d = l;
            if (!l.trim().equals("")) {
                this.t = (ArrayList) new Gson().fromJson(this.d, new b(this).getType());
            }
        } catch (Exception unused) {
        }
        this.r = false;
        if (getIntent().hasExtra("edit")) {
            this.U.setText(getString(2131689628));
            this.D.setText(getString(2131689510));
            this.P.setEnabled(false);
            this.F.setVisibility(4);
            try {
                Collections.reverse(this.t);
                this.i = (HashMap) this.t.get((int) Double.parseDouble(getIntent().getStringExtra("position")));
                ArrayList arrayList = (ArrayList) new Gson().fromJson(this.i.get("list").toString(), new c(this).getType());
                this.u = arrayList;
                Collections.reverse(arrayList);
                this.j = Double.parseDouble(getIntent().getStringExtra("version"));
                this.P.setText(((HashMap) this.t.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString());
                this.Q.setText(((HashMap) this.u.get((int) this.j)).get("plugin_name").toString());
                this.R.setText(((HashMap) this.u.get((int) this.j)).get("plugin_author").toString());
                this.S.setText(((HashMap) this.u.get((int) this.j)).get("plugin_desc").toString());
            } catch (Exception e) {
                getApplicationContext();
                ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
            }
            this.E.setText(getIntent().getStringExtra("ver"));
            this.G.setVisibility(4);
        } else {
            this.P.setText(this.V.getString("eID", ""));
            this.Q.setText(this.V.getString("eNAME", ""));
            this.R.setText(this.V.getString("eAUTHOR", ""));
            this.S.setText(this.V.getString("eDESC", ""));
            this.E.setVisibility(8);
        }
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (!"android.intent.action.VIEW".equals(action) || data == null) {
            return;
        }
        String F = F(data);
        if (F.endsWith(".plugin") || F.endsWith(".py")) {
            d.b(getApplicationContext(), "Открыт файл: ".concat(Uri.parse(F).getLastPathSegment()));
            int i = Build.VERSION.SDK_INT;
            if (i <= 29) {
                a.m("", "");
            } else if (!Environment.isExternalStorageManager()) {
                this.Y.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                startActivity(this.Y);
            }
            this.s = false;
            if (i > 29 && !Environment.isExternalStorageManager()) {
                this.s = true;
            }
            if (this.s) {
                d.b(getApplicationContext(), "Нет доступа к файлу!");
                return;
            }
            if (!F.endsWith(".plugin") && !F.endsWith(".py")) {
                d.b(getApplicationContext(), "Требуется файл .plugin/.py!");
                return;
            }
            try {
                String l2 = a.l(F);
                this.k = l2;
                Matcher matcher = Pattern.compile("(?ms)__\\s*(id|name|description|author|version)\\s*__\\s*=\\s*(?:\"\"\"([\\s\\S]*?)\"\"\"|'([^']*?)'|\"([^\"]*?)\")").matcher(l2);
                while (matcher.find()) {
                    String group = matcher.group(1);
                    String group2 = matcher.group(2) != null ? matcher.group(2) : matcher.group(3) != null ? matcher.group(3) : matcher.group(4) != null ? matcher.group(4) : "";
                    switch (group.hashCode()) {
                        case -1724546052:
                            if (!group.equals("description")) {
                                break;
                            } else {
                                this.n = group2;
                                break;
                            }
                        case -1406328437:
                            if (!group.equals("author")) {
                                break;
                            } else {
                                this.o = group2;
                                break;
                            }
                        case 3355:
                            if (!group.equals("id")) {
                                break;
                            } else {
                                this.l = group2;
                                break;
                            }
                        case 3373707:
                            if (!group.equals("name")) {
                                break;
                            } else {
                                this.m = group2;
                                break;
                            }
                        case 351608024:
                            if (!group.equals("version")) {
                                break;
                            } else {
                                this.p = group2;
                                break;
                            }
                    }
                }
                this.q = l2.replaceAll("(?ms)__\\s*(?:id|name|description|author|version)\\s*__\\s*=\\s*(?:\"\"\"[\\s\\S]*?\"\"\"|'[^']*?'|\"[^\"]*?\")\\s*;?\\s*", "");
                this.P.setText(this.l);
                this.Q.setText(this.m);
                this.R.setText(this.o);
                this.S.setText(this.n);
                this.U.setText(getString(2131689541));
                this.r = true;
            } catch (Exception e2) {
                d.b(getApplicationContext(), e2.getMessage());
                this.r = false;
                this.U.setText(getString(2131689629));
            }
        }
    }

    static /* bridge */ /* synthetic */ ImageView a(CreateActivity createActivity) {
        return createActivity.C;
    }

    static /* bridge */ /* synthetic */ ImageView b(CreateActivity createActivity) {
        return createActivity.F;
    }

    static /* bridge */ /* synthetic */ MaterialButton c(CreateActivity createActivity) {
        return createActivity.U;
    }

    static /* bridge */ /* synthetic */ String d(CreateActivity createActivity) {
        return createActivity.q;
    }

    static /* bridge */ /* synthetic */ boolean e(CreateActivity createActivity) {
        return createActivity.c;
    }

    static /* bridge */ /* synthetic */ EditText f(CreateActivity createActivity) {
        return createActivity.R;
    }

    static /* bridge */ /* synthetic */ EditText g(CreateActivity createActivity) {
        return createActivity.S;
    }

    static /* bridge */ /* synthetic */ EditText h(CreateActivity createActivity) {
        return createActivity.P;
    }

    static /* bridge */ /* synthetic */ EditText i(CreateActivity createActivity) {
        return createActivity.Q;
    }

    static /* bridge */ /* synthetic */ Intent j(CreateActivity createActivity) {
        return createActivity.X;
    }

    static /* bridge */ /* synthetic */ Intent k(CreateActivity createActivity) {
        return createActivity.W;
    }

    static /* bridge */ /* synthetic */ boolean l(CreateActivity createActivity) {
        return createActivity.e;
    }

    static /* bridge */ /* synthetic */ boolean m(CreateActivity createActivity) {
        return createActivity.r;
    }

    static /* bridge */ /* synthetic */ ImageView n(CreateActivity createActivity) {
        return createActivity.G;
    }

    static /* bridge */ /* synthetic */ Intent o(CreateActivity createActivity) {
        return createActivity.Y;
    }

    static /* bridge */ /* synthetic */ ArrayList p(CreateActivity createActivity) {
        return createActivity.t;
    }

    static /* bridge */ /* synthetic */ double q(CreateActivity createActivity) {
        return createActivity.f;
    }

    static /* bridge */ /* synthetic */ HashMap r(CreateActivity createActivity) {
        return createActivity.b;
    }

    static /* bridge */ /* synthetic */ SharedPreferences s(CreateActivity createActivity) {
        return createActivity.V;
    }

    static /* bridge */ /* synthetic */ HashMap t(CreateActivity createActivity) {
        return createActivity.g;
    }

    static /* bridge */ /* synthetic */ String u(CreateActivity createActivity) {
        return createActivity.p;
    }

    static /* bridge */ /* synthetic */ double v(CreateActivity createActivity) {
        return createActivity.j;
    }

    static /* bridge */ /* synthetic */ ArrayList w(CreateActivity createActivity) {
        return createActivity.u;
    }

    static /* bridge */ /* synthetic */ void x(CreateActivity createActivity, String str) {
        createActivity.q = str;
    }

    static /* bridge */ /* synthetic */ void y(CreateActivity createActivity, boolean z) {
        createActivity.c = z;
    }

    static /* bridge */ /* synthetic */ void z(CreateActivity createActivity, boolean z) {
        createActivity.e = z;
    }

    public String F(Uri uri) {
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            File createTempFile = File.createTempFile("shared", ".plugin", getCacheDir());
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read == -1) {
                    openInputStream.close();
                    fileOutputStream.close();
                    return createTempFile.getAbsolutePath();
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Attempt to invoke interface method 'java.util.Iterator java.util.List.iterator()' on a null object reference
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(Unknown Source:139)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(Unknown Source:6)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(Unknown Source:6)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(Unknown Source:34)
     */
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == -1) {
            ArrayList arrayList = new ArrayList();
            if (intent != null) {
                if (intent.getClipData() != null) {
                    for (int i3 = 0; i3 < intent.getClipData().getItemCount(); i3++) {
                        arrayList.add(a.a(getApplicationContext(), intent.getClipData().getItemAt(i3).getUri()));
                    }
                } else {
                    arrayList.add(a.a(getApplicationContext(), intent.getData()));
                }
            }
            this.s = false;
            if (Build.VERSION.SDK_INT > 29 && !Environment.isExternalStorageManager()) {
                this.s = true;
            }
            if (this.s) {
                d.b(getApplicationContext(), "Нет доступа к файлу!");
                return;
            }
            if (!((String) arrayList.get(0)).endsWith(".plugin") && !((String) arrayList.get(0)).endsWith(".py") && !((String) arrayList.get(0)).endsWith(".python")) {
                d.b(getApplicationContext(), "Требуется файл .plugin/.py!");
                return;
            }
            try {
                String l = a.l((String) arrayList.get(0));
                this.k = l;
                Matcher matcher = Pattern.compile("(?ms)__\\s*(id|name|description|author|version)\\s*__\\s*=\\s*(?:\"\"\"([\\s\\S]*?)\"\"\"|'([^']*?)'|\"([^\"]*?)\")").matcher(l);
                while (true) {
                    String str = "";
                    if (!matcher.find()) {
                        this.q = l.replaceAll("(?ms)__\\s*(?:id|name|description|author|version)\\s*__\\s*=\\s*(?:\"\"\"[\\s\\S]*?\"\"\"|'[^']*?'|\"[^\"]*?\")\\s*;?\\s*", "");
                        this.P.setText(this.l);
                        this.Q.setText(this.m);
                        this.R.setText(this.o);
                        this.S.setText(this.n);
                        this.U.setText(getString(2131689541));
                        this.r = true;
                        return;
                    }
                    String group = matcher.group(1);
                    if (matcher.group(2) != null) {
                        str = matcher.group(2);
                    } else if (matcher.group(3) != null) {
                        str = matcher.group(3);
                    } else if (matcher.group(4) != null) {
                        str = matcher.group(4);
                    }
                    switch (group.hashCode()) {
                        case -1724546052:
                            if (group.equals("description")) {
                                this.n = str;
                                break;
                            } else {
                                break;
                            }
                        case -1406328437:
                            if (group.equals("author")) {
                                this.o = str;
                                break;
                            } else {
                                break;
                            }
                        case 3355:
                            if (group.equals("id")) {
                                this.l = str;
                                break;
                            } else {
                                break;
                            }
                        case 3373707:
                            if (group.equals("name")) {
                                this.m = str;
                                break;
                            } else {
                                break;
                            }
                        case 351608024:
                            if (group.equals("version")) {
                                this.p = str;
                                break;
                            } else {
                                break;
                            }
                    }
                }
            } catch (Exception e) {
                d.b(getApplicationContext(), e.getMessage());
                this.r = false;
                this.U.setText(getString(2131689629));
            }
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(17432576, 17432577);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427360);
        H(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            I();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            I();
        }
    }
}
