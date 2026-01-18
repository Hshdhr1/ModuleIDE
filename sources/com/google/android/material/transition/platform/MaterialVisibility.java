package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
abstract class MaterialVisibility extends Visibility {
    private final List additionalAnimatorProviders = new ArrayList();
    private final VisibilityAnimatorProvider primaryAnimatorProvider;
    private VisibilityAnimatorProvider secondaryAnimatorProvider;

    protected MaterialVisibility(VisibilityAnimatorProvider visibilityAnimatorProvider, VisibilityAnimatorProvider secondaryAnimatorProvider) {
        this.primaryAnimatorProvider = visibilityAnimatorProvider;
        this.secondaryAnimatorProvider = secondaryAnimatorProvider;
    }

    public VisibilityAnimatorProvider getPrimaryAnimatorProvider() {
        return this.primaryAnimatorProvider;
    }

    public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return this.secondaryAnimatorProvider;
    }

    public void setSecondaryAnimatorProvider(VisibilityAnimatorProvider secondaryAnimatorProvider) {
        this.secondaryAnimatorProvider = secondaryAnimatorProvider;
    }

    public void addAdditionalAnimatorProvider(VisibilityAnimatorProvider additionalAnimatorProvider) {
        this.additionalAnimatorProviders.add(additionalAnimatorProvider);
    }

    public boolean removeAdditionalAnimatorProvider(VisibilityAnimatorProvider additionalAnimatorProvider) {
        return this.additionalAnimatorProviders.remove(additionalAnimatorProvider);
    }

    public void clearAdditionalAnimatorProvider() {
        this.additionalAnimatorProviders.clear();
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createAnimator(sceneRoot, view, true);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createAnimator(sceneRoot, view, false);
    }

    private Animator createAnimator(ViewGroup sceneRoot, View view, boolean appearing) {
        AnimatorSet set = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        addAnimatorIfNeeded(arrayList, this.primaryAnimatorProvider, sceneRoot, view, appearing);
        addAnimatorIfNeeded(arrayList, this.secondaryAnimatorProvider, sceneRoot, view, appearing);
        for (VisibilityAnimatorProvider additionalAnimatorProvider : this.additionalAnimatorProviders) {
            addAnimatorIfNeeded(arrayList, additionalAnimatorProvider, sceneRoot, view, appearing);
        }
        maybeApplyThemeValues(sceneRoot.getContext(), appearing);
        AnimatorSetCompat.playTogether(set, arrayList);
        return set;
    }

    private static void addAnimatorIfNeeded(List list, VisibilityAnimatorProvider animatorProvider, ViewGroup sceneRoot, View view, boolean appearing) {
        Animator animator;
        if (animatorProvider == null) {
            return;
        }
        if (appearing) {
            animator = animatorProvider.createAppear(sceneRoot, view);
        } else {
            animator = animatorProvider.createDisappear(sceneRoot, view);
        }
        if (animator != null) {
            list.add(animator);
        }
    }

    private void maybeApplyThemeValues(Context context, boolean appearing) {
        TransitionUtils.maybeApplyThemeDuration(this, context, getDurationThemeAttrResId(appearing));
        TransitionUtils.maybeApplyThemeInterpolator(this, context, getEasingThemeAttrResId(appearing), getDefaultEasingInterpolator(appearing));
    }

    int getDurationThemeAttrResId(boolean appearing) {
        return 0;
    }

    int getEasingThemeAttrResId(boolean appearing) {
        return 0;
    }

    TimeInterpolator getDefaultEasingInterpolator(boolean appearing) {
        return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
}
