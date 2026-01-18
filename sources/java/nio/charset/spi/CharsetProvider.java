package java.nio.charset.spi;

import java.nio.charset.Charset;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class CharsetProvider {
    public abstract Charset charsetForName(String str);

    public abstract Iterator charsets();

    private static Void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        securityManager.checkPermission(new RuntimePermission("charsetProvider"));
        return null;
    }

    private CharsetProvider(Void r1) {
    }

    protected CharsetProvider() {
        this(checkPermission());
    }
}
