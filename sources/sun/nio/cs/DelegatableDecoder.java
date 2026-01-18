package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CoderResult;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DelegatableDecoder {
    CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer);

    CoderResult implFlush(CharBuffer charBuffer);

    void implReset();
}
