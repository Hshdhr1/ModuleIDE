package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.DistinctOps;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DistinctOps$1$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ AtomicBoolean f$0;
    public final /* synthetic */ ConcurrentHashMap f$1;

    public /* synthetic */ DistinctOps$1$$ExternalSyntheticLambda0(AtomicBoolean atomicBoolean, ConcurrentHashMap concurrentHashMap) {
        this.f$0 = atomicBoolean;
        this.f$1 = concurrentHashMap;
    }

    public final void accept(Object obj) {
        DistinctOps.1.lambda$opEvaluateParallel$0(this.f$0, this.f$1, obj);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.-CC.$default$andThen(this, consumer);
    }
}
