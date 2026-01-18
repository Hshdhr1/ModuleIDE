package java.util.concurrent.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarAtomicReference {
    private DesugarAtomicReference() {
    }

    public static Object getAndUpdate(AtomicReference atomicReference, UnaryOperator unaryOperator) {
        Object obj;
        do {
            obj = atomicReference.get();
        } while (!DesugarAtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, obj, unaryOperator.apply(obj)));
        return obj;
    }

    public static Object updateAndGet(AtomicReference atomicReference, UnaryOperator unaryOperator) {
        Object obj;
        Object apply;
        do {
            obj = atomicReference.get();
            apply = unaryOperator.apply(obj);
        } while (!DesugarAtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, obj, apply));
        return apply;
    }

    public static Object getAndAccumulate(AtomicReference atomicReference, Object obj, BinaryOperator binaryOperator) {
        Object obj2;
        do {
            obj2 = atomicReference.get();
        } while (!DesugarAtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, obj2, binaryOperator.apply(obj2, obj)));
        return obj2;
    }

    public static Object accumulateAndGet(AtomicReference atomicReference, Object obj, BinaryOperator binaryOperator) {
        Object obj2;
        Object apply;
        do {
            obj2 = atomicReference.get();
            apply = binaryOperator.apply(obj2, obj);
        } while (!DesugarAtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, obj2, apply));
        return apply;
    }
}
