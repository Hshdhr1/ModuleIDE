package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class FileOwnerAttributeViewImpl implements FileOwnerAttributeView, DynamicFileAttributeView {
    private static final String OWNER_NAME = "owner";
    private final boolean isPosixView = false;
    private final FileAttributeView view;

    FileOwnerAttributeViewImpl(PosixFileAttributeView posixFileAttributeView) {
        this.view = posixFileAttributeView;
    }

    FileOwnerAttributeViewImpl(AclFileAttributeView aclFileAttributeView) {
        this.view = aclFileAttributeView;
    }

    public String name() {
        return "owner";
    }

    public void setAttribute(String str, Object obj) throws IOException {
        if (str.equals("owner")) {
            setOwner((UserPrincipal) obj);
            return;
        }
        throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
    }

    public Map readAttributes(String[] strArr) throws IOException {
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            if (str.equals("*") || str.equals("owner")) {
                hashMap.put("owner", getOwner());
            } else {
                throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
            }
        }
        return hashMap;
    }

    public UserPrincipal getOwner() throws IOException {
        if (this.isPosixView) {
            return ((PosixFileAttributeView) this.view).readAttributes().owner();
        }
        return ((AclFileAttributeView) this.view).getOwner();
    }

    public void setOwner(UserPrincipal userPrincipal) throws IOException {
        if (this.isPosixView) {
            ((PosixFileAttributeView) this.view).setOwner(userPrincipal);
        } else {
            ((AclFileAttributeView) this.view).setOwner(userPrincipal);
        }
    }
}
