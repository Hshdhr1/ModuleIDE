package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UTF_32Coder {
    protected static final int BIG = 1;
    protected static final int BOM_BIG = 65279;
    protected static final int BOM_LITTLE = -131072;
    protected static final int LITTLE = 2;
    protected static final int NONE = 0;

    UTF_32Coder() {
    }

    protected static class Decoder extends CharsetDecoder {
        private int currentBO;
        private int expectedBO;

        protected Decoder(Charset charset, int i) {
            super(charset, 0.25f, 1.0f);
            this.expectedBO = i;
            this.currentBO = 0;
        }

        private int getCP(ByteBuffer byteBuffer) {
            int i;
            int i2;
            if (this.currentBO == 1) {
                i = ((byteBuffer.get() & 255) << 24) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 8);
                i2 = byteBuffer.get() & 255;
            } else {
                i = (byteBuffer.get() & 255) | ((byteBuffer.get() & 255) << 8) | ((byteBuffer.get() & 255) << 16);
                i2 = (byteBuffer.get() & 255) << 24;
            }
            return i2 | i;
        }

        protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            if (byteBuffer.remaining() < 4) {
                return CoderResult.UNDERFLOW;
            }
            int position = byteBuffer.position();
            try {
                if (this.currentBO == 0) {
                    int i = ((byteBuffer.get() & 255) << 24) | ((byteBuffer.get() & 255) << 16) | ((byteBuffer.get() & 255) << 8) | (byteBuffer.get() & 255);
                    if (i == 65279 && this.expectedBO != 2) {
                        this.currentBO = 1;
                    } else if (i == -131072 && this.expectedBO != 1) {
                        this.currentBO = 2;
                    } else {
                        int i2 = this.expectedBO;
                        if (i2 == 0) {
                            this.currentBO = 1;
                        } else {
                            this.currentBO = i2;
                        }
                        byteBuffer.position(position);
                    }
                    position += 4;
                }
                while (true) {
                    if (byteBuffer.remaining() >= 4) {
                        int cp = getCP(byteBuffer);
                        if (Character.isBmpCodePoint(cp)) {
                            if (!charBuffer.hasRemaining()) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            position += 4;
                            charBuffer.put((char) cp);
                        } else if (Character.isValidCodePoint(cp)) {
                            if (charBuffer.remaining() < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            position += 4;
                            charBuffer.put(Character.highSurrogate(cp));
                            charBuffer.put(Character.lowSurrogate(cp));
                        } else {
                            coderResult = CoderResult.malformedForLength(4);
                            break;
                        }
                    } else {
                        coderResult = CoderResult.UNDERFLOW;
                        break;
                    }
                }
                return coderResult;
            } finally {
                byteBuffer.position(position);
            }
        }

        protected void implReset() {
            this.currentBO = 0;
        }
    }

    protected static class Encoder extends CharsetEncoder {
        private int byteOrder;
        private boolean doBOM;
        private boolean doneBOM;

        protected void put(int i, ByteBuffer byteBuffer) {
            if (this.byteOrder == 1) {
                byteBuffer.put((byte) (i >> 24));
                byteBuffer.put((byte) (i >> 16));
                byteBuffer.put((byte) (i >> 8));
                byteBuffer.put((byte) i);
                return;
            }
            byteBuffer.put((byte) i);
            byteBuffer.put((byte) (i >> 8));
            byteBuffer.put((byte) (i >> 16));
            byteBuffer.put((byte) (i >> 24));
        }

        /* JADX WARN: Illegal instructions before constructor call */
        protected Encoder(Charset charset, int i, boolean z) {
            byte[] bArr;
            float f = z ? 8.0f : 4.0f;
            if (i == 1) {
                bArr = new byte[]{0, 0, -1, -3};
            } else {
                bArr = new byte[]{-3, -1, 0, 0};
            }
            super(charset, 4.0f, f, bArr);
            this.byteOrder = i;
            this.doBOM = z;
            this.doneBOM = !z;
        }

        protected CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            if (!this.doneBOM && charBuffer.hasRemaining()) {
                if (byteBuffer.remaining() < 4) {
                    return CoderResult.OVERFLOW;
                }
                put(65279, byteBuffer);
                this.doneBOM = true;
            }
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        if (!Character.isSurrogate(c)) {
                            if (byteBuffer.remaining() < 4) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            position++;
                            put(c, byteBuffer);
                        } else if (Character.isHighSurrogate(c)) {
                            if (!charBuffer.hasRemaining()) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            char c2 = charBuffer.get();
                            if (Character.isLowSurrogate(c2)) {
                                if (byteBuffer.remaining() < 4) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                position += 2;
                                put(Character.toCodePoint(c, c2), byteBuffer);
                            } else {
                                coderResult = CoderResult.malformedForLength(1);
                                break;
                            }
                        } else {
                            coderResult = CoderResult.malformedForLength(1);
                            break;
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
            this.doneBOM = !this.doBOM;
        }
    }
}
