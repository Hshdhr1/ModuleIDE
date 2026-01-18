package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UTF_16LE_BOM extends Unicode {
    public UTF_16LE_BOM() {
        super("x-UTF-16LE-BOM", StandardCharsets.aliases_UTF_16LE_BOM());
    }

    public String historicalName() {
        return "UnicodeLittle";
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    private static class Decoder extends UnicodeDecoder {
        public Decoder(Charset charset) {
            super(charset, 0, 2);
        }
    }

    private static class Encoder extends UnicodeEncoder {
        public Encoder(Charset charset) {
            super(charset, 1, true);
        }
    }
}
