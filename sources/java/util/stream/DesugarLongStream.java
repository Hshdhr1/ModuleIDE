package java.util.stream;

import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarLongStream {
    private DesugarLongStream() {
    }

    public static LongStream iterate(long j, LongPredicate longPredicate, LongUnaryOperator longUnaryOperator) {
        longUnaryOperator.getClass();
        longPredicate.getClass();
        return StreamSupport.longStream(new 1(Long.MAX_VALUE, 1296, longUnaryOperator, j, longPredicate), false);
    }

    class 1 extends Spliterators.AbstractLongSpliterator {
        boolean finished;
        long prev;
        boolean started;
        final /* synthetic */ LongPredicate val$hasNext;
        final /* synthetic */ LongUnaryOperator val$next;
        final /* synthetic */ long val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, LongUnaryOperator longUnaryOperator, long j2, LongPredicate longPredicate) {
            super(j, i);
            this.val$next = longUnaryOperator;
            this.val$seed = j2;
            this.val$hasNext = longPredicate;
        }

        public boolean tryAdvance(LongConsumer longConsumer) {
            long j;
            longConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                j = this.val$next.applyAsLong(this.prev);
            } else {
                j = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(j)) {
                this.finished = true;
                return false;
            }
            this.prev = j;
            longConsumer.accept(j);
            return true;
        }

        public void forEachRemaining(LongConsumer longConsumer) {
            longConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            long applyAsLong = this.started ? this.val$next.applyAsLong(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsLong)) {
                longConsumer.accept(applyAsLong);
                applyAsLong = this.val$next.applyAsLong(applyAsLong);
            }
        }
    }
}
