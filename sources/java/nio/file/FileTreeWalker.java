package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import sun.nio.fs.BasicFileAttributesHolder;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class FileTreeWalker implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean closed;
    private final boolean followLinks;
    private final LinkOption[] linkOptions;
    private final int maxDepth;
    private final ArrayDeque stack = new ArrayDeque();

    enum EventType {
        START_DIRECTORY,
        END_DIRECTORY,
        ENTRY
    }

    private static class DirectoryNode {
        private final Path dir;
        private final Iterator iterator;
        private final Object key;
        private boolean skipped;
        private final DirectoryStream stream;

        DirectoryNode(Path path, Object obj, DirectoryStream directoryStream) {
            this.dir = path;
            this.key = obj;
            this.stream = directoryStream;
            this.iterator = directoryStream.iterator();
        }

        Path directory() {
            return this.dir;
        }

        Object key() {
            return this.key;
        }

        DirectoryStream stream() {
            return this.stream;
        }

        Iterator iterator() {
            return this.iterator;
        }

        void skip() {
            this.skipped = true;
        }

        boolean skipped() {
            return this.skipped;
        }
    }

    static class Event {
        private final BasicFileAttributes attrs;
        private final Path file;
        private final IOException ioe;
        private final EventType type;

        private Event(EventType eventType, Path path, BasicFileAttributes basicFileAttributes, IOException iOException) {
            this.type = eventType;
            this.file = path;
            this.attrs = basicFileAttributes;
            this.ioe = iOException;
        }

        Event(EventType eventType, Path path, BasicFileAttributes basicFileAttributes) {
            this(eventType, path, basicFileAttributes, null);
        }

        Event(EventType eventType, Path path, IOException iOException) {
            this(eventType, path, null, iOException);
        }

        EventType type() {
            return this.type;
        }

        Path file() {
            return this.file;
        }

        BasicFileAttributes attributes() {
            return this.attrs;
        }

        IOException ioeException() {
            return this.ioe;
        }
    }

    FileTreeWalker(Collection collection, int i) {
        LinkOption[] linkOptionArr;
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (1.$SwitchMap$java$nio$file$FileVisitOption[((FileVisitOption) it.next()).ordinal()] != 1) {
                throw new AssertionError("Should not get here");
            }
            z = true;
        }
        if (i < 0) {
            throw new IllegalArgumentException("'maxDepth' is negative");
        }
        this.followLinks = z;
        if (z) {
            linkOptionArr = new LinkOption[0];
        } else {
            linkOptionArr = new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
        }
        this.linkOptions = linkOptionArr;
        this.maxDepth = i;
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$FileVisitOption;

        static {
            int[] iArr = new int[FileVisitOption.values().length];
            $SwitchMap$java$nio$file$FileVisitOption = iArr;
            try {
                iArr[FileVisitOption.FOLLOW_LINKS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private BasicFileAttributes getAttributes(Path path, boolean z) throws IOException {
        BasicFileAttributes basicFileAttributes;
        if (z && (path instanceof BasicFileAttributesHolder) && System.getSecurityManager() == null && (basicFileAttributes = ((BasicFileAttributesHolder) path).get()) != null && (!this.followLinks || !basicFileAttributes.isSymbolicLink())) {
            return basicFileAttributes;
        }
        try {
            return Files.readAttributes(path, BasicFileAttributes.class, this.linkOptions);
        } catch (IOException e) {
            if (!this.followLinks) {
                throw e;
            }
            return Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        }
    }

    private boolean wouldLoop(Path path, Object obj) {
        Iterator it = this.stack.iterator();
        while (it.hasNext()) {
            DirectoryNode directoryNode = (DirectoryNode) it.next();
            Object key = directoryNode.key();
            if (obj != null && key != null) {
                if (obj.equals(key)) {
                    return true;
                }
            } else {
                try {
                    if (Files.isSameFile(path, directoryNode.directory())) {
                        return true;
                    }
                } catch (IOException | SecurityException unused) {
                }
            }
        }
        return false;
    }

    private Event visit(Path path, boolean z, boolean z2) {
        try {
            BasicFileAttributes attributes = getAttributes(path, z2);
            if (this.stack.size() >= this.maxDepth || !attributes.isDirectory()) {
                return new Event(EventType.ENTRY, path, attributes);
            }
            if (this.followLinks && wouldLoop(path, attributes.fileKey())) {
                return new Event(EventType.ENTRY, path, new FileSystemLoopException(path.toString()));
            }
            try {
                this.stack.push(new DirectoryNode(path, attributes.fileKey(), Files.newDirectoryStream(path)));
                return new Event(EventType.START_DIRECTORY, path, attributes);
            } catch (IOException e) {
                return new Event(EventType.ENTRY, path, e);
            } catch (SecurityException e2) {
                if (z) {
                    return null;
                }
                throw e2;
            }
        } catch (IOException e3) {
            return new Event(EventType.ENTRY, path, e3);
        } catch (SecurityException e4) {
            if (z) {
                return null;
            }
            throw e4;
        }
    }

    Event walk(Path path) {
        if (this.closed) {
            throw new IllegalStateException("Closed");
        }
        return visit(path, false, false);
    }

    Event next() {
        Path path;
        Throwable th;
        Event visit;
        DirectoryNode directoryNode = (DirectoryNode) this.stack.peek();
        if (directoryNode == null) {
            return null;
        }
        do {
            if (directoryNode.skipped()) {
                path = null;
                th = null;
            } else {
                Iterator it = directoryNode.iterator();
                try {
                    path = it.hasNext() ? (Path) it.next() : null;
                    th = null;
                } catch (DirectoryIteratorException e) {
                    th = e.getCause();
                    path = null;
                }
            }
            if (path == null) {
                try {
                    directoryNode.stream().close();
                } catch (IOException e2) {
                    if (th == null) {
                        th = e2;
                    } else {
                        FileTreeWalker$$ExternalSyntheticBackport0.m(th, e2);
                    }
                }
                this.stack.pop();
                return new Event(EventType.END_DIRECTORY, directoryNode.directory(), (IOException) th);
            }
            visit = visit(path, true, true);
        } while (visit == null);
        return visit;
    }

    void pop() {
        if (this.stack.isEmpty()) {
            return;
        }
        try {
            ((DirectoryNode) this.stack.pop()).stream().close();
        } catch (IOException unused) {
        }
    }

    void skipRemainingSiblings() {
        if (this.stack.isEmpty()) {
            return;
        }
        ((DirectoryNode) this.stack.peek()).skip();
    }

    boolean isOpen() {
        return !this.closed;
    }

    public void close() {
        if (this.closed) {
            return;
        }
        while (!this.stack.isEmpty()) {
            pop();
        }
        this.closed = true;
    }
}
