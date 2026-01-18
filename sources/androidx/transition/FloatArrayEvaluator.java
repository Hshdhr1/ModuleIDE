package androidx.transition;

import android.animation.TypeEvaluator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class FloatArrayEvaluator implements TypeEvaluator {
    private float[] mArray;

    FloatArrayEvaluator(float[] reuseArray) {
        this.mArray = reuseArray;
    }

    public float[] evaluate(float fraction, float[] startValue, float[] endValue) {
        float[] array = this.mArray;
        if (array == null) {
            array = new float[startValue.length];
        }
        for (int i = 0; i < array.length; i++) {
            float start = startValue[i];
            float end = endValue[i];
            array[i] = ((end - start) * fraction) + start;
        }
        return array;
    }
}
