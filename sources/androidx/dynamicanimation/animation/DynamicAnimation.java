package androidx.dynamicanimation.animation;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.AnimationHandler;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes41.dex */
public abstract class DynamicAnimation implements AnimationHandler.AnimationFrameCallback {

    @SuppressLint({"MinMaxConstant"})
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;

    @SuppressLint({"MinMaxConstant"})
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;

    @SuppressLint({"MinMaxConstant"})
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;

    @SuppressLint({"MinMaxConstant"})
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    private static final float THRESHOLD_MULTIPLIER = 0.75f;
    private static final float UNSET = Float.MAX_VALUE;
    private final ArrayList mEndListeners;
    private long mLastFrameTime;
    float mMaxValue;
    float mMinValue;
    private float mMinVisibleChange;
    final FloatPropertyCompat mProperty;
    boolean mRunning;
    boolean mStartValueIsSet;
    final Object mTarget;
    private final ArrayList mUpdateListeners;
    float mValue;
    float mVelocity;
    public static final ViewProperty TRANSLATION_X = new 1("translationX");
    public static final ViewProperty TRANSLATION_Y = new 2("translationY");
    public static final ViewProperty TRANSLATION_Z = new 3("translationZ");
    public static final ViewProperty SCALE_X = new 4("scaleX");
    public static final ViewProperty SCALE_Y = new 5("scaleY");
    public static final ViewProperty ROTATION = new 6("rotation");
    public static final ViewProperty ROTATION_X = new 7("rotationX");
    public static final ViewProperty ROTATION_Y = new 8("rotationY");
    public static final ViewProperty X = new 9("x");
    public static final ViewProperty Y = new 10("y");
    public static final ViewProperty Z = new 11("z");
    public static final ViewProperty ALPHA = new 12("alpha");
    public static final ViewProperty SCROLL_X = new 13("scrollX");
    public static final ViewProperty SCROLL_Y = new 14("scrollY");

    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2);
    }

    abstract float getAcceleration(float f, float f2);

    abstract boolean isAtEquilibrium(float f, float f2);

    abstract void setValueThreshold(float f);

    abstract boolean updateValueAndVelocity(long j);

    public static abstract class ViewProperty extends FloatPropertyCompat {
        /* synthetic */ ViewProperty(String str, 1 r7) {
            this(str);
        }

        private ViewProperty(String str) {
            super(str);
        }
    }

    static class 1 extends ViewProperty {
        1(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }

        public float getValue(View view) {
            return view.getTranslationX();
        }
    }

    static class 2 extends ViewProperty {
        2(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }

        public float getValue(View view) {
            return view.getTranslationY();
        }
    }

    static class 3 extends ViewProperty {
        3(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            ViewCompat.setTranslationZ(view, f);
        }

        public float getValue(View view) {
            return ViewCompat.getTranslationZ(view);
        }
    }

    static class 4 extends ViewProperty {
        4(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setScaleX(f);
        }

        public float getValue(View view) {
            return view.getScaleX();
        }
    }

    static class 5 extends ViewProperty {
        5(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setScaleY(f);
        }

        public float getValue(View view) {
            return view.getScaleY();
        }
    }

    static class 6 extends ViewProperty {
        6(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setRotation(f);
        }

        public float getValue(View view) {
            return view.getRotation();
        }
    }

    static class 7 extends ViewProperty {
        7(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setRotationX(f);
        }

        public float getValue(View view) {
            return view.getRotationX();
        }
    }

    static class 8 extends ViewProperty {
        8(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setRotationY(f);
        }

        public float getValue(View view) {
            return view.getRotationY();
        }
    }

    static class 9 extends ViewProperty {
        9(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setX(f);
        }

        public float getValue(View view) {
            return view.getX();
        }
    }

    static class 10 extends ViewProperty {
        10(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setY(f);
        }

        public float getValue(View view) {
            return view.getY();
        }
    }

    static class 11 extends ViewProperty {
        11(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            ViewCompat.setZ(view, f);
        }

        public float getValue(View view) {
            return ViewCompat.getZ(view);
        }
    }

    static class 12 extends ViewProperty {
        12(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setAlpha(f);
        }

        public float getValue(View view) {
            return view.getAlpha();
        }
    }

    static class 13 extends ViewProperty {
        13(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }

        public float getValue(View view) {
            return view.getScrollX();
        }
    }

    static class 14 extends ViewProperty {
        14(String str) {
            super(str, null);
        }

        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }

        public float getValue(View view) {
            return view.getScrollY();
        }
    }

    static class MassState {
        float mValue;
        float mVelocity;

        MassState() {
        }
    }

    DynamicAnimation(FloatValueHolder floatValueHolder) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -this.mMaxValue;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new ArrayList();
        this.mUpdateListeners = new ArrayList();
        this.mTarget = null;
        this.mProperty = new 15("FloatValueHolder", floatValueHolder);
        this.mMinVisibleChange = 1.0f;
    }

    class 15 extends FloatPropertyCompat {
        final /* synthetic */ FloatValueHolder val$floatValueHolder;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        15(String str, FloatValueHolder floatValueHolder) {
            super(str);
            this.val$floatValueHolder = floatValueHolder;
        }

        public float getValue(Object obj) {
            return this.val$floatValueHolder.getValue();
        }

        public void setValue(Object obj, float f) {
            this.val$floatValueHolder.setValue(f);
        }
    }

    DynamicAnimation(Object obj, FloatPropertyCompat floatPropertyCompat) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -this.mMaxValue;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new ArrayList();
        this.mUpdateListeners = new ArrayList();
        this.mTarget = obj;
        this.mProperty = floatPropertyCompat;
        if (this.mProperty == ROTATION || this.mProperty == ROTATION_X || this.mProperty == ROTATION_Y) {
            this.mMinVisibleChange = 0.1f;
            return;
        }
        if (this.mProperty == ALPHA) {
            this.mMinVisibleChange = 0.00390625f;
        } else if (this.mProperty == SCALE_X || this.mProperty == SCALE_Y) {
            this.mMinVisibleChange = 0.00390625f;
        } else {
            this.mMinVisibleChange = 1.0f;
        }
    }

    public DynamicAnimation setStartValue(float f) {
        this.mValue = f;
        this.mStartValueIsSet = true;
        return this;
    }

    public DynamicAnimation setStartVelocity(float f) {
        this.mVelocity = f;
        return this;
    }

    public DynamicAnimation setMaxValue(float f) {
        this.mMaxValue = f;
        return this;
    }

    public DynamicAnimation setMinValue(float f) {
        this.mMinValue = f;
        return this;
    }

    public DynamicAnimation addEndListener(OnAnimationEndListener onAnimationEndListener) {
        if (!this.mEndListeners.contains(onAnimationEndListener)) {
            this.mEndListeners.add(onAnimationEndListener);
        }
        return this;
    }

    public void removeEndListener(OnAnimationEndListener onAnimationEndListener) {
        removeEntry(this.mEndListeners, onAnimationEndListener);
    }

    public DynamicAnimation addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (isRunning()) {
            throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
        }
        if (!this.mUpdateListeners.contains(onAnimationUpdateListener)) {
            this.mUpdateListeners.add(onAnimationUpdateListener);
        }
        return this;
    }

    public void removeUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        removeEntry(this.mUpdateListeners, onAnimationUpdateListener);
    }

    public DynamicAnimation setMinimumVisibleChange(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum visible change must be positive.");
        }
        this.mMinVisibleChange = f;
        setValueThreshold(f * 0.75f);
        return this;
    }

    public float getMinimumVisibleChange() {
        return this.mMinVisibleChange;
    }

    private static void removeNullEntries(ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    private static void removeEntry(ArrayList arrayList, Object obj) {
        int indexOf = arrayList.indexOf(obj);
        if (indexOf >= 0) {
            arrayList.set(indexOf, (Object) null);
        }
    }

    @MainThread
    public void start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        if (!this.mRunning) {
            startAnimationInternal();
        }
    }

    @MainThread
    public void cancel() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be canceled on the main thread");
        }
        if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    private void startAnimationInternal() {
        if (!this.mRunning) {
            this.mRunning = true;
            if (!this.mStartValueIsSet) {
                this.mValue = getPropertyValue();
            }
            if (this.mValue <= this.mMaxValue && this.mValue >= this.mMinValue) {
                AnimationHandler.getInstance().addAnimationFrameCallback(this, 0L);
                return;
            }
            throw new IllegalArgumentException("Starting value need to be in between min value and max value");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean doAnimationFrame(long j) {
        if (this.mLastFrameTime == 0) {
            this.mLastFrameTime = j;
            setPropertyValue(this.mValue);
            return false;
        }
        long j2 = j - this.mLastFrameTime;
        this.mLastFrameTime = j;
        boolean updateValueAndVelocity = updateValueAndVelocity(j2);
        this.mValue = Math.min(this.mValue, this.mMaxValue);
        this.mValue = Math.max(this.mValue, this.mMinValue);
        setPropertyValue(this.mValue);
        if (updateValueAndVelocity) {
            endAnimationInternal(false);
        }
        return updateValueAndVelocity;
    }

    private void endAnimationInternal(boolean z) {
        this.mRunning = false;
        AnimationHandler.getInstance().removeCallback(this);
        this.mLastFrameTime = 0L;
        this.mStartValueIsSet = false;
        for (int i = 0; i < this.mEndListeners.size(); i++) {
            if (this.mEndListeners.get(i) != null) {
                ((OnAnimationEndListener) this.mEndListeners.get(i)).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mEndListeners);
    }

    void setPropertyValue(float f) {
        this.mProperty.setValue(this.mTarget, f);
        for (int i = 0; i < this.mUpdateListeners.size(); i++) {
            if (this.mUpdateListeners.get(i) != null) {
                ((OnAnimationUpdateListener) this.mUpdateListeners.get(i)).onAnimationUpdate(this, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mUpdateListeners);
    }

    float getValueThreshold() {
        return this.mMinVisibleChange * 0.75f;
    }

    private float getPropertyValue() {
        return this.mProperty.getValue(this.mTarget);
    }
}
