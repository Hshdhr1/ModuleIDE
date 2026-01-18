package java.util.stream;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.StreamSpliterators;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class DistinctOps {
    private DistinctOps() {
    }

    class 1 extends ReferencePipeline.StatefulOp {
        1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }

        Node reduce(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return Nodes.node((Collection) ReduceOps.makeRef(new DistinctOps$1$$ExternalSyntheticLambda1(), new DistinctOps$1$$ExternalSyntheticLambda2(), new DistinctOps$1$$ExternalSyntheticLambda3()).evaluateParallel(pipelineHelper, spliterator));
        }

        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            if (StreamOpFlag.DISTINCT.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return pipelineHelper.evaluate(spliterator, false, intFunction);
            }
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return reduce(pipelineHelper, spliterator);
            }
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            ForEachOps.makeRef(new DistinctOps$1$$ExternalSyntheticLambda0(atomicBoolean, concurrentHashMap), false).evaluateParallel(pipelineHelper, spliterator);
            Set keySet = concurrentHashMap.keySet();
            if (atomicBoolean.get()) {
                Set hashSet = new HashSet(keySet);
                hashSet.add(null);
                keySet = hashSet;
            }
            return Nodes.node(keySet);
        }

        static /* synthetic */ void lambda$opEvaluateParallel$0(AtomicBoolean atomicBoolean, ConcurrentHashMap concurrentHashMap, Object obj) {
            if (obj == null) {
                atomicBoolean.set(true);
            } else {
                concurrentHashMap.putIfAbsent(obj, Boolean.TRUE);
            }
        }

        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.DISTINCT.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return pipelineHelper.wrapSpliterator(spliterator);
            }
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return reduce(pipelineHelper, spliterator).spliterator();
            }
            return new StreamSpliterators.DistinctSpliterator(pipelineHelper.wrapSpliterator(spliterator));
        }

        Sink opWrapSink(int i, Sink sink) {
            sink.getClass();
            if (StreamOpFlag.DISTINCT.isKnown(i)) {
                return sink;
            }
            if (StreamOpFlag.SORTED.isKnown(i)) {
                return new 1(sink);
            }
            return new 2(sink);
        }

        class 1 extends Sink.ChainedReference {
            Object lastSeen;
            boolean seenNull;

            1(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.seenNull = false;
                this.lastSeen = null;
                this.downstream.begin(-1L);
            }

            public void end() {
                this.seenNull = false;
                this.lastSeen = null;
                this.downstream.end();
            }

            public void accept(Object obj) {
                if (obj == null) {
                    if (this.seenNull) {
                        return;
                    }
                    this.seenNull = true;
                    Sink sink = this.downstream;
                    this.lastSeen = null;
                    sink.accept((Object) null);
                    return;
                }
                Object obj2 = this.lastSeen;
                if (obj2 == null || !obj.equals(obj2)) {
                    Sink sink2 = this.downstream;
                    this.lastSeen = obj;
                    sink2.accept(obj);
                }
            }
        }

        class 2 extends Sink.ChainedReference {
            Set seen;

            2(Sink sink) {
                super(sink);
            }

            public void begin(long j) {
                this.seen = new HashSet();
                this.downstream.begin(-1L);
            }

            public void end() {
                this.seen = null;
                this.downstream.end();
            }

            public void accept(Object obj) {
                if (this.seen.contains(obj)) {
                    return;
                }
                this.seen.add(obj);
                this.downstream.accept(obj);
            }
        }
    }

    static ReferencePipeline makeRef(AbstractPipeline abstractPipeline) {
        return new 1(abstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_DISTINCT | StreamOpFlag.NOT_SIZED);
    }
}
