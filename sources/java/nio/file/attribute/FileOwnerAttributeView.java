package java.nio.file.attribute;

import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface FileOwnerAttributeView extends FileAttributeView {
    UserPrincipal getOwner() throws IOException;

    String name();

    void setOwner(UserPrincipal userPrincipal) throws IOException;
}
