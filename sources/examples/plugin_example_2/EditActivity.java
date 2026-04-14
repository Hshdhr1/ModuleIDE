package ru.yufic.exteraPlugins;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.PrecomputedTextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.blacksquircle.ui.editorkit.model.UndoStack;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import com.blacksquircle.ui.language.python.PythonLanguage;
import com.github.difflib.DiffUtils;
import com.github.difflib.unifieddiff.UnifiedDiffFile;
import com.github.difflib.unifieddiff.UnifiedDiffReader;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class EditActivity extends AppCompatActivity {
    private ImageView A0;
    private TextView B0;
    private TextView C0;
    private ImageView D0;
    private TextView E0;
    private ImageView F0;
    private TextView G0;
    private ImageView H0;
    private TextView I0;
    private AlertDialog.Builder J0;
    private LinearLayout M;
    private AlertDialog.Builder M0;
    private LinearLayout N;
    private c N0;
    private LinearLayout O;
    private b O0;
    private LinearLayout P;
    private ImageView Q;
    private AlertDialog.Builder Q0;
    private LinearLayout R;
    private AlertDialog.Builder R0;
    private ImageView S;
    private SharedPreferences S0;
    private ImageView T;
    private AlertDialog.Builder T0;
    private ImageView U;
    private ImageView V;
    private TimerTask V0;
    private LinearLayout W;
    private Spinner X;
    private TextView Y;
    private LinearLayout Z;
    private LinearLayout a0;
    private MaterialToolbar b;
    private ScrollView b0;
    private AppBarLayout c;
    private LinearLayout c0;
    private CoordinatorLayout d;
    private TextProcessor d0;
    private DrawerLayout e;
    private LinearLayout e0;
    private LinearLayout f0;
    private ImageView g0;
    private ImageView h0;
    private ImageView i0;
    private ImageView j0;
    private ImageView k0;
    private LinearLayout l0;
    private ImageView m0;
    private ImageView n0;
    private ImageView o0;
    private LinearLayout p0;
    private LinearLayout q0;
    private LinearLayout r0;
    private LinearLayout s0;
    private Dialog t;
    private LinearLayout t0;
    private LinearLayout u0;
    private LinearLayout v0;
    private LinearLayout w0;
    private LinearLayout x0;
    private LinearLayout y0;
    private LinearLayout z0;
    private Timer a = new Timer();
    private HashMap f = new HashMap();
    private String g = "";
    private double h = 0.0d;
    private double i = 0.0d;
    private HashMap j = new HashMap();
    private HashMap k = new HashMap();
    private HashMap l = new HashMap();
    private String m = "";
    private String n = "";
    private HashMap o = new HashMap();
    private String p = "";
    private HashMap q = new HashMap();
    private String r = "";
    private String s = "";
    private boolean u = false;
    private double v = 0.0d;
    private String w = "";
    private boolean x = false;
    private String y = "";
    private final Executor z = Executors.newSingleThreadExecutor();
    private final Handler A = new Handler(Looper.getMainLooper());
    private boolean B = false;
    private String C = "";
    private double D = 0.0d;
    private boolean E = false;
    private String F = "";
    private ArrayList G = new ArrayList();
    private ArrayList H = new ArrayList();
    private ArrayList I = new ArrayList();
    private ArrayList J = new ArrayList();
    private ArrayList K = new ArrayList();
    private ArrayList L = new ArrayList();
    private Intent K0 = new Intent();
    private Intent L0 = new Intent();
    private Intent P0 = new Intent();
    private Intent U0 = new Intent();

    static /* bridge */ /* synthetic */ ImageView A(EditActivity editActivity) {
        return editActivity.h0;
    }

    static /* bridge */ /* synthetic */ Intent B(EditActivity editActivity) {
        return editActivity.K0;
    }

    static /* bridge */ /* synthetic */ LinearLayout C(EditActivity editActivity) {
        return editActivity.W;
    }

    static /* bridge */ /* synthetic */ Handler D(EditActivity editActivity) {
        return editActivity.A;
    }

    static /* bridge */ /* synthetic */ ArrayList E(EditActivity editActivity) {
        return editActivity.K;
    }

    static /* bridge */ /* synthetic */ double F(EditActivity editActivity) {
        return editActivity.h;
    }

    static /* bridge */ /* synthetic */ AlertDialog.Builder G(EditActivity editActivity) {
        return editActivity.J0;
    }

    static /* bridge */ /* synthetic */ double H(EditActivity editActivity) {
        return editActivity.v;
    }

    static /* bridge */ /* synthetic */ boolean I(EditActivity editActivity) {
        return editActivity.x;
    }

    static /* bridge */ /* synthetic */ String J(EditActivity editActivity) {
        return editActivity.w;
    }

    static /* bridge */ /* synthetic */ String K(EditActivity editActivity) {
        return editActivity.m;
    }

    static /* bridge */ /* synthetic */ ArrayList L(EditActivity editActivity) {
        return editActivity.G;
    }

    static /* bridge */ /* synthetic */ ImageView M(EditActivity editActivity) {
        return editActivity.g0;
    }

    static /* bridge */ /* synthetic */ SharedPreferences N(EditActivity editActivity) {
        return editActivity.S0;
    }

    static /* bridge */ /* synthetic */ ImageView O(EditActivity editActivity) {
        return editActivity.k0;
    }

    static /* bridge */ /* synthetic */ Spinner P(EditActivity editActivity) {
        return editActivity.X;
    }

    static /* bridge */ /* synthetic */ boolean Q(EditActivity editActivity) {
        return editActivity.u;
    }

    static /* bridge */ /* synthetic */ HashMap R(EditActivity editActivity) {
        return editActivity.l;
    }

    static /* bridge */ /* synthetic */ double S(EditActivity editActivity) {
        return editActivity.i;
    }

    static /* bridge */ /* synthetic */ ArrayList T(EditActivity editActivity) {
        return editActivity.I;
    }

    static /* bridge */ /* synthetic */ ArrayList U(EditActivity editActivity) {
        return editActivity.H;
    }

    static /* bridge */ /* synthetic */ void V(EditActivity editActivity, String str) {
        editActivity.F = str;
    }

    static /* bridge */ /* synthetic */ void W(EditActivity editActivity, double d) {
        editActivity.h = d;
    }

    static /* bridge */ /* synthetic */ void X(EditActivity editActivity, double d) {
        editActivity.v = d;
    }

    static /* bridge */ /* synthetic */ void Y(EditActivity editActivity, boolean z) {
        editActivity.x = z;
    }

    static /* bridge */ /* synthetic */ void Z(EditActivity editActivity, String str) {
        editActivity.w = str;
    }

    static /* bridge */ /* synthetic */ ImageView a(EditActivity editActivity) {
        return editActivity.T;
    }

    static /* bridge */ /* synthetic */ void a0(EditActivity editActivity, String str) {
        editActivity.m = str;
    }

    static /* bridge */ /* synthetic */ ImageView b(EditActivity editActivity) {
        return editActivity.Q;
    }

    static /* bridge */ /* synthetic */ void b0(EditActivity editActivity, boolean z) {
        editActivity.u = z;
    }

    static /* bridge */ /* synthetic */ ImageView c(EditActivity editActivity) {
        return editActivity.U;
    }

    static /* bridge */ /* synthetic */ void c0(EditActivity editActivity, HashMap hashMap) {
        editActivity.l = hashMap;
    }

    static /* bridge */ /* synthetic */ ImageView d(EditActivity editActivity) {
        return editActivity.S;
    }

    static /* bridge */ /* synthetic */ void d0(EditActivity editActivity, double d) {
        editActivity.i = d;
    }

    static /* bridge */ /* synthetic */ ImageView e(EditActivity editActivity) {
        return editActivity.V;
    }

    static /* bridge */ /* synthetic */ int e0(EditActivity editActivity, int i) {
        return editActivity.m0(i);
    }

    static /* bridge */ /* synthetic */ DrawerLayout f(EditActivity editActivity) {
        return editActivity.e;
    }

    static /* bridge */ /* synthetic */ ImageView g(EditActivity editActivity) {
        return editActivity.A0;
    }

    static /* bridge */ /* synthetic */ ImageView h(EditActivity editActivity) {
        return editActivity.D0;
    }

    static /* bridge */ /* synthetic */ ImageView i(EditActivity editActivity) {
        return editActivity.F0;
    }

    static /* bridge */ /* synthetic */ ImageView j(EditActivity editActivity) {
        return editActivity.H0;
    }

    static /* bridge */ /* synthetic */ LinearLayout k(EditActivity editActivity) {
        return editActivity.p0;
    }

    static /* bridge */ /* synthetic */ LinearLayout l(EditActivity editActivity) {
        return editActivity.w0;
    }

    static /* bridge */ /* synthetic */ LinearLayout m(EditActivity editActivity) {
        return editActivity.t0;
    }

    private int m0(int i) {
        return MaterialColors.getColor(this, i, "getMaterialColor");
    }

    static /* bridge */ /* synthetic */ LinearLayout n(EditActivity editActivity) {
        return editActivity.u0;
    }

    private void n0(Bundle bundle) {
        this.c = findViewById(2131230751);
        this.d = findViewById(2131230752);
        MaterialToolbar findViewById = findViewById(2131230756);
        this.b = findViewById;
        setSupportActionBar(findViewById);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.b.setNavigationOnClickListener(new l1(this));
        this.e = findViewById(2131230753);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.e, this.b, 2131689504, 2131689504);
        this.e.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        LinearLayout findViewById2 = findViewById(2131230755);
        this.M = findViewById(2131231314);
        this.N = findViewById(2131231028);
        this.O = findViewById(2131230750);
        this.P = findViewById(2131230980);
        this.Q = findViewById(2131230731);
        this.R = findViewById(2131231047);
        this.S = findViewById(2131230734);
        this.T = findViewById(2131230721);
        this.U = findViewById(2131230733);
        this.V = findViewById(2131230735);
        this.W = findViewById(2131231040);
        this.X = findViewById(2131231225);
        this.Y = findViewById(2131230730);
        this.Z = findViewById(2131231041);
        this.a0 = findViewById(2131230843);
        this.b0 = findViewById(2131231341);
        this.c0 = findViewById(2131231042);
        this.d0 = findViewById(2131230930);
        this.e0 = findViewById(2131231036);
        this.f0 = findViewById(2131231037);
        this.g0 = findViewById(2131231203);
        this.h0 = findViewById(2131230994);
        this.i0 = findViewById(2131230951);
        this.j0 = findViewById(2131230834);
        this.k0 = findViewById(2131231222);
        this.l0 = findViewById(2131231038);
        this.m0 = findViewById(2131230832);
        this.n0 = findViewById(2131230830);
        this.o0 = findViewById(2131230831);
        this.p0 = findViewById2.findViewById(2131231043);
        this.q0 = findViewById2.findViewById(2131231028);
        this.r0 = findViewById2.findViewById(2131230980);
        this.s0 = findViewById2.findViewById(2131231036);
        this.t0 = findViewById2.findViewById(2131231148);
        this.u0 = findViewById2.findViewById(2131231149);
        this.v0 = findViewById2.findViewById(2131231040);
        this.w0 = findViewById2.findViewById(2131231140);
        this.x0 = findViewById2.findViewById(2131231035);
        this.y0 = findViewById2.findViewById(2131231038);
        this.z0 = findViewById2.findViewById(2131231037);
        this.A0 = findViewById2.findViewById(2131230833);
        this.B0 = findViewById2.findViewById(2131231305);
        this.C0 = findViewById2.findViewById(2131231308);
        this.D0 = findViewById2.findViewById(2131231000);
        this.E0 = findViewById2.findViewById(2131231297);
        this.F0 = findViewById2.findViewById(2131231001);
        this.G0 = findViewById2.findViewById(2131231299);
        this.H0 = findViewById2.findViewById(2131231002);
        this.I0 = findViewById2.findViewById(2131231298);
        this.J0 = new AlertDialog.Builder(this);
        this.M0 = new AlertDialog.Builder(this);
        this.N0 = new c(this);
        this.Q0 = new AlertDialog.Builder(this);
        this.R0 = new AlertDialog.Builder(this);
        this.S0 = getSharedPreferences("sp", 0);
        this.T0 = new AlertDialog.Builder(this);
        this.Q.setOnClickListener(new z1(this));
        this.S.setOnClickListener(new i2(this));
        this.T.setOnClickListener(new j2(this));
        this.U.setOnClickListener(new m2(this));
        this.V.setOnClickListener(new n2(this));
        this.X.setOnItemSelectedListener(new t2(this));
        this.g0.setOnClickListener(new u2(this));
        this.h0.setOnClickListener(new v2(this));
        this.i0.setOnClickListener(new r0(this));
        this.j0.setOnClickListener(new s0(this));
        this.k0.setOnClickListener(new t0(this));
        this.m0.setOnClickListener(new u0(this));
        this.n0.setOnClickListener(new v0(this));
        this.o0.setOnClickListener(new w0(this));
        this.O0 = new x0(this);
    }

    static /* bridge */ /* synthetic */ TextView o(EditActivity editActivity) {
        return editActivity.C0;
    }

    private void o0() {
        runOnUiThread(new i1(this));
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.g = l;
            if (!l.trim().equals("")) {
                this.G = (ArrayList) new Gson().fromJson(this.g, new j1(this).getType());
            }
            this.Y.setText(((HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString());
            Collections.reverse(this.G);
            this.B0.setText(((HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString());
            this.f = (HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")));
            ArrayList arrayList = (ArrayList) new Gson().fromJson(this.f.get("list").toString(), new k1(this).getType());
            this.H = arrayList;
            Collections.reverse(arrayList);
            this.i = 1.0d;
            this.h = 0.0d;
            HashMap hashMap = new HashMap();
            hashMap.put("version", getString(2131689518));
            this.I.add(hashMap);
            for (int i = 0; i < this.H.size(); i++) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("version", ((HashMap) this.H.get((int) this.h)).get("version").toString());
                this.I.add(hashMap2);
                this.h += 1.0d;
            }
            this.X.setAdapter(new w2(this, this.I));
            this.X.setSelection(1);
        } catch (Exception e) {
            getApplicationContext();
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e.getMessage()));
        }
        Dialog dialog = new Dialog(this);
        this.t = dialog;
        dialog.requestWindowFeature(1);
        this.t.setContentView(2131427378);
        if (this.t.getWindow() != null) {
            this.t.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.t.getWindow().addFlags(2);
            this.t.getWindow().setDimAmount(0.5f);
            this.t.getWindow().setLayout(-1, -1);
        }
        this.t.setCanceledOnTouchOutside(false);
        this.t.setCancelable(false);
        try {
            this.d0.setLanguage(new PythonLanguage());
            this.d0.setUndoStack(new UndoStack());
            this.d0.setRedoStack(new UndoStack());
            this.d0.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/fira_code.ttf"), 0);
        } catch (Exception e2) {
            getApplicationContext();
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", e2.getMessage()));
        }
    }

    static /* bridge */ /* synthetic */ String p(EditActivity editActivity) {
        return editActivity.C;
    }

    static /* bridge */ /* synthetic */ String q(EditActivity editActivity) {
        return editActivity.F;
    }

    static /* bridge */ /* synthetic */ HashMap r(EditActivity editActivity) {
        return editActivity.o;
    }

    static /* bridge */ /* synthetic */ ImageView s(EditActivity editActivity) {
        return editActivity.n0;
    }

    static /* bridge */ /* synthetic */ ImageView t(EditActivity editActivity) {
        return editActivity.o0;
    }

    static /* bridge */ /* synthetic */ ImageView u(EditActivity editActivity) {
        return editActivity.m0;
    }

    static /* bridge */ /* synthetic */ ImageView v(EditActivity editActivity) {
        return editActivity.j0;
    }

    static /* bridge */ /* synthetic */ Intent w(EditActivity editActivity) {
        return editActivity.L0;
    }

    static /* bridge */ /* synthetic */ TextProcessor x(EditActivity editActivity) {
        return editActivity.d0;
    }

    static /* bridge */ /* synthetic */ ImageView y(EditActivity editActivity) {
        return editActivity.i0;
    }

    static /* bridge */ /* synthetic */ Intent z(EditActivity editActivity) {
        return editActivity.P0;
    }

    public void f0(String str) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Запрос к ИИ");
        materialAlertDialogBuilder.setIcon(2131165315);
        materialAlertDialogBuilder.setMessage("Напишите сообщение для ИИ (генерация может занимать до 10 минут!)");
        materialAlertDialogBuilder.setCancelable(false);
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        materialAlertDialogBuilder.setView(editText);
        editText.setTextColor(m0(2130903278));
        editText.setHintTextColor(m0(2130903275));
        editText.setHint("Отредактируй плагин");
        editText.setText(str);
        editText.setMaxHeight((int) TypedValue.applyDimension(1, 155.0f, getResources().getDisplayMetrics()));
        materialAlertDialogBuilder.setPositiveButton("Отправить", new x1(this, editText));
        materialAlertDialogBuilder.setNeutralButton(getString(2131689509), new y1(this));
        materialAlertDialogBuilder.show();
    }

    public String g0(String str) {
        this.n = "__id__ = \"".concat(((HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString().replace("\\", "\\\\").replace("\"", "\\\"").replace(" ", "_").concat("\"\n__name__ = \"".concat(((HashMap) this.H.get((int) this.i)).get("plugin_name").toString().replace("\\", "\\\\").replace("\"", "\\\"").concat("\"\n__author__ = \"").concat(((HashMap) this.H.get((int) this.i)).get("plugin_author").toString().replace("\\", "\\\\").replace("\"", "\\\"").concat("\"\n__version__ = \"").concat(((HashMap) this.H.get((int) this.i)).get("version").toString().replace("\\", "").replace("\"", "").replace(" ", "").concat("\""))))));
        if (!((HashMap) this.H.get((int) this.i)).get("plugin_desc").toString().trim().equals("")) {
            this.n = this.n.concat("\n__description__ = \"\"\"".concat(((HashMap) this.H.get((int) this.i)).get("plugin_desc").toString().replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\n").concat("\"\"\"")));
        }
        String replaceAll = Pattern.compile("(?m)^[ \\t]*__(?:id|name|description|author|version)__[ \\t]*=[ \\t]*(\"\"\"[\\s\\S]*?\"\"\"|\"(?:\\\\\"|[^\"])*\"|'(?:\\\\'|[^'])*')[ \\t]*\\r?\\n?").matcher(str).replaceAll("");
        this.y = replaceAll;
        String concat = this.n.concat("\n\n".concat(replaceAll));
        this.n = concat;
        return concat;
    }

    public void h0(String str) {
        this.E = false;
        if (str.contains("HTTP 404: {\"error\":\"Model not found: ")) {
            d.b(getApplicationContext(), "Модель не найдена!");
            this.E = true;
        } else if (str.contains("HTTP 402: {\"error\":\"Model not found or tier not high enough. Your tier: anonymous, required tier: seed. To get a token or add a referrer, visit https://auth.pollinations.ai\",\"status\":402}") || str.contains("HTTP 500: {\"error\":\"Invalid or unrecognized token provided\",\"status\":500}")) {
            d.b(getApplicationContext(), "Неверный API ключ!");
            this.E = true;
        } else if (5.0d < this.D) {
            d.b(getApplicationContext(), str);
            getApplicationContext();
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", str));
            this.E = true;
        } else if (this.S0.getString("api", "").equals("gemini")) {
            getApplicationContext();
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("clipboard", str));
        } else {
            d.b(getApplicationContext(), "Повторная попытка");
            this.D += 1.0d;
        }
        if (this.S0.getString("api", "").equals("gemini")) {
            this.E = true;
        }
        if (!this.E) {
            h2 h2Var = new h2(this);
            this.V0 = h2Var;
            this.a.schedule(h2Var, 5000L);
        } else {
            this.t.dismiss();
            ArrayList arrayList = this.K;
            arrayList.remove(arrayList.size() - 1);
            f0(this.s);
        }
    }

    public void i0(String str) {
        try {
            try {
                JSONArray jSONArray = new JSONObject(str).getJSONArray("choices");
                if (jSONArray.length() > 0) {
                    this.r = jSONArray.getJSONObject(0).getJSONObject("message").getString("content");
                } else {
                    this.r = "";
                }
            } catch (JSONException unused) {
                this.r = "";
            }
            this.r = Pattern.compile("(?m)^[ \\t]*__(?:id|name|description|author|version)__[ \\t]*=[ \\t]*(\"\"\"[\\s\\S]*?\"\"\"|\"(?:\\\\\"|[^\"])*\"|'(?:\\\\'|[^'])*')[ \\t]*\\r?\\n?").matcher(this.r).replaceAll("");
            HashMap hashMap = new HashMap();
            this.q = hashMap;
            hashMap.put("role", "assistant");
            this.q.put("content", this.r);
            this.K.add(this.q);
            Pattern compile = Pattern.compile("```(?:py|python)[ \\t]*\\r?\\n(.*?)```", 34);
            Pattern compile2 = Pattern.compile("```diff[ \\t]*\\r?\\n(.*?)```", 34);
            Matcher matcher = compile.matcher(this.r);
            Matcher matcher2 = compile2.matcher(this.r);
            if (matcher.find()) {
                this.d0.setText(matcher.group(1));
            } else if (matcher2.find()) {
                String group = matcher2.group(1);
                List asList = Arrays.asList(this.d0.getText().toString().split("\\r?\\n", -1));
                try {
                    Iterator it = UnifiedDiffReader.parseUnifiedDiff(new ByteArrayInputStream(group.getBytes(StandardCharsets.UTF_8))).getFiles().iterator();
                    while (it.hasNext()) {
                        asList = DiffUtils.patch(asList, ((UnifiedDiffFile) it.next()).getPatch());
                    }
                    this.d0.setText(TextUtils.join("\n", asList));
                    d.b(getApplicationContext(), "Успешно пропатчено!");
                } catch (Exception e) {
                    d.b(getApplicationContext(), "Ошибка при применении патча: " + e.getMessage());
                }
            } else {
                this.d0.setText(this.r);
            }
            if (this.B) {
                d.b(getApplicationContext(), getString(2131689619));
            }
        } catch (Exception unused2) {
            d.b(getApplicationContext(), getString(2131689529));
            ArrayList arrayList = this.K;
            arrayList.remove(arrayList.size() - 1);
            f0(this.s);
        }
        this.t.dismiss();
    }

    public void j0(String str) {
        if (!d.a(getApplicationContext())) {
            d.b(getApplicationContext(), "Нет соединения!");
            return;
        }
        this.t.show();
        this.J = (ArrayList) new Gson().fromJson("[{\"type\": \"web_search\"}]", new s1(this).getType());
        HashMap hashMap = new HashMap();
        this.q = hashMap;
        hashMap.put("role", "user");
        this.s = str;
        this.q.put("content", str.concat("\n\nКод:\n```py".concat(this.d0.getText().toString().concat("\n```"))));
        this.K.add(this.q);
        if (20 < this.K.size()) {
            this.K.remove(0);
            this.K.remove(0);
        }
        this.o = new HashMap();
        if (!this.S0.getString("api", "").equals("")) {
            this.o.put("base_url", this.S0.getString("api", ""));
        }
        this.o.put("messages", this.K);
        if (this.S0.getString("model", "").equals("")) {
            this.o.put("model", "grok");
            this.o.put("api_key", this.S0.getString("apiKey", "").trim());
        } else {
            this.o.put("model", this.S0.getString("model", ""));
            this.o.put("api_key", this.S0.getString("apiKeyG", "").trim());
        }
        this.o.put("max_tokens", 128000);
        this.D = 0.0d;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        OkHttpClient build = builder.connectTimeout(10L, timeUnit).writeTimeout(10L, timeUnit).readTimeout(10L, timeUnit).build();
        Request.Builder post = new Request.Builder().url("https://api.neurix.ru/plugin/pide/chat").addHeader("Accept", "application/json").post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(this.o)));
        String str2 = this.C;
        if (str2 != null && !str2.trim().isEmpty()) {
            post.addHeader("Authorization", "Bearer " + this.C);
        }
        build.newCall(post.build()).enqueue(new w1(this));
    }

    public void k0(String str) {
        this.z.execute(new b2(this, str, new PrecomputedTextCompat.Params.Builder(this.d0.getPaint()).setBreakStrategy(1).setHyphenationFrequency(1).build()));
        this.C0.setText(((HashMap) this.H.get((int) this.i)).get("plugin_name").toString().concat(" • ".concat(((HashMap) this.H.get((int) this.i)).get("plugin_author").toString())));
    }

    public void l0() {
        this.d0.setUndoStack(new UndoStack());
        this.d0.setRedoStack(new UndoStack());
        this.K.clear();
    }

    public void onBackPressed() {
        if (this.w.equals(this.d0.getText().toString())) {
            finish();
            overridePendingTransition(17432576, 17432577);
            return;
        }
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Не сохранено!");
        materialAlertDialogBuilder.setIcon(2131165320);
        materialAlertDialogBuilder.setMessage("Вы не сохранили изменения. Хотите сохранить?");
        materialAlertDialogBuilder.setPositiveButton(getString(2131689628), new p1(this));
        materialAlertDialogBuilder.setNegativeButton("Отмена", new q1(this));
        materialAlertDialogBuilder.setNeutralButton("Выйти", new r1(this));
        materialAlertDialogBuilder.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427380);
        n0(bundle);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == -1 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
        } else {
            o0();
        }
    }

    public void onPause() {
        super.onPause();
        this.B = true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            o0();
        }
    }

    public void onResume() {
        super.onResume();
        this.B = false;
        try {
            String l = a.l(a.f(getApplicationContext()).concat("/list"));
            this.g = l;
            if (!l.trim().equals("")) {
                this.G = (ArrayList) new Gson().fromJson(this.g, new m1(this).getType());
            }
            Collections.reverse(this.G);
            this.f = (HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")));
            ArrayList arrayList = (ArrayList) new Gson().fromJson(this.f.get("list").toString(), new n1(this).getType());
            this.H = arrayList;
            Collections.reverse(arrayList);
            this.Y.setText(((HashMap) this.G.get((int) Double.parseDouble(getIntent().getStringExtra("position")))).get("plugin_id").toString());
            this.h = 0.0d;
            this.L = (ArrayList) new Gson().fromJson(new Gson().toJson(this.I), new o1(this).getType());
            this.I.clear();
            HashMap hashMap = new HashMap();
            hashMap.put("version", getString(2131689518));
            this.I.add(hashMap);
            for (int i = 0; i < this.H.size(); i++) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("version", ((HashMap) this.H.get((int) this.h)).get("version").toString());
                this.I.add(hashMap2);
                this.h += 1.0d;
            }
            this.X.getAdapter().notifyDataSetChanged();
            if (new Gson().toJson(this.L).equals(new Gson().toJson(this.I))) {
                return;
            }
            this.X.setSelection(1);
            this.X.getAdapter().notifyDataSetChanged();
        } catch (Exception unused) {
        }
    }
}
