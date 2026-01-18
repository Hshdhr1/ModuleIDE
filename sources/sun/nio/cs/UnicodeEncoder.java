package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class UnicodeEncoder extends CharsetEncoder {
    protected static final int BIG = 0;
    protected static final char BYTE_ORDER_MARK = 65279;
    protected static final int LITTLE = 1;
    protected static final char REVERSED_MARK = 65534;
    private int byteOrder;
    private boolean needsMark;
    private final Surrogate.Parser sgp;
    private boolean usesMark;

    /* JADX WARN: Illegal instructions before constructor call */
    protected UnicodeEncoder(Charset charset, int i, boolean z) {
        byte[] bArr;
        float f = z ? 4.0f : 2.0f;
        if (i == 0) {
            bArr = new byte[]{-1, -3};
        } else {
            bArr = new byte[]{-3, -1};
        }
        super(charset, 2.0f, f, bArr);
        this.sgp = new Surrogate.Parser();
        this.needsMark = z;
        this.usesMark = z;
        this.byteOrder = i;
    }

    private void put(char c, ByteBuffer byteBuffer) {
        if (this.byteOrder == 0) {
            byteBuffer.put((byte) (c >> '\b'));
            byteBuffer.put((byte) (c & 255));
        } else {
            byteBuffer.put((byte) (c & 255));
            byteBuffer.put((byte) (c >> '\b'));
        }
    }

    protected CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
        CoderResult coderResult;
        int position = charBuffer.position();
        if (this.needsMark && charBuffer.hasRemaining()) {
            if (byteBuffer.remaining() < 2) {
                return CoderResult.OVERFLOW;
            }
            put((char) 65279, byteBuffer);
            this.needsMark = false;
        }
        while (true) {
            try {
                if (charBuffer.hasRemaining()) {
                    char c = charBuffer.get();
                    if (!Character.isSurrogate(c)) {
                        if (byteBuffer.remaining() < 2) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        position++;
                        put(c, byteBuffer);
                    } else {
                        int parse = this.sgp.parse(c, charBuffer);
                        if (parse < 0) {
                            coderResult = this.sgp.error();
                            break;
                        }
                        if (byteBuffer.remaining() < 4) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        position += 2;
                        put(Character.highSurrogate(parse), byteBuffer);
                        put(Character.lowSurrogate(parse), byteBuffer);
                    }
                } else {
                    coderResult = CoderResult.UNDERFLOW;
                    break;
                }
            } finally {
                charBuffer.position(position);
            }
        }
        return coderResult;
    }

    protected void implReset() {
        this.needsMark = this.usesMark;
    }

    public boolean canEncode(char c) {
        return !Character.isSurrogate(c);
    }
}
