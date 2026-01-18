package android.net.http;

import java.io.File;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public final class HttpResponseCache extends ResponseCache {
    HttpResponseCache() {
        throw new RuntimeException("Stub!");
    }

    public static HttpResponseCache getInstalled() {
        throw new RuntimeException("Stub!");
    }

    public static synchronized HttpResponseCache install(File directory, long maxSize) throws IOException {
        synchronized (HttpResponseCache.class) {
            throw new RuntimeException("Stub!");
        }
    }

    public CacheResponse get(URI uri, String requestMethod, Map map) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public long size() {
        throw new RuntimeException("Stub!");
    }

    public long maxSize() {
        throw new RuntimeException("Stub!");
    }

    public void flush() {
        throw new RuntimeException("Stub!");
    }

    public int getNetworkCount() {
        throw new RuntimeException("Stub!");
    }

    public int getHitCount() {
        throw new RuntimeException("Stub!");
    }

    public int getRequestCount() {
        throw new RuntimeException("Stub!");
    }

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void delete() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
