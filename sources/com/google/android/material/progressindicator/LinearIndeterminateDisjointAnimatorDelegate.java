package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class LinearIndeterminateDisjointAnimatorDelegate extends IndeterminateAnimatorDelegate {
    private static final int TOTAL_DURATION_IN_MS = 1800;
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat.AnimationCallback animatorCompleteCallback;
    private final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    private boolean dirtyColors;
    private int indicatorColorIndex;
    private final Interpolator[] interpolatorArray;
    private static final int[] DURATION_TO_MOVE_SEGMENT_ENDS = {533, 567, 850, 750};
    private static final int[] DELAY_TO_MOVE_SEGMENT_ENDS = {1267, 1000, 333, 0};
    private static final Property ANIMATION_FRACTION = new 3(Float.class, "animationFraction");

    static /* synthetic */ int access$000(LinearIndeterminateDisjointAnimatorDelegate x0) {
        return x0.indicatorColorIndex;
    }

    static /* synthetic */ int access$002(LinearIndeterminateDisjointAnimatorDelegate x0, int x1) {
        x0.indicatorColorIndex = x1;
        return x1;
    }

    static /* synthetic */ BaseProgressIndicatorSpec access$100(LinearIndeterminateDisjointAnimatorDelegate x0) {
        return x0.baseSpec;
    }

    static /* synthetic */ boolean access$202(LinearIndeterminateDisjointAnimatorDelegate x0, boolean x1) {
        x0.dirtyColors = x1;
        return x1;
    }

    static /* synthetic */ float access$300(LinearIndeterminateDisjointAnimatorDelegate x0) {
        return x0.getAnimationFraction();
    }

    public LinearIndeterminateDisjointAnimatorDelegate(Context context, LinearProgressIndicatorSpec spec) {
        super(2);
        this.indicatorColorIndex = 0;
        this.animatorCompleteCallback = null;
        this.baseSpec = spec;
        this.interpolatorArray = new Interpolator[]{AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line1_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line1_tail_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line2_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line2_tail_interpolator)};
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
            ofFloat.setDuration(1800L);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new 1());
        }
        if (this.completeEndAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{1.0f});
            this.completeEndAnimator = ofFloat2;
            ofFloat2.setDuration(1800L);
            this.completeEndAnimator.setInterpolator((TimeInterpolator) null);
            this.completeEndAnimator.addListener(new 2());
        }
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationRepeat(Animator animation) {
            super.onAnimationRepeat(animation);
            LinearIndeterminateDisjointAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate = LinearIndeterminateDisjointAnimatorDelegate.this;
            LinearIndeterminateDisjointAnimatorDelegate.access$002(linearIndeterminateDisjointAnimatorDelegate, (LinearIndeterminateDisjointAnimatorDelegate.access$000(linearIndeterminateDisjointAnimatorDelegate) + 1) % LinearIndeterminateDisjointAnimatorDelegate.access$100(LinearIndeterminateDisjointAnimatorDelegate.this).indicatorColors.length);
            LinearIndeterminateDisjointAnimatorDelegate.access$202(LinearIndeterminateDisjointAnimatorDelegate.this, true);
        }
    }

    class 2 extends AnimatorListenerAdapter {
        2() {
        }

        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            LinearIndeterminateDisjointAnimatorDelegate.this.cancelAnimatorImmediately();
            if (LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback != null) {
                LinearIndeterminateDisjointAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(LinearIndeterminateDisjointAnimatorDelegate.this.drawable);
            }
        }
    }

    public void cancelAnimatorImmediately() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void requestCancelAnimatorAfterCurrentCycle() {
        ObjectAnimator objectAnimator = this.completeEndAnimator;
        if (objectAnimator == null || objectAnimator.isRunning()) {
            return;
        }
        cancelAnimatorImmediately();
        if (this.drawable.isVisible()) {
            this.completeEndAnimator.setFloatValues(new float[]{this.animationFraction, 1.0f});
            this.completeEndAnimator.setDuration((long) ((1.0f - this.animationFraction) * 1800.0f));
            this.completeEndAnimator.start();
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
        for (int i = 0; i < 4; i++) {
            float fraction = getFractionInRange(playtime, DELAY_TO_MOVE_SEGMENT_ENDS[i], DURATION_TO_MOVE_SEGMENT_ENDS[i]);
            float segmentPosition = this.interpolatorArray[i].getInterpolation(fraction);
            this.segmentPositions[i] = Math.max(0.0f, Math.min(1.0f, segmentPosition));
        }
    }

    private void maybeUpdateSegmentColors() {
        if (this.dirtyColors) {
            Arrays.fill(this.segmentColors, MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[this.indicatorColorIndex], this.drawable.getAlpha()));
            this.dirtyColors = false;
        }
    }

    void resetPropertiesForNewStart() {
        this.indicatorColorIndex = 0;
        int indicatorColor = MaterialColors.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.drawable.getAlpha());
        this.segmentColors[0] = indicatorColor;
        this.segmentColors[1] = indicatorColor;
    }

    private float getAnimationFraction() {
        return this.animationFraction;
    }

    void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        int playtime = (int) (1800.0f * fraction);
        updateSegmentPositions(playtime);
        maybeUpdateSegmentColors();
        this.drawable.invalidateSelf();
    }

    class 3 extends Property {
        3(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(LinearIndeterminateDisjointAnimatorDelegate delegate) {
            return Float.valueOf(LinearIndeterminateDisjointAnimatorDelegate.access$300(delegate));
        }

        public void set(LinearIndeterminateDisjointAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    }
}
