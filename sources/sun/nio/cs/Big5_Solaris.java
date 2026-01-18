package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import sun.nio.cs.DoubleByte;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Big5_Solaris extends Charset implements HistoricallyNamedCharset {
    static char[][] b2c = null;
    private static volatile boolean b2cInitialized = false;
    static char[] b2cSB = null;
    static char[] c2b = null;
    static char[] c2bIndex = null;
    private static volatile boolean c2bInitialized = false;

    public Big5_Solaris() {
        super("x-Big5-Solaris", StandardCharsets.aliases_Big5_Solaris());
    }

    public String historicalName() {
        return "Big5_Solaris";
    }

    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof Big5) || (charset instanceof Big5_Solaris);
    }

    public CharsetDecoder newDecoder() {
        initb2c();
        return new DoubleByte.Decoder(this, b2c, b2cSB, 64, 254, true);
    }

    public CharsetEncoder newEncoder() {
        initc2b();
        return new DoubleByte.Encoder(this, c2b, c2bIndex, true);
    }

    static void initb2c() {
        if (b2cInitialized) {
            return;
        }
        synchronized (Big5_Solaris.class) {
            if (b2cInitialized) {
                return;
            }
            Big5.initb2c();
            char[][] cArr = (char[][]) Big5.b2c.clone();
            b2c = cArr;
            int[] iArr = {63958, 30849, 63959, 37561, 63960, 35023, 63961, 22715, 63962, 24658, 63963, 31911, 63964, 23290};
            if (cArr[249] == DoubleByte.B2C_UNMAPPABLE) {
                char[] cArr2 = new char[191];
                b2c[249] = cArr2;
                Arrays.fill(cArr2, (char) 65533);
            }
            int i = 0;
            while (i < 14) {
                char[] cArr3 = b2c[249];
                int i2 = i + 1;
                int i3 = iArr[i] & 191;
                i += 2;
                cArr3[i3] = (char) iArr[i2];
            }
            b2cSB = Big5.b2cSB;
            b2cInitialized = true;
        }
    }

    static void initc2b() {
        if (c2bInitialized) {
            return;
        }
        synchronized (Big5_Solaris.class) {
            if (c2bInitialized) {
                return;
            }
            Big5.initc2b();
            c2b = (char[]) Big5.c2b.clone();
            c2bIndex = (char[]) Big5.c2bIndex.clone();
            int[] iArr = {30849, 63958, 37561, 63959, 35023, 63960, 22715, 63961, 24658, 63962, 31911, 63963, 23290, 63964};
            int i = 0;
            while (i < 14) {
                int i2 = i + 1;
                int i3 = iArr[i];
                i += 2;
                c2b[c2bIndex[i3 >> 8] + (i3 & 255)] = (char) iArr[i2];
            }
            c2bInitialized = true;
        }
    }
}
