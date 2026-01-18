package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class TextScale extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            TextView textview = transitionValues.view;
            transitionValues.values.put("android:textscale:scale", Float.valueOf(textview.getScaleX()));
        }
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null || !(startValues.view instanceof TextView) || !(endValues.view instanceof TextView)) {
            return null;
        }
        TextView view = endValues.view;
        Map<String, Object> startVals = startValues.values;
        Map<String, Object> endVals = endValues.values;
        float startSize = startVals.get("android:textscale:scale") != null ? ((Float) startVals.get("android:textscale:scale")).floatValue() : 1.0f;
        float endSize = endVals.get("android:textscale:scale") != null ? ((Float) endVals.get("android:textscale:scale")).floatValue() : 1.0f;
        if (startSize == endSize) {
            return null;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startSize, endSize});
        animator.addUpdateListener(new 1(view));
        return animator;
    }

    class 1 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ TextView val$view;

        1(TextView textView) {
            this.val$view = textView;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.val$view.setScaleX(animatedValue);
            this.val$view.setScaleY(animatedValue);
        }
    }
}
