package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.util.Preconditions;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class CompositeDateValidator implements CalendarConstraints.DateValidator {
    private static final int COMPARATOR_ALL_ID = 2;
    private static final int COMPARATOR_ANY_ID = 1;
    private final Operator operator;
    private final List validators;
    private static final Operator ANY_OPERATOR = new 1();
    private static final Operator ALL_OPERATOR = new 2();
    public static final Parcelable.Creator CREATOR = new 3();

    private interface Operator {
        int getId();

        boolean isValid(List list, long j);
    }

    /* synthetic */ CompositeDateValidator(List x0, Operator x1, 1 x2) {
        this(x0, x1);
    }

    static /* synthetic */ Operator access$000() {
        return ALL_OPERATOR;
    }

    static /* synthetic */ Operator access$100() {
        return ANY_OPERATOR;
    }

    class 1 implements Operator {
        1() {
        }

        public boolean isValid(List list, long date) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                CalendarConstraints.DateValidator validator = (CalendarConstraints.DateValidator) it.next();
                if (validator != null && validator.isValid(date)) {
                    return true;
                }
            }
            return false;
        }

        public int getId() {
            return 1;
        }
    }

    class 2 implements Operator {
        2() {
        }

        public boolean isValid(List list, long date) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                CalendarConstraints.DateValidator validator = (CalendarConstraints.DateValidator) it.next();
                if (validator != null && !validator.isValid(date)) {
                    return false;
                }
            }
            return true;
        }

        public int getId() {
            return 2;
        }
    }

    private CompositeDateValidator(List list, Operator operator) {
        this.validators = list;
        this.operator = operator;
    }

    public static CalendarConstraints.DateValidator allOf(List list) {
        return new CompositeDateValidator(list, ALL_OPERATOR);
    }

    public static CalendarConstraints.DateValidator anyOf(List list) {
        return new CompositeDateValidator(list, ANY_OPERATOR);
    }

    class 3 implements Parcelable.Creator {
        3() {
        }

        public CompositeDateValidator createFromParcel(Parcel source) {
            Operator operator;
            ArrayList readArrayList = source.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader());
            int id = source.readInt();
            if (id == 2) {
                operator = CompositeDateValidator.access$000();
            } else if (id == 1) {
                operator = CompositeDateValidator.access$100();
            } else {
                operator = CompositeDateValidator.access$000();
            }
            return new CompositeDateValidator((List) Preconditions.checkNotNull(readArrayList), operator, null);
        }

        public CompositeDateValidator[] newArray(int size) {
            return new CompositeDateValidator[size];
        }
    }

    public boolean isValid(long date) {
        return this.operator.isValid(this.validators, date);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.validators);
        dest.writeInt(this.operator.getId());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompositeDateValidator)) {
            return false;
        }
        CompositeDateValidator that = (CompositeDateValidator) o;
        return this.validators.equals(that.validators) && this.operator.getId() == that.operator.getId();
    }

    public int hashCode() {
        return this.validators.hashCode();
    }
}
