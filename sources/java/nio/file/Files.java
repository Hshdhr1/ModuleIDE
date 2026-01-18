package java.nio.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileTreeWalker;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.spi.FileSystemProvider;
import java.nio.file.spi.FileTypeDetector;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import sun.nio.cs.UTF_8;
import sun.nio.fs.AbstractFileSystemProvider;
import sun.nio.fs.DefaultFileTypeDetector;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Files {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BUFFER_SIZE = 8192;
    private static final Set DEFAULT_CREATE_OPTIONS = Files$$ExternalSyntheticBackport1.m(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
    private static final int MAX_BUFFER_SIZE = 2147483639;

    private Files() {
    }

    private static FileSystemProvider provider(Path path) {
        return path.getFileSystem().provider();
    }

    private static Runnable asUncheckedRunnable(Closeable closeable) {
        return new Files$$ExternalSyntheticLambda3(closeable);
    }

    static /* synthetic */ void lambda$asUncheckedRunnable$0(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static InputStream newInputStream(Path path, OpenOption... openOptionArr) throws IOException {
        return provider(path).newInputStream(path, openOptionArr);
    }

    public static OutputStream newOutputStream(Path path, OpenOption... openOptionArr) throws IOException {
        return provider(path).newOutputStream(path, openOptionArr);
    }

    public static SeekableByteChannel newByteChannel(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        return provider(path).newByteChannel(path, set, fileAttributeArr);
    }

    public static SeekableByteChannel newByteChannel(Path path, OpenOption... openOptionArr) throws IOException {
        Set set;
        if (openOptionArr.length == 0) {
            set = Collections.EMPTY_SET;
        } else {
            Set hashSet = new HashSet();
            Collections.addAll(hashSet, openOptionArr);
            set = hashSet;
        }
        return newByteChannel(path, set, new FileAttribute[0]);
    }

    private static class AcceptAllFilter implements DirectoryStream.Filter {
        static final AcceptAllFilter FILTER = new AcceptAllFilter();

        public boolean accept(Path path) {
            return true;
        }

        private AcceptAllFilter() {
        }
    }

    public static DirectoryStream newDirectoryStream(Path path) throws IOException {
        return provider(path).newDirectoryStream(path, AcceptAllFilter.FILTER);
    }

    public static DirectoryStream newDirectoryStream(Path path, String str) throws IOException {
        if (str.equals("*")) {
            return newDirectoryStream(path);
        }
        FileSystem fileSystem = path.getFileSystem();
        return fileSystem.provider().newDirectoryStream(path, new 1(fileSystem.getPathMatcher("glob:" + str)));
    }

    class 1 implements DirectoryStream.Filter {
        final /* synthetic */ PathMatcher val$matcher;

        1(PathMatcher pathMatcher) {
            this.val$matcher = pathMatcher;
        }

        public boolean accept(Path path) {
            return this.val$matcher.matches(path.getFileName());
        }
    }

    public static DirectoryStream newDirectoryStream(Path path, DirectoryStream.Filter filter) throws IOException {
        return provider(path).newDirectoryStream(path, filter);
    }

    public static Path createFile(Path path, FileAttribute... fileAttributeArr) throws IOException {
        newByteChannel(path, DEFAULT_CREATE_OPTIONS, fileAttributeArr).close();
        return path;
    }

    public static Path createDirectory(Path path, FileAttribute... fileAttributeArr) throws IOException {
        provider(path).createDirectory(path, fileAttributeArr);
        return path;
    }

    public static Path createDirectories(Path path, FileAttribute... fileAttributeArr) throws IOException {
        try {
            createAndCheckIsDirectory(path, fileAttributeArr);
            return path;
        } catch (FileAlreadyExistsException e) {
            throw e;
        } catch (IOException unused) {
            try {
                path = path.toAbsolutePath();
                e = null;
            } catch (SecurityException e2) {
                e = e2;
            }
            Path parent = path.getParent();
            while (parent != null) {
                try {
                    provider(parent).checkAccess(parent, new AccessMode[0]);
                    break;
                } catch (NoSuchFileException unused2) {
                    parent = parent.getParent();
                }
            }
            if (parent == null) {
                if (e == null) {
                    throw new FileSystemException(path.toString(), null, "Unable to determine if root directory exists");
                }
                throw e;
            }
            Iterator it = parent.relativize(path).iterator();
            while (it.hasNext()) {
                parent = parent.resolve((Path) it.next());
                createAndCheckIsDirectory(parent, fileAttributeArr);
            }
            return path;
        }
    }

    private static void createAndCheckIsDirectory(Path path, FileAttribute... fileAttributeArr) throws IOException {
        try {
            createDirectory(path, fileAttributeArr);
        } catch (FileAlreadyExistsException e) {
            if (!isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                throw e;
            }
        }
    }

    public static Path createTempFile(Path path, String str, String str2, FileAttribute... fileAttributeArr) throws IOException {
        path.getClass();
        return TempFileHelper.createTempFile(path, str, str2, fileAttributeArr);
    }

    public static Path createTempFile(String str, String str2, FileAttribute... fileAttributeArr) throws IOException {
        return TempFileHelper.createTempFile(null, str, str2, fileAttributeArr);
    }

    public static Path createTempDirectory(Path path, String str, FileAttribute... fileAttributeArr) throws IOException {
        path.getClass();
        return TempFileHelper.createTempDirectory(path, str, fileAttributeArr);
    }

    public static Path createTempDirectory(String str, FileAttribute... fileAttributeArr) throws IOException {
        return TempFileHelper.createTempDirectory(null, str, fileAttributeArr);
    }

    public static Path createSymbolicLink(Path path, Path path2, FileAttribute... fileAttributeArr) throws IOException {
        provider(path).createSymbolicLink(path, path2, fileAttributeArr);
        return path;
    }

    public static Path createLink(Path path, Path path2) throws IOException {
        provider(path).createLink(path, path2);
        return path;
    }

    public static void delete(Path path) throws IOException {
        provider(path).delete(path);
    }

    public static boolean deleteIfExists(Path path) throws IOException {
        return provider(path).deleteIfExists(path);
    }

    public static Path copy(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        FileSystemProvider provider = provider(path);
        if (provider(path2).equals(provider)) {
            provider.copy(path, path2, copyOptionArr);
            return path2;
        }
        CopyMoveHelper.copyToForeignTarget(path, path2, copyOptionArr);
        return path2;
    }

    public static Path move(Path path, Path path2, CopyOption... copyOptionArr) throws IOException {
        FileSystemProvider provider = provider(path);
        if (provider(path2).equals(provider)) {
            provider.move(path, path2, copyOptionArr);
            return path2;
        }
        CopyMoveHelper.moveToForeignTarget(path, path2, copyOptionArr);
        return path2;
    }

    public static Path readSymbolicLink(Path path) throws IOException {
        return provider(path).readSymbolicLink(path);
    }

    public static FileStore getFileStore(Path path) throws IOException {
        return provider(path).getFileStore(path);
    }

    public static boolean isSameFile(Path path, Path path2) throws IOException {
        return provider(path).isSameFile(path, path2);
    }

    public static boolean isHidden(Path path) throws IOException {
        return provider(path).isHidden(path);
    }

    private static class FileTypeDetectors {
        static final FileTypeDetector defaultFileTypeDetector = createDefaultFileTypeDetector();
        static final List installedDetectors = loadInstalledDetectors();

        private FileTypeDetectors() {
        }

        class 1 implements PrivilegedAction {
            1() {
            }

            public FileTypeDetector run() {
                return DefaultFileTypeDetector.create();
            }
        }

        private static FileTypeDetector createDefaultFileTypeDetector() {
            return (FileTypeDetector) AccessController.doPrivileged(new 1());
        }

        class 2 implements PrivilegedAction {
            2() {
            }

            public List run() {
                ArrayList arrayList = new ArrayList();
                Iterator it = ServiceLoader.load(FileTypeDetector.class, ClassLoader.getSystemClassLoader()).iterator();
                while (it.hasNext()) {
                    arrayList.add((FileTypeDetector) it.next());
                }
                return arrayList;
            }
        }

        private static List loadInstalledDetectors() {
            return (List) AccessController.doPrivileged(new 2());
        }
    }

    public static String probeContentType(Path path) throws IOException {
        Iterator it = FileTypeDetectors.installedDetectors.iterator();
        while (it.hasNext()) {
            String probeContentType = ((FileTypeDetector) it.next()).probeContentType(path);
            if (probeContentType != null) {
                return probeContentType;
            }
        }
        return FileTypeDetectors.defaultFileTypeDetector.probeContentType(path);
    }

    public static FileAttributeView getFileAttributeView(Path path, Class cls, LinkOption... linkOptionArr) {
        return provider(path).getFileAttributeView(path, cls, linkOptionArr);
    }

    public static BasicFileAttributes readAttributes(Path path, Class cls, LinkOption... linkOptionArr) throws IOException {
        return provider(path).readAttributes(path, cls, linkOptionArr);
    }

    public static Path setAttribute(Path path, String str, Object obj, LinkOption... linkOptionArr) throws IOException {
        provider(path).setAttribute(path, str, obj, linkOptionArr);
        return path;
    }

    public static Object getAttribute(Path path, String str, LinkOption... linkOptionArr) throws IOException {
        if (str.indexOf(42) >= 0 || str.indexOf(44) >= 0) {
            throw new IllegalArgumentException(str);
        }
        Map readAttributes = readAttributes(path, str, linkOptionArr);
        int indexOf = str.indexOf(58);
        if (indexOf != -1) {
            str = indexOf == str.length() ? "" : str.substring(indexOf + 1);
        }
        return readAttributes.get(str);
    }

    public static Map readAttributes(Path path, String str, LinkOption... linkOptionArr) throws IOException {
        return provider(path).readAttributes(path, str, linkOptionArr);
    }

    public static Set getPosixFilePermissions(Path path, LinkOption... linkOptionArr) throws IOException {
        return ((PosixFileAttributes) readAttributes(path, PosixFileAttributes.class, linkOptionArr)).permissions();
    }

    public static Path setPosixFilePermissions(Path path, Set set) throws IOException {
        PosixFileAttributeView posixFileAttributeView = (PosixFileAttributeView) getFileAttributeView(path, PosixFileAttributeView.class, new LinkOption[0]);
        if (posixFileAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        posixFileAttributeView.setPermissions(set);
        return path;
    }

    public static UserPrincipal getOwner(Path path, LinkOption... linkOptionArr) throws IOException {
        FileOwnerAttributeView fileOwnerAttributeView = (FileOwnerAttributeView) getFileAttributeView(path, FileOwnerAttributeView.class, linkOptionArr);
        if (fileOwnerAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        return fileOwnerAttributeView.getOwner();
    }

    public static Path setOwner(Path path, UserPrincipal userPrincipal) throws IOException {
        FileOwnerAttributeView fileOwnerAttributeView = (FileOwnerAttributeView) getFileAttributeView(path, FileOwnerAttributeView.class, new LinkOption[0]);
        if (fileOwnerAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        fileOwnerAttributeView.setOwner(userPrincipal);
        return path;
    }

    public static boolean isSymbolicLink(Path path) {
        try {
            return readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).isSymbolicLink();
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isDirectory(Path path, LinkOption... linkOptionArr) {
        if (linkOptionArr.length == 0) {
            FileSystemProvider provider = provider(path);
            if (provider instanceof AbstractFileSystemProvider) {
                return ((AbstractFileSystemProvider) provider).isDirectory(path);
            }
        }
        try {
            return readAttributes(path, BasicFileAttributes.class, linkOptionArr).isDirectory();
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isRegularFile(Path path, LinkOption... linkOptionArr) {
        if (linkOptionArr.length == 0) {
            FileSystemProvider provider = provider(path);
            if (provider instanceof AbstractFileSystemProvider) {
                return ((AbstractFileSystemProvider) provider).isRegularFile(path);
            }
        }
        try {
            return readAttributes(path, BasicFileAttributes.class, linkOptionArr).isRegularFile();
        } catch (IOException unused) {
            return false;
        }
    }

    public static FileTime getLastModifiedTime(Path path, LinkOption... linkOptionArr) throws IOException {
        return readAttributes(path, BasicFileAttributes.class, linkOptionArr).lastModifiedTime();
    }

    public static Path setLastModifiedTime(Path path, FileTime fileTime) throws IOException {
        BasicFileAttributeView basicFileAttributeView = (BasicFileAttributeView) getFileAttributeView(path, BasicFileAttributeView.class, new LinkOption[0]);
        fileTime.getClass();
        basicFileAttributeView.setTimes(fileTime, null, null);
        return path;
    }

    public static long size(Path path) throws IOException {
        return readAttributes(path, BasicFileAttributes.class, new LinkOption[0]).size();
    }

    private static boolean followLinks(LinkOption... linkOptionArr) {
        int length = linkOptionArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            LinkOption linkOption = linkOptionArr[i];
            if (linkOption != LinkOption.NOFOLLOW_LINKS) {
                linkOption.getClass();
                throw new AssertionError("Should not get here");
            }
            i++;
            z = false;
        }
        return z;
    }

    public static boolean exists(Path path, LinkOption... linkOptionArr) {
        if (linkOptionArr.length == 0) {
            FileSystemProvider provider = provider(path);
            if (provider instanceof AbstractFileSystemProvider) {
                return ((AbstractFileSystemProvider) provider).exists(path);
            }
        }
        try {
            if (followLinks(linkOptionArr)) {
                provider(path).checkAccess(path, new AccessMode[0]);
            } else {
                readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean notExists(Path path, LinkOption... linkOptionArr) {
        try {
            if (followLinks(linkOptionArr)) {
                provider(path).checkAccess(path, new AccessMode[0]);
            } else {
                readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
        } catch (NoSuchFileException unused) {
            return true;
        } catch (IOException unused2) {
        }
        return false;
    }

    private static boolean isAccessible(Path path, AccessMode... accessModeArr) {
        try {
            provider(path).checkAccess(path, accessModeArr);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isReadable(Path path) {
        return isAccessible(path, AccessMode.READ);
    }

    public static boolean isWritable(Path path) {
        return isAccessible(path, AccessMode.WRITE);
    }

    public static boolean isExecutable(Path path) {
        return isAccessible(path, AccessMode.EXECUTE);
    }

    public static Path walkFileTree(Path path, Set set, int i, FileVisitor fileVisitor) throws IOException {
        FileVisitResult visitFile;
        FileTreeWalker fileTreeWalker = new FileTreeWalker(set, i);
        try {
            FileTreeWalker.Event walk = fileTreeWalker.walk(path);
            do {
                int i2 = 3.$SwitchMap$java$nio$file$FileTreeWalker$EventType[walk.type().ordinal()];
                if (i2 == 1) {
                    IOException ioeException = walk.ioeException();
                    visitFile = ioeException == null ? fileVisitor.visitFile(walk.file(), walk.attributes()) : fileVisitor.visitFileFailed(walk.file(), ioeException);
                } else if (i2 == 2) {
                    visitFile = fileVisitor.preVisitDirectory(walk.file(), walk.attributes());
                    if (visitFile == FileVisitResult.SKIP_SUBTREE || visitFile == FileVisitResult.SKIP_SIBLINGS) {
                        fileTreeWalker.pop();
                    }
                } else if (i2 == 3) {
                    visitFile = fileVisitor.postVisitDirectory(walk.file(), walk.ioeException());
                    if (visitFile == FileVisitResult.SKIP_SIBLINGS) {
                        visitFile = FileVisitResult.CONTINUE;
                    }
                } else {
                    throw new AssertionError("Should not get here");
                }
                visitFile.getClass();
                if (visitFile != FileVisitResult.CONTINUE) {
                    if (visitFile == FileVisitResult.TERMINATE) {
                        break;
                    }
                    if (visitFile == FileVisitResult.SKIP_SIBLINGS) {
                        fileTreeWalker.skipRemainingSiblings();
                    }
                    walk = fileTreeWalker.next();
                } else {
                    walk = fileTreeWalker.next();
                }
            } while (walk != null);
            fileTreeWalker.close();
            return path;
        } catch (Throwable th) {
            try {
                fileTreeWalker.close();
            } catch (Throwable th2) {
                Files$$ExternalSyntheticBackport0.m(th, th2);
            }
            throw th;
        }
    }

    static /* synthetic */ class 3 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$FileTreeWalker$EventType;

        static {
            int[] iArr = new int[FileTreeWalker.EventType.values().length];
            $SwitchMap$java$nio$file$FileTreeWalker$EventType = iArr;
            try {
                iArr[FileTreeWalker.EventType.ENTRY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$nio$file$FileTreeWalker$EventType[FileTreeWalker.EventType.START_DIRECTORY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$nio$file$FileTreeWalker$EventType[FileTreeWalker.EventType.END_DIRECTORY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static Path walkFileTree(Path path, FileVisitor fileVisitor) throws IOException {
        return walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
    }

    public static BufferedReader newBufferedReader(Path path, Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(newInputStream(path, new OpenOption[0]), charset.newDecoder()));
    }

    public static BufferedReader newBufferedReader(Path path) throws IOException {
        return newBufferedReader(path, UTF_8.INSTANCE);
    }

    public static BufferedWriter newBufferedWriter(Path path, Charset charset, OpenOption... openOptionArr) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(newOutputStream(path, openOptionArr), charset.newEncoder()));
    }

    public static BufferedWriter newBufferedWriter(Path path, OpenOption... openOptionArr) throws IOException {
        return newBufferedWriter(path, UTF_8.INSTANCE, openOptionArr);
    }

    public static long copy(InputStream inputStream, Path path, CopyOption... copyOptionArr) throws IOException {
        inputStream.getClass();
        int length = copyOptionArr.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            CopyOption copyOption = copyOptionArr[i];
            if (copyOption != StandardCopyOption.REPLACE_EXISTING) {
                if (copyOption == null) {
                    throw new NullPointerException("options contains 'null'");
                }
                throw new UnsupportedOperationException(copyOption + " not supported");
            }
            i++;
            z = true;
        }
        if (z) {
            try {
                deleteIfExists(path);
                e = null;
            } catch (SecurityException e) {
                e = e;
            }
        } else {
            e = null;
        }
        try {
            OutputStream newOutputStream = newOutputStream(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            try {
                long transferTo = inputStream.transferTo(newOutputStream);
                if (newOutputStream != null) {
                    newOutputStream.close();
                }
                return transferTo;
            } catch (Throwable th) {
                if (newOutputStream != null) {
                    try {
                        newOutputStream.close();
                    } catch (Throwable th2) {
                        Files$$ExternalSyntheticBackport0.m(th, th2);
                    }
                }
                throw th;
            }
        } catch (FileAlreadyExistsException e2) {
            if (e != null) {
                throw e;
            }
            throw e2;
        }
    }

    public static long copy(Path path, OutputStream outputStream) throws IOException {
        outputStream.getClass();
        InputStream newInputStream = newInputStream(path, new OpenOption[0]);
        try {
            long transferTo = newInputStream.transferTo(outputStream);
            if (newInputStream != null) {
                newInputStream.close();
            }
            return transferTo;
        } catch (Throwable th) {
            if (newInputStream != null) {
                try {
                    newInputStream.close();
                } catch (Throwable th2) {
                    Files$$ExternalSyntheticBackport0.m(th, th2);
                }
            }
            throw th;
        }
    }

    private static byte[] read(InputStream inputStream, int i) throws IOException {
        int read;
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (true) {
            int read2 = inputStream.read(bArr, i2, i - i2);
            if (read2 > 0) {
                i2 += read2;
            } else {
                if (read2 < 0 || (read = inputStream.read()) < 0) {
                    break;
                }
                if (i <= 2147483639 - i) {
                    i = Math.max(i << 1, 8192);
                } else {
                    if (i == 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    i = 2147483639;
                }
                bArr = Arrays.copyOf(bArr, i);
                bArr[i2] = (byte) read;
                i2++;
            }
        }
        return i == i2 ? bArr : Arrays.copyOf(bArr, i2);
    }

    public static byte[] readAllBytes(Path path) throws IOException {
        SeekableByteChannel newByteChannel = newByteChannel(path, new OpenOption[0]);
        try {
            InputStream newInputStream = Channels.newInputStream(newByteChannel);
            try {
                long size = newByteChannel.size();
                if (size > 2147483639) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                byte[] read = read(newInputStream, (int) size);
                if (newInputStream != null) {
                    newInputStream.close();
                }
                if (newByteChannel != null) {
                    newByteChannel.close();
                }
                return read;
            } finally {
            }
        } catch (Throwable th) {
            if (newByteChannel != null) {
                try {
                    newByteChannel.close();
                } catch (Throwable th2) {
                    Files$$ExternalSyntheticBackport0.m(th, th2);
                }
            }
            throw th;
        }
    }

    public static String readString(Path path) throws IOException {
        return readString(path, UTF_8.INSTANCE);
    }

    public static String readString(Path path, Charset charset) throws IOException {
        path.getClass();
        charset.getClass();
        return new String(readAllBytes(path), charset);
    }

    public static List readAllLines(Path path, Charset charset) throws IOException {
        BufferedReader newBufferedReader = newBufferedReader(path, charset);
        try {
            ArrayList arrayList = new ArrayList();
            while (true) {
                String readLine = newBufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                arrayList.add(readLine);
            }
            if (newBufferedReader != null) {
                newBufferedReader.close();
            }
            return arrayList;
        } catch (Throwable th) {
            if (newBufferedReader != null) {
                try {
                    newBufferedReader.close();
                } catch (Throwable th2) {
                    Files$$ExternalSyntheticBackport0.m(th, th2);
                }
            }
            throw th;
        }
    }

    public static List readAllLines(Path path) throws IOException {
        return readAllLines(path, UTF_8.INSTANCE);
    }

    public static Path write(Path path, byte[] bArr, OpenOption... openOptionArr) throws IOException {
        bArr.getClass();
        OutputStream newOutputStream = newOutputStream(path, openOptionArr);
        try {
            int length = bArr.length;
            int i = length;
            while (i > 0) {
                int min = Math.min(i, 8192);
                newOutputStream.write(bArr, length - i, min);
                i -= min;
            }
            if (newOutputStream != null) {
                newOutputStream.close();
            }
            return path;
        } catch (Throwable th) {
            if (newOutputStream != null) {
                try {
                    newOutputStream.close();
                } catch (Throwable th2) {
                    Files$$ExternalSyntheticBackport0.m(th, th2);
                }
            }
            throw th;
        }
    }

    public static Path write(Path path, Iterable iterable, Charset charset, OpenOption... openOptionArr) throws IOException {
        iterable.getClass();
        CharsetEncoder newEncoder = charset.newEncoder();
        OutputStream newOutputStream = newOutputStream(path, openOptionArr);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(newOutputStream, newEncoder));
            try {
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    bufferedWriter.append((CharSequence) it.next());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                if (newOutputStream != null) {
                    newOutputStream.close();
                }
                return path;
            } finally {
            }
        } catch (Throwable th) {
            if (newOutputStream != null) {
                try {
                    newOutputStream.close();
                } catch (Throwable th2) {
                    Files$$ExternalSyntheticBackport0.m(th, th2);
                }
            }
            throw th;
        }
    }

    public static Path write(Path path, Iterable iterable, OpenOption... openOptionArr) throws IOException {
        return write(path, iterable, UTF_8.INSTANCE, openOptionArr);
    }

    public static Path writeString(Path path, CharSequence charSequence, OpenOption... openOptionArr) throws IOException {
        return writeString(path, charSequence, UTF_8.INSTANCE, openOptionArr);
    }

    public static Path writeString(Path path, CharSequence charSequence, Charset charset, OpenOption... openOptionArr) throws IOException {
        path.getClass();
        charSequence.getClass();
        charset.getClass();
        write(path, String.valueOf(charSequence).getBytes(charset), openOptionArr);
        return path;
    }

    public static Stream list(Path path) throws IOException {
        DirectoryStream newDirectoryStream = newDirectoryStream(path);
        try {
            return (Stream) StreamSupport.stream(Spliterators.spliteratorUnknownSize(new 2(newDirectoryStream.iterator()), 1), false).onClose(asUncheckedRunnable(newDirectoryStream));
        } catch (RuntimeException | Error e) {
            try {
                newDirectoryStream.close();
            } catch (IOException e2) {
                try {
                    Files$$ExternalSyntheticBackport0.m(e, e2);
                } catch (Throwable unused) {
                }
            }
            throw e;
        }
    }

    class 2 implements Iterator {
        final /* synthetic */ Iterator val$delegate;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        public /* synthetic */ void remove() {
            Iterator.-CC.$default$remove(this);
        }

        2(Iterator it) {
            this.val$delegate = it;
        }

        public boolean hasNext() {
            try {
                return this.val$delegate.hasNext();
            } catch (DirectoryIteratorException e) {
                throw new UncheckedIOException(e.getCause());
            }
        }

        public Path next() {
            try {
                return (Path) this.val$delegate.next();
            } catch (DirectoryIteratorException e) {
                throw new UncheckedIOException(e.getCause());
            }
        }
    }

    public static Stream walk(Path path, int i, FileVisitOption... fileVisitOptionArr) throws IOException {
        FileTreeIterator fileTreeIterator = new FileTreeIterator(path, i, fileVisitOptionArr);
        try {
            return ((Stream) StreamSupport.stream(Spliterators.spliteratorUnknownSize(fileTreeIterator, 1), false).onClose(new Files$$ExternalSyntheticLambda4(fileTreeIterator))).map(new Files$$ExternalSyntheticLambda7());
        } catch (RuntimeException | Error e) {
            fileTreeIterator.close();
            throw e;
        }
    }

    static /* synthetic */ Path lambda$walk$1(FileTreeWalker.Event event) {
        return event.file();
    }

    public static Stream walk(Path path, FileVisitOption... fileVisitOptionArr) throws IOException {
        return walk(path, Integer.MAX_VALUE, fileVisitOptionArr);
    }

    public static Stream find(Path path, int i, BiPredicate biPredicate, FileVisitOption... fileVisitOptionArr) throws IOException {
        FileTreeIterator fileTreeIterator = new FileTreeIterator(path, i, fileVisitOptionArr);
        try {
            return ((Stream) StreamSupport.stream(Spliterators.spliteratorUnknownSize(fileTreeIterator, 1), false).onClose(new Files$$ExternalSyntheticLambda4(fileTreeIterator))).filter(new Files$$ExternalSyntheticLambda5(biPredicate)).map(new Files$$ExternalSyntheticLambda6());
        } catch (Error | RuntimeException e) {
            fileTreeIterator.close();
            throw e;
        }
    }

    static /* synthetic */ boolean lambda$find$2(BiPredicate biPredicate, FileTreeWalker.Event event) {
        return biPredicate.test(event.file(), event.attributes());
    }

    static /* synthetic */ Path lambda$find$3(FileTreeWalker.Event event) {
        return event.file();
    }

    public static Stream lines(Path path, Charset charset) throws IOException {
        if (path.getFileSystem() == FileSystems.getDefault() && FileChannelLinesSpliterator.SUPPORTED_CHARSET_NAMES.contains(charset.name())) {
            FileChannel open = FileChannel.open(path, StandardOpenOption.READ);
            Stream createFileChannelLinesStream = createFileChannelLinesStream(open, charset);
            if (createFileChannelLinesStream != null) {
                return createFileChannelLinesStream;
            }
            open.close();
        }
        return createBufferedReaderLinesStream(newBufferedReader(path, charset));
    }

    private static Stream createFileChannelLinesStream(FileChannel fileChannel, Charset charset) throws IOException {
        try {
            long size = fileChannel.size();
            if (size <= 0 || size > 2147483647L) {
                return null;
            }
            return (Stream) StreamSupport.stream(new FileChannelLinesSpliterator(fileChannel, charset, 0, (int) size), false).onClose(asUncheckedRunnable(fileChannel));
        } catch (RuntimeException | IOException | Error e) {
            try {
                fileChannel.close();
            } catch (IOException e2) {
                try {
                    Files$$ExternalSyntheticBackport0.m(e, e2);
                } catch (Throwable unused) {
                }
            }
            throw e;
        }
    }

    private static Stream createBufferedReaderLinesStream(BufferedReader bufferedReader) {
        try {
            return (Stream) bufferedReader.lines().onClose(asUncheckedRunnable(bufferedReader));
        } catch (Error | RuntimeException e) {
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                try {
                    Files$$ExternalSyntheticBackport0.m(e, e2);
                } catch (Throwable unused) {
                }
            }
            throw e;
        }
    }

    public static Stream lines(Path path) throws IOException {
        return lines(path, StandardCharsets.UTF_8);
    }
}
