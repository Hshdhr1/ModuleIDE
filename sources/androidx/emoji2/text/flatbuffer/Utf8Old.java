package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes27.dex */
public class Utf8Old extends Utf8 {
    private static final ThreadLocal CACHE = ThreadLocal.withInitial(new Utf8Old$$ExternalSyntheticLambda0());

    private static class Cache {
        CharSequence lastInput = null;
        ByteBuffer lastOutput = null;
        final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

        Cache() {
        }
    }

    static /* synthetic */ Cache lambda$static$0() {
        return new Cache();
    }

    public int encodedLength(CharSequence in) {
        Cache cache = (Cache) CACHE.get();
        int estimated = (int) (in.length() * cache.encoder.maxBytesPerChar());
        if (cache.lastOutput == null || cache.lastOutput.capacity() < estimated) {
            cache.lastOutput = ByteBuffer.allocate(Math.max(128, estimated));
        }
        cache.lastOutput.clear();
        cache.lastInput = in;
        CharBuffer wrap = in instanceof CharBuffer ? (CharBuffer) in : CharBuffer.wrap(in);
        CoderResult result = cache.encoder.encode(wrap, cache.lastOutput, true);
        if (result.isError()) {
            try {
                result.throwException();
            } catch (CharacterCodingException e) {
                throw new IllegalArgumentException("bad character encoding", e);
            }
        }
        cache.lastOutput.flip();
        return cache.lastOutput.remaining();
    }

    public void encodeUtf8(CharSequence in, ByteBuffer out) {
        Cache cache = (Cache) CACHE.get();
        if (cache.lastInput != in) {
            encodedLength(in);
        }
        out.put(cache.lastOutput);
    }

    public String decodeUtf8(ByteBuffer buffer, int offset, int length) {
        CharsetDecoder decoder = ((Cache) CACHE.get()).decoder;
        decoder.reset();
        ByteBuffer buffer2 = buffer.duplicate();
        buffer2.position(offset);
        buffer2.limit(offset + length);
        try {
            CharBuffer result = decoder.decode(buffer2);
            return result.toString();
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Bad encoding", e);
        }
    }
}
