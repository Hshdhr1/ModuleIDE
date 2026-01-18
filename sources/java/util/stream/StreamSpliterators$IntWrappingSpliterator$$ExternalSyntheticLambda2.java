package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BooleanSupplier;
import java.util.stream.StreamSpliterators;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda2 implements BooleanSupplier {
    public final /* synthetic */ StreamSpliterators.IntWrappingSpliterator f$0;

    public /* synthetic */ StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda2(StreamSpliterators.IntWrappingSpliterator intWrappingSpliterator) {
        this.f$0 = intWrappingSpliterator;
    }

    public final boolean getAsBoolean() {
        return this.f$0.lambda$initPartialTraversalState$0$java-util-stream-StreamSpliterators$IntWrappingSpliterator();
    }
}
