package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class BottomAppBar extends Toolbar implements CoordinatorLayout.AttachedBehavior {
    private static final long ANIMATION_DURATION = 300;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_BottomAppBar;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    private static final int NO_MENU_RES_ID = 0;
    private int animatingModeChangeCounter;
    private ArrayList animationListeners;
    private Behavior behavior;
    private int bottomInset;
    private int fabAlignmentMode;
    AnimatorListenerAdapter fabAnimationListener;
    private int fabAnimationMode;
    private boolean fabAttached;
    private final int fabOffsetEndMode;
    TransformationCallback fabTransformationCallback;
    private boolean hideOnScroll;
    private int leftInset;
    private final MaterialShapeDrawable materialShapeDrawable;
    private boolean menuAnimatingWithFabAlignmentMode;
    private Animator menuAnimator;
    private Animator modeAnimator;
    private Integer navigationIconTint;
    private final boolean paddingBottomSystemWindowInsets;
    private final boolean paddingLeftSystemWindowInsets;
    private final boolean paddingRightSystemWindowInsets;
    private int pendingMenuResId;
    private int rightInset;

    interface AnimationListener {
        void onAnimationEnd(BottomAppBar bottomAppBar);

        void onAnimationStart(BottomAppBar bottomAppBar);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAlignmentMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FabAnimationMode {
    }

    static /* synthetic */ boolean access$000(BottomAppBar x0) {
        return x0.menuAnimatingWithFabAlignmentMode;
    }

    static /* synthetic */ boolean access$002(BottomAppBar x0, boolean x1) {
        x0.menuAnimatingWithFabAlignmentMode = x1;
        return x1;
    }

    static /* synthetic */ int access$100(BottomAppBar x0) {
        return x0.fabAlignmentMode;
    }

    static /* synthetic */ boolean access$1000(BottomAppBar x0) {
        return x0.paddingRightSystemWindowInsets;
    }

    static /* synthetic */ int access$1100(BottomAppBar x0) {
        return x0.rightInset;
    }

    static /* synthetic */ int access$1102(BottomAppBar x0, int x1) {
        x0.rightInset = x1;
        return x1;
    }

    static /* synthetic */ void access$1200(BottomAppBar x0) {
        x0.cancelAnimations();
    }

    static /* synthetic */ void access$1300(BottomAppBar x0) {
        x0.setCutoutState();
    }

    static /* synthetic */ void access$1400(BottomAppBar x0) {
        x0.setActionMenuViewPosition();
    }

    static /* synthetic */ void access$1500(BottomAppBar x0) {
        x0.dispatchAnimationStart();
    }

    static /* synthetic */ void access$1600(BottomAppBar x0) {
        x0.dispatchAnimationEnd();
    }

    static /* synthetic */ Animator access$1702(BottomAppBar x0, Animator x1) {
        x0.modeAnimator = x1;
        return x1;
    }

    static /* synthetic */ float access$1800(BottomAppBar x0, int x1) {
        return x0.getFabTranslationX(x1);
    }

    static /* synthetic */ Animator access$1902(BottomAppBar x0, Animator x1) {
        x0.menuAnimator = x1;
        return x1;
    }

    static /* synthetic */ boolean access$200(BottomAppBar x0) {
        return x0.fabAttached;
    }

    static /* synthetic */ int access$2000(BottomAppBar x0) {
        return x0.pendingMenuResId;
    }

    static /* synthetic */ void access$2100(BottomAppBar x0, ActionMenuView x1, int x2, boolean x3, boolean x4) {
        x0.translateActionMenuView(x1, x2, x3, x4);
    }

    static /* synthetic */ FloatingActionButton access$2200(BottomAppBar x0) {
        return x0.findDependentFab();
    }

    static /* synthetic */ float access$2300(BottomAppBar x0) {
        return x0.getFabTranslationX();
    }

    static /* synthetic */ int access$2700(BottomAppBar x0) {
        return x0.getBottomInset();
    }

    static /* synthetic */ int access$2800(BottomAppBar x0) {
        return x0.getLeftInset();
    }

    static /* synthetic */ int access$2900(BottomAppBar x0) {
        return x0.getRightInset();
    }

    static /* synthetic */ void access$300(BottomAppBar x0, int x1, boolean x2) {
        x0.maybeAnimateMenuView(x1, x2);
    }

    static /* synthetic */ int access$3000(BottomAppBar x0) {
        return x0.fabOffsetEndMode;
    }

    static /* synthetic */ View access$3100(BottomAppBar x0) {
        return x0.findDependentView();
    }

    static /* synthetic */ void access$3200(BottomAppBar x0, FloatingActionButton x1) {
        x0.addFabAnimationListeners(x1);
    }

    static /* synthetic */ MaterialShapeDrawable access$400(BottomAppBar x0) {
        return x0.materialShapeDrawable;
    }

    static /* synthetic */ BottomAppBarTopEdgeTreatment access$500(BottomAppBar x0) {
        return x0.getTopEdgeTreatment();
    }

    static /* synthetic */ boolean access$600(BottomAppBar x0) {
        return x0.paddingBottomSystemWindowInsets;
    }

    static /* synthetic */ int access$702(BottomAppBar x0, int x1) {
        x0.bottomInset = x1;
        return x1;
    }

    static /* synthetic */ boolean access$800(BottomAppBar x0) {
        return x0.paddingLeftSystemWindowInsets;
    }

    static /* synthetic */ int access$900(BottomAppBar x0) {
        return x0.leftInset;
    }

    static /* synthetic */ int access$902(BottomAppBar x0, int x1) {
        x0.leftInset = x1;
        return x1;
    }

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationStart(Animator animation) {
            if (!BottomAppBar.access$000(BottomAppBar.this)) {
                BottomAppBar bottomAppBar = BottomAppBar.this;
                BottomAppBar.access$300(bottomAppBar, BottomAppBar.access$100(bottomAppBar), BottomAppBar.access$200(BottomAppBar.this));
            }
        }
    }

    class 2 implements TransformationCallback {
        2() {
        }

        public void onScaleChanged(FloatingActionButton fab) {
            BottomAppBar.access$400(BottomAppBar.this).setInterpolation(fab.getVisibility() == 0 ? fab.getScaleY() : 0.0f);
        }

        public void onTranslationChanged(FloatingActionButton fab) {
            float horizontalOffset = fab.getTranslationX();
            if (BottomAppBar.access$500(BottomAppBar.this).getHorizontalOffset() != horizontalOffset) {
                BottomAppBar.access$500(BottomAppBar.this).setHorizontalOffset(horizontalOffset);
                BottomAppBar.access$400(BottomAppBar.this).invalidateSelf();
            }
            float verticalOffset = Math.max(0.0f, -fab.getTranslationY());
            if (BottomAppBar.access$500(BottomAppBar.this).getCradleVerticalOffset() != verticalOffset) {
                BottomAppBar.access$500(BottomAppBar.this).setCradleVerticalOffset(verticalOffset);
                BottomAppBar.access$400(BottomAppBar.this).invalidateSelf();
            }
            BottomAppBar.access$400(BottomAppBar.this).setInterpolation(fab.getVisibility() == 0 ? fab.getScaleY() : 0.0f);
        }
    }

    public BottomAppBar(Context context) {
        this(context, null);
    }

    public BottomAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.bottomAppBarStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public BottomAppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        int i = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, i), attrs, defStyleAttr);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.materialShapeDrawable = materialShapeDrawable;
        this.animatingModeChangeCounter = 0;
        this.pendingMenuResId = 0;
        this.menuAnimatingWithFabAlignmentMode = false;
        this.fabAttached = true;
        this.fabAnimationListener = new 1();
        this.fabTransformationCallback = new 2();
        Context context2 = getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.BottomAppBar, defStyleAttr, i, new int[0]);
        ColorStateList backgroundTint = MaterialResources.getColorStateList(context2, a, R.styleable.BottomAppBar_backgroundTint);
        if (a.hasValue(R.styleable.BottomAppBar_navigationIconTint)) {
            setNavigationIconTint(a.getColor(R.styleable.BottomAppBar_navigationIconTint, -1));
        }
        int elevation = a.getDimensionPixelSize(R.styleable.BottomAppBar_elevation, 0);
        float fabCradleMargin = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
        float fabCornerRadius = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
        float fabVerticalOffset = a.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
        this.fabAlignmentMode = a.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.fabAnimationMode = a.getInt(R.styleable.BottomAppBar_fabAnimationMode, 0);
        this.hideOnScroll = a.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
        this.paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
        a.recycle();
        this.fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        EdgeTreatment topEdgeTreatment = new BottomAppBarTopEdgeTreatment(fabCradleMargin, fabCornerRadius, fabVerticalOffset);
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder().setTopEdge(topEdgeTreatment).build();
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.setShadowCompatibilityMode(2);
        materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
        materialShapeDrawable.initializeElevationOverlay(context2);
        setElevation(elevation);
        DrawableCompat.setTintList(materialShapeDrawable, backgroundTint);
        ViewCompat.setBackground(this, materialShapeDrawable);
        ViewUtils.doOnApplyWindowInsets(this, attrs, defStyleAttr, i, new 3());
    }

    class 3 implements ViewUtils.OnApplyWindowInsetsListener {
        3() {
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
            boolean leftInsetsChanged = false;
            boolean rightInsetsChanged = false;
            if (BottomAppBar.access$600(BottomAppBar.this)) {
                BottomAppBar.access$702(BottomAppBar.this, insets.getSystemWindowInsetBottom());
            }
            if (BottomAppBar.access$800(BottomAppBar.this)) {
                leftInsetsChanged = BottomAppBar.access$900(BottomAppBar.this) != insets.getSystemWindowInsetLeft();
                BottomAppBar.access$902(BottomAppBar.this, insets.getSystemWindowInsetLeft());
            }
            if (BottomAppBar.access$1000(BottomAppBar.this)) {
                rightInsetsChanged = BottomAppBar.access$1100(BottomAppBar.this) != insets.getSystemWindowInsetRight();
                BottomAppBar.access$1102(BottomAppBar.this, insets.getSystemWindowInsetRight());
            }
            if (leftInsetsChanged || rightInsetsChanged) {
                BottomAppBar.access$1200(BottomAppBar.this);
                BottomAppBar.access$1300(BottomAppBar.this);
                BottomAppBar.access$1400(BottomAppBar.this);
            }
            return insets;
        }
    }

    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(maybeTintNavigationIcon(drawable));
    }

    public void setNavigationIconTint(int navigationIconTint) {
        this.navigationIconTint = Integer.valueOf(navigationIconTint);
        Drawable navigationIcon = getNavigationIcon();
        if (navigationIcon != null) {
            setNavigationIcon(navigationIcon);
        }
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public void setFabAlignmentMode(int fabAlignmentMode) {
        setFabAlignmentModeAndReplaceMenu(fabAlignmentMode, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int fabAlignmentMode, int newMenu) {
        this.pendingMenuResId = newMenu;
        this.menuAnimatingWithFabAlignmentMode = true;
        maybeAnimateMenuView(fabAlignmentMode, this.fabAttached);
        maybeAnimateModeChange(fabAlignmentMode);
        this.fabAlignmentMode = fabAlignmentMode;
    }

    public int getFabAnimationMode() {
        return this.fabAnimationMode;
    }

    public void setFabAnimationMode(int fabAnimationMode) {
        this.fabAnimationMode = fabAnimationMode;
    }

    public void setBackgroundTint(ColorStateList backgroundTint) {
        DrawableCompat.setTintList(this.materialShapeDrawable, backgroundTint);
    }

    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    public float getFabCradleMargin() {
        return getTopEdgeTreatment().getFabCradleMargin();
    }

    public void setFabCradleMargin(float cradleMargin) {
        if (cradleMargin != getFabCradleMargin()) {
            getTopEdgeTreatment().setFabCradleMargin(cradleMargin);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getFabCradleRoundedCornerRadius() {
        return getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
    }

    public void setFabCradleRoundedCornerRadius(float roundedCornerRadius) {
        if (roundedCornerRadius != getFabCradleRoundedCornerRadius()) {
            getTopEdgeTreatment().setFabCradleRoundedCornerRadius(roundedCornerRadius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    public float getCradleVerticalOffset() {
        return getTopEdgeTreatment().getCradleVerticalOffset();
    }

    public void setCradleVerticalOffset(float verticalOffset) {
        if (verticalOffset != getCradleVerticalOffset()) {
            getTopEdgeTreatment().setCradleVerticalOffset(verticalOffset);
            this.materialShapeDrawable.invalidateSelf();
            setCutoutState();
        }
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    public void setHideOnScroll(boolean hide) {
        this.hideOnScroll = hide;
    }

    public void performHide() {
        performHide(true);
    }

    public void performHide(boolean animate) {
        getBehavior().slideDown(this, animate);
    }

    public void performShow() {
        performShow(true);
    }

    public void performShow(boolean animate) {
        getBehavior().slideUp(this, animate);
    }

    public boolean isScrolledDown() {
        return getBehavior().isScrolledDown();
    }

    public boolean isScrolledUp() {
        return getBehavior().isScrolledUp();
    }

    public void setElevation(float elevation) {
        this.materialShapeDrawable.setElevation(elevation);
        int topShadowHeight = this.materialShapeDrawable.getShadowRadius() - this.materialShapeDrawable.getShadowOffsetY();
        getBehavior().setAdditionalHiddenOffsetY(this, topShadowHeight);
    }

    public void replaceMenu(int newMenu) {
        if (newMenu != 0) {
            this.pendingMenuResId = 0;
            getMenu().clear();
            inflateMenu(newMenu);
        }
    }

    void addAnimationListener(AnimationListener listener) {
        if (this.animationListeners == null) {
            this.animationListeners = new ArrayList();
        }
        this.animationListeners.add(listener);
    }

    void removeAnimationListener(AnimationListener listener) {
        ArrayList arrayList = this.animationListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(listener);
    }

    private void dispatchAnimationStart() {
        ArrayList arrayList;
        int i = this.animatingModeChangeCounter;
        this.animatingModeChangeCounter = i + 1;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AnimationListener listener = (AnimationListener) it.next();
                listener.onAnimationStart(this);
            }
        }
    }

    private void dispatchAnimationEnd() {
        ArrayList arrayList;
        int i = this.animatingModeChangeCounter - 1;
        this.animatingModeChangeCounter = i;
        if (i == 0 && (arrayList = this.animationListeners) != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AnimationListener listener = (AnimationListener) it.next();
                listener.onAnimationEnd(this);
            }
        }
    }

    boolean setFabDiameter(int diameter) {
        if (diameter != getTopEdgeTreatment().getFabDiameter()) {
            getTopEdgeTreatment().setFabDiameter(diameter);
            this.materialShapeDrawable.invalidateSelf();
            return true;
        }
        return false;
    }

    void setFabCornerSize(float radius) {
        if (radius != getTopEdgeTreatment().getFabCornerRadius()) {
            getTopEdgeTreatment().setFabCornerSize(radius);
            this.materialShapeDrawable.invalidateSelf();
        }
    }

    private void maybeAnimateModeChange(int targetMode) {
        if (this.fabAlignmentMode == targetMode || !ViewCompat.isLaidOut(this)) {
            return;
        }
        Animator animator = this.modeAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ArrayList arrayList = new ArrayList();
        if (this.fabAnimationMode == 1) {
            createFabTranslationXAnimation(targetMode, arrayList);
        } else {
            createFabDefaultXAnimation(targetMode, arrayList);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(arrayList);
        this.modeAnimator = set;
        set.addListener(new 4());
        this.modeAnimator.start();
    }

    class 4 extends AnimatorListenerAdapter {
        4() {
        }

        public void onAnimationStart(Animator animation) {
            BottomAppBar.access$1500(BottomAppBar.this);
        }

        public void onAnimationEnd(Animator animation) {
            BottomAppBar.access$1600(BottomAppBar.this);
            BottomAppBar.access$1702(BottomAppBar.this, null);
        }
    }

    private FloatingActionButton findDependentFab() {
        FloatingActionButton findDependentView = findDependentView();
        if (findDependentView instanceof FloatingActionButton) {
            return findDependentView;
        }
        return null;
    }

    private View findDependentView() {
        if (!(getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        List<View> dependents = getParent().getDependents(this);
        for (View v : dependents) {
            if ((v instanceof FloatingActionButton) || (v instanceof ExtendedFloatingActionButton)) {
                return v;
            }
        }
        return null;
    }

    private boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton fab = findDependentFab();
        return fab != null && fab.isOrWillBeShown();
    }

    protected void createFabDefaultXAnimation(int targetMode, List list) {
        FloatingActionButton fab = findDependentFab();
        if (fab == null || fab.isOrWillBeHidden()) {
            return;
        }
        dispatchAnimationStart();
        fab.hide(new 5(targetMode));
    }

    class 5 extends FloatingActionButton.OnVisibilityChangedListener {
        final /* synthetic */ int val$targetMode;

        5(int i) {
            this.val$targetMode = i;
        }

        public void onHidden(FloatingActionButton fab) {
            fab.setTranslationX(BottomAppBar.access$1800(BottomAppBar.this, this.val$targetMode));
            fab.show(new 1());
        }

        class 1 extends FloatingActionButton.OnVisibilityChangedListener {
            1() {
            }

            public void onShown(FloatingActionButton fab) {
                BottomAppBar.access$1600(BottomAppBar.this);
            }
        }
    }

    private void createFabTranslationXAnimation(int targetMode, List list) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", new float[]{getFabTranslationX(targetMode)});
        animator.setDuration(300L);
        list.add(animator);
    }

    private Drawable maybeTintNavigationIcon(Drawable navigationIcon) {
        if (navigationIcon != null && this.navigationIconTint != null) {
            Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
            DrawableCompat.setTint(wrappedNavigationIcon, this.navigationIconTint.intValue());
            return wrappedNavigationIcon;
        }
        return navigationIcon;
    }

    private void maybeAnimateMenuView(int targetMode, boolean newFabAttached) {
        if (!ViewCompat.isLaidOut(this)) {
            this.menuAnimatingWithFabAlignmentMode = false;
            replaceMenu(this.pendingMenuResId);
            return;
        }
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        ArrayList arrayList = new ArrayList();
        if (!isFabVisibleOrWillBeShown()) {
            targetMode = 0;
            newFabAttached = false;
        }
        createMenuViewTranslationAnimation(targetMode, newFabAttached, arrayList);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(arrayList);
        this.menuAnimator = set;
        set.addListener(new 6());
        this.menuAnimator.start();
    }

    class 6 extends AnimatorListenerAdapter {
        6() {
        }

        public void onAnimationStart(Animator animation) {
            BottomAppBar.access$1500(BottomAppBar.this);
        }

        public void onAnimationEnd(Animator animation) {
            BottomAppBar.access$1600(BottomAppBar.this);
            BottomAppBar.access$002(BottomAppBar.this, false);
            BottomAppBar.access$1902(BottomAppBar.this, null);
        }
    }

    private void createMenuViewTranslationAnimation(int targetMode, boolean targetAttached, List list) {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView == null) {
            return;
        }
        Animator fadeIn = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{1.0f});
        float translationXDifference = actionMenuView.getTranslationX() - getActionMenuViewTranslationX(actionMenuView, targetMode, targetAttached);
        if (Math.abs(translationXDifference) <= 1.0f) {
            if (actionMenuView.getAlpha() < 1.0f) {
                list.add(fadeIn);
            }
        } else {
            Animator fadeOut = ObjectAnimator.ofFloat(actionMenuView, "alpha", new float[]{0.0f});
            fadeOut.addListener(new 7(actionMenuView, targetMode, targetAttached));
            AnimatorSet set = new AnimatorSet();
            set.setDuration(150L);
            set.playSequentially(new Animator[]{fadeOut, fadeIn});
            list.add(set);
        }
    }

    class 7 extends AnimatorListenerAdapter {
        public boolean cancelled;
        final /* synthetic */ ActionMenuView val$actionMenuView;
        final /* synthetic */ boolean val$targetAttached;
        final /* synthetic */ int val$targetMode;

        7(ActionMenuView actionMenuView, int i, boolean z) {
            this.val$actionMenuView = actionMenuView;
            this.val$targetMode = i;
            this.val$targetAttached = z;
        }

        public void onAnimationCancel(Animator animation) {
            this.cancelled = true;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.cancelled) {
                boolean replaced = BottomAppBar.access$2000(BottomAppBar.this) != 0;
                BottomAppBar bottomAppBar = BottomAppBar.this;
                bottomAppBar.replaceMenu(BottomAppBar.access$2000(bottomAppBar));
                BottomAppBar.access$2100(BottomAppBar.this, this.val$actionMenuView, this.val$targetMode, this.val$targetAttached, replaced);
            }
        }
    }

    private float getFabTranslationY() {
        return -getTopEdgeTreatment().getCradleVerticalOffset();
    }

    private float getFabTranslationX(int fabAlignmentMode) {
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        if (fabAlignmentMode == 1) {
            int systemEndInset = isRtl ? this.leftInset : this.rightInset;
            int totalEndInset = this.fabOffsetEndMode + systemEndInset;
            return ((getMeasuredWidth() / 2) - totalEndInset) * (isRtl ? -1 : 1);
        }
        return 0.0f;
    }

    private float getFabTranslationX() {
        return getFabTranslationX(this.fabAlignmentMode);
    }

    private ActionMenuView getActionMenuView() {
        for (int i = 0; i < getChildCount(); i++) {
            ActionMenuView childAt = getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                return childAt;
            }
        }
        return null;
    }

    private void translateActionMenuView(ActionMenuView actionMenuView, int fabAlignmentMode, boolean fabAttached) {
        translateActionMenuView(actionMenuView, fabAlignmentMode, fabAttached, false);
    }

    class 8 implements Runnable {
        final /* synthetic */ ActionMenuView val$actionMenuView;
        final /* synthetic */ int val$fabAlignmentMode;
        final /* synthetic */ boolean val$fabAttached;

        8(ActionMenuView actionMenuView, int i, boolean z) {
            this.val$actionMenuView = actionMenuView;
            this.val$fabAlignmentMode = i;
            this.val$fabAttached = z;
        }

        public void run() {
            this.val$actionMenuView.setTranslationX(BottomAppBar.this.getActionMenuViewTranslationX(r0, this.val$fabAlignmentMode, this.val$fabAttached));
        }
    }

    private void translateActionMenuView(ActionMenuView actionMenuView, int fabAlignmentMode, boolean fabAttached, boolean shouldWaitForMenuReplacement) {
        Runnable runnable = new 8(actionMenuView, fabAlignmentMode, fabAttached);
        if (shouldWaitForMenuReplacement) {
            actionMenuView.post(runnable);
        } else {
            runnable.run();
        }
    }

    protected int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int fabAlignmentMode, boolean fabAttached) {
        int max;
        if (fabAlignmentMode != 1 || !fabAttached) {
            return 0;
        }
        boolean isRtl = ViewUtils.isLayoutRtl(this);
        int toolbarLeftContentEnd = isRtl ? getMeasuredWidth() : 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            boolean isAlignedToStart = (view.getLayoutParams() instanceof Toolbar.LayoutParams) && (view.getLayoutParams().gravity & 8388615) == 8388611;
            if (isAlignedToStart) {
                if (isRtl) {
                    max = Math.min(toolbarLeftContentEnd, view.getLeft());
                } else {
                    max = Math.max(toolbarLeftContentEnd, view.getRight());
                }
                toolbarLeftContentEnd = max;
            }
        }
        int actionMenuViewStart = isRtl ? actionMenuView.getRight() : actionMenuView.getLeft();
        int systemStartInset = isRtl ? this.rightInset : -this.leftInset;
        int end = actionMenuViewStart + systemStartInset;
        return toolbarLeftContentEnd - end;
    }

    private void cancelAnimations() {
        Animator animator = this.menuAnimator;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.modeAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            cancelAnimations();
            setCutoutState();
        }
        setActionMenuViewPosition();
    }

    private BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment) this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
    }

    private void setCutoutState() {
        getTopEdgeTreatment().setHorizontalOffset(getFabTranslationX());
        View fab = findDependentView();
        this.materialShapeDrawable.setInterpolation((this.fabAttached && isFabVisibleOrWillBeShown()) ? 1.0f : 0.0f);
        if (fab != null) {
            fab.setTranslationY(getFabTranslationY());
            fab.setTranslationX(getFabTranslationX());
        }
    }

    private void setActionMenuViewPosition() {
        ActionMenuView actionMenuView = getActionMenuView();
        if (actionMenuView != null && this.menuAnimator == null) {
            actionMenuView.setAlpha(1.0f);
            if (!isFabVisibleOrWillBeShown()) {
                translateActionMenuView(actionMenuView, 0, false);
            } else {
                translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
            }
        }
    }

    private void addFabAnimationListeners(FloatingActionButton fab) {
        fab.addOnHideAnimationListener(this.fabAnimationListener);
        fab.addOnShowAnimationListener(new 9());
        fab.addTransformationCallback(this.fabTransformationCallback);
    }

    class 9 extends AnimatorListenerAdapter {
        9() {
        }

        public void onAnimationStart(Animator animation) {
            BottomAppBar.this.fabAnimationListener.onAnimationStart(animation);
            FloatingActionButton fab = BottomAppBar.access$2200(BottomAppBar.this);
            if (fab != null) {
                fab.setTranslationX(BottomAppBar.access$2300(BottomAppBar.this));
            }
        }
    }

    private int getBottomInset() {
        return this.bottomInset;
    }

    private int getRightInset() {
        return this.rightInset;
    }

    private int getLeftInset() {
        return this.leftInset;
    }

    public void setTitle(CharSequence title) {
    }

    public void setSubtitle(CharSequence subtitle) {
    }

    public Behavior getBehavior() {
        if (this.behavior == null) {
            this.behavior = new Behavior();
        }
        return this.behavior;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
        if (getParent() instanceof ViewGroup) {
            getParent().setClipChildren(false);
        }
    }

    public static class Behavior extends HideBottomViewOnScrollBehavior {
        private final Rect fabContentRect;
        private final View.OnLayoutChangeListener fabLayoutListener;
        private int originalBottomMargin;
        private WeakReference viewRef;

        static /* synthetic */ WeakReference access$2400(Behavior x0) {
            return x0.viewRef;
        }

        static /* synthetic */ Rect access$2500(Behavior x0) {
            return x0.fabContentRect;
        }

        static /* synthetic */ int access$2600(Behavior x0) {
            return x0.originalBottomMargin;
        }

        class 1 implements View.OnLayoutChangeListener {
            1() {
            }

            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                BottomAppBar child = (BottomAppBar) Behavior.access$2400(Behavior.this).get();
                if (child == null || !(v instanceof FloatingActionButton)) {
                    v.removeOnLayoutChangeListener(this);
                    return;
                }
                FloatingActionButton fab = (FloatingActionButton) v;
                fab.getMeasuredContentRect(Behavior.access$2500(Behavior.this));
                int height = Behavior.access$2500(Behavior.this).height();
                child.setFabDiameter(height);
                float cornerSize = fab.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(Behavior.access$2500(Behavior.this)));
                child.setFabCornerSize(cornerSize);
                CoordinatorLayout.LayoutParams fabLayoutParams = v.getLayoutParams();
                if (Behavior.access$2600(Behavior.this) == 0) {
                    int bottomShadowPadding = (fab.getMeasuredHeight() - height) / 2;
                    int bottomMargin = child.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin);
                    int minBottomMargin = bottomMargin - bottomShadowPadding;
                    fabLayoutParams.bottomMargin = BottomAppBar.access$2700(child) + minBottomMargin;
                    fabLayoutParams.leftMargin = BottomAppBar.access$2800(child);
                    fabLayoutParams.rightMargin = BottomAppBar.access$2900(child);
                    boolean isRtl = ViewUtils.isLayoutRtl(fab);
                    if (isRtl) {
                        fabLayoutParams.leftMargin += BottomAppBar.access$3000(child);
                    } else {
                        fabLayoutParams.rightMargin += BottomAppBar.access$3000(child);
                    }
                }
            }
        }

        public Behavior() {
            this.fabLayoutListener = new 1();
            this.fabContentRect = new Rect();
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.fabLayoutListener = new 1();
            this.fabContentRect = new Rect();
        }

        public boolean onLayoutChild(CoordinatorLayout parent, BottomAppBar child, int layoutDirection) {
            this.viewRef = new WeakReference(child);
            FloatingActionButton access$3100 = BottomAppBar.access$3100(child);
            if (access$3100 != null && !ViewCompat.isLaidOut(access$3100)) {
                CoordinatorLayout.LayoutParams fabLayoutParams = access$3100.getLayoutParams();
                fabLayoutParams.anchorGravity = 49;
                this.originalBottomMargin = fabLayoutParams.bottomMargin;
                if (access$3100 instanceof FloatingActionButton) {
                    FloatingActionButton fab = access$3100;
                    if (fab.getShowMotionSpec() == null) {
                        fab.setShowMotionSpecResource(R.animator.mtrl_fab_show_motion_spec);
                    }
                    if (fab.getHideMotionSpec() == null) {
                        fab.setHideMotionSpecResource(R.animator.mtrl_fab_hide_motion_spec);
                    }
                    fab.addOnLayoutChangeListener(this.fabLayoutListener);
                    BottomAppBar.access$3200(child, fab);
                }
                BottomAppBar.access$1300(child);
            }
            parent.onLayoutChild(child, layoutDirection);
            return super.onLayoutChild(parent, (View) child, layoutDirection);
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar child, View directTargetChild, View target, int axes, int type) {
            return child.getHideOnScroll() && super.onStartNestedScroll(coordinatorLayout, (View) child, directTargetChild, target, axes, type);
        }
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.fabAlignmentMode = savedState.fabAlignmentMode;
        this.fabAttached = savedState.fabAttached;
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new 1();
        int fabAlignmentMode;
        boolean fabAttached;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.fabAlignmentMode = in.readInt();
            this.fabAttached = in.readInt() != 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.fabAlignmentMode);
            parcel.writeInt(this.fabAttached ? 1 : 0);
        }

        class 1 implements Parcelable.ClassLoaderCreator {
            1() {
            }

            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }
}
