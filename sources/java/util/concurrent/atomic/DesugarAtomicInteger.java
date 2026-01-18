package java.util.concurrent.atomic;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarAtomicInteger {
    private DesugarAtomicInteger() {
    }

    public static int getAndUpdate(AtomicInteger atomicInteger, IntUnaryOperator intUnaryOperator) {
        int i;
        do {
            i = atomicInteger.get();
        } while (!atomicInteger.compareAndSet(i, intUnaryOperator.applyAsInt(i)));
        return i;
    }

    public static int updateAndGet(AtomicInteger atomicInteger, IntUnaryOperator intUnaryOperator) {
        int i;
        int applyAsInt;
        do {
            i = atomicInteger.get();
            applyAsInt = intUnaryOperator.applyAsInt(i);
        } while (!atomicInteger.compareAndSet(i, applyAsInt));
        return applyAsInt;
    }

    public static int getAndAccumulate(AtomicInteger atomicInteger, int i, IntBinaryOperator intBinaryOperator) {
        int i2;
        do {
            i2 = atomicInteger.get();
        } while (!atomicInteger.compareAndSet(i2, intBinaryOperator.applyAsInt(i2, i)));
        return i2;
    }

    public static int accumulateAndGet(AtomicInteger atomicInteger, int i, IntBinaryOperator intBinaryOperator) {
        int i2;
        int applyAsInt;
        do {
            i2 = atomicInteger.get();
            applyAsInt = intBinaryOperator.applyAsInt(i2, i);
        } while (!atomicInteger.compareAndSet(i2, applyAsInt));
        return applyAsInt;
    }
}
