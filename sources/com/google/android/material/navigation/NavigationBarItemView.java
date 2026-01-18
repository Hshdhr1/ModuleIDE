package com.google.android.material.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    private static final int INVALID_ITEM_POSITION = -1;
    private ValueAnimator activeIndicatorAnimator;
    private int activeIndicatorDesiredHeight;
    private int activeIndicatorDesiredWidth;
    private boolean activeIndicatorEnabled;
    private int activeIndicatorMarginHorizontal;
    private float activeIndicatorProgress;
    private boolean activeIndicatorResizeable;
    private ActiveIndicatorTransform activeIndicatorTransform;
    private final View activeIndicatorView;
    private BadgeDrawable badgeDrawable;
    private final ImageView icon;
    private final FrameLayout iconContainer;
    private ColorStateList iconTint;
    private boolean initialized;
    private boolean isShifting;
    private MenuItemImpl itemData;
    private int itemPaddingBottom;
    private int itemPaddingTop;
    private int itemPosition;
    private final ViewGroup labelGroup;
    private int labelVisibilityMode;
    private final TextView largeLabel;
    private Drawable originalIconDrawable;
    private float scaleDownFactor;
    private float scaleUpFactor;
    private float shiftAmount;
    private final TextView smallLabel;
    private Drawable wrappedIconDrawable;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform(null);
    private static final ActiveIndicatorTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform(null);

    protected abstract int getItemLayoutResId();

    static /* synthetic */ ImageView access$200(NavigationBarItemView x0) {
        return x0.icon;
    }

    static /* synthetic */ void access$300(NavigationBarItemView x0, View x1) {
        x0.tryUpdateBadgeBounds(x1);
    }

    static /* synthetic */ void access$400(NavigationBarItemView x0, int x1) {
        x0.updateActiveIndicatorLayoutParams(x1);
    }

    static /* synthetic */ void access$500(NavigationBarItemView x0, float x1, float x2) {
        x0.setActiveIndicatorProgress(x1, x2);
    }

    public NavigationBarItemView(Context context) {
        super(context);
        this.initialized = false;
        this.itemPosition = -1;
        this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        this.activeIndicatorProgress = 0.0f;
        this.activeIndicatorEnabled = false;
        this.activeIndicatorDesiredWidth = 0;
        this.activeIndicatorDesiredHeight = 0;
        this.activeIndicatorResizeable = false;
        this.activeIndicatorMarginHorizontal = 0;
        LayoutInflater.from(context).inflate(getItemLayoutResId(), this, true);
        this.iconContainer = findViewById(R.id.navigation_bar_item_icon_container);
        this.activeIndicatorView = findViewById(R.id.navigation_bar_item_active_indicator_view);
        ImageView findViewById = findViewById(R.id.navigation_bar_item_icon_view);
        this.icon = findViewById;
        ViewGroup findViewById2 = findViewById(R.id.navigation_bar_item_labels_group);
        this.labelGroup = findViewById2;
        TextView findViewById3 = findViewById(R.id.navigation_bar_item_small_label_view);
        this.smallLabel = findViewById3;
        TextView findViewById4 = findViewById(R.id.navigation_bar_item_large_label_view);
        this.largeLabel = findViewById4;
        setBackgroundResource(getItemBackgroundResId());
        this.itemPaddingTop = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.itemPaddingBottom = findViewById2.getPaddingBottom();
        ViewCompat.setImportantForAccessibility(findViewById3, 2);
        ViewCompat.setImportantForAccessibility(findViewById4, 2);
        setFocusable(true);
        calculateTextScaleFactors(findViewById3.getTextSize(), findViewById4.getTextSize());
        if (findViewById != null) {
            findViewById.addOnLayoutChangeListener(new 1());
        }
    }

    class 1 implements View.OnLayoutChangeListener {
        1() {
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (NavigationBarItemView.access$200(NavigationBarItemView.this).getVisibility() == 0) {
                NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                NavigationBarItemView.access$300(navigationBarItemView, NavigationBarItemView.access$200(navigationBarItemView));
            }
        }
    }

    protected int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams labelGroupParams = this.labelGroup.getLayoutParams();
        int labelWidth = labelGroupParams.leftMargin + this.labelGroup.getMeasuredWidth() + labelGroupParams.rightMargin;
        return Math.max(getSuggestedIconWidth(), labelWidth);
    }

    protected int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams labelGroupParams = this.labelGroup.getLayoutParams();
        return getSuggestedIconHeight() + labelGroupParams.topMargin + this.labelGroup.getMeasuredHeight() + labelGroupParams.bottomMargin;
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        CharSequence tooltipText;
        this.itemData = itemData;
        setCheckable(itemData.isCheckable());
        setChecked(itemData.isChecked());
        setEnabled(itemData.isEnabled());
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitle());
        setId(itemData.getItemId());
        if (!TextUtils.isEmpty(itemData.getContentDescription())) {
            setContentDescription(itemData.getContentDescription());
        }
        if (!TextUtils.isEmpty(itemData.getTooltipText())) {
            tooltipText = itemData.getTooltipText();
        } else {
            tooltipText = itemData.getTitle();
        }
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
        setVisibility(itemData.isVisible() ? 0 : 8);
        this.initialized = true;
    }

    void clear() {
        removeBadge();
        this.itemData = null;
        this.activeIndicatorProgress = 0.0f;
        this.initialized = false;
    }

    private View getIconOrContainer() {
        FrameLayout frameLayout = this.iconContainer;
        return frameLayout != null ? frameLayout : this.icon;
    }

    public void setItemPosition(int position) {
        this.itemPosition = position;
    }

    public int getItemPosition() {
        return this.itemPosition;
    }

    public void setShifting(boolean shifting) {
        if (this.isShifting != shifting) {
            this.isShifting = shifting;
            refreshChecked();
        }
    }

    public void setLabelVisibilityMode(int mode) {
        if (this.labelVisibilityMode != mode) {
            this.labelVisibilityMode = mode;
            updateActiveIndicatorTransform();
            updateActiveIndicatorLayoutParams(getWidth());
            refreshChecked();
        }
    }

    public MenuItemImpl getItemData() {
        return this.itemData;
    }

    public void setTitle(CharSequence title) {
        CharSequence tooltipText;
        this.smallLabel.setText(title);
        this.largeLabel.setText(title);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl == null || TextUtils.isEmpty(menuItemImpl.getContentDescription())) {
            setContentDescription(title);
        }
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2 == null || TextUtils.isEmpty(menuItemImpl2.getTooltipText())) {
            tooltipText = title;
        } else {
            tooltipText = this.itemData.getTooltipText();
        }
        if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
            TooltipCompat.setTooltipText(this, tooltipText);
        }
    }

    public void setCheckable(boolean checkable) {
        refreshDrawableState();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new 2(w));
    }

    class 2 implements Runnable {
        final /* synthetic */ int val$width;

        2(int i) {
            this.val$width = i;
        }

        public void run() {
            NavigationBarItemView.access$400(NavigationBarItemView.this, this.val$width);
        }
    }

    private void updateActiveIndicatorTransform() {
        if (isActiveIndicatorResizeableAndUnlabeled()) {
            this.activeIndicatorTransform = ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
        } else {
            this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        }
    }

    private void setActiveIndicatorProgress(float progress, float target) {
        View view = this.activeIndicatorView;
        if (view != null) {
            this.activeIndicatorTransform.updateForProgress(progress, target, view);
        }
        this.activeIndicatorProgress = progress;
    }

    private void maybeAnimateActiveIndicatorToProgress(float newProgress) {
        if (!this.activeIndicatorEnabled || !this.initialized || !ViewCompat.isAttachedToWindow(this)) {
            setActiveIndicatorProgress(newProgress, newProgress);
            return;
        }
        ValueAnimator valueAnimator = this.activeIndicatorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.activeIndicatorAnimator = null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.activeIndicatorProgress, newProgress});
        this.activeIndicatorAnimator = ofFloat;
        ofFloat.addUpdateListener(new 3(newProgress));
        this.activeIndicatorAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        this.activeIndicatorAnimator.setDuration(MotionUtils.resolveThemeDuration(getContext(), R.attr.motionDurationLong1, getResources().getInteger(R.integer.material_motion_duration_long_1)));
        this.activeIndicatorAnimator.start();
    }

    class 3 implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ float val$newProgress;

        3(float f) {
            this.val$newProgress = f;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float progress = ((Float) animation.getAnimatedValue()).floatValue();
            NavigationBarItemView.access$500(NavigationBarItemView.this, progress, this.val$newProgress);
        }
    }

    private void refreshChecked() {
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null) {
            setChecked(menuItemImpl.isChecked());
        }
    }

    public void setChecked(boolean checked) {
        this.largeLabel.setPivotX(r0.getWidth() / 2);
        this.largeLabel.setPivotY(r0.getBaseline());
        this.smallLabel.setPivotX(r0.getWidth() / 2);
        this.smallLabel.setPivotY(r0.getBaseline());
        float newIndicatorProgress = checked ? 1.0f : 0.0f;
        maybeAnimateActiveIndicatorToProgress(newIndicatorProgress);
        switch (this.labelVisibilityMode) {
            case -1:
                if (this.isShifting) {
                    if (checked) {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                        this.largeLabel.setVisibility(0);
                    } else {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                        updateViewPaddingBottom(this.labelGroup, 0);
                        this.largeLabel.setVisibility(4);
                    }
                    this.smallLabel.setVisibility(4);
                    break;
                } else {
                    updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                    if (checked) {
                        setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                        setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                        TextView textView = this.smallLabel;
                        float f = this.scaleUpFactor;
                        setViewScaleValues(textView, f, f, 4);
                        break;
                    } else {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                        TextView textView2 = this.largeLabel;
                        float f2 = this.scaleDownFactor;
                        setViewScaleValues(textView2, f2, f2, 4);
                        setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                        break;
                    }
                }
            case 0:
                if (checked) {
                    setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                    updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                    this.largeLabel.setVisibility(0);
                } else {
                    setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                    updateViewPaddingBottom(this.labelGroup, 0);
                    this.largeLabel.setVisibility(4);
                }
                this.smallLabel.setVisibility(4);
                break;
            case 1:
                updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                if (checked) {
                    setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                    setViewScaleValues(this.largeLabel, 1.0f, 1.0f, 0);
                    TextView textView3 = this.smallLabel;
                    float f3 = this.scaleUpFactor;
                    setViewScaleValues(textView3, f3, f3, 4);
                    break;
                } else {
                    setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                    TextView textView4 = this.largeLabel;
                    float f4 = this.scaleDownFactor;
                    setViewScaleValues(textView4, f4, f4, 4);
                    setViewScaleValues(this.smallLabel, 1.0f, 1.0f, 0);
                    break;
                }
            case 2:
                setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                this.largeLabel.setVisibility(8);
                this.smallLabel.setVisibility(8);
                break;
        }
        refreshDrawableState();
        setSelected(checked);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        if (badgeDrawable != null && badgeDrawable.isVisible()) {
            CharSequence customContentDescription = this.itemData.getTitle();
            if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
                customContentDescription = this.itemData.getContentDescription();
            }
            info.setContentDescription(customContentDescription + ", " + this.badgeDrawable.getContentDescription());
        }
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
        if (isSelected()) {
            infoCompat.setClickable(false);
            infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        infoCompat.setRoleDescription(getResources().getString(R.string.item_view_role_description));
    }

    private int getItemVisiblePosition() {
        ViewGroup parent = getParent();
        int index = parent.indexOfChild(this);
        int visiblePosition = 0;
        for (int i = 0; i < index; i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof NavigationBarItemView) && child.getVisibility() == 0) {
                visiblePosition++;
            }
        }
        return visiblePosition;
    }

    private static void setViewTopMarginAndGravity(View view, int topMargin, int gravity) {
        FrameLayout.LayoutParams viewParams = view.getLayoutParams();
        viewParams.topMargin = topMargin;
        viewParams.bottomMargin = topMargin;
        viewParams.gravity = gravity;
        view.setLayoutParams(viewParams);
    }

    private static void setViewScaleValues(View view, float scaleX, float scaleY, int visibility) {
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setVisibility(visibility);
    }

    private static void updateViewPaddingBottom(View view, int paddingBottom) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.smallLabel.setEnabled(enabled);
        this.largeLabel.setEnabled(enabled);
        this.icon.setEnabled(enabled);
        if (enabled) {
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        } else {
            ViewCompat.setPointerIcon(this, (PointerIconCompat) null);
        }
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(Drawable iconDrawable) {
        if (iconDrawable == this.originalIconDrawable) {
            return;
        }
        this.originalIconDrawable = iconDrawable;
        if (iconDrawable != null) {
            Drawable.ConstantState state = iconDrawable.getConstantState();
            iconDrawable = DrawableCompat.wrap(state == null ? iconDrawable : state.newDrawable()).mutate();
            this.wrappedIconDrawable = iconDrawable;
            ColorStateList colorStateList = this.iconTint;
            if (colorStateList != null) {
                DrawableCompat.setTintList(iconDrawable, colorStateList);
            }
        }
        this.icon.setImageDrawable(iconDrawable);
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return true;
    }

    public void setIconTintList(ColorStateList tint) {
        Drawable drawable;
        this.iconTint = tint;
        if (this.itemData != null && (drawable = this.wrappedIconDrawable) != null) {
            DrawableCompat.setTintList(drawable, tint);
            this.wrappedIconDrawable.invalidateSelf();
        }
    }

    public void setIconSize(int iconSize) {
        FrameLayout.LayoutParams iconParams = this.icon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        this.icon.setLayoutParams(iconParams);
    }

    public void setTextAppearanceInactive(int inactiveTextAppearance) {
        setTextAppearanceWithoutFontScaling(this.smallLabel, inactiveTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public void setTextAppearanceActive(int activeTextAppearance) {
        setTextAppearanceWithoutFontScaling(this.largeLabel, activeTextAppearance);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    private static void setTextAppearanceWithoutFontScaling(TextView textView, int textAppearance) {
        TextViewCompat.setTextAppearance(textView, textAppearance);
        int unscaledSize = MaterialResources.getUnscaledTextSize(textView.getContext(), textAppearance, 0);
        if (unscaledSize != 0) {
            textView.setTextSize(0, unscaledSize);
        }
    }

    public void setTextColor(ColorStateList color) {
        if (color != null) {
            this.smallLabel.setTextColor(color);
            this.largeLabel.setTextColor(color);
        }
    }

    private void calculateTextScaleFactors(float smallLabelSize, float largeLabelSize) {
        this.shiftAmount = smallLabelSize - largeLabelSize;
        this.scaleUpFactor = (largeLabelSize * 1.0f) / smallLabelSize;
        this.scaleDownFactor = (1.0f * smallLabelSize) / largeLabelSize;
    }

    public void setItemBackground(int background) {
        Drawable backgroundDrawable = background == 0 ? null : ContextCompat.getDrawable(getContext(), background);
        setItemBackground(backgroundDrawable);
    }

    public void setItemBackground(Drawable background) {
        if (background != null && background.getConstantState() != null) {
            background = background.getConstantState().newDrawable().mutate();
        }
        ViewCompat.setBackground(this, background);
    }

    public void setItemPaddingTop(int paddingTop) {
        if (this.itemPaddingTop != paddingTop) {
            this.itemPaddingTop = paddingTop;
            refreshChecked();
        }
    }

    public void setItemPaddingBottom(int paddingBottom) {
        if (this.itemPaddingBottom != paddingBottom) {
            this.itemPaddingBottom = paddingBottom;
            refreshChecked();
        }
    }

    public void setActiveIndicatorEnabled(boolean enabled) {
        this.activeIndicatorEnabled = enabled;
        View view = this.activeIndicatorView;
        if (view != null) {
            view.setVisibility(enabled ? 0 : 8);
            requestLayout();
        }
    }

    public void setActiveIndicatorWidth(int width) {
        this.activeIndicatorDesiredWidth = width;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    private void updateActiveIndicatorLayoutParams(int availableWidth) {
        if (this.activeIndicatorView == null) {
            return;
        }
        int newWidth = Math.min(this.activeIndicatorDesiredWidth, availableWidth - (this.activeIndicatorMarginHorizontal * 2));
        FrameLayout.LayoutParams indicatorParams = this.activeIndicatorView.getLayoutParams();
        indicatorParams.height = isActiveIndicatorResizeableAndUnlabeled() ? newWidth : this.activeIndicatorDesiredHeight;
        indicatorParams.width = newWidth;
        this.activeIndicatorView.setLayoutParams(indicatorParams);
    }

    private boolean isActiveIndicatorResizeableAndUnlabeled() {
        return this.activeIndicatorResizeable && this.labelVisibilityMode == 2;
    }

    public void setActiveIndicatorHeight(int height) {
        this.activeIndicatorDesiredHeight = height;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public void setActiveIndicatorMarginHorizontal(int marginHorizontal) {
        this.activeIndicatorMarginHorizontal = marginHorizontal;
        updateActiveIndicatorLayoutParams(getWidth());
    }

    public Drawable getActiveIndicatorDrawable() {
        View view = this.activeIndicatorView;
        if (view == null) {
            return null;
        }
        return view.getBackground();
    }

    public void setActiveIndicatorDrawable(Drawable activeIndicatorDrawable) {
        View view = this.activeIndicatorView;
        if (view == null) {
            return;
        }
        view.setBackgroundDrawable(activeIndicatorDrawable);
    }

    public void setActiveIndicatorResizeable(boolean resizeable) {
        this.activeIndicatorResizeable = resizeable;
    }

    void setBadge(BadgeDrawable badgeDrawable) {
        if (this.badgeDrawable == badgeDrawable) {
            return;
        }
        if (hasBadge() && this.icon != null) {
            Log.w("NavigationBar", "Multiple badges shouldn't be attached to one item.");
            tryRemoveBadgeFromAnchor(this.icon);
        }
        this.badgeDrawable = badgeDrawable;
        ImageView imageView = this.icon;
        if (imageView != null) {
            tryAttachBadgeToAnchor(imageView);
        }
    }

    public BadgeDrawable getBadge() {
        return this.badgeDrawable;
    }

    void removeBadge() {
        tryRemoveBadgeFromAnchor(this.icon);
    }

    private boolean hasBadge() {
        return this.badgeDrawable != null;
    }

    private void tryUpdateBadgeBounds(View anchorView) {
        if (!hasBadge()) {
            return;
        }
        BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
    }

    private void tryAttachBadgeToAnchor(View anchorView) {
        if (hasBadge() && anchorView != null) {
            setClipChildren(false);
            setClipToPadding(false);
            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
        }
    }

    private void tryRemoveBadgeFromAnchor(View anchorView) {
        if (!hasBadge()) {
            return;
        }
        if (anchorView != null) {
            setClipChildren(true);
            setClipToPadding(true);
            BadgeUtils.detachBadgeDrawable(this.badgeDrawable, anchorView);
        }
        this.badgeDrawable = null;
    }

    private FrameLayout getCustomParentForBadge(View anchorView) {
        if (anchorView == this.icon && BadgeUtils.USE_COMPAT_PARENT) {
            return this.icon.getParent();
        }
        return null;
    }

    private int getSuggestedIconWidth() {
        int badgeWidth;
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        if (badgeDrawable == null) {
            badgeWidth = 0;
        } else {
            badgeWidth = badgeDrawable.getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
        }
        FrameLayout.LayoutParams iconContainerParams = getIconOrContainer().getLayoutParams();
        return Math.max(badgeWidth, iconContainerParams.leftMargin) + this.icon.getMeasuredWidth() + Math.max(badgeWidth, iconContainerParams.rightMargin);
    }

    private int getSuggestedIconHeight() {
        int badgeHeight = 0;
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        if (badgeDrawable != null) {
            badgeHeight = badgeDrawable.getMinimumHeight() / 2;
        }
        FrameLayout.LayoutParams iconContainerParams = getIconOrContainer().getLayoutParams();
        return Math.max(badgeHeight, iconContainerParams.topMargin) + this.icon.getMeasuredWidth() + badgeHeight;
    }

    protected int getItemBackgroundResId() {
        return R.drawable.mtrl_navigation_bar_item_background;
    }

    protected int getItemDefaultMarginResId() {
        return R.dimen.mtrl_navigation_bar_item_default_margin;
    }

    private static class ActiveIndicatorTransform {
        private static final float ALPHA_FRACTION = 0.2f;
        private static final float SCALE_X_HIDDEN = 0.4f;
        private static final float SCALE_X_SHOWN = 1.0f;

        private ActiveIndicatorTransform() {
        }

        /* synthetic */ ActiveIndicatorTransform(1 x0) {
            this();
        }

        protected float calculateAlpha(float progress, float targetValue) {
            float startAlphaFraction = targetValue == 0.0f ? 0.8f : 0.0f;
            float endAlphaFraction = targetValue == 0.0f ? 1.0f : 0.2f;
            return AnimationUtils.lerp(0.0f, 1.0f, startAlphaFraction, endAlphaFraction, progress);
        }

        protected float calculateScaleX(float progress, float targetValue) {
            return AnimationUtils.lerp(0.4f, 1.0f, progress);
        }

        protected float calculateScaleY(float progress, float targetValue) {
            return 1.0f;
        }

        public void updateForProgress(float progress, float targetValue, View indicator) {
            indicator.setScaleX(calculateScaleX(progress, targetValue));
            indicator.setScaleY(calculateScaleY(progress, targetValue));
            indicator.setAlpha(calculateAlpha(progress, targetValue));
        }
    }

    private static class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {
        private ActiveIndicatorUnlabeledTransform() {
            super(null);
        }

        /* synthetic */ ActiveIndicatorUnlabeledTransform(1 x0) {
            this();
        }

        protected float calculateScaleY(float progress, float targetValue) {
            return calculateScaleX(progress, targetValue);
        }
    }
}
