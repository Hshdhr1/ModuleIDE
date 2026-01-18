package java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoublePipeline;
import java.util.stream.LongPipeline;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class IntPipeline extends AbstractPipeline implements IntStream {
    static /* bridge */ /* synthetic */ Spliterator.OfInt -$$Nest$smadapt(Spliterator spliterator) {
        return adapt(spliterator);
    }

    public /* bridge */ /* synthetic */ IntStream parallel() {
        return (IntStream) super.parallel();
    }

    public /* bridge */ /* synthetic */ IntStream sequential() {
        return (IntStream) super.sequential();
    }

    IntPipeline(Supplier supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    IntPipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    IntPipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    private static IntConsumer adapt(Sink sink) {
        if (sink instanceof IntConsumer) {
            return (IntConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Sink<Integer> s)");
        }
        sink.getClass();
        return new IntPipeline$$ExternalSyntheticLambda11(sink);
    }

    private static Spliterator.OfInt adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Spliterator<Integer> s)");
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    final StreamShape getOutputShape() {
        return StreamShape.INT_VALUE;
    }

    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectInt(pipelineHelper, spliterator, z);
    }

    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators.IntWrappingSpliterator(pipelineHelper, supplier, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Spliterator.OfInt lazySpliterator(Supplier supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfInt(supplier);
    }

    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean cancellationRequested;
        Spliterator.OfInt adapt = adapt(spliterator);
        IntConsumer adapt2 = adapt(sink);
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (adapt.tryAdvance(adapt2));
        return cancellationRequested;
    }

    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.intBuilder(j);
    }

    class 1 extends ReferencePipeline.StatelessOp {
        final /* synthetic */ IntFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntFunction intFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = intFunction;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(1.this.val$mapper.apply(i));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    private Stream mapToObj(IntFunction intFunction, int i) {
        return new 1(this, StreamShape.INT_VALUE, i, intFunction);
    }

    public final PrimitiveIterator.OfInt iterator() {
        return Spliterators.iterator(spliterator());
    }

    public final Spliterator.OfInt spliterator() {
        return adapt(super.spliterator());
    }

    class 2 extends LongPipeline.StatelessOp {
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(i);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream asLongStream() {
        return new 2(this, StreamShape.INT_VALUE, 0);
    }

    class 3 extends DoublePipeline.StatelessOp {
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(i);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream asDoubleStream() {
        return new 3(this, StreamShape.INT_VALUE, 0);
    }

    public final Stream boxed() {
        return mapToObj(new IntPipeline$$ExternalSyntheticLambda13(), 0);
    }

    public final IntStream map(IntUnaryOperator intUnaryOperator) {
        intUnaryOperator.getClass();
        return new 4(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intUnaryOperator);
    }

    class 4 extends StatelessOp {
        final /* synthetic */ IntUnaryOperator val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntUnaryOperator intUnaryOperator) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = intUnaryOperator;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(4.this.val$mapper.applyAsInt(i));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream mapToObj(IntFunction intFunction) {
        intFunction.getClass();
        return mapToObj(intFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    public final LongStream mapToLong(IntToLongFunction intToLongFunction) {
        intToLongFunction.getClass();
        return new 5(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intToLongFunction);
    }

    class 5 extends LongPipeline.StatelessOp {
        final /* synthetic */ IntToLongFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        5(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntToLongFunction intToLongFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = intToLongFunction;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(5.this.val$mapper.applyAsLong(i));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream mapToDouble(IntToDoubleFunction intToDoubleFunction) {
        intToDoubleFunction.getClass();
        return new 6(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, intToDoubleFunction);
    }

    class 6 extends DoublePipeline.StatelessOp {
        final /* synthetic */ IntToDoubleFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        6(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntToDoubleFunction intToDoubleFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = intToDoubleFunction;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                this.downstream.accept(6.this.val$mapper.applyAsDouble(i));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final IntStream flatMap(IntFunction intFunction) {
        intFunction.getClass();
        return new 7(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, intFunction);
    }

    class 7 extends StatelessOp {
        final /* synthetic */ IntFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        7(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntFunction intFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = intFunction;
        }

        class 1 extends Sink.ChainedInt {
            boolean cancellationRequestedCalled;
            IntConsumer downstreamAsInt;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsInt = new IntPipeline$7$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(int i) {
                IntStream intStream = (IntStream) 7.this.val$mapper.apply(i);
                if (intStream != null) {
                    try {
                        if (!this.cancellationRequestedCalled) {
                            intStream.sequential().forEach(this.downstreamAsInt);
                        } else {
                            Spliterator.OfInt spliterator = intStream.sequential().spliterator();
                            while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstreamAsInt)) {
                            }
                        }
                    } catch (Throwable th) {
                        if (intStream != null) {
                            try {
                                intStream.close();
                            } catch (Throwable th2) {
                                IntPipeline$7$1$$ExternalSyntheticBackport0.m(th, th2);
                            }
                        }
                        throw th;
                    }
                }
                if (intStream != null) {
                    intStream.close();
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

    public IntStream unordered() {
        return !isOrdered() ? this : new 8(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_ORDERED);
    }

    class 8 extends StatelessOp {
        Sink opWrapSink(int i, Sink sink) {
            return sink;
        }

        8(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }
    }

    public final IntStream filter(IntPredicate intPredicate) {
        intPredicate.getClass();
        return new 9(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SIZED, intPredicate);
    }

    class 9 extends StatelessOp {
        final /* synthetic */ IntPredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        9(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntPredicate intPredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = intPredicate;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(int i) {
                if (9.this.val$predicate.test(i)) {
                    this.downstream.accept(i);
                }
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final IntStream peek(IntConsumer intConsumer) {
        intConsumer.getClass();
        return new 10(this, StreamShape.INT_VALUE, 0, intConsumer);
    }

    class 10 extends StatelessOp {
        final /* synthetic */ IntConsumer val$action;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        10(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, IntConsumer intConsumer) {
            super(abstractPipeline, streamShape, i);
            this.val$action = intConsumer;
        }

        class 1 extends Sink.ChainedInt {
            1(Sink sink) {
                super(sink);
            }

            public void accept(int i) {
                10.this.val$action.accept(i);
                this.downstream.accept(i);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final IntStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeInt(this, 0L, j);
    }

    public final IntStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeInt(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final IntStream takeWhile(IntPredicate intPredicate) {
        return WhileOps.makeTakeWhileInt(this, intPredicate);
    }

    public final IntStream dropWhile(IntPredicate intPredicate) {
        return WhileOps.makeDropWhileInt(this, intPredicate);
    }

    public final IntStream sorted() {
        return SortedOps.makeInt(this);
    }

    static /* synthetic */ int lambda$distinct$0(Integer num) {
        return num.intValue();
    }

    public final IntStream distinct() {
        return boxed().distinct().mapToInt(new IntPipeline$$ExternalSyntheticLambda12());
    }

    public void forEach(IntConsumer intConsumer) {
        evaluate(ForEachOps.makeInt(intConsumer, false));
    }

    public void forEachOrdered(IntConsumer intConsumer) {
        evaluate(ForEachOps.makeInt(intConsumer, true));
    }

    public final int sum() {
        return reduce(0, new IntPipeline$$ExternalSyntheticLambda6());
    }

    public final OptionalInt min() {
        return reduce(new IntPipeline$$ExternalSyntheticLambda1());
    }

    public final OptionalInt max() {
        return reduce(new IntPipeline$$ExternalSyntheticLambda7());
    }

    public final long count() {
        return ((Long) evaluate(ReduceOps.makeIntCounting())).longValue();
    }

    static /* synthetic */ long[] lambda$average$1() {
        return new long[2];
    }

    public final OptionalDouble average() {
        long[] jArr = (long[]) collect(new IntPipeline$$ExternalSyntheticLambda8(), new IntPipeline$$ExternalSyntheticLambda9(), new IntPipeline$$ExternalSyntheticLambda10());
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

    static /* synthetic */ void lambda$average$2(long[] jArr, int i) {
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + i;
    }

    static /* synthetic */ void lambda$average$3(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
    }

    public final IntSummaryStatistics summaryStatistics() {
        return (IntSummaryStatistics) collect(new IntPipeline$$ExternalSyntheticLambda2(), new IntPipeline$$ExternalSyntheticLambda3(), new IntPipeline$$ExternalSyntheticLambda4());
    }

    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        return ((Integer) evaluate(ReduceOps.makeInt(i, intBinaryOperator))).intValue();
    }

    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        return (OptionalInt) evaluate(ReduceOps.makeInt(intBinaryOperator));
    }

    public final Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        biConsumer.getClass();
        return evaluate(ReduceOps.makeInt(supplier, objIntConsumer, new IntPipeline$$ExternalSyntheticLambda5(biConsumer)));
    }

    static /* synthetic */ Object lambda$collect$4(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }

    public final boolean anyMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    public final boolean allMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    public final boolean noneMatch(IntPredicate intPredicate) {
        return ((Boolean) evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    public final OptionalInt findFirst() {
        return (OptionalInt) evaluate(FindOps.makeInt(true));
    }

    public final OptionalInt findAny() {
        return (OptionalInt) evaluate(FindOps.makeInt(false));
    }

    static /* synthetic */ Integer[] lambda$toArray$5(int i) {
        return new Integer[i];
    }

    public final int[] toArray() {
        return (int[]) Nodes.flattenInt((Node.OfInt) evaluateToArrayNode(new IntPipeline$$ExternalSyntheticLambda0())).asPrimitiveArray();
    }

    static class Head extends IntPipeline {
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
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

        public void forEach(IntConsumer intConsumer) {
            if (!isParallel()) {
                IntPipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEach(intConsumer);
            }
        }

        public void forEachOrdered(IntConsumer intConsumer) {
            if (!isParallel()) {
                IntPipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEachOrdered(intConsumer);
            }
        }
    }

    static abstract class StatelessOp extends IntPipeline {
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

        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
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

    static abstract class StatefulOp extends IntPipeline {
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

        public /* bridge */ /* synthetic */ IntStream parallel() {
            return (IntStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ IntStream sequential() {
            return (IntStream) super.sequential();
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
