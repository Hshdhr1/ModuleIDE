package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class FileLockImpl extends FileLock {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile boolean invalid;

    FileLockImpl(FileChannel fileChannel, long j, long j2, boolean z) {
        super(fileChannel, j, j2, z);
    }

    FileLockImpl(AsynchronousFileChannel asynchronousFileChannel, long j, long j2, boolean z) {
        super(asynchronousFileChannel, j, j2, z);
    }

    public boolean isValid() {
        return !this.invalid;
    }

    void invalidate() {
        this.invalid = true;
    }

    public synchronized void release() throws IOException {
        Channel acquiredBy = acquiredBy();
        if (!acquiredBy.isOpen()) {
            throw new ClosedChannelException();
        }
        if (isValid()) {
            if (acquiredBy instanceof FileChannelImpl) {
                ((FileChannelImpl) acquiredBy).release(this);
            } else if (acquiredBy instanceof AsynchronousFileChannelImpl) {
                ((AsynchronousFileChannelImpl) acquiredBy).release(this);
            } else {
                throw new AssertionError();
            }
            invalidate();
        }
    }
}
