package java.io;

import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarBufferedReader {
    private DesugarBufferedReader() {
    }

    public static Stream lines(BufferedReader bufferedReader) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new DesugarBufferedReaderLinesIterator(bufferedReader), 272), false);
    }
}
