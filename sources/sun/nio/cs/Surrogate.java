package sun.nio.cs;

import java.nio.CharBuffer;
import java.nio.charset.CoderResult;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class Surrogate {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final char MAX = 57343;
    public static final char MAX_HIGH = 56319;
    public static final char MAX_LOW = 57343;
    public static final char MIN = 55296;
    public static final char MIN_HIGH = 55296;
    public static final char MIN_LOW = 56320;
    public static final int UCS4_MAX = 1114111;
    public static final int UCS4_MIN = 65536;

    public static boolean is(int i) {
        return 55296 <= i && i <= 57343;
    }

    public static boolean isHigh(int i) {
        return 55296 <= i && i <= 56319;
    }

    public static boolean isLow(int i) {
        return 56320 <= i && i <= 57343;
    }

    private Surrogate() {
    }

    public static boolean neededFor(int i) {
        return Character.isSupplementaryCodePoint(i);
    }

    public static char high(int i) {
        return Character.highSurrogate(i);
    }

    public static char low(int i) {
        return Character.lowSurrogate(i);
    }

    public static int toUCS4(char c, char c2) {
        return Character.toCodePoint(c, c2);
    }

    public static class Parser {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int character;
        private CoderResult error = CoderResult.UNDERFLOW;
        private boolean isPair;

        public int character() {
            return this.character;
        }

        public boolean isPair() {
            return this.isPair;
        }

        public int increment() {
            return this.isPair ? 2 : 1;
        }

        public CoderResult error() {
            return this.error;
        }

        public CoderResult unmappableResult() {
            return CoderResult.unmappableForLength(this.isPair ? 2 : 1);
        }

        public int parse(char c, CharBuffer charBuffer) {
            if (Character.isHighSurrogate(c)) {
                if (!charBuffer.hasRemaining()) {
                    this.error = CoderResult.UNDERFLOW;
                    return -1;
                }
                char c2 = charBuffer.get();
                if (Character.isLowSurrogate(c2)) {
                    int codePoint = Character.toCodePoint(c, c2);
                    this.character = codePoint;
                    this.isPair = true;
                    this.error = null;
                    return codePoint;
                }
                this.error = CoderResult.malformedForLength(1);
                return -1;
            }
            if (Character.isLowSurrogate(c)) {
                this.error = CoderResult.malformedForLength(1);
                return -1;
            }
            this.character = c;
            this.isPair = false;
            this.error = null;
            return c;
        }

        public int parse(char c, char[] cArr, int i, int i2) {
            if (!Character.isHighSurrogate(c)) {
                if (Character.isLowSurrogate(c)) {
                    this.error = CoderResult.malformedForLength(1);
                    return -1;
                }
                this.character = c;
                this.isPair = false;
                this.error = null;
                return c;
            }
            if (i2 - i < 2) {
                this.error = CoderResult.UNDERFLOW;
                return -1;
            }
            char c2 = cArr[i + 1];
            if (Character.isLowSurrogate(c2)) {
                int codePoint = Character.toCodePoint(c, c2);
                this.character = codePoint;
                this.isPair = true;
                this.error = null;
                return codePoint;
            }
            this.error = CoderResult.malformedForLength(1);
            return -1;
        }
    }

    public static class Generator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private CoderResult error = CoderResult.OVERFLOW;

        public CoderResult error() {
            return this.error;
        }

        public int generate(int i, int i2, CharBuffer charBuffer) {
            if (Character.isBmpCodePoint(i)) {
                char c = (char) i;
                if (Character.isSurrogate(c)) {
                    this.error = CoderResult.malformedForLength(i2);
                    return -1;
                }
                if (charBuffer.remaining() < 1) {
                    this.error = CoderResult.OVERFLOW;
                    return -1;
                }
                charBuffer.put(c);
                this.error = null;
                return 1;
            }
            if (Character.isValidCodePoint(i)) {
                if (charBuffer.remaining() < 2) {
                    this.error = CoderResult.OVERFLOW;
                    return -1;
                }
                charBuffer.put(Character.highSurrogate(i));
                charBuffer.put(Character.lowSurrogate(i));
                this.error = null;
                return 2;
            }
            this.error = CoderResult.unmappableForLength(i2);
            return -1;
        }

        public int generate(int i, int i2, char[] cArr, int i3, int i4) {
            if (Character.isBmpCodePoint(i)) {
                char c = (char) i;
                if (Character.isSurrogate(c)) {
                    this.error = CoderResult.malformedForLength(i2);
                    return -1;
                }
                if (i4 - i3 < 1) {
                    this.error = CoderResult.OVERFLOW;
                    return -1;
                }
                cArr[i3] = c;
                this.error = null;
                return 1;
            }
            if (!Character.isValidCodePoint(i)) {
                this.error = CoderResult.unmappableForLength(i2);
                return -1;
            }
            if (i4 - i3 < 2) {
                this.error = CoderResult.OVERFLOW;
                return -1;
            }
            cArr[i3] = Character.highSurrogate(i);
            cArr[i3 + 1] = Character.lowSurrogate(i);
            this.error = null;
            return 2;
        }
    }
}
