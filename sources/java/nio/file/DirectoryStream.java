package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DirectoryStream extends Closeable, Iterable {

    @FunctionalInterface
    public interface Filter {
        boolean accept(Object obj) throws IOException;
    }

    Iterator iterator();
}
