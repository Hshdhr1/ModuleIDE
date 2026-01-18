package java.util.stream;

import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.stream.Node;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class PipelineHelper {
    abstract void copyInto(Sink sink, Spliterator spliterator);

    abstract boolean copyIntoWithCancel(Sink sink, Spliterator spliterator);

    abstract Node evaluate(Spliterator spliterator, boolean z, IntFunction intFunction);

    abstract long exactOutputSizeIfKnown(Spliterator spliterator);

    abstract StreamShape getSourceShape();

    abstract int getStreamAndOpFlags();

    abstract Node.Builder makeNodeBuilder(long j, IntFunction intFunction);

    abstract Sink wrapAndCopyInto(Sink sink, Spliterator spliterator);

    abstract Sink wrapSink(Sink sink);

    abstract Spliterator wrapSpliterator(Spliterator spliterator);

    PipelineHelper() {
    }
}
