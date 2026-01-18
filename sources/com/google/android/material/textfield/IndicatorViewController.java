package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.resources.MaterialResources;
import java.util.ArrayList;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class IndicatorViewController {
    private static final int CAPTION_OPACITY_FADE_ANIMATION_DURATION = 167;
    private static final int CAPTION_STATE_ERROR = 1;
    private static final int CAPTION_STATE_HELPER_TEXT = 2;
    private static final int CAPTION_STATE_NONE = 0;
    private static final int CAPTION_TRANSLATE_Y_ANIMATION_DURATION = 217;
    static final int COUNTER_INDEX = 2;
    static final int ERROR_INDEX = 0;
    static final int HELPER_INDEX = 1;
    private Animator captionAnimator;
    private FrameLayout captionArea;
    private int captionDisplayed;
    private int captionToShow;
    private final float captionTranslationYPx;
    private final Context context;
    private boolean errorEnabled;
    private CharSequence errorText;
    private int errorTextAppearance;
    private TextView errorView;
    private CharSequence errorViewContentDescription;
    private ColorStateList errorViewTextColor;
    private CharSequence helperText;
    private boolean helperTextEnabled;
    private int helperTextTextAppearance;
    private TextView helperTextView;
    private ColorStateList helperTextViewTextColor;
    private LinearLayout indicatorArea;
    private int indicatorsAdded;
    private final TextInputLayout textInputView;
    private Typeface typeface;

    static /* synthetic */ int access$002(IndicatorViewController x0, int x1) {
        x0.captionDisplayed = x1;
        return x1;
    }

    static /* synthetic */ Animator access$102(IndicatorViewController x0, Animator x1) {
        x0.captionAnimator = x1;
        return x1;
    }

    static /* synthetic */ TextView access$200(IndicatorViewController x0) {
        return x0.errorView;
    }

    static /* synthetic */ TextInputLayout access$300(IndicatorViewController x0) {
        return x0.textInputView;
    }

    public IndicatorViewController(TextInputLayout textInputView) {
        this.context = textInputView.getContext();
        this.textInputView = textInputView;
        this.captionTranslationYPx = r0.getResources().getDimensionPixelSize(R.dimen.design_textinput_caption_translate_y);
    }

    void showHelper(CharSequence helperText) {
        cancelCaptionAnimator();
        this.helperText = helperText;
        this.helperTextView.setText(helperText);
        int i = this.captionDisplayed;
        if (i != 2) {
            this.captionToShow = 2;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, helperText));
    }

    void hideHelperText() {
        cancelCaptionAnimator();
        int i = this.captionDisplayed;
        if (i == 2) {
            this.captionToShow = 0;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, ""));
    }

    void showError(CharSequence errorText) {
        cancelCaptionAnimator();
        this.errorText = errorText;
        this.errorView.setText(errorText);
        int i = this.captionDisplayed;
        if (i != 1) {
            this.captionToShow = 1;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.errorView, errorText));
    }

    void hideError() {
        this.errorText = null;
        cancelCaptionAnimator();
        if (this.captionDisplayed == 1) {
            if (this.helperTextEnabled && !TextUtils.isEmpty(this.helperText)) {
                this.captionToShow = 2;
            } else {
                this.captionToShow = 0;
            }
        }
        updateCaptionViewsVisibility(this.captionDisplayed, this.captionToShow, shouldAnimateCaptionView(this.errorView, ""));
    }

    private boolean shouldAnimateCaptionView(TextView captionView, CharSequence captionText) {
        return ViewCompat.isLaidOut(this.textInputView) && this.textInputView.isEnabled() && !(this.captionToShow == this.captionDisplayed && captionView != null && TextUtils.equals(captionView.getText(), captionText));
    }

    private void updateCaptionViewsVisibility(int captionToHide, int captionToShow, boolean animate) {
        if (captionToHide == captionToShow) {
            return;
        }
        if (animate) {
            AnimatorSet captionAnimator = new AnimatorSet();
            this.captionAnimator = captionAnimator;
            ArrayList arrayList = new ArrayList();
            createCaptionAnimators(arrayList, this.helperTextEnabled, this.helperTextView, 2, captionToHide, captionToShow);
            createCaptionAnimators(arrayList, this.errorEnabled, this.errorView, 1, captionToHide, captionToShow);
            AnimatorSetCompat.playTogether(captionAnimator, arrayList);
            TextView captionViewToHide = getCaptionViewFromDisplayState(captionToHide);
            TextView captionViewToShow = getCaptionViewFromDisplayState(captionToShow);
            captionAnimator.addListener(new 1(captionToShow, captionViewToHide, captionToHide, captionViewToShow));
            captionAnimator.start();
        } else {
            setCaptionViewVisibilities(captionToHide, captionToShow);
        }
        this.textInputView.updateEditTextBackground();
        this.textInputView.updateLabelState(animate);
        this.textInputView.updateTextInputBoxState();
    }

    class 1 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$captionToHide;
        final /* synthetic */ int val$captionToShow;
        final /* synthetic */ TextView val$captionViewToHide;
        final /* synthetic */ TextView val$captionViewToShow;

        1(int i, TextView textView, int i2, TextView textView2) {
            this.val$captionToShow = i;
            this.val$captionViewToHide = textView;
            this.val$captionToHide = i2;
            this.val$captionViewToShow = textView2;
        }

        public void onAnimationEnd(Animator animator) {
            IndicatorViewController.access$002(IndicatorViewController.this, this.val$captionToShow);
            IndicatorViewController.access$102(IndicatorViewController.this, null);
            TextView textView = this.val$captionViewToHide;
            if (textView != null) {
                textView.setVisibility(4);
                if (this.val$captionToHide == 1 && IndicatorViewController.access$200(IndicatorViewController.this) != null) {
                    IndicatorViewController.access$200(IndicatorViewController.this).setText((CharSequence) null);
                }
            }
            TextView textView2 = this.val$captionViewToShow;
            if (textView2 != null) {
                textView2.setTranslationY(0.0f);
                this.val$captionViewToShow.setAlpha(1.0f);
            }
        }

        public void onAnimationStart(Animator animator) {
            TextView textView = this.val$captionViewToShow;
            if (textView != null) {
                textView.setVisibility(0);
            }
        }
    }

    private void setCaptionViewVisibilities(int captionToHide, int captionToShow) {
        TextView captionViewDisplayed;
        TextView captionViewToShow;
        if (captionToHide == captionToShow) {
            return;
        }
        if (captionToShow != 0 && (captionViewToShow = getCaptionViewFromDisplayState(captionToShow)) != null) {
            captionViewToShow.setVisibility(0);
            captionViewToShow.setAlpha(1.0f);
        }
        if (captionToHide != 0 && (captionViewDisplayed = getCaptionViewFromDisplayState(captionToHide)) != null) {
            captionViewDisplayed.setVisibility(4);
            if (captionToHide == 1) {
                captionViewDisplayed.setText((CharSequence) null);
            }
        }
        this.captionDisplayed = captionToShow;
    }

    private void createCaptionAnimators(List list, boolean captionEnabled, TextView captionView, int captionState, int captionToHide, int captionToShow) {
        if (captionView == null || !captionEnabled) {
            return;
        }
        if (captionState == captionToShow || captionState == captionToHide) {
            list.add(createCaptionOpacityAnimator(captionView, captionToShow == captionState));
            if (captionToShow == captionState) {
                list.add(createCaptionTranslationYAnimator(captionView));
            }
        }
    }

    private ObjectAnimator createCaptionOpacityAnimator(TextView captionView, boolean display) {
        float endValue = display ? 1.0f : 0.0f;
        ObjectAnimator opacityAnimator = ObjectAnimator.ofFloat(captionView, View.ALPHA, new float[]{endValue});
        opacityAnimator.setDuration(167L);
        opacityAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        return opacityAnimator;
    }

    private ObjectAnimator createCaptionTranslationYAnimator(TextView captionView) {
        ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(captionView, View.TRANSLATION_Y, new float[]{-this.captionTranslationYPx, 0.0f});
        translationYAnimator.setDuration(217L);
        translationYAnimator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        return translationYAnimator;
    }

    void cancelCaptionAnimator() {
        Animator animator = this.captionAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    boolean isCaptionView(int index) {
        return index == 0 || index == 1;
    }

    private TextView getCaptionViewFromDisplayState(int captionDisplayState) {
        switch (captionDisplayState) {
            case 1:
                return this.errorView;
            case 2:
                return this.helperTextView;
            default:
                return null;
        }
    }

    void adjustIndicatorPadding() {
        if (canAdjustIndicatorPadding()) {
            EditText editText = this.textInputView.getEditText();
            boolean isFontScaleLarge = MaterialResources.isFontScaleAtLeast1_3(this.context);
            ViewCompat.setPaddingRelative(this.indicatorArea, getIndicatorPadding(isFontScaleLarge, R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingStart(editText)), getIndicatorPadding(isFontScaleLarge, R.dimen.material_helper_text_font_1_3_padding_top, this.context.getResources().getDimensionPixelSize(R.dimen.material_helper_text_default_padding_top)), getIndicatorPadding(isFontScaleLarge, R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingEnd(editText)), 0);
        }
    }

    private boolean canAdjustIndicatorPadding() {
        return (this.indicatorArea == null || this.textInputView.getEditText() == null) ? false : true;
    }

    private int getIndicatorPadding(boolean isFontScaleLarge, int largeFontPaddingRes, int defaultPadding) {
        if (isFontScaleLarge) {
            return this.context.getResources().getDimensionPixelSize(largeFontPaddingRes);
        }
        return defaultPadding;
    }

    void addIndicator(TextView indicator, int index) {
        if (this.indicatorArea == null && this.captionArea == null) {
            LinearLayout linearLayout = new LinearLayout(this.context);
            this.indicatorArea = linearLayout;
            linearLayout.setOrientation(0);
            this.textInputView.addView((View) this.indicatorArea, -1, -2);
            this.captionArea = new FrameLayout(this.context);
            LinearLayout.LayoutParams captionAreaLp = new LinearLayout.LayoutParams(0, -2, 1.0f);
            this.indicatorArea.addView(this.captionArea, captionAreaLp);
            if (this.textInputView.getEditText() != null) {
                adjustIndicatorPadding();
            }
        }
        if (isCaptionView(index)) {
            this.captionArea.setVisibility(0);
            this.captionArea.addView(indicator);
        } else {
            LinearLayout.LayoutParams indicatorAreaLp = new LinearLayout.LayoutParams(-2, -2);
            this.indicatorArea.addView(indicator, indicatorAreaLp);
        }
        this.indicatorArea.setVisibility(0);
        this.indicatorsAdded++;
    }

    void removeIndicator(TextView indicator, int index) {
        FrameLayout frameLayout;
        if (this.indicatorArea == null) {
            return;
        }
        if (isCaptionView(index) && (frameLayout = this.captionArea) != null) {
            frameLayout.removeView(indicator);
        } else {
            this.indicatorArea.removeView(indicator);
        }
        int i = this.indicatorsAdded - 1;
        this.indicatorsAdded = i;
        setViewGroupGoneIfEmpty(this.indicatorArea, i);
    }

    private void setViewGroupGoneIfEmpty(ViewGroup viewGroup, int indicatorsAdded) {
        if (indicatorsAdded == 0) {
            viewGroup.setVisibility(8);
        }
    }

    void setErrorEnabled(boolean enabled) {
        if (this.errorEnabled == enabled) {
            return;
        }
        cancelCaptionAnimator();
        if (enabled) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
            this.errorView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_error);
            if (Build.VERSION.SDK_INT >= 17) {
                this.errorView.setTextAlignment(5);
            }
            Typeface typeface = this.typeface;
            if (typeface != null) {
                this.errorView.setTypeface(typeface);
            }
            setErrorTextAppearance(this.errorTextAppearance);
            setErrorViewTextColor(this.errorViewTextColor);
            setErrorContentDescription(this.errorViewContentDescription);
            this.errorView.setVisibility(4);
            ViewCompat.setAccessibilityLiveRegion(this.errorView, 1);
            addIndicator(this.errorView, 0);
        } else {
            hideError();
            removeIndicator(this.errorView, 0);
            this.errorView = null;
            this.textInputView.updateEditTextBackground();
            this.textInputView.updateTextInputBoxState();
        }
        this.errorEnabled = enabled;
    }

    boolean isErrorEnabled() {
        return this.errorEnabled;
    }

    boolean isHelperTextEnabled() {
        return this.helperTextEnabled;
    }

    void setHelperTextEnabled(boolean enabled) {
        if (this.helperTextEnabled == enabled) {
            return;
        }
        cancelCaptionAnimator();
        if (enabled) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
            this.helperTextView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_helper_text);
            if (Build.VERSION.SDK_INT >= 17) {
                this.helperTextView.setTextAlignment(5);
            }
            Typeface typeface = this.typeface;
            if (typeface != null) {
                this.helperTextView.setTypeface(typeface);
            }
            this.helperTextView.setVisibility(4);
            ViewCompat.setAccessibilityLiveRegion(this.helperTextView, 1);
            setHelperTextAppearance(this.helperTextTextAppearance);
            setHelperTextViewTextColor(this.helperTextViewTextColor);
            addIndicator(this.helperTextView, 1);
            if (Build.VERSION.SDK_INT >= 17) {
                this.helperTextView.setAccessibilityDelegate(new 2());
            }
        } else {
            hideHelperText();
            removeIndicator(this.helperTextView, 1);
            this.helperTextView = null;
            this.textInputView.updateEditTextBackground();
            this.textInputView.updateTextInputBoxState();
        }
        this.helperTextEnabled = enabled;
    }

    class 2 extends View.AccessibilityDelegate {
        2() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            EditText editText = IndicatorViewController.access$300(IndicatorViewController.this).getEditText();
            if (editText != null) {
                accessibilityNodeInfo.setLabeledBy(editText);
            }
        }
    }

    View getHelperTextView() {
        return this.helperTextView;
    }

    boolean errorIsDisplayed() {
        return isCaptionStateError(this.captionDisplayed);
    }

    boolean errorShouldBeShown() {
        return isCaptionStateError(this.captionToShow);
    }

    private boolean isCaptionStateError(int captionState) {
        return (captionState != 1 || this.errorView == null || TextUtils.isEmpty(this.errorText)) ? false : true;
    }

    boolean helperTextIsDisplayed() {
        return isCaptionStateHelperText(this.captionDisplayed);
    }

    boolean helperTextShouldBeShown() {
        return isCaptionStateHelperText(this.captionToShow);
    }

    private boolean isCaptionStateHelperText(int captionState) {
        return (captionState != 2 || this.helperTextView == null || TextUtils.isEmpty(this.helperText)) ? false : true;
    }

    CharSequence getErrorText() {
        return this.errorText;
    }

    CharSequence getHelperText() {
        return this.helperText;
    }

    void setTypefaces(Typeface typeface) {
        if (typeface != this.typeface) {
            this.typeface = typeface;
            setTextViewTypeface(this.errorView, typeface);
            setTextViewTypeface(this.helperTextView, typeface);
        }
    }

    private void setTextViewTypeface(TextView captionView, Typeface typeface) {
        if (captionView != null) {
            captionView.setTypeface(typeface);
        }
    }

    int getErrorViewCurrentTextColor() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    ColorStateList getErrorViewTextColors() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    void setErrorViewTextColor(ColorStateList errorViewTextColor) {
        this.errorViewTextColor = errorViewTextColor;
        TextView textView = this.errorView;
        if (textView != null && errorViewTextColor != null) {
            textView.setTextColor(errorViewTextColor);
        }
    }

    void setErrorTextAppearance(int resId) {
        this.errorTextAppearance = resId;
        TextView textView = this.errorView;
        if (textView != null) {
            this.textInputView.setTextAppearanceCompatWithErrorFallback(textView, resId);
        }
    }

    void setErrorContentDescription(CharSequence errorContentDescription) {
        this.errorViewContentDescription = errorContentDescription;
        TextView textView = this.errorView;
        if (textView != null) {
            textView.setContentDescription(errorContentDescription);
        }
    }

    CharSequence getErrorContentDescription() {
        return this.errorViewContentDescription;
    }

    int getHelperTextViewCurrentTextColor() {
        TextView textView = this.helperTextView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    ColorStateList getHelperTextViewColors() {
        TextView textView = this.helperTextView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    void setHelperTextViewTextColor(ColorStateList helperTextViewTextColor) {
        this.helperTextViewTextColor = helperTextViewTextColor;
        TextView textView = this.helperTextView;
        if (textView != null && helperTextViewTextColor != null) {
            textView.setTextColor(helperTextViewTextColor);
        }
    }

    void setHelperTextAppearance(int resId) {
        this.helperTextTextAppearance = resId;
        TextView textView = this.helperTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, resId);
        }
    }
}
