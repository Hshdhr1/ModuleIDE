package sun.nio.ch;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.Cleaner;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.FileLockInterruptionException;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import jdk.internal.misc.JavaIOFileDescriptorAccess;
import jdk.internal.misc.JavaNioAccess;
import jdk.internal.misc.SharedSecrets;
import jdk.internal.ref.CleanerFactory;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class FileChannelImpl extends FileChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAPPED_TRANSFER_SIZE = 8388608;
    private static final int MAP_PV = 2;
    private static final int MAP_RO = 0;
    private static final int MAP_RW = 1;
    private static final int TRANSFER_SIZE = 8192;
    private static final long allocationGranularity;
    private final int alignment;
    private final Cleaner.Cleanable closer;
    private final boolean direct;
    private final FileDescriptor fd;
    private volatile FileLockTable fileLockTable;
    private final FileDispatcher nd;
    private final Object parent;
    private final String path;
    private final boolean readable;
    private volatile boolean uninterruptible;
    private final boolean writable;
    private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
    private static volatile boolean transferSupported = true;
    private static volatile boolean pipeSupported = true;
    private static volatile boolean fileSupported = true;
    private final NativeThreadSet threads = new NativeThreadSet(2);
    private final Object positionLock = new Object();

    static /* bridge */ /* synthetic */ JavaIOFileDescriptorAccess -$$Nest$sfgetfdAccess() {
        return fdAccess;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$smunmap0(long j, long j2) {
        return unmap0(j, j2);
    }

    private static native long initIDs();

    private native long map0(int i, long j, long j2) throws IOException;

    private native long transferTo0(FileDescriptor fileDescriptor, long j, long j2, FileDescriptor fileDescriptor2);

    private static native int unmap0(long j, long j2);

    static {
        IOUtil.load();
        allocationGranularity = initIDs();
    }

    private static class Closer implements Runnable {
        private final FileDescriptor fd;

        Closer(FileDescriptor fileDescriptor) {
            this.fd = fileDescriptor;
        }

        public void run() {
            try {
                FileChannelImpl.-$$Nest$sfgetfdAccess().close(this.fd);
            } catch (IOException e) {
                throw new UncheckedIOException("close", e);
            }
        }
    }

    private FileChannelImpl(FileDescriptor fileDescriptor, String str, boolean z, boolean z2, boolean z3, Object obj) {
        this.fd = fileDescriptor;
        this.readable = z;
        this.writable = z2;
        this.parent = obj;
        this.path = str;
        this.direct = z3;
        FileDispatcherImpl fileDispatcherImpl = new FileDispatcherImpl();
        this.nd = fileDispatcherImpl;
        if (z3) {
            this.alignment = fileDispatcherImpl.setDirectIO(fileDescriptor, str);
        } else {
            this.alignment = -1;
        }
        this.closer = obj != null ? null : CleanerFactory.cleaner().register(this, new Closer(fileDescriptor));
    }

    public static FileChannel open(FileDescriptor fileDescriptor, String str, boolean z, boolean z2, boolean z3, Object obj) {
        return new FileChannelImpl(fileDescriptor, str, z, z2, z3, obj);
    }

    private void ensureOpen() throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    public void setUninterruptible() {
        this.uninterruptible = true;
    }

    private void beginBlocking() {
        if (this.uninterruptible) {
            return;
        }
        begin();
    }

    private void endBlocking(boolean z) throws AsynchronousCloseException {
        if (this.uninterruptible) {
            return;
        }
        end(z);
    }

    protected void implCloseChannel() throws IOException {
        if (this.fd.valid()) {
            if (this.fileLockTable != null) {
                for (FileLock fileLock : this.fileLockTable.removeAll()) {
                    synchronized (fileLock) {
                        if (fileLock.isValid()) {
                            this.nd.release(this.fd, fileLock.position(), fileLock.size());
                            ((FileLockImpl) fileLock).invalidate();
                        }
                    }
                }
            }
            this.threads.signalAndWait();
            Object obj = this.parent;
            if (obj != null) {
                ((Closeable) obj).close();
                return;
            }
            Cleaner.Cleanable cleanable = this.closer;
            if (cleanable != null) {
                try {
                    cleanable.clean();
                    return;
                } catch (UncheckedIOException e) {
                    throw e.getCause();
                }
            }
            fdAccess.close(this.fd);
        }
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        Throwable th;
        int i;
        ensureOpen();
        if (!this.readable) {
            throw new NonReadableChannelException();
        }
        synchronized (this.positionLock) {
            if (this.direct) {
                Util.checkChannelPositionAligned(position(), this.alignment);
            }
            boolean z = true;
            int i2 = -1;
            try {
                beginBlocking();
                i2 = this.threads.add();
                if (isOpen()) {
                    i = 0;
                    while (true) {
                        try {
                            ByteBuffer byteBuffer2 = byteBuffer;
                            i = IOUtil.read(this.fd, byteBuffer2, -1L, this.direct, this.alignment, this.nd);
                            if (i != -3 || !isOpen()) {
                                break;
                            }
                            byteBuffer = byteBuffer2;
                        } catch (Throwable th2) {
                            th = th2;
                            this.threads.remove(i2);
                            if (i <= 0) {
                                z = false;
                            }
                            endBlocking(z);
                            throw th;
                        }
                    }
                    int normalize = IOStatus.normalize(i);
                    this.threads.remove(i2);
                    if (i <= 0) {
                        z = false;
                    }
                    endBlocking(z);
                    return normalize;
                }
                this.threads.remove(i2);
                endBlocking(false);
                return 0;
            } catch (Throwable th3) {
                th = th3;
                i = 0;
            }
        }
    }

    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        int i3 = i;
        if (i3 >= 0 && i2 >= 0) {
            ByteBuffer[] byteBufferArr2 = byteBufferArr;
            if (i3 <= byteBufferArr2.length - i2) {
                ensureOpen();
                if (!this.readable) {
                    throw new NonReadableChannelException();
                }
                synchronized (this.positionLock) {
                    if (this.direct) {
                        Util.checkChannelPositionAligned(position(), this.alignment);
                    }
                    boolean z = true;
                    int i4 = -1;
                    try {
                        beginBlocking();
                        int add = this.threads.add();
                        try {
                            if (isOpen()) {
                                j = 0;
                                while (true) {
                                    try {
                                        j = IOUtil.read(this.fd, byteBufferArr2, i3, i2, this.direct, this.alignment, this.nd);
                                        if (j != -3 || !isOpen()) {
                                            break;
                                        }
                                        byteBufferArr2 = byteBufferArr;
                                        i3 = i;
                                    } catch (Throwable th) {
                                        th = th;
                                        i4 = add;
                                        this.threads.remove(i4);
                                        if (j <= 0) {
                                            z = false;
                                        }
                                        endBlocking(z);
                                        throw th;
                                    }
                                }
                                long normalize = IOStatus.normalize(j);
                                this.threads.remove(add);
                                if (j <= 0) {
                                    z = false;
                                }
                                endBlocking(z);
                                return normalize;
                            }
                            this.threads.remove(add);
                            endBlocking(false);
                            return 0L;
                        } catch (Throwable th2) {
                            th = th2;
                            j = 0;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        j = 0;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        Throwable th;
        int i;
        ensureOpen();
        if (!this.writable) {
            throw new NonWritableChannelException();
        }
        synchronized (this.positionLock) {
            if (this.direct) {
                Util.checkChannelPositionAligned(position(), this.alignment);
            }
            boolean z = true;
            int i2 = -1;
            try {
                beginBlocking();
                i2 = this.threads.add();
                if (isOpen()) {
                    i = 0;
                    while (true) {
                        try {
                            ByteBuffer byteBuffer2 = byteBuffer;
                            i = IOUtil.write(this.fd, byteBuffer2, -1L, this.direct, this.alignment, this.nd);
                            if (i != -3 || !isOpen()) {
                                break;
                            }
                            byteBuffer = byteBuffer2;
                        } catch (Throwable th2) {
                            th = th2;
                            this.threads.remove(i2);
                            if (i <= 0) {
                                z = false;
                            }
                            endBlocking(z);
                            throw th;
                        }
                    }
                    int normalize = IOStatus.normalize(i);
                    this.threads.remove(i2);
                    if (i <= 0) {
                        z = false;
                    }
                    endBlocking(z);
                    return normalize;
                }
                this.threads.remove(i2);
                endBlocking(false);
                return 0;
            } catch (Throwable th3) {
                th = th3;
                i = 0;
            }
        }
    }

    public long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        int i3 = i;
        if (i3 >= 0 && i2 >= 0) {
            ByteBuffer[] byteBufferArr2 = byteBufferArr;
            if (i3 <= byteBufferArr2.length - i2) {
                ensureOpen();
                if (!this.writable) {
                    throw new NonWritableChannelException();
                }
                synchronized (this.positionLock) {
                    if (this.direct) {
                        Util.checkChannelPositionAligned(position(), this.alignment);
                    }
                    boolean z = true;
                    int i4 = -1;
                    try {
                        beginBlocking();
                        int add = this.threads.add();
                        try {
                            if (isOpen()) {
                                j = 0;
                                while (true) {
                                    try {
                                        j = IOUtil.write(this.fd, byteBufferArr2, i3, i2, this.direct, this.alignment, this.nd);
                                        if (j != -3 || !isOpen()) {
                                            break;
                                        }
                                        byteBufferArr2 = byteBufferArr;
                                        i3 = i;
                                    } catch (Throwable th) {
                                        th = th;
                                        i4 = add;
                                        this.threads.remove(i4);
                                        if (j <= 0) {
                                            z = false;
                                        }
                                        endBlocking(z);
                                        throw th;
                                    }
                                }
                                long normalize = IOStatus.normalize(j);
                                this.threads.remove(add);
                                if (j <= 0) {
                                    z = false;
                                }
                                endBlocking(z);
                                return normalize;
                            }
                            this.threads.remove(add);
                            endBlocking(false);
                            return 0L;
                        } catch (Throwable th2) {
                            th = th2;
                            j = 0;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        j = 0;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public long position() throws IOException {
        long j;
        ensureOpen();
        synchronized (this.positionLock) {
            boolean z = true;
            int i = -1;
            try {
                beginBlocking();
                i = this.threads.add();
                if (isOpen()) {
                    boolean append = fdAccess.getAppend(this.fd);
                    j = -1;
                    do {
                        if (append) {
                            try {
                                j = this.nd.size(this.fd);
                            } catch (Throwable th) {
                                th = th;
                                this.threads.remove(i);
                                if (j <= -1) {
                                    z = false;
                                }
                                endBlocking(z);
                                throw th;
                            }
                        } else {
                            j = this.nd.seek(this.fd, -1L);
                        }
                        if (j != -3) {
                            break;
                        }
                    } while (isOpen());
                    long normalize = IOStatus.normalize(j);
                    this.threads.remove(i);
                    if (j <= -1) {
                        z = false;
                    }
                    endBlocking(z);
                    return normalize;
                }
                this.threads.remove(i);
                endBlocking(false);
                return 0L;
            } catch (Throwable th2) {
                th = th2;
                j = -1;
            }
        }
    }

    public FileChannel position(long j) throws IOException {
        long j2;
        ensureOpen();
        if (j < 0) {
            throw new IllegalArgumentException();
        }
        synchronized (this.positionLock) {
            boolean z = true;
            int i = -1;
            try {
                beginBlocking();
                i = this.threads.add();
                if (isOpen()) {
                    j2 = -1;
                    do {
                        try {
                            j2 = this.nd.seek(this.fd, j);
                            if (j2 != -3) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            this.threads.remove(i);
                            if (j2 <= -1) {
                                z = false;
                            }
                            endBlocking(z);
                            throw th;
                        }
                    } while (isOpen());
                    this.threads.remove(i);
                    if (j2 <= -1) {
                        z = false;
                    }
                    endBlocking(z);
                    return this;
                }
                this.threads.remove(i);
                endBlocking(false);
                return null;
            } catch (Throwable th2) {
                th = th2;
                j2 = -1;
            }
        }
    }

    public long size() throws IOException {
        long j;
        ensureOpen();
        synchronized (this.positionLock) {
            boolean z = true;
            int i = -1;
            try {
                beginBlocking();
                i = this.threads.add();
                if (isOpen()) {
                    j = -1;
                    do {
                        try {
                            j = this.nd.size(this.fd);
                            if (j != -3) {
                                break;
                            }
                        } catch (Throwable th) {
                            th = th;
                            this.threads.remove(i);
                            if (j <= -1) {
                                z = false;
                            }
                            endBlocking(z);
                            throw th;
                        }
                    } while (isOpen());
                    long normalize = IOStatus.normalize(j);
                    this.threads.remove(i);
                    if (j <= -1) {
                        z = false;
                    }
                    endBlocking(z);
                    return normalize;
                }
                this.threads.remove(i);
                endBlocking(false);
                return -1L;
            } catch (Throwable th2) {
                th = th2;
                j = -1;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.nio.channels.FileChannel truncate(long r18) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.FileChannelImpl.truncate(long):java.nio.channels.FileChannel");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void force(boolean r8) throws java.io.IOException {
        /*
            r7 = this;
            r7.ensureOpen()
            r0 = 1
            r1 = 0
            r2 = -1
            r7.beginBlocking()     // Catch: java.lang.Throwable -> L41
            sun.nio.ch.NativeThreadSet r3 = r7.threads     // Catch: java.lang.Throwable -> L41
            int r3 = r3.add()     // Catch: java.lang.Throwable -> L41
            boolean r4 = r7.isOpen()     // Catch: java.lang.Throwable -> L3f
            if (r4 != 0) goto L1e
            sun.nio.ch.NativeThreadSet r8 = r7.threads
            r8.remove(r3)
            r7.endBlocking(r1)
            return
        L1e:
            r4 = -1
        L1f:
            sun.nio.ch.FileDispatcher r5 = r7.nd     // Catch: java.lang.Throwable -> L3d
            java.io.FileDescriptor r6 = r7.fd     // Catch: java.lang.Throwable -> L3d
            int r4 = r5.force(r6, r8)     // Catch: java.lang.Throwable -> L3d
            r5 = -3
            if (r4 != r5) goto L30
            boolean r5 = r7.isOpen()     // Catch: java.lang.Throwable -> L3d
            if (r5 != 0) goto L1f
        L30:
            sun.nio.ch.NativeThreadSet r8 = r7.threads
            r8.remove(r3)
            if (r4 <= r2) goto L38
            goto L39
        L38:
            r0 = 0
        L39:
            r7.endBlocking(r0)
            return
        L3d:
            r8 = move-exception
            goto L44
        L3f:
            r8 = move-exception
            goto L43
        L41:
            r8 = move-exception
            r3 = -1
        L43:
            r4 = -1
        L44:
            sun.nio.ch.NativeThreadSet r5 = r7.threads
            r5.remove(r3)
            if (r4 <= r2) goto L4c
            goto L4d
        L4c:
            r0 = 0
        L4d:
            r7.endBlocking(r0)
            goto L52
        L51:
            throw r8
        L52:
            goto L51
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.FileChannelImpl.force(boolean):void");
    }

    private long transferToDirectlyInternal(long j, int i, WritableByteChannel writableByteChannel, FileDescriptor fileDescriptor) throws IOException {
        long j2;
        int i2 = -1;
        try {
            beginBlocking();
            int add = this.threads.add();
            try {
                if (!isOpen()) {
                    this.threads.remove(add);
                    end(false);
                    return -1L;
                }
                j2 = -1;
                do {
                    try {
                        j2 = transferTo0(this.fd, j, i, fileDescriptor);
                        if (j2 != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        i2 = add;
                        this.threads.remove(i2);
                        end(j2 > -1);
                        throw th;
                    }
                } while (isOpen());
                if (j2 == -6) {
                    if (writableByteChannel instanceof SinkChannelImpl) {
                        pipeSupported = false;
                    }
                    if (writableByteChannel instanceof FileChannelImpl) {
                        fileSupported = false;
                    }
                    this.threads.remove(add);
                    end(j2 > -1);
                    return -6L;
                }
                if (j2 == -4) {
                    transferSupported = false;
                    this.threads.remove(add);
                    end(j2 > -1);
                    return -4L;
                }
                long normalize = IOStatus.normalize(j2);
                this.threads.remove(add);
                end(j2 > -1);
                return normalize;
            } catch (Throwable th2) {
                th = th2;
                j2 = -1;
            }
        } catch (Throwable th3) {
            th = th3;
            j2 = -1;
        }
    }

    private long transferToDirectly(long j, int i, WritableByteChannel writableByteChannel) throws IOException {
        FileDescriptor fileDescriptor;
        if (!transferSupported) {
            return -4L;
        }
        if (writableByteChannel instanceof FileChannelImpl) {
            if (!fileSupported) {
                return -6L;
            }
            fileDescriptor = ((FileChannelImpl) writableByteChannel).fd;
        } else if (!(writableByteChannel instanceof SelChImpl)) {
            fileDescriptor = null;
        } else {
            if ((writableByteChannel instanceof SinkChannelImpl) && !pipeSupported) {
                return -6L;
            }
            if (!this.nd.canTransferToDirectly((SelectableChannel) writableByteChannel)) {
                return -6L;
            }
            fileDescriptor = ((SelChImpl) writableByteChannel).getFD();
        }
        FileDescriptor fileDescriptor2 = fileDescriptor;
        if (fileDescriptor2 == null || IOUtil.fdVal(this.fd) == IOUtil.fdVal(fileDescriptor2)) {
            return -4L;
        }
        if (this.nd.transferToDirectlyNeedsPositionLock()) {
            synchronized (this.positionLock) {
                try {
                    try {
                        long position = position();
                        try {
                            return transferToDirectlyInternal(j, i, writableByteChannel, fileDescriptor2);
                        } finally {
                            position(position);
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return transferToDirectlyInternal(j, i, writableByteChannel, fileDescriptor2);
    }

    /* JADX WARN: Finally extract failed */
    private long transferToTrustedChannel(long j, long j2, WritableByteChannel writableByteChannel) throws IOException {
        boolean z = writableByteChannel instanceof SelChImpl;
        if (!(writableByteChannel instanceof FileChannelImpl) && !z) {
            return -4L;
        }
        long j3 = j;
        long j4 = j2;
        while (true) {
            if (j4 <= 0) {
                break;
            }
            try {
                MappedByteBuffer map = map(FileChannel.MapMode.READ_ONLY, j3, Math.min(j4, 8388608L));
                try {
                    long write = writableByteChannel.write(map);
                    j4 -= write;
                    if (z) {
                        unmap(map);
                        break;
                    }
                    j3 += write;
                    unmap(map);
                } catch (Throwable th) {
                    unmap(map);
                    throw th;
                }
            } catch (IOException e) {
                if (j4 == j2) {
                    throw e;
                }
            } catch (ClosedByInterruptException e2) {
                try {
                    close();
                    throw e2;
                } catch (Throwable th2) {
                    FileChannelImpl$$ExternalSyntheticBackport0.m(e2, th2);
                    throw e2;
                }
            }
        }
        return j2 - j4;
    }

    private long transferToArbitraryChannel(long j, int i, WritableByteChannel writableByteChannel) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(Math.min(i, 8192));
        long j2 = 0;
        while (true) {
            long j3 = i;
            if (j2 >= j3) {
                break;
            }
            try {
                allocate.limit(Math.min((int) (j3 - j2), 8192));
                int read = read(allocate, j);
                if (read <= 0) {
                    break;
                }
                allocate.flip();
                int write = writableByteChannel.write(allocate);
                long j4 = write;
                j2 += j4;
                if (write != read) {
                    return j2;
                }
                j += j4;
                allocate.clear();
            } catch (IOException e) {
                if (j2 > 0) {
                    return j2;
                }
                throw e;
            }
        }
        return j2;
    }

    public long transferTo(long j, long j2, WritableByteChannel writableByteChannel) throws IOException {
        ensureOpen();
        if (!writableByteChannel.isOpen()) {
            throw new ClosedChannelException();
        }
        if (!this.readable) {
            throw new NonReadableChannelException();
        }
        if ((writableByteChannel instanceof FileChannelImpl) && !((FileChannelImpl) writableByteChannel).writable) {
            throw new NonWritableChannelException();
        }
        if (j < 0 || j2 < 0) {
            throw new IllegalArgumentException();
        }
        long size = size();
        if (j > size) {
            return 0L;
        }
        int min = (int) Math.min(j2, 2147483647L);
        long j3 = size - j;
        if (j3 < min) {
            min = (int) j3;
        }
        long transferToDirectly = transferToDirectly(j, min, writableByteChannel);
        if (transferToDirectly >= 0) {
            return transferToDirectly;
        }
        long transferToTrustedChannel = transferToTrustedChannel(j, min, writableByteChannel);
        return transferToTrustedChannel >= 0 ? transferToTrustedChannel : transferToArbitraryChannel(j, min, writableByteChannel);
    }

    private long transferFromFileChannel(FileChannelImpl fileChannelImpl, long j, long j2) throws IOException {
        MappedByteBuffer mappedByteBuffer;
        if (!fileChannelImpl.readable) {
            throw new NonReadableChannelException();
        }
        synchronized (fileChannelImpl.positionLock) {
            try {
                long position = fileChannelImpl.position();
                long min = Math.min(j2, fileChannelImpl.size() - position);
                long j3 = j;
                long j4 = position;
                long j5 = min;
                while (j5 > 0) {
                    MappedByteBuffer map = fileChannelImpl.map(FileChannel.MapMode.READ_ONLY, j4, Math.min(j5, 8388608L));
                    try {
                        long write = write(map, j3);
                        long j6 = j4 + write;
                        j3 += write;
                        j5 -= write;
                        try {
                            unmap(map);
                            j4 = j6;
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    } catch (IOException e) {
                        mappedByteBuffer = map;
                        if (j5 == min) {
                            try {
                                throw e;
                            } catch (Throwable th2) {
                                th = th2;
                                unmap(mappedByteBuffer);
                                throw th;
                            }
                        }
                        unmap(mappedByteBuffer);
                    } catch (Throwable th3) {
                        th = th3;
                        mappedByteBuffer = map;
                        unmap(mappedByteBuffer);
                        throw th;
                    }
                }
                long j7 = min - j5;
                fileChannelImpl.position(position + j7);
                return j7;
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    private long transferFromArbitraryChannel(ReadableByteChannel readableByteChannel, long j, long j2) throws IOException {
        long j3 = 8192;
        ByteBuffer allocate = ByteBuffer.allocate((int) Math.min(j2, 8192L));
        long j4 = j;
        long j5 = 0;
        while (j5 < j2) {
            try {
                allocate.limit((int) Math.min(j2 - j5, j3));
                int read = readableByteChannel.read(allocate);
                if (read <= 0) {
                    break;
                }
                allocate.flip();
                int write = write(allocate, j4);
                long j6 = write;
                j5 += j6;
                if (write != read) {
                    return j5;
                }
                j4 += j6;
                allocate.clear();
                j3 = 8192;
            } catch (IOException e) {
                if (j5 > 0) {
                    return j5;
                }
                throw e;
            }
        }
        return j5;
    }

    public long transferFrom(ReadableByteChannel readableByteChannel, long j, long j2) throws IOException {
        ensureOpen();
        if (!readableByteChannel.isOpen()) {
            throw new ClosedChannelException();
        }
        if (!this.writable) {
            throw new NonWritableChannelException();
        }
        if (j < 0 || j2 < 0) {
            throw new IllegalArgumentException();
        }
        if (j > size()) {
            return 0L;
        }
        if (readableByteChannel instanceof FileChannelImpl) {
            return transferFromFileChannel((FileChannelImpl) readableByteChannel, j, j2);
        }
        return transferFromArbitraryChannel(readableByteChannel, j, j2);
    }

    public int read(ByteBuffer byteBuffer, long j) throws IOException {
        int readInternal;
        byteBuffer.getClass();
        if (j < 0) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.readable) {
            throw new NonReadableChannelException();
        }
        if (this.direct) {
            Util.checkChannelPositionAligned(j, this.alignment);
        }
        ensureOpen();
        if (this.nd.needsPositionLock()) {
            synchronized (this.positionLock) {
                readInternal = readInternal(byteBuffer, j);
            }
            return readInternal;
        }
        return readInternal(byteBuffer, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readInternal(java.nio.ByteBuffer r13, long r14) throws java.io.IOException {
        /*
            r12 = this;
            r1 = 1
            r2 = -1
            r3 = 0
            r12.beginBlocking()     // Catch: java.lang.Throwable -> L51
            sun.nio.ch.NativeThreadSet r0 = r12.threads     // Catch: java.lang.Throwable -> L51
            int r4 = r0.add()     // Catch: java.lang.Throwable -> L51
            boolean r0 = r12.isOpen()     // Catch: java.lang.Throwable -> L4d
            if (r0 != 0) goto L1b
            sun.nio.ch.NativeThreadSet r13 = r12.threads
            r13.remove(r4)
            r12.endBlocking(r3)
            return r2
        L1b:
            r2 = 0
        L1c:
            java.io.FileDescriptor r5 = r12.fd     // Catch: java.lang.Throwable -> L48
            boolean r9 = r12.direct     // Catch: java.lang.Throwable -> L48
            int r10 = r12.alignment     // Catch: java.lang.Throwable -> L48
            sun.nio.ch.FileDispatcher r11 = r12.nd     // Catch: java.lang.Throwable -> L48
            r6 = r13
            r7 = r14
            int r2 = sun.nio.ch.IOUtil.read(r5, r6, r7, r9, r10, r11)     // Catch: java.lang.Throwable -> L48
            r13 = -3
            if (r2 != r13) goto L37
            boolean r13 = r12.isOpen()     // Catch: java.lang.Throwable -> L48
            if (r13 != 0) goto L34
            goto L37
        L34:
            r13 = r6
            r14 = r7
            goto L1c
        L37:
            int r13 = sun.nio.ch.IOStatus.normalize(r2)     // Catch: java.lang.Throwable -> L48
            sun.nio.ch.NativeThreadSet r14 = r12.threads
            r14.remove(r4)
            if (r2 <= 0) goto L43
            goto L44
        L43:
            r1 = 0
        L44:
            r12.endBlocking(r1)
            return r13
        L48:
            r0 = move-exception
            r13 = r0
            r14 = r2
            r2 = r4
            goto L54
        L4d:
            r0 = move-exception
            r13 = r0
            r2 = r4
            goto L53
        L51:
            r0 = move-exception
            r13 = r0
        L53:
            r14 = 0
        L54:
            sun.nio.ch.NativeThreadSet r15 = r12.threads
            r15.remove(r2)
            if (r14 <= 0) goto L5c
            goto L5d
        L5c:
            r1 = 0
        L5d:
            r12.endBlocking(r1)
            goto L62
        L61:
            throw r13
        L62:
            goto L61
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.FileChannelImpl.readInternal(java.nio.ByteBuffer, long):int");
    }

    public int write(ByteBuffer byteBuffer, long j) throws IOException {
        int writeInternal;
        byteBuffer.getClass();
        if (j < 0) {
            throw new IllegalArgumentException("Negative position");
        }
        if (!this.writable) {
            throw new NonWritableChannelException();
        }
        if (this.direct) {
            Util.checkChannelPositionAligned(j, this.alignment);
        }
        ensureOpen();
        if (this.nd.needsPositionLock()) {
            synchronized (this.positionLock) {
                writeInternal = writeInternal(byteBuffer, j);
            }
            return writeInternal;
        }
        return writeInternal(byteBuffer, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int writeInternal(java.nio.ByteBuffer r13, long r14) throws java.io.IOException {
        /*
            r12 = this;
            r1 = 1
            r2 = -1
            r3 = 0
            r12.beginBlocking()     // Catch: java.lang.Throwable -> L51
            sun.nio.ch.NativeThreadSet r0 = r12.threads     // Catch: java.lang.Throwable -> L51
            int r4 = r0.add()     // Catch: java.lang.Throwable -> L51
            boolean r0 = r12.isOpen()     // Catch: java.lang.Throwable -> L4d
            if (r0 != 0) goto L1b
            sun.nio.ch.NativeThreadSet r13 = r12.threads
            r13.remove(r4)
            r12.endBlocking(r3)
            return r2
        L1b:
            r2 = 0
        L1c:
            java.io.FileDescriptor r5 = r12.fd     // Catch: java.lang.Throwable -> L48
            boolean r9 = r12.direct     // Catch: java.lang.Throwable -> L48
            int r10 = r12.alignment     // Catch: java.lang.Throwable -> L48
            sun.nio.ch.FileDispatcher r11 = r12.nd     // Catch: java.lang.Throwable -> L48
            r6 = r13
            r7 = r14
            int r2 = sun.nio.ch.IOUtil.write(r5, r6, r7, r9, r10, r11)     // Catch: java.lang.Throwable -> L48
            r13 = -3
            if (r2 != r13) goto L37
            boolean r13 = r12.isOpen()     // Catch: java.lang.Throwable -> L48
            if (r13 != 0) goto L34
            goto L37
        L34:
            r13 = r6
            r14 = r7
            goto L1c
        L37:
            int r13 = sun.nio.ch.IOStatus.normalize(r2)     // Catch: java.lang.Throwable -> L48
            sun.nio.ch.NativeThreadSet r14 = r12.threads
            r14.remove(r4)
            if (r2 <= 0) goto L43
            goto L44
        L43:
            r1 = 0
        L44:
            r12.endBlocking(r1)
            return r13
        L48:
            r0 = move-exception
            r13 = r0
            r14 = r2
            r2 = r4
            goto L54
        L4d:
            r0 = move-exception
            r13 = r0
            r2 = r4
            goto L53
        L51:
            r0 = move-exception
            r13 = r0
        L53:
            r14 = 0
        L54:
            sun.nio.ch.NativeThreadSet r15 = r12.threads
            r15.remove(r2)
            if (r14 <= 0) goto L5c
            goto L5d
        L5c:
            r1 = 0
        L5d:
            r12.endBlocking(r1)
            goto L62
        L61:
            throw r13
        L62:
            goto L61
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.FileChannelImpl.writeInternal(java.nio.ByteBuffer, long):int");
    }

    private static class Unmapper implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static volatile int count;
        private static final NativeDispatcher nd = new FileDispatcherImpl();
        static volatile long totalCapacity;
        static volatile long totalSize;
        private volatile long address;
        private final int cap;
        private final FileDescriptor fd;
        private final long size;

        /* synthetic */ Unmapper(long j, long j2, int i, FileDescriptor fileDescriptor, FileChannelImpl-IA r7) {
            this(j, j2, i, fileDescriptor);
        }

        private Unmapper(long j, long j2, int i, FileDescriptor fileDescriptor) {
            this.address = j;
            this.size = j2;
            this.cap = i;
            this.fd = fileDescriptor;
            synchronized (Unmapper.class) {
                count++;
                totalSize += j2;
                totalCapacity += i;
            }
        }

        public void run() {
            if (this.address == 0) {
                return;
            }
            FileChannelImpl.-$$Nest$smunmap0(this.address, this.size);
            this.address = 0L;
            if (this.fd.valid()) {
                try {
                    nd.close(this.fd);
                } catch (IOException unused) {
                }
            }
            synchronized (Unmapper.class) {
                count--;
                totalSize -= this.size;
                totalCapacity -= this.cap;
            }
        }
    }

    private static void unmap(MappedByteBuffer mappedByteBuffer) {
        jdk.internal.ref.Cleaner cleaner = ((DirectBuffer) mappedByteBuffer).cleaner();
        if (cleaner != null) {
            cleaner.clean();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v32, types: [sun.nio.ch.FileDispatcher] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.FileDescriptor] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    public MappedByteBuffer map(FileChannel.MapMode mapMode, long j, long j2) throws IOException {
        long size;
        ?? r3;
        long j3;
        MappedByteBuffer newMappedByteBufferR;
        boolean checkAll;
        FileChannelImpl fileChannelImpl = this;
        fileChannelImpl.ensureOpen();
        if (mapMode == null) {
            throw new NullPointerException("Mode is null");
        }
        long j4 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("Negative position");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("Negative size");
        }
        long j5 = j + j2;
        if (j5 < 0) {
            throw new IllegalArgumentException("Position + size overflow");
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException("Size exceeds Integer.MAX_VALUE");
        }
        int i = -1;
        int i2 = mapMode == FileChannel.MapMode.READ_ONLY ? 0 : mapMode == FileChannel.MapMode.READ_WRITE ? 1 : mapMode == FileChannel.MapMode.PRIVATE ? 2 : -1;
        if (mapMode != FileChannel.MapMode.READ_ONLY && !fileChannelImpl.writable) {
            throw new NonWritableChannelException();
        }
        if (!fileChannelImpl.readable) {
            throw new NonReadableChannelException();
        }
        long j6 = -1;
        try {
            fileChannelImpl.beginBlocking();
            i = fileChannelImpl.threads.add();
            if (fileChannelImpl.isOpen()) {
                synchronized (fileChannelImpl.positionLock) {
                    do {
                        try {
                            try {
                                size = fileChannelImpl.nd.size(fileChannelImpl.fd);
                                if (size != -3) {
                                    break;
                                }
                            } catch (Throwable th) {
                                th = th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } while (fileChannelImpl.isOpen());
                    if (fileChannelImpl.isOpen()) {
                        if (size < j5) {
                            if (!fileChannelImpl.writable) {
                                throw new IOException("Channel not open for writing - cannot extend file to required size");
                            }
                            while (fileChannelImpl.nd.truncate(fileChannelImpl.fd, j5) == -3 && fileChannelImpl.isOpen()) {
                            }
                            if (!fileChannelImpl.isOpen()) {
                            }
                        }
                        if (j2 == 0) {
                            FileDescriptor fileDescriptor = new FileDescriptor();
                            newMappedByteBufferR = (!fileChannelImpl.writable || i2 == 0) ? Util.newMappedByteBufferR(0, 0L, fileDescriptor, null) : Util.newMappedByteBuffer(0, 0L, fileDescriptor, null);
                            fileChannelImpl.threads.remove(i);
                            checkAll = IOStatus.checkAll(0L);
                        } else {
                            long j7 = (int) (j % allocationGranularity);
                            long j8 = j - j7;
                            long j9 = j2 + j7;
                            int i3 = i2;
                            try {
                                try {
                                    long map0 = fileChannelImpl.map0(i3, j8, j9);
                                    fileChannelImpl = this;
                                    r3 = map0;
                                } catch (OutOfMemoryError unused) {
                                    System.gc();
                                    try {
                                        Thread.sleep(100L);
                                    } catch (InterruptedException unused2) {
                                        Thread.currentThread().interrupt();
                                    }
                                    fileChannelImpl = this;
                                    try {
                                        r3 = fileChannelImpl.map0(i3, j8, j9);
                                    } catch (OutOfMemoryError e) {
                                        throw new IOException("Map failed", e);
                                    }
                                }
                                j3 = r3;
                            } catch (Throwable th3) {
                                th = th3;
                                fileChannelImpl = this;
                                j4 = -1;
                                try {
                                    throw th;
                                } catch (Throwable th4) {
                                    th = th4;
                                    j6 = j4;
                                    fileChannelImpl.threads.remove(i);
                                    fileChannelImpl.endBlocking(IOStatus.checkAll(j6));
                                    throw th;
                                }
                            }
                            try {
                                try {
                                    try {
                                        try {
                                            ?? r0 = fileChannelImpl.nd;
                                            r3 = fileChannelImpl.fd;
                                            FileDescriptor duplicateForMapping = r0.duplicateForMapping(r3);
                                            int i4 = (int) j2;
                                            Unmapper unmapper = new Unmapper(j3, j9, i4, duplicateForMapping, null);
                                            newMappedByteBufferR = (!fileChannelImpl.writable || i3 == 0) ? Util.newMappedByteBufferR(i4, j3 + j7, duplicateForMapping, unmapper) : Util.newMappedByteBuffer(i4, j3 + j7, duplicateForMapping, unmapper);
                                            fileChannelImpl.threads.remove(i);
                                            checkAll = IOStatus.checkAll(j3);
                                        } catch (Throwable th5) {
                                            th = th5;
                                            j6 = r3;
                                            fileChannelImpl.threads.remove(i);
                                            fileChannelImpl.endBlocking(IOStatus.checkAll(j6));
                                            throw th;
                                        }
                                    } catch (Throwable th6) {
                                        th = th6;
                                        r3 = j3;
                                        j6 = r3;
                                        fileChannelImpl.threads.remove(i);
                                        fileChannelImpl.endBlocking(IOStatus.checkAll(j6));
                                        throw th;
                                    }
                                } catch (IOException e2) {
                                    unmap0(j3, j9);
                                    throw e2;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                j4 = j3;
                                throw th;
                            }
                        }
                        fileChannelImpl.endBlocking(checkAll);
                        return newMappedByteBufferR;
                    }
                }
            }
            fileChannelImpl.threads.remove(i);
            fileChannelImpl.endBlocking(IOStatus.checkAll(-1L));
            return null;
        } catch (Throwable th8) {
            th = th8;
        }
    }

    class 1 implements JavaNioAccess.BufferPool {
        1() {
        }

        public String getName() {
            return "mapped";
        }

        public long getCount() {
            return Unmapper.count;
        }

        public long getTotalCapacity() {
            return Unmapper.totalCapacity;
        }

        public long getMemoryUsed() {
            return Unmapper.totalSize;
        }
    }

    public static JavaNioAccess.BufferPool getMappedBufferPool() {
        return new 1();
    }

    private FileLockTable fileLockTable() throws IOException {
        if (this.fileLockTable == null) {
            synchronized (this) {
                if (this.fileLockTable == null) {
                    int add = this.threads.add();
                    try {
                        ensureOpen();
                        this.fileLockTable = new FileLockTable(this, this.fd);
                        this.threads.remove(add);
                    } catch (Throwable th) {
                        this.threads.remove(add);
                        throw th;
                    }
                }
            }
        }
        return this.fileLockTable;
    }

    public FileLock lock(long j, long j2, boolean z) throws IOException {
        FileLockTable fileLockTable;
        boolean z2;
        int lock;
        int i;
        FileLockImpl fileLockImpl;
        boolean z3;
        ensureOpen();
        if (z && !this.readable) {
            throw new NonReadableChannelException();
        }
        if (!z && !this.writable) {
            throw new NonWritableChannelException();
        }
        FileLockImpl fileLockImpl2 = new FileLockImpl(this, j, j2, z);
        FileLockTable fileLockTable2 = fileLockTable();
        fileLockTable2.add(fileLockImpl2);
        int i2 = -1;
        try {
            beginBlocking();
            i2 = this.threads.add();
        } catch (Throwable th) {
            th = th;
            fileLockTable = fileLockTable2;
        }
        try {
            if (isOpen()) {
                do {
                    lock = this.nd.lock(this.fd, true, j, j2, z);
                    if (lock != 2) {
                        break;
                    }
                } while (isOpen());
                if (isOpen()) {
                    if (lock == 1) {
                        fileLockTable = fileLockTable2;
                        i = i2;
                        z2 = false;
                        try {
                            fileLockImpl = new FileLockImpl((FileChannel) this, j, j2, false);
                            fileLockTable.replace(fileLockImpl2, fileLockImpl);
                        } catch (Throwable th2) {
                            th = th2;
                            i2 = i;
                            fileLockTable.remove(fileLockImpl2);
                            this.threads.remove(i2);
                            try {
                                endBlocking(z2);
                                throw th;
                            } catch (ClosedByInterruptException unused) {
                                throw new FileLockInterruptionException();
                            }
                        }
                    } else {
                        fileLockTable = fileLockTable2;
                        i = i2;
                        fileLockImpl = fileLockImpl2;
                    }
                    z3 = true;
                } else {
                    fileLockTable = fileLockTable2;
                    i = i2;
                    fileLockImpl = fileLockImpl2;
                    z3 = false;
                }
                if (!z3) {
                    fileLockTable.remove(fileLockImpl);
                }
                this.threads.remove(i);
                try {
                    endBlocking(z3);
                    return fileLockImpl;
                } catch (ClosedByInterruptException unused2) {
                    throw new FileLockInterruptionException();
                }
            }
            fileLockTable2.remove(fileLockImpl2);
            this.threads.remove(i2);
            try {
                endBlocking(false);
                return null;
            } catch (ClosedByInterruptException unused3) {
                throw new FileLockInterruptionException();
            }
        } catch (Throwable th3) {
            th = th3;
            fileLockTable = fileLockTable2;
            z2 = false;
            fileLockTable.remove(fileLockImpl2);
            this.threads.remove(i2);
            endBlocking(z2);
            throw th;
        }
    }

    public FileLock tryLock(long j, long j2, boolean z) throws IOException {
        ensureOpen();
        if (z && !this.readable) {
            throw new NonReadableChannelException();
        }
        if (!z && !this.writable) {
            throw new NonWritableChannelException();
        }
        FileLockImpl fileLockImpl = new FileLockImpl(this, j, j2, z);
        FileLockTable fileLockTable = fileLockTable();
        fileLockTable.add(fileLockImpl);
        int add = this.threads.add();
        try {
            try {
                ensureOpen();
                int lock = this.nd.lock(this.fd, false, j, j2, z);
                if (lock == -1) {
                    fileLockTable.remove(fileLockImpl);
                    this.threads.remove(add);
                    return null;
                }
                if (lock != 1) {
                    this.threads.remove(add);
                    return fileLockImpl;
                }
                FileLockImpl fileLockImpl2 = new FileLockImpl((FileChannel) this, j, j2, false);
                fileLockTable.replace(fileLockImpl, fileLockImpl2);
                this.threads.remove(add);
                return fileLockImpl2;
            } catch (IOException e) {
                fileLockTable.remove(fileLockImpl);
                throw e;
            }
        } catch (Throwable th) {
            this.threads.remove(add);
            throw th;
        }
    }

    void release(FileLockImpl fileLockImpl) throws IOException {
        int add = this.threads.add();
        try {
            ensureOpen();
            this.nd.release(this.fd, fileLockImpl.position(), fileLockImpl.size());
            this.threads.remove(add);
            this.fileLockTable.remove(fileLockImpl);
        } catch (Throwable th) {
            this.threads.remove(add);
            throw th;
        }
    }
}
