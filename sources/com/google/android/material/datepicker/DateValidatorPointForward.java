package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class DateValidatorPointForward implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator CREATOR = new 1();
    private final long point;

    /* synthetic */ DateValidatorPointForward(long x0, 1 x1) {
        this(x0);
    }

    private DateValidatorPointForward(long point) {
        this.point = point;
    }

    public static DateValidatorPointForward from(long point) {
        return new DateValidatorPointForward(point);
    }

    public static DateValidatorPointForward now() {
        return from(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    class 1 implements Parcelable.Creator {
        1() {
        }

        public DateValidatorPointForward createFromParcel(Parcel source) {
            return new DateValidatorPointForward(source.readLong(), null);
        }

        public DateValidatorPointForward[] newArray(int size) {
            return new DateValidatorPointForward[size];
        }
    }

    public boolean isValid(long date) {
        return date >= this.point;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.point);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DateValidatorPointForward)) {
            return false;
        }
        DateValidatorPointForward that = (DateValidatorPointForward) o;
        return this.point == that.point;
    }

    public int hashCode() {
        Object[] hashedFields = {Long.valueOf(this.point)};
        return Arrays.hashCode(hashedFields);
    }
}
