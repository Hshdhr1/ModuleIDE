package sun.nio.ch;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 27, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class SourceChannelImpl$$ExternalSyntheticBackport0 {
    public static /* synthetic */ int m(int i, int i2, int i3) {
        if (i >= 0 && i2 >= 0 && i3 >= 0 && i <= i3 - i2) {
            return i;
        }
        throw new IndexOutOfBoundsException("Range [" + i + ", " + i + " + " + i2 + ") out of bounds for length " + i3);
    }
}
