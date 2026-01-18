package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
    private static final int DEF_STYLE_RES = R.style.Widget_Design_AppBarLayout;
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_FORCE = 8;
    static final int PENDING_ACTION_NONE = 0;
    private Behavior behavior;
    private int currentOffset;
    private int downPreScrollRange;
    private int downScrollRange;
    private ValueAnimator elevationOverlayAnimator;
    private boolean haveChildWithInterpolator;
    private WindowInsetsCompat lastInsets;
    private boolean liftOnScroll;
    private final List liftOnScrollListeners;
    private WeakReference liftOnScrollTargetView;
    private int liftOnScrollTargetViewId;
    private boolean liftable;
    private boolean liftableOverride;
    private boolean lifted;
    private List listeners;
    private int pendingAction;
    private Drawable statusBarForeground;
    private int[] tmpStatesArray;
    private int totalScrollRange;

    public interface BaseOnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public static abstract class ChildScrollEffect {
        public abstract void onOffsetChanged(AppBarLayout appBarLayout, View view, float f);
    }

    public interface LiftOnScrollListener {
        void onUpdate(float f, int i);
    }

    public interface OnOffsetChangedListener extends BaseOnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    static /* synthetic */ Drawable access$000(AppBarLayout x0) {
        return x0.statusBarForeground;
    }

    static /* synthetic */ List access$100(AppBarLayout x0) {
        return x0.liftOnScrollListeners;
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.appBarLayoutStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AppBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        int i = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, i), attrs, defStyleAttr);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.pendingAction = 0;
        this.liftOnScrollListeners = new ArrayList();
        Context context2 = getContext();
        setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            if (getOutlineProvider() == ViewOutlineProvider.BACKGROUND) {
                ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
            }
            ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attrs, defStyleAttr, i);
        }
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.AppBarLayout, defStyleAttr, i, new int[0]);
        ViewCompat.setBackground(this, a.getDrawable(R.styleable.AppBarLayout_android_background));
        if (getBackground() instanceof ColorDrawable) {
            ColorDrawable background = getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(background.getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        if (a.hasValue(R.styleable.AppBarLayout_expanded)) {
            setExpanded(a.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && a.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, a.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (a.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(a.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (a.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(a.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.liftOnScroll = a.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        this.liftOnScrollTargetViewId = a.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
        setStatusBarForeground(a.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
        a.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new 1());
    }

    class 1 implements OnApplyWindowInsetsListener {
        1() {
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            return AppBarLayout.this.onWindowInsetChanged(insets);
        }
    }

    public void addOnOffsetChangedListener(BaseOnOffsetChangedListener listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (listener != null && !this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        addOnOffsetChangedListener((BaseOnOffsetChangedListener) listener);
    }

    public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener listener) {
        List list = this.listeners;
        if (list != null && listener != null) {
            list.remove(listener);
        }
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        removeOnOffsetChangedListener((BaseOnOffsetChangedListener) listener);
    }

    public void addLiftOnScrollListener(LiftOnScrollListener liftOnScrollListener) {
        this.liftOnScrollListeners.add(liftOnScrollListener);
    }

    public boolean removeLiftOnScrollListener(LiftOnScrollListener liftOnScrollListener) {
        return this.liftOnScrollListeners.remove(liftOnScrollListener);
    }

    public void clearLiftOnScrollListener() {
        this.liftOnScrollListeners.clear();
    }

    public void setStatusBarForeground(Drawable drawable) {
        Drawable drawable2 = this.statusBarForeground;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            Drawable mutate = drawable != null ? drawable.mutate() : null;
            this.statusBarForeground = mutate;
            if (mutate != null) {
                if (mutate.isStateful()) {
                    this.statusBarForeground.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection(this));
                this.statusBarForeground.setVisible(getVisibility() == 0, false);
                this.statusBarForeground.setCallback(this);
            }
            updateWillNotDraw();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setStatusBarForegroundColor(int color) {
        setStatusBarForeground(new ColorDrawable(color));
    }

    public void setStatusBarForegroundResource(int resId) {
        setStatusBarForeground(AppCompatResources.getDrawable(getContext(), resId));
    }

    public Drawable getStatusBarForeground() {
        return this.statusBarForeground;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (shouldDrawStatusBarForeground()) {
            int saveCount = canvas.save();
            canvas.translate(0.0f, -this.currentOffset);
            this.statusBarForeground.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] state = getDrawableState();
        Drawable d = this.statusBarForeground;
        if (d != null && d.isStateful() && d.setState(state)) {
            invalidateDrawable(d);
        }
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.statusBarForeground;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean visible = visibility == 0;
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setVisible(visible, false);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != 1073741824 && ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int newHeight = getMeasuredHeight();
            switch (heightMode) {
                case Integer.MIN_VALUE:
                    newHeight = MathUtils.clamp(getMeasuredHeight() + getTopInset(), 0, View.MeasureSpec.getSize(heightMeasureSpec));
                    break;
                case 0:
                    newHeight += getTopInset();
                    break;
            }
            setMeasuredDimension(getMeasuredWidth(), newHeight);
        }
        invalidateScrollRanges();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        boolean z = true;
        if (ViewCompat.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int z2 = getChildCount() - 1; z2 >= 0; z2--) {
                ViewCompat.offsetTopAndBottom(getChildAt(z2), topInset);
            }
        }
        invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int i = 0;
        int z3 = getChildCount();
        while (true) {
            if (i >= z3) {
                break;
            }
            View child = getChildAt(i);
            LayoutParams childLp = child.getLayoutParams();
            Interpolator interpolator = childLp.getScrollInterpolator();
            if (interpolator == null) {
                i++;
            } else {
                this.haveChildWithInterpolator = true;
                break;
            }
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftableOverride) {
            if (!this.liftOnScroll && !hasCollapsibleChild()) {
                z = false;
            }
            setLiftableState(z);
        }
    }

    private void updateWillNotDraw() {
        setWillNotDraw(!shouldDrawStatusBarForeground());
    }

    private boolean shouldDrawStatusBarForeground() {
        return this.statusBarForeground != null && getTopInset() > 0;
    }

    private boolean hasCollapsibleChild() {
        int z = getChildCount();
        for (int i = 0; i < z; i++) {
            if (getChildAt(i).getLayoutParams().isCollapsible()) {
                return true;
            }
        }
        return false;
    }

    private void invalidateScrollRanges() {
        Behavior behavior = this.behavior;
        BaseBehavior.SavedState savedState = (behavior == null || this.totalScrollRange == -1 || this.pendingAction != 0) ? null : behavior.saveScrollState(AbsSavedState.EMPTY_STATE, this);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        if (savedState != null) {
            this.behavior.restoreScrollState(savedState, false);
        }
    }

    public void setOrientation(int orientation) {
        if (orientation != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    public CoordinatorLayout.Behavior getBehavior() {
        Behavior behavior = new Behavior();
        this.behavior = behavior;
        return behavior;
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void setExpanded(boolean expanded) {
        setExpanded(expanded, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean expanded, boolean animate) {
        setExpanded(expanded, animate, true);
    }

    private void setExpanded(boolean expanded, boolean animate, boolean force) {
        this.pendingAction = (expanded ? 1 : 2) | (animate ? 4 : 0) | (force ? 8 : 0);
        requestLayout();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (Build.VERSION.SDK_INT >= 19 && (p instanceof LinearLayout.LayoutParams)) {
            return new LayoutParams((LinearLayout.LayoutParams) p);
        }
        if (p instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearLiftOnScrollTargetView();
    }

    boolean hasChildWithInterpolator() {
        return this.haveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        int i = this.totalScrollRange;
        if (i != -1) {
            return i;
        }
        int range = 0;
        int i2 = 0;
        int z = getChildCount();
        while (true) {
            if (i2 >= z) {
                break;
            }
            View child = getChildAt(i2);
            LayoutParams lp = child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int flags = lp.scrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += lp.topMargin + childHeight + lp.bottomMargin;
            if (i2 == 0 && ViewCompat.getFitsSystemWindows(child)) {
                range -= getTopInset();
            }
            if ((flags & 2) == 0) {
                i2++;
            } else {
                range -= ViewCompat.getMinimumHeight(child);
                break;
            }
        }
        int max = Math.max(0, range);
        this.totalScrollRange = max;
        return max;
    }

    boolean hasScrollableChildren() {
        return getTotalScrollRange() != 0;
    }

    int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    int getDownNestedPreScrollRange() {
        int childRange;
        int i = this.downPreScrollRange;
        if (i != -1) {
            return i;
        }
        int range = 0;
        for (int i2 = getChildCount() - 1; i2 >= 0; i2--) {
            View child = getChildAt(i2);
            LayoutParams lp = child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int flags = lp.scrollFlags;
            if ((flags & 5) == 5) {
                int childRange2 = lp.topMargin + lp.bottomMargin;
                if ((flags & 8) != 0) {
                    childRange = childRange2 + ViewCompat.getMinimumHeight(child);
                } else if ((flags & 2) != 0) {
                    childRange = childRange2 + (childHeight - ViewCompat.getMinimumHeight(child));
                } else {
                    childRange = childRange2 + childHeight;
                }
                if (i2 == 0 && ViewCompat.getFitsSystemWindows(child)) {
                    childRange = Math.min(childRange, childHeight - getTopInset());
                }
                range += childRange;
            } else if (range > 0) {
                break;
            }
        }
        int max = Math.max(0, range);
        this.downPreScrollRange = max;
        return max;
    }

    int getDownNestedScrollRange() {
        int i = this.downScrollRange;
        if (i != -1) {
            return i;
        }
        int range = 0;
        int i2 = 0;
        int z = getChildCount();
        while (true) {
            if (i2 >= z) {
                break;
            }
            View child = getChildAt(i2);
            LayoutParams lp = child.getLayoutParams();
            int childHeight = child.getMeasuredHeight();
            int childHeight2 = childHeight + lp.topMargin + lp.bottomMargin;
            int flags = lp.scrollFlags;
            if ((flags & 1) == 0) {
                break;
            }
            range += childHeight2;
            if ((flags & 2) == 0) {
                i2++;
            } else {
                range -= ViewCompat.getMinimumHeight(child);
                break;
            }
        }
        int max = Math.max(0, range);
        this.downScrollRange = max;
        return max;
    }

    void onOffsetChanged(int offset) {
        this.currentOffset = offset;
        if (!willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        List list = this.listeners;
        if (list != null) {
            int z = list.size();
            for (int i = 0; i < z; i++) {
                BaseOnOffsetChangedListener listener = (BaseOnOffsetChangedListener) this.listeners.get(i);
                if (listener != null) {
                    listener.onOffsetChanged(this, offset);
                }
            }
        }
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minHeight = ViewCompat.getMinimumHeight(this);
        if (minHeight != 0) {
            return (minHeight * 2) + topInset;
        }
        int childCount = getChildCount();
        int lastChildMinHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
        if (lastChildMinHeight != 0) {
            return (lastChildMinHeight * 2) + topInset;
        }
        return getHeight() / 3;
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] extraStates = this.tmpStatesArray;
        int[] states = super.onCreateDrawableState(extraStates.length + extraSpace);
        extraStates[0] = this.liftable ? R.attr.state_liftable : -R.attr.state_liftable;
        extraStates[1] = (this.liftable && this.lifted) ? R.attr.state_lifted : -R.attr.state_lifted;
        extraStates[2] = this.liftable ? R.attr.state_collapsible : -R.attr.state_collapsible;
        extraStates[3] = (this.liftable && this.lifted) ? R.attr.state_collapsed : -R.attr.state_collapsed;
        return mergeDrawableStates(states, extraStates);
    }

    public boolean setLiftable(boolean liftable) {
        this.liftableOverride = true;
        return setLiftableState(liftable);
    }

    public void setLiftableOverrideEnabled(boolean enabled) {
        this.liftableOverride = enabled;
    }

    private boolean setLiftableState(boolean liftable) {
        if (this.liftable != liftable) {
            this.liftable = liftable;
            refreshDrawableState();
            return true;
        }
        return false;
    }

    public boolean setLifted(boolean lifted) {
        return setLiftedState(lifted, true);
    }

    public boolean isLifted() {
        return this.lifted;
    }

    boolean setLiftedState(boolean lifted) {
        return setLiftedState(lifted, !this.liftableOverride);
    }

    boolean setLiftedState(boolean lifted, boolean force) {
        if (force && this.lifted != lifted) {
            this.lifted = lifted;
            refreshDrawableState();
            if (this.liftOnScroll && (getBackground() instanceof MaterialShapeDrawable)) {
                startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable) getBackground(), lifted);
                return true;
            }
            return true;
        }
        return false;
    }

    private void startLiftOnScrollElevationOverlayAnimation(MaterialShapeDrawable background, boolean lifted) {
        float appBarElevation = getResources().getDimension(R.dimen.design_appbar_elevation);
        float fromElevation = lifted ? 0.0f : appBarElevation;
        float toElevation = lifted ? appBarElevation : 0.0f;
        ValueAnimator valueAnimator = this.elevationOverlayAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{fromElevation, toElevation});
        this.elevationOverlayAnimator = ofFloat;
        ofFloat.setDuration(getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
        this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.elevationOverlayAnimator.addUpdateListener(new 2(background));
        this.elevationOverlayAnimator.start();
    }

    class 2 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ MaterialShapeDrawable val$background;

        2(MaterialShapeDrawable materialShapeDrawable) {
            this.val$background = materialShapeDrawable;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float elevation = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.val$background.setElevation(elevation);
            if (AppBarLayout.access$000(AppBarLayout.this) instanceof MaterialShapeDrawable) {
                ((MaterialShapeDrawable) AppBarLayout.access$000(AppBarLayout.this)).setElevation(elevation);
            }
            for (LiftOnScrollListener liftOnScrollListener : AppBarLayout.access$100(AppBarLayout.this)) {
                liftOnScrollListener.onUpdate(elevation, this.val$background.getResolvedTintColor());
            }
        }
    }

    public void setLiftOnScroll(boolean liftOnScroll) {
        this.liftOnScroll = liftOnScroll;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    public void setLiftOnScrollTargetViewId(int liftOnScrollTargetViewId) {
        this.liftOnScrollTargetViewId = liftOnScrollTargetViewId;
        clearLiftOnScrollTargetView();
    }

    public int getLiftOnScrollTargetViewId() {
        return this.liftOnScrollTargetViewId;
    }

    boolean shouldLift(View defaultScrollingView) {
        View scrollingView = findLiftOnScrollTargetView(defaultScrollingView);
        if (scrollingView == null) {
            scrollingView = defaultScrollingView;
        }
        return scrollingView != null && (scrollingView.canScrollVertically(-1) || scrollingView.getScrollY() > 0);
    }

    private View findLiftOnScrollTargetView(View defaultScrollingView) {
        int i;
        if (this.liftOnScrollTargetView == null && (i = this.liftOnScrollTargetViewId) != -1) {
            View targetView = null;
            if (defaultScrollingView != null) {
                targetView = defaultScrollingView.findViewById(i);
            }
            if (targetView == null && (getParent() instanceof ViewGroup)) {
                targetView = getParent().findViewById(this.liftOnScrollTargetViewId);
            }
            if (targetView != null) {
                this.liftOnScrollTargetView = new WeakReference(targetView);
            }
        }
        WeakReference weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    private void clearLiftOnScrollTargetView() {
        WeakReference weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    @Deprecated
    public void setTargetElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    int getPendingAction() {
        return this.pendingAction;
    }

    void resetPendingAction() {
        this.pendingAction = 0;
    }

    final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    private boolean shouldOffsetFirstChild() {
        if (getChildCount() <= 0) {
            return false;
        }
        View firstChild = getChildAt(0);
        return (firstChild.getVisibility() == 8 || ViewCompat.getFitsSystemWindows(firstChild)) ? false : true;
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }
        if (!ObjectsCompat.equals(this.lastInsets, newInsets)) {
            this.lastInsets = newInsets;
            updateWillNotDraw();
            requestLayout();
        }
        return insets;
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        private static final int SCROLL_EFFECT_COMPRESS = 1;
        private static final int SCROLL_EFFECT_NONE = 0;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_NO_SCROLL = 0;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
        private ChildScrollEffect scrollEffect;
        int scrollFlags;
        Interpolator scrollInterpolator;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollFlags {
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.scrollFlags = 1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.AppBarLayout_Layout);
            this.scrollFlags = a.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            int scrollEffectInt = a.getInt(R.styleable.AppBarLayout_Layout_layout_scrollEffect, 0);
            setScrollEffect(createScrollEffectFromInt(scrollEffectInt));
            if (a.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                int resId = a.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0);
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(c, resId);
            }
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.scrollFlags = 1;
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.scrollFlags = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.scrollFlags = 1;
        }

        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
            this.scrollFlags = 1;
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.scrollFlags = 1;
            this.scrollFlags = source.scrollFlags;
            this.scrollInterpolator = source.scrollInterpolator;
        }

        public void setScrollFlags(int flags) {
            this.scrollFlags = flags;
        }

        public int getScrollFlags() {
            return this.scrollFlags;
        }

        private ChildScrollEffect createScrollEffectFromInt(int scrollEffectInt) {
            switch (scrollEffectInt) {
                case 1:
                    return new CompressChildScrollEffect();
                default:
                    return null;
            }
        }

        public ChildScrollEffect getScrollEffect() {
            return this.scrollEffect;
        }

        public void setScrollEffect(ChildScrollEffect scrollEffect) {
            this.scrollEffect = scrollEffect;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.scrollInterpolator = interpolator;
        }

        public Interpolator getScrollInterpolator() {
            return this.scrollInterpolator;
        }

        boolean isCollapsible() {
            int i = this.scrollFlags;
            return (i & 1) == 1 && (i & 10) != 0;
        }
    }

    public static class Behavior extends BaseBehavior {

        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback {
        }

        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(coordinatorLayout, view, motionEvent);
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            return super.onLayoutChild(coordinatorLayout, appBarLayout, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, appBarLayout, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
        }

        public /* bridge */ /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            super.onNestedScroll(coordinatorLayout, appBarLayout, view, i, i2, i3, i4, i5, iArr);
        }

        public /* bridge */ /* synthetic */ void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            super.onRestoreInstanceState(coordinatorLayout, appBarLayout, parcelable);
        }

        public /* bridge */ /* synthetic */ Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            return super.onSaveInstanceState(coordinatorLayout, appBarLayout);
        }

        public /* bridge */ /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            return super.onStartNestedScroll(coordinatorLayout, appBarLayout, view, view2, i, i2);
        }

        public /* bridge */ /* synthetic */ void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            super.onStopNestedScroll(coordinatorLayout, appBarLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
            return super.onTouchEvent(coordinatorLayout, view, motionEvent);
        }

        public /* bridge */ /* synthetic */ void setDragCallback(BaseBehavior.BaseDragCallback baseDragCallback) {
            super.setDragCallback(baseDragCallback);
        }

        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    protected static class BaseBehavior extends HeaderBehavior {
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private boolean coordinatorLayoutA11yScrollable;
        private WeakReference lastNestedScrollingChildRef;
        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        private int offsetDelta;
        private BaseDragCallback onDragCallback;
        private SavedState savedState;

        public static abstract class BaseDragCallback {
            public abstract boolean canDrag(AppBarLayout appBarLayout);
        }

        static /* synthetic */ boolean access$200(BaseBehavior x0) {
            return x0.coordinatorLayoutA11yScrollable;
        }

        static /* synthetic */ int access$300(BaseBehavior x0) {
            return x0.offsetDelta;
        }

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout appBarLayout, View directTargetChild, View target, int nestedScrollAxes, int type) {
            ValueAnimator valueAnimator;
            boolean started = (nestedScrollAxes & 2) != 0 && (appBarLayout.isLiftOnScroll() || canScrollChildren(parent, appBarLayout, directTargetChild));
            if (started && (valueAnimator = this.offsetAnimator) != null) {
                valueAnimator.cancel();
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = type;
            return started;
        }

        private boolean canScrollChildren(CoordinatorLayout parent, AppBarLayout appBarLayout, View directTargetChild) {
            return appBarLayout.hasScrollableChildren() && parent.getHeight() - directTargetChild.getHeight() <= appBarLayout.getHeight();
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View target, int dx, int dy, int[] consumed, int type) {
            int min;
            int max;
            if (dy != 0) {
                if (dy < 0) {
                    int min2 = -appBarLayout.getTotalScrollRange();
                    min = min2;
                    max = appBarLayout.getDownNestedPreScrollRange() + min2;
                } else {
                    int min3 = appBarLayout.getUpNestedPreScrollRange();
                    min = -min3;
                    max = 0;
                }
                if (min != max) {
                    consumed[1] = scroll(coordinatorLayout, appBarLayout, dy, min, max);
                }
            }
            if (appBarLayout.isLiftOnScroll()) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(target));
            }
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
            if (dyUnconsumed < 0) {
                consumed[1] = scroll(coordinatorLayout, appBarLayout, dyUnconsumed, -appBarLayout.getDownNestedScrollRange(), 0);
            }
            if (dyUnconsumed == 0) {
                updateAccessibilityActions(coordinatorLayout, appBarLayout);
            }
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View target, int type) {
            if (this.lastStartedType == 0 || type == 1) {
                snapToChildIfNeeded(coordinatorLayout, appBarLayout);
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(target));
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference(target);
        }

        public void setDragCallback(BaseDragCallback callback) {
            this.onDragCallback = callback;
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int offset, float velocity) {
            int duration;
            int distance = Math.abs(getTopBottomOffsetForScrollingSibling() - offset);
            float velocity2 = Math.abs(velocity);
            if (velocity2 > 0.0f) {
                duration = Math.round((distance / velocity2) * 1000.0f) * 3;
            } else {
                float distanceRatio = distance / appBarLayout.getHeight();
                duration = (int) ((1.0f + distanceRatio) * 150.0f);
            }
            animateOffsetWithDuration(coordinatorLayout, appBarLayout, offset, duration);
        }

        private void animateOffsetWithDuration(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int offset, int duration) {
            int currentOffset = getTopBottomOffsetForScrollingSibling();
            if (currentOffset == offset) {
                ValueAnimator valueAnimator = this.offsetAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.offsetAnimator.cancel();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator2 = this.offsetAnimator;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.offsetAnimator = valueAnimator3;
                valueAnimator3.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.offsetAnimator.addUpdateListener(new 1(coordinatorLayout, appBarLayout));
            } else {
                valueAnimator2.cancel();
            }
            this.offsetAnimator.setDuration(Math.min(duration, 600));
            this.offsetAnimator.setIntValues(new int[]{currentOffset, offset});
            this.offsetAnimator.start();
        }

        class 1 implements ValueAnimator.AnimatorUpdateListener {
            final /* synthetic */ AppBarLayout val$child;
            final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

            1(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
                this.val$coordinatorLayout = coordinatorLayout;
                this.val$child = appBarLayout;
            }

            public void onAnimationUpdate(ValueAnimator animator) {
                BaseBehavior.this.setHeaderTopBottomOffset(this.val$coordinatorLayout, this.val$child, ((Integer) animator.getAnimatedValue()).intValue());
            }
        }

        private int getChildIndexOnOffset(AppBarLayout appBarLayout, int offset) {
            int count = appBarLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = appBarLayout.getChildAt(i);
                int top = child.getTop();
                int bottom = child.getBottom();
                LayoutParams lp = child.getLayoutParams();
                if (checkFlag(lp.getScrollFlags(), 32)) {
                    top -= lp.topMargin;
                    bottom += lp.bottomMargin;
                }
                if (top <= (-offset) && bottom >= (-offset)) {
                    return i;
                }
            }
            return -1;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int topInset = appBarLayout.getTopInset() + appBarLayout.getPaddingTop();
            int offset = getTopBottomOffsetForScrollingSibling() - topInset;
            int offsetChildIndex = getChildIndexOnOffset(appBarLayout, offset);
            if (offsetChildIndex >= 0) {
                View offsetChild = appBarLayout.getChildAt(offsetChildIndex);
                LayoutParams lp = offsetChild.getLayoutParams();
                int flags = lp.getScrollFlags();
                if ((flags & 17) == 17) {
                    int snapTop = -offsetChild.getTop();
                    int snapBottom = -offsetChild.getBottom();
                    if (offsetChildIndex == 0 && ViewCompat.getFitsSystemWindows(appBarLayout) && ViewCompat.getFitsSystemWindows(offsetChild)) {
                        snapTop -= appBarLayout.getTopInset();
                    }
                    if (checkFlag(flags, 2)) {
                        snapBottom += ViewCompat.getMinimumHeight(offsetChild);
                    } else if (checkFlag(flags, 5)) {
                        int seam = ViewCompat.getMinimumHeight(offsetChild) + snapBottom;
                        if (offset < seam) {
                            snapTop = seam;
                        } else {
                            snapBottom = seam;
                        }
                    }
                    if (checkFlag(flags, 32)) {
                        snapTop += lp.topMargin;
                        snapBottom -= lp.bottomMargin;
                    }
                    int newOffset = calculateSnapOffset(offset, snapBottom, snapTop) + topInset;
                    animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(newOffset, -appBarLayout.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        private int calculateSnapOffset(int value, int bottom, int top) {
            return value < (bottom + top) / 2 ? bottom : top;
        }

        private static boolean checkFlag(int flags, int check) {
            return (flags & check) == check;
        }

        public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout appBarLayout, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
            CoordinatorLayout.LayoutParams lp = appBarLayout.getLayoutParams();
            if (lp.height == -2) {
                parent.onMeasureChild(appBarLayout, parentWidthMeasureSpec, widthUsed, View.MeasureSpec.makeMeasureSpec(0, 0), heightUsed);
                return true;
            }
            return super.onMeasureChild(parent, (View) appBarLayout, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        }

        public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout appBarLayout, int layoutDirection) {
            int offset;
            boolean handled = super.onLayoutChild(parent, (View) appBarLayout, layoutDirection);
            int pendingAction = appBarLayout.getPendingAction();
            SavedState savedState = this.savedState;
            if (savedState != null && (pendingAction & 8) == 0) {
                if (savedState.fullyScrolled) {
                    setHeaderTopBottomOffset(parent, appBarLayout, -appBarLayout.getTotalScrollRange());
                } else if (this.savedState.fullyExpanded) {
                    setHeaderTopBottomOffset(parent, appBarLayout, 0);
                } else {
                    View child = appBarLayout.getChildAt(this.savedState.firstVisibleChildIndex);
                    int offset2 = -child.getBottom();
                    if (this.savedState.firstVisibleChildAtMinimumHeight) {
                        offset = offset2 + ViewCompat.getMinimumHeight(child) + appBarLayout.getTopInset();
                    } else {
                        offset = offset2 + Math.round(child.getHeight() * this.savedState.firstVisibleChildPercentageShown);
                    }
                    setHeaderTopBottomOffset(parent, appBarLayout, offset);
                }
            } else if (pendingAction != 0) {
                boolean animate = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int offset3 = -appBarLayout.getUpNestedPreScrollRange();
                    if (animate) {
                        animateOffsetTo(parent, appBarLayout, offset3, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(parent, appBarLayout, offset3);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (animate) {
                        animateOffsetTo(parent, appBarLayout, 0, 0.0f);
                    } else {
                        setHeaderTopBottomOffset(parent, appBarLayout, 0);
                    }
                }
            }
            appBarLayout.resetPendingAction();
            this.savedState = null;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0));
            updateAppBarLayoutDrawableState(parent, appBarLayout, getTopAndBottomOffset(), 0, true);
            appBarLayout.onOffsetChanged(getTopAndBottomOffset());
            updateAccessibilityActions(parent, appBarLayout);
            return handled;
        }

        private void updateAccessibilityActions(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            View scrollingView;
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            if (appBarLayout.getTotalScrollRange() == 0 || (scrollingView = getChildWithScrollingBehavior(coordinatorLayout)) == null || !childrenHaveScrollFlags(appBarLayout)) {
                return;
            }
            if (!ViewCompat.hasAccessibilityDelegate(coordinatorLayout)) {
                ViewCompat.setAccessibilityDelegate(coordinatorLayout, new 2());
            }
            this.coordinatorLayoutA11yScrollable = addAccessibilityScrollActions(coordinatorLayout, appBarLayout, scrollingView);
        }

        class 2 extends AccessibilityDelegateCompat {
            2() {
            }

            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setScrollable(BaseBehavior.access$200(BaseBehavior.this));
                info.setClassName(ScrollView.class.getName());
            }
        }

        private View getChildWithScrollingBehavior(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = coordinatorLayout.getChildAt(i);
                CoordinatorLayout.LayoutParams lp = child.getLayoutParams();
                if (lp.getBehavior() instanceof ScrollingViewBehavior) {
                    return child;
                }
            }
            return null;
        }

        private boolean childrenHaveScrollFlags(AppBarLayout appBarLayout) {
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = appBarLayout.getChildAt(i);
                LayoutParams childLp = child.getLayoutParams();
                int flags = childLp.scrollFlags;
                if (flags != 0) {
                    return true;
                }
            }
            return false;
        }

        private boolean addAccessibilityScrollActions(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View scrollingView) {
            boolean a11yScrollable = false;
            if (getTopBottomOffsetForScrollingSibling() != (-appBarLayout.getTotalScrollRange())) {
                addActionToExpand(coordinatorLayout, appBarLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
                a11yScrollable = true;
            }
            if (getTopBottomOffsetForScrollingSibling() != 0) {
                if (scrollingView.canScrollVertically(-1)) {
                    int dy = -appBarLayout.getDownNestedPreScrollRange();
                    if (dy != 0) {
                        ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, (CharSequence) null, new 3(coordinatorLayout, appBarLayout, scrollingView, dy));
                        return true;
                    }
                    return a11yScrollable;
                }
                addActionToExpand(coordinatorLayout, appBarLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
                return true;
            }
            return a11yScrollable;
        }

        class 3 implements AccessibilityViewCommand {
            final /* synthetic */ AppBarLayout val$appBarLayout;
            final /* synthetic */ CoordinatorLayout val$coordinatorLayout;
            final /* synthetic */ int val$dy;
            final /* synthetic */ View val$scrollingView;

            3(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
                this.val$coordinatorLayout = coordinatorLayout;
                this.val$appBarLayout = appBarLayout;
                this.val$scrollingView = view;
                this.val$dy = i;
            }

            public boolean perform(View view, AccessibilityViewCommand.CommandArguments arguments) {
                BaseBehavior.this.onNestedPreScroll(this.val$coordinatorLayout, this.val$appBarLayout, this.val$scrollingView, 0, this.val$dy, new int[]{0, 0}, 1);
                return true;
            }
        }

        private void addActionToExpand(CoordinatorLayout parent, AppBarLayout appBarLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat action, boolean expand) {
            ViewCompat.replaceAccessibilityAction(parent, action, (CharSequence) null, new 4(appBarLayout, expand));
        }

        class 4 implements AccessibilityViewCommand {
            final /* synthetic */ AppBarLayout val$appBarLayout;
            final /* synthetic */ boolean val$expand;

            4(AppBarLayout appBarLayout, boolean z) {
                this.val$appBarLayout = appBarLayout;
                this.val$expand = z;
            }

            public boolean perform(View view, AccessibilityViewCommand.CommandArguments arguments) {
                this.val$appBarLayout.setExpanded(this.val$expand);
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public boolean canDragView(AppBarLayout appBarLayout) {
            BaseDragCallback baseDragCallback = this.onDragCallback;
            if (baseDragCallback != null) {
                return baseDragCallback.canDrag(appBarLayout);
            }
            WeakReference weakReference = this.lastNestedScrollingChildRef;
            if (weakReference == null) {
                return true;
            }
            View scrollingView = (View) weakReference.get();
            return (scrollingView == null || !scrollingView.isShown() || scrollingView.canScrollVertically(-1)) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void onFlingFinished(CoordinatorLayout parent, AppBarLayout appBarLayout) {
            snapToChildIfNeeded(parent, appBarLayout);
            if (appBarLayout.isLiftOnScroll()) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(findFirstScrollingChild(parent)));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int getMaxDragOffset(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int getScrollRangeForDragFling(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int newOffset, int minOffset, int maxOffset) {
            int i;
            int curOffset = getTopBottomOffsetForScrollingSibling();
            int consumed = 0;
            if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
                int newOffset2 = MathUtils.clamp(newOffset, minOffset, maxOffset);
                if (curOffset != newOffset2) {
                    if (appBarLayout.hasChildWithInterpolator()) {
                        i = interpolateOffset(appBarLayout, newOffset2);
                    } else {
                        i = newOffset2;
                    }
                    int interpolatedOffset = i;
                    boolean offsetChanged = setTopAndBottomOffset(interpolatedOffset);
                    consumed = curOffset - newOffset2;
                    this.offsetDelta = newOffset2 - interpolatedOffset;
                    if (offsetChanged) {
                        for (int i2 = 0; i2 < appBarLayout.getChildCount(); i2++) {
                            LayoutParams params = appBarLayout.getChildAt(i2).getLayoutParams();
                            ChildScrollEffect scrollEffect = params.getScrollEffect();
                            if (scrollEffect != null && (params.getScrollFlags() & 1) != 0) {
                                scrollEffect.onOffsetChanged(appBarLayout, appBarLayout.getChildAt(i2), getTopAndBottomOffset());
                            }
                        }
                    }
                    if (!offsetChanged && appBarLayout.hasChildWithInterpolator()) {
                        coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
                    }
                    appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, newOffset2, newOffset2 < curOffset ? -1 : 1, false);
                }
            } else {
                this.offsetDelta = 0;
            }
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return consumed;
        }

        boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        private int interpolateOffset(AppBarLayout appBarLayout, int offset) {
            int absOffset = Math.abs(offset);
            int i = 0;
            int z = appBarLayout.getChildCount();
            while (true) {
                if (i >= z) {
                    break;
                }
                View child = appBarLayout.getChildAt(i);
                LayoutParams childLp = child.getLayoutParams();
                Interpolator interpolator = childLp.getScrollInterpolator();
                if (absOffset < child.getTop() || absOffset > child.getBottom()) {
                    i++;
                } else if (interpolator != null) {
                    int childScrollableHeight = 0;
                    int flags = childLp.getScrollFlags();
                    if ((flags & 1) != 0) {
                        childScrollableHeight = 0 + child.getHeight() + childLp.topMargin + childLp.bottomMargin;
                        if ((flags & 2) != 0) {
                            childScrollableHeight -= ViewCompat.getMinimumHeight(child);
                        }
                    }
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        childScrollableHeight -= appBarLayout.getTopInset();
                    }
                    if (childScrollableHeight > 0) {
                        int offsetForView = absOffset - child.getTop();
                        int interpolatedDiff = Math.round(childScrollableHeight * interpolator.getInterpolation(offsetForView / childScrollableHeight));
                        return Integer.signum(offset) * (child.getTop() + interpolatedDiff);
                    }
                }
            }
            return offset;
        }

        private void updateAppBarLayoutDrawableState(CoordinatorLayout parent, AppBarLayout appBarLayout, int offset, int direction, boolean forceJump) {
            View child = getAppBarChildOnOffset(appBarLayout, offset);
            boolean lifted = false;
            if (child != null) {
                LayoutParams childLp = child.getLayoutParams();
                int flags = childLp.getScrollFlags();
                if ((flags & 1) != 0) {
                    int minHeight = ViewCompat.getMinimumHeight(child);
                    if (direction > 0 && (flags & 12) != 0) {
                        lifted = (-offset) >= (child.getBottom() - minHeight) - appBarLayout.getTopInset();
                    } else if ((flags & 2) != 0) {
                        lifted = (-offset) >= (child.getBottom() - minHeight) - appBarLayout.getTopInset();
                    }
                }
            }
            if (appBarLayout.isLiftOnScroll()) {
                lifted = appBarLayout.shouldLift(findFirstScrollingChild(parent));
            }
            boolean changed = appBarLayout.setLiftedState(lifted);
            if (forceJump || (changed && shouldJumpElevationState(parent, appBarLayout))) {
                appBarLayout.jumpDrawablesToCurrentState();
            }
        }

        private boolean shouldJumpElevationState(CoordinatorLayout parent, AppBarLayout appBarLayout) {
            List<View> dependencies = parent.getDependents(appBarLayout);
            int size = dependencies.size();
            for (int i = 0; i < size; i++) {
                View dependency = (View) dependencies.get(i);
                CoordinatorLayout.LayoutParams lp = dependency.getLayoutParams();
                CoordinatorLayout.Behavior behavior = lp.getBehavior();
                if (behavior instanceof ScrollingViewBehavior) {
                    return ((ScrollingViewBehavior) behavior).getOverlayTop() != 0;
                }
            }
            return false;
        }

        private static View getAppBarChildOnOffset(AppBarLayout layout, int offset) {
            int absOffset = Math.abs(offset);
            int z = layout.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = layout.getChildAt(i);
                if (absOffset >= child.getTop() && absOffset <= child.getBottom()) {
                    return child;
                }
            }
            return null;
        }

        private View findFirstScrollingChild(CoordinatorLayout parent) {
            int z = parent.getChildCount();
            for (int i = 0; i < z; i++) {
                View child = parent.getChildAt(i);
                if ((child instanceof NestedScrollingChild) || (child instanceof ListView) || (child instanceof ScrollView)) {
                    return child;
                }
            }
            return null;
        }

        int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout parent, AppBarLayout appBarLayout) {
            Parcelable superState = super.onSaveInstanceState(parent, (View) appBarLayout);
            SavedState scrollState = saveScrollState(superState, appBarLayout);
            return scrollState == null ? superState : scrollState;
        }

        public void onRestoreInstanceState(CoordinatorLayout parent, AppBarLayout appBarLayout, Parcelable state) {
            if (state instanceof SavedState) {
                restoreScrollState((SavedState) state, true);
                super.onRestoreInstanceState(parent, (View) appBarLayout, this.savedState.getSuperState());
            } else {
                super.onRestoreInstanceState(parent, (View) appBarLayout, state);
                this.savedState = null;
            }
        }

        SavedState saveScrollState(Parcelable superState, AppBarLayout appBarLayout) {
            int offset = getTopAndBottomOffset();
            int count = appBarLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = appBarLayout.getChildAt(i);
                int visBottom = child.getBottom() + offset;
                if (child.getTop() + offset <= 0 && visBottom >= 0) {
                    SavedState ss = new SavedState(superState == null ? AbsSavedState.EMPTY_STATE : superState);
                    ss.fullyExpanded = offset == 0;
                    ss.fullyScrolled = !ss.fullyExpanded && (-offset) >= appBarLayout.getTotalScrollRange();
                    ss.firstVisibleChildIndex = i;
                    ss.firstVisibleChildAtMinimumHeight = visBottom == ViewCompat.getMinimumHeight(child) + appBarLayout.getTopInset();
                    ss.firstVisibleChildPercentageShown = visBottom / child.getHeight();
                    return ss;
                }
            }
            return null;
        }

        void restoreScrollState(SavedState state, boolean force) {
            if (this.savedState == null || force) {
                this.savedState = state;
            }
        }

        protected static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator CREATOR = new 1();
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;
            boolean fullyExpanded;
            boolean fullyScrolled;

            public SavedState(Parcel source, ClassLoader loader) {
                super(source, loader);
                this.fullyScrolled = source.readByte() != 0;
                this.fullyExpanded = source.readByte() != 0;
                this.firstVisibleChildIndex = source.readInt();
                this.firstVisibleChildPercentageShown = source.readFloat();
                this.firstVisibleChildAtMinimumHeight = source.readByte() != 0;
            }

            public SavedState(Parcelable superState) {
                super(superState);
            }

            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeByte(this.fullyScrolled ? (byte) 1 : (byte) 0);
                parcel.writeByte(this.fullyExpanded ? (byte) 1 : (byte) 0);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : (byte) 0);
            }

            class 1 implements Parcelable.ClassLoaderCreator {
                1() {
                }

                public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                    return new SavedState(source, loader);
                }

                public SavedState createFromParcel(Parcel source) {
                    return new SavedState(source, null);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            }
        }
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        public /* bridge */ /* synthetic */ boolean isHorizontalOffsetEnabled() {
            return super.isHorizontalOffsetEnabled();
        }

        public /* bridge */ /* synthetic */ boolean isVerticalOffsetEnabled() {
            return super.isVerticalOffsetEnabled();
        }

        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            return super.onLayoutChild(coordinatorLayout, view, i);
        }

        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view, i, i2, i3, i4);
        }

        public /* bridge */ /* synthetic */ void setHorizontalOffsetEnabled(boolean z) {
            super.setHorizontalOffsetEnabled(z);
        }

        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public /* bridge */ /* synthetic */ void setVerticalOffsetEnabled(boolean z) {
            super.setVerticalOffsetEnabled(z);
        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(a.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            a.recycle();
        }

        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return dependency instanceof AppBarLayout;
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            offsetChildAsNeeded(child, dependency);
            updateLiftedStateIfNeeded(child, dependency);
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                ViewCompat.removeAccessibilityAction(parent, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
                ViewCompat.removeAccessibilityAction(parent, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
                ViewCompat.setAccessibilityDelegate(parent, (AccessibilityDelegateCompat) null);
            }
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout parent, View child, Rect rectangle, boolean immediate) {
            AppBarLayout header = findFirstDependency(parent.getDependencies(child));
            if (header != null) {
                rectangle.offset(child.getLeft(), child.getTop());
                Rect parentRect = this.tempRect1;
                parentRect.set(0, 0, parent.getWidth(), parent.getHeight());
                if (!parentRect.contains(rectangle)) {
                    header.setExpanded(false, !immediate);
                    return true;
                }
            }
            return false;
        }

        private void offsetChildAsNeeded(View child, View dependency) {
            CoordinatorLayout.Behavior behavior = dependency.getLayoutParams().getBehavior();
            if (behavior instanceof BaseBehavior) {
                BaseBehavior ablBehavior = (BaseBehavior) behavior;
                ViewCompat.offsetTopAndBottom(child, (((dependency.getBottom() - child.getTop()) + BaseBehavior.access$300(ablBehavior)) + getVerticalLayoutGap()) - getOverlapPixelsForOffset(dependency));
            }
        }

        float getOverlapRatioForOffset(View header) {
            int availScrollRange;
            if (header instanceof AppBarLayout) {
                AppBarLayout abl = (AppBarLayout) header;
                int totalScrollRange = abl.getTotalScrollRange();
                int preScrollDown = abl.getDownNestedPreScrollRange();
                int offset = getAppBarLayoutOffset(abl);
                if ((preScrollDown == 0 || totalScrollRange + offset > preScrollDown) && (availScrollRange = totalScrollRange - preScrollDown) != 0) {
                    return (offset / availScrollRange) + 1.0f;
                }
            }
            return 0.0f;
        }

        private static int getAppBarLayoutOffset(AppBarLayout abl) {
            CoordinatorLayout.Behavior behavior = abl.getLayoutParams().getBehavior();
            if (behavior instanceof BaseBehavior) {
                return ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling();
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public AppBarLayout findFirstDependency(List list) {
            int z = list.size();
            for (int i = 0; i < z; i++) {
                AppBarLayout appBarLayout = (View) list.get(i);
                if (appBarLayout instanceof AppBarLayout) {
                    return appBarLayout;
                }
            }
            return null;
        }

        int getScrollRange(View v) {
            if (v instanceof AppBarLayout) {
                return ((AppBarLayout) v).getTotalScrollRange();
            }
            return super.getScrollRange(v);
        }

        private void updateLiftedStateIfNeeded(View child, View dependency) {
            if (dependency instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) dependency;
                if (appBarLayout.isLiftOnScroll()) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(child));
                }
            }
        }
    }

    public static class CompressChildScrollEffect extends ChildScrollEffect {
        private static final float COMPRESS_DISTANCE_FACTOR = 0.3f;
        private final Rect relativeRect = new Rect();
        private final Rect ghostRect = new Rect();

        private static void updateRelativeRect(Rect rect, AppBarLayout appBarLayout, View child) {
            child.getDrawingRect(rect);
            appBarLayout.offsetDescendantRectToMyCoords(child, rect);
            rect.offset(0, -appBarLayout.getTopInset());
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, View child, float offset) {
            updateRelativeRect(this.relativeRect, appBarLayout, child);
            float distanceFromCeiling = this.relativeRect.top - Math.abs(offset);
            if (distanceFromCeiling <= 0.0f) {
                float p = MathUtils.clamp(Math.abs(distanceFromCeiling / this.relativeRect.height()), 0.0f, 1.0f);
                float easeOutQuad = 1.0f - ((1.0f - p) * (1.0f - p));
                float distance = this.relativeRect.height() * 0.3f;
                float offsetY = (-distanceFromCeiling) - (distance * easeOutQuad);
                child.setTranslationY(offsetY);
                child.getDrawingRect(this.ghostRect);
                this.ghostRect.offset(0, (int) (-offsetY));
                ViewCompat.setClipBounds(child, this.ghostRect);
                return;
            }
            ViewCompat.setClipBounds(child, (Rect) null);
            child.setTranslationY(0.0f);
        }
    }
}
