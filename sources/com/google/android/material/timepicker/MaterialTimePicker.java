package com.google.android.material.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.timepicker.TimePickerView;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class MaterialTimePicker extends DialogFragment implements TimePickerView.OnDoubleTapListener {
    public static final int INPUT_MODE_CLOCK = 0;
    static final String INPUT_MODE_EXTRA = "TIME_PICKER_INPUT_MODE";
    public static final int INPUT_MODE_KEYBOARD = 1;
    static final String NEGATIVE_BUTTON_TEXT_EXTRA = "TIME_PICKER_NEGATIVE_BUTTON_TEXT";
    static final String NEGATIVE_BUTTON_TEXT_RES_EXTRA = "TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES";
    static final String OVERRIDE_THEME_RES_ID = "TIME_PICKER_OVERRIDE_THEME_RES_ID";
    static final String POSITIVE_BUTTON_TEXT_EXTRA = "TIME_PICKER_POSITIVE_BUTTON_TEXT";
    static final String POSITIVE_BUTTON_TEXT_RES_EXTRA = "TIME_PICKER_POSITIVE_BUTTON_TEXT_RES";
    static final String TIME_MODEL_EXTRA = "TIME_PICKER_TIME_MODEL";
    static final String TITLE_RES_EXTRA = "TIME_PICKER_TITLE_RES";
    static final String TITLE_TEXT_EXTRA = "TIME_PICKER_TITLE_TEXT";
    private TimePickerPresenter activePresenter;
    private Button cancelButton;
    private int clockIcon;
    private int keyboardIcon;
    private MaterialButton modeButton;
    private CharSequence negativeButtonText;
    private CharSequence positiveButtonText;
    private ViewStub textInputStub;
    private TimeModel time;
    private TimePickerClockPresenter timePickerClockPresenter;
    private TimePickerTextInputPresenter timePickerTextInputPresenter;
    private TimePickerView timePickerView;
    private CharSequence titleText;
    private final Set positiveButtonListeners = new LinkedHashSet();
    private final Set negativeButtonListeners = new LinkedHashSet();
    private final Set cancelListeners = new LinkedHashSet();
    private final Set dismissListeners = new LinkedHashSet();
    private int titleResId = 0;
    private int positiveButtonTextResId = 0;
    private int negativeButtonTextResId = 0;
    private int inputMode = 0;
    private int overrideThemeResId = 0;

    static /* synthetic */ Set access$1000(MaterialTimePicker x0) {
        return x0.negativeButtonListeners;
    }

    static /* synthetic */ int access$1100(MaterialTimePicker x0) {
        return x0.inputMode;
    }

    static /* synthetic */ int access$1102(MaterialTimePicker x0, int x1) {
        x0.inputMode = x1;
        return x1;
    }

    static /* synthetic */ MaterialButton access$1200(MaterialTimePicker x0) {
        return x0.modeButton;
    }

    static /* synthetic */ void access$1300(MaterialTimePicker x0, MaterialButton x1) {
        x0.updateInputMode(x1);
    }

    static /* synthetic */ MaterialTimePicker access$1400(Builder x0) {
        return newInstance(x0);
    }

    static /* synthetic */ Set access$900(MaterialTimePicker x0) {
        return x0.positiveButtonListeners;
    }

    private static MaterialTimePicker newInstance(Builder options) {
        MaterialTimePicker fragment = new MaterialTimePicker();
        Bundle args = new Bundle();
        args.putParcelable("TIME_PICKER_TIME_MODEL", Builder.access$000(options));
        args.putInt("TIME_PICKER_INPUT_MODE", Builder.access$100(options));
        args.putInt("TIME_PICKER_TITLE_RES", Builder.access$200(options));
        if (Builder.access$300(options) != null) {
            args.putCharSequence("TIME_PICKER_TITLE_TEXT", Builder.access$300(options));
        }
        args.putInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", Builder.access$400(options));
        if (Builder.access$500(options) != null) {
            args.putCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT", Builder.access$500(options));
        }
        args.putInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", Builder.access$600(options));
        if (Builder.access$700(options) != null) {
            args.putCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT", Builder.access$700(options));
        }
        args.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", Builder.access$800(options));
        fragment.setArguments(args);
        return fragment;
    }

    public int getMinute() {
        return this.time.minute;
    }

    public void setMinute(int minute) {
        this.time.setMinute(minute);
        TimePickerPresenter timePickerPresenter = this.activePresenter;
        if (timePickerPresenter != null) {
            timePickerPresenter.invalidate();
        }
    }

    public int getHour() {
        return this.time.hour % 24;
    }

    public void setHour(int hour) {
        this.time.setHour(hour);
        TimePickerPresenter timePickerPresenter = this.activePresenter;
        if (timePickerPresenter != null) {
            timePickerPresenter.invalidate();
        }
    }

    public int getInputMode() {
        return this.inputMode;
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId());
        Context context = dialog.getContext();
        int surfaceColor = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialTimePicker.class.getCanonicalName());
        MaterialShapeDrawable background = new MaterialShapeDrawable(context, null, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, R.styleable.MaterialTimePicker, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
        this.clockIcon = a.getResourceId(R.styleable.MaterialTimePicker_clockIcon, 0);
        this.keyboardIcon = a.getResourceId(R.styleable.MaterialTimePicker_keyboardIcon, 0);
        a.recycle();
        background.initializeElevationOverlay(context);
        background.setFillColor(ColorStateList.valueOf(surfaceColor));
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(background);
        window.requestFeature(1);
        window.setLayout(-2, -2);
        background.setElevation(ViewCompat.getElevation(window.getDecorView()));
        return dialog;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        restoreState(bundle == null ? getArguments() : bundle);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("TIME_PICKER_TIME_MODEL", this.time);
        bundle.putInt("TIME_PICKER_INPUT_MODE", this.inputMode);
        bundle.putInt("TIME_PICKER_TITLE_RES", this.titleResId);
        bundle.putCharSequence("TIME_PICKER_TITLE_TEXT", this.titleText);
        bundle.putInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", this.positiveButtonTextResId);
        bundle.putCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT", this.positiveButtonText);
        bundle.putInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", this.negativeButtonTextResId);
        bundle.putCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT", this.negativeButtonText);
        bundle.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
    }

    private void restoreState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        TimeModel timeModel = (TimeModel) bundle.getParcelable("TIME_PICKER_TIME_MODEL");
        this.time = timeModel;
        if (timeModel == null) {
            this.time = new TimeModel();
        }
        this.inputMode = bundle.getInt("TIME_PICKER_INPUT_MODE", 0);
        this.titleResId = bundle.getInt("TIME_PICKER_TITLE_RES", 0);
        this.titleText = bundle.getCharSequence("TIME_PICKER_TITLE_TEXT");
        this.positiveButtonTextResId = bundle.getInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", 0);
        this.positiveButtonText = bundle.getCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT");
        this.negativeButtonTextResId = bundle.getInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", 0);
        this.negativeButtonText = bundle.getCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT");
        this.overrideThemeResId = bundle.getInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", 0);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup root = layoutInflater.inflate(R.layout.material_timepicker_dialog, viewGroup);
        TimePickerView findViewById = root.findViewById(R.id.material_timepicker_view);
        this.timePickerView = findViewById;
        findViewById.setOnDoubleTapListener(this);
        this.textInputStub = root.findViewById(R.id.material_textinput_timepicker);
        this.modeButton = root.findViewById(R.id.material_timepicker_mode_button);
        TextView headerTitle = root.findViewById(R.id.header_title);
        int i = this.titleResId;
        if (i != 0) {
            headerTitle.setText(i);
        } else if (!TextUtils.isEmpty(this.titleText)) {
            headerTitle.setText(this.titleText);
        }
        updateInputMode(this.modeButton);
        Button okButton = root.findViewById(R.id.material_timepicker_ok_button);
        okButton.setOnClickListener(new 1());
        int i2 = this.positiveButtonTextResId;
        if (i2 != 0) {
            okButton.setText(i2);
        } else if (!TextUtils.isEmpty(this.positiveButtonText)) {
            okButton.setText(this.positiveButtonText);
        }
        Button findViewById2 = root.findViewById(R.id.material_timepicker_cancel_button);
        this.cancelButton = findViewById2;
        findViewById2.setOnClickListener(new 2());
        int i3 = this.negativeButtonTextResId;
        if (i3 != 0) {
            this.cancelButton.setText(i3);
        } else if (!TextUtils.isEmpty(this.negativeButtonText)) {
            this.cancelButton.setText(this.negativeButtonText);
        }
        updateCancelButtonVisibility();
        this.modeButton.setOnClickListener(new 3());
        return root;
    }

    class 1 implements View.OnClickListener {
        1() {
        }

        public void onClick(View v) {
            for (View.OnClickListener listener : MaterialTimePicker.access$900(MaterialTimePicker.this)) {
                listener.onClick(v);
            }
            MaterialTimePicker.this.dismiss();
        }
    }

    class 2 implements View.OnClickListener {
        2() {
        }

        public void onClick(View v) {
            for (View.OnClickListener listener : MaterialTimePicker.access$1000(MaterialTimePicker.this)) {
                listener.onClick(v);
            }
            MaterialTimePicker.this.dismiss();
        }
    }

    class 3 implements View.OnClickListener {
        3() {
        }

        public void onClick(View v) {
            MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
            MaterialTimePicker.access$1102(materialTimePicker, MaterialTimePicker.access$1100(materialTimePicker) == 0 ? 1 : 0);
            MaterialTimePicker materialTimePicker2 = MaterialTimePicker.this;
            MaterialTimePicker.access$1300(materialTimePicker2, MaterialTimePicker.access$1200(materialTimePicker2));
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.activePresenter = null;
        this.timePickerClockPresenter = null;
        this.timePickerTextInputPresenter = null;
        TimePickerView timePickerView = this.timePickerView;
        if (timePickerView != null) {
            timePickerView.setOnDoubleTapListener(null);
            this.timePickerView = null;
        }
    }

    public final void onCancel(DialogInterface dialogInterface) {
        for (DialogInterface.OnCancelListener listener : this.cancelListeners) {
            listener.onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        for (DialogInterface.OnDismissListener listener : this.dismissListeners) {
            listener.onDismiss(dialogInterface);
        }
        super.onDismiss(dialogInterface);
    }

    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        updateCancelButtonVisibility();
    }

    public void onDoubleTap() {
        this.inputMode = 1;
        updateInputMode(this.modeButton);
        this.timePickerTextInputPresenter.resetChecked();
    }

    private void updateInputMode(MaterialButton modeButton) {
        if (modeButton == null || this.timePickerView == null || this.textInputStub == null) {
            return;
        }
        TimePickerPresenter timePickerPresenter = this.activePresenter;
        if (timePickerPresenter != null) {
            timePickerPresenter.hide();
        }
        TimePickerPresenter initializeOrRetrieveActivePresenterForMode = initializeOrRetrieveActivePresenterForMode(this.inputMode, this.timePickerView, this.textInputStub);
        this.activePresenter = initializeOrRetrieveActivePresenterForMode;
        initializeOrRetrieveActivePresenterForMode.show();
        this.activePresenter.invalidate();
        Pair<Integer, Integer> buttonData = dataForMode(this.inputMode);
        modeButton.setIconResource(((Integer) buttonData.first).intValue());
        modeButton.setContentDescription(getResources().getString(((Integer) buttonData.second).intValue()));
        modeButton.sendAccessibilityEvent(4);
    }

    private void updateCancelButtonVisibility() {
        Button button = this.cancelButton;
        if (button != null) {
            button.setVisibility(isCancelable() ? 0 : 8);
        }
    }

    private TimePickerPresenter initializeOrRetrieveActivePresenterForMode(int mode, TimePickerView timePickerView, ViewStub textInputStub) {
        if (mode == 0) {
            TimePickerClockPresenter timePickerClockPresenter = this.timePickerClockPresenter;
            if (timePickerClockPresenter == null) {
                timePickerClockPresenter = new TimePickerClockPresenter(timePickerView, this.time);
            }
            this.timePickerClockPresenter = timePickerClockPresenter;
            return timePickerClockPresenter;
        }
        if (this.timePickerTextInputPresenter == null) {
            LinearLayout textInputView = textInputStub.inflate();
            this.timePickerTextInputPresenter = new TimePickerTextInputPresenter(textInputView, this.time);
        }
        this.timePickerTextInputPresenter.clearCheck();
        return this.timePickerTextInputPresenter;
    }

    private Pair dataForMode(int mode) {
        switch (mode) {
            case 0:
                return new Pair(Integer.valueOf(this.keyboardIcon), Integer.valueOf(R.string.material_timepicker_text_input_mode_description));
            case 1:
                return new Pair(Integer.valueOf(this.clockIcon), Integer.valueOf(R.string.material_timepicker_clock_mode_description));
            default:
                throw new IllegalArgumentException("no icon for mode: " + mode);
        }
    }

    TimePickerClockPresenter getTimePickerClockPresenter() {
        return this.timePickerClockPresenter;
    }

    void setActivePresenter(TimePickerPresenter presenter) {
        this.activePresenter = presenter;
    }

    public boolean addOnPositiveButtonClickListener(View.OnClickListener listener) {
        return this.positiveButtonListeners.add(listener);
    }

    public boolean removeOnPositiveButtonClickListener(View.OnClickListener listener) {
        return this.positiveButtonListeners.remove(listener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.positiveButtonListeners.clear();
    }

    public boolean addOnNegativeButtonClickListener(View.OnClickListener listener) {
        return this.negativeButtonListeners.add(listener);
    }

    public boolean removeOnNegativeButtonClickListener(View.OnClickListener listener) {
        return this.negativeButtonListeners.remove(listener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.negativeButtonListeners.clear();
    }

    public boolean addOnCancelListener(DialogInterface.OnCancelListener listener) {
        return this.cancelListeners.add(listener);
    }

    public boolean removeOnCancelListener(DialogInterface.OnCancelListener listener) {
        return this.cancelListeners.remove(listener);
    }

    public void clearOnCancelListeners() {
        this.cancelListeners.clear();
    }

    public boolean addOnDismissListener(DialogInterface.OnDismissListener listener) {
        return this.dismissListeners.add(listener);
    }

    public boolean removeOnDismissListener(DialogInterface.OnDismissListener listener) {
        return this.dismissListeners.remove(listener);
    }

    public void clearOnDismissListeners() {
        this.dismissListeners.clear();
    }

    private int getThemeResId() {
        int i = this.overrideThemeResId;
        if (i != 0) {
            return i;
        }
        TypedValue value = MaterialAttributes.resolve(requireContext(), R.attr.materialTimePickerTheme);
        if (value == null) {
            return 0;
        }
        return value.data;
    }

    public static final class Builder {
        private int inputMode;
        private CharSequence negativeButtonText;
        private CharSequence positiveButtonText;
        private CharSequence titleText;
        private TimeModel time = new TimeModel();
        private int titleTextResId = 0;
        private int positiveButtonTextResId = 0;
        private int negativeButtonTextResId = 0;
        private int overrideThemeResId = 0;

        static /* synthetic */ TimeModel access$000(Builder x0) {
            return x0.time;
        }

        static /* synthetic */ int access$100(Builder x0) {
            return x0.inputMode;
        }

        static /* synthetic */ int access$200(Builder x0) {
            return x0.titleTextResId;
        }

        static /* synthetic */ CharSequence access$300(Builder x0) {
            return x0.titleText;
        }

        static /* synthetic */ int access$400(Builder x0) {
            return x0.positiveButtonTextResId;
        }

        static /* synthetic */ CharSequence access$500(Builder x0) {
            return x0.positiveButtonText;
        }

        static /* synthetic */ int access$600(Builder x0) {
            return x0.negativeButtonTextResId;
        }

        static /* synthetic */ CharSequence access$700(Builder x0) {
            return x0.negativeButtonText;
        }

        static /* synthetic */ int access$800(Builder x0) {
            return x0.overrideThemeResId;
        }

        public Builder setInputMode(int inputMode) {
            this.inputMode = inputMode;
            return this;
        }

        public Builder setHour(int hour) {
            this.time.setHourOfDay(hour);
            return this;
        }

        public Builder setMinute(int minute) {
            this.time.setMinute(minute);
            return this;
        }

        public Builder setTimeFormat(int format) {
            int hour = this.time.hour;
            int minute = this.time.minute;
            TimeModel timeModel = new TimeModel(format);
            this.time = timeModel;
            timeModel.setMinute(minute);
            this.time.setHourOfDay(hour);
            return this;
        }

        public Builder setTitleText(int titleTextResId) {
            this.titleTextResId = titleTextResId;
            return this;
        }

        public Builder setTitleText(CharSequence charSequence) {
            this.titleText = charSequence;
            return this;
        }

        public Builder setPositiveButtonText(int positiveButtonTextResId) {
            this.positiveButtonTextResId = positiveButtonTextResId;
            return this;
        }

        public Builder setPositiveButtonText(CharSequence positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }

        public Builder setNegativeButtonText(int negativeButtonTextResId) {
            this.negativeButtonTextResId = negativeButtonTextResId;
            return this;
        }

        public Builder setNegativeButtonText(CharSequence negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }

        public Builder setTheme(int themeResId) {
            this.overrideThemeResId = themeResId;
            return this;
        }

        public MaterialTimePicker build() {
            return MaterialTimePicker.access$1400(this);
        }
    }
}
