package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    private float dependencyOriginalTranslationX;
    private float dependencyOriginalTranslationY;
    private final int[] tmpArray;
    private final Rect tmpRect;
    private final RectF tmpRectF1;
    private final RectF tmpRectF2;

    protected abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);

    public FabTransformationBehavior() {
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    public FabTransformationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.tmpRect = new Rect();
        this.tmpRectF1 = new RectF();
        this.tmpRectF2 = new RectF();
        this.tmpArray = new int[2];
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (child.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        }
        if (!(dependency instanceof FloatingActionButton)) {
            return false;
        }
        int expandedComponentIdHint = ((FloatingActionButton) dependency).getExpandedComponentIdHint();
        return expandedComponentIdHint == 0 || expandedComponentIdHint == child.getId();
    }

    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams lp) {
        if (lp.dodgeInsetEdges == 0) {
            lp.dodgeInsetEdges = 80;
        }
    }

    protected AnimatorSet onCreateExpandedStateChangeAnimation(View dependency, View child, boolean expanded, boolean isAnimating) {
        FabTransformationSpec spec = onCreateMotionSpec(child.getContext(), expanded);
        if (expanded) {
            this.dependencyOriginalTranslationX = dependency.getTranslationX();
            this.dependencyOriginalTranslationY = dependency.getTranslationY();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (Build.VERSION.SDK_INT >= 21) {
            createElevationAnimation(dependency, child, expanded, isAnimating, spec, arrayList, arrayList2);
        }
        RectF childBounds = this.tmpRectF1;
        createTranslationAnimation(dependency, child, expanded, isAnimating, spec, arrayList, arrayList2, childBounds);
        float childWidth = childBounds.width();
        float childHeight = childBounds.height();
        createDependencyTranslationAnimation(dependency, child, expanded, spec, arrayList);
        createIconFadeAnimation(dependency, child, expanded, isAnimating, spec, arrayList, arrayList2);
        createExpansionAnimation(dependency, child, expanded, isAnimating, spec, childWidth, childHeight, arrayList, arrayList2);
        createColorAnimation(dependency, child, expanded, isAnimating, spec, arrayList, arrayList2);
        createChildrenFadeAnimation(dependency, child, expanded, isAnimating, spec, arrayList, arrayList2);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, arrayList);
        set.addListener(new 1(expanded, child, dependency));
        int count = arrayList2.size();
        for (int i = 0; i < count; i++) {
            set.addListener((Animator.AnimatorListener) arrayList2.get(i));
        }
        return set;
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$child;
        final /* synthetic */ View val$dependency;
        final /* synthetic */ boolean val$expanded;

        1(boolean z, View view, View view2) {
            this.val$expanded = z;
            this.val$child = view;
            this.val$dependency = view2;
        }

        public void onAnimationStart(Animator animation) {
            if (this.val$expanded) {
                this.val$child.setVisibility(0);
                this.val$dependency.setAlpha(0.0f);
                this.val$dependency.setVisibility(4);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.val$expanded) {
                this.val$child.setVisibility(4);
                this.val$dependency.setAlpha(1.0f);
                this.val$dependency.setVisibility(0);
            }
        }
    }

    private void createElevationAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List list, List list2) {
        ObjectAnimator ofFloat;
        float translationZ = ViewCompat.getElevation(child) - ViewCompat.getElevation(dependency);
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationZ(-translationZ);
            }
            ofFloat = ObjectAnimator.ofFloat(child, View.TRANSLATION_Z, new float[]{0.0f});
        } else {
            ofFloat = ObjectAnimator.ofFloat(child, View.TRANSLATION_Z, new float[]{-translationZ});
        }
        MotionTiming timing = spec.timings.getTiming("elevation");
        timing.apply(ofFloat);
        list.add(ofFloat);
    }

    private void createDependencyTranslationAnimation(View dependency, View child, boolean expanded, FabTransformationSpec spec, List list) {
        float translationX = calculateTranslationX(dependency, child, spec.positioning);
        float translationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> motionTiming = calculateMotionTiming(translationX, translationY, expanded, spec);
        MotionTiming translationXTiming = (MotionTiming) motionTiming.first;
        MotionTiming translationYTiming = (MotionTiming) motionTiming.second;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        fArr[0] = expanded ? translationX : this.dependencyOriginalTranslationX;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(dependency, property, fArr);
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[1];
        fArr2[0] = expanded ? translationY : this.dependencyOriginalTranslationY;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(dependency, property2, fArr2);
        translationXTiming.apply(ofFloat);
        translationYTiming.apply(ofFloat2);
        list.add(ofFloat);
        list.add(ofFloat2);
    }

    private void createTranslationAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List list, List list2, RectF childBounds) {
        MotionTiming translationYTiming;
        MotionTiming translationXTiming;
        Animator ofFloat;
        Animator ofFloat2;
        float translationX = calculateTranslationX(dependency, child, spec.positioning);
        float translationY = calculateTranslationY(dependency, child, spec.positioning);
        Pair<MotionTiming, MotionTiming> motionTiming = calculateMotionTiming(translationX, translationY, expanded, spec);
        MotionTiming translationXTiming2 = (MotionTiming) motionTiming.first;
        MotionTiming translationYTiming2 = (MotionTiming) motionTiming.second;
        if (expanded) {
            if (!currentlyAnimating) {
                child.setTranslationX(-translationX);
                child.setTranslationY(-translationY);
            }
            Animator ofFloat3 = ObjectAnimator.ofFloat(child, View.TRANSLATION_X, new float[]{0.0f});
            Animator ofFloat4 = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, new float[]{0.0f});
            translationYTiming = translationYTiming2;
            translationXTiming = translationXTiming2;
            calculateChildVisibleBoundsAtEndOfExpansion(child, spec, translationXTiming2, translationYTiming2, -translationX, -translationY, 0.0f, 0.0f, childBounds);
            ofFloat = ofFloat3;
            ofFloat2 = ofFloat4;
        } else {
            translationYTiming = translationYTiming2;
            translationXTiming = translationXTiming2;
            ofFloat = ObjectAnimator.ofFloat(child, View.TRANSLATION_X, new float[]{-translationX});
            ofFloat2 = ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, new float[]{-translationY});
        }
        translationXTiming.apply(ofFloat);
        translationYTiming.apply(ofFloat2);
        list.add(ofFloat);
        list.add(ofFloat2);
    }

    private void createIconFadeAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List list, List list2) {
        ObjectAnimator animator;
        if (!(child instanceof CircularRevealWidget) || !(dependency instanceof ImageView)) {
            return;
        }
        CircularRevealWidget circularRevealChild = (CircularRevealWidget) child;
        ImageView dependencyImageView = (ImageView) dependency;
        Drawable icon = dependencyImageView.getDrawable();
        if (icon == null) {
            return;
        }
        icon.mutate();
        if (expanded) {
            if (!currentlyAnimating) {
                icon.setAlpha(255);
            }
            animator = ObjectAnimator.ofInt(icon, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, new int[]{0});
        } else {
            animator = ObjectAnimator.ofInt(icon, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, new int[]{255});
        }
        animator.addUpdateListener(new 2(child));
        MotionTiming timing = spec.timings.getTiming("iconFade");
        timing.apply(animator);
        list.add(animator);
        list2.add(new 3(circularRevealChild, icon));
    }

    class 2 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ View val$child;

        2(View view) {
            this.val$child = view;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            this.val$child.invalidate();
        }
    }

    class 3 extends AnimatorListenerAdapter {
        final /* synthetic */ CircularRevealWidget val$circularRevealChild;
        final /* synthetic */ Drawable val$icon;

        3(CircularRevealWidget circularRevealWidget, Drawable drawable) {
            this.val$circularRevealChild = circularRevealWidget;
            this.val$icon = drawable;
        }

        public void onAnimationStart(Animator animation) {
            this.val$circularRevealChild.setCircularRevealOverlayDrawable(this.val$icon);
        }

        public void onAnimationEnd(Animator animation) {
            this.val$circularRevealChild.setCircularRevealOverlayDrawable(null);
        }
    }

    private void createExpansionAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, float childWidth, float childHeight, List list, List list2) {
        MotionTiming timing;
        CircularRevealWidget circularRevealChild;
        Animator animator;
        if (!(child instanceof CircularRevealWidget)) {
            return;
        }
        CircularRevealWidget circularRevealChild2 = (CircularRevealWidget) child;
        float revealCenterX = calculateRevealCenterX(dependency, child, spec.positioning);
        float revealCenterY = calculateRevealCenterY(dependency, child, spec.positioning);
        ((FloatingActionButton) dependency).getContentRect(this.tmpRect);
        float dependencyRadius = this.tmpRect.width() / 2.0f;
        MotionTiming timing2 = spec.timings.getTiming("expansion");
        if (expanded) {
            if (!currentlyAnimating) {
                circularRevealChild2.setRevealInfo(new CircularRevealWidget.RevealInfo(revealCenterX, revealCenterY, dependencyRadius));
            }
            float fromRadius = currentlyAnimating ? circularRevealChild2.getRevealInfo().radius : dependencyRadius;
            float toRadius = MathUtils.distanceToFurthestCorner(revealCenterX, revealCenterY, 0.0f, 0.0f, childWidth, childHeight);
            Animator animator2 = CircularRevealCompat.createCircularReveal(circularRevealChild2, revealCenterX, revealCenterY, toRadius);
            animator2.addListener(new 4(circularRevealChild2));
            timing = timing2;
            createPreFillRadialExpansion(child, timing2.getDelay(), (int) revealCenterX, (int) revealCenterY, fromRadius, list);
            circularRevealChild = circularRevealChild2;
            animator = animator2;
        } else {
            timing = timing2;
            float fromRadius2 = circularRevealChild2.getRevealInfo().radius;
            Animator animator3 = CircularRevealCompat.createCircularReveal(circularRevealChild2, revealCenterX, revealCenterY, dependencyRadius);
            createPreFillRadialExpansion(child, timing.getDelay(), (int) revealCenterX, (int) revealCenterY, fromRadius2, list);
            circularRevealChild = circularRevealChild2;
            createPostFillRadialExpansion(child, timing.getDelay(), timing.getDuration(), spec.timings.getTotalDuration(), (int) revealCenterX, (int) revealCenterY, dependencyRadius, list);
            animator = animator3;
        }
        timing.apply(animator);
        list.add(animator);
        list2.add(CircularRevealCompat.createCircularRevealListener(circularRevealChild));
    }

    class 4 extends AnimatorListenerAdapter {
        final /* synthetic */ CircularRevealWidget val$circularRevealChild;

        4(CircularRevealWidget circularRevealWidget) {
            this.val$circularRevealChild = circularRevealWidget;
        }

        public void onAnimationEnd(Animator animation) {
            CircularRevealWidget.RevealInfo revealInfo = this.val$circularRevealChild.getRevealInfo();
            revealInfo.radius = Float.MAX_VALUE;
            this.val$circularRevealChild.setRevealInfo(revealInfo);
        }
    }

    private void createColorAnimation(View dependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List list, List list2) {
        ObjectAnimator animator;
        if (!(child instanceof CircularRevealWidget)) {
            return;
        }
        CircularRevealWidget circularRevealChild = (CircularRevealWidget) child;
        int tint = getBackgroundTint(dependency);
        int transparent = 16777215 & tint;
        if (expanded) {
            if (!currentlyAnimating) {
                circularRevealChild.setCircularRevealScrimColor(tint);
            }
            animator = ObjectAnimator.ofInt(circularRevealChild, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, new int[]{transparent});
        } else {
            animator = ObjectAnimator.ofInt(circularRevealChild, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, new int[]{tint});
        }
        animator.setEvaluator(ArgbEvaluatorCompat.getInstance());
        MotionTiming timing = spec.timings.getTiming("color");
        timing.apply(animator);
        list.add(animator);
    }

    private void createChildrenFadeAnimation(View unusedDependency, View child, boolean expanded, boolean currentlyAnimating, FabTransformationSpec spec, List list, List list2) {
        ViewGroup childContentContainer;
        ObjectAnimator ofFloat;
        if (!(child instanceof ViewGroup)) {
            return;
        }
        if (((child instanceof CircularRevealWidget) && CircularRevealHelper.STRATEGY == 0) || (childContentContainer = calculateChildContentContainer(child)) == null) {
            return;
        }
        if (expanded) {
            if (!currentlyAnimating) {
                ChildrenAlphaProperty.CHILDREN_ALPHA.set(childContentContainer, Float.valueOf(0.0f));
            }
            ofFloat = ObjectAnimator.ofFloat(childContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, new float[]{1.0f});
        } else {
            ofFloat = ObjectAnimator.ofFloat(childContentContainer, ChildrenAlphaProperty.CHILDREN_ALPHA, new float[]{0.0f});
        }
        MotionTiming timing = spec.timings.getTiming("contentFade");
        timing.apply(ofFloat);
        list.add(ofFloat);
    }

    private Pair calculateMotionTiming(float translationX, float translationY, boolean expanded, FabTransformationSpec spec) {
        MotionTiming translationXTiming;
        MotionTiming translationYTiming;
        if (translationX == 0.0f || translationY == 0.0f) {
            translationXTiming = spec.timings.getTiming("translationXLinear");
            translationYTiming = spec.timings.getTiming("translationYLinear");
        } else if ((expanded && translationY < 0.0f) || (!expanded && translationY > 0.0f)) {
            translationXTiming = spec.timings.getTiming("translationXCurveUpwards");
            translationYTiming = spec.timings.getTiming("translationYCurveUpwards");
        } else {
            translationXTiming = spec.timings.getTiming("translationXCurveDownwards");
            translationYTiming = spec.timings.getTiming("translationYCurveDownwards");
        }
        return new Pair(translationXTiming, translationYTiming);
    }

    private float calculateTranslationX(View dependency, View child, Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationX = 0.0f;
        switch (positioning.gravity & 7) {
            case 1:
                translationX = childBounds.centerX() - dependencyBounds.centerX();
                break;
            case 3:
                translationX = childBounds.left - dependencyBounds.left;
                break;
            case 5:
                translationX = childBounds.right - dependencyBounds.right;
                break;
        }
        return translationX + positioning.xAdjustment;
    }

    private float calculateTranslationY(View dependency, View child, Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationY = 0.0f;
        switch (positioning.gravity & 112) {
            case 16:
                translationY = childBounds.centerY() - dependencyBounds.centerY();
                break;
            case 48:
                translationY = childBounds.top - dependencyBounds.top;
                break;
            case 80:
                translationY = childBounds.bottom - dependencyBounds.bottom;
                break;
        }
        return translationY + positioning.yAdjustment;
    }

    private void calculateWindowBounds(View view, RectF rect) {
        rect.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        int[] windowLocation = this.tmpArray;
        view.getLocationInWindow(windowLocation);
        rect.offsetTo(windowLocation[0], windowLocation[1]);
        rect.offset((int) (-view.getTranslationX()), (int) (-view.getTranslationY()));
    }

    private void calculateDependencyWindowBounds(View view, RectF rect) {
        calculateWindowBounds(view, rect);
        rect.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
    }

    private float calculateRevealCenterX(View dependency, View child, Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationX = calculateTranslationX(dependency, child, positioning);
        childBounds.offset(-translationX, 0.0f);
        return dependencyBounds.centerX() - childBounds.left;
    }

    private float calculateRevealCenterY(View dependency, View child, Positioning positioning) {
        RectF dependencyBounds = this.tmpRectF1;
        RectF childBounds = this.tmpRectF2;
        calculateDependencyWindowBounds(dependency, dependencyBounds);
        calculateWindowBounds(child, childBounds);
        float translationY = calculateTranslationY(dependency, child, positioning);
        childBounds.offset(0.0f, -translationY);
        return dependencyBounds.centerY() - childBounds.top;
    }

    private void calculateChildVisibleBoundsAtEndOfExpansion(View child, FabTransformationSpec spec, MotionTiming translationXTiming, MotionTiming translationYTiming, float fromX, float fromY, float toX, float toY, RectF childBounds) {
        float translationX = calculateValueOfAnimationAtEndOfExpansion(spec, translationXTiming, fromX, toX);
        float translationY = calculateValueOfAnimationAtEndOfExpansion(spec, translationYTiming, fromY, toY);
        Rect window = this.tmpRect;
        child.getWindowVisibleDisplayFrame(window);
        RectF windowF = this.tmpRectF1;
        windowF.set(window);
        RectF childVisibleBounds = this.tmpRectF2;
        calculateWindowBounds(child, childVisibleBounds);
        childVisibleBounds.offset(translationX, translationY);
        childVisibleBounds.intersect(windowF);
        childBounds.set(childVisibleBounds);
    }

    private float calculateValueOfAnimationAtEndOfExpansion(FabTransformationSpec spec, MotionTiming timing, float from, float to) {
        long delay = timing.getDelay();
        long duration = timing.getDuration();
        MotionTiming expansionTiming = spec.timings.getTiming("expansion");
        long expansionEnd = expansionTiming.getDelay() + expansionTiming.getDuration();
        float fraction = ((expansionEnd + 17) - delay) / duration;
        return AnimationUtils.lerp(from, to, timing.getInterpolator().getInterpolation(fraction));
    }

    private ViewGroup calculateChildContentContainer(View view) {
        View childContentContainer = view.findViewById(R.id.mtrl_child_content_container);
        if (childContentContainer != null) {
            return toViewGroupOrNull(childContentContainer);
        }
        if ((view instanceof TransformationChildLayout) || (view instanceof TransformationChildCard)) {
            return toViewGroupOrNull(((ViewGroup) view).getChildAt(0));
        }
        return toViewGroupOrNull(view);
    }

    private ViewGroup toViewGroupOrNull(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    private int getBackgroundTint(View view) {
        ColorStateList tintList = ViewCompat.getBackgroundTintList(view);
        if (tintList != null) {
            return tintList.getColorForState(view.getDrawableState(), tintList.getDefaultColor());
        }
        return 0;
    }

    private void createPreFillRadialExpansion(View child, long delay, int revealCenterX, int revealCenterY, float fromRadius, List list) {
        if (Build.VERSION.SDK_INT >= 21 && delay > 0) {
            Animator animator = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, fromRadius, fromRadius);
            animator.setStartDelay(0L);
            animator.setDuration(delay);
            list.add(animator);
        }
    }

    private void createPostFillRadialExpansion(View child, long delay, long duration, long totalDuration, int revealCenterX, int revealCenterY, float toRadius, List list) {
        if (Build.VERSION.SDK_INT >= 21 && delay + duration < totalDuration) {
            Animator animator = ViewAnimationUtils.createCircularReveal(child, revealCenterX, revealCenterY, toRadius, toRadius);
            animator.setStartDelay(delay + duration);
            animator.setDuration(totalDuration - (delay + duration));
            list.add(animator);
        }
    }

    protected static class FabTransformationSpec {
        public Positioning positioning;
        public MotionSpec timings;

        protected FabTransformationSpec() {
        }
    }
}
