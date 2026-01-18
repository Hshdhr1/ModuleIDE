package sun.nio.ch;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class NativeThread {
    public static native long current();

    private static native void init();

    public static native void signal(long j);

    static {
        IOUtil.load();
        init();
    }
}
