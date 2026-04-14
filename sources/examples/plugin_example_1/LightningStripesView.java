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

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class LightningStripesView extends View {
    private float a;
    private float b;
    private float c;
    private Paint d;
    private Path[] e;
    private float[] f;
    private LinearGradient g;
    private float h;
    private float i;
    private float j;
    private long k;

    public LightningStripesView(Context context) {
        super(context);
        a();
    }

    private void a() {
        this.k = SystemClock.elapsedRealtime();
        float f = getResources().getDisplayMetrics().density;
        this.c = 3.0f * f;
        this.a = 18.0f * f;
        this.b = f * 220.0f;
        Paint paint = new Paint(1);
        this.d = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.d.setStrokeWidth(this.c);
        this.e = new Path[10];
        this.f = new float[10];
        for (int i = 0; i < 10; i++) {
            this.e[i] = new Path();
            this.f[i] = (float) (Math.random() * 2.0d * 3.141592653589793d);
        }
        this.h = (float) (Math.random() * 2.0d * 3.141592653589793d);
        this.i = (float) (Math.random() * 2.0d * 3.141592653589793d);
        this.j = (float) (Math.random() * 2.0d * 3.141592653589793d);
    }

    protected void onDraw(Canvas canvas) {
        LightningStripesView lightningStripesView = this;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        int width = lightningStripesView.getWidth();
        int height = lightningStripesView.getHeight();
        float f = width;
        float f2 = f / 11.0f;
        float elapsedRealtime = (SystemClock.elapsedRealtime() - lightningStripesView.k) / 1000.0f;
        float f3 = (0.8f * elapsedRealtime) + lightningStripesView.j;
        double d = elapsedRealtime;
        float sin = ((((float) Math.sin((0.3141592700403172d * d) + lightningStripesView.h)) * 0.6f) + (((float) Math.sin((d * 0.6911503800446842d) + lightningStripesView.i)) * 0.4f)) * 10.0f;
        canvas2.save();
        int i = 0;
        canvas2.clipRect(0, 0, width, height);
        float f4 = f * 0.5f;
        float f5 = height;
        float f6 = f5 * 0.5f;
        canvas2.scale(1.1f, 1.1f, f4, f6);
        canvas2.rotate(sin, f4, f6);
        while (i < 10) {
            Path path = lightningStripesView.e[i];
            path.reset();
            int i2 = i + 1;
            float f7 = i2 * f2;
            path.moveTo(f7, f5);
            float f8 = height - 12;
            float f9 = f5;
            float f10 = f7;
            while (f8 >= 0.0f) {
                double d2 = f3;
                float sin2 = ((float) (Math.sin((((f5 - f8) / lightningStripesView.b) * 2.0f * 3.141592653589793d) + d2) * lightningStripesView.a * ((Math.sin((d2 * 1.3d) + lightningStripesView.f[i]) * 0.4d) + 1.0d))) + f7;
                path.quadTo(f10, f9, (f10 + sin2) * 0.5f, (f9 + f8) * 0.5f);
                f10 = sin2;
                f9 = f8;
                height = height;
                lightningStripesView = this;
                f8 -= 12.0f;
                canvas2 = canvas;
            }
            path.lineTo(f10, 0.0f);
            canvas2.drawPath(path, lightningStripesView.d);
            i = i2;
        }
        canvas2.restore();
        lightningStripesView.postInvalidateOnAnimation();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, i2, new int[]{ContextCompat.getColor(getContext(), 2131034210), ContextCompat.getColor(getContext(), 2131034211)}, (float[]) null, Shader.TileMode.CLAMP);
        this.g = linearGradient;
        this.d.setShader(linearGradient);
    }

    public LightningStripesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }
}
