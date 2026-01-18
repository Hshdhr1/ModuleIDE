package sun.nio.fs;

import java.io.IOException;
import java.lang.Iterable;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixDirectoryStream implements DirectoryStream {
    private final UnixPath dir;
    private final long dp;
    private final DirectoryStream.Filter filter;
    private volatile boolean isClosed;
    private Iterator iterator;
    private final ReentrantReadWriteLock streamLock = new ReentrantReadWriteLock(true);

    static /* bridge */ /* synthetic */ UnixPath -$$Nest$fgetdir(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.dir;
    }

    static /* bridge */ /* synthetic */ long -$$Nest$fgetdp(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.dp;
    }

    static /* bridge */ /* synthetic */ DirectoryStream.Filter -$$Nest$fgetfilter(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.filter;
    }

    public /* synthetic */ void forEach(Consumer consumer) {
        Iterable.-CC.$default$forEach(this, consumer);
    }

    public /* synthetic */ Spliterator spliterator() {
        return Iterable.-CC.$default$spliterator(this);
    }

    UnixDirectoryStream(UnixPath unixPath, long j, DirectoryStream.Filter filter) {
        this.dir = unixPath;
        this.dp = j;
        this.filter = filter;
    }

    protected final UnixPath directory() {
        return this.dir;
    }

    protected final Lock readLock() {
        return this.streamLock.readLock();
    }

    protected final Lock writeLock() {
        return this.streamLock.writeLock();
    }

    protected final boolean isOpen() {
        return !this.isClosed;
    }

    protected final boolean closeImpl() throws IOException {
        if (this.isClosed) {
            return false;
        }
        this.isClosed = true;
        try {
            UnixNativeDispatcher.closedir(this.dp);
            return true;
        } catch (UnixException e) {
            throw new IOException(e.errorString());
        }
    }

    public void close() throws IOException {
        writeLock().lock();
        try {
            closeImpl();
        } finally {
            writeLock().unlock();
        }
    }

    protected final Iterator iterator(DirectoryStream directoryStream) {
        UnixDirectoryIterator unixDirectoryIterator;
        if (this.isClosed) {
            throw new IllegalStateException("Directory stream is closed");
        }
        synchronized (this) {
            if (this.iterator != null) {
                throw new IllegalStateException("Iterator already obtained");
            }
            unixDirectoryIterator = new UnixDirectoryIterator();
            this.iterator = unixDirectoryIterator;
        }
        return unixDirectoryIterator;
    }

    public Iterator iterator() {
        return iterator(this);
    }

    private class UnixDirectoryIterator implements Iterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean atEof = false;
        private Path nextEntry;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        UnixDirectoryIterator() {
        }

        private boolean isSelfOrParent(byte[] bArr) {
            return bArr[0] == 46 && (bArr.length == 1 || (bArr.length == 2 && bArr[1] == 46));
        }

        private Path readNextEntry() {
            UnixPath resolve;
            while (true) {
                UnixDirectoryStream.this.readLock().lock();
                try {
                    try {
                        byte[] readdir = UnixDirectoryStream.this.isOpen() ? UnixNativeDispatcher.readdir(UnixDirectoryStream.-$$Nest$fgetdp(UnixDirectoryStream.this)) : null;
                        if (readdir == null) {
                            this.atEof = true;
                            return null;
                        }
                        if (!isSelfOrParent(readdir)) {
                            resolve = UnixDirectoryStream.-$$Nest$fgetdir(UnixDirectoryStream.this).resolve(readdir);
                            try {
                                if (UnixDirectoryStream.-$$Nest$fgetfilter(UnixDirectoryStream.this) == null || UnixDirectoryStream.-$$Nest$fgetfilter(UnixDirectoryStream.this).accept(resolve)) {
                                    break;
                                }
                            } catch (IOException e) {
                                throw new DirectoryIteratorException(e);
                            }
                        }
                    } catch (UnixException e2) {
                        throw new DirectoryIteratorException(e2.asIOException(UnixDirectoryStream.-$$Nest$fgetdir(UnixDirectoryStream.this)));
                    }
                } finally {
                    UnixDirectoryStream.this.readLock().unlock();
                }
            }
            return resolve;
        }

        public synchronized boolean hasNext() {
            if (this.nextEntry == null && !this.atEof) {
                this.nextEntry = readNextEntry();
            }
            return this.nextEntry != null;
        }

        public synchronized Path next() {
            Path path;
            path = this.nextEntry;
            if (path == null && !this.atEof) {
                path = readNextEntry();
            } else {
                this.nextEntry = null;
            }
            if (path == null) {
                throw new NoSuchElementException();
            }
            return path;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
