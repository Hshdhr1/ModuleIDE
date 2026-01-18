package java.util.stream;

import java.util.Iterator;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoublePipeline;
import java.util.stream.IntPipeline;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class LongPipeline extends AbstractPipeline implements LongStream {
    static /* bridge */ /* synthetic */ Spliterator.OfLong -$$Nest$smadapt(Spliterator spliterator) {
        return adapt(spliterator);
    }

    public /* bridge */ /* synthetic */ LongStream parallel() {
        return (LongStream) super.parallel();
    }

    public /* bridge */ /* synthetic */ LongStream sequential() {
        return (LongStream) super.sequential();
    }

    LongPipeline(Supplier supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    LongPipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    LongPipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    private static LongConsumer adapt(Sink sink) {
        if (sink instanceof LongConsumer) {
            return (LongConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Sink<Long> s)");
        }
        sink.getClass();
        return new LongPipeline$$ExternalSyntheticLambda13(sink);
    }

    private static Spliterator.OfLong adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfLong) {
            return (Spliterator.OfLong) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Spliterator<Long> s)");
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    final StreamShape getOutputShape() {
        return StreamShape.LONG_VALUE;
    }

    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectLong(pipelineHelper, spliterator, z);
    }

    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators.LongWrappingSpliterator(pipelineHelper, supplier, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Spliterator.OfLong lazySpliterator(Supplier supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfLong(supplier);
    }

    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean cancellationRequested;
        Spliterator.OfLong adapt = adapt(spliterator);
        LongConsumer adapt2 = adapt(sink);
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (adapt.tryAdvance(adapt2));
        return cancellationRequested;
    }

    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.longBuilder(j);
    }

    class 1 extends ReferencePipeline.StatelessOp {
        final /* synthetic */ LongFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongFunction longFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = longFunction;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                this.downstream.accept(1.this.val$mapper.apply(j));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    private Stream mapToObj(LongFunction longFunction, int i) {
        return new 1(this, StreamShape.LONG_VALUE, i, longFunction);
    }

    public final PrimitiveIterator.OfLong iterator() {
        return Spliterators.iterator(spliterator());
    }

    public final Spliterator.OfLong spliterator() {
        return adapt(super.spliterator());
    }

    class 2 extends DoublePipeline.StatelessOp {
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                this.downstream.accept(j);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream asDoubleStream() {
        return new 2(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_DISTINCT);
    }

    public final Stream boxed() {
        return mapToObj(new LongPipeline$$ExternalSyntheticLambda4(), 0);
    }

    public final LongStream map(LongUnaryOperator longUnaryOperator) {
        longUnaryOperator.getClass();
        return new 3(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longUnaryOperator);
    }

    class 3 extends StatelessOp {
        final /* synthetic */ LongUnaryOperator val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongUnaryOperator longUnaryOperator) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = longUnaryOperator;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                this.downstream.accept(3.this.val$mapper.applyAsLong(j));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream mapToObj(LongFunction longFunction) {
        longFunction.getClass();
        return mapToObj(longFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    public final IntStream mapToInt(LongToIntFunction longToIntFunction) {
        longToIntFunction.getClass();
        return new 4(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longToIntFunction);
    }

    class 4 extends IntPipeline.StatelessOp {
        final /* synthetic */ LongToIntFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongToIntFunction longToIntFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = longToIntFunction;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                this.downstream.accept(4.this.val$mapper.applyAsInt(j));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction) {
        longToDoubleFunction.getClass();
        return new 5(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, longToDoubleFunction);
    }

    class 5 extends DoublePipeline.StatelessOp {
        final /* synthetic */ LongToDoubleFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        5(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongToDoubleFunction longToDoubleFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = longToDoubleFunction;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                this.downstream.accept(5.this.val$mapper.applyAsDouble(j));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream flatMap(LongFunction longFunction) {
        longFunction.getClass();
        return new 6(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, longFunction);
    }

    class 6 extends StatelessOp {
        final /* synthetic */ LongFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        6(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongFunction longFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = longFunction;
        }

        class 1 extends Sink.ChainedLong {
            boolean cancellationRequestedCalled;
            LongConsumer downstreamAsLong;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsLong = new LongPipeline$6$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(long j) {
                LongStream longStream = (LongStream) 6.this.val$mapper.apply(j);
                if (longStream != null) {
                    try {
                        if (!this.cancellationRequestedCalled) {
                            longStream.sequential().forEach(this.downstreamAsLong);
                        } else {
                            Spliterator.OfLong spliterator = longStream.sequential().spliterator();
                            while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstreamAsLong)) {
                            }
                        }
                    } catch (Throwable th) {
                        if (longStream != null) {
                            try {
                                longStream.close();
                            } catch (Throwable th2) {
                                LongPipeline$6$1$$ExternalSyntheticBackport0.m(th, th2);
                            }
                        }
                        throw th;
                    }
                }
                if (longStream != null) {
                    longStream.close();
                }
            }

            public boolean cancellationRequested() {
                this.cancellationRequestedCalled = true;
                return this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public LongStream unordered() {
        return !isOrdered() ? this : new 7(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_ORDERED);
    }

    class 7 extends StatelessOp {
        Sink opWrapSink(int i, Sink sink) {
            return sink;
        }

        7(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }
    }

    public final LongStream filter(LongPredicate longPredicate) {
        longPredicate.getClass();
        return new 8(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SIZED, longPredicate);
    }

    class 8 extends StatelessOp {
        final /* synthetic */ LongPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        8(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongPredicate longPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = longPredicate;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(long j) {
                if (8.this.val$predicate.test(j)) {
                    this.downstream.accept(j);
                }
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream peek(LongConsumer longConsumer) {
        longConsumer.getClass();
        return new 9(this, StreamShape.LONG_VALUE, 0, longConsumer);
    }

    class 9 extends StatelessOp {
        final /* synthetic */ LongConsumer val$action;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        9(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, LongConsumer longConsumer) {
            super(abstractPipeline, streamShape, i);
            this.val$action = longConsumer;
        }

        class 1 extends Sink.ChainedLong {
            1(Sink sink) {
                super(sink);
            }

            public void accept(long j) {
                9.this.val$action.accept(j);
                this.downstream.accept(j);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeLong(this, 0L, j);
    }

    public final LongStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeLong(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final LongStream takeWhile(LongPredicate longPredicate) {
        return WhileOps.makeTakeWhileLong(this, longPredicate);
    }

    public final LongStream dropWhile(LongPredicate longPredicate) {
        return WhileOps.makeDropWhileLong(this, longPredicate);
    }

    public final LongStream sorted() {
        return SortedOps.makeLong(this);
    }

    static /* synthetic */ long lambda$distinct$0(Long l) {
        return l.longValue();
    }

    public final LongStream distinct() {
        return boxed().distinct().mapToLong(new LongPipeline$$ExternalSyntheticLambda12());
    }

    public void forEach(LongConsumer longConsumer) {
        evaluate(ForEachOps.makeLong(longConsumer, false));
    }

    public void forEachOrdered(LongConsumer longConsumer) {
        evaluate(ForEachOps.makeLong(longConsumer, true));
    }

    public final long sum() {
        return reduce(0L, new LongPipeline$$ExternalSyntheticLambda10());
    }

    public final OptionalLong min() {
        return reduce(new LongPipeline$$ExternalSyntheticLambda11());
    }

    public final OptionalLong max() {
        return reduce(new LongPipeline$$ExternalSyntheticLambda9());
    }

    static /* synthetic */ long[] lambda$average$1() {
        return new long[2];
    }

    public final OptionalDouble average() {
        long[] jArr = (long[]) collect(new LongPipeline$$ExternalSyntheticLambda5(), new LongPipeline$$ExternalSyntheticLambda6(), new LongPipeline$$ExternalSyntheticLambda7());
        long j = jArr[0];
        if (j > 0) {
            double d = jArr[1];
            double d2 = j;
            Double.isNaN(d);
            Double.isNaN(d2);
            return OptionalDouble.of(d / d2);
        }
        return OptionalDouble.empty();
    }

    static /* synthetic */ void lambda$average$2(long[] jArr, long j) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + j;
    }

    static /* synthetic */ void lambda$average$3(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    public final long count() {
        return ((Long) evaluate(ReduceOps.makeLongCounting())).longValue();
    }

    public final LongSummaryStatistics summaryStatistics() {
        return (LongSummaryStatistics) collect(new LongPipeline$$ExternalSyntheticLambda0(), new LongPipeline$$ExternalSyntheticLambda1(), new LongPipeline$$ExternalSyntheticLambda2());
    }

    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return ((Long) evaluate(ReduceOps.makeLong(j, longBinaryOperator))).longValue();
    }

    public final OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        return (OptionalLong) evaluate(ReduceOps.makeLong(longBinaryOperator));
    }

    public final Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        biConsumer.getClass();
        return evaluate(ReduceOps.makeLong(supplier, objLongConsumer, new LongPipeline$$ExternalSyntheticLambda8(biConsumer)));
    }

    static /* synthetic */ Object lambda$collect$4(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }

    public final boolean anyMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    public final boolean allMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    public final boolean noneMatch(LongPredicate longPredicate) {
        return ((Boolean) evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    public final OptionalLong findFirst() {
        return (OptionalLong) evaluate(FindOps.makeLong(true));
    }

    public final OptionalLong findAny() {
        return (OptionalLong) evaluate(FindOps.makeLong(false));
    }

    static /* synthetic */ Long[] lambda$toArray$5(int i) {
        return new Long[i];
    }

    public final long[] toArray() {
        return (long[]) Nodes.flattenLong((Node.OfLong) evaluateToArrayNode(new LongPipeline$$ExternalSyntheticLambda3())).asPrimitiveArray();
    }

    static class Head extends LongPipeline {
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }

        Head(Supplier supplier, int i, boolean z) {
            super(supplier, i, z);
        }

        Head(Spliterator spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        final Sink opWrapSink(int i, Sink sink) {
            throw new UnsupportedOperationException();
        }

        public void forEach(LongConsumer longConsumer) {
            if (!isParallel()) {
                LongPipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEach(longConsumer);
            }
        }

        public void forEachOrdered(LongConsumer longConsumer) {
            if (!isParallel()) {
                LongPipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEachOrdered(longConsumer);
            }
        }
    }

    static abstract class StatelessOp extends LongPipeline {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        final boolean opIsStateful() {
            return false;
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }

        StatelessOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }

    static abstract class StatefulOp extends LongPipeline {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        abstract Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction);

        final boolean opIsStateful() {
            return true;
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        public /* bridge */ /* synthetic */ LongStream parallel() {
            return (LongStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ LongStream sequential() {
            return (LongStream) super.sequential();
        }

        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }

        StatefulOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }
}
