package sun.misc;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = 1, kind = 27, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarUnsafe$$ExternalSyntheticBackport0 {
    public static /* synthetic */ AssertionError m(String str, Throwable th) {
        try {
            return (AssertionError) AssertionError.class.getDeclaredConstructor(new Class[]{String.class, Throwable.class}).newInstance(new Object[]{str, th});
        } catch (Exception unused) {
            return new AssertionError(str);
        }
    }
}
