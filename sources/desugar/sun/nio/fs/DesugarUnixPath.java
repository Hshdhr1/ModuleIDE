package desugar.sun.nio.fs;

import java.io.File;
import java.io.IOException;
import java.lang.Iterable;
import java.net.URI;
import java.nio.file.AccessMode;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarUnixPath implements Path {
    private static final Pattern PATH_COMPONENT_SPLITERATOR = Pattern.compile("/+");
    private static final String SEPARATOR = "/";
    private volatile byte[] byteArrayValue;
    private final List fileNames;
    private final FileSystem fileSystem;
    private final boolean isAbsolutePath;
    private final String pathText;
    private final String rootDir;
    private final String userDir;

    public /* synthetic */ void forEach(Consumer consumer) {
        Iterable.-CC.$default$forEach(this, consumer);
    }

    public /* synthetic */ WatchKey register(WatchService watchService, WatchEvent.Kind... kindArr) {
        return Path.-CC.$default$register(this, watchService, kindArr);
    }

    public /* synthetic */ Path resolve(String str) {
        return Path.-CC.$default$resolve(this, str);
    }

    public /* synthetic */ Spliterator spliterator() {
        return Iterable.-CC.$default$spliterator(this);
    }

    public DesugarUnixPath(FileSystem fileSystem, String rawPath, String userDir, String rootDir) {
        this(fileSystem, rawPath.startsWith("/"), getFileNames(rawPath), userDir, rootDir);
    }

    private DesugarUnixPath(FileSystem fileSystem, boolean isAbsolutePath, List fileNames, String userDir, String rootDir) {
        this.fileSystem = fileSystem;
        this.isAbsolutePath = isAbsolutePath;
        this.fileNames = fileNames;
        this.pathText = getPathText(isAbsolutePath, fileNames);
        this.userDir = userDir;
        this.rootDir = rootDir;
    }

    private static List getFileNames(String rawTextPath) {
        if (rawTextPath.isEmpty()) {
            return Collections.singletonList("");
        }
        return (List) Arrays.stream(PATH_COMPONENT_SPLITERATOR.split(rawTextPath)).filter(new DesugarUnixPath$$ExternalSyntheticLambda1()).collect(Collectors.toUnmodifiableList());
    }

    static /* synthetic */ boolean lambda$getFileNames$0(String str) {
        return !str.isEmpty();
    }

    private static String getPathText(boolean isAbsolutePath, Collection fileNames) {
        StringBuilder sb = new StringBuilder();
        sb.append(isAbsolutePath ? "/" : "");
        sb.append(DesugarUnixPath$$ExternalSyntheticBackport0.m("/", fileNames));
        return sb.toString();
    }

    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    public boolean isAbsolute() {
        return this.isAbsolutePath;
    }

    public DesugarUnixPath getRoot() {
        if (!isAbsolute()) {
            return null;
        }
        FileSystem fileSystem = getFileSystem();
        String str = this.rootDir;
        return new DesugarUnixPath(fileSystem, str, this.userDir, str);
    }

    DesugarUnixPath getUserDir() {
        FileSystem fileSystem = getFileSystem();
        String str = this.userDir;
        return new DesugarUnixPath(fileSystem, str, str, this.rootDir);
    }

    public DesugarUnixPath getFileName() {
        if (this.fileNames.isEmpty()) {
            if (this.isAbsolutePath) {
                return null;
            }
            return this;
        }
        return new DesugarUnixPath(this.fileSystem, (String) this.fileNames.get(getNameCount() - 1), this.userDir, this.rootDir);
    }

    public DesugarUnixPath getParent() {
        int nameCount = getNameCount();
        if (nameCount == 0) {
            return null;
        }
        if (nameCount == 1 && !this.isAbsolutePath) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (this.isAbsolutePath) {
            sb.append("/");
        }
        sb.append(subPathName(0, nameCount - 1));
        return new DesugarUnixPath(this.fileSystem, sb.toString(), this.userDir, this.rootDir);
    }

    public int getNameCount() {
        return this.fileNames.size();
    }

    public DesugarUnixPath getName(int index) {
        if (index < 0 || index >= getNameCount()) {
            throw new IllegalArgumentException(String.format("Requested name for index (%d) is out of bound in \n%s.", Integer.valueOf(index), this));
        }
        return new DesugarUnixPath(this.fileSystem, (String) this.fileNames.get(index), this.userDir, this.rootDir);
    }

    public DesugarUnixPath subpath(int beginIndex, int endIndex) {
        return new DesugarUnixPath(this.fileSystem, subPathName(beginIndex, endIndex), this.userDir, this.rootDir);
    }

    private String subPathName(int beginIndex, int endIndex) {
        return DesugarUnixPath$$ExternalSyntheticBackport0.m("/", this.fileNames.subList(beginIndex, endIndex));
    }

    public boolean startsWith(String other) {
        return startsWith(new DesugarUnixPath(this.fileSystem, other, this.userDir, this.rootDir));
    }

    public boolean startsWith(Path other) {
        int nameCount;
        other.getClass();
        if (!(other instanceof DesugarUnixPath) || isAbsolute() != other.isAbsolute() || getNameCount() < (nameCount = other.getNameCount())) {
            return false;
        }
        for (int i = 0; i < nameCount; i++) {
            if (!getName(i).equals(other.getName(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(String other) {
        return endsWith(new DesugarUnixPath(this.fileSystem, other, this.userDir, this.rootDir));
    }

    public boolean endsWith(Path other) {
        other.getClass();
        if (!(other instanceof DesugarUnixPath)) {
            return false;
        }
        if (other.isAbsolute()) {
            return equals(other);
        }
        int nameCount = other.getNameCount();
        if (getNameCount() < nameCount) {
            return false;
        }
        int nameCount2 = getNameCount();
        for (int i = nameCount - 1; i >= 0; i--) {
            if (!getName((i - nameCount) + nameCount2).equals(other.getName(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object other) {
        return (other instanceof DesugarUnixPath) && compareTo((Path) other) == 0;
    }

    public int hashCode() {
        return this.pathText.hashCode();
    }

    public int compareTo(Path other) {
        return this.pathText.compareTo(((DesugarUnixPath) other).pathText);
    }

    public DesugarUnixPath normalize() {
        ArrayDeque arrayDeque = new ArrayDeque();
        for (String str : this.fileNames) {
            str.hashCode();
            if (!str.equals(".")) {
                if (str.equals("..")) {
                    arrayDeque.removeLast();
                } else {
                    arrayDeque.add(str);
                }
            }
        }
        return new DesugarUnixPath(this.fileSystem, getPathText(this.isAbsolutePath, arrayDeque), this.userDir, this.rootDir);
    }

    public DesugarUnixPath resolve(Path other) {
        if (!(other instanceof DesugarUnixPath)) {
            throw new IllegalArgumentException(String.format("Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s (%s).", other, other.getFileSystem()));
        }
        if (other.isAbsolute()) {
            return (DesugarUnixPath) other;
        }
        return new DesugarUnixPath(this.fileSystem, this.pathText + "/" + other, this.userDir, this.rootDir);
    }

    public DesugarUnixPath resolveSibling(Path other) {
        other.getClass();
        if (!(other instanceof DesugarUnixPath)) {
            throw new IllegalArgumentException(String.format("Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s (%s).", other, other.getFileSystem()));
        }
        DesugarUnixPath parent = getParent();
        return parent == null ? (DesugarUnixPath) other : parent.resolve(other);
    }

    public DesugarUnixPath resolveSibling(String other) {
        return resolveSibling((Path) new DesugarUnixPath(this.fileSystem, other, this.userDir, this.rootDir));
    }

    public DesugarUnixPath relativize(Path other) {
        int i = 0;
        if (!(other instanceof DesugarUnixPath)) {
            throw new IllegalArgumentException(String.format("Expected to resolve paths on the same file system as DesugarUnixPath, but gets %s (%s).", other, other.getFileSystem()));
        }
        if (isAbsolute() != other.isAbsolute()) {
            throw new IllegalArgumentException("'other' is different type of Path in absolute property.");
        }
        List list = ((DesugarUnixPath) other).fileNames;
        int size = this.fileNames.size();
        int size2 = list.size();
        while (i < size && i < size2 && ((String) this.fileNames.get(i)).equals(list.get(i))) {
            i++;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = i; i2 < size; i2++) {
            arrayList.add("..");
        }
        while (i < size2) {
            arrayList.add((String) list.get(i));
            i++;
        }
        return new DesugarUnixPath(this.fileSystem, false, arrayList, this.userDir, this.rootDir);
    }

    public File toFile() {
        return new File(toString());
    }

    public URI toUri() {
        return DesugarUnixUriUtils.toUri(this);
    }

    public DesugarUnixPath toAbsolutePath() {
        return isAbsolute() ? this : getUserDir().resolve((Path) this);
    }

    public DesugarUnixPath toRealPath(LinkOption... options) throws IOException {
        getFileSystem().provider().checkAccess(this, AccessMode.READ);
        if (Arrays.asList(options).contains(LinkOption.NOFOLLOW_LINKS)) {
            return toAbsolutePath();
        }
        return new DesugarUnixPath(this.fileSystem, toFile().getCanonicalPath(), this.userDir, this.rootDir);
    }

    public String toString() {
        return this.pathText;
    }

    byte[] asByteArray() {
        if (this.byteArrayValue == null) {
            this.byteArrayValue = this.pathText.getBytes(DesugarUtil.jnuEncoding());
        }
        return this.byteArrayValue;
    }

    public Iterator iterator() {
        return Path.-CC.$default$iterator(this);
    }

    public WatchKey register(WatchService watcher, WatchEvent.Kind[] events, WatchEvent.Modifier... modifiers) throws IOException {
        throw new UnsupportedOperationException("Watch Service is not supported");
    }
}
