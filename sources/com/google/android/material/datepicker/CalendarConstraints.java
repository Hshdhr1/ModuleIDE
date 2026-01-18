package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class CalendarConstraints implements Parcelable {
    public static final Parcelable.Creator CREATOR = new 1();
    private final Month end;
    private final int monthSpan;
    private Month openAt;
    private final Month start;
    private final DateValidator validator;
    private final int yearSpan;

    public interface DateValidator extends Parcelable {
        boolean isValid(long j);
    }

    /* synthetic */ CalendarConstraints(Month x0, Month x1, DateValidator x2, Month x3, 1 x4) {
        this(x0, x1, x2, x3);
    }

    static /* synthetic */ Month access$100(CalendarConstraints x0) {
        return x0.start;
    }

    static /* synthetic */ Month access$200(CalendarConstraints x0) {
        return x0.end;
    }

    static /* synthetic */ Month access$300(CalendarConstraints x0) {
        return x0.openAt;
    }

    static /* synthetic */ DateValidator access$400(CalendarConstraints x0) {
        return x0.validator;
    }

    private CalendarConstraints(Month start, Month end, DateValidator validator, Month openAt) {
        this.start = start;
        this.end = end;
        this.openAt = openAt;
        this.validator = validator;
        if (openAt != null && start.compareTo(openAt) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        }
        if (openAt != null && openAt.compareTo(end) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
        this.monthSpan = start.monthsUntil(end) + 1;
        this.yearSpan = (end.year - start.year) + 1;
    }

    boolean isWithinBounds(long date) {
        if (this.start.getDay(1) <= date) {
            Month month = this.end;
            if (date <= month.getDay(month.daysInMonth)) {
                return true;
            }
        }
        return false;
    }

    public DateValidator getDateValidator() {
        return this.validator;
    }

    Month getStart() {
        return this.start;
    }

    Month getEnd() {
        return this.end;
    }

    Month getOpenAt() {
        return this.openAt;
    }

    void setOpenAt(Month openAt) {
        this.openAt = openAt;
    }

    int getMonthSpan() {
        return this.monthSpan;
    }

    int getYearSpan() {
        return this.yearSpan;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarConstraints)) {
            return false;
        }
        CalendarConstraints that = (CalendarConstraints) o;
        return this.start.equals(that.start) && this.end.equals(that.end) && ObjectsCompat.equals(this.openAt, that.openAt) && this.validator.equals(that.validator);
    }

    public int hashCode() {
        Object[] hashedFields = {this.start, this.end, this.openAt, this.validator};
        return Arrays.hashCode(hashedFields);
    }

    class 1 implements Parcelable.Creator {
        1() {
        }

        public CalendarConstraints createFromParcel(Parcel source) {
            Month start = (Month) source.readParcelable(Month.class.getClassLoader());
            Month end = (Month) source.readParcelable(Month.class.getClassLoader());
            Month openAt = (Month) source.readParcelable(Month.class.getClassLoader());
            DateValidator validator = (DateValidator) source.readParcelable(DateValidator.class.getClassLoader());
            return new CalendarConstraints(start, end, validator, openAt, null);
        }

        public CalendarConstraints[] newArray(int size) {
            return new CalendarConstraints[size];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.start, 0);
        dest.writeParcelable(this.end, 0);
        dest.writeParcelable(this.openAt, 0);
        dest.writeParcelable(this.validator, 0);
    }

    Month clamp(Month month) {
        if (month.compareTo(this.start) < 0) {
            return this.start;
        }
        if (month.compareTo(this.end) > 0) {
            return this.end;
        }
        return month;
    }

    public static final class Builder {
        private static final String DEEP_COPY_VALIDATOR_KEY = "DEEP_COPY_VALIDATOR_KEY";
        private long end;
        private Long openAt;
        private long start;
        private DateValidator validator;
        static final long DEFAULT_START = UtcDates.canonicalYearMonthDay(Month.create(1900, 0).timeInMillis);
        static final long DEFAULT_END = UtcDates.canonicalYearMonthDay(Month.create(2100, 11).timeInMillis);

        public Builder() {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
        }

        Builder(CalendarConstraints clone) {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
            this.start = CalendarConstraints.access$100(clone).timeInMillis;
            this.end = CalendarConstraints.access$200(clone).timeInMillis;
            this.openAt = Long.valueOf(CalendarConstraints.access$300(clone).timeInMillis);
            this.validator = CalendarConstraints.access$400(clone);
        }

        public Builder setStart(long month) {
            this.start = month;
            return this;
        }

        public Builder setEnd(long month) {
            this.end = month;
            return this;
        }

        public Builder setOpenAt(long month) {
            this.openAt = Long.valueOf(month);
            return this;
        }

        public Builder setValidator(DateValidator validator) {
            this.validator = validator;
            return this;
        }

        public CalendarConstraints build() {
            Bundle deepCopyBundle = new Bundle();
            deepCopyBundle.putParcelable("DEEP_COPY_VALIDATOR_KEY", this.validator);
            Month create = Month.create(this.start);
            Month create2 = Month.create(this.end);
            DateValidator dateValidator = (DateValidator) deepCopyBundle.getParcelable("DEEP_COPY_VALIDATOR_KEY");
            Long l = this.openAt;
            return new CalendarConstraints(create, create2, dateValidator, l == null ? null : Month.create(l.longValue()), null);
        }
    }
}
