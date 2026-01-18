package java.nio.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class CopyMoveHelper {
    private CopyMoveHelper() {
    }

    private static class CopyOptions {
        boolean replaceExisting = false;
        boolean copyAttributes = false;
        boolean followLinks = true;

        private CopyOptions() {
        }

        static CopyOptions parse(CopyOption... copyOptionArr) {
            CopyOptions copyOptions = new CopyOptions();
            for (CopyOption copyOption : copyOptionArr) {
                if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                    copyOptions.replaceExisting = true;
                } else if (copyOption == LinkOption.NOFOLLOW_LINKS) {
                    copyOptions.followLinks = false;
                } else if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
                    copyOptions.copyAttributes = true;
                } else {
                    copyOption.getClass();
                    throw new UnsupportedOperationException("'" + copyOption + "' is not a recognized copy option");
                }
            }
            return copyOptions;
        }
    }

    private static CopyOption[] convertMoveToCopyOptions(CopyOption... copyOptionArr) throws AtomicMoveNotSupportedException {
        int length = copyOptionArr.length;
        CopyOption[] copyOptionArr2 = new CopyOption[length + 2];
        for (int i = 0; i < length; i++) {
            CopyOption copyOption = copyOptionArr[i];
            if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
                throw new AtomicMoveNotSupportedException(null, null, "Atomic move between providers is not supported");
            }
            copyOptionArr2[i] = copyOption;
        }
        copyOptionArr2[length] = LinkOption.NOFOLLOW_LINKS;
        copyOptionArr2[length + 1] = StandardCopyOption.COPY_ATTRIBUTES;
        return copyOptionArr2;
    }

    static void copyToForeignTarget(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        CopyOptions parse = CopyOptions.parse(copyOptionArr);
        BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class, parse.followLinks ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        if (readAttributes.isSymbolicLink()) {
            throw new IOException("Copying of symbolic links not supported");
        }
        if (parse.replaceExisting) {
            Files.deleteIfExists(path2);
        } else if (Files.exists(path2, new LinkOption[0])) {
            throw new FileAlreadyExistsException(path2.toString());
        }
        if (readAttributes.isDirectory()) {
            Files.createDirectory(path2, new FileAttribute[0]);
        } else {
            InputStream newInputStream = Files.newInputStream(path, new OpenOption[0]);
            try {
                Files.copy(newInputStream, path2, new CopyOption[0]);
                if (newInputStream != null) {
                    newInputStream.close();
                }
            } catch (Throwable th) {
                if (newInputStream != null) {
                    try {
                        newInputStream.close();
                    } catch (Throwable th2) {
                        CopyMoveHelper$$ExternalSyntheticBackport0.m(th, th2);
                    }
                }
                throw th;
            }
        }
        if (parse.copyAttributes) {
            try {
                ((BasicFileAttributeView) Files.getFileAttributeView(path2, BasicFileAttributeView.class, new LinkOption[0])).setTimes(readAttributes.lastModifiedTime(), readAttributes.lastAccessTime(), readAttributes.creationTime());
            } catch (Throwable th3) {
                try {
                    Files.delete(path2);
                } catch (Throwable th4) {
                    CopyMoveHelper$$ExternalSyntheticBackport0.m(th3, th4);
                }
                throw th3;
            }
        }
    }

    static void moveToForeignTarget(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        copyToForeignTarget(path, path2, convertMoveToCopyOptions(copyOptionArr));
        Files.delete(path);
    }
}
