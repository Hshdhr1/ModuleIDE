package com.google.android.material.animation;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Property;
import java.util.WeakHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class DrawableAlphaProperty extends Property {
    public static final Property DRAWABLE_ALPHA_COMPAT = new DrawableAlphaProperty();
    private final WeakHashMap alphaCache;

    private DrawableAlphaProperty() {
        super(Integer.class, "drawableAlphaCompat");
        this.alphaCache = new WeakHashMap();
    }

    public Integer get(Drawable object) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Integer.valueOf(object.getAlpha());
        }
        if (this.alphaCache.containsKey(object)) {
            return (Integer) this.alphaCache.get(object);
        }
        return 255;
    }

    public void set(Drawable object, Integer value) {
        if (Build.VERSION.SDK_INT < 19) {
            this.alphaCache.put(object, value);
        }
        object.setAlpha(value.intValue());
    }
}
