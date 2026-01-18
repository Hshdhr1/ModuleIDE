package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.UTF_32Coder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class UTF_32LE_BOM extends Unicode {
    public /* bridge */ /* synthetic */ boolean contains(Charset charset) {
        return super.contains(charset);
    }

    public UTF_32LE_BOM() {
        super("X-UTF-32LE-BOM", StandardCharsets.aliases_UTF_32LE_BOM());
    }

    public String historicalName() {
        return "X-UTF-32LE-BOM";
    }

    public CharsetDecoder newDecoder() {
        return new UTF_32Coder.Decoder(this, 2);
    }

    public CharsetEncoder newEncoder() {
        return new UTF_32Coder.Encoder(this, 2, true);
    }
}
