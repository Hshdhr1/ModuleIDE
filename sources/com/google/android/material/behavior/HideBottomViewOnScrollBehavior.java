package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimationUtils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class HideBottomViewOnScrollBehavior extends CoordinatorLayout.Behavior {
    protected static final int ENTER_ANIMATION_DURATION = 225;
    protected static final int EXIT_ANIMATION_DURATION = 175;
    private static final int STATE_SCROLLED_DOWN = 1;
    private static final int STATE_SCROLLED_UP = 2;
    private int additionalHiddenOffsetY;
    private ViewPropertyAnimator currentAnimator;
    private int currentState;
    private int height;

    static /* synthetic */ ViewPropertyAnimator access$002(HideBottomViewOnScrollBehavior x0, ViewPropertyAnimator x1) {
        x0.currentAnimator = x1;
        return x1;
    }

    public HideBottomViewOnScrollBehavior() {
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.height = 0;
        this.currentState = 2;
        this.additionalHiddenOffsetY = 0;
    }

    public boolean onLayoutChild(CoordinatorLayout parent, View view, int layoutDirection) {
        ViewGroup.MarginLayoutParams paramsCompat = view.getLayoutParams();
        this.height = view.getMeasuredHeight() + paramsCompat.bottomMargin;
        return super.onLayoutChild(parent, view, layoutDirection);
    }

    public void setAdditionalHiddenOffsetY(View view, int offset) {
        this.additionalHiddenOffsetY = offset;
        if (this.currentState == 1) {
            view.setTranslationY(this.height + offset);
        }
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes == 2;
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
        if (dyConsumed > 0) {
            slideDown(view);
        } else if (dyConsumed < 0) {
            slideUp(view);
        }
    }

    public boolean isScrolledUp() {
        return this.currentState == 2;
    }

    public void slideUp(View view) {
        slideUp(view, true);
    }

    public void slideUp(View view, boolean animate) {
        if (isScrolledUp()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.currentState = 2;
        if (animate) {
            animateChildTo(view, 0, 225L, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        } else {
            view.setTranslationY(0);
        }
    }

    public boolean isScrolledDown() {
        return this.currentState == 1;
    }

    public void slideDown(View view) {
        slideDown(view, true);
    }

    public void slideDown(View view, boolean animate) {
        if (isScrolledDown()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.currentState = 1;
        int targetTranslationY = this.height + this.additionalHiddenOffsetY;
        if (animate) {
            animateChildTo(view, targetTranslationY, 175L, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        } else {
            view.setTranslationY(targetTranslationY);
        }
    }

    private void animateChildTo(View view, int targetY, long duration, TimeInterpolator interpolator) {
        this.currentAnimator = view.animate().translationY(targetY).setInterpolator(interpolator).setDuration(duration).setListener(new 1());
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationEnd(Animator animation) {
            HideBottomViewOnScrollBehavior.access$002(HideBottomViewOnScrollBehavior.this, null);
        }
    }
}
