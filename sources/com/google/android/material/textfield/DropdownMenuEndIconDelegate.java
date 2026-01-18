package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.TextInputLayout;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class DropdownMenuEndIconDelegate extends EndIconDelegate {
    private static final int ANIMATION_FADE_IN_DURATION = 67;
    private static final int ANIMATION_FADE_OUT_DURATION = 50;
    private static final boolean IS_LOLLIPOP;
    private final TextInputLayout.AccessibilityDelegate accessibilityDelegate;
    private AccessibilityManager accessibilityManager;
    private final TextInputLayout.OnEditTextAttachedListener dropdownMenuOnEditTextAttachedListener;
    private long dropdownPopupActivatedAt;
    private boolean dropdownPopupDirty;
    private final TextInputLayout.OnEndIconChangedListener endIconChangedListener;
    private final TextWatcher exposedDropdownEndIconTextWatcher;
    private ValueAnimator fadeInAnim;
    private ValueAnimator fadeOutAnim;
    private StateListDrawable filledPopupBackground;
    private boolean isEndIconChecked;
    private final View.OnAttachStateChangeListener onAttachStateChangeListener;
    private final View.OnFocusChangeListener onFocusChangeListener;
    private MaterialShapeDrawable outlinedPopupBackground;
    private final AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;

    static /* synthetic */ AutoCompleteTextView access$000(EditText x0) {
        return castAutoCompleteTextViewOrThrow(x0);
    }

    static /* synthetic */ AccessibilityManager access$100(DropdownMenuEndIconDelegate x0) {
        return x0.accessibilityManager;
    }

    static /* synthetic */ TextWatcher access$1000(DropdownMenuEndIconDelegate x0) {
        return x0.exposedDropdownEndIconTextWatcher;
    }

    static /* synthetic */ TextInputLayout.AccessibilityDelegate access$1100(DropdownMenuEndIconDelegate x0) {
        return x0.accessibilityDelegate;
    }

    static /* synthetic */ View.OnFocusChangeListener access$1200(DropdownMenuEndIconDelegate x0) {
        return x0.onFocusChangeListener;
    }

    static /* synthetic */ boolean access$1300() {
        return IS_LOLLIPOP;
    }

    static /* synthetic */ View.OnAttachStateChangeListener access$1400(DropdownMenuEndIconDelegate x0) {
        return x0.onAttachStateChangeListener;
    }

    static /* synthetic */ void access$1500(DropdownMenuEndIconDelegate x0) {
        x0.removeTouchExplorationStateChangeListenerIfNeeded();
    }

    static /* synthetic */ void access$1600(DropdownMenuEndIconDelegate x0) {
        x0.addTouchExplorationStateChangeListenerIfNeeded();
    }

    static /* synthetic */ boolean access$1700(DropdownMenuEndIconDelegate x0) {
        return x0.isDropdownPopupActive();
    }

    static /* synthetic */ boolean access$1800(DropdownMenuEndIconDelegate x0) {
        return x0.isEndIconChecked;
    }

    static /* synthetic */ ValueAnimator access$1900(DropdownMenuEndIconDelegate x0) {
        return x0.fadeInAnim;
    }

    static /* synthetic */ boolean access$200(EditText x0) {
        return isEditable(x0);
    }

    static /* synthetic */ void access$300(DropdownMenuEndIconDelegate x0, boolean x1) {
        x0.setEndIconChecked(x1);
    }

    static /* synthetic */ boolean access$402(DropdownMenuEndIconDelegate x0, boolean x1) {
        x0.dropdownPopupDirty = x1;
        return x1;
    }

    static /* synthetic */ void access$500(DropdownMenuEndIconDelegate x0, AutoCompleteTextView x1) {
        x0.showHideDropdown(x1);
    }

    static /* synthetic */ void access$600(DropdownMenuEndIconDelegate x0) {
        x0.updateDropdownPopupDirty();
    }

    static /* synthetic */ void access$700(DropdownMenuEndIconDelegate x0, AutoCompleteTextView x1) {
        x0.setPopupBackground(x1);
    }

    static /* synthetic */ void access$800(DropdownMenuEndIconDelegate x0, AutoCompleteTextView x1) {
        x0.addRippleEffect(x1);
    }

    static /* synthetic */ void access$900(DropdownMenuEndIconDelegate x0, AutoCompleteTextView x1) {
        x0.setUpDropdownShowHideBehavior(x1);
    }

    static {
        IS_LOLLIPOP = Build.VERSION.SDK_INT >= 21;
    }

    class 1 extends TextWatcherAdapter {
        1() {
        }

        public void afterTextChanged(Editable s) {
            AutoCompleteTextView editText = DropdownMenuEndIconDelegate.access$000(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText());
            if (DropdownMenuEndIconDelegate.access$100(DropdownMenuEndIconDelegate.this).isTouchExplorationEnabled() && DropdownMenuEndIconDelegate.access$200(editText) && !DropdownMenuEndIconDelegate.this.endIconView.hasFocus()) {
                editText.dismissDropDown();
            }
            editText.post(new 1(editText));
        }

        class 1 implements Runnable {
            final /* synthetic */ AutoCompleteTextView val$editText;

            1(AutoCompleteTextView autoCompleteTextView) {
                this.val$editText = autoCompleteTextView;
            }

            public void run() {
                boolean isPopupShowing = this.val$editText.isPopupShowing();
                DropdownMenuEndIconDelegate.access$300(DropdownMenuEndIconDelegate.this, isPopupShowing);
                DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, isPopupShowing);
            }
        }
    }

    class 2 implements View.OnFocusChangeListener {
        2() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            DropdownMenuEndIconDelegate.this.textInputLayout.setEndIconActivated(hasFocus);
            if (!hasFocus) {
                DropdownMenuEndIconDelegate.access$300(DropdownMenuEndIconDelegate.this, false);
                DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, false);
            }
        }
    }

    class 3 extends TextInputLayout.AccessibilityDelegate {
        3(TextInputLayout layout) {
            super(layout);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (!DropdownMenuEndIconDelegate.access$200(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText())) {
                info.setClassName(Spinner.class.getName());
            }
            if (info.isShowingHintText()) {
                info.setHintText((CharSequence) null);
            }
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            AutoCompleteTextView editText = DropdownMenuEndIconDelegate.access$000(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText());
            if (event.getEventType() == 1 && DropdownMenuEndIconDelegate.access$100(DropdownMenuEndIconDelegate.this).isEnabled() && !DropdownMenuEndIconDelegate.access$200(DropdownMenuEndIconDelegate.this.textInputLayout.getEditText())) {
                DropdownMenuEndIconDelegate.access$500(DropdownMenuEndIconDelegate.this, editText);
                DropdownMenuEndIconDelegate.access$600(DropdownMenuEndIconDelegate.this);
            }
        }
    }

    class 4 implements TextInputLayout.OnEditTextAttachedListener {
        4() {
        }

        public void onEditTextAttached(TextInputLayout textInputLayout) {
            AutoCompleteTextView autoCompleteTextView = DropdownMenuEndIconDelegate.access$000(textInputLayout.getEditText());
            DropdownMenuEndIconDelegate.access$700(DropdownMenuEndIconDelegate.this, autoCompleteTextView);
            DropdownMenuEndIconDelegate.access$800(DropdownMenuEndIconDelegate.this, autoCompleteTextView);
            DropdownMenuEndIconDelegate.access$900(DropdownMenuEndIconDelegate.this, autoCompleteTextView);
            autoCompleteTextView.setThreshold(0);
            autoCompleteTextView.removeTextChangedListener(DropdownMenuEndIconDelegate.access$1000(DropdownMenuEndIconDelegate.this));
            autoCompleteTextView.addTextChangedListener(DropdownMenuEndIconDelegate.access$1000(DropdownMenuEndIconDelegate.this));
            textInputLayout.setEndIconCheckable(true);
            textInputLayout.setErrorIconDrawable((Drawable) null);
            if (!DropdownMenuEndIconDelegate.access$200(autoCompleteTextView) && DropdownMenuEndIconDelegate.access$100(DropdownMenuEndIconDelegate.this).isTouchExplorationEnabled()) {
                ViewCompat.setImportantForAccessibility(DropdownMenuEndIconDelegate.this.endIconView, 2);
            }
            textInputLayout.setTextInputAccessibilityDelegate(DropdownMenuEndIconDelegate.access$1100(DropdownMenuEndIconDelegate.this));
            textInputLayout.setEndIconVisible(true);
        }
    }

    class 5 implements TextInputLayout.OnEndIconChangedListener {
        5() {
        }

        public void onEndIconChanged(TextInputLayout textInputLayout, int previousIcon) {
            AutoCompleteTextView editText = textInputLayout.getEditText();
            if (editText != null && previousIcon == 3) {
                editText.post(new 1(editText));
                if (editText.getOnFocusChangeListener() == DropdownMenuEndIconDelegate.access$1200(DropdownMenuEndIconDelegate.this)) {
                    editText.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                }
                editText.setOnTouchListener((View.OnTouchListener) null);
                if (DropdownMenuEndIconDelegate.access$1300()) {
                    editText.setOnDismissListener((AutoCompleteTextView.OnDismissListener) null);
                }
            }
            if (previousIcon == 3) {
                textInputLayout.removeOnAttachStateChangeListener(DropdownMenuEndIconDelegate.access$1400(DropdownMenuEndIconDelegate.this));
                DropdownMenuEndIconDelegate.access$1500(DropdownMenuEndIconDelegate.this);
            }
        }

        class 1 implements Runnable {
            final /* synthetic */ AutoCompleteTextView val$editText;

            1(AutoCompleteTextView autoCompleteTextView) {
                this.val$editText = autoCompleteTextView;
            }

            public void run() {
                this.val$editText.removeTextChangedListener(DropdownMenuEndIconDelegate.access$1000(DropdownMenuEndIconDelegate.this));
            }
        }
    }

    class 6 implements View.OnAttachStateChangeListener {
        6() {
        }

        public void onViewAttachedToWindow(View ignored) {
            DropdownMenuEndIconDelegate.access$1600(DropdownMenuEndIconDelegate.this);
        }

        public void onViewDetachedFromWindow(View ignored) {
            DropdownMenuEndIconDelegate.access$1500(DropdownMenuEndIconDelegate.this);
        }
    }

    class 7 implements AccessibilityManagerCompat.TouchExplorationStateChangeListener {
        7() {
        }

        public void onTouchExplorationStateChanged(boolean enabled) {
            AutoCompleteTextView autoCompleteTextView;
            if (DropdownMenuEndIconDelegate.this.textInputLayout != null && (autoCompleteTextView = DropdownMenuEndIconDelegate.this.textInputLayout.getEditText()) != null && !DropdownMenuEndIconDelegate.access$200(autoCompleteTextView)) {
                ViewCompat.setImportantForAccessibility(DropdownMenuEndIconDelegate.this.endIconView, enabled ? 2 : 1);
            }
        }
    }

    DropdownMenuEndIconDelegate(TextInputLayout textInputLayout, int customEndIcon) {
        super(textInputLayout, customEndIcon);
        this.exposedDropdownEndIconTextWatcher = new 1();
        this.onFocusChangeListener = new 2();
        this.accessibilityDelegate = new 3(this.textInputLayout);
        this.dropdownMenuOnEditTextAttachedListener = new 4();
        this.endIconChangedListener = new 5();
        this.onAttachStateChangeListener = new 6();
        this.touchExplorationStateChangeListener = new 7();
        this.dropdownPopupDirty = false;
        this.isEndIconChecked = false;
        this.dropdownPopupActivatedAt = Long.MAX_VALUE;
    }

    void initialize() {
        int drawableResId;
        float popupCornerRadius = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_shape_corner_size_small_component);
        float exposedDropdownPopupElevation = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        int exposedDropdownPopupVerticalPadding = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        MaterialShapeDrawable roundedCornersPopupBackground = getPopUpMaterialShapeDrawable(popupCornerRadius, popupCornerRadius, exposedDropdownPopupElevation, exposedDropdownPopupVerticalPadding);
        MaterialShapeDrawable roundedBottomCornersPopupBackground = getPopUpMaterialShapeDrawable(0.0f, popupCornerRadius, exposedDropdownPopupElevation, exposedDropdownPopupVerticalPadding);
        this.outlinedPopupBackground = roundedCornersPopupBackground;
        StateListDrawable stateListDrawable = new StateListDrawable();
        this.filledPopupBackground = stateListDrawable;
        stateListDrawable.addState(new int[]{16842922}, roundedCornersPopupBackground);
        this.filledPopupBackground.addState(new int[0], roundedBottomCornersPopupBackground);
        if (this.customEndIcon == 0) {
            drawableResId = IS_LOLLIPOP ? R.drawable.mtrl_dropdown_arrow : R.drawable.mtrl_ic_arrow_drop_down;
        } else {
            drawableResId = this.customEndIcon;
        }
        this.textInputLayout.setEndIconDrawable(drawableResId);
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.exposed_dropdown_menu_content_description));
        this.textInputLayout.setEndIconOnClickListener(new 8());
        this.textInputLayout.addOnEditTextAttachedListener(this.dropdownMenuOnEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
        initAnimators();
        this.accessibilityManager = (AccessibilityManager) this.context.getSystemService("accessibility");
        this.textInputLayout.addOnAttachStateChangeListener(this.onAttachStateChangeListener);
        addTouchExplorationStateChangeListenerIfNeeded();
    }

    class 8 implements View.OnClickListener {
        8() {
        }

        public void onClick(View v) {
            AutoCompleteTextView editText = DropdownMenuEndIconDelegate.this.textInputLayout.getEditText();
            DropdownMenuEndIconDelegate.access$500(DropdownMenuEndIconDelegate.this, editText);
        }
    }

    boolean shouldTintIconOnError() {
        return true;
    }

    boolean isBoxBackgroundModeSupported(int boxBackgroundMode) {
        return boxBackgroundMode != 0;
    }

    private void showHideDropdown(AutoCompleteTextView editText) {
        if (editText == null) {
            return;
        }
        if (isDropdownPopupActive()) {
            this.dropdownPopupDirty = false;
        }
        if (!this.dropdownPopupDirty) {
            if (IS_LOLLIPOP) {
                setEndIconChecked(!this.isEndIconChecked);
            } else {
                this.isEndIconChecked = !this.isEndIconChecked;
                this.endIconView.toggle();
            }
            if (this.isEndIconChecked) {
                editText.requestFocus();
                editText.showDropDown();
                return;
            } else {
                editText.dismissDropDown();
                return;
            }
        }
        this.dropdownPopupDirty = false;
    }

    private void setPopupBackground(AutoCompleteTextView editText) {
        if (IS_LOLLIPOP) {
            int boxBackgroundMode = this.textInputLayout.getBoxBackgroundMode();
            if (boxBackgroundMode == 2) {
                editText.setDropDownBackgroundDrawable(this.outlinedPopupBackground);
            } else if (boxBackgroundMode == 1) {
                editText.setDropDownBackgroundDrawable(this.filledPopupBackground);
            }
        }
    }

    void updateOutlinedRippleEffect(AutoCompleteTextView editText) {
        if (isEditable(editText) || this.textInputLayout.getBoxBackgroundMode() != 2 || !(editText.getBackground() instanceof LayerDrawable)) {
            return;
        }
        addRippleEffect(editText);
    }

    private void addRippleEffect(AutoCompleteTextView editText) {
        if (isEditable(editText)) {
            return;
        }
        int boxBackgroundMode = this.textInputLayout.getBoxBackgroundMode();
        MaterialShapeDrawable boxBackground = this.textInputLayout.getBoxBackground();
        int rippleColor = MaterialColors.getColor(editText, R.attr.colorControlHighlight);
        int[][] states = {new int[]{16842919}, new int[0]};
        if (boxBackgroundMode == 2) {
            addRippleEffectOnOutlinedLayout(editText, rippleColor, states, boxBackground);
        } else if (boxBackgroundMode == 1) {
            addRippleEffectOnFilledLayout(editText, rippleColor, states, boxBackground);
        }
    }

    private void addRippleEffectOnOutlinedLayout(AutoCompleteTextView editText, int rippleColor, int[][] states, MaterialShapeDrawable boxBackground) {
        Drawable layerDrawable;
        int surfaceColor = MaterialColors.getColor(editText, R.attr.colorSurface);
        MaterialShapeDrawable rippleBackground = new MaterialShapeDrawable(boxBackground.getShapeAppearanceModel());
        int pressedBackgroundColor = MaterialColors.layer(rippleColor, surfaceColor, 0.1f);
        int[] rippleBackgroundColors = {pressedBackgroundColor, 0};
        rippleBackground.setFillColor(new ColorStateList(states, rippleBackgroundColors));
        if (IS_LOLLIPOP) {
            rippleBackground.setTint(surfaceColor);
            int[] colors = {pressedBackgroundColor, surfaceColor};
            ColorStateList rippleColorStateList = new ColorStateList(states, colors);
            MaterialShapeDrawable mask = new MaterialShapeDrawable(boxBackground.getShapeAppearanceModel());
            mask.setTint(-1);
            Drawable rippleDrawable = new RippleDrawable(rippleColorStateList, rippleBackground, mask);
            Drawable[] layers = {rippleDrawable, boxBackground};
            layerDrawable = new LayerDrawable(layers);
        } else {
            Drawable[] layers2 = {rippleBackground, boxBackground};
            layerDrawable = new LayerDrawable(layers2);
        }
        ViewCompat.setBackground(editText, layerDrawable);
    }

    private void addRippleEffectOnFilledLayout(AutoCompleteTextView editText, int rippleColor, int[][] states, MaterialShapeDrawable boxBackground) {
        int boxBackgroundColor = this.textInputLayout.getBoxBackgroundColor();
        int pressedBackgroundColor = MaterialColors.layer(rippleColor, boxBackgroundColor, 0.1f);
        int[] colors = {pressedBackgroundColor, boxBackgroundColor};
        if (IS_LOLLIPOP) {
            ColorStateList rippleColorStateList = new ColorStateList(states, colors);
            ViewCompat.setBackground(editText, new RippleDrawable(rippleColorStateList, boxBackground, boxBackground));
            return;
        }
        MaterialShapeDrawable rippleBackground = new MaterialShapeDrawable(boxBackground.getShapeAppearanceModel());
        rippleBackground.setFillColor(new ColorStateList(states, colors));
        Drawable[] layers = {boxBackground, rippleBackground};
        LayerDrawable editTextBackground = new LayerDrawable(layers);
        int start = ViewCompat.getPaddingStart(editText);
        int top = editText.getPaddingTop();
        int end = ViewCompat.getPaddingEnd(editText);
        int bottom = editText.getPaddingBottom();
        ViewCompat.setBackground(editText, editTextBackground);
        ViewCompat.setPaddingRelative(editText, start, top, end, bottom);
    }

    class 9 implements View.OnTouchListener {
        final /* synthetic */ AutoCompleteTextView val$editText;

        9(AutoCompleteTextView autoCompleteTextView) {
            this.val$editText = autoCompleteTextView;
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                if (DropdownMenuEndIconDelegate.access$1700(DropdownMenuEndIconDelegate.this)) {
                    DropdownMenuEndIconDelegate.access$402(DropdownMenuEndIconDelegate.this, false);
                }
                DropdownMenuEndIconDelegate.access$500(DropdownMenuEndIconDelegate.this, this.val$editText);
                DropdownMenuEndIconDelegate.access$600(DropdownMenuEndIconDelegate.this);
            }
            return false;
        }
    }

    private void setUpDropdownShowHideBehavior(AutoCompleteTextView editText) {
        editText.setOnTouchListener(new 9(editText));
        editText.setOnFocusChangeListener(this.onFocusChangeListener);
        if (IS_LOLLIPOP) {
            editText.setOnDismissListener(new 10());
        }
    }

    class 10 implements AutoCompleteTextView.OnDismissListener {
        10() {
        }

        public void onDismiss() {
            DropdownMenuEndIconDelegate.access$600(DropdownMenuEndIconDelegate.this);
            DropdownMenuEndIconDelegate.access$300(DropdownMenuEndIconDelegate.this, false);
        }
    }

    private MaterialShapeDrawable getPopUpMaterialShapeDrawable(float topCornerRadius, float bottomCornerRadius, float elevation, int verticalPadding) {
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder().setTopLeftCornerSize(topCornerRadius).setTopRightCornerSize(topCornerRadius).setBottomLeftCornerSize(bottomCornerRadius).setBottomRightCornerSize(bottomCornerRadius).build();
        MaterialShapeDrawable popupDrawable = MaterialShapeDrawable.createWithElevationOverlay(this.context, elevation);
        popupDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        popupDrawable.setPadding(0, verticalPadding, 0, verticalPadding);
        return popupDrawable;
    }

    private boolean isDropdownPopupActive() {
        long activeFor = System.currentTimeMillis() - this.dropdownPopupActivatedAt;
        return activeFor < 0 || activeFor > 300;
    }

    private static AutoCompleteTextView castAutoCompleteTextViewOrThrow(EditText editText) {
        if (!(editText instanceof AutoCompleteTextView)) {
            throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
        }
        return (AutoCompleteTextView) editText;
    }

    private void updateDropdownPopupDirty() {
        this.dropdownPopupDirty = true;
        this.dropdownPopupActivatedAt = System.currentTimeMillis();
    }

    private static boolean isEditable(EditText editText) {
        return editText.getKeyListener() != null;
    }

    private void setEndIconChecked(boolean checked) {
        if (this.isEndIconChecked != checked) {
            this.isEndIconChecked = checked;
            this.fadeInAnim.cancel();
            this.fadeOutAnim.start();
        }
    }

    private void initAnimators() {
        this.fadeInAnim = getAlphaAnimator(67, 0.0f, 1.0f);
        ValueAnimator alphaAnimator = getAlphaAnimator(50, 1.0f, 0.0f);
        this.fadeOutAnim = alphaAnimator;
        alphaAnimator.addListener(new 11());
    }

    class 11 extends AnimatorListenerAdapter {
        11() {
        }

        public void onAnimationEnd(Animator animation) {
            DropdownMenuEndIconDelegate.this.endIconView.setChecked(DropdownMenuEndIconDelegate.access$1800(DropdownMenuEndIconDelegate.this));
            DropdownMenuEndIconDelegate.access$1900(DropdownMenuEndIconDelegate.this).start();
        }
    }

    private ValueAnimator getAlphaAnimator(int duration, float... values) {
        ValueAnimator animator = ValueAnimator.ofFloat(values);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.setDuration(duration);
        animator.addUpdateListener(new 12());
        return animator;
    }

    class 12 implements ValueAnimator.AnimatorUpdateListener {
        12() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float alpha = ((Float) animation.getAnimatedValue()).floatValue();
            DropdownMenuEndIconDelegate.this.endIconView.setAlpha(alpha);
        }
    }

    private void addTouchExplorationStateChangeListenerIfNeeded() {
        if (this.accessibilityManager != null && this.textInputLayout != null && ViewCompat.isAttachedToWindow(this.textInputLayout)) {
            AccessibilityManagerCompat.addTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
        }
    }

    private void removeTouchExplorationStateChangeListenerIfNeeded() {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(accessibilityManager, this.touchExplorationStateChangeListener);
        }
    }
}
