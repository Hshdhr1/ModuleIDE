package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
abstract class BaseSlider extends View {
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION = "minSeparation(%s) must be greater or equal to 0";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE = "minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT = "minSeparation(%s) cannot be set as a dimension when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo(%s) must be greater than valueFrom(%s)";
    private static final int HALO_ALPHA = 63;
    private static final long LABEL_ANIMATION_ENTER_DURATION = 83;
    private static final long LABEL_ANIMATION_EXIT_DURATION = 117;
    private static final double THRESHOLD = 1.0E-4d;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    static final int UNIT_PX = 0;
    static final int UNIT_VALUE = 1;
    private static final String WARNING_FLOATING_POINT_ERROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.";
    private AccessibilityEventSender accessibilityEventSender;
    private final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final List changeListeners;
    private Drawable customThumbDrawable;
    private List customThumbDrawablesForValues;
    private final MaterialShapeDrawable defaultThumbDrawable;
    private int defaultThumbRadius;
    private boolean dirtyConfig;
    private int focusedThumbIdx;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Paint inactiveTicksPaint;
    private final Paint inactiveTrackPaint;
    private boolean isLongPress;
    private int labelBehavior;
    private final TooltipDrawableFactory labelMaker;
    private int labelPadding;
    private final List labels;
    private boolean labelsAreAnimatedIn;
    private ValueAnimator labelsInAnimator;
    private ValueAnimator labelsOutAnimator;
    private MotionEvent lastEvent;
    private int minTrackSidePadding;
    private final int scaledTouchSlop;
    private int separationUnit;
    private float stepSize;
    private boolean thumbIsPressed;
    private final Paint thumbPaint;
    private int thumbRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private boolean tickVisible;
    private float[] ticksCoordinates;
    private float touchDownX;
    private final List touchListeners;
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackHeight;
    private int trackSidePadding;
    private int trackTop;
    private int trackWidth;
    private float valueFrom;
    private float valueTo;
    private ArrayList values;
    private int widgetHeight;
    private static final String TAG = BaseSlider.class.getSimpleName();
    static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;

    private interface TooltipDrawableFactory {
        TooltipDrawable createTooltipDrawable();
    }

    static /* synthetic */ TooltipDrawable access$000(Context x0, TypedArray x1) {
        return parseLabelDrawable(x0, x1);
    }

    static /* synthetic */ List access$100(BaseSlider x0) {
        return x0.labels;
    }

    static /* synthetic */ AccessibilityHelper access$300(BaseSlider x0) {
        return x0.accessibilityHelper;
    }

    static /* synthetic */ String access$500(BaseSlider x0, float x1) {
        return x0.formatValue(x1);
    }

    static /* synthetic */ boolean access$600(BaseSlider x0, int x1, float x2) {
        return x0.snapThumbToValue(x1, x2);
    }

    static /* synthetic */ void access$700(BaseSlider x0) {
        x0.updateHaloHotspot();
    }

    static /* synthetic */ float access$800(BaseSlider x0, int x1) {
        return x0.calculateStepIncrement(x1);
    }

    public BaseSlider(Context context) {
        this(context, null);
    }

