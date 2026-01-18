package desugar.sun.nio.fs;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.spi.FileSystemProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarDefaultFileSystemProvider {
    private static final FileSystemProvider INSTANCE = DesugarLinuxFileSystemProvider.create();

    private DesugarDefaultFileSystemProvider() {
    }

    public static FileSystemProvider instance() {
        return INSTANCE;
    }

    public static FileSystem theFileSystem() {
        return INSTANCE.getFileSystem(URI.create("file:///"));
    }
}
