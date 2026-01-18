package kotlin.internal.jdk7;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 27, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final /* synthetic */ class JDK7PlatformImplementations$$ExternalSyntheticBackport1 {
    public static /* synthetic */ Throwable[] m(Throwable th) {
        try {
            return (Throwable[]) Throwable.class.getDeclaredMethod("getSuppressed", new Class[0]).invoke(th, new Object[0]);
        } catch (Exception unused) {
            return new Throwable[0];
        }
    }
}
