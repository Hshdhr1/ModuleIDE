package sun.nio.ch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketOption;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.TimeUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class SocketAdaptor extends Socket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final SocketChannelImpl sc;
    private InputStream socketInputStream;
    private volatile int timeout;

    static /* bridge */ /* synthetic */ SocketChannelImpl -$$Nest$fgetsc(SocketAdaptor socketAdaptor) {
        return socketAdaptor.sc;
    }

    static /* bridge */ /* synthetic */ int -$$Nest$fgettimeout(SocketAdaptor socketAdaptor) {
        return socketAdaptor.timeout;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private SocketAdaptor(SocketChannelImpl socketChannelImpl) throws SocketException {
        super((SocketImpl) null);
        this.socketInputStream = null;
        this.sc = socketChannelImpl;
    }

    public static Socket create(SocketChannelImpl socketChannelImpl) {
        try {
            return new SocketAdaptor(socketChannelImpl);
        } catch (SocketException unused) {
            throw new InternalError("Should not reach here");
        }
    }

    public SocketChannel getChannel() {
        return this.sc;
    }

    public void connect(SocketAddress socketAddress) throws IOException {
        connect(socketAddress, 0);
    }

    public void connect(SocketAddress socketAddress, int i) throws IOException {
        if (socketAddress == null) {
            throw new IllegalArgumentException("connect: The address can't be null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("connect: timeout can't be negative");
        }
        synchronized (this.sc.blockingLock()) {
            if (!this.sc.isBlocking()) {
                throw new IllegalBlockingModeException();
            }
            try {
                if (i == 0) {
                    this.sc.connect(socketAddress);
                    return;
                }
                this.sc.configureBlocking(false);
                try {
                    if (this.sc.connect(socketAddress)) {
                        try {
                            this.sc.configureBlocking(true);
                        } catch (ClosedChannelException unused) {
                        }
                        return;
                    }
                    long j = i;
                    long convert = TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS);
                    while (true) {
                        long nanoTime = System.nanoTime();
                        if (this.sc.pollConnected(j)) {
                            this.sc.finishConnect();
                            break;
                        }
                        convert -= System.nanoTime() - nanoTime;
                        if (convert <= 0) {
                            try {
                                this.sc.close();
                            } catch (IOException unused2) {
                            }
                            throw new SocketTimeoutException();
                        }
                        j = TimeUnit.MILLISECONDS.convert(convert, TimeUnit.NANOSECONDS);
                    }
                } finally {
                    try {
                        this.sc.configureBlocking(true);
                    } catch (ClosedChannelException unused3) {
                    }
                }
            } catch (Exception e) {
                Net.translateException(e, true);
            }
        }
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        try {
            this.sc.bind(socketAddress);
        } catch (Exception e) {
            Net.translateException(e);
        }
    }

    public InetAddress getInetAddress() {
        InetSocketAddress remoteAddress = this.sc.remoteAddress();
        if (remoteAddress == null) {
            return null;
        }
        return remoteAddress.getAddress();
    }

    public InetAddress getLocalAddress() {
        InetSocketAddress localAddress;
        if (this.sc.isOpen() && (localAddress = this.sc.localAddress()) != null) {
            return Net.getRevealedLocalAddress(localAddress).getAddress();
        }
        return new InetSocketAddress(0).getAddress();
    }

    public int getPort() {
        InetSocketAddress remoteAddress = this.sc.remoteAddress();
        if (remoteAddress == null) {
            return 0;
        }
        return remoteAddress.getPort();
    }

    public int getLocalPort() {
        InetSocketAddress localAddress = this.sc.localAddress();
        if (localAddress == null) {
            return -1;
        }
        return localAddress.getPort();
    }

    private class SocketInputStream extends ChannelInputStream {
        /* synthetic */ SocketInputStream(SocketAdaptor socketAdaptor, SocketAdaptor-IA r2) {
            this();
        }

        private SocketInputStream() {
            super(SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this));
        }

        protected int read(ByteBuffer byteBuffer) throws IOException {
            synchronized (SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this).blockingLock()) {
                if (!SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this).isBlocking()) {
                    throw new IllegalBlockingModeException();
                }
                long j = SocketAdaptor.-$$Nest$fgettimeout(SocketAdaptor.this);
                if (j == 0) {
                    return SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this).read(byteBuffer);
                }
                long convert = TimeUnit.NANOSECONDS.convert(j, TimeUnit.MILLISECONDS);
                while (true) {
                    long nanoTime = System.nanoTime();
                    if (SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this).pollRead(j)) {
                        return SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this).read(byteBuffer);
                    }
                    convert -= System.nanoTime() - nanoTime;
                    if (convert <= 0) {
                        throw new SocketTimeoutException();
                    }
                    j = TimeUnit.MILLISECONDS.convert(convert, TimeUnit.NANOSECONDS);
                }
            }
        }
    }

    public InputStream getInputStream() throws IOException {
        if (!this.sc.isOpen()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.sc.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (!this.sc.isInputOpen()) {
            throw new SocketException("Socket input is shutdown");
        }
        if (this.socketInputStream == null) {
            try {
                this.socketInputStream = (InputStream) AccessController.doPrivileged(new 1());
            } catch (PrivilegedActionException e) {
                throw e.getException();
            }
        }
        return this.socketInputStream;
    }

    class 1 implements PrivilegedExceptionAction {
        1() {
        }

        public InputStream run() throws IOException {
            return new SocketInputStream(SocketAdaptor.this, null);
        }
    }

    public OutputStream getOutputStream() throws IOException {
        if (!this.sc.isOpen()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.sc.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (!this.sc.isOutputOpen()) {
            throw new SocketException("Socket output is shutdown");
        }
        try {
            return (OutputStream) AccessController.doPrivileged(new 2());
        } catch (PrivilegedActionException e) {
            throw e.getException();
        }
    }

    class 2 implements PrivilegedExceptionAction {
        2() {
        }

        public OutputStream run() throws IOException {
            return Channels.newOutputStream(SocketAdaptor.-$$Nest$fgetsc(SocketAdaptor.this));
        }
    }

    private void setBooleanOption(SocketOption socketOption, boolean z) throws SocketException {
        try {
            this.sc.setOption(socketOption, (Object) Boolean.valueOf(z));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    private void setIntOption(SocketOption socketOption, int i) throws SocketException {
        try {
            this.sc.setOption(socketOption, (Object) Integer.valueOf(i));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    private boolean getBooleanOption(SocketOption socketOption) throws SocketException {
        try {
            return ((Boolean) this.sc.getOption(socketOption)).booleanValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return false;
        }
    }

    private int getIntOption(SocketOption socketOption) throws SocketException {
        try {
            return ((Integer) this.sc.getOption(socketOption)).intValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return -1;
        }
    }

    public void setTcpNoDelay(boolean z) throws SocketException {
        setBooleanOption(StandardSocketOptions.TCP_NODELAY, z);
    }

    public boolean getTcpNoDelay() throws SocketException {
        return getBooleanOption(StandardSocketOptions.TCP_NODELAY);
    }

    public void setSoLinger(boolean z, int i) throws SocketException {
        if (!z) {
            i = -1;
        }
        setIntOption(StandardSocketOptions.SO_LINGER, i);
    }

    public int getSoLinger() throws SocketException {
        return getIntOption(StandardSocketOptions.SO_LINGER);
    }

    public void sendUrgentData(int i) throws IOException {
        if (this.sc.sendOutOfBandData((byte) i) == 0) {
            throw new IOException("Socket buffer full");
        }
    }

    public void setOOBInline(boolean z) throws SocketException {
        setBooleanOption(ExtendedSocketOption.SO_OOBINLINE, z);
    }

    public boolean getOOBInline() throws SocketException {
        return getBooleanOption(ExtendedSocketOption.SO_OOBINLINE);
    }

    public void setSoTimeout(int i) throws SocketException {
        if (i < 0) {
            throw new IllegalArgumentException("timeout can't be negative");
        }
        this.timeout = i;
    }

    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    public void setSendBufferSize(int i) throws SocketException {
        if (i <= 0) {
            throw new IllegalArgumentException("Invalid send size");
        }
        setIntOption(StandardSocketOptions.SO_SNDBUF, i);
    }

    public int getSendBufferSize() throws SocketException {
        return getIntOption(StandardSocketOptions.SO_SNDBUF);
    }

    public void setReceiveBufferSize(int i) throws SocketException {
        if (i <= 0) {
            throw new IllegalArgumentException("Invalid receive size");
        }
        setIntOption(StandardSocketOptions.SO_RCVBUF, i);
    }

    public int getReceiveBufferSize() throws SocketException {
        return getIntOption(StandardSocketOptions.SO_RCVBUF);
    }

    public void setKeepAlive(boolean z) throws SocketException {
        setBooleanOption(StandardSocketOptions.SO_KEEPALIVE, z);
    }

    public boolean getKeepAlive() throws SocketException {
        return getBooleanOption(StandardSocketOptions.SO_KEEPALIVE);
    }

    public void setTrafficClass(int i) throws SocketException {
        setIntOption(StandardSocketOptions.IP_TOS, i);
    }

    public int getTrafficClass() throws SocketException {
        return getIntOption(StandardSocketOptions.IP_TOS);
    }

    public void setReuseAddress(boolean z) throws SocketException {
        setBooleanOption(StandardSocketOptions.SO_REUSEADDR, z);
    }

    public boolean getReuseAddress() throws SocketException {
        return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
    }

    public void close() throws IOException {
        this.sc.close();
    }

    public void shutdownInput() throws IOException {
        try {
            this.sc.shutdownInput();
        } catch (Exception e) {
            Net.translateException(e);
        }
    }

    public void shutdownOutput() throws IOException {
        try {
            this.sc.shutdownOutput();
        } catch (Exception e) {
            Net.translateException(e);
        }
    }

    public String toString() {
        if (this.sc.isConnected()) {
            return "Socket[addr=" + getInetAddress() + ",port=" + getPort() + ",localport=" + getLocalPort() + "]";
        }
        return "Socket[unconnected]";
    }

    public boolean isConnected() {
        return this.sc.isConnected();
    }

    public boolean isBound() {
        return this.sc.localAddress() != null;
    }

    public boolean isClosed() {
        return !this.sc.isOpen();
    }

    public boolean isInputShutdown() {
        return !this.sc.isInputOpen();
    }

    public boolean isOutputShutdown() {
        return !this.sc.isOutputOpen();
    }
}
