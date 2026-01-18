package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class TabLayout extends HorizontalScrollView {
    private static final int ANIMATION_DURATION = 300;
    static final int DEFAULT_GAP_TEXT_ICON = 8;
    private static final int DEFAULT_HEIGHT = 48;
    private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
    static final int FIXED_WRAP_GUTTER_MIN = 16;
    public static final int GRAVITY_CENTER = 1;
    public static final int GRAVITY_FILL = 0;
    public static final int GRAVITY_START = 2;
    public static final int INDICATOR_ANIMATION_MODE_ELASTIC = 1;
    public static final int INDICATOR_ANIMATION_MODE_FADE = 2;
    public static final int INDICATOR_ANIMATION_MODE_LINEAR = 0;
    public static final int INDICATOR_GRAVITY_BOTTOM = 0;
    public static final int INDICATOR_GRAVITY_CENTER = 1;
    public static final int INDICATOR_GRAVITY_STRETCH = 3;
    public static final int INDICATOR_GRAVITY_TOP = 2;
    private static final int INVALID_WIDTH = -1;
    private static final String LOG_TAG = "TabLayout";
    public static final int MODE_AUTO = 2;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
    public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
    private static final int TAB_MIN_WIDTH_MARGIN = 56;
    private AdapterChangeListener adapterChangeListener;
    private int contentInsetStart;
    private BaseOnTabSelectedListener currentVpSelectedListener;
    boolean inlineLabel;
    int mode;
    private TabLayoutOnPageChangeListener pageChangeListener;
    private PagerAdapter pagerAdapter;
    private DataSetObserver pagerAdapterObserver;
    private final int requestedTabMaxWidth;
    private final int requestedTabMinWidth;
    private ValueAnimator scrollAnimator;
    private final int scrollableTabMinWidth;
    private BaseOnTabSelectedListener selectedListener;
    private final ArrayList selectedListeners;
    private Tab selectedTab;
    private boolean setupViewPagerImplicitly;
    final SlidingTabIndicator slidingTabIndicator;
    final int tabBackgroundResId;
    int tabGravity;
    ColorStateList tabIconTint;
    PorterDuff.Mode tabIconTintMode;
    int tabIndicatorAnimationDuration;
    int tabIndicatorAnimationMode;
    boolean tabIndicatorFullWidth;
    int tabIndicatorGravity;
    int tabIndicatorHeight;
    private TabIndicatorInterpolator tabIndicatorInterpolator;
    int tabMaxWidth;
    int tabPaddingBottom;
    int tabPaddingEnd;
    int tabPaddingStart;
    int tabPaddingTop;
    ColorStateList tabRippleColorStateList;
    Drawable tabSelectedIndicator;
    private int tabSelectedIndicatorColor;
    int tabTextAppearance;
    ColorStateList tabTextColors;
    float tabTextMultiLineSize;
    float tabTextSize;
    private final Pools.Pool tabViewPool;
    private final ArrayList tabs;
    boolean unboundedRipple;
    ViewPager viewPager;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_TabLayout;
    private static final Pools.Pool tabPool = new Pools.SynchronizedPool(16);

    @Deprecated
    public interface BaseOnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    public @interface LabelVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabSelectedListener extends BaseOnTabSelectedListener {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndicatorAnimationMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TabIndicatorGravity {
    }

    static /* synthetic */ TabIndicatorInterpolator access$1300(TabLayout x0) {
        return x0.tabIndicatorInterpolator;
    }

    static /* synthetic */ int access$1500(TabLayout x0) {
        return x0.tabSelectedIndicatorColor;
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tabStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        int i = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, i), attrs, defStyleAttr);
        this.tabs = new ArrayList();
        this.tabSelectedIndicator = new GradientDrawable();
        this.tabSelectedIndicatorColor = 0;
        this.tabMaxWidth = Integer.MAX_VALUE;
        this.tabIndicatorHeight = -1;
        this.selectedListeners = new ArrayList();
        this.tabViewPool = new Pools.SimplePool(12);
        Context context2 = getContext();
        setHorizontalScrollBarEnabled(false);
        SlidingTabIndicator slidingTabIndicator = new SlidingTabIndicator(context2);
        this.slidingTabIndicator = slidingTabIndicator;
        super.addView(slidingTabIndicator, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.TabLayout, defStyleAttr, i, R.styleable.TabLayout_tabTextAppearance);
        if (getBackground() instanceof ColorDrawable) {
            ColorDrawable background = getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(background.getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            materialShapeDrawable.setElevation(ViewCompat.getElevation(this));
            ViewCompat.setBackground(this, materialShapeDrawable);
        }
        setSelectedTabIndicator(MaterialResources.getDrawable(context2, a, R.styleable.TabLayout_tabIndicator));
        setSelectedTabIndicatorColor(a.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
        slidingTabIndicator.setSelectedIndicatorHeight(a.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, -1));
        setSelectedTabIndicatorGravity(a.getInt(R.styleable.TabLayout_tabIndicatorGravity, 0));
        setTabIndicatorAnimationMode(a.getInt(R.styleable.TabLayout_tabIndicatorAnimationMode, 0));
        setTabIndicatorFullWidth(a.getBoolean(R.styleable.TabLayout_tabIndicatorFullWidth, true));
        int dimensionPixelSize = a.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
        this.tabPaddingBottom = dimensionPixelSize;
        this.tabPaddingEnd = dimensionPixelSize;
        this.tabPaddingTop = dimensionPixelSize;
        this.tabPaddingStart = dimensionPixelSize;
        this.tabPaddingStart = a.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.tabPaddingStart);
        this.tabPaddingTop = a.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.tabPaddingTop);
        this.tabPaddingEnd = a.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.tabPaddingEnd);
        this.tabPaddingBottom = a.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.tabPaddingBottom);
        int resourceId = a.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        this.tabTextAppearance = resourceId;
        TypedArray ta = context2.obtainStyledAttributes(resourceId, androidx.appcompat.R.styleable.TextAppearance);
        try {
            this.tabTextSize = ta.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
            this.tabTextColors = MaterialResources.getColorStateList(context2, ta, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
            ta.recycle();
            if (a.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.tabTextColors = MaterialResources.getColorStateList(context2, a, R.styleable.TabLayout_tabTextColor);
            }
            if (a.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                int selected = a.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
                this.tabTextColors = createColorStateList(this.tabTextColors.getDefaultColor(), selected);
            }
            int selected2 = R.styleable.TabLayout_tabIconTint;
            this.tabIconTint = MaterialResources.getColorStateList(context2, a, selected2);
            this.tabIconTintMode = ViewUtils.parseTintMode(a.getInt(R.styleable.TabLayout_tabIconTintMode, -1), null);
            this.tabRippleColorStateList = MaterialResources.getColorStateList(context2, a, R.styleable.TabLayout_tabRippleColor);
            this.tabIndicatorAnimationDuration = a.getInt(R.styleable.TabLayout_tabIndicatorAnimationDuration, 300);
            this.requestedTabMinWidth = a.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
            this.requestedTabMaxWidth = a.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
            this.tabBackgroundResId = a.getResourceId(R.styleable.TabLayout_tabBackground, 0);
            this.contentInsetStart = a.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
            this.mode = a.getInt(R.styleable.TabLayout_tabMode, 1);
            this.tabGravity = a.getInt(R.styleable.TabLayout_tabGravity, 0);
            this.inlineLabel = a.getBoolean(R.styleable.TabLayout_tabInlineLabel, false);
            this.unboundedRipple = a.getBoolean(R.styleable.TabLayout_tabUnboundedRipple, false);
            a.recycle();
            Resources res = getResources();
            this.tabTextMultiLineSize = res.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
            this.scrollableTabMinWidth = res.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
            applyModeAndGravity();
        } catch (Throwable th) {
            ta.recycle();
            throw th;
        }
    }

    public void setSelectedTabIndicatorColor(int color) {
        this.tabSelectedIndicatorColor = color;
        updateTabViews(false);
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int height) {
        this.tabIndicatorHeight = height;
        this.slidingTabIndicator.setSelectedIndicatorHeight(height);
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText) {
        setScrollPosition(position, positionOffset, updateSelectedText, true);
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText, boolean updateIndicatorPosition) {
        int roundedPosition = Math.round(position + positionOffset);
        if (roundedPosition < 0 || roundedPosition >= this.slidingTabIndicator.getChildCount()) {
            return;
        }
        if (updateIndicatorPosition) {
            this.slidingTabIndicator.setIndicatorPositionFromTabPosition(position, positionOffset);
        }
        ValueAnimator valueAnimator = this.scrollAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.scrollAnimator.cancel();
        }
        scrollTo(position < 0 ? 0 : calculateScrollXForTab(position, positionOffset), 0);
        if (updateSelectedText) {
            setSelectedTabView(roundedPosition);
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, int position) {
        addTab(tab, position, this.tabs.isEmpty());
    }

    public void addTab(Tab tab, boolean setSelected) {
        addTab(tab, this.tabs.size(), setSelected);
    }

    public void addTab(Tab tab, int position, boolean setSelected) {
        if (tab.parent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        configureTab(tab, position);
        addTabView(tab);
        if (setSelected) {
            tab.select();
        }
    }

    private void addTabFromItemView(TabItem item) {
        Tab tab = newTab();
        if (item.text != null) {
            tab.setText(item.text);
        }
        if (item.icon != null) {
            tab.setIcon(item.icon);
        }
        if (item.customLayout != 0) {
            tab.setCustomView(item.customLayout);
        }
        if (!TextUtils.isEmpty(item.getContentDescription())) {
            tab.setContentDescription(item.getContentDescription());
        }
        addTab(tab);
    }

    private boolean isScrollingEnabled() {
        return getTabMode() == 0 || getTabMode() == 2;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isScrollingEnabled() && super.onInterceptTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == 8 && !isScrollingEnabled()) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Deprecated
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        setOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void setOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.selectedListener;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
        }
        this.selectedListener = listener;
        if (listener != null) {
            addOnTabSelectedListener(listener);
        }
    }

    public void addOnTabSelectedListener(OnTabSelectedListener listener) {
        addOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void addOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        if (!this.selectedListeners.contains(listener)) {
            this.selectedListeners.add(listener);
        }
    }

    public void removeOnTabSelectedListener(OnTabSelectedListener listener) {
        removeOnTabSelectedListener((BaseOnTabSelectedListener) listener);
    }

    @Deprecated
    public void removeOnTabSelectedListener(BaseOnTabSelectedListener listener) {
        this.selectedListeners.remove(listener);
    }

    public void clearOnTabSelectedListeners() {
        this.selectedListeners.clear();
    }

    public Tab newTab() {
        Tab tab = createTabFromPool();
        tab.parent = this;
        tab.view = createTabView(tab);
        if (Tab.access$000(tab) != -1) {
            tab.view.setId(Tab.access$000(tab));
        }
        return tab;
    }

    protected Tab createTabFromPool() {
        Tab tab = (Tab) tabPool.acquire();
        if (tab == null) {
            return new Tab();
        }
        return tab;
    }

    protected boolean releaseFromTabPool(Tab tab) {
        return tabPool.release(tab);
    }

    public int getTabCount() {
        return this.tabs.size();
    }

    public Tab getTabAt(int index) {
        if (index < 0 || index >= getTabCount()) {
            return null;
        }
        return (Tab) this.tabs.get(index);
    }

    public int getSelectedTabPosition() {
        Tab tab = this.selectedTab;
        if (tab != null) {
            return tab.getPosition();
        }
        return -1;
    }

    public void removeTab(Tab tab) {
        if (tab.parent != this) {
            throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
        }
        removeTabAt(tab.getPosition());
    }

    public void removeTabAt(int position) {
        Tab tab = this.selectedTab;
        int selectedTabPosition = tab != null ? tab.getPosition() : 0;
        removeTabViewAt(position);
        Tab removedTab = (Tab) this.tabs.remove(position);
        if (removedTab != null) {
            removedTab.reset();
            releaseFromTabPool(removedTab);
        }
        int newTabCount = this.tabs.size();
        for (int i = position; i < newTabCount; i++) {
            ((Tab) this.tabs.get(i)).setPosition(i);
        }
        if (selectedTabPosition == position) {
            selectTab(this.tabs.isEmpty() ? null : (Tab) this.tabs.get(Math.max(0, position - 1)));
        }
    }

    public void removeAllTabs() {
        for (int i = this.slidingTabIndicator.getChildCount() - 1; i >= 0; i--) {
            removeTabViewAt(i);
        }
        Iterator<Tab> i2 = this.tabs.iterator();
        while (i2.hasNext()) {
            Tab tab = (Tab) i2.next();
            i2.remove();
            tab.reset();
            releaseFromTabPool(tab);
        }
        this.selectedTab = null;
    }

    public void setTabMode(int mode) {
        if (mode != this.mode) {
            this.mode = mode;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mode;
    }

    public void setTabGravity(int gravity) {
        if (this.tabGravity != gravity) {
            this.tabGravity = gravity;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.tabGravity;
    }

    public void setSelectedTabIndicatorGravity(int indicatorGravity) {
        if (this.tabIndicatorGravity != indicatorGravity) {
            this.tabIndicatorGravity = indicatorGravity;
            ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
        }
    }

    public int getTabIndicatorGravity() {
        return this.tabIndicatorGravity;
    }

    public void setTabIndicatorAnimationMode(int tabIndicatorAnimationMode) {
        this.tabIndicatorAnimationMode = tabIndicatorAnimationMode;
        switch (tabIndicatorAnimationMode) {
            case 0:
                this.tabIndicatorInterpolator = new TabIndicatorInterpolator();
                return;
            case 1:
                this.tabIndicatorInterpolator = new ElasticTabIndicatorInterpolator();
                return;
            case 2:
                this.tabIndicatorInterpolator = new FadeTabIndicatorInterpolator();
                return;
            default:
                throw new IllegalArgumentException(tabIndicatorAnimationMode + " is not a valid TabIndicatorAnimationMode");
        }
    }

    public int getTabIndicatorAnimationMode() {
        return this.tabIndicatorAnimationMode;
    }

    public void setTabIndicatorFullWidth(boolean tabIndicatorFullWidth) {
        this.tabIndicatorFullWidth = tabIndicatorFullWidth;
        SlidingTabIndicator.access$100(this.slidingTabIndicator);
        ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
    }

    public boolean isTabIndicatorFullWidth() {
        return this.tabIndicatorFullWidth;
    }

    public void setInlineLabel(boolean inline) {
        if (this.inlineLabel != inline) {
            this.inlineLabel = inline;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                TabView childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    childAt.updateOrientation();
                }
            }
            applyModeAndGravity();
        }
    }

    public void setInlineLabelResource(int inlineResourceId) {
        setInlineLabel(getResources().getBoolean(inlineResourceId));
    }

    public boolean isInlineLabel() {
        return this.inlineLabel;
    }

    public void setUnboundedRipple(boolean unboundedRipple) {
        if (this.unboundedRipple != unboundedRipple) {
            this.unboundedRipple = unboundedRipple;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                TabView childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    TabView.access$200(childAt, getContext());
                }
            }
        }
    }

    public void setUnboundedRippleResource(int unboundedRippleResourceId) {
        setUnboundedRipple(getResources().getBoolean(unboundedRippleResourceId));
    }

    public boolean hasUnboundedRipple() {
        return this.unboundedRipple;
    }

    public void setTabTextColors(ColorStateList textColor) {
        if (this.tabTextColors != textColor) {
            this.tabTextColors = textColor;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.tabTextColors;
    }

    public void setTabTextColors(int normalColor, int selectedColor) {
        setTabTextColors(createColorStateList(normalColor, selectedColor));
    }

    public void setTabIconTint(ColorStateList iconTint) {
        if (this.tabIconTint != iconTint) {
            this.tabIconTint = iconTint;
            updateAllTabs();
        }
    }

    public void setTabIconTintResource(int iconTintResourceId) {
        setTabIconTint(AppCompatResources.getColorStateList(getContext(), iconTintResourceId));
    }

    public ColorStateList getTabIconTint() {
        return this.tabIconTint;
    }

    public ColorStateList getTabRippleColor() {
        return this.tabRippleColorStateList;
    }

    public void setTabRippleColor(ColorStateList color) {
        if (this.tabRippleColorStateList != color) {
            this.tabRippleColorStateList = color;
            for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
                TabView childAt = this.slidingTabIndicator.getChildAt(i);
                if (childAt instanceof TabView) {
                    TabView.access$200(childAt, getContext());
                }
            }
        }
    }

    public void setTabRippleColorResource(int tabRippleColorResourceId) {
        setTabRippleColor(AppCompatResources.getColorStateList(getContext(), tabRippleColorResourceId));
    }

    public Drawable getTabSelectedIndicator() {
        return this.tabSelectedIndicator;
    }

    public void setSelectedTabIndicator(Drawable tabSelectedIndicator) {
        if (this.tabSelectedIndicator != tabSelectedIndicator) {
            Drawable gradientDrawable = tabSelectedIndicator != null ? tabSelectedIndicator : new GradientDrawable();
            this.tabSelectedIndicator = gradientDrawable;
            int i = this.tabIndicatorHeight;
            if (i == -1) {
                i = gradientDrawable.getIntrinsicHeight();
            }
            int indicatorHeight = i;
            this.slidingTabIndicator.setSelectedIndicatorHeight(indicatorHeight);
        }
    }

    public void setSelectedTabIndicator(int tabSelectedIndicatorResourceId) {
        if (tabSelectedIndicatorResourceId != 0) {
            setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), tabSelectedIndicatorResourceId));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(ViewPager viewPager, boolean autoRefresh) {
        setupWithViewPager(viewPager, autoRefresh, false);
    }

    private void setupWithViewPager(ViewPager viewPager, boolean autoRefresh, boolean implicitSetup) {
        ViewPager viewPager2 = this.viewPager;
        if (viewPager2 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.pageChangeListener;
            if (tabLayoutOnPageChangeListener != null) {
                viewPager2.removeOnPageChangeListener(tabLayoutOnPageChangeListener);
            }
            AdapterChangeListener adapterChangeListener = this.adapterChangeListener;
            if (adapterChangeListener != null) {
                this.viewPager.removeOnAdapterChangeListener(adapterChangeListener);
            }
        }
        BaseOnTabSelectedListener baseOnTabSelectedListener = this.currentVpSelectedListener;
        if (baseOnTabSelectedListener != null) {
            removeOnTabSelectedListener(baseOnTabSelectedListener);
            this.currentVpSelectedListener = null;
        }
        if (viewPager != null) {
            this.viewPager = viewPager;
            if (this.pageChangeListener == null) {
                this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.pageChangeListener.reset();
            viewPager.addOnPageChangeListener(this.pageChangeListener);
            ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
            this.currentVpSelectedListener = viewPagerOnTabSelectedListener;
            addOnTabSelectedListener((BaseOnTabSelectedListener) viewPagerOnTabSelectedListener);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter, autoRefresh);
            }
            if (this.adapterChangeListener == null) {
                this.adapterChangeListener = new AdapterChangeListener();
            }
            this.adapterChangeListener.setAutoRefresh(autoRefresh);
            viewPager.addOnAdapterChangeListener(this.adapterChangeListener);
            setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.viewPager = null;
            setPagerAdapter(null, false);
        }
        this.setupViewPagerImplicitly = implicitSetup;
    }

    @Deprecated
    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        setPagerAdapter(adapter, false);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (this.viewPager == null) {
            ViewParent vp = getParent();
            if (vp instanceof ViewPager) {
                setupWithViewPager((ViewPager) vp, true, true);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.setupViewPagerImplicitly) {
            setupWithViewPager(null);
            this.setupViewPagerImplicitly = false;
        }
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    void setPagerAdapter(PagerAdapter adapter, boolean addObserver) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter = this.pagerAdapter;
        if (pagerAdapter != null && (dataSetObserver = this.pagerAdapterObserver) != null) {
            pagerAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        this.pagerAdapter = adapter;
        if (addObserver && adapter != null) {
            if (this.pagerAdapterObserver == null) {
                this.pagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(this.pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    void populateFromPagerAdapter() {
        int curItem;
        removeAllTabs();
        PagerAdapter pagerAdapter = this.pagerAdapter;
        if (pagerAdapter != null) {
            int adapterCount = pagerAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                addTab(newTab().setText(this.pagerAdapter.getPageTitle(i)), false);
            }
            ViewPager viewPager = this.viewPager;
            if (viewPager != null && adapterCount > 0 && (curItem = viewPager.getCurrentItem()) != getSelectedTabPosition() && curItem < getTabCount()) {
                selectTab(getTabAt(curItem));
            }
        }
    }

    private void updateAllTabs() {
        int z = this.tabs.size();
        for (int i = 0; i < z; i++) {
            ((Tab) this.tabs.get(i)).updateView();
        }
    }

    private TabView createTabView(Tab tab) {
        Pools.Pool pool = this.tabViewPool;
        TabView tabView = pool != null ? (TabView) pool.acquire() : null;
        if (tabView == null) {
            tabView = new TabView(getContext());
        }
        tabView.setTab(tab);
        tabView.setFocusable(true);
        tabView.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(Tab.access$300(tab))) {
            tabView.setContentDescription(Tab.access$400(tab));
        } else {
            tabView.setContentDescription(Tab.access$300(tab));
        }
        return tabView;
    }

    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        this.tabs.add(position, tab);
        int count = this.tabs.size();
        for (int i = position + 1; i < count; i++) {
            ((Tab) this.tabs.get(i)).setPosition(i);
        }
    }

    private void addTabView(Tab tab) {
        View view = tab.view;
        view.setSelected(false);
        view.setActivated(false);
        this.slidingTabIndicator.addView(view, tab.getPosition(), createLayoutParamsForTabs());
    }

    public void addView(View child) {
        addViewInternal(child);
    }

    public void addView(View child, int index) {
        addViewInternal(child);
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        addViewInternal(child);
    }

    private void addViewInternal(View child) {
        if (child instanceof TabItem) {
            addTabFromItemView((TabItem) child);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(lp);
        return lp;
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams lp) {
        if (this.mode == 1 && this.tabGravity == 0) {
            lp.width = 0;
            lp.weight = 1.0f;
        } else {
            lp.width = -2;
            lp.weight = 0.0f;
        }
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getTabCount(), false, 1));
    }

    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            TabView childAt = this.slidingTabIndicator.getChildAt(i);
            if (childAt instanceof TabView) {
                TabView.access$500(childAt, canvas);
            }
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int idealHeight = Math.round(ViewUtils.dpToPx(getContext(), getDefaultHeight()));
        switch (View.MeasureSpec.getMode(heightMeasureSpec)) {
            case Integer.MIN_VALUE:
                if (getChildCount() == 1 && View.MeasureSpec.getSize(heightMeasureSpec) >= idealHeight) {
                    getChildAt(0).setMinimumHeight(idealHeight);
                    break;
                }
                break;
            case 0:
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getPaddingTop() + idealHeight + getPaddingBottom(), 1073741824);
                break;
        }
        int specWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) != 0) {
            int i = this.requestedTabMaxWidth;
            if (i <= 0) {
                i = (int) (specWidth - ViewUtils.dpToPx(getContext(), 56));
            }
            this.tabMaxWidth = i;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 1) {
            View child = getChildAt(0);
            boolean remeasure = false;
            switch (this.mode) {
                case 0:
                case 2:
                    remeasure = child.getMeasuredWidth() < getMeasuredWidth();
                    break;
                case 1:
                    remeasure = child.getMeasuredWidth() != getMeasuredWidth();
                    break;
            }
            if (remeasure) {
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height);
                int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    private void removeTabViewAt(int position) {
        TabView view = this.slidingTabIndicator.getChildAt(position);
        this.slidingTabIndicator.removeViewAt(position);
        if (view != null) {
            view.reset();
            this.tabViewPool.release(view);
        }
        requestLayout();
    }

    private void animateToTab(int newPosition) {
        if (newPosition == -1) {
            return;
        }
        if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.slidingTabIndicator.childrenNeedLayout()) {
            setScrollPosition(newPosition, 0.0f, true);
            return;
        }
        int startScrollX = getScrollX();
        int targetScrollX = calculateScrollXForTab(newPosition, 0.0f);
        if (startScrollX != targetScrollX) {
            ensureScrollAnimator();
            this.scrollAnimator.setIntValues(new int[]{startScrollX, targetScrollX});
            this.scrollAnimator.start();
        }
        this.slidingTabIndicator.animateIndicatorToPosition(newPosition, this.tabIndicatorAnimationDuration);
    }

    private void ensureScrollAnimator() {
        if (this.scrollAnimator == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.scrollAnimator = valueAnimator;
            valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.scrollAnimator.setDuration(this.tabIndicatorAnimationDuration);
            this.scrollAnimator.addUpdateListener(new 1());
        }
    }

    class 1 implements ValueAnimator.AnimatorUpdateListener {
        1() {
        }

        public void onAnimationUpdate(ValueAnimator animator) {
            TabLayout.this.scrollTo(((Integer) animator.getAnimatedValue()).intValue(), 0);
        }
    }

    void setScrollAnimatorListener(Animator.AnimatorListener listener) {
        ensureScrollAnimator();
        this.scrollAnimator.addListener(listener);
    }

    private void setSelectedTabView(int position) {
        int tabCount = this.slidingTabIndicator.getChildCount();
        if (position < tabCount) {
            int i = 0;
            while (i < tabCount) {
                View child = this.slidingTabIndicator.getChildAt(i);
                boolean z = false;
                child.setSelected(i == position);
                if (i == position) {
                    z = true;
                }
                child.setActivated(z);
                i++;
            }
        }
    }

    public void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    public void selectTab(Tab tab, boolean updateIndicator) {
        Tab currentTab = this.selectedTab;
        if (currentTab == tab) {
            if (currentTab != null) {
                dispatchTabReselected(tab);
                animateToTab(tab.getPosition());
                return;
            }
            return;
        }
        int newPosition = tab != null ? tab.getPosition() : -1;
        if (updateIndicator) {
            if ((currentTab == null || currentTab.getPosition() == -1) && newPosition != -1) {
                setScrollPosition(newPosition, 0.0f, true);
            } else {
                animateToTab(newPosition);
            }
            if (newPosition != -1) {
                setSelectedTabView(newPosition);
            }
        }
        this.selectedTab = tab;
        if (currentTab != null) {
            dispatchTabUnselected(currentTab);
        }
        if (tab != null) {
            dispatchTabSelected(tab);
        }
    }

    private void dispatchTabSelected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            ((BaseOnTabSelectedListener) this.selectedListeners.get(i)).onTabSelected(tab);
        }
    }

    private void dispatchTabUnselected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            ((BaseOnTabSelectedListener) this.selectedListeners.get(i)).onTabUnselected(tab);
        }
    }

    private void dispatchTabReselected(Tab tab) {
        for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
            ((BaseOnTabSelectedListener) this.selectedListeners.get(i)).onTabReselected(tab);
        }
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        View selectedChild;
        View nextChild;
        int i = this.mode;
        if ((i != 0 && i != 2) || (selectedChild = this.slidingTabIndicator.getChildAt(position)) == null) {
            return 0;
        }
        if (position + 1 < this.slidingTabIndicator.getChildCount()) {
            nextChild = this.slidingTabIndicator.getChildAt(position + 1);
        } else {
            nextChild = null;
        }
        int selectedWidth = selectedChild.getWidth();
        int nextWidth = nextChild != null ? nextChild.getWidth() : 0;
        int scrollBase = (selectedChild.getLeft() + (selectedWidth / 2)) - (getWidth() / 2);
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);
        if (ViewCompat.getLayoutDirection(this) == 0) {
            return scrollBase + scrollOffset;
        }
        return scrollBase - scrollOffset;
    }

    private void applyModeAndGravity() {
        int paddingStart = 0;
        int i = this.mode;
        if (i == 0 || i == 2) {
            paddingStart = Math.max(0, this.contentInsetStart - this.tabPaddingStart);
        }
        ViewCompat.setPaddingRelative(this.slidingTabIndicator, paddingStart, 0, 0, 0);
        switch (this.mode) {
            case 0:
                applyGravityForModeScrollable(this.tabGravity);
                break;
            case 1:
            case 2:
                if (this.tabGravity == 2) {
                    Log.w("TabLayout", "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
                }
                this.slidingTabIndicator.setGravity(1);
                break;
        }
        updateTabViews(true);
    }

    private void applyGravityForModeScrollable(int tabGravity) {
        switch (tabGravity) {
            case 0:
                Log.w("TabLayout", "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
                break;
            case 1:
                this.slidingTabIndicator.setGravity(1);
                return;
            case 2:
                break;
            default:
                return;
        }
        this.slidingTabIndicator.setGravity(8388611);
    }

    void updateTabViews(boolean requestLayout) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View child = this.slidingTabIndicator.getChildAt(i);
            child.setMinimumWidth(getTabMinWidth());
            updateTabViewLayoutParams((LinearLayout.LayoutParams) child.getLayoutParams());
            if (requestLayout) {
                child.requestLayout();
            }
        }
    }

    public static class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence contentDesc;
        private View customView;
        private Drawable icon;
        public TabLayout parent;
        private Object tag;
        private CharSequence text;
        public TabView view;
        private int position = -1;
        private int labelVisibilityMode = 1;
        private int id = -1;

        static /* synthetic */ int access$000(Tab x0) {
            return x0.id;
        }

        static /* synthetic */ int access$1200(Tab x0) {
            return x0.labelVisibilityMode;
        }

        static /* synthetic */ CharSequence access$300(Tab x0) {
            return x0.contentDesc;
        }

        static /* synthetic */ CharSequence access$400(Tab x0) {
            return x0.text;
        }

        public Object getTag() {
            return this.tag;
        }

        public Tab setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Tab setId(int id) {
            this.id = id;
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.setId(id);
            }
            return this;
        }

        public int getId() {
            return this.id;
        }

        public View getCustomView() {
            return this.customView;
        }

        public Tab setCustomView(View view) {
            this.customView = view;
            updateView();
            return this;
        }

        public Tab setCustomView(int resId) {
            LayoutInflater inflater = LayoutInflater.from(this.view.getContext());
            return setCustomView(inflater.inflate(resId, this.view, false));
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public int getPosition() {
            return this.position;
        }

        void setPosition(int position) {
            this.position = position;
        }

        public CharSequence getText() {
            return this.text;
        }

        public Tab setIcon(Drawable icon) {
            this.icon = icon;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && TabView.access$600(this.view) && TabView.access$700(this.view).isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public Tab setIcon(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return setIcon(AppCompatResources.getDrawable(tabLayout.getContext(), resId));
        }

        public Tab setText(CharSequence text) {
            if (TextUtils.isEmpty(this.contentDesc) && !TextUtils.isEmpty(text)) {
                this.view.setContentDescription(text);
            }
            this.text = text;
            updateView();
            return this;
        }

        public Tab setText(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return setText(tabLayout.getResources().getText(resId));
        }

        public BadgeDrawable getOrCreateBadge() {
            return TabView.access$800(this.view);
        }

        public void removeBadge() {
            TabView.access$900(this.view);
        }

        public BadgeDrawable getBadge() {
            return TabView.access$1000(this.view);
        }

        public Tab setTabLabelVisibility(int mode) {
            this.labelVisibilityMode = mode;
            if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
                this.parent.updateTabViews(true);
            }
            updateView();
            if (BadgeUtils.USE_COMPAT_PARENT && TabView.access$600(this.view) && TabView.access$700(this.view).isVisible()) {
                this.view.invalidate();
            }
            return this;
        }

        public int getTabLabelVisibility() {
            return this.labelVisibilityMode;
        }

        public void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            tabLayout.selectTab(this);
        }

        public boolean isSelected() {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            int selectedPosition = tabLayout.getSelectedTabPosition();
            return selectedPosition != -1 && selectedPosition == this.position;
        }

        public Tab setContentDescription(int resId) {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return setContentDescription(tabLayout.getResources().getText(resId));
        }

        public Tab setContentDescription(CharSequence contentDesc) {
            this.contentDesc = contentDesc;
            updateView();
            return this;
        }

        public CharSequence getContentDescription() {
            TabView tabView = this.view;
            if (tabView == null) {
                return null;
            }
            return tabView.getContentDescription();
        }

        void updateView() {
            TabView tabView = this.view;
            if (tabView != null) {
                tabView.update();
            }
        }

        void reset() {
            this.parent = null;
            this.view = null;
            this.tag = null;
            this.icon = null;
            this.id = -1;
            this.text = null;
            this.contentDesc = null;
            this.position = -1;
            this.customView = null;
        }
    }

    public final class TabView extends LinearLayout {
        private View badgeAnchorView;
        private BadgeDrawable badgeDrawable;
        private Drawable baseBackgroundDrawable;
        private ImageView customIconView;
        private TextView customTextView;
        private View customView;
        private int defaultMaxLines;
        private ImageView iconView;
        private Tab tab;
        private TextView textView;

        static /* synthetic */ BadgeDrawable access$1000(TabView x0) {
            return x0.getBadge();
        }

        static /* synthetic */ void access$1100(TabView x0, View x1) {
            x0.tryUpdateBadgeDrawableBounds(x1);
        }

        static /* synthetic */ void access$200(TabView x0, Context x1) {
            x0.updateBackgroundDrawable(x1);
        }

        static /* synthetic */ void access$500(TabView x0, Canvas x1) {
            x0.drawBackground(x1);
        }

        static /* synthetic */ boolean access$600(TabView x0) {
            return x0.hasBadgeDrawable();
        }

        static /* synthetic */ BadgeDrawable access$700(TabView x0) {
            return x0.badgeDrawable;
        }

        static /* synthetic */ BadgeDrawable access$800(TabView x0) {
            return x0.getOrCreateBadge();
        }

        static /* synthetic */ void access$900(TabView x0) {
            x0.removeBadge();
        }

        public TabView(Context context) {
            super(context);
            this.defaultMaxLines = 2;
            updateBackgroundDrawable(context);
            ViewCompat.setPaddingRelative(this, TabLayout.this.tabPaddingStart, TabLayout.this.tabPaddingTop, TabLayout.this.tabPaddingEnd, TabLayout.this.tabPaddingBottom);
            setGravity(17);
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            setClickable(true);
            ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
        }

        private void updateBackgroundDrawable(Context context) {
            Drawable background;
            if (TabLayout.this.tabBackgroundResId != 0) {
                Drawable drawable = AppCompatResources.getDrawable(context, TabLayout.this.tabBackgroundResId);
                this.baseBackgroundDrawable = drawable;
                if (drawable != null && drawable.isStateful()) {
                    this.baseBackgroundDrawable.setState(getDrawableState());
                }
            } else {
                this.baseBackgroundDrawable = null;
            }
            Drawable contentDrawable = new GradientDrawable();
            ((GradientDrawable) contentDrawable).setColor(0);
            if (TabLayout.this.tabRippleColorStateList != null) {
                Drawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(1.0E-5f);
                gradientDrawable.setColor(-1);
                ColorStateList rippleColor = RippleUtils.convertToRippleDrawableColor(TabLayout.this.tabRippleColorStateList);
                if (Build.VERSION.SDK_INT >= 21) {
                    background = new RippleDrawable(rippleColor, TabLayout.this.unboundedRipple ? null : contentDrawable, TabLayout.this.unboundedRipple ? null : gradientDrawable);
                } else {
                    Drawable rippleDrawable = DrawableCompat.wrap(gradientDrawable);
                    DrawableCompat.setTintList(rippleDrawable, rippleColor);
                    background = new LayerDrawable(new Drawable[]{contentDrawable, rippleDrawable});
                }
            } else {
                background = contentDrawable;
            }
            ViewCompat.setBackground(this, background);
            TabLayout.this.invalidate();
        }

        private void drawBackground(Canvas canvas) {
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null) {
                drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                this.baseBackgroundDrawable.draw(canvas);
            }
        }

        protected void drawableStateChanged() {
            super.drawableStateChanged();
            boolean changed = false;
            int[] state = getDrawableState();
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null && drawable.isStateful()) {
                changed = false | this.baseBackgroundDrawable.setState(state);
            }
            if (changed) {
                invalidate();
                TabLayout.this.invalidate();
            }
        }

        public boolean performClick() {
            boolean handled = super.performClick();
            if (this.tab != null) {
                if (!handled) {
                    playSoundEffect(0);
                }
                this.tab.select();
                return true;
            }
            return handled;
        }

        public void setSelected(boolean selected) {
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected && Build.VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            TextView textView = this.textView;
            if (textView != null) {
                textView.setSelected(selected);
            }
            ImageView imageView = this.iconView;
            if (imageView != null) {
                imageView.setSelected(selected);
            }
            View view = this.customView;
            if (view != null) {
                view.setSelected(selected);
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            BadgeDrawable badgeDrawable = this.badgeDrawable;
            if (badgeDrawable != null && badgeDrawable.isVisible()) {
                CharSequence customContentDescription = getContentDescription();
                info.setContentDescription(customContentDescription + ", " + this.badgeDrawable.getContentDescription());
            }
            AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
            infoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.tab.getPosition(), 1, false, isSelected()));
            if (isSelected()) {
                infoCompat.setClickable(false);
                infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }
            infoCompat.setRoleDescription(getResources().getString(R.string.item_view_role_description));
        }

        public void onMeasure(int origWidthMeasureSpec, int origHeightMeasureSpec) {
            int widthMeasureSpec;
            Layout layout;
            int specWidthSize = View.MeasureSpec.getSize(origWidthMeasureSpec);
            int specWidthMode = View.MeasureSpec.getMode(origWidthMeasureSpec);
            int maxWidth = TabLayout.this.getTabMaxWidth();
            if (maxWidth > 0 && (specWidthMode == 0 || specWidthSize > maxWidth)) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, Integer.MIN_VALUE);
            } else {
                widthMeasureSpec = origWidthMeasureSpec;
            }
            super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
            if (this.textView != null) {
                float textSize = TabLayout.this.tabTextSize;
                int maxLines = this.defaultMaxLines;
                ImageView imageView = this.iconView;
                if (imageView != null && imageView.getVisibility() == 0) {
                    maxLines = 1;
                } else {
                    TextView textView = this.textView;
                    if (textView != null && textView.getLineCount() > 1) {
                        textSize = TabLayout.this.tabTextMultiLineSize;
                    }
                }
                float curTextSize = this.textView.getTextSize();
                int curLineCount = this.textView.getLineCount();
                int curMaxLines = TextViewCompat.getMaxLines(this.textView);
                if (textSize != curTextSize || (curMaxLines >= 0 && maxLines != curMaxLines)) {
                    boolean updateTextView = true;
                    if (TabLayout.this.mode == 1 && textSize > curTextSize && curLineCount == 1 && ((layout = this.textView.getLayout()) == null || approximateLineWidth(layout, 0, textSize) > (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) {
                        updateTextView = false;
                    }
                    if (updateTextView) {
                        this.textView.setTextSize(0, textSize);
                        this.textView.setMaxLines(maxLines);
                        super.onMeasure(widthMeasureSpec, origHeightMeasureSpec);
                    }
                }
            }
        }

        void setTab(Tab tab) {
            if (tab != this.tab) {
                this.tab = tab;
                update();
            }
        }

        void reset() {
            setTab(null);
            setSelected(false);
        }

        final void update() {
            Tab tab = this.tab;
            View custom = tab != null ? tab.getCustomView() : null;
            if (custom != null) {
                ViewGroup parent = custom.getParent();
                if (parent != this) {
                    if (parent != null) {
                        parent.removeView(custom);
                    }
                    addView(custom);
                }
                this.customView = custom;
                TextView textView = this.textView;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.iconView.setImageDrawable((Drawable) null);
                }
                TextView findViewById = custom.findViewById(16908308);
                this.customTextView = findViewById;
                if (findViewById != null) {
                    this.defaultMaxLines = TextViewCompat.getMaxLines(findViewById);
                }
                this.customIconView = custom.findViewById(16908294);
            } else {
                View view = this.customView;
                if (view != null) {
                    removeView(view);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            if (this.customView == null) {
                if (this.iconView == null) {
                    inflateAndAddDefaultIconView();
                }
                if (this.textView == null) {
                    inflateAndAddDefaultTextView();
                    this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
                }
                TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
                if (TabLayout.this.tabTextColors != null) {
                    this.textView.setTextColor(TabLayout.this.tabTextColors);
                }
                updateTextAndIcon(this.textView, this.iconView);
                tryUpdateBadgeAnchor();
                addOnLayoutChangeListener(this.iconView);
                addOnLayoutChangeListener(this.textView);
            } else {
                TextView textView2 = this.customTextView;
                if (textView2 != null || this.customIconView != null) {
                    updateTextAndIcon(textView2, this.customIconView);
                }
            }
            if (tab != null && !TextUtils.isEmpty(Tab.access$300(tab))) {
                setContentDescription(Tab.access$300(tab));
            }
            setSelected(tab != null && tab.isSelected());
        }

        private void inflateAndAddDefaultIconView() {
            TabView tabView = this;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                tabView = createPreApi18BadgeAnchorRoot();
                addView(tabView, 0);
            }
            ImageView inflate = LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, tabView, false);
            this.iconView = inflate;
            tabView.addView(inflate, 0);
        }

        private void inflateAndAddDefaultTextView() {
            TabView tabView = this;
            if (BadgeUtils.USE_COMPAT_PARENT) {
                tabView = createPreApi18BadgeAnchorRoot();
                addView(tabView);
            }
            TextView inflate = LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, tabView, false);
            this.textView = inflate;
            tabView.addView(inflate);
        }

        private FrameLayout createPreApi18BadgeAnchorRoot() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(-2, -2);
            frameLayout.setLayoutParams(layoutparams);
            return frameLayout;
        }

        private BadgeDrawable getOrCreateBadge() {
            if (this.badgeDrawable == null) {
                this.badgeDrawable = BadgeDrawable.create(getContext());
            }
            tryUpdateBadgeAnchor();
            BadgeDrawable badgeDrawable = this.badgeDrawable;
            if (badgeDrawable == null) {
                throw new IllegalStateException("Unable to create badge");
            }
            return badgeDrawable;
        }

        private BadgeDrawable getBadge() {
            return this.badgeDrawable;
        }

        private void removeBadge() {
            if (this.badgeAnchorView != null) {
                tryRemoveBadgeFromAnchor();
            }
            this.badgeDrawable = null;
        }

        private void addOnLayoutChangeListener(View view) {
            if (view == null) {
                return;
            }
            view.addOnLayoutChangeListener(new 1(view));
        }

        class 1 implements View.OnLayoutChangeListener {
            final /* synthetic */ View val$view;

            1(View view) {
                this.val$view = view;
            }

            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (this.val$view.getVisibility() == 0) {
                    TabView.access$1100(TabView.this, this.val$view);
                }
            }
        }

        private void tryUpdateBadgeAnchor() {
            Tab tab;
            Tab tab2;
            if (!hasBadgeDrawable()) {
                return;
            }
            if (this.customView != null) {
                tryRemoveBadgeFromAnchor();
                return;
            }
            if (this.iconView != null && (tab2 = this.tab) != null && tab2.getIcon() != null) {
                ImageView imageView = this.badgeAnchorView;
                ImageView imageView2 = this.iconView;
                if (imageView != imageView2) {
                    tryRemoveBadgeFromAnchor();
                    tryAttachBadgeToAnchor(this.iconView);
                    return;
                } else {
                    tryUpdateBadgeDrawableBounds(imageView2);
                    return;
                }
            }
            if (this.textView != null && (tab = this.tab) != null && tab.getTabLabelVisibility() == 1) {
                TextView textView = this.badgeAnchorView;
                TextView textView2 = this.textView;
                if (textView != textView2) {
                    tryRemoveBadgeFromAnchor();
                    tryAttachBadgeToAnchor(this.textView);
                    return;
                } else {
                    tryUpdateBadgeDrawableBounds(textView2);
                    return;
                }
            }
            tryRemoveBadgeFromAnchor();
        }

        private void tryAttachBadgeToAnchor(View anchorView) {
            if (hasBadgeDrawable() && anchorView != null) {
                clipViewToPaddingForBadge(false);
                BadgeUtils.attachBadgeDrawable(this.badgeDrawable, anchorView, getCustomParentForBadge(anchorView));
                this.badgeAnchorView = anchorView;
            }
        }

        private void tryRemoveBadgeFromAnchor() {
            if (!hasBadgeDrawable()) {
                return;
            }
            clipViewToPaddingForBadge(true);
            View view = this.badgeAnchorView;
            if (view != null) {
                BadgeUtils.detachBadgeDrawable(this.badgeDrawable, view);
                this.badgeAnchorView = null;
            }
        }

        private void clipViewToPaddingForBadge(boolean flag) {
            setClipChildren(flag);
            setClipToPadding(flag);
            ViewGroup parent = getParent();
            if (parent != null) {
                parent.setClipChildren(flag);
                parent.setClipToPadding(flag);
            }
        }

        final void updateOrientation() {
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            TextView textView = this.customTextView;
            if (textView != null || this.customIconView != null) {
                updateTextAndIcon(textView, this.customIconView);
            } else {
                updateTextAndIcon(this.textView, this.iconView);
            }
        }

        private void updateTextAndIcon(TextView textView, ImageView iconView) {
            Drawable icon;
            Tab tab = this.tab;
            if (tab != null && tab.getIcon() != null) {
                icon = DrawableCompat.wrap(this.tab.getIcon()).mutate();
            } else {
                icon = null;
            }
            if (icon != null) {
                DrawableCompat.setTintList(icon, TabLayout.this.tabIconTint);
                if (TabLayout.this.tabIconTintMode != null) {
                    DrawableCompat.setTintMode(icon, TabLayout.this.tabIconTintMode);
                }
            }
            Tab tab2 = this.tab;
            CharSequence text = tab2 != null ? tab2.getText() : null;
            if (iconView != null) {
                if (icon != null) {
                    iconView.setImageDrawable(icon);
                    iconView.setVisibility(0);
                    setVisibility(0);
                } else {
                    iconView.setVisibility(8);
                    iconView.setImageDrawable((Drawable) null);
                }
            }
            boolean hasText = !TextUtils.isEmpty(text);
            if (textView != null) {
                if (hasText) {
                    textView.setText(text);
                    if (Tab.access$1200(this.tab) == 1) {
                        textView.setVisibility(0);
                    } else {
                        textView.setVisibility(8);
                    }
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
            }
            if (iconView != null) {
                ViewGroup.MarginLayoutParams lp = iconView.getLayoutParams();
                int iconMargin = 0;
                if (hasText && iconView.getVisibility() == 0) {
                    iconMargin = (int) ViewUtils.dpToPx(getContext(), 8);
                }
                if (TabLayout.this.inlineLabel) {
                    if (iconMargin != MarginLayoutParamsCompat.getMarginEnd(lp)) {
                        MarginLayoutParamsCompat.setMarginEnd(lp, iconMargin);
                        lp.bottomMargin = 0;
                        iconView.setLayoutParams(lp);
                        iconView.requestLayout();
                    }
                } else if (iconMargin != lp.bottomMargin) {
                    lp.bottomMargin = iconMargin;
                    MarginLayoutParamsCompat.setMarginEnd(lp, 0);
                    iconView.setLayoutParams(lp);
                    iconView.requestLayout();
                }
            }
            Tab tab3 = this.tab;
            CharSequence contentDesc = tab3 != null ? Tab.access$300(tab3) : null;
            if (Build.VERSION.SDK_INT < 21 || Build.VERSION.SDK_INT > 23) {
                TooltipCompat.setTooltipText(this, hasText ? text : contentDesc);
            }
        }

        private void tryUpdateBadgeDrawableBounds(View anchor) {
            if (hasBadgeDrawable() && anchor == this.badgeAnchorView) {
                BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, anchor, getCustomParentForBadge(anchor));
            }
        }

        private boolean hasBadgeDrawable() {
            return this.badgeDrawable != null;
        }

        private FrameLayout getCustomParentForBadge(View anchor) {
            if ((anchor == this.iconView || anchor == this.textView) && BadgeUtils.USE_COMPAT_PARENT) {
                return anchor.getParent();
            }
            return null;
        }

        int getContentWidth() {
            boolean initialized = false;
            int left = 0;
            int right = 0;
            View[] viewArr = {this.textView, this.iconView, this.customView};
            for (int i = 0; i < 3; i++) {
                View view = viewArr[i];
                if (view != null && view.getVisibility() == 0) {
                    int left2 = view.getLeft();
                    if (initialized) {
                        left2 = Math.min(left, left2);
                    }
                    left = left2;
                    int right2 = view.getRight();
                    if (initialized) {
                        right2 = Math.max(right, right2);
                    }
                    right = right2;
                    initialized = true;
                }
            }
            return right - left;
        }

        int getContentHeight() {
            boolean initialized = false;
            int top = 0;
            int bottom = 0;
            View[] viewArr = {this.textView, this.iconView, this.customView};
            for (int i = 0; i < 3; i++) {
                View view = viewArr[i];
                if (view != null && view.getVisibility() == 0) {
                    int top2 = view.getTop();
                    if (initialized) {
                        top2 = Math.min(top, top2);
                    }
                    top = top2;
                    int bottom2 = view.getBottom();
                    if (initialized) {
                        bottom2 = Math.max(bottom, bottom2);
                    }
                    bottom = bottom2;
                    initialized = true;
                }
            }
            return bottom - top;
        }

        public Tab getTab() {
            return this.tab;
        }

        private float approximateLineWidth(Layout layout, int line, float textSize) {
            return layout.getLineWidth(line) * (textSize / layout.getPaint().getTextSize());
        }
    }

    class SlidingTabIndicator extends LinearLayout {
        ValueAnimator indicatorAnimator;
        private int layoutDirection;
        int selectedPosition;
        float selectionOffset;

        static /* synthetic */ void access$100(SlidingTabIndicator x0) {
            x0.jumpIndicatorToSelectedPosition();
        }

        static /* synthetic */ void access$1400(SlidingTabIndicator x0, View x1, View x2, float x3) {
            x0.tweenIndicatorPosition(x1, x2, x3);
        }

        SlidingTabIndicator(Context context) {
            super(context);
            this.selectedPosition = -1;
            this.layoutDirection = -1;
            setWillNotDraw(false);
        }

        void setSelectedIndicatorHeight(int height) {
            Rect bounds = TabLayout.this.tabSelectedIndicator.getBounds();
            TabLayout.this.tabSelectedIndicator.setBounds(bounds.left, 0, bounds.right, height);
            requestLayout();
        }

        boolean childrenNeedLayout() {
            int z = getChildCount();
            for (int i = 0; i < z; i++) {
                View child = getChildAt(i);
                if (child.getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.indicatorAnimator.cancel();
            }
            this.selectedPosition = position;
            this.selectionOffset = positionOffset;
            View selectedTitle = getChildAt(position);
            View nextTitle = getChildAt(this.selectedPosition + 1);
            tweenIndicatorPosition(selectedTitle, nextTitle, this.selectionOffset);
        }

        float getIndicatorPosition() {
            return this.selectedPosition + this.selectionOffset;
        }

        public void onRtlPropertiesChanged(int layoutDirection) {
            super.onRtlPropertiesChanged(layoutDirection);
            if (Build.VERSION.SDK_INT < 23 && this.layoutDirection != layoutDirection) {
                requestLayout();
                this.layoutDirection = layoutDirection;
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (View.MeasureSpec.getMode(widthMeasureSpec) != 1073741824) {
                return;
            }
            if (TabLayout.this.tabGravity == 1 || TabLayout.this.mode == 2) {
                int count = getChildCount();
                int largestTabWidth = 0;
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() == 0) {
                        largestTabWidth = Math.max(largestTabWidth, child.getMeasuredWidth());
                    }
                }
                if (largestTabWidth <= 0) {
                    return;
                }
                int gutter = (int) ViewUtils.dpToPx(getContext(), 16);
                boolean remeasure = false;
                if (largestTabWidth * count <= getMeasuredWidth() - (gutter * 2)) {
                    for (int i2 = 0; i2 < count; i2++) {
                        LinearLayout.LayoutParams lp = getChildAt(i2).getLayoutParams();
                        if (lp.width != largestTabWidth || lp.weight != 0.0f) {
                            lp.width = largestTabWidth;
                            lp.weight = 0.0f;
                            remeasure = true;
                        }
                    }
                } else {
                    TabLayout.this.tabGravity = 0;
                    TabLayout.this.updateTabViews(false);
                    remeasure = true;
                }
                if (remeasure) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                updateOrRecreateIndicatorAnimation(false, this.selectedPosition, -1);
            } else {
                jumpIndicatorToSelectedPosition();
            }
        }

        private void jumpIndicatorToSelectedPosition() {
            View currentView = getChildAt(this.selectedPosition);
            TabIndicatorInterpolator access$1300 = TabLayout.access$1300(TabLayout.this);
            TabLayout tabLayout = TabLayout.this;
            access$1300.setIndicatorBoundsForTab(tabLayout, currentView, tabLayout.tabSelectedIndicator);
        }

        private void tweenIndicatorPosition(View startTitle, View endTitle, float fraction) {
            boolean hasVisibleTitle = startTitle != null && startTitle.getWidth() > 0;
            if (hasVisibleTitle) {
                TabIndicatorInterpolator access$1300 = TabLayout.access$1300(TabLayout.this);
                TabLayout tabLayout = TabLayout.this;
                access$1300.updateIndicatorForOffset(tabLayout, startTitle, endTitle, fraction, tabLayout.tabSelectedIndicator);
            } else {
                TabLayout.this.tabSelectedIndicator.setBounds(-1, TabLayout.this.tabSelectedIndicator.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }

        void animateIndicatorToPosition(int position, int duration) {
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.indicatorAnimator.cancel();
            }
            updateOrRecreateIndicatorAnimation(true, position, duration);
        }

        private void updateOrRecreateIndicatorAnimation(boolean recreateAnimation, int position, int duration) {
            View currentView = getChildAt(this.selectedPosition);
            View targetView = getChildAt(position);
            if (targetView == null) {
                jumpIndicatorToSelectedPosition();
                return;
            }
            ValueAnimator.AnimatorUpdateListener updateListener = new 1(currentView, targetView);
            if (recreateAnimation) {
                ValueAnimator animator = new ValueAnimator();
                this.indicatorAnimator = animator;
                animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                animator.setDuration(duration);
                animator.setFloatValues(new float[]{0.0f, 1.0f});
                animator.addUpdateListener(updateListener);
                animator.addListener(new 2(position));
                animator.start();
                return;
            }
            this.indicatorAnimator.removeAllUpdateListeners();
            this.indicatorAnimator.addUpdateListener(updateListener);
        }

        class 1 implements ValueAnimator.AnimatorUpdateListener {
            final /* synthetic */ View val$currentView;
            final /* synthetic */ View val$targetView;

            1(View view, View view2) {
                this.val$currentView = view;
                this.val$targetView = view2;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SlidingTabIndicator.access$1400(SlidingTabIndicator.this, this.val$currentView, this.val$targetView, valueAnimator.getAnimatedFraction());
            }
        }

        class 2 extends AnimatorListenerAdapter {
            final /* synthetic */ int val$position;

            2(int i) {
                this.val$position = i;
            }

            public void onAnimationStart(Animator animator) {
                SlidingTabIndicator.this.selectedPosition = this.val$position;
            }

            public void onAnimationEnd(Animator animator) {
                SlidingTabIndicator.this.selectedPosition = this.val$position;
            }
        }

        public void draw(Canvas canvas) {
            int indicatorHeight = TabLayout.this.tabSelectedIndicator.getBounds().height();
            if (indicatorHeight < 0) {
                indicatorHeight = TabLayout.this.tabSelectedIndicator.getIntrinsicHeight();
            }
            int indicatorTop = 0;
            int indicatorBottom = 0;
            switch (TabLayout.this.tabIndicatorGravity) {
                case 0:
                    indicatorTop = getHeight() - indicatorHeight;
                    indicatorBottom = getHeight();
                    break;
                case 1:
                    indicatorTop = (getHeight() - indicatorHeight) / 2;
                    indicatorBottom = (getHeight() + indicatorHeight) / 2;
                    break;
                case 2:
                    indicatorTop = 0;
                    indicatorBottom = indicatorHeight;
                    break;
                case 3:
                    indicatorTop = 0;
                    indicatorBottom = getHeight();
                    break;
            }
            if (TabLayout.this.tabSelectedIndicator.getBounds().width() > 0) {
                Rect indicatorBounds = TabLayout.this.tabSelectedIndicator.getBounds();
                TabLayout.this.tabSelectedIndicator.setBounds(indicatorBounds.left, indicatorTop, indicatorBounds.right, indicatorBottom);
                Drawable indicator = TabLayout.this.tabSelectedIndicator;
                if (TabLayout.access$1500(TabLayout.this) != 0) {
                    indicator = DrawableCompat.wrap(indicator);
                    if (Build.VERSION.SDK_INT == 21) {
                        indicator.setColorFilter(TabLayout.access$1500(TabLayout.this), PorterDuff.Mode.SRC_IN);
                    } else {
                        DrawableCompat.setTint(indicator, TabLayout.access$1500(TabLayout.this));
                    }
                } else if (Build.VERSION.SDK_INT == 21) {
                    indicator.setColorFilter((ColorFilter) null);
                } else {
                    DrawableCompat.setTintList(indicator, (ColorStateList) null);
                }
                indicator.draw(canvas);
            }
            super.draw(canvas);
        }
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        int[][] states = new int[2][];
        int[] colors = new int[2];
        states[0] = SELECTED_STATE_SET;
        colors[0] = selectedColor;
        int i = 0 + 1;
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        int i2 = i + 1;
        return new ColorStateList(states, colors);
    }

    private int getDefaultHeight() {
        boolean hasIconAndText = false;
        int i = 0;
        int count = this.tabs.size();
        while (true) {
            if (i < count) {
                Tab tab = (Tab) this.tabs.get(i);
                if (tab == null || tab.getIcon() == null || TextUtils.isEmpty(tab.getText())) {
                    i++;
                } else {
                    hasIconAndText = true;
                    break;
                }
            } else {
                break;
            }
        }
        return (!hasIconAndText || this.inlineLabel) ? 48 : 72;
    }

    private int getTabMinWidth() {
        int i = this.requestedTabMinWidth;
        if (i != -1) {
            return i;
        }
        int i2 = this.mode;
        if (i2 == 0 || i2 == 2) {
            return this.scrollableTabMinWidth;
        }
        return 0;
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return generateDefaultLayoutParams();
    }

    int getTabMaxWidth() {
        return this.tabMaxWidth;
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int previousScrollState;
        private int scrollState;
        private final WeakReference tabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference(tabLayout);
        }

        public void onPageScrollStateChanged(int state) {
            this.previousScrollState = this.scrollState;
            this.scrollState = state;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i = this.scrollState;
                boolean updateText = i != 2 || this.previousScrollState == 1;
                boolean updateIndicator = (i == 2 && this.previousScrollState == 0) ? false : true;
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        }

        public void onPageSelected(int position) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position && position < tabLayout.getTabCount()) {
                int i = this.scrollState;
                boolean updateIndicator = i == 0 || (i == 2 && this.previousScrollState == 0);
                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
            }
        }

        void reset() {
            this.scrollState = 0;
            this.previousScrollState = 0;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager viewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        public void onTabSelected(Tab tab) {
            this.viewPager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        private boolean autoRefresh;

        AdapterChangeListener() {
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
            if (TabLayout.this.viewPager == viewPager) {
                TabLayout.this.setPagerAdapter(newAdapter, this.autoRefresh);
            }
        }

        void setAutoRefresh(boolean autoRefresh) {
            this.autoRefresh = autoRefresh;
        }
    }
}
