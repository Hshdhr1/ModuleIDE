package org.antlr.v4.runtime;

import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.misc.Interval;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public abstract class CodePointCharStream implements CharStream {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected final String name;
    protected int position;
    protected final int size;

    abstract Object getInternalStorage();

    static {
        $assertionsDisabled = !CodePointCharStream.class.desiredAssertionStatus();
    }

    /* synthetic */ CodePointCharStream(int x0, int x1, String x2, 1 x3) {
        this(x0, x1, x2);
    }

    private CodePointCharStream(int position, int remaining, String name) {
        if (!$assertionsDisabled && position != 0) {
            throw new AssertionError();
        }
        this.size = remaining;
        this.name = name;
        this.position = 0;
    }

    public static CodePointCharStream fromBuffer(CodePointBuffer codePointBuffer) {
        return fromBuffer(codePointBuffer, "<unknown>");
    }

    public static CodePointCharStream fromBuffer(CodePointBuffer codePointBuffer, String name) {
        switch (codePointBuffer.getType()) {
            case BYTE:
                return new CodePoint8BitCharStream(codePointBuffer.position(), codePointBuffer.remaining(), name, codePointBuffer.byteArray(), codePointBuffer.arrayOffset(), null);
            case CHAR:
                return new CodePoint16BitCharStream(codePointBuffer.position(), codePointBuffer.remaining(), name, codePointBuffer.charArray(), codePointBuffer.arrayOffset(), null);
            case INT:
                return new CodePoint32BitCharStream(codePointBuffer.position(), codePointBuffer.remaining(), name, codePointBuffer.intArray(), codePointBuffer.arrayOffset(), null);
            default:
                throw new UnsupportedOperationException("Not reached");
        }
    }

    public final void consume() {
        if (this.size - this.position == 0) {
            if (!$assertionsDisabled && LA(1) != -1) {
                throw new AssertionError();
            }
            throw new IllegalStateException("cannot consume EOF");
        }
        this.position++;
    }

    public final int index() {
        return this.position;
    }

    public final int size() {
        return this.size;
    }

    public final int mark() {
        return -1;
    }

    public final void release(int marker) {
    }

    public final void seek(int index) {
        this.position = index;
    }

    public final String getSourceName() {
        return (this.name == null || this.name.isEmpty()) ? "<unknown>" : this.name;
    }

    public final String toString() {
        return getText(Interval.of(0, this.size - 1));
    }

    private static final class CodePoint8BitCharStream extends CodePointCharStream {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final byte[] byteArray;

        static {
            $assertionsDisabled = !CodePointCharStream.class.desiredAssertionStatus();
        }

        /* synthetic */ CodePoint8BitCharStream(int x0, int x1, String x2, byte[] x3, int x4, 1 x5) {
            this(x0, x1, x2, x3, x4);
        }

        private CodePoint8BitCharStream(int position, int remaining, String name, byte[] byteArray, int arrayOffset) {
            super(position, remaining, name, null);
            if (!$assertionsDisabled && arrayOffset != 0) {
                throw new AssertionError();
            }
            this.byteArray = byteArray;
        }

        public String getText(Interval interval) {
            int startIdx = Math.min(interval.a, this.size);
            int len = Math.min((interval.b - interval.a) + 1, this.size - startIdx);
            return new String(this.byteArray, startIdx, len, StandardCharsets.ISO_8859_1);
        }

        public int LA(int i) {
            switch (Integer.signum(i)) {
                case -1:
                    int offset = this.position + i;
                    if (offset < 0) {
                        return -1;
                    }
                    return this.byteArray[offset] & 255;
                case 0:
                    return 0;
                case 1:
                    int offset2 = (this.position + i) - 1;
                    if (offset2 < this.size) {
                        return this.byteArray[offset2] & 255;
                    }
                    return -1;
                default:
                    throw new UnsupportedOperationException("Not reached");
            }
        }

        Object getInternalStorage() {
            return this.byteArray;
        }
    }

    private static final class CodePoint16BitCharStream extends CodePointCharStream {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final char[] charArray;

        static {
            $assertionsDisabled = !CodePointCharStream.class.desiredAssertionStatus();
        }

        /* synthetic */ CodePoint16BitCharStream(int x0, int x1, String x2, char[] x3, int x4, 1 x5) {
            this(x0, x1, x2, x3, x4);
        }

        private CodePoint16BitCharStream(int position, int remaining, String name, char[] charArray, int arrayOffset) {
            super(position, remaining, name, null);
            this.charArray = charArray;
            if (!$assertionsDisabled && arrayOffset != 0) {
                throw new AssertionError();
            }
        }

        public String getText(Interval interval) {
            int startIdx = Math.min(interval.a, this.size);
            int len = Math.min((interval.b - interval.a) + 1, this.size - startIdx);
            return new String(this.charArray, startIdx, len);
        }

        public int LA(int i) {
            switch (Integer.signum(i)) {
                case -1:
                    int offset = this.position + i;
                    if (offset < 0) {
                        return -1;
                    }
                    return this.charArray[offset] & 65535;
                case 0:
                    return 0;
                case 1:
                    int offset2 = (this.position + i) - 1;
                    if (offset2 < this.size) {
                        return this.charArray[offset2] & 65535;
                    }
                    return -1;
                default:
                    throw new UnsupportedOperationException("Not reached");
            }
        }

        Object getInternalStorage() {
            return this.charArray;
        }
    }

    private static final class CodePoint32BitCharStream extends CodePointCharStream {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final int[] intArray;

        static {
            $assertionsDisabled = !CodePointCharStream.class.desiredAssertionStatus();
        }

        /* synthetic */ CodePoint32BitCharStream(int x0, int x1, String x2, int[] x3, int x4, 1 x5) {
            this(x0, x1, x2, x3, x4);
        }

        private CodePoint32BitCharStream(int position, int remaining, String name, int[] intArray, int arrayOffset) {
            super(position, remaining, name, null);
            this.intArray = intArray;
            if (!$assertionsDisabled && arrayOffset != 0) {
                throw new AssertionError();
            }
        }

        public String getText(Interval interval) {
            int startIdx = Math.min(interval.a, this.size);
            int len = Math.min((interval.b - interval.a) + 1, this.size - startIdx);
            return new String(this.intArray, startIdx, len);
        }

        public int LA(int i) {
            switch (Integer.signum(i)) {
                case -1:
                    int offset = this.position + i;
                    if (offset < 0) {
                        return -1;
                    }
                    return this.intArray[offset];
                case 0:
                    return 0;
                case 1:
                    int offset2 = (this.position + i) - 1;
                    if (offset2 < this.size) {
                        return this.intArray[offset2];
                    }
                    return -1;
                default:
                    throw new UnsupportedOperationException("Not reached");
            }
        }

        Object getInternalStorage() {
            return this.intArray;
        }
    }
}
