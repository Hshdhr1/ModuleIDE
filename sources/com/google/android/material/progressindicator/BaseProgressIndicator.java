package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class BaseProgressIndicator extends ProgressBar {
    static final float DEFAULT_OPACITY = 0.2f;
    static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ProgressIndicator;
    public static final int HIDE_INWARD = 2;
    public static final int HIDE_NONE = 0;
    public static final int HIDE_OUTWARD = 1;
    static final int MAX_ALPHA = 255;
    static final int MAX_HIDE_DELAY = 1000;
    public static final int SHOW_INWARD = 2;
    public static final int SHOW_NONE = 0;
    public static final int SHOW_OUTWARD = 1;
    AnimatorDurationScaleProvider animatorDurationScaleProvider;
    private final Runnable delayedHide;
    private final Runnable delayedShow;
    private final Animatable2Compat.AnimationCallback hideAnimationCallback;
    private boolean isIndeterminateModeChangeRequested;
    private boolean isParentDoneInitializing;
    private long lastShowStartTime;
    private final int minHideDelay;
    private final int showDelay;
    BaseProgressIndicatorSpec spec;
    private int storedProgress;
    private boolean storedProgressAnimated;
    private final Animatable2Compat.AnimationCallback switchIndeterminateModeCallback;
    private int visibilityAfterHide;

    @Retention(RetentionPolicy.SOURCE)
    public @interface HideAnimationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowAnimationBehavior {
    }

    abstract BaseProgressIndicatorSpec createSpec(Context context, AttributeSet attributeSet);

    static /* synthetic */ void access$000(BaseProgressIndicator x0) {
        x0.internalShow();
    }

    static /* synthetic */ void access$100(BaseProgressIndicator x0) {
        x0.internalHide();
    }

    static /* synthetic */ long access$202(BaseProgressIndicator x0, long x1) {
        x0.lastShowStartTime = x1;
        return x1;
    }

    static /* synthetic */ int access$300(BaseProgressIndicator x0) {
        return x0.storedProgress;
    }

    static /* synthetic */ boolean access$400(BaseProgressIndicator x0) {
        return x0.storedProgressAnimated;
    }

    static /* synthetic */ boolean access$500(BaseProgressIndicator x0) {
        return x0.isIndeterminateModeChangeRequested;
    }

    static /* synthetic */ int access$600(BaseProgressIndicator x0) {
        return x0.visibilityAfterHide;
    }

    protected BaseProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.lastShowStartTime = -1L;
        this.isIndeterminateModeChangeRequested = false;
        this.visibilityAfterHide = 4;
        this.delayedShow = new 1();
        this.delayedHide = new 2();
        this.switchIndeterminateModeCallback = new 3();
        this.hideAnimationCallback = new 4();
        Context context2 = getContext();
        this.spec = createSpec(context2, attrs);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.showDelay = a.getInt(R.styleable.BaseProgressIndicator_showDelay, -1);
        int minHideDelayUncapped = a.getInt(R.styleable.BaseProgressIndicator_minHideDelay, -1);
        this.minHideDelay = Math.min(minHideDelayUncapped, 1000);
        a.recycle();
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        this.isParentDoneInitializing = true;
    }

    private void registerAnimationCallbacks() {
        if (getProgressDrawable() != null && getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().getAnimatorDelegate().registerAnimatorsCompleteCallback(this.switchIndeterminateModeCallback);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.hideAnimationCallback);
        }
    }

    private void unregisterAnimationCallbacks() {
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
            getIndeterminateDrawable().getAnimatorDelegate().unregisterAnimatorsCompleteCallback();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.hideAnimationCallback);
        }
    }

    public void show() {
        if (this.showDelay > 0) {
            removeCallbacks(this.delayedShow);
            postDelayed(this.delayedShow, this.showDelay);
        } else {
            this.delayedShow.run();
        }
    }

    private void internalShow() {
        if (this.minHideDelay > 0) {
            this.lastShowStartTime = SystemClock.uptimeMillis();
        }
        setVisibility(0);
    }

    public void hide() {
        if (getVisibility() != 0) {
            removeCallbacks(this.delayedShow);
            return;
        }
        removeCallbacks(this.delayedHide);
        long timeElapsedSinceShowStart = SystemClock.uptimeMillis() - this.lastShowStartTime;
        int i = this.minHideDelay;
        boolean enoughTimeElapsed = timeElapsedSinceShowStart >= ((long) i);
        if (enoughTimeElapsed) {
            this.delayedHide.run();
        } else {
            postDelayed(this.delayedHide, i - timeElapsedSinceShowStart);
        }
    }

    private void internalHide() {
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(false, false, true);
        if (isNoLongerNeedToBeVisible()) {
            setVisibility(4);
        }
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        applyNewVisibility(visibility == 0);
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        applyNewVisibility(false);
    }

    protected void applyNewVisibility(boolean animate) {
        if (!this.isParentDoneInitializing) {
            return;
        }
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).setVisible(visibleToUser(), false, animate);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerAnimationCallbacks();
        if (visibleToUser()) {
            internalShow();
        }
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.delayedHide);
        removeCallbacks(this.delayedShow);
        ((DrawableWithAnimatedVisibilityChange) getCurrentDrawable()).hideNow();
        unregisterAnimationCallbacks();
        super.onDetachedFromWindow();
    }

    protected synchronized void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (getPaddingLeft() != 0 || getPaddingTop() != 0) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
        }
        if (getPaddingRight() != 0 || getPaddingBottom() != 0) {
            int w = getWidth() - (getPaddingLeft() + getPaddingRight());
            int h = getHeight() - (getPaddingTop() + getPaddingBottom());
            canvas.clipRect(0, 0, w, h);
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft;
        int paddingTop;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DrawingDelegate currentDrawingDelegate = getCurrentDrawingDelegate();
        if (currentDrawingDelegate == null) {
            return;
        }
        int drawableMeasuredWidth = currentDrawingDelegate.getPreferredWidth();
        int drawableMeasuredHeight = currentDrawingDelegate.getPreferredHeight();
        if (drawableMeasuredWidth < 0) {
            paddingLeft = getMeasuredWidth();
        } else {
            paddingLeft = getPaddingLeft() + drawableMeasuredWidth + getPaddingRight();
        }
        if (drawableMeasuredHeight < 0) {
            paddingTop = getMeasuredHeight();
        } else {
            paddingTop = getPaddingTop() + drawableMeasuredHeight + getPaddingBottom();
        }
        setMeasuredDimension(paddingLeft, paddingTop);
    }

    public void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    public Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    private DrawingDelegate getCurrentDrawingDelegate() {
        if (isIndeterminate()) {
            if (getIndeterminateDrawable() == null) {
                return null;
            }
            return getIndeterminateDrawable().getDrawingDelegate();
        }
        if (getProgressDrawable() == null) {
            return null;
        }
        return getProgressDrawable().getDrawingDelegate();
    }

    public void setProgressDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable((Drawable) null);
        } else {
            if (drawable instanceof DeterminateDrawable) {
                DeterminateDrawable determinateDrawable = (DeterminateDrawable) drawable;
                determinateDrawable.hideNow();
                super.setProgressDrawable(determinateDrawable);
                determinateDrawable.setLevelByFraction(getProgress() / getMax());
                return;
            }
            throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable((Drawable) null);
        } else {
            if (drawable instanceof IndeterminateDrawable) {
                ((DrawableWithAnimatedVisibilityChange) drawable).hideNow();
                super.setIndeterminateDrawable(drawable);
                return;
            }
            throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
        }
    }

    public DeterminateDrawable getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }

    public IndeterminateDrawable getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    boolean visibleToUser() {
        return ViewCompat.isAttachedToWindow(this) && getWindowVisibility() == 0 && isEffectivelyVisible();
    }

    boolean isEffectivelyVisible() {
        BaseProgressIndicator baseProgressIndicator = this;
        while (baseProgressIndicator.getVisibility() == 0) {
            ViewParent parent = baseProgressIndicator.getParent();
            if (parent == null) {
                return getWindowVisibility() == 0;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            baseProgressIndicator = (View) parent;
        }
        return false;
    }

    private boolean isNoLongerNeedToBeVisible() {
        return (getProgressDrawable() == null || !getProgressDrawable().isVisible()) && (getIndeterminateDrawable() == null || !getIndeterminateDrawable().isVisible());
    }

    public synchronized void setIndeterminate(boolean indeterminate) {
        if (indeterminate == isIndeterminate()) {
            return;
        }
        DrawableWithAnimatedVisibilityChange oldDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
        if (oldDrawable != null) {
            oldDrawable.hideNow();
        }
        super.setIndeterminate(indeterminate);
        DrawableWithAnimatedVisibilityChange newDrawable = (DrawableWithAnimatedVisibilityChange) getCurrentDrawable();
        if (newDrawable != null) {
            newDrawable.setVisible(visibleToUser(), false, false);
        }
        if ((newDrawable instanceof IndeterminateDrawable) && visibleToUser()) {
            ((IndeterminateDrawable) newDrawable).getAnimatorDelegate().startAnimator();
        }
        this.isIndeterminateModeChangeRequested = false;
    }

    public int getTrackThickness() {
        return this.spec.trackThickness;
    }

    public void setTrackThickness(int trackThickness) {
        if (this.spec.trackThickness != trackThickness) {
            this.spec.trackThickness = trackThickness;
            requestLayout();
        }
    }

    public int[] getIndicatorColor() {
        return this.spec.indicatorColors;
    }

    public void setIndicatorColor(int... indicatorColors) {
        if (indicatorColors.length == 0) {
            indicatorColors = new int[]{MaterialColors.getColor(getContext(), R.attr.colorPrimary, -1)};
        }
        if (!Arrays.equals(getIndicatorColor(), indicatorColors)) {
            this.spec.indicatorColors = indicatorColors;
            getIndeterminateDrawable().getAnimatorDelegate().invalidateSpecValues();
            invalidate();
        }
    }

    public int getTrackColor() {
        return this.spec.trackColor;
    }

    public void setTrackColor(int trackColor) {
        if (this.spec.trackColor != trackColor) {
            this.spec.trackColor = trackColor;
            invalidate();
        }
    }

    public int getTrackCornerRadius() {
        return this.spec.trackCornerRadius;
    }

    public void setTrackCornerRadius(int trackCornerRadius) {
        if (this.spec.trackCornerRadius != trackCornerRadius) {
            BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
            baseProgressIndicatorSpec.trackCornerRadius = Math.min(trackCornerRadius, baseProgressIndicatorSpec.trackThickness / 2);
        }
    }

    public int getShowAnimationBehavior() {
        return this.spec.showAnimationBehavior;
    }

    public void setShowAnimationBehavior(int showAnimationBehavior) {
        this.spec.showAnimationBehavior = showAnimationBehavior;
        invalidate();
    }

    public int getHideAnimationBehavior() {
        return this.spec.hideAnimationBehavior;
    }

    public void setHideAnimationBehavior(int hideAnimationBehavior) {
        this.spec.hideAnimationBehavior = hideAnimationBehavior;
        invalidate();
    }

    public synchronized void setProgress(int progress) {
        if (isIndeterminate()) {
            return;
        }
        setProgressCompat(progress, false);
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (isIndeterminate()) {
            if (getProgressDrawable() != null) {
                this.storedProgress = progress;
                this.storedProgressAnimated = animated;
                this.isIndeterminateModeChangeRequested = true;
                if (!getIndeterminateDrawable().isVisible() || this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(getContext().getContentResolver()) == 0.0f) {
                    this.switchIndeterminateModeCallback.onAnimationEnd(getIndeterminateDrawable());
                    return;
                } else {
                    getIndeterminateDrawable().getAnimatorDelegate().requestCancelAnimatorAfterCurrentCycle();
                    return;
                }
            }
            return;
        }
        super.setProgress(progress);
        if (getProgressDrawable() != null && !animated) {
            getProgressDrawable().jumpToCurrentState();
        }
    }

    public void setVisibilityAfterHide(int visibility) {
        if (visibility != 0 && visibility != 4 && visibility != 8) {
            throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
        }
        this.visibilityAfterHide = visibility;
    }

    public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider animatorDurationScaleProvider) {
        this.animatorDurationScaleProvider = animatorDurationScaleProvider;
        if (getProgressDrawable() != null) {
            getProgressDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().animatorDurationScaleProvider = animatorDurationScaleProvider;
        }
    }

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            BaseProgressIndicator.access$000(BaseProgressIndicator.this);
        }
    }

    class 2 implements Runnable {
        2() {
        }

        public void run() {
            BaseProgressIndicator.access$100(BaseProgressIndicator.this);
            BaseProgressIndicator.access$202(BaseProgressIndicator.this, -1L);
        }
    }

    class 3 extends Animatable2Compat.AnimationCallback {
        3() {
        }

        public void onAnimationEnd(Drawable drawable) {
            BaseProgressIndicator.this.setIndeterminate(false);
            BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
            baseProgressIndicator.setProgressCompat(BaseProgressIndicator.access$300(baseProgressIndicator), BaseProgressIndicator.access$400(BaseProgressIndicator.this));
        }
    }

    class 4 extends Animatable2Compat.AnimationCallback {
        4() {
        }

        public void onAnimationEnd(Drawable drawable) {
            super.onAnimationEnd(drawable);
            if (!BaseProgressIndicator.access$500(BaseProgressIndicator.this)) {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                baseProgressIndicator.setVisibility(BaseProgressIndicator.access$600(baseProgressIndicator));
            }
        }
    }
}
