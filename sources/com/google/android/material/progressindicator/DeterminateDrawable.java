package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class DeterminateDrawable extends DrawableWithAnimatedVisibilityChange {
    private static final FloatPropertyCompat INDICATOR_LENGTH_IN_LEVEL = new 1("indicatorLevel");
    private static final int MAX_DRAWABLE_LEVEL = 10000;
    private static final float SPRING_FORCE_STIFFNESS = 50.0f;
    private DrawingDelegate drawingDelegate;
    private float indicatorFraction;
    private boolean skipAnimationOnLevelChange;
    private final SpringAnimation springAnimation;
    private final SpringForce springForce;

    static /* synthetic */ float access$000(DeterminateDrawable x0) {
        return x0.getIndicatorFraction();
    }

    static /* synthetic */ void access$100(DeterminateDrawable x0, float x1) {
        x0.setIndicatorFraction(x1);
    }

    public /* bridge */ /* synthetic */ void clearAnimationCallbacks() {
        super.clearAnimationCallbacks();
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    public /* bridge */ /* synthetic */ boolean isHiding() {
        return super.isHiding();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ boolean isShowing() {
        return super.isShowing();
    }

    public /* bridge */ /* synthetic */ void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        super.registerAnimationCallback(animationCallback);
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2, boolean z3) {
        return super.setVisible(z, z2, z3);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        return super.unregisterAnimationCallback(animationCallback);
    }

    DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseSpec, DrawingDelegate drawingDelegate) {
        super(context, baseSpec);
        this.skipAnimationOnLevelChange = false;
        setDrawingDelegate(drawingDelegate);
        SpringForce springForce = new SpringForce();
        this.springForce = springForce;
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(50.0f);
        SpringAnimation springAnimation = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation = springAnimation;
        springAnimation.setSpring(springForce);
        setGrowFraction(1.0f);
    }

    public static DeterminateDrawable createLinearDrawable(Context context, LinearProgressIndicatorSpec spec) {
        return new DeterminateDrawable(context, spec, new LinearDrawingDelegate(spec));
    }

    public static DeterminateDrawable createCircularDrawable(Context context, CircularProgressIndicatorSpec spec) {
        return new DeterminateDrawable(context, spec, new CircularDrawingDelegate(spec));
    }

    public void addSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.addEndListener(listener);
    }

    public void removeSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.removeEndListener(listener);
    }

    boolean setVisibleInternal(boolean visible, boolean restart, boolean animate) {
        boolean changed = super.setVisibleInternal(visible, restart, animate);
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / systemAnimatorDurationScale);
        }
        return changed;
    }

    public void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        setIndicatorFraction(getLevel() / 10000.0f);
    }

    protected boolean onLevelChange(int level) {
        if (this.skipAnimationOnLevelChange) {
            this.springAnimation.skipToEnd();
            setIndicatorFraction(level / 10000.0f);
            return true;
        }
        this.springAnimation.setStartValue(getIndicatorFraction() * 10000.0f);
        this.springAnimation.animateToFinalPosition(level);
        return true;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    void setLevelByFraction(float fraction) {
        setLevel((int) (10000.0f * fraction));
    }

    public void draw(Canvas canvas) {
        Rect clipBounds = new Rect();
        if (getBounds().isEmpty() || !isVisible() || !canvas.getClipBounds(clipBounds)) {
            return;
        }
        canvas.save();
        this.drawingDelegate.validateSpecAndAdjustCanvas(canvas, getGrowFraction());
        this.drawingDelegate.fillTrack(canvas, this.paint);
        int indicatorColor = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], getAlpha());
        this.drawingDelegate.fillIndicator(canvas, this.paint, 0.0f, getIndicatorFraction(), indicatorColor);
        canvas.restore();
    }

    private float getIndicatorFraction() {
        return this.indicatorFraction;
    }

    private void setIndicatorFraction(float indicatorFraction) {
        this.indicatorFraction = indicatorFraction;
        invalidateSelf();
    }

    DrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }

    void setDrawingDelegate(DrawingDelegate drawingDelegate) {
        this.drawingDelegate = drawingDelegate;
        drawingDelegate.registerDrawable(this);
    }

    class 1 extends FloatPropertyCompat {
        1(String arg0) {
            super(arg0);
        }

        public float getValue(DeterminateDrawable drawable) {
            return DeterminateDrawable.access$000(drawable) * 10000.0f;
        }

        public void setValue(DeterminateDrawable drawable, float value) {
            DeterminateDrawable.access$100(drawable, value / 10000.0f);
        }
    }
}
