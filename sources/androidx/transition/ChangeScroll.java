package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class ChangeScroll extends Transition {
    private static final String[] PROPERTIES = {"android:changeScroll:x", "android:changeScroll:y"};
    private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";

    public ChangeScroll() {
    }

    public ChangeScroll(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Nullable
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put("android:changeScroll:x", Integer.valueOf(transitionValues.view.getScrollX()));
        transitionValues.values.put("android:changeScroll:y", Integer.valueOf(transitionValues.view.getScrollY()));
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        View view = endValues.view;
        int startX = ((Integer) startValues.values.get("android:changeScroll:x")).intValue();
        int endX = ((Integer) endValues.values.get("android:changeScroll:x")).intValue();
        int startY = ((Integer) startValues.values.get("android:changeScroll:y")).intValue();
        int endY = ((Integer) endValues.values.get("android:changeScroll:y")).intValue();
        ObjectAnimator objectAnimator = null;
        ObjectAnimator objectAnimator2 = null;
        if (startX != endX) {
            view.setScrollX(startX);
            objectAnimator = ObjectAnimator.ofInt(view, "scrollX", new int[]{startX, endX});
        }
        if (startY != endY) {
            view.setScrollY(startY);
            objectAnimator2 = ObjectAnimator.ofInt(view, "scrollY", new int[]{startY, endY});
        }
        return TransitionUtils.mergeAnimators(objectAnimator, objectAnimator2);
    }
}
