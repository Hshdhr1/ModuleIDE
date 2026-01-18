package java.io;

import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class BufferedInputStream extends FilterInputStream {
    private static final long BUF_OFFSET;
    private static int DEFAULT_BUFFER_SIZE = 8192;
    private static int MAX_BUFFER_SIZE = 2147483639;
    private static final Unsafe U;
    protected volatile byte[] buf;
    protected int count;
    protected int marklimit;
    protected int markpos;
    protected int pos;

    public boolean markSupported() {
        return true;
    }

    static {
        Unsafe unsafe = Unsafe.getUnsafe();
        U = unsafe;
        BUF_OFFSET = unsafe.objectFieldOffset(BufferedInputStream.class, "buf");
    }

    private InputStream getInIfOpen() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            return inputStream;
        }
        throw new IOException("Stream closed");
    }

    private byte[] getBufIfOpen() throws IOException {
        byte[] bArr = this.buf;
        if (bArr != null) {
            return bArr;
        }
        throw new IOException("Stream closed");
    }

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, DEFAULT_BUFFER_SIZE);
    }

    public BufferedInputStream(InputStream inputStream, int i) {
        super(inputStream);
        this.markpos = -1;
        if (i <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.buf = new byte[i];
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void fill() throws java.io.IOException {
        /*
            r6 = this;
            byte[] r4 = r6.getBufIfOpen()
            int r0 = r6.markpos
            r1 = 0
            if (r0 >= 0) goto Lc
            r6.pos = r1
            goto L59
        Lc:
            int r2 = r6.pos
            int r3 = r4.length
            if (r2 < r3) goto L59
            if (r0 <= 0) goto L1c
            int r2 = r2 - r0
            java.lang.System.arraycopy(r4, r0, r4, r1, r2)
            r6.pos = r2
            r6.markpos = r1
            goto L59
        L1c:
            int r0 = r4.length
            int r3 = r6.marklimit
            if (r0 < r3) goto L27
            r0 = -1
            r6.markpos = r0
            r6.pos = r1
            goto L59
        L27:
            int r0 = r4.length
            int r5 = java.io.BufferedInputStream.MAX_BUFFER_SIZE
            if (r0 >= r5) goto L50
            int r0 = r5 - r2
            if (r2 > r0) goto L32
            int r5 = r2 * 2
        L32:
            if (r5 <= r3) goto L35
            goto L36
        L35:
            r3 = r5
        L36:
            byte[] r5 = new byte[r3]
            java.lang.System.arraycopy(r4, r1, r5, r1, r2)
            jdk.internal.misc.Unsafe r0 = java.io.BufferedInputStream.U
            long r2 = java.io.BufferedInputStream.BUF_OFFSET
            r1 = r6
            boolean r0 = r0.compareAndSetObject(r1, r2, r4, r5)
            if (r0 == 0) goto L48
            r4 = r5
            goto L5a
        L48:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r2 = "Stream closed"
            r0.<init>(r2)
            throw r0
        L50:
            r1 = r6
            java.lang.OutOfMemoryError r0 = new java.lang.OutOfMemoryError
            java.lang.String r2 = "Required array size too large"
            r0.<init>(r2)
            throw r0
        L59:
            r1 = r6
        L5a:
            int r0 = r1.pos
            r1.count = r0
            java.io.InputStream r0 = r6.getInIfOpen()
            int r2 = r1.pos
            int r3 = r4.length
            int r3 = r3 - r2
            int r0 = r0.read(r4, r2, r3)
            if (r0 <= 0) goto L71
            int r2 = r1.pos
            int r0 = r0 + r2
            r1.count = r0
        L71:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java.io.BufferedInputStream.fill():void");
    }

    public synchronized int read() throws IOException {
        if (this.pos >= this.count) {
            fill();
            if (this.pos >= this.count) {
                return -1;
            }
        }
        byte[] bufIfOpen = getBufIfOpen();
        int i = this.pos;
        this.pos = i + 1;
        return bufIfOpen[i] & 255;
    }

    private int read1(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.count - this.pos;
        if (i3 <= 0) {
            if (i2 >= getBufIfOpen().length && this.markpos < 0) {
                return getInIfOpen().read(bArr, i, i2);
            }
            fill();
            i3 = this.count - this.pos;
            if (i3 <= 0) {
                return -1;
            }
        }
        if (i3 < i2) {
            i2 = i3;
        }
        System.arraycopy(getBufIfOpen(), this.pos, bArr, i, i2);
        this.pos += i2;
        return i2;
    }

    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        getBufIfOpen();
        int i3 = i + i2;
        if ((i | i2 | i3 | (bArr.length - i3)) < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        if (i2 == 0) {
            return 0;
        }
        while (true) {
            int read1 = read1(bArr, i + i4, i2 - i4);
            if (read1 <= 0) {
                if (i4 == 0) {
                    i4 = read1;
                }
                return i4;
            }
            i4 += read1;
            if (i4 >= i2) {
                return i4;
            }
            InputStream inputStream = this.in;
            if (inputStream != null && inputStream.available() <= 0) {
                return i4;
            }
        }
    }

    public synchronized long skip(long j) throws IOException {
        getBufIfOpen();
        if (j <= 0) {
            return 0L;
        }
        long j2 = this.count - this.pos;
        if (j2 <= 0) {
            if (this.markpos < 0) {
                return getInIfOpen().skip(j);
            }
            fill();
            j2 = this.count - this.pos;
            if (j2 <= 0) {
                return 0L;
            }
        }
        if (j2 < j) {
            j = j2;
        }
        this.pos = (int) (this.pos + j);
        return j;
    }

    public synchronized int available() throws IOException {
        int i;
        int available;
        i = this.count - this.pos;
        available = getInIfOpen().available();
        return i <= Integer.MAX_VALUE - available ? i + available : Integer.MAX_VALUE;
    }

    public synchronized void mark(int i) {
        this.marklimit = i;
        this.markpos = this.pos;
    }

    public synchronized void reset() throws IOException {
        getBufIfOpen();
        int i = this.markpos;
        if (i < 0) {
            throw new IOException("Resetting to invalid mark");
        }
        this.pos = i;
    }

    public void close() throws IOException {
        byte[] bArr;
        do {
            bArr = this.buf;
            if (bArr == null) {
                return;
            }
        } while (!U.compareAndSetObject(this, BUF_OFFSET, bArr, (Object) null));
        InputStream inputStream = this.in;
        this.in = null;
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
