package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import sun.nio.cs.Surrogate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DoubleByte {
    public static final char[] B2C_UNMAPPABLE;

    static {
        char[] cArr = new char[256];
        B2C_UNMAPPABLE = cArr;
        Arrays.fill(cArr, (char) 65533);
    }

    public static class Decoder extends CharsetDecoder implements DelegatableDecoder, ArrayDecoder {
        final int b2Max;
        final int b2Min;
        final char[][] b2c;
        final char[] b2cSB;
        final boolean isASCIICompatible;

        protected CoderResult crMalformedOrUnderFlow(int i) {
            return CoderResult.UNDERFLOW;
        }

        protected CoderResult crMalformedOrUnmappable(int i, int i2) {
            if (this.b2c[i] == DoubleByte.B2C_UNMAPPABLE || this.b2c[i2] != DoubleByte.B2C_UNMAPPABLE || decodeSingle(i2) != 65533) {
                return CoderResult.malformedForLength(1);
            }
            return CoderResult.unmappableForLength(2);
        }

        public Decoder(Charset charset, float f, float f2, char[][] cArr, char[] cArr2, int i, int i2, boolean z) {
            super(charset, f, f2);
            this.b2c = cArr;
            this.b2cSB = cArr2;
            this.b2Min = i;
            this.b2Max = i2;
            this.isASCIICompatible = z;
        }

        public Decoder(Charset charset, char[][] cArr, char[] cArr2, int i, int i2, boolean z) {
            this(charset, 0.5f, 1.0f, cArr, cArr2, i, i2, z);
        }

        public Decoder(Charset charset, char[][] cArr, char[] cArr2, int i, int i2) {
            this(charset, 0.5f, 1.0f, cArr, cArr2, i, i2, false);
        }

        protected CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            char c;
            int i;
            int i2;
            char c2;
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
            char[] array2 = charBuffer.array();
            int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
            while (arrayOffset < arrayOffset2 && arrayOffset3 < arrayOffset4) {
                try {
                    int i3 = array[arrayOffset] & 255;
                    c = this.b2cSB[i3];
                    if (c == 65533) {
                        i = 2;
                        if (arrayOffset2 - arrayOffset < 2) {
                            coderResult = crMalformedOrUnderFlow(i3);
                            break;
                        }
                        int i4 = array[arrayOffset + 1] & 255;
                        int i5 = this.b2Min;
                        if (i4 >= i5 && i4 <= this.b2Max && (c2 = this.b2c[i3][i4 - i5]) != 65533) {
                            c = c2;
                        }
                        coderResult = crMalformedOrUnmappable(i3, i4);
                        break;
                    }
                    i = 1;
                    i2 = arrayOffset3 + 1;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    array2[arrayOffset3] = c;
                    arrayOffset += i;
                    arrayOffset3 = i2;
                } catch (Throwable th2) {
                    th = th2;
                    arrayOffset3 = i2;
                    byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
                    charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
                    throw th;
                }
            }
            if (arrayOffset >= arrayOffset2) {
                coderResult = CoderResult.UNDERFLOW;
            } else {
                coderResult = CoderResult.OVERFLOW;
            }
            byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
            charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
            return coderResult;
        }

        protected CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            char c;
            int position = byteBuffer.position();
            while (byteBuffer.hasRemaining() && charBuffer.hasRemaining()) {
                try {
                    int i = byteBuffer.get() & 255;
                    char c2 = this.b2cSB[i];
                    int i2 = 1;
                    if (c2 == 65533) {
                        if (byteBuffer.remaining() < 1) {
                            coderResult = crMalformedOrUnderFlow(i);
                            break;
                        }
                        int i3 = byteBuffer.get() & 255;
                        int i4 = this.b2Min;
                        if (i3 >= i4 && i3 <= this.b2Max && (c = this.b2c[i][i3 - i4]) != 65533) {
                            c2 = c;
                            i2 = 2;
                        }
                        coderResult = crMalformedOrUnmappable(i, i3);
                        break;
                    }
                    charBuffer.put(c2);
                    position += i2;
                } finally {
                    byteBuffer.position(position);
                }
            }
            coderResult = byteBuffer.hasRemaining() ? CoderResult.OVERFLOW : CoderResult.UNDERFLOW;
            return coderResult;
        }

        public CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            if (byteBuffer.hasArray() && charBuffer.hasArray()) {
                return decodeArrayLoop(byteBuffer, charBuffer);
            }
            return decodeBufferLoop(byteBuffer, charBuffer);
        }

        public int decode(byte[] bArr, int i, int i2, char[] cArr) {
            int i3 = i2 + i;
            int i4 = 0;
            char charAt = replacement().charAt(0);
            while (i < i3) {
                int i5 = i + 1;
                int i6 = bArr[i] & 255;
                char c = this.b2cSB[i6];
                if (c == 65533) {
                    if (i5 < i3) {
                        int i7 = i + 2;
                        int i8 = bArr[i5] & 255;
                        int i9 = this.b2Min;
                        i5 = ((i8 < i9 || i8 > this.b2Max || (c = this.b2c[i6][i8 - i9]) == 65533) && crMalformedOrUnmappable(i6, i8).length() == 1) ? i + 1 : i7;
                    }
                    if (c == 65533) {
                        c = charAt;
                    }
                }
                i = i5;
                cArr[i4] = c;
                i4++;
            }
            return i4;
        }

        public boolean isASCIICompatible() {
            return this.isASCIICompatible;
        }

        public void implReset() {
            super.implReset();
        }

        public CoderResult implFlush(CharBuffer charBuffer) {
            return super.implFlush(charBuffer);
        }

        public char decodeSingle(int i) {
            return this.b2cSB[i];
        }

        public char decodeDouble(int i, int i2) {
            int i3;
            if (i < 0) {
                return (char) 65533;
            }
            char[][] cArr = this.b2c;
            if (i > cArr.length || i2 < (i3 = this.b2Min) || i2 > this.b2Max) {
                return (char) 65533;
            }
            return cArr[i][i2 - i3];
        }
    }

    public static class Decoder_EBCDIC extends Decoder {
        private static final int DBCS = 1;
        private static final int SBCS = 0;
        private static final int SI = 15;
        private static final int SO = 14;
        private int currentState;

        private static boolean isDoubleByte(int i, int i2) {
            if (65 > i || i > 254 || 65 > i2 || i2 > 254) {
                return i == 64 && i2 == 64;
            }
            return true;
        }

        public Decoder_EBCDIC(Charset charset, char[][] cArr, char[] cArr2, int i, int i2, boolean z) {
            super(charset, cArr, cArr2, i, i2, z);
        }

        public Decoder_EBCDIC(Charset charset, char[][] cArr, char[] cArr2, int i, int i2) {
            super(charset, cArr, cArr2, i, i2, false);
        }

        public void implReset() {
            this.currentState = 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:57:0x00b9, code lost:
        
            if (isDoubleByte(r6, r7) != false) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00bb, code lost:
        
            r0 = java.nio.charset.CoderResult.malformedForLength(2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00c1, code lost:
        
            r0 = java.nio.charset.CoderResult.unmappableForLength(2);
         */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00a6 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected java.nio.charset.CoderResult decodeArrayLoop(java.nio.ByteBuffer r14, java.nio.CharBuffer r15) {
            /*
                Method dump skipped, instructions count: 229
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.DoubleByte.Decoder_EBCDIC.decodeArrayLoop(java.nio.ByteBuffer, java.nio.CharBuffer):java.nio.charset.CoderResult");
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x0085, code lost:
        
            if (isDoubleByte(r1, r2) != false) goto L44;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x0087, code lost:
        
            r10 = java.nio.charset.CoderResult.malformedForLength(2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x008c, code lost:
        
            r10 = java.nio.charset.CoderResult.unmappableForLength(2);
         */
        /* JADX WARN: Removed duplicated region for block: B:32:0x007b A[Catch: all -> 0x0094, TryCatch #0 {all -> 0x0094, blocks: (B:3:0x0004, B:5:0x000a, B:7:0x0015, B:13:0x0019, B:9:0x0024, B:11:0x007f, B:19:0x002b, B:24:0x002f, B:21:0x0034, B:25:0x0038, B:27:0x003f, B:37:0x0045, B:30:0x0072, B:35:0x0078, B:32:0x007b, B:38:0x004c, B:56:0x0052, B:40:0x0055, B:42:0x0060, B:44:0x0064, B:49:0x0081, B:51:0x0087, B:52:0x008c, B:58:0x0091), top: B:2:0x0004 }] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0078 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected java.nio.charset.CoderResult decodeBufferLoop(java.nio.ByteBuffer r9, java.nio.CharBuffer r10) {
            /*
                r8 = this;
                int r0 = r9.position()
            L4:
                boolean r1 = r9.hasRemaining()     // Catch: java.lang.Throwable -> L94
                if (r1 == 0) goto L91
                byte r1 = r9.get()     // Catch: java.lang.Throwable -> L94
                r1 = r1 & 255(0xff, float:3.57E-43)
                r2 = 14
                r3 = 1
                if (r1 != r2) goto L27
                int r1 = r8.currentState     // Catch: java.lang.Throwable -> L94
                if (r1 == 0) goto L24
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.malformedForLength(r3)     // Catch: java.lang.Throwable -> L94
            L1d:
                java.nio.Buffer r9 = r9.position(r0)
                java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
                return r10
            L24:
                r8.currentState = r3     // Catch: java.lang.Throwable -> L94
                goto L7f
            L27:
                r2 = 15
                if (r1 != r2) goto L38
                int r1 = r8.currentState     // Catch: java.lang.Throwable -> L94
                if (r1 == r3) goto L34
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.malformedForLength(r3)     // Catch: java.lang.Throwable -> L94
                goto L1d
            L34:
                r1 = 0
                r8.currentState = r1     // Catch: java.lang.Throwable -> L94
                goto L7f
            L38:
                int r2 = r8.currentState     // Catch: java.lang.Throwable -> L94
                r4 = 65533(0xfffd, float:9.1831E-41)
                if (r2 != 0) goto L4c
                char[] r2 = r8.b2cSB     // Catch: java.lang.Throwable -> L94
                char r1 = r2[r1]     // Catch: java.lang.Throwable -> L94
                if (r1 != r4) goto L4a
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.unmappableForLength(r3)     // Catch: java.lang.Throwable -> L94
                goto L1d
            L4a:
                r6 = 1
                goto L72
            L4c:
                int r2 = r9.remaining()     // Catch: java.lang.Throwable -> L94
                if (r2 >= r3) goto L55
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> L94
                goto L1d
            L55:
                byte r2 = r9.get()     // Catch: java.lang.Throwable -> L94
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r5 = r8.b2Min     // Catch: java.lang.Throwable -> L94
                r6 = 2
                if (r2 < r5) goto L81
                int r5 = r8.b2Max     // Catch: java.lang.Throwable -> L94
                if (r2 > r5) goto L81
                char[][] r5 = r8.b2c     // Catch: java.lang.Throwable -> L94
                r5 = r5[r1]     // Catch: java.lang.Throwable -> L94
                int r7 = r8.b2Min     // Catch: java.lang.Throwable -> L94
                int r7 = r2 - r7
                char r5 = r5[r7]     // Catch: java.lang.Throwable -> L94
                if (r5 != r4) goto L71
                goto L81
            L71:
                r1 = r5
            L72:
                int r2 = r10.remaining()     // Catch: java.lang.Throwable -> L94
                if (r2 >= r3) goto L7b
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.OVERFLOW     // Catch: java.lang.Throwable -> L94
                goto L1d
            L7b:
                r10.put(r1)     // Catch: java.lang.Throwable -> L94
                r3 = r6
            L7f:
                int r0 = r0 + r3
                goto L4
            L81:
                boolean r10 = isDoubleByte(r1, r2)     // Catch: java.lang.Throwable -> L94
                if (r10 != 0) goto L8c
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.malformedForLength(r6)     // Catch: java.lang.Throwable -> L94
                goto L1d
            L8c:
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.unmappableForLength(r6)     // Catch: java.lang.Throwable -> L94
                goto L1d
            L91:
                java.nio.charset.CoderResult r10 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> L94
                goto L1d
            L94:
                r10 = move-exception
                java.nio.Buffer r9 = r9.position(r0)
                java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
                goto L9d
            L9c:
                throw r10
            L9d:
                goto L9c
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.DoubleByte.Decoder_EBCDIC.decodeBufferLoop(java.nio.ByteBuffer, java.nio.CharBuffer):java.nio.charset.CoderResult");
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0048 A[PHI: r3
          0x0048: PHI (r3v7 int) = (r3v0 int), (r3v3 int), (r3v0 int) binds: [B:16:0x0046, B:24:0x0068, B:11:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int decode(byte[] r9, int r10, int r11, char[] r12) {
            /*
                r8 = this;
                int r11 = r11 + r10
                r0 = 0
                r8.currentState = r0
                java.lang.String r1 = r8.replacement()
                char r1 = r1.charAt(r0)
                r2 = 0
            Ld:
                if (r10 >= r11) goto L71
                int r3 = r10 + 1
                r4 = r9[r10]
                r4 = r4 & 255(0xff, float:3.57E-43)
                r5 = 14
                r6 = 1
                if (r4 != r5) goto L27
                int r10 = r8.currentState
                if (r10 == 0) goto L24
                int r10 = r2 + 1
                r12[r2] = r1
            L22:
                r2 = r10
                goto L36
            L24:
                r8.currentState = r6
                goto L36
            L27:
                r5 = 15
                if (r4 != r5) goto L38
                int r10 = r8.currentState
                if (r10 == r6) goto L34
                int r10 = r2 + 1
                r12[r2] = r1
                goto L22
            L34:
                r8.currentState = r0
            L36:
                r10 = r3
                goto Ld
            L38:
                int r5 = r8.currentState
                r6 = 65533(0xfffd, float:9.1831E-41)
                if (r5 != 0) goto L46
                char[] r10 = r8.b2cSB
                char r10 = r10[r4]
                if (r10 != r6) goto L6a
                goto L48
            L46:
                if (r11 != r3) goto L4a
            L48:
                r10 = r1
                goto L6a
            L4a:
                int r10 = r10 + 2
                r3 = r9[r3]
                r3 = r3 & 255(0xff, float:3.57E-43)
                int r5 = r8.b2Min
                if (r3 < r5) goto L68
                int r5 = r8.b2Max
                if (r3 > r5) goto L68
                char[][] r5 = r8.b2c
                r4 = r5[r4]
                int r5 = r8.b2Min
                int r3 = r3 - r5
                char r3 = r4[r3]
                if (r3 != r6) goto L64
                goto L68
            L64:
                r7 = r3
                r3 = r10
                r10 = r7
                goto L6a
            L68:
                r3 = r10
                goto L48
            L6a:
                int r4 = r2 + 1
                r12[r2] = r10
                r10 = r3
                r2 = r4
                goto Ld
            L71:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.DoubleByte.Decoder_EBCDIC.decode(byte[], int, int, char[]):int");
        }
    }

    public static class Decoder_DBCSONLY extends Decoder {
        static final char[] b2cSB_UNMAPPABLE;

        static {
            char[] cArr = new char[256];
            b2cSB_UNMAPPABLE = cArr;
            Arrays.fill(cArr, (char) 65533);
        }

        protected CoderResult crMalformedOrUnmappable(int i, int i2) {
            return CoderResult.unmappableForLength(2);
        }

        public Decoder_DBCSONLY(Charset charset, char[][] cArr, char[] cArr2, int i, int i2, boolean z) {
            super(charset, 0.5f, 1.0f, cArr, b2cSB_UNMAPPABLE, i, i2, z);
        }

        public Decoder_DBCSONLY(Charset charset, char[][] cArr, char[] cArr2, int i, int i2) {
            super(charset, 0.5f, 1.0f, cArr, b2cSB_UNMAPPABLE, i, i2, false);
        }
    }

    public static class Decoder_EUC_SIM extends Decoder {
        private final int SS2;
        private final int SS3;

        public Decoder_EUC_SIM(Charset charset, char[][] cArr, char[] cArr2, int i, int i2, boolean z) {
            super(charset, cArr, cArr2, i, i2, z);
            this.SS2 = 142;
            this.SS3 = 143;
        }

        protected CoderResult crMalformedOrUnderFlow(int i) {
            if (i == 142 || i == 143) {
                return CoderResult.malformedForLength(1);
            }
            return CoderResult.UNDERFLOW;
        }

        protected CoderResult crMalformedOrUnmappable(int i, int i2) {
            if (i == 142 || i == 143) {
                return CoderResult.malformedForLength(1);
            }
            return CoderResult.unmappableForLength(2);
        }

        public int decode(byte[] bArr, int i, int i2, char[] cArr) {
            char c;
            int i3 = i2 + i;
            int i4 = 0;
            char charAt = replacement().charAt(0);
            while (i < i3) {
                int i5 = i + 1;
                int i6 = bArr[i] & 255;
                char c2 = this.b2cSB[i6];
                if (c2 != 65533) {
                    i = i5;
                } else if (i5 < i3) {
                    int i7 = i + 2;
                    int i8 = bArr[i5] & 255;
                    if (i8 < this.b2Min || i8 > this.b2Max || (c = this.b2c[i6][i8 - this.b2Min]) == 65533) {
                        if (i6 == 142 || i6 == 143) {
                            i7 = i + 1;
                        }
                        c = charAt;
                    }
                    i = i7;
                    c2 = c;
                } else {
                    c2 = charAt;
                    i = i5;
                }
                cArr[i4] = c2;
                i4++;
            }
            return i4;
        }
    }

    public static class Encoder extends CharsetEncoder implements ArrayEncoder {
        protected final int MAX_SINGLEBYTE;
        private final char[] c2b;
        private final char[] c2bIndex;
        final boolean isASCIICompatible;
        protected byte[] repl;
        protected Surrogate.Parser sgp;

        public Encoder(Charset charset, char[] cArr, char[] cArr2) {
            this(charset, cArr, cArr2, false);
        }

        public Encoder(Charset charset, char[] cArr, char[] cArr2, boolean z) {
            super(charset, 2.0f, 2.0f);
            this.MAX_SINGLEBYTE = 255;
            this.repl = replacement();
            this.c2b = cArr;
            this.c2bIndex = cArr2;
            this.isASCIICompatible = z;
        }

        public Encoder(Charset charset, float f, float f2, byte[] bArr, char[] cArr, char[] cArr2, boolean z) {
            super(charset, f, f2, bArr);
            this.MAX_SINGLEBYTE = 255;
            this.repl = replacement();
            this.c2b = cArr;
            this.c2bIndex = cArr2;
            this.isASCIICompatible = z;
        }

        public boolean canEncode(char c) {
            return encodeChar(c) != 65533;
        }

        protected Surrogate.Parser sgp() {
            if (this.sgp == null) {
                this.sgp = new Surrogate.Parser();
            }
            return this.sgp;
        }

        protected CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset2 = charBuffer.arrayOffset() + charBuffer.limit();
            byte[] array2 = byteBuffer.array();
            int arrayOffset3 = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset4 = byteBuffer.arrayOffset() + byteBuffer.limit();
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        char c = array[arrayOffset];
                        int encodeChar = encodeChar(c);
                        if (encodeChar == 65533) {
                            if (Character.isSurrogate(c)) {
                                if (sgp().parse(c, array, arrayOffset, arrayOffset2) < 0) {
                                    coderResult = this.sgp.error();
                                } else {
                                    coderResult = this.sgp.unmappableResult();
                                }
                            } else {
                                coderResult = CoderResult.unmappableForLength(1);
                            }
                        } else if (encodeChar > 255) {
                            if (arrayOffset4 - arrayOffset3 < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i = arrayOffset3 + 1;
                            try {
                                array2[arrayOffset3] = (byte) (encodeChar >> 8);
                                arrayOffset3 += 2;
                                array2[i] = (byte) encodeChar;
                                arrayOffset++;
                            } catch (Throwable th) {
                                th = th;
                                arrayOffset3 = i;
                                charBuffer.position(arrayOffset - charBuffer.arrayOffset());
                                byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
                                throw th;
                            }
                        } else {
                            if (arrayOffset4 - arrayOffset3 < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i2 = arrayOffset3 + 1;
                            array2[arrayOffset3] = (byte) encodeChar;
                            arrayOffset3 = i2;
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

        protected CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        int encodeChar = encodeChar(c);
                        if (encodeChar == 65533) {
                            if (Character.isSurrogate(c)) {
                                if (sgp().parse(c, charBuffer) < 0) {
                                    coderResult = this.sgp.error();
                                } else {
                                    coderResult = this.sgp.unmappableResult();
                                }
                            } else {
                                coderResult = CoderResult.unmappableForLength(1);
                            }
                        } else if (encodeChar > 255) {
                            if (byteBuffer.remaining() < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) (encodeChar >> 8));
                            byteBuffer.put((byte) encodeChar);
                            position++;
                        } else {
                            if (byteBuffer.remaining() < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) encodeChar);
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

        protected void implReplaceWith(byte[] bArr) {
            this.repl = bArr;
        }

        public int encode(char[] cArr, int i, int i2, byte[] bArr) {
            int i3 = i2 + i;
            int length = bArr.length;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                char c = cArr[i];
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    i = (Character.isHighSurrogate(c) && i5 < i3 && Character.isLowSurrogate(cArr[i5])) ? i + 2 : i5;
                    int i6 = i4 + 1;
                    byte[] bArr2 = this.repl;
                    bArr[i4] = bArr2[0];
                    if (bArr2.length > 1) {
                        i4 += 2;
                        bArr[i6] = bArr2[1];
                    } else {
                        i4 = i6;
                    }
                } else {
                    if (encodeChar > 255) {
                        int i7 = i4 + 1;
                        bArr[i4] = (byte) (encodeChar >> 8);
                        i4 += 2;
                        bArr[i7] = (byte) encodeChar;
                    } else {
                        bArr[i4] = (byte) encodeChar;
                        i4++;
                    }
                    i = i5;
                }
            }
            return i4;
        }

        public int encodeFromLatin1(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3 = i2 + i;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                int encodeChar = encodeChar((char) (bArr[i] & 255));
                if (encodeChar == 65533) {
                    int i6 = i4 + 1;
                    byte[] bArr3 = this.repl;
                    bArr2[i4] = bArr3[0];
                    if (bArr3.length > 1) {
                        i4 += 2;
                        bArr2[i6] = bArr3[1];
                    } else {
                        i4 = i6;
                    }
                } else if (encodeChar > 255) {
                    int i7 = i4 + 1;
                    bArr2[i4] = (byte) (encodeChar >> 8);
                    i4 += 2;
                    bArr2[i7] = (byte) encodeChar;
                } else {
                    bArr2[i4] = (byte) encodeChar;
                    i4++;
                }
                i = i5;
            }
            return i4;
        }

        public int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3 = i2 + i;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                char c = StringUTF16.getChar(bArr, i);
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    i = (Character.isHighSurrogate(c) && i5 < i3 && Character.isLowSurrogate(StringUTF16.getChar(bArr, i5))) ? i + 2 : i5;
                    int i6 = i4 + 1;
                    byte[] bArr3 = this.repl;
                    bArr2[i4] = bArr3[0];
                    if (bArr3.length > 1) {
                        i4 += 2;
                        bArr2[i6] = bArr3[1];
                    } else {
                        i4 = i6;
                    }
                } else {
                    if (encodeChar > 255) {
                        int i7 = i4 + 1;
                        bArr2[i4] = (byte) (encodeChar >> 8);
                        i4 += 2;
                        bArr2[i7] = (byte) encodeChar;
                    } else {
                        bArr2[i4] = (byte) encodeChar;
                        i4++;
                    }
                    i = i5;
                }
            }
            return i4;
        }

        public boolean isASCIICompatible() {
            return this.isASCIICompatible;
        }

        public int encodeChar(char c) {
            return this.c2b[this.c2bIndex[c >> '\b'] + (c & 255)];
        }

        public static void initC2B(String[] strArr, String str, String str2, String str3, int i, int i2, char[] cArr, char[] cArr2) {
            int i3;
            int i4;
            Arrays.fill(cArr, (char) 65533);
            char[][] cArr3 = new char[strArr.length][];
            char[] charArray = str != null ? str.toCharArray() : null;
            for (int i5 = 0; i5 < strArr.length; i5++) {
                String str4 = strArr[i5];
                if (str4 != null) {
                    cArr3[i5] = str4.toCharArray();
                }
            }
            int i6 = 256;
            if (str2 != null) {
                int i7 = 0;
                while (i7 < str2.length()) {
                    int i8 = i7 + 1;
                    char charAt = str2.charAt(i7);
                    i7 += 2;
                    char charAt2 = str2.charAt(i8);
                    if (charAt < 256 && charArray != null) {
                        if (charArray[charAt] == charAt2) {
                            charArray[charAt] = 65533;
                        }
                    } else {
                        char[] cArr4 = cArr3[charAt >> '\b'];
                        int i9 = (charAt & 255) - i;
                        if (cArr4[i9] == charAt2) {
                            cArr4[i9] = 65533;
                        }
                    }
                }
            }
            if (charArray != null) {
                for (int i10 = 0; i10 < charArray.length; i10++) {
                    char c = charArray[i10];
                    if (c != 65533) {
                        int i11 = c >> '\b';
                        char c2 = cArr2[i11];
                        if (c2 == 0) {
                            i4 = i6 + 256;
                            cArr2[i11] = (char) i6;
                        } else {
                            i4 = i6;
                            i6 = c2;
                        }
                        cArr[i6 + (c & 255)] = (char) i10;
                        i6 = i4;
                    }
                }
            }
            for (int i12 = 0; i12 < strArr.length; i12++) {
                char[] cArr5 = cArr3[i12];
                if (cArr5 != null) {
                    for (int i13 = i; i13 <= i2; i13++) {
                        char c3 = cArr5[i13 - i];
                        if (c3 != 65533) {
                            int i14 = c3 >> '\b';
                            char c4 = cArr2[i14];
                            if (c4 == 0) {
                                i3 = i6 + 256;
                                cArr2[i14] = (char) i6;
                            } else {
                                i3 = i6;
                                i6 = c4;
                            }
                            cArr[i6 + (c3 & 255)] = (char) ((i12 << 8) | i13);
                            i6 = i3;
                        }
                    }
                }
            }
            if (str3 != null) {
                for (int i15 = 0; i15 < str3.length(); i15 += 2) {
                    char charAt3 = str3.charAt(i15);
                    char charAt4 = str3.charAt(i15 + 1);
                    int i16 = charAt4 >> '\b';
                    if (cArr2[i16] == 0) {
                        cArr2[i16] = (char) i6;
                        i6 += 256;
                    }
                    cArr[cArr2[i16] + (charAt4 & 255)] = charAt3;
                }
            }
        }
    }

    public static class Encoder_DBCSONLY extends Encoder {
        public Encoder_DBCSONLY(Charset charset, byte[] bArr, char[] cArr, char[] cArr2, boolean z) {
            super(charset, 2.0f, 2.0f, bArr, cArr, cArr2, z);
        }

        public int encodeChar(char c) {
            int encodeChar = super.encodeChar(c);
            if (encodeChar <= 255) {
                return 65533;
            }
            return encodeChar;
        }
    }

    public static class Encoder_EBCDIC extends Encoder {
        static final int DBCS = 1;
        static final int SBCS = 0;
        static final byte SI = 15;
        static final byte SO = 14;
        protected int currentState;

        public Encoder_EBCDIC(Charset charset, char[] cArr, char[] cArr2, boolean z) {
            super(charset, 4.0f, 5.0f, new byte[]{111}, cArr, cArr2, z);
            this.currentState = 0;
        }

        protected void implReset() {
            this.currentState = 0;
        }

        protected CoderResult implFlush(ByteBuffer byteBuffer) {
            if (this.currentState == 1) {
                if (byteBuffer.remaining() < 1) {
                    return CoderResult.OVERFLOW;
                }
                byteBuffer.put((byte) 15);
            }
            implReset();
            return CoderResult.UNDERFLOW;
        }

        protected CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset2 = charBuffer.arrayOffset() + charBuffer.limit();
            byte[] array2 = byteBuffer.array();
            int arrayOffset3 = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset4 = byteBuffer.arrayOffset() + byteBuffer.limit();
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        char c = array[arrayOffset];
                        int encodeChar = encodeChar(c);
                        if (encodeChar == 65533) {
                            if (Character.isSurrogate(c)) {
                                if (sgp().parse(c, array, arrayOffset, arrayOffset2) < 0) {
                                    coderResult = this.sgp.error();
                                } else {
                                    coderResult = this.sgp.unmappableResult();
                                }
                            } else {
                                coderResult = CoderResult.unmappableForLength(1);
                            }
                        } else if (encodeChar > 255) {
                            if (this.currentState == 0) {
                                if (arrayOffset4 - arrayOffset3 < 1) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                this.currentState = 1;
                                int i = arrayOffset3 + 1;
                                try {
                                    array2[arrayOffset3] = 14;
                                    arrayOffset3 = i;
                                } catch (Throwable th) {
                                    th = th;
                                    arrayOffset3 = i;
                                    charBuffer.position(arrayOffset - charBuffer.arrayOffset());
                                    byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
                                    throw th;
                                }
                            }
                            if (arrayOffset4 - arrayOffset3 < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i2 = arrayOffset3 + 1;
                            array2[arrayOffset3] = (byte) (encodeChar >> 8);
                            arrayOffset3 += 2;
                            array2[i2] = (byte) encodeChar;
                            arrayOffset++;
                        } else {
                            if (this.currentState == 1) {
                                if (arrayOffset4 - arrayOffset3 < 1) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                this.currentState = 0;
                                int i3 = arrayOffset3 + 1;
                                array2[arrayOffset3] = 15;
                                arrayOffset3 = i3;
                            }
                            if (arrayOffset4 - arrayOffset3 < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i4 = arrayOffset3 + 1;
                            array2[arrayOffset3] = (byte) encodeChar;
                            arrayOffset3 = i4;
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

        protected CoderResult encodeBufferLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        int encodeChar = encodeChar(c);
                        if (encodeChar == 65533) {
                            if (Character.isSurrogate(c)) {
                                if (sgp().parse(c, charBuffer) < 0) {
                                    coderResult = this.sgp.error();
                                } else {
                                    coderResult = this.sgp.unmappableResult();
                                }
                            } else {
                                coderResult = CoderResult.unmappableForLength(1);
                            }
                        } else if (encodeChar > 255) {
                            if (this.currentState == 0) {
                                if (byteBuffer.remaining() < 1) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                this.currentState = 1;
                                byteBuffer.put((byte) 14);
                            }
                            if (byteBuffer.remaining() < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) (encodeChar >> 8));
                            byteBuffer.put((byte) encodeChar);
                            position++;
                        } else {
                            if (this.currentState == 1) {
                                if (byteBuffer.remaining() < 1) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                this.currentState = 0;
                                byteBuffer.put((byte) 15);
                            }
                            if (byteBuffer.remaining() < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) encodeChar);
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

        public int encode(char[] cArr, int i, int i2, byte[] bArr) {
            int i3 = i2 + i;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                char c = cArr[i];
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    i = (Character.isHighSurrogate(c) && i5 < i3 && Character.isLowSurrogate(cArr[i5])) ? i + 2 : i5;
                    int i6 = i4 + 1;
                    bArr[i4] = this.repl[0];
                    if (this.repl.length > 1) {
                        i4 += 2;
                        bArr[i6] = this.repl[1];
                    } else {
                        i4 = i6;
                    }
                } else {
                    if (encodeChar > 255) {
                        if (this.currentState == 0) {
                            this.currentState = 1;
                            bArr[i4] = 14;
                            i4++;
                        }
                        int i7 = i4 + 1;
                        bArr[i4] = (byte) (encodeChar >> 8);
                        i4 += 2;
                        bArr[i7] = (byte) encodeChar;
                    } else {
                        if (this.currentState == 1) {
                            this.currentState = 0;
                            bArr[i4] = 15;
                            i4++;
                        }
                        bArr[i4] = (byte) encodeChar;
                        i4++;
                    }
                    i = i5;
                }
            }
            if (this.currentState != 1) {
                return i4;
            }
            this.currentState = 0;
            int i8 = i4 + 1;
            bArr[i4] = 15;
            return i8;
        }

        public int encodeFromLatin1(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3 = i2 + i;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                int encodeChar = encodeChar((char) (bArr[i] & 255));
                if (encodeChar == 65533) {
                    int i6 = i4 + 1;
                    bArr2[i4] = this.repl[0];
                    if (this.repl.length > 1) {
                        i4 += 2;
                        bArr2[i6] = this.repl[1];
                    } else {
                        i4 = i6;
                    }
                } else if (encodeChar > 255) {
                    if (this.currentState == 0) {
                        this.currentState = 1;
                        bArr2[i4] = 14;
                        i4++;
                    }
                    int i7 = i4 + 1;
                    bArr2[i4] = (byte) (encodeChar >> 8);
                    i4 += 2;
                    bArr2[i7] = (byte) encodeChar;
                } else {
                    if (this.currentState == 1) {
                        this.currentState = 0;
                        bArr2[i4] = 15;
                        i4++;
                    }
                    bArr2[i4] = (byte) encodeChar;
                    i4++;
                }
                i = i5;
            }
            if (this.currentState != 1) {
                return i4;
            }
            this.currentState = 0;
            int i8 = i4 + 1;
            bArr2[i4] = 15;
            return i8;
        }

        public int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3 = i2 + i;
            int i4 = 0;
            while (i < i3) {
                int i5 = i + 1;
                char c = StringUTF16.getChar(bArr, i);
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    i = (Character.isHighSurrogate(c) && i5 < i3 && Character.isLowSurrogate(StringUTF16.getChar(bArr, i5))) ? i + 2 : i5;
                    int i6 = i4 + 1;
                    bArr2[i4] = this.repl[0];
                    if (this.repl.length > 1) {
                        i4 += 2;
                        bArr2[i6] = this.repl[1];
                    } else {
                        i4 = i6;
                    }
                } else {
                    if (encodeChar > 255) {
                        if (this.currentState == 0) {
                            this.currentState = 1;
                            bArr2[i4] = 14;
                            i4++;
                        }
                        int i7 = i4 + 1;
                        bArr2[i4] = (byte) (encodeChar >> 8);
                        i4 += 2;
                        bArr2[i7] = (byte) encodeChar;
                    } else {
                        if (this.currentState == 1) {
                            this.currentState = 0;
                            bArr2[i4] = 15;
                            i4++;
                        }
                        bArr2[i4] = (byte) encodeChar;
                        i4++;
                    }
                    i = i5;
                }
            }
            if (this.currentState != 1) {
                return i4;
            }
            this.currentState = 0;
            int i8 = i4 + 1;
            bArr2[i4] = 15;
            return i8;
        }
    }

    public static class Encoder_EUC_SIM extends Encoder {
        public Encoder_EUC_SIM(Charset charset, char[] cArr, char[] cArr2, boolean z) {
            super(charset, cArr, cArr2, z);
        }
    }
}
