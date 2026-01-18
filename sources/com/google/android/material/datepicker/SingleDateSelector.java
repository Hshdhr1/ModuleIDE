package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class SingleDateSelector implements DateSelector {
    public static final Parcelable.Creator CREATOR = new 2();
    private Long selectedItem;

    static /* synthetic */ void access$000(SingleDateSelector x0) {
        x0.clearSelection();
    }

    static /* synthetic */ Long access$102(SingleDateSelector x0, Long x1) {
        x0.selectedItem = x1;
        return x1;
    }

    public void select(long selection) {
        this.selectedItem = Long.valueOf(selection);
    }

    private void clearSelection() {
        this.selectedItem = null;
    }

    public void setSelection(Long selection) {
        this.selectedItem = selection == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(selection.longValue()));
    }

    public boolean isSelectionComplete() {
        return this.selectedItem != null;
    }

    public Collection getSelectedRanges() {
        return new ArrayList();
    }

    public Collection getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        Long l = this.selectedItem;
        if (l != null) {
            selections.add(l);
        }
        return selections;
    }

    public Long getSelection() {
        return this.selectedItem;
    }

    public View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle, CalendarConstraints constraints, OnSelectionChangedListener onSelectionChangedListener) {
        View root = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date, viewGroup, false);
        TextInputLayout dateTextInput = root.findViewById(R.id.mtrl_picker_text_input_date);
        EditText dateEditText = dateTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            dateEditText.setInputType(17);
        }
        SimpleDateFormat format = UtcDates.getTextInputFormat();
        String formatHint = UtcDates.getTextInputHint(root.getResources(), format);
        dateTextInput.setPlaceholderText(formatHint);
        Long l = this.selectedItem;
        if (l != null) {
            dateEditText.setText(format.format(l));
        }
        dateEditText.addTextChangedListener(new 1(formatHint, format, dateTextInput, constraints, onSelectionChangedListener));
        ViewUtils.requestFocusAndShowKeyboard(dateEditText);
        return root;
    }

    class 1 extends DateFormatTextWatcher {
        final /* synthetic */ OnSelectionChangedListener val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(String formatHint, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints constraints, OnSelectionChangedListener onSelectionChangedListener) {
            super(formatHint, dateFormat, textInputLayout, constraints);
            this.val$listener = onSelectionChangedListener;
        }

        void onValidDate(Long day) {
            if (day == null) {
                SingleDateSelector.access$000(SingleDateSelector.this);
            } else {
                SingleDateSelector.this.select(day.longValue());
            }
            this.val$listener.onSelectionChanged(SingleDateSelector.this.getSelection());
        }

        void onInvalidDate() {
            this.val$listener.onIncompleteSelectionChanged();
        }
    }

    public int getDefaultThemeResId(Context context) {
        return MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
    }

    public String getSelectionDisplayString(Context context) {
        Resources res = context.getResources();
        Long l = this.selectedItem;
        if (l == null) {
            return res.getString(R.string.mtrl_picker_date_header_unselected);
        }
        String startString = DateStrings.getYearMonthDay(l.longValue());
        return res.getString(R.string.mtrl_picker_date_header_selected, new Object[]{startString});
    }

    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_date_header_title;
    }

    class 2 implements Parcelable.Creator {
        2() {
        }

        public SingleDateSelector createFromParcel(Parcel source) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            SingleDateSelector.access$102(singleDateSelector, (Long) source.readValue(Long.class.getClassLoader()));
            return singleDateSelector;
        }

        public SingleDateSelector[] newArray(int size) {
            return new SingleDateSelector[size];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.selectedItem);
    }
}
