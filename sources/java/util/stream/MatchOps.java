package java.util.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Sink;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class MatchOps {
    private MatchOps() {
    }

    enum MatchKind {
        ANY(true, true),
        ALL(false, false),
        NONE(true, false);

        private final boolean shortCircuitResult;
        private final boolean stopOnPredicateMatches;

        static /* bridge */ /* synthetic */ boolean -$$Nest$fgetshortCircuitResult(MatchKind matchKind) {
            return matchKind.shortCircuitResult;
        }

        static /* bridge */ /* synthetic */ boolean -$$Nest$fgetstopOnPredicateMatches(MatchKind matchKind) {
            return matchKind.stopOnPredicateMatches;
        }

        MatchKind(boolean z, boolean z2) {
            this.stopOnPredicateMatches = z;
            this.shortCircuitResult = z2;
        }
    }

    public static TerminalOp makeRef(Predicate predicate, MatchKind matchKind) {
        predicate.getClass();
        matchKind.getClass();
        return new MatchOp(StreamShape.REFERENCE, matchKind, new MatchOps$$ExternalSyntheticLambda3(matchKind, predicate));
    }

    class 1MatchSink extends BooleanTerminalSink {
        final /* synthetic */ MatchKind val$matchKind;
        final /* synthetic */ Predicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1MatchSink(MatchKind matchKind, Predicate predicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
            this.val$predicate = predicate;
        }

        public void accept(Object obj) {
            if (this.stop || this.val$predicate.test(obj) != MatchKind.-$$Nest$fgetstopOnPredicateMatches(this.val$matchKind)) {
                return;
            }
            this.stop = true;
            this.value = MatchKind.-$$Nest$fgetshortCircuitResult(this.val$matchKind);
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeRef$0(MatchKind matchKind, Predicate predicate) {
        return new 1MatchSink(matchKind, predicate);
    }

    public static TerminalOp makeInt(IntPredicate intPredicate, MatchKind matchKind) {
        intPredicate.getClass();
        matchKind.getClass();
        return new MatchOp(StreamShape.INT_VALUE, matchKind, new MatchOps$$ExternalSyntheticLambda1(matchKind, intPredicate));
    }

    class 2MatchSink extends BooleanTerminalSink implements Sink.OfInt {
        final /* synthetic */ MatchKind val$matchKind;
        final /* synthetic */ IntPredicate val$predicate;

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2MatchSink(MatchKind matchKind, IntPredicate intPredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
            this.val$predicate = intPredicate;
        }

        public void accept(int i) {
            if (this.stop || this.val$predicate.test(i) != MatchKind.-$$Nest$fgetstopOnPredicateMatches(this.val$matchKind)) {
                return;
            }
            this.stop = true;
            this.value = MatchKind.-$$Nest$fgetshortCircuitResult(this.val$matchKind);
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeInt$1(MatchKind matchKind, IntPredicate intPredicate) {
        return new 2MatchSink(matchKind, intPredicate);
    }

    public static TerminalOp makeLong(LongPredicate longPredicate, MatchKind matchKind) {
        longPredicate.getClass();
        matchKind.getClass();
        return new MatchOp(StreamShape.LONG_VALUE, matchKind, new MatchOps$$ExternalSyntheticLambda0(matchKind, longPredicate));
    }

    class 3MatchSink extends BooleanTerminalSink implements Sink.OfLong {
        final /* synthetic */ MatchKind val$matchKind;
        final /* synthetic */ LongPredicate val$predicate;

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3MatchSink(MatchKind matchKind, LongPredicate longPredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
            this.val$predicate = longPredicate;
        }

        public void accept(long j) {
            if (this.stop || this.val$predicate.test(j) != MatchKind.-$$Nest$fgetstopOnPredicateMatches(this.val$matchKind)) {
                return;
            }
            this.stop = true;
            this.value = MatchKind.-$$Nest$fgetshortCircuitResult(this.val$matchKind);
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeLong$2(MatchKind matchKind, LongPredicate longPredicate) {
        return new 3MatchSink(matchKind, longPredicate);
    }

    public static TerminalOp makeDouble(DoublePredicate doublePredicate, MatchKind matchKind) {
        doublePredicate.getClass();
        matchKind.getClass();
        return new MatchOp(StreamShape.DOUBLE_VALUE, matchKind, new MatchOps$$ExternalSyntheticLambda2(matchKind, doublePredicate));
    }

    class 4MatchSink extends BooleanTerminalSink implements Sink.OfDouble {
        final /* synthetic */ MatchKind val$matchKind;
        final /* synthetic */ DoublePredicate val$predicate;

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4MatchSink(MatchKind matchKind, DoublePredicate doublePredicate) {
            super(matchKind);
            this.val$matchKind = matchKind;
            this.val$predicate = doublePredicate;
        }

        public void accept(double d) {
            if (this.stop || this.val$predicate.test(d) != MatchKind.-$$Nest$fgetstopOnPredicateMatches(this.val$matchKind)) {
                return;
            }
            this.stop = true;
            this.value = MatchKind.-$$Nest$fgetshortCircuitResult(this.val$matchKind);
        }
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeDouble$3(MatchKind matchKind, DoublePredicate doublePredicate) {
        return new 4MatchSink(matchKind, doublePredicate);
    }

    private static final class MatchOp implements TerminalOp {
        private final StreamShape inputShape;
        final MatchKind matchKind;
        final Supplier sinkSupplier;

        MatchOp(StreamShape streamShape, MatchKind matchKind, Supplier supplier) {
            this.inputShape = streamShape;
            this.matchKind = matchKind;
            this.sinkSupplier = supplier;
        }

        public int getOpFlags() {
            return StreamOpFlag.IS_SHORT_CIRCUIT | StreamOpFlag.NOT_ORDERED;
        }

        public StreamShape inputShape() {
            return this.inputShape;
        }

        public Boolean evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return Boolean.valueOf(((BooleanTerminalSink) pipelineHelper.wrapAndCopyInto((BooleanTerminalSink) this.sinkSupplier.get(), spliterator)).getAndClearState());
        }

        public Boolean evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return (Boolean) new MatchTask(this, pipelineHelper, spliterator).invoke();
        }
    }

    private static abstract class BooleanTerminalSink implements Sink {
        boolean stop;
        boolean value;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ void begin(long j) {
            Sink.-CC.$default$begin(this, j);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        BooleanTerminalSink(MatchKind matchKind) {
            this.value = !MatchKind.-$$Nest$fgetshortCircuitResult(matchKind);
        }

        public boolean getAndClearState() {
            return this.value;
        }

        public boolean cancellationRequested() {
            return this.stop;
        }
    }

    private static final class MatchTask extends AbstractShortCircuitTask {
        private final MatchOp op;

        MatchTask(MatchOp matchOp, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.op = matchOp;
        }

        MatchTask(MatchTask matchTask, Spliterator spliterator) {
            super(matchTask, spliterator);
            this.op = matchTask.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public MatchTask makeChild(Spliterator spliterator) {
            return new MatchTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doLeaf() {
            boolean andClearState = ((BooleanTerminalSink) this.helper.wrapAndCopyInto((BooleanTerminalSink) this.op.sinkSupplier.get(), this.spliterator)).getAndClearState();
            if (andClearState != MatchKind.-$$Nest$fgetshortCircuitResult(this.op.matchKind)) {
                return null;
            }
            shortCircuit(Boolean.valueOf(andClearState));
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean getEmptyResult() {
            return Boolean.valueOf(!MatchKind.-$$Nest$fgetshortCircuitResult(this.op.matchKind));
        }
    }
}
