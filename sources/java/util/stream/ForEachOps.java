package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.Node;
import java.util.stream.Sink;
import java.util.stream.TerminalOp;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class ForEachOps {
    private ForEachOps() {
    }

    public static TerminalOp makeRef(Consumer consumer, boolean z) {
        consumer.getClass();
        return new ForEachOp.OfRef(consumer, z);
    }

    public static TerminalOp makeInt(IntConsumer intConsumer, boolean z) {
        intConsumer.getClass();
        return new ForEachOp.OfInt(intConsumer, z);
    }

    public static TerminalOp makeLong(LongConsumer longConsumer, boolean z) {
        longConsumer.getClass();
        return new ForEachOp.OfLong(longConsumer, z);
    }

    public static TerminalOp makeDouble(DoubleConsumer doubleConsumer, boolean z) {
        doubleConsumer.getClass();
        return new ForEachOp.OfDouble(doubleConsumer, z);
    }

    static abstract class ForEachOp implements TerminalOp, TerminalSink {
        private final boolean ordered;

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

        public /* synthetic */ boolean cancellationRequested() {
            return Sink.-CC.$default$cancellationRequested(this);
        }

        public /* synthetic */ void end() {
            Sink.-CC.$default$end(this);
        }

        public Void get() {
            return null;
        }

        public /* synthetic */ StreamShape inputShape() {
            return TerminalOp.-CC.$default$inputShape(this);
        }

        protected ForEachOp(boolean z) {
            this.ordered = z;
        }

        public int getOpFlags() {
            if (this.ordered) {
                return 0;
            }
            return StreamOpFlag.NOT_ORDERED;
        }

        public Void evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return ((ForEachOp) pipelineHelper.wrapAndCopyInto(this, spliterator)).get();
        }

        public Void evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (this.ordered) {
                new ForEachOrderedTask(pipelineHelper, spliterator, this).invoke();
                return null;
            }
            new ForEachTask(pipelineHelper, spliterator, pipelineHelper.wrapSink(this)).invoke();
            return null;
        }

        static final class OfRef extends ForEachOp {
            final Consumer consumer;

            public /* bridge */ /* synthetic */ Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateParallel(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateSequential(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            OfRef(Consumer consumer, boolean z) {
                super(z);
                this.consumer = consumer;
            }

            public void accept(Object obj) {
                this.consumer.accept(obj);
            }
        }

        static final class OfInt extends ForEachOp implements Sink.OfInt {
            final IntConsumer consumer;

            public /* synthetic */ void accept(Integer num) {
                Sink.OfInt.-CC.$default$accept((Sink.OfInt) this, num);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfInt.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            public /* bridge */ /* synthetic */ Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateParallel(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateSequential(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            OfInt(IntConsumer intConsumer, boolean z) {
                super(z);
                this.consumer = intConsumer;
            }

            public StreamShape inputShape() {
                return StreamShape.INT_VALUE;
            }

            public void accept(int i) {
                this.consumer.accept(i);
            }
        }

        static final class OfLong extends ForEachOp implements Sink.OfLong {
            final LongConsumer consumer;

            public /* synthetic */ void accept(Long l) {
                Sink.OfLong.-CC.$default$accept((Sink.OfLong) this, l);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfLong.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            public /* bridge */ /* synthetic */ Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateParallel(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateSequential(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            OfLong(LongConsumer longConsumer, boolean z) {
                super(z);
                this.consumer = longConsumer;
            }

            public StreamShape inputShape() {
                return StreamShape.LONG_VALUE;
            }

            public void accept(long j) {
                this.consumer.accept(j);
            }
        }

        static final class OfDouble extends ForEachOp implements Sink.OfDouble {
            final DoubleConsumer consumer;

            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.-CC.$default$accept((Sink.OfDouble) this, d);
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.-CC.$default$accept(this, obj);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            public /* bridge */ /* synthetic */ Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateParallel(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return super.evaluateSequential(pipelineHelper, spliterator);
            }

            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }

            OfDouble(DoubleConsumer doubleConsumer, boolean z) {
                super(z);
                this.consumer = doubleConsumer;
            }

            public StreamShape inputShape() {
                return StreamShape.DOUBLE_VALUE;
            }

            public void accept(double d) {
                this.consumer.accept(d);
            }
        }
    }

    static final class ForEachTask extends CountedCompleter {
        private final PipelineHelper helper;
        private final Sink sink;
        private Spliterator spliterator;
        private long targetSize;

        ForEachTask(PipelineHelper pipelineHelper, Spliterator spliterator, Sink sink) {
            super(null);
            this.sink = sink;
            this.helper = pipelineHelper;
            this.spliterator = spliterator;
            this.targetSize = 0L;
        }

        ForEachTask(ForEachTask forEachTask, Spliterator spliterator) {
            super(forEachTask);
            this.spliterator = spliterator;
            this.sink = forEachTask.sink;
            this.targetSize = forEachTask.targetSize;
            this.helper = forEachTask.helper;
        }

        public void compute() {
            Spliterator trySplit;
            Spliterator spliterator = this.spliterator;
            long estimateSize = spliterator.estimateSize();
            long j = this.targetSize;
            if (j == 0) {
                j = AbstractTask.suggestTargetSize(estimateSize);
                this.targetSize = j;
            }
            boolean isKnown = StreamOpFlag.SHORT_CIRCUIT.isKnown(this.helper.getStreamAndOpFlags());
            Sink sink = this.sink;
            boolean z = false;
            ForEachTask forEachTask = this;
            while (true) {
                if (isKnown && sink.cancellationRequested()) {
                    break;
                }
                if (estimateSize <= j || (trySplit = spliterator.trySplit()) == null) {
                    break;
                }
                ForEachTask forEachTask2 = new ForEachTask(forEachTask, trySplit);
                forEachTask.addToPendingCount(1);
                if (z) {
                    spliterator = trySplit;
                } else {
                    ForEachTask forEachTask3 = forEachTask;
                    forEachTask = forEachTask2;
                    forEachTask2 = forEachTask3;
                }
                z = !z;
                forEachTask.fork();
                forEachTask = forEachTask2;
                estimateSize = spliterator.estimateSize();
            }
            forEachTask.helper.copyInto(sink, spliterator);
            forEachTask.spliterator = null;
            forEachTask.propagateCompletion();
        }
    }

    static final class ForEachOrderedTask extends CountedCompleter {
        private final Sink action;
        private final ConcurrentHashMap completionMap;
        private final PipelineHelper helper;
        private final ForEachOrderedTask leftPredecessor;
        private Node node;
        private Spliterator spliterator;
        private final long targetSize;

        protected ForEachOrderedTask(PipelineHelper pipelineHelper, Spliterator spliterator, Sink sink) {
            super(null);
            this.helper = pipelineHelper;
            this.spliterator = spliterator;
            this.targetSize = AbstractTask.suggestTargetSize(spliterator.estimateSize());
            this.completionMap = new ConcurrentHashMap(Math.max(16, AbstractTask.getLeafTarget() << 1));
            this.action = sink;
            this.leftPredecessor = null;
        }

        ForEachOrderedTask(ForEachOrderedTask forEachOrderedTask, Spliterator spliterator, ForEachOrderedTask forEachOrderedTask2) {
            super(forEachOrderedTask);
            this.helper = forEachOrderedTask.helper;
            this.spliterator = spliterator;
            this.targetSize = forEachOrderedTask.targetSize;
            this.completionMap = forEachOrderedTask.completionMap;
            this.action = forEachOrderedTask.action;
            this.leftPredecessor = forEachOrderedTask2;
        }

        public final void compute() {
            doCompute(this);
        }

        private static void doCompute(ForEachOrderedTask forEachOrderedTask) {
            Spliterator trySplit;
            Spliterator spliterator = forEachOrderedTask.spliterator;
            long j = forEachOrderedTask.targetSize;
            boolean z = false;
            while (spliterator.estimateSize() > j && (trySplit = spliterator.trySplit()) != null) {
                ForEachOrderedTask forEachOrderedTask2 = new ForEachOrderedTask(forEachOrderedTask, trySplit, forEachOrderedTask.leftPredecessor);
                ForEachOrderedTask forEachOrderedTask3 = new ForEachOrderedTask(forEachOrderedTask, spliterator, forEachOrderedTask2);
                forEachOrderedTask.addToPendingCount(1);
                forEachOrderedTask3.addToPendingCount(1);
                forEachOrderedTask.completionMap.put(forEachOrderedTask2, forEachOrderedTask3);
                if (forEachOrderedTask.leftPredecessor != null) {
                    forEachOrderedTask2.addToPendingCount(1);
                    if (forEachOrderedTask.completionMap.replace(forEachOrderedTask.leftPredecessor, forEachOrderedTask, forEachOrderedTask2)) {
                        forEachOrderedTask.addToPendingCount(-1);
                    } else {
                        forEachOrderedTask2.addToPendingCount(-1);
                    }
                }
                if (z) {
                    spliterator = trySplit;
                    forEachOrderedTask = forEachOrderedTask2;
                    forEachOrderedTask2 = forEachOrderedTask3;
                } else {
                    forEachOrderedTask = forEachOrderedTask3;
                }
                z = !z;
                forEachOrderedTask2.fork();
            }
            if (forEachOrderedTask.getPendingCount() > 0) {
                ForEachOps$ForEachOrderedTask$$ExternalSyntheticLambda0 forEachOps$ForEachOrderedTask$$ExternalSyntheticLambda0 = new ForEachOps$ForEachOrderedTask$$ExternalSyntheticLambda0();
                PipelineHelper pipelineHelper = forEachOrderedTask.helper;
                forEachOrderedTask.node = ((Node.Builder) forEachOrderedTask.helper.wrapAndCopyInto(pipelineHelper.makeNodeBuilder(pipelineHelper.exactOutputSizeIfKnown(spliterator), forEachOps$ForEachOrderedTask$$ExternalSyntheticLambda0), spliterator)).build();
                forEachOrderedTask.spliterator = null;
            }
            forEachOrderedTask.tryComplete();
        }

        static /* synthetic */ Object[] lambda$doCompute$0(int i) {
            return new Object[i];
        }

        public void onCompletion(CountedCompleter countedCompleter) {
            Node node = this.node;
            if (node != null) {
                node.forEach(this.action);
                this.node = null;
            } else {
                Spliterator spliterator = this.spliterator;
                if (spliterator != null) {
                    this.helper.wrapAndCopyInto(this.action, spliterator);
                    this.spliterator = null;
                }
            }
            ForEachOrderedTask forEachOrderedTask = (ForEachOrderedTask) this.completionMap.remove(this);
            if (forEachOrderedTask != null) {
                forEachOrderedTask.tryComplete();
            }
        }
    }
}
