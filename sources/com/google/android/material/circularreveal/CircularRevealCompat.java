package com.google.android.material.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.google.android.material.circularreveal.CircularRevealWidget;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class CircularRevealCompat {
    private CircularRevealCompat() {
    }

    public static Animator createCircularReveal(CircularRevealWidget view, float centerX, float centerY, float endRadius) {
        Animator revealInfoAnimator = ObjectAnimator.ofObject(view, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[]{new CircularRevealWidget.RevealInfo(centerX, centerY, endRadius)});
        if (Build.VERSION.SDK_INT >= 21) {
            CircularRevealWidget.RevealInfo revealInfo = view.getRevealInfo();
            if (revealInfo == null) {
                throw new IllegalStateException("Caller must set a non-null RevealInfo before calling this.");
            }
            float startRadius = revealInfo.radius;
            Animator circularRevealAnimator = ViewAnimationUtils.createCircularReveal((View) view, (int) centerX, (int) centerY, startRadius, endRadius);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(new Animator[]{revealInfoAnimator, circularRevealAnimator});
            return set;
        }
        return revealInfoAnimator;
    }

    public static Animator createCircularReveal(CircularRevealWidget view, float centerX, float centerY, float startRadius, float endRadius) {
        Animator revealInfoAnimator = ObjectAnimator.ofObject(view, CircularRevealWidget.CircularRevealProperty.CIRCULAR_REVEAL, CircularRevealWidget.CircularRevealEvaluator.CIRCULAR_REVEAL, new CircularRevealWidget.RevealInfo[]{new CircularRevealWidget.RevealInfo(centerX, centerY, startRadius), new CircularRevealWidget.RevealInfo(centerX, centerY, endRadius)});
        if (Build.VERSION.SDK_INT >= 21) {
            Animator circularRevealAnimator = ViewAnimationUtils.createCircularReveal((View) view, (int) centerX, (int) centerY, startRadius, endRadius);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(new Animator[]{revealInfoAnimator, circularRevealAnimator});
            return set;
        }
        return revealInfoAnimator;
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ CircularRevealWidget val$view;

        1(CircularRevealWidget circularRevealWidget) {
            this.val$view = circularRevealWidget;
        }

        public void onAnimationStart(Animator animation) {
            this.val$view.buildCircularRevealCache();
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.destroyCircularRevealCache();
        }
    }

    public static Animator.AnimatorListener createCircularRevealListener(CircularRevealWidget view) {
        return new 1(view);
    }
}
