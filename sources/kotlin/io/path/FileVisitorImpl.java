package kotlin.io.path;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileVisitorBuilder.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001By\u0012\u001a\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001a\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004\u0012\u001c\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004¢\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0018\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u001a\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00022\b\u0010\u0013\u001a\u0004\u0018\u00010\tH\u0016R\"\u0010\u0003\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlin/io/path/FileVisitorImpl;", "Ljava/nio/file/SimpleFileVisitor;", "Ljava/nio/file/Path;", "onPreVisitDirectory", "Lkotlin/Function2;", "Ljava/nio/file/attribute/BasicFileAttributes;", "Ljava/nio/file/FileVisitResult;", "onVisitFile", "onVisitFileFailed", "Ljava/io/IOException;", "onPostVisitDirectory", "<init>", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "preVisitDirectory", "dir", "attrs", "visitFile", "file", "visitFileFailed", "exc", "postVisitDirectory", "kotlin-stdlib-jdk7"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
final class FileVisitorImpl extends SimpleFileVisitor {

    @Nullable
    private final Function2 onPostVisitDirectory;

    @Nullable
    private final Function2 onPreVisitDirectory;

    @Nullable
    private final Function2 onVisitFile;

    @Nullable
    private final Function2 onVisitFileFailed;

    public FileVisitorImpl(@Nullable Function2 function2, @Nullable Function2 function22, @Nullable Function2 function23, @Nullable Function2 function24) {
        this.onPreVisitDirectory = function2;
        this.onVisitFile = function22;
        this.onVisitFileFailed = function23;
        this.onPostVisitDirectory = function24;
    }

    @NotNull
    public FileVisitResult preVisitDirectory(@NotNull Path dir, @NotNull BasicFileAttributes attrs) {
        FileVisitResult fileVisitResult;
        Intrinsics.checkNotNullParameter(dir, "dir");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        Function2 function2 = this.onPreVisitDirectory;
        if (function2 != null && (fileVisitResult = (FileVisitResult) function2.invoke(dir, attrs)) != null) {
            return fileVisitResult;
        }
        FileVisitResult preVisitDirectory = super.preVisitDirectory(dir, attrs);
        Intrinsics.checkNotNullExpressionValue(preVisitDirectory, "preVisitDirectory(...)");
        return preVisitDirectory;
    }

    @NotNull
    public FileVisitResult visitFile(@NotNull Path file, @NotNull BasicFileAttributes attrs) {
        FileVisitResult fileVisitResult;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        Function2 function2 = this.onVisitFile;
        if (function2 != null && (fileVisitResult = (FileVisitResult) function2.invoke(file, attrs)) != null) {
            return fileVisitResult;
        }
        FileVisitResult visitFile = super.visitFile(file, attrs);
        Intrinsics.checkNotNullExpressionValue(visitFile, "visitFile(...)");
        return visitFile;
    }

    @NotNull
    public FileVisitResult visitFileFailed(@NotNull Path file, @NotNull IOException exc) {
        FileVisitResult fileVisitResult;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(exc, "exc");
        Function2 function2 = this.onVisitFileFailed;
        if (function2 != null && (fileVisitResult = (FileVisitResult) function2.invoke(file, exc)) != null) {
            return fileVisitResult;
        }
        FileVisitResult visitFileFailed = super.visitFileFailed(file, exc);
        Intrinsics.checkNotNullExpressionValue(visitFileFailed, "visitFileFailed(...)");
        return visitFileFailed;
    }

    @NotNull
    public FileVisitResult postVisitDirectory(@NotNull Path dir, @Nullable IOException exc) {
        FileVisitResult fileVisitResult;
        Intrinsics.checkNotNullParameter(dir, "dir");
        Function2 function2 = this.onPostVisitDirectory;
        if (function2 != null && (fileVisitResult = (FileVisitResult) function2.invoke(dir, exc)) != null) {
            return fileVisitResult;
        }
        FileVisitResult postVisitDirectory = super.postVisitDirectory(dir, exc);
        Intrinsics.checkNotNullExpressionValue(postVisitDirectory, "postVisitDirectory(...)");
        return postVisitDirectory;
    }
}
