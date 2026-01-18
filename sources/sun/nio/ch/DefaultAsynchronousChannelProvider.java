package sun.nio.ch;

import java.nio.channels.spi.AsynchronousChannelProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DefaultAsynchronousChannelProvider {
    private DefaultAsynchronousChannelProvider() {
    }

    public static AsynchronousChannelProvider create() {
        return new LinuxAsynchronousChannelProvider();
    }
}
