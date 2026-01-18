package sun.nio.cs;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class CharsetMapping {
    private static final int MAP_COMPOSITE = 7;
    private static final int MAP_DOUBLEBYTE1 = 2;
    private static final int MAP_DOUBLEBYTE2 = 3;
    private static final int MAP_INDEXC2B = 8;
    private static final int MAP_SINGLEBYTE = 1;
    private static final int MAP_SUPPLEMENT = 5;
    private static final int MAP_SUPPLEMENT_C2B = 6;
    public static final char UNMAPPABLE_DECODING = 65533;
    public static final int UNMAPPABLE_ENCODING = 65533;
    static Comparator comparatorBytes = new 2();
    static Comparator comparatorCP = new 3();
    static Comparator comparatorComp = new 4();
    int b1MaxDB1;
    int b1MaxDB2;
    int b1MinDB1;
    int b1MinDB2;
    int b2Max;
    int b2Min;
    Entry[] b2cComp;
    char[] b2cDB1;
    char[] b2cDB2;
    char[] b2cSB;
    char[] b2cSupp;
    byte[] bb;
    char[] c2b;
    Entry[] c2bComp;
    char[] c2bIndex;
    char[] c2bSupp;
    int dbSegSize;
    int off = 0;

    public static class Entry {
        public int bs;
        public int cp;
        public int cp2;
    }

    public char decodeSingle(int i) {
        return this.b2cSB[i];
    }

    public char decodeDouble(int i, int i2) {
        int i3 = this.b2Min;
        if (i2 < i3 || i2 >= this.b2Max) {
            return (char) 65533;
        }
        int i4 = i2 - i3;
        int i5 = this.b1MinDB1;
        if (i >= i5 && i <= this.b1MaxDB1) {
            return this.b2cDB1[((i - i5) * this.dbSegSize) + i4];
        }
        int i6 = this.b1MinDB2;
        if (i < i6 || i > this.b1MaxDB2) {
            return (char) 65533;
        }
        return this.b2cDB2[((i - i6) * this.dbSegSize) + i4];
    }

    public char[] decodeSurrogate(int i, char[] cArr) {
        char[] cArr2 = this.b2cSupp;
        int length = cArr2.length / 2;
        int binarySearch = Arrays.binarySearch(cArr2, 0, length, (char) i);
        if (binarySearch < 0) {
            return null;
        }
        Character.toChars(this.b2cSupp[length + binarySearch] + 0, cArr, 0);
        return cArr;
    }

    public char[] decodeComposite(Entry entry, char[] cArr) {
        int findBytes = findBytes(this.b2cComp, entry);
        if (findBytes < 0) {
            return null;
        }
        cArr[0] = (char) this.b2cComp[findBytes].cp;
        cArr[1] = (char) this.b2cComp[findBytes].cp2;
        return cArr;
    }

    public int encodeChar(char c) {
        char c2 = this.c2bIndex[c >> '\b'];
        if (c2 == 65535) {
            return 65533;
        }
        return this.c2b[c2 + (c & 255)];
    }

    public int encodeSurrogate(char c, char c2) {
        int codePoint = Character.toCodePoint(c, c2);
        if (codePoint >= 131072 && codePoint < 196608) {
            char[] cArr = this.c2bSupp;
            int length = cArr.length / 2;
            int binarySearch = Arrays.binarySearch(cArr, 0, length, (char) codePoint);
            if (binarySearch >= 0) {
                return this.c2bSupp[length + binarySearch];
            }
        }
        return 65533;
    }

    public boolean isCompositeBase(Entry entry) {
        return entry.cp <= 12791 && entry.cp >= 230 && findCP(this.c2bComp, entry) >= 0;
    }

    public int encodeComposite(Entry entry) {
        int findComp = findComp(this.c2bComp, entry);
        if (findComp >= 0) {
            return this.c2bComp[findComp].bs;
        }
        return 65533;
    }

    class 1 implements PrivilegedAction {
        final /* synthetic */ InputStream val$is;

        1(InputStream inputStream) {
            this.val$is = inputStream;
        }

        public CharsetMapping run() {
            return new CharsetMapping().load(this.val$is);
        }
    }

    public static CharsetMapping get(InputStream inputStream) {
        return (CharsetMapping) AccessController.doPrivileged(new 1(inputStream));
    }

    class 2 implements Comparator {
        public boolean equals(Object obj) {
            return this == obj;
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

        2() {
        }

        public int compare(Entry entry, Entry entry2) {
            return entry.bs - entry2.bs;
        }
    }

    class 3 implements Comparator {
        public boolean equals(Object obj) {
            return this == obj;
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

        3() {
        }

        public int compare(Entry entry, Entry entry2) {
            return entry.cp - entry2.cp;
        }
    }

    class 4 implements Comparator {
        public boolean equals(Object obj) {
            return this == obj;
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

        4() {
        }

        public int compare(Entry entry, Entry entry2) {
            int i = entry.cp - entry2.cp;
            return i == 0 ? entry.cp2 - entry2.cp2 : i;
        }
    }

    static int findBytes(Entry[] entryArr, Entry entry) {
        return Arrays.binarySearch(entryArr, 0, entryArr.length, entry, comparatorBytes);
    }

    static int findCP(Entry[] entryArr, Entry entry) {
        return Arrays.binarySearch(entryArr, 0, entryArr.length, entry, comparatorCP);
    }

    static int findComp(Entry[] entryArr, Entry entry) {
        return Arrays.binarySearch(entryArr, 0, entryArr.length, entry, comparatorComp);
    }

    private static final boolean readNBytes(InputStream inputStream, byte[] bArr, int i) throws IOException {
        int i2 = 0;
        while (i > 0) {
            int read = inputStream.read(bArr, i2, i);
            if (read == -1) {
                return false;
            }
            i -= read;
            i2 += read;
        }
        return true;
    }

    private char[] readCharArray() {
        byte[] bArr = this.bb;
        int i = this.off;
        int i2 = i + 1;
        this.off = i2;
        int i3 = (bArr[i] & 255) << 8;
        this.off = i + 2;
        int i4 = (bArr[i2] & 255) | i3;
        char[] cArr = new char[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            byte[] bArr2 = this.bb;
            int i6 = this.off;
            int i7 = i6 + 1;
            this.off = i7;
            int i8 = (bArr2[i6] & 255) << 8;
            this.off = i6 + 2;
            cArr[i5] = (char) ((bArr2[i7] & 255) | i8);
        }
        return cArr;
    }

    void readSINGLEBYTE() {
        char[] readCharArray = readCharArray();
        for (int i = 0; i < readCharArray.length; i++) {
            char c = readCharArray[i];
            if (c != 65533) {
                this.c2b[this.c2bIndex[c >> '\b'] + (c & 255)] = (char) i;
            }
        }
        this.b2cSB = readCharArray;
    }

    void readINDEXC2B() {
        char c;
        char[] readCharArray = readCharArray();
        int length = readCharArray.length - 1;
        while (true) {
            if (length >= 0) {
                if (this.c2b == null && (c = readCharArray[length]) != 65535) {
                    char[] cArr = new char[c + 256];
                    this.c2b = cArr;
                    Arrays.fill(cArr, (char) 65533);
                    break;
                }
                length--;
            } else {
                break;
            }
        }
        this.c2bIndex = readCharArray;
    }

    char[] readDB(int i, int i2, int i3) {
        char[] readCharArray = readCharArray();
        for (int i4 = 0; i4 < readCharArray.length; i4++) {
            char c = readCharArray[i4];
            if (c != 65533) {
                this.c2b[this.c2bIndex[c >> '\b'] + (c & 255)] = (char) ((((i4 / i3) + i) * 256) + (i4 % i3) + i2);
            }
        }
        return readCharArray;
    }

    void readDOUBLEBYTE1() {
        byte[] bArr = this.bb;
        int i = this.off;
        int i2 = i + 1;
        this.off = i2;
        int i3 = (bArr[i] & 255) << 8;
        int i4 = i + 2;
        this.off = i4;
        int i5 = (bArr[i2] & 255) | i3;
        this.b1MinDB1 = i5;
        int i6 = i + 3;
        this.off = i6;
        int i7 = (bArr[i4] & 255) << 8;
        int i8 = i + 4;
        this.off = i8;
        this.b1MaxDB1 = (bArr[i6] & 255) | i7;
        int i9 = i + 5;
        this.off = i9;
        int i10 = (bArr[i8] & 255) << 8;
        int i11 = i + 6;
        this.off = i11;
        int i12 = (bArr[i9] & 255) | i10;
        this.b2Min = i12;
        int i13 = i + 7;
        this.off = i13;
        int i14 = (bArr[i11] & 255) << 8;
        this.off = i + 8;
        int i15 = (bArr[i13] & 255) | i14;
        this.b2Max = i15;
        int i16 = (i15 - i12) + 1;
        this.dbSegSize = i16;
        this.b2cDB1 = readDB(i5, i12, i16);
    }

    void readDOUBLEBYTE2() {
        byte[] bArr = this.bb;
        int i = this.off;
        int i2 = i + 1;
        this.off = i2;
        int i3 = (bArr[i] & 255) << 8;
        int i4 = i + 2;
        this.off = i4;
        int i5 = (bArr[i2] & 255) | i3;
        this.b1MinDB2 = i5;
        int i6 = i + 3;
        this.off = i6;
        int i7 = (bArr[i4] & 255) << 8;
        int i8 = i + 4;
        this.off = i8;
        this.b1MaxDB2 = (bArr[i6] & 255) | i7;
        int i9 = i + 5;
        this.off = i9;
        int i10 = (bArr[i8] & 255) << 8;
        int i11 = i + 6;
        this.off = i11;
        int i12 = (bArr[i9] & 255) | i10;
        this.b2Min = i12;
        int i13 = i + 7;
        this.off = i13;
        int i14 = (bArr[i11] & 255) << 8;
        this.off = i + 8;
        int i15 = (bArr[i13] & 255) | i14;
        this.b2Max = i15;
        int i16 = (i15 - i12) + 1;
        this.dbSegSize = i16;
        this.b2cDB2 = readDB(i5, i12, i16);
    }

    void readCOMPOSITE() {
        char[] readCharArray = readCharArray();
        int length = readCharArray.length / 3;
        this.b2cComp = new Entry[length];
        this.c2bComp = new Entry[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            Entry entry = new Entry();
            entry.bs = readCharArray[i];
            int i3 = i + 2;
            entry.cp = readCharArray[i + 1];
            i += 3;
            entry.cp2 = readCharArray[i3];
            this.b2cComp[i2] = entry;
            this.c2bComp[i2] = entry;
        }
        Entry[] entryArr = this.c2bComp;
        Arrays.sort(entryArr, 0, entryArr.length, comparatorComp);
    }

    CharsetMapping load(InputStream inputStream) {
        try {
            int read = ((inputStream.read() & 255) << 24) | ((inputStream.read() & 255) << 16) | ((inputStream.read() & 255) << 8) | (inputStream.read() & 255);
            byte[] bArr = new byte[read];
            this.bb = bArr;
            this.off = 0;
            if (!readNBytes(inputStream, bArr, read)) {
                throw new RuntimeException("Corrupted data file");
            }
            inputStream.close();
            while (true) {
                int i = this.off;
                if (i < read) {
                    byte[] bArr2 = this.bb;
                    int i2 = i + 1;
                    this.off = i2;
                    int i3 = (bArr2[i] & 255) << 8;
                    this.off = i + 2;
                    switch ((bArr2[i2] & 255) | i3) {
                        case 1:
                            readSINGLEBYTE();
                            break;
                        case 2:
                            readDOUBLEBYTE1();
                            break;
                        case 3:
                            readDOUBLEBYTE2();
                            break;
                        case 4:
                        default:
                            throw new RuntimeException("Corrupted data file");
                        case 5:
                            this.b2cSupp = readCharArray();
                            break;
                        case 6:
                            this.c2bSupp = readCharArray();
                            break;
                        case 7:
                            readCOMPOSITE();
                            break;
                        case 8:
                            readINDEXC2B();
                            break;
                    }
                } else {
                    this.bb = null;
                    return this;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
