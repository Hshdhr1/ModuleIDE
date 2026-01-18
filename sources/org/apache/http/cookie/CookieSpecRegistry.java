package org.apache.http.cookie;

import java.util.List;
import java.util.Map;
import org.apache.http.params.HttpParams;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public final class CookieSpecRegistry {
    public CookieSpecRegistry() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void register(String name, CookieSpecFactory factory) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void unregister(String id) {
        throw new RuntimeException("Stub!");
    }

    public synchronized CookieSpec getCookieSpec(String name, HttpParams params) throws IllegalStateException {
        throw new RuntimeException("Stub!");
    }

    public synchronized CookieSpec getCookieSpec(String name) throws IllegalStateException {
        throw new RuntimeException("Stub!");
    }

    public synchronized List getSpecNames() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setItems(Map map) {
        throw new RuntimeException("Stub!");
    }
}
