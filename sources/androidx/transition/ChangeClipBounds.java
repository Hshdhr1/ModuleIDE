package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class ChangeClipBounds extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = {"android:clipBounds:clip"};

    @NonNull
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() != 8) {
            Rect clip = ViewCompat.getClipBounds(view);
            values.values.put("android:clipBounds:clip", clip);
            if (clip == null) {
                Rect bounds = new Rect(0, 0, view.getWidth(), view.getHeight());
                values.values.put("android:clipBounds:bounds", bounds);
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        ObjectAnimator animator = null;
        if (startValues != null && endValues != null && startValues.values.containsKey("android:clipBounds:clip") && endValues.values.containsKey("android:clipBounds:clip")) {
            Rect start = (Rect) startValues.values.get("android:clipBounds:clip");
            Rect end = (Rect) endValues.values.get("android:clipBounds:clip");
            boolean endIsNull = end == null;
            if (start != null || end != null) {
                if (start == null) {
                    start = (Rect) startValues.values.get("android:clipBounds:bounds");
                } else if (end == null) {
                    end = (Rect) endValues.values.get("android:clipBounds:bounds");
                }
                if (!start.equals(end)) {
                    ViewCompat.setClipBounds(endValues.view, start);
                    RectEvaluator evaluator = new RectEvaluator(new Rect());
                    animator = ObjectAnimator.ofObject(endValues.view, ViewUtils.CLIP_BOUNDS, evaluator, new Rect[]{start, end});
                    if (endIsNull) {
                        View endView = endValues.view;
                        animator.addListener(new 1(endView));
                    }
                }
            }
        }
        return animator;
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$endView;

        1(View view) {
            this.val$endView = view;
        }

        public void onAnimationEnd(Animator animation) {
            ViewCompat.setClipBounds(this.val$endView, (Rect) null);
        }
    }
}
