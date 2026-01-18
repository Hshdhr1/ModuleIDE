package kotlin.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;

/* compiled from: UProgressionUtil.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0005\u0010\u0006\u001a'\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\b\u0010\t\u001a'\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001¢\u0006\u0004\b\u000f\u0010\u0006\u001a'\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001¢\u0006\u0004\b\u0011\u0010\t¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class UProgressionUtilKt {
    private static final int differenceModulo-WZ9TVnA(int i, int i2, int i3) {
        int m = UProgressionUtilKt$$ExternalSyntheticBackport0.m(i, i3);
        int m2 = UProgressionUtilKt$$ExternalSyntheticBackport0.m(i2, i3);
        int m3 = UProgressionUtilKt$$ExternalSyntheticBackport1.m(m, m2);
        int i4 = UInt.constructor-impl(m - m2);
        return m3 >= 0 ? i4 : UInt.constructor-impl(i4 + i3);
    }

    private static final long differenceModulo-sambcqE(long j, long j2, long j3) {
        long m = UProgressionUtilKt$$ExternalSyntheticBackport3.m(j, j3);
        long m2 = UProgressionUtilKt$$ExternalSyntheticBackport3.m(j2, j3);
        int m3 = UProgressionUtilKt$$ExternalSyntheticBackport4.m(m, m2);
        long j4 = ULong.constructor-impl(m - m2);
        return m3 >= 0 ? j4 : ULong.constructor-impl(j4 + j3);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final int getProgressionLastElement-Nkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            if (UProgressionUtilKt$$ExternalSyntheticBackport5.m(i, i2) < 0) {
                return UInt.constructor-impl(i2 - differenceModulo-WZ9TVnA(i2, i, UInt.constructor-impl(i3)));
            }
        } else if (i3 < 0) {
            if (UProgressionUtilKt$$ExternalSyntheticBackport6.m(i, i2) > 0) {
                return UInt.constructor-impl(i2 + differenceModulo-WZ9TVnA(i, i2, UInt.constructor-impl(-i3)));
            }
        } else {
            throw new IllegalArgumentException("Step is zero.");
        }
        return i2;
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final long getProgressionLastElement-7ftBX0g(long j, long j2, long j3) {
        if (j3 > 0) {
            return UProgressionUtilKt$$ExternalSyntheticBackport4.m(j, j2) >= 0 ? j2 : ULong.constructor-impl(j2 - differenceModulo-sambcqE(j2, j, ULong.constructor-impl(j3)));
        }
        if (j3 < 0) {
            return UProgressionUtilKt$$ExternalSyntheticBackport4.m(j, j2) <= 0 ? j2 : ULong.constructor-impl(j2 + differenceModulo-sambcqE(j, j2, ULong.constructor-impl(-j3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
