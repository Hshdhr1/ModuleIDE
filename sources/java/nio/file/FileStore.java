package java.nio.file;

import java.io.IOException;
import java.nio.file.attribute.FileStoreAttributeView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class FileStore {
    public abstract Object getAttribute(String str) throws IOException;

    public abstract FileStoreAttributeView getFileStoreAttributeView(Class cls);

    public abstract long getTotalSpace() throws IOException;

    public abstract long getUnallocatedSpace() throws IOException;

    public abstract long getUsableSpace() throws IOException;

    public abstract boolean isReadOnly();

    public abstract String name();

    public abstract boolean supportsFileAttributeView(Class cls);

    public abstract boolean supportsFileAttributeView(String str);

    public abstract String type();

    protected FileStore() {
    }

    public long getBlockSize() throws IOException {
        throw new UnsupportedOperationException();
    }
}
