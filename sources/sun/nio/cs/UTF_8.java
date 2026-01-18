package sun.nio.cs;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class UTF_8 extends Unicode {
    public static final UTF_8 INSTANCE = new UTF_8();

    public /* bridge */ /* synthetic */ boolean contains(Charset charset) {
        return super.contains(charset);
    }

    public UTF_8() {
        super("UTF-8", StandardCharsets.aliases_UTF_8());
    }

    public String historicalName() {
        return "UTF8";
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    static final void updatePositions(Buffer buffer, int i, Buffer buffer2, int i2) {
        buffer.position(i - buffer.arrayOffset());
        buffer2.position(i2 - buffer2.arrayOffset());
    }

    private static class Decoder extends CharsetDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* synthetic */ Decoder(Charset charset, UTF_8-IA r2) {
            this(charset);
        }

        private static boolean isMalformed3(int i, int i2, int i3) {
            return ((i != -32 || (i2 & 224) != 128) && (i2 & 192) == 128 && (i3 & 192) == 128) ? false : true;
        }

        private static boolean isMalformed3_2(int i, int i2) {
            return (i == -32 && (i2 & 224) == 128) || (i2 & 192) != 128;
        }

        private static boolean isMalformed4(int i, int i2, int i3) {
            return ((i & 192) == 128 && (i2 & 192) == 128 && (i3 & 192) == 128) ? false : true;
        }

        private static boolean isMalformed4_2(int i, int i2) {
            if (i != 240 || (i2 >= 144 && i2 <= 191)) {
                return (i == 244 && (i2 & 240) != 128) || (i2 & 192) != 128;
            }
            return true;
        }

        private static boolean isMalformed4_3(int i) {
            return (i & 192) != 128;
        }

        private static boolean isNotContinuation(int i) {
            return (i & 192) != 128;
        }

        private Decoder(Charset charset) {
            super(charset, 1.0f, 1.0f);
        }

        private static CoderResult lookupN(ByteBuffer byteBuffer, int i) {
            for (int i2 = 1; i2 < i; i2++) {
                if (isNotContinuation(byteBuffer.get())) {
                    return CoderResult.malformedForLength(i2);
                }
            }
            return CoderResult.malformedForLength(i);
        }

        private static CoderResult malformedN(ByteBuffer byteBuffer, int i) {
            int i2 = 1;
            if (i == 1 || i == 2) {
                return CoderResult.malformedForLength(1);
            }
            if (i == 3) {
                byte b = byteBuffer.get();
                byte b2 = byteBuffer.get();
                if ((b != -32 || (b2 & 224) != 128) && !isNotContinuation(b2)) {
                    i2 = 2;
                }
                return CoderResult.malformedForLength(i2);
            }
            if (i != 4) {
                return null;
            }
            int i3 = byteBuffer.get() & 255;
            byte b3 = byteBuffer.get();
            int i4 = b3 & 255;
            if (i3 > 244 || ((i3 == 240 && (i4 < 144 || i4 > 191)) || ((i3 == 244 && (b3 & 240) != 128) || isNotContinuation(i4)))) {
                return CoderResult.malformedForLength(1);
            }
            if (isNotContinuation(byteBuffer.get())) {
                return CoderResult.malformedForLength(2);
            }
            return CoderResult.malformedForLength(3);
        }

        private static CoderResult malformed(ByteBuffer byteBuffer, int i, CharBuffer charBuffer, int i2, int i3) {
            byteBuffer.position(i - byteBuffer.arrayOffset());
            CoderResult malformedN = malformedN(byteBuffer, i3);
            UTF_8.updatePositions(byteBuffer, i, charBuffer, i2);
            return malformedN;
        }

        private static CoderResult malformed(ByteBuffer byteBuffer, int i, int i2) {
            byteBuffer.position(i);
            CoderResult malformedN = malformedN(byteBuffer, i2);
            byteBuffer.position(i);
            return malformedN;
        }

        private static CoderResult malformedForLength(ByteBuffer byteBuffer, int i, CharBuffer charBuffer, int i2, int i3) {
            UTF_8.updatePositions(byteBuffer, i, charBuffer, i2);
            return CoderResult.malformedForLength(i3);
        }

        private static CoderResult malformedForLength(ByteBuffer byteBuffer, int i, int i2) {
            byteBuffer.position(i);
            return CoderResult.malformedForLength(i2);
        }

        private static CoderResult xflow(Buffer buffer, int i, int i2, Buffer buffer2, int i3, int i4) {
            UTF_8.updatePositions(buffer, i, buffer2, i3);
            return (i4 == 0 || i2 - i < i4) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
        }

        private static CoderResult xflow(Buffer buffer, int i, int i2) {
            buffer.position(i);
            return (i2 == 0 || buffer.remaining() < i2) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
        }

        private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            ByteBuffer byteBuffer2;
            int i;
            CharBuffer charBuffer2;
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
            char[] array2 = charBuffer.array();
            int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
            int min = Math.min(arrayOffset2 - arrayOffset, arrayOffset4 - arrayOffset3) + arrayOffset3;
            while (arrayOffset3 < min) {
                byte b = array[arrayOffset];
                if (b < 0) {
                    break;
                }
                arrayOffset++;
                array2[arrayOffset3] = (char) b;
                arrayOffset3++;
            }
            int i2 = arrayOffset;
            int i3 = arrayOffset3;
            while (i2 < arrayOffset2) {
                byte b2 = array[i2];
                if (b2 < 0) {
                    int i4 = arrayOffset4;
                    byteBuffer2 = byteBuffer;
                    i = i4;
                    charBuffer2 = charBuffer;
                    if ((b2 >> 5) != -2 || (b2 & 30) == 0) {
                        if ((b2 >> 4) == -2) {
                            int i5 = arrayOffset2 - i2;
                            if (i5 < 3 || i3 >= i) {
                                if (i5 > 1 && isMalformed3_2(b2, array[i2 + 1])) {
                                    return malformedForLength(byteBuffer2, i2, charBuffer2, i3, 1);
                                }
                                return xflow(byteBuffer2, i2, arrayOffset2, charBuffer2, i3, 3);
                            }
                            byte b3 = array[i2 + 1];
                            byte b4 = array[i2 + 2];
                            if (isMalformed3(b2, b3, b4)) {
                                return malformed(byteBuffer2, i2, charBuffer2, i3, 3);
                            }
                            char c = (char) (((b3 << 6) ^ (b2 << 12)) ^ ((-123008) ^ b4));
                            if (Character.isSurrogate(c)) {
                                return malformedForLength(byteBuffer2, i2, charBuffer2, i3, 3);
                            }
                            array2[i3] = c;
                            i2 += 3;
                            i3++;
                        } else {
                            if ((b2 >> 3) != -2) {
                                return malformed(byteBuffer2, i2, charBuffer2, i3, 1);
                            }
                            int i6 = arrayOffset2 - i2;
                            if (i6 < 4 || i - i3 < 2) {
                                int i7 = b2 & 255;
                                if (i7 > 244 || (i6 > 1 && isMalformed4_2(i7, array[i2 + 1] & 255))) {
                                    return malformedForLength(byteBuffer2, i2, charBuffer2, i3, 1);
                                }
                                if (i6 > 2 && isMalformed4_3(array[i2 + 2])) {
                                    return malformedForLength(byteBuffer2, i2, charBuffer2, i3, 2);
                                }
                                return xflow(byteBuffer2, i2, arrayOffset2, charBuffer2, i3, 4);
                            }
                            byte b5 = array[i2 + 1];
                            byte b6 = array[i2 + 2];
                            byte b7 = array[i2 + 3];
                            int i8 = (((b2 << 18) ^ (b5 << 12)) ^ (b6 << 6)) ^ (3678080 ^ b7);
                            if (isMalformed4(b5, b6, b7) || !Character.isSupplementaryCodePoint(i8)) {
                                return malformed(byteBuffer2, i2, charBuffer2, i3, 4);
                            }
                            int i9 = i3 + 1;
                            array2[i3] = Character.highSurrogate(i8);
                            i3 += 2;
                            array2[i9] = Character.lowSurrogate(i8);
                            i2 += 4;
                        }
                    } else {
                        if (arrayOffset2 - i2 < 2 || i3 >= i) {
                            return xflow(byteBuffer2, i2, arrayOffset2, charBuffer2, i3, 2);
                        }
                        byte b8 = array[i2 + 1];
                        if (isNotContinuation(b8)) {
                            return malformedForLength(byteBuffer2, i2, charBuffer2, i3, 1);
                        }
                        array2[i3] = (char) ((b8 ^ (b2 << 6)) ^ 3968);
                        i2 += 2;
                        i3++;
                    }
                } else {
                    if (i3 >= arrayOffset4) {
                        return xflow(byteBuffer, i2, arrayOffset2, charBuffer, i3, 1);
                    }
                    int i10 = arrayOffset4;
                    byteBuffer2 = byteBuffer;
                    i = i10;
                    charBuffer2 = charBuffer;
                    array2[i3] = (char) b2;
                    i2++;
                    i3++;
                }
                ByteBuffer byteBuffer3 = byteBuffer2;
                arrayOffset4 = i;
                byteBuffer = byteBuffer3;
                charBuffer = charBuffer2;
            }
            return xflow(byteBuffer, i2, arrayOffset2, charBuffer, i3, 0);
        }

        private CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            int position = byteBuffer.position();
            int limit = byteBuffer.limit();
            while (position < limit) {
                byte b = byteBuffer.get();
                if (b >= 0) {
                    if (charBuffer.remaining() < 1) {
                        return xflow(byteBuffer, position, 1);
                    }
                    charBuffer.put((char) b);
                    position++;
                } else if ((b >> 5) != -2 || (b & 30) == 0) {
                    if ((b >> 4) == -2) {
                        int i = limit - position;
                        if (i < 3 || charBuffer.remaining() < 1) {
                            if (i > 1 && isMalformed3_2(b, byteBuffer.get())) {
                                return malformedForLength(byteBuffer, position, 1);
                            }
                            return xflow(byteBuffer, position, 3);
                        }
                        byte b2 = byteBuffer.get();
                        byte b3 = byteBuffer.get();
                        if (isMalformed3(b, b2, b3)) {
                            return malformed(byteBuffer, position, 3);
                        }
                        char c = (char) (((b << 12) ^ (b2 << 6)) ^ ((-123008) ^ b3));
                        if (Character.isSurrogate(c)) {
                            return malformedForLength(byteBuffer, position, 3);
                        }
                        charBuffer.put(c);
                        position += 3;
                    } else if ((b >> 3) == -2) {
                        int i2 = limit - position;
                        if (i2 < 4 || charBuffer.remaining() < 2) {
                            int i3 = b & 255;
                            if (i3 > 244 || (i2 > 1 && isMalformed4_2(i3, byteBuffer.get() & 255))) {
                                return malformedForLength(byteBuffer, position, 1);
                            }
                            if (i2 > 2 && isMalformed4_3(byteBuffer.get())) {
                                return malformedForLength(byteBuffer, position, 2);
                            }
                            return xflow(byteBuffer, position, 4);
                        }
                        byte b4 = byteBuffer.get();
                        byte b5 = byteBuffer.get();
                        byte b6 = byteBuffer.get();
                        int i4 = (((b << 18) ^ (b4 << 12)) ^ (b5 << 6)) ^ (3678080 ^ b6);
                        if (isMalformed4(b4, b5, b6) || !Character.isSupplementaryCodePoint(i4)) {
                            return malformed(byteBuffer, position, 4);
                        }
                        charBuffer.put(Character.highSurrogate(i4));
                        charBuffer.put(Character.lowSurrogate(i4));
                        position += 4;
                    } else {
                        return malformed(byteBuffer, position, 1);
                    }
                } else {
                    if (limit - position < 2 || charBuffer.remaining() < 1) {
                        return xflow(byteBuffer, position, 2);
                    }
                    byte b7 = byteBuffer.get();
                    if (isNotContinuation(b7)) {
                        return malformedForLength(byteBuffer, position, 1);
                    }
                    charBuffer.put((char) (((b << 6) ^ b7) ^ 3968));
                    position += 2;
                }
            }
            return xflow(byteBuffer, position, 0);
        }

        protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            if (byteBuffer.hasArray() && charBuffer.hasArray()) {
                return decodeArrayLoop(byteBuffer, charBuffer);
            }
            return decodeBufferLoop(byteBuffer, charBuffer);
        }

        private static ByteBuffer getByteBuffer(ByteBuffer byteBuffer, byte[] bArr, int i) {
            if (byteBuffer == null) {
                byteBuffer = ByteBuffer.wrap(bArr);
            }
            byteBuffer.position(i);
            return byteBuffer;
        }
    }

    private static final class Encoder extends CharsetEncoder {
        private Surrogate.Parser sgp;

        /* synthetic */ Encoder(Charset charset, UTF_8-IA r2) {
            this(charset);
        }

        private Encoder(Charset charset) {
            super(charset, 1.1f, 3.0f);
        }

        public boolean canEncode(char c) {
            return !Character.isSurrogate(c);
        }

        public boolean isLegalReplacement(byte[] bArr) {
            return (bArr.length == 1 && bArr[0] >= 0) || super.isLegalReplacement(bArr);
        }

        private static CoderResult overflow(CharBuffer charBuffer, int i, ByteBuffer byteBuffer, int i2) {
            UTF_8.updatePositions(charBuffer, i, byteBuffer, i2);
            return CoderResult.OVERFLOW;
        }

        private static CoderResult overflow(CharBuffer charBuffer, int i) {
            charBuffer.position(i);
            return CoderResult.OVERFLOW;
        }

        private CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            char c;
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset2 = charBuffer.arrayOffset() + charBuffer.limit();
            byte[] array2 = byteBuffer.array();
            int arrayOffset3 = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset4 = byteBuffer.arrayOffset() + byteBuffer.limit();
            int min = Math.min(arrayOffset2 - arrayOffset, arrayOffset4 - arrayOffset3) + arrayOffset3;
            while (arrayOffset3 < min && (c = array[arrayOffset]) < 128) {
                arrayOffset++;
                array2[arrayOffset3] = (byte) c;
                arrayOffset3++;
            }
            while (arrayOffset < arrayOffset2) {
                char c2 = array[arrayOffset];
                if (c2 < 128) {
                    if (arrayOffset3 >= arrayOffset4) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    array2[arrayOffset3] = (byte) c2;
                    arrayOffset3++;
                } else if (c2 < 2048) {
                    if (arrayOffset4 - arrayOffset3 < 2) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    int i = arrayOffset3 + 1;
                    array2[arrayOffset3] = (byte) ((c2 >> 6) | 192);
                    arrayOffset3 += 2;
                    array2[i] = (byte) ((c2 & '?') | 128);
                } else if (Character.isSurrogate(c2)) {
                    if (this.sgp == null) {
                        this.sgp = new Surrogate.Parser();
                    }
                    int parse = this.sgp.parse(c2, array, arrayOffset, arrayOffset2);
                    if (parse < 0) {
                        UTF_8.updatePositions(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                        return this.sgp.error();
                    }
                    if (arrayOffset4 - arrayOffset3 < 4) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    array2[arrayOffset3] = (byte) ((parse >> 18) | 240);
                    array2[arrayOffset3 + 1] = (byte) (((parse >> 12) & 63) | 128);
                    int i2 = arrayOffset3 + 3;
                    array2[arrayOffset3 + 2] = (byte) (((parse >> 6) & 63) | 128);
                    arrayOffset3 += 4;
                    array2[i2] = (byte) ((parse & 63) | 128);
                    arrayOffset++;
                } else {
                    if (arrayOffset4 - arrayOffset3 < 3) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    array2[arrayOffset3] = (byte) ((c2 >> '\f') | 224);
                    int i3 = arrayOffset3 + 2;
                    array2[arrayOffset3 + 1] = (byte) (((c2 >> 6) & 63) | 128);
                    arrayOffset3 += 3;
                    array2[i3] = (byte) ((c2 & '?') | 128);
                }
                arrayOffset++;
            }
            UTF_8.updatePositions(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
            return CoderResult.UNDERFLOW;
        }

        private CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            int position = charBuffer.position();
            while (charBuffer.hasRemaining()) {
                char c = charBuffer.get();
                if (c < 128) {
                    if (!byteBuffer.hasRemaining()) {
                        return overflow(charBuffer, position);
                    }
                    byteBuffer.put((byte) c);
                } else if (c < 2048) {
                    if (byteBuffer.remaining() < 2) {
                        return overflow(charBuffer, position);
                    }
                    byteBuffer.put((byte) ((c >> 6) | 192));
                    byteBuffer.put((byte) ((c & '?') | 128));
                } else if (Character.isSurrogate(c)) {
                    if (this.sgp == null) {
                        this.sgp = new Surrogate.Parser();
                    }
                    int parse = this.sgp.parse(c, charBuffer);
                    if (parse < 0) {
                        charBuffer.position(position);
                        return this.sgp.error();
                    }
                    if (byteBuffer.remaining() < 4) {
                        return overflow(charBuffer, position);
                    }
                    byteBuffer.put((byte) ((parse >> 18) | 240));
                    byteBuffer.put((byte) (((parse >> 12) & 63) | 128));
                    byteBuffer.put((byte) (((parse >> 6) & 63) | 128));
                    byteBuffer.put((byte) ((parse & 63) | 128));
                    position++;
                } else {
                    if (byteBuffer.remaining() < 3) {
                        return overflow(charBuffer, position);
                    }
                    byteBuffer.put((byte) ((c >> '\f') | 224));
                    byteBuffer.put((byte) (((c >> 6) & 63) | 128));
                    byteBuffer.put((byte) ((c & '?') | 128));
                }
                position++;
            }
            charBuffer.position(position);
            return CoderResult.UNDERFLOW;
        }

        protected final CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            if (charBuffer.hasArray() && byteBuffer.hasArray()) {
                return encodeArrayLoop(charBuffer, byteBuffer);
            }
            return encodeBufferLoop(charBuffer, byteBuffer);
        }
    }
}
