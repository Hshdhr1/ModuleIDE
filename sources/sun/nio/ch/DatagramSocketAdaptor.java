package sun.nio.ch;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.IllegalBlockingModeException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DatagramSocketAdaptor extends DatagramSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final DatagramSocketImpl dummyDatagramSocket = new 1();
    private final DatagramChannelImpl dc;
    private volatile int timeout;

    private DatagramSocketAdaptor(DatagramChannelImpl datagramChannelImpl) throws IOException {
        super(dummyDatagramSocket);
        this.dc = datagramChannelImpl;
    }

    public static DatagramSocket create(DatagramChannelImpl datagramChannelImpl) {
        try {
            return new DatagramSocketAdaptor(datagramChannelImpl);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private void connectInternal(SocketAddress socketAddress) throws SocketException {
        int port = Net.asInetSocketAddress(socketAddress).getPort();
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("connect: " + port);
        }
        if (socketAddress == null) {
            throw new IllegalArgumentException("connect: null address");
        }
        try {
            this.dc.connect(socketAddress);
        } catch (ClosedChannelException unused) {
        } catch (Exception e) {
            Net.translateToSocketException(e);
        }
    }

    public void bind(SocketAddress socketAddress) throws SocketException {
        if (socketAddress == null) {
            try {
                socketAddress = new InetSocketAddress(0);
            } catch (Exception e) {
                Net.translateToSocketException(e);
                return;
            }
        }
        this.dc.bind(socketAddress);
    }

    public void connect(InetAddress inetAddress, int i) {
        try {
            connectInternal(new InetSocketAddress(inetAddress, i));
        } catch (SocketException unused) {
        }
    }

    public void connect(SocketAddress socketAddress) throws SocketException {
        DatagramSocketAdaptor$$ExternalSyntheticBackport0.m(socketAddress, "Address can't be null");
        connectInternal(socketAddress);
    }

    public void disconnect() {
        try {
            this.dc.disconnect();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public boolean isBound() {
        return this.dc.localAddress() != null;
    }

    public boolean isConnected() {
        return this.dc.remoteAddress() != null;
    }

    public InetAddress getInetAddress() {
        InetSocketAddress remoteAddress = this.dc.remoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress();
        }
        return null;
    }

    public int getPort() {
        InetSocketAddress remoteAddress = this.dc.remoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getPort();
        }
        return -1;
    }

    public void send(DatagramPacket datagramPacket) throws IOException {
        synchronized (this.dc.blockingLock()) {
            if (!this.dc.isBlocking()) {
                throw new IllegalBlockingModeException();
            }
            try {
                synchronized (datagramPacket) {
                    ByteBuffer wrap = ByteBuffer.wrap(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                    if (this.dc.isConnected()) {
                        if (datagramPacket.getAddress() == null) {
                            InetSocketAddress remoteAddress = this.dc.remoteAddress();
                            datagramPacket.setPort(remoteAddress.getPort());
                            datagramPacket.setAddress(remoteAddress.getAddress());
                            this.dc.write(wrap);
                        } else {
                            this.dc.send(wrap, datagramPacket.getSocketAddress());
                        }
                    } else {
                        this.dc.send(wrap, datagramPacket.getSocketAddress());
                    }
                }
            } catch (IOException e) {
                Net.translateException(e);
            }
        }
    }

    private SocketAddress receive(ByteBuffer byteBuffer) throws IOException {
        long j = this.timeout;
        if (j == 0) {
            return this.dc.receive(byteBuffer);
        }
        while (this.dc.isOpen()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.dc.pollRead(j)) {
                return this.dc.receive(byteBuffer);
            }
            j -= System.currentTimeMillis() - currentTimeMillis;
            if (j <= 0) {
                throw new SocketTimeoutException();
            }
        }
        throw new ClosedChannelException();
    }

    public void receive(DatagramPacket datagramPacket) throws IOException {
        synchronized (this.dc.blockingLock()) {
            if (!this.dc.isBlocking()) {
                throw new IllegalBlockingModeException();
            }
            try {
                synchronized (datagramPacket) {
                    ByteBuffer wrap = ByteBuffer.wrap(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                    datagramPacket.setSocketAddress(receive(wrap));
                    datagramPacket.setLength(wrap.position() - datagramPacket.getOffset());
                }
            } catch (IOException e) {
                Net.translateException(e);
            }
        }
    }

    public InetAddress getLocalAddress() {
        if (isClosed()) {
            return null;
        }
        InetSocketAddress localAddress = this.dc.localAddress();
        if (localAddress == null) {
            localAddress = new InetSocketAddress(0);
        }
        InetAddress address = localAddress.getAddress();
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return address;
        }
        try {
            securityManager.checkConnect(address.getHostAddress(), -1);
            return address;
        } catch (SecurityException unused) {
            return new InetSocketAddress(0).getAddress();
        }
    }

    public int getLocalPort() {
        if (isClosed()) {
            return -1;
        }
        try {
            InetSocketAddress localAddress = this.dc.localAddress();
            if (localAddress != null) {
                return localAddress.getPort();
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public void setSoTimeout(int i) throws SocketException {
        this.timeout = i;
    }

    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    private void setBooleanOption(SocketOption socketOption, boolean z) throws SocketException {
        try {
            this.dc.setOption(socketOption, (Object) Boolean.valueOf(z));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    private void setIntOption(SocketOption socketOption, int i) throws SocketException {
        try {
            this.dc.setOption(socketOption, (Object) Integer.valueOf(i));
        } catch (IOException e) {
            Net.translateToSocketException(e);
        }
    }

    private boolean getBooleanOption(SocketOption socketOption) throws SocketException {
        try {
            return ((Boolean) this.dc.getOption(socketOption)).booleanValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return false;
        }
    }

    private int getIntOption(SocketOption socketOption) throws SocketException {
        try {
            return ((Integer) this.dc.getOption(socketOption)).intValue();
        } catch (IOException e) {
            Net.translateToSocketException(e);
            return -1;
        }
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

    public void setReuseAddress(boolean z) throws SocketException {
        setBooleanOption(StandardSocketOptions.SO_REUSEADDR, z);
    }

    public boolean getReuseAddress() throws SocketException {
        return getBooleanOption(StandardSocketOptions.SO_REUSEADDR);
    }

    public void setBroadcast(boolean z) throws SocketException {
        setBooleanOption(StandardSocketOptions.SO_BROADCAST, z);
    }

    public boolean getBroadcast() throws SocketException {
        return getBooleanOption(StandardSocketOptions.SO_BROADCAST);
    }

    public void setTrafficClass(int i) throws SocketException {
        setIntOption(StandardSocketOptions.IP_TOS, i);
    }

    public int getTrafficClass() throws SocketException {
        return getIntOption(StandardSocketOptions.IP_TOS);
    }

    public void close() {
        try {
            this.dc.close();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public boolean isClosed() {
        return !this.dc.isOpen();
    }

    public DatagramChannel getChannel() {
        return this.dc;
    }

    class 1 extends DatagramSocketImpl {
        protected void bind(int i, InetAddress inetAddress) throws SocketException {
        }

        protected void close() {
        }

        protected void create() throws SocketException {
        }

        public Object getOption(int i) throws SocketException {
            return null;
        }

        @Deprecated
        protected byte getTTL() throws IOException {
            return (byte) 0;
        }

        protected int getTimeToLive() throws IOException {
            return 0;
        }

        protected void join(InetAddress inetAddress) throws IOException {
        }

        protected void joinGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        }

        protected void leave(InetAddress inetAddress) throws IOException {
        }

        protected void leaveGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        }

        protected int peek(InetAddress inetAddress) throws IOException {
            return 0;
        }

        protected int peekData(DatagramPacket datagramPacket) throws IOException {
            return 0;
        }

        protected void receive(DatagramPacket datagramPacket) throws IOException {
        }

        protected void send(DatagramPacket datagramPacket) throws IOException {
        }

        public void setOption(int i, Object obj) throws SocketException {
        }

        @Deprecated
        protected void setTTL(byte b) throws IOException {
        }

        protected void setTimeToLive(int i) throws IOException {
        }

        1() {
        }
    }
}
