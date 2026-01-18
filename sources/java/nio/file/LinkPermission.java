package java.nio.file;

import java.security.BasicPermission;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class LinkPermission extends BasicPermission {
    static final long serialVersionUID = -1441492453772213220L;

    private void checkName(String str) {
        if (str.equals("hard") || str.equals("symbolic")) {
            return;
        }
        throw new IllegalArgumentException("name: " + str);
    }

    public LinkPermission(String str) {
        super(str);
        checkName(str);
    }

    public LinkPermission(String str, String str2) {
        super(str);
        checkName(str);
        if (str2 == null || str2.isEmpty()) {
            return;
        }
        throw new IllegalArgumentException("actions: " + str2);
    }
}
