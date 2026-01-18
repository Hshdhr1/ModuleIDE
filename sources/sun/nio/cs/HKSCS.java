package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import sun.nio.cs.DoubleByte;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class HKSCS {

    public static class Decoder extends DoubleByte.Decoder {
        static int b2Max = 254;
        static int b2Min = 64;
        private char[][] b2cBmp;
        private char[][] b2cSupp;
        private DoubleByte.Decoder big5Dec;

        protected Decoder(Charset charset, DoubleByte.Decoder decoder, char[][] cArr, char[][] cArr2) {
            super(charset, 0.5f, 1.0f, null, null, 0, 0, true);
            this.big5Dec = decoder;
            this.b2cBmp = cArr;
            this.b2cSupp = cArr2;
        }

        public char decodeSingle(int i) {
            return this.big5Dec.decodeSingle(i);
        }

        public char decodeBig5(int i, int i2) {
            return this.big5Dec.decodeDouble(i, i2);
        }

        public char decodeDouble(int i, int i2) {
            return this.b2cBmp[i][i2 - b2Min];
        }

        public char decodeDoubleEx(int i, int i2) {
            return this.b2cSupp[i][i2 - b2Min];
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x0087, code lost:
        
            r0 = java.nio.charset.CoderResult.unmappableForLength(2);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        protected java.nio.charset.CoderResult decodeArrayLoop(java.nio.ByteBuffer r13, java.nio.CharBuffer r14) {
            /*
                r12 = this;
                byte[] r0 = r13.array()
                int r1 = r13.arrayOffset()
                int r2 = r13.position()
                int r1 = r1 + r2
                int r2 = r13.arrayOffset()
                int r3 = r13.limit()
                int r2 = r2 + r3
                char[] r3 = r14.array()
                int r4 = r14.arrayOffset()
                int r5 = r14.position()
                int r4 = r4 + r5
                int r5 = r14.arrayOffset()
                int r6 = r14.limit()
                int r5 = r5 + r6
            L2c:
                if (r1 >= r2) goto Lb5
                r6 = r0[r1]     // Catch: java.lang.Throwable -> Lb8
                r6 = r6 & 255(0xff, float:3.57E-43)
                char r7 = r12.decodeSingle(r6)     // Catch: java.lang.Throwable -> Lb8
                r8 = 65533(0xfffd, float:9.1831E-41)
                r9 = 1
                r10 = 2
                if (r7 != r8) goto L8c
                int r7 = r2 - r1
                if (r7 >= r10) goto L5a
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> Lb8
            L43:
                int r2 = r13.arrayOffset()
                int r1 = r1 - r2
                java.nio.Buffer r13 = r13.position(r1)
                java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
                int r13 = r14.arrayOffset()
                int r4 = r4 - r13
                java.nio.Buffer r13 = r14.position(r4)
                java.nio.CharBuffer r13 = (java.nio.CharBuffer) r13
                return r0
            L5a:
                int r7 = r1 + 1
                r7 = r0[r7]     // Catch: java.lang.Throwable -> Lb8
                r7 = r7 & 255(0xff, float:3.57E-43)
                int r11 = sun.nio.cs.HKSCS.Decoder.b2Min     // Catch: java.lang.Throwable -> Lb8
                if (r7 < r11) goto L87
                int r11 = sun.nio.cs.HKSCS.Decoder.b2Max     // Catch: java.lang.Throwable -> Lb8
                if (r7 <= r11) goto L69
                goto L87
            L69:
                char r11 = r12.decodeDouble(r6, r7)     // Catch: java.lang.Throwable -> Lb8
                if (r11 != r8) goto L84
                char r11 = r12.decodeDoubleEx(r6, r7)     // Catch: java.lang.Throwable -> Lb8
                if (r11 != r8) goto L80
                char r7 = r12.decodeBig5(r6, r7)     // Catch: java.lang.Throwable -> Lb8
                if (r7 != r8) goto L85
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.unmappableForLength(r10)     // Catch: java.lang.Throwable -> Lb8
                goto L43
            L80:
                r7 = r11
                r6 = 2
                r9 = 2
                goto L8d
            L84:
                r7 = r11
            L85:
                r6 = 2
                goto L8d
            L87:
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.unmappableForLength(r10)     // Catch: java.lang.Throwable -> Lb8
                goto L43
            L8c:
                r6 = 1
            L8d:
                int r8 = r5 - r4
                if (r8 >= r9) goto L94
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.OVERFLOW     // Catch: java.lang.Throwable -> Lb8
                goto L43
            L94:
                if (r9 != r10) goto Laa
                int r8 = r4 + 1
                r9 = 131072(0x20000, float:1.83671E-40)
                int r7 = r7 + r9
                char r9 = sun.nio.cs.Surrogate.high(r7)     // Catch: java.lang.Throwable -> Lb2
                r3[r4] = r9     // Catch: java.lang.Throwable -> Lb2
                int r4 = r4 + 2
                char r7 = sun.nio.cs.Surrogate.low(r7)     // Catch: java.lang.Throwable -> Lb8
                r3[r8] = r7     // Catch: java.lang.Throwable -> Lb8
                goto Laf
            Laa:
                int r8 = r4 + 1
                r3[r4] = r7     // Catch: java.lang.Throwable -> Lb2
                r4 = r8
            Laf:
                int r1 = r1 + r6
                goto L2c
            Lb2:
                r0 = move-exception
                r4 = r8
                goto Lb9
            Lb5:
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> Lb8
                goto L43
            Lb8:
                r0 = move-exception
            Lb9:
                int r2 = r13.arrayOffset()
                int r1 = r1 - r2
                java.nio.Buffer r13 = r13.position(r1)
                java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
                int r13 = r14.arrayOffset()
                int r4 = r4 - r13
                java.nio.Buffer r13 = r14.position(r4)
                java.nio.CharBuffer r13 = (java.nio.CharBuffer) r13
                goto Ld1
            Ld0:
                throw r0
            Ld1:
                goto Ld0
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.HKSCS.Decoder.decodeArrayLoop(java.nio.ByteBuffer, java.nio.CharBuffer):java.nio.charset.CoderResult");
        }

        protected CoderResult decodeBufferLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            int i;
            int position = byteBuffer.position();
            while (true) {
                try {
                    if (byteBuffer.hasRemaining()) {
                        int i2 = byteBuffer.get() & 255;
                        char decodeSingle = decodeSingle(i2);
                        int i3 = 1;
                        if (decodeSingle != 65533) {
                            i = 1;
                        } else {
                            if (byteBuffer.remaining() < 1) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            int i4 = byteBuffer.get() & 255;
                            if (i4 < b2Min || i4 > b2Max) {
                                break;
                            }
                            char decodeDouble = decodeDouble(i2, i4);
                            if (decodeDouble == 65533) {
                                char decodeDoubleEx = decodeDoubleEx(i2, i4);
                                if (decodeDoubleEx == 65533) {
                                    decodeSingle = decodeBig5(i2, i4);
                                    if (decodeSingle == 65533) {
                                        coderResult = CoderResult.unmappableForLength(2);
                                        break;
                                    }
                                } else {
                                    decodeSingle = decodeDoubleEx;
                                    i = 2;
                                    i3 = 2;
                                }
                            } else {
                                decodeSingle = decodeDouble;
                            }
                            i = 2;
                        }
                        if (charBuffer.remaining() < i3) {
                            coderResult = CoderResult.OVERFLOW;
                            break;
                        }
                        if (i3 == 2) {
                            int i5 = decodeSingle + 0;
                            charBuffer.put(Surrogate.high(i5));
                            charBuffer.put(Surrogate.low(i5));
                        } else {
                            charBuffer.put(decodeSingle);
                        }
                        position += i;
                    } else {
                        coderResult = CoderResult.UNDERFLOW;
                        break;
                    }
                } finally {
                    byteBuffer.position(position);
                }
            }
            coderResult = CoderResult.unmappableForLength(2);
            return coderResult;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0055  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int decode(byte[] r7, int r8, int r9, char[] r10) {
            /*
                r6 = this;
                int r9 = r9 + r8
                java.lang.String r0 = r6.replacement()
                r1 = 0
                char r0 = r0.charAt(r1)
            La:
                if (r8 >= r9) goto L5e
                int r2 = r8 + 1
                r3 = r7[r8]
                r3 = r3 & 255(0xff, float:3.57E-43)
                char r4 = r6.decodeSingle(r3)
                r5 = 65533(0xfffd, float:9.1831E-41)
                if (r4 != r5) goto L57
                if (r9 != r2) goto L1f
                r4 = r0
                goto L57
            L1f:
                int r8 = r8 + 2
                r2 = r7[r2]
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r4 = sun.nio.cs.HKSCS.Decoder.b2Min
                if (r2 < r4) goto L55
                int r4 = sun.nio.cs.HKSCS.Decoder.b2Max
                if (r2 <= r4) goto L2e
                goto L55
            L2e:
                char r4 = r6.decodeDouble(r3, r2)
                if (r4 != r5) goto L58
                char r4 = r6.decodeDoubleEx(r3, r2)
                if (r4 != r5) goto L41
                char r4 = r6.decodeBig5(r3, r2)
                if (r4 != r5) goto L58
                goto L55
            L41:
                int r2 = r1 + 1
                r3 = 131072(0x20000, float:1.83671E-40)
                int r4 = r4 + r3
                char r3 = sun.nio.cs.Surrogate.high(r4)
                r10[r1] = r3
                int r1 = r1 + 2
                char r3 = sun.nio.cs.Surrogate.low(r4)
                r10[r2] = r3
                goto La
            L55:
                r4 = r0
                goto L58
            L57:
                r8 = r2
            L58:
                int r2 = r1 + 1
                r10[r1] = r4
                r1 = r2
                goto La
            L5e:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.HKSCS.Decoder.decode(byte[], int, int, char[]):int");
        }

        public CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            if (byteBuffer.hasArray() && charBuffer.hasArray()) {
                return decodeArrayLoop(byteBuffer, charBuffer);
            }
            return decodeBufferLoop(byteBuffer, charBuffer);
        }

        public static void initb2c(char[][] cArr, String[] strArr) {
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                if (str == null) {
                    cArr[i] = DoubleByte.B2C_UNMAPPABLE;
                } else {
                    cArr[i] = str.toCharArray();
                }
            }
        }
    }

    public static class Encoder extends DoubleByte.Encoder {
        static char[] C2B_UNMAPPABLE;
        private DoubleByte.Encoder big5Enc;
        private char[][] c2bBmp;
        private char[][] c2bSupp;
        private byte[] repl;

        protected Encoder(Charset charset, DoubleByte.Encoder encoder, char[][] cArr, char[][] cArr2) {
            super(charset, null, null, true);
            this.repl = replacement();
            this.big5Enc = encoder;
            this.c2bBmp = cArr;
            this.c2bSupp = cArr2;
        }

        public int encodeBig5(char c) {
            return this.big5Enc.encodeChar(c);
        }

        public int encodeChar(char c) {
            char c2 = this.c2bBmp[c >> '\b'][c & 255];
            return c2 == 65533 ? encodeBig5(c) : c2;
        }

        public int encodeSupp(int i) {
            if ((983040 & i) != 131072) {
                return 65533;
            }
            return this.c2bSupp[(i >> 8) & 255][i & 255];
        }

        public boolean canEncode(char c) {
            return encodeChar(c) != 65533;
        }

        protected CoderResult encodeArrayLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            CoderResult coderResult;
            int i;
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
                        if (encodeChar != 65533) {
                            i = 1;
                        } else if (Character.isSurrogate(c)) {
                            int parse = sgp().parse(c, array, arrayOffset, arrayOffset2);
                            if (parse < 0) {
                                coderResult = this.sgp.error();
                                break;
                            }
                            encodeChar = encodeSupp(parse);
                            if (encodeChar == 65533) {
                                coderResult = CoderResult.unmappableForLength(2);
                                break;
                            }
                            i = 2;
                        } else {
                            coderResult = CoderResult.unmappableForLength(1);
                            break;
                        }
                        if (encodeChar > 255) {
                            if (arrayOffset4 - arrayOffset3 < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i2 = arrayOffset3 + 1;
                            try {
                                array2[arrayOffset3] = (byte) (encodeChar >> 8);
                                arrayOffset3 += 2;
                                array2[i2] = (byte) encodeChar;
                                arrayOffset += i;
                            } catch (Throwable th) {
                                th = th;
                                arrayOffset3 = i2;
                                charBuffer.position(arrayOffset - charBuffer.arrayOffset());
                                byteBuffer.position(arrayOffset3 - byteBuffer.arrayOffset());
                                throw th;
                            }
                        } else {
                            if (arrayOffset4 - arrayOffset3 < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            int i3 = arrayOffset3 + 1;
                            array2[arrayOffset3] = (byte) encodeChar;
                            arrayOffset3 = i3;
                            arrayOffset += i;
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
            int i;
            int position = charBuffer.position();
            while (true) {
                try {
                    if (charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        int encodeChar = encodeChar(c);
                        if (encodeChar != 65533) {
                            i = 1;
                        } else if (Character.isSurrogate(c)) {
                            int parse = sgp().parse(c, charBuffer);
                            if (parse < 0) {
                                coderResult = this.sgp.error();
                                break;
                            }
                            encodeChar = encodeSupp(parse);
                            if (encodeChar == 65533) {
                                coderResult = CoderResult.unmappableForLength(2);
                                break;
                            }
                            i = 2;
                        } else {
                            coderResult = CoderResult.unmappableForLength(1);
                            break;
                        }
                        if (encodeChar > 255) {
                            if (byteBuffer.remaining() < 2) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) (encodeChar >> 8));
                            byteBuffer.put((byte) encodeChar);
                            position += i;
                        } else {
                            if (byteBuffer.remaining() < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            byteBuffer.put((byte) encodeChar);
                            position += i;
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
            int i3;
            int i4 = i2 + i;
            int i5 = 0;
            while (i < i4) {
                int i6 = i + 1;
                char c = cArr[i];
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    if (Character.isHighSurrogate(c) && i6 != i4 && Character.isLowSurrogate(cArr[i6])) {
                        i += 2;
                        encodeChar = encodeSupp(Character.toCodePoint(c, cArr[i6]));
                        if (encodeChar == 65533) {
                        }
                    } else {
                        i = i6;
                    }
                    i3 = i5 + 1;
                    byte[] bArr2 = this.repl;
                    bArr[i5] = bArr2[0];
                    if (bArr2.length > 1) {
                        i5 += 2;
                        bArr[i3] = bArr2[1];
                    } else {
                        i5 = i3;
                    }
                } else {
                    i = i6;
                }
                if (encodeChar > 255) {
                    int i7 = i5 + 1;
                    bArr[i5] = (byte) (encodeChar >> 8);
                    i5 += 2;
                    bArr[i7] = (byte) encodeChar;
                } else {
                    i3 = i5 + 1;
                    bArr[i5] = (byte) encodeChar;
                    i5 = i3;
                }
            }
            return i5;
        }

        public int encodeFromUTF16(byte[] bArr, int i, int i2, byte[] bArr2) {
            int i3;
            int i4 = i2 + i;
            int length = bArr2.length;
            int i5 = 0;
            while (i < i4) {
                int i6 = i + 1;
                char c = StringUTF16.getChar(bArr, i);
                int encodeChar = encodeChar(c);
                if (encodeChar == 65533) {
                    if (Character.isHighSurrogate(c) && i6 != i4 && Character.isLowSurrogate(StringUTF16.getChar(bArr, i6))) {
                        i += 2;
                        encodeChar = encodeSupp(Character.toCodePoint(c, StringUTF16.getChar(bArr, i6)));
                        if (encodeChar == 65533) {
                        }
                    } else {
                        i = i6;
                    }
                    i3 = i5 + 1;
                    byte[] bArr3 = this.repl;
                    bArr2[i5] = bArr3[0];
                    if (bArr3.length > 1) {
                        i5 += 2;
                        bArr2[i3] = bArr3[1];
                    } else {
                        i5 = i3;
                    }
                } else {
                    i = i6;
                }
                if (encodeChar > 255) {
                    int i7 = i5 + 1;
                    bArr2[i5] = (byte) (encodeChar >> 8);
                    i5 += 2;
                    bArr2[i7] = (byte) encodeChar;
                } else {
                    i3 = i5 + 1;
                    bArr2[i5] = (byte) encodeChar;
                    i5 = i3;
                }
            }
            return i5;
        }

        static {
            char[] cArr = new char[256];
            C2B_UNMAPPABLE = cArr;
            Arrays.fill(cArr, (char) 65533);
        }

        public static void initc2b(char[][] cArr, String[] strArr, String str) {
            Arrays.fill(cArr, C2B_UNMAPPABLE);
            for (int i = 0; i < 256; i++) {
                String str2 = strArr[i];
                if (str2 != null) {
                    for (int i2 = 0; i2 < str2.length(); i2++) {
                        char charAt = str2.charAt(i2);
                        if (charAt != 65533) {
                            int i3 = charAt >> '\b';
                            if (cArr[i3] == C2B_UNMAPPABLE) {
                                char[] cArr2 = new char[256];
                                cArr[i3] = cArr2;
                                Arrays.fill(cArr2, (char) 65533);
                            }
                            cArr[i3][charAt & 255] = (char) ((i << 8) | (i2 + 64));
                        }
                    }
                }
            }
            if (str != null) {
                char c = 57344;
                for (int i4 = 0; i4 < str.length(); i4++) {
                    char charAt2 = str.charAt(i4);
                    if (charAt2 != 65533) {
                        int i5 = c >> '\b';
                        if (cArr[i5] == C2B_UNMAPPABLE) {
                            char[] cArr3 = new char[256];
                            cArr[i5] = cArr3;
                            Arrays.fill(cArr3, (char) 65533);
                        }
                        cArr[i5][c & 255] = charAt2;
                    }
                    c = (char) (c + 1);
                }
            }
        }
    }
}
