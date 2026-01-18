package java.nio.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class FileChannelLinesSpliterator implements Spliterator {
    static final Set SUPPORTED_CHARSET_NAMES;
    private ByteBuffer buffer;
    private final Charset cs;
    private final FileChannel fc;
    private final int fence;
    private int index;
    private BufferedReader reader;

    static /* bridge */ /* synthetic */ FileChannel -$$Nest$fgetfc(FileChannelLinesSpliterator fileChannelLinesSpliterator) {
        return fileChannelLinesSpliterator.fc;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgetfence(FileChannelLinesSpliterator fileChannelLinesSpliterator) {
        return fileChannelLinesSpliterator.fence;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgetindex(FileChannelLinesSpliterator fileChannelLinesSpliterator) {
        return fileChannelLinesSpliterator.index;
    }

    static /* bridge */ /* synthetic */ void -$$Nest$fputindex(FileChannelLinesSpliterator fileChannelLinesSpliterator, int i) {
        fileChannelLinesSpliterator.index = i;
    }

    public int characteristics() {
        return 272;
    }

    public /* synthetic */ Comparator getComparator() {
        return Spliterator.-CC.$default$getComparator(this);
    }

    public long getExactSizeIfKnown() {
        return -1L;
    }

    public /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.-CC.$default$hasCharacteristics(this, i);
    }

    static {
        HashSet hashSet = new HashSet();
        SUPPORTED_CHARSET_NAMES = hashSet;
        hashSet.add("UTF-8");
        hashSet.add("ISO-8859-1");
        hashSet.add("US-ASCII");
    }

    FileChannelLinesSpliterator(FileChannel fileChannel, Charset charset, int i, int i2) {
        this.fc = fileChannel;
        this.cs = charset;
        this.index = i;
        this.fence = i2;
    }

    private FileChannelLinesSpliterator(FileChannel fileChannel, Charset charset, int i, int i2, ByteBuffer byteBuffer) {
        this.fc = fileChannel;
        this.buffer = byteBuffer;
        this.cs = charset;
        this.index = i;
        this.fence = i2;
    }

    public boolean tryAdvance(Consumer consumer) {
        String readLine = readLine();
        if (readLine == null) {
            return false;
        }
        consumer.accept(readLine);
        return true;
    }

    public void forEachRemaining(Consumer consumer) {
        while (true) {
            String readLine = readLine();
            if (readLine == null) {
                return;
            } else {
                consumer.accept(readLine);
            }
        }
    }

    class 1 implements ReadableByteChannel {
        1() {
        }

        public int read(ByteBuffer byteBuffer) throws IOException {
            int read;
            int i = FileChannelLinesSpliterator.-$$Nest$fgetfence(FileChannelLinesSpliterator.this) - FileChannelLinesSpliterator.-$$Nest$fgetindex(FileChannelLinesSpliterator.this);
            if (i == 0) {
                return -1;
            }
            if (i < byteBuffer.remaining()) {
                int limit = byteBuffer.limit();
                byteBuffer.limit(byteBuffer.position() + i);
                read = FileChannelLinesSpliterator.-$$Nest$fgetfc(FileChannelLinesSpliterator.this).read(byteBuffer, FileChannelLinesSpliterator.-$$Nest$fgetindex(FileChannelLinesSpliterator.this));
                byteBuffer.limit(limit);
            } else {
                read = FileChannelLinesSpliterator.-$$Nest$fgetfc(FileChannelLinesSpliterator.this).read(byteBuffer, FileChannelLinesSpliterator.-$$Nest$fgetindex(FileChannelLinesSpliterator.this));
            }
            if (read == -1) {
                FileChannelLinesSpliterator fileChannelLinesSpliterator = FileChannelLinesSpliterator.this;
                FileChannelLinesSpliterator.-$$Nest$fputindex(fileChannelLinesSpliterator, FileChannelLinesSpliterator.-$$Nest$fgetfence(fileChannelLinesSpliterator));
                return read;
            }
            FileChannelLinesSpliterator fileChannelLinesSpliterator2 = FileChannelLinesSpliterator.this;
            FileChannelLinesSpliterator.-$$Nest$fputindex(fileChannelLinesSpliterator2, FileChannelLinesSpliterator.-$$Nest$fgetindex(fileChannelLinesSpliterator2) + read);
            return read;
        }

        public boolean isOpen() {
            return FileChannelLinesSpliterator.-$$Nest$fgetfc(FileChannelLinesSpliterator.this).isOpen();
        }

        public void close() throws IOException {
            FileChannelLinesSpliterator.-$$Nest$fgetfc(FileChannelLinesSpliterator.this).close();
        }
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(Channels.newReader(new 1(), this.cs.newDecoder(), -1));
    }

    private String readLine() {
        if (this.reader == null) {
            this.reader = getBufferedReader();
            this.buffer = null;
        }
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ByteBuffer getMappedByteBuffer() {
        try {
            return this.fc.map(FileChannel.MapMode.READ_ONLY, 0L, this.fence);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0031, code lost:
    
        if (r7.get(r3) == 10) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0033, code lost:
    
        r2 = r2 + 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0061, code lost:
    
        if (r7.get(r3) == 10) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Spliterator trySplit() {
        /*
            r10 = this;
            java.io.BufferedReader r0 = r10.reader
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            java.nio.ByteBuffer r0 = r10.buffer
            if (r0 != 0) goto L10
            java.nio.ByteBuffer r0 = r10.getMappedByteBuffer()
            r10.buffer = r0
        L10:
            r7 = r0
            int r0 = r10.fence
            int r5 = r10.index
            int r2 = r5 + r0
            int r2 = r2 >>> 1
            byte r3 = r7.get(r2)
            r4 = 10
            if (r3 != r4) goto L25
            int r2 = r2 + 1
        L23:
            r6 = r2
            goto L69
        L25:
            r6 = 13
            if (r3 != r6) goto L38
            int r3 = r2 + 1
            if (r3 >= r0) goto L36
            byte r6 = r7.get(r3)
            if (r6 != r4) goto L36
        L33:
            int r2 = r2 + 2
            goto L23
        L36:
            r6 = r3
            goto L69
        L38:
            int r3 = r2 + (-1)
            int r2 = r2 + 1
        L3c:
            if (r3 <= r5) goto L67
            if (r2 >= r0) goto L67
            int r8 = r3 + (-1)
            byte r9 = r7.get(r3)
            if (r9 == r4) goto L64
            if (r9 != r6) goto L4b
            goto L64
        L4b:
            int r3 = r2 + 1
            byte r9 = r7.get(r2)
            if (r9 == r4) goto L59
            if (r9 != r6) goto L56
            goto L59
        L56:
            r2 = r3
            r3 = r8
            goto L3c
        L59:
            if (r9 != r6) goto L36
            if (r3 >= r0) goto L36
            byte r6 = r7.get(r3)
            if (r6 != r4) goto L36
            goto L33
        L64:
            int r2 = r3 + 1
            goto L23
        L67:
            r2 = 0
            r6 = 0
        L69:
            if (r6 <= r5) goto L79
            if (r6 >= r0) goto L79
            java.nio.file.FileChannelLinesSpliterator r2 = new java.nio.file.FileChannelLinesSpliterator
            java.nio.channels.FileChannel r3 = r10.fc
            java.nio.charset.Charset r4 = r10.cs
            r10.index = r6
            r2.<init>(r3, r4, r5, r6, r7)
            return r2
        L79:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java.nio.file.FileChannelLinesSpliterator.trySplit():java.util.Spliterator");
    }

    public long estimateSize() {
        return this.fence - this.index;
    }
}
