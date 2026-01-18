package java.nio.file;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface WatchEvent {

    public interface Kind {
        String name();

        Class type();
    }

    public interface Modifier {
        String name();
    }

    Object context();

    int count();

    Kind kind();
}
