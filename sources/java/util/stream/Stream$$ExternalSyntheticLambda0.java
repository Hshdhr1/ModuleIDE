package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class Stream$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Stream f$0;

    public /* synthetic */ Stream$$ExternalSyntheticLambda0(Stream stream) {
        this.f$0 = stream;
    }

    public final void run() {
        this.f$0.close();
    }
}
