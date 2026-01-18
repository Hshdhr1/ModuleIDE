package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes55.dex */
public final class Okio {
    static final Logger logger = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    public static BufferedSource buffer(Source source) {
        return new RealBufferedSource(source);
    }

    public static BufferedSink buffer(Sink sink) {
        return new RealBufferedSink(sink);
    }

    public static Sink sink(OutputStream out) {
        return sink(out, new Timeout());
    }

    private static Sink sink(OutputStream out, Timeout timeout) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (timeout == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new 1(timeout, out);
    }

    final class 1 implements Sink {
        final /* synthetic */ OutputStream val$out;
        final /* synthetic */ Timeout val$timeout;

        1(Timeout timeout, OutputStream outputStream) {
            this.val$timeout = timeout;
            this.val$out = outputStream;
        }

        public void write(Buffer source, long byteCount) throws IOException {
            Util.checkOffsetAndCount(source.size, 0L, byteCount);
            while (byteCount > 0) {
                this.val$timeout.throwIfReached();
                Segment head = source.head;
                int toCopy = (int) Math.min(byteCount, head.limit - head.pos);
                this.val$out.write(head.data, head.pos, toCopy);
                head.pos += toCopy;
                byteCount -= toCopy;
                source.size -= toCopy;
                if (head.pos == head.limit) {
                    source.head = head.pop();
                    SegmentPool.recycle(head);
                }
            }
        }

        public void flush() throws IOException {
            this.val$out.flush();
        }

        public void close() throws IOException {
            this.val$out.close();
        }

        public Timeout timeout() {
            return this.val$timeout;
        }

        public String toString() {
            return "sink(" + this.val$out + ")";
        }
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getOutputStream() == null) {
            throw new IOException("socket's output stream == null");
        }
        AsyncTimeout timeout = timeout(socket);
        Sink sink = sink(socket.getOutputStream(), timeout);
        return timeout.sink(sink);
    }

    public static Source source(InputStream in) {
        return source(in, new Timeout());
    }

    private static Source source(InputStream in, Timeout timeout) {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new 2(timeout, in);
    }

    final class 2 implements Source {
        final /* synthetic */ InputStream val$in;
        final /* synthetic */ Timeout val$timeout;

        2(Timeout timeout, InputStream inputStream) {
            this.val$timeout = timeout;
            this.val$in = inputStream;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            if (byteCount < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            }
            if (byteCount == 0) {
                return 0L;
            }
            try {
                this.val$timeout.throwIfReached();
                Segment tail = sink.writableSegment(1);
                int maxToCopy = (int) Math.min(byteCount, 8192 - tail.limit);
                int bytesRead = this.val$in.read(tail.data, tail.limit, maxToCopy);
                if (bytesRead == -1) {
                    return -1L;
                }
                tail.limit += bytesRead;
                sink.size += bytesRead;
                return bytesRead;
            } catch (AssertionError e) {
                if (Okio.isAndroidGetsocknameError(e)) {
                    throw new IOException(e);
                }
                throw e;
            }
        }

        public void close() throws IOException {
            this.val$in.close();
        }

        public Timeout timeout() {
            return this.val$timeout;
        }

        public String toString() {
            return "source(" + this.val$in + ")";
        }
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return source((InputStream) new FileInputStream(file));
    }

    @IgnoreJRERequirement
    public static Source source(Path path, OpenOption... options) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("path == null");
        }
        return source(Files.newInputStream(path, options));
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return sink((OutputStream) new FileOutputStream(file));
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return sink((OutputStream) new FileOutputStream(file, true));
    }

    @IgnoreJRERequirement
    public static Sink sink(Path path, OpenOption... options) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("path == null");
        }
        return sink(Files.newOutputStream(path, options));
    }

    final class 3 implements Sink {
        3() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            source.skip(byteCount);
        }

        public void flush() throws IOException {
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }

        public void close() throws IOException {
        }
    }

    public static Sink blackhole() {
        return new 3();
    }

    public static Source source(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getInputStream() == null) {
            throw new IOException("socket's input stream == null");
        }
        AsyncTimeout timeout = timeout(socket);
        Source source = source(socket.getInputStream(), timeout);
        return timeout.source(source);
    }

    final class 4 extends AsyncTimeout {
        final /* synthetic */ Socket val$socket;

        4(Socket socket) {
            this.val$socket = socket;
        }

        protected IOException newTimeoutException(@Nullable IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        protected void timedOut() {
            try {
                this.val$socket.close();
            } catch (Exception e) {
                Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, e);
            } catch (AssertionError e2) {
                if (Okio.isAndroidGetsocknameError(e2)) {
                    Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, e2);
                    return;
                }
                throw e2;
            }
        }
    }

    private static AsyncTimeout timeout(Socket socket) {
        return new 4(socket);
    }

    static boolean isAndroidGetsocknameError(AssertionError e) {
        return (e.getCause() == null || e.getMessage() == null || !e.getMessage().contains("getsockname failed")) ? false : true;
    }
}
