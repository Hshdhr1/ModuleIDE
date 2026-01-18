package java.util.stream;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Sink;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class FindOps {
    private FindOps() {
    }

    public static TerminalOp makeRef(boolean z) {
        return z ? FindSink.OfRef.OP_FIND_FIRST : FindSink.OfRef.OP_FIND_ANY;
    }

    public static TerminalOp makeInt(boolean z) {
        return z ? FindSink.OfInt.OP_FIND_FIRST : FindSink.OfInt.OP_FIND_ANY;
    }

    public static TerminalOp makeLong(boolean z) {
        return z ? FindSink.OfLong.OP_FIND_FIRST : FindSink.OfLong.OP_FIND_ANY;
    }

    public static TerminalOp makeDouble(boolean z) {
        return z ? FindSink.OfDouble.OP_FIND_FIRST : FindSink.OfDouble.OP_FIND_ANY;
    }

    private static final class FindOp implements TerminalOp {
        final Object emptyValue;
        final int opFlags;
        final Predicate presentPredicate;
        private final StreamShape shape;
        final Supplier sinkSupplier;

        FindOp(boolean z, StreamShape streamShape, Object obj, Predicate predicate, Supplier supplier) {
            this.opFlags = (z ? 0 : StreamOpFlag.NOT_ORDERED) | StreamOpFlag.IS_SHORT_CIRCUIT;
            this.shape = streamShape;
            this.emptyValue = obj;
            this.presentPredicate = predicate;
            this.sinkSupplier = supplier;
        }

        public int getOpFlags() {
            return this.opFlags;
        }

        public StreamShape inputShape() {
            return this.shape;
        }

        public Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Object obj = ((TerminalSink) pipelineHelper.wrapAndCopyInto((TerminalSink) this.sinkSupplier.get(), spliterator)).get();
            return obj != null ? obj : this.emptyValue;
        }

        public Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return new FindTask(this, StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()), pipelineHelper, spliterator).invoke();
        }
    }

    private static abstract class FindSink implements TerminalSink {
        boolean hasValue;
        Object value;

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

        FindSink() {
        }

        public void accept(Object obj) {
            if (this.hasValue) {
                return;
            }
            this.hasValue = true;
            this.value = obj;
        }

        public boolean cancellationRequested() {
            return this.hasValue;
        }

        static final class OfRef extends FindSink {
            static final TerminalOp OP_FIND_FIRST = new FindOp(true, StreamShape.REFERENCE, Optional.empty(), new FindOps$FindSink$OfRef$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfRef$$ExternalSyntheticLambda1());
            static final TerminalOp OP_FIND_ANY = new FindOp(false, StreamShape.REFERENCE, Optional.empty(), new FindOps$FindSink$OfRef$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfRef$$ExternalSyntheticLambda1());

            OfRef() {
            }

            public Optional get() {
                if (this.hasValue) {
                    return Optional.of(this.value);
                }
                return null;
            }
        }

        static final class OfInt extends FindSink implements Sink.OfInt {
            static final TerminalOp OP_FIND_FIRST = new FindOp(true, StreamShape.INT_VALUE, OptionalInt.empty(), new FindOps$FindSink$OfInt$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfInt$$ExternalSyntheticLambda1());
            static final TerminalOp OP_FIND_ANY = new FindOp(false, StreamShape.INT_VALUE, OptionalInt.empty(), new FindOps$FindSink$OfInt$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfInt$$ExternalSyntheticLambda1());

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer.-CC.$default$andThen(this, intConsumer);
            }

            OfInt() {
            }

            public /* bridge */ /* synthetic */ void accept(Integer num) {
                super.accept((Object) num);
            }

            public void accept(int i) {
                accept((Object) Integer.valueOf(i));
            }

            public OptionalInt get() {
                if (this.hasValue) {
                    return OptionalInt.of(((Integer) this.value).intValue());
                }
                return null;
            }
        }

        static final class OfLong extends FindSink implements Sink.OfLong {
            static final TerminalOp OP_FIND_FIRST = new FindOp(true, StreamShape.LONG_VALUE, OptionalLong.empty(), new FindOps$FindSink$OfLong$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfLong$$ExternalSyntheticLambda1());
            static final TerminalOp OP_FIND_ANY = new FindOp(false, StreamShape.LONG_VALUE, OptionalLong.empty(), new FindOps$FindSink$OfLong$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfLong$$ExternalSyntheticLambda1());

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer.-CC.$default$andThen(this, longConsumer);
            }

            OfLong() {
            }

            public /* bridge */ /* synthetic */ void accept(Long l) {
                super.accept((Object) l);
            }

            public void accept(long j) {
                accept((Object) Long.valueOf(j));
            }

            public OptionalLong get() {
                if (this.hasValue) {
                    return OptionalLong.of(((Long) this.value).longValue());
                }
                return null;
            }
        }

        static final class OfDouble extends FindSink implements Sink.OfDouble {
            static final TerminalOp OP_FIND_FIRST = new FindOp(true, StreamShape.DOUBLE_VALUE, OptionalDouble.empty(), new FindOps$FindSink$OfDouble$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfDouble$$ExternalSyntheticLambda1());
            static final TerminalOp OP_FIND_ANY = new FindOp(false, StreamShape.DOUBLE_VALUE, OptionalDouble.empty(), new FindOps$FindSink$OfDouble$$ExternalSyntheticLambda0(), new FindOps$FindSink$OfDouble$$ExternalSyntheticLambda1());

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
            }

            OfDouble() {
            }

            public /* bridge */ /* synthetic */ void accept(Double d) {
                super.accept((Object) d);
            }

            public void accept(double d) {
                accept((Object) Double.valueOf(d));
            }

            public OptionalDouble get() {
                if (this.hasValue) {
                    return OptionalDouble.of(((Double) this.value).doubleValue());
                }
                return null;
            }
        }
    }

    private static final class FindTask extends AbstractShortCircuitTask {
        private final boolean mustFindFirst;
        private final FindOp op;

        FindTask(FindOp findOp, boolean z, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.mustFindFirst = z;
            this.op = findOp;
        }

        FindTask(FindTask findTask, Spliterator spliterator) {
            super(findTask, spliterator);
            this.mustFindFirst = findTask.mustFindFirst;
            this.op = findTask.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public FindTask makeChild(Spliterator spliterator) {
            return new FindTask(this, spliterator);
        }

        protected Object getEmptyResult() {
            return this.op.emptyValue;
        }

        private void foundResult(Object obj) {
            if (isLeftmostNode()) {
                shortCircuit(obj);
            } else {
                cancelLaterNodes();
            }
        }

        protected Object doLeaf() {
            Object obj = ((TerminalSink) this.helper.wrapAndCopyInto((TerminalSink) this.op.sinkSupplier.get(), this.spliterator)).get();
            if (!this.mustFindFirst) {
                if (obj != null) {
                    shortCircuit(obj);
                }
                return null;
            }
            if (obj == null) {
                return null;
            }
            foundResult(obj);
            return obj;
        }

        public void onCompletion(CountedCompleter countedCompleter) {
            if (this.mustFindFirst) {
                FindTask findTask = (FindTask) this.leftChild;
                FindTask findTask2 = null;
                while (true) {
                    if (findTask != findTask2) {
                        Object localResult = findTask.getLocalResult();
                        if (localResult == null || !this.op.presentPredicate.test(localResult)) {
                            findTask2 = findTask;
                            findTask = (FindTask) this.rightChild;
                        } else {
                            setLocalResult(localResult);
                            foundResult(localResult);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            super.onCompletion(countedCompleter);
        }
    }
}
