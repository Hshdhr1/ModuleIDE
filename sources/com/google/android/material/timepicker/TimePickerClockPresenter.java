package com.google.android.material.timepicker;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.timepicker.ClockHandView;
import com.google.android.material.timepicker.TimePickerView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class TimePickerClockPresenter implements ClockHandView.OnRotateListener, TimePickerView.OnSelectionChange, TimePickerView.OnPeriodChangeListener, ClockHandView.OnActionUpListener, TimePickerPresenter {
    private static final int DEGREES_PER_HOUR = 30;
    private static final int DEGREES_PER_MINUTE = 6;
    private boolean broadcasting = false;
    private float hourRotation;
    private float minuteRotation;
    private final TimeModel time;
    private final TimePickerView timePickerView;
    private static final String[] HOUR_CLOCK_VALUES = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private static final String[] HOUR_CLOCK_24_VALUES = {"00", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22"};
    private static final String[] MINUTE_CLOCK_VALUES = {"00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

    static /* synthetic */ TimeModel access$000(TimePickerClockPresenter x0) {
        return x0.time;
    }

    public TimePickerClockPresenter(TimePickerView timePickerView, TimeModel time) {
        this.timePickerView = timePickerView;
        this.time = time;
        initialize();
    }

    public void initialize() {
        if (this.time.format == 0) {
            this.timePickerView.showToggle();
        }
        this.timePickerView.addOnRotateListener(this);
        this.timePickerView.setOnSelectionChangeListener(this);
        this.timePickerView.setOnPeriodChangeListener(this);
        this.timePickerView.setOnActionUpListener(this);
        updateValues();
        invalidate();
    }

    public void invalidate() {
        int hourForDisplay = this.time.getHourForDisplay();
        this.hourRotation = getDegreesPerHour() * hourForDisplay;
        this.minuteRotation = this.time.minute * 6;
        setSelection(this.time.selection, false);
        updateTime();
    }

    public void show() {
        this.timePickerView.setVisibility(0);
    }

    public void hide() {
        this.timePickerView.setVisibility(8);
    }

    private String[] getHourClockValues() {
        return this.time.format == 1 ? HOUR_CLOCK_24_VALUES : HOUR_CLOCK_VALUES;
    }

    private int getDegreesPerHour() {
        return this.time.format == 1 ? 15 : 30;
    }

    public void onRotate(float rotation, boolean animating) {
        if (this.broadcasting) {
            return;
        }
        int prevHour = this.time.hour;
        int prevMinute = this.time.minute;
        int rotationInt = Math.round(rotation);
        if (this.time.selection == 12) {
            this.time.setMinute((rotationInt + 3) / 6);
            this.minuteRotation = (float) Math.floor(this.time.minute * 6);
        } else {
            int hourOffset = getDegreesPerHour() / 2;
            this.time.setHour((rotationInt + hourOffset) / getDegreesPerHour());
            this.hourRotation = this.time.getHourForDisplay() * getDegreesPerHour();
        }
        if (!animating) {
            updateTime();
            performHapticFeedback(prevHour, prevMinute);
        }
    }

    private void performHapticFeedback(int prevHour, int prevMinute) {
        if (this.time.minute != prevMinute || this.time.hour != prevHour) {
            int feedbackKey = Build.VERSION.SDK_INT >= 21 ? 4 : 1;
            this.timePickerView.performHapticFeedback(feedbackKey);
        }
    }

    public void onSelectionChanged(int selection) {
        setSelection(selection, true);
    }

    public void onPeriodChange(int period) {
        this.time.setPeriod(period);
    }

    void setSelection(int selection, boolean animate) {
        boolean isMinute = selection == 12;
        this.timePickerView.setAnimateOnTouchUp(isMinute);
        this.time.selection = selection;
        this.timePickerView.setValues(isMinute ? MINUTE_CLOCK_VALUES : getHourClockValues(), isMinute ? R.string.material_minute_suffix : R.string.material_hour_suffix);
        this.timePickerView.setHandRotation(isMinute ? this.minuteRotation : this.hourRotation, animate);
        this.timePickerView.setActiveSelection(selection);
        this.timePickerView.setMinuteHourDelegate(new 1(this.timePickerView.getContext(), R.string.material_hour_selection));
        this.timePickerView.setHourClickDelegate(new 2(this.timePickerView.getContext(), R.string.material_minute_selection));
    }

    class 1 extends ClickActionDelegate {
        1(Context context, int resId) {
            super(context, resId);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(host.getResources().getString(R.string.material_hour_suffix, new Object[]{String.valueOf(TimePickerClockPresenter.access$000(TimePickerClockPresenter.this).getHourForDisplay())}));
        }
    }

    class 2 extends ClickActionDelegate {
        2(Context context, int resId) {
            super(context, resId);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(host.getResources().getString(R.string.material_minute_suffix, new Object[]{String.valueOf(TimePickerClockPresenter.access$000(TimePickerClockPresenter.this).minute)}));
        }
    }

    public void onActionUp(float rotation, boolean moveInEventStream) {
        this.broadcasting = true;
        int prevMinute = this.time.minute;
        int prevHour = this.time.hour;
        if (this.time.selection == 10) {
            this.timePickerView.setHandRotation(this.hourRotation, false);
            AccessibilityManager am = (AccessibilityManager) ContextCompat.getSystemService(this.timePickerView.getContext(), AccessibilityManager.class);
            boolean isExploreByTouchEnabled = am != null && am.isTouchExplorationEnabled();
            if (!isExploreByTouchEnabled) {
                setSelection(12, true);
            }
        } else {
            int rotationInt = Math.round(rotation);
            if (!moveInEventStream) {
                int newRotation = (rotationInt + 15) / 30;
                this.time.setMinute(newRotation * 5);
                this.minuteRotation = this.time.minute * 6;
            }
            this.timePickerView.setHandRotation(this.minuteRotation, moveInEventStream);
        }
        this.broadcasting = false;
        updateTime();
        performHapticFeedback(prevHour, prevMinute);
    }

    private void updateTime() {
        this.timePickerView.updateTime(this.time.period, this.time.getHourForDisplay(), this.time.minute);
    }

    private void updateValues() {
        updateValues(HOUR_CLOCK_VALUES, "%d");
        updateValues(HOUR_CLOCK_24_VALUES, "%d");
        updateValues(MINUTE_CLOCK_VALUES, "%02d");
    }

    private void updateValues(String[] values, String format) {
        for (int i = 0; i < values.length; i++) {
            values[i] = TimeModel.formatText(this.timePickerView.getResources(), values[i], format);
        }
    }
}
