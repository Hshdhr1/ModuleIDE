package sun.nio.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jdk.internal.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxUserDefinedFileAttributeView extends AbstractUserDefinedFileAttributeView {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String USER_NAMESPACE = "user.";
    private static final int XATTR_NAME_MAX = 255;
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final UnixPath file;
    private final boolean followLinks;

    private byte[] nameAsBytes(UnixPath unixPath, String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("'name' is null");
        }
        String str2 = "user." + str;
        byte[] bytes = Util.toBytes(str2);
        if (bytes.length <= 255) {
            return bytes;
        }
        throw new FileSystemException(unixPath.getPathForExceptionMessage(), null, "'" + str2 + "' is too big");
    }

    private List asList(long j, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Unsafe unsafe2 = unsafe;
            if (unsafe2.getByte(i3 + j) == 0) {
                int i4 = i3 - i2;
                byte[] bArr = new byte[i4];
                unsafe2.copyMemory((Object) null, i2 + j, bArr, Unsafe.ARRAY_BYTE_BASE_OFFSET, i4);
                String util = Util.toString(bArr);
                if (util.startsWith("user.")) {
                    arrayList.add(util.substring(5));
                }
                i2 = i3 + 1;
            }
        }
        return arrayList;
    }

    LinuxUserDefinedFileAttributeView(UnixPath unixPath, boolean z) {
        this.file = unixPath;
        this.followLinks = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.List] */
    public List list() throws IOException {
        int i;
        NativeBuffer nativeBuffer;
        Throwable th;
        if (System.getSecurityManager() != null) {
            checkAccess(this.file.getPathForPermissionCheck(), true, false);
        }
        try {
            i = this.file.openForAttributeAccess(this.followLinks);
        } catch (UnixException e) {
            e.rethrowAsIOException(this.file);
            i = -1;
        }
        ?? r1 = 0;
        r1 = 0;
        int i2 = 1024;
        try {
            nativeBuffer = NativeBuffers.getNativeBuffer(1024);
            while (true) {
                try {
                    try {
                        r1 = Collections.unmodifiableList(asList(nativeBuffer.address(), LinuxNativeDispatcher.flistxattr(i, nativeBuffer.address(), i2)));
                        if (nativeBuffer != null) {
                            nativeBuffer.release();
                        }
                        LinuxNativeDispatcher.close(i);
                        return r1;
                    } catch (Throwable th2) {
                        th = th2;
                        if (nativeBuffer != null) {
                            nativeBuffer.release();
                        }
                        LinuxNativeDispatcher.close(i);
                        throw th;
                    }
                } catch (UnixException e2) {
                    if (e2.errno() == 34 && i2 < 32768) {
                        nativeBuffer.release();
                        i2 *= 2;
                        nativeBuffer = NativeBuffers.getNativeBuffer(i2);
                    } else {
                        throw new FileSystemException(this.file.getPathForExceptionMessage(), r1, "Unable to get list of extended attributes: " + e2.getMessage());
                    }
                }
            }
            throw new FileSystemException(this.file.getPathForExceptionMessage(), r1, "Unable to get list of extended attributes: " + e2.getMessage());
        } catch (Throwable th3) {
            nativeBuffer = r1;
            th = th3;
        }
    }

    public int size(String str) throws IOException {
        int i;
        if (System.getSecurityManager() != null) {
            checkAccess(this.file.getPathForPermissionCheck(), true, false);
        }
        try {
            i = this.file.openForAttributeAccess(this.followLinks);
        } catch (UnixException e) {
            e.rethrowAsIOException(this.file);
            i = -1;
        }
        try {
            try {
                return LinuxNativeDispatcher.fgetxattr(i, nameAsBytes(this.file, str), 0L, 0);
            } catch (UnixException e2) {
                throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Unable to get size of extended attribute '" + str + "': " + e2.getMessage());
            }
        } finally {
            LinuxNativeDispatcher.close(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d0 A[Catch: all -> 0x00f5, TryCatch #5 {all -> 0x00f5, blocks: (B:42:0x009e, B:43:0x00ac, B:50:0x00c7, B:53:0x00d4, B:54:0x00f4, B:55:0x00d0), top: B:17:0x005b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read(java.lang.String r22, java.nio.ByteBuffer r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.LinuxUserDefinedFileAttributeView.read(java.lang.String, java.nio.ByteBuffer):int");
    }

    public int write(String str, ByteBuffer byteBuffer) throws IOException {
        long j;
        NativeBuffer nativeBuffer;
        int i;
        if (System.getSecurityManager() != null) {
            checkAccess(this.file.getPathForPermissionCheck(), false, true);
        }
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i2 = position <= limit ? limit - position : 0;
        if (byteBuffer instanceof DirectBuffer) {
            j = ((DirectBuffer) byteBuffer).address() + position;
            nativeBuffer = null;
        } else {
            NativeBuffer nativeBuffer2 = NativeBuffers.getNativeBuffer(i2);
            long address = nativeBuffer2.address();
            if (byteBuffer.hasArray()) {
                j = address;
                unsafe.copyMemory(byteBuffer.array(), byteBuffer.arrayOffset() + position + Unsafe.ARRAY_BYTE_BASE_OFFSET, (Object) null, j, i2);
            } else {
                byte[] bArr = new byte[i2];
                byteBuffer.get(bArr);
                byteBuffer.position(position);
                j = address;
                unsafe.copyMemory(bArr, Unsafe.ARRAY_BYTE_BASE_OFFSET, (Object) null, j, i2);
            }
            nativeBuffer = nativeBuffer2;
        }
        try {
            i = this.file.openForAttributeAccess(this.followLinks);
        } catch (UnixException e) {
            e.rethrowAsIOException(this.file);
            i = -1;
        }
        try {
            try {
                try {
                    LinuxNativeDispatcher.fsetxattr(i, nameAsBytes(this.file, str), j, i2);
                    byteBuffer.position(position + i2);
                    return i2;
                } finally {
                    LinuxNativeDispatcher.close(i);
                }
            } finally {
                if (nativeBuffer != null) {
                    nativeBuffer.release();
                }
            }
        } catch (UnixException e2) {
            throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Error writing extended attribute '" + str + "': " + e2.getMessage());
        }
    }

    public void delete(String str) throws IOException {
        int i;
        if (System.getSecurityManager() != null) {
            checkAccess(this.file.getPathForPermissionCheck(), false, true);
        }
        try {
            i = this.file.openForAttributeAccess(this.followLinks);
        } catch (UnixException e) {
            e.rethrowAsIOException(this.file);
            i = -1;
        }
        try {
            try {
                LinuxNativeDispatcher.fremovexattr(i, nameAsBytes(this.file, str));
            } catch (UnixException e2) {
                throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Unable to delete extended attribute '" + str + "': " + e2.getMessage());
            }
        } finally {
            LinuxNativeDispatcher.close(i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001a, code lost:
    
        if (r5 >= r0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x001c, code lost:
    
        r7 = sun.nio.fs.LinuxUserDefinedFileAttributeView.unsafe;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0024, code lost:
    
        if (r7.getByte(r5 + r2) != 0) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        r8 = r5 - r6;
        r11 = new byte[r8];
        r7.copyMemory((java.lang.Object) null, r6 + r2, r11, jdk.internal.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
    
        copyExtendedAttribute(r16, r11, r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0041, code lost:
    
        if (r4 == null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0014, code lost:
    
        r2 = r4.address();
        r5 = 0;
        r6 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void copyExtendedAttributes(int r16, int r17) {
        /*
            r1 = r16
            r2 = 0
            r0 = 1024(0x400, float:1.435E-42)
            sun.nio.fs.NativeBuffer r3 = sun.nio.fs.NativeBuffers.getNativeBuffer(r0)     // Catch: java.lang.Throwable -> L67
            r4 = r3
            r3 = 1024(0x400, float:1.435E-42)
        Lc:
            long r5 = r4.address()     // Catch: java.lang.Throwable -> L44 sun.nio.fs.UnixException -> L47
            int r0 = sun.nio.fs.LinuxNativeDispatcher.flistxattr(r1, r5, r3)     // Catch: java.lang.Throwable -> L44 sun.nio.fs.UnixException -> L47
            long r2 = r4.address()     // Catch: java.lang.Throwable -> L44
            r5 = 0
            r6 = 0
        L1a:
            if (r5 >= r0) goto L41
            jdk.internal.misc.Unsafe r7 = sun.nio.fs.LinuxUserDefinedFileAttributeView.unsafe     // Catch: java.lang.Throwable -> L44
            long r8 = (long) r5     // Catch: java.lang.Throwable -> L44
            long r8 = r8 + r2
            byte r8 = r7.getByte(r8)     // Catch: java.lang.Throwable -> L44
            if (r8 != 0) goto L3c
            int r8 = r5 - r6
            byte[] r11 = new byte[r8]     // Catch: java.lang.Throwable -> L44
            long r9 = (long) r6     // Catch: java.lang.Throwable -> L44
            long r9 = r9 + r2
            int r6 = jdk.internal.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET     // Catch: java.lang.Throwable -> L44
            long r12 = (long) r6     // Catch: java.lang.Throwable -> L44
            long r14 = (long) r8     // Catch: java.lang.Throwable -> L44
            r8 = 0
            r7.copyMemory(r8, r9, r11, r12, r14)     // Catch: java.lang.Throwable -> L44
            r7 = r17
            copyExtendedAttribute(r1, r11, r7)     // Catch: sun.nio.fs.UnixException -> L39 java.lang.Throwable -> L44
        L39:
            int r6 = r5 + 1
            goto L3e
        L3c:
            r7 = r17
        L3e:
            int r5 = r5 + 1
            goto L1a
        L41:
            if (r4 == 0) goto L66
            goto L63
        L44:
            r0 = move-exception
            r2 = r4
            goto L68
        L47:
            r0 = move-exception
            r7 = r17
            int r0 = r0.errno()     // Catch: java.lang.Throwable -> L44
            r5 = 34
            if (r0 != r5) goto L61
            r0 = 32768(0x8000, float:4.5918E-41)
            if (r3 >= r0) goto L61
            r4.release()     // Catch: java.lang.Throwable -> L44
            int r3 = r3 * 2
            sun.nio.fs.NativeBuffer r4 = sun.nio.fs.NativeBuffers.getNativeBuffer(r3)     // Catch: java.lang.Throwable -> L67
            goto Lc
        L61:
            if (r4 == 0) goto L66
        L63:
            r4.release()
        L66:
            return
        L67:
            r0 = move-exception
        L68:
            if (r2 == 0) goto L6d
            r2.release()
        L6d:
            goto L6f
        L6e:
            throw r0
        L6f:
            goto L6e
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.LinuxUserDefinedFileAttributeView.copyExtendedAttributes(int, int):void");
    }

    private static void copyExtendedAttribute(int i, byte[] bArr, int i2) throws UnixException {
        int fgetxattr = LinuxNativeDispatcher.fgetxattr(i, bArr, 0L, 0);
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(fgetxattr);
        try {
            long address = nativeBuffer.address();
            LinuxNativeDispatcher.fsetxattr(i2, bArr, address, LinuxNativeDispatcher.fgetxattr(i, bArr, address, fgetxattr));
        } finally {
            nativeBuffer.release();
        }
    }
}
