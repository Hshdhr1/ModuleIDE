package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.EUC_JP;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class EUC_JP_LINUX extends Charset implements HistoricallyNamedCharset {
    public EUC_JP_LINUX() {
        super("x-euc-jp-linux", StandardCharsets.aliases_EUC_JP_LINUX());
    }

    public String historicalName() {
        return "EUC_JP_LINUX";
    }

    public boolean contains(Charset charset) {
        return (charset instanceof JIS_X_0201) || charset.name().equals("US-ASCII") || (charset instanceof EUC_JP_LINUX);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    private static class Decoder extends EUC_JP.Decoder {
        /* synthetic */ Decoder(Charset charset, EUC_JP_LINUX-IA r2) {
            this(charset);
        }

        private Decoder(Charset charset) {
            super(charset, 1.0f, 1.0f, DEC0201, DEC0208, null);
        }
    }

    private static class Encoder extends EUC_JP.Encoder {
        /* synthetic */ Encoder(Charset charset, EUC_JP_LINUX-IA r2) {
            this(charset);
        }

        private Encoder(Charset charset) {
            super(charset, 2.0f, 2.0f, ENC0201, ENC0208, null);
        }
    }
}
