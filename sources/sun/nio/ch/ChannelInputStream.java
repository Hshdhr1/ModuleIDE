package sun.nio.ch;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectableChannel;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class ChannelInputStream extends InputStream {
    protected final ReadableByteChannel ch;
    private ByteBuffer bb = null;
    private byte[] bs = null;
    private byte[] b1 = null;

    public static int read(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer, boolean z) throws IOException {
        int read;
        if (readableByteChannel instanceof SelectableChannel) {
            SelectableChannel selectableChannel = (SelectableChannel) readableByteChannel;
            synchronized (selectableChannel.blockingLock()) {
                boolean isBlocking = selectableChannel.isBlocking();
                if (!isBlocking) {
                    throw new IllegalBlockingModeException();
                }
                if (isBlocking != z) {
                    selectableChannel.configureBlocking(z);
                }
                read = readableByteChannel.read(byteBuffer);
                if (isBlocking != z) {
                    selectableChannel.configureBlocking(isBlocking);
                }
            }
            return read;
        }
        return readableByteChannel.read(byteBuffer);
    }

    public ChannelInputStream(ReadableByteChannel readableByteChannel) {
        this.ch = readableByteChannel;
    }

    public synchronized int read() throws IOException {
        if (this.b1 == null) {
            this.b1 = new byte[1];
        }
        if (read(this.b1) != 1) {
            return -1;
        }
        return this.b1[0] & 255;
    }

    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        ByteBuffer wrap;
        if (i >= 0) {
            if (i <= bArr.length && i2 >= 0 && (i3 = i + i2) <= bArr.length && i3 >= 0) {
                if (i2 == 0) {
                    return 0;
                }
                if (this.bs == bArr) {
                    wrap = this.bb;
                } else {
                    wrap = ByteBuffer.wrap(bArr);
                }
                wrap.limit(Math.min(i3, wrap.capacity()));
                wrap.position(i);
                this.bb = wrap;
                this.bs = bArr;
                return read(wrap);
            }
        }
        throw new IndexOutOfBoundsException();
    }

    protected int read(ByteBuffer byteBuffer) throws IOException {
        return read(this.ch, byteBuffer, true);
    }

    public int available() throws IOException {
        SeekableByteChannel seekableByteChannel = this.ch;
        if (!(seekableByteChannel instanceof SeekableByteChannel)) {
            return 0;
        }
        SeekableByteChannel seekableByteChannel2 = seekableByteChannel;
        long max = Math.max(0L, seekableByteChannel2.size() - seekableByteChannel2.position());
        if (max > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) max;
    }

    public void close() throws IOException {
        this.ch.close();
    }
}
