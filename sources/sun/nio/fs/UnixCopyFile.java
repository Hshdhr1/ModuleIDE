package sun.nio.fs;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.LinkOption;
import java.nio.file.LinkPermission;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixCopyFile {
    static /* bridge */ /* synthetic */ void -$$Nest$smcopyFile(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags, long j) {
        copyFile(unixPath, unixFileAttributes, unixPath2, flags, j);
    }

    static /* synthetic */ boolean lambda$ensureEmptyDir$0(Path path) throws IOException {
        return true;
    }

    static native void transfer(int i, int i2, long j) throws UnixException;

    private UnixCopyFile() {
    }

    private static class Flags {
        boolean atomicMove;
        boolean copyBasicAttributes;
        boolean copyNonPosixAttributes;
        boolean copyPosixAttributes;
        boolean failIfUnableToCopyBasic;
        boolean failIfUnableToCopyNonPosix;
        boolean failIfUnableToCopyPosix;
        boolean followLinks;
        boolean interruptible;
        boolean replaceExisting;

        private Flags() {
        }

        static Flags fromCopyOptions(CopyOption... copyOptionArr) {
            Flags flags = new Flags();
            flags.followLinks = true;
            for (CopyOption copyOption : copyOptionArr) {
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    flags.replaceExisting = true;
                } else if (copyOption == LinkOption.NOFOLLOW_LINKS) {
                    flags.followLinks = false;
                } else if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
                    flags.copyBasicAttributes = true;
                    flags.copyPosixAttributes = true;
                    flags.copyNonPosixAttributes = true;
                    flags.failIfUnableToCopyBasic = true;
                } else if (ExtendedOptions.INTERRUPTIBLE.matches(copyOption)) {
                    flags.interruptible = true;
                } else {
                    copyOption.getClass();
                    throw new UnsupportedOperationException("Unsupported copy option");
                }
            }
            return flags;
        }

        static Flags fromMoveOptions(CopyOption... copyOptionArr) {
            Flags flags = new Flags();
            for (CopyOption copyOption : copyOptionArr) {
                if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
                    flags.atomicMove = true;
                } else if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    flags.replaceExisting = true;
                } else if (copyOption != LinkOption.NOFOLLOW_LINKS) {
                    copyOption.getClass();
                    throw new UnsupportedOperationException("Unsupported copy option");
                }
            }
            flags.copyBasicAttributes = true;
            flags.copyPosixAttributes = true;
            flags.copyNonPosixAttributes = true;
            flags.failIfUnableToCopyBasic = true;
            return flags;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00a8 A[Catch: UnixException -> 0x00c0, all -> 0x00ce, TRY_LEAVE, TryCatch #1 {UnixException -> 0x00c0, blocks: (B:42:0x008a, B:44:0x0090, B:33:0x00a8), top: B:41:0x008a, outer: #7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void copyDirectory(sun.nio.fs.UnixPath r5, sun.nio.fs.UnixFileAttributes r6, sun.nio.fs.UnixPath r7, sun.nio.fs.UnixCopyFile.Flags r8) throws java.io.IOException {
        /*
            int r0 = r6.mode()     // Catch: sun.nio.fs.UnixException -> L8
            sun.nio.fs.UnixNativeDispatcher.mkdir(r7, r0)     // Catch: sun.nio.fs.UnixException -> L8
            goto Lc
        L8:
            r0 = move-exception
            r0.rethrowAsIOException(r7)
        Lc:
            boolean r0 = r8.copyBasicAttributes
            if (r0 != 0) goto L1a
            boolean r0 = r8.copyPosixAttributes
            if (r0 != 0) goto L1a
            boolean r0 = r8.copyNonPosixAttributes
            if (r0 != 0) goto L1a
            goto Lcd
        L1a:
            r0 = -1
            r1 = 0
            int r2 = sun.nio.fs.UnixNativeDispatcher.open(r7, r1, r1)     // Catch: sun.nio.fs.UnixException -> L21
            goto L31
        L21:
            r2 = move-exception
            boolean r3 = r8.copyNonPosixAttributes
            if (r3 == 0) goto L30
            boolean r3 = r8.failIfUnableToCopyNonPosix
            if (r3 == 0) goto L30
            sun.nio.fs.UnixNativeDispatcher.rmdir(r7)     // Catch: sun.nio.fs.UnixException -> L2d
        L2d:
            r2.rethrowAsIOException(r7)
        L30:
            r2 = -1
        L31:
            boolean r3 = r8.copyPosixAttributes     // Catch: java.lang.Throwable -> Lce
            if (r3 == 0) goto L65
            if (r2 < 0) goto L4a
            int r3 = r6.uid()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            int r4 = r6.gid()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.fchown(r2, r3, r4)     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            int r3 = r6.mode()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.fchmod(r2, r3)     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            goto L65
        L4a:
            int r3 = r6.uid()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            int r4 = r6.gid()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.chown(r7, r3, r4)     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            int r3 = r6.mode()     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.chmod(r7, r3)     // Catch: sun.nio.fs.UnixException -> L5d java.lang.Throwable -> Lce
            goto L65
        L5d:
            r3 = move-exception
            boolean r4 = r8.failIfUnableToCopyPosix     // Catch: java.lang.Throwable -> Lce
            if (r4 == 0) goto L65
            r3.rethrowAsIOException(r7)     // Catch: java.lang.Throwable -> Lce
        L65:
            boolean r3 = r8.copyNonPosixAttributes     // Catch: java.lang.Throwable -> Lce
            if (r3 == 0) goto L84
            if (r2 < 0) goto L84
            int r0 = sun.nio.fs.UnixNativeDispatcher.open(r5, r1, r1)     // Catch: sun.nio.fs.UnixException -> L70 java.lang.Throwable -> Lce
            goto L78
        L70:
            r1 = move-exception
            boolean r3 = r8.failIfUnableToCopyNonPosix     // Catch: java.lang.Throwable -> Lce
            if (r3 == 0) goto L78
            r1.rethrowAsIOException(r5)     // Catch: java.lang.Throwable -> Lce
        L78:
            if (r0 < 0) goto L84
            sun.nio.fs.UnixFileSystem r5 = r5.getFileSystem()     // Catch: java.lang.Throwable -> Lce
            r5.copyNonPosixAttributes(r0, r2)     // Catch: java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.close(r0)     // Catch: java.lang.Throwable -> Lce
        L84:
            boolean r5 = r8.copyBasicAttributes     // Catch: java.lang.Throwable -> Lce
            if (r5 == 0) goto Lc8
            if (r2 < 0) goto La8
            boolean r5 = sun.nio.fs.UnixNativeDispatcher.futimesSupported()     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            if (r5 == 0) goto La8
            java.nio.file.attribute.FileTime r5 = r6.lastAccessTime()     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            long r0 = r5.to(r0)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.nio.file.attribute.FileTime r5 = r6.lastModifiedTime()     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            long r5 = r5.to(r6)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.futimes(r2, r0, r5)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            goto Lc8
        La8:
            java.nio.file.attribute.FileTime r5 = r6.lastAccessTime()     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            long r0 = r5.to(r0)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.nio.file.attribute.FileTime r5 = r6.lastModifiedTime()     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MICROSECONDS     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            long r5 = r5.to(r6)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            sun.nio.fs.UnixNativeDispatcher.utimes(r7, r0, r5)     // Catch: sun.nio.fs.UnixException -> Lc0 java.lang.Throwable -> Lce
            goto Lc8
        Lc0:
            r5 = move-exception
            boolean r6 = r8.failIfUnableToCopyBasic     // Catch: java.lang.Throwable -> Lce
            if (r6 == 0) goto Lc8
            r5.rethrowAsIOException(r7)     // Catch: java.lang.Throwable -> Lce
        Lc8:
            if (r2 < 0) goto Lcd
            sun.nio.fs.UnixNativeDispatcher.close(r2)
        Lcd:
            return
        Lce:
            r5 = move-exception
            if (r2 < 0) goto Ld4
            sun.nio.fs.UnixNativeDispatcher.close(r2)
        Ld4:
            sun.nio.fs.UnixNativeDispatcher.rmdir(r7)     // Catch: sun.nio.fs.UnixException -> Ld7
        Ld7:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixCopyFile.copyDirectory(sun.nio.fs.UnixPath, sun.nio.fs.UnixFileAttributes, sun.nio.fs.UnixPath, sun.nio.fs.UnixCopyFile$Flags):void");
    }

    private static void copyFile(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags, long j) throws IOException {
        int i;
        int i2 = -1;
        try {
            i = UnixNativeDispatcher.open(unixPath, 0, 0);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
            i = -1;
        }
        try {
            try {
                i2 = UnixNativeDispatcher.open(unixPath2, 193, unixFileAttributes.mode());
            } catch (UnixException e2) {
                e2.rethrowAsIOException(unixPath2);
            }
            try {
                try {
                    transfer(i2, i2, j);
                } finally {
                    UnixNativeDispatcher.close(i2);
                    try {
                        UnixNativeDispatcher.unlink(unixPath2);
                    } catch (UnixException unused) {
                    }
                }
            } catch (UnixException e3) {
                e3.rethrowAsIOException(unixPath, unixPath2);
            }
            if (flags.copyPosixAttributes) {
                try {
                    UnixNativeDispatcher.fchown(i2, unixFileAttributes.uid(), unixFileAttributes.gid());
                    UnixNativeDispatcher.fchmod(i2, unixFileAttributes.mode());
                } catch (UnixException e4) {
                    if (flags.failIfUnableToCopyPosix) {
                        e4.rethrowAsIOException(unixPath2);
                    }
                }
            }
            if (flags.copyNonPosixAttributes) {
                unixPath.getFileSystem().copyNonPosixAttributes(i2, i2);
            }
            if (flags.copyBasicAttributes) {
                try {
                    if (UnixNativeDispatcher.futimesSupported()) {
                        UnixNativeDispatcher.futimes(i2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                    } else {
                        UnixNativeDispatcher.utimes(unixPath2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                    }
                } catch (UnixException e5) {
                    if (flags.failIfUnableToCopyBasic) {
                        e5.rethrowAsIOException(unixPath2);
                    }
                }
            }
            UnixNativeDispatcher.close(i2);
        } catch (Throwable th) {
            throw th;
        }
    }

    private static void copyLink(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags) throws IOException {
        byte[] bArr;
        try {
            bArr = UnixNativeDispatcher.readlink(unixPath);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
            bArr = null;
        }
        try {
            UnixNativeDispatcher.symlink(bArr, unixPath2);
            if (flags.copyPosixAttributes) {
                try {
                    UnixNativeDispatcher.lchown(unixPath2, unixFileAttributes.uid(), unixFileAttributes.gid());
                } catch (UnixException unused) {
                }
            }
        } catch (UnixException e2) {
            e2.rethrowAsIOException(unixPath2);
        }
    }

    private static void copySpecial(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags) throws IOException {
        try {
            UnixNativeDispatcher.mknod(unixPath2, unixFileAttributes.mode(), unixFileAttributes.rdev());
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath2);
        }
        try {
            if (flags.copyPosixAttributes) {
                try {
                    UnixNativeDispatcher.chown(unixPath2, unixFileAttributes.uid(), unixFileAttributes.gid());
                    UnixNativeDispatcher.chmod(unixPath2, unixFileAttributes.mode());
                } catch (UnixException e2) {
                    if (flags.failIfUnableToCopyPosix) {
                        e2.rethrowAsIOException(unixPath2);
                    }
                }
            }
            if (flags.copyBasicAttributes) {
                try {
                    UnixNativeDispatcher.utimes(unixPath2, unixFileAttributes.lastAccessTime().to(TimeUnit.MICROSECONDS), unixFileAttributes.lastModifiedTime().to(TimeUnit.MICROSECONDS));
                } catch (UnixException e3) {
                    if (flags.failIfUnableToCopyBasic) {
                        e3.rethrowAsIOException(unixPath2);
                    }
                }
            }
        } catch (Throwable th) {
            try {
                UnixNativeDispatcher.unlink(unixPath2);
            } catch (UnixException unused) {
            }
            throw th;
        }
    }

    static void ensureEmptyDir(UnixPath unixPath) throws IOException {
        try {
            UnixDirectoryStream unixDirectoryStream = new UnixDirectoryStream(unixPath, UnixNativeDispatcher.opendir(unixPath), new UnixCopyFile$$ExternalSyntheticLambda1());
            try {
                if (unixDirectoryStream.iterator().hasNext()) {
                    throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
                }
                unixDirectoryStream.close();
            } finally {
            }
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:42|43|(1:47)|48|(1:50)(2:80|(1:82)(2:83|(1:85)(6:86|52|53|(1:55)(1:59)|56|57)))|51|52|53|(0)(0)|56|57) */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009a, code lost:
    
        r0.rethrowAsIOException(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00ea, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00f0, code lost:
    
        if (r2.isDirectory() != false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00f2, code lost:
    
        sun.nio.fs.UnixNativeDispatcher.rmdir(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00ff, code lost:
    
        if (r2.isDirectory() == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0118, code lost:
    
        r0.rethrowAsIOException(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0117, code lost:
    
        throw new java.nio.file.DirectoryNotEmptyException(r1.getPathForExceptionMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x011b, code lost:
    
        r0.rethrowAsIOException(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f6, code lost:
    
        sun.nio.fs.UnixNativeDispatcher.unlink(r3);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e2 A[Catch: UnixException -> 0x00ea, TryCatch #2 {UnixException -> 0x00ea, blocks: (B:53:0x00dc, B:55:0x00e2, B:59:0x00e6), top: B:52:0x00dc }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e6 A[Catch: UnixException -> 0x00ea, TRY_LEAVE, TryCatch #2 {UnixException -> 0x00ea, blocks: (B:53:0x00dc, B:55:0x00e2, B:59:0x00e6), top: B:52:0x00dc }] */
    /* JADX WARN: Type inference failed for: r11v13, types: [boolean] */
    /* JADX WARN: Type inference failed for: r11v15, types: [int] */
    /* JADX WARN: Type inference failed for: r11v17 */
    /* JADX WARN: Type inference failed for: r11v6, types: [sun.nio.fs.UnixFileAttributes] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void move(sun.nio.fs.UnixPath r9, sun.nio.fs.UnixPath r10, java.nio.file.CopyOption... r11) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.UnixCopyFile.move(sun.nio.fs.UnixPath, sun.nio.fs.UnixPath, java.nio.file.CopyOption[]):void");
    }

    static void copy(UnixPath unixPath, UnixPath unixPath2, CopyOption... copyOptionArr) throws IOException {
        UnixFileAttributes unixFileAttributes;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            unixPath.checkRead();
            unixPath2.checkWrite();
        }
        Flags fromCopyOptions = Flags.fromCopyOptions(copyOptionArr);
        UnixFileAttributes unixFileAttributes2 = null;
        try {
            unixFileAttributes = UnixFileAttributes.get(unixPath, fromCopyOptions.followLinks);
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
            unixFileAttributes = null;
        }
        if (securityManager != null && unixFileAttributes.isSymbolicLink()) {
            securityManager.checkPermission(new LinkPermission("symbolic"));
        }
        try {
            unixFileAttributes2 = UnixFileAttributes.get(unixPath2, false);
        } catch (UnixException unused) {
        }
        if (unixFileAttributes2 != null) {
            if (unixFileAttributes.isSameFile(unixFileAttributes2)) {
                return;
            }
            if (!fromCopyOptions.replaceExisting) {
                throw new FileAlreadyExistsException(unixPath2.getPathForExceptionMessage());
            }
            try {
                if (unixFileAttributes2.isDirectory()) {
                    UnixNativeDispatcher.rmdir(unixPath2);
                } else {
                    UnixNativeDispatcher.unlink(unixPath2);
                }
            } catch (UnixException e2) {
                if (unixFileAttributes2.isDirectory() && (e2.errno() == 17 || e2.errno() == 39)) {
                    throw new DirectoryNotEmptyException(unixPath2.getPathForExceptionMessage());
                }
                e2.rethrowAsIOException(unixPath2);
            }
        }
        if (unixFileAttributes.isDirectory()) {
            copyDirectory(unixPath, unixFileAttributes, unixPath2, fromCopyOptions);
            return;
        }
        if (unixFileAttributes.isSymbolicLink()) {
            copyLink(unixPath, unixFileAttributes, unixPath2, fromCopyOptions);
            return;
        }
        if (!fromCopyOptions.interruptible) {
            copyFile(unixPath, unixFileAttributes, unixPath2, fromCopyOptions, 0L);
            return;
        }
        try {
            Cancellable.runInterruptibly(new 1(unixPath, unixFileAttributes, unixPath2, fromCopyOptions));
        } catch (ExecutionException e3) {
            IOException cause = e3.getCause();
            if (cause instanceof IOException) {
                throw cause;
            }
            throw new IOException(cause);
        }
    }

    class 1 extends Cancellable {
        final /* synthetic */ UnixFileAttributes val$attrsToCopy;
        final /* synthetic */ Flags val$flags;
        final /* synthetic */ UnixPath val$source;
        final /* synthetic */ UnixPath val$target;

        1(UnixPath unixPath, UnixFileAttributes unixFileAttributes, UnixPath unixPath2, Flags flags) {
            this.val$source = unixPath;
            this.val$attrsToCopy = unixFileAttributes;
            this.val$target = unixPath2;
            this.val$flags = flags;
        }

        public void implRun() throws IOException {
            UnixCopyFile.-$$Nest$smcopyFile(this.val$source, this.val$attrsToCopy, this.val$target, this.val$flags, addressToPollForCancel());
        }
    }

    class 2 implements PrivilegedAction {
        2() {
        }

        public Void run() {
            System.loadLibrary("nio");
            return null;
        }
    }

    static {
        AccessController.doPrivileged(new 2());
    }
}
