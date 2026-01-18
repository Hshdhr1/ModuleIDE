package sun.nio.cs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class StreamEncoder extends Writer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    private ByteBuffer bb;
    private WritableByteChannel ch;
    private volatile boolean closed;
    private Charset cs;
    private CharsetEncoder encoder;
    private boolean haveLeftoverChar;
    private CharBuffer lcb;
    private char leftoverChar;
    private final OutputStream out;

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream outputStream, Object obj, String str) throws UnsupportedEncodingException {
        if (str == null) {
            str = Charset.defaultCharset().name();
        }
        try {
            if (Charset.isSupported(str)) {
                return new StreamEncoder(outputStream, obj, Charset.forName(str));
            }
        } catch (IllegalCharsetNameException unused) {
        }
        throw new UnsupportedEncodingException(str);
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream outputStream, Object obj, Charset charset) {
        return new StreamEncoder(outputStream, obj, charset);
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream outputStream, Object obj, CharsetEncoder charsetEncoder) {
        return new StreamEncoder(outputStream, obj, charsetEncoder);
    }

    public static StreamEncoder forEncoder(WritableByteChannel writableByteChannel, CharsetEncoder charsetEncoder, int i) {
        return new StreamEncoder(writableByteChannel, charsetEncoder, i);
    }

    public String getEncoding() {
        if (isOpen()) {
            return encodingName();
        }
        return null;
    }

    public void flushBuffer() throws IOException {
        synchronized (this.lock) {
            if (isOpen()) {
                implFlushBuffer();
            } else {
                throw new IOException("Stream closed");
            }
        }
    }

    public void write(int i) throws IOException {
        write(new char[]{(char) i}, 0, 1);
    }

    public void write(char[] cArr, int i, int i2) throws IOException {
        int i3;
        synchronized (this.lock) {
            ensureOpen();
            if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) > cArr.length || i3 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return;
            }
            implWrite(cArr, i, i2);
        }
    }

    public void write(String str, int i, int i2) throws IOException {
        if (i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        char[] cArr = new char[i2];
        str.getChars(i, i + i2, cArr, 0);
        write(cArr, 0, i2);
    }

    public void write(CharBuffer charBuffer) throws IOException {
        int position = charBuffer.position();
        try {
            synchronized (this.lock) {
                ensureOpen();
                implWrite(charBuffer);
            }
        } finally {
            charBuffer.position(position);
        }
    }

    public void flush() throws IOException {
        synchronized (this.lock) {
            ensureOpen();
            implFlush();
        }
    }

    public void close() throws IOException {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            implClose();
            this.closed = true;
        }
    }

    private boolean isOpen() {
        return !this.closed;
    }

    private StreamEncoder(OutputStream outputStream, Object obj, Charset charset) {
        this(outputStream, obj, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
    }

    private StreamEncoder(OutputStream outputStream, Object obj, CharsetEncoder charsetEncoder) {
        super(obj);
        this.haveLeftoverChar = false;
        this.lcb = null;
        this.out = outputStream;
        this.ch = null;
        this.cs = charsetEncoder.charset();
        this.encoder = charsetEncoder;
        if (this.ch == null) {
            this.bb = ByteBuffer.allocate(8192);
        }
    }

    private StreamEncoder(WritableByteChannel writableByteChannel, CharsetEncoder charsetEncoder, int i) {
        this.haveLeftoverChar = false;
        this.lcb = null;
        this.out = null;
        this.ch = writableByteChannel;
        this.cs = charsetEncoder.charset();
        this.encoder = charsetEncoder;
        this.bb = ByteBuffer.allocate(i < 0 ? 8192 : i);
    }

    private void writeBytes() throws IOException {
        this.bb.flip();
        int limit = this.bb.limit();
        int position = this.bb.position();
        int i = position <= limit ? limit - position : 0;
        if (i > 0) {
            WritableByteChannel writableByteChannel = this.ch;
            if (writableByteChannel != null) {
                writableByteChannel.write(this.bb);
            } else {
                this.out.write(this.bb.array(), this.bb.arrayOffset() + position, i);
            }
        }
        this.bb.clear();
    }

    private void flushLeftoverChar(CharBuffer charBuffer, boolean z) throws IOException {
        if (this.haveLeftoverChar || z) {
            CharBuffer charBuffer2 = this.lcb;
            if (charBuffer2 == null) {
                this.lcb = CharBuffer.allocate(2);
            } else {
                charBuffer2.clear();
            }
            if (this.haveLeftoverChar) {
                this.lcb.put(this.leftoverChar);
            }
            if (charBuffer != null && charBuffer.hasRemaining()) {
                this.lcb.put(charBuffer.get());
            }
            this.lcb.flip();
            while (true) {
                if (!this.lcb.hasRemaining() && !z) {
                    break;
                }
                CoderResult encode = this.encoder.encode(this.lcb, this.bb, z);
                if (encode.isUnderflow()) {
                    if (!this.lcb.hasRemaining()) {
                        break;
                    }
                    this.leftoverChar = this.lcb.get();
                    if (charBuffer == null || !charBuffer.hasRemaining()) {
                        return;
                    }
                    this.lcb.clear();
                    this.lcb.put(this.leftoverChar).put(charBuffer.get()).flip();
                } else if (encode.isOverflow()) {
                    writeBytes();
                } else {
                    encode.throwException();
                }
            }
            this.haveLeftoverChar = false;
        }
    }

    void implWrite(char[] cArr, int i, int i2) throws IOException {
        implWrite(CharBuffer.wrap(cArr, i, i2));
    }

    void implWrite(CharBuffer charBuffer) throws IOException {
        if (this.haveLeftoverChar) {
            flushLeftoverChar(charBuffer, false);
        }
        while (charBuffer.hasRemaining()) {
            CoderResult encode = this.encoder.encode(charBuffer, this.bb, false);
            if (encode.isUnderflow()) {
                if (charBuffer.remaining() == 1) {
                    this.haveLeftoverChar = true;
                    this.leftoverChar = charBuffer.get();
                    return;
                }
                return;
            }
            if (encode.isOverflow()) {
                writeBytes();
            } else {
                encode.throwException();
            }
        }
    }

    void implFlushBuffer() throws IOException {
        if (this.bb.position() > 0) {
            writeBytes();
        }
    }

    void implFlush() throws IOException {
        implFlushBuffer();
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    void implClose() throws IOException {
        flushLeftoverChar(null, true);
        while (true) {
            try {
                CoderResult flush = this.encoder.flush(this.bb);
                if (flush.isUnderflow()) {
                    break;
                } else if (flush.isOverflow()) {
                    writeBytes();
                } else {
                    flush.throwException();
                }
            } catch (IOException e) {
                this.encoder.reset();
                throw e;
            }
        }
        if (this.bb.position() > 0) {
            writeBytes();
        }
        WritableByteChannel writableByteChannel = this.ch;
        if (writableByteChannel != null) {
            writableByteChannel.close();
        } else {
            this.out.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    String encodingName() {
        Charset charset = this.cs;
        if (charset instanceof HistoricallyNamedCharset) {
            return ((HistoricallyNamedCharset) charset).historicalName();
        }
        return charset.name();
    }
}
