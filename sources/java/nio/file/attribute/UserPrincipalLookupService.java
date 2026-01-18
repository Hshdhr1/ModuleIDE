package java.nio.file.attribute;

import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class UserPrincipalLookupService {
    public abstract GroupPrincipal lookupPrincipalByGroupName(String str) throws IOException;

    public abstract UserPrincipal lookupPrincipalByName(String str) throws IOException;

    protected UserPrincipalLookupService() {
    }
}
