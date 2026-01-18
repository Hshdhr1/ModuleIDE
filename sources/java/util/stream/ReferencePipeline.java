package java.util.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoublePipeline;
import java.util.stream.IntPipeline;
import java.util.stream.LongPipeline;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class ReferencePipeline extends AbstractPipeline implements Stream {
    ReferencePipeline(Supplier supplier, int i, boolean z) {
        super(supplier, i, z);
    }

    ReferencePipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    ReferencePipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    final StreamShape getOutputShape() {
        return StreamShape.REFERENCE;
    }

    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collect(pipelineHelper, spliterator, z, intFunction);
    }

    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators.WrappingSpliterator(pipelineHelper, supplier, z);
    }

    final Spliterator lazySpliterator(Supplier supplier) {
        return new StreamSpliterators.DelegatingSpliterator(supplier);
    }

    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean cancellationRequested;
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (spliterator.tryAdvance(sink));
        return cancellationRequested;
    }

    final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.builder(j, intFunction);
    }

    public final Iterator iterator() {
        return Spliterators.iterator(spliterator());
    }

    public Stream unordered() {
        return !isOrdered() ? this : new 1(this, StreamShape.REFERENCE, StreamOpFlag.NOT_ORDERED);
    }

    class 1 extends StatelessOp {
        Sink opWrapSink(int i, Sink sink) {
            return sink;
        }

        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }
    }

    public final Stream filter(Predicate predicate) {
        predicate.getClass();
        return new 2(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SIZED, predicate);
    }

    class 2 extends StatelessOp {
        final /* synthetic */ Predicate val$predicate;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Predicate predicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = predicate;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                if (2.this.val$predicate.test(obj)) {
                    this.downstream.accept(obj);
                }
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream map(Function function) {
        function.getClass();
        return new 3(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, function);
    }

    class 3 extends StatelessOp {
        final /* synthetic */ Function val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Function function) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = function;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void accept(Object obj) {
                this.downstream.accept(3.this.val$mapper.apply(obj));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final IntStream mapToInt(ToIntFunction toIntFunction) {
        toIntFunction.getClass();
        return new 4(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, toIntFunction);
    }

    class 4 extends IntPipeline.StatelessOp {
        final /* synthetic */ ToIntFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, ToIntFunction toIntFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = toIntFunction;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void accept(Object obj) {
                this.downstream.accept(4.this.val$mapper.applyAsInt(obj));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final LongStream mapToLong(ToLongFunction toLongFunction) {
        toLongFunction.getClass();
        return new 5(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, toLongFunction);
    }

    class 5 extends LongPipeline.StatelessOp {
        final /* synthetic */ ToLongFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        5(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, ToLongFunction toLongFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = toLongFunction;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void accept(Object obj) {
                this.downstream.accept(5.this.val$mapper.applyAsLong(obj));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final DoubleStream mapToDouble(ToDoubleFunction toDoubleFunction) {
        toDoubleFunction.getClass();
        return new 6(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, toDoubleFunction);
    }

    class 6 extends DoublePipeline.StatelessOp {
        final /* synthetic */ ToDoubleFunction val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        6(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, ToDoubleFunction toDoubleFunction) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = toDoubleFunction;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void accept(Object obj) {
                this.downstream.accept(6.this.val$mapper.applyAsDouble(obj));
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream flatMap(Function function) {
        function.getClass();
        return new 7(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, function);
    }

    class 7 extends StatelessOp {
        final /* synthetic */ Function val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        7(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Function function) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = function;
        }

        class 1 extends Sink.ChainedReference {
            boolean cancellationRequestedCalled;

            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                Stream stream = (Stream) 7.this.val$mapper.apply(obj);
                if (stream != null) {
                    try {
                        if (!this.cancellationRequestedCalled) {
                            ((Stream) stream.sequential()).forEach(this.downstream);
                        } else {
                            Spliterator spliterator = ((Stream) stream.sequential()).spliterator();
                            while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstream)) {
                            }
                        }
                    } catch (Throwable th) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable th2) {
                                ReferencePipeline$7$1$$ExternalSyntheticBackport0.m(th, th2);
                            }
                        }
                        throw th;
                    }
                }
                if (stream != null) {
                    stream.close();
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

    public final IntStream flatMapToInt(Function function) {
        function.getClass();
        return new 8(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, function);
    }

    class 8 extends IntPipeline.StatelessOp {
        final /* synthetic */ Function val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        8(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Function function) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = function;
        }

        class 1 extends Sink.ChainedReference {
            boolean cancellationRequestedCalled;
            IntConsumer downstreamAsInt;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsInt = new ReferencePipeline$8$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                IntStream intStream = (IntStream) 8.this.val$mapper.apply(obj);
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
                                ReferencePipeline$8$1$$ExternalSyntheticBackport0.m(th, th2);
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

    public final DoubleStream flatMapToDouble(Function function) {
        function.getClass();
        return new 9(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, function);
    }

    class 9 extends DoublePipeline.StatelessOp {
        final /* synthetic */ Function val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        9(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Function function) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = function;
        }

        class 1 extends Sink.ChainedReference {
            boolean cancellationRequestedCalled;
            DoubleConsumer downstreamAsDouble;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsDouble = new ReferencePipeline$9$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                DoubleStream doubleStream = (DoubleStream) 9.this.val$mapper.apply(obj);
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
                                ReferencePipeline$9$1$$ExternalSyntheticBackport0.m(th, th2);
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

    public final LongStream flatMapToLong(Function function) {
        function.getClass();
        return new 10(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED, function);
    }

    class 10 extends LongPipeline.StatelessOp {
        final /* synthetic */ Function val$mapper;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        10(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Function function) {
            super(abstractPipeline, streamShape, i);
            this.val$mapper = function;
        }

        class 1 extends Sink.ChainedReference {
            boolean cancellationRequestedCalled;
            LongConsumer downstreamAsLong;

            1(Sink sink) {
                super(sink);
                Sink sink2 = this.downstream;
                sink2.getClass();
                this.downstreamAsLong = new ReferencePipeline$10$1$$ExternalSyntheticLambda1(sink2);
            }

            public void begin(long j) {
                this.downstream.begin(-1L);
            }

            public void accept(Object obj) {
                LongStream longStream = (LongStream) 10.this.val$mapper.apply(obj);
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
                                ReferencePipeline$10$1$$ExternalSyntheticBackport0.m(th, th2);
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

    public final Stream peek(Consumer consumer) {
        consumer.getClass();
        return new 11(this, StreamShape.REFERENCE, 0, consumer);
    }

    class 11 extends StatelessOp {
        final /* synthetic */ Consumer val$action;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        11(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Consumer consumer) {
            super(abstractPipeline, streamShape, i);
            this.val$action = consumer;
        }

        class 1 extends Sink.ChainedReference {
            1(Sink sink) {
                super(sink);
            }

            public void accept(Object obj) {
                11.this.val$action.accept(obj);
                this.downstream.accept(obj);
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public final Stream distinct() {
        return DistinctOps.makeRef(this);
    }

    public final Stream sorted() {
        return SortedOps.makeRef(this);
    }

    public final Stream sorted(Comparator comparator) {
        return SortedOps.makeRef(this, comparator);
    }

    public final Stream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return SliceOps.makeRef(this, 0L, j);
    }

    public final Stream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : SliceOps.makeRef(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final Stream takeWhile(Predicate predicate) {
        return WhileOps.makeTakeWhileRef(this, predicate);
    }

    public final Stream dropWhile(Predicate predicate) {
        return WhileOps.makeDropWhileRef(this, predicate);
    }

    public void forEach(Consumer consumer) {
        evaluate(ForEachOps.makeRef(consumer, false));
    }

    public void forEachOrdered(Consumer consumer) {
        evaluate(ForEachOps.makeRef(consumer, true));
    }

    public final Object[] toArray(IntFunction intFunction) {
        return Nodes.flatten(evaluateToArrayNode(intFunction), intFunction).asArray(intFunction);
    }

    static /* synthetic */ Object[] lambda$toArray$0(int i) {
        return new Object[i];
    }

    public final Object[] toArray() {
        return toArray(new ReferencePipeline$$ExternalSyntheticLambda1());
    }

    public final boolean anyMatch(Predicate predicate) {
        return ((Boolean) evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    public final boolean allMatch(Predicate predicate) {
        return ((Boolean) evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    public final boolean noneMatch(Predicate predicate) {
        return ((Boolean) evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    public final Optional findFirst() {
        return (Optional) evaluate(FindOps.makeRef(true));
    }

    public final Optional findAny() {
        return (Optional) evaluate(FindOps.makeRef(false));
    }

    public final Object reduce(Object obj, BinaryOperator binaryOperator) {
        return evaluate(ReduceOps.makeRef(obj, binaryOperator, binaryOperator));
    }

    public final Optional reduce(BinaryOperator binaryOperator) {
        return (Optional) evaluate(ReduceOps.makeRef(binaryOperator));
    }

    public final Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        return evaluate(ReduceOps.makeRef(obj, biFunction, binaryOperator));
    }

    public final Object collect(Collector collector) {
        Object evaluate;
        if (isParallel() && collector.characteristics().contains(Collector.Characteristics.CONCURRENT) && (!isOrdered() || collector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
            evaluate = collector.supplier().get();
            forEach(new ReferencePipeline$$ExternalSyntheticLambda0(collector.accumulator(), evaluate));
        } else {
            evaluate = evaluate(ReduceOps.makeRef(collector));
        }
        return collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH) ? evaluate : collector.finisher().apply(evaluate);
    }

    static /* synthetic */ void lambda$collect$1(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
    }

    public final Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        return evaluate(ReduceOps.makeRef(supplier, biConsumer, biConsumer2));
    }

    public final Optional max(Comparator comparator) {
        return reduce(BinaryOperator.-CC.maxBy(comparator));
    }

    public final Optional min(Comparator comparator) {
        return reduce(BinaryOperator.-CC.minBy(comparator));
    }

    public final long count() {
        return ((Long) evaluate(ReduceOps.makeRefCounting())).longValue();
    }

    static class Head extends ReferencePipeline {
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

        public void forEach(Consumer consumer) {
            if (!isParallel()) {
                sourceStageSpliterator().forEachRemaining(consumer);
            } else {
                super.forEach(consumer);
            }
        }

        public void forEachOrdered(Consumer consumer) {
            if (!isParallel()) {
                sourceStageSpliterator().forEachRemaining(consumer);
            } else {
                super.forEachOrdered(consumer);
            }
        }
    }

    static abstract class StatelessOp extends ReferencePipeline {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        final boolean opIsStateful() {
            return false;
        }

        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }

        StatelessOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }

    static abstract class StatefulOp extends ReferencePipeline {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        abstract Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction);

        final boolean opIsStateful() {
            return true;
        }

        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }

        StatefulOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }
    }
}
