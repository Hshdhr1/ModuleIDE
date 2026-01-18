package sun.nio.fs;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import jdk.internal.util.StaticProperty;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class UnixFileStore extends FileStore {
    private static final Object loadLock = new Object();
    private static volatile Properties props;
    private final long dev;
    private final UnixMountEntry entry;
    private final UnixPath file;

    enum FeatureStatus {
        PRESENT,
        NOT_PRESENT,
        UNKNOWN
    }

    static /* bridge */ /* synthetic */ Properties -$$Nest$smloadProperties() {
        return loadProperties();
    }

    abstract UnixMountEntry findMountEntry() throws IOException;

    private static long devFor(UnixPath unixPath) throws IOException {
        try {
            return UnixFileAttributes.get(unixPath, true).dev();
        } catch (UnixException e) {
            e.rethrowAsIOException(unixPath);
            return 0L;
        }
    }

    UnixFileStore(UnixPath unixPath) throws IOException {
        this.file = unixPath;
        this.dev = devFor(unixPath);
        this.entry = findMountEntry();
    }

    UnixFileStore(UnixFileSystem unixFileSystem, UnixMountEntry unixMountEntry) throws IOException {
        UnixPath unixPath = new UnixPath(unixFileSystem, unixMountEntry.dir());
        this.file = unixPath;
        this.dev = unixMountEntry.dev() == 0 ? devFor(unixPath) : unixMountEntry.dev();
        this.entry = unixMountEntry;
    }

    UnixPath file() {
        return this.file;
    }

    long dev() {
        return this.dev;
    }

    UnixMountEntry entry() {
        return this.entry;
    }

    public String name() {
        return this.entry.name();
    }

    public String type() {
        return this.entry.fstype();
    }

    public boolean isReadOnly() {
        return this.entry.isReadOnly();
    }

    private UnixFileStoreAttributes readAttributes() throws IOException {
        try {
            return UnixFileStoreAttributes.get(this.file);
        } catch (UnixException e) {
            e.rethrowAsIOException(this.file);
            return null;
        }
    }

    public long getTotalSpace() throws IOException {
        UnixFileStoreAttributes readAttributes = readAttributes();
        return readAttributes.blockSize() * readAttributes.totalBlocks();
    }

    public long getUsableSpace() throws IOException {
        UnixFileStoreAttributes readAttributes = readAttributes();
        return readAttributes.blockSize() * readAttributes.availableBlocks();
    }

    public long getBlockSize() throws IOException {
        return readAttributes().blockSize();
    }

    public long getUnallocatedSpace() throws IOException {
        UnixFileStoreAttributes readAttributes = readAttributes();
        return readAttributes.blockSize() * readAttributes.freeBlocks();
    }

    public FileStoreAttributeView getFileStoreAttributeView(Class cls) {
        cls.getClass();
        return null;
    }

    public Object getAttribute(String str) throws IOException {
        if (str.equals("totalSpace")) {
            return Long.valueOf(getTotalSpace());
        }
        if (str.equals("usableSpace")) {
            return Long.valueOf(getUsableSpace());
        }
        if (str.equals("unallocatedSpace")) {
            return Long.valueOf(getUnallocatedSpace());
        }
        throw new UnsupportedOperationException("'" + str + "' not recognized");
    }

    public boolean supportsFileAttributeView(Class cls) {
        cls.getClass();
        if (cls == BasicFileAttributeView.class) {
            return true;
        }
        return (cls == PosixFileAttributeView.class || cls == FileOwnerAttributeView.class) && checkIfFeaturePresent("posix") != FeatureStatus.NOT_PRESENT;
    }

    public boolean supportsFileAttributeView(String str) {
        if (str.equals("basic") || str.equals("unix")) {
            return true;
        }
        if (str.equals("posix")) {
            return supportsFileAttributeView(PosixFileAttributeView.class);
        }
        if (str.equals("owner")) {
            return supportsFileAttributeView(FileOwnerAttributeView.class);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnixFileStore)) {
            return false;
        }
        UnixFileStore unixFileStore = (UnixFileStore) obj;
        return this.dev == unixFileStore.dev && Arrays.equals(this.entry.dir(), unixFileStore.entry.dir()) && this.entry.name().equals(unixFileStore.entry.name());
    }

    public int hashCode() {
        long j = this.dev;
        return Arrays.hashCode(this.entry.dir()) ^ ((int) (j ^ (j >>> 32)));
    }

    public String toString() {
        return Util.toString(this.entry.dir()) + " (" + this.entry.name() + ")";
    }

    FeatureStatus checkIfFeaturePresent(String str) {
        if (props == null) {
            synchronized (loadLock) {
                if (props == null) {
                    props = (Properties) AccessController.doPrivileged(new 1());
                }
            }
        }
        String property = props.getProperty(type());
        if (property != null) {
            for (String str2 : property.split("\\s")) {
                String lowerCase = str2.trim().toLowerCase();
                if (lowerCase.equals(str)) {
                    return FeatureStatus.PRESENT;
                }
                if (lowerCase.startsWith("no") && lowerCase.substring(2).equals(str)) {
                    return FeatureStatus.NOT_PRESENT;
                }
            }
        }
        return FeatureStatus.UNKNOWN;
    }

    class 1 implements PrivilegedAction {
        1() {
        }

        public Properties run() {
            return UnixFileStore.-$$Nest$smloadProperties();
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            SeekableByteChannel newByteChannel = Files.newByteChannel(Path.-CC.of(StaticProperty.javaHome() + "/lib/fstypes.properties", new String[0]), new OpenOption[0]);
            try {
                properties.load(Channels.newReader(newByteChannel, "UTF-8"));
                if (newByteChannel != null) {
                    newByteChannel.close();
                    return properties;
                }
            } finally {
            }
        } catch (IOException unused) {
        }
        return properties;
    }
}
