package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import sun.nio.cs.DoubleByte;
import sun.nio.cs.SingleByte;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class EUC_JP extends Charset implements HistoricallyNamedCharset {
    public EUC_JP() {
        super("EUC-JP", StandardCharsets.aliases_EUC_JP());
    }

    public String historicalName() {
        return "EUC_JP";
    }

    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof JIS_X_0201) || (charset instanceof JIS_X_0208) || (charset instanceof JIS_X_0212) || (charset instanceof EUC_JP);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    static class Decoder extends CharsetDecoder implements DelegatableDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final SingleByte.Decoder DEC0201 = (SingleByte.Decoder) new JIS_X_0201().newDecoder();
        static final DoubleByte.Decoder DEC0208 = (DoubleByte.Decoder) new JIS_X_0208().newDecoder();
        static final DoubleByte.Decoder DEC0212 = (DoubleByte.Decoder) new JIS_X_0212().newDecoder();
        private final SingleByte.Decoder dec0201;
        private final DoubleByte.Decoder dec0208;
        private final DoubleByte.Decoder dec0212;

        protected Decoder(Charset charset) {
            this(charset, 0.5f, 1.0f, DEC0201, DEC0208, DEC0212);
        }

        protected Decoder(Charset charset, float f, float f2, SingleByte.Decoder decoder, DoubleByte.Decoder decoder2, DoubleByte.Decoder decoder3) {
            super(charset, f, f2);
            this.dec0201 = decoder;
            this.dec0208 = decoder2;
            this.dec0212 = decoder3;
        }

        protected char decodeDouble(int i, int i2) {
            if (i != 142) {
                return this.dec0208.decodeDouble(i - 128, i2 - 128);
            }
            if (i2 < 128) {
                return (char) 65533;
            }
            return this.dec0201.decode((byte) i2);
        }

        private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            char decodeDouble;
            int i;
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
                        int i2 = b & 255;
                        if ((b & 128) == 0) {
                            decodeDouble = (char) i2;
                            i = 1;
                        } else if (i2 == 143) {
                            if (arrayOffset + 3 > arrayOffset2) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            int i3 = array[arrayOffset + 1] & 255;
                            int i4 = array[arrayOffset + 2] & 255;
                            DoubleByte.Decoder decoder = this.dec0212;
                            if (decoder == null) {
                                coderResult = CoderResult.unmappableForLength(3);
                                break;
                            }
                            decodeDouble = decoder.decodeDouble(i3 - 128, i4 - 128);
                            i = 3;
                        } else {
                            if (arrayOffset + 2 > arrayOffset2) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            decodeDouble = decodeDouble(i2, array[arrayOffset + 1] & 255);
                            i = 2;
                        }
                        if (decodeDouble == 65533) {
                            coderResult = CoderResult.unmappableForLength(i);
                            break;
                        }
                        int i5 = arrayOffset3 + 1;
                        if (i5 > arrayOffset4) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        try {
                            array2[arrayOffset3] = decodeDouble;
                            arrayOffset += i;
                            arrayOffset3 = i5;
                        } catch (Throwable th) {
                            th = th;
                            arrayOffset3 = i5;
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
            byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
            charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
            return coderResult;
        }

        private CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            int i;
            char decodeDouble;
            int position = byteBuffer.position();
            while (true) {
                try {
                    if (byteBuffer.hasRemaining()) {
                        byte b = byteBuffer.get();
                        int i2 = b & 255;
                        if ((b & 128) == 0) {
                            decodeDouble = (char) i2;
                            i = 1;
                        } else {
                            i = 2;
                            if (i2 == 143) {
                                if (byteBuffer.remaining() < 2) {
                                    coderResult = CoderResult.UNDERFLOW;
                                    break;
                                }
                                int i3 = byteBuffer.get() & 255;
                                int i4 = byteBuffer.get() & 255;
                                DoubleByte.Decoder decoder = this.dec0212;
                                if (decoder == null) {
                                    coderResult = CoderResult.unmappableForLength(3);
                                    break;
                                }
                                decodeDouble = decoder.decodeDouble(i3 - 128, i4 - 128);
                                i = 3;
                            } else {
                                if (byteBuffer.remaining() < 1) {
                                    coderResult = CoderResult.UNDERFLOW;
                                    break;
                                }
                                decodeDouble = decodeDouble(i2, byteBuffer.get() & 255);
                            }
                        }
                        if (decodeDouble == 65533) {
                            coderResult = CoderResult.unmappableForLength(i);
                            break;
                        }
                        if (charBuffer.remaining() < 1) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        charBuffer.put(decodeDouble);
                        position += i;
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

        public CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            if (byteBuffer.hasArray() && charBuffer.hasArray()) {
                return decodeArrayLoop(byteBuffer, charBuffer);
            }
            return decodeBufferLoop(byteBuffer, charBuffer);
        }

        public void implReset() {
            super.implReset();
        }

        public CoderResult implFlush(CharBuffer charBuffer) {
            return super.implFlush(charBuffer);
        }
    }

    static class Encoder extends CharsetEncoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final SingleByte.Encoder ENC0201 = (SingleByte.Encoder) new JIS_X_0201().newEncoder();
        static final DoubleByte.Encoder ENC0208 = (DoubleByte.Encoder) new JIS_X_0208().newEncoder();
        static final DoubleByte.Encoder ENC0212 = (DoubleByte.Encoder) new JIS_X_0212().newEncoder();
        private final SingleByte.Encoder enc0201;
        private final DoubleByte.Encoder enc0208;
        private final DoubleByte.Encoder enc0212;
        private final Surrogate.Parser sgp;

        protected Encoder(Charset charset) {
            this(charset, 3.0f, 3.0f, ENC0201, ENC0208, ENC0212);
        }

        protected Encoder(Charset charset, float f, float f2, SingleByte.Encoder encoder, DoubleByte.Encoder encoder2, DoubleByte.Encoder encoder3) {
            super(charset, f, f2);
            this.sgp = new Surrogate.Parser();
            this.enc0201 = encoder;
            this.enc0208 = encoder2;
            this.enc0212 = encoder3;
        }

        public boolean canEncode(char c) {
            return (encodeSingle(c, new byte[3]) == 0 && encodeDouble(c) == 65533) ? false : true;
        }

        protected int encodeSingle(char c, byte[] bArr) {
            int encode = this.enc0201.encode(c);
            if (encode == 65533) {
                return 0;
            }
            if (encode >= 0 && encode < 128) {
                bArr[0] = (byte) encode;
                return 1;
            }
            bArr[0] = -114;
            bArr[1] = (byte) encode;
            return 2;
        }

        protected int encodeDouble(char c) {
            int encodeChar = this.enc0208.encodeChar(c);
            if (encodeChar != 65533) {
                return encodeChar + 32896;
            }
            DoubleByte.Encoder encoder = this.enc0212;
            if (encoder == null) {
                return encodeChar;
            }
            int encodeChar2 = encoder.encodeChar(c);
            return encodeChar2 != 65533 ? encodeChar2 + 9404544 : encodeChar2;
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
            byte[] bArr = new byte[3];
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        char c = array[arrayOffset];
                        if (Character.isSurrogate(c)) {
                            if (this.sgp.parse(c, array, arrayOffset, arrayOffset2) < 0) {
                                coderResult = this.sgp.error();
                            } else {
                                coderResult = this.sgp.unmappableResult();
                            }
                        } else {
                            int encodeSingle = encodeSingle(c, bArr);
                            int i = 0;
                            if (encodeSingle == 0) {
                                int encodeDouble = encodeDouble(c);
                                if (encodeDouble == 65533) {
                                    coderResult = CoderResult.unmappableForLength(1);
                                    break;
                                }
                                if ((16711680 & encodeDouble) == 0) {
                                    bArr[0] = (byte) ((encodeDouble & 65280) >> 8);
                                    bArr[1] = (byte) (encodeDouble & 255);
                                    encodeSingle = 2;
                                } else {
                                    bArr[0] = -113;
                                    bArr[1] = (byte) ((encodeDouble & 65280) >> 8);
                                    bArr[2] = (byte) (encodeDouble & 255);
                                    encodeSingle = 3;
                                }
                            }
                            if (arrayOffset4 - arrayOffset3 < encodeSingle) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            while (i < encodeSingle) {
                                int i2 = arrayOffset3 + 1;
                                try {
                                    array2[arrayOffset3] = bArr[i];
                                    i++;
                                    arrayOffset3 = i2;
                                } catch (Throwable th) {
                                    th = th;
                                    arrayOffset3 = i2;
                                    charBuffer.position(arrayOffset - charBuffer.arrayOffset());
                                    byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
                                    throw th;
                                }
                            }
                            arrayOffset++;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    coderResult = CoderResult.UNDERFLOW;
                    break;
                }
            }
            charBuffer.position(arrayOffset - charBuffer.arrayOffset());
            byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
            return coderResult;
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x007b A[ADDED_TO_REGION, LOOP:1: B:22:0x007b->B:23:0x007d, LOOP_START, PHI: r5
          0x007b: PHI (r5v1 int) = (r5v0 int), (r5v2 int) binds: [B:21:0x0076, B:23:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0078 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.nio.charset.CoderResult encodeBufferLoop(java.nio.CharBuffer r10, java.nio.ByteBuffer r11) {
            /*
                r9 = this;
                r0 = 3
                byte[] r1 = new byte[r0]
                int r2 = r10.position()
            L7:
                boolean r3 = r10.hasRemaining()     // Catch: java.lang.Throwable -> L8c
                if (r3 == 0) goto L89
                char r3 = r10.get()     // Catch: java.lang.Throwable -> L8c
                boolean r4 = java.lang.Character.isSurrogate(r3)     // Catch: java.lang.Throwable -> L8c
                if (r4 == 0) goto L33
                sun.nio.cs.Surrogate$Parser r11 = r9.sgp     // Catch: java.lang.Throwable -> L8c
                int r11 = r11.parse(r3, r10)     // Catch: java.lang.Throwable -> L8c
                if (r11 >= 0) goto L2c
                sun.nio.cs.Surrogate$Parser r11 = r9.sgp     // Catch: java.lang.Throwable -> L8c
                java.nio.charset.CoderResult r11 = r11.error()     // Catch: java.lang.Throwable -> L8c
            L25:
                java.nio.Buffer r10 = r10.position(r2)
                java.nio.CharBuffer r10 = (java.nio.CharBuffer) r10
                return r11
            L2c:
                sun.nio.cs.Surrogate$Parser r11 = r9.sgp     // Catch: java.lang.Throwable -> L8c
                java.nio.charset.CoderResult r11 = r11.unmappableResult()     // Catch: java.lang.Throwable -> L8c
                goto L25
            L33:
                int r4 = r9.encodeSingle(r3, r1)     // Catch: java.lang.Throwable -> L8c
                r5 = 0
                if (r4 != 0) goto L72
                int r3 = r9.encodeDouble(r3)     // Catch: java.lang.Throwable -> L8c
                r4 = 65533(0xfffd, float:9.1831E-41)
                r6 = 1
                if (r3 == r4) goto L6d
                r4 = 16711680(0xff0000, float:2.3418052E-38)
                r4 = r4 & r3
                r7 = 2
                r8 = 65280(0xff00, float:9.1477E-41)
                if (r4 != 0) goto L5b
                r4 = r3 & r8
                int r4 = r4 >> 8
                byte r4 = (byte) r4     // Catch: java.lang.Throwable -> L8c
                r1[r5] = r4     // Catch: java.lang.Throwable -> L8c
                r3 = r3 & 255(0xff, float:3.57E-43)
                byte r3 = (byte) r3     // Catch: java.lang.Throwable -> L8c
                r1[r6] = r3     // Catch: java.lang.Throwable -> L8c
                r4 = 2
                goto L72
            L5b:
                r4 = -113(0xffffffffffffff8f, float:NaN)
                r1[r5] = r4     // Catch: java.lang.Throwable -> L8c
                r4 = r3 & r8
                int r4 = r4 >> 8
                byte r4 = (byte) r4     // Catch: java.lang.Throwable -> L8c
                r1[r6] = r4     // Catch: java.lang.Throwable -> L8c
                r3 = r3 & 255(0xff, float:3.57E-43)
                byte r3 = (byte) r3     // Catch: java.lang.Throwable -> L8c
                r1[r7] = r3     // Catch: java.lang.Throwable -> L8c
                r4 = 3
                goto L72
            L6d:
                java.nio.charset.CoderResult r11 = java.nio.charset.CoderResult.unmappableForLength(r6)     // Catch: java.lang.Throwable -> L8c
                goto L25
            L72:
                int r3 = r11.remaining()     // Catch: java.lang.Throwable -> L8c
                if (r3 >= r4) goto L7b
                java.nio.charset.CoderResult r11 = java.nio.charset.CoderResult.OVERFLOW     // Catch: java.lang.Throwable -> L8c
                goto L25
            L7b:
                if (r5 >= r4) goto L85
                r3 = r1[r5]     // Catch: java.lang.Throwable -> L8c
                r11.put(r3)     // Catch: java.lang.Throwable -> L8c
                int r5 = r5 + 1
                goto L7b
            L85:
                int r2 = r2 + 1
                goto L7
            L89:
                java.nio.charset.CoderResult r11 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> L8c
                goto L25
            L8c:
                r11 = move-exception
                java.nio.Buffer r10 = r10.position(r2)
                java.nio.CharBuffer r10 = (java.nio.CharBuffer) r10
                goto L95
            L94:
                throw r11
            L95:
                goto L94
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.EUC_JP.Encoder.encodeBufferLoop(java.nio.CharBuffer, java.nio.ByteBuffer):java.nio.charset.CoderResult");
        }

        protected CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            if (charBuffer.hasArray() && byteBuffer.hasArray()) {
                return encodeArrayLoop(charBuffer, byteBuffer);
            }
            return encodeBufferLoop(charBuffer, byteBuffer);
        }
    }
}
