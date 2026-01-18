package java.util.stream;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.DoublePipeline;
import java.util.stream.IntPipeline;
import java.util.stream.LongPipeline;
import java.util.stream.Node;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class WhileOps {
    static final int TAKE_FLAGS = StreamOpFlag.NOT_SIZED | StreamOpFlag.IS_SHORT_CIRCUIT;
    static final int DROP_FLAGS = StreamOpFlag.NOT_SIZED;

    interface DropWhileOp {
        DropWhileSink opWrapSink(Sink sink, boolean z);
    }

    interface DropWhileSink extends Sink {
        long getDropCount();
    }

    WhileOps() {
    }

    class 1 extends ReferencePipeline.StatefulOp {
        final /* synthetic */ Predicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Predicate predicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = predicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfRef.Taking(pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        class 1 extends Sink.ChainedReference {
            boolean take;

            1(Sink sink) {
                super(sink);
                this.take = true;
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                if (this.take) {
                    boolean test = 1.this.val$predicate.test(obj);
                    this.take = test;
                    if (test) {
                        this.downstream.accept(obj);
                    }
                }
            }

            public boolean cancellationRequested() {
                return !this.take || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    static Stream makeTakeWhileRef(AbstractPipeline abstractPipeline, Predicate predicate) {
        predicate.getClass();
        return new 1(abstractPipeline, StreamShape.REFERENCE, TAKE_FLAGS, predicate);
    }

    class 2 extends IntPipeline.StatefulOp {
        final /* synthetic */ IntPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntPredicate intPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = intPredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$2$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfInt.Taking((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        class 1 extends Sink.ChainedInt {
            boolean take;

            1(Sink sink) {
                super(sink);
                this.take = true;
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(int i) {
                if (this.take) {
                    boolean test = 2.this.val$predicate.test(i);
                    this.take = test;
                    if (test) {
                        this.downstream.accept(i);
                    }
                }
            }

            public boolean cancellationRequested() {
                return !this.take || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    static IntStream makeTakeWhileInt(AbstractPipeline abstractPipeline, IntPredicate intPredicate) {
        intPredicate.getClass();
        return new 2(abstractPipeline, StreamShape.INT_VALUE, TAKE_FLAGS, intPredicate);
    }

    class 3 extends LongPipeline.StatefulOp {
        final /* synthetic */ LongPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongPredicate longPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = longPredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$3$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfLong.Taking((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        class 1 extends Sink.ChainedLong {
            boolean take;

            1(Sink sink) {
                super(sink);
                this.take = true;
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(long j) {
                if (this.take) {
                    boolean test = 3.this.val$predicate.test(j);
                    this.take = test;
                    if (test) {
                        this.downstream.accept(j);
                    }
                }
            }

            public boolean cancellationRequested() {
                return !this.take || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    static LongStream makeTakeWhileLong(AbstractPipeline abstractPipeline, LongPredicate longPredicate) {
        longPredicate.getClass();
        return new 3(abstractPipeline, StreamShape.LONG_VALUE, TAKE_FLAGS, longPredicate);
    }

    class 4 extends DoublePipeline.StatefulOp {
        final /* synthetic */ DoublePredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoublePredicate doublePredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = doublePredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$4$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfDouble.Taking((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        class 1 extends Sink.ChainedDouble {
            boolean take;

            1(Sink sink) {
                super(sink);
                this.take = true;
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(double d) {
                if (this.take) {
                    boolean test = 4.this.val$predicate.test(d);
                    this.take = test;
                    if (test) {
                        this.downstream.accept(d);
                    }
                }
            }

            public boolean cancellationRequested() {
                return !this.take || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    static DoubleStream makeTakeWhileDouble(AbstractPipeline abstractPipeline, DoublePredicate doublePredicate) {
        doublePredicate.getClass();
        return new 4(abstractPipeline, StreamShape.DOUBLE_VALUE, TAKE_FLAGS, doublePredicate);
    }

    static Stream makeDropWhileRef(AbstractPipeline abstractPipeline, Predicate predicate) {
        predicate.getClass();
        return new 1Op(abstractPipeline, StreamShape.REFERENCE, DROP_FLAGS, predicate);
    }

    class 1Op extends ReferencePipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ Predicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public 1Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Predicate predicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = predicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfRef.Dropping(pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        class 1OpSink extends Sink.ChainedReference implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0015  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void accept(java.lang.Object r7) {
                /*
                    r6 = this;
                    boolean r0 = r6.take
                    if (r0 != 0) goto L15
                    java.util.stream.WhileOps$1Op r0 = java.util.stream.WhileOps.1Op.this
                    java.util.function.Predicate r0 = r0.val$predicate
                    boolean r0 = r0.test(r7)
                    r1 = r0 ^ 1
                    r6.take = r1
                    if (r0 != 0) goto L13
                    goto L15
                L13:
                    r0 = 0
                    goto L16
                L15:
                    r0 = 1
                L16:
                    boolean r1 = r6.val$retainAndCountDroppedElements
                    if (r1 == 0) goto L23
                    if (r0 != 0) goto L23
                    long r2 = r6.dropCount
                    r4 = 1
                    long r2 = r2 + r4
                    r6.dropCount = r2
                L23:
                    if (r1 != 0) goto L29
                    if (r0 == 0) goto L28
                    goto L29
                L28:
                    return
                L29:
                    java.util.stream.Sink r0 = r6.downstream
                    r0.accept(r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: java.util.stream.WhileOps.1Op.1OpSink.accept(java.lang.Object):void");
            }

            public long getDropCount() {
                return this.dropCount;
            }
        }

        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new 1OpSink(sink, z);
        }
    }

    static IntStream makeDropWhileInt(AbstractPipeline abstractPipeline, IntPredicate intPredicate) {
        intPredicate.getClass();
        return new 2Op(abstractPipeline, StreamShape.INT_VALUE, DROP_FLAGS, intPredicate);
    }

    class 2Op extends IntPipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ IntPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public 2Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntPredicate intPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = intPredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$2Op$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfInt.Dropping((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        class 1OpSink extends Sink.ChainedInt implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0015  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void accept(int r7) {
                /*
                    r6 = this;
                    boolean r0 = r6.take
                    if (r0 != 0) goto L15
                    java.util.stream.WhileOps$2Op r0 = java.util.stream.WhileOps.2Op.this
                    java.util.function.IntPredicate r0 = r0.val$predicate
                    boolean r0 = r0.test(r7)
                    r1 = r0 ^ 1
                    r6.take = r1
                    if (r0 != 0) goto L13
                    goto L15
                L13:
                    r0 = 0
                    goto L16
                L15:
                    r0 = 1
                L16:
                    boolean r1 = r6.val$retainAndCountDroppedElements
                    if (r1 == 0) goto L23
                    if (r0 != 0) goto L23
                    long r2 = r6.dropCount
                    r4 = 1
                    long r2 = r2 + r4
                    r6.dropCount = r2
                L23:
                    if (r1 != 0) goto L29
                    if (r0 == 0) goto L28
                    goto L29
                L28:
                    return
                L29:
                    java.util.stream.Sink r0 = r6.downstream
                    r0.accept(r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: java.util.stream.WhileOps.2Op.1OpSink.accept(int):void");
            }

            public long getDropCount() {
                return this.dropCount;
            }
        }

        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new 1OpSink(sink, z);
        }
    }

    static LongStream makeDropWhileLong(AbstractPipeline abstractPipeline, LongPredicate longPredicate) {
        longPredicate.getClass();
        return new 3Op(abstractPipeline, StreamShape.LONG_VALUE, DROP_FLAGS, longPredicate);
    }

    class 3Op extends LongPipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ LongPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public 3Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongPredicate longPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = longPredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$3Op$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfLong.Dropping((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        class 1OpSink extends Sink.ChainedLong implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0015  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void accept(long r7) {
                /*
                    r6 = this;
                    boolean r0 = r6.take
                    if (r0 != 0) goto L15
                    java.util.stream.WhileOps$3Op r0 = java.util.stream.WhileOps.3Op.this
                    java.util.function.LongPredicate r0 = r0.val$predicate
                    boolean r0 = r0.test(r7)
                    r1 = r0 ^ 1
                    r6.take = r1
                    if (r0 != 0) goto L13
                    goto L15
                L13:
                    r0 = 0
                    goto L16
                L15:
                    r0 = 1
                L16:
                    boolean r1 = r6.val$retainAndCountDroppedElements
                    if (r1 == 0) goto L23
                    if (r0 != 0) goto L23
                    long r2 = r6.dropCount
                    r4 = 1
                    long r2 = r2 + r4
                    r6.dropCount = r2
                L23:
                    if (r1 != 0) goto L29
                    if (r0 == 0) goto L28
                    goto L29
                L28:
                    return
                L29:
                    java.util.stream.Sink r0 = r6.downstream
                    r0.accept(r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: java.util.stream.WhileOps.3Op.1OpSink.accept(long):void");
            }

            public long getDropCount() {
                return this.dropCount;
            }
        }

        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new 1OpSink(sink, z);
        }
    }

    static DoubleStream makeDropWhileDouble(AbstractPipeline abstractPipeline, DoublePredicate doublePredicate) {
        doublePredicate.getClass();
        return new 4Op(abstractPipeline, StreamShape.DOUBLE_VALUE, DROP_FLAGS, doublePredicate);
    }

    class 4Op extends DoublePipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ DoublePredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public 4Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoublePredicate doublePredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = doublePredicate;
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return opEvaluateParallel(pipelineHelper, spliterator, new WhileOps$4Op$$ExternalSyntheticLambda0()).spliterator();
            }
            return new UnorderedWhileSpliterator.OfDouble.Dropping((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        class 1OpSink extends Sink.ChainedDouble implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0015  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void accept(double r7) {
                /*
                    r6 = this;
                    boolean r0 = r6.take
                    if (r0 != 0) goto L15
                    java.util.stream.WhileOps$4Op r0 = java.util.stream.WhileOps.4Op.this
                    java.util.function.DoublePredicate r0 = r0.val$predicate
                    boolean r0 = r0.test(r7)
                    r1 = r0 ^ 1
                    r6.take = r1
                    if (r0 != 0) goto L13
                    goto L15
                L13:
                    r0 = 0
                    goto L16
                L15:
                    r0 = 1
                L16:
                    boolean r1 = r6.val$retainAndCountDroppedElements
                    if (r1 == 0) goto L23
                    if (r0 != 0) goto L23
                    long r2 = r6.dropCount
                    r4 = 1
                    long r2 = r2 + r4
                    r6.dropCount = r2
                L23:
                    if (r1 != 0) goto L29
                    if (r0 == 0) goto L28
                    goto L29
                L28:
                    return
                L29:
                    java.util.stream.Sink r0 = r6.downstream
                    r0.accept(r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: java.util.stream.WhileOps.4Op.1OpSink.accept(double):void");
            }

            public long getDropCount() {
                return this.dropCount;
            }
        }

        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new 1OpSink(sink, z);
        }
    }

    static abstract class UnorderedWhileSpliterator implements Spliterator {
        static final int CANCEL_CHECK_COUNT = 63;
        final AtomicBoolean cancel;
        int count;
        final boolean noSplitting;
        final Spliterator s;
        boolean takeOrDrop;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public long getExactSizeIfKnown() {
            return -1L;
        }

        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.-CC.$default$hasCharacteristics(this, i);
        }

        abstract Spliterator makeSpliterator(Spliterator spliterator);

        UnorderedWhileSpliterator(Spliterator spliterator, boolean z) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = z;
            this.cancel = new AtomicBoolean();
        }

        UnorderedWhileSpliterator(Spliterator spliterator, UnorderedWhileSpliterator unorderedWhileSpliterator) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = unorderedWhileSpliterator.noSplitting;
            this.cancel = unorderedWhileSpliterator.cancel;
        }

        public long estimateSize() {
            return this.s.estimateSize();
        }

        public int characteristics() {
            return this.s.characteristics() & (-16449);
        }

        public Comparator getComparator() {
            return this.s.getComparator();
        }

        public Spliterator trySplit() {
            Spliterator trySplit = this.noSplitting ? null : this.s.trySplit();
            if (trySplit != null) {
                return makeSpliterator(trySplit);
            }
            return null;
        }

        boolean checkCancelOnCount() {
            return (this.count == 0 && this.cancel.get()) ? false : true;
        }

        static abstract class OfRef extends UnorderedWhileSpliterator implements Consumer {
            final Predicate p;
            Object t;

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.-CC.$default$andThen(this, consumer);
            }

            OfRef(Spliterator spliterator, boolean z, Predicate predicate) {
                super(spliterator, z);
                this.p = predicate;
            }

            OfRef(Spliterator spliterator, OfRef ofRef) {
                super(spliterator, ofRef);
                this.p = ofRef.p;
            }

            public void accept(Object obj) {
                this.count = (this.count + 1) & 63;
                this.t = obj;
            }

            static final class Taking extends OfRef {
                Taking(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                Taking(Spliterator spliterator, Taking taking) {
                    super(spliterator, taking);
                }

                public boolean tryAdvance(Consumer consumer) {
                    boolean z;
                    if (this.takeOrDrop && checkCancelOnCount() && this.s.tryAdvance(this)) {
                        z = this.p.test(this.t);
                        if (z) {
                            consumer.accept(this.t);
                            return true;
                        }
                    } else {
                        z = true;
                    }
                    this.takeOrDrop = false;
                    if (!z) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                public Spliterator trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return super.trySplit();
                }

                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Taking(spliterator, this);
                }
            }

            static final class Dropping extends OfRef {
                Dropping(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                Dropping(Spliterator spliterator, Dropping dropping) {
                    super(spliterator, dropping);
                }

                public boolean tryAdvance(Consumer consumer) {
                    boolean tryAdvance;
                    if (this.takeOrDrop) {
                        boolean z = false;
                        this.takeOrDrop = false;
                        while (true) {
                            tryAdvance = this.s.tryAdvance(this);
                            if (!tryAdvance || !checkCancelOnCount() || !this.p.test(this.t)) {
                                break;
                            }
                            z = true;
                        }
                        if (tryAdvance) {
                            if (z) {
                                this.cancel.set(true);
                            }
                            consumer.accept(this.t);
                        }
                        return tryAdvance;
                    }
                    return this.s.tryAdvance(consumer);
                }

                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Dropping(spliterator, this);
                }
            }
        }

        static abstract class OfInt extends UnorderedWhileSpliterator implements IntConsumer, Spliterator.OfInt {
            final IntPredicate p;
            int t;

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfInt.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                Spliterator.OfInt.-CC.$default$forEachRemaining((Spliterator.OfInt) this, intConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
                return Spliterator.OfInt.-CC.$default$tryAdvance(this, obj);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.-CC.$default$tryAdvance((Spliterator.OfInt) this, consumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfInt(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                super(ofInt, z);
                this.p = intPredicate;
            }

            OfInt(Spliterator.OfInt ofInt, OfInt ofInt2) {
                super(ofInt, ofInt2);
                this.p = ofInt2.p;
            }

            public void accept(int i) {
                this.count = (this.count + 1) & 63;
                this.t = i;
            }

            static final class Taking extends OfInt {
                Taking(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                Taking(Spliterator.OfInt ofInt, OfInt ofInt2) {
                    super(ofInt, ofInt2);
                }

                public boolean tryAdvance(IntConsumer intConsumer) {
                    boolean z;
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfInt) this.s).tryAdvance((IntConsumer) this)) {
                        z = this.p.test(this.t);
                        if (z) {
                            intConsumer.accept(this.t);
                            return true;
                        }
                    } else {
                        z = true;
                    }
                    this.takeOrDrop = false;
                    if (!z) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                public Spliterator.OfInt trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfInt) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                    return new Taking(ofInt, this);
                }
            }

            static final class Dropping extends OfInt {
                public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                    return (Spliterator.OfInt) super.trySplit();
                }

                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfInt ofInt, boolean z, IntPredicate intPredicate) {
                    super(ofInt, z, intPredicate);
                }

                Dropping(Spliterator.OfInt ofInt, OfInt ofInt2) {
                    super(ofInt, ofInt2);
                }

                public boolean tryAdvance(IntConsumer intConsumer) {
                    boolean tryAdvance;
                    if (this.takeOrDrop) {
                        boolean z = false;
                        this.takeOrDrop = false;
                        while (true) {
                            tryAdvance = ((Spliterator.OfInt) this.s).tryAdvance((IntConsumer) this);
                            if (!tryAdvance || !checkCancelOnCount() || !this.p.test(this.t)) {
                                break;
                            }
                            z = true;
                        }
                        if (tryAdvance) {
                            if (z) {
                                this.cancel.set(true);
                            }
                            intConsumer.accept(this.t);
                        }
                        return tryAdvance;
                    }
                    return ((Spliterator.OfInt) this.s).tryAdvance(intConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                    return new Dropping(ofInt, this);
                }
            }
        }

        static abstract class OfLong extends UnorderedWhileSpliterator implements LongConsumer, Spliterator.OfLong {
            final LongPredicate p;
            long t;

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfLong.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                Spliterator.OfLong.-CC.$default$forEachRemaining((Spliterator.OfLong) this, longConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
                return Spliterator.OfLong.-CC.$default$tryAdvance(this, obj);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.-CC.$default$tryAdvance((Spliterator.OfLong) this, consumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfLong(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                super(ofLong, z);
                this.p = longPredicate;
            }

            OfLong(Spliterator.OfLong ofLong, OfLong ofLong2) {
                super(ofLong, ofLong2);
                this.p = ofLong2.p;
            }

            public void accept(long j) {
                this.count = (this.count + 1) & 63;
                this.t = j;
            }

            static final class Taking extends OfLong {
                Taking(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                Taking(Spliterator.OfLong ofLong, OfLong ofLong2) {
                    super(ofLong, ofLong2);
                }

                public boolean tryAdvance(LongConsumer longConsumer) {
                    boolean z;
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfLong) this.s).tryAdvance((LongConsumer) this)) {
                        z = this.p.test(this.t);
                        if (z) {
                            longConsumer.accept(this.t);
                            return true;
                        }
                    } else {
                        z = true;
                    }
                    this.takeOrDrop = false;
                    if (!z) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                public Spliterator.OfLong trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfLong) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                    return new Taking(ofLong, this);
                }
            }

            static final class Dropping extends OfLong {
                public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                    return (Spliterator.OfLong) super.trySplit();
                }

                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfLong ofLong, boolean z, LongPredicate longPredicate) {
                    super(ofLong, z, longPredicate);
                }

                Dropping(Spliterator.OfLong ofLong, OfLong ofLong2) {
                    super(ofLong, ofLong2);
                }

                public boolean tryAdvance(LongConsumer longConsumer) {
                    boolean tryAdvance;
                    if (this.takeOrDrop) {
                        boolean z = false;
                        this.takeOrDrop = false;
                        while (true) {
                            tryAdvance = ((Spliterator.OfLong) this.s).tryAdvance((LongConsumer) this);
                            if (!tryAdvance || !checkCancelOnCount() || !this.p.test(this.t)) {
                                break;
                            }
                            z = true;
                        }
                        if (tryAdvance) {
                            if (z) {
                                this.cancel.set(true);
                            }
                            longConsumer.accept(this.t);
                        }
                        return tryAdvance;
                    }
                    return ((Spliterator.OfLong) this.s).tryAdvance(longConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                    return new Dropping(ofLong, this);
                }
            }
        }

        static abstract class OfDouble extends UnorderedWhileSpliterator implements DoubleConsumer, Spliterator.OfDouble {
            final DoublePredicate p;
            double t;

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            public /* bridge */ /* synthetic */ void forEachRemaining(Object obj) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining(this, obj);
            }

            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, consumer);
            }

            public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                Spliterator.OfDouble.-CC.$default$forEachRemaining((Spliterator.OfDouble) this, doubleConsumer);
            }

            public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
                return Spliterator.OfDouble.-CC.$default$tryAdvance(this, obj);
            }

            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.-CC.$default$tryAdvance((Spliterator.OfDouble) this, consumer);
            }

            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }

            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }

            OfDouble(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                super(ofDouble, z);
                this.p = doublePredicate;
            }

            OfDouble(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
                this.p = ofDouble2.p;
            }

            public void accept(double d) {
                this.count = (this.count + 1) & 63;
                this.t = d;
            }

            static final class Taking extends OfDouble {
                Taking(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                Taking(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                    super(ofDouble, ofDouble2);
                }

                public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                    boolean z;
                    if (this.takeOrDrop && checkCancelOnCount() && ((Spliterator.OfDouble) this.s).tryAdvance((DoubleConsumer) this)) {
                        z = this.p.test(this.t);
                        if (z) {
                            doubleConsumer.accept(this.t);
                            return true;
                        }
                    } else {
                        z = true;
                    }
                    this.takeOrDrop = false;
                    if (!z) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                public Spliterator.OfDouble trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return (Spliterator.OfDouble) super.trySplit();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                    return new Taking(ofDouble, this);
                }
            }

            static final class Dropping extends OfDouble {
                public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                    return (Spliterator.OfDouble) super.trySplit();
                }

                public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                    return (Spliterator.OfPrimitive) super.trySplit();
                }

                Dropping(Spliterator.OfDouble ofDouble, boolean z, DoublePredicate doublePredicate) {
                    super(ofDouble, z, doublePredicate);
                }

                Dropping(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                    super(ofDouble, ofDouble2);
                }

                public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                    boolean tryAdvance;
                    if (this.takeOrDrop) {
                        boolean z = false;
                        this.takeOrDrop = false;
                        while (true) {
                            tryAdvance = ((Spliterator.OfDouble) this.s).tryAdvance((DoubleConsumer) this);
                            if (!tryAdvance || !checkCancelOnCount() || !this.p.test(this.t)) {
                                break;
                            }
                            z = true;
                        }
                        if (tryAdvance) {
                            if (z) {
                                this.cancel.set(true);
                            }
                            doubleConsumer.accept(this.t);
                        }
                        return tryAdvance;
                    }
                    return ((Spliterator.OfDouble) this.s).tryAdvance(doubleConsumer);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                public Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                    return new Dropping(ofDouble, this);
                }
            }
        }
    }

    private static final class TakeWhileTask extends AbstractShortCircuitTask {
        private volatile boolean completed;
        private final IntFunction generator;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private boolean shortCircuited;
        private long thisNodeSize;

        TakeWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        TakeWhileTask(TakeWhileTask takeWhileTask, Spliterator spliterator) {
            super(takeWhileTask, spliterator);
            this.op = takeWhileTask.op;
            this.generator = takeWhileTask.generator;
            this.isOrdered = takeWhileTask.isOrdered;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public TakeWhileTask makeChild(Spliterator spliterator) {
            return new TakeWhileTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final Node getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final Node doLeaf() {
            Node.Builder makeNodeBuilder = this.helper.makeNodeBuilder(-1L, this.generator);
            boolean copyIntoWithCancel = this.helper.copyIntoWithCancel(this.helper.wrapSink(this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder)), this.spliterator);
            this.shortCircuited = copyIntoWithCancel;
            if (copyIntoWithCancel) {
                cancelLaterNodes();
            }
            Node build = makeNodeBuilder.build();
            this.thisNodeSize = build.count();
            return build;
        }

        public final void onCompletion(CountedCompleter countedCompleter) {
            Node merge;
            if (!isLeaf()) {
                this.shortCircuited = ((TakeWhileTask) this.leftChild).shortCircuited | ((TakeWhileTask) this.rightChild).shortCircuited;
                if (this.isOrdered && this.canceled) {
                    this.thisNodeSize = 0L;
                    merge = getEmptyResult();
                } else if (this.isOrdered && ((TakeWhileTask) this.leftChild).shortCircuited) {
                    this.thisNodeSize = ((TakeWhileTask) this.leftChild).thisNodeSize;
                    merge = (Node) ((TakeWhileTask) this.leftChild).getLocalResult();
                } else {
                    this.thisNodeSize = ((TakeWhileTask) this.leftChild).thisNodeSize + ((TakeWhileTask) this.rightChild).thisNodeSize;
                    merge = merge();
                }
                setLocalResult(merge);
            }
            this.completed = true;
            super.onCompletion(countedCompleter);
        }

        Node merge() {
            if (((TakeWhileTask) this.leftChild).thisNodeSize == 0) {
                return (Node) ((TakeWhileTask) this.rightChild).getLocalResult();
            }
            if (((TakeWhileTask) this.rightChild).thisNodeSize == 0) {
                return (Node) ((TakeWhileTask) this.leftChild).getLocalResult();
            }
            return Nodes.conc(this.op.getOutputShape(), (Node) ((TakeWhileTask) this.leftChild).getLocalResult(), (Node) ((TakeWhileTask) this.rightChild).getLocalResult());
        }

        protected void cancel() {
            super.cancel();
            if (this.isOrdered && this.completed) {
                setLocalResult(getEmptyResult());
            }
        }
    }

    private static final class DropWhileTask extends AbstractTask {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final IntFunction generator;
        private long index;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private long thisNodeSize;

        DropWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        DropWhileTask(DropWhileTask dropWhileTask, Spliterator spliterator) {
            super(dropWhileTask, spliterator);
            this.op = dropWhileTask.op;
            this.generator = dropWhileTask.generator;
            this.isOrdered = dropWhileTask.isOrdered;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public DropWhileTask makeChild(Spliterator spliterator) {
            return new DropWhileTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final Node doLeaf() {
            boolean isRoot = isRoot();
            Node.Builder makeNodeBuilder = this.helper.makeNodeBuilder((!isRoot && this.isOrdered && StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags)) ? this.op.exactOutputSizeIfKnown(this.spliterator) : -1L, this.generator);
            DropWhileSink opWrapSink = ((DropWhileOp) this.op).opWrapSink(makeNodeBuilder, this.isOrdered && !isRoot);
            this.helper.wrapAndCopyInto(opWrapSink, this.spliterator);
            Node build = makeNodeBuilder.build();
            this.thisNodeSize = build.count();
            this.index = opWrapSink.getDropCount();
            return build;
        }

        public final void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                if (this.isOrdered) {
                    long j = ((DropWhileTask) this.leftChild).index;
                    this.index = j;
                    if (j == ((DropWhileTask) this.leftChild).thisNodeSize) {
                        this.index += ((DropWhileTask) this.rightChild).index;
                    }
                }
                this.thisNodeSize = ((DropWhileTask) this.leftChild).thisNodeSize + ((DropWhileTask) this.rightChild).thisNodeSize;
                Node merge = merge();
                if (isRoot()) {
                    merge = doTruncate(merge);
                }
                setLocalResult(merge);
            }
            super.onCompletion(countedCompleter);
        }

        private Node merge() {
            if (((DropWhileTask) this.leftChild).thisNodeSize == 0) {
                return (Node) ((DropWhileTask) this.rightChild).getLocalResult();
            }
            if (((DropWhileTask) this.rightChild).thisNodeSize == 0) {
                return (Node) ((DropWhileTask) this.leftChild).getLocalResult();
            }
            return Nodes.conc(this.op.getOutputShape(), (Node) ((DropWhileTask) this.leftChild).getLocalResult(), (Node) ((DropWhileTask) this.rightChild).getLocalResult());
        }

        private Node doTruncate(Node node) {
            return this.isOrdered ? node.truncate(this.index, node.count(), this.generator) : node;
        }
    }
}
