package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class DateValidatorPointBackward implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator CREATOR = new 1();
    private final long point;

    /* synthetic */ DateValidatorPointBackward(long x0, 1 x1) {
        this(x0);
    }

    private DateValidatorPointBackward(long point) {
        this.point = point;
    }

    public static DateValidatorPointBackward before(long point) {
        return new DateValidatorPointBackward(point);
    }

    public static DateValidatorPointBackward now() {
        return before(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    class 1 implements Parcelable.Creator {
        1() {
        }

        public DateValidatorPointBackward createFromParcel(Parcel source) {
            return new DateValidatorPointBackward(source.readLong(), null);
        }

        public DateValidatorPointBackward[] newArray(int size) {
            return new DateValidatorPointBackward[size];
        }
    }

    public boolean isValid(long date) {
        return date <= this.point;
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
        if (!(o instanceof DateValidatorPointBackward)) {
            return false;
        }
        DateValidatorPointBackward that = (DateValidatorPointBackward) o;
        return this.point == that.point;
    }

    public int hashCode() {
        Object[] hashedFields = {Long.valueOf(this.point)};
        return Arrays.hashCode(hashedFields);
    }
}
