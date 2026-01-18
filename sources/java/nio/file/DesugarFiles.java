package java.nio.file;

import java.io.IOException;
import java.nio.charset.Charset;
import sun.nio.cs.UTF_8;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarFiles {
    private DesugarFiles() {
    }

    public static String readString(Path path) throws IOException {
        return readString(path, UTF_8.INSTANCE);
    }

    public static String readString(Path path, Charset charset) throws IOException {
        path.getClass();
        charset.getClass();
        return new String(Files.readAllBytes(path), charset);
    }

    public static Path writeString(Path path, CharSequence charSequence, OpenOption... openOptionArr) throws IOException {
        return writeString(path, charSequence, UTF_8.INSTANCE, openOptionArr);
    }

    public static Path writeString(Path path, CharSequence charSequence, Charset charset, OpenOption... openOptionArr) throws IOException {
        path.getClass();
        charSequence.getClass();
        charset.getClass();
        Files.write(path, String.valueOf(charSequence).getBytes(charset), openOptionArr);
        return path;
    }
}
