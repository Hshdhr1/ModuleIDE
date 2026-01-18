package org.antlr.v4.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CodePointBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class CharStreams {
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    private CharStreams() {
    }

    public static CharStream fromPath(Path path) throws IOException {
        return fromPath(path, StandardCharsets.UTF_8);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.antlr.v4.runtime.CharStream fromPath(java.nio.file.Path r9, java.nio.charset.Charset r10) throws java.io.IOException {
        /*
            long r6 = java.nio.file.Files.size(r9)
            r2 = 0
            java.nio.file.OpenOption[] r2 = new java.nio.file.OpenOption[r2]
            java.nio.channels.SeekableByteChannel r1 = java.nio.file.Files.newByteChannel(r9, r2)
            r8 = 0
            r3 = 4096(0x1000, float:5.74E-42)
            java.nio.charset.CodingErrorAction r4 = java.nio.charset.CodingErrorAction.REPLACE     // Catch: java.lang.Throwable -> L2a java.lang.Throwable -> L3f
            java.lang.String r5 = r9.toString()     // Catch: java.lang.Throwable -> L2a java.lang.Throwable -> L3f
            r2 = r10
            org.antlr.v4.runtime.CodePointCharStream r2 = fromChannel(r1, r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L2a java.lang.Throwable -> L3f
            if (r1 == 0) goto L20
            if (r8 == 0) goto L26
            r1.close()     // Catch: java.lang.Throwable -> L21
        L20:
            return r2
        L21:
            r0 = move-exception
            r8.addSuppressed(r0)
            goto L20
        L26:
            r1.close()
            goto L20
        L2a:
            r2 = move-exception
            throw r2     // Catch: java.lang.Throwable -> L2c
        L2c:
            r3 = move-exception
            r4 = r2
        L2e:
            if (r1 == 0) goto L35
            if (r4 == 0) goto L3b
            r1.close()     // Catch: java.lang.Throwable -> L36
        L35:
            throw r3
        L36:
            r0 = move-exception
            r4.addSuppressed(r0)
            goto L35
        L3b:
            r1.close()
            goto L35
        L3f:
            r2 = move-exception
            r3 = r2
            r4 = r8
            goto L2e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.v4.runtime.CharStreams.fromPath(java.nio.file.Path, java.nio.charset.Charset):org.antlr.v4.runtime.CharStream");
    }

    public static CharStream fromFileName(String fileName) throws IOException {
        return fromPath(Paths.get(fileName, new String[0]), StandardCharsets.UTF_8);
    }

    public static CharStream fromFileName(String fileName, Charset charset) throws IOException {
        return fromPath(Paths.get(fileName, new String[0]), charset);
    }

    public static CharStream fromStream(InputStream is) throws IOException {
        return fromStream(is, StandardCharsets.UTF_8);
    }

    public static CharStream fromStream(InputStream is, Charset charset) throws IOException {
        return fromStream(is, charset, -1L);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.antlr.v4.runtime.CharStream fromStream(java.io.InputStream r10, java.nio.charset.Charset r11, long r12) throws java.io.IOException {
        /*
            java.nio.channels.ReadableByteChannel r1 = java.nio.channels.Channels.newChannel(r10)
            r8 = 0
            r3 = 4096(0x1000, float:5.74E-42)
            java.nio.charset.CodingErrorAction r4 = java.nio.charset.CodingErrorAction.REPLACE     // Catch: java.lang.Throwable -> L22 java.lang.Throwable -> L37
            java.lang.String r5 = "<unknown>"
            r2 = r11
            r6 = r12
            org.antlr.v4.runtime.CodePointCharStream r2 = fromChannel(r1, r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L22 java.lang.Throwable -> L37
            if (r1 == 0) goto L18
            if (r8 == 0) goto L1e
            r1.close()     // Catch: java.lang.Throwable -> L19
        L18:
            return r2
        L19:
            r0 = move-exception
            r8.addSuppressed(r0)
            goto L18
        L1e:
            r1.close()
            goto L18
        L22:
            r2 = move-exception
            throw r2     // Catch: java.lang.Throwable -> L24
        L24:
            r3 = move-exception
            r4 = r2
        L26:
            if (r1 == 0) goto L2d
            if (r4 == 0) goto L33
            r1.close()     // Catch: java.lang.Throwable -> L2e
        L2d:
            throw r3
        L2e:
            r0 = move-exception
            r4.addSuppressed(r0)
            goto L2d
        L33:
            r1.close()
            goto L2d
        L37:
            r2 = move-exception
            r3 = r2
            r4 = r8
            goto L26
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.v4.runtime.CharStreams.fromStream(java.io.InputStream, java.nio.charset.Charset, long):org.antlr.v4.runtime.CharStream");
    }

    public static CharStream fromChannel(ReadableByteChannel channel) throws IOException {
        return fromChannel(channel, StandardCharsets.UTF_8);
    }

    public static CharStream fromChannel(ReadableByteChannel channel, Charset charset) throws IOException {
        return fromChannel(channel, 4096, CodingErrorAction.REPLACE, "<unknown>");
    }

    public static CodePointCharStream fromReader(Reader r) throws IOException {
        return fromReader(r, "<unknown>");
    }

    public static CodePointCharStream fromReader(Reader r, String sourceName) throws IOException {
        try {
            CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder(4096);
            CharBuffer charBuffer = CharBuffer.allocate(4096);
            while (r.read(charBuffer) != -1) {
                charBuffer.flip();
                codePointBufferBuilder.append(charBuffer);
                charBuffer.compact();
            }
            return CodePointCharStream.fromBuffer(codePointBufferBuilder.build(), sourceName);
        } finally {
            r.close();
        }
    }

    public static CodePointCharStream fromString(String s) {
        return fromString(s, "<unknown>");
    }

    public static CodePointCharStream fromString(String s, String sourceName) {
        CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder(s.length());
        CharBuffer cb = CharBuffer.allocate(s.length());
        cb.put(s);
        cb.flip();
        codePointBufferBuilder.append(cb);
        return CodePointCharStream.fromBuffer(codePointBufferBuilder.build(), sourceName);
    }

    public static CodePointCharStream fromChannel(ReadableByteChannel channel, int bufferSize, CodingErrorAction decodingErrorAction, String sourceName) throws IOException {
        return fromChannel(channel, StandardCharsets.UTF_8, bufferSize, decodingErrorAction, sourceName, -1L);
    }

    public static CodePointCharStream fromChannel(ReadableByteChannel channel, Charset charset, int bufferSize, CodingErrorAction decodingErrorAction, String sourceName, long inputSize) throws IOException {
        try {
            ByteBuffer utf8BytesIn = ByteBuffer.allocate(bufferSize);
            CharBuffer utf16CodeUnitsOut = CharBuffer.allocate(bufferSize);
            if (inputSize == -1) {
                inputSize = bufferSize;
            } else if (inputSize > 2147483647L) {
                throw new IOException(String.format("inputSize %d larger than max %d", new Object[]{Long.valueOf(inputSize), Integer.MAX_VALUE}));
            }
            CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder((int) inputSize);
            CharsetDecoder decoder = charset.newDecoder().onMalformedInput(decodingErrorAction).onUnmappableCharacter(decodingErrorAction);
            boolean endOfInput = false;
            while (!endOfInput) {
                int bytesRead = channel.read(utf8BytesIn);
                endOfInput = bytesRead == -1;
                utf8BytesIn.flip();
                CoderResult result = decoder.decode(utf8BytesIn, utf16CodeUnitsOut, endOfInput);
                if (result.isError() && decodingErrorAction.equals(CodingErrorAction.REPORT)) {
                    result.throwException();
                }
                utf16CodeUnitsOut.flip();
                codePointBufferBuilder.append(utf16CodeUnitsOut);
                utf8BytesIn.compact();
                utf16CodeUnitsOut.compact();
            }
            CoderResult flushResult = decoder.flush(utf16CodeUnitsOut);
            if (flushResult.isError() && decodingErrorAction.equals(CodingErrorAction.REPORT)) {
                flushResult.throwException();
            }
            utf16CodeUnitsOut.flip();
            codePointBufferBuilder.append(utf16CodeUnitsOut);
            CodePointBuffer codePointBuffer = codePointBufferBuilder.build();
            return CodePointCharStream.fromBuffer(codePointBuffer, sourceName);
        } finally {
            channel.close();
        }
    }
}
