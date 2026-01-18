package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\u001a*\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0007\u001a*\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0007\u001a\u0012\u0010\u000e\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0012\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u001b\u0010\u0013\u001a\u0004\u0018\u00010\u0003*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u0014\u001a&\u0010\u0015\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00012\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a\u001a8\u0010\u001b\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00012\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u001a\b\u0002\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\u001d\u001a\n\u0010 \u001a\u00020\u0018*\u00020\u0001\u001a\u0012\u0010!\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001\u001a\u0012\u0010!\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\"\u001a\u00020\u0003\u001a\u0012\u0010#\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\"\u001a\u00020\u0001\u001a\u0012\u0010#\u001a\u00020\u0018*\u00020\u00012\u0006\u0010\"\u001a\u00020\u0003\u001a\n\u0010$\u001a\u00020\u0001*\u00020\u0001\u001a\u0011\u0010$\u001a\u00020%*\u00020%H\u0002¢\u0006\u0002\b&\u001a\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010'*\b\u0012\u0004\u0012\u00020\u00010'H\u0002¢\u0006\u0002\b&\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00012\u0006\u0010)\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00012\u0006\u0010)\u001a\u00020\u0003\u001a\u0012\u0010*\u001a\u00020\u0001*\u00020\u00012\u0006\u0010)\u001a\u00020\u0001\u001a\u0012\u0010*\u001a\u00020\u0001*\u00020\u00012\u0006\u0010)\u001a\u00020\u0003\"\u0015\u0010\u0007\u001a\u00020\u0003*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0015\u0010\n\u001a\u00020\u0003*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\"\u0015\u0010\f\u001a\u00020\u0003*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\r\u0010\t¨\u0006+"}, d2 = {"createTempDir", "Ljava/io/File;", "prefix", "", "suffix", "directory", "createTempFile", "extension", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "toRelativeString", "base", "relativeTo", "relativeToOrSelf", "relativeToOrNull", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "copyTo", "target", "overwrite", "", "bufferSize", "", "copyRecursively", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "deleteRecursively", "startsWith", "other", "endsWith", "normalize", "Lkotlin/io/FilePathComponents;", "normalize$FilesKt__UtilsKt", "", "resolve", "relative", "resolveSibling", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/io/FilesKt")
@SourceDebugExtension({"SMAP\nUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Utils.kt\nkotlin/io/FilesKt__UtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,473:1\n1#2:474\n1292#3,3:475\n*S KotlinDebug\n*F\n+ 1 Utils.kt\nkotlin/io/FilesKt__UtilsKt\n*L\n347#1:475,3\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
    public static /* synthetic */ Unit $r8$lambda$b184Hv2lLSyaIabDgjGNkJ5ATf4(Function2 function2, File file, IOException iOException) {
        return copyRecursively$lambda$4$FilesKt__UtilsKt(function2, file, iOException);
    }

    public static /* synthetic */ File createTempDir$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempDir(str, str2, file);
    }

    @Deprecated(message = "Avoid creating temporary directories in the default temp location with this function due to too wide permissions on the newly created directory. Use kotlin.io.path.createTempDirectory instead.")
    @NotNull
    public static final File createTempDir(@NotNull String prefix, @Nullable String str, @Nullable File file) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        File createTempFile = File.createTempFile(prefix, str, file);
        createTempFile.delete();
        if (createTempFile.mkdir()) {
            Intrinsics.checkNotNull(createTempFile);
            return createTempFile;
        }
        throw new IOException("Unable to create temporary directory " + createTempFile + '.');
    }

    public static /* synthetic */ File createTempFile$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempFile(str, str2, file);
    }

    @Deprecated(message = "Avoid creating temporary files in the default temp location with this function due to too wide permissions on the newly created file. Use kotlin.io.path.createTempFile instead or resort to java.io.File.createTempFile.")
    @NotNull
    public static final File createTempFile(@NotNull String prefix, @Nullable String str, @Nullable File file) {
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        File createTempFile = File.createTempFile(prefix, str, file);
        Intrinsics.checkNotNullExpressionValue(createTempFile, "createTempFile(...)");
        return createTempFile;
    }

    @NotNull
    public static final String getExtension(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        String name = file.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.substringAfterLast(name, '.', "");
    }

    @NotNull
    public static final String getInvariantSeparatorsPath(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        if (File.separatorChar != '/') {
            String path = file.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            return StringsKt.replace$default(path, File.separatorChar, '/', false, 4, (Object) null);
        }
        String path2 = file.getPath();
        Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
        return path2;
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        String name = file.getName();
        Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
        return StringsKt.substringBeforeLast$default(name, ".", (String) null, 2, (Object) null);
    }

    @NotNull
    public static final String toRelativeString(@NotNull File file, @NotNull File base) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(file, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return relativeStringOrNull$FilesKt__UtilsKt;
        }
        throw new IllegalArgumentException("this and base files have different roots: " + file + " and " + base + '.');
    }

    @NotNull
    public static final File relativeTo(@NotNull File file, @NotNull File base) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        return new File(FilesKt.toRelativeString(file, base));
    }

    @NotNull
    public static final File relativeToOrSelf(@NotNull File file, @NotNull File base) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(file, base);
        return relativeStringOrNull$FilesKt__UtilsKt != null ? new File(relativeStringOrNull$FilesKt__UtilsKt) : file;
    }

    @Nullable
    public static final File relativeToOrNull(@NotNull File file, @NotNull File base) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(file, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return new File(relativeStringOrNull$FilesKt__UtilsKt);
        }
        return null;
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File file, File file2) {
        FilePathComponents normalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt(FilesKt.toComponents(file));
        FilePathComponents normalize$FilesKt__UtilsKt2 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(file2));
        if (!Intrinsics.areEqual(normalize$FilesKt__UtilsKt.getRoot(), normalize$FilesKt__UtilsKt2.getRoot())) {
            return null;
        }
        int size = normalize$FilesKt__UtilsKt2.getSize();
        int size2 = normalize$FilesKt__UtilsKt.getSize();
        int min = Math.min(size2, size);
        int i = 0;
        while (i < min && Intrinsics.areEqual(normalize$FilesKt__UtilsKt.getSegments().get(i), normalize$FilesKt__UtilsKt2.getSegments().get(i))) {
            i++;
        }
        Appendable sb = new StringBuilder();
        int i2 = size - 1;
        if (i <= i2) {
            while (!Intrinsics.areEqual(((File) normalize$FilesKt__UtilsKt2.getSegments().get(i2)).getName(), "..")) {
                sb.append("..");
                if (i2 != i) {
                    sb.append(File.separatorChar);
                }
                if (i2 != i) {
                    i2--;
                }
            }
            return null;
        }
        if (i < size2) {
            if (i < size) {
                sb.append(File.separatorChar);
            }
            CharSequence separator = File.separator;
            Intrinsics.checkNotNullExpressionValue(separator, "separator");
            CollectionsKt.joinTo$default(CollectionsKt.drop(normalize$FilesKt__UtilsKt.getSegments(), i), sb, separator, null, null, 0, null, null, 124, null);
        }
        return sb.toString();
    }

    public static /* synthetic */ File copyTo$default(File file, File file2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return FilesKt.copyTo(file, file2, z, i);
    }

    @NotNull
    public static final File copyTo(@NotNull File file, @NotNull File target, boolean z, int i) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        if (!file.exists()) {
            throw new NoSuchFileException(file, null, "The source file doesn't exist.", 2, null);
        }
        if (target.exists()) {
            if (!z) {
                throw new FileAlreadyExistsException(file, target, "The destination file already exists.");
            }
            if (!target.delete()) {
                throw new FileAlreadyExistsException(file, target, "Tried to overwrite the destination, but failed to delete it.");
            }
        }
        if (file.isDirectory()) {
            if (target.mkdirs()) {
                return target;
            }
            throw new FileSystemException(file, target, "Failed to create target directory.");
        }
        File parentFile = target.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        OutputStream outputStream = (Closeable) new FileInputStream(file);
        try {
            InputStream inputStream = (FileInputStream) outputStream;
            outputStream = (Closeable) new FileOutputStream(target);
            try {
                long copyTo = ByteStreamsKt.copyTo(inputStream, (FileOutputStream) outputStream, i);
                CloseableKt.closeFinally(outputStream, null);
                Long.valueOf(copyTo);
                CloseableKt.closeFinally(outputStream, null);
                return target;
            } finally {
            }
        } finally {
        }
    }

    /* compiled from: Utils.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    static final class 1 implements Function2 {
        public static final 1 INSTANCE = new 1();

        1() {
        }

        public final Void invoke(File file, IOException exception) {
            Intrinsics.checkNotNullParameter(file, "<unused var>");
            Intrinsics.checkNotNullParameter(exception, "exception");
            throw exception;
        }
    }

    public static /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            function2 = 1.INSTANCE;
        }
        return FilesKt.copyRecursively(file, file2, z, function2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00b1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ad A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean copyRecursively(@org.jetbrains.annotations.NotNull java.io.File r12, @org.jetbrains.annotations.NotNull java.io.File r13, boolean r14, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.Function2 r15) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)
            java.lang.String r0 = "target"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r0)
            java.lang.String r0 = "onError"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r15, r0)
            boolean r0 = r12.exists()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L2c
            kotlin.io.NoSuchFileException r3 = new kotlin.io.NoSuchFileException
            r7 = 2
            r8 = 0
            r5 = 0
            java.lang.String r6 = "The source file doesn't exist."
            r4 = r12
            r3.<init>(r4, r5, r6, r7, r8)
            java.lang.Object r12 = r15.invoke(r4, r3)
            kotlin.io.OnErrorAction r13 = kotlin.io.OnErrorAction.TERMINATE
            if (r12 == r13) goto L2b
            return r1
        L2b:
            return r2
        L2c:
            r4 = r12
            kotlin.io.FileTreeWalk r12 = kotlin.io.FilesKt.walkTopDown(r4)     // Catch: kotlin.io.TerminateException -> Lda
            kotlin.io.FilesKt__UtilsKt$$ExternalSyntheticLambda0 r0 = new kotlin.io.FilesKt__UtilsKt$$ExternalSyntheticLambda0     // Catch: kotlin.io.TerminateException -> Lda
            r0.<init>(r15)     // Catch: kotlin.io.TerminateException -> Lda
            kotlin.io.FileTreeWalk r12 = r12.onFail(r0)     // Catch: kotlin.io.TerminateException -> Lda
            java.util.Iterator r12 = r12.iterator()     // Catch: kotlin.io.TerminateException -> Lda
        L3e:
            boolean r0 = r12.hasNext()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 == 0) goto Ld9
            java.lang.Object r0 = r12.next()     // Catch: kotlin.io.TerminateException -> Lda
            r5 = r0
            java.io.File r5 = (java.io.File) r5     // Catch: kotlin.io.TerminateException -> Lda
            boolean r0 = r5.exists()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != 0) goto L65
            r6 = r5
            kotlin.io.NoSuchFileException r5 = new kotlin.io.NoSuchFileException     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.String r8 = "The source file doesn't exist."
            r9 = 2
            r10 = 0
            r7 = 0
            r5.<init>(r6, r7, r8, r9, r10)     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.Object r0 = r15.invoke(r6, r5)     // Catch: kotlin.io.TerminateException -> Lda
            kotlin.io.OnErrorAction r3 = kotlin.io.OnErrorAction.TERMINATE     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != r3) goto L3e
            return r2
        L65:
            r6 = r5
            java.lang.String r0 = kotlin.io.FilesKt.toRelativeString(r6, r4)     // Catch: kotlin.io.TerminateException -> Lda
            java.io.File r6 = new java.io.File     // Catch: kotlin.io.TerminateException -> Lda
            r6.<init>(r13, r0)     // Catch: kotlin.io.TerminateException -> Lda
            boolean r0 = r6.exists()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 == 0) goto La7
            boolean r0 = r5.isDirectory()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 == 0) goto L81
            boolean r0 = r6.isDirectory()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != 0) goto La7
        L81:
            if (r14 != 0) goto L84
            goto L97
        L84:
            boolean r0 = r6.isDirectory()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 == 0) goto L91
            boolean r0 = kotlin.io.FilesKt.deleteRecursively(r6)     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != 0) goto La7
            goto L97
        L91:
            boolean r0 = r6.delete()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != 0) goto La7
        L97:
            kotlin.io.FileAlreadyExistsException r0 = new kotlin.io.FileAlreadyExistsException     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.String r3 = "The destination file already exists."
            r0.<init>(r5, r6, r3)     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.Object r0 = r15.invoke(r6, r0)     // Catch: kotlin.io.TerminateException -> Lda
            kotlin.io.OnErrorAction r3 = kotlin.io.OnErrorAction.TERMINATE     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 != r3) goto L3e
            return r2
        La7:
            boolean r0 = r5.isDirectory()     // Catch: kotlin.io.TerminateException -> Lda
            if (r0 == 0) goto Lb1
            r6.mkdirs()     // Catch: kotlin.io.TerminateException -> Lda
            goto L3e
        Lb1:
            r9 = 4
            r10 = 0
            r8 = 0
            r7 = r14
            java.io.File r14 = kotlin.io.FilesKt.copyTo$default(r5, r6, r7, r8, r9, r10)     // Catch: kotlin.io.TerminateException -> Lda
            r6 = r5
            long r8 = r14.length()     // Catch: kotlin.io.TerminateException -> Lda
            long r10 = r6.length()     // Catch: kotlin.io.TerminateException -> Lda
            int r14 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r14 == 0) goto Ld6
            java.io.IOException r14 = new java.io.IOException     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.String r0 = "Source file wasn't copied completely, length of destination file differs."
            r14.<init>(r0)     // Catch: kotlin.io.TerminateException -> Lda
            java.lang.Object r14 = r15.invoke(r6, r14)     // Catch: kotlin.io.TerminateException -> Lda
            kotlin.io.OnErrorAction r0 = kotlin.io.OnErrorAction.TERMINATE     // Catch: kotlin.io.TerminateException -> Lda
            if (r14 != r0) goto Ld6
            return r2
        Ld6:
            r14 = r7
            goto L3e
        Ld9:
            return r1
        Lda:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.FilesKt__UtilsKt.copyRecursively(java.io.File, java.io.File, boolean, kotlin.jvm.functions.Function2):boolean");
    }

    private static final Unit copyRecursively$lambda$4$FilesKt__UtilsKt(Function2 function2, File f, IOException e) {
        Intrinsics.checkNotNullParameter(f, "f");
        Intrinsics.checkNotNullParameter(e, "e");
        if (function2.invoke(f, e) != OnErrorAction.TERMINATE) {
            return Unit.INSTANCE;
        }
        throw new TerminateException(f);
    }

    public static final boolean deleteRecursively(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        while (true) {
            boolean z = true;
            for (File file2 : FilesKt.walkBottomUp(file)) {
                if (file2.delete() || !file2.exists()) {
                    if (z) {
                        break;
                    }
                }
                z = false;
            }
            return z;
        }
    }

    public static final boolean startsWith(@NotNull File file, @NotNull File other) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FilePathComponents components = FilesKt.toComponents(file);
        FilePathComponents components2 = FilesKt.toComponents(other);
        if (Intrinsics.areEqual(components.getRoot(), components2.getRoot()) && components.getSize() >= components2.getSize()) {
            return components.getSegments().subList(0, components2.getSize()).equals(components2.getSegments());
        }
        return false;
    }

    public static final boolean startsWith(@NotNull File file, @NotNull String other) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return FilesKt.startsWith(file, new File(other));
    }

    public static final boolean endsWith(@NotNull File file, @NotNull File other) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        FilePathComponents components = FilesKt.toComponents(file);
        FilePathComponents components2 = FilesKt.toComponents(other);
        if (components2.isRooted()) {
            return Intrinsics.areEqual(file, other);
        }
        int size = components.getSize() - components2.getSize();
        if (size < 0) {
            return false;
        }
        return components.getSegments().subList(size, components.getSize()).equals(components2.getSegments());
    }

    public static final boolean endsWith(@NotNull File file, @NotNull String other) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return FilesKt.endsWith(file, new File(other));
    }

    @NotNull
    public static final File normalize(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        FilePathComponents components = FilesKt.toComponents(file);
        File root = components.getRoot();
        Iterable normalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt(components.getSegments());
        CharSequence separator = File.separator;
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        return FilesKt.resolve(root, CollectionsKt.joinToString$default(normalize$FilesKt__UtilsKt, separator, null, null, 0, null, null, 62, null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents filePathComponents) {
        return new FilePathComponents(filePathComponents.getRoot(), normalize$FilesKt__UtilsKt(filePathComponents.getSegments()));
    }

    private static final List normalize$FilesKt__UtilsKt(List list) {
        List arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            String name = file.getName();
            if (!Intrinsics.areEqual(name, ".")) {
                if (Intrinsics.areEqual(name, "..")) {
                    if (arrayList.isEmpty() || Intrinsics.areEqual(((File) CollectionsKt.last(arrayList)).getName(), "..")) {
                        arrayList.add(file);
                    } else {
                        arrayList.remove(arrayList.size() - 1);
                    }
                } else {
                    arrayList.add(file);
                }
            }
        }
        return arrayList;
    }

    @NotNull
    public static final File resolve(@NotNull File file, @NotNull File relative) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        if (FilesKt.isRooted(relative)) {
            return relative;
        }
        CharSequence file2 = file.toString();
        Intrinsics.checkNotNullExpressionValue(file2, "toString(...)");
        CharSequence charSequence = file2;
        if (charSequence.length() == 0 || StringsKt.endsWith$default(charSequence, File.separatorChar, false, 2, (Object) null)) {
            StringBuilder sb = new StringBuilder();
            sb.append(file2);
            sb.append(relative);
            return new File(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file2);
        sb2.append(File.separatorChar);
        sb2.append(relative);
        return new File(sb2.toString());
    }

    @NotNull
    public static final File resolve(@NotNull File file, @NotNull String relative) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        return FilesKt.resolve(file, new File(relative));
    }

    @NotNull
    public static final File resolveSibling(@NotNull File file, @NotNull File relative) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        FilePathComponents components = FilesKt.toComponents(file);
        return FilesKt.resolve(FilesKt.resolve(components.getRoot(), components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1)), relative);
    }

    @NotNull
    public static final File resolveSibling(@NotNull File file, @NotNull String relative) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(relative, "relative");
        return FilesKt.resolveSibling(file, new File(relative));
    }
}
