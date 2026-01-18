package sun.nio.cs;

import jdk.internal.misc.Unsafe;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class StringUTF16 {
    private static final Unsafe unsafe = Unsafe.getUnsafe();

    StringUTF16() {
    }

    public static char getChar(byte[] bArr, int i) {
        return unsafe.getChar(bArr, Unsafe.ARRAY_BYTE_BASE_OFFSET + (Unsafe.ARRAY_BYTE_INDEX_SCALE * i * 2));
    }
}
