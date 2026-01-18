package java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.IntPipeline;
import java.util.stream.LongPipeline;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class DoublePipeline extends AbstractPipeline implements DoubleStream {
    static /* bridge */ /* synthetic */ Spliterator.OfDouble -$$Nest$smadapt(Spliterator spliterator) {
        return adapt(spliterator);
    }

    public /* bridge */ /* synthetic */ DoubleStream parallel() {
        return (DoubleStream) super.parallel();
    }

    public /* bridge */ /* synthetic */ DoubleStream sequential() {
        return (DoubleStream) super.sequential();
    }

    DoublePipeline(Supplier supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    DoublePipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    DoublePipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    private static DoubleConsumer adapt(Sink sink) {
        if (sink instanceof DoubleConsumer) {
            return (DoubleConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Sink<Double> s)");
        }
        sink.getClass();
        return new DoublePipeline$$ExternalSyntheticLambda0(sink);
    }

    private static Spliterator.OfDouble adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfDouble) {
            return (Spliterator.OfDouble) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Spliterator<Double> s)");
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    final StreamShape getOutputShape() {
        return StreamShape.DOUBLE_VALUE;
    }

    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectDouble(pipelineHelper, spliterator, z);
    }

    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators.DoubleWrappingSpliterator(pipelineHelper, supplier, z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Spliterator.OfDouble lazySpliterator(Supplier supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfDouble(supplier);
    }

    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean cancellationRequested;
        Spliterator.OfDouble adapt = adapt(spliterator);
        DoubleConsumer adapt2 = adapt(sink);
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (adapt.tryAdvance(adapt2));
        return cancellationRequested;
    }

    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.doubleBuilder(j);
    }

    class 1 extends ReferencePipeline.StatelessOp {
        final /* synthetic */ DoubleFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleFunction doubleFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = doubleFunction;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void accept(double d) {
                this.downstream.accept(1.this.val$mapper.apply(d));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    private Stream mapToObj(DoubleFunction doubleFunction, int i) {
        return new 1(this, StreamShape.DOUBLE_VALUE, i, doubleFunction);
    }

    public final PrimitiveIterator.OfDouble iterator() {
        return Spliterators.iterator(spliterator());
    }

    public final Spliterator.OfDouble spliterator() {
        return adapt(super.spliterator());
    }

    public final Stream boxed() {
        return mapToObj(new DoublePipeline$$ExternalSyntheticLambda4(), 0);
    }

    public final DoubleStream map(DoubleUnaryOperator doubleUnaryOperator) {
        doubleUnaryOperator.getClass();
        return new 2(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleUnaryOperator);
    }

    class 2 extends StatelessOp {
        final /* synthetic */ DoubleUnaryOperator val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleUnaryOperator doubleUnaryOperator) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = doubleUnaryOperator;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void accept(double d) {
                this.downstream.accept(2.this.val$mapper.applyAsDouble(d));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream mapToObj(DoubleFunction doubleFunction) {
        doubleFunction.getClass();
        return mapToObj(doubleFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    public final IntStream mapToInt(DoubleToIntFunction doubleToIntFunction) {
        doubleToIntFunction.getClass();
        return new 3(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleToIntFunction);
    }

    class 3 extends IntPipeline.StatelessOp {
        final /* synthetic */ DoubleToIntFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleToIntFunction doubleToIntFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = doubleToIntFunction;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void accept(double d) {
                this.downstream.accept(3.this.val$mapper.applyAsInt(d));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream mapToLong(DoubleToLongFunction doubleToLongFunction) {
        doubleToLongFunction.getClass();
        return new 4(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleToLongFunction);
    }

    class 4 extends LongPipeline.StatelessOp {
        final /* synthetic */ DoubleToLongFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleToLongFunction doubleToLongFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = doubleToLongFunction;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void accept(double d) {
                this.downstream.accept(4.this.val$mapper.applyAsLong(d));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream flatMap(DoubleFunction doubleFunction) {
        doubleFunction.getClass();
        return new 5(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, doubleFunction);
    }

    class 5 extends StatelessOp {
        final /* synthetic */ DoubleFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        5(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleFunction doubleFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = doubleFunction;
        }

        class 1 extends Sink.ChainedDouble {
            boolean cancellationRequestedCalled;
            DoubleConsumer downstreamAsDouble;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsDouble = new DoublePipeline$5$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(double d) {
                DoubleStream doubleStream = (DoubleStream) 5.this.val$mapper.apply(d);
                if (doubleStream != null) {
                    try {
                        if (!this.cancellationRequestedCalled) {
                            doubleStream.sequential().forEach(this.downstreamAsDouble);
                        } else {
                            Spliterator.OfDouble spliterator = doubleStream.sequential().spliterator();
                            while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstreamAsDouble)) {
                            }
                        }
                    } catch (Throwable th) {
                        if (doubleStream != null) {
                            try {
                                doubleStream.close();
                            } catch (Throwable th2) {
                                DoublePipeline$5$1$$ExternalSyntheticBackport0.m(th, th2);
                            }
                        }
                        throw th;
                    }
                }
                if (doubleStream != null) {
                    doubleStream.close();
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

    public DoubleStream unordered() {
        return !isOrdered() ? this : new 6(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_ORDERED);
    }

    class 6 extends StatelessOp {
        Sink opWrapSink(int i, Sink sink) {
            return sink;
        }

        6(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }
    }

    public final DoubleStream filter(DoublePredicate doublePredicate) {
        doublePredicate.getClass();
        return new 7(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SIZED, doublePredicate);
    }

    class 7 extends StatelessOp {
        final /* synthetic */ DoublePredicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        7(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoublePredicate doublePredicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = doublePredicate;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(double d) {
                if (7.this.val$predicate.test(d)) {
                    this.downstream.accept(d);
                }
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream peek(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        return new 8(this, StreamShape.DOUBLE_VALUE, 0, doubleConsumer);
    }

    class 8 extends StatelessOp {
        final /* synthetic */ DoubleConsumer val$action;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        8(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, DoubleConsumer doubleConsumer) {
            super(abstractPipeline, streamShape, i);
            this.val$action = doubleConsumer;
        }

        class 1 extends Sink.ChainedDouble {
            1(Sink sink) {
                super(sink);
            }

            public void accept(double d) {
                8.this.val$action.accept(d);
                this.downstream.accept(d);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeDouble(this, 0L, j);
    }

    public final DoubleStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeDouble(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final DoubleStream takeWhile(DoublePredicate doublePredicate) {
        return WhileOps.makeTakeWhileDouble(this, doublePredicate);
    }

    public final DoubleStream dropWhile(DoublePredicate doublePredicate) {
        return WhileOps.makeDropWhileDouble(this, doublePredicate);
    }

    public final DoubleStream sorted() {
        return SortedOps.makeDouble(this);
    }

    static /* synthetic */ double lambda$distinct$0(Double d) {
        return d.doubleValue();
    }

    public final DoubleStream distinct() {
        return boxed().distinct().mapToDouble(new DoublePipeline$$ExternalSyntheticLambda5());
    }

    public void forEach(DoubleConsumer doubleConsumer) {
        evaluate(ForEachOps.makeDouble(doubleConsumer, false));
    }

    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        evaluate(ForEachOps.makeDouble(doubleConsumer, true));
    }

    static /* synthetic */ double[] lambda$sum$1() {
        return new double[3];
    }

    public final double sum() {
        return Collectors.computeFinalSum((double[]) collect(new DoublePipeline$$ExternalSyntheticLambda9(), new DoublePipeline$$ExternalSyntheticLambda10(), new DoublePipeline$$ExternalSyntheticLambda11()));
    }

    static /* synthetic */ void lambda$sum$2(double[] dArr, double d) {
        Collectors.sumWithCompensation(dArr, d);
        dArr[2] = dArr[2] + d;
    }

    static /* synthetic */ void lambda$sum$3(double[] dArr, double[] dArr2) {
        Collectors.sumWithCompensation(dArr, dArr2[0]);
        Collectors.sumWithCompensation(dArr, dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
    }

    public final OptionalDouble min() {
        return reduce(new DoublePipeline$$ExternalSyntheticLambda12());
    }

    public final OptionalDouble max() {
        return reduce(new DoublePipeline$$ExternalSyntheticLambda8());
    }

    static /* synthetic */ double[] lambda$average$4() {
        return new double[4];
    }

    public final OptionalDouble average() {
        double[] dArr = (double[]) collect(new DoublePipeline$$ExternalSyntheticLambda13(), new DoublePipeline$$ExternalSyntheticLambda14(), new DoublePipeline$$ExternalSyntheticLambda15());
        if (dArr[2] > 0.0d) {
            return OptionalDouble.of(Collectors.computeFinalSum(dArr) / dArr[2]);
        }
        return OptionalDouble.empty();
    }

    static /* synthetic */ void lambda$average$5(double[] dArr, double d) {
        dArr[2] = dArr[2] + 1.0d;
        Collectors.sumWithCompensation(dArr, d);
        dArr[3] = dArr[3] + d;
    }

    static /* synthetic */ void lambda$average$6(double[] dArr, double[] dArr2) {
        Collectors.sumWithCompensation(dArr, dArr2[0]);
        Collectors.sumWithCompensation(dArr, dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
        dArr[3] = dArr[3] + dArr2[3];
    }

    public final long count() {
        return ((Long) evaluate(ReduceOps.makeDoubleCounting())).longValue();
    }

    public final DoubleSummaryStatistics summaryStatistics() {
        return (DoubleSummaryStatistics) collect(new DoublePipeline$$ExternalSyntheticLambda1(), new DoublePipeline$$ExternalSyntheticLambda2(), new DoublePipeline$$ExternalSyntheticLambda3());
    }

    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return ((Double) evaluate(ReduceOps.makeDouble(d, doubleBinaryOperator))).doubleValue();
    }

    public final OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return (OptionalDouble) evaluate(ReduceOps.makeDouble(doubleBinaryOperator));
    }

    public final Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        biConsumer.getClass();
        return evaluate(ReduceOps.makeDouble(supplier, objDoubleConsumer, new DoublePipeline$$ExternalSyntheticLambda7(biConsumer)));
    }

    static /* synthetic */ Object lambda$collect$7(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        return obj;
    }

    public final boolean anyMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    public final boolean allMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    public final boolean noneMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    public final OptionalDouble findFirst() {
        return (OptionalDouble) evaluate(FindOps.makeDouble(true));
    }

    public final OptionalDouble findAny() {
        return (OptionalDouble) evaluate(FindOps.makeDouble(false));
    }

    static /* synthetic */ Double[] lambda$toArray$8(int i) {
        return new Double[i];
    }

    public final double[] toArray() {
        return (double[]) Nodes.flattenDouble((Node.OfDouble) evaluateToArrayNode(new DoublePipeline$$ExternalSyntheticLambda6())).asPrimitiveArray();
    }

    static class Head extends DoublePipeline {
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
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

        public void forEach(DoubleConsumer doubleConsumer) {
            if (!isParallel()) {
                DoublePipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            } else {
                super.forEach(doubleConsumer);
            }
        }

        public void forEachOrdered(DoubleConsumer doubleConsumer) {
            if (!isParallel()) {
                DoublePipeline.-$$Nest$smadapt(sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            } else {
                super.forEachOrdered(doubleConsumer);
            }
        }
    }

    static abstract class StatelessOp extends DoublePipeline {
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

        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
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

    static abstract class StatefulOp extends DoublePipeline {
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

        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
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
