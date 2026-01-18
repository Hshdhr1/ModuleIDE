package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractWatchService implements WatchService {
    private volatile boolean closed;
    private final LinkedBlockingDeque pendingKeys = new LinkedBlockingDeque();
    private final WatchKey CLOSE_KEY = new 1(null, null);
    private final Object closeLock = new Object();

    abstract void implClose() throws IOException;

    abstract WatchKey register(Path path, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException;

    class 1 extends AbstractWatchKey {
        public void cancel() {
        }

        public boolean isValid() {
            return true;
        }

        1(Path path, AbstractWatchService abstractWatchService) {
            super(path, abstractWatchService);
        }
    }

    protected AbstractWatchService() {
    }

    final void enqueueKey(WatchKey watchKey) {
        this.pendingKeys.offer(watchKey);
    }

    private void checkOpen() {
        if (this.closed) {
            throw new ClosedWatchServiceException();
        }
    }

    private void checkKey(WatchKey watchKey) {
        if (watchKey == this.CLOSE_KEY) {
            enqueueKey(watchKey);
        }
        checkOpen();
    }

    public final WatchKey poll() {
        checkOpen();
        WatchKey watchKey = (WatchKey) this.pendingKeys.poll();
        checkKey(watchKey);
        return watchKey;
    }

    public final WatchKey poll(long j, TimeUnit timeUnit) throws InterruptedException {
        checkOpen();
        WatchKey watchKey = (WatchKey) this.pendingKeys.poll(j, timeUnit);
        checkKey(watchKey);
        return watchKey;
    }

    public final WatchKey take() throws InterruptedException {
        checkOpen();
        WatchKey watchKey = (WatchKey) this.pendingKeys.take();
        checkKey(watchKey);
        return watchKey;
    }

    final boolean isOpen() {
        return !this.closed;
    }

    final Object closeLock() {
        return this.closeLock;
    }

    public final void close() throws IOException {
        synchronized (this.closeLock) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            implClose();
            this.pendingKeys.clear();
            this.pendingKeys.offer(this.CLOSE_KEY);
        }
    }
}
