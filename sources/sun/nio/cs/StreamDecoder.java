package sun.nio.cs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class StreamDecoder extends Reader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    private static final int MIN_BYTE_BUFFER_SIZE = 32;
    private static volatile boolean channelsAvailable = true;
    private ByteBuffer bb;
    private ReadableByteChannel ch;
    private volatile boolean closed;
    private Charset cs;
    private CharsetDecoder decoder;
    private boolean haveLeftoverChar;
    private InputStream in;
    private char leftoverChar;

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public static StreamDecoder forInputStreamReader(InputStream inputStream, Object obj, String str) throws UnsupportedEncodingException {
        if (str == null) {
            str = Charset.defaultCharset().name();
        }
        try {
            if (Charset.isSupported(str)) {
                return new StreamDecoder(inputStream, obj, Charset.forName(str));
            }
        } catch (IllegalCharsetNameException unused) {
        }
        throw new UnsupportedEncodingException(str);
    }

    public static StreamDecoder forInputStreamReader(InputStream inputStream, Object obj, Charset charset) {
        return new StreamDecoder(inputStream, obj, charset);
    }

    public static StreamDecoder forInputStreamReader(InputStream inputStream, Object obj, CharsetDecoder charsetDecoder) {
        return new StreamDecoder(inputStream, obj, charsetDecoder);
    }

    public static StreamDecoder forDecoder(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder, int i) {
        return new StreamDecoder(readableByteChannel, charsetDecoder, i);
    }

    public String getEncoding() {
        if (isOpen()) {
            return encodingName();
        }
        return null;
    }

    public int read() throws IOException {
        return read0();
    }

    private int read0() throws IOException {
        synchronized (this.lock) {
            if (this.haveLeftoverChar) {
                this.haveLeftoverChar = false;
                return this.leftoverChar;
            }
            char[] cArr = new char[2];
            int read = read(cArr, 0, 2);
            if (read == -1) {
                return -1;
            }
            if (read != 1) {
                if (read != 2) {
                    return -1;
                }
                this.leftoverChar = cArr[1];
                this.haveLeftoverChar = true;
            }
            return cArr[0];
        }
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        synchronized (this.lock) {
            ensureOpen();
            if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) > cArr.length || i3 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (i2 == 0) {
                return 0;
            }
            if (this.haveLeftoverChar) {
                cArr[i] = this.leftoverChar;
                i++;
                i2--;
                this.haveLeftoverChar = false;
                i4 = (i2 != 0 && implReady()) ? 1 : 0;
                return 1;
            }
            if (i2 == 1) {
                int read0 = read0();
                if (read0 != -1) {
                    cArr[i] = (char) read0;
                    return i4 + 1;
                }
                if (i4 == 0) {
                    i4 = -1;
                }
                return i4;
            }
            return i4 + implRead(cArr, i, i2 + i);
        }
    }

    public boolean ready() throws IOException {
        boolean z;
        synchronized (this.lock) {
            ensureOpen();
            z = this.haveLeftoverChar || implReady();
        }
        return z;
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

    private static FileChannel getChannel(FileInputStream fileInputStream) {
        if (!channelsAvailable) {
            return null;
        }
        try {
            return fileInputStream.getChannel();
        } catch (UnsatisfiedLinkError unused) {
            channelsAvailable = false;
            return null;
        }
    }

    StreamDecoder(InputStream inputStream, Object obj, Charset charset) {
        this(inputStream, obj, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
    }

    StreamDecoder(InputStream inputStream, Object obj, CharsetDecoder charsetDecoder) {
        super(obj);
        this.haveLeftoverChar = false;
        this.cs = charsetDecoder.charset();
        this.decoder = charsetDecoder;
        if (this.ch == null) {
            this.in = inputStream;
            this.ch = null;
            this.bb = ByteBuffer.allocate(8192);
        }
        this.bb.flip();
    }

    StreamDecoder(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder, int i) {
        this.haveLeftoverChar = false;
        this.in = null;
        this.ch = readableByteChannel;
        this.decoder = charsetDecoder;
        this.cs = charsetDecoder.charset();
        if (i < 0) {
            i = 8192;
        } else if (i < 32) {
            i = 32;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        this.bb = allocate;
        allocate.flip();
    }

    private int readBytes() throws IOException {
        this.bb.compact();
        try {
            ReadableByteChannel readableByteChannel = this.ch;
            if (readableByteChannel != null) {
                int read = readableByteChannel.read(this.bb);
                if (read < 0) {
                    return read;
                }
            } else {
                int limit = this.bb.limit();
                int position = this.bb.position();
                int read2 = this.in.read(this.bb.array(), this.bb.arrayOffset() + position, position <= limit ? limit - position : 0);
                if (read2 < 0) {
                    return read2;
                }
                if (read2 == 0) {
                    throw new IOException("Underlying input stream returned zero bytes");
                }
                this.bb.position(position + read2);
            }
            this.bb.flip();
            return this.bb.remaining();
        } finally {
            this.bb.flip();
        }
    }

    int implRead(char[] cArr, int i, int i2) throws IOException {
        CharBuffer wrap = CharBuffer.wrap(cArr, i, i2 - i);
        if (wrap.position() != 0) {
            wrap = wrap.slice();
        }
        boolean z = false;
        while (true) {
            CoderResult decode = this.decoder.decode(this.bb, wrap, z);
            if (decode.isUnderflow()) {
                if (z || !wrap.hasRemaining() || (wrap.position() > 0 && !inReady())) {
                    break;
                }
                if (readBytes() < 0) {
                    if (wrap.position() == 0 && !this.bb.hasRemaining()) {
                        z = true;
                        break;
                    }
                    this.decoder.reset();
                    z = true;
                } else {
                    continue;
                }
            } else {
                if (decode.isOverflow()) {
                    break;
                }
                decode.throwException();
            }
        }
        if (z) {
            this.decoder.reset();
        }
        if (wrap.position() == 0 && z) {
            return -1;
        }
        return wrap.position();
    }

    /* JADX WARN: Multi-variable type inference failed */
    String encodingName() {
        Charset charset = this.cs;
        if (charset instanceof HistoricallyNamedCharset) {
            return ((HistoricallyNamedCharset) charset).historicalName();
        }
        return charset.name();
    }

    private boolean inReady() {
        try {
            InputStream inputStream = this.in;
            if (inputStream == null || inputStream.available() <= 0) {
                if (!(this.ch instanceof FileChannel)) {
                    return false;
                }
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    boolean implReady() {
        return this.bb.hasRemaining() || inReady();
    }

    void implClose() throws IOException {
        ReadableByteChannel readableByteChannel = this.ch;
        if (readableByteChannel != null) {
            readableByteChannel.close();
        } else {
            this.in.close();
        }
    }
}
