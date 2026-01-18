package com.google.android.material.animation;

import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.R;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ChildrenAlphaProperty extends Property {
    public static final Property CHILDREN_ALPHA = new ChildrenAlphaProperty("childrenAlpha");

    private ChildrenAlphaProperty(String name) {
        super(Float.class, name);
    }

    public Float get(ViewGroup object) {
        Float alpha = (Float) object.getTag(R.id.mtrl_internal_children_alpha_tag);
        if (alpha != null) {
            return alpha;
        }
        return Float.valueOf(1.0f);
    }

    public void set(ViewGroup object, Float value) {
        float alpha = value.floatValue();
        object.setTag(R.id.mtrl_internal_children_alpha_tag, Float.valueOf(alpha));
        int count = object.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = object.getChildAt(i);
            child.setAlpha(alpha);
        }
    }
}
