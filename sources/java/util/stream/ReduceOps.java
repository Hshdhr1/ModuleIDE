package java.util.stream;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Sink;
import java.util.stream.TerminalOp;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class ReduceOps {

    private interface AccumulatingSink extends TerminalSink {
        void combine(AccumulatingSink accumulatingSink);
    }

    private ReduceOps() {
    }

    public static TerminalOp makeRef(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        biFunction.getClass();
        binaryOperator.getClass();
        return new 1(StreamShape.REFERENCE, binaryOperator, biFunction, obj);
    }

    class 1ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ BiFunction val$reducer;
        final /* synthetic */ Object val$seed;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        1ReducingSink(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
            this.val$seed = obj;
            this.val$reducer = biFunction;
            this.val$combiner = binaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$seed;
        }

        public void accept(Object obj) {
            this.state = this.val$reducer.apply(this.state, obj);
        }

        public void combine(1ReducingSink r3) {
            this.state = this.val$combiner.apply(this.state, r3.state);
        }
    }

    class 1 extends ReduceOp {
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ BiFunction val$reducer;
        final /* synthetic */ Object val$seed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(StreamShape streamShape, BinaryOperator binaryOperator, BiFunction biFunction, Object obj) {
            super(streamShape);
            this.val$combiner = binaryOperator;
            this.val$reducer = biFunction;
            this.val$seed = obj;
        }

        public 1ReducingSink makeSink() {
            return new 1ReducingSink(this.val$seed, this.val$reducer, this.val$combiner);
        }
    }

    class 2ReducingSink implements AccumulatingSink {
        private boolean empty;
        private Object state;
        final /* synthetic */ BinaryOperator val$operator;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        2ReducingSink(BinaryOperator binaryOperator) {
            this.val$operator = binaryOperator;
        }

        public void begin(long j) {
            this.empty = true;
            this.state = null;
        }

        public void accept(Object obj) {
            if (this.empty) {
                this.empty = false;
                this.state = obj;
            } else {
                this.state = this.val$operator.apply(this.state, obj);
            }
        }

        public Optional get() {
            return this.empty ? Optional.empty() : Optional.of(this.state);
        }

        public void combine(2ReducingSink r2) {
            if (r2.empty) {
                return;
            }
            accept(r2.state);
        }
    }

    public static TerminalOp makeRef(BinaryOperator binaryOperator) {
        binaryOperator.getClass();
        return new 2(StreamShape.REFERENCE, binaryOperator);
    }

    class 2 extends ReduceOp {
        final /* synthetic */ BinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(StreamShape streamShape, BinaryOperator binaryOperator) {
            super(streamShape);
            this.val$operator = binaryOperator;
        }

        public 2ReducingSink makeSink() {
            return new 2ReducingSink(this.val$operator);
        }
    }

    public static TerminalOp makeRef(Collector collector) {
        collector.getClass();
        Supplier supplier = collector.supplier();
        BiConsumer accumulator = collector.accumulator();
        return new 3(StreamShape.REFERENCE, collector.combiner(), accumulator, supplier, collector);
    }

    class 3ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        3ReducingSink(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = biConsumer;
            this.val$combiner = binaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        public void accept(Object obj) {
            this.val$accumulator.accept(this.state, obj);
        }

        public void combine(3ReducingSink r3) {
            this.state = this.val$combiner.apply(this.state, r3.state);
        }
    }

    class 3 extends ReduceOp {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ Collector val$collector;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(StreamShape streamShape, BinaryOperator binaryOperator, BiConsumer biConsumer, Supplier supplier, Collector collector) {
            super(streamShape);
            this.val$combiner = binaryOperator;
            this.val$accumulator = biConsumer;
            this.val$supplier = supplier;
            this.val$collector = collector;
        }

        public 3ReducingSink makeSink() {
            return new 3ReducingSink(this.val$supplier, this.val$accumulator, this.val$combiner);
        }

        public int getOpFlags() {
            if (this.val$collector.characteristics().contains(Collector.Characteristics.UNORDERED)) {
                return StreamOpFlag.NOT_ORDERED;
            }
            return 0;
        }
    }

    public static TerminalOp makeRef(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        supplier.getClass();
        biConsumer.getClass();
        biConsumer2.getClass();
        return new 4(StreamShape.REFERENCE, biConsumer2, biConsumer, supplier);
    }

    class 4ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BiConsumer val$reducer;
        final /* synthetic */ Supplier val$seedFactory;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        4ReducingSink(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
            this.val$seedFactory = supplier;
            this.val$accumulator = biConsumer;
            this.val$reducer = biConsumer2;
        }

        public void begin(long j) {
            this.state = this.val$seedFactory.get();
        }

        public void accept(Object obj) {
            this.val$accumulator.accept(this.state, obj);
        }

        public void combine(4ReducingSink r3) {
            this.val$reducer.accept(this.state, r3.state);
        }
    }

    class 4 extends ReduceOp {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BiConsumer val$reducer;
        final /* synthetic */ Supplier val$seedFactory;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(StreamShape streamShape, BiConsumer biConsumer, BiConsumer biConsumer2, Supplier supplier) {
            super(streamShape);
            this.val$reducer = biConsumer;
            this.val$accumulator = biConsumer2;
            this.val$seedFactory = supplier;
        }

        public 4ReducingSink makeSink() {
            return new 4ReducingSink(this.val$seedFactory, this.val$accumulator, this.val$reducer);
        }
    }

    class 5 extends ReduceOp {
        5(StreamShape streamShape) {
            super(streamShape);
        }

        public CountingSink makeSink() {
            return new CountingSink.OfRef();
        }

        public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateSequential(pipelineHelper, spliterator);
        }

        public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateParallel(pipelineHelper, spliterator);
        }

        public int getOpFlags() {
            return StreamOpFlag.NOT_ORDERED;
        }
    }

    public static TerminalOp makeRefCounting() {
        return new 5(StreamShape.REFERENCE);
    }

    class 5ReducingSink implements AccumulatingSink, Sink.OfInt {
        private int state;
        final /* synthetic */ int val$identity;
        final /* synthetic */ IntBinaryOperator val$operator;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        5ReducingSink(int i, IntBinaryOperator intBinaryOperator) {
            this.val$identity = i;
            this.val$operator = intBinaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$identity;
        }

        public void accept(int i) {
            this.state = this.val$operator.applyAsInt(this.state, i);
        }

        public Integer get() {
            return Integer.valueOf(this.state);
        }

        public void combine(5ReducingSink r1) {
            accept(r1.state);
        }
    }

    public static TerminalOp makeInt(int i, IntBinaryOperator intBinaryOperator) {
        intBinaryOperator.getClass();
        return new 6(StreamShape.INT_VALUE, intBinaryOperator, i);
    }

    class 6 extends ReduceOp {
        final /* synthetic */ int val$identity;
        final /* synthetic */ IntBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        6(StreamShape streamShape, IntBinaryOperator intBinaryOperator, int i) {
            super(streamShape);
            this.val$operator = intBinaryOperator;
            this.val$identity = i;
        }

        public 5ReducingSink makeSink() {
            return new 5ReducingSink(this.val$identity, this.val$operator);
        }
    }

    class 6ReducingSink implements AccumulatingSink, Sink.OfInt {
        private boolean empty;
        private int state;
        final /* synthetic */ IntBinaryOperator val$operator;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        6ReducingSink(IntBinaryOperator intBinaryOperator) {
            this.val$operator = intBinaryOperator;
        }

        public void begin(long j) {
            this.empty = true;
            this.state = 0;
        }

        public void accept(int i) {
            if (this.empty) {
                this.empty = false;
                this.state = i;
            } else {
                this.state = this.val$operator.applyAsInt(this.state, i);
            }
        }

        public OptionalInt get() {
            return this.empty ? OptionalInt.empty() : OptionalInt.of(this.state);
        }

        public void combine(6ReducingSink r2) {
            if (r2.empty) {
                return;
            }
            accept(r2.state);
        }
    }

    public static TerminalOp makeInt(IntBinaryOperator intBinaryOperator) {
        intBinaryOperator.getClass();
        return new 7(StreamShape.INT_VALUE, intBinaryOperator);
    }

    class 7 extends ReduceOp {
        final /* synthetic */ IntBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        7(StreamShape streamShape, IntBinaryOperator intBinaryOperator) {
            super(streamShape);
            this.val$operator = intBinaryOperator;
        }

        public 6ReducingSink makeSink() {
            return new 6ReducingSink(this.val$operator);
        }
    }

    public static TerminalOp makeInt(Supplier supplier, ObjIntConsumer objIntConsumer, BinaryOperator binaryOperator) {
        supplier.getClass();
        objIntConsumer.getClass();
        binaryOperator.getClass();
        return new 8(StreamShape.INT_VALUE, binaryOperator, objIntConsumer, supplier);
    }

    class 7ReducingSink extends Box implements AccumulatingSink, Sink.OfInt {
        final /* synthetic */ ObjIntConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer.-CC.$default$andThen(this, intConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        7ReducingSink(Supplier supplier, ObjIntConsumer objIntConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objIntConsumer;
            this.val$combiner = binaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        public void accept(int i) {
            this.val$accumulator.accept(this.state, i);
        }

        public void combine(7ReducingSink r3) {
            this.state = this.val$combiner.apply(this.state, r3.state);
        }
    }

    class 8 extends ReduceOp {
        final /* synthetic */ ObjIntConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        8(StreamShape streamShape, BinaryOperator binaryOperator, ObjIntConsumer objIntConsumer, Supplier supplier) {
            super(streamShape);
            this.val$combiner = binaryOperator;
            this.val$accumulator = objIntConsumer;
            this.val$supplier = supplier;
        }

        public 7ReducingSink makeSink() {
            return new 7ReducingSink(this.val$supplier, this.val$accumulator, this.val$combiner);
        }
    }

    class 9 extends ReduceOp {
        9(StreamShape streamShape) {
            super(streamShape);
        }

        public CountingSink makeSink() {
            return new CountingSink.OfInt();
        }

        public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateSequential(pipelineHelper, spliterator);
        }

        public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateParallel(pipelineHelper, spliterator);
        }

        public int getOpFlags() {
            return StreamOpFlag.NOT_ORDERED;
        }
    }

    public static TerminalOp makeIntCounting() {
        return new 9(StreamShape.INT_VALUE);
    }

    class 8ReducingSink implements AccumulatingSink, Sink.OfLong {
        private long state;
        final /* synthetic */ long val$identity;
        final /* synthetic */ LongBinaryOperator val$operator;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        8ReducingSink(long j, LongBinaryOperator longBinaryOperator) {
            this.val$identity = j;
            this.val$operator = longBinaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$identity;
        }

        public void accept(long j) {
            this.state = this.val$operator.applyAsLong(this.state, j);
        }

        public Long get() {
            return Long.valueOf(this.state);
        }

        public void combine(8ReducingSink r3) {
            accept(r3.state);
        }
    }

    public static TerminalOp makeLong(long j, LongBinaryOperator longBinaryOperator) {
        longBinaryOperator.getClass();
        return new 10(StreamShape.LONG_VALUE, longBinaryOperator, j);
    }

    class 10 extends ReduceOp {
        final /* synthetic */ long val$identity;
        final /* synthetic */ LongBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        10(StreamShape streamShape, LongBinaryOperator longBinaryOperator, long j) {
            super(streamShape);
            this.val$operator = longBinaryOperator;
            this.val$identity = j;
        }

        public 8ReducingSink makeSink() {
            return new 8ReducingSink(this.val$identity, this.val$operator);
        }
    }

    class 9ReducingSink implements AccumulatingSink, Sink.OfLong {
        private boolean empty;
        private long state;
        final /* synthetic */ LongBinaryOperator val$operator;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        9ReducingSink(LongBinaryOperator longBinaryOperator) {
            this.val$operator = longBinaryOperator;
        }

        public void begin(long j) {
            this.empty = true;
            this.state = 0L;
        }

        public void accept(long j) {
            if (this.empty) {
                this.empty = false;
                this.state = j;
            } else {
                this.state = this.val$operator.applyAsLong(this.state, j);
            }
        }

        public OptionalLong get() {
            return this.empty ? OptionalLong.empty() : OptionalLong.of(this.state);
        }

        public void combine(9ReducingSink r3) {
            if (r3.empty) {
                return;
            }
            accept(r3.state);
        }
    }

    public static TerminalOp makeLong(LongBinaryOperator longBinaryOperator) {
        longBinaryOperator.getClass();
        return new 11(StreamShape.LONG_VALUE, longBinaryOperator);
    }

    class 11 extends ReduceOp {
        final /* synthetic */ LongBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        11(StreamShape streamShape, LongBinaryOperator longBinaryOperator) {
            super(streamShape);
            this.val$operator = longBinaryOperator;
        }

        public 9ReducingSink makeSink() {
            return new 9ReducingSink(this.val$operator);
        }
    }

    public static TerminalOp makeLong(Supplier supplier, ObjLongConsumer objLongConsumer, BinaryOperator binaryOperator) {
        supplier.getClass();
        objLongConsumer.getClass();
        binaryOperator.getClass();
        return new 12(StreamShape.LONG_VALUE, binaryOperator, objLongConsumer, supplier);
    }

    class 10ReducingSink extends Box implements AccumulatingSink, Sink.OfLong {
        final /* synthetic */ ObjLongConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        public /* synthetic */ void accept(double d) {
            Sink.-CC.$default$accept(this, d);
        }

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer.-CC.$default$andThen(this, longConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        10ReducingSink(Supplier supplier, ObjLongConsumer objLongConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objLongConsumer;
            this.val$combiner = binaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        public void accept(long j) {
            this.val$accumulator.accept(this.state, j);
        }

        public void combine(10ReducingSink r3) {
            this.state = this.val$combiner.apply(this.state, r3.state);
        }
    }

    class 12 extends ReduceOp {
        final /* synthetic */ ObjLongConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        12(StreamShape streamShape, BinaryOperator binaryOperator, ObjLongConsumer objLongConsumer, Supplier supplier) {
            super(streamShape);
            this.val$combiner = binaryOperator;
            this.val$accumulator = objLongConsumer;
            this.val$supplier = supplier;
        }

        public 10ReducingSink makeSink() {
            return new 10ReducingSink(this.val$supplier, this.val$accumulator, this.val$combiner);
        }
    }

    class 13 extends ReduceOp {
        13(StreamShape streamShape) {
            super(streamShape);
        }

        public CountingSink makeSink() {
            return new CountingSink.OfLong();
        }

        public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateSequential(pipelineHelper, spliterator);
        }

        public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateParallel(pipelineHelper, spliterator);
        }

        public int getOpFlags() {
            return StreamOpFlag.NOT_ORDERED;
        }
    }

    public static TerminalOp makeLongCounting() {
        return new 13(StreamShape.LONG_VALUE);
    }

    class 11ReducingSink implements AccumulatingSink, Sink.OfDouble {
        private double state;
        final /* synthetic */ double val$identity;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        11ReducingSink(double d, DoubleBinaryOperator doubleBinaryOperator) {
            this.val$identity = d;
            this.val$operator = doubleBinaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$identity;
        }

        public void accept(double d) {
            this.state = this.val$operator.applyAsDouble(this.state, d);
        }

        public Double get() {
            return Double.valueOf(this.state);
        }

        public void combine(11ReducingSink r3) {
            accept(r3.state);
        }
    }

    public static TerminalOp makeDouble(double d, DoubleBinaryOperator doubleBinaryOperator) {
        doubleBinaryOperator.getClass();
        return new 14(StreamShape.DOUBLE_VALUE, doubleBinaryOperator, d);
    }

    class 14 extends ReduceOp {
        final /* synthetic */ double val$identity;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        14(StreamShape streamShape, DoubleBinaryOperator doubleBinaryOperator, double d) {
            super(streamShape);
            this.val$operator = doubleBinaryOperator;
            this.val$identity = d;
        }

        public 11ReducingSink makeSink() {
            return new 11ReducingSink(this.val$identity, this.val$operator);
        }
    }

    class 12ReducingSink implements AccumulatingSink, Sink.OfDouble {
        private boolean empty;
        private double state;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        12ReducingSink(DoubleBinaryOperator doubleBinaryOperator) {
            this.val$operator = doubleBinaryOperator;
        }

        public void begin(long j) {
            this.empty = true;
            this.state = 0.0d;
        }

        public void accept(double d) {
            if (this.empty) {
                this.empty = false;
                this.state = d;
            } else {
                this.state = this.val$operator.applyAsDouble(this.state, d);
            }
        }

        public OptionalDouble get() {
            return this.empty ? OptionalDouble.empty() : OptionalDouble.of(this.state);
        }

        public void combine(12ReducingSink r3) {
            if (r3.empty) {
                return;
            }
            accept(r3.state);
        }
    }

    public static TerminalOp makeDouble(DoubleBinaryOperator doubleBinaryOperator) {
        doubleBinaryOperator.getClass();
        return new 15(StreamShape.DOUBLE_VALUE, doubleBinaryOperator);
    }

    class 15 extends ReduceOp {
        final /* synthetic */ DoubleBinaryOperator val$operator;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        15(StreamShape streamShape, DoubleBinaryOperator doubleBinaryOperator) {
            super(streamShape);
            this.val$operator = doubleBinaryOperator;
        }

        public 12ReducingSink makeSink() {
            return new 12ReducingSink(this.val$operator);
        }
    }

    public static TerminalOp makeDouble(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BinaryOperator binaryOperator) {
        supplier.getClass();
        objDoubleConsumer.getClass();
        binaryOperator.getClass();
        return new 16(StreamShape.DOUBLE_VALUE, binaryOperator, objDoubleConsumer, supplier);
    }

    class 13ReducingSink extends Box implements AccumulatingSink, Sink.OfDouble {
        final /* synthetic */ ObjDoubleConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        public /* synthetic */ void accept(int i) {
            Sink.-CC.$default$accept((Sink) this, i);
        }

        public /* synthetic */ void accept(long j) {
            Sink.-CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.-CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
        }

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        13ReducingSink(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objDoubleConsumer;
            this.val$combiner = binaryOperator;
        }

        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        public void accept(double d) {
            this.val$accumulator.accept(this.state, d);
        }

        public void combine(13ReducingSink r3) {
            this.state = this.val$combiner.apply(this.state, r3.state);
        }
    }

    class 16 extends ReduceOp {
        final /* synthetic */ ObjDoubleConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        16(StreamShape streamShape, BinaryOperator binaryOperator, ObjDoubleConsumer objDoubleConsumer, Supplier supplier) {
            super(streamShape);
            this.val$combiner = binaryOperator;
            this.val$accumulator = objDoubleConsumer;
            this.val$supplier = supplier;
        }

        public 13ReducingSink makeSink() {
            return new 13ReducingSink(this.val$supplier, this.val$accumulator, this.val$combiner);
        }
    }

    class 17 extends ReduceOp {
        17(StreamShape streamShape) {
            super(streamShape);
        }

        public CountingSink makeSink() {
            return new CountingSink.OfDouble();
        }

        public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateSequential(pipelineHelper, spliterator);
        }

        public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Long.valueOf(spliterator.getExactSizeIfKnown());
            }
            return (Long) super.evaluateParallel(pipelineHelper, spliterator);
        }

        public int getOpFlags() {
            return StreamOpFlag.NOT_ORDERED;
        }
    }

    public static TerminalOp makeDoubleCounting() {
        return new 17(StreamShape.DOUBLE_VALUE);
    }

    static abstract class CountingSink extends Box implements AccumulatingSink {
        long count;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        CountingSink() {
        }

        public void begin(long j) {
            this.count = 0L;
        }

        public Long get() {
            return Long.valueOf(this.count);
        }

        public void combine(CountingSink countingSink) {
            this.count += countingSink.count;
        }

        static final class OfRef extends CountingSink {
            OfRef() {
            }

            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            public void accept(Object obj) {
                this.count++;
            }
        }

        static final class OfInt extends CountingSink implements Sink.OfInt {
            public /* synthetic */ void accept(Integer num) {
                Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfInt.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            OfInt() {
            }

            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            public void accept(int i) {
                this.count++;
            }
        }

        static final class OfLong extends CountingSink implements Sink.OfLong {
            public /* synthetic */ void accept(Long l) {
                Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfLong.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            OfLong() {
            }

            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            public void accept(long j) {
                this.count++;
            }
        }

        static final class OfDouble extends CountingSink implements Sink.OfDouble {
            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            OfDouble() {
            }

            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            public void accept(double d) {
                this.count++;
            }
        }
    }

    private static abstract class Box {
        Object state;

        Box() {
        }

        public Object get() {
            return this.state;
        }
    }

    private static abstract class ReduceOp implements TerminalOp {
        private final StreamShape inputShape;

        public /* synthetic */ int getOpFlags() {
            return TerminalOp.-CC.$default$getOpFlags(this);
        }

        public abstract AccumulatingSink makeSink();

        ReduceOp(StreamShape streamShape) {
            this.inputShape = streamShape;
        }

        public StreamShape inputShape() {
            return this.inputShape;
        }

        public Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return ((AccumulatingSink) pipelineHelper.wrapAndCopyInto(makeSink(), spliterator)).get();
        }

        public Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return ((AccumulatingSink) new ReduceTask(this, pipelineHelper, spliterator).invoke()).get();
        }
    }

    private static final class ReduceTask extends AbstractTask {
        private final ReduceOp op;

        ReduceTask(ReduceOp reduceOp, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.op = reduceOp;
        }

        ReduceTask(ReduceTask reduceTask, Spliterator spliterator) {
            super(reduceTask, spliterator);
            this.op = reduceTask.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public ReduceTask makeChild(Spliterator spliterator) {
            return new ReduceTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public AccumulatingSink doLeaf() {
            return (AccumulatingSink) this.helper.wrapAndCopyInto(this.op.makeSink(), this.spliterator);
        }

        public void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                AccumulatingSink accumulatingSink = (AccumulatingSink) ((ReduceTask) this.leftChild).getLocalResult();
                accumulatingSink.combine((AccumulatingSink) ((ReduceTask) this.rightChild).getLocalResult());
                setLocalResult(accumulatingSink);
            }
            super.onCompletion(countedCompleter);
        }
    }
}
