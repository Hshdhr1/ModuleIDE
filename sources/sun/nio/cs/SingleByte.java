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
public class SingleByte {
    static /* bridge */ /* synthetic */ CoderResult -$$Nest$smwithResult(CoderResult coderResult, Buffer buffer, int i, Buffer buffer2, int i2) {
        return withResult(coderResult, buffer, i, buffer2, i2);
    }

    private static final CoderResult withResult(CoderResult coderResult, Buffer buffer, int i, Buffer buffer2, int i2) {
        buffer.position(i - buffer.arrayOffset());
        buffer2.position(i2 - buffer2.arrayOffset());
        return coderResult;
    }

    public static final class Decoder extends CharsetDecoder implements ArrayDecoder {
        private final char[] b2c;
        private final boolean isASCIICompatible;
        private char repl;

        public Decoder(Charset charset, char[] cArr) {
            super(charset, 1.0f, 1.0f);
            this.repl = (char) 65533;
            this.b2c = cArr;
            this.isASCIICompatible = false;
        }

        public Decoder(Charset charset, char[] cArr, boolean z) {
            super(charset, 1.0f, 1.0f);
            this.repl = (char) 65533;
            this.b2c = cArr;
            this.isASCIICompatible = z;
        }

        private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
            char[] array2 = charBuffer.array();
            int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
            CoderResult coderResult = CoderResult.UNDERFLOW;
            int i = arrayOffset4 - arrayOffset3;
            if (i < arrayOffset2 - arrayOffset) {
                arrayOffset2 = arrayOffset + i;
                coderResult = CoderResult.OVERFLOW;
            }
            while (arrayOffset < arrayOffset2) {
                char decode = decode(array[arrayOffset]);
                if (decode == 65533) {
                    return SingleByte.-$$Nest$smwithResult(CoderResult.unmappableForLength(1), byteBuffer, arrayOffset, charBuffer, arrayOffset3);
                }
                array2[arrayOffset3] = decode;
                arrayOffset++;
                arrayOffset3++;
            }
            return SingleByte.-$$Nest$smwithResult(coderResult, byteBuffer, arrayOffset, charBuffer, arrayOffset3);
        }

