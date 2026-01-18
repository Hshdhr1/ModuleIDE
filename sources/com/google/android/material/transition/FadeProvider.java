package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class FadeProvider implements VisibilityAnimatorProvider {
    private float incomingEndThreshold = 1.0f;

    public float getIncomingEndThreshold() {
        return this.incomingEndThreshold;
    }

    public void setIncomingEndThreshold(float incomingEndThreshold) {
        this.incomingEndThreshold = incomingEndThreshold;
    }

    public Animator createAppear(ViewGroup sceneRoot, View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, 0.0f, originalAlpha, 0.0f, this.incomingEndThreshold, originalAlpha);
    }

    public Animator createDisappear(ViewGroup sceneRoot, View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, originalAlpha, 0.0f, 0.0f, 1.0f, originalAlpha);
    }

    private static Animator createFadeAnimator(View view, float startValue, float endValue, float startFraction, float endFraction, float originalAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new 1(view, startValue, endValue, startFraction, endFraction));
        animator.addListener(new 2(view, originalAlpha));
        return animator;
    }

    class 1 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ float val$endFraction;
        final /* synthetic */ float val$endValue;
        final /* synthetic */ float val$startFraction;
        final /* synthetic */ float val$startValue;
        final /* synthetic */ View val$view;

        1(View view, float f, float f2, float f3, float f4) {
            this.val$view = view;
            this.val$startValue = f;
            this.val$endValue = f2;
            this.val$startFraction = f3;
            this.val$endFraction = f4;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float progress = ((Float) animation.getAnimatedValue()).floatValue();
            this.val$view.setAlpha(TransitionUtils.lerp(this.val$startValue, this.val$endValue, this.val$startFraction, this.val$endFraction, progress));
        }
    }

    class 2 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$originalAlpha;
        final /* synthetic */ View val$view;

        2(View view, float f) {
            this.val$view = view;
            this.val$originalAlpha = f;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setAlpha(this.val$originalAlpha);
        }
    }
}
