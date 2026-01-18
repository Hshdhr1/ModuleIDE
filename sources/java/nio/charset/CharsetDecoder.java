package java.nio.charset;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class CharsetDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CODING = 1;
    private static final int ST_END = 2;
    private static final int ST_FLUSHED = 3;
    private static final int ST_RESET = 0;
    private static String[] stateNames = {"RESET", "CODING", "CODING_END", "FLUSHED"};
    private final float averageCharsPerByte;
    private final Charset charset;
    private CodingErrorAction malformedInputAction;
    private final float maxCharsPerByte;
    private String replacement;
    private int state;
    private CodingErrorAction unmappableCharacterAction;

    protected abstract CoderResult decodeLoop(ByteBuffer byteBuffer, CharBuffer charBuffer);

    protected void implOnMalformedInput(CodingErrorAction codingErrorAction) {
    }

    protected void implOnUnmappableCharacter(CodingErrorAction codingErrorAction) {
    }

    protected void implReplaceWith(String str) {
    }

    protected void implReset() {
    }

    public boolean isAutoDetecting() {
        return false;
    }

    private CharsetDecoder(Charset charset, float f, float f2, String str) {
        this.malformedInputAction = CodingErrorAction.REPORT;
        this.unmappableCharacterAction = CodingErrorAction.REPORT;
        this.state = 0;
        this.charset = charset;
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Non-positive averageCharsPerByte");
        }
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("Non-positive maxCharsPerByte");
        }
        if (f > f2) {
            throw new IllegalArgumentException("averageCharsPerByte exceeds maxCharsPerByte");
        }
        this.replacement = str;
        this.averageCharsPerByte = f;
        this.maxCharsPerByte = f2;
        replaceWith(str);
    }

    protected CharsetDecoder(Charset charset, float f, float f2) {
        this(charset, f, f2, "�");
    }

    public final Charset charset() {
        return this.charset;
    }

    public final String replacement() {
        return this.replacement;
    }

    public final CharsetDecoder replaceWith(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Null replacement");
        }
        int length = str.length();
        if (length == 0) {
            throw new IllegalArgumentException("Empty replacement");
        }
        if (length > this.maxCharsPerByte) {
            throw new IllegalArgumentException("Replacement too long");
        }
        this.replacement = str;
        implReplaceWith(str);
        return this;
    }

    public CodingErrorAction malformedInputAction() {
        return this.malformedInputAction;
    }

    public final CharsetDecoder onMalformedInput(CodingErrorAction codingErrorAction) {
        if (codingErrorAction == null) {
            throw new IllegalArgumentException("Null action");
        }
        this.malformedInputAction = codingErrorAction;
        implOnMalformedInput(codingErrorAction);
        return this;
    }

    public CodingErrorAction unmappableCharacterAction() {
        return this.unmappableCharacterAction;
    }

    public final CharsetDecoder onUnmappableCharacter(CodingErrorAction codingErrorAction) {
        if (codingErrorAction == null) {
            throw new IllegalArgumentException("Null action");
        }
        this.unmappableCharacterAction = codingErrorAction;
        implOnUnmappableCharacter(codingErrorAction);
        return this;
    }

    public final float averageCharsPerByte() {
        return this.averageCharsPerByte;
    }

    public final float maxCharsPerByte() {
        return this.maxCharsPerByte;
    }

    public final CoderResult decode(ByteBuffer byteBuffer, CharBuffer charBuffer, boolean z) {
        CoderResult decodeLoop;
        CodingErrorAction codingErrorAction;
        int i = z ? 2 : 1;
        int i2 = this.state;
        if (i2 != 0 && i2 != 1 && (!z || i2 != 2)) {
            throwIllegalStateException(i2, i);
        }
        this.state = i;
        while (true) {
            try {
                decodeLoop = decodeLoop(byteBuffer, charBuffer);
                if (decodeLoop.isOverflow()) {
                    break;
                }
                if (decodeLoop.isUnderflow()) {
                    if (!z || !byteBuffer.hasRemaining()) {
                        break;
                    }
                    decodeLoop = CoderResult.malformedForLength(byteBuffer.remaining());
                }
                if (decodeLoop.isMalformed()) {
                    codingErrorAction = this.malformedInputAction;
                } else {
                    codingErrorAction = decodeLoop.isUnmappable() ? this.unmappableCharacterAction : null;
                }
                if (codingErrorAction == CodingErrorAction.REPORT) {
                    return decodeLoop;
                }
                if (codingErrorAction == CodingErrorAction.REPLACE) {
                    if (charBuffer.remaining() < this.replacement.length()) {
                        return CoderResult.OVERFLOW;
                    }
                    charBuffer.put(this.replacement);
                }
                if (codingErrorAction == CodingErrorAction.IGNORE || codingErrorAction == CodingErrorAction.REPLACE) {
                    byteBuffer.position(byteBuffer.position() + decodeLoop.length());
                }
            } catch (BufferOverflowException e) {
                throw new CoderMalfunctionError(e);
            } catch (BufferUnderflowException e2) {
                throw new CoderMalfunctionError(e2);
            }
        }
        return decodeLoop;
    }

    public final CoderResult flush(CharBuffer charBuffer) {
        int i = this.state;
        if (i == 2) {
            CoderResult implFlush = implFlush(charBuffer);
            if (implFlush.isUnderflow()) {
                this.state = 3;
            }
            return implFlush;
        }
        if (i != 3) {
            throwIllegalStateException(i, 3);
        }
        return CoderResult.UNDERFLOW;
    }

    protected CoderResult implFlush(CharBuffer charBuffer) {
        return CoderResult.UNDERFLOW;
    }

    public final CharsetDecoder reset() {
        implReset();
        this.state = 0;
        return this;
    }

    public final CharBuffer decode(ByteBuffer byteBuffer) throws CharacterCodingException {
        int remaining = (int) (byteBuffer.remaining() * averageCharsPerByte());
        CharBuffer allocate = CharBuffer.allocate(remaining);
        if (remaining == 0 && byteBuffer.remaining() == 0) {
            return allocate;
        }
        reset();
        while (true) {
            CoderResult decode = byteBuffer.hasRemaining() ? decode(byteBuffer, allocate, true) : CoderResult.UNDERFLOW;
            if (decode.isUnderflow()) {
                decode = flush(allocate);
            }
            if (!decode.isUnderflow()) {
                if (decode.isOverflow()) {
                    remaining = (remaining * 2) + 1;
                    CharBuffer allocate2 = CharBuffer.allocate(remaining);
                    allocate.flip();
                    allocate2.put(allocate);
                    allocate = allocate2;
                } else {
                    decode.throwException();
                }
            } else {
                allocate.flip();
                return allocate;
            }
        }
    }

    public boolean isCharsetDetected() {
        throw new UnsupportedOperationException();
    }

    public Charset detectedCharset() {
        throw new UnsupportedOperationException();
    }

    private void throwIllegalStateException(int i, int i2) {
        String[] strArr = stateNames;
        throw new IllegalStateException("Current state = " + strArr[i] + ", new state = " + strArr[i2]);
    }
}
