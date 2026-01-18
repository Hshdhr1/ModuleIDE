package java.nio.file;

import java.io.IOException;
import java.nio.file.WatchEvent;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Watchable {
    WatchKey register(WatchService watchService, WatchEvent.Kind... kindArr) throws IOException;

    WatchKey register(WatchService watchService, WatchEvent.Kind[] kindArr, WatchEvent.Modifier... modifierArr) throws IOException;
}
