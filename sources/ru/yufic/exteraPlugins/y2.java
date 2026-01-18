package ru.yufic.exteraPlugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
class y2 {
    private static volatile /* synthetic */ int[] k;
    float a;
    float b;
    float c;
    float d;
    float e;
    float f;
    List g = new ArrayList();
    int h;
    x2 i;
    Random j;

    private y2() {
    }

    static /* synthetic */ int[] a() {
        int[] iArr = k;
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
        k = iArr2;
        return iArr2;
    }

    static y2 b(x2 x2Var, float f, float f2, float f3, Random random) {
        y2 y2Var = new y2();
        y2Var.i = x2Var;
        y2Var.j = random;
        int i = a()[x2Var.ordinal()];
        if (i == 2) {
            y2Var.e = (random.nextFloat() * 5.0f) + 2.0f;
            float nextFloat = random.nextFloat() * 2.0f * 3.1415927f;
            float nextFloat2 = random.nextFloat() * f3;
            double d = nextFloat;
            y2Var.a = f + (((float) Math.cos(d)) * nextFloat2);
            y2Var.b = f2 + (((float) Math.sin(d)) * nextFloat2);
            y2Var.c = (random.nextFloat() - 0.5f) * 0.5f;
            y2Var.d = (random.nextFloat() - 0.5f) * 0.5f;
            y2Var.f = 0.0f;
            return y2Var;
        }
        if (i != 3) {
            y2Var.e = 0.0f;
            y2Var.f = 0.0f;
            return y2Var;
        }
        y2Var.e = (random.nextFloat() * 3.0f) + 2.0f;
        float nextFloat3 = random.nextFloat() * 2.0f * 3.1415927f;
        float nextFloat4 = random.nextFloat() * f3 * 0.1f;
        double d2 = nextFloat3;
        y2Var.a = f + (((float) Math.cos(d2)) * nextFloat4);
        y2Var.b = f2 + (((float) Math.sin(d2)) * nextFloat4);
        float nextFloat5 = ((random.nextFloat() * 200.0f) + 100.0f) / 60.0f;
        y2Var.c = ((float) Math.cos(d2)) * nextFloat5;
        y2Var.d = ((float) Math.sin(d2)) * nextFloat5;
        y2Var.f = 1.0f;
        y2Var.h = random.nextInt(5) + 5;
        return y2Var;
    }

    boolean c(float f, float f2, float f3) {
        int i = a()[this.i.ordinal()];
        if (i == 2) {
            float min = Math.min(1.0f, this.f + 0.01f);
            this.f = min;
            this.a += this.c;
            this.b += this.d;
            return ((double) min) > 0.1d;
        }
        if (i != 3) {
            return false;
        }
        this.g.add(new float[]{this.a, this.b});
        if (this.g.size() > this.h) {
            this.g.remove(0);
        }
        float f4 = this.a + this.c;
        this.a = f4;
        float f5 = this.b + this.d;
        this.b = f5;
        float f6 = this.f * 0.98f;
        this.f = f6;
        float f7 = f4 - f;
        float f8 = f5 - f2;
        return (f7 * f7) + (f8 * f8) < f3 * f3 && f6 > 0.05f;
    }
}
