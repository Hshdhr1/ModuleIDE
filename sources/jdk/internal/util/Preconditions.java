package jdk.internal.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Preconditions {
    static /* bridge */ /* synthetic */ String -$$Nest$smoutOfBoundsMessage(String str, List list) {
        return outOfBoundsMessage(str, list);
    }

    private static RuntimeException outOfBounds(BiFunction biFunction, String str, Integer... numArr) {
        List m = Preconditions$$ExternalSyntheticBackport0.m(numArr);
        RuntimeException runtimeException = biFunction == null ? null : (RuntimeException) biFunction.apply(str, m);
        return runtimeException == null ? new IndexOutOfBoundsException(outOfBoundsMessage(str, m)) : runtimeException;
    }

    private static RuntimeException outOfBoundsCheckIndex(BiFunction biFunction, int i, int i2) {
        return outOfBounds(biFunction, "checkIndex", Integer.valueOf(i), Integer.valueOf(i2));
    }

    private static RuntimeException outOfBoundsCheckFromToIndex(BiFunction biFunction, int i, int i2, int i3) {
        return outOfBounds(biFunction, "checkFromToIndex", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    private static RuntimeException outOfBoundsCheckFromIndexSize(BiFunction biFunction, int i, int i2, int i3) {
        return outOfBounds(biFunction, "checkFromIndexSize", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    class 1 implements BiFunction {
        final /* synthetic */ Function val$f;

        public /* synthetic */ BiFunction andThen(Function function) {
            return BiFunction.-CC.$default$andThen(this, function);
        }

        1(Function function) {
            this.val$f = function;
        }

        public RuntimeException apply(String str, List list) {
            return (RuntimeException) this.val$f.apply(Preconditions.-$$Nest$smoutOfBoundsMessage(str, list));
        }
    }

    public static BiFunction outOfBoundsExceptionFormatter(Function function) {
        return new 1(function);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String outOfBoundsMessage(java.lang.String r10, java.util.List r11) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.internal.util.Preconditions.outOfBoundsMessage(java.lang.String, java.util.List):java.lang.String");
    }

    public static int checkIndex(int i, int i2, BiFunction biFunction) {
        if (i < 0 || i >= i2) {
            throw outOfBoundsCheckIndex(biFunction, i, i2);
        }
        return i;
    }

    public static int checkFromToIndex(int i, int i2, int i3, BiFunction biFunction) {
        if (i < 0 || i > i2 || i2 > i3) {
            throw outOfBoundsCheckFromToIndex(biFunction, i, i2, i3);
        }
        return i;
    }

    public static int checkFromIndexSize(int i, int i2, int i3, BiFunction biFunction) {
        if ((i3 | i | i2) < 0 || i2 > i3 - i) {
            throw outOfBoundsCheckFromIndexSize(biFunction, i, i2, i3);
        }
        return i;
    }
}
