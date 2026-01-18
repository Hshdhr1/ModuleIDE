package sun.nio.cs;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ArrayDecoder {

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static boolean $default$isASCIICompatible(ArrayDecoder _this) {
            return false;
        }
    }

    int decode(byte[] bArr, int i, int i2, char[] cArr);

    boolean isASCIICompatible();
}
