package sun.nio.ch;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public abstract class SelectorProviderImpl extends SelectorProvider {
    public abstract AbstractSelector openSelector() throws IOException;

    public DatagramChannel openDatagramChannel() throws IOException {
        return new DatagramChannelImpl(this);
    }

    public DatagramChannel openDatagramChannel(ProtocolFamily protocolFamily) throws IOException {
        return new DatagramChannelImpl(this, protocolFamily);
    }

    public Pipe openPipe() throws IOException {
        return new PipeImpl(this);
    }

    public ServerSocketChannel openServerSocketChannel() throws IOException {
        return new ServerSocketChannelImpl(this);
    }

    public SocketChannel openSocketChannel() throws IOException {
        return new SocketChannelImpl(this);
    }
}
