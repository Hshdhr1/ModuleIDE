package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda1 implements Sink.OfInt {
    public final /* synthetic */ SpinedBuffer.OfInt f$0;

    public /* synthetic */ StreamSpliterators$IntWrappingSpliterator$$ExternalSyntheticLambda1(SpinedBuffer.OfInt ofInt) {
        this.f$0 = ofInt;
    }

    public /* synthetic */ void accept(double d) {
        Sink.-CC.$default$accept(this, d);
    }

    public final void accept(int i) {
        this.f$0.accept(i);
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
