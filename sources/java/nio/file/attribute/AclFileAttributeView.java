package java.nio.file.attribute;

import java.io.IOException;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface AclFileAttributeView extends FileOwnerAttributeView {
    List getAcl() throws IOException;

    String name();

    void setAcl(List list) throws IOException;
}
