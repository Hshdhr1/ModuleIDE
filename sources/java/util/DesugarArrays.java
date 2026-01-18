package java.util;

import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarArrays {
    private DesugarArrays() {
    }

    static boolean deepEquals0(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("e1 is null!");
        }
        if ((obj instanceof Object[]) && (obj2 instanceof Object[])) {
            return Arrays.deepEquals((Object[]) obj, (Object[]) obj2);
        }
        if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
            return Arrays.equals((byte[]) obj, (byte[]) obj2);
        }
        if ((obj instanceof short[]) && (obj2 instanceof short[])) {
            return Arrays.equals((short[]) obj, (short[]) obj2);
        }
        if ((obj instanceof int[]) && (obj2 instanceof int[])) {
            return Arrays.equals((int[]) obj, (int[]) obj2);
        }
        if ((obj instanceof long[]) && (obj2 instanceof long[])) {
            return Arrays.equals((long[]) obj, (long[]) obj2);
        }
        if ((obj instanceof char[]) && (obj2 instanceof char[])) {
            return Arrays.equals((char[]) obj, (char[]) obj2);
        }
        if ((obj instanceof float[]) && (obj2 instanceof float[])) {
            return Arrays.equals((float[]) obj, (float[]) obj2);
        }
        if ((obj instanceof double[]) && (obj2 instanceof double[])) {
            return Arrays.equals((double[]) obj, (double[]) obj2);
        }
        if ((obj instanceof boolean[]) && (obj2 instanceof boolean[])) {
            return Arrays.equals((boolean[]) obj, (boolean[]) obj2);
        }
        return obj.equals(obj2);
    }

    public static void setAll(Object[] objArr, IntFunction intFunction) {
        intFunction.getClass();
        for (int i = 0; i < objArr.length; i++) {
            objArr[i] = intFunction.apply(i);
        }
    }

    public static void parallelSetAll(Object[] objArr, IntFunction intFunction) {
        intFunction.getClass();
        IntStream.-CC.range(0, objArr.length).parallel().forEach(new DesugarArrays$$ExternalSyntheticLambda0(objArr, intFunction));
    }

    static /* synthetic */ void lambda$parallelSetAll$0(Object[] objArr, IntFunction intFunction, int i) {
        objArr[i] = intFunction.apply(i);
    }

    public static void setAll(int[] iArr, IntUnaryOperator intUnaryOperator) {
        intUnaryOperator.getClass();
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = intUnaryOperator.applyAsInt(i);
        }
    }

    public static void parallelSetAll(int[] iArr, IntUnaryOperator intUnaryOperator) {
        intUnaryOperator.getClass();
        IntStream.-CC.range(0, iArr.length).parallel().forEach(new DesugarArrays$$ExternalSyntheticLambda3(iArr, intUnaryOperator));
    }

    static /* synthetic */ void lambda$parallelSetAll$1(int[] iArr, IntUnaryOperator intUnaryOperator, int i) {
        iArr[i] = intUnaryOperator.applyAsInt(i);
    }

    public static void setAll(long[] jArr, IntToLongFunction intToLongFunction) {
        intToLongFunction.getClass();
        for (int i = 0; i < jArr.length; i++) {
            jArr[i] = intToLongFunction.applyAsLong(i);
        }
    }

    public static void parallelSetAll(long[] jArr, IntToLongFunction intToLongFunction) {
        intToLongFunction.getClass();
        IntStream.-CC.range(0, jArr.length).parallel().forEach(new DesugarArrays$$ExternalSyntheticLambda2(jArr, intToLongFunction));
    }

    static /* synthetic */ void lambda$parallelSetAll$2(long[] jArr, IntToLongFunction intToLongFunction, int i) {
        jArr[i] = intToLongFunction.applyAsLong(i);
    }

    public static void setAll(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        intToDoubleFunction.getClass();
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = intToDoubleFunction.applyAsDouble(i);
        }
    }

    public static void parallelSetAll(double[] dArr, IntToDoubleFunction intToDoubleFunction) {
        intToDoubleFunction.getClass();
        IntStream.-CC.range(0, dArr.length).parallel().forEach(new DesugarArrays$$ExternalSyntheticLambda1(dArr, intToDoubleFunction));
    }

    static /* synthetic */ void lambda$parallelSetAll$3(double[] dArr, IntToDoubleFunction intToDoubleFunction, int i) {
        dArr[i] = intToDoubleFunction.applyAsDouble(i);
    }

    public static Spliterator spliterator(Object[] objArr) {
        return Spliterators.spliterator(objArr, 1040);
    }

    public static Spliterator spliterator(Object[] objArr, int i, int i2) {
        return Spliterators.spliterator(objArr, i, i2, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] iArr) {
        return Spliterators.spliterator(iArr, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2) {
        return Spliterators.spliterator(iArr, i, i2, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] jArr) {
        return Spliterators.spliterator(jArr, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] jArr, int i, int i2) {
        return Spliterators.spliterator(jArr, i, i2, 1040);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr) {
        return Spliterators.spliterator(dArr, 1040);
    }

    public static Spliterator.OfDouble spliterator(double[] dArr, int i, int i2) {
        return Spliterators.spliterator(dArr, i, i2, 1040);
    }

    public static Stream stream(Object[] objArr) {
        return Arrays.stream(objArr, 0, objArr.length);
    }

    public static Stream stream(Object[] objArr, int i, int i2) {
        return StreamSupport.stream(Arrays.spliterator(objArr, i, i2), false);
    }

    public static IntStream stream(int[] iArr) {
        return Arrays.stream(iArr, 0, iArr.length);
    }

    public static IntStream stream(int[] iArr, int i, int i2) {
        return StreamSupport.intStream(Arrays.spliterator(iArr, i, i2), false);
    }

    public static LongStream stream(long[] jArr) {
        return Arrays.stream(jArr, 0, jArr.length);
    }

    public static LongStream stream(long[] jArr, int i, int i2) {
        return StreamSupport.longStream(Arrays.spliterator(jArr, i, i2), false);
    }

    public static DoubleStream stream(double[] dArr) {
        return Arrays.stream(dArr, 0, dArr.length);
    }

    public static DoubleStream stream(double[] dArr, int i, int i2) {
        return StreamSupport.doubleStream(Arrays.spliterator(dArr, i, i2), false);
    }
}
