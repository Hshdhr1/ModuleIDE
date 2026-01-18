package org.apache.http.protocol;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public class SyncBasicHttpContext extends BasicHttpContext {
    public SyncBasicHttpContext(HttpContext parentContext) {
        throw new RuntimeException("Stub!");
    }

    public synchronized Object getAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setAttribute(String id, Object obj) {
        throw new RuntimeException("Stub!");
    }

    public synchronized Object removeAttribute(String id) {
        throw new RuntimeException("Stub!");
    }
}
