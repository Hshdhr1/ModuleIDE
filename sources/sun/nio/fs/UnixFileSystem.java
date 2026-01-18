package sun.nio.fs;

import java.io.IOException;
import java.lang.Iterable;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import sun.security.action.GetPropertyAction;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class UnixFileSystem extends FileSystem {
    private static final String GLOB_SYNTAX = "glob";
    private static final String REGEX_SYNTAX = "regex";
    private final byte[] defaultDirectory;
    private final boolean needToResolveAgainstDefaultDirectory;
    private final UnixFileSystemProvider provider;
    private final UnixPath rootDirectory;

    static /* bridge */ /* synthetic */ UnixPath -$$Nest$fgetrootDirectory(UnixFileSystem unixFileSystem) {
        return unixFileSystem.rootDirectory;
    }

    void copyNonPosixAttributes(int i, int i2) {
    }

    abstract FileStore getFileStore(UnixMountEntry unixMountEntry) throws IOException;

    abstract Iterable getMountEntries();

    public final boolean isOpen() {
        return true;
    }

    public final boolean isReadOnly() {
        return false;
    }

    boolean isSolaris() {
        return false;
    }

    String normalizeJavaPath(String str) {
        return str;
    }

    char[] normalizeNativePath(char[] cArr) {
        return cArr;
    }

    UnixFileSystem(UnixFileSystemProvider unixFileSystemProvider, String str) {
        this.provider = unixFileSystemProvider;
        byte[] bytes = Util.toBytes(UnixPath.normalizeAndCheck(str));
        this.defaultDirectory = bytes;
        boolean z = false;
        if (bytes[0] != 47) {
            throw new RuntimeException("default directory must be absolute");
        }
        String privilegedGetProperty = GetPropertyAction.privilegedGetProperty("sun.nio.fs.chdirAllowed", "false");
        if (privilegedGetProperty.isEmpty() ? true : Boolean.parseBoolean(privilegedGetProperty)) {
            this.needToResolveAgainstDefaultDirectory = true;
        } else {
            byte[] bArr = UnixNativeDispatcher.getcwd();
            boolean z2 = bArr.length == bytes.length;
            if (z2) {
                for (int i = 0; i < bArr.length; i++) {
                    if (bArr[i] != this.defaultDirectory[i]) {
                        break;
                    }
                }
                z = z2;
                this.needToResolveAgainstDefaultDirectory = !z;
            } else {
                z = z2;
                this.needToResolveAgainstDefaultDirectory = !z;
            }
        }
        this.rootDirectory = new UnixPath(this, "/");
    }

    byte[] defaultDirectory() {
        return this.defaultDirectory;
    }

    boolean needToResolveAgainstDefaultDirectory() {
        return this.needToResolveAgainstDefaultDirectory;
    }

    UnixPath rootDirectory() {
        return this.rootDirectory;
    }

    static List standardFileAttributeViews() {
        return Arrays.asList(new String[]{"basic", "posix", "unix", "owner"});
    }

    public final FileSystemProvider provider() {
        return this.provider;
    }

    public final String getSeparator() {
        return "/";
    }

    public final void close() throws IOException {
        throw new UnsupportedOperationException();
    }

    public final Iterable getRootDirectories() {
        return new 1(Collections.unmodifiableList(Arrays.asList(new Path[]{this.rootDirectory})));
    }

    class 1 implements Iterable {
        final /* synthetic */ List val$allowedList;

        public /* synthetic */ void forEach(Consumer consumer) {
            Iterable.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Iterable.-CC.$default$spliterator(this);
        }

        1(List list) {
            this.val$allowedList = list;
        }

        public Iterator iterator() {
            try {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkRead(UnixFileSystem.-$$Nest$fgetrootDirectory(UnixFileSystem.this).toString());
                }
                return this.val$allowedList.iterator();
            } catch (SecurityException unused) {
                return Collections.EMPTY_LIST.iterator();
            }
        }
    }

    private class FileStoreIterator implements Iterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Iterator entries;
        private FileStore next;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        FileStoreIterator() {
            this.entries = UnixFileSystem.this.getMountEntries().iterator();
        }

        private FileStore readNext() {
            while (this.entries.hasNext()) {
                UnixMountEntry unixMountEntry = (UnixMountEntry) this.entries.next();
                if (!unixMountEntry.isIgnored()) {
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            securityManager.checkRead(Util.toString(unixMountEntry.dir()));
                        } catch (SecurityException | IOException unused) {
                        }
                    }
                    return UnixFileSystem.this.getFileStore(unixMountEntry);
                }
            }
            return null;
        }

        public synchronized boolean hasNext() {
            if (this.next != null) {
                return true;
            }
            FileStore readNext = readNext();
            this.next = readNext;
            return readNext != null;
        }

        public synchronized FileStore next() {
            FileStore fileStore;
            if (this.next == null) {
                this.next = readNext();
            }
            fileStore = this.next;
            if (fileStore == null) {
                throw new NoSuchElementException();
            }
            this.next = null;
            return fileStore;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public final Iterable getFileStores() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            try {
                securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
            } catch (SecurityException unused) {
                return Collections.EMPTY_LIST;
            }
        }
        return new 2();
    }

    class 2 implements Iterable {
        public /* synthetic */ void forEach(Consumer consumer) {
            Iterable.-CC.$default$forEach(this, consumer);
        }

        public /* synthetic */ Spliterator spliterator() {
            return Iterable.-CC.$default$spliterator(this);
        }

        2() {
        }

        public Iterator iterator() {
            return UnixFileSystem.this.new FileStoreIterator();
        }
    }

    public final Path getPath(String str, String... strArr) {
        if (strArr.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            for (String str2 : strArr) {
                if (!str2.isEmpty()) {
                    if (sb.length() > 0) {
                        sb.append('/');
                    }
                    sb.append(str2);
                }
            }
            str = sb.toString();
        }
        return new UnixPath(this, str);
    }

    public PathMatcher getPathMatcher(String str) {
        int indexOf = str.indexOf(58);
        if (indexOf <= 0 || indexOf == str.length()) {
            throw new IllegalArgumentException();
        }
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf + 1);
        if (substring.equalsIgnoreCase("glob")) {
            substring2 = Globs.toUnixRegexPattern(substring2);
        } else if (!substring.equalsIgnoreCase("regex")) {
            throw new UnsupportedOperationException("Syntax '" + substring + "' not recognized");
        }
        return new 3(compilePathMatchPattern(substring2));
    }

    class 3 implements PathMatcher {
        final /* synthetic */ Pattern val$pattern;

        3(Pattern pattern) {
            this.val$pattern = pattern;
        }

        public boolean matches(Path path) {
            return this.val$pattern.matcher(path.toString()).matches();
        }
    }

    public final UserPrincipalLookupService getUserPrincipalLookupService() {
        return LookupService.instance;
    }

    private static class LookupService {
        static final UserPrincipalLookupService instance = new 1();

        private LookupService() {
        }

        class 1 extends UserPrincipalLookupService {
            1() {
            }

            public UserPrincipal lookupPrincipalByName(String str) throws IOException {
                return UnixUserPrincipals.lookupUser(str);
            }

            public GroupPrincipal lookupPrincipalByGroupName(String str) throws IOException {
                return UnixUserPrincipals.lookupGroup(str);
            }
        }
    }

    Pattern compilePathMatchPattern(String str) {
        return Pattern.compile(str);
    }
}
