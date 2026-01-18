package java.nio.file;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class SimpleFileVisitor implements FileVisitor {
    protected SimpleFileVisitor() {
    }

    public FileVisitResult preVisitDirectory(Object obj, BasicFileAttributes basicFileAttributes) throws IOException {
        obj.getClass();
        basicFileAttributes.getClass();
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Object obj, BasicFileAttributes basicFileAttributes) throws IOException {
        obj.getClass();
        basicFileAttributes.getClass();
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFileFailed(Object obj, IOException iOException) throws IOException {
        obj.getClass();
        throw iOException;
    }

    public FileVisitResult postVisitDirectory(Object obj, IOException iOException) throws IOException {
        obj.getClass();
        if (iOException != null) {
            throw iOException;
        }
        return FileVisitResult.CONTINUE;
    }
}
