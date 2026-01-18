package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.os.Build;
import android.util.Property;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class ObjectAnimatorUtils {
    static ObjectAnimator ofPointF(Object obj, Property property, Path path) {
        return Build.VERSION.SDK_INT >= 21 ? ObjectAnimator.ofObject(obj, property, (TypeConverter) null, path) : ObjectAnimator.ofFloat(obj, new PathProperty(property, path), new float[]{0.0f, 1.0f});
    }

    private ObjectAnimatorUtils() {
    }
}
