package java.nio.file.attribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface UserDefinedFileAttributeView extends FileAttributeView {
    void delete(String str) throws IOException;

    List list() throws IOException;

    String name();

    int read(String str, ByteBuffer byteBuffer) throws IOException;

    int size(String str) throws IOException;

    int write(String str, ByteBuffer byteBuffer) throws IOException;
}
