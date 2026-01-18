package java.nio.file;

import java.net.URI;
import java.nio.file.Path;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Paths {
    private Paths() {
    }

    public static Path get(String str, String... strArr) {
        return Path.-CC.of(str, strArr);
    }

    public static Path get(URI uri) {
        return Path.-CC.of(uri);
    }
}
