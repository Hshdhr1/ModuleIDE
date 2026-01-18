package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class AsynchronousFileChannel implements AsynchronousChannel {
    private static final FileAttribute[] NO_ATTRIBUTES = new FileAttribute[0];

    public abstract void force(boolean z) throws IOException;

    public abstract Future lock(long j, long j2, boolean z);

    public abstract void lock(long j, long j2, boolean z, Object obj, CompletionHandler completionHandler);

    public abstract Future read(ByteBuffer byteBuffer, long j);

    public abstract void read(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler);

    public abstract long size() throws IOException;

    public abstract AsynchronousFileChannel truncate(long j) throws IOException;

    public abstract FileLock tryLock(long j, long j2, boolean z) throws IOException;

    public abstract Future write(ByteBuffer byteBuffer, long j);

    public abstract void write(ByteBuffer byteBuffer, long j, Object obj, CompletionHandler completionHandler);

    protected AsynchronousFileChannel() {
    }

    public static AsynchronousFileChannel open(Path path, Set set, ExecutorService executorService, FileAttribute... fileAttributeArr) throws IOException {
        return path.getFileSystem().provider().newAsynchronousFileChannel(path, set, executorService, fileAttributeArr);
    }

    public static AsynchronousFileChannel open(Path path, OpenOption... openOptionArr) throws IOException {
        Set set;
        if (openOptionArr.length == 0) {
            set = Collections.EMPTY_SET;
        } else {
            Set hashSet = new HashSet();
            Collections.addAll(hashSet, openOptionArr);
            set = hashSet;
        }
        return open(path, set, null, NO_ATTRIBUTES);
    }

    public final void lock(Object obj, CompletionHandler completionHandler) {
        lock(0L, Long.MAX_VALUE, false, obj, completionHandler);
    }

    public final Future lock() {
        return lock(0L, Long.MAX_VALUE, false);
    }

    public final FileLock tryLock() throws IOException {
        return tryLock(0L, Long.MAX_VALUE, false);
    }
}
