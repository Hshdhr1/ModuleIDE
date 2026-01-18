package sun.nio.ch;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.security.PrivilegedAction;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class ThreadPool$$ExternalSyntheticLambda0 implements PrivilegedAction {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ ThreadPool$$ExternalSyntheticLambda0(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final Object run() {
        return ThreadPool.lambda$defaultThreadFactory$1(this.f$0);
    }
}
