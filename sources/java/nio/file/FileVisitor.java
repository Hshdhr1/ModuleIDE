package java.nio.file;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface FileVisitor {
    FileVisitResult postVisitDirectory(Object obj, IOException iOException) throws IOException;

    FileVisitResult preVisitDirectory(Object obj, BasicFileAttributes basicFileAttributes) throws IOException;

    FileVisitResult visitFile(Object obj, BasicFileAttributes basicFileAttributes) throws IOException;

    FileVisitResult visitFileFailed(Object obj, IOException iOException) throws IOException;
}
