package sun.nio.fs;

import java.io.File;
import java.io.IOException;
import java.lang.Iterable;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixPath implements Path {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ThreadLocal encoder = new ThreadLocal();
    private final UnixFileSystem fs;
    private int hash;
    private volatile int[] offsets;
    private final byte[] path;
    private volatile String stringValue;

    public /* synthetic */ boolean endsWith(String str) {
        return Path.-CC.$default$endsWith(this, str);
    }

    public /* synthetic */ void forEach(Consumer consumer) {
        Iterable.-CC.$default$forEach(this, consumer);
    }

    public /* synthetic */ Iterator iterator() {
        return Path.-CC.$default$iterator(this);
    }

    public /* synthetic */ WatchKey register(WatchService watchService, WatchEvent.Kind... kindArr) {
        return Path.-CC.$default$register(this, watchService, kindArr);
    }

    public /* synthetic */ Path resolve(String str) {
        return Path.-CC.$default$resolve(this, str);
    }

    public /* synthetic */ Path resolveSibling(String str) {
        return Path.-CC.$default$resolveSibling(this, str);
    }

    public /* synthetic */ Path resolveSibling(Path path) {
        return Path.-CC.$default$resolveSibling(this, path);
    }

    public /* synthetic */ Spliterator spliterator() {
        return Iterable.-CC.$default$spliterator(this);
    }

    public /* synthetic */ boolean startsWith(String str) {
        return Path.-CC.$default$startsWith(this, str);
    }

    public /* synthetic */ File toFile() {
        return Path.-CC.$default$toFile(this);
    }

    UnixPath(UnixFileSystem unixFileSystem, byte[] bArr) {
        this.fs = unixFileSystem;
        this.path = bArr;
    }

    UnixPath(UnixFileSystem unixFileSystem, String str) {
        this(unixFileSystem, encode(unixFileSystem, normalizeAndCheck(str)));
    }

    static String normalizeAndCheck(String str) {
        int length = str.length();
        int i = 0;
        char c = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt == '/' && c == '/') {
                return normalize(str, length, i - 1);
            }
            checkNotNul(str, charAt);
            i++;
            c = charAt;
        }
        return c == '/' ? normalize(str, length, length - 1) : str;
    }

    private static void checkNotNul(String str, char c) {
        if (c == 0) {
            throw new InvalidPathException(str, "Nul character not allowed");
        }
    }

    private static String normalize(String str, int i, int i2) {
        if (i == 0) {
            return str;
        }
        while (i > 0 && str.charAt(i - 1) == '/') {
            i--;
        }
        if (i == 0) {
            return "/";
        }
        StringBuilder sb = new StringBuilder(str.length());
        char c = 0;
        if (i2 > 0) {
            sb.append(str.substring(0, i2));
        }
        while (i2 < i) {
            char charAt = str.charAt(i2);
            if (charAt != '/' || c != '/') {
                checkNotNul(str, charAt);
                sb.append(charAt);
                c = charAt;
            }
            i2++;
        }
        return sb.toString();
    }

    private static byte[] encode(UnixFileSystem unixFileSystem, String str) {
        SoftReference softReference = (SoftReference) encoder.get();
        CharsetEncoder charsetEncoder = softReference != null ? (CharsetEncoder) softReference.get() : null;
        if (charsetEncoder == null) {
            charsetEncoder = Util.jnuEncoding().newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
            encoder.set(new SoftReference(charsetEncoder));
        }
        char[] normalizeNativePath = unixFileSystem.normalizeNativePath(str.toCharArray());
        double length = normalizeNativePath.length;
        double maxBytesPerChar = charsetEncoder.maxBytesPerChar();
        Double.isNaN(length);
        Double.isNaN(maxBytesPerChar);
        int i = (int) (length * maxBytesPerChar);
        byte[] bArr = new byte[i];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        CharBuffer wrap2 = CharBuffer.wrap(normalizeNativePath);
        charsetEncoder.reset();
        if (charsetEncoder.encode(wrap2, wrap, true).isUnderflow() ? true ^ charsetEncoder.flush(wrap).isUnderflow() : true) {
            throw new InvalidPathException(str, "Malformed input or input contains unmappable characters");
        }
        int position = wrap.position();
        return position != i ? Arrays.copyOf(bArr, position) : bArr;
    }

    byte[] asByteArray() {
        return this.path;
    }

    byte[] getByteArrayForSysCalls() {
        if (getFileSystem().needToResolveAgainstDefaultDirectory()) {
            return resolve(getFileSystem().defaultDirectory(), this.path);
        }
        if (!isEmpty()) {
            return this.path;
        }
        return new byte[]{46};
    }

    String getPathForExceptionMessage() {
        return toString();
    }

    String getPathForPermissionCheck() {
        if (getFileSystem().needToResolveAgainstDefaultDirectory()) {
            return Util.toString(getByteArrayForSysCalls());
        }
        return toString();
    }

    static UnixPath toUnixPath(Path path) {
        path.getClass();
        if (!(path instanceof UnixPath)) {
            throw new ProviderMismatchException();
        }
        return (UnixPath) path;
    }

    private void initOffsets() {
        int i;
        if (this.offsets == null) {
            int i2 = 0;
            if (!isEmpty()) {
                i = 0;
                int i3 = 0;
                while (true) {
                    byte[] bArr = this.path;
                    if (i3 >= bArr.length) {
                        break;
                    }
                    int i4 = i3 + 1;
                    if (bArr[i3] != 47) {
                        i++;
                        while (true) {
                            byte[] bArr2 = this.path;
                            if (i4 < bArr2.length && bArr2[i4] != 47) {
                                i4++;
                            }
                        }
                    }
                    i3 = i4;
                }
            } else {
                i = 1;
            }
            int[] iArr = new int[i];
            int i5 = 0;
            while (true) {
                byte[] bArr3 = this.path;
                if (i2 >= bArr3.length) {
                    break;
                }
                if (bArr3[i2] == 47) {
                    i2++;
                } else {
                    int i6 = i5 + 1;
                    int i7 = i2 + 1;
                    iArr[i5] = i2;
                    while (true) {
                        byte[] bArr4 = this.path;
                        if (i7 >= bArr4.length || bArr4[i7] == 47) {
                            break;
                        } else {
                            i7++;
                        }
                    }
                    i5 = i6;
                    i2 = i7;
                }
            }
            synchronized (this) {
                if (this.offsets == null) {
                    this.offsets = iArr;
                }
            }
        }
    }

    private boolean isEmpty() {
        return this.path.length == 0;
    }

    private UnixPath emptyPath() {
        return new UnixPath(getFileSystem(), new byte[0]);
    }

    private boolean hasDotOrDotDot() {
        int nameCount = getNameCount();
        for (int i = 0; i < nameCount; i++) {
            byte[] bArr = getName(i).path;
            if (bArr.length == 1 && bArr[0] == 46) {
                return true;
            }
            if (bArr.length == 2 && bArr[0] == 46 && bArr[1] == 46) {
                return true;
            }
        }
        return false;
    }

    public UnixFileSystem getFileSystem() {
        return this.fs;
    }

    public UnixPath getRoot() {
        byte[] bArr = this.path;
        if (bArr.length <= 0 || bArr[0] != 47) {
            return null;
        }
        return getFileSystem().rootDirectory();
    }

    public UnixPath getFileName() {
        initOffsets();
        int length = this.offsets.length;
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            byte[] bArr = this.path;
            if (bArr.length > 0 && bArr[0] != 47) {
                return this;
            }
        }
        int i = this.offsets[length - 1];
        byte[] bArr2 = this.path;
        int length2 = bArr2.length - i;
        byte[] bArr3 = new byte[length2];
        System.arraycopy(bArr2, i, bArr3, 0, length2);
        return new UnixPath(getFileSystem(), bArr3);
    }

    public UnixPath getParent() {
        initOffsets();
        int length = this.offsets.length;
        if (length == 0) {
            return null;
        }
        int i = this.offsets[length - 1] - 1;
        if (i <= 0) {
            return getRoot();
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.path, 0, bArr, 0, i);
        return new UnixPath(getFileSystem(), bArr);
    }

    public int getNameCount() {
        initOffsets();
        return this.offsets.length;
    }

    public UnixPath getName(int i) {
        int i2;
        initOffsets();
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i >= this.offsets.length) {
            throw new IllegalArgumentException();
        }
        int i3 = this.offsets[i];
        if (i == this.offsets.length - 1) {
            i2 = this.path.length - i3;
        } else {
            i2 = (this.offsets[i + 1] - i3) - 1;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.path, i3, bArr, 0, i2);
        return new UnixPath(getFileSystem(), bArr);
    }

    public UnixPath subpath(int i, int i2) {
        int i3;
        initOffsets();
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i >= this.offsets.length) {
            throw new IllegalArgumentException();
        }
        if (i2 > this.offsets.length) {
            throw new IllegalArgumentException();
        }
        if (i >= i2) {
            throw new IllegalArgumentException();
        }
        int i4 = this.offsets[i];
        if (i2 == this.offsets.length) {
            i3 = this.path.length - i4;
        } else {
            i3 = (this.offsets[i2] - i4) - 1;
        }
        byte[] bArr = new byte[i3];
        System.arraycopy(this.path, i4, bArr, 0, i3);
        return new UnixPath(getFileSystem(), bArr);
    }

    public boolean isAbsolute() {
        byte[] bArr = this.path;
        return bArr.length > 0 && bArr[0] == 47;
    }

    private static byte[] resolve(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int length2 = bArr2.length;
        if (length2 == 0) {
            return bArr;
        }
        if (length == 0 || bArr2[0] == 47) {
            return bArr2;
        }
        if (length == 1 && bArr[0] == 47) {
            byte[] bArr3 = new byte[length2 + 1];
            bArr3[0] = 47;
            System.arraycopy(bArr2, 0, bArr3, 1, length2);
            return bArr3;
        }
        int i = length + 1;
        byte[] bArr4 = new byte[i + length2];
        System.arraycopy(bArr, 0, bArr4, 0, length);
        bArr4[bArr.length] = 47;
        System.arraycopy(bArr2, 0, bArr4, i, length2);
        return bArr4;
    }

    public UnixPath resolve(Path path) {
        byte[] bArr = toUnixPath(path).path;
        if (bArr.length > 0 && bArr[0] == 47) {
            return (UnixPath) path;
        }
        return new UnixPath(getFileSystem(), resolve(this.path, bArr));
    }

    UnixPath resolve(byte[] bArr) {
        return resolve((Path) new UnixPath(getFileSystem(), bArr));
    }

    public UnixPath relativize(Path path) {
        UnixPath normalize;
        UnixPath subpath;
        boolean isEmpty;
        int nameCount;
        UnixPath unixPath = toUnixPath(path);
        if (unixPath.equals(this)) {
            return emptyPath();
        }
        if (isAbsolute() != unixPath.isAbsolute()) {
            throw new IllegalArgumentException("'other' is different type of Path");
        }
        if (isEmpty()) {
            return unixPath;
        }
        if (hasDotOrDotDot() || unixPath.hasDotOrDotDot()) {
            normalize = normalize();
            unixPath = unixPath.normalize();
        } else {
            normalize = this;
        }
        int nameCount2 = normalize.getNameCount();
        int nameCount3 = unixPath.getNameCount();
        int min = Math.min(nameCount2, nameCount3);
        int i = 0;
        while (i < min && normalize.getName(i).equals(unixPath.getName(i))) {
            i++;
        }
        if (i == nameCount3) {
            subpath = emptyPath();
            isEmpty = true;
        } else {
            subpath = unixPath.subpath(i, nameCount3);
            isEmpty = subpath.isEmpty();
        }
        if (i != nameCount2) {
            UnixPath subpath2 = normalize.subpath(i, nameCount2);
            if (subpath2.hasDotOrDotDot()) {
                throw new IllegalArgumentException("Unable to compute relative  path from " + this + " to " + path);
            }
            if (!subpath2.isEmpty() && (nameCount = subpath2.getNameCount()) != 0) {
                int length = (nameCount * 3) + subpath.path.length;
                if (isEmpty) {
                    length--;
                }
                byte[] bArr = new byte[length];
                int i2 = 0;
                while (nameCount > 0) {
                    bArr[i2] = 46;
                    int i3 = i2 + 2;
                    bArr[i2 + 1] = 46;
                    if (!isEmpty) {
                        i2 += 3;
                        bArr[i3] = 47;
                    } else if (nameCount > 1) {
                        i2 += 3;
                        bArr[i3] = 47;
                    } else {
                        i2 = i3;
                    }
                    nameCount--;
                }
                byte[] bArr2 = subpath.path;
                System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
                return new UnixPath(getFileSystem(), bArr);
            }
        }
        return subpath;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public sun.nio.fs.UnixPath normalize() {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixPath.normalize():sun.nio.fs.UnixPath");
    }

    public boolean startsWith(Path path) {
        path.getClass();
        if (!(path instanceof UnixPath)) {
            return false;
        }
        UnixPath unixPath = (UnixPath) path;
        if (unixPath.path.length > this.path.length) {
            return false;
        }
        int nameCount = getNameCount();
        int nameCount2 = unixPath.getNameCount();
        if (nameCount2 == 0 && isAbsolute()) {
            return !unixPath.isEmpty();
        }
        if (nameCount2 > nameCount) {
            return false;
        }
        if (nameCount2 == nameCount && this.path.length != unixPath.path.length) {
            return false;
        }
        for (int i = 0; i < nameCount2; i++) {
            if (!Integer.valueOf(this.offsets[i]).equals(Integer.valueOf(unixPath.offsets[i]))) {
                return false;
            }
        }
        int i2 = 0;
        while (true) {
            byte[] bArr = unixPath.path;
            if (i2 < bArr.length) {
                if (this.path[i2] != bArr[i2]) {
                    return false;
                }
                i2++;
            } else {
                byte[] bArr2 = this.path;
                return i2 >= bArr2.length || bArr2[i2] == 47;
            }
        }
    }

    public boolean endsWith(Path path) {
        int nameCount;
        int nameCount2;
        path.getClass();
        if (!(path instanceof UnixPath)) {
            return false;
        }
        UnixPath unixPath = (UnixPath) path;
        int length = this.path.length;
        int length2 = unixPath.path.length;
        if (length2 > length) {
            return false;
        }
        if (length > 0 && length2 == 0) {
            return false;
        }
        if ((unixPath.isAbsolute() && !isAbsolute()) || (nameCount2 = unixPath.getNameCount()) > (nameCount = getNameCount())) {
            return false;
        }
        if (nameCount2 == nameCount) {
            if (nameCount == 0) {
                return true;
            }
            if (length2 != ((!isAbsolute() || unixPath.isAbsolute()) ? length : length - 1)) {
                return false;
            }
        } else if (unixPath.isAbsolute()) {
            return false;
        }
        int i = this.offsets[nameCount - nameCount2];
        int i2 = unixPath.offsets[0];
        if (length2 - i2 != length - i) {
            return false;
        }
        while (i2 < length2) {
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (this.path[i] != unixPath.path[i2]) {
                return false;
            }
            i = i3;
            i2 = i4;
        }
        return true;
    }

    public int compareTo(Path path) {
        int length = this.path.length;
        UnixPath unixPath = (UnixPath) path;
        int length2 = unixPath.path.length;
        int min = Math.min(length, length2);
        byte[] bArr = this.path;
        byte[] bArr2 = unixPath.path;
        for (int i = 0; i < min; i++) {
            int i2 = bArr[i] & 255;
            int i3 = bArr2[i] & 255;
            if (i2 != i3) {
                return i2 - i3;
            }
        }
        return length - length2;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof UnixPath) && compareTo((Path) obj) == 0;
    }

    public int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int i2 = 0;
            while (true) {
                byte[] bArr = this.path;
                if (i2 >= bArr.length) {
                    break;
                }
                i = (i * 31) + (bArr[i2] & 255);
                i2++;
            }
            this.hash = i;
        }
        return i;
    }

    public String toString() {
        if (this.stringValue == null) {
            this.stringValue = this.fs.normalizeJavaPath(Util.toString(this.path));
        }
        return this.stringValue;
    }

    int openForAttributeAccess(boolean z) throws UnixException {
        try {
            return UnixNativeDispatcher.open(this, !z ? 131072 : 0, 0);
        } catch (UnixException e) {
            if (getFileSystem().isSolaris() && e.errno() == 22) {
                e.setError(40);
            }
            throw e;
        }
    }

    void checkRead() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(getPathForPermissionCheck());
        }
    }

    void checkWrite() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(getPathForPermissionCheck());
        }
    }

    void checkDelete() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(getPathForPermissionCheck());
        }
    }

    public UnixPath toAbsolutePath() {
        if (isAbsolute()) {
            return this;
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPropertyAccess("user.dir");
        }
        return new UnixPath(getFileSystem(), resolve(getFileSystem().defaultDirectory(), this.path));
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.nio.file.Path toRealPath(java.nio.file.LinkOption... r9) throws java.io.IOException {
        /*
            r8 = this;
            r8.checkRead()
            sun.nio.fs.UnixPath r0 = r8.toAbsolutePath()
            boolean r9 = sun.nio.fs.Util.followLinks(r9)
            if (r9 == 0) goto L1f
            byte[] r9 = sun.nio.fs.UnixNativeDispatcher.realpath(r0)     // Catch: sun.nio.fs.UnixException -> L1b
            sun.nio.fs.UnixPath r1 = new sun.nio.fs.UnixPath     // Catch: sun.nio.fs.UnixException -> L1b
            sun.nio.fs.UnixFileSystem r2 = r8.getFileSystem()     // Catch: sun.nio.fs.UnixException -> L1b
            r1.<init>(r2, r9)     // Catch: sun.nio.fs.UnixException -> L1b
            return r1
        L1b:
            r9 = move-exception
            r9.rethrowAsIOException(r8)
        L1f:
            sun.nio.fs.UnixFileSystem r9 = r8.fs
            sun.nio.fs.UnixPath r9 = r9.rootDirectory()
            r1 = 0
            r2 = 0
        L27:
            int r3 = r0.getNameCount()
            if (r2 >= r3) goto L80
            sun.nio.fs.UnixPath r3 = r0.getName(r2)
            byte[] r4 = r3.asByteArray()
            int r4 = r4.length
            r5 = 46
            r6 = 1
            if (r4 != r6) goto L44
            byte[] r4 = r3.asByteArray()
            r4 = r4[r1]
            if (r4 != r5) goto L44
            goto L7d
        L44:
            byte[] r4 = r3.asByteArray()
            int r4 = r4.length
            r7 = 2
            if (r4 != r7) goto L79
            byte[] r4 = r3.asByteArray()
            r4 = r4[r1]
            if (r4 != r5) goto L79
            byte[] r4 = r3.asByteArray()
            r4 = r4[r6]
            if (r4 != r5) goto L79
            sun.nio.fs.UnixFileAttributes r4 = sun.nio.fs.UnixFileAttributes.get(r9, r1)     // Catch: sun.nio.fs.UnixException -> L61
            goto L66
        L61:
            r4 = move-exception
            r4.rethrowAsIOException(r9)
            r4 = 0
        L66:
            boolean r4 = r4.isSymbolicLink()
            if (r4 != 0) goto L79
            sun.nio.fs.UnixPath r9 = r9.getParent()
            if (r9 != 0) goto L7d
            sun.nio.fs.UnixFileSystem r9 = r8.fs
            sun.nio.fs.UnixPath r9 = r9.rootDirectory()
            goto L7d
        L79:
            sun.nio.fs.UnixPath r9 = r9.resolve(r3)
        L7d:
            int r2 = r2 + 1
            goto L27
        L80:
            sun.nio.fs.UnixFileAttributes.get(r9, r1)     // Catch: sun.nio.fs.UnixException -> L84
            goto L88
        L84:
            r0 = move-exception
            r0.rethrowAsIOException(r9)
        L88:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixPath.toRealPath(java.nio.file.LinkOption[]):java.nio.file.Path");
    }

    public URI toUri() {
        return UnixUriUtils.toUri(this);
    }

    public WatchKey register(WatchService watchService, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException {
        watchService.getClass();
        if (!(watchService instanceof AbstractWatchService)) {
            throw new ProviderMismatchException();
        }
        checkRead();
        return ((AbstractWatchService) watchService).register(this, kindArr, modifierArr);
    }
}
