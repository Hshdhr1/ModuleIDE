package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface WatchService extends Closeable {
    void close() throws IOException;

    WatchKey poll();

    WatchKey poll(long j, TimeUnit timeUnit) throws InterruptedException;

    WatchKey take() throws InterruptedException;
}
