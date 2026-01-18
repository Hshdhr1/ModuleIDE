package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractAclFileAttributeView implements AclFileAttributeView, DynamicFileAttributeView {
    private static final String ACL_NAME = "acl";
    private static final String OWNER_NAME = "owner";

    AbstractAclFileAttributeView() {
    }

    public final String name() {
        return "acl";
    }

    public final void setAttribute(String str, Object obj) throws IOException {
        if (str.equals("owner")) {
            setOwner((UserPrincipal) obj);
            return;
        }
        if (str.equals("acl")) {
            setAcl((List) obj);
            return;
        }
        throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
    }

    public final Map readAttributes(String[] strArr) throws IOException {
        boolean z = false;
        boolean z2 = false;
        for (String str : strArr) {
            if (str.equals("*")) {
                z = true;
            } else if (str.equals("acl")) {
                z = true;
            } else if (!str.equals("owner")) {
                throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
            }
            z2 = true;
        }
        HashMap hashMap = new HashMap(2);
        if (z) {
            hashMap.put("acl", getAcl());
        }
        if (z2) {
            hashMap.put("owner", getOwner());
        }
        return Collections.unmodifiableMap(hashMap);
    }
}
