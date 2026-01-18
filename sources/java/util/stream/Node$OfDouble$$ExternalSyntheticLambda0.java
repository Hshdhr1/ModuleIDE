package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.DoubleConsumer;
import java.util.stream.Node;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Node$OfDouble$$ExternalSyntheticLambda0 implements DoubleConsumer {
    public final void accept(double d) {
        Node.OfDouble.-CC.lambda$truncate$0(d);
    }

    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return DoubleConsumer.-CC.$default$andThen(this, doubleConsumer);
    }
}
