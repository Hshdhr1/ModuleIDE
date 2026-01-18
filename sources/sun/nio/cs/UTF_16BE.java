package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class UTF_16BE extends Unicode {
    public /* bridge */ /* synthetic */ boolean contains(Charset charset) {
        return super.contains(charset);
    }

    public UTF_16BE() {
        super("UTF-16BE", StandardCharsets.aliases_UTF_16BE());
    }

    public String historicalName() {
        return "UnicodeBigUnmarked";
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    private static class Decoder extends UnicodeDecoder {
        public Decoder(Charset charset) {
            super(charset, 1);
        }
    }

    private static class Encoder extends UnicodeEncoder {
        public Encoder(Charset charset) {
            super(charset, 0, false);
        }
    }
}
