package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class ExpandableTransformationBehavior extends ExpandableBehavior {
    private AnimatorSet currentAnimation;

    protected abstract AnimatorSet onCreateExpandedStateChangeAnimation(View view, View view2, boolean z, boolean z2);

    static /* synthetic */ AnimatorSet access$002(ExpandableTransformationBehavior x0, AnimatorSet x1) {
        x0.currentAnimation = x1;
        return x1;
    }

    public ExpandableTransformationBehavior() {
    }

    public ExpandableTransformationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected boolean onExpandedStateChange(View dependency, View child, boolean expanded, boolean animated) {
        AnimatorSet animatorSet = this.currentAnimation;
        boolean currentlyAnimating = animatorSet != null;
        if (currentlyAnimating) {
            animatorSet.cancel();
        }
        AnimatorSet onCreateExpandedStateChangeAnimation = onCreateExpandedStateChangeAnimation(dependency, child, expanded, currentlyAnimating);
        this.currentAnimation = onCreateExpandedStateChangeAnimation;
        onCreateExpandedStateChangeAnimation.addListener(new 1());
        this.currentAnimation.start();
        if (!animated) {
            this.currentAnimation.end();
        }
        return true;
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationEnd(Animator animation) {
            ExpandableTransformationBehavior.access$002(ExpandableTransformationBehavior.this, null);
        }
    }
}
