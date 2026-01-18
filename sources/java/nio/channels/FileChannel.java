package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class FileChannel extends AbstractInterruptibleChannel implements SeekableByteChannel, GatheringByteChannel, ScatteringByteChannel {
    private static final FileAttribute[] NO_ATTRIBUTES = new FileAttribute[0];

    public abstract void force(boolean z) throws IOException;

    public abstract FileLock lock(long j, long j2, boolean z) throws IOException;

    public abstract MappedByteBuffer map(MapMode mapMode, long j, long j2) throws IOException;

    public abstract long position() throws IOException;

    public abstract FileChannel position(long j) throws IOException;

    public abstract int read(ByteBuffer byteBuffer) throws IOException;

    public abstract int read(ByteBuffer byteBuffer, long j) throws IOException;

    public abstract long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException;

    public abstract long size() throws IOException;

    public abstract long transferFrom(ReadableByteChannel readableByteChannel, long j, long j2) throws IOException;

    public abstract long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException;

    public abstract FileChannel truncate(long j) throws IOException;

    public abstract FileLock tryLock(long j, long j2, boolean z) throws IOException;

    public abstract int write(ByteBuffer byteBuffer) throws IOException;

    public abstract int write(ByteBuffer byteBuffer, long j) throws IOException;

    public abstract long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException;

    protected FileChannel() {
    }

    public static FileChannel open(Path path, Set set, FileAttribute... fileAttributeArr) throws IOException {
        return path.getFileSystem().provider().newFileChannel(path, set, fileAttributeArr);
    }

    public static FileChannel open(Path path, OpenOption... openOptionArr) throws IOException {
        Set set;
        if (openOptionArr.length == 0) {
            set = Collections.EMPTY_SET;
        } else {
            Set hashSet = new HashSet();
            Collections.addAll(hashSet, openOptionArr);
            set = hashSet;
        }
        return open(path, set, new FileAttribute[0]);
    }

    public final long read(ByteBuffer[] byteBufferArr) throws IOException {
        return read(byteBufferArr, 0, byteBufferArr.length);
    }

    public final long write(ByteBuffer[] byteBufferArr) throws IOException {
        return write(byteBufferArr, 0, byteBufferArr.length);
    }

    public static class MapMode {
        private final String name;
        public static final MapMode READ_ONLY = new MapMode("READ_ONLY");
        public static final MapMode READ_WRITE = new MapMode("READ_WRITE");
        public static final MapMode PRIVATE = new MapMode("PRIVATE");

        private MapMode(String str) {
            this.name = str;
        }

        public String toString() {
            return this.name;
        }
    }

    public final FileLock lock() throws IOException {
        return lock(0L, Long.MAX_VALUE, false);
    }

    public final FileLock tryLock() throws IOException {
        return tryLock(0L, Long.MAX_VALUE, false);
    }
}
