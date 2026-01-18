package sun.nio.fs;

import java.io.IOException;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
interface DynamicFileAttributeView {
    Map readAttributes(String[] strArr) throws IOException;

    void setAttribute(String str, Object obj) throws IOException;
}
