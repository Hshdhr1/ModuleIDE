package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Secrets {
    private Secrets() {
    }

    private static SelectorProvider provider() {
        SelectorProvider provider = SelectorProvider.provider();
        if (provider instanceof SelectorProviderImpl) {
            return provider;
        }
        throw new UnsupportedOperationException();
    }

    public static SocketChannel newSocketChannel(FileDescriptor fileDescriptor) {
        try {
            return new SocketChannelImpl(provider(), fileDescriptor, false);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static ServerSocketChannel newServerSocketChannel(FileDescriptor fileDescriptor) {
        try {
            return new ServerSocketChannelImpl(provider(), fileDescriptor, false);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
