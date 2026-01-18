package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface SeekableByteChannel extends ByteChannel {
    long position() throws IOException;

    SeekableByteChannel position(long j) throws IOException;

    int read(ByteBuffer byteBuffer) throws IOException;

    long size() throws IOException;

    SeekableByteChannel truncate(long j) throws IOException;

    int write(ByteBuffer byteBuffer) throws IOException;
}
