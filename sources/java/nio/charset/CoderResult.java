package java.nio.charset;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class CoderResult {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CR_ERROR_MIN = 2;
    private static final int CR_MALFORMED = 2;
    private static final int CR_OVERFLOW = 1;
    private static final int CR_UNDERFLOW = 0;
    private static final int CR_UNMAPPABLE = 3;
    private final int length;
    private final int type;
    private static final String[] names = {"UNDERFLOW", "OVERFLOW", "MALFORMED", "UNMAPPABLE"};
    public static final CoderResult UNDERFLOW = new CoderResult(0, 0);
    public static final CoderResult OVERFLOW = new CoderResult(1, 0);
    private static final CoderResult[] malformed4 = {new CoderResult(2, 1), new CoderResult(2, 2), new CoderResult(2, 3), new CoderResult(2, 4)};
    private static final CoderResult[] unmappable4 = {new CoderResult(3, 1), new CoderResult(3, 2), new CoderResult(3, 3), new CoderResult(3, 4)};

    private CoderResult(int i, int i2) {
        this.type = i;
        this.length = i2;
    }

    public String toString() {
        String str = names[this.type];
        if (!isError()) {
            return str;
        }
        return str + "[" + this.length + "]";
    }

    public boolean isUnderflow() {
        return this.type == 0;
    }

    public boolean isOverflow() {
        return this.type == 1;
    }

    public boolean isError() {
        return this.type >= 2;
    }

    public boolean isMalformed() {
        return this.type == 2;
    }

    public boolean isUnmappable() {
        return this.type == 3;
    }

    public int length() {
        if (!isError()) {
            throw new UnsupportedOperationException();
        }
        return this.length;
    }

    private static final class Cache {
        static final Cache INSTANCE = new Cache();
        final Map unmappable = new ConcurrentHashMap();
        final Map malformed = new ConcurrentHashMap();

        private Cache() {
        }
    }

    public static CoderResult malformedForLength(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Non-positive length");
        }
        if (i <= 4) {
            return malformed4[i - 1];
        }
        return (CoderResult) Cache.INSTANCE.malformed.computeIfAbsent(Integer.valueOf(i), new CoderResult$$ExternalSyntheticLambda0());
    }

    static /* synthetic */ CoderResult lambda$malformedForLength$0(Integer num) {
        return new CoderResult(2, num.intValue());
    }

    public static CoderResult unmappableForLength(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Non-positive length");
        }
        if (i <= 4) {
            return unmappable4[i - 1];
        }
        return (CoderResult) Cache.INSTANCE.unmappable.computeIfAbsent(Integer.valueOf(i), new CoderResult$$ExternalSyntheticLambda1());
    }

    static /* synthetic */ CoderResult lambda$unmappableForLength$1(Integer num) {
        return new CoderResult(3, num.intValue());
    }

    public void throwException() throws CharacterCodingException {
        int i = this.type;
        if (i == 0) {
            throw new BufferUnderflowException();
        }
        if (i == 1) {
            throw new BufferOverflowException();
        }
        if (i == 2) {
            throw new MalformedInputException(this.length);
        }
        if (i == 3) {
            throw new UnmappableCharacterException(this.length);
        }
    }
}
