package com.google.android.material.snackbar;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.snackbar.SnackbarManager;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class BaseTransientBottomBar {
    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;
    private static final int ANIMATION_FADE_IN_DURATION = 150;
    private static final int ANIMATION_FADE_OUT_DURATION = 75;
    public static final int ANIMATION_MODE_FADE = 1;
    public static final int ANIMATION_MODE_SLIDE = 0;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    static final int MSG_DISMISS = 1;
    static final int MSG_SHOW = 0;
    private static final int[] SNACKBAR_STYLE_ATTR;
    private static final String TAG;
    private static final boolean USE_OFFSET_API;
    static final Handler handler;
    private final AccessibilityManager accessibilityManager;
    private Anchor anchor;
    private boolean anchorViewLayoutListenerEnabled;
    private Behavior behavior;
    private final Runnable bottomMarginGestureInsetRunnable;
    private List callbacks;
    private final com.google.android.material.snackbar.ContentViewCallback contentViewCallback;
    private final Context context;
    private int duration;
    private int extraBottomMarginAnchorView;
    private int extraBottomMarginGestureInset;
    private int extraBottomMarginWindowInset;
    private int extraLeftMarginWindowInset;
    private int extraRightMarginWindowInset;
    private boolean gestureInsetBottomIgnored;
    SnackbarManager.Callback managerCallback;
    private boolean pendingShowingView;
    private final ViewGroup targetParent;
    protected final SnackbarBaseLayout view;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationMode {
    }

    @Deprecated
    public interface ContentViewCallback extends com.google.android.material.snackbar.ContentViewCallback {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    static /* synthetic */ Context access$000(BaseTransientBottomBar x0) {
        return x0.context;
    }

    static /* synthetic */ int access$100(BaseTransientBottomBar x0) {
        return x0.getScreenHeight();
    }

    static /* synthetic */ void access$1200(BaseTransientBottomBar x0) {
        x0.startFadeInAnimation();
    }

    static /* synthetic */ void access$1300(BaseTransientBottomBar x0) {
        x0.startSlideInAnimation();
    }

    static /* synthetic */ com.google.android.material.snackbar.ContentViewCallback access$1400(BaseTransientBottomBar x0) {
        return x0.contentViewCallback;
    }

    static /* synthetic */ boolean access$1500() {
        return USE_OFFSET_API;
    }

    static /* synthetic */ boolean access$1600(BaseTransientBottomBar x0) {
        return x0.anchorViewLayoutListenerEnabled;
    }

    static /* synthetic */ void access$1700(BaseTransientBottomBar x0) {
        x0.recalculateAndUpdateMargins();
    }

    static /* synthetic */ int access$200(BaseTransientBottomBar x0) {
        return x0.getViewAbsoluteBottom();
    }

    static /* synthetic */ int access$300(BaseTransientBottomBar x0) {
        return x0.extraBottomMarginGestureInset;
    }

    static /* synthetic */ String access$400() {
        return TAG;
    }

    static /* synthetic */ int access$602(BaseTransientBottomBar x0, int x1) {
        x0.extraBottomMarginWindowInset = x1;
        return x1;
    }

    static /* synthetic */ int access$702(BaseTransientBottomBar x0, int x1) {
        x0.extraLeftMarginWindowInset = x1;
        return x1;
    }

    static /* synthetic */ int access$802(BaseTransientBottomBar x0, int x1) {
        x0.extraRightMarginWindowInset = x1;
        return x1;
    }

    static /* synthetic */ void access$900(BaseTransientBottomBar x0) {
        x0.updateMargins();
    }

    public static abstract class BaseCallback {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public void onDismissed(Object obj, int event) {
        }

        public void onShown(Object obj) {
        }
    }

    static {
        USE_OFFSET_API = Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19;
        SNACKBAR_STYLE_ATTR = new int[]{R.attr.snackbarStyle};
        TAG = BaseTransientBottomBar.class.getSimpleName();
        handler = new Handler(Looper.getMainLooper(), new 1());
    }

    class 1 implements Handler.Callback {
        1() {
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ((BaseTransientBottomBar) message.obj).showView();
                    break;
                case 1:
                    ((BaseTransientBottomBar) message.obj).hideView(message.arg1);
                    break;
            }
            return true;
        }
    }

    class 2 implements Runnable {
        2() {
        }

        public void run() {
            int currentInsetBottom;
            if (BaseTransientBottomBar.this.view == null || BaseTransientBottomBar.access$000(BaseTransientBottomBar.this) == null || (currentInsetBottom = (BaseTransientBottomBar.access$100(BaseTransientBottomBar.this) - BaseTransientBottomBar.access$200(BaseTransientBottomBar.this)) + ((int) BaseTransientBottomBar.this.view.getTranslationY())) >= BaseTransientBottomBar.access$300(BaseTransientBottomBar.this)) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = BaseTransientBottomBar.this.view.getLayoutParams();
            if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                Log.w(BaseTransientBottomBar.access$400(), "Unable to apply gesture inset because layout params are not MarginLayoutParams");
                return;
            }
            ViewGroup.MarginLayoutParams marginParams = layoutParams;
            marginParams.bottomMargin += BaseTransientBottomBar.access$300(BaseTransientBottomBar.this) - currentInsetBottom;
            BaseTransientBottomBar.this.view.requestLayout();
        }
    }

    protected BaseTransientBottomBar(ViewGroup parent, View content, com.google.android.material.snackbar.ContentViewCallback contentViewCallback) {
        this(parent.getContext(), parent, content, contentViewCallback);
    }

    protected BaseTransientBottomBar(Context context, ViewGroup parent, View content, com.google.android.material.snackbar.ContentViewCallback contentViewCallback) {
        this.anchorViewLayoutListenerEnabled = false;
        this.bottomMarginGestureInsetRunnable = new 2();
        this.managerCallback = new 5();
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        }
        if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        }
        if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
        this.targetParent = parent;
        this.contentViewCallback = contentViewCallback;
        this.context = context;
        ThemeEnforcement.checkAppCompatTheme(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        SnackbarBaseLayout inflate = inflater.inflate(getSnackbarBaseLayoutResId(), parent, false);
        this.view = inflate;
        SnackbarBaseLayout.access$500(inflate, this);
        if (content instanceof SnackbarContentLayout) {
            ((SnackbarContentLayout) content).updateActionTextColorAlphaIfNeeded(inflate.getActionTextColorAlpha());
            ((SnackbarContentLayout) content).setMaxInlineActionWidth(inflate.getMaxInlineActionWidth());
        }
        inflate.addView(content);
        ViewCompat.setAccessibilityLiveRegion(inflate, 1);
        ViewCompat.setImportantForAccessibility(inflate, 1);
        ViewCompat.setFitsSystemWindows(inflate, true);
        ViewCompat.setOnApplyWindowInsetsListener(inflate, new 3());
        ViewCompat.setAccessibilityDelegate(inflate, new 4());
        this.accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
    }

    class 3 implements OnApplyWindowInsetsListener {
        3() {
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            BaseTransientBottomBar.access$602(BaseTransientBottomBar.this, insets.getSystemWindowInsetBottom());
            BaseTransientBottomBar.access$702(BaseTransientBottomBar.this, insets.getSystemWindowInsetLeft());
            BaseTransientBottomBar.access$802(BaseTransientBottomBar.this, insets.getSystemWindowInsetRight());
            BaseTransientBottomBar.access$900(BaseTransientBottomBar.this);
            return insets;
        }
    }

    class 4 extends AccessibilityDelegateCompat {
        4() {
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.addAction(1048576);
            info.setDismissable(true);
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (action == 1048576) {
                BaseTransientBottomBar.this.dismiss();
                return true;
            }
            return super.performAccessibilityAction(host, action, args);
        }
    }

    private void updateMargins() {
        ViewGroup.MarginLayoutParams layoutParams = this.view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams) || SnackbarBaseLayout.access$1000(this.view) == null) {
            Log.w(TAG, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }
        if (this.view.getParent() == null) {
            return;
        }
        int extraBottomMargin = getAnchorView() != null ? this.extraBottomMarginAnchorView : this.extraBottomMarginWindowInset;
        ViewGroup.MarginLayoutParams marginParams = layoutParams;
        marginParams.bottomMargin = SnackbarBaseLayout.access$1000(this.view).bottom + extraBottomMargin;
        marginParams.leftMargin = SnackbarBaseLayout.access$1000(this.view).left + this.extraLeftMarginWindowInset;
        marginParams.rightMargin = SnackbarBaseLayout.access$1000(this.view).right + this.extraRightMarginWindowInset;
        marginParams.topMargin = SnackbarBaseLayout.access$1000(this.view).top;
        this.view.requestLayout();
        if (Build.VERSION.SDK_INT >= 29 && shouldUpdateGestureInset()) {
            this.view.removeCallbacks(this.bottomMarginGestureInsetRunnable);
            this.view.post(this.bottomMarginGestureInsetRunnable);
        }
    }

    private boolean shouldUpdateGestureInset() {
        return this.extraBottomMarginGestureInset > 0 && !this.gestureInsetBottomIgnored && isSwipeDismissable();
    }

    private boolean isSwipeDismissable() {
        CoordinatorLayout.LayoutParams layoutParams = this.view.getLayoutParams();
        return (layoutParams instanceof CoordinatorLayout.LayoutParams) && (layoutParams.getBehavior() instanceof SwipeDismissBehavior);
    }

    protected int getSnackbarBaseLayoutResId() {
        return hasSnackbarStyleAttr() ? R.layout.mtrl_layout_snackbar : R.layout.design_layout_snackbar;
    }

    protected boolean hasSnackbarStyleAttr() {
        TypedArray a = this.context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int snackbarStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarStyleResId != -1;
    }

    public BaseTransientBottomBar setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int getDuration() {
        return this.duration;
    }

    public BaseTransientBottomBar setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored;
        return this;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public int getAnimationMode() {
        return this.view.getAnimationMode();
    }

    public BaseTransientBottomBar setAnimationMode(int animationMode) {
        this.view.setAnimationMode(animationMode);
        return this;
    }

    public View getAnchorView() {
        Anchor anchor = this.anchor;
        if (anchor == null) {
            return null;
        }
        return anchor.getAnchorView();
    }

    public BaseTransientBottomBar setAnchorView(View anchorView) {
        Anchor anchor = this.anchor;
        if (anchor != null) {
            anchor.unanchor();
        }
        this.anchor = anchorView == null ? null : Anchor.anchor(this, anchorView);
        return this;
    }

    public BaseTransientBottomBar setAnchorView(int anchorViewId) {
        View anchorView = this.targetParent.findViewById(anchorViewId);
        if (anchorView == null) {
            throw new IllegalArgumentException("Unable to find anchor view with id: " + anchorViewId);
        }
        return setAnchorView(anchorView);
    }

    public boolean isAnchorViewLayoutListenerEnabled() {
        return this.anchorViewLayoutListenerEnabled;
    }

    public void setAnchorViewLayoutListenerEnabled(boolean anchorViewLayoutListenerEnabled) {
        this.anchorViewLayoutListenerEnabled = anchorViewLayoutListenerEnabled;
    }

    public BaseTransientBottomBar setBehavior(Behavior behavior) {
        this.behavior = behavior;
        return this;
    }

    public Behavior getBehavior() {
        return this.behavior;
    }

    public Context getContext() {
        return this.context;
    }

    public View getView() {
        return this.view;
    }

    public void show() {
        SnackbarManager.getInstance().show(getDuration(), this.managerCallback);
    }

    public void dismiss() {
        dispatchDismiss(3);
    }

    protected void dispatchDismiss(int event) {
        SnackbarManager.getInstance().dismiss(this.managerCallback, event);
    }

    public BaseTransientBottomBar addCallback(BaseCallback baseCallback) {
        if (baseCallback == null) {
            return this;
        }
        if (this.callbacks == null) {
            this.callbacks = new ArrayList();
        }
        this.callbacks.add(baseCallback);
        return this;
    }

    public BaseTransientBottomBar removeCallback(BaseCallback baseCallback) {
        if (baseCallback == null) {
            return this;
        }
        List list = this.callbacks;
        if (list == null) {
            return this;
        }
        list.remove(baseCallback);
        return this;
    }

    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(this.managerCallback);
    }

    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(this.managerCallback);
    }

    class 5 implements SnackbarManager.Callback {
        5() {
        }

        public void show() {
            BaseTransientBottomBar.handler.sendMessage(BaseTransientBottomBar.handler.obtainMessage(0, BaseTransientBottomBar.this));
        }

        public void dismiss(int event) {
            BaseTransientBottomBar.handler.sendMessage(BaseTransientBottomBar.handler.obtainMessage(1, event, 0, BaseTransientBottomBar.this));
        }
    }

    protected SwipeDismissBehavior getNewBehavior() {
        return new Behavior();
    }

    final void showView() {
        if (this.view.getParent() == null) {
            ViewGroup.LayoutParams lp = this.view.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                setUpBehavior((CoordinatorLayout.LayoutParams) lp);
            }
            this.view.addToTargetParent(this.targetParent);
            recalculateAndUpdateMargins();
            this.view.setVisibility(4);
        }
        if (ViewCompat.isLaidOut(this.view)) {
            showViewImpl();
        } else {
            this.pendingShowingView = true;
        }
    }

    void onAttachedToWindow() {
        WindowInsets insets;
        if (Build.VERSION.SDK_INT >= 29 && (insets = this.view.getRootWindowInsets()) != null) {
            this.extraBottomMarginGestureInset = insets.getMandatorySystemGestureInsets().bottom;
            updateMargins();
        }
    }

    void onDetachedFromWindow() {
        if (isShownOrQueued()) {
            handler.post(new 6());
        }
    }

    class 6 implements Runnable {
        6() {
        }

        public void run() {
            BaseTransientBottomBar.this.onViewHidden(3);
        }
    }

    void onLayoutChange() {
        if (this.pendingShowingView) {
            showViewImpl();
            this.pendingShowingView = false;
        }
    }

    private void showViewImpl() {
        if (shouldAnimate()) {
            animateViewIn();
            return;
        }
        if (this.view.getParent() != null) {
            this.view.setVisibility(0);
        }
        onViewShown();
    }

    private int getViewAbsoluteBottom() {
        int[] absoluteLocation = new int[2];
        this.view.getLocationOnScreen(absoluteLocation);
        return absoluteLocation[1] + this.view.getHeight();
    }

    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) this.context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void setUpBehavior(CoordinatorLayout.LayoutParams lp) {
        SwipeDismissBehavior<? extends View> behavior = this.behavior;
        if (behavior == null) {
            behavior = getNewBehavior();
        }
        if (behavior instanceof Behavior) {
            Behavior.access$1100((Behavior) behavior, this);
        }
        behavior.setListener(new 7());
        lp.setBehavior(behavior);
        if (getAnchorView() == null) {
            lp.insetEdge = 80;
        }
    }

    class 7 implements SwipeDismissBehavior.OnDismissListener {
        7() {
        }

        public void onDismiss(View view) {
            if (view.getParent() != null) {
                view.setVisibility(8);
            }
            BaseTransientBottomBar.this.dispatchDismiss(0);
        }

        public void onDragStateChanged(int state) {
            switch (state) {
                case 0:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.managerCallback);
                    break;
                case 1:
                case 2:
                    SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.managerCallback);
                    break;
            }
        }
    }

    private void recalculateAndUpdateMargins() {
        this.extraBottomMarginAnchorView = calculateBottomMarginForAnchorView();
        updateMargins();
    }

    private int calculateBottomMarginForAnchorView() {
        if (getAnchorView() == null) {
            return 0;
        }
        int[] anchorViewLocation = new int[2];
        getAnchorView().getLocationOnScreen(anchorViewLocation);
        int anchorViewAbsoluteYTop = anchorViewLocation[1];
        int[] targetParentLocation = new int[2];
        this.targetParent.getLocationOnScreen(targetParentLocation);
        int targetParentAbsoluteYBottom = targetParentLocation[1] + this.targetParent.getHeight();
        return targetParentAbsoluteYBottom - anchorViewAbsoluteYTop;
    }

    class 8 implements Runnable {
        8() {
        }

        public void run() {
            if (BaseTransientBottomBar.this.view == null) {
                return;
            }
            if (BaseTransientBottomBar.this.view.getParent() != null) {
                BaseTransientBottomBar.this.view.setVisibility(0);
            }
            if (BaseTransientBottomBar.this.view.getAnimationMode() == 1) {
                BaseTransientBottomBar.access$1200(BaseTransientBottomBar.this);
            } else {
                BaseTransientBottomBar.access$1300(BaseTransientBottomBar.this);
            }
        }
    }

    void animateViewIn() {
        this.view.post(new 8());
    }

    private void animateViewOut(int event) {
        if (this.view.getAnimationMode() == 1) {
            startFadeOutAnimation(event);
        } else {
            startSlideOutAnimation(event);
        }
    }

    private void startFadeInAnimation() {
        Animator alphaAnimator = getAlphaAnimator(0.0f, 1.0f);
        Animator scaleAnimator = getScaleAnimator(0.8f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{alphaAnimator, scaleAnimator});
        animatorSet.setDuration(150L);
        animatorSet.addListener(new 9());
        animatorSet.start();
    }

    class 9 extends AnimatorListenerAdapter {
        9() {
        }

        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.onViewShown();
        }
    }

    private void startFadeOutAnimation(int event) {
        ValueAnimator animator = getAlphaAnimator(1.0f, 0.0f);
        animator.setDuration(75L);
        animator.addListener(new 10(event));
        animator.start();
    }

    class 10 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$event;

        10(int i) {
            this.val$event = i;
        }

        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.onViewHidden(this.val$event);
        }
    }

    private ValueAnimator getAlphaAnimator(float... alphaValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(alphaValues);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.addUpdateListener(new 11());
        return animator;
    }

    class 11 implements ValueAnimator.AnimatorUpdateListener {
        11() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            BaseTransientBottomBar.this.view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    private ValueAnimator getScaleAnimator(float... scaleValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(scaleValues);
        animator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animator.addUpdateListener(new 12());
        return animator;
    }

    class 12 implements ValueAnimator.AnimatorUpdateListener {
        12() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float scale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            BaseTransientBottomBar.this.view.setScaleX(scale);
            BaseTransientBottomBar.this.view.setScaleY(scale);
        }
    }

    private void startSlideInAnimation() {
        int translationYBottom = getTranslationYBottom();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom(this.view, translationYBottom);
        } else {
            this.view.setTranslationY(translationYBottom);
        }
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{translationYBottom, 0});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250L);
        animator.addListener(new 13());
        animator.addUpdateListener(new 14(translationYBottom));
        animator.start();
    }

    class 13 extends AnimatorListenerAdapter {
        13() {
        }

        public void onAnimationStart(Animator animator) {
            BaseTransientBottomBar.access$1400(BaseTransientBottomBar.this).animateContentIn(70, 180);
        }

        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.onViewShown();
        }
    }

    class 14 implements ValueAnimator.AnimatorUpdateListener {
        private int previousAnimatedIntValue;
        final /* synthetic */ int val$translationYBottom;

        14(int i) {
            this.val$translationYBottom = i;
            this.previousAnimatedIntValue = i;
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
            if (BaseTransientBottomBar.access$1500()) {
                ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
            } else {
                BaseTransientBottomBar.this.view.setTranslationY(currentAnimatedIntValue);
            }
            this.previousAnimatedIntValue = currentAnimatedIntValue;
        }
    }

    private void startSlideOutAnimation(int event) {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{0, getTranslationYBottom()});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250L);
        animator.addListener(new 15(event));
        animator.addUpdateListener(new 16());
        animator.start();
    }

    class 15 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$event;

        15(int i) {
            this.val$event = i;
        }

        public void onAnimationStart(Animator animator) {
            BaseTransientBottomBar.access$1400(BaseTransientBottomBar.this).animateContentOut(0, 180);
        }

        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.onViewHidden(this.val$event);
        }
    }

    class 16 implements ValueAnimator.AnimatorUpdateListener {
        private int previousAnimatedIntValue = 0;

        16() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            int currentAnimatedIntValue = ((Integer) animator.getAnimatedValue()).intValue();
            if (BaseTransientBottomBar.access$1500()) {
                ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
            } else {
                BaseTransientBottomBar.this.view.setTranslationY(currentAnimatedIntValue);
            }
            this.previousAnimatedIntValue = currentAnimatedIntValue;
        }
    }

    private int getTranslationYBottom() {
        int translationY = this.view.getHeight();
        ViewGroup.MarginLayoutParams layoutParams = this.view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return translationY + layoutParams.bottomMargin;
        }
        return translationY;
    }

    final void hideView(int event) {
        if (shouldAnimate() && this.view.getVisibility() == 0) {
            animateViewOut(event);
        } else {
            onViewHidden(event);
        }
    }

    void onViewShown() {
        SnackbarManager.getInstance().onShown(this.managerCallback);
        List list = this.callbacks;
        if (list != null) {
            int callbackCount = list.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                ((BaseCallback) this.callbacks.get(i)).onShown(this);
            }
        }
    }

    void onViewHidden(int event) {
        SnackbarManager.getInstance().onDismissed(this.managerCallback);
        List list = this.callbacks;
        if (list != null) {
            int callbackCount = list.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                ((BaseCallback) this.callbacks.get(i)).onDismissed(this, event);
            }
        }
        ViewGroup parent = this.view.getParent();
        if (parent instanceof ViewGroup) {
            parent.removeView(this.view);
        }
    }

    boolean shouldAnimate() {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager == null) {
            return true;
        }
        List<AccessibilityServiceInfo> serviceList = accessibilityManager.getEnabledAccessibilityServiceList(1);
        return serviceList != null && serviceList.isEmpty();
    }

    protected static class SnackbarBaseLayout extends FrameLayout {
        private static final View.OnTouchListener consumeAllTouchListener = new 1();
        private final float actionTextColorAlpha;
        private boolean addingToTargetParent;
        private int animationMode;
        private final float backgroundOverlayColorAlpha;
        private ColorStateList backgroundTint;
        private PorterDuff.Mode backgroundTintMode;
        private BaseTransientBottomBar baseTransientBottomBar;
        private final int maxInlineActionWidth;
        private final int maxWidth;
        private Rect originalMargins;

        static /* synthetic */ Rect access$1000(SnackbarBaseLayout x0) {
            return x0.originalMargins;
        }

        static /* synthetic */ void access$500(SnackbarBaseLayout x0, BaseTransientBottomBar x1) {
            x0.setBaseTransientBottomBar(x1);
        }

        class 1 implements View.OnTouchListener {
            1() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        }

        protected SnackbarBaseLayout(Context context) {
            this(context, null);
        }

        protected SnackbarBaseLayout(Context context, AttributeSet attrs) {
            super(MaterialThemeOverlay.wrap(context, attrs, 0, 0), attrs);
            Context context2 = getContext();
            TypedArray a = context2.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            this.animationMode = a.getInt(R.styleable.SnackbarLayout_animationMode, 0);
            this.backgroundOverlayColorAlpha = a.getFloat(R.styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
            setBackgroundTintList(MaterialResources.getColorStateList(context2, a, R.styleable.SnackbarLayout_backgroundTint));
            setBackgroundTintMode(ViewUtils.parseTintMode(a.getInt(R.styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
            this.actionTextColorAlpha = a.getFloat(R.styleable.SnackbarLayout_actionTextColorAlpha, 1.0f);
            this.maxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
            this.maxInlineActionWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
            a.recycle();
            setOnTouchListener(consumeAllTouchListener);
            setFocusable(true);
            if (getBackground() == null) {
                ViewCompat.setBackground(this, createThemedBackground());
            }
        }

        public void setBackground(Drawable drawable) {
            setBackgroundDrawable(drawable);
        }

        public void setBackgroundDrawable(Drawable drawable) {
            if (drawable != null && this.backgroundTint != null) {
                drawable = DrawableCompat.wrap(drawable.mutate());
                DrawableCompat.setTintList(drawable, this.backgroundTint);
                DrawableCompat.setTintMode(drawable, this.backgroundTintMode);
            }
            super.setBackgroundDrawable(drawable);
        }

        public void setBackgroundTintList(ColorStateList backgroundTint) {
            this.backgroundTint = backgroundTint;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintList(wrappedBackground, backgroundTint);
                DrawableCompat.setTintMode(wrappedBackground, this.backgroundTintMode);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        public void setBackgroundTintMode(PorterDuff.Mode backgroundTintMode) {
            this.backgroundTintMode = backgroundTintMode;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
            super.setOnClickListener(onClickListener);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.maxWidth > 0) {
                int measuredWidth = getMeasuredWidth();
                int i = this.maxWidth;
                if (measuredWidth > i) {
                    int widthMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
                    super.onMeasure(widthMeasureSpec2, heightMeasureSpec);
                }
            }
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null) {
                baseTransientBottomBar.onLayoutChange();
            }
        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null) {
                baseTransientBottomBar.onAttachedToWindow();
            }
            ViewCompat.requestApplyInsets(this);
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
            if (baseTransientBottomBar != null) {
                baseTransientBottomBar.onDetachedFromWindow();
            }
        }

        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);
            if (!this.addingToTargetParent && (params instanceof ViewGroup.MarginLayoutParams)) {
                updateOriginalMargins((ViewGroup.MarginLayoutParams) params);
                BaseTransientBottomBar baseTransientBottomBar = this.baseTransientBottomBar;
                if (baseTransientBottomBar != null) {
                    BaseTransientBottomBar.access$900(baseTransientBottomBar);
                }
            }
        }

        int getAnimationMode() {
            return this.animationMode;
        }

        void setAnimationMode(int animationMode) {
            this.animationMode = animationMode;
        }

        float getBackgroundOverlayColorAlpha() {
            return this.backgroundOverlayColorAlpha;
        }

        float getActionTextColorAlpha() {
            return this.actionTextColorAlpha;
        }

        int getMaxWidth() {
            return this.maxWidth;
        }

        int getMaxInlineActionWidth() {
            return this.maxInlineActionWidth;
        }

        void addToTargetParent(ViewGroup targetParent) {
            this.addingToTargetParent = true;
            targetParent.addView(this);
            this.addingToTargetParent = false;
        }

        private void setBaseTransientBottomBar(BaseTransientBottomBar baseTransientBottomBar) {
            this.baseTransientBottomBar = baseTransientBottomBar;
        }

        private void updateOriginalMargins(ViewGroup.MarginLayoutParams params) {
            this.originalMargins = new Rect(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin);
        }

        private Drawable createThemedBackground() {
            float cornerRadius = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
            GradientDrawable background = new GradientDrawable();
            background.setShape(0);
            background.setCornerRadius(cornerRadius);
            int backgroundColor = MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha());
            background.setColor(backgroundColor);
            if (this.backgroundTint != null) {
                Drawable wrappedDrawable = DrawableCompat.wrap(background);
                DrawableCompat.setTintList(wrappedDrawable, this.backgroundTint);
                return wrappedDrawable;
            }
            return DrawableCompat.wrap(background);
        }
    }

    public static class Behavior extends SwipeDismissBehavior {
        private final BehaviorDelegate delegate = new BehaviorDelegate(this);

        static /* synthetic */ void access$1100(Behavior x0, BaseTransientBottomBar x1) {
            x0.setBaseTransientBottomBar(x1);
        }

        private void setBaseTransientBottomBar(BaseTransientBottomBar baseTransientBottomBar) {
            this.delegate.setBaseTransientBottomBar(baseTransientBottomBar);
        }

        public boolean canSwipeDismissView(View child) {
            return this.delegate.canSwipeDismissView(child);
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
            this.delegate.onInterceptTouchEvent(parent, child, event);
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    public static class BehaviorDelegate {
        private SnackbarManager.Callback managerCallback;

        public BehaviorDelegate(SwipeDismissBehavior swipeDismissBehavior) {
            swipeDismissBehavior.setStartAlphaSwipeDistance(0.1f);
            swipeDismissBehavior.setEndAlphaSwipeDistance(0.6f);
            swipeDismissBehavior.setSwipeDirection(0);
        }

        public void setBaseTransientBottomBar(BaseTransientBottomBar baseTransientBottomBar) {
            this.managerCallback = baseTransientBottomBar.managerCallback;
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        public void onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
            switch (event.getActionMasked()) {
                case 0:
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        SnackbarManager.getInstance().pauseTimeout(this.managerCallback);
                        break;
                    }
                    break;
                case 1:
                case 3:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(this.managerCallback);
                    break;
            }
        }
    }

    static class Anchor implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
        private final WeakReference anchorView;
        private final WeakReference transientBottomBar;

        static Anchor anchor(BaseTransientBottomBar transientBottomBar, View anchorView) {
            Anchor anchor = new Anchor(transientBottomBar, anchorView);
            if (ViewCompat.isAttachedToWindow(anchorView)) {
                ViewUtils.addOnGlobalLayoutListener(anchorView, anchor);
            }
            anchorView.addOnAttachStateChangeListener(anchor);
            return anchor;
        }

        private Anchor(BaseTransientBottomBar transientBottomBar, View anchorView) {
            this.transientBottomBar = new WeakReference(transientBottomBar);
            this.anchorView = new WeakReference(anchorView);
        }

        public void onViewAttachedToWindow(View anchorView) {
            if (unanchorIfNoTransientBottomBar()) {
                return;
            }
            ViewUtils.addOnGlobalLayoutListener(anchorView, this);
        }

        public void onViewDetachedFromWindow(View anchorView) {
            if (unanchorIfNoTransientBottomBar()) {
                return;
            }
            ViewUtils.removeOnGlobalLayoutListener(anchorView, this);
        }

        public void onGlobalLayout() {
            if (unanchorIfNoTransientBottomBar() || !BaseTransientBottomBar.access$1600((BaseTransientBottomBar) this.transientBottomBar.get())) {
                return;
            }
            BaseTransientBottomBar.access$1700((BaseTransientBottomBar) this.transientBottomBar.get());
        }

        View getAnchorView() {
            return (View) this.anchorView.get();
        }

        private boolean unanchorIfNoTransientBottomBar() {
            if (this.transientBottomBar.get() == null) {
                unanchor();
                return true;
            }
            return false;
        }

        void unanchor() {
            if (this.anchorView.get() != null) {
                ((View) this.anchorView.get()).removeOnAttachStateChangeListener(this);
                ViewUtils.removeOnGlobalLayoutListener((View) this.anchorView.get(), this);
            }
            this.anchorView.clear();
            this.transientBottomBar.clear();
        }
    }
}
