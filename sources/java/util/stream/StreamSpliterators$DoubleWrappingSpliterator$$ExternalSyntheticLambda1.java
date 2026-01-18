package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda1 implements Sink.OfDouble {
    public final /* synthetic */ SpinedBuffer.OfDouble f$0;

    public /* synthetic */ StreamSpliterators$DoubleWrappingSpliterator$$ExternalSyntheticLambda1(SpinedBuffer.OfDouble ofDouble) {
        this.f$0 = ofDouble;
    }

    public final void accept(double d) {
        this.f$0.accept(d);
    }

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