        private CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            int position = byteBuffer.position();
            while (true) {
                try {
                    if (byteBuffer.hasRemaining()) {
                        char decode = decode(byteBuffer.get());
                        if (decode == 65533) {
                            coderResult = CoderResult.unmappableForLength(1);
                            break;
                        }
                        if (!charBuffer.hasRemaining()) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        charBuffer.put(decode);
                        position++;
                    } else {
                        coderResult = CoderResult.UNDERFLOW;
                        break;
                    }
                } finally {
                    byteBuffer.position(position);
                }
            }
            return coderResult;
        }

        protected CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            if (byteBuffer.hasArray() && charBuffer.hasArray()) {
                return decodeArrayLoop(byteBuffer, charBuffer);
            }
            return decodeBufferLoop(byteBuffer, charBuffer);
        }

        public final char decode(int i) {
            return this.b2c[i + 128];
        }

        protected void implReplaceWith(String str) {
            this.repl = str.charAt(0);
        }

        public int decode(byte[] bArr, int i, int i2, char[] cArr) {
            if (i2 > cArr.length) {
                i2 = cArr.length;
            }
            int i3 = 0;
            while (i3 < i2) {
                int i4 = i + 1;
                char decode = decode(bArr[i]);
                cArr[i3] = decode;
                if (decode == 65533) {
                    cArr[i3] = this.repl;
                }
                i3++;
                i = i4;
            }
            return i3;
        }

        public boolean isASCIICompatible() {
            return this.isASCIICompatible;
        }
    }

    public static final class Encoder extends CharsetEncoder implements ArrayEncoder {
        private final char[] c2b;
        private final char[] c2bIndex;
        private final boolean isASCIICompatible;
        private byte repl;
        private Surrogate.Parser sgp;

        public Encoder(Charset charset, char[] cArr, char[] cArr2, boolean z) {
            super(charset, 1.0f, 1.0f);
            this.repl = (byte) 63;
            this.c2b = cArr;
            this.c2bIndex = cArr2;
            this.isASCIICompatible = z;
        }

        public boolean canEncode(char c) {
            return encode(c) != 65533;
        }

        public boolean isLegalReplacement(byte[] bArr) {
            return (bArr.length == 1 && bArr[0] == 63) || super.isLegalReplacement(bArr);
        }

        private CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset2 = charBuffer.arrayOffset() + charBuffer.limit();
            byte[] array2 = byteBuffer.array();
            int arrayOffset3 = byteBuffer.arrayOffset() + byteBuffer.position();
            int min = Math.min((byteBuffer.arrayOffset() + byteBuffer.limit()) - arrayOffset3, arrayOffset2 - arrayOffset);
            while (true) {
                int i = min - 1;
                if (min <= 0) {
                    return SingleByte.-$$Nest$smwithResult(arrayOffset < arrayOffset2 ? CoderResult.OVERFLOW : CoderResult.UNDERFLOW, charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                }
                char c = array[arrayOffset];
                int encode = encode(c);
                if (encode == 65533) {
                    if (Character.isSurrogate(c)) {
                        if (this.sgp == null) {
                            this.sgp = new Surrogate.Parser();
                        }
                        if (this.sgp.parse(c, array, arrayOffset, arrayOffset2) < 0) {
                            return SingleByte.-$$Nest$smwithResult(this.sgp.error(), charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                        }
                        return SingleByte.-$$Nest$smwithResult(this.sgp.unmappableResult(), charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                    }
                    return SingleByte.-$$Nest$smwithResult(CoderResult.unmappableForLength(1), charBuffer, arrayOffset, byteBuffer, arrayOffset3);
                }
                array2[arrayOffset3] = (byte) encode;
                arrayOffset++;
                arrayOffset3++;
                min = i;
            }
        }

        private CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        int encode = encode(c);
                        if (encode == 65533) {
                            if (Character.isSurrogate(c)) {
                                if (this.sgp == null) {
                                    this.sgp = new Surrogate.Parser();
                                }
                                if (this.sgp.parse(c, charBuffer) < 0) {
                                    coderResult = this.sgp.error();
                                } else {
                                    coderResult = this.sgp.unmappableResult();
                                }
                            } else {
                                coderResult = CoderResult.unmappableForLength(1);
                            }
                        } else {
                            if (!byteBuffer.hasRemaining()) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) encode);
                            position++;
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

        protected CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            if (charBuffer.hasArray() && byteBuffer.hasArray()) {
                return encodeArrayLoop(charBuffer, byteBuffer);
            }
            return encodeBufferLoop(charBuffer, byteBuffer);
        }

        public final int encode(char c) {
            char c2 = this.c2bIndex[c >> '\b'];
            if (c2 == 65533) {
                return 65533;
            }
            return this.c2b[c2 + (c & 255)];
        }

        protected void implReplaceWith(byte[] bArr) {
            this.repl = bArr[0];
        }

        public int encode(char[] cArr, int i, int i2, byte[] bArr) {
            int min = Math.min(i2, bArr.length) + i;
            int i3 = 0;
            while (i < min) {
                int i4 = i + 1;
                char c = cArr[i];
                int encode = encode(c);
                if (encode != 65533) {
                    bArr[i3] = (byte) encode;
                    i3++;
                    i = i4;
                } else {
                    if (Character.isHighSurrogate(c) && i4 < min && Character.isLowSurrogate(cArr[i4])) {
                        if (i2 > bArr.length) {
                            min++;
                            i2--;
                        }
                        i += 2;
                    } else {
                        i = i4;
                    }
                    bArr[i3] = this.repl;
                    i3++;
                }
            }
            return i3;
        }

        public int encodeFromLatin1(byte[] bArr, int i, int i2, byte[] bArr2) {
            int min = Math.min(i2, bArr2.length) + i;
            int i3 = 0;
            while (i < min) {
                int i4 = i + 1;
                int encode = encode((char) (bArr[i] & 255));
                if (encode == 65533) {
                    bArr2[i3] = this.repl;
                    i3++;
                } else {
                    bArr2[i3] = (byte) encode;
                    i3++;
                }
                i = i4;
            }
            return i3;
        }

        public int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2) {
            int min = Math.min(i2, bArr2.length) + i;
            int i3 = 0;
            while (i < min) {
                int i4 = i + 1;
                char c = StringUTF16.getChar(bArr, i);
                int encode = encode(c);
                if (encode != 65533) {
                    bArr2[i3] = (byte) encode;
                    i3++;
                    i = i4;
                } else {
                    if (Character.isHighSurrogate(c) && i4 < min && Character.isLowSurrogate(StringUTF16.getChar(bArr, i4))) {
                        if (i2 > bArr2.length) {
                            min++;
                            i2--;
                        }
                        i += 2;
                    } else {
                        i = i4;
                    }
                    bArr2[i3] = this.repl;
                    i3++;
                }
            }
            return i3;
        }

        public boolean isASCIICompatible() {
            return this.isASCIICompatible;
        }
    }

    public static void initC2B(char[] cArr, char[] cArr2, char[] cArr3, char[] cArr4) {
        int i = 0;
        for (int i2 = 0; i2 < cArr4.length; i2++) {
            cArr4[i2] = 65533;
        }
        for (int i3 = 0; i3 < cArr3.length; i3++) {
            cArr3[i3] = 65533;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < cArr.length) {
            char c = cArr[i4];
            if (c != 65533) {
                int i6 = c >> '\b';
                if (cArr4[i6] == 65533) {
                    cArr4[i6] = (char) i5;
                    i5 += 256;
                }
                cArr3[cArr4[i6] + (c & 255)] = (char) (i4 >= 128 ? i4 - 128 : i4 + 128);
            }
            i4++;
        }
        if (cArr2 != null) {
            while (i < cArr2.length) {
                int i7 = i + 1;
                char c2 = cArr2[i];
                i += 2;
                char c3 = cArr2[i7];
                int i8 = c3 >> '\b';
                if (cArr4[i8] == 65533) {
                    cArr4[i8] = (char) i5;
                    i5 += 256;
                }
                cArr3[cArr4[i8] + (c3 & 255)] = c2;
            }
        }
    }
}
