package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class FabTransformationScrimBehavior extends ExpandableTransformationBehavior {
    public static final long COLLAPSE_DELAY = 0;
    public static final long COLLAPSE_DURATION = 150;
    public static final long EXPAND_DELAY = 75;
    public static final long EXPAND_DURATION = 150;
    private final MotionTiming collapseTiming;
    private final MotionTiming expandTiming;

    public FabTransformationScrimBehavior() {
        this.expandTiming = new MotionTiming(75L, 150L);
        this.collapseTiming = new MotionTiming(0L, 150L);
    }

    public FabTransformationScrimBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.expandTiming = new MotionTiming(75L, 150L);
        this.collapseTiming = new MotionTiming(0L, 150L);
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof FloatingActionButton;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    protected AnimatorSet onCreateExpandedStateChangeAnimation(View dependency, View child, boolean expanded, boolean isAnimating) {
        ArrayList arrayList = new ArrayList();
        createScrimAnimation(child, expanded, isAnimating, arrayList, new ArrayList());
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, arrayList);
        set.addListener(new 1(expanded, child));
        return set;
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$child;
        final /* synthetic */ boolean val$expanded;

        1(boolean z, View view) {
            this.val$expanded = z;
            this.val$child = view;
        }

        public void onAnimationStart(Animator animation) {
            if (this.val$expanded) {
                this.val$child.setVisibility(0);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.val$expanded) {
                this.val$child.setVisibility(4);
            }
        }
    }

    private void createScrimAnimation(View child, boolean expanded, boolean currentlyAnimating, List list, List list2) {
        ObjectAnimator ofFloat;
        MotionTiming timing = expanded ? this.expandTiming : this.collapseTiming;
        if (expanded) {
            if (!currentlyAnimating) {
                child.setAlpha(0.0f);
            }
            ofFloat = ObjectAnimator.ofFloat(child, View.ALPHA, new float[]{1.0f});
        } else {
            ofFloat = ObjectAnimator.ofFloat(child, View.ALPHA, new float[]{0.0f});
        }
        timing.apply(ofFloat);
        list.add(ofFloat);
    }
}
