package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.color.MaterialColors;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class LinearIndeterminateContiguousAnimatorDelegate extends IndeterminateAnimatorDelegate {
    private static final Property ANIMATION_FRACTION = new 2(Float.class, "animationFraction");
    private static final int DURATION_PER_CYCLE_IN_MS = 333;
    private static final int TOTAL_DURATION_IN_MS = 667;
    private float animationFraction;
    private ObjectAnimator animator;
    private final BaseProgressIndicatorSpec baseSpec;
    private boolean dirtyColors;
    private FastOutSlowInInterpolator interpolator;
    private int newIndicatorColorIndex;

    static /* synthetic */ int access$000(LinearIndeterminateContiguousAnimatorDelegate x0) {
        return x0.newIndicatorColorIndex;
    }

    static /* synthetic */ int access$002(LinearIndeterminateContiguousAnimatorDelegate x0, int x1) {
        x0.newIndicatorColorIndex = x1;
        return x1;
    }

    static /* synthetic */ BaseProgressIndicatorSpec access$100(LinearIndeterminateContiguousAnimatorDelegate x0) {
        return x0.baseSpec;
    }

    static /* synthetic */ boolean access$202(LinearIndeterminateContiguousAnimatorDelegate x0, boolean x1) {
        x0.dirtyColors = x1;
        return x1;
    }

    static /* synthetic */ float access$300(LinearIndeterminateContiguousAnimatorDelegate x0) {
        return x0.getAnimationFraction();
    }

    public LinearIndeterminateContiguousAnimatorDelegate(LinearProgressIndicatorSpec spec) {
        super(3);
        this.newIndicatorColorIndex = 1;
        this.baseSpec = spec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator = ofFloat;
            ofFloat.setDuration(333L);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new 1());
        }
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            LinearIndeterminateContiguousAnimatorDelegate linearIndeterminateContiguousAnimatorDelegate = LinearIndeterminateContiguousAnimatorDelegate.this;
            LinearIndeterminateContiguousAnimatorDelegate.access$002(linearIndeterminateContiguousAnimatorDelegate, (LinearIndeterminateContiguousAnimatorDelegate.access$000(linearIndeterminateContiguousAnimatorDelegate) + 1) % LinearIndeterminateContiguousAnimatorDelegate.access$100(LinearIndeterminateContiguousAnimatorDelegate.this).indicatorColors.length);
            LinearIndeterminateContiguousAnimatorDelegate.access$202(LinearIndeterminateContiguousAnimatorDelegate.this, true);
        }
    }

    public void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
    }

    public void invalidateSpecValues() {
        resetPropertiesForNewStart();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
    }

    public void unregisterAnimatorsCompleteCallback() {
    }

    private void updateSegmentPositions(int playtime) {
        this.segmentPositions[0] = 0.0f;
        float fraction = getFractionInRange(playtime, 0, 667);
        float[] fArr = this.segmentPositions;
        float[] fArr2 = this.segmentPositions;
        float interpolation = this.interpolator.getInterpolation(fraction);
        fArr2[2] = interpolation;
        fArr[1] = interpolation;
        float[] fArr3 = this.segmentPositions;
        float[] fArr4 = this.segmentPositions;
        float interpolation2 = this.interpolator.getInterpolation(fraction + 0.49925038f);
        fArr4[4] = interpolation2;
        fArr3[3] = interpolation2;
        this.segmentPositions[5] = 1.0f;
    }

    private void maybeUpdateSegmentColors() {
        if (this.dirtyColors && this.segmentPositions[3] < 1.0f) {
            this.segmentColors[2] = this.segmentColors[1];
            this.segmentColors[1] = this.segmentColors[0];
            this.segmentColors[0] = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[this.newIndicatorColorIndex], this.drawable.getAlpha());
            this.dirtyColors = false;
        }
    }

    void resetPropertiesForNewStart() {
        this.dirtyColors = true;
        this.newIndicatorColorIndex = 1;
        Arrays.fill(this.segmentColors, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha()));
    }

    private float getAnimationFraction() {
        return this.animationFraction;
    }

    void setAnimationFraction(float value) {
        this.animationFraction = value;
        int playtime = (int) (333.0f * value);
        updateSegmentPositions(playtime);
        maybeUpdateSegmentColors();
        this.drawable.invalidateSelf();
    }

    class 2 extends Property {
        2(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(LinearIndeterminateContiguousAnimatorDelegate delegate) {
            return Float.valueOf(LinearIndeterminateContiguousAnimatorDelegate.access$300(delegate));
        }

        public void set(LinearIndeterminateContiguousAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    }
}
