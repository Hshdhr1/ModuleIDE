package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Node;
import java.util.stream.Nodes;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Nodes$CollectorTask$OfDouble$$ExternalSyntheticLambda1 implements BinaryOperator {
    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction.-CC.$default$andThen(this, function);
    }

    public final Object apply(Object obj, Object obj2) {
        return new Nodes.ConcNode.OfDouble((Node.OfDouble) obj, (Node.OfDouble) obj2);
    }
}
