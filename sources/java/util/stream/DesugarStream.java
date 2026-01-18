package java.util.stream;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarStream {
    private DesugarStream() {
    }

    public static Stream iterate(Object obj, Predicate predicate, UnaryOperator unaryOperator) {
        unaryOperator.getClass();
        predicate.getClass();
        return StreamSupport.stream(new 1(Long.MAX_VALUE, 1040, unaryOperator, obj, predicate), false);
    }

    class 1 extends Spliterators.AbstractSpliterator {
        boolean finished;
        Object prev;
        boolean started;
        final /* synthetic */ Predicate val$hasNext;
        final /* synthetic */ UnaryOperator val$next;
        final /* synthetic */ Object val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(long j, int i, UnaryOperator unaryOperator, Object obj, Predicate predicate) {
            super(j, i);
            this.val$next = unaryOperator;
            this.val$seed = obj;
            this.val$hasNext = predicate;
        }

        public boolean tryAdvance(Consumer consumer) {
            Object obj;
            consumer.getClass();
            if (this.finished) {
                return false;
            }
            if (this.started) {
                obj = this.val$next.apply(this.prev);
            } else {
                obj = this.val$seed;
                this.started = true;
            }
            if (!this.val$hasNext.test(obj)) {
                this.prev = null;
                this.finished = true;
                return false;
            }
            this.prev = obj;
            consumer.accept(obj);
            return true;
        }

        public void forEachRemaining(Consumer consumer) {
            consumer.getClass();
            if (this.finished) {
                return;
            }
            this.finished = true;
            Object apply = this.started ? this.val$next.apply(this.prev) : this.val$seed;
            this.prev = null;
            while (this.val$hasNext.test(apply)) {
                consumer.accept(apply);
                apply = this.val$next.apply(apply);
            }
        }
    }
}
