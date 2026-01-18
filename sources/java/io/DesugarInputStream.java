package java.io;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static long transferTo(InputStream inputStream, OutputStream outputStream) throws IOException {
        DesugarInputStream$$ExternalSyntheticBackport0.m(outputStream, "out");
        byte[] bArr = new byte[8192];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, 8192);
            if (read < 0) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += read;
        }
    }
}
