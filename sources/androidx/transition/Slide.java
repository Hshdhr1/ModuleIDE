package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class Slide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private CalculateSlide mSlideCalculator;
    private int mSlideEdge;
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    private static final CalculateSlide sCalculateLeft = new 1();
    private static final CalculateSlide sCalculateStart = new 2();
    private static final CalculateSlide sCalculateTop = new 3();
    private static final CalculateSlide sCalculateRight = new 4();
    private static final CalculateSlide sCalculateEnd = new 5();
    private static final CalculateSlide sCalculateBottom = new 6();

    private interface CalculateSlide {
        float getGoneX(ViewGroup viewGroup, View view);

        float getGoneY(ViewGroup viewGroup, View view);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public @interface GravityFlag {
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        /* synthetic */ CalculateSlideHorizontal(1 x0) {
            this();
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        /* synthetic */ CalculateSlideVertical(1 x0) {
            this();
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX();
        }
    }

    class 1 extends CalculateSlideHorizontal {
        1() {
            super(null);
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() - sceneRoot.getWidth();
        }
    }

    class 2 extends CalculateSlideHorizontal {
        2() {
            super(null);
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = ViewCompat.getLayoutDirection(sceneRoot) == 1;
            if (isRtl) {
                float x = view.getTranslationX() + sceneRoot.getWidth();
                return x;
            }
            float x2 = view.getTranslationX() - sceneRoot.getWidth();
            return x2;
        }
    }

    class 3 extends CalculateSlideVertical {
        3() {
            super(null);
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() - sceneRoot.getHeight();
        }
    }

    class 4 extends CalculateSlideHorizontal {
        4() {
            super(null);
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() + sceneRoot.getWidth();
        }
    }

    class 5 extends CalculateSlideHorizontal {
        5() {
            super(null);
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = ViewCompat.getLayoutDirection(sceneRoot) == 1;
            if (isRtl) {
                float x = view.getTranslationX() - sceneRoot.getWidth();
                return x;
            }
            float x2 = view.getTranslationX() + sceneRoot.getWidth();
            return x2;
        }
    }

    class 6 extends CalculateSlideVertical {
        6() {
            super(null);
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() + sceneRoot.getHeight();
        }
    }

    public Slide() {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        setSlideEdge(80);
    }

    public Slide(int slideEdge) {
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        setSlideEdge(slideEdge);
    }

    @SuppressLint({"RestrictedApi"})
    public Slide(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        this.mSlideCalculator = sCalculateBottom;
        this.mSlideEdge = 80;
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.SLIDE);
        int edge = TypedArrayUtils.getNamedInt(a, (XmlPullParser) attrs, "slideEdge", 0, 80);
        a.recycle();
        setSlideEdge(edge);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        transitionValues.values.put("android:slide:screenPosition", position);
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int slideEdge) {
        switch (slideEdge) {
            case 3:
                this.mSlideCalculator = sCalculateLeft;
                break;
            case 5:
                this.mSlideCalculator = sCalculateRight;
                break;
            case 48:
                this.mSlideCalculator = sCalculateTop;
                break;
            case 80:
                this.mSlideCalculator = sCalculateBottom;
                break;
            case 8388611:
                this.mSlideCalculator = sCalculateStart;
                break;
            case 8388613:
                this.mSlideCalculator = sCalculateEnd;
                break;
            default:
                throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = slideEdge;
        SidePropagation propagation = new SidePropagation();
        propagation.setSide(slideEdge);
        setPropagation(propagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    @Nullable
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null) {
            return null;
        }
        int[] position = (int[]) endValues.values.get("android:slide:screenPosition");
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = this.mSlideCalculator.getGoneX(sceneRoot, view);
        float startY = this.mSlideCalculator.getGoneY(sceneRoot, view);
        return TranslationAnimationCreator.createAnimation(view, endValues, position[0], position[1], startX, startY, endX, endY, sDecelerate, this);
    }

    @Nullable
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        int[] position = (int[]) startValues.values.get("android:slide:screenPosition");
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = this.mSlideCalculator.getGoneX(sceneRoot, view);
        float endY = this.mSlideCalculator.getGoneY(sceneRoot, view);
        return TranslationAnimationCreator.createAnimation(view, startValues, position[0], position[1], startX, startY, endX, endY, sAccelerate, this);
    }
}
