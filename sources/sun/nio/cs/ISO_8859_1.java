package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ISO_8859_1 extends Charset implements HistoricallyNamedCharset {
    public static final ISO_8859_1 INSTANCE = new ISO_8859_1();

    public ISO_8859_1() {
        super("ISO-8859-1", StandardCharsets.aliases_ISO_8859_1());
    }

    public String historicalName() {
        return "ISO8859_1";
    }

    public boolean contains(Charset charset) {
        return (charset instanceof US_ASCII) || (charset instanceof ISO_8859_1);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    private static class Decoder extends CharsetDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* synthetic */ Decoder(Charset charset, ISO_8859_1-IA r2) {
            this(charset);
        }

        private Decoder(Charset charset) {
            super(charset, 1.0f, 1.0f);
        }

        private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
            if (arrayOffset > arrayOffset2) {
                arrayOffset = arrayOffset2;
            }
            char[] array2 = charBuffer.array();
            int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
            if (arrayOffset3 > arrayOffset4) {
                arrayOffset3 = arrayOffset4;
            }
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        byte b = array[arrayOffset];
                        if (arrayOffset3 >= arrayOffset4) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        int i = arrayOffset3 + 1;
                        try {
                            array2[arrayOffset3] = (char) (b & 255);
                            arrayOffset++;
                            arrayOffset3 = i;
                        } catch (Throwable th) {
                            th = th;
                            arrayOffset3 = i;
                            byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
                            charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    coderResult = CoderResult.UNDERFLOW;
                    break;
                }
            }
            arrayOffset -= byteBuffer.arrayOffset();
            byteBuffer.position(arrayOffset);
            arrayOffset3 -= charBuffer.arrayOffset();
            byteBuffer = (CharBuffer) charBuffer.position(arrayOffset3);
            return coderResult;
        }

        private CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            int position = byteBuffer.position();
            while (true) {
                try {
                    if (byteBuffer.hasRemaining()) {
                        byte b = byteBuffer.get();
                        if (!charBuffer.hasRemaining()) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        charBuffer.put((char) (b & 255));
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
    }

    private static class Encoder extends CharsetEncoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Surrogate.Parser sgp;

        /* synthetic */ Encoder(Charset charset, ISO_8859_1-IA r2) {
            this(charset);
        }

        public boolean canEncode(char c) {
            return c <= 255;
        }

        public boolean isLegalReplacement(byte[] bArr) {
            return true;
        }

        private Encoder(Charset charset) {
            super(charset, 1.0f, 1.0f);
            this.sgp = new Surrogate.Parser();
        }

        private static int encodeISOArray(char[] cArr, int i, byte[] bArr, int i2, int i3) {
            if (i3 <= 0) {
                return 0;
            }
            encodeISOArrayCheck(cArr, i, bArr, i2, i3);
            return implEncodeISOArray(cArr, i, bArr, i2, i3);
        }

        private static int implEncodeISOArray(char[] cArr, int i, byte[] bArr, int i2, int i3) {
            int i4 = 0;
            while (i4 < i3) {
                int i5 = i + 1;
                char c = cArr[i];
                if (c > 255) {
                    break;
                }
                bArr[i2] = (byte) c;
                i4++;
                i = i5;
                i2++;
            }
            return i4;
        }

        private static void encodeISOArrayCheck(char[] cArr, int i, byte[] bArr, int i2, int i3) {
            cArr.getClass();
            bArr.getClass();
            if (i < 0 || i >= cArr.length) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            if (i2 < 0 || i2 >= bArr.length) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            int i4 = (i + i3) - 1;
            if (i4 < 0 || i4 >= cArr.length) {
                throw new ArrayIndexOutOfBoundsException(i4);
            }
            int i5 = (i2 + i3) - 1;
            if (i5 < 0 || i5 >= bArr.length) {
                throw new ArrayIndexOutOfBoundsException(i5);
            }
        }

        private CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset();
            int position = charBuffer.position() + arrayOffset;
            int limit = charBuffer.limit() + arrayOffset;
            if (position > limit) {
                position = limit;
            }
            byte[] array2 = byteBuffer.array();
            int arrayOffset2 = byteBuffer.arrayOffset();
            int position2 = byteBuffer.position() + arrayOffset2;
            int limit2 = byteBuffer.limit() + arrayOffset2;
            if (position2 > limit2) {
                position2 = limit2;
            }
            int i = limit2 - position2;
            int i2 = limit - position;
            if (i >= i2) {
                i = i2;
            }
            try {
                int encodeISOArray = encodeISOArray(array, position, array2, position2, i);
                int i3 = position + encodeISOArray;
                int i4 = position2 + encodeISOArray;
                if (encodeISOArray != i) {
                    if (this.sgp.parse(array[i3], array, i3, limit) < 0) {
                        coderResult = this.sgp.error();
                    } else {
                        coderResult = this.sgp.unmappableResult();
                    }
                } else if (i < i2) {
                    coderResult = CoderResult.OVERFLOW;
                } else {
                    coderResult = CoderResult.UNDERFLOW;
                }
                charBuffer.position(i3 - arrayOffset);
                byteBuffer.position(i4 - arrayOffset2);
                return coderResult;
            } catch (Throwable th) {
                charBuffer.position(position - arrayOffset);
                byteBuffer.position(position2 - arrayOffset2);
                throw th;
            }
        }

        private CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        if (c <= 255) {
                            if (!byteBuffer.hasRemaining()) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) c);
                            position++;
                        } else if (this.sgp.parse(c, charBuffer) < 0) {
                            coderResult = this.sgp.error();
                        } else {
                            coderResult = this.sgp.unmappableResult();
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
    }
}
