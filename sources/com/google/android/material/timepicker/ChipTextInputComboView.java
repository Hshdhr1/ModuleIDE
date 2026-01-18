package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class ChipTextInputComboView extends FrameLayout implements Checkable {
    private final Chip chip;
    private final EditText editText;
    private TextView label;
    private final TextInputLayout textInputLayout;
    private TextWatcher watcher;

    static /* synthetic */ String access$100(ChipTextInputComboView x0, CharSequence x1) {
        return x0.formatText(x1);
    }

    static /* synthetic */ Chip access$200(ChipTextInputComboView x0) {
        return x0.chip;
    }

    public ChipTextInputComboView(Context context) {
        this(context, null);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChipTextInputComboView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        Chip inflate = inflater.inflate(R.layout.material_time_chip, this, false);
        this.chip = inflate;
        inflate.setAccessibilityClassName("android.view.View");
        TextInputLayout inflate2 = inflater.inflate(R.layout.material_time_input, this, false);
        this.textInputLayout = inflate2;
        EditText editText = inflate2.getEditText();
        this.editText = editText;
        editText.setVisibility(4);
        TextFormatter textFormatter = new TextFormatter(this, null);
        this.watcher = textFormatter;
        editText.addTextChangedListener(textFormatter);
        updateHintLocales();
        addView(inflate);
        addView(inflate2);
        this.label = findViewById(R.id.material_label);
        editText.setSaveEnabled(false);
        editText.setLongClickable(false);
    }

    private void updateHintLocales() {
        if (Build.VERSION.SDK_INT >= 24) {
            Configuration configuration = getContext().getResources().getConfiguration();
            LocaleList locales = configuration.getLocales();
            this.editText.setImeHintLocales(locales);
        }
    }

    public boolean isChecked() {
        return this.chip.isChecked();
    }

    public void setChecked(boolean checked) {
        this.chip.setChecked(checked);
        this.editText.setVisibility(checked ? 0 : 4);
        this.chip.setVisibility(checked ? 8 : 0);
        if (isChecked()) {
            ViewUtils.requestFocusAndShowKeyboard(this.editText);
            if (!TextUtils.isEmpty(this.editText.getText())) {
                EditText editText = this.editText;
                editText.setSelection(editText.getText().length());
            }
        }
    }

    public void toggle() {
        this.chip.toggle();
    }

    public void setText(CharSequence text) {
        this.chip.setText(formatText(text));
        if (!TextUtils.isEmpty(this.editText.getText())) {
            this.editText.removeTextChangedListener(this.watcher);
            this.editText.setText((CharSequence) null);
            this.editText.addTextChangedListener(this.watcher);
        }
    }

    private String formatText(CharSequence text) {
        return TimeModel.formatText(getResources(), text);
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.chip.setOnClickListener(l);
    }

    public void setTag(int key, Object tag) {
        this.chip.setTag(key, tag);
    }

    public void setHelperText(CharSequence helperText) {
        this.label.setText(helperText);
    }

    public void setCursorVisible(boolean visible) {
        this.editText.setCursorVisible(visible);
    }

    public void addInputFilter(InputFilter filter) {
        InputFilter[] current = this.editText.getFilters();
        InputFilter[] arr = (InputFilter[]) Arrays.copyOf(current, current.length + 1);
        arr[current.length] = filter;
        this.editText.setFilters(arr);
    }

    public TextInputLayout getTextInput() {
        return this.textInputLayout;
    }

    public void setChipDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.chip, clickActionDelegate);
    }

    private class TextFormatter extends TextWatcherAdapter {
        private static final String DEFAULT_TEXT = "00";

        private TextFormatter() {
        }

        /* synthetic */ TextFormatter(ChipTextInputComboView x0, 1 x1) {
            this();
        }

        public void afterTextChanged(Editable editable) {
            if (TextUtils.isEmpty(editable)) {
                ChipTextInputComboView.access$200(ChipTextInputComboView.this).setText(ChipTextInputComboView.access$100(ChipTextInputComboView.this, "00"));
            } else {
                ChipTextInputComboView.access$200(ChipTextInputComboView.this).setText(ChipTextInputComboView.access$100(ChipTextInputComboView.this, editable));
            }
        }
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateHintLocales();
    }
}
