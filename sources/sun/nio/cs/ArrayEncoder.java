package sun.nio.cs;

import com.android.tools.r8.annotations.SynthesizedClassV2;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface ArrayEncoder {

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static int $default$encodeFromLatin1(ArrayEncoder _this, byte[] bArr, int i, int i2, byte[] bArr2) {
            return -1;
        }

        public static int $default$encodeFromUTF16(ArrayEncoder _this, byte[] bArr, int i, int i2, byte[] bArr2) {
            return -1;
        }

        public static boolean $default$isASCIICompatible(ArrayEncoder _this) {
            return false;
        }
    }

    int encode(char[] cArr, int i, int i2, byte[] bArr);

    int encodeFromLatin1(byte[] bArr, int i, int i2, byte[] bArr2);

    int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2);

    boolean isASCIICompatible();
}
