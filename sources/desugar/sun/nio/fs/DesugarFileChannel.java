package desugar.sun.nio.fs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarFileChannel {
    public static FileChannel openEmulatedFileChannel(Path path, Set openOptions, FileAttribute... attrs) throws IOException {
        validateOpenOptions(path, openOptions);
        RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), getFileAccessModeText(openOptions));
        if (openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING) && openOptions.contains(StandardOpenOption.WRITE)) {
            randomAccessFile.setLength(0L);
        }
        if (!openOptions.contains(StandardOpenOption.APPEND) && !openOptions.contains(StandardOpenOption.DELETE_ON_CLOSE)) {
            return randomAccessFile.getChannel();
        }
        return WrappedFileChannel.withExtraOptions(randomAccessFile.getChannel(), openOptions, path);
    }

    private static void validateOpenOptions(Path path, Set openOptions) throws IOException {
        Iterator it = openOptions.iterator();
        while (it.hasNext()) {
            ((OpenOption) it.next()).getClass();
        }
        if (path.toFile().exists()) {
            if (openOptions.contains(StandardOpenOption.CREATE_NEW) && openOptions.contains(StandardOpenOption.WRITE)) {
                throw new FileAlreadyExistsException(path.toString());
            }
        } else if (!openOptions.contains(StandardOpenOption.CREATE) && !openOptions.contains(StandardOpenOption.CREATE_NEW)) {
            throw new NoSuchFileException(path.toString());
        }
        if (openOptions.contains(StandardOpenOption.READ) && openOptions.contains(StandardOpenOption.APPEND)) {
            throw new IllegalArgumentException("READ + APPEND not allowed");
        }
        if (openOptions.contains(StandardOpenOption.APPEND) && openOptions.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
            throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
        }
    }

    private static String getFileAccessModeText(Set options) {
        if (!options.contains(StandardOpenOption.WRITE) && !options.contains(StandardOpenOption.APPEND)) {
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

    public static FileChannel wrap(FileChannel raw) {
        return WrappedFileChannel.wrap(raw);
    }

    static class WrappedFileChannel extends FileChannel implements SeekableByteChannel {
        final boolean appendMode;
        final FileChannel delegate;
        final boolean deleteOnClose;
        final Path path;

        public static FileChannel wrap(FileChannel channel) {
            return channel instanceof WrappedFileChannel ? channel : new WrappedFileChannel(channel, false, false, null);
        }

        public static FileChannel withExtraOptions(FileChannel channel, Set options, Path path) {
            if (channel instanceof WrappedFileChannel) {
                channel = ((WrappedFileChannel) channel).delegate;
            }
            return new WrappedFileChannel(channel, options.contains(StandardOpenOption.DELETE_ON_CLOSE), options.contains(StandardOpenOption.APPEND), path);
        }

        private WrappedFileChannel(FileChannel delegate, boolean deleteOnClose, boolean appendMode, Path path) {
            this.delegate = delegate;
            this.deleteOnClose = deleteOnClose;
            this.appendMode = appendMode;
            this.path = deleteOnClose ? path : null;
        }

        public int read(ByteBuffer dst) throws IOException {
            return this.delegate.read(dst);
        }

        public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
            return this.delegate.read(dsts, offset, length);
        }

        public int write(ByteBuffer src) throws IOException {
            if (this.appendMode) {
                return this.delegate.write(src, size());
            }
            return this.delegate.write(src);
        }

        public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
            return this.delegate.write(srcs, offset, length);
        }

        public long position() throws IOException {
            return this.delegate.position();
        }

        public FileChannel position(long newPosition) throws IOException {
            return wrap(this.delegate.position(newPosition));
        }

        public long size() throws IOException {
            return this.delegate.size();
        }

        public FileChannel truncate(long size) throws IOException {
            return wrap(this.delegate.truncate(size));
        }

        public void force(boolean metaData) throws IOException {
            this.delegate.force(metaData);
        }

        public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
            return this.delegate.transferTo(position, count, target);
        }

        public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
            return this.delegate.transferFrom(src, position, count);
        }

        public int read(ByteBuffer dst, long position) throws IOException {
            return this.delegate.read(dst, position);
        }

        public int write(ByteBuffer src, long position) throws IOException {
            return this.delegate.write(src, position);
        }

        public MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) throws IOException {
            return this.delegate.map(mode, position, size);
        }

        public FileLock lock(long position, long size, boolean shared) throws IOException {
            return wrapLock(this.delegate.lock(position, size, shared));
        }

        public FileLock tryLock(long position, long size, boolean shared) throws IOException {
            return wrapLock(this.delegate.tryLock(position, size, shared));
        }

        private FileLock wrapLock(FileLock lock) {
            if (lock == null) {
                return null;
            }
            return new WrappedFileChannelFileLock(lock, this);
        }

        public void implCloseChannel() throws IOException {
            this.delegate.close();
            if (this.deleteOnClose) {
                this.path.toFile().delete();
            }
        }
    }

    static class WrappedFileChannelFileLock extends FileLock {
        private final FileLock delegate;

        WrappedFileChannelFileLock(FileLock delegate, WrappedFileChannel wrappedFileChannel) {
            super(wrappedFileChannel, delegate.position(), delegate.size(), delegate.isShared());
            this.delegate = delegate;
        }

        public boolean isValid() {
            return this.delegate.isValid();
        }

        public void release() throws IOException {
            this.delegate.release();
        }
    }
}
