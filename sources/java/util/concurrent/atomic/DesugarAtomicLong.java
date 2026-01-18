package java.util.concurrent.atomic;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarAtomicLong {
    private DesugarAtomicLong() {
    }

    public static long getAndUpdate(AtomicLong atomicLong, LongUnaryOperator longUnaryOperator) {
        long j;
        do {
            j = atomicLong.get();
        } while (!atomicLong.compareAndSet(j, longUnaryOperator.applyAsLong(j)));
        return j;
    }

    public static long updateAndGet(AtomicLong atomicLong, LongUnaryOperator longUnaryOperator) {
        long j;
        long applyAsLong;
        do {
            j = atomicLong.get();
            applyAsLong = longUnaryOperator.applyAsLong(j);
        } while (!atomicLong.compareAndSet(j, applyAsLong));
        return applyAsLong;
    }

    public static long getAndAccumulate(AtomicLong atomicLong, long j, LongBinaryOperator longBinaryOperator) {
        long j2;
        do {
            j2 = atomicLong.get();
        } while (!atomicLong.compareAndSet(j2, longBinaryOperator.applyAsLong(j2, j)));
        return j2;
    }

    public static long accumulateAndGet(AtomicLong atomicLong, long j, LongBinaryOperator longBinaryOperator) {
        long j2;
        long applyAsLong;
        do {
            j2 = atomicLong.get();
            applyAsLong = longBinaryOperator.applyAsLong(j2, j);
        } while (!atomicLong.compareAndSet(j2, applyAsLong));
        return applyAsLong;
    }
}