    public BaseSlider(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.sliderStyle);
    }

    public BaseSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.thumbIsPressed = false;
        this.values = new ArrayList();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.tickVisible = true;
        this.isLongPress = false;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.defaultThumbDrawable = materialShapeDrawable;
        this.customThumbDrawablesForValues = Collections.emptyList();
        this.separationUnit = 0;
        Context context2 = getContext();
        Paint paint = new Paint();
        this.inactiveTrackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.activeTrackPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        Paint paint3 = new Paint(1);
        this.thumbPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint4 = new Paint(1);
        this.haloPaint = paint4;
        paint4.setStyle(Paint.Style.FILL);
        Paint paint5 = new Paint();
        this.inactiveTicksPaint = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeCap(Paint.Cap.ROUND);
        Paint paint6 = new Paint();
        this.activeTicksPaint = paint6;
        paint6.setStyle(Paint.Style.STROKE);
        paint6.setStrokeCap(Paint.Cap.ROUND);
        loadResources(context2.getResources());
        this.labelMaker = new 1(attrs, defStyleAttr);
        processAttributes(context2, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        materialShapeDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper(this);
        this.accessibilityHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }

    class 1 implements TooltipDrawableFactory {
        final /* synthetic */ AttributeSet val$attrs;
        final /* synthetic */ int val$defStyleAttr;

        1(AttributeSet attributeSet, int i) {
            this.val$attrs = attributeSet;
            this.val$defStyleAttr = i;
        }

        public TooltipDrawable createTooltipDrawable() {
            TypedArray a = ThemeEnforcement.obtainStyledAttributes(BaseSlider.this.getContext(), this.val$attrs, R.styleable.Slider, this.val$defStyleAttr, BaseSlider.DEF_STYLE_RES, new int[0]);
            TooltipDrawable d = BaseSlider.access$000(BaseSlider.this.getContext(), a);
            a.recycle();
            return d;
        }
    }

    private void loadResources(Resources resources) {
        this.widgetHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.minTrackSidePadding = dimensionPixelOffset;
        this.trackSidePadding = dimensionPixelOffset;
        this.defaultThumbRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.trackTop = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_top);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
    }

    private void processAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        ColorStateList colorStateList4;
        ColorStateList colorStateList5;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.Slider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.valueFrom = a.getFloat(R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = a.getFloat(R.styleable.Slider_android_valueTo, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        this.stepSize = a.getFloat(R.styleable.Slider_android_stepSize, 0.0f);
        boolean hasTrackColor = a.hasValue(R.styleable.Slider_trackColor);
        int trackColorInactiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorInactive;
        int trackColorActiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorActive;
        ColorStateList trackColorInactive = MaterialResources.getColorStateList(context, a, trackColorInactiveRes);
        if (trackColorInactive != null) {
            colorStateList = trackColorInactive;
        } else {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_track_color);
        }
        setTrackInactiveTintList(colorStateList);
        ColorStateList trackColorActive = MaterialResources.getColorStateList(context, a, trackColorActiveRes);
        if (trackColorActive != null) {
            colorStateList2 = trackColorActive;
        } else {
            colorStateList2 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_track_color);
        }
        setTrackActiveTintList(colorStateList2);
        ColorStateList thumbColor = MaterialResources.getColorStateList(context, a, R.styleable.Slider_thumbColor);
        this.defaultThumbDrawable.setFillColor(thumbColor);
        if (a.hasValue(R.styleable.Slider_thumbStrokeColor)) {
            setThumbStrokeColor(MaterialResources.getColorStateList(context, a, R.styleable.Slider_thumbStrokeColor));
        }
        setThumbStrokeWidth(a.getDimension(R.styleable.Slider_thumbStrokeWidth, 0.0f));
        ColorStateList haloColor = MaterialResources.getColorStateList(context, a, R.styleable.Slider_haloColor);
        if (haloColor != null) {
            colorStateList3 = haloColor;
        } else {
            colorStateList3 = AppCompatResources.getColorStateList(context, R.color.material_slider_halo_color);
        }
        setHaloTintList(colorStateList3);
        this.tickVisible = a.getBoolean(R.styleable.Slider_tickVisible, true);
        boolean hasTickColor = a.hasValue(R.styleable.Slider_tickColor);
        int tickColorInactiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorInactive;
        int tickColorActiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorActive;
        ColorStateList tickColorInactive = MaterialResources.getColorStateList(context, a, tickColorInactiveRes);
        if (tickColorInactive != null) {
            colorStateList4 = tickColorInactive;
        } else {
            colorStateList4 = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_tick_marks_color);
        }
        setTickInactiveTintList(colorStateList4);
        ColorStateList tickColorActive = MaterialResources.getColorStateList(context, a, tickColorActiveRes);
        if (tickColorActive != null) {
            colorStateList5 = tickColorActive;
        } else {
            colorStateList5 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_tick_marks_color);
        }
        setTickActiveTintList(colorStateList5);
        setThumbRadius(a.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0));
        setHaloRadius(a.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
        setThumbElevation(a.getDimension(R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(a.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
        setLabelBehavior(a.getInt(R.styleable.Slider_labelBehavior, 0));
        if (!a.getBoolean(R.styleable.Slider_android_enabled, true)) {
            setEnabled(false);
        }
        a.recycle();
    }

    private static TooltipDrawable parseLabelDrawable(Context context, TypedArray a) {
        return TooltipDrawable.createFromAttributes(context, null, 0, a.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip));
    }

    private void maybeIncreaseTrackSidePadding() {
        int increasedSidePadding = Math.max(this.thumbRadius - this.defaultThumbRadius, 0);
        this.trackSidePadding = this.minTrackSidePadding + increasedSidePadding;
        if (ViewCompat.isLaidOut(this)) {
            updateTrackWidth(getWidth());
        }
    }

    private void validateValueFrom() {
        if (this.valueFrom >= this.valueTo) {
            throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", new Object[]{Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
    }

    private void validateValueTo() {
        if (this.valueTo <= this.valueFrom) {
            throw new IllegalStateException(String.format("valueTo(%s) must be greater than valueFrom(%s)", new Object[]{Float.valueOf(this.valueTo), Float.valueOf(this.valueFrom)}));
        }
    }

    private boolean valueLandsOnTick(float value) {
        return isMultipleOfStepSize(value - this.valueFrom);
    }

    private boolean isMultipleOfStepSize(float value) {
        double result = new BigDecimal(Float.toString(value)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        double round = Math.round(result);
        Double.isNaN(round);
        return Math.abs(round - result) < 1.0E-4d;
    }

    private void validateStepSize() {
        if (this.stepSize > 0.0f && !valueLandsOnTick(this.valueTo)) {
            throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", new Object[]{Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
    }

    private void validateValues() {
        Iterator it = this.values.iterator();
        while (it.hasNext()) {
            Float value = (Float) it.next();
            if (value.floatValue() < this.valueFrom || value.floatValue() > this.valueTo) {
                throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
            }
            if (this.stepSize > 0.0f && !valueLandsOnTick(value.floatValue())) {
                throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
            }
        }
    }

    private void validateMinSeparation() {
        float minSeparation = getMinSeparation();
        if (minSeparation < 0.0f) {
            throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal to 0", new Object[]{Float.valueOf(minSeparation)}));
        }
        float f = this.stepSize;
        if (f > 0.0f && minSeparation > 0.0f) {
            if (this.separationUnit != 1) {
                throw new IllegalStateException(String.format("minSeparation(%s) cannot be set as a dimension when using stepSize(%s)", new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize)}));
            }
            if (minSeparation < f || !isMultipleOfStepSize(minSeparation)) {
                throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)", new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
            }
        }
    }

    private void warnAboutFloatingPointError() {
        float f = this.stepSize;
        if (f == 0.0f) {
            return;
        }
        if (((int) f) != f) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"stepSize", Float.valueOf(f)}));
        }
        float f2 = this.valueFrom;
        if (((int) f2) != f2) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"valueFrom", Float.valueOf(f2)}));
        }
        float f3 = this.valueTo;
        if (((int) f3) != f3) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[]{"valueTo", Float.valueOf(f3)}));
        }
    }

    private void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            validateValueFrom();
            validateValueTo();
            validateStepSize();
            validateValues();
            validateMinSeparation();
            warnAboutFloatingPointError();
            this.dirtyConfig = false;
        }
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public void setValueFrom(float valueFrom) {
        this.valueFrom = valueFrom;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.valueTo;
    }

    public void setValueTo(float valueTo) {
        this.valueTo = valueTo;
        this.dirtyConfig = true;
        postInvalidate();
    }

    List getValues() {
        return new ArrayList(this.values);
    }

    void setValues(Float... values) {
        ArrayList<Float> list = new ArrayList<>();
        Collections.addAll(list, values);
        setValuesInternal(list);
    }

    void setValues(List list) {
        setValuesInternal(new ArrayList(list));
    }

    private void setValuesInternal(ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("At least one value must be set");
        }
        Collections.sort(arrayList);
        if (this.values.size() == arrayList.size() && this.values.equals(arrayList)) {
            return;
        }
        this.values = arrayList;
        this.dirtyConfig = true;
        this.focusedThumbIdx = 0;
        updateHaloHotspot();
        createLabelPool();
        dispatchOnChangedProgrammatically();
        postInvalidate();
    }

    private void createLabelPool() {
        if (this.labels.size() > this.values.size()) {
            List<TooltipDrawable> tooltipDrawables = this.labels.subList(this.values.size(), this.labels.size());
            for (TooltipDrawable label : tooltipDrawables) {
                if (ViewCompat.isAttachedToWindow(this)) {
                    detachLabelFromContentView(label);
                }
            }
            tooltipDrawables.clear();
        }
        while (this.labels.size() < this.values.size()) {
            TooltipDrawable tooltipDrawable = this.labelMaker.createTooltipDrawable();
            this.labels.add(tooltipDrawable);
            if (ViewCompat.isAttachedToWindow(this)) {
                attachLabelToContentView(tooltipDrawable);
            }
        }
        int strokeWidth = this.labels.size() == 1 ? 0 : 1;
        for (TooltipDrawable label2 : this.labels) {
            label2.setStrokeWidth(strokeWidth);
        }
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(float stepSize) {
        if (stepSize < 0.0f) {
            throw new IllegalArgumentException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", new Object[]{Float.valueOf(stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
        if (this.stepSize != stepSize) {
            this.stepSize = stepSize;
            this.dirtyConfig = true;
            postInvalidate();
        }
    }

    void setCustomThumbDrawable(int drawableResId) {
        setCustomThumbDrawable(getResources().getDrawable(drawableResId));
    }

    void setCustomThumbDrawable(Drawable drawable) {
        this.customThumbDrawable = initializeCustomThumbDrawable(drawable);
        this.customThumbDrawablesForValues.clear();
        postInvalidate();
    }

    void setCustomThumbDrawablesForValues(int... customThumbDrawableResIds) {
        Drawable[] customThumbDrawables = new Drawable[customThumbDrawableResIds.length];
        for (int i = 0; i < customThumbDrawableResIds.length; i++) {
            customThumbDrawables[i] = getResources().getDrawable(customThumbDrawableResIds[i]);
        }
        setCustomThumbDrawablesForValues(customThumbDrawables);
    }

    void setCustomThumbDrawablesForValues(Drawable... customThumbDrawables) {
        this.customThumbDrawable = null;
        this.customThumbDrawablesForValues = new ArrayList();
        for (Drawable originalDrawable : customThumbDrawables) {
            this.customThumbDrawablesForValues.add(initializeCustomThumbDrawable(originalDrawable));
        }
        postInvalidate();
    }

    private Drawable initializeCustomThumbDrawable(Drawable originalDrawable) {
        Drawable drawable = originalDrawable.mutate().getConstantState().newDrawable();
        adjustCustomThumbDrawableBounds(drawable);
        return drawable;
    }

    private void adjustCustomThumbDrawableBounds(Drawable drawable) {
        int thumbDiameter = this.thumbRadius * 2;
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        if (originalWidth == -1 && originalHeight == -1) {
            drawable.setBounds(0, 0, thumbDiameter, thumbDiameter);
        } else {
            float scaleRatio = thumbDiameter / Math.max(originalWidth, originalHeight);
            drawable.setBounds(0, 0, (int) (originalWidth * scaleRatio), (int) (originalHeight * scaleRatio));
        }
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public void setFocusedThumbIndex(int index) {
        if (index < 0 || index >= this.values.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.focusedThumbIdx = index;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(index);
        postInvalidate();
    }

    protected void setActiveThumbIndex(int index) {
        this.activeThumbIdx = index;
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public void addOnChangeListener(BaseOnChangeListener baseOnChangeListener) {
        this.changeListeners.add(baseOnChangeListener);
    }

    public void removeOnChangeListener(BaseOnChangeListener baseOnChangeListener) {
        this.changeListeners.remove(baseOnChangeListener);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void addOnSliderTouchListener(BaseOnSliderTouchListener baseOnSliderTouchListener) {
        this.touchListeners.add(baseOnSliderTouchListener);
    }

    public void removeOnSliderTouchListener(BaseOnSliderTouchListener baseOnSliderTouchListener) {
        this.touchListeners.remove(baseOnSliderTouchListener);
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean hasLabelFormatter() {
        return this.formatter != null;
    }

    public void setLabelFormatter(LabelFormatter formatter) {
        this.formatter = formatter;
    }

    public float getThumbElevation() {
        return this.defaultThumbDrawable.getElevation();
    }

    public void setThumbElevation(float elevation) {
        this.defaultThumbDrawable.setElevation(elevation);
    }

    public void setThumbElevationResource(int elevation) {
        setThumbElevation(getResources().getDimension(elevation));
    }

    public int getThumbRadius() {
        return this.thumbRadius;
    }

    public void setThumbRadius(int radius) {
        if (radius == this.thumbRadius) {
            return;
        }
        this.thumbRadius = radius;
        maybeIncreaseTrackSidePadding();
        this.defaultThumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, this.thumbRadius).build());
        MaterialShapeDrawable materialShapeDrawable = this.defaultThumbDrawable;
        int i = this.thumbRadius;
        materialShapeDrawable.setBounds(0, 0, i * 2, i * 2);
        Drawable drawable = this.customThumbDrawable;
        if (drawable != null) {
            adjustCustomThumbDrawableBounds(drawable);
        }
        for (Drawable customDrawable : this.customThumbDrawablesForValues) {
            adjustCustomThumbDrawableBounds(customDrawable);
        }
        postInvalidate();
    }

    public void setThumbRadiusResource(int radius) {
        setThumbRadius(getResources().getDimensionPixelSize(radius));
    }

    public void setThumbStrokeColor(ColorStateList thumbStrokeColor) {
        this.defaultThumbDrawable.setStrokeColor(thumbStrokeColor);
        postInvalidate();
    }

    public void setThumbStrokeColorResource(int thumbStrokeColorResourceId) {
        if (thumbStrokeColorResourceId != 0) {
            setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), thumbStrokeColorResourceId));
        }
    }

    public ColorStateList getThumbStrokeColor() {
        return this.defaultThumbDrawable.getStrokeColor();
    }

    public void setThumbStrokeWidth(float thumbStrokeWidth) {
        this.defaultThumbDrawable.setStrokeWidth(thumbStrokeWidth);
        postInvalidate();
    }

    public void setThumbStrokeWidthResource(int thumbStrokeWidthResourceId) {
        if (thumbStrokeWidthResourceId != 0) {
            setThumbStrokeWidth(getResources().getDimension(thumbStrokeWidthResourceId));
        }
    }

    public float getThumbStrokeWidth() {
        return this.defaultThumbDrawable.getStrokeWidth();
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public void setHaloRadius(int radius) {
        if (radius == this.haloRadius) {
            return;
        }
        this.haloRadius = radius;
        RippleDrawable background = getBackground();
        if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
            DrawableUtils.setRippleDrawableRadius(background, this.haloRadius);
        } else {
            postInvalidate();
        }
    }

    public void setHaloRadiusResource(int radius) {
        setHaloRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public void setLabelBehavior(int labelBehavior) {
        if (this.labelBehavior != labelBehavior) {
            this.labelBehavior = labelBehavior;
            requestLayout();
        }
    }

    private boolean shouldAlwaysShowLabel() {
        return this.labelBehavior == 3;
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public int getTrackHeight() {
        return this.trackHeight;
    }

    public void setTrackHeight(int trackHeight) {
        if (this.trackHeight != trackHeight) {
            this.trackHeight = trackHeight;
            invalidateTrack();
            postInvalidate();
        }
    }

    public ColorStateList getHaloTintList() {
        return this.haloColor;
    }

    public void setHaloTintList(ColorStateList haloColor) {
        if (haloColor.equals(this.haloColor)) {
            return;
        }
        this.haloColor = haloColor;
        RippleDrawable background = getBackground();
        if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
            background.setColor(haloColor);
            return;
        }
        this.haloPaint.setColor(getColorForState(haloColor));
        this.haloPaint.setAlpha(63);
        invalidate();
    }

    public ColorStateList getThumbTintList() {
        return this.defaultThumbDrawable.getFillColor();
    }

    public void setThumbTintList(ColorStateList thumbColor) {
        if (thumbColor.equals(this.defaultThumbDrawable.getFillColor())) {
            return;
        }
        this.defaultThumbDrawable.setFillColor(thumbColor);
        invalidate();
    }

    public ColorStateList getTickTintList() {
        if (!this.tickColorInactive.equals(this.tickColorActive)) {
            throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
        }
        return this.tickColorActive;
    }

    public void setTickTintList(ColorStateList tickColor) {
        setTickInactiveTintList(tickColor);
        setTickActiveTintList(tickColor);
    }

    public ColorStateList getTickActiveTintList() {
        return this.tickColorActive;
    }

    public void setTickActiveTintList(ColorStateList tickColor) {
        if (tickColor.equals(this.tickColorActive)) {
            return;
        }
        this.tickColorActive = tickColor;
        this.activeTicksPaint.setColor(getColorForState(tickColor));
        invalidate();
    }

    public ColorStateList getTickInactiveTintList() {
        return this.tickColorInactive;
    }

    public void setTickInactiveTintList(ColorStateList tickColor) {
        if (tickColor.equals(this.tickColorInactive)) {
            return;
        }
        this.tickColorInactive = tickColor;
        this.inactiveTicksPaint.setColor(getColorForState(tickColor));
        invalidate();
    }

    public boolean isTickVisible() {
        return this.tickVisible;
    }

    public void setTickVisible(boolean tickVisible) {
        if (this.tickVisible != tickVisible) {
            this.tickVisible = tickVisible;
            postInvalidate();
        }
    }

    public ColorStateList getTrackTintList() {
        if (!this.trackColorInactive.equals(this.trackColorActive)) {
            throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
        }
        return this.trackColorActive;
    }

    public void setTrackTintList(ColorStateList trackColor) {
        setTrackInactiveTintList(trackColor);
        setTrackActiveTintList(trackColor);
    }

    public ColorStateList getTrackActiveTintList() {
        return this.trackColorActive;
    }

    public void setTrackActiveTintList(ColorStateList trackColor) {
        if (trackColor.equals(this.trackColorActive)) {
            return;
        }
        this.trackColorActive = trackColor;
        this.activeTrackPaint.setColor(getColorForState(trackColor));
        invalidate();
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.trackColorInactive;
    }

    public void setTrackInactiveTintList(ColorStateList trackColor) {
        if (trackColor.equals(this.trackColorInactive)) {
            return;
        }
        this.trackColorInactive = trackColor;
        this.inactiveTrackPaint.setColor(getColorForState(trackColor));
        invalidate();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setLayerType(enabled ? 0 : 2, null);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (TooltipDrawable label : this.labels) {
            attachLabelToContentView(label);
        }
    }

    private void attachLabelToContentView(TooltipDrawable label) {
        label.setRelativeToView(ViewUtils.getContentView(this));
    }

    protected void onDetachedFromWindow() {
        AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
        if (accessibilityEventSender != null) {
            removeCallbacks(accessibilityEventSender);
        }
        this.labelsAreAnimatedIn = false;
        for (TooltipDrawable label : this.labels) {
            detachLabelFromContentView(label);
        }
        super.onDetachedFromWindow();
    }

    private void detachLabelFromContentView(TooltipDrawable label) {
        ViewOverlayImpl contentViewOverlay = ViewUtils.getContentViewOverlay(this);
        if (contentViewOverlay != null) {
            contentViewOverlay.remove(label);
            label.detachView(ViewUtils.getContentView(this));
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(this.widgetHeight + ((this.labelBehavior == 1 || shouldAlwaysShowLabel()) ? ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight() : 0), 1073741824));
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateTrackWidth(w);
        updateHaloHotspot();
    }

    private void maybeCalculateTicksCoordinates() {
        if (this.stepSize <= 0.0f) {
            return;
        }
        validateConfigurationIfDirty();
        int tickCount = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / (this.trackHeight * 2)) + 1);
        float[] fArr = this.ticksCoordinates;
        if (fArr == null || fArr.length != tickCount * 2) {
            this.ticksCoordinates = new float[tickCount * 2];
        }
        float interval = this.trackWidth / (tickCount - 1);
        for (int i = 0; i < tickCount * 2; i += 2) {
            float[] fArr2 = this.ticksCoordinates;
            fArr2[i] = this.trackSidePadding + ((i / 2) * interval);
            fArr2[i + 1] = calculateTop();
        }
    }

    private void updateTrackWidth(int width) {
        this.trackWidth = Math.max(width - (this.trackSidePadding * 2), 0);
        maybeCalculateTicksCoordinates();
    }

    private void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int x = (int) ((normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * this.trackWidth) + this.trackSidePadding);
                int y = calculateTop();
                int i = this.haloRadius;
                DrawableCompat.setHotspotBounds(background, x - i, y - i, x + i, i + y);
            }
        }
    }

    private int calculateTop() {
        return this.trackTop + ((this.labelBehavior == 1 || shouldAlwaysShowLabel()) ? ((TooltipDrawable) this.labels.get(0)).getIntrinsicHeight() : 0);
    }

    protected void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            maybeCalculateTicksCoordinates();
        }
        super.onDraw(canvas);
        int top = calculateTop();
        drawInactiveTrack(canvas, this.trackWidth, top);
        if (((Float) Collections.max(getValues())).floatValue() > this.valueFrom) {
            drawActiveTrack(canvas, this.trackWidth, top);
        }
        maybeDrawTicks(canvas);
        if ((this.thumbIsPressed || isFocused() || shouldAlwaysShowLabel()) && isEnabled()) {
            maybeDrawHalo(canvas, this.trackWidth, top);
            if (this.activeThumbIdx != -1 || shouldAlwaysShowLabel()) {
                ensureLabelsAdded();
            } else {
                ensureLabelsRemoved();
            }
        } else {
            ensureLabelsRemoved();
        }
        drawThumbs(canvas, this.trackWidth, top);
    }

    private float[] getActiveRange() {
        float max = ((Float) Collections.max(getValues())).floatValue();
        float min = ((Float) Collections.min(getValues())).floatValue();
        float left = normalizeValue(this.values.size() == 1 ? this.valueFrom : min);
        float right = normalizeValue(max);
        return isRtl() ? new float[]{right, left} : new float[]{left, right};
    }

    private void drawInactiveTrack(Canvas canvas, int width, int top) {
        float[] activeRange = getActiveRange();
        float right = this.trackSidePadding + (activeRange[1] * width);
        if (right < r1 + width) {
            canvas.drawLine(right, top, r1 + width, top, this.inactiveTrackPaint);
        }
        int i = this.trackSidePadding;
        float left = i + (activeRange[0] * width);
        if (left > i) {
            canvas.drawLine(i, top, left, top, this.inactiveTrackPaint);
        }
    }

    private float normalizeValue(float value) {
        float f = this.valueFrom;
        float normalized = (value - f) / (this.valueTo - f);
        if (isRtl()) {
            return 1.0f - normalized;
        }
        return normalized;
    }

    private void drawActiveTrack(Canvas canvas, int width, int top) {
        float[] activeRange = getActiveRange();
        int i = this.trackSidePadding;
        float right = i + (activeRange[1] * width);
        float left = i + (activeRange[0] * width);
        canvas.drawLine(left, top, right, top, this.activeTrackPaint);
    }

    private void maybeDrawTicks(Canvas canvas) {
        if (!this.tickVisible || this.stepSize <= 0.0f) {
            return;
        }
        float[] activeRange = getActiveRange();
        int leftPivotIndex = pivotIndex(this.ticksCoordinates, activeRange[0]);
        int rightPivotIndex = pivotIndex(this.ticksCoordinates, activeRange[1]);
        canvas.drawPoints(this.ticksCoordinates, 0, leftPivotIndex * 2, this.inactiveTicksPaint);
        canvas.drawPoints(this.ticksCoordinates, leftPivotIndex * 2, (rightPivotIndex * 2) - (leftPivotIndex * 2), this.activeTicksPaint);
        float[] fArr = this.ticksCoordinates;
        canvas.drawPoints(fArr, rightPivotIndex * 2, fArr.length - (rightPivotIndex * 2), this.inactiveTicksPaint);
    }

    private void drawThumbs(Canvas canvas, int width, int top) {
        for (int i = 0; i < this.values.size(); i++) {
            float value = ((Float) this.values.get(i)).floatValue();
            Drawable drawable = this.customThumbDrawable;
            if (drawable != null) {
                drawThumbDrawable(canvas, width, top, value, drawable);
            } else if (i < this.customThumbDrawablesForValues.size()) {
                drawThumbDrawable(canvas, width, top, value, (Drawable) this.customThumbDrawablesForValues.get(i));
            } else {
                if (!isEnabled()) {
                    canvas.drawCircle(this.trackSidePadding + (normalizeValue(value) * width), top, this.thumbRadius, this.thumbPaint);
                }
                drawThumbDrawable(canvas, width, top, value, this.defaultThumbDrawable);
            }
        }
    }

    private void drawThumbDrawable(Canvas canvas, int width, int top, float value, Drawable thumbDrawable) {
        canvas.save();
        canvas.translate((this.trackSidePadding + ((int) (normalizeValue(value) * width))) - (thumbDrawable.getBounds().width() / 2.0f), top - (thumbDrawable.getBounds().height() / 2.0f));
        thumbDrawable.draw(canvas);
        canvas.restore();
    }

    private void maybeDrawHalo(Canvas canvas, int width, int top) {
        if (shouldDrawCompatHalo()) {
            int centerX = (int) (this.trackSidePadding + (normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * width));
            if (Build.VERSION.SDK_INT < 28) {
                int i = this.haloRadius;
                canvas.clipRect(centerX - i, top - i, centerX + i, i + top, Region.Op.UNION);
            }
            canvas.drawCircle(centerX, top, this.haloRadius, this.haloPaint);
        }
    }

    private boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || Build.VERSION.SDK_INT < 21 || !(getBackground() instanceof RippleDrawable);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        float x = event.getX();
        float f = (x - this.trackSidePadding) / this.trackWidth;
        this.touchPosition = f;
        float max = Math.max(0.0f, f);
        this.touchPosition = max;
        this.touchPosition = Math.min(1.0f, max);
        switch (event.getActionMasked()) {
            case 0:
                this.touchDownX = x;
                if (!isInVerticalScrollingContainer()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        requestFocus();
                        this.thumbIsPressed = true;
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                        onStartTrackingTouch();
                        break;
                    }
                }
                break;
            case 1:
                this.thumbIsPressed = false;
                MotionEvent motionEvent = this.lastEvent;
                if (motionEvent != null && motionEvent.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - event.getX()) <= this.scaledTouchSlop && Math.abs(this.lastEvent.getY() - event.getY()) <= this.scaledTouchSlop && pickActiveThumb()) {
                    onStartTrackingTouch();
                }
                if (this.activeThumbIdx != -1) {
                    snapTouchPosition();
                    this.activeThumbIdx = -1;
                    onStopTrackingTouch();
                }
                invalidate();
                break;
            case 2:
                if (!this.thumbIsPressed) {
                    if (isInVerticalScrollingContainer() && Math.abs(x - this.touchDownX) < this.scaledTouchSlop) {
                        return false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    onStartTrackingTouch();
                }
                if (pickActiveThumb()) {
                    this.thumbIsPressed = true;
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                    break;
                }
                break;
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(event);
        return true;
    }

    private static int pivotIndex(float[] coordinates, float position) {
        return Math.round(((coordinates.length / 2) - 1) * position);
    }

    private double snapPosition(float position) {
        float f = this.stepSize;
        if (f > 0.0f) {
            int stepCount = (int) ((this.valueTo - this.valueFrom) / f);
            double round = Math.round(stepCount * position);
            double d = stepCount;
            Double.isNaN(round);
            Double.isNaN(d);
            return round / d;
        }
        return position;
    }

    protected boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float touchValue = getValueOfTouchPositionAbsolute();
        float touchX = valueToX(touchValue);
        this.activeThumbIdx = 0;
        float activeThumbDiff = Math.abs(((Float) this.values.get(0)).floatValue() - touchValue);
        for (int i = 1; i < this.values.size(); i++) {
            float valueDiff = Math.abs(((Float) this.values.get(i)).floatValue() - touchValue);
            float valueX = valueToX(((Float) this.values.get(i)).floatValue());
            if (Float.compare(valueDiff, activeThumbDiff) > 1) {
                break;
            }
            boolean movingForward = !isRtl() ? valueX - touchX >= 0.0f : valueX - touchX <= 0.0f;
            if (Float.compare(valueDiff, activeThumbDiff) < 0) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            } else if (Float.compare(valueDiff, activeThumbDiff) != 0) {
                continue;
            } else {
                if (Math.abs(valueX - touchX) < this.scaledTouchSlop) {
                    this.activeThumbIdx = -1;
                    return false;
                }
                if (movingForward) {
                    activeThumbDiff = valueDiff;
                    this.activeThumbIdx = i;
                }
            }
        }
        int i2 = this.activeThumbIdx;
        return i2 != -1;
    }

    private float getValueOfTouchPositionAbsolute() {
        float position = this.touchPosition;
        if (isRtl()) {
            position = 1.0f - position;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        return ((f - f2) * position) + f2;
    }

    private boolean snapTouchPosition() {
        return snapActiveThumbToValue(getValueOfTouchPosition());
    }

    private boolean snapActiveThumbToValue(float value) {
        return snapThumbToValue(this.activeThumbIdx, value);
    }

    private boolean snapThumbToValue(int idx, float value) {
        this.focusedThumbIdx = idx;
        if (Math.abs(value - ((Float) this.values.get(idx)).floatValue()) < 1.0E-4d) {
            return false;
        }
        float newValue = getClampedValue(idx, value);
        this.values.set(idx, Float.valueOf(newValue));
        dispatchOnChangedFromUser(idx);
        return true;
    }

    private float getClampedValue(int idx, float value) {
        float minSeparation = getMinSeparation();
        float minSeparation2 = this.separationUnit == 0 ? dimenToValue(minSeparation) : minSeparation;
        if (isRtl()) {
            minSeparation2 = -minSeparation2;
        }
        float upperBound = idx + 1 >= this.values.size() ? this.valueTo : ((Float) this.values.get(idx + 1)).floatValue() - minSeparation2;
        float lowerBound = idx + (-1) < 0 ? this.valueFrom : ((Float) this.values.get(idx - 1)).floatValue() + minSeparation2;
        return MathUtils.clamp(value, lowerBound, upperBound);
    }

    private float dimenToValue(float dimen) {
        if (dimen == 0.0f) {
            return 0.0f;
        }
        float f = (dimen - this.trackSidePadding) / this.trackWidth;
        float f2 = this.valueFrom;
        return (f * (f2 - this.valueTo)) + f2;
    }

    protected void setSeparationUnit(int separationUnit) {
        this.separationUnit = separationUnit;
        this.dirtyConfig = true;
        postInvalidate();
    }

    protected float getMinSeparation() {
        return 0.0f;
    }

    private float getValueOfTouchPosition() {
        double position = snapPosition(this.touchPosition);
        if (isRtl()) {
            position = 1.0d - position;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        double d = f - f2;
        Double.isNaN(d);
        double d2 = f2;
        Double.isNaN(d2);
        return (float) ((d * position) + d2);
    }

    private float valueToX(float value) {
        return (normalizeValue(value) * this.trackWidth) + this.trackSidePadding;
    }

    private static float getAnimatorCurrentValueOrDefault(ValueAnimator animator, float defaultValue) {
        if (animator != null && animator.isRunning()) {
            float value = ((Float) animator.getAnimatedValue()).floatValue();
            animator.cancel();
            return value;
        }
        return defaultValue;
    }

    private ValueAnimator createLabelAnimator(boolean enter) {
        TimeInterpolator timeInterpolator;
        float startFraction = enter ? 0.0f : 1.0f;
        float startFraction2 = getAnimatorCurrentValueOrDefault(enter ? this.labelsOutAnimator : this.labelsInAnimator, startFraction);
        float endFraction = enter ? 1.0f : 0.0f;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startFraction2, endFraction});
        animator.setDuration(enter ? 83L : 117L);
        if (enter) {
            timeInterpolator = AnimationUtils.DECELERATE_INTERPOLATOR;
        } else {
            timeInterpolator = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
        }
        animator.setInterpolator(timeInterpolator);
        animator.addUpdateListener(new 2());
        return animator;
    }

    class 2 implements ValueAnimator.AnimatorUpdateListener {
        2() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float fraction = ((Float) animation.getAnimatedValue()).floatValue();
            for (TooltipDrawable label : BaseSlider.access$100(BaseSlider.this)) {
                label.setRevealFraction(fraction);
            }
            ViewCompat.postInvalidateOnAnimation(BaseSlider.this);
        }
    }

    private void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            ValueAnimator createLabelAnimator = createLabelAnimator(false);
            this.labelsOutAnimator = createLabelAnimator;
            this.labelsInAnimator = null;
            createLabelAnimator.addListener(new 3());
            this.labelsOutAnimator.start();
        }
    }

    class 3 extends AnimatorListenerAdapter {
        3() {
        }

        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            for (TooltipDrawable label : BaseSlider.access$100(BaseSlider.this)) {
                ViewUtils.getContentViewOverlay(BaseSlider.this).remove(label);
            }
        }
    }

    private void ensureLabelsAdded() {
        if (this.labelBehavior == 2) {
            return;
        }
        if (!this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = true;
            ValueAnimator createLabelAnimator = createLabelAnimator(true);
            this.labelsInAnimator = createLabelAnimator;
            this.labelsOutAnimator = null;
            createLabelAnimator.start();
        }
        Iterator<TooltipDrawable> labelItr = this.labels.iterator();
        for (int i = 0; i < this.values.size() && labelItr.hasNext(); i++) {
            if (i != this.focusedThumbIdx) {
                setValueForLabel((TooltipDrawable) labelItr.next(), ((Float) this.values.get(i)).floatValue());
            }
        }
        if (!labelItr.hasNext()) {
            throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", new Object[]{Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size())}));
        }
        setValueForLabel((TooltipDrawable) labelItr.next(), ((Float) this.values.get(this.focusedThumbIdx)).floatValue());
    }

    private String formatValue(float value) {
        if (hasLabelFormatter()) {
            return this.formatter.getFormattedValue(value);
        }
        return String.format(((float) ((int) value)) == value ? "%.0f" : "%.2f", new Object[]{Float.valueOf(value)});
    }

    private void setValueForLabel(TooltipDrawable label, float value) {
        label.setText(formatValue(value));
        int left = (this.trackSidePadding + ((int) (normalizeValue(value) * this.trackWidth))) - (label.getIntrinsicWidth() / 2);
        int top = calculateTop() - (this.labelPadding + this.thumbRadius);
        label.setBounds(left, top - label.getIntrinsicHeight(), label.getIntrinsicWidth() + left, top);
        Rect rect = new Rect(label.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        label.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).add(label);
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth(this.trackHeight);
        this.activeTrackPaint.setStrokeWidth(this.trackHeight);
        this.inactiveTicksPaint.setStrokeWidth(this.trackHeight / 2.0f);
        this.activeTicksPaint.setStrokeWidth(this.trackHeight / 2.0f);
    }

    private boolean isInVerticalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            boolean canScrollVertically = parent.canScrollVertically(1) || parent.canScrollVertically(-1);
            if (canScrollVertically && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private void dispatchOnChangedProgrammatically() {
        for (BaseOnChangeListener baseOnChangeListener : this.changeListeners) {
            Iterator it = this.values.iterator();
            while (it.hasNext()) {
                Float value = (Float) it.next();
                baseOnChangeListener.onValueChange(this, value.floatValue(), false);
            }
        }
    }

    private void dispatchOnChangedFromUser(int idx) {
        Iterator it = this.changeListeners.iterator();
        while (it.hasNext()) {
            ((BaseOnChangeListener) it.next()).onValueChange(this, ((Float) this.values.get(idx)).floatValue(), true);
        }
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            scheduleAccessibilityEventSender(idx);
        }
    }

    private void onStartTrackingTouch() {
        Iterator it = this.touchListeners.iterator();
        while (it.hasNext()) {
            ((BaseOnSliderTouchListener) it.next()).onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        Iterator it = this.touchListeners.iterator();
        while (it.hasNext()) {
            ((BaseOnSliderTouchListener) it.next()).onStopTrackingTouch(this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        for (TooltipDrawable label : this.labels) {
            if (label.isStateful()) {
                label.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    void forceDrawCompatHalo(boolean force) {
        this.forceDrawCompatHalo = force;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isEnabled()) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        if (this.activeThumbIdx == -1) {
            Boolean handled = onKeyDownNoActiveThumb(keyCode, event);
            return handled != null ? handled.booleanValue() : super.onKeyDown(keyCode, event);
        }
        this.isLongPress |= event.isLongPress();
        Float increment = calculateIncrementForKey(keyCode);
        if (increment != null) {
            if (snapActiveThumbToValue(((Float) this.values.get(this.activeThumbIdx)).floatValue() + increment.floatValue())) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        switch (keyCode) {
            case 23:
            case 66:
                this.activeThumbIdx = -1;
                postInvalidate();
                break;
            case 61:
                if (!event.hasNoModifiers()) {
                    if (event.isShiftPressed()) {
                        break;
                    }
                } else {
                    break;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Boolean onKeyDownNoActiveThumb(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                moveFocusInAbsoluteDirection(-1);
                break;
            case 22:
                moveFocusInAbsoluteDirection(1);
                break;
            case 23:
            case 66:
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                break;
            case 61:
                if (!event.hasNoModifiers()) {
                    if (!event.isShiftPressed()) {
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            case 69:
                moveFocus(-1);
                break;
            case 70:
            case 81:
                moveFocus(1);
                break;
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        this.isLongPress = false;
        return super.onKeyUp(keyCode, event);
    }

    final boolean isRtl() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    private boolean moveFocus(int direction) {
        int oldFocusedThumbIdx = this.focusedThumbIdx;
        long newFocusedThumbIdx = oldFocusedThumbIdx + direction;
        int clamp = (int) MathUtils.clamp(newFocusedThumbIdx, 0L, this.values.size() - 1);
        this.focusedThumbIdx = clamp;
        if (clamp == oldFocusedThumbIdx) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = clamp;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    private boolean moveFocusInAbsoluteDirection(int direction) {
        if (isRtl()) {
            direction = direction == Integer.MIN_VALUE ? Integer.MAX_VALUE : -direction;
        }
        return moveFocus(direction);
    }

    private Float calculateIncrementForKey(int keyCode) {
        float increment = this.isLongPress ? calculateStepIncrement(20) : calculateStepIncrement();
        switch (keyCode) {
            case 21:
                return Float.valueOf(isRtl() ? increment : -increment);
            case 22:
                return Float.valueOf(isRtl() ? -increment : increment);
            case 69:
                return Float.valueOf(-increment);
            case 70:
            case 81:
                return Float.valueOf(increment);
            default:
                return null;
        }
    }

    private float calculateStepIncrement() {
        float f = this.stepSize;
        if (f == 0.0f) {
            return 1.0f;
        }
        return f;
    }

    private float calculateStepIncrement(int stepFactor) {
        float increment = calculateStepIncrement();
        float numSteps = (this.valueTo - this.valueFrom) / increment;
        if (numSteps <= stepFactor) {
            return increment;
        }
        return Math.round(numSteps / stepFactor) * increment;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!gainFocus) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
        } else {
            focusThumbOnFocusGained(direction);
            this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
        }
    }

    private void focusThumbOnFocusGained(int direction) {
        switch (direction) {
            case 1:
                moveFocus(Integer.MAX_VALUE);
                break;
            case 2:
                moveFocus(Integer.MIN_VALUE);
                break;
            case 17:
                moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
                break;
            case 66:
                moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
                break;
        }
    }

    final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.accessibilityHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void scheduleAccessibilityEventSender(int idx) {
        AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
        if (accessibilityEventSender == null) {
            this.accessibilityEventSender = new AccessibilityEventSender(this, null);
        } else {
            removeCallbacks(accessibilityEventSender);
        }
        this.accessibilityEventSender.setVirtualViewId(idx);
        postDelayed(this.accessibilityEventSender, 200L);
    }

    private class AccessibilityEventSender implements Runnable {
        int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        /* synthetic */ AccessibilityEventSender(BaseSlider x0, 1 x1) {
            this();
        }

        void setVirtualViewId(int virtualViewId) {
            this.virtualViewId = virtualViewId;
        }

        public void run() {
            BaseSlider.access$300(BaseSlider.this).sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SliderState sliderState = new SliderState(superState);
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SliderState sliderState = (SliderState) state;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    static class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new 1();
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList values;

        /* synthetic */ SliderState(Parcel x0, 1 x1) {
            this(x0);
        }

        class 1 implements Parcelable.Creator {
            1() {
            }

            public SliderState createFromParcel(Parcel source) {
                return new SliderState(source, null);
            }

            public SliderState[] newArray(int size) {
                return new SliderState[size];
            }
        }

        SliderState(Parcelable superState) {
            super(superState);
        }

        private SliderState(Parcel source) {
            super(source);
            this.valueFrom = source.readFloat();
            this.valueTo = source.readFloat();
            ArrayList arrayList = new ArrayList();
            this.values = arrayList;
            source.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = source.readFloat();
            this.hasFocus = source.createBooleanArray()[0];
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(this.valueFrom);
            dest.writeFloat(this.valueTo);
            dest.writeList(this.values);
            dest.writeFloat(this.stepSize);
            boolean[] booleans = {this.hasFocus};
            dest.writeBooleanArray(booleans);
        }
    }

    void updateBoundsForVirturalViewId(int virtualViewId, Rect virtualViewBounds) {
        int x = this.trackSidePadding + ((int) (normalizeValue(((Float) getValues().get(virtualViewId)).floatValue()) * this.trackWidth));
        int y = calculateTop();
        int i = this.thumbRadius;
        virtualViewBounds.set(x - i, y - i, x + i, i + y);
    }

    private static class AccessibilityHelper extends ExploreByTouchHelper {
        private final BaseSlider slider;
        final Rect virtualViewBounds;

        AccessibilityHelper(BaseSlider baseSlider) {
            super(baseSlider);
            this.virtualViewBounds = new Rect();
            this.slider = baseSlider;
        }

        protected int getVirtualViewAt(float x, float y) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                this.slider.updateBoundsForVirturalViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) x, (int) y)) {
                    return i;
                }
            }
            return -1;
        }

        protected void getVisibleVirtualViews(List list) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                list.add(Integer.valueOf(i));
            }
        }

        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat info) {
            info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> values = this.slider.getValues();
            float value = ((Float) values.get(virtualViewId)).floatValue();
            float valueFrom = this.slider.getValueFrom();
            float valueTo = this.slider.getValueTo();
            if (this.slider.isEnabled()) {
                if (value > valueFrom) {
                    info.addAction(8192);
                }
                if (value < valueTo) {
                    info.addAction(4096);
                }
            }
            info.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, valueFrom, valueTo, value));
            info.setClassName(SeekBar.class.getName());
            StringBuilder contentDescription = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                contentDescription.append(this.slider.getContentDescription()).append(",");
            }
            if (values.size() > 1) {
                contentDescription.append(startOrEndDescription(virtualViewId));
                contentDescription.append(BaseSlider.access$500(this.slider, value));
            }
            info.setContentDescription(contentDescription.toString());
            this.slider.updateBoundsForVirturalViewId(virtualViewId, this.virtualViewBounds);
            info.setBoundsInParent(this.virtualViewBounds);
        }

        private String startOrEndDescription(int virtualViewId) {
            List<Float> values = this.slider.getValues();
            if (virtualViewId == values.size() - 1) {
                return this.slider.getContext().getString(R.string.material_slider_range_end);
            }
            if (virtualViewId == 0) {
                return this.slider.getContext().getString(R.string.material_slider_range_start);
            }
            return "";
        }

        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (!this.slider.isEnabled()) {
                return false;
            }
            switch (action) {
                case 4096:
                case 8192:
                    float increment = BaseSlider.access$800(this.slider, 20);
                    if (action == 8192) {
                        increment = -increment;
                    }
                    if (this.slider.isRtl()) {
                        increment = -increment;
                    }
                    List<Float> values = this.slider.getValues();
                    float clamped = MathUtils.clamp(((Float) values.get(virtualViewId)).floatValue() + increment, this.slider.getValueFrom(), this.slider.getValueTo());
                    if (BaseSlider.access$600(this.slider, virtualViewId, clamped)) {
                        BaseSlider.access$700(this.slider);
                        this.slider.postInvalidate();
                        invalidateVirtualView(virtualViewId);
                        break;
                    }
                    break;
                case 16908349:
                    if (arguments != null && arguments.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                        float value = arguments.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                        if (BaseSlider.access$600(this.slider, virtualViewId, value)) {
                            BaseSlider.access$700(this.slider);
                            this.slider.postInvalidate();
                            invalidateVirtualView(virtualViewId);
                            break;
                        }
                    }
                    break;
            }
            return false;
        }
    }
}
