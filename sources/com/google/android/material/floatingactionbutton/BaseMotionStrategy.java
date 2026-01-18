package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
abstract class BaseMotionStrategy implements MotionStrategy {
    private final Context context;
    private MotionSpec defaultMotionSpec;
    private final ExtendedFloatingActionButton fab;
    private final ArrayList listeners = new ArrayList();
    private MotionSpec motionSpec;
    private final AnimatorTracker tracker;

    static /* synthetic */ ExtendedFloatingActionButton access$000(BaseMotionStrategy x0) {
        return x0.fab;
    }

    BaseMotionStrategy(ExtendedFloatingActionButton fab, AnimatorTracker tracker) {
        this.fab = fab;
        this.context = fab.getContext();
        this.tracker = tracker;
    }

    public final void setMotionSpec(MotionSpec motionSpec) {
        this.motionSpec = motionSpec;
    }

    public final MotionSpec getCurrentMotionSpec() {
        MotionSpec motionSpec = this.motionSpec;
        if (motionSpec != null) {
            return motionSpec;
        }
        if (this.defaultMotionSpec == null) {
            this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
        }
        return (MotionSpec) Preconditions.checkNotNull(this.defaultMotionSpec);
    }

    public final void addAnimationListener(Animator.AnimatorListener listener) {
        this.listeners.add(listener);
    }

    public final void removeAnimationListener(Animator.AnimatorListener listener) {
        this.listeners.remove(listener);
    }

    public final List getListeners() {
        return this.listeners;
    }

    public MotionSpec getMotionSpec() {
        return this.motionSpec;
    }

    public void onAnimationStart(Animator animator) {
        this.tracker.onNextAnimationStart(animator);
    }

    public void onAnimationEnd() {
        this.tracker.clear();
    }

    public void onAnimationCancel() {
        this.tracker.clear();
    }

    public AnimatorSet createAnimator() {
        return createAnimator(getCurrentMotionSpec());
    }

    AnimatorSet createAnimator(MotionSpec spec) {
        ArrayList arrayList = new ArrayList();
        if (spec.hasPropertyValues("opacity")) {
            arrayList.add(spec.getAnimator("opacity", this.fab, View.ALPHA));
        }
        if (spec.hasPropertyValues("scale")) {
            arrayList.add(spec.getAnimator("scale", this.fab, View.SCALE_Y));
            arrayList.add(spec.getAnimator("scale", this.fab, View.SCALE_X));
        }
        if (spec.hasPropertyValues("width")) {
            arrayList.add(spec.getAnimator("width", this.fab, ExtendedFloatingActionButton.WIDTH));
        }
        if (spec.hasPropertyValues("height")) {
            arrayList.add(spec.getAnimator("height", this.fab, ExtendedFloatingActionButton.HEIGHT));
        }
        if (spec.hasPropertyValues("paddingStart")) {
            arrayList.add(spec.getAnimator("paddingStart", this.fab, ExtendedFloatingActionButton.PADDING_START));
        }
        if (spec.hasPropertyValues("paddingEnd")) {
            arrayList.add(spec.getAnimator("paddingEnd", this.fab, ExtendedFloatingActionButton.PADDING_END));
        }
        if (spec.hasPropertyValues("labelOpacity")) {
            ObjectAnimator animator = spec.getAnimator("labelOpacity", this.fab, new 1(Float.class, "LABEL_OPACITY_PROPERTY"));
            arrayList.add(animator);
        }
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, arrayList);
        return set;
    }

    class 1 extends Property {
        1(Class cls, String arg1) {
            super(cls, arg1);
        }

        public Float get(ExtendedFloatingActionButton object) {
            int originalOpacity = Color.alpha(object.originalTextCsl.getColorForState(object.getDrawableState(), BaseMotionStrategy.access$000(BaseMotionStrategy.this).originalTextCsl.getDefaultColor()));
            float currentOpacity = Color.alpha(object.getCurrentTextColor()) / 255.0f;
            return Float.valueOf(AnimationUtils.lerp(0.0f, 1.0f, currentOpacity / originalOpacity));
        }

        public void set(ExtendedFloatingActionButton object, Float value) {
            int originalColor = object.originalTextCsl.getColorForState(object.getDrawableState(), BaseMotionStrategy.access$000(BaseMotionStrategy.this).originalTextCsl.getDefaultColor());
            float interpolatedValue = AnimationUtils.lerp(0.0f, Color.alpha(originalColor) / 255.0f, value.floatValue());
            int alphaColor = Color.argb((int) (255.0f * interpolatedValue), Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor));
            ColorStateList csl = ColorStateList.valueOf(alphaColor);
            if (value.floatValue() == 1.0f) {
                object.silentlyUpdateTextColor(object.originalTextCsl);
            } else {
                object.silentlyUpdateTextColor(csl);
            }
        }
    }
}
