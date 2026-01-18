package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class US_ASCII extends Charset implements HistoricallyNamedCharset {
    public static final US_ASCII INSTANCE = new US_ASCII();

    public US_ASCII() {
        super("US-ASCII", StandardCharsets.aliases_US_ASCII());
    }

    public String historicalName() {
        return "ASCII";
    }

    public boolean contains(Charset charset) {
        return charset instanceof US_ASCII;
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this, null);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this, null);
    }

    private static class Decoder extends CharsetDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* synthetic */ Decoder(Charset charset, US_ASCII-IA r2) {
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
                        if (b < 0) {
                            coderResult = CoderResult.malformedForLength(1);
                            break;
                        }
                        if (arrayOffset3 >= arrayOffset4) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        int i = arrayOffset3 + 1;
                        try {
                            array2[arrayOffset3] = (char) b;
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
                        if (b >= 0) {
                            if (!charBuffer.hasRemaining()) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            charBuffer.put((char) b);
                            position++;
                        } else {
                            coderResult = CoderResult.malformedForLength(1);
                            break;
                        }
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

        /* synthetic */ Encoder(Charset charset, US_ASCII-IA r2) {
            this(charset);
        }

        public boolean canEncode(char c) {
            return c < 128;
        }

        private Encoder(Charset charset) {
            super(charset, 1.0f, 1.0f);
            this.sgp = new Surrogate.Parser();
        }

        public boolean isLegalReplacement(byte[] bArr) {
            return (bArr.length == 1 && bArr[0] >= 0) || super.isLegalReplacement(bArr);
        }

        private CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset2 = charBuffer.arrayOffset() + charBuffer.limit();
            if (arrayOffset > arrayOffset2) {
                arrayOffset = arrayOffset2;
            }
            byte[] array2 = byteBuffer.array();
            int arrayOffset3 = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset4 = byteBuffer.arrayOffset() + byteBuffer.limit();
            if (arrayOffset3 > arrayOffset4) {
                arrayOffset3 = arrayOffset4;
            }
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        char c = array[arrayOffset];
                        if (c < 128) {
                            if (arrayOffset3 >= arrayOffset4) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            array2[arrayOffset3] = (byte) c;
                            arrayOffset++;
                            arrayOffset3++;
                        } else if (this.sgp.parse(c, array, arrayOffset, arrayOffset2) < 0) {
                            coderResult = this.sgp.error();
                        } else {
                            coderResult = this.sgp.unmappableResult();
                        }
                    } finally {
                        charBuffer.position(arrayOffset - charBuffer.arrayOffset());
                        byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
                    }
                } else {
                    coderResult = CoderResult.UNDERFLOW;
                    break;
                }
            }
            return coderResult;
        }

        private CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        if (c < 128) {
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
