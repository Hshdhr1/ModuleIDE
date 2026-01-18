package okhttp3.internal.cache;

import java.io.IOException;
import okio.Sink;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
public interface CacheRequest {
    void abort();

    Sink body() throws IOException;
}
