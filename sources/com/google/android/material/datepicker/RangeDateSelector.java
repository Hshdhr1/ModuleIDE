package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class RangeDateSelector implements DateSelector {
    public static final Parcelable.Creator CREATOR = new 3();
    private String invalidRangeStartError;
    private final String invalidRangeEndError = " ";
    private Long selectedStartItem = null;
    private Long selectedEndItem = null;
    private Long proposedTextStart = null;
    private Long proposedTextEnd = null;

    static /* synthetic */ Long access$002(RangeDateSelector x0, Long x1) {
        x0.proposedTextStart = x1;
        return x1;
    }

    static /* synthetic */ void access$100(RangeDateSelector x0, TextInputLayout x1, TextInputLayout x2, OnSelectionChangedListener x3) {
        x0.updateIfValidTextProposal(x1, x2, x3);
    }

    static /* synthetic */ Long access$202(RangeDateSelector x0, Long x1) {
        x0.proposedTextEnd = x1;
        return x1;
    }

    static /* synthetic */ Long access$302(RangeDateSelector x0, Long x1) {
        x0.selectedStartItem = x1;
        return x1;
    }

    static /* synthetic */ Long access$402(RangeDateSelector x0, Long x1) {
        x0.selectedEndItem = x1;
        return x1;
    }

    public void select(long selection) {
        Long l = this.selectedStartItem;
        if (l == null) {
            this.selectedStartItem = Long.valueOf(selection);
        } else if (this.selectedEndItem == null && isValidRange(l.longValue(), selection)) {
            this.selectedEndItem = Long.valueOf(selection);
        } else {
            this.selectedEndItem = null;
            this.selectedStartItem = Long.valueOf(selection);
        }
    }

    public boolean isSelectionComplete() {
        Long l = this.selectedStartItem;
        return (l == null || this.selectedEndItem == null || !isValidRange(l.longValue(), this.selectedEndItem.longValue())) ? false : true;
    }

    public void setSelection(Pair pair) {
        if (pair.first != null && pair.second != null) {
            Preconditions.checkArgument(isValidRange(((Long) pair.first).longValue(), ((Long) pair.second).longValue()));
        }
        this.selectedStartItem = pair.first == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(((Long) pair.first).longValue()));
        this.selectedEndItem = pair.second != null ? Long.valueOf(UtcDates.canonicalYearMonthDay(((Long) pair.second).longValue())) : null;
    }

    public Pair getSelection() {
        return new Pair(this.selectedStartItem, this.selectedEndItem);
    }

    public Collection getSelectedRanges() {
        if (this.selectedStartItem == null || this.selectedEndItem == null) {
            return new ArrayList();
        }
        ArrayList<Pair<Long, Long>> ranges = new ArrayList<>();
        Pair<Long, Long> range = new Pair<>(this.selectedStartItem, this.selectedEndItem);
        ranges.add(range);
        return ranges;
    }

    public Collection getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        Long l = this.selectedStartItem;
        if (l != null) {
            selections.add(l);
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            selections.add(l2);
        }
        return selections;
    }

    public int getDefaultThemeResId(Context context) {
        int defaultThemeAttr;
        Resources res = context.getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        int maximumDefaultFullscreenMinorAxis = res.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis);
        int minorAxisPx = Math.min(display.widthPixels, display.heightPixels);
        if (minorAxisPx > maximumDefaultFullscreenMinorAxis) {
            defaultThemeAttr = R.attr.materialCalendarTheme;
        } else {
            defaultThemeAttr = R.attr.materialCalendarFullscreenTheme;
        }
        return MaterialAttributes.resolveOrThrow(context, defaultThemeAttr, MaterialDatePicker.class.getCanonicalName());
    }

    public String getSelectionDisplayString(Context context) {
        Resources res = context.getResources();
        Long l = this.selectedStartItem;
        if (l == null && this.selectedEndItem == null) {
            return res.getString(R.string.mtrl_picker_range_header_unselected);
        }
        Long l2 = this.selectedEndItem;
        if (l2 == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_start_selected, new Object[]{DateStrings.getDateString(this.selectedStartItem.longValue())});
        }
        if (l == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_end_selected, new Object[]{DateStrings.getDateString(this.selectedEndItem.longValue())});
        }
        Pair<String, String> dateRangeStrings = DateStrings.getDateRangeString(l, l2);
        return res.getString(R.string.mtrl_picker_range_header_selected, new Object[]{dateRangeStrings.first, dateRangeStrings.second});
    }

    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_range_header_title;
    }

    public View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle, CalendarConstraints constraints, OnSelectionChangedListener onSelectionChangedListener) {
        View root = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, viewGroup, false);
        TextInputLayout startTextInput = root.findViewById(R.id.mtrl_picker_text_input_range_start);
        TextInputLayout endTextInput = root.findViewById(R.id.mtrl_picker_text_input_range_end);
        EditText startEditText = startTextInput.getEditText();
        EditText endEditText = endTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            startEditText.setInputType(17);
            endEditText.setInputType(17);
        }
        this.invalidRangeStartError = root.getResources().getString(R.string.mtrl_picker_invalid_range);
        SimpleDateFormat format = UtcDates.getTextInputFormat();
        Long l = this.selectedStartItem;
        if (l != null) {
            startEditText.setText(format.format(l));
            this.proposedTextStart = this.selectedStartItem;
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            endEditText.setText(format.format(l2));
            this.proposedTextEnd = this.selectedEndItem;
        }
        String formatHint = UtcDates.getTextInputHint(root.getResources(), format);
        startTextInput.setPlaceholderText(formatHint);
        endTextInput.setPlaceholderText(formatHint);
        startEditText.addTextChangedListener(new 1(formatHint, format, startTextInput, constraints, startTextInput, endTextInput, onSelectionChangedListener));
        endEditText.addTextChangedListener(new 2(formatHint, format, endTextInput, constraints, startTextInput, endTextInput, onSelectionChangedListener));
        ViewUtils.requestFocusAndShowKeyboard(startEditText);
        return root;
    }

    class 1 extends DateFormatTextWatcher {
        final /* synthetic */ TextInputLayout val$endTextInput;
        final /* synthetic */ OnSelectionChangedListener val$listener;
        final /* synthetic */ TextInputLayout val$startTextInput;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(String formatHint, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints constraints, TextInputLayout textInputLayout2, TextInputLayout textInputLayout3, OnSelectionChangedListener onSelectionChangedListener) {
            super(formatHint, dateFormat, textInputLayout, constraints);
            this.val$startTextInput = textInputLayout2;
            this.val$endTextInput = textInputLayout3;
            this.val$listener = onSelectionChangedListener;
        }

        void onValidDate(Long day) {
            RangeDateSelector.access$002(RangeDateSelector.this, day);
            RangeDateSelector.access$100(RangeDateSelector.this, this.val$startTextInput, this.val$endTextInput, this.val$listener);
        }

        void onInvalidDate() {
            RangeDateSelector.access$002(RangeDateSelector.this, null);
            RangeDateSelector.access$100(RangeDateSelector.this, this.val$startTextInput, this.val$endTextInput, this.val$listener);
        }
    }

    class 2 extends DateFormatTextWatcher {
        final /* synthetic */ TextInputLayout val$endTextInput;
        final /* synthetic */ OnSelectionChangedListener val$listener;
        final /* synthetic */ TextInputLayout val$startTextInput;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(String formatHint, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints constraints, TextInputLayout textInputLayout2, TextInputLayout textInputLayout3, OnSelectionChangedListener onSelectionChangedListener) {
            super(formatHint, dateFormat, textInputLayout, constraints);
            this.val$startTextInput = textInputLayout2;
            this.val$endTextInput = textInputLayout3;
            this.val$listener = onSelectionChangedListener;
        }

        void onValidDate(Long day) {
            RangeDateSelector.access$202(RangeDateSelector.this, day);
            RangeDateSelector.access$100(RangeDateSelector.this, this.val$startTextInput, this.val$endTextInput, this.val$listener);
        }

        void onInvalidDate() {
            RangeDateSelector.access$202(RangeDateSelector.this, null);
            RangeDateSelector.access$100(RangeDateSelector.this, this.val$startTextInput, this.val$endTextInput, this.val$listener);
        }
    }

    private boolean isValidRange(long start, long end) {
        return start <= end;
    }

    private void updateIfValidTextProposal(TextInputLayout startTextInput, TextInputLayout endTextInput, OnSelectionChangedListener onSelectionChangedListener) {
        Long l = this.proposedTextStart;
        if (l == null || this.proposedTextEnd == null) {
            clearInvalidRange(startTextInput, endTextInput);
            onSelectionChangedListener.onIncompleteSelectionChanged();
        } else if (isValidRange(l.longValue(), this.proposedTextEnd.longValue())) {
            this.selectedStartItem = this.proposedTextStart;
            this.selectedEndItem = this.proposedTextEnd;
            onSelectionChangedListener.onSelectionChanged(getSelection());
        } else {
            setInvalidRange(startTextInput, endTextInput);
            onSelectionChangedListener.onIncompleteSelectionChanged();
        }
    }

    private void clearInvalidRange(TextInputLayout start, TextInputLayout end) {
        if (start.getError() != null && this.invalidRangeStartError.contentEquals(start.getError())) {
            start.setError(null);
        }
        if (end.getError() != null && " ".contentEquals(end.getError())) {
            end.setError(null);
        }
    }

    private void setInvalidRange(TextInputLayout start, TextInputLayout end) {
        start.setError(this.invalidRangeStartError);
        end.setError(" ");
    }

    class 3 implements Parcelable.Creator {
        3() {
        }

        public RangeDateSelector createFromParcel(Parcel source) {
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            RangeDateSelector.access$302(rangeDateSelector, (Long) source.readValue(Long.class.getClassLoader()));
            RangeDateSelector.access$402(rangeDateSelector, (Long) source.readValue(Long.class.getClassLoader()));
            return rangeDateSelector;
        }

        public RangeDateSelector[] newArray(int size) {
            return new RangeDateSelector[size];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.selectedStartItem);
        dest.writeValue(this.selectedEndItem);
    }
}
