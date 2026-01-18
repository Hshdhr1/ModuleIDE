package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.stream.StreamSpliterators;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class StreamSpliterators$DistinctSpliterator$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ StreamSpliterators.DistinctSpliterator f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ StreamSpliterators$DistinctSpliterator$$ExternalSyntheticLambda0(StreamSpliterators.DistinctSpliterator distinctSpliterator, Consumer consumer) {
        this.f$0 = distinctSpliterator;
        this.f$1 = consumer;
    }

    public final void accept(Object obj) {
        this.f$0.lambda$forEachRemaining$0$java-util-stream-StreamSpliterators$DistinctSpliterator(this.f$1, obj);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.-CC.$default$andThen(this, consumer);
    }
}
