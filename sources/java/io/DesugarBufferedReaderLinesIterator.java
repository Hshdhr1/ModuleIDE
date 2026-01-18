package java.io;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DesugarBufferedReaderLinesIterator implements Iterator {
    private final BufferedReader bufferedReader;
    String nextLine = null;

    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.-CC.$default$forEachRemaining(this, consumer);
    }

    public /* synthetic */ void remove() {
        Iterator.-CC.$default$remove(this);
    }

    DesugarBufferedReaderLinesIterator(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public boolean hasNext() {
        if (this.nextLine != null) {
            return true;
        }
        try {
            String readLine = this.bufferedReader.readLine();
            this.nextLine = readLine;
            return readLine != null;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String next() {
        if (this.nextLine != null || hasNext()) {
            String str = this.nextLine;
            this.nextLine = null;
            return str;
        }
        throw new NoSuchElementException();
    }
}
