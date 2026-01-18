package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.util.Map;
import java.util.Set;
import sun.nio.fs.AbstractBasicFileAttributeView;
import sun.nio.fs.UnixUserPrincipals;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixFileAttributeViews {
    UnixFileAttributeViews() {
    }

    static class Basic extends AbstractBasicFileAttributeView {
        protected final UnixPath file;
        protected final boolean followLinks;

        Basic(UnixPath unixPath, boolean z) {
            this.file = unixPath;
            this.followLinks = z;
        }

        public BasicFileAttributes readAttributes() throws IOException {
            this.file.checkRead();
            try {
                return UnixFileAttributes.get(this.file, this.followLinks).asBasicFileAttributes();
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
                return null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0068 A[Catch: all -> 0x0052, UnixException -> 0x0072, TRY_ENTER, TryCatch #2 {UnixException -> 0x0072, blocks: (B:19:0x0068, B:24:0x006c), top: B:17:0x0066, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x006c A[Catch: all -> 0x0052, UnixException -> 0x0072, TRY_LEAVE, TryCatch #2 {UnixException -> 0x0072, blocks: (B:19:0x0068, B:24:0x006c), top: B:17:0x0066, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0036 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0038 A[Catch: all -> 0x0052, UnixException -> 0x0054, TRY_ENTER, TryCatch #3 {UnixException -> 0x0054, blocks: (B:50:0x0038, B:52:0x0047, B:54:0x004d, B:56:0x003d), top: B:48:0x0036, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0047 A[Catch: all -> 0x0052, UnixException -> 0x0054, TryCatch #3 {UnixException -> 0x0054, blocks: (B:50:0x0038, B:52:0x0047, B:54:0x004d, B:56:0x003d), top: B:48:0x0036, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x004d A[Catch: all -> 0x0052, UnixException -> 0x0054, TRY_LEAVE, TryCatch #3 {UnixException -> 0x0054, blocks: (B:50:0x0038, B:52:0x0047, B:54:0x004d, B:56:0x003d), top: B:48:0x0036, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x003d A[Catch: all -> 0x0052, UnixException -> 0x0054, TryCatch #3 {UnixException -> 0x0054, blocks: (B:50:0x0038, B:52:0x0047, B:54:0x004d, B:56:0x003d), top: B:48:0x0036, outer: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void setTimes(java.nio.file.attribute.FileTime r8, java.nio.file.attribute.FileTime r9, java.nio.file.attribute.FileTime r10) throws java.io.IOException {
            /*
                r7 = this;
                if (r8 != 0) goto L6
                if (r9 != 0) goto L6
                goto Laa
            L6:
                sun.nio.fs.UnixPath r10 = r7.file
                r10.checkWrite()
                r10 = -1
                r0 = 0
                sun.nio.fs.UnixPath r1 = r7.file     // Catch: sun.nio.fs.UnixException -> L21
                boolean r2 = r7.followLinks     // Catch: sun.nio.fs.UnixException -> L21
                int r1 = r1.openForAttributeAccess(r2)     // Catch: sun.nio.fs.UnixException -> L21
                if (r1 == r10) goto L31
                r10 = 1
                boolean r0 = sun.nio.fs.UnixNativeDispatcher.futimesSupported()     // Catch: sun.nio.fs.UnixException -> L1f
                r10 = r0
                r0 = 1
                goto L32
            L1f:
                r2 = move-exception
                goto L24
            L21:
                r2 = move-exception
                r10 = 0
                r1 = -1
            L24:
                int r3 = r2.errno()
                r4 = 6
                if (r3 == r4) goto L30
                sun.nio.fs.UnixPath r3 = r7.file
                r2.rethrowAsIOException(r3)
            L30:
                r0 = r10
            L31:
                r10 = 0
            L32:
                if (r8 == 0) goto L36
                if (r9 != 0) goto L5a
            L36:
                if (r0 == 0) goto L3d
                sun.nio.fs.UnixFileAttributes r0 = sun.nio.fs.UnixFileAttributes.get(r1)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
                goto L45
            L3d:
                sun.nio.fs.UnixPath r0 = r7.file     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
                boolean r2 = r7.followLinks     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
                sun.nio.fs.UnixFileAttributes r0 = sun.nio.fs.UnixFileAttributes.get(r0, r2)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
            L45:
                if (r8 != 0) goto L4b
                java.nio.file.attribute.FileTime r8 = r0.lastModifiedTime()     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
            L4b:
                if (r9 != 0) goto L5a
                java.nio.file.attribute.FileTime r9 = r0.lastAccessTime()     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L54
                goto L5a
            L52:
                r8 = move-exception
                goto Lab
            L54:
                r0 = move-exception
                sun.nio.fs.UnixPath r2 = r7.file     // Catch: java.lang.Throwable -> L52
                r0.rethrowAsIOException(r2)     // Catch: java.lang.Throwable -> L52
            L5a:
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: java.lang.Throwable -> L52
                long r2 = r8.to(r0)     // Catch: java.lang.Throwable -> L52
                java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: java.lang.Throwable -> L52
                long r8 = r9.to(r8)     // Catch: java.lang.Throwable -> L52
                if (r10 == 0) goto L6c
                sun.nio.fs.UnixNativeDispatcher.futimes(r1, r8, r2)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L72
                goto La7
            L6c:
                sun.nio.fs.UnixPath r0 = r7.file     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L72
                sun.nio.fs.UnixNativeDispatcher.utimes(r0, r8, r2)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L72
                goto La7
            L72:
                r0 = move-exception
                int r4 = r0.errno()     // Catch: java.lang.Throwable -> L52
                r5 = 22
                if (r4 != r5) goto La2
                r4 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 < 0) goto L85
                int r6 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r6 >= 0) goto La2
            L85:
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 >= 0) goto L8a
                r2 = r4
            L8a:
                int r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r0 >= 0) goto L8f
                r8 = r4
            L8f:
                if (r10 == 0) goto L95
                sun.nio.fs.UnixNativeDispatcher.futimes(r1, r8, r2)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L9b
                goto La7
            L95:
                sun.nio.fs.UnixPath r10 = r7.file     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L9b
                sun.nio.fs.UnixNativeDispatcher.utimes(r10, r8, r2)     // Catch: java.lang.Throwable -> L52 sun.nio.fs.UnixException -> L9b
                goto La7
            L9b:
                r8 = move-exception
                sun.nio.fs.UnixPath r9 = r7.file     // Catch: java.lang.Throwable -> L52
                r8.rethrowAsIOException(r9)     // Catch: java.lang.Throwable -> L52
                goto La7
            La2:
                sun.nio.fs.UnixPath r8 = r7.file     // Catch: java.lang.Throwable -> L52
                r0.rethrowAsIOException(r8)     // Catch: java.lang.Throwable -> L52
            La7:
                sun.nio.fs.UnixNativeDispatcher.close(r1)
            Laa:
                return
            Lab:
                sun.nio.fs.UnixNativeDispatcher.close(r1)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixFileAttributeViews.Basic.setTimes(java.nio.file.attribute.FileTime, java.nio.file.attribute.FileTime, java.nio.file.attribute.FileTime):void");
        }
    }

    private static class Posix extends Basic implements PosixFileAttributeView {
        private static final String GROUP_NAME = "group";
        private static final String OWNER_NAME = "owner";
        private static final String PERMISSIONS_NAME = "permissions";
        static final Set posixAttributeNames = Util.newSet(basicAttributeNames, "permissions", "owner", "group");

        Posix(UnixPath unixPath, boolean z) {
            super(unixPath, z);
        }

        final void checkReadExtended() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.file.checkRead();
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        final void checkWriteExtended() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.file.checkWrite();
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        public String name() {
            return "posix";
        }

        public void setAttribute(String str, Object obj) throws IOException {
            if (str.equals("permissions")) {
                setPermissions((Set) obj);
                return;
            }
            if (str.equals("owner")) {
                setOwner((UserPrincipal) obj);
            } else if (str.equals("group")) {
                setGroup((GroupPrincipal) obj);
            } else {
                super.setAttribute(str, obj);
            }
        }

        final void addRequestedPosixAttributes(PosixFileAttributes posixFileAttributes, AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder) {
            addRequestedBasicAttributes(posixFileAttributes, attributesBuilder);
            if (attributesBuilder.match("permissions")) {
                attributesBuilder.add("permissions", posixFileAttributes.permissions());
            }
            if (attributesBuilder.match("owner")) {
                attributesBuilder.add("owner", posixFileAttributes.owner());
            }
            if (attributesBuilder.match("group")) {
                attributesBuilder.add("group", posixFileAttributes.group());
            }
        }

        public Map readAttributes(String[] strArr) throws IOException {
            AbstractBasicFileAttributeView.AttributesBuilder create = AbstractBasicFileAttributeView.AttributesBuilder.create(posixAttributeNames, strArr);
            addRequestedPosixAttributes(readAttributes(), create);
            return create.unmodifiableMap();
        }

        public UnixFileAttributes readAttributes() throws IOException {
            checkReadExtended();
            try {
                return UnixFileAttributes.get(this.file, this.followLinks);
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
                return null;
            }
        }

        final void setMode(int i) throws IOException {
            checkWriteExtended();
            try {
                if (this.followLinks) {
                    UnixNativeDispatcher.chmod(this.file, i);
                    return;
                }
                int openForAttributeAccess = this.file.openForAttributeAccess(false);
                try {
                    UnixNativeDispatcher.fchmod(openForAttributeAccess, i);
                } finally {
                    UnixNativeDispatcher.close(openForAttributeAccess);
                }
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
            }
        }

        final void setOwners(int i, int i2) throws IOException {
            checkWriteExtended();
            try {
                if (this.followLinks) {
                    UnixNativeDispatcher.chown(this.file, i, i2);
                } else {
                    UnixNativeDispatcher.lchown(this.file, i, i2);
                }
            } catch (UnixException e) {
                e.rethrowAsIOException(this.file);
            }
        }

        public void setPermissions(Set set) throws IOException {
            setMode(UnixFileModeAttribute.toUnixMode(set));
        }

        public void setOwner(UserPrincipal userPrincipal) throws IOException {
            if (userPrincipal == null) {
                throw new NullPointerException("'owner' is null");
            }
            if (!(userPrincipal instanceof UnixUserPrincipals.User)) {
                throw new ProviderMismatchException();
            }
            if (userPrincipal instanceof UnixUserPrincipals.Group) {
                throw new IOException("'owner' parameter can't be a group");
            }
            setOwners(((UnixUserPrincipals.User) userPrincipal).uid(), -1);
        }

        public UserPrincipal getOwner() throws IOException {
            return readAttributes().owner();
        }

        public void setGroup(GroupPrincipal groupPrincipal) throws IOException {
            if (groupPrincipal == null) {
                throw new NullPointerException("'owner' is null");
            }
            if (!(groupPrincipal instanceof UnixUserPrincipals.Group)) {
                throw new ProviderMismatchException();
            }
            setOwners(-1, ((UnixUserPrincipals.Group) groupPrincipal).gid());
        }
    }

    private static class Unix extends Posix {
        private static final String CTIME_NAME = "ctime";
        private static final String DEV_NAME = "dev";
        private static final String GID_NAME = "gid";
        private static final String INO_NAME = "ino";
        private static final String MODE_NAME = "mode";
        private static final String NLINK_NAME = "nlink";
        private static final String RDEV_NAME = "rdev";
        private static final String UID_NAME = "uid";
        static final Set unixAttributeNames = Util.newSet(posixAttributeNames, "mode", "ino", "dev", "rdev", "nlink", "uid", "gid", "ctime");

        Unix(UnixPath unixPath, boolean z) {
            super(unixPath, z);
        }

        public String name() {
            return "unix";
        }

        public void setAttribute(String str, Object obj) throws IOException {
            if (str.equals("mode")) {
                setMode(((Integer) obj).intValue());
                return;
            }
            if (str.equals("uid")) {
                setOwners(((Integer) obj).intValue(), -1);
            } else if (str.equals("gid")) {
                setOwners(-1, ((Integer) obj).intValue());
            } else {
                super.setAttribute(str, obj);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Map readAttributes(String[] strArr) throws IOException {
            AbstractBasicFileAttributeView.AttributesBuilder create = AbstractBasicFileAttributeView.AttributesBuilder.create(unixAttributeNames, strArr);
            UnixFileAttributes readAttributes = readAttributes();
            addRequestedPosixAttributes(readAttributes, create);
            if (create.match("mode")) {
                create.add("mode", Integer.valueOf(readAttributes.mode()));
            }
            if (create.match("ino")) {
                create.add("ino", Long.valueOf(readAttributes.ino()));
            }
            if (create.match("dev")) {
                create.add("dev", Long.valueOf(readAttributes.dev()));
            }
            if (create.match("rdev")) {
                create.add("rdev", Long.valueOf(readAttributes.rdev()));
            }
            if (create.match("nlink")) {
                create.add("nlink", Integer.valueOf(readAttributes.nlink()));
            }
            if (create.match("uid")) {
                create.add("uid", Integer.valueOf(readAttributes.uid()));
            }
            if (create.match("gid")) {
                create.add("gid", Integer.valueOf(readAttributes.gid()));
            }
            if (create.match("ctime")) {
                create.add("ctime", readAttributes.ctime());
            }
            return create.unmodifiableMap();
        }
    }

    static Basic createBasicView(UnixPath unixPath, boolean z) {
        return new Basic(unixPath, z);
    }

    static Posix createPosixView(UnixPath unixPath, boolean z) {
        return new Posix(unixPath, z);
    }

    static Unix createUnixView(UnixPath unixPath, boolean z) {
        return new Unix(unixPath, z);
    }

    static FileOwnerAttributeViewImpl createOwnerView(UnixPath unixPath, boolean z) {
        return new FileOwnerAttributeViewImpl(createPosixView(unixPath, z));
    }
}
