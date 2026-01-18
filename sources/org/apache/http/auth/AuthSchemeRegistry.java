package org.apache.http.auth;

import java.util.List;
import java.util.Map;
import org.apache.http.params.HttpParams;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public final class AuthSchemeRegistry {
    public AuthSchemeRegistry() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void register(String name, AuthSchemeFactory factory) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void unregister(String name) {
        throw new RuntimeException("Stub!");
    }

    public synchronized AuthScheme getAuthScheme(String name, HttpParams params) throws IllegalStateException {
        throw new RuntimeException("Stub!");
    }

    public synchronized List getSchemeNames() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setItems(Map map) {
        throw new RuntimeException("Stub!");
    }
}
