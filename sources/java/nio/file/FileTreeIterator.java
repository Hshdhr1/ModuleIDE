package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileTreeWalker;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class FileTreeIterator implements Iterator, Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private FileTreeWalker.Event next;
    private final FileTreeWalker walker;

    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.-CC.$default$forEachRemaining(this, consumer);
    }

    public /* synthetic */ void remove() {
        Iterator.-CC.$default$remove(this);
    }

    FileTreeIterator(Path path, int i, FileVisitOption... fileVisitOptionArr) throws IOException {
        FileTreeWalker fileTreeWalker = new FileTreeWalker(Arrays.asList(fileVisitOptionArr), i);
        this.walker = fileTreeWalker;
        FileTreeWalker.Event walk = fileTreeWalker.walk(path);
        this.next = walk;
        IOException ioeException = walk.ioeException();
        if (ioeException != null) {
            throw ioeException;
        }
    }

    private void fetchNextIfNeeded() {
        if (this.next == null) {
            FileTreeWalker.Event next = this.walker.next();
            while (next != null) {
                IOException ioeException = next.ioeException();
                if (ioeException != null) {
                    throw new UncheckedIOException(ioeException);
                }
                if (next.type() != FileTreeWalker.EventType.END_DIRECTORY) {
                    this.next = next;
                    return;
                }
                next = this.walker.next();
            }
        }
    }

    public boolean hasNext() {
        if (!this.walker.isOpen()) {
            throw new IllegalStateException();
        }
        fetchNextIfNeeded();
        return this.next != null;
    }

    public FileTreeWalker.Event next() {
        if (!this.walker.isOpen()) {
            throw new IllegalStateException();
        }
        fetchNextIfNeeded();
        FileTreeWalker.Event event = this.next;
        if (event == null) {
            throw new NoSuchElementException();
        }
        this.next = null;
        return event;
    }

    public void close() {
        this.walker.close();
    }
}
