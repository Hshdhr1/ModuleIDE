package ru.yufic.exteraPlugins;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class FastWavyCirclesView extends View {
    private static volatile /* synthetic */ int[] p;
    private final float a;
    private final float b;
    private final Paint c;
    private final Path[] d;
    private final float[][] e;
    private final float[][] f;
    private final float[][] g;
    private final float[] h;
    private final float[] i;
    private LinearGradient j;
    private final long k;
    private x2 l;
    private long m;
    private Random n;
    private List o;

    public FastWavyCirclesView(Context context) {
        super(context);
        this.l = x2.WAVES;
        this.n = new Random();
        this.o = new ArrayList();
        this.k = SystemClock.elapsedRealtime();
        float f = getResources().getDisplayMetrics().density;
        this.a = 20.0f * f;
        float f2 = f * 3.0f;
        this.b = f2;
        Paint paint = new Paint(1);
        this.c = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(f2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.d = new Path[3];
        Class cls = Float.TYPE;
        this.e = (float[][]) Array.newInstance(cls, new int[]{3, 5});
        this.f = (float[][]) Array.newInstance(cls, new int[]{3, 5});
        this.g = (float[][]) Array.newInstance(cls, new int[]{3, 5});
        this.h = new float[3];
        this.i = new float[3];
        e();
        f();
    }

    static /* synthetic */ int[] a() {
        int[] iArr = p;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[x2.valuesCustom().length];
        try {
            iArr2[x2.FLOATING_DOTS.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[x2.STARFALL.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[x2.WAVES.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        p = iArr2;
        return iArr2;
    }

    private void b() {
        int i = a()[this.l.ordinal()];
        if (i == 1) {
            this.l = x2.FLOATING_DOTS;
        } else if (i == 2) {
            this.l = x2.STARFALL;
        } else if (i == 3) {
            this.l = x2.WAVES;
        }
        this.o.clear();
    }

    private void c(Canvas canvas, float f, float f2, float f3, int i, float f4) {
        int i2;
        Path path = this.d[i];
        path.reset();
        float f5 = 2.0f;
        float f6 = 3.1415927f;
        float f7 = (this.h[i] * f4 * 2.0f * 3.1415927f) + this.i[i];
        int i3 = 0;
        float f8 = 0.0f;
        while (true) {
            i2 = 5;
            if (i3 >= 5) {
                break;
            }
            f8 = (float) (f8 + Math.sin((this.e[i][i3] * f7) + this.g[i][i3] + (this.f[i][i3] * f4)));
            i3++;
            f5 = 2.0f;
            f6 = 3.1415927f;
        }
        float f9 = 5.0f;
        float f10 = f3 + ((f8 / 5.0f) * this.a);
        double d = f7;
        float cos = f + (((float) Math.cos(d)) * f10);
        float sin = f2 + (f10 * ((float) Math.sin(d)));
        path.moveTo(cos, sin);
        float f11 = sin;
        float f12 = cos;
        int i4 = 1;
        while (i4 <= 360) {
            float f13 = ((i4 / 360.0f) * f5 * f6) + f7;
            int i5 = 0;
            float f14 = 0.0f;
            while (i5 < i2) {
                f14 = (float) (f14 + Math.sin((this.e[i][i5] * f13) + this.g[i][i5] + (this.f[i][i5] * f4)));
                i5++;
                i2 = 5;
                f9 = 5.0f;
            }
            float f15 = f3 + ((f14 / f9) * this.a);
            double d2 = f13;
            float cos2 = f + (((float) Math.cos(d2)) * f15);
            float sin2 = f2 + (f15 * ((float) Math.sin(d2)));
            path.quadTo(f12, f11, (f12 + cos2) / 2.0f, (f11 + sin2) / 2.0f);
            i4++;
            f12 = cos2;
            f11 = sin2;
            f5 = 2.0f;
            f6 = 3.1415927f;
            i2 = 5;
            f9 = 5.0f;
        }
        path.quadTo(f12, f11, (cos + f12) / f5, (sin + f11) / f5);
        path.close();
        canvas.drawPath(path, this.c);
    }

    private void d(Canvas canvas) {
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        for (y2 y2Var : this.o) {
            paint.setAlpha((int) (y2Var.f * 255.0f));
            canvas.drawCircle(y2Var.a, y2Var.b, y2Var.e, paint);
            if (this.l == x2.STARFALL) {
                int i = 0;
                while (i < y2Var.g.size() - 1) {
                    float[] fArr = (float[]) y2Var.g.get(i);
                    int i2 = i + 1;
                    float[] fArr2 = (float[]) y2Var.g.get(i2);
                    paint.setStrokeWidth(y2Var.e * ((y2Var.g.size() - i) / y2Var.g.size()));
                    canvas.drawLine(fArr[0], fArr[1], fArr2[0], fArr2[1], paint);
                    i = i2;
                }
            }
        }
    }

    private void e() {
        for (int i = 0; i < 3; i++) {
            this.d[i] = new Path();
            for (int i2 = 0; i2 < 5; i2++) {
                this.e[i][i2] = (this.n.nextFloat() * 5.0f) + 1.0f;
                this.f[i][i2] = (this.n.nextFloat() * 0.8f) + 0.2f;
                this.g[i][i2] = this.n.nextFloat() * 2.0f * 3.1415927f;
            }
            this.h[i] = (this.n.nextFloat() * 0.4f) + 0.1f;
            this.i[i] = this.n.nextFloat() * 2.0f * 3.1415927f;
        }
    }

    private void f() {
        this.m = SystemClock.elapsedRealtime() + this.n.nextInt(5000) + 3000;
    }

    private void g(float f, float f2, float f3) {
        if (this.o.size() < 200 && this.n.nextFloat() < 0.05d) {
            this.o.add(y2.b(this.l, f, f2, f3, this.n));
        }
        ArrayList arrayList = new ArrayList();
        for (y2 y2Var : this.o) {
            if (y2Var.c(f, f2, f3)) {
                arrayList.add(y2Var);
            }
        }
        this.o = arrayList;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (SystemClock.elapsedRealtime() >= this.m) {
            b();
            f();
        }
        float width = getWidth() * 0.5f;
        float height = getHeight() * 0.5f;
        float min = Math.min(width, height) * 0.9f;
        float f = min / 4.0f;
        float f2 = (r0 - this.k) / 1000.0f;
        int i = 0;
        while (i < 3) {
            int i2 = i + 1;
            c(canvas, width, height, f * i2, i, f2);
            i = i2;
        }
        g(width, height, min);
        d(canvas);
        postInvalidateOnAnimation();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, i2, new int[]{ContextCompat.getColor(getContext(), 2131034210), ContextCompat.getColor(getContext(), 2131034211)}, (float[]) null, Shader.TileMode.CLAMP);
        this.j = linearGradient;
        this.c.setShader(linearGradient);
    }

    public FastWavyCirclesView(Context context, AttributeSet attributeSet) {
        this(context);
    }
}
