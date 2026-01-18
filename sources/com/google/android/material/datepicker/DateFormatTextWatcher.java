package com.google.android.material.datepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
abstract class DateFormatTextWatcher extends TextWatcherAdapter {
    private static final int VALIDATION_DELAY = 1000;
    private final CalendarConstraints constraints;
    private final DateFormat dateFormat;
    private final String outOfRange;
    private final Runnable setErrorCallback;
    private Runnable setRangeErrorCallback;
    private final TextInputLayout textInputLayout;

    abstract void onValidDate(Long l);

    static /* synthetic */ TextInputLayout access$000(DateFormatTextWatcher x0) {
        return x0.textInputLayout;
    }

    static /* synthetic */ DateFormat access$100(DateFormatTextWatcher x0) {
        return x0.dateFormat;
    }

    static /* synthetic */ String access$200(DateFormatTextWatcher x0) {
        return x0.outOfRange;
    }

    DateFormatTextWatcher(String formatHint, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints constraints) {
        this.dateFormat = dateFormat;
        this.textInputLayout = textInputLayout;
        this.constraints = constraints;
        this.outOfRange = textInputLayout.getContext().getString(R.string.mtrl_picker_out_of_range);
        this.setErrorCallback = new 1(formatHint);
    }

    class 1 implements Runnable {
        final /* synthetic */ String val$formatHint;

        1(String str) {
            this.val$formatHint = str;
        }

        public void run() {
            TextInputLayout textLayout = DateFormatTextWatcher.access$000(DateFormatTextWatcher.this);
            DateFormat df = DateFormatTextWatcher.access$100(DateFormatTextWatcher.this);
            Context context = textLayout.getContext();
            String invalidFormat = context.getString(R.string.mtrl_picker_invalid_format);
            String useLine = String.format(context.getString(R.string.mtrl_picker_invalid_format_use), new Object[]{this.val$formatHint});
            String exampleLine = String.format(context.getString(R.string.mtrl_picker_invalid_format_example), new Object[]{df.format(new Date(UtcDates.getTodayCalendar().getTimeInMillis()))});
            textLayout.setError(invalidFormat + "\n" + useLine + "\n" + exampleLine);
            DateFormatTextWatcher.this.onInvalidDate();
        }
    }

    void onInvalidDate() {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.textInputLayout.removeCallbacks(this.setErrorCallback);
        this.textInputLayout.removeCallbacks(this.setRangeErrorCallback);
        this.textInputLayout.setError(null);
        onValidDate(null);
        if (TextUtils.isEmpty(s)) {
            return;
        }
        try {
            Date date = this.dateFormat.parse(s.toString());
            this.textInputLayout.setError(null);
            long milliseconds = date.getTime();
            if (this.constraints.getDateValidator().isValid(milliseconds) && this.constraints.isWithinBounds(milliseconds)) {
                onValidDate(Long.valueOf(date.getTime()));
                return;
            }
            Runnable createRangeErrorCallback = createRangeErrorCallback(milliseconds);
            this.setRangeErrorCallback = createRangeErrorCallback;
            runValidation(this.textInputLayout, createRangeErrorCallback);
        } catch (ParseException e) {
            runValidation(this.textInputLayout, this.setErrorCallback);
        }
    }

    class 2 implements Runnable {
        final /* synthetic */ long val$milliseconds;

        2(long j) {
            this.val$milliseconds = j;
        }

        public void run() {
            DateFormatTextWatcher.access$000(DateFormatTextWatcher.this).setError(String.format(DateFormatTextWatcher.access$200(DateFormatTextWatcher.this), new Object[]{DateStrings.getDateString(this.val$milliseconds)}));
            DateFormatTextWatcher.this.onInvalidDate();
        }
    }

    private Runnable createRangeErrorCallback(long milliseconds) {
        return new 2(milliseconds);
    }

    public void runValidation(View view, Runnable validation) {
        view.postDelayed(validation, 1000L);
    }
}
