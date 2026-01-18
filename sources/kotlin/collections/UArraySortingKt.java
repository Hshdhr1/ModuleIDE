package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\n\u0010\u000b\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\r\u0010\u000e\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0012\u0010\u0013\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u000b\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0010\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0015\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort-Aa5vz7o", "([SII)V", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "quickSort-oBK06Vg", "([III)V", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "quickSort--nroSd4", "([JII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-Aa5vz7o", "sortArray-oBK06Vg", "sortArray--nroSd4", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class UArraySortingKt {
    @ExperimentalUnsignedTypes
    private static final int partition-4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte b = UByteArray.get-w2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = b & 255;
                if (Intrinsics.compare(UByteArray.get-w2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.get-w2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte b2 = UByteArray.get-w2LRezQ(bArr, i);
                UByteArray.set-VurrAj0(bArr, i, UByteArray.get-w2LRezQ(bArr, i2));
                UByteArray.set-VurrAj0(bArr, i2, b2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    private static final void quickSort-4UcCI2c(byte[] bArr, int i, int i2) {
        int i3 = partition-4UcCI2c(bArr, i, i2);
        int i4 = i3 - 1;
        if (i < i4) {
            quickSort-4UcCI2c(bArr, i, i4);
        }
        if (i3 < i2) {
            quickSort-4UcCI2c(bArr, i3, i2);
        }
    }

    @ExperimentalUnsignedTypes
    private static final int partition-Aa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short s = UShortArray.get-Mh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = s & 65535;
                if (Intrinsics.compare(UShortArray.get-Mh2AYeg(sArr, i) & 65535, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.get-Mh2AYeg(sArr, i2) & 65535, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short s2 = UShortArray.get-Mh2AYeg(sArr, i);
                UShortArray.set-01HTLdE(sArr, i, UShortArray.get-Mh2AYeg(sArr, i2));
                UShortArray.set-01HTLdE(sArr, i2, s2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    private static final void quickSort-Aa5vz7o(short[] sArr, int i, int i2) {
        int i3 = partition-Aa5vz7o(sArr, i, i2);
        int i4 = i3 - 1;
        if (i < i4) {
            quickSort-Aa5vz7o(sArr, i, i4);
        }
        if (i3 < i2) {
            quickSort-Aa5vz7o(sArr, i3, i2);
        }
    }

    @ExperimentalUnsignedTypes
    private static final int partition-oBK06Vg(int[] iArr, int i, int i2) {
        int i3 = UIntArray.get-pVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UArraySortingKt$$ExternalSyntheticBackport0.m(UIntArray.get-pVg5ArA(iArr, i), i3) < 0) {
                i++;
            }
            while (UArraySortingKt$$ExternalSyntheticBackport1.m(UIntArray.get-pVg5ArA(iArr, i2), i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                int i4 = UIntArray.get-pVg5ArA(iArr, i);
                UIntArray.set-VXSXFK8(iArr, i, UIntArray.get-pVg5ArA(iArr, i2));
                UIntArray.set-VXSXFK8(iArr, i2, i4);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    private static final void quickSort-oBK06Vg(int[] iArr, int i, int i2) {
        int i3 = partition-oBK06Vg(iArr, i, i2);
        int i4 = i3 - 1;
        if (i < i4) {
            quickSort-oBK06Vg(iArr, i, i4);
        }
        if (i3 < i2) {
            quickSort-oBK06Vg(iArr, i3, i2);
        }
    }

    @ExperimentalUnsignedTypes
    private static final int partition--nroSd4(long[] jArr, int i, int i2) {
        long j = ULongArray.get-s-VKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UArraySortingKt$$ExternalSyntheticBackport3.m(ULongArray.get-s-VKNKU(jArr, i), j) < 0) {
                i++;
            }
            while (UArraySortingKt$$ExternalSyntheticBackport3.m(ULongArray.get-s-VKNKU(jArr, i2), j) > 0) {
                i2--;
            }
            if (i <= i2) {
                long j2 = ULongArray.get-s-VKNKU(jArr, i);
                ULongArray.set-k8EXiF4(jArr, i, ULongArray.get-s-VKNKU(jArr, i2));
                ULongArray.set-k8EXiF4(jArr, i2, j2);
                i++;
                i2--;
            }
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    private static final void quickSort--nroSd4(long[] jArr, int i, int i2) {
        int i3 = partition--nroSd4(jArr, i, i2);
        int i4 = i3 - 1;
        if (i < i4) {
            quickSort--nroSd4(jArr, i, i4);
        }
        if (i3 < i2) {
            quickSort--nroSd4(jArr, i3, i2);
        }
    }

    @ExperimentalUnsignedTypes
    public static final void sortArray-4UcCI2c(@NotNull byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        quickSort-4UcCI2c(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    public static final void sortArray-Aa5vz7o(@NotNull short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        quickSort-Aa5vz7o(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    public static final void sortArray-oBK06Vg(@NotNull int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        quickSort-oBK06Vg(array, i, i2 - 1);
    }

    @ExperimentalUnsignedTypes
    public static final void sortArray--nroSd4(@NotNull long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        quickSort--nroSd4(array, i, i2 - 1);
    }
}
