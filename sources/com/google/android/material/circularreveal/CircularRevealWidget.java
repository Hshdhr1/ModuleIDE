package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.math.MathUtils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public interface CircularRevealWidget extends CircularRevealHelper.Delegate {
    void buildCircularRevealCache();

    void destroyCircularRevealCache();

    void draw(Canvas canvas);

    Drawable getCircularRevealOverlayDrawable();

    int getCircularRevealScrimColor();

    RevealInfo getRevealInfo();

    boolean isOpaque();

    void setCircularRevealOverlayDrawable(Drawable drawable);

    void setCircularRevealScrimColor(int i);

    void setRevealInfo(RevealInfo revealInfo);

    public static class RevealInfo {
        public static final float INVALID_RADIUS = Float.MAX_VALUE;
        public float centerX;
        public float centerY;
        public float radius;

        /* synthetic */ RevealInfo(1 x0) {
            this();
        }

        private RevealInfo() {
        }

        public RevealInfo(float centerX, float centerY, float radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }

        public RevealInfo(RevealInfo other) {
            this(other.centerX, other.centerY, other.radius);
        }

        public void set(float centerX, float centerY, float radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }

        public void set(RevealInfo other) {
            set(other.centerX, other.centerY, other.radius);
        }

        public boolean isInvalid() {
            return this.radius == Float.MAX_VALUE;
        }
    }

    public static class CircularRevealProperty extends Property {
        public static final Property CIRCULAR_REVEAL = new CircularRevealProperty("circularReveal");

        private CircularRevealProperty(String name) {
            super(RevealInfo.class, name);
        }

        public RevealInfo get(CircularRevealWidget object) {
            return object.getRevealInfo();
        }

        public void set(CircularRevealWidget object, RevealInfo value) {
            object.setRevealInfo(value);
        }
    }

    public static class CircularRevealEvaluator implements TypeEvaluator {
        public static final TypeEvaluator CIRCULAR_REVEAL = new CircularRevealEvaluator();
        private final RevealInfo revealInfo = new RevealInfo((1) null);

        public RevealInfo evaluate(float fraction, RevealInfo startValue, RevealInfo endValue) {
            this.revealInfo.set(MathUtils.lerp(startValue.centerX, endValue.centerX, fraction), MathUtils.lerp(startValue.centerY, endValue.centerY, fraction), MathUtils.lerp(startValue.radius, endValue.radius, fraction));
            return this.revealInfo;
        }
    }

    public static class CircularRevealScrimColorProperty extends Property {
        public static final Property CIRCULAR_REVEAL_SCRIM_COLOR = new CircularRevealScrimColorProperty("circularRevealScrimColor");

        private CircularRevealScrimColorProperty(String name) {
            super(Integer.class, name);
        }

        public Integer get(CircularRevealWidget object) {
            return Integer.valueOf(object.getCircularRevealScrimColor());
        }

        public void set(CircularRevealWidget object, Integer value) {
            object.setCircularRevealScrimColor(value.intValue());
        }
    }
}
