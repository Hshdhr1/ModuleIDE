package okio;

import java.io.IOException;
import javax.annotation.Nullable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes55.dex */
public final class Pipe {

    @Nullable
    private Sink foldedSink;
    final long maxBufferSize;
    boolean sinkClosed;
    boolean sourceClosed;
    final Buffer buffer = new Buffer();
    private final Sink sink = new PipeSink();
    private final Source source = new PipeSource();

    static /* synthetic */ Sink access$000(Pipe x0) {
        return x0.foldedSink;
    }

    public Pipe(long maxBufferSize) {
        if (maxBufferSize < 1) {
            throw new IllegalArgumentException("maxBufferSize < 1: " + maxBufferSize);
        }
        this.maxBufferSize = maxBufferSize;
    }

    public final Source source() {
        return this.source;
    }

    public final Sink sink() {
        return this.sink;
    }

    public void fold(Sink sink) throws IOException {
        boolean closed;
        Buffer sinkBuffer;
        while (true) {
            synchronized (this.buffer) {
                if (this.foldedSink != null) {
                    throw new IllegalStateException("sink already folded");
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = sink;
                    return;
                } else {
                    closed = this.sinkClosed;
                    sinkBuffer = new Buffer();
                    sinkBuffer.write(this.buffer, this.buffer.size);
                    this.buffer.notifyAll();
                }
            }
            try {
                sink.write(sinkBuffer, sinkBuffer.size);
                if (closed) {
                    sink.close();
                } else {
                    sink.flush();
                }
                if (1 == 0) {
                    synchronized (this.buffer) {
                        this.sourceClosed = true;
                        this.buffer.notifyAll();
                    }
                }
            } catch (Throwable th) {
                if (0 == 0) {
                    synchronized (this.buffer) {
                        this.sourceClosed = true;
                        this.buffer.notifyAll();
                    }
                }
                throw th;
            }
        }
    }

    final class PipeSink implements Sink {
        final PushableTimeout timeout = new PushableTimeout();

        PipeSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            Sink delegate = null;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                while (true) {
                    if (byteCount <= 0) {
                        break;
                    }
                    if (Pipe.access$000(Pipe.this) != null) {
                        delegate = Pipe.access$000(Pipe.this);
                        break;
                    }
                    if (Pipe.this.sourceClosed) {
                        throw new IOException("source is closed");
                    }
                    long bufferSpaceAvailable = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
                    if (bufferSpaceAvailable == 0) {
                        this.timeout.waitUntilNotified(Pipe.this.buffer);
                    } else {
                        long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount);
                        Pipe.this.buffer.write(source, bytesToWrite);
                        byteCount -= bytesToWrite;
                        Pipe.this.buffer.notifyAll();
                    }
                }
            }
            if (delegate != null) {
                this.timeout.push(delegate.timeout());
                try {
                    delegate.write(source, byteCount);
                } finally {
                    this.timeout.pop();
                }
            }
        }

        public void flush() throws IOException {
            Sink delegate = null;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                }
                if (Pipe.access$000(Pipe.this) != null) {
                    delegate = Pipe.access$000(Pipe.this);
                } else if (Pipe.this.sourceClosed && Pipe.this.buffer.size() > 0) {
                    throw new IOException("source is closed");
                }
            }
            if (delegate != null) {
                this.timeout.push(delegate.timeout());
                try {
                    delegate.flush();
                } finally {
                    this.timeout.pop();
                }
            }
        }

        public void close() throws IOException {
            Sink delegate = null;
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sinkClosed) {
                    if (Pipe.access$000(Pipe.this) != null) {
                        delegate = Pipe.access$000(Pipe.this);
                    } else {
                        if (Pipe.this.sourceClosed && Pipe.this.buffer.size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe.this.sinkClosed = true;
                        Pipe.this.buffer.notifyAll();
                    }
                    if (delegate != null) {
                        this.timeout.push(delegate.timeout());
                        try {
                            delegate.close();
                        } finally {
                            this.timeout.pop();
                        }
                    }
                }
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            long read;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sourceClosed) {
                    throw new IllegalStateException("closed");
                }
                while (true) {
                    if (Pipe.this.buffer.size() == 0) {
                        if (Pipe.this.sinkClosed) {
                            read = -1;
                            break;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.buffer);
                    } else {
                        read = Pipe.this.buffer.read(sink, byteCount);
                        Pipe.this.buffer.notifyAll();
                        break;
                    }
                }
                return read;
            }
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }
}
