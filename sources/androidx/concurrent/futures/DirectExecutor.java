package androidx.concurrent.futures;

import java.util.concurrent.Executor;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes8.dex */
public enum DirectExecutor implements Executor {
    INSTANCE;

    public void execute(Runnable command) {
        command.run();
    }

    public String toString() {
        return "DirectExecutor";
    }
}
