package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class StreamSpliterators$LongWrappingSpliterator$$ExternalSyntheticLambda1 implements Sink.OfLong {
    public final /* synthetic */ SpinedBuffer.OfLong f$0;

    public /* synthetic */ StreamSpliterators$LongWrappingSpliterator$$ExternalSyntheticLambda1(SpinedBuffer.OfLong ofLong) {
        this.f$0 = ofLong;
    }

    public /* synthetic */ void accept(double d) {
        Sink.-CC.$default$accept(this, d);
    }

    public /* synthetic */ void accept(int i) {
        Sink.-CC.$default$accept((Sink) this, i);
    }

    public final void accept(long j) {
        this.f$0.accept(j);
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

    public /* synthetic */ void begin(long j) {
        Sink.-CC.$default$begin(this, j);
    }

    public /* synthetic */ boolean cancellationRequested() {
        return Sink.-CC.$default$cancellationRequested(this);
    }

    public /* synthetic */ void end() {
        Sink.-CC.$default$end(this);
    }
}
