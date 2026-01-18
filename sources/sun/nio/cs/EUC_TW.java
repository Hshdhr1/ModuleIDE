package sun.nio.cs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class EUC_TW extends Charset implements HistoricallyNamedCharset {
    private static final int SS2 = 142;

    public EUC_TW() {
        super("x-EUC-TW", StandardCharsets.aliases_EUC_TW());
    }

    public String historicalName() {
        return "EUC_TW";
    }

    public boolean contains(Charset charset) {
        return charset.name().equals("US-ASCII") || (charset instanceof EUC_TW);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    public static class Decoder extends CharsetDecoder {
        static final int b1Max = 254;
        static final int b1Min = 161;
        static final int b2Max = 254;
        static final int b2Min = 161;
        static final String[] b2c = EUC_TWMapping.b2c;
        static final byte[] b2cIsSupp;
        static final byte[] cnspToIndex;
        static final int dbSegSize = 94;
        char[] c1;
        char[] c2;

        static boolean isLegalDB(int i) {
            return i >= 161 && i <= 254;
        }

        public Decoder(Charset charset) {
            super(charset, 2.0f, 2.0f);
            this.c1 = new char[1];
            this.c2 = new char[2];
        }

        public char[] toUnicode(int i, int i2, int i3) {
            return decode(i, i2, i3, this.c1, this.c2);
        }

        static {
            byte[] bArr = new byte[256];
            cnspToIndex = bArr;
            Arrays.fill(bArr, (byte) -1);
            bArr[162] = 1;
            bArr[163] = 2;
            bArr[164] = 3;
            bArr[165] = 4;
            bArr[166] = 5;
            bArr[167] = 6;
            bArr[175] = 7;
            String str = EUC_TWMapping.b2cIsSuppStr;
            byte[] bArr2 = new byte[str.length() << 1];
            int i = 0;
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt = str.charAt(i2);
                int i3 = i + 1;
                bArr2[i] = (byte) (charAt >> '\b');
                i += 2;
                bArr2[i3] = (byte) (charAt & 255);
            }
            b2cIsSupp = bArr2;
        }

        static char[] decode(int i, int i2, int i3, char[] cArr, char[] cArr2) {
            int i4;
            char charAt;
            if (i < 161 || i > 254 || i2 < 161 || i2 > 254 || (charAt = b2c[i3].charAt((i4 = (((i - 161) * 94) + i2) - 161))) == 65533) {
                return null;
            }
            if ((b2cIsSupp[i4] & (1 << i3)) == 0) {
                cArr[0] = charAt;
                return cArr;
            }
            int i5 = charAt + 0;
            cArr2[0] = Character.highSurrogate(i5);
            cArr2[1] = Character.lowSurrogate(i5);
            return cArr2;
        }

        private CoderResult decodeArrayLoop(ByteBuffer byteBuffer, CharBuffer charBuffer) {
            CoderResult coderResult;
            int i;
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int arrayOffset2 = byteBuffer.arrayOffset() + byteBuffer.limit();
            char[] array2 = charBuffer.array();
            int arrayOffset3 = charBuffer.arrayOffset() + charBuffer.position();
            int arrayOffset4 = charBuffer.arrayOffset() + charBuffer.limit();
            while (true) {
                if (arrayOffset < arrayOffset2) {
                    try {
                        int i2 = array[arrayOffset] & 255;
                        if (i2 == 142) {
                            if (arrayOffset2 - arrayOffset < 4) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            byte b = cnspToIndex[array[arrayOffset + 1] & 255];
                            if (b < 0) {
                                coderResult = CoderResult.malformedForLength(2);
                                break;
                            }
                            int i3 = array[arrayOffset + 2] & 255;
                            int i4 = array[arrayOffset + 3] & 255;
                            char[] unicode = toUnicode(i3, i4, b);
                            if (unicode == null) {
                                if (!isLegalDB(i3) || !isLegalDB(i4)) {
                                    coderResult = CoderResult.malformedForLength(4);
                                } else {
                                    coderResult = CoderResult.unmappableForLength(4);
                                }
                            } else {
                                if (arrayOffset4 - arrayOffset3 < unicode.length) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                if (unicode.length == 1) {
                                    i = arrayOffset3 + 1;
                                    try {
                                        array2[arrayOffset3] = unicode[0];
                                    } catch (Throwable th) {
                                        th = th;
                                        arrayOffset3 = i;
                                        byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
                                        charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
                                        throw th;
                                    }
                                } else {
                                    int i5 = arrayOffset3 + 1;
                                    array2[arrayOffset3] = unicode[0];
                                    int i6 = arrayOffset3 + 2;
                                    array2[i5] = unicode[1];
                                    i = i6;
                                }
                                arrayOffset += 4;
                                arrayOffset3 = i;
                            }
                        } else if (i2 < 128) {
                            if (arrayOffset4 - arrayOffset3 < 1) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            i = arrayOffset3 + 1;
                            array2[arrayOffset3] = (char) i2;
                            arrayOffset++;
                            arrayOffset3 = i;
                        } else {
                            if (arrayOffset2 - arrayOffset < 2) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            int i7 = array[arrayOffset + 1] & 255;
                            char[] unicode2 = toUnicode(i2, i7, 0);
                            if (unicode2 == null) {
                                if (!isLegalDB(i2) || !isLegalDB(i7)) {
                                    coderResult = CoderResult.malformedForLength(1);
                                } else {
                                    coderResult = CoderResult.unmappableForLength(2);
                                }
                            } else {
                                if (arrayOffset4 - arrayOffset3 < 1) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                int i8 = arrayOffset3 + 1;
                                try {
                                    array2[arrayOffset3] = unicode2[0];
                                    arrayOffset += 2;
                                    arrayOffset3 = i8;
                                } catch (Throwable th2) {
                                    th = th2;
                                    arrayOffset3 = i8;
                                    byteBuffer.position(arrayOffset - byteBuffer.arrayOffset());
                                    charBuffer.position(arrayOffset3 - charBuffer.arrayOffset());
                                    throw th;
                                }
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
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
            int position = byteBuffer.position();
            while (true) {
                try {
                    if (byteBuffer.hasRemaining()) {
                        int i = byteBuffer.get() & 255;
                        if (i == 142) {
                            if (byteBuffer.remaining() < 3) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            byte b = cnspToIndex[byteBuffer.get() & 255];
                            if (b < 0) {
                                coderResult = CoderResult.malformedForLength(2);
                                break;
                            }
                            int i2 = byteBuffer.get() & 255;
                            int i3 = byteBuffer.get() & 255;
                            char[] unicode = toUnicode(i2, i3, b);
                            if (unicode == null) {
                                if (!isLegalDB(i2) || !isLegalDB(i3)) {
                                    coderResult = CoderResult.malformedForLength(4);
                                } else {
                                    coderResult = CoderResult.unmappableForLength(4);
                                }
                            } else {
                                if (charBuffer.remaining() < unicode.length) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                if (unicode.length == 1) {
                                    charBuffer.put(unicode[0]);
                                } else {
                                    charBuffer.put(unicode[0]);
                                    charBuffer.put(unicode[1]);
                                }
                                position += 4;
                            }
                        } else if (i < 128) {
                            if (!charBuffer.hasRemaining()) {
                                coderResult = CoderResult.OVERFLOW;
                                break;
                            }
                            charBuffer.put((char) i);
                            position++;
                        } else {
                            if (!byteBuffer.hasRemaining()) {
                                coderResult = CoderResult.UNDERFLOW;
                                break;
                            }
                            int i4 = byteBuffer.get() & 255;
                            char[] unicode2 = toUnicode(i, i4, 0);
                            if (unicode2 == null) {
                                if (!isLegalDB(i) || !isLegalDB(i4)) {
                                    coderResult = CoderResult.malformedForLength(1);
                                } else {
                                    coderResult = CoderResult.unmappableForLength(2);
                                }
                            } else {
                                if (!charBuffer.hasRemaining()) {
                                    coderResult = CoderResult.OVERFLOW;
                                    break;
                                }
                                charBuffer.put(unicode2[0]);
                                position += 2;
                            }
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

    public static class Encoder extends CharsetEncoder {
        static final char[] c2b;
        static final char[] c2bIndex;
        static final byte[] c2bPlane;
        static final char[] c2bSupp;
        static final char[] c2bSuppIndex;
        private byte[] bb;

        public Encoder(Charset charset) {
            super(charset, 4.0f, 4.0f);
            this.bb = new byte[4];
        }

        public boolean canEncode(char c) {
            return c <= 127 || toEUC(c, this.bb) != -1;
        }

        public boolean canEncode(CharSequence charSequence) {
            int i = 0;
            while (i < charSequence.length()) {
                int i2 = i + 1;
                char charAt = charSequence.charAt(i);
                if (Character.isHighSurrogate(charAt)) {
                    if (i2 == charSequence.length()) {
                        return false;
                    }
                    i += 2;
                    char charAt2 = charSequence.charAt(i2);
                    if (!Character.isLowSurrogate(charAt2) || toEUC(charAt, charAt2, this.bb) == -1) {
                        return false;
                    }
                } else {
                    if (!canEncode(charAt)) {
                        return false;
                    }
                    i = i2;
                }
            }
            return true;
        }

        public int toEUC(char c, char c2, byte[] bArr) {
            return encode(c, c2, bArr);
        }

        public int toEUC(char c, byte[] bArr) {
            return encode(c, bArr);
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x008e A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0093 A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:4:0x002e, B:6:0x0037, B:29:0x008e, B:9:0x0093, B:25:0x0097, B:30:0x003d, B:32:0x0045, B:34:0x004b, B:42:0x004f, B:36:0x0068, B:40:0x0070, B:38:0x0075, B:43:0x007f, B:46:0x0085, B:50:0x00ae), top: B:3:0x002e }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.nio.charset.CoderResult encodeArrayLoop(java.nio.CharBuffer r13, java.nio.ByteBuffer r14) {
            /*
                r12 = this;
                char[] r0 = r13.array()
                int r1 = r13.arrayOffset()
                int r2 = r13.position()
                int r1 = r1 + r2
                int r2 = r13.arrayOffset()
                int r3 = r13.limit()
                int r2 = r2 + r3
                byte[] r3 = r14.array()
                int r4 = r14.arrayOffset()
                int r5 = r14.position()
                int r4 = r4 + r5
                int r5 = r14.arrayOffset()
                int r6 = r14.limit()
                int r5 = r5 + r6
            L2c:
                if (r1 >= r2) goto Lae
                char r6 = r0[r1]     // Catch: java.lang.Throwable -> Lb1
                r7 = 128(0x80, float:1.8E-43)
                r8 = -1
                r9 = 0
                r10 = 1
                if (r6 >= r7) goto L3d
                byte[] r7 = r12.bb     // Catch: java.lang.Throwable -> Lb1
                byte r6 = (byte) r6     // Catch: java.lang.Throwable -> Lb1
                r7[r9] = r6     // Catch: java.lang.Throwable -> Lb1
                goto L8b
            L3d:
                byte[] r7 = r12.bb     // Catch: java.lang.Throwable -> Lb1
                int r7 = r12.toEUC(r6, r7)     // Catch: java.lang.Throwable -> Lb1
                if (r7 != r8) goto L8a
                boolean r11 = java.lang.Character.isHighSurrogate(r6)     // Catch: java.lang.Throwable -> Lb1
                if (r11 == 0) goto L7f
                int r7 = r1 + 1
                if (r7 != r2) goto L68
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> Lb1
            L51:
                int r2 = r13.arrayOffset()
                int r1 = r1 - r2
                java.nio.Buffer r13 = r13.position(r1)
                java.nio.CharBuffer r13 = (java.nio.CharBuffer) r13
                int r13 = r14.arrayOffset()
                int r4 = r4 - r13
                java.nio.Buffer r13 = r14.position(r4)
                java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
                return r0
            L68:
                char r11 = r0[r7]     // Catch: java.lang.Throwable -> Lb1
                boolean r11 = java.lang.Character.isLowSurrogate(r11)     // Catch: java.lang.Throwable -> Lb1
                if (r11 != 0) goto L75
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.malformedForLength(r10)     // Catch: java.lang.Throwable -> Lb1
                goto L51
            L75:
                char r7 = r0[r7]     // Catch: java.lang.Throwable -> Lb1
                byte[] r10 = r12.bb     // Catch: java.lang.Throwable -> Lb1
                int r10 = r12.toEUC(r6, r7, r10)     // Catch: java.lang.Throwable -> Lb1
                r6 = 2
                goto L8c
            L7f:
                boolean r6 = java.lang.Character.isLowSurrogate(r6)     // Catch: java.lang.Throwable -> Lb1
                if (r6 == 0) goto L8a
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.malformedForLength(r10)     // Catch: java.lang.Throwable -> Lb1
                goto L51
            L8a:
                r10 = r7
            L8b:
                r6 = 1
            L8c:
                if (r10 != r8) goto L93
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.unmappableForLength(r6)     // Catch: java.lang.Throwable -> Lb1
                goto L51
            L93:
                int r7 = r5 - r4
                if (r7 >= r10) goto L9a
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.OVERFLOW     // Catch: java.lang.Throwable -> Lb1
                goto L51
            L9a:
                if (r9 >= r10) goto Lab
                int r7 = r4 + 1
                byte[] r8 = r12.bb     // Catch: java.lang.Throwable -> La8
                r8 = r8[r9]     // Catch: java.lang.Throwable -> La8
                r3[r4] = r8     // Catch: java.lang.Throwable -> La8
                int r9 = r9 + 1
                r4 = r7
                goto L9a
            La8:
                r0 = move-exception
                r4 = r7
                goto Lb2
            Lab:
                int r1 = r1 + r6
                goto L2c
            Lae:
                java.nio.charset.CoderResult r0 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> Lb1
                goto L51
            Lb1:
                r0 = move-exception
            Lb2:
                int r2 = r13.arrayOffset()
                int r1 = r1 - r2
                java.nio.Buffer r13 = r13.position(r1)
                java.nio.CharBuffer r13 = (java.nio.CharBuffer) r13
                int r13 = r14.arrayOffset()
                int r4 = r4 - r13
                java.nio.Buffer r13 = r14.position(r4)
                java.nio.ByteBuffer r13 = (java.nio.ByteBuffer) r13
                goto Lca
            Lc9:
                throw r0
            Lca:
                goto Lc9
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.EUC_TW.Encoder.encodeArrayLoop(java.nio.CharBuffer, java.nio.ByteBuffer):java.nio.charset.CoderResult");
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0063 A[Catch: all -> 0x007d, TryCatch #0 {all -> 0x007d, blocks: (B:3:0x0004, B:5:0x000a, B:7:0x0015, B:22:0x005e, B:10:0x0063, B:17:0x0069, B:13:0x006e, B:15:0x0078, B:23:0x001b, B:25:0x0023, B:27:0x0029, B:35:0x002f, B:29:0x0038, B:33:0x0042, B:31:0x0047, B:36:0x004f, B:39:0x0055, B:42:0x007a), top: B:2:0x0004 }] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x005e A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.nio.charset.CoderResult encodeBufferLoop(java.nio.CharBuffer r8, java.nio.ByteBuffer r9) {
            /*
                r7 = this;
                int r0 = r8.position()
            L4:
                boolean r1 = r8.hasRemaining()     // Catch: java.lang.Throwable -> L7d
                if (r1 == 0) goto L7a
                char r1 = r8.get()     // Catch: java.lang.Throwable -> L7d
                r2 = 128(0x80, float:1.8E-43)
                r3 = -1
                r4 = 0
                r5 = 1
                if (r1 >= r2) goto L1b
                byte[] r2 = r7.bb     // Catch: java.lang.Throwable -> L7d
                byte r1 = (byte) r1     // Catch: java.lang.Throwable -> L7d
                r2[r4] = r1     // Catch: java.lang.Throwable -> L7d
                goto L5b
            L1b:
                byte[] r2 = r7.bb     // Catch: java.lang.Throwable -> L7d
                int r2 = r7.toEUC(r1, r2)     // Catch: java.lang.Throwable -> L7d
                if (r2 != r3) goto L5a
                boolean r6 = java.lang.Character.isHighSurrogate(r1)     // Catch: java.lang.Throwable -> L7d
                if (r6 == 0) goto L4f
                boolean r2 = r8.hasRemaining()     // Catch: java.lang.Throwable -> L7d
                if (r2 != 0) goto L38
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> L7d
            L31:
                java.nio.Buffer r8 = r8.position(r0)
                java.nio.CharBuffer r8 = (java.nio.CharBuffer) r8
                return r9
            L38:
                char r2 = r8.get()     // Catch: java.lang.Throwable -> L7d
                boolean r6 = java.lang.Character.isLowSurrogate(r2)     // Catch: java.lang.Throwable -> L7d
                if (r6 != 0) goto L47
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.malformedForLength(r5)     // Catch: java.lang.Throwable -> L7d
                goto L31
            L47:
                byte[] r5 = r7.bb     // Catch: java.lang.Throwable -> L7d
                int r5 = r7.toEUC(r1, r2, r5)     // Catch: java.lang.Throwable -> L7d
                r1 = 2
                goto L5c
            L4f:
                boolean r1 = java.lang.Character.isLowSurrogate(r1)     // Catch: java.lang.Throwable -> L7d
                if (r1 == 0) goto L5a
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.malformedForLength(r5)     // Catch: java.lang.Throwable -> L7d
                goto L31
            L5a:
                r5 = r2
            L5b:
                r1 = 1
            L5c:
                if (r5 != r3) goto L63
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.unmappableForLength(r1)     // Catch: java.lang.Throwable -> L7d
                goto L31
            L63:
                int r2 = r9.remaining()     // Catch: java.lang.Throwable -> L7d
                if (r2 >= r5) goto L6c
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.OVERFLOW     // Catch: java.lang.Throwable -> L7d
                goto L31
            L6c:
                if (r4 >= r5) goto L78
                byte[] r2 = r7.bb     // Catch: java.lang.Throwable -> L7d
                r2 = r2[r4]     // Catch: java.lang.Throwable -> L7d
                r9.put(r2)     // Catch: java.lang.Throwable -> L7d
                int r4 = r4 + 1
                goto L6c
            L78:
                int r0 = r0 + r1
                goto L4
            L7a:
                java.nio.charset.CoderResult r9 = java.nio.charset.CoderResult.UNDERFLOW     // Catch: java.lang.Throwable -> L7d
                goto L31
            L7d:
                r9 = move-exception
                java.nio.Buffer r8 = r8.position(r0)
                java.nio.CharBuffer r8 = (java.nio.CharBuffer) r8
                goto L86
            L85:
                throw r9
            L86:
                goto L85
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.cs.EUC_TW.Encoder.encodeBufferLoop(java.nio.CharBuffer, java.nio.ByteBuffer):java.nio.charset.CoderResult");
        }

        protected CoderResult encodeLoop(CharBuffer charBuffer, ByteBuffer byteBuffer) {
            if (charBuffer.hasArray() && byteBuffer.hasArray()) {
                return encodeArrayLoop(charBuffer, byteBuffer);
            }
            return encodeBufferLoop(charBuffer, byteBuffer);
        }

        static int encode(char c, char c2, byte[] bArr) {
            int i;
            char c3;
            int codePoint = Character.toCodePoint(c, c2);
            if ((983040 & codePoint) != 131072) {
                return -1;
            }
            int i2 = codePoint - 131072;
            char c4 = c2bSuppIndex[i2 >> 8];
            if (c4 == 65533 || (c3 = c2bSupp[(i = c4 + (i2 & 255))]) == 65533) {
                return -1;
            }
            int i3 = (c2bPlane[i] >> 4) & 15;
            bArr[0] = -114;
            bArr[1] = (byte) (i3 | 160);
            bArr[2] = (byte) (c3 >> '\b');
            bArr[3] = (byte) c3;
            return 4;
        }

        static int encode(char c, byte[] bArr) {
            int i;
            char c2;
            char c3 = c2bIndex[c >> '\b'];
            if (c3 == 65533 || (c2 = c2b[(i = c3 + (c & 255))]) == 65533) {
                return -1;
            }
            int i2 = c2bPlane[i] & 15;
            if (i2 == 0) {
                bArr[0] = (byte) (c2 >> '\b');
                bArr[1] = (byte) c2;
                return 2;
            }
            bArr[0] = -114;
            bArr[1] = (byte) (i2 | 160);
            bArr[2] = (byte) (c2 >> '\b');
            bArr[3] = (byte) c2;
            return 4;
        }

        static {
            String[] strArr = Decoder.b2c;
            byte[] bArr = Decoder.b2cIsSupp;
            c2bIndex = EUC_TWMapping.c2bIndex;
            c2bSuppIndex = EUC_TWMapping.c2bSuppIndex;
            char[] cArr = new char[31744];
            char[] cArr2 = new char[43520];
            byte[] bArr2 = new byte[Math.max(31744, 43520)];
            char c = 65533;
            Arrays.fill(cArr, (char) 65533);
            Arrays.fill(cArr2, (char) 65533);
            int i = 0;
            while (i < strArr.length) {
                String str = strArr[i];
                int i2 = i == 7 ? 15 : i != 0 ? i + 1 : i;
                int i3 = 161;
                int i4 = 0;
                while (i3 <= 254) {
                    int i5 = 161;
                    while (i5 <= 254) {
                        char charAt = str.charAt(i4);
                        if (charAt != c) {
                            if ((bArr[i4] & (1 << i)) != 0) {
                                int i6 = c2bSuppIndex[charAt >> '\b'] + (charAt & 255);
                                cArr2[i6] = (char) ((i3 << 8) + i5);
                                bArr2[i6] = (byte) (((byte) (i2 << 4)) | bArr2[i6]);
                            } else {
                                int i7 = c2bIndex[charAt >> '\b'] + (charAt & 255);
                                cArr[i7] = (char) ((i3 << 8) + i5);
                                bArr2[i7] = (byte) (((byte) i2) | bArr2[i7]);
                            }
                        }
                        i4++;
                        i5++;
                        c = 65533;
                    }
                    i3++;
                    c = 65533;
                }
                i++;
                c = 65533;
            }
            c2b = cArr;
            c2bSupp = cArr2;
            c2bPlane = bArr2;
        }
    }
}
