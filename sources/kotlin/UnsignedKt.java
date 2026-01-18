package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: UnsignedJVM.kt */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001f\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u0007\u0010\u0005\u001a\u001f\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0001¢\u0006\u0004\b\n\u0010\u000b\u001a\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0001¢\u0006\u0004\b\r\u0010\u000b\u001a\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u000fH\u0001\u001a\u0018\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u0011H\u0001\u001a\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000fH\u0081\b¢\u0006\u0002\u0010\u0014\u001a\u0011\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000fH\u0081\b\u001a\u0011\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u000fH\u0081\b\u001a\u0016\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0017H\u0081\b¢\u0006\u0002\u0010\u0019\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u000fH\u0001\u001a\u0015\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u001bH\u0001¢\u0006\u0002\u0010\u001d\u001a\u0011\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0011H\u0081\b\u001a\u0016\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0017H\u0081\b¢\u0006\u0002\u0010 \u001a\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u0011H\u0001\u001a\u0015\u0010\"\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u001bH\u0001¢\u0006\u0002\u0010#\u001a\u0011\u0010$\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u000fH\u0081\b\u001a\u0019\u0010$\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u000fH\u0081\b\u001a\u0011\u0010'\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u0011H\u0081\b\u001a\u0018\u0010'\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010&\u001a\u00020\u000fH\u0000¨\u0006("}, d2 = {"uintRemainder", "Lkotlin/UInt;", "v1", "v2", "uintRemainder-J1ME1BU", "(II)I", "uintDivide", "uintDivide-J1ME1BU", "ulongDivide", "Lkotlin/ULong;", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "uintCompare", "", "ulongCompare", "", "uintToULong", "value", "(I)J", "uintToLong", "uintToFloat", "", "floatToUInt", "(F)I", "uintToDouble", "", "doubleToUInt", "(D)I", "ulongToFloat", "floatToULong", "(F)J", "ulongToDouble", "doubleToULong", "(D)J", "uintToString", "", "base", "ulongToString", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
@JvmName(name = "UnsignedKt")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class UnsignedKt {
    @PublishedApi
    @InlineOnly
    private static final long uintToLong(int i) {
        return i & 4294967295L;
    }

    @PublishedApi
    public static final int uintRemainder-J1ME1BU(int i, int i2) {
        return UInt.constructor-impl((int) ((i & 4294967295L) % (i2 & 4294967295L)));
    }

    @PublishedApi
    public static final int uintDivide-J1ME1BU(int i, int i2) {
        return UInt.constructor-impl((int) ((i & 4294967295L) / (i2 & 4294967295L)));
    }

    @PublishedApi
    public static final long ulongDivide-eb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return UnsignedKt$$ExternalSyntheticBackport0.m(j, j2) < 0 ? ULong.constructor-impl(0L) : ULong.constructor-impl(1L);
        }
        if (j >= 0) {
            return ULong.constructor-impl(j / j2);
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return ULong.constructor-impl(j3 + (UnsignedKt$$ExternalSyntheticBackport0.m(ULong.constructor-impl(j - (j3 * j2)), ULong.constructor-impl(j2)) < 0 ? 0 : 1));
    }

    @PublishedApi
    public static final long ulongRemainder-eb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return UnsignedKt$$ExternalSyntheticBackport0.m(j, j2) < 0 ? j : ULong.constructor-impl(j - j2);
        }
        if (j >= 0) {
            return ULong.constructor-impl(j % j2);
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (UnsignedKt$$ExternalSyntheticBackport0.m(ULong.constructor-impl(j3), ULong.constructor-impl(j2)) < 0) {
            j2 = 0;
        }
        return ULong.constructor-impl(j3 - j2);
    }

    @PublishedApi
    public static final int uintCompare(int i, int i2) {
        return Intrinsics.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    @PublishedApi
    public static final int ulongCompare(long j, long j2) {
        return Intrinsics.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE);
    }

    @PublishedApi
    @InlineOnly
    private static final long uintToULong(int i) {
        return ULong.constructor-impl(i & 4294967295L);
    }

    @PublishedApi
    @InlineOnly
    private static final float uintToFloat(int i) {
        return (float) uintToDouble(i);
    }

    @PublishedApi
    @InlineOnly
    private static final int floatToUInt(float f) {
        return doubleToUInt(f);
    }

    @PublishedApi
    public static final double uintToDouble(int i) {
        double d = Integer.MAX_VALUE & i;
        double d2 = (i >>> 31) << 30;
        double d3 = 2;
        Double.isNaN(d2);
        Double.isNaN(d3);
        Double.isNaN(d);
        return d + (d2 * d3);
    }

    @PublishedApi
    public static final int doubleToUInt(double d) {
        if (Double.isNaN(d) || d <= uintToDouble(0)) {
            return 0;
        }
        if (d >= uintToDouble(-1)) {
            return -1;
        }
        if (d <= 2.147483647E9d) {
            return UInt.constructor-impl((int) d);
        }
        double d2 = Integer.MAX_VALUE;
        Double.isNaN(d2);
        return UInt.constructor-impl(UInt.constructor-impl((int) (d - d2)) + UInt.constructor-impl(Integer.MAX_VALUE));
    }

    @PublishedApi
    @InlineOnly
    private static final float ulongToFloat(long j) {
        return (float) ulongToDouble(j);
    }

    @PublishedApi
    @InlineOnly
    private static final long floatToULong(float f) {
        return doubleToULong(f);
    }

    @PublishedApi
    public static final double ulongToDouble(long j) {
        double d = j >>> 11;
        double d2 = 2048;
        Double.isNaN(d);
        Double.isNaN(d2);
        double d3 = j & 2047;
        Double.isNaN(d3);
        return (d * d2) + d3;
    }

    @PublishedApi
    public static final long doubleToULong(double d) {
        if (Double.isNaN(d) || d <= ulongToDouble(0L)) {
            return 0L;
        }
        if (d >= ulongToDouble(-1L)) {
            return -1L;
        }
        if (d < 9.223372036854776E18d) {
            return ULong.constructor-impl((long) d);
        }
        return ULong.constructor-impl(ULong.constructor-impl((long) (d - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    @InlineOnly
    private static final String uintToString(int i) {
        return String.valueOf(i & 4294967295L);
    }

    @InlineOnly
    private static final String uintToString(int i, int i2) {
        return ulongToString(i & 4294967295L, i2);
    }

    @InlineOnly
    private static final String ulongToString(long j) {
        return ulongToString(j, 10);
    }

    @NotNull
    public static final String ulongToString(long j, int i) {
        if (j >= 0) {
            String l = Long.toString(j, CharsKt.checkRadix(i));
            Intrinsics.checkNotNullExpressionValue(l, "toString(...)");
            return l;
        }
        long j2 = i;
        long j3 = ((j >>> 1) / j2) << 1;
        long j4 = j - (j3 * j2);
        if (j4 >= j2) {
            j4 -= j2;
            j3++;
        }
        StringBuilder sb = new StringBuilder();
        String l2 = Long.toString(j3, CharsKt.checkRadix(i));
        Intrinsics.checkNotNullExpressionValue(l2, "toString(...)");
        sb.append(l2);
        String l3 = Long.toString(j4, CharsKt.checkRadix(i));
        Intrinsics.checkNotNullExpressionValue(l3, "toString(...)");
        sb.append(l3);
        return sb.toString();
    }
}
