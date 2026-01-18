package java.util;

import java.util.function.Supplier;
import jdk.internal.util.Preconditions;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Objects {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    private Objects() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static boolean deepEquals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return Arrays.deepEquals0(obj, obj2);
    }

    public static int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static int hash(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static String toString(Object obj) {
        return String.valueOf(obj);
    }

    public static String toString(Object obj, String str) {
        return obj != null ? obj.toString() : str;
    }

    public static int compare(Object obj, Object obj2, Comparator comparator) {
        if (obj == obj2) {
            return 0;
        }
        return comparator.compare(obj, obj2);
    }

    public static Object requireNonNull(Object obj) {
        if (obj != null) {
            return obj;
        }
        throw null;
    }

    public static Object requireNonNull(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    public static Object requireNonNullElse(Object obj, Object obj2) {
        return obj != null ? obj : Objects$$ExternalSyntheticBackport0.m(obj2, "defaultObj");
    }

    public static Object requireNonNullElseGet(Object obj, Supplier supplier) {
        return obj != null ? obj : Objects$$ExternalSyntheticBackport0.m(((Supplier) Objects$$ExternalSyntheticBackport0.m(supplier, "supplier")).get(), "supplier.get()");
    }

    public static Object requireNonNull(Object obj, Supplier supplier) {
        if (obj == null) {
            throw new NullPointerException(supplier == null ? null : (String) supplier.get());
        }
        return obj;
    }

    public static int checkIndex(int i, int i2) {
        return Preconditions.checkIndex(i, i2, null);
    }

    public static int checkFromToIndex(int i, int i2, int i3) {
        return Preconditions.checkFromToIndex(i, i2, i3, null);
    }

    public static int checkFromIndexSize(int i, int i2, int i3) {
        return Preconditions.checkFromIndexSize(i, i2, i3, null);
    }
}
