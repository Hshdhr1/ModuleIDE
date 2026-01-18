package ru.yufic.exteraPlugins;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class SettingsActivity extends AppCompatActivity {
    private LinearLayout A;
    private LinearLayout B;
    private LinearLayout C;
    private TextView D;
    private TextView E;
    private LinearLayout F;
    private LinearLayout G;
    private LinearLayout H;
    private LinearLayout I;
    private TextView J;
    private LinearLayout K;
    private TextView L;
    private ImageView M;
    private MaterialButton N;
    private MaterialButton O;
    private LinearLayout P;
    private MaterialButton Q;
    private LinearLayout R;
    private RadioButton S;
    private RadioButton T;
    private RadioButton U;
    private TextInputLayout V;
    private MaterialButton W;
    private EditText X;
    private TextView Y;
    private AlertDialog.Builder c0;
    private SharedPreferences f0;
    private AlertDialog.Builder h0;
    private AlertDialog.Builder i0;
    private LinearLayout p;
    private ScrollView q;
    private LinearLayout r;
    private LinearLayout s;
    private ImageView t;
    private LinearLayout u;
    private LinearLayout v;
    private TextView w;
    private LinearLayout x;
    private LinearLayout y;
    private LinearLayout z;
    public final int a = 101;
    private String b = "";
    private String c = "";
    private String d = "";
    private boolean e = false;
    private double f = 0.0d;
    private double g = 0.0d;
    private String h = "";
    private String i = "";
    private boolean j = false;
    private double k = 0.0d;
    private String l = "";
    private ArrayList m = new ArrayList();
    private ArrayList n = new ArrayList();
    private ArrayList o = new ArrayList();
    private Intent Z = new Intent();
    private Calendar a0 = Calendar.getInstance();
    private Intent b0 = new Intent();
    private Intent d0 = new Intent("android.intent.action.GET_CONTENT");
    private Intent e0 = new Intent();
    private Intent g0 = new Intent();
    private Intent j0 = new Intent();

    static /* bridge */ /* synthetic */ RadioButton A(SettingsActivity settingsActivity) {
        return settingsActivity.T;
    }

    static /* bridge */ /* synthetic */ RadioButton B(SettingsActivity settingsActivity) {
        return settingsActivity.U;
    }

    static /* bridge */ /* synthetic */ String C(SettingsActivity settingsActivity) {
        return settingsActivity.b;
    }

    static /* bridge */ /* synthetic */ String D(SettingsActivity settingsActivity) {
        return settingsActivity.d;
    }

    static /* bridge */ /* synthetic */ String E(SettingsActivity settingsActivity) {
        return settingsActivity.h;
    }

    static /* bridge */ /* synthetic */ SharedPreferences F(SettingsActivity settingsActivity) {
        return settingsActivity.f0;
    }

    static /* bridge */ /* synthetic */ TextView G(SettingsActivity settingsActivity) {
        return settingsActivity.Y;
    }

    static /* bridge */ /* synthetic */ ArrayList H(SettingsActivity settingsActivity) {
        return settingsActivity.n;
    }

    static /* bridge */ /* synthetic */ double I(SettingsActivity settingsActivity) {
        return settingsActivity.g;
    }

    static /* bridge */ /* synthetic */ void J(SettingsActivity settingsActivity, Calendar calendar) {
        settingsActivity.a0 = calendar;
    }

    static /* bridge */ /* synthetic */ void K(SettingsActivity settingsActivity, String str) {
        settingsActivity.i = str;
    }

    static /* bridge */ /* synthetic */ void L(SettingsActivity settingsActivity, String str) {
        settingsActivity.l = str;
    }

    static /* bridge */ /* synthetic */ void M(SettingsActivity settingsActivity, ArrayList arrayList) {
        settingsActivity.o = arrayList;
    }

    static /* bridge */ /* synthetic */ void N(SettingsActivity settingsActivity, double d) {
        settingsActivity.f = d;
    }

    static /* bridge */ /* synthetic */ void O(SettingsActivity settingsActivity, boolean z) {
        settingsActivity.j = z;
    }

    static /* bridge */ /* synthetic */ void P(SettingsActivity settingsActivity, String str) {
        settingsActivity.c = str;
    }

    static /* bridge */ /* synthetic */ void Q(SettingsActivity settingsActivity, ArrayList arrayList) {
        settingsActivity.m = arrayList;
    }

    static /* bridge */ /* synthetic */ void R(SettingsActivity settingsActivity, double d) {
        settingsActivity.k = d;
    }

    static /* bridge */ /* synthetic */ void S(SettingsActivity settingsActivity, String str) {
        settingsActivity.b = str;
    }

    static /* bridge */ /* synthetic */ void T(SettingsActivity settingsActivity, String str) {
        settingsActivity.h = str;
    }

    static /* bridge */ /* synthetic */ void U(SettingsActivity settingsActivity, ArrayList arrayList) {
        settingsActivity.n = arrayList;
    }

    static /* bridge */ /* synthetic */ void V(SettingsActivity settingsActivity, double d) {
        settingsActivity.g = d;
    }

    static /* bridge */ /* synthetic */ int W(SettingsActivity settingsActivity, int i) {
        return settingsActivity.d0(i);
    }

    static /* bridge */ /* synthetic */ ImageView a(SettingsActivity settingsActivity) {
        return settingsActivity.t;
    }

    static /* bridge */ /* synthetic */ ImageView b(SettingsActivity settingsActivity) {
        return settingsActivity.M;
    }

    static /* bridge */ /* synthetic */ TextView c(SettingsActivity settingsActivity) {
        return settingsActivity.L;
    }

    static /* bridge */ /* synthetic */ Calendar d(SettingsActivity settingsActivity) {
        return settingsActivity.a0;
    }

    private int d0(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    static /* bridge */ /* synthetic */ String e(SettingsActivity settingsActivity) {
        return settingsActivity.i;
    }

    private void e0(Bundle bundle) {
        this.p = findViewById(2131231314);
        this.q = findViewById(2131231341);
        this.r = findViewById(2131230750);
        this.s = findViewById(2131230980);
        this.t = findViewById(2131230731);
        this.u = findViewById(2131231047);
        this.v = findViewById(2131231048);
        this.w = findViewById(2131230730);
        this.x = findViewById(2131231028);
        this.y = findViewById(2131231036);
        this.z = findViewById(2131231037);
        this.A = findViewById(2131230981);
        this.B = findViewById(2131231038);
        this.C = findViewById(2131230982);
        this.D = findViewById(2131231299);
        this.E = findViewById(2131231300);
        this.F = findViewById(2131231042);
        this.G = findViewById(2131231041);
        this.H = findViewById(2131230983);
        this.I = findViewById(2131231035);
        this.J = findViewById(2131231298);
        this.K = findViewById(2131231045);
        this.L = findViewById(2131230742);
        this.M = findViewById(2131230741);
        this.N = findViewById(2131230850);
        this.O = findViewById(2131231075);
        this.P = findViewById(2131231040);
        this.Q = findViewById(2131231076);
        this.R = findViewById(2131231039);
        this.S = findViewById(2131231170);
        this.T = findViewById(2131231171);
        this.U = findViewById(2131231172);
        this.V = findViewById(2131231294);
        this.W = findViewById(2131231077);
        this.X = findViewById(2131230931);
        this.Y = findViewById(2131231297);
        this.c0 = new AlertDialog.Builder(this);
        this.d0.setType("*/*");
        this.d0.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        this.f0 = getSharedPreferences("sp", 0);
        this.h0 = new AlertDialog.Builder(this);
        this.i0 = new AlertDialog.Builder(this);
        this.t.setOnClickListener(new j5(this));
        this.E.setOnClickListener(new k5(this));
        this.K.setOnClickListener(new o5(this));
        this.N.setOnClickListener(new p5(this));
        this.O.setOnClickListener(new q5(this));
        this.Q.setOnClickListener(new r5(this));
        this.S.setOnCheckedChangeListener(new s5(this));
        this.T.setOnCheckedChangeListener(new t5(this));
        this.U.setOnCheckedChangeListener(new u5(this));
        this.W.setOnClickListener(new r4(this));
        this.X.addTextChangedListener(new s4(this));
    }

    static /* bridge */ /* synthetic */ EditText f(SettingsActivity settingsActivity) {
        return settingsActivity.X;
    }

    private void f0() {
        runOnUiThread(new u4(this));
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.b = l;
            if (!l.trim().equals("")) {
                this.m = (ArrayList) new Gson().fromJson(this.b, new v4(this).getType());
            }
            this.L.setText(String.valueOf(this.m.size()));
        } catch (Exception unused) {
            this.L.setText("N/A");
        }
        if (this.f0.getString("api", "").equals("gemini")) {
            this.X.setText(this.f0.getString("apiKeyG", ""));
            if (this.f0.getString("model", "").equals("gemini-2.5-flash")) {
                this.U.setChecked(true);
            } else {
                this.T.setChecked(true);
            }
        } else {
            this.X.setText(this.f0.getString("apiKey", ""));
            this.S.setChecked(true);
        }
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        if (!"android.intent.action.VIEW".equals(action) || data == null) {
            return;
        }
        String c0 = c0(data);
        if (c0.endsWith(".piex")) {
            d.b(getApplicationContext(), "Резервная копия: ".concat(Uri.parse(c0).getLastPathSegment()));
            int i = Build.VERSION.SDK_INT;
            if (i <= 29) {
                a.m("", "");
            } else if (!Environment.isExternalStorageManager()) {
                this.e0.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                startActivity(this.e0);
            }
            this.e = false;
            if (i > 29 && !Environment.isExternalStorageManager()) {
                this.e = true;
            }
            if (this.e) {
                d.b(getApplicationContext(), "Нет доступа к файлу!");
                return;
            }
            if (!c0.endsWith(".piex")) {
                d.b(getApplicationContext(), "Требуется файл .piex!");
                return;
            }
            try {
                this.d = a.l(c0);
                Z(false);
            } catch (Exception e) {
                d.b(getApplicationContext(), e.getMessage());
            }
        }
    }

    static /* bridge */ /* synthetic */ Intent g(SettingsActivity settingsActivity) {
        return settingsActivity.b0;
    }

    private static String g0(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            sb.append((char) (((str.charAt(i) - str2.charAt(i % str2.length())) + 65535) % 65535));
        }
        return sb.toString();
    }

    static /* bridge */ /* synthetic */ LinearLayout h(SettingsActivity settingsActivity) {
        return settingsActivity.A;
    }

    private static String h0(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            sb.append((char) ((str.charAt(i) + str2.charAt(i % str2.length())) % 65535));
        }
        return sb.toString();
    }

    static /* bridge */ /* synthetic */ LinearLayout i(SettingsActivity settingsActivity) {
        return settingsActivity.C;
    }

    private static byte[] i0(byte[] bArr, String str) {
        return g0(new String(bArr, "UTF-8"), str).getBytes("UTF-8");
    }

    static /* bridge */ /* synthetic */ Intent j(SettingsActivity settingsActivity) {
        return settingsActivity.Z;
    }

    private static byte[] j0(byte[] bArr, String str) {
        return h0(new String(bArr, "UTF-8"), str).getBytes("UTF-8");
    }

    static /* bridge */ /* synthetic */ Intent k(SettingsActivity settingsActivity) {
        return settingsActivity.g0;
    }

    static /* bridge */ /* synthetic */ Intent l(SettingsActivity settingsActivity) {
        return settingsActivity.e0;
    }

    static /* bridge */ /* synthetic */ String m(SettingsActivity settingsActivity) {
        return settingsActivity.l;
    }

    static /* bridge */ /* synthetic */ LinearLayout n(SettingsActivity settingsActivity) {
        return settingsActivity.y;
    }

    static /* bridge */ /* synthetic */ LinearLayout o(SettingsActivity settingsActivity) {
        return settingsActivity.z;
    }

    static /* bridge */ /* synthetic */ LinearLayout p(SettingsActivity settingsActivity) {
        return settingsActivity.B;
    }

    static /* bridge */ /* synthetic */ LinearLayout q(SettingsActivity settingsActivity) {
        return settingsActivity.K;
    }

    static /* bridge */ /* synthetic */ ArrayList r(SettingsActivity settingsActivity) {
        return settingsActivity.o;
    }

    static /* bridge */ /* synthetic */ double s(SettingsActivity settingsActivity) {
        return settingsActivity.f;
    }

    static /* bridge */ /* synthetic */ Intent t(SettingsActivity settingsActivity) {
        return settingsActivity.j0;
    }

    static /* bridge */ /* synthetic */ boolean u(SettingsActivity settingsActivity) {
        return settingsActivity.j;
    }

    static /* bridge */ /* synthetic */ String v(SettingsActivity settingsActivity) {
        return settingsActivity.c;
    }

    static /* bridge */ /* synthetic */ Intent w(SettingsActivity settingsActivity) {
        return settingsActivity.d0;
    }

    static /* bridge */ /* synthetic */ ArrayList x(SettingsActivity settingsActivity) {
        return settingsActivity.m;
    }

    static /* bridge */ /* synthetic */ double y(SettingsActivity settingsActivity) {
        return settingsActivity.k;
    }

    static /* bridge */ /* synthetic */ RadioButton z(SettingsActivity settingsActivity) {
        return settingsActivity.S;
    }

    public String X(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append((char) (((str.charAt(i) - str2.charAt(i % str2.length())) + 65535) % 65535));
        }
        return sb.toString();
    }

    public String Y(String str, String str2) {
        try {
            byte[] decode = Base64.getDecoder().decode(str);
            if (decode.length <= 32) {
                return "invalid key";
            }
            byte[] copyOf = Arrays.copyOf(decode, decode.length - 32);
            byte[] copyOfRange = Arrays.copyOfRange(decode, decode.length - 32, decode.length);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str2.getBytes("UTF-8"), "HmacSHA256"));
            return !MessageDigest.isEqual(mac.doFinal(copyOf), copyOfRange) ? "invalid key" : new String(i0(copyOf, str2), "UTF-8");
        } catch (Exception unused) {
            return "invalid key";
        }
    }

    public void Z(boolean z) {
        this.j = false;
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        if (z) {
            materialAlertDialogBuilder.setTitle("Параметры экспорта");
        } else {
            materialAlertDialogBuilder.setTitle("Параметры импорта");
        }
        materialAlertDialogBuilder.setIcon(2131165347);
        materialAlertDialogBuilder.setMessage("Выберите шифрование");
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.setPositiveButton("Пароль", new c5(this, z));
        materialAlertDialogBuilder.setNegativeButton("Без", new h5(this, z));
        materialAlertDialogBuilder.setNeutralButton(getString(2131689509), new i5(this));
        materialAlertDialogBuilder.show();
    }

    public String a0(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append((char) ((str.charAt(i) + str2.charAt(i % str2.length())) % 65535));
        }
        return sb.toString();
    }

    public String b0(String str, String str2) {
        try {
            byte[] j0 = j0(str.getBytes("UTF-8"), str2);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str2.getBytes("UTF-8"), "HmacSHA256"));
            byte[] doFinal = mac.doFinal(j0);
            ByteBuffer allocate = ByteBuffer.allocate(j0.length + doFinal.length);
            allocate.put(j0);
            allocate.put(doFinal);
            return Base64.getEncoder().encodeToString(allocate.array());
        } catch (Exception e) {
            Log.e("SettingsActivity", "encryptWithHmac failed", e);
            return null;
        }
    }

    public String c0(Uri uri) {
        try {
            InputStream openInputStream = getContentResolver().openInputStream(uri);
            File createTempFile = File.createTempFile("shared", ".piex", getCacheDir());
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
            this.e = false;
            if (Build.VERSION.SDK_INT > 29 && !Environment.isExternalStorageManager()) {
                this.e = true;
            }
            if (this.e) {
                d.b(getApplicationContext(), "Нет доступа к файлу!");
            } else if (!((String) arrayList.get(0)).endsWith(".piex")) {
                d.b(getApplicationContext(), "Выберите файл формата piex!");
            } else {
                this.d = a.l((String) arrayList.get(0));
                Z(false);
            }
        }
    }

    public void onBackPressed() {
        if (getIntent().hasExtra("proj")) {
            finish();
            overridePendingTransition(17432576, 17432577);
        } else {
            this.Z.setClass(getApplicationContext(), MainActivity.class);
            startActivity(this.Z);
            finish();
            overridePendingTransition(17432576, 17432577);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427443);
        e0(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            f0();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            f0();
        }
    }
}
