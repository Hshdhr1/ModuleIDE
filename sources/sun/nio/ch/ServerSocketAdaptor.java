package sun.nio.ch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.NotYetBoundException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class ServerSocketAdaptor extends ServerSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ServerSocketChannelImpl ssc;
    private volatile int timeout;

    public static ServerSocket create(ServerSocketChannelImpl serverSocketChannelImpl) {
        try {
            return new ServerSocketAdaptor(serverSocketChannelImpl);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private ServerSocketAdaptor(ServerSocketChannelImpl serverSocketChannelImpl) throws IOException {
        this.ssc = serverSocketChannelImpl;
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        bind(socketAddress, 50);
    }

    public void bind(SocketAddress socketAddress, int i) throws IOException {
        if (socketAddress == null) {
            socketAddress = new InetSocketAddress(0);
        }
        try {
            this.ssc.bind(socketAddress, i);
        } catch (Exception e) {
            Net.translateException(e);
        }
    }

    public InetAddress getInetAddress() {
        InetSocketAddress localAddress = this.ssc.localAddress();
        if (localAddress == null) {
            return null;
        }
        return Net.getRevealedLocalAddress(localAddress).getAddress();
    }

    public int getLocalPort() {
        InetSocketAddress localAddress = this.ssc.localAddress();
        if (localAddress == null) {
            return -1;
        }
        return localAddress.getPort();
    }

    public Socket accept() throws IOException {
        synchronized (this.ssc.blockingLock()) {
            try {
                if (!this.ssc.isBound()) {
                    throw new NotYetBoundException();
                }
                long j = this.timeout;
                if (j == 0) {
                    SocketChannel accept = this.ssc.accept();
                    if (accept == null && !this.ssc.isBlocking()) {
                        throw new IllegalBlockingModeException();
                    }
                    return accept.socket();
                }
                if (!this.ssc.isBlocking()) {
                    throw new IllegalBlockingModeException();
                }
                do {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (this.ssc.pollAccept(j)) {
                        return this.ssc.accept().socket();
                    }
                    j -= System.currentTimeMillis() - currentTimeMillis;
                } while (j > 0);
                throw new SocketTimeoutException();
            } catch (Exception e) {
                Net.translateException(e);
                return null;
            }
        }
    }

    public void close() throws IOException {
        this.ssc.close();
    }

    public ServerSocketChannel getChannel() {
        return this.ssc;
    }

    public boolean isBound() {
        return this.ssc.isBound();
    }

    public boolean isClosed() {
        return !this.ssc.isOpen();
    }

    public void setSoTimeout(int i) throws SocketException {
        this.timeout = i;
    }

    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    public void setReuseAddress(boolean z) throws SocketException {
        try {
            this.ssc.setOption(StandardSocketOptions.SO_REUSEADDR, (Object) Boolean.valueOf(z));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    public boolean getReuseAddress() throws SocketException {
        try {
            return ((Boolean) this.ssc.getOption(StandardSocketOptions.SO_REUSEADDR)).booleanValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return false;
        }
    }

    public String toString() {
        if (!isBound()) {
            return "ServerSocket[unbound]";
        }
        return "ServerSocket[addr=" + getInetAddress() + ",localport=" + getLocalPort() + "]";
    }

    public void setReceiveBufferSize(int i) throws SocketException {
        if (i <= 0) {
            throw new IllegalArgumentException("size cannot be 0 or negative");
        }
        try {
            this.ssc.setOption(StandardSocketOptions.SO_RCVBUF, (Object) Integer.valueOf(i));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    public int getReceiveBufferSize() throws SocketException {
        try {
            return ((Integer) this.ssc.getOption(StandardSocketOptions.SO_RCVBUF)).intValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return -1;
        }
    }
}
