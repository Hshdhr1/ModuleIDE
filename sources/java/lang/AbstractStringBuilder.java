package java.lang;

import java.lang.StringLatin1;
import java.lang.StringUTF16;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import jdk.internal.math.FloatingDecimal;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    private static final byte[] EMPTYVALUE = new byte[0];
    private static final int MAX_ARRAY_SIZE = 2147483639;
    byte coder;
    int count;
    byte[] value;

    public abstract String toString();

    AbstractStringBuilder() {
        this.value = EMPTYVALUE;
    }

    AbstractStringBuilder(int i) {
        if (String.COMPACT_STRINGS) {
            this.value = new byte[i];
            this.coder = (byte) 0;
        } else {
            this.value = StringUTF16.newBytesFor(i);
            this.coder = (byte) 1;
        }
    }

    int compareTo(AbstractStringBuilder abstractStringBuilder) {
        if (this == abstractStringBuilder) {
            return 0;
        }
        byte[] bArr = this.value;
        byte[] bArr2 = abstractStringBuilder.value;
        int i = this.count;
        int i2 = abstractStringBuilder.count;
        return this.coder == abstractStringBuilder.coder ? isLatin1() ? StringLatin1.compareTo(bArr, bArr2, i, i2) : StringUTF16.compareTo(bArr, bArr2, i, i2) : isLatin1() ? StringLatin1.compareToUTF16(bArr, bArr2, i, i2) : StringUTF16.compareToLatin1(bArr, bArr2, i, i2);
    }

    public int length() {
        return this.count;
    }

    public int capacity() {
        return this.value.length >> this.coder;
    }

    public void ensureCapacity(int i) {
        if (i > 0) {
            ensureCapacityInternal(i);
        }
    }

    private void ensureCapacityInternal(int i) {
        byte[] bArr = this.value;
        if (i - (bArr.length >> this.coder) > 0) {
            this.value = Arrays.copyOf(bArr, newCapacity(i) << this.coder);
        }
    }

    private int newCapacity(int i) {
        int length = this.value.length;
        byte b = this.coder;
        int i2 = ((length >> b) << 1) + 2;
        if (i2 - i < 0) {
            i2 = i;
        }
        return (i2 <= 0 || (2147483639 >> b) - i2 < 0) ? hugeCapacity(i) : i2;
    }

    private int hugeCapacity(int i) {
        byte b = this.coder;
        int i2 = 2147483639 >> b;
        if ((2147483647 >> b) - i >= 0) {
            return i > i2 ? i : i2;
        }
        throw new OutOfMemoryError();
    }

    private void inflate() {
        if (isLatin1()) {
            byte[] newBytesFor = StringUTF16.newBytesFor(this.value.length);
            StringLatin1.inflate(this.value, 0, newBytesFor, 0, this.count);
            this.value = newBytesFor;
            this.coder = (byte) 1;
        }
    }

    public void trimToSize() {
        int i = this.count << this.coder;
        byte[] bArr = this.value;
        if (i < bArr.length) {
            this.value = Arrays.copyOf(bArr, i);
        }
    }

    public void setLength(int i) {
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        }
        ensureCapacityInternal(i);
        if (this.count < i) {
            if (isLatin1()) {
                StringLatin1.fillNull(this.value, this.count, i);
            } else {
                StringUTF16.fillNull(this.value, this.count, i);
            }
        }
        this.count = i;
    }

    public char charAt(int i) {
        String.checkIndex(i, this.count);
        if (isLatin1()) {
            return (char) (this.value[i] & 255);
        }
        return StringUTF16.charAt(this.value, i);
    }

    public int codePointAt(int i) {
        int i2 = this.count;
        byte[] bArr = this.value;
        String.checkIndex(i, i2);
        if (isLatin1()) {
            return bArr[i] & 255;
        }
        return StringUTF16.codePointAtSB(bArr, i, i2);
    }

    public int codePointBefore(int i) {
        int i2 = i - 1;
        if (i2 < 0 || i2 >= this.count) {
            throw new StringIndexOutOfBoundsException(i);
        }
        if (isLatin1()) {
            return this.value[i2] & 255;
        }
        return StringUTF16.codePointBeforeSB(this.value, i);
    }

    public int codePointCount(int i, int i2) {
        if (i < 0 || i2 > this.count || i > i2) {
            throw new IndexOutOfBoundsException();
        }
        return isLatin1() ? i2 - i : StringUTF16.codePointCountSB(this.value, i, i2);
    }

    public int offsetByCodePoints(int i, int i2) {
        if (i < 0 || i > this.count) {
            throw new IndexOutOfBoundsException();
        }
        return Character.offsetByCodePoints(this, i, i2);
    }

    public void getChars(int i, int i2, char[] cArr, int i3) {
        checkRangeSIOOBE(i, i2, this.count);
        checkRange(i3, (i2 - i) + i3, cArr.length);
        if (isLatin1()) {
            StringLatin1.getChars(this.value, i, i2, cArr, i3);
        } else {
            StringUTF16.getChars(this.value, i, i2, cArr, i3);
        }
    }

    public void setCharAt(int i, char c) {
        String.checkIndex(i, this.count);
        if (isLatin1() && StringLatin1.canEncode(c)) {
            this.value[i] = (byte) c;
            return;
        }
        if (isLatin1()) {
            inflate();
        }
        StringUTF16.putCharSB(this.value, i, c);
    }

    public AbstractStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    public AbstractStringBuilder append(String str) {
        if (str == null) {
            return appendNull();
        }
        int length = str.length();
        ensureCapacityInternal(this.count + length);
        putStringAt(this.count, str);
        this.count += length;
        return this;
    }

    public AbstractStringBuilder append(StringBuffer stringBuffer) {
        return append((AbstractStringBuilder) stringBuffer);
    }

    AbstractStringBuilder append(AbstractStringBuilder abstractStringBuilder) {
        if (abstractStringBuilder == null) {
            return appendNull();
        }
        int length = abstractStringBuilder.length();
        ensureCapacityInternal(this.count + length);
        if (getCoder() != abstractStringBuilder.getCoder()) {
            inflate();
        }
        abstractStringBuilder.getBytes(this.value, this.count, this.coder);
        this.count += length;
        return this;
    }

    public AbstractStringBuilder append(CharSequence charSequence) {
        if (charSequence == null) {
            return appendNull();
        }
        if (charSequence instanceof String) {
            return append((String) charSequence);
        }
        if (charSequence instanceof AbstractStringBuilder) {
            return append((AbstractStringBuilder) charSequence);
        }
        return append(charSequence, 0, charSequence.length());
    }

    private AbstractStringBuilder appendNull() {
        int putCharsAt;
        ensureCapacityInternal(this.count + 4);
        int i = this.count;
        byte[] bArr = this.value;
        if (isLatin1()) {
            bArr[i] = 110;
            bArr[i + 1] = 117;
            int i2 = i + 3;
            bArr[i + 2] = 108;
            putCharsAt = i + 4;
            bArr[i2] = 108;
        } else {
            putCharsAt = StringUTF16.putCharsAt(bArr, i, 'n', 'u', 'l', 'l');
        }
        this.count = putCharsAt;
        return this;
    }

    public AbstractStringBuilder append(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            charSequence = "null";
        }
        checkRange(i, i2, charSequence.length());
        ensureCapacityInternal(this.count + (i2 - i));
        appendChars(charSequence, i, i2);
        return this;
    }

    public AbstractStringBuilder append(char[] cArr) {
        int length = cArr.length;
        ensureCapacityInternal(this.count + length);
        appendChars(cArr, 0, length);
        return this;
    }

    public AbstractStringBuilder append(char[] cArr, int i, int i2) {
        int i3 = i + i2;
        checkRange(i, i3, cArr.length);
        ensureCapacityInternal(this.count + i2);
        appendChars(cArr, i, i3);
        return this;
    }

    public AbstractStringBuilder append(boolean z) {
        int putCharsAt;
        ensureCapacityInternal(this.count + (z ? 4 : 5));
        int i = this.count;
        byte[] bArr = this.value;
        if (isLatin1()) {
            if (z) {
                bArr[i] = 116;
                bArr[i + 1] = 114;
                int i2 = i + 3;
                bArr[i + 2] = 117;
                putCharsAt = i + 4;
                bArr[i2] = 101;
            } else {
                bArr[i] = 102;
                bArr[i + 1] = 97;
                bArr[i + 2] = 108;
                int i3 = i + 4;
                bArr[i + 3] = 115;
                putCharsAt = i + 5;
                bArr[i3] = 101;
            }
        } else if (z) {
            putCharsAt = StringUTF16.putCharsAt(bArr, i, 't', 'r', 'u', 'e');
        } else {
            putCharsAt = StringUTF16.putCharsAt(bArr, i, 'f', 'a', 'l', 's', 'e');
        }
        this.count = putCharsAt;
        return this;
    }

    public AbstractStringBuilder append(char c) {
        ensureCapacityInternal(this.count + 1);
        if (isLatin1() && StringLatin1.canEncode(c)) {
            byte[] bArr = this.value;
            int i = this.count;
            this.count = i + 1;
            bArr[i] = (byte) c;
            return this;
        }
        if (isLatin1()) {
            inflate();
        }
        byte[] bArr2 = this.value;
        int i2 = this.count;
        this.count = i2 + 1;
        StringUTF16.putCharSB(bArr2, i2, c);
        return this;
    }

    public AbstractStringBuilder append(int i) {
        int i2 = this.count;
        int stringSize = Integer.stringSize(i) + i2;
        ensureCapacityInternal(stringSize);
        if (isLatin1()) {
            Integer.getChars(i, stringSize, this.value);
        } else {
            StringUTF16.getChars(i, i2, stringSize, this.value);
        }
        this.count = stringSize;
        return this;
    }

    public AbstractStringBuilder append(long j) {
        int i = this.count;
        int stringSize = Long.stringSize(j) + i;
        ensureCapacityInternal(stringSize);
        if (isLatin1()) {
            Long.getChars(j, stringSize, this.value);
        } else {
            StringUTF16.getChars(j, i, stringSize, this.value);
        }
        this.count = stringSize;
        return this;
    }

    public AbstractStringBuilder append(float f) {
        FloatingDecimal.appendTo(f, this);
        return this;
    }

    public AbstractStringBuilder append(double d) {
        FloatingDecimal.appendTo(d, this);
        return this;
    }

    public AbstractStringBuilder delete(int i, int i2) {
        int i3 = this.count;
        if (i2 > i3) {
            i2 = i3;
        }
        checkRangeSIOOBE(i, i2, i3);
        int i4 = i2 - i;
        if (i4 > 0) {
            shift(i2, -i4);
            this.count = i3 - i4;
        }
        return this;
    }

    public AbstractStringBuilder appendCodePoint(int i) {
        if (Character.isBmpCodePoint(i)) {
            return append((char) i);
        }
        return append(Character.toChars(i));
    }

    public AbstractStringBuilder deleteCharAt(int i) {
        String.checkIndex(i, this.count);
        shift(i + 1, -1);
        this.count--;
        return this;
    }

    public AbstractStringBuilder replace(int i, int i2, String str) {
        int i3 = this.count;
        if (i2 > i3) {
            i2 = i3;
        }
        checkRangeSIOOBE(i, i2, i3);
        int length = (str.length() + i3) - (i2 - i);
        ensureCapacityInternal(length);
        shift(i2, length - i3);
        this.count = length;
        putStringAt(i, str);
        return this;
    }

    public String substring(int i) {
        return substring(i, this.count);
    }

    public CharSequence subSequence(int i, int i2) {
        return substring(i, i2);
    }

    public String substring(int i, int i2) {
        checkRangeSIOOBE(i, i2, this.count);
        if (isLatin1()) {
            return StringLatin1.newString(this.value, i, i2 - i);
        }
        return StringUTF16.newString(this.value, i, i2 - i);
    }

    private void shift(int i, int i2) {
        byte[] bArr = this.value;
        byte b = this.coder;
        System.arraycopy(bArr, i << b, bArr, (i2 + i) << b, (this.count - i) << b);
    }

    public AbstractStringBuilder insert(int i, char[] cArr, int i2, int i3) {
        String.checkOffset(i, this.count);
        int i4 = i2 + i3;
        checkRangeSIOOBE(i2, i4, cArr.length);
        ensureCapacityInternal(this.count + i3);
        shift(i, i3);
        this.count += i3;
        putCharsAt(i, cArr, i2, i4);
        return this;
    }

    public AbstractStringBuilder insert(int i, Object obj) {
        return insert(i, String.valueOf(obj));
    }

    public AbstractStringBuilder insert(int i, String str) {
        String.checkOffset(i, this.count);
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        ensureCapacityInternal(this.count + length);
        shift(i, length);
        this.count += length;
        putStringAt(i, str);
        return this;
    }

    public AbstractStringBuilder insert(int i, char[] cArr) {
        String.checkOffset(i, this.count);
        int length = cArr.length;
        ensureCapacityInternal(this.count + length);
        shift(i, length);
        this.count += length;
        putCharsAt(i, cArr, 0, length);
        return this;
    }

    public AbstractStringBuilder insert(int i, CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "null";
        }
        if (charSequence instanceof String) {
            return insert(i, (String) charSequence);
        }
        return insert(i, charSequence, 0, charSequence.length());
    }

    public AbstractStringBuilder insert(int i, CharSequence charSequence, int i2, int i3) {
        if (charSequence == null) {
            charSequence = "null";
        }
        String.checkOffset(i, this.count);
        checkRange(i2, i3, charSequence.length());
        int i4 = i3 - i2;
        ensureCapacityInternal(this.count + i4);
        shift(i, i4);
        this.count += i4;
        putCharsAt(i, charSequence, i2, i3);
        return this;
    }

    public AbstractStringBuilder insert(int i, boolean z) {
        return insert(i, String.valueOf(z));
    }

    public AbstractStringBuilder insert(int i, char c) {
        String.checkOffset(i, this.count);
        ensureCapacityInternal(this.count + 1);
        shift(i, 1);
        this.count++;
        if (isLatin1() && StringLatin1.canEncode(c)) {
            this.value[i] = (byte) c;
            return this;
        }
        if (isLatin1()) {
            inflate();
        }
        StringUTF16.putCharSB(this.value, i, c);
        return this;
    }

    public AbstractStringBuilder insert(int i, int i2) {
        return insert(i, String.valueOf(i2));
    }

    public AbstractStringBuilder insert(int i, long j) {
        return insert(i, String.valueOf(j));
    }

    public AbstractStringBuilder insert(int i, float f) {
        return insert(i, String.valueOf(f));
    }

    public AbstractStringBuilder insert(int i, double d) {
        return insert(i, String.valueOf(d));
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public int indexOf(String str, int i) {
        return String.indexOf(this.value, this.coder, this.count, str, i);
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, this.count);
    }

    public int lastIndexOf(String str, int i) {
        return String.lastIndexOf(this.value, this.coder, this.count, str, i);
    }

    public AbstractStringBuilder reverse() {
        byte[] bArr = this.value;
        int i = this.count;
        byte b = this.coder;
        int i2 = i - 1;
        if (!String.COMPACT_STRINGS || b != 0) {
            StringUTF16.reverse(bArr, i);
            return this;
        }
        for (int i3 = (i - 2) >> 1; i3 >= 0; i3--) {
            int i4 = i2 - i3;
            byte b2 = bArr[i3];
            bArr[i3] = bArr[i4];
            bArr[i4] = b2;
        }
        return this;
    }

    public IntStream chars() {
        return StreamSupport.intStream(new AbstractStringBuilder$$ExternalSyntheticLambda0(this), 16464, false);
    }

    /* synthetic */ Spliterator.OfInt lambda$chars$0$java-lang-AbstractStringBuilder() {
        byte[] bArr = this.value;
        int i = this.count;
        if (this.coder == 0) {
            return new StringLatin1.CharsSpliterator(bArr, 0, i, 0);
        }
        return new StringUTF16.CharsSpliterator(bArr, 0, i, 0);
    }

    public IntStream codePoints() {
        return StreamSupport.intStream(new AbstractStringBuilder$$ExternalSyntheticLambda1(this), 16, false);
    }

    /* synthetic */ Spliterator.OfInt lambda$codePoints$1$java-lang-AbstractStringBuilder() {
        byte[] bArr = this.value;
        int i = this.count;
        if (this.coder == 0) {
            return new StringLatin1.CharsSpliterator(bArr, 0, i, 0);
        }
        return new StringUTF16.CodePointsSpliterator(bArr, 0, i, 0);
    }

    final byte[] getValue() {
        return this.value;
    }

    void getBytes(byte[] bArr, int i, byte b) {
        if (this.coder == b) {
            System.arraycopy(this.value, 0, bArr, i << b, this.count << b);
        } else {
            StringLatin1.inflate(this.value, 0, bArr, i, this.count);
        }
    }

    void initBytes(char[] cArr, int i, int i2) {
        if (String.COMPACT_STRINGS) {
            byte[] compress = StringUTF16.compress(cArr, i, i2);
            this.value = compress;
            if (compress != null) {
                this.coder = (byte) 0;
                return;
            }
        }
        this.coder = (byte) 1;
        this.value = StringUTF16.toBytes(cArr, i, i2);
    }

    final byte getCoder() {
        if (String.COMPACT_STRINGS) {
            return this.coder;
        }
        return (byte) 1;
    }

    final boolean isLatin1() {
        return String.COMPACT_STRINGS && this.coder == 0;
    }

    private final void putCharsAt(int i, char[] cArr, int i2, int i3) {
        if (isLatin1()) {
            byte[] bArr = this.value;
            while (i2 < i3) {
                char c = cArr[i2];
                if (StringLatin1.canEncode(c)) {
                    bArr[i] = (byte) c;
                    i2++;
                    i++;
                } else {
                    inflate();
                    StringUTF16.putCharsSB(this.value, i, cArr, i2, i3);
                    return;
                }
            }
            return;
        }
        StringUTF16.putCharsSB(this.value, i, cArr, i2, i3);
    }

    private final void putCharsAt(int i, CharSequence charSequence, int i2, int i3) {
        if (isLatin1()) {
            byte[] bArr = this.value;
            while (i2 < i3) {
                char charAt = charSequence.charAt(i2);
                if (StringLatin1.canEncode(charAt)) {
                    bArr[i] = (byte) charAt;
                    i2++;
                    i++;
                } else {
                    inflate();
                    StringUTF16.putCharsSB(this.value, i, charSequence, i2, i3);
                    return;
                }
            }
            return;
        }
        StringUTF16.putCharsSB(this.value, i, charSequence, i2, i3);
    }

    private final void putStringAt(int i, String str) {
        if (getCoder() != str.coder()) {
            inflate();
        }
        str.getBytes(this.value, i, this.coder);
    }

    private final void appendChars(char[] cArr, int i, int i2) {
        int i3 = this.count;
        if (isLatin1()) {
            byte[] bArr = this.value;
            int i4 = i;
            int i5 = i3;
            while (i4 < i2) {
                char c = cArr[i4];
                if (StringLatin1.canEncode(c)) {
                    bArr[i5] = (byte) c;
                    i4++;
                    i5++;
                } else {
                    this.count = i5;
                    inflate();
                    StringUTF16.putCharsSB(this.value, i5, cArr, i4, i2);
                    this.count = (i5 + i2) - i4;
                    return;
                }
            }
        } else {
            StringUTF16.putCharsSB(this.value, i3, cArr, i, i2);
        }
        this.count = (i3 + i2) - i;
    }

    private final void appendChars(CharSequence charSequence, int i, int i2) {
        if (isLatin1()) {
            byte[] bArr = this.value;
            int i3 = this.count;
            int i4 = i;
            while (i4 < i2) {
                char charAt = charSequence.charAt(i4);
                if (StringLatin1.canEncode(charAt)) {
                    bArr[i3] = (byte) charAt;
                    i4++;
                    i3++;
                } else {
                    this.count = i3;
                    inflate();
                    StringUTF16.putCharsSB(this.value, i3, charSequence, i4, i2);
                    this.count += i2 - i4;
                    return;
                }
            }
        } else {
            StringUTF16.putCharsSB(this.value, this.count, charSequence, i, i2);
        }
        this.count += i2 - i;
    }

    private static void checkRange(int i, int i2, int i3) {
        if (i < 0 || i > i2 || i2 > i3) {
            throw new IndexOutOfBoundsException("start " + i + ", end " + i2 + ", length " + i3);
        }
    }

    private static void checkRangeSIOOBE(int i, int i2, int i3) {
        if (i < 0 || i > i2 || i2 > i3) {
            throw new StringIndexOutOfBoundsException("start " + i + ", end " + i2 + ", length " + i3);
        }
    }
}
