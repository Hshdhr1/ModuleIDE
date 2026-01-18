package com.google.android.material.timepicker;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class TimePickerView extends ConstraintLayout implements TimePickerControls {
    static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private final ClockFaceView clockFace;
    private final ClockHandView clockHandView;
    private final Chip hourView;
    private final Chip minuteView;
    private OnDoubleTapListener onDoubleTapListener;
    private OnPeriodChangeListener onPeriodChangeListener;
    private OnSelectionChange onSelectionChangeListener;
    private final View.OnClickListener selectionListener;
    private final MaterialButtonToggleGroup toggle;

    interface OnDoubleTapListener {
        void onDoubleTap();
    }

    interface OnPeriodChangeListener {
        void onPeriodChange(int i);
    }

    interface OnSelectionChange {
        void onSelectionChanged(int i);
    }

    static /* synthetic */ OnSelectionChange access$000(TimePickerView x0) {
        return x0.onSelectionChangeListener;
    }

    static /* synthetic */ OnPeriodChangeListener access$100(TimePickerView x0) {
        return x0.onPeriodChangeListener;
    }

    static /* synthetic */ OnDoubleTapListener access$200(TimePickerView x0) {
        return x0.onDoubleTapListener;
    }

    class 1 implements View.OnClickListener {
        1() {
        }

        public void onClick(View v) {
            if (TimePickerView.access$000(TimePickerView.this) != null) {
                TimePickerView.access$000(TimePickerView.this).onSelectionChanged(((Integer) v.getTag(R.id.selection_type)).intValue());
            }
        }
    }

    public TimePickerView(Context context) {
        this(context, null);
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.selectionListener = new 1();
        LayoutInflater.from(context).inflate(R.layout.material_timepicker, this);
        this.clockFace = findViewById(R.id.material_clock_face);
        MaterialButtonToggleGroup findViewById = findViewById(R.id.material_clock_period_toggle);
        this.toggle = findViewById;
        findViewById.addOnButtonCheckedListener(new 2());
        this.minuteView = findViewById(R.id.material_minute_tv);
        this.hourView = findViewById(R.id.material_hour_tv);
        this.clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        setupDoubleTap();
        setUpDisplay();
    }

    class 2 implements MaterialButtonToggleGroup.OnButtonCheckedListener {
        2() {
        }

        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            int period = checkedId == R.id.material_clock_period_pm_button ? 1 : 0;
            if (TimePickerView.access$100(TimePickerView.this) != null && isChecked) {
                TimePickerView.access$100(TimePickerView.this).onPeriodChange(period);
            }
        }
    }

    private void setupDoubleTap() {
        GestureDetector gestureDetector = new GestureDetector(getContext(), new 3());
        View.OnTouchListener onTouchListener = new 4(gestureDetector);
        this.minuteView.setOnTouchListener(onTouchListener);
        this.hourView.setOnTouchListener(onTouchListener);
    }

    class 3 extends GestureDetector.SimpleOnGestureListener {
        3() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            OnDoubleTapListener listener = TimePickerView.access$200(TimePickerView.this);
            if (listener != null) {
                listener.onDoubleTap();
                return true;
            }
            return false;
        }
    }

    class 4 implements View.OnTouchListener {
        final /* synthetic */ GestureDetector val$gestureDetector;

        4(GestureDetector gestureDetector) {
            this.val$gestureDetector = gestureDetector;
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (((Checkable) v).isChecked()) {
                return this.val$gestureDetector.onTouchEvent(event);
            }
            return false;
        }
    }

    public void setMinuteHourDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.hourView, clickActionDelegate);
    }

    public void setHourClickDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.minuteView, clickActionDelegate);
    }

    private void setUpDisplay() {
        this.minuteView.setTag(R.id.selection_type, 12);
        this.hourView.setTag(R.id.selection_type, 10);
        this.minuteView.setOnClickListener(this.selectionListener);
        this.hourView.setOnClickListener(this.selectionListener);
        this.minuteView.setAccessibilityClassName("android.view.View");
        this.hourView.setAccessibilityClassName("android.view.View");
    }

    public void setValues(String[] values, int contentDescription) {
        this.clockFace.setValues(values, contentDescription);
    }

    public void setHandRotation(float rotation) {
        this.clockHandView.setHandRotation(rotation);
    }

    public void setHandRotation(float rotation, boolean animate) {
        this.clockHandView.setHandRotation(rotation, animate);
    }

    public void setAnimateOnTouchUp(boolean animating) {
        this.clockHandView.setAnimateOnTouchUp(animating);
    }

    public void updateTime(int period, int hourOfDay, int minute) {
        int checkedId;
        if (period == 1) {
            checkedId = R.id.material_clock_period_pm_button;
        } else {
            checkedId = R.id.material_clock_period_am_button;
        }
        this.toggle.check(checkedId);
        Locale current = getResources().getConfiguration().locale;
        CharSequence format = String.format(current, "%02d", new Object[]{Integer.valueOf(minute)});
        CharSequence format2 = String.format(current, "%02d", new Object[]{Integer.valueOf(hourOfDay)});
        if (!TextUtils.equals(this.minuteView.getText(), format)) {
            this.minuteView.setText(format);
        }
        if (!TextUtils.equals(this.hourView.getText(), format2)) {
            this.hourView.setText(format2);
        }
    }

    public void setActiveSelection(int selection) {
        updateSelection(this.minuteView, selection == 12);
        updateSelection(this.hourView, selection == 10);
    }

    private void updateSelection(Chip chip, boolean isSelected) {
        int i;
        chip.setChecked(isSelected);
        if (isSelected) {
            i = 2;
        } else {
            i = 0;
        }
        ViewCompat.setAccessibilityLiveRegion(chip, i);
    }

    public void addOnRotateListener(ClockHandView.OnRotateListener onRotateListener) {
        this.clockHandView.addOnRotateListener(onRotateListener);
    }

    public void setOnActionUpListener(ClockHandView.OnActionUpListener onActionUpListener) {
        this.clockHandView.setOnActionUpListener(onActionUpListener);
    }

    void setOnPeriodChangeListener(OnPeriodChangeListener onPeriodChangeListener) {
        this.onPeriodChangeListener = onPeriodChangeListener;
    }

    void setOnSelectionChangeListener(OnSelectionChange onSelectionChangeListener) {
        this.onSelectionChangeListener = onSelectionChangeListener;
    }

    void setOnDoubleTapListener(OnDoubleTapListener listener) {
        this.onDoubleTapListener = listener;
    }

    public void showToggle() {
        this.toggle.setVisibility(0);
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility == 0) {
            updateToggleConstraints();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateToggleConstraints();
    }

    private void updateToggleConstraints() {
        if (this.toggle.getVisibility() == 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            boolean isLtr = ViewCompat.getLayoutDirection(this) == 0;
            int sideToClear = isLtr ? 2 : 1;
            constraintSet.clear(R.id.material_clock_display, sideToClear);
            constraintSet.applyTo(this);
        }
    }
}
