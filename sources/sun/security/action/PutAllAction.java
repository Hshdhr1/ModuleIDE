package sun.security.action;

import java.security.PrivilegedAction;
import java.security.Provider;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class PutAllAction implements PrivilegedAction {
    private final Map map;
    private final Provider provider;

    public PutAllAction(Provider provider, Map map) {
        this.provider = provider;
        this.map = map;
    }

    public Void run() {
        this.provider.putAll(this.map);
        return null;
    }
}
