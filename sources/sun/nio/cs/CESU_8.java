package sun.nio.cs;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import sun.nio.cs.ArrayDecoder;
import sun.nio.cs.ArrayEncoder;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class CESU_8 extends Unicode {
    static /* bridge */ /* synthetic */ void -$$Nest$smupdatePositions(Buffer buffer, int i, Buffer buffer2, int i2) {
        updatePositions(buffer, i, buffer2, i2);
    }

    public CESU_8() {
        super("CESU-8", StandardCharsets.aliases_CESU_8());
    }

    public String historicalName() {
        return "CESU8";
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    private static final void updatePositions(Buffer buffer, int i, Buffer buffer2, int i2) {
        buffer.position(i - buffer.arrayOffset());
        buffer2.position(i2 - buffer2.arrayOffset());
    }

    private static class Decoder extends CharsetDecoder implements ArrayDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* synthetic */ Decoder(Charset charset, CESU_8-IA r2) {
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
            return (i == 240 && i2 == 144) || (i2 & 192) != 128;
        }

        private static boolean isMalformed4_3(int i) {
            return (i & 192) != 128;
        }

        private static boolean isNotContinuation(int i) {
            return (i & 192) != 128;
        }

        public /* synthetic */ boolean isASCIICompatible() {
            return ArrayDecoder.-CC.$default$isASCIICompatible(this);
        }

        private Decoder(Charset charset) {
            super(charset, 1.0f, 1.0f);
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
            CESU_8.-$$Nest$smupdatePositions(byteBuffer, i, charBuffer, i2);
            return malformedN;
        }

        private static CoderResult malformed(ByteBuffer byteBuffer, int i, int i2) {
            byteBuffer.position(i);
            CoderResult malformedN = malformedN(byteBuffer, i2);
            byteBuffer.position(i);
            return malformedN;
        }

        private static CoderResult malformedForLength(ByteBuffer byteBuffer, int i, CharBuffer charBuffer, int i2, int i3) {
            CESU_8.-$$Nest$smupdatePositions(byteBuffer, i, charBuffer, i2);
            return CoderResult.malformedForLength(i3);
        }

        private static CoderResult malformedForLength(ByteBuffer byteBuffer, int i, int i2) {
            byteBuffer.position(i);
            return CoderResult.malformedForLength(i2);
        }

        private static CoderResult xflow(Buffer buffer, int i, int i2, Buffer buffer2, int i3, int i4) {
            CESU_8.-$$Nest$smupdatePositions(buffer, i, buffer2, i3);
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
            int i2;
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
            int i3 = arrayOffset;
            int i4 = arrayOffset3;
            while (i3 < arrayOffset2) {
                byte b2 = array[i3];
                if (b2 < 0) {
                    int i5 = arrayOffset4;
                    byteBuffer2 = byteBuffer;
                    i = i5;
                    charBuffer2 = charBuffer;
                    if ((b2 >> 5) != -2 || (b2 & 30) == 0) {
                        if ((b2 >> 4) != -2) {
                            return malformed(byteBuffer2, i3, charBuffer2, i4, 1);
                        }
                        int i6 = arrayOffset2 - i3;
                        if (i6 < 3 || i4 >= i) {
                            if (i6 > 1 && isMalformed3_2(b2, array[i3 + 1])) {
                                return malformedForLength(byteBuffer2, i3, charBuffer2, i4, 1);
                            }
                            return xflow(byteBuffer2, i3, arrayOffset2, charBuffer2, i4, 3);
                        }
                        byte b3 = array[i3 + 1];
                        byte b4 = array[i3 + 2];
                        if (isMalformed3(b2, b3, b4)) {
                            return malformed(byteBuffer2, i3, charBuffer2, i4, 3);
                        }
                        i2 = i4 + 1;
                        array2[i4] = (char) (((b3 << 6) ^ (b2 << 12)) ^ ((-123008) ^ b4));
                        i3 += 3;
                    } else {
                        if (arrayOffset2 - i3 < 2 || i4 >= i) {
                            return xflow(byteBuffer2, i3, arrayOffset2, charBuffer2, i4, 2);
                        }
                        byte b5 = array[i3 + 1];
                        if (isNotContinuation(b5)) {
                            return malformedForLength(byteBuffer2, i3, charBuffer2, i4, 1);
                        }
                        i2 = i4 + 1;
                        array2[i4] = (char) ((b5 ^ (b2 << 6)) ^ 3968);
                        i3 += 2;
                    }
                    i4 = i2;
                } else {
                    if (i4 >= arrayOffset4) {
                        return xflow(byteBuffer, i3, arrayOffset2, charBuffer, i4, 1);
                    }
                    int i7 = arrayOffset4;
                    byteBuffer2 = byteBuffer;
                    i = i7;
                    charBuffer2 = charBuffer;
                    array2[i4] = (char) b2;
                    i3++;
                    i4++;
                }
                ByteBuffer byteBuffer3 = byteBuffer2;
                arrayOffset4 = i;
                byteBuffer = byteBuffer3;
                charBuffer = charBuffer2;
            }
            return xflow(byteBuffer, i3, arrayOffset2, charBuffer, i4, 0);
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
                        charBuffer.put((char) (((b << 12) ^ (b2 << 6)) ^ ((-123008) ^ b3)));
                        position += 3;
                    } else {
                        return malformed(byteBuffer, position, 1);
                    }
                } else {
                    if (limit - position < 2 || charBuffer.remaining() < 1) {
                        return xflow(byteBuffer, position, 2);
                    }
                    byte b4 = byteBuffer.get();
                    if (isNotContinuation(b4)) {
                        return malformedForLength(byteBuffer, position, 1);
                    }
                    charBuffer.put((char) (((b << 6) ^ b4) ^ 3968));
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

        public int decode(byte[] bArr, int i, int i2, char[] cArr) {
            int i3;
            int i4;
            int i5 = i + i2;
            int min = Math.min(i2, cArr.length);
            int i6 = 0;
            while (i6 < min) {
                byte b = bArr[i];
                if (b < 0) {
                    break;
                }
                i++;
                cArr[i6] = (char) b;
                i6++;
            }
            ByteBuffer byteBuffer = null;
            while (i < i5) {
                int i7 = i + 1;
                byte b2 = bArr[i];
                if (b2 >= 0) {
                    i3 = i6 + 1;
                    cArr[i6] = (char) b2;
                } else if ((b2 >> 5) != -2 || (b2 & 30) == 0) {
                    if ((b2 >> 4) == -2) {
                        int i8 = i + 2;
                        if (i8 < i5) {
                            byte b3 = bArr[i7];
                            int i9 = i + 3;
                            byte b4 = bArr[i8];
                            if (isMalformed3(b2, b3, b4)) {
                                if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                    return -1;
                                }
                                i4 = i6 + 1;
                                cArr[i6] = replacement().charAt(0);
                                byteBuffer = getByteBuffer(byteBuffer, bArr, i);
                                i += malformedN(byteBuffer, 3).length();
                                i6 = i4;
                            } else {
                                cArr[i6] = (char) (((b3 << 6) ^ (b2 << 12)) ^ ((-123008) ^ b4));
                                i6++;
                                i = i9;
                            }
                        } else {
                            if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                return -1;
                            }
                            if (i7 < i5 && isMalformed3_2(b2, bArr[i7])) {
                                i3 = i6 + 1;
                                cArr[i6] = replacement().charAt(0);
                            } else {
                                int i10 = i6 + 1;
                                cArr[i6] = replacement().charAt(0);
                                return i10;
                            }
                        }
                    } else {
                        if (malformedInputAction() != CodingErrorAction.REPLACE) {
                            return -1;
                        }
                        i3 = i6 + 1;
                        cArr[i6] = replacement().charAt(0);
                    }
                } else if (i7 < i5) {
                    int i11 = i + 2;
                    byte b5 = bArr[i7];
                    if (isNotContinuation(b5)) {
                        if (malformedInputAction() != CodingErrorAction.REPLACE) {
                            return -1;
                        }
                        i4 = i6 + 1;
                        cArr[i6] = replacement().charAt(0);
                        i++;
                        i6 = i4;
                    } else {
                        cArr[i6] = (char) ((b5 ^ (b2 << 6)) ^ 3968);
                        i6++;
                        i = i11;
                    }
                } else {
                    if (malformedInputAction() != CodingErrorAction.REPLACE) {
                        return -1;
                    }
                    int i12 = i6 + 1;
                    cArr[i6] = replacement().charAt(0);
                    return i12;
                }
                i6 = i3;
                i = i7;
            }
            return i6;
        }
    }

    private static class Encoder extends CharsetEncoder implements ArrayEncoder {
        private char[] c2;
        private Surrogate.Parser sgp;

        /* synthetic */ Encoder(Charset charset, CESU_8-IA r2) {
            this(charset);
        }

        public /* synthetic */ int encodeFromLatin1(byte[] bArr, int i, int i2, byte[] bArr2) {
            return ArrayEncoder.-CC.$default$encodeFromLatin1(this, bArr, i, i2, bArr2);
        }

        public /* synthetic */ int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2) {
            return ArrayEncoder.-CC.$default$encodeFromUTF16(this, bArr, i, i2, bArr2);
        }

        public /* synthetic */ boolean isASCIICompatible() {
            return ArrayEncoder.-CC.$default$isASCIICompatible(this);
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
            CESU_8.-$$Nest$smupdatePositions(charBuffer, i, byteBuffer, i2);
            return CoderResult.OVERFLOW;
        }

        private static CoderResult overflow(CharBuffer charBuffer, int i) {
            charBuffer.position(i);
            return CoderResult.OVERFLOW;
        }

        private static void to3Bytes(byte[] bArr, int i, char c) {
            bArr[i] = (byte) ((c >> '\f') | 224);
            bArr[i + 1] = (byte) (((c >> 6) & 63) | 128);
            bArr[i + 2] = (byte) ((c & '?') | 128);
        }

        private static void to3Bytes(ByteBuffer byteBuffer, char c) {
            byteBuffer.put((byte) ((c >> '\f') | 224));
            byteBuffer.put((byte) (((c >> 6) & 63) | 128));
            byteBuffer.put((byte) ((c & '?') | 128));
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
                        CESU_8.-$$Nest$smupdatePositions(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                        return this.sgp.error();
                    }
                    if (arrayOffset4 - arrayOffset3 < 6) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    to3Bytes(array2, arrayOffset3, Character.highSurrogate(parse));
                    to3Bytes(array2, arrayOffset3 + 3, Character.lowSurrogate(parse));
                    arrayOffset3 += 6;
                    arrayOffset++;
                } else {
                    if (arrayOffset4 - arrayOffset3 < 3) {
                        return overflow(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    to3Bytes(array2, arrayOffset3, c2);
                    arrayOffset3 += 3;
                }
                arrayOffset++;
            }
            CESU_8.-$$Nest$smupdatePositions(charBuffer, arrayOffset, byteBuffer, arrayOffset3);
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
                    if (byteBuffer.remaining() < 6) {
                        return overflow(charBuffer, position);
                    }
                    to3Bytes(byteBuffer, Character.highSurrogate(parse));
                    to3Bytes(byteBuffer, Character.lowSurrogate(parse));
                    position++;
                } else {
                    if (byteBuffer.remaining() < 3) {
                        return overflow(charBuffer, position);
                    }
                    to3Bytes(byteBuffer, c);
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

        public int encode(char[] cArr, int i, int i2, byte[] bArr) {
            int i3;
            char c;
            int i4 = i + i2;
            int min = Math.min(i2, bArr.length);
            int i5 = 0;
            while (i5 < min && (c = cArr[i]) < 128) {
                i++;
                bArr[i5] = (byte) c;
                i5++;
            }
            while (i < i4) {
                int i6 = i + 1;
                char c2 = cArr[i];
                if (c2 < 128) {
                    i3 = i5 + 1;
                    bArr[i5] = (byte) c2;
                } else {
                    if (c2 < 2048) {
                        int i7 = i5 + 1;
                        bArr[i5] = (byte) ((c2 >> 6) | 192);
                        i5 += 2;
                        bArr[i7] = (byte) ((c2 & '?') | 128);
                    } else if (Character.isSurrogate(c2)) {
                        if (this.sgp == null) {
                            this.sgp = new Surrogate.Parser();
                        }
                        int parse = this.sgp.parse(c2, cArr, i, i4);
                        if (parse < 0) {
                            if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                return -1;
                            }
                            i3 = i5 + 1;
                            bArr[i5] = replacement()[0];
                        } else {
                            to3Bytes(bArr, i5, Character.highSurrogate(parse));
                            to3Bytes(bArr, i5 + 3, Character.lowSurrogate(parse));
                            i6 = i + 2;
                            i3 = i5 + 6;
                        }
                    } else {
                        to3Bytes(bArr, i5, c2);
                        i5 += 3;
                    }
                    i = i6;
                }
                i5 = i3;
                i = i6;
            }
            return i5;
        }
    }
}
