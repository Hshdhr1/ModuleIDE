package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;
import java.util.stream.DoublePipeline;
import java.util.stream.IntPipeline;
import java.util.stream.LongPipeline;
import java.util.stream.Node;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class SliceOps {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    static /* bridge */ /* synthetic */ long -$$Nest$smcalcSize(long j, long j2, long j3) {
        return calcSize(j, j2, j3);
    }

    static /* bridge */ /* synthetic */ long -$$Nest$smcalcSliceFence(long j, long j2) {
        return calcSliceFence(j, j2);
    }

    static /* bridge */ /* synthetic */ Spliterator -$$Nest$smsliceSpliterator(StreamShape streamShape, Spliterator spliterator, long j, long j2) {
        return sliceSpliterator(streamShape, spliterator, j, j2);
    }

    private static long calcSliceFence(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    private SliceOps() {
    }

    private static long calcSize(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    static /* synthetic */ class 5 {
        static final /* synthetic */ int[] $SwitchMap$java$util$stream$StreamShape;

        static {
            int[] iArr = new int[StreamShape.values().length];
            $SwitchMap$java$util$stream$StreamShape = iArr;
            try {
                iArr[StreamShape.REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.INT_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.LONG_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.DOUBLE_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static Spliterator sliceSpliterator(StreamShape streamShape, Spliterator spliterator, long j, long j2) {
        long calcSliceFence = calcSliceFence(j, j2);
        int i = 5.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i == 1) {
            return new StreamSpliterators.SliceSpliterator.OfRef(spliterator, j, calcSliceFence);
        }
        if (i == 2) {
            return new StreamSpliterators.SliceSpliterator.OfInt((Spliterator.OfInt) spliterator, j, calcSliceFence);
        }
        if (i == 3) {
            return new StreamSpliterators.SliceSpliterator.OfLong((Spliterator.OfLong) spliterator, j, calcSliceFence);
        }
        if (i == 4) {
            return new StreamSpliterators.SliceSpliterator.OfDouble((Spliterator.OfDouble) spliterator, j, calcSliceFence);
        }
        throw new IllegalStateException("Unknown shape " + streamShape);
    }

    class 1 extends ReferencePipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        Spliterator unorderedSkipLimitSpliterator(Spliterator spliterator, long j, long j2, long j3) {
            long j4;
            if (j <= j3) {
                long j5 = j3 - j;
                j2 = j2 >= 0 ? Math.min(j2, j5) : j5;
                j4 = 0;
            } else {
                j4 = j;
            }
            return new StreamSpliterators.UnorderedSliceSpliterator.OfRef(spliterator, j4, j2);
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Spliterator spliterator2;
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0) {
                spliterator2 = spliterator;
                if (spliterator2.hasCharacteristics(16384)) {
                    Spliterator wrapSpliterator = pipelineHelper.wrapSpliterator(spliterator);
                    long j = this.val$skip;
                    return new StreamSpliterators.SliceSpliterator.OfRef(wrapSpliterator, j, SliceOps.-$$Nest$smcalcSliceFence(j, this.val$limit));
                }
            } else {
                spliterator2 = spliterator;
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return unorderedSkipLimitSpliterator(pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown);
            }
            return ((Node) new SliceTask(this, pipelineHelper, spliterator2, Nodes.castingArray(), this.val$skip, this.val$limit).invoke()).spliterator();
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collect(pipelineHelper, SliceOps.-$$Nest$smsliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true, intFunction);
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Nodes.collect(this, unorderedSkipLimitSpliterator(pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true, intFunction);
            }
            return (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        class 1 extends Sink.ChainedReference {
            long m;
            long n;

            1(Sink sink) {
                super(sink);
                this.n = 1.this.val$skip;
                this.m = 1.this.val$limit >= 0 ? 1.this.val$limit : Long.MAX_VALUE;
            }

            public void begin(long j) {
                this.downstream.begin(SliceOps.-$$Nest$smcalcSize(j, 1.this.val$skip, this.m));
            }

            public void accept(Object obj) {
                long j = this.n;
                if (j == 0) {
                    long j2 = this.m;
                    if (j2 > 0) {
                        this.m = j2 - 1;
                        this.downstream.accept(obj);
                        return;
                    }
                    return;
                }
                this.n = j - 1;
            }

            public boolean cancellationRequested() {
                return this.m == 0 || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public static Stream makeRef(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new 1(abstractPipeline, StreamShape.REFERENCE, flags(j2), j, j2);
    }

    class 2 extends IntPipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        Spliterator.OfInt unorderedSkipLimitSpliterator(Spliterator.OfInt ofInt, long j, long j2, long j3) {
            long j4;
            if (j <= j3) {
                long j5 = j3 - j;
                j2 = j2 >= 0 ? Math.min(j2, j5) : j5;
                j4 = 0;
            } else {
                j4 = j;
            }
            return new StreamSpliterators.UnorderedSliceSpliterator.OfInt(ofInt, j4, j2);
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Spliterator spliterator2;
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0) {
                spliterator2 = spliterator;
                if (spliterator2.hasCharacteristics(16384)) {
                    Spliterator.OfInt ofInt = (Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator);
                    long j = this.val$skip;
                    return new StreamSpliterators.SliceSpliterator.OfInt(ofInt, j, SliceOps.-$$Nest$smcalcSliceFence(j, this.val$limit));
                }
            } else {
                spliterator2 = spliterator;
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return unorderedSkipLimitSpliterator((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown);
            }
            return ((Node) new SliceTask(this, pipelineHelper, spliterator2, new SliceOps$2$$ExternalSyntheticLambda0(), this.val$skip, this.val$limit).invoke()).spliterator();
        }

        static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectInt(pipelineHelper, SliceOps.-$$Nest$smsliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Nodes.collectInt(this, unorderedSkipLimitSpliterator((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true);
            }
            return (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        class 1 extends Sink.ChainedInt {
            long m;
            long n;

            1(Sink sink) {
                super(sink);
                this.n = 2.this.val$skip;
                this.m = 2.this.val$limit >= 0 ? 2.this.val$limit : Long.MAX_VALUE;
            }

            public void begin(long j) {
                this.downstream.begin(SliceOps.-$$Nest$smcalcSize(j, 2.this.val$skip, this.m));
            }

            public void accept(int i) {
                long j = this.n;
                if (j == 0) {
                    long j2 = this.m;
                    if (j2 > 0) {
                        this.m = j2 - 1;
                        this.downstream.accept(i);
                        return;
                    }
                    return;
                }
                this.n = j - 1;
            }

            public boolean cancellationRequested() {
                return this.m == 0 || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public static IntStream makeInt(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new 2(abstractPipeline, StreamShape.INT_VALUE, flags(j2), j, j2);
    }

    class 3 extends LongPipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        Spliterator.OfLong unorderedSkipLimitSpliterator(Spliterator.OfLong ofLong, long j, long j2, long j3) {
            long j4;
            if (j <= j3) {
                long j5 = j3 - j;
                j2 = j2 >= 0 ? Math.min(j2, j5) : j5;
                j4 = 0;
            } else {
                j4 = j;
            }
            return new StreamSpliterators.UnorderedSliceSpliterator.OfLong(ofLong, j4, j2);
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Spliterator spliterator2;
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0) {
                spliterator2 = spliterator;
                if (spliterator2.hasCharacteristics(16384)) {
                    Spliterator.OfLong ofLong = (Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator);
                    long j = this.val$skip;
                    return new StreamSpliterators.SliceSpliterator.OfLong(ofLong, j, SliceOps.-$$Nest$smcalcSliceFence(j, this.val$limit));
                }
            } else {
                spliterator2 = spliterator;
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return unorderedSkipLimitSpliterator((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown);
            }
            return ((Node) new SliceTask(this, pipelineHelper, spliterator2, new SliceOps$3$$ExternalSyntheticLambda0(), this.val$skip, this.val$limit).invoke()).spliterator();
        }

        static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectLong(pipelineHelper, SliceOps.-$$Nest$smsliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Nodes.collectLong(this, unorderedSkipLimitSpliterator((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true);
            }
            return (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        class 1 extends Sink.ChainedLong {
            long m;
            long n;

            1(Sink sink) {
                super(sink);
                this.n = 3.this.val$skip;
                this.m = 3.this.val$limit >= 0 ? 3.this.val$limit : Long.MAX_VALUE;
            }

            public void begin(long j) {
                this.downstream.begin(SliceOps.-$$Nest$smcalcSize(j, 3.this.val$skip, this.m));
            }

            public void accept(long j) {
                long j2 = this.n;
                if (j2 == 0) {
                    long j3 = this.m;
                    if (j3 > 0) {
                        this.m = j3 - 1;
                        this.downstream.accept(j);
                        return;
                    }
                    return;
                }
                this.n = j2 - 1;
            }

            public boolean cancellationRequested() {
                return this.m == 0 || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public static LongStream makeLong(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new 3(abstractPipeline, StreamShape.LONG_VALUE, flags(j2), j, j2);
    }

    class 4 extends DoublePipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        Spliterator.OfDouble unorderedSkipLimitSpliterator(Spliterator.OfDouble ofDouble, long j, long j2, long j3) {
            long j4;
            if (j <= j3) {
                long j5 = j3 - j;
                j2 = j2 >= 0 ? Math.min(j2, j5) : j5;
                j4 = 0;
            } else {
                j4 = j;
            }
            return new StreamSpliterators.UnorderedSliceSpliterator.OfDouble(ofDouble, j4, j2);
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Spliterator spliterator2;
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0) {
                spliterator2 = spliterator;
                if (spliterator2.hasCharacteristics(16384)) {
                    Spliterator.OfDouble ofDouble = (Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator);
                    long j = this.val$skip;
                    return new StreamSpliterators.SliceSpliterator.OfDouble(ofDouble, j, SliceOps.-$$Nest$smcalcSliceFence(j, this.val$limit));
                }
            } else {
                spliterator2 = spliterator;
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return unorderedSkipLimitSpliterator((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown);
            }
            return ((Node) new SliceTask(this, pipelineHelper, spliterator2, new SliceOps$4$$ExternalSyntheticLambda0(), this.val$skip, this.val$limit).invoke()).spliterator();
        }

        static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectDouble(pipelineHelper, SliceOps.-$$Nest$smsliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return Nodes.collectDouble(this, unorderedSkipLimitSpliterator((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true);
            }
            return (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        class 1 extends Sink.ChainedDouble {
            long m;
            long n;

            1(Sink sink) {
                super(sink);
                this.n = 4.this.val$skip;
                this.m = 4.this.val$limit >= 0 ? 4.this.val$limit : Long.MAX_VALUE;
            }

            public void begin(long j) {
                this.downstream.begin(SliceOps.-$$Nest$smcalcSize(j, 4.this.val$skip, this.m));
            }

            public void accept(double d) {
                long j = this.n;
                if (j == 0) {
                    long j2 = this.m;
                    if (j2 > 0) {
                        this.m = j2 - 1;
                        this.downstream.accept(d);
                        return;
                    }
                    return;
                }
                this.n = j - 1;
            }

            public boolean cancellationRequested() {
                return this.m == 0 || this.downstream.cancellationRequested();
            }
        }

        Sink opWrapSink(int i, Sink sink) {
            return new 1(sink);
        }
    }

    public static DoubleStream makeDouble(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new 4(abstractPipeline, StreamShape.DOUBLE_VALUE, flags(j2), j, j2);
    }

    private static int flags(long j) {
        return (j != -1 ? StreamOpFlag.IS_SHORT_CIRCUIT : 0) | StreamOpFlag.NOT_SIZED;
    }

    private static final class SliceTask extends AbstractShortCircuitTask {
        private volatile boolean completed;
        private final IntFunction generator;
        private final AbstractPipeline op;
        private final long targetOffset;
        private final long targetSize;
        private long thisNodeSize;

        SliceTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction, long j, long j2) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.targetOffset = j;
            this.targetSize = j2;
        }

        SliceTask(SliceTask sliceTask, Spliterator spliterator) {
            super(sliceTask, spliterator);
            this.op = sliceTask.op;
            this.generator = sliceTask.generator;
            this.targetOffset = sliceTask.targetOffset;
            this.targetSize = sliceTask.targetSize;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public SliceTask makeChild(Spliterator spliterator) {
            return new SliceTask(this, spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final Node getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final Node doLeaf() {
            if (isRoot()) {
                Node.Builder makeNodeBuilder = this.op.makeNodeBuilder(StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags) ? this.op.exactOutputSizeIfKnown(this.spliterator) : -1L, this.generator);
                this.helper.copyIntoWithCancel(this.helper.wrapSink(this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder)), this.spliterator);
                return makeNodeBuilder.build();
            }
            Node.Builder makeNodeBuilder2 = this.op.makeNodeBuilder(-1L, this.generator);
            if (this.targetOffset == 0) {
                this.helper.copyIntoWithCancel(this.helper.wrapSink(this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder2)), this.spliterator);
            } else {
                this.helper.wrapAndCopyInto(makeNodeBuilder2, this.spliterator);
            }
            Node build = makeNodeBuilder2.build();
            this.thisNodeSize = build.count();
            this.completed = true;
            this.spliterator = null;
            return build;
        }

        public final void onCompletion(CountedCompleter countedCompleter) {
            Node conc;
            if (!isLeaf()) {
                this.thisNodeSize = ((SliceTask) this.leftChild).thisNodeSize + ((SliceTask) this.rightChild).thisNodeSize;
                if (this.canceled) {
                    this.thisNodeSize = 0L;
                    conc = getEmptyResult();
                } else if (this.thisNodeSize == 0) {
                    conc = getEmptyResult();
                } else if (((SliceTask) this.leftChild).thisNodeSize == 0) {
                    conc = (Node) ((SliceTask) this.rightChild).getLocalResult();
                } else {
                    conc = Nodes.conc(this.op.getOutputShape(), (Node) ((SliceTask) this.leftChild).getLocalResult(), (Node) ((SliceTask) this.rightChild).getLocalResult());
                }
                if (isRoot()) {
                    conc = doTruncate(conc);
                }
                setLocalResult(conc);
                this.completed = true;
            }
            if (this.targetSize >= 0 && !isRoot() && isLeftCompleted(this.targetOffset + this.targetSize)) {
                cancelLaterNodes();
            }
            super.onCompletion(countedCompleter);
        }

        protected void cancel() {
            super.cancel();
            if (this.completed) {
                setLocalResult(getEmptyResult());
            }
        }

        private Node doTruncate(Node node) {
            return node.truncate(this.targetOffset, this.targetSize >= 0 ? Math.min(node.count(), this.targetOffset + this.targetSize) : this.thisNodeSize, this.generator);
        }

        private boolean isLeftCompleted(long j) {
            SliceTask sliceTask;
            long completedSize = this.completed ? this.thisNodeSize : completedSize(j);
            if (completedSize >= j) {
                return true;
            }
            SliceTask sliceTask2 = this;
            for (SliceTask sliceTask3 = (SliceTask) getParent(); sliceTask3 != null; sliceTask3 = (SliceTask) sliceTask3.getParent()) {
                if (sliceTask2 == sliceTask3.rightChild && (sliceTask = (SliceTask) sliceTask3.leftChild) != null) {
                    completedSize += sliceTask.completedSize(j);
                    if (completedSize >= j) {
                        return true;
                    }
                }
                sliceTask2 = sliceTask3;
            }
            return completedSize >= j;
        }

        private long completedSize(long j) {
            if (this.completed) {
                return this.thisNodeSize;
            }
            SliceTask sliceTask = (SliceTask) this.leftChild;
            SliceTask sliceTask2 = (SliceTask) this.rightChild;
            if (sliceTask == null || sliceTask2 == null) {
                return this.thisNodeSize;
            }
            long completedSize = sliceTask.completedSize(j);
            return completedSize >= j ? completedSize : completedSize + sliceTask2.completedSize(j);
        }
    }
}
