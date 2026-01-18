package java.nio.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.EnumSet;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class TempFileHelper {
    private static final Path tmpdir = Path.-CC.of(GetPropertyAction.privilegedGetProperty("java.io.tmpdir"), new String[0]);
    private static final boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
    private static final SecureRandom random = new SecureRandom();

    private TempFileHelper() {
    }

    private static Path generatePath(String str, String str2, Path path) {
        Path path2 = path.getFileSystem().getPath(str + TempFileHelper$$ExternalSyntheticBackport2.m(random.nextLong()) + str2, new String[0]);
        if (path2.getParent() != null) {
            throw new IllegalArgumentException("Invalid prefix or suffix");
        }
        return path.resolve(path2);
    }

    private static class PosixPermissions {
        static final FileAttribute filePermissions = PosixFilePermissions.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
        static final FileAttribute dirPermissions = PosixFilePermissions.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE));

        private PosixPermissions() {
        }
    }

    private static Path create(Path path, String str, String str2, boolean z, FileAttribute[] fileAttributeArr) throws IOException {
        FileAttribute fileAttribute;
        FileAttribute fileAttribute2;
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            if (z) {
                str2 = "";
            } else {
                str2 = ".tmp";
            }
        }
        if (path == null) {
            path = tmpdir;
        }
        if (isPosix && path.getFileSystem() == FileSystems.getDefault()) {
            if (fileAttributeArr.length == 0) {
                fileAttributeArr = new FileAttribute[1];
                if (z) {
                    fileAttribute2 = PosixPermissions.dirPermissions;
                } else {
                    fileAttribute2 = PosixPermissions.filePermissions;
                }
                fileAttributeArr[0] = fileAttribute2;
            } else {
                int i = 0;
                while (true) {
                    if (i < fileAttributeArr.length) {
                        if (fileAttributeArr[i].name().equals("posix:permissions")) {
                            break;
                        }
                        i++;
                    } else {
                        int length = fileAttributeArr.length;
                        FileAttribute[] fileAttributeArr2 = new FileAttribute[length + 1];
                        System.arraycopy(fileAttributeArr, 0, fileAttributeArr2, 0, fileAttributeArr.length);
                        if (z) {
                            fileAttribute = PosixPermissions.dirPermissions;
                        } else {
                            fileAttribute = PosixPermissions.filePermissions;
                        }
                        fileAttributeArr2[length] = fileAttribute;
                        fileAttributeArr = fileAttributeArr2;
                    }
                }
            }
        }
        SecurityManager securityManager = System.getSecurityManager();
        while (true) {
            try {
                Path generatePath = generatePath(str, str2, path);
                try {
                    if (z) {
                        return Files.createDirectory(generatePath, fileAttributeArr);
                    }
                    return Files.createFile(generatePath, fileAttributeArr);
                } catch (FileAlreadyExistsException unused) {
                } catch (SecurityException e) {
                    if (path == tmpdir && securityManager != null) {
                        throw new SecurityException("Unable to create temporary file or directory");
                    }
                    throw e;
                }
            } catch (InvalidPathException e2) {
                if (securityManager != null) {
                    throw new IllegalArgumentException("Invalid prefix or suffix");
                }
                throw e2;
            }
        }
    }

    static Path createTempFile(Path path, String str, String str2, FileAttribute[] fileAttributeArr) throws IOException {
        return create(path, str, str2, false, fileAttributeArr);
    }

    static Path createTempDirectory(Path path, String str, FileAttribute[] fileAttributeArr) throws IOException {
        return create(path, str, null, true, fileAttributeArr);
    }
}
