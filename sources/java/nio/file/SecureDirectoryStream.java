package java.nio.file;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface SecureDirectoryStream extends DirectoryStream {
    void deleteDirectory(Object obj) throws IOException;

    void deleteFile(Object obj) throws IOException;

    FileAttributeView getFileAttributeView(Class cls);

    FileAttributeView getFileAttributeView(Object obj, Class cls, LinkOption... linkOptionArr);

    void move(Object obj, SecureDirectoryStream secureDirectoryStream, Object obj2) throws IOException;

    SeekableByteChannel newByteChannel(Object obj, Set set, FileAttribute... fileAttributeArr) throws IOException;

    SecureDirectoryStream newDirectoryStream(Object obj, LinkOption... linkOptionArr) throws IOException;
}
