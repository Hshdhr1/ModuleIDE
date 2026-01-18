package java.util.stream;

import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarIntStream {
    private DesugarIntStream() {
    }

    public static IntStream iterate(int i, IntPredicate intPredicate, IntUnaryOperator intUnaryOperator) {
        intUnaryOperator.getClass();
        intPredicate.getClass();
        return StreamSupport.intStream(new 1(Long.MAX_VALUE, 1296, intUnaryOperator, i, intPredicate), false);
    }

    class 1 extends Spliterators.AbstractIntSpliterator {
        boolean finished;
        int prev;
        boolean started;
        final /* synthetic */ IntPredicate val$hasNext;
        final /* synthetic */ IntUnaryOperator val$next;
        final /* synthetic */ int val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, IntUnaryOperator intUnaryOperator, int i2, IntPredicate intPredicate) {
            super(j, i);
            this.val$next = intUnaryOperator;
            this.val$seed = i2;
            this.val$hasNext = intPredicate;
        }

        public boolean tryAdvance(IntConsumer intConsumer) {
            int i;
            intConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                i = this.val$next.applyAsInt(this.prev);
            } else {
                i = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(i)) {
                this.finished = true;
                return false;
            }
            this.prev = i;
            intConsumer.accept(i);
            return true;
        }

        public void forEachRemaining(IntConsumer intConsumer) {
            intConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            int applyAsInt = this.started ? this.val$next.applyAsInt(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsInt)) {
                intConsumer.accept(applyAsInt);
                applyAsInt = this.val$next.applyAsInt(applyAsInt);
            }
        }
    }
}
