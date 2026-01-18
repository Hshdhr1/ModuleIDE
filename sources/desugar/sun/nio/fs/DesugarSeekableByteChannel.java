package desugar.sun.nio.fs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarSeekableByteChannel implements SeekableByteChannel {
    private final FileChannel fileChannel;
    private final Set openOptions;

    public static DesugarSeekableByteChannel create(Path path, Set openOptions) throws IOException {
        if (openOptions.contains(StandardOpenOption.READ) && openOptions.contains(StandardOpenOption.APPEND)) {
            throw new IllegalArgumentException("READ + APPEND not allowed");
        }
        if (openOptions.contains(StandardOpenOption.APPEND) && openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
            throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
        }
        if (openOptions.contains(StandardOpenOption.APPEND) && !path.toFile().exists()) {
            throw new NoSuchFileException(path.toString());
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), getFileAccessModeText(openOptions));
        if (openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
            randomAccessFile.setLength(0L);
        }
        return new DesugarSeekableByteChannel(randomAccessFile.getChannel(), openOptions);
    }

    private static String getFileAccessModeText(Set options) {
        if (!options.contains(StandardOpenOption.WRITE)) {
            return "r";
        }
        if (options.contains(StandardOpenOption.SYNC)) {
            return "rws";
        }
        if (options.contains(StandardOpenOption.DSYNC)) {
            return "rwd";
        }
        return "rw";
    }

    private DesugarSeekableByteChannel(FileChannel fileChannel, Set openOptions) {
        this.fileChannel = fileChannel;
        this.openOptions = openOptions;
    }

    public FileChannel getFileChannel() {
        return this.fileChannel;
    }

    public int read(ByteBuffer dst) throws IOException {
        return this.fileChannel.read(dst);
    }

    public int write(ByteBuffer src) throws IOException {
        if (this.openOptions.contains(StandardOpenOption.APPEND)) {
            return this.fileChannel.write(src, size());
        }
        return this.fileChannel.write(src);
    }

    public long position() throws IOException {
        return this.fileChannel.position();
    }

    public SeekableByteChannel position(long newPosition) throws IOException {
        return this.fileChannel.position(newPosition);
    }

    public long size() throws IOException {
        return this.fileChannel.size();
    }

    public SeekableByteChannel truncate(long size) throws IOException {
        return this.fileChannel.truncate(size);
    }

    public boolean isOpen() {
        return this.fileChannel.isOpen();
    }

    public void close() throws IOException {
        this.fileChannel.close();
    }
}
