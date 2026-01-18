package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.timepicker.TimePickerView;
import java.lang.reflect.Field;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class TimePickerTextInputPresenter implements TimePickerView.OnSelectionChange, TimePickerPresenter {
    private final TimePickerTextInputKeyController controller;
    private final EditText hourEditText;
    private final ChipTextInputComboView hourTextInput;
    private final EditText minuteEditText;
    private final ChipTextInputComboView minuteTextInput;
    private final TimeModel time;
    private final LinearLayout timePickerView;
    private MaterialButtonToggleGroup toggle;
    private final TextWatcher minuteTextWatcher = new 1();
    private final TextWatcher hourTextWatcher = new 2();

    static /* synthetic */ TimeModel access$000(TimePickerTextInputPresenter x0) {
        return x0.time;
    }

    class 1 extends TextWatcherAdapter {
        1() {
        }

        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.access$000(TimePickerTextInputPresenter.this).setMinute(0);
                } else {
                    int minute = Integer.parseInt(s.toString());
                    TimePickerTextInputPresenter.access$000(TimePickerTextInputPresenter.this).setMinute(minute);
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    class 2 extends TextWatcherAdapter {
        2() {
        }

        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.access$000(TimePickerTextInputPresenter.this).setHour(0);
                } else {
                    int hour = Integer.parseInt(s.toString());
                    TimePickerTextInputPresenter.access$000(TimePickerTextInputPresenter.this).setHour(hour);
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public TimePickerTextInputPresenter(LinearLayout timePickerView, TimeModel time) {
        this.timePickerView = timePickerView;
        this.time = time;
        Resources res = timePickerView.getResources();
        ChipTextInputComboView findViewById = timePickerView.findViewById(R.id.material_minute_text_input);
        this.minuteTextInput = findViewById;
        ChipTextInputComboView findViewById2 = timePickerView.findViewById(R.id.material_hour_text_input);
        this.hourTextInput = findViewById2;
        TextView minuteLabel = findViewById.findViewById(R.id.material_label);
        TextView hourLabel = findViewById2.findViewById(R.id.material_label);
        minuteLabel.setText(res.getString(R.string.material_timepicker_minute));
        hourLabel.setText(res.getString(R.string.material_timepicker_hour));
        findViewById.setTag(R.id.selection_type, 12);
        findViewById2.setTag(R.id.selection_type, 10);
        if (time.format == 0) {
            setupPeriodToggle();
        }
        View.OnClickListener onClickListener = new 3();
        findViewById2.setOnClickListener(onClickListener);
        findViewById.setOnClickListener(onClickListener);
        findViewById2.addInputFilter(time.getHourInputValidator());
        findViewById.addInputFilter(time.getMinuteInputValidator());
        EditText editText = findViewById2.getTextInput().getEditText();
        this.hourEditText = editText;
        EditText editText2 = findViewById.getTextInput().getEditText();
        this.minuteEditText = editText2;
        if (Build.VERSION.SDK_INT < 21) {
            int primaryColor = MaterialColors.getColor(timePickerView, R.attr.colorPrimary);
            setCursorDrawableColor(editText, primaryColor);
            setCursorDrawableColor(editText2, primaryColor);
        }
        this.controller = new TimePickerTextInputKeyController(findViewById2, findViewById, time);
        findViewById2.setChipDelegate(new 4(timePickerView.getContext(), R.string.material_hour_selection, time));
        findViewById.setChipDelegate(new 5(timePickerView.getContext(), R.string.material_minute_selection, time));
        initialize();
    }

    class 3 implements View.OnClickListener {
        3() {
        }

        public void onClick(View v) {
            TimePickerTextInputPresenter.this.onSelectionChanged(((Integer) v.getTag(R.id.selection_type)).intValue());
        }
    }

    class 4 extends ClickActionDelegate {
        final /* synthetic */ TimeModel val$time;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(Context context, int resId, TimeModel timeModel) {
            super(context, resId);
            this.val$time = timeModel;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(host.getResources().getString(R.string.material_hour_suffix, new Object[]{String.valueOf(this.val$time.getHourForDisplay())}));
        }
    }

    class 5 extends ClickActionDelegate {
        final /* synthetic */ TimeModel val$time;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        5(Context context, int resId, TimeModel timeModel) {
            super(context, resId);
            this.val$time = timeModel;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(host.getResources().getString(R.string.material_minute_suffix, new Object[]{String.valueOf(this.val$time.minute)}));
        }
    }

    public void initialize() {
        addTextWatchers();
        setTime(this.time);
        this.controller.bind();
    }

    private void addTextWatchers() {
        this.hourEditText.addTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.addTextChangedListener(this.minuteTextWatcher);
    }

    private void removeTextWatchers() {
        this.hourEditText.removeTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.removeTextChangedListener(this.minuteTextWatcher);
    }

    private void setTime(TimeModel time) {
        removeTextWatchers();
        Locale current = this.timePickerView.getResources().getConfiguration().locale;
        CharSequence format = String.format(current, "%02d", new Object[]{Integer.valueOf(time.minute)});
        CharSequence format2 = String.format(current, "%02d", new Object[]{Integer.valueOf(time.getHourForDisplay())});
        this.minuteTextInput.setText(format);
        this.hourTextInput.setText(format2);
        addTextWatchers();
        updateSelection();
    }

    private void setupPeriodToggle() {
        MaterialButtonToggleGroup findViewById = this.timePickerView.findViewById(R.id.material_clock_period_toggle);
        this.toggle = findViewById;
        findViewById.addOnButtonCheckedListener(new 6());
        this.toggle.setVisibility(0);
        updateSelection();
    }

    class 6 implements MaterialButtonToggleGroup.OnButtonCheckedListener {
        6() {
        }

        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            int period = checkedId == R.id.material_clock_period_pm_button ? 1 : 0;
            TimePickerTextInputPresenter.access$000(TimePickerTextInputPresenter.this).setPeriod(period);
        }
    }

    private void updateSelection() {
        int i;
        MaterialButtonToggleGroup materialButtonToggleGroup = this.toggle;
        if (materialButtonToggleGroup == null) {
            return;
        }
        if (this.time.period == 0) {
            i = R.id.material_clock_period_am_button;
        } else {
            i = R.id.material_clock_period_pm_button;
        }
        materialButtonToggleGroup.check(i);
    }

    public void onSelectionChanged(int selection) {
        this.time.selection = selection;
        this.minuteTextInput.setChecked(selection == 12);
        this.hourTextInput.setChecked(selection == 10);
        updateSelection();
    }

    public void show() {
        this.timePickerView.setVisibility(0);
    }

    public void hide() {
        View currentFocus = this.timePickerView.getFocusedChild();
        if (currentFocus == null) {
            this.timePickerView.setVisibility(8);
            return;
        }
        Context context = this.timePickerView.getContext();
        InputMethodManager imm = (InputMethodManager) ContextCompat.getSystemService(context, InputMethodManager.class);
        if (imm != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        this.timePickerView.setVisibility(8);
    }

    public void invalidate() {
        setTime(this.time);
    }

    private static void setCursorDrawableColor(EditText view, int color) {
        try {
            Context context = view.getContext();
            Field cursorDrawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorDrawableResField.setAccessible(true);
            int cursorDrawableResId = cursorDrawableResField.getInt(view);
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editor = editorField.get(view);
            Class<?> clazz = editor.getClass();
            Field cursorDrawableField = clazz.getDeclaredField("mCursorDrawable");
            cursorDrawableField.setAccessible(true);
            Drawable drawable = AppCompatResources.getDrawable(context, cursorDrawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};
            cursorDrawableField.set(editor, drawables);
        } catch (Throwable th) {
        }
    }

    public void resetChecked() {
        this.minuteTextInput.setChecked(this.time.selection == 12);
        this.hourTextInput.setChecked(this.time.selection == 10);
    }

    public void clearCheck() {
        this.minuteTextInput.setChecked(false);
        this.hourTextInput.setChecked(false);
    }
}
