package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.Set;
import jdk.internal.misc.Unsafe;
import sun.nio.fs.AbstractBasicFileAttributeView;
import sun.nio.fs.UnixFileAttributeViews;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxDosFileAttributeView extends UnixFileAttributeViews.Basic implements DosFileAttributeView {
    private static final String ARCHIVE_NAME = "archive";
    private static final int DOS_XATTR_ARCHIVE = 32;
    private static final int DOS_XATTR_HIDDEN = 2;
    private static final String DOS_XATTR_NAME = "user.DOSATTRIB";
    private static final int DOS_XATTR_READONLY = 1;
    private static final int DOS_XATTR_SYSTEM = 4;
    private static final String HIDDEN_NAME = "hidden";
    private static final String READONLY_NAME = "readonly";
    private static final String SYSTEM_NAME = "system";
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final byte[] DOS_XATTR_NAME_AS_BYTES = Util.toBytes("user.DOSATTRIB");
    private static final Set dosAttributeNames = Util.newSet(basicAttributeNames, "readonly", "archive", "system", "hidden");

    LinuxDosFileAttributeView(UnixPath unixPath, boolean z) {
        super(unixPath, z);
    }

    public String name() {
        return "dos";
    }

    public void setAttribute(String str, Object obj) throws IOException {
        if (str.equals("readonly")) {
            setReadOnly(((Boolean) obj).booleanValue());
            return;
        }
        if (str.equals("archive")) {
            setArchive(((Boolean) obj).booleanValue());
            return;
        }
        if (str.equals("system")) {
            setSystem(((Boolean) obj).booleanValue());
        } else if (str.equals("hidden")) {
            setHidden(((Boolean) obj).booleanValue());
        } else {
            super.setAttribute(str, obj);
        }
    }

    public Map readAttributes(String[] strArr) throws IOException {
        AbstractBasicFileAttributeView.AttributesBuilder create = AbstractBasicFileAttributeView.AttributesBuilder.create(dosAttributeNames, strArr);
        DosFileAttributes readAttributes = readAttributes();
        addRequestedBasicAttributes(readAttributes, create);
        if (create.match("readonly")) {
            create.add("readonly", Boolean.valueOf(readAttributes.isReadOnly()));
        }
        if (create.match("archive")) {
            create.add("archive", Boolean.valueOf(readAttributes.isArchive()));
        }
        if (create.match("system")) {
            create.add("system", Boolean.valueOf(readAttributes.isSystem()));
        }
        if (create.match("hidden")) {
            create.add("hidden", Boolean.valueOf(readAttributes.isHidden()));
        }
        return create.unmodifiableMap();
    }

    public DosFileAttributes readAttributes() throws IOException {
        this.file.checkRead();
        int i = -1;
        try {
            try {
                i = this.file.openForAttributeAccess(this.followLinks);
                return new 1(UnixFileAttributes.get(i), getDosAttribute(i));
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
                UnixNativeDispatcher.close(i);
                return null;
            }
        } finally {
            UnixNativeDispatcher.close(i);
        }
    }

    class 1 implements DosFileAttributes {
        final /* synthetic */ UnixFileAttributes val$attrs;
        final /* synthetic */ int val$dosAttribute;

        1(UnixFileAttributes unixFileAttributes, int i) {
            this.val$attrs = unixFileAttributes;
            this.val$dosAttribute = i;
        }

        public FileTime lastModifiedTime() {
            return this.val$attrs.lastModifiedTime();
        }

        public FileTime lastAccessTime() {
            return this.val$attrs.lastAccessTime();
        }

        public FileTime creationTime() {
            return this.val$attrs.creationTime();
        }

        public boolean isRegularFile() {
            return this.val$attrs.isRegularFile();
        }

        public boolean isDirectory() {
            return this.val$attrs.isDirectory();
        }

        public boolean isSymbolicLink() {
            return this.val$attrs.isSymbolicLink();
        }

        public boolean isOther() {
            return this.val$attrs.isOther();
        }

        public long size() {
            return this.val$attrs.size();
        }

        public Object fileKey() {
            return this.val$attrs.fileKey();
        }

        public boolean isReadOnly() {
            return (this.val$dosAttribute & 1) != 0;
        }

        public boolean isHidden() {
            return (this.val$dosAttribute & 2) != 0;
        }

        public boolean isArchive() {
            return (this.val$dosAttribute & 32) != 0;
        }

        public boolean isSystem() {
            return (this.val$dosAttribute & 4) != 0;
        }
    }

    public void setReadOnly(boolean z) throws IOException {
        updateDosAttribute(1, z);
    }

    public void setHidden(boolean z) throws IOException {
        updateDosAttribute(2, z);
    }

    public void setArchive(boolean z) throws IOException {
        updateDosAttribute(32, z);
    }

    public void setSystem(boolean z) throws IOException {
        updateDosAttribute(4, z);
    }

    private int getDosAttribute(int i) throws UnixException {
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(24);
        try {
            try {
                int fgetxattr = LinuxNativeDispatcher.fgetxattr(i, DOS_XATTR_NAME_AS_BYTES, nativeBuffer.address(), 24);
                if (fgetxattr > 0) {
                    Unsafe unsafe2 = unsafe;
                    if (unsafe2.getByte((nativeBuffer.address() + fgetxattr) - 1) == 0) {
                        fgetxattr--;
                    }
                    byte[] bArr = new byte[fgetxattr];
                    unsafe2.copyMemory((Object) null, nativeBuffer.address(), bArr, Unsafe.ARRAY_BYTE_BASE_OFFSET, fgetxattr);
                    String util = Util.toString(bArr);
                    if (util.length() >= 3 && util.startsWith("0x")) {
                        try {
                            int parseInt = Integer.parseInt(util.substring(2), 16);
                            nativeBuffer.release();
                            return parseInt;
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
                throw new UnixException("Value of user.DOSATTRIB attribute is invalid");
            } catch (UnixException e) {
                if (e.errno() != 61) {
                    throw e;
                }
                nativeBuffer.release();
                return 0;
            }
        } catch (Throwable th) {
            nativeBuffer.release();
            throw th;
        }
    }

    private void updateDosAttribute(int i, boolean z) throws IOException {
        int openForAttributeAccess;
        this.file.checkWrite();
        int i2 = -1;
        try {
            try {
                openForAttributeAccess = this.file.openForAttributeAccess(this.followLinks);
            } catch (Throwable th) {
                th = th;
            }
        } catch (UnixException e) {
            e = e;
        }
        try {
            int dosAttribute = getDosAttribute(openForAttributeAccess);
            int i3 = z ? i | dosAttribute : (i ^ (-1)) & dosAttribute;
            if (i3 != dosAttribute) {
                byte[] bytes = Util.toBytes("0x" + Integer.toHexString(i3));
                NativeBuffer asNativeBuffer = NativeBuffers.asNativeBuffer(bytes);
                try {
                    LinuxNativeDispatcher.fsetxattr(openForAttributeAccess, DOS_XATTR_NAME_AS_BYTES, asNativeBuffer.address(), bytes.length + 1);
                    asNativeBuffer.release();
                } catch (Throwable th2) {
                    asNativeBuffer.release();
                    throw th2;
                }
            }
            UnixNativeDispatcher.close(openForAttributeAccess);
        } catch (UnixException e2) {
            e = e2;
            i2 = openForAttributeAccess;
            e.rethrowAsIOException(this.file);
            UnixNativeDispatcher.close(i2);
        } catch (Throwable th3) {
            th = th3;
            i2 = openForAttributeAccess;
            UnixNativeDispatcher.close(i2);
            throw th;
        }
    }
}
