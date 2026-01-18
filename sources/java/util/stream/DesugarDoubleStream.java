package java.util.stream;

import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarDoubleStream {
    private DesugarDoubleStream() {
    }

    public static DoubleStream iterate(double d, DoublePredicate doublePredicate, DoubleUnaryOperator doubleUnaryOperator) {
        doubleUnaryOperator.getClass();
        doublePredicate.getClass();
        return StreamSupport.doubleStream(new 1(Long.MAX_VALUE, 1296, doubleUnaryOperator, d, doublePredicate), false);
    }

    class 1 extends Spliterators.AbstractDoubleSpliterator {
        boolean finished;
        double prev;
        boolean started;
        final /* synthetic */ DoublePredicate val$hasNext;
        final /* synthetic */ DoubleUnaryOperator val$next;
        final /* synthetic */ double val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, DoubleUnaryOperator doubleUnaryOperator, double d, DoublePredicate doublePredicate) {
            super(j, i);
            this.val$next = doubleUnaryOperator;
            this.val$seed = d;
            this.val$hasNext = doublePredicate;
        }

        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            double d;
            doubleConsumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                d = this.val$next.applyAsDouble(this.prev);
            } else {
                d = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(d)) {
                this.finished = true;
                return false;
            }
            this.prev = d;
            doubleConsumer.accept(d);
            return true;
        }

        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            doubleConsumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            double applyAsDouble = this.started ? this.val$next.applyAsDouble(this.prev) : this.val$seed;
            while (this.val$hasNext.test(applyAsDouble)) {
                doubleConsumer.accept(applyAsDouble);
                applyAsDouble = this.val$next.applyAsDouble(applyAsDouble);
            }
        }
    }
}
