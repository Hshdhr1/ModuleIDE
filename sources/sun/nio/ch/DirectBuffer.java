package sun.nio.ch;

import jdk.internal.ref.Cleaner;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DirectBuffer {
    long address();

    Object attachment();

    Cleaner cleaner();
}
