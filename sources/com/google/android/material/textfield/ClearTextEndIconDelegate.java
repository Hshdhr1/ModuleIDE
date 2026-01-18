package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.textfield.TextInputLayout;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class ClearTextEndIconDelegate extends EndIconDelegate {
    private static final int ANIMATION_FADE_DURATION = 100;
    private static final int ANIMATION_SCALE_DURATION = 150;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;
    private final TextWatcher clearTextEndIconTextWatcher;
    private final TextInputLayout.OnEditTextAttachedListener clearTextOnEditTextAttachedListener;
    private final TextInputLayout.OnEndIconChangedListener endIconChangedListener;
    private AnimatorSet iconInAnim;
    private ValueAnimator iconOutAnim;
    private final View.OnFocusChangeListener onFocusChangeListener;

    static /* synthetic */ boolean access$000(ClearTextEndIconDelegate x0) {
        return x0.shouldBeVisible();
    }

    static /* synthetic */ void access$100(ClearTextEndIconDelegate x0, boolean x1) {
        x0.animateIcon(x1);
    }

    static /* synthetic */ View.OnFocusChangeListener access$200(ClearTextEndIconDelegate x0) {
        return x0.onFocusChangeListener;
    }

    static /* synthetic */ TextWatcher access$300(ClearTextEndIconDelegate x0) {
        return x0.clearTextEndIconTextWatcher;
    }

    class 1 implements TextWatcher {
        1() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (ClearTextEndIconDelegate.this.textInputLayout.getSuffixText() != null) {
                return;
            }
            ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
            ClearTextEndIconDelegate.access$100(clearTextEndIconDelegate, ClearTextEndIconDelegate.access$000(clearTextEndIconDelegate));
        }
    }

    class 2 implements View.OnFocusChangeListener {
        2() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
            ClearTextEndIconDelegate.access$100(clearTextEndIconDelegate, ClearTextEndIconDelegate.access$000(clearTextEndIconDelegate));
        }
    }

    class 3 implements TextInputLayout.OnEditTextAttachedListener {
        3() {
        }

        public void onEditTextAttached(TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            textInputLayout.setEndIconVisible(ClearTextEndIconDelegate.access$000(ClearTextEndIconDelegate.this));
            editText.setOnFocusChangeListener(ClearTextEndIconDelegate.access$200(ClearTextEndIconDelegate.this));
            ClearTextEndIconDelegate.this.endIconView.setOnFocusChangeListener(ClearTextEndIconDelegate.access$200(ClearTextEndIconDelegate.this));
            editText.removeTextChangedListener(ClearTextEndIconDelegate.access$300(ClearTextEndIconDelegate.this));
            editText.addTextChangedListener(ClearTextEndIconDelegate.access$300(ClearTextEndIconDelegate.this));
        }
    }

    class 4 implements TextInputLayout.OnEndIconChangedListener {
        4() {
        }

        public void onEndIconChanged(TextInputLayout textInputLayout, int previousIcon) {
            EditText editText = textInputLayout.getEditText();
            if (editText != null && previousIcon == 2) {
                editText.post(new 1(editText));
                if (editText.getOnFocusChangeListener() == ClearTextEndIconDelegate.access$200(ClearTextEndIconDelegate.this)) {
                    editText.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                }
                if (ClearTextEndIconDelegate.this.endIconView.getOnFocusChangeListener() == ClearTextEndIconDelegate.access$200(ClearTextEndIconDelegate.this)) {
                    ClearTextEndIconDelegate.this.endIconView.setOnFocusChangeListener(null);
                }
            }
        }

        class 1 implements Runnable {
            final /* synthetic */ EditText val$editText;

            1(EditText editText) {
                this.val$editText = editText;
            }

            public void run() {
                this.val$editText.removeTextChangedListener(ClearTextEndIconDelegate.access$300(ClearTextEndIconDelegate.this));
                ClearTextEndIconDelegate.access$100(ClearTextEndIconDelegate.this, true);
            }
        }
    }

    ClearTextEndIconDelegate(TextInputLayout textInputLayout, int customEndIcon) {
        super(textInputLayout, customEndIcon);
        this.clearTextEndIconTextWatcher = new 1();
        this.onFocusChangeListener = new 2();
        this.clearTextOnEditTextAttachedListener = new 3();
        this.endIconChangedListener = new 4();
    }

    void initialize() {
        this.textInputLayout.setEndIconDrawable(this.customEndIcon == 0 ? R.drawable.mtrl_ic_cancel : this.customEndIcon);
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.clear_text_end_icon_content_description));
        this.textInputLayout.setEndIconCheckable(false);
        this.textInputLayout.setEndIconOnClickListener(new 5());
        this.textInputLayout.addOnEditTextAttachedListener(this.clearTextOnEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
        initAnimators();
    }

    class 5 implements View.OnClickListener {
        5() {
        }

        public void onClick(View v) {
            Editable text = ClearTextEndIconDelegate.this.textInputLayout.getEditText().getText();
            if (text != null) {
                text.clear();
            }
            ClearTextEndIconDelegate.this.textInputLayout.refreshEndIconDrawableState();
        }
    }

    void onSuffixVisibilityChanged(boolean visible) {
        if (this.textInputLayout.getSuffixText() == null) {
            return;
        }
        animateIcon(visible);
    }

    private void animateIcon(boolean show) {
        boolean shouldSkipAnimation = this.textInputLayout.isEndIconVisible() == show;
        if (show && !this.iconInAnim.isRunning()) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (shouldSkipAnimation) {
                this.iconInAnim.end();
                return;
            }
            return;
        }
        if (!show) {
            this.iconInAnim.cancel();
            this.iconOutAnim.start();
            if (shouldSkipAnimation) {
                this.iconOutAnim.end();
            }
        }
    }

    private void initAnimators() {
        Animator scaleAnimator = getScaleAnimator();
        Animator alphaAnimator = getAlphaAnimator(0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        this.iconInAnim = animatorSet;
        animatorSet.playTogether(new Animator[]{scaleAnimator, alphaAnimator});
        this.iconInAnim.addListener(new 6());
        ValueAnimator alphaAnimator2 = getAlphaAnimator(1.0f, 0.0f);
        this.iconOutAnim = alphaAnimator2;
        alphaAnimator2.addListener(new 7());
    }

    class 6 extends AnimatorListenerAdapter {
        6() {
        }

        public void onAnimationStart(Animator animation) {
            ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(true);
        }
    }

    class 7 extends AnimatorListenerAdapter {
        7() {
        }

        public void onAnimationEnd(Animator animation) {
            ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(false);
        }
    }

    private ValueAnimator getAlphaAnimator(float... values) {
        ValueAnimator animator = ValueAnimator.ofFloat(values);
        animator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        animator.setDuration(100L);
        animator.addUpdateListener(new 8());
        return animator;
    }

    class 8 implements ValueAnimator.AnimatorUpdateListener {
        8() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float alpha = ((Float) animation.getAnimatedValue()).floatValue();
            ClearTextEndIconDelegate.this.endIconView.setAlpha(alpha);
        }
    }

    private ValueAnimator getScaleAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.8f, 1.0f});
        animator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(150L);
        animator.addUpdateListener(new 9());
        return animator;
    }

    class 9 implements ValueAnimator.AnimatorUpdateListener {
        9() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float scale = ((Float) animation.getAnimatedValue()).floatValue();
            ClearTextEndIconDelegate.this.endIconView.setScaleX(scale);
            ClearTextEndIconDelegate.this.endIconView.setScaleY(scale);
        }
    }

    private boolean shouldBeVisible() {
        EditText editText = this.textInputLayout.getEditText();
        return editText != null && (editText.hasFocus() || this.endIconView.hasFocus()) && editText.getText().length() > 0;
    }
}
