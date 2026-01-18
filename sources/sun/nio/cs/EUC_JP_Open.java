package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.DoubleByte;
import sun.nio.cs.EUC_JP;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class EUC_JP_Open extends Charset implements HistoricallyNamedCharset {
    public EUC_JP_Open() {
        super("x-eucJP-Open", StandardCharsets.aliases_EUC_JP_Open());
    }

    public String historicalName() {
        return "EUC_JP_Solaris";
    }

    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof JIS_X_0201) || (charset instanceof EUC_JP);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    private static class Decoder extends EUC_JP.Decoder {
        private static DoubleByte.Decoder DEC0208_Solaris = (DoubleByte.Decoder) new JIS_X_0208_Solaris().newDecoder();
        private static DoubleByte.Decoder DEC0212_Solaris = (DoubleByte.Decoder) new JIS_X_0212_Solaris().newDecoder();

        /* synthetic */ Decoder(Charset charset, EUC_JP_Open-IA r2) {
            this(charset);
        }

        private Decoder(Charset charset) {
            super(charset, 0.5f, 1.0f, DEC0201, DEC0208, DEC0212_Solaris);
        }

        protected char decodeDouble(int i, int i2) {
            char decodeDouble = super.decodeDouble(i, i2);
            return decodeDouble == 65533 ? DEC0208_Solaris.decodeDouble(i - 128, i2 - 128) : decodeDouble;
        }
    }

    private static class Encoder extends EUC_JP.Encoder {
        private static DoubleByte.Encoder ENC0208_Solaris = (DoubleByte.Encoder) new JIS_X_0208_Solaris().newEncoder();
        private static DoubleByte.Encoder ENC0212_Solaris = (DoubleByte.Encoder) new JIS_X_0212_Solaris().newEncoder();

        /* synthetic */ Encoder(Charset charset, EUC_JP_Open-IA r2) {
            this(charset);
        }

        private Encoder(Charset charset) {
            super(charset);
        }

        protected int encodeDouble(char c) {
            int encodeDouble = super.encodeDouble(c);
            if (encodeDouble != 65533) {
                return encodeDouble;
            }
            int encodeChar = ENC0208_Solaris.encodeChar(c);
            if (encodeChar == 65533 || encodeChar <= 29952) {
                return encodeChar == 65533 ? encodeChar : encodeChar + 32896;
            }
            return ENC0212_Solaris.encodeChar(c) + 9404544;
        }
    }
}
