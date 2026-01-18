package org.apache.http.impl.client;

import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HttpContext;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public class DefaultProxyAuthenticationHandler extends AbstractAuthenticationHandler {
    public DefaultProxyAuthenticationHandler() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAuthenticationRequested(HttpResponse response, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public Map getChallenges(HttpResponse response, HttpContext context) throws MalformedChallengeException {
        throw new RuntimeException("Stub!");
    }
}
