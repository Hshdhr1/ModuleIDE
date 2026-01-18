package com.google.android.material.textfield;

import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class PasswordToggleEndIconDelegate extends EndIconDelegate {
    private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener;
    private final TextInputLayout.OnEndIconChangedListener onEndIconChangedListener;
    private final TextWatcher textWatcher;

    static /* synthetic */ boolean access$000(PasswordToggleEndIconDelegate x0) {
        return x0.hasPasswordTransformation();
    }

    static /* synthetic */ TextWatcher access$100(PasswordToggleEndIconDelegate x0) {
        return x0.textWatcher;
    }

    class 1 extends TextWatcherAdapter {
        1() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            PasswordToggleEndIconDelegate.this.endIconView.setChecked(!PasswordToggleEndIconDelegate.access$000(PasswordToggleEndIconDelegate.this));
        }
    }

    class 2 implements TextInputLayout.OnEditTextAttachedListener {
        2() {
        }

        public void onEditTextAttached(TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            PasswordToggleEndIconDelegate.this.endIconView.setChecked(!PasswordToggleEndIconDelegate.access$000(PasswordToggleEndIconDelegate.this));
            editText.removeTextChangedListener(PasswordToggleEndIconDelegate.access$100(PasswordToggleEndIconDelegate.this));
            editText.addTextChangedListener(PasswordToggleEndIconDelegate.access$100(PasswordToggleEndIconDelegate.this));
        }
    }

    class 3 implements TextInputLayout.OnEndIconChangedListener {
        3() {
        }

        public void onEndIconChanged(TextInputLayout textInputLayout, int previousIcon) {
            EditText editText = textInputLayout.getEditText();
            if (editText != null && previousIcon == 1) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText.post(new 1(editText));
            }
        }

        class 1 implements Runnable {
            final /* synthetic */ EditText val$editText;

            1(EditText editText) {
                this.val$editText = editText;
            }

            public void run() {
                this.val$editText.removeTextChangedListener(PasswordToggleEndIconDelegate.access$100(PasswordToggleEndIconDelegate.this));
            }
        }
    }

    PasswordToggleEndIconDelegate(TextInputLayout textInputLayout, int customEndIcon) {
        super(textInputLayout, customEndIcon);
        this.textWatcher = new 1();
        this.onEditTextAttachedListener = new 2();
        this.onEndIconChangedListener = new 3();
    }

    void initialize() {
        this.textInputLayout.setEndIconDrawable(this.customEndIcon == 0 ? R.drawable.design_password_eye : this.customEndIcon);
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.password_toggle_content_description));
        this.textInputLayout.setEndIconVisible(true);
        this.textInputLayout.setEndIconCheckable(true);
        this.textInputLayout.setEndIconOnClickListener(new 4());
        this.textInputLayout.addOnEditTextAttachedListener(this.onEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.onEndIconChangedListener);
        EditText editText = this.textInputLayout.getEditText();
        if (isInputTypePassword(editText)) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    class 4 implements View.OnClickListener {
        4() {
        }

        public void onClick(View v) {
            EditText editText = PasswordToggleEndIconDelegate.this.textInputLayout.getEditText();
            if (editText == null) {
                return;
            }
            int selection = editText.getSelectionEnd();
            if (PasswordToggleEndIconDelegate.access$000(PasswordToggleEndIconDelegate.this)) {
                editText.setTransformationMethod((TransformationMethod) null);
            } else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (selection >= 0) {
                editText.setSelection(selection);
            }
            PasswordToggleEndIconDelegate.this.textInputLayout.refreshEndIconDrawableState();
        }
    }

    private boolean hasPasswordTransformation() {
        EditText editText = this.textInputLayout.getEditText();
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private static boolean isInputTypePassword(EditText editText) {
        return editText != null && (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224);
    }
}
