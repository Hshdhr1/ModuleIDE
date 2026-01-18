package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class ScaleProvider implements VisibilityAnimatorProvider {
    private boolean growing;
    private float incomingEndScale;
    private float incomingStartScale;
    private float outgoingEndScale;
    private float outgoingStartScale;
    private boolean scaleOnDisappear;

    public ScaleProvider() {
        this(true);
    }

    public ScaleProvider(boolean growing) {
        this.outgoingStartScale = 1.0f;
        this.outgoingEndScale = 1.1f;
        this.incomingStartScale = 0.8f;
        this.incomingEndScale = 1.0f;
        this.scaleOnDisappear = true;
        this.growing = growing;
    }

    public boolean isGrowing() {
        return this.growing;
    }

    public void setGrowing(boolean growing) {
        this.growing = growing;
    }

    public boolean isScaleOnDisappear() {
        return this.scaleOnDisappear;
    }

    public void setScaleOnDisappear(boolean scaleOnDisappear) {
        this.scaleOnDisappear = scaleOnDisappear;
    }

    public float getOutgoingStartScale() {
        return this.outgoingStartScale;
    }

    public void setOutgoingStartScale(float outgoingStartScale) {
        this.outgoingStartScale = outgoingStartScale;
    }

    public float getOutgoingEndScale() {
        return this.outgoingEndScale;
    }

    public void setOutgoingEndScale(float outgoingEndScale) {
        this.outgoingEndScale = outgoingEndScale;
    }

    public float getIncomingStartScale() {
        return this.incomingStartScale;
    }

    public void setIncomingStartScale(float incomingStartScale) {
        this.incomingStartScale = incomingStartScale;
    }

    public float getIncomingEndScale() {
        return this.incomingEndScale;
    }

    public void setIncomingEndScale(float incomingEndScale) {
        this.incomingEndScale = incomingEndScale;
    }

    public Animator createAppear(ViewGroup sceneRoot, View view) {
        if (this.growing) {
            return createScaleAnimator(view, this.incomingStartScale, this.incomingEndScale);
        }
        return createScaleAnimator(view, this.outgoingEndScale, this.outgoingStartScale);
    }

    public Animator createDisappear(ViewGroup sceneRoot, View view) {
        if (!this.scaleOnDisappear) {
            return null;
        }
        if (this.growing) {
            return createScaleAnimator(view, this.outgoingStartScale, this.outgoingEndScale);
        }
        return createScaleAnimator(view, this.incomingEndScale, this.incomingStartScale);
    }

    private static Animator createScaleAnimator(View view, float startScale, float endScale) {
        float originalScaleX = view.getScaleX();
        float originalScaleY = view.getScaleY();
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{originalScaleX * startScale, originalScaleX * endScale}), PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{originalScaleY * startScale, originalScaleY * endScale})});
        animator.addListener(new 1(view, originalScaleX, originalScaleY));
        return animator;
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ float val$originalScaleX;
        final /* synthetic */ float val$originalScaleY;
        final /* synthetic */ View val$view;

        1(View view, float f, float f2) {
            this.val$view = view;
            this.val$originalScaleX = f;
            this.val$originalScaleY = f2;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setScaleX(this.val$originalScaleX);
            this.val$view.setScaleY(this.val$originalScaleY);
        }
    }
}
