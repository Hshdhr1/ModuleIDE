package desugar.sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarLinuxFileSystem extends FileSystem {
    private static final String GLOB_SYNTAX = "glob";
    private static final String REGEX_SYNTAX = "regex";
    public static final String SEPARATOR = "/";
    private final DesugarLinuxFileSystemProvider provider;
    private final String rootDir;
    private final String userDir;

    public boolean isOpen() {
        return true;
    }

    public boolean isReadOnly() {
        return false;
    }

    public DesugarLinuxFileSystem(DesugarLinuxFileSystemProvider provider, String userDir, String rootDir) {
        this.provider = provider;
        this.userDir = userDir;
        this.rootDir = rootDir;
    }

    public FileSystemProvider provider() {
        return this.provider;
    }

    public void close() throws IOException {
        throw new UnsupportedOperationException();
    }

    public String getSeparator() {
        return "/";
    }

    public String getUserDir() {
        return this.userDir;
    }

    public String getRootDir() {
        return this.rootDir;
    }

    public Iterable getRootDirectories() {
        return DesugarLinuxFileSystem$$ExternalSyntheticBackport2.m(new DesugarUnixPath(this, "/", this.userDir, this.rootDir));
    }

    public Iterable getFileStores() {
        throw new UnsupportedOperationException("");
    }

    public Set supportedFileAttributeViews() {
        return DesugarLinuxFileSystem$$ExternalSyntheticBackport0.m("basic");
    }

    public DesugarUnixPath getPath(String first, String... more) {
        if (more.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(first);
            for (String str : more) {
                if (!str.isEmpty()) {
                    if (sb.length() > 0) {
                        sb.append('/');
                    }
                    sb.append(str);
                }
            }
            first = sb.toString();
        }
        return new DesugarUnixPath(this, first, this.userDir, this.rootDir);
    }

    public PathMatcher getPathMatcher(String syntaxAndPattern) {
        int indexOf = syntaxAndPattern.indexOf(58);
        if (indexOf <= 0 || indexOf == syntaxAndPattern.length()) {
            throw new IllegalArgumentException(String.format("Requested <syntax>:<pattern> spliterator(':') position(%d) is out of bound in %s", Integer.valueOf(indexOf), syntaxAndPattern));
        }
        String substring = syntaxAndPattern.substring(0, indexOf);
        String substring2 = syntaxAndPattern.substring(indexOf + 1);
        if (substring.equalsIgnoreCase("glob")) {
            substring2 = DesugarGlobs.toUnixRegexPattern(substring2);
        } else if (!substring.equalsIgnoreCase("regex")) {
            throw new UnsupportedOperationException("Syntax '" + substring + "' not recognized");
        }
        return new DesugarLinuxFileSystem$$ExternalSyntheticLambda4(Pattern.compile(substring2));
    }

    static /* synthetic */ boolean lambda$getPathMatcher$0(Pattern pattern, Path path) {
        return pattern.matcher(path.toString()).matches();
    }

    public UserPrincipalLookupService getUserPrincipalLookupService() {
        throw new UnsupportedOperationException();
    }

    public WatchService newWatchService() throws IOException {
        throw new UnsupportedOperationException();
    }
}
