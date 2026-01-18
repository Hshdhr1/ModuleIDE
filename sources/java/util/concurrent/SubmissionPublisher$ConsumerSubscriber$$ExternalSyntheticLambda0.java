package java.util.concurrent;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.BiConsumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class SubmissionPublisher$ConsumerSubscriber$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ Flow.Subscription f$0;

    public /* synthetic */ SubmissionPublisher$ConsumerSubscriber$$ExternalSyntheticLambda0(Flow.Subscription subscription) {
        this.f$0 = subscription;
    }

    public final void accept(Object obj, Object obj2) {
        SubmissionPublisher.ConsumerSubscriber.lambda$onSubscribe$0(this.f$0, (Void) obj, (Throwable) obj2);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        return BiConsumer.-CC.$default$andThen(this, biConsumer);
    }
}
