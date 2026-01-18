package androidx.dynamicanimation.animation;

import android.util.FloatProperty;
import androidx.annotation.RequiresApi;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes41.dex */
public abstract class FloatPropertyCompat {
    final String mPropertyName;

    public abstract float getValue(Object obj);

    public abstract void setValue(Object obj, float f);

    public FloatPropertyCompat(String str) {
        this.mPropertyName = str;
    }

    static class 1 extends FloatPropertyCompat {
        final /* synthetic */ FloatProperty val$property;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(String str, FloatProperty floatProperty) {
            super(str);
            this.val$property = floatProperty;
        }

        public float getValue(Object obj) {
            return ((Float) this.val$property.get(obj)).floatValue();
        }

        public void setValue(Object obj, float f) {
            this.val$property.setValue(obj, f);
        }
    }

    @RequiresApi(24)
    public static FloatPropertyCompat createFloatPropertyCompat(FloatProperty floatProperty) {
        return new 1(floatProperty.getName(), floatProperty);
    }
}
