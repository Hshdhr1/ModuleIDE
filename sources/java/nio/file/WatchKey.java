package java.nio.file;

import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface WatchKey {
    void cancel();

    boolean isValid();

    List pollEvents();

    boolean reset();

    Watchable watchable();
}
