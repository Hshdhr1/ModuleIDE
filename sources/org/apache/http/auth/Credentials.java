package org.apache.http.auth;

import java.security.Principal;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public interface Credentials {
    String getPassword();

    Principal getUserPrincipal();
}
