package java.lang;

import java.io.ObjectStreamField;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.StringCoding;
import java.lang.StringLatin1;
import java.lang.StringUTF16;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class String implements Serializable, Comparable, CharSequence {
    static final boolean COMPACT_STRINGS = true;
    static final byte LATIN1 = 0;
    static final byte UTF16 = 1;
    private static final long serialVersionUID = -6849794470754667710L;
    private final byte coder;
    private int hash;
    private final byte[] value;
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    public static final Comparator CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator(null);

    static /* bridge */ /* synthetic */ byte[] -$$Nest$fgetvalue(String str) {
        return str.value;
    }

    static /* bridge */ /* synthetic */ boolean -$$Nest$misLatin1(String str) {
        return str.isLatin1();
    }

    public native String intern();

    public String toString() {
        return this;
    }

    public String() {
        this.value = "".value;
        this.coder = "".coder;
    }

    public String(String str) {
        this.value = str.value;
        this.coder = str.coder;
        this.hash = str.hash;
    }

    public String(char[] cArr) {
        this(cArr, 0, cArr.length, (Void) null);
    }

    public String(char[] cArr, int i, int i2) {
        this(cArr, i, i2, rangeCheck(cArr, i, i2));
    }

    private static Void rangeCheck(char[] cArr, int i, int i2) {
        checkBoundsOffCount(i, i2, cArr.length);
        return null;
    }

    public String(int[] iArr, int i, int i2) {
        byte[] bytes;
        checkBoundsOffCount(i, i2, iArr.length);
        if (i2 == 0) {
            this.value = "".value;
            this.coder = "".coder;
        } else if (COMPACT_STRINGS && (bytes = StringLatin1.toBytes(iArr, i, i2)) != null) {
            this.coder = (byte) 0;
            this.value = bytes;
        } else {
            this.coder = (byte) 1;
            this.value = StringUTF16.toBytes(iArr, i, i2);
        }
    }

    @Deprecated(since = "1.1")
    public String(byte[] bArr, int i, int i2, int i3) {
        checkBoundsOffCount(i2, i3, bArr.length);
        if (i3 == 0) {
            this.value = "".value;
            this.coder = "".coder;
            return;
        }
        int i4 = 0;
        if (COMPACT_STRINGS && ((byte) i) == 0) {
            this.value = Arrays.copyOfRange(bArr, i2, i3 + i2);
            this.coder = (byte) 0;
            return;
        }
        int i5 = i << 8;
        byte[] newBytesFor = StringUTF16.newBytesFor(i3);
        while (i4 < i3) {
            StringUTF16.putChar(newBytesFor, i4, (bArr[i2] & 255) | i5);
            i4++;
            i2++;
        }
        this.value = newBytesFor;
        this.coder = (byte) 1;
    }

    @Deprecated(since = "1.1")
    public String(byte[] bArr, int i) {
        this(bArr, i, 0, bArr.length);
    }

    public String(byte[] bArr, int i, int i2, String str) throws UnsupportedEncodingException {
        if (str == null) {
            throw new NullPointerException("charsetName");
        }
        checkBoundsOffCount(i, i2, bArr.length);
        StringCoding.Result decode = StringCoding.decode(str, bArr, i, i2);
        this.value = decode.value;
        this.coder = decode.coder;
    }

    public String(byte[] bArr, int i, int i2, Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        checkBoundsOffCount(i, i2, bArr.length);
        StringCoding.Result decode = StringCoding.decode(charset, bArr, i, i2);
        this.value = decode.value;
        this.coder = decode.coder;
    }

    public String(byte[] bArr, String str) throws UnsupportedEncodingException {
        this(bArr, 0, bArr.length, str);
    }

    public String(byte[] bArr, Charset charset) {
        this(bArr, 0, bArr.length, charset);
    }

    public String(byte[] bArr, int i, int i2) {
        checkBoundsOffCount(i, i2, bArr.length);
        StringCoding.Result decode = StringCoding.decode(bArr, i, i2);
        this.value = decode.value;
        this.coder = decode.coder;
    }

    public String(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public String(StringBuffer stringBuffer) {
        this(stringBuffer.toString());
    }

    public String(StringBuilder sb) {
        this((AbstractStringBuilder) sb, (Void) null);
    }

    public int length() {
        return this.value.length >> coder();
    }

    public boolean isEmpty() {
        return this.value.length == 0;
    }

    public char charAt(int i) {
        if (isLatin1()) {
            return StringLatin1.charAt(this.value, i);
        }
        return StringUTF16.charAt(this.value, i);
    }

    public int codePointAt(int i) {
        if (isLatin1()) {
            checkIndex(i, this.value.length);
            return this.value[i] & 255;
        }
        int length = this.value.length >> 1;
        checkIndex(i, length);
        return StringUTF16.codePointAt(this.value, i, length);
    }

    public int codePointBefore(int i) {
        int i2 = i - 1;
        if (i2 < 0 || i2 >= length()) {
            throw new StringIndexOutOfBoundsException(i);
        }
        if (isLatin1()) {
            return this.value[i2] & 255;
        }
        return StringUTF16.codePointBefore(this.value, i);
    }

    public int codePointCount(int i, int i2) {
        if (i < 0 || i > i2 || i2 > length()) {
            throw new IndexOutOfBoundsException();
        }
        return isLatin1() ? i2 - i : StringUTF16.codePointCount(this.value, i, i2);
    }

    public int offsetByCodePoints(int i, int i2) {
        if (i < 0 || i > length()) {
            throw new IndexOutOfBoundsException();
        }
        return Character.offsetByCodePoints(this, i, i2);
    }

    public void getChars(int i, int i2, char[] cArr, int i3) {
        checkBoundsBeginEnd(i, i2, length());
        checkBoundsOffCount(i3, i2 - i, cArr.length);
        if (isLatin1()) {
            StringLatin1.getChars(this.value, i, i2, cArr, i3);
        } else {
            StringUTF16.getChars(this.value, i, i2, cArr, i3);
        }
    }

    @Deprecated(since = "1.1")
    public void getBytes(int i, int i2, byte[] bArr, int i3) {
        checkBoundsBeginEnd(i, i2, length());
        bArr.getClass();
        checkBoundsOffCount(i3, i2 - i, bArr.length);
        if (isLatin1()) {
            StringLatin1.getBytes(this.value, i, i2, bArr, i3);
        } else {
            StringUTF16.getBytes(this.value, i, i2, bArr, i3);
        }
    }

    public byte[] getBytes(String str) throws UnsupportedEncodingException {
        str.getClass();
        return StringCoding.encode(str, coder(), this.value);
    }

    public byte[] getBytes(Charset charset) {
        charset.getClass();
        return StringCoding.encode(charset, coder(), this.value);
    }

    public byte[] getBytes() {
        return StringCoding.encode(coder(), this.value);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof String)) {
            return false;
        }
        String str = (String) obj;
        if (coder() == str.coder()) {
            return isLatin1() ? StringLatin1.equals(this.value, str.value) : StringUTF16.equals(this.value, str.value);
        }
        return false;
    }

    public boolean contentEquals(StringBuffer stringBuffer) {
        return contentEquals((CharSequence) stringBuffer);
    }

    private boolean nonSyncContentEquals(AbstractStringBuilder abstractStringBuilder) {
        int length = length();
        if (length != abstractStringBuilder.length()) {
            return false;
        }
        byte[] bArr = this.value;
        byte[] value = abstractStringBuilder.getValue();
        if (coder() == abstractStringBuilder.getCoder()) {
            int length2 = bArr.length;
            for (int i = 0; i < length2; i++) {
                if (bArr[i] != value[i]) {
                    return false;
                }
            }
            return true;
        }
        if (isLatin1()) {
            return StringUTF16.contentEquals(bArr, value, length);
        }
        return false;
    }

    public boolean contentEquals(CharSequence charSequence) {
        boolean nonSyncContentEquals;
        if (charSequence instanceof AbstractStringBuilder) {
            if (charSequence instanceof StringBuffer) {
                synchronized (charSequence) {
                    nonSyncContentEquals = nonSyncContentEquals((AbstractStringBuilder) charSequence);
                }
                return nonSyncContentEquals;
            }
            return nonSyncContentEquals((AbstractStringBuilder) charSequence);
        }
        if (charSequence instanceof String) {
            return equals(charSequence);
        }
        int length = charSequence.length();
        if (length != length()) {
            return false;
        }
        byte[] bArr = this.value;
        if (!isLatin1()) {
            return StringUTF16.contentEquals(bArr, charSequence, length);
        }
        for (int i = 0; i < length; i++) {
            if ((bArr[i] & 255) != charSequence.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsIgnoreCase(String str) {
        if (this == str) {
            return true;
        }
        return str != null && str.length() == length() && regionMatches(true, 0, str, 0, length());
    }

    public int compareTo(String str) {
        byte[] bArr = this.value;
        byte[] bArr2 = str.value;
        return coder() == str.coder() ? isLatin1() ? StringLatin1.compareTo(bArr, bArr2) : StringUTF16.compareTo(bArr, bArr2) : isLatin1() ? StringLatin1.compareToUTF16(bArr, bArr2) : StringUTF16.compareToLatin1(bArr, bArr2);
    }

    private static class CaseInsensitiveComparator implements Comparator, Serializable {
        private static final long serialVersionUID = 8575799808933029326L;

        /* synthetic */ CaseInsensitiveComparator(String-IA r1) {
            this();
        }

        public /* synthetic */ Comparator reversed() {
            return Comparator.-CC.$default$reversed(this);
        }

        public /* synthetic */ Comparator thenComparing(Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, comparator);
        }

        public /* synthetic */ Comparator thenComparing(Function function) {
            return Comparator.-CC.$default$thenComparing(this, function);
        }

        public /* synthetic */ Comparator thenComparing(Function function, Comparator comparator) {
            return Comparator.-CC.$default$thenComparing(this, function, comparator);
        }

        public /* synthetic */ Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
            return Comparator.-CC.$default$thenComparingDouble(this, toDoubleFunction);
        }

        public /* synthetic */ Comparator thenComparingInt(ToIntFunction toIntFunction) {
            return Comparator.-CC.$default$thenComparingInt(this, toIntFunction);
        }

        public /* synthetic */ Comparator thenComparingLong(ToLongFunction toLongFunction) {
            return Comparator.-CC.$default$thenComparingLong(this, toLongFunction);
        }

        private CaseInsensitiveComparator() {
        }

        public int compare(String str, String str2) {
            byte[] bArr = String.-$$Nest$fgetvalue(str);
            byte[] bArr2 = String.-$$Nest$fgetvalue(str2);
            return str.coder() == str2.coder() ? String.-$$Nest$misLatin1(str) ? StringLatin1.compareToCI(bArr, bArr2) : StringUTF16.compareToCI(bArr, bArr2) : String.-$$Nest$misLatin1(str) ? StringLatin1.compareToCI_UTF16(bArr, bArr2) : StringUTF16.compareToCI_Latin1(bArr, bArr2);
        }

        private Object readResolve() {
            return String.CASE_INSENSITIVE_ORDER;
        }
    }

    public int compareToIgnoreCase(String str) {
        return CASE_INSENSITIVE_ORDER.compare(this, str);
    }

    public boolean regionMatches(int i, String str, int i2, int i3) {
        byte[] bArr = this.value;
        byte[] bArr2 = str.value;
        if (i2 >= 0 && i >= 0) {
            long j = i3;
            if (i <= length() - j && i2 <= str.length() - j) {
                if (coder() == str.coder()) {
                    if (!isLatin1() && i3 > 0) {
                        i <<= 1;
                        i2 <<= 1;
                        i3 <<= 1;
                    }
                    while (true) {
                        int i4 = i3 - 1;
                        if (i3 <= 0) {
                            return true;
                        }
                        int i5 = i + 1;
                        int i6 = i2 + 1;
                        if (bArr[i] != bArr2[i2]) {
                            return false;
                        }
                        i = i5;
                        i2 = i6;
                        i3 = i4;
                    }
                } else if (coder() == 0) {
                    while (true) {
                        int i7 = i3 - 1;
                        if (i3 <= 0) {
                            return true;
                        }
                        int i8 = i + 1;
                        int i9 = i2 + 1;
                        if (StringLatin1.getChar(bArr, i) != StringUTF16.getChar(bArr2, i2)) {
                            return false;
                        }
                        i = i8;
                        i2 = i9;
                        i3 = i7;
                    }
                } else {
                    while (true) {
                        int i10 = i3 - 1;
                        if (i3 <= 0) {
                            return true;
                        }
                        int i11 = i + 1;
                        int i12 = i2 + 1;
                        if (StringUTF16.getChar(bArr, i) != StringLatin1.getChar(bArr2, i2)) {
                            return false;
                        }
                        i = i11;
                        i2 = i12;
                        i3 = i10;
                    }
                }
            }
        }
        return false;
    }

    public boolean regionMatches(boolean z, int i, String str, int i2, int i3) {
        if (!z) {
            return regionMatches(i, str, i2, i3);
        }
        if (i2 < 0 || i < 0) {
            return false;
        }
        long j = i3;
        if (i > length() - j || i2 > str.length() - j) {
            return false;
        }
        byte[] bArr = this.value;
        byte[] bArr2 = str.value;
        if (coder() == str.coder()) {
            if (isLatin1()) {
                return StringLatin1.regionMatchesCI(bArr, i, bArr2, i2, i3);
            }
            return StringUTF16.regionMatchesCI(bArr, i, bArr2, i2, i3);
        }
        if (isLatin1()) {
            return StringLatin1.regionMatchesCI_UTF16(bArr, i, bArr2, i2, i3);
        }
        return StringUTF16.regionMatchesCI_Latin1(bArr, i, bArr2, i2, i3);
    }

    public boolean startsWith(String str, int i) {
        if (i < 0 || i > length() - str.length()) {
            return false;
        }
        byte[] bArr = this.value;
        byte[] bArr2 = str.value;
        int length = bArr2.length;
        if (coder() == str.coder()) {
            if (!isLatin1()) {
                i <<= 1;
            }
            int i2 = 0;
            while (i2 < length) {
                int i3 = i + 1;
                int i4 = i2 + 1;
                if (bArr[i] != bArr2[i2]) {
                    return false;
                }
                i = i3;
                i2 = i4;
            }
            return true;
        }
        if (isLatin1()) {
            return false;
        }
        int i5 = 0;
        while (i5 < length) {
            int i6 = i + 1;
            int i7 = i5 + 1;
            if (StringUTF16.getChar(bArr, i) != (bArr2[i5] & 255)) {
                return false;
            }
            i = i6;
            i5 = i7;
        }
        return true;
    }

    public boolean startsWith(String str) {
        return startsWith(str, 0);
    }

    public boolean endsWith(String str) {
        return startsWith(str, length() - str.length());
    }

    public int hashCode() {
        int i = this.hash;
        if (i == 0 && this.value.length > 0) {
            i = isLatin1() ? StringLatin1.hashCode(this.value) : StringUTF16.hashCode(this.value);
            this.hash = i;
        }
        return i;
    }

    public int indexOf(int i) {
        return indexOf(i, 0);
    }

    public int indexOf(int i, int i2) {
        return isLatin1() ? StringLatin1.indexOf(this.value, i, i2) : StringUTF16.indexOf(this.value, i, i2);
    }

    public int lastIndexOf(int i) {
        return lastIndexOf(i, length() - 1);
    }

    public int lastIndexOf(int i, int i2) {
        return isLatin1() ? StringLatin1.lastIndexOf(this.value, i, i2) : StringUTF16.lastIndexOf(this.value, i, i2);
    }

    public int indexOf(String str) {
        if (coder() == str.coder()) {
            return isLatin1() ? StringLatin1.indexOf(this.value, str.value) : StringUTF16.indexOf(this.value, str.value);
        }
        if (coder() == 0) {
            return -1;
        }
        return StringUTF16.indexOfLatin1(this.value, str.value);
    }

    public int indexOf(String str, int i) {
        return indexOf(this.value, coder(), length(), str, i);
    }

    static int indexOf(byte[] bArr, byte b, int i, String str, int i2) {
        byte[] bArr2 = str.value;
        byte coder = str.coder();
        int length = str.length();
        if (i2 >= i) {
            if (length == 0) {
                return i;
            }
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (length == 0) {
            return i2;
        }
        if (length > i) {
            return -1;
        }
        if (b == coder) {
            if (b == 0) {
                return StringLatin1.indexOf(bArr, i, bArr2, length, i2);
            }
            return StringUTF16.indexOf(bArr, i, bArr2, length, i2);
        }
        if (b == 0) {
            return -1;
        }
        return StringUTF16.indexOfLatin1(bArr, i, bArr2, length, i2);
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, length());
    }

    public int lastIndexOf(String str, int i) {
        return lastIndexOf(this.value, coder(), length(), str, i);
    }

    static int lastIndexOf(byte[] bArr, byte b, int i, String str, int i2) {
        byte[] bArr2 = str.value;
        byte coder = str.coder();
        int length = str.length();
        int i3 = i - length;
        if (i2 > i3) {
            i2 = i3;
        }
        if (i2 < 0) {
            return -1;
        }
        if (length == 0) {
            return i2;
        }
        if (b == coder) {
            if (b == 0) {
                return StringLatin1.lastIndexOf(bArr, i, bArr2, length, i2);
            }
            return StringUTF16.lastIndexOf(bArr, i, bArr2, length, i2);
        }
        if (b == 0) {
            return -1;
        }
        return StringUTF16.lastIndexOfLatin1(bArr, i, bArr2, length, i2);
    }

    public String substring(int i) {
        if (i < 0) {
            throw new StringIndexOutOfBoundsException(i);
        }
        int length = length() - i;
        if (length >= 0) {
            return i == 0 ? this : isLatin1() ? StringLatin1.newString(this.value, i, length) : StringUTF16.newString(this.value, i, length);
        }
        throw new StringIndexOutOfBoundsException(length);
    }

    public String substring(int i, int i2) {
        int length = length();
        checkBoundsBeginEnd(i, i2, length);
        int i3 = i2 - i;
        return (i == 0 && i2 == length) ? this : isLatin1() ? StringLatin1.newString(this.value, i, i3) : StringUTF16.newString(this.value, i, i3);
    }

    public CharSequence subSequence(int i, int i2) {
        return substring(i, i2);
    }

    public String concat(String str) {
        if (str.isEmpty()) {
            return this;
        }
        if (coder() == str.coder()) {
            byte[] bArr = this.value;
            byte[] bArr2 = str.value;
            byte[] copyOf = Arrays.copyOf(bArr, bArr.length + bArr2.length);
            System.arraycopy(bArr2, 0, copyOf, bArr.length, bArr2.length);
            return new String(copyOf, this.coder);
        }
        int length = length();
        byte[] newBytesFor = StringUTF16.newBytesFor(str.length() + length);
        getBytes(newBytesFor, 0, (byte) 1);
        str.getBytes(newBytesFor, length, (byte) 1);
        return new String(newBytesFor, (byte) 1);
    }

    public String replace(char c, char c2) {
        if (c != c2) {
            String replace = isLatin1() ? StringLatin1.replace(this.value, c, c2) : StringUTF16.replace(this.value, c, c2);
            if (replace != null) {
                return replace;
            }
        }
        return this;
    }

    public boolean matches(String str) {
        return Pattern.matches(str, this);
    }

    public boolean contains(CharSequence charSequence) {
        return indexOf(charSequence.toString()) >= 0;
    }

    public String replaceFirst(String str, String str2) {
        return Pattern.compile(str).matcher(this).replaceFirst(str2);
    }

    public String replaceAll(String str, String str2) {
        return Pattern.compile(str).matcher(this).replaceAll(str2);
    }

    public String replace(CharSequence charSequence, CharSequence charSequence2) {
        String charSequence3 = charSequence.toString();
        String charSequence4 = charSequence2.toString();
        int indexOf = indexOf(charSequence3);
        if (indexOf < 0) {
            return this;
        }
        int length = charSequence3.length();
        int max = Math.max(length, 1);
        int length2 = length();
        int length3 = (length2 - length) + charSequence4.length();
        if (length3 < 0) {
            throw new OutOfMemoryError();
        }
        StringBuilder sb = new StringBuilder(length3);
        int i = 0;
        do {
            sb.append(this, i, indexOf);
            sb.append(charSequence4);
            i = indexOf + length;
            if (indexOf >= length2) {
                break;
            }
            indexOf = indexOf(charSequence3, indexOf + max);
        } while (indexOf > 0);
        sb.append(this, i, length2);
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x003d A[PHI: r0
      0x003d: PHI (r0v4 char) = (r0v3 char), (r0v6 char) binds: [B:53:0x003b, B:4:0x0013] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] split(java.lang.String r10, int r11) {
        /*
            r9 = this;
            int r0 = r10.length()
            r1 = -1
            r2 = 0
            r3 = 1
            if (r0 != r3) goto L15
            char r0 = r10.charAt(r2)
            java.lang.String r4 = ".$|()[{^?*+\\"
            int r4 = r4.indexOf(r0)
            if (r4 == r1) goto L3d
        L15:
            int r0 = r10.length()
            r4 = 2
            if (r0 != r4) goto Lba
            char r0 = r10.charAt(r2)
            r4 = 92
            if (r0 != r4) goto Lba
            char r0 = r10.charAt(r3)
            int r4 = r0 + (-48)
            int r5 = 57 - r0
            r4 = r4 | r5
            if (r4 >= 0) goto Lba
            int r4 = r0 + (-97)
            int r5 = 122 - r0
            r4 = r4 | r5
            if (r4 >= 0) goto Lba
            int r4 = r0 + (-65)
            int r5 = 90 - r0
            r4 = r4 | r5
            if (r4 >= 0) goto Lba
        L3d:
            r4 = 55296(0xd800, float:7.7486E-41)
            if (r0 < r4) goto L47
            r4 = 57343(0xdfff, float:8.0355E-41)
            if (r0 <= r4) goto Lba
        L47:
            if (r11 <= 0) goto L4b
            r10 = 1
            goto L4c
        L4b:
            r10 = 0
        L4c:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
        L52:
            int r6 = r9.indexOf(r0, r5)
            if (r6 == r1) goto L7a
            if (r10 == 0) goto L70
            int r7 = r4.size()
            int r8 = r11 + (-1)
            if (r7 >= r8) goto L63
            goto L70
        L63:
            int r0 = r9.length()
            java.lang.String r1 = r9.substring(r5, r0)
            r4.add(r1)
            r5 = r0
            goto L7a
        L70:
            java.lang.String r5 = r9.substring(r5, r6)
            r4.add(r5)
            int r5 = r6 + 1
            goto L52
        L7a:
            if (r5 != 0) goto L81
            java.lang.String[] r10 = new java.lang.String[r3]
            r10[r2] = r9
            return r10
        L81:
            if (r10 == 0) goto L89
            int r10 = r4.size()
            if (r10 >= r11) goto L94
        L89:
            int r10 = r9.length()
            java.lang.String r10 = r9.substring(r5, r10)
            r4.add(r10)
        L94:
            int r10 = r4.size()
            if (r11 != 0) goto Lad
        L9a:
            if (r10 <= 0) goto Lad
            int r11 = r10 + (-1)
            java.lang.Object r11 = r4.get(r11)
            java.lang.String r11 = (java.lang.String) r11
            boolean r11 = r11.isEmpty()
            if (r11 == 0) goto Lad
            int r10 = r10 + (-1)
            goto L9a
        Lad:
            java.lang.String[] r11 = new java.lang.String[r10]
            java.util.List r10 = r4.subList(r2, r10)
            java.lang.Object[] r10 = r10.toArray(r11)
            java.lang.String[] r10 = (java.lang.String[]) r10
            return r10
        Lba:
            java.util.regex.Pattern r10 = java.util.regex.Pattern.compile(r10)
            java.lang.String[] r10 = r10.split(r9, r11)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: java.lang.String.split(java.lang.String, int):java.lang.String[]");
    }

    public String[] split(String str) {
        return split(str, 0);
    }

    public static String join(CharSequence charSequence, CharSequence... charSequenceArr) {
        charSequence.getClass();
        charSequenceArr.getClass();
        StringJoiner stringJoiner = new StringJoiner(charSequence);
        for (CharSequence charSequence2 : charSequenceArr) {
            stringJoiner.add(charSequence2);
        }
        return stringJoiner.toString();
    }

    public static String join(CharSequence charSequence, Iterable iterable) {
        charSequence.getClass();
        iterable.getClass();
        StringJoiner stringJoiner = new StringJoiner(charSequence);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            stringJoiner.add((CharSequence) it.next());
        }
        return stringJoiner.toString();
    }

    public String toLowerCase(Locale locale) {
        return isLatin1() ? StringLatin1.toLowerCase(this, this.value, locale) : StringUTF16.toLowerCase(this, this.value, locale);
    }

    public String toLowerCase() {
        return toLowerCase(Locale.getDefault());
    }

    public String toUpperCase(Locale locale) {
        return isLatin1() ? StringLatin1.toUpperCase(this, this.value, locale) : StringUTF16.toUpperCase(this, this.value, locale);
    }

    public String toUpperCase() {
        return toUpperCase(Locale.getDefault());
    }

    public String trim() {
        String trim = isLatin1() ? StringLatin1.trim(this.value) : StringUTF16.trim(this.value);
        return trim == null ? this : trim;
    }

    public String strip() {
        if (!isEmpty()) {
            int i = 0;
            if (Character.isWhitespace(charAt(0)) || Character.isWhitespace(length() - 1)) {
                while (Character.isWhitespace(charAt(i))) {
                    i++;
                }
                int length = length();
                do {
                    length--;
                } while (Character.isWhitespace(charAt(length)));
                return substring(i, length + 1);
            }
        }
        return this;
    }

    public String stripLeading() {
        if (!isEmpty()) {
            int i = 0;
            if (Character.isWhitespace(charAt(0))) {
                while (Character.isWhitespace(charAt(i))) {
                    i++;
                }
                return substring(i);
            }
        }
        return this;
    }

    public String stripTrailing() {
        if (isEmpty() || !Character.isWhitespace(length() - 1)) {
            return this;
        }
        int length = length();
        do {
            length--;
        } while (Character.isWhitespace(charAt(length)));
        return substring(0, length + 1);
    }

    public boolean isBlank() {
        for (char c : toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    private int indexOfNonWhitespace() {
        if (isLatin1()) {
            return StringLatin1.indexOfNonWhitespace(this.value);
        }
        return StringUTF16.indexOfNonWhitespace(this.value);
    }

    public Stream lines() {
        return Arrays.stream(split("\n"));
    }

    public IntStream chars() {
        IntStream.Builder builder = IntStream.-CC.builder();
        for (char c : toCharArray()) {
            builder.add(c);
        }
        return builder.build();
    }

    public IntStream codePoints() {
        return StreamSupport.intStream(isLatin1() ? new StringLatin1.CharsSpliterator(this.value, 1024) : new StringUTF16.CodePointsSpliterator(this.value, 1024), false);
    }

    public char[] toCharArray() {
        return isLatin1() ? StringLatin1.toChars(this.value) : StringUTF16.toChars(this.value);
    }

    public static String format(String str, Object... objArr) {
        return new Formatter().format(str, objArr).toString();
    }

    public static String format(Locale locale, String str, Object... objArr) {
        return new Formatter(locale).format(str, objArr).toString();
    }

    public static String valueOf(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    public static String valueOf(char[] cArr) {
        return new String(cArr);
    }

    public static String valueOf(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    public static String copyValueOf(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    public static String copyValueOf(char[] cArr) {
        return new String(cArr);
    }

    public static String valueOf(boolean z) {
        return z ? "true" : "false";
    }

    public static String valueOf(char c) {
        if (COMPACT_STRINGS && StringLatin1.canEncode(c)) {
            return new String(StringLatin1.toBytes(c), (byte) 0);
        }
        return new String(StringUTF16.toBytes(c), (byte) 1);
    }

    public static String valueOf(int i) {
        return Integer.toString(i);
    }

    public static String valueOf(long j) {
        return Long.toString(j);
    }

    public static String valueOf(float f) {
        return Float.toString(f);
    }

    public static String valueOf(double d) {
        return Double.toString(d);
    }

    public String repeat(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("count is negative: " + i);
        }
        if (i == 1) {
            return this;
        }
        byte[] bArr = this.value;
        int length = bArr.length;
        if (length == 0 || i == 0) {
            return "";
        }
        if (length == 1) {
            byte[] bArr2 = new byte[i];
            Arrays.fill(bArr2, bArr[0]);
            return new String(bArr2, this.coder);
        }
        if (Integer.MAX_VALUE / i < length) {
            throw new OutOfMemoryError("Repeating " + length + " bytes String " + i + " times will produce a String exceeding maximum size.");
        }
        int i2 = i * length;
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        while (true) {
            int i3 = i2 - length;
            if (length < i3) {
                System.arraycopy(bArr3, 0, bArr3, length, length);
                length <<= 1;
            } else {
                System.arraycopy(bArr3, 0, bArr3, length, i3);
                return new String(bArr3, this.coder);
            }
        }
    }

    void getBytes(byte[] bArr, int i, byte b) {
        if (coder() == b) {
            byte[] bArr2 = this.value;
            System.arraycopy(bArr2, 0, bArr, i << b, bArr2.length);
        } else {
            byte[] bArr3 = this.value;
            StringLatin1.inflate(bArr3, 0, bArr, i, bArr3.length);
        }
    }

    String(char[] cArr, int i, int i2, Void r4) {
        byte[] compress;
        if (i2 == 0) {
            this.value = "".value;
            this.coder = "".coder;
        } else if (COMPACT_STRINGS && (compress = StringUTF16.compress(cArr, i, i2)) != null) {
            this.value = compress;
            this.coder = (byte) 0;
        } else {
            this.coder = (byte) 1;
            this.value = StringUTF16.toBytes(cArr, i, i2);
        }
    }

    String(AbstractStringBuilder abstractStringBuilder, Void r4) {
        byte[] compress;
        byte[] value = abstractStringBuilder.getValue();
        int length = abstractStringBuilder.length();
        if (abstractStringBuilder.isLatin1()) {
            this.coder = (byte) 0;
            this.value = Arrays.copyOfRange(value, 0, length);
        } else if (COMPACT_STRINGS && (compress = StringUTF16.compress(value, 0, length)) != null) {
            this.coder = (byte) 0;
            this.value = compress;
        } else {
            this.coder = (byte) 1;
            this.value = Arrays.copyOfRange(value, 0, length << 1);
        }
    }

    String(byte[] bArr, byte b) {
        this.value = bArr;
        this.coder = b;
    }

    byte coder() {
        if (COMPACT_STRINGS) {
            return this.coder;
        }
        return (byte) 1;
    }

    byte[] value() {
        return this.value;
    }

    private boolean isLatin1() {
        return COMPACT_STRINGS && this.coder == 0;
    }

    static void checkIndex(int i, int i2) {
        if (i < 0 || i >= i2) {
            throw new StringIndexOutOfBoundsException("index " + i + ",length " + i2);
        }
    }

    static void checkOffset(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new StringIndexOutOfBoundsException("offset " + i + ",length " + i2);
        }
    }

    static void checkBoundsOffCount(int i, int i2, int i3) {
        if (i < 0 || i2 < 0 || i > i3 - i2) {
            throw new StringIndexOutOfBoundsException("offset " + i + ", count " + i2 + ", length " + i3);
        }
    }

    static void checkBoundsBeginEnd(int i, int i2, int i3) {
        if (i < 0 || i > i2 || i2 > i3) {
            throw new StringIndexOutOfBoundsException("begin " + i + ", end " + i2 + ", length " + i3);
        }
    }

    static String valueOfCodePoint(int i) {
        if (COMPACT_STRINGS && StringLatin1.canEncode(i)) {
            return new String(StringLatin1.toBytes((char) i), (byte) 0);
        }
        if (Character.isBmpCodePoint(i)) {
            return new String(StringUTF16.toBytes((char) i), (byte) 1);
        }
        if (Character.isSupplementaryCodePoint(i)) {
            return new String(StringUTF16.toBytesSupplementary(i), (byte) 1);
        }
        throw new IllegalArgumentException(format("Not a valid Unicode code point: 0x%X", Integer.valueOf(i)));
    }
}
