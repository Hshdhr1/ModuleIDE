package sun.nio.fs;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.spi.FileTypeDetector;
import jdk.internal.util.StaticProperty;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class LinuxFileSystemProvider extends UnixFileSystemProvider {
    /* JADX INFO: Access modifiers changed from: package-private */
    public LinuxFileSystem newFileSystem(String str) {
        return new LinuxFileSystem(this, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinuxFileStore getFileStore(UnixPath unixPath) throws IOException {
        return new LinuxFileStore(unixPath);
    }

    public FileAttributeView getFileAttributeView(Path path, Class cls, LinkOption... linkOptionArr) {
        if (cls == DosFileAttributeView.class) {
            return new LinuxDosFileAttributeView(UnixPath.toUnixPath(path), Util.followLinks(linkOptionArr));
        }
        if (cls == UserDefinedFileAttributeView.class) {
            return new LinuxUserDefinedFileAttributeView(UnixPath.toUnixPath(path), Util.followLinks(linkOptionArr));
        }
        return super.getFileAttributeView(path, cls, linkOptionArr);
    }

    public DynamicFileAttributeView getFileAttributeView(Path path, String str, LinkOption... linkOptionArr) {
        if (str.equals("dos")) {
            return new LinuxDosFileAttributeView(UnixPath.toUnixPath(path), Util.followLinks(linkOptionArr));
        }
        if (str.equals("user")) {
            return new LinuxUserDefinedFileAttributeView(UnixPath.toUnixPath(path), Util.followLinks(linkOptionArr));
        }
        return super.getFileAttributeView(path, str, linkOptionArr);
    }

    public BasicFileAttributes readAttributes(Path path, Class cls, LinkOption... linkOptionArr) throws IOException {
        if (cls == DosFileAttributes.class) {
            return ((DosFileAttributeView) getFileAttributeView(path, DosFileAttributeView.class, linkOptionArr)).readAttributes();
        }
        return super.readAttributes(path, cls, linkOptionArr);
    }

    FileTypeDetector getFileTypeDetector() {
        return chain(new MimeTypesFileTypeDetector(Path.-CC.of(StaticProperty.userHome(), ".mime.types")), new MimeTypesFileTypeDetector(Path.-CC.of("/etc/mime.types", new String[0])));
    }
}
