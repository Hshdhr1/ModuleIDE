package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.color.MaterialColors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class CircularIndeterminateAnimatorDelegate extends IndeterminateAnimatorDelegate {
    private static final int CONSTANT_ROTATION_DEGREES = 1520;
    private static final int DURATION_TO_COLLAPSE_IN_MS = 667;
    private static final int DURATION_TO_COMPLETE_END_IN_MS = 333;
    private static final int DURATION_TO_EXPAND_IN_MS = 667;
    private static final int DURATION_TO_FADE_IN_MS = 333;
    private static final int EXTRA_DEGREES_PER_CYCLE = 250;
    private static final int TAIL_DEGREES_OFFSET = -20;
    private static final int TOTAL_CYCLES = 4;
    private static final int TOTAL_DURATION_IN_MS = 5400;
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat.AnimationCallback animatorCompleteCallback;
    private final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    private float completeEndFraction;
    private int indicatorColorIndexOffset;
    private final FastOutSlowInInterpolator interpolator;
    private static final int[] DELAY_TO_EXPAND_IN_MS = {0, 1350, 2700, 4050};
    private static final int[] DELAY_TO_COLLAPSE_IN_MS = {667, 2017, 3367, 4717};
    private static final int[] DELAY_TO_FADE_IN_MS = {1000, 2350, 3700, 5050};
    private static final Property ANIMATION_FRACTION = new 3(Float.class, "animationFraction");
    private static final Property COMPLETE_END_FRACTION = new 4(Float.class, "completeEndFraction");

    static /* synthetic */ int access$000(CircularIndeterminateAnimatorDelegate x0) {
        return x0.indicatorColorIndexOffset;
    }

    static /* synthetic */ int access$002(CircularIndeterminateAnimatorDelegate x0, int x1) {
        x0.indicatorColorIndexOffset = x1;
        return x1;
    }

    static /* synthetic */ BaseProgressIndicatorSpec access$100(CircularIndeterminateAnimatorDelegate x0) {
        return x0.baseSpec;
    }

    static /* synthetic */ float access$200(CircularIndeterminateAnimatorDelegate x0) {
        return x0.getAnimationFraction();
    }

    static /* synthetic */ float access$300(CircularIndeterminateAnimatorDelegate x0) {
        return x0.getCompleteEndFraction();
    }

    static /* synthetic */ void access$400(CircularIndeterminateAnimatorDelegate x0, float x1) {
        x0.setCompleteEndFraction(x1);
    }

    public CircularIndeterminateAnimatorDelegate(CircularProgressIndicatorSpec spec) {
        super(1);
        this.indicatorColorIndexOffset = 0;
        this.animatorCompleteCallback = null;
        this.baseSpec = spec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(5400L);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new 1());
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, new float[]{0.0f, 1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(333L);
            this.completeEndAnimator.setInterpolator(this.interpolator);
            this.completeEndAnimator.addListener(new 2());
        }
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            CircularIndeterminateAnimatorDelegate circularIndeterminateAnimatorDelegate = CircularIndeterminateAnimatorDelegate.this;
            CircularIndeterminateAnimatorDelegate.access$002(circularIndeterminateAnimatorDelegate, (CircularIndeterminateAnimatorDelegate.access$000(circularIndeterminateAnimatorDelegate) + 4) % CircularIndeterminateAnimatorDelegate.access$100(CircularIndeterminateAnimatorDelegate.this).indicatorColors.length);
        }
    }

    class 2 extends AnimatorListenerAdapter {
        2() {
        }

        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            CircularIndeterminateAnimatorDelegate.this.cancelAnimatorImmediately();
            if (CircularIndeterminateAnimatorDelegate.this.animatorCompleteCallback != null) {
                CircularIndeterminateAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(CircularIndeterminateAnimatorDelegate.this.drawable);
            }
        }
    }

    void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator == null || objectAnimator.isRunning()) {
            return;
        }
        if (this.drawable.isVisible()) {
            this.completeEndAnimator.start();
        } else {
            cancelAnimatorImmediately();
        }
    }

    public void invalidateSpecValues() {
        resetPropertiesForNewStart();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
        this.animatorCompleteCallback = callback;
    }

    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private void updateSegmentPositions(int playtime) {
        this.segmentPositions[0] = (this.animationFraction * 1520.0f) - 20.0f;
        this.segmentPositions[1] = this.animationFraction * 1520.0f;
        for (int cycleIndex = 0; cycleIndex < 4; cycleIndex++) {
            float fraction = getFractionInRange(playtime, DELAY_TO_EXPAND_IN_MS[cycleIndex], 667);
            float[] fArr = this.segmentPositions;
            fArr[1] = fArr[1] + (this.interpolator.getInterpolation(fraction) * 250.0f);
            float fraction2 = getFractionInRange(playtime, DELAY_TO_COLLAPSE_IN_MS[cycleIndex], 667);
            float[] fArr2 = this.segmentPositions;
            fArr2[0] = fArr2[0] + (this.interpolator.getInterpolation(fraction2) * 250.0f);
        }
        float[] fArr3 = this.segmentPositions;
        fArr3[0] = fArr3[0] + ((this.segmentPositions[1] - this.segmentPositions[0]) * this.completeEndFraction);
        float[] fArr4 = this.segmentPositions;
        fArr4[0] = fArr4[0] / 360.0f;
        float[] fArr5 = this.segmentPositions;
        fArr5[1] = fArr5[1] / 360.0f;
    }

    private void maybeUpdateSegmentColors(int playtime) {
        for (int cycleIndex = 0; cycleIndex < 4; cycleIndex++) {
            float timeFraction = getFractionInRange(playtime, DELAY_TO_FADE_IN_MS[cycleIndex], 333);
            if (timeFraction >= 0.0f && timeFraction <= 1.0f) {
                int startColorIndex = (this.indicatorColorIndexOffset + cycleIndex) % this.baseSpec.indicatorColors.length;
                int endColorIndex = (startColorIndex + 1) % this.baseSpec.indicatorColors.length;
                int startColor = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[startColorIndex], this.drawable.getAlpha());
                int endColor = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[endColorIndex], this.drawable.getAlpha());
                float colorFraction = this.interpolator.getInterpolation(timeFraction);
                this.segmentColors[0] = ArgbEvaluatorCompat.getInstance().evaluate(colorFraction, Integer.valueOf(startColor), Integer.valueOf(endColor)).intValue();
                return;
            }
        }
    }

    void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        this.segmentColors[0] = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha());
        this.completeEndFraction = 0.0f;
    }

    private float getAnimationFraction() {
        return this.animationFraction;
    }

    void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        int playtime = (int) (5400.0f * fraction);
        updateSegmentPositions(playtime);
        maybeUpdateSegmentColors(playtime);
        this.drawable.invalidateSelf();
    }

    private float getCompleteEndFraction() {
        return this.completeEndFraction;
    }

    private void setCompleteEndFraction(float fraction) {
        this.completeEndFraction = fraction;
    }

    class 3 extends Property {
        3(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(CircularIndeterminateAnimatorDelegate delegate) {
            return Float.valueOf(CircularIndeterminateAnimatorDelegate.access$200(delegate));
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    }

    class 4 extends Property {
        4(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(CircularIndeterminateAnimatorDelegate delegate) {
            return Float.valueOf(CircularIndeterminateAnimatorDelegate.access$300(delegate));
        }

        public void set(CircularIndeterminateAnimatorDelegate delegate, Float value) {
            CircularIndeterminateAnimatorDelegate.access$400(delegate, value.floatValue());
        }
    }
}
