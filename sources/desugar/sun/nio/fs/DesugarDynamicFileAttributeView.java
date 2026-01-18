package desugar.sun.nio.fs;

import java.io.IOException;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
interface DesugarDynamicFileAttributeView {
    Map readAttributes(String[] attributes) throws IOException;

    void setAttribute(String attribute, Object value) throws IOException;
}
