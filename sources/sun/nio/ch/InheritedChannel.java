package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class InheritedChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int O_RDONLY = 0;
    private static final int O_RDWR = 2;
    private static final int O_WRONLY = 1;
    private static final int SOCK_DGRAM = 2;
    private static final int SOCK_STREAM = 1;
    private static final int UNKNOWN = -1;
    private static Channel channel = null;
    private static int devnull = -1;
    private static boolean haveChannel = false;

    static /* bridge */ /* synthetic */ void -$$Nest$smdetachIOStreams() {
        detachIOStreams();
    }

    private static native void close0(int i) throws IOException;

    private static native int dup(int i) throws IOException;

    private static native void dup2(int i, int i2) throws IOException;

    private static native void initIDs();

    private static native int open0(String str, int i) throws IOException;

    private static native InetAddress peerAddress0(int i);

    private static native int peerPort0(int i);

    private static native int soType0(int i);

    InheritedChannel() {
    }

    private static void detachIOStreams() {
        try {
            dup2(devnull, 0);
            dup2(devnull, 1);
            dup2(devnull, 2);
        } catch (IOException e) {
            throw new InternalError(e);
        }
    }

    public static class InheritedSocketChannelImpl extends SocketChannelImpl {
        public /* bridge */ /* synthetic */ SocketChannel bind(SocketAddress socketAddress) throws IOException {
            return super.bind(socketAddress);
        }

        public /* bridge */ /* synthetic */ boolean connect(SocketAddress socketAddress) throws IOException {
            return super.connect(socketAddress);
        }

        public /* bridge */ /* synthetic */ boolean finishConnect() throws IOException {
            return super.finishConnect();
        }

        public /* bridge */ /* synthetic */ FileDescriptor getFD() {
            return super.getFD();
        }

        public /* bridge */ /* synthetic */ int getFDVal() {
            return super.getFDVal();
        }

        public /* bridge */ /* synthetic */ SocketAddress getLocalAddress() throws IOException {
            return super.getLocalAddress();
        }

        public /* bridge */ /* synthetic */ Object getOption(SocketOption socketOption) throws IOException {
            return super.getOption(socketOption);
        }

        public /* bridge */ /* synthetic */ SocketAddress getRemoteAddress() throws IOException {
            return super.getRemoteAddress();
        }

        public /* bridge */ /* synthetic */ boolean isConnected() {
            return super.isConnected();
        }

        public /* bridge */ /* synthetic */ boolean isConnectionPending() {
            return super.isConnectionPending();
        }

        public /* bridge */ /* synthetic */ void kill() throws IOException {
            super.kill();
        }

        public /* bridge */ /* synthetic */ int read(ByteBuffer byteBuffer) throws IOException {
            return super.read(byteBuffer);
        }

        public /* bridge */ /* synthetic */ long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
            return super.read(byteBufferArr, i, i2);
        }

        public /* bridge */ /* synthetic */ SocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
            return super.setOption(socketOption, obj);
        }

        public /* bridge */ /* synthetic */ SocketChannel shutdownInput() throws IOException {
            return super.shutdownInput();
        }

        public /* bridge */ /* synthetic */ SocketChannel shutdownOutput() throws IOException {
            return super.shutdownOutput();
        }

        public /* bridge */ /* synthetic */ Socket socket() {
            return super.socket();
        }

        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        public /* bridge */ /* synthetic */ boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndSetReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndUpdateReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ int translateInterestOps(int i) {
            return super.translateInterestOps(i);
        }

        public /* bridge */ /* synthetic */ boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
            return super.translateReadyOps(i, i2, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ int write(ByteBuffer byteBuffer) throws IOException {
            return super.write(byteBuffer);
        }

        public /* bridge */ /* synthetic */ long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
            return super.write(byteBufferArr, i, i2);
        }

        InheritedSocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
            super(selectorProvider, fileDescriptor, inetSocketAddress);
        }

        protected void implCloseSelectableChannel() throws IOException {
            super.implCloseSelectableChannel();
            InheritedChannel.-$$Nest$smdetachIOStreams();
        }
    }

    public static class InheritedServerSocketChannelImpl extends ServerSocketChannelImpl {
        public /* bridge */ /* synthetic */ SocketChannel accept() throws IOException {
            return super.accept();
        }

        public /* bridge */ /* synthetic */ ServerSocketChannel bind(SocketAddress socketAddress, int i) throws IOException {
            return super.bind(socketAddress, i);
        }

        public /* bridge */ /* synthetic */ FileDescriptor getFD() {
            return super.getFD();
        }

        public /* bridge */ /* synthetic */ int getFDVal() {
            return super.getFDVal();
        }

        public /* bridge */ /* synthetic */ SocketAddress getLocalAddress() throws IOException {
            return super.getLocalAddress();
        }

        public /* bridge */ /* synthetic */ Object getOption(SocketOption socketOption) throws IOException {
            return super.getOption(socketOption);
        }

        public /* bridge */ /* synthetic */ void kill() throws IOException {
            super.kill();
        }

        public /* bridge */ /* synthetic */ ServerSocketChannel setOption(SocketOption socketOption, Object obj) throws IOException {
            return super.setOption(socketOption, obj);
        }

        public /* bridge */ /* synthetic */ ServerSocket socket() {
            return super.socket();
        }

        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        public /* bridge */ /* synthetic */ boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndSetReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndUpdateReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ int translateInterestOps(int i) {
            return super.translateInterestOps(i);
        }

        public /* bridge */ /* synthetic */ boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
            return super.translateReadyOps(i, i2, selectionKeyImpl);
        }

        InheritedServerSocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor) throws IOException {
            super(selectorProvider, fileDescriptor, true);
        }

        protected void implCloseSelectableChannel() throws IOException {
            super.implCloseSelectableChannel();
            InheritedChannel.-$$Nest$smdetachIOStreams();
        }
    }

    public static class InheritedDatagramChannelImpl extends DatagramChannelImpl {
        public /* bridge */ /* synthetic */ DatagramChannel bind(SocketAddress socketAddress) throws IOException {
            return super.bind(socketAddress);
        }

        public /* bridge */ /* synthetic */ DatagramChannel connect(SocketAddress socketAddress) throws IOException {
            return super.connect(socketAddress);
        }

        public /* bridge */ /* synthetic */ DatagramChannel disconnect() throws IOException {
            return super.disconnect();
        }

        public /* bridge */ /* synthetic */ FileDescriptor getFD() {
            return super.getFD();
        }

        public /* bridge */ /* synthetic */ int getFDVal() {
            return super.getFDVal();
        }

        public /* bridge */ /* synthetic */ SocketAddress getLocalAddress() throws IOException {
            return super.getLocalAddress();
        }

        public /* bridge */ /* synthetic */ Object getOption(SocketOption socketOption) throws IOException {
            return super.getOption(socketOption);
        }

        public /* bridge */ /* synthetic */ SocketAddress getRemoteAddress() throws IOException {
            return super.getRemoteAddress();
        }

        public /* bridge */ /* synthetic */ boolean isConnected() {
            return super.isConnected();
        }

        public /* bridge */ /* synthetic */ MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface) throws IOException {
            return super.join(inetAddress, networkInterface);
        }

        public /* bridge */ /* synthetic */ MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) throws IOException {
            return super.join(inetAddress, networkInterface, inetAddress2);
        }

        public /* bridge */ /* synthetic */ void kill() throws IOException {
            super.kill();
        }

        public /* bridge */ /* synthetic */ int read(ByteBuffer byteBuffer) throws IOException {
            return super.read(byteBuffer);
        }

        public /* bridge */ /* synthetic */ long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
            return super.read(byteBufferArr, i, i2);
        }

        public /* bridge */ /* synthetic */ SocketAddress receive(ByteBuffer byteBuffer) throws IOException {
            return super.receive(byteBuffer);
        }

        public /* bridge */ /* synthetic */ int send(ByteBuffer byteBuffer, SocketAddress socketAddress) throws IOException {
            return super.send(byteBuffer, socketAddress);
        }

        public /* bridge */ /* synthetic */ DatagramChannel setOption(SocketOption socketOption, Object obj) throws IOException {
            return super.setOption(socketOption, obj);
        }

        public /* bridge */ /* synthetic */ DatagramSocket socket() {
            return super.socket();
        }

        public /* bridge */ /* synthetic */ boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndSetReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
            return super.translateAndUpdateReadyOps(i, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ int translateInterestOps(int i) {
            return super.translateInterestOps(i);
        }

        public /* bridge */ /* synthetic */ boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
            return super.translateReadyOps(i, i2, selectionKeyImpl);
        }

        public /* bridge */ /* synthetic */ int write(ByteBuffer byteBuffer) throws IOException {
            return super.write(byteBuffer);
        }

        public /* bridge */ /* synthetic */ long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
            return super.write(byteBufferArr, i, i2);
        }

        InheritedDatagramChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor) throws IOException {
            super(selectorProvider, fileDescriptor);
        }

        protected void implCloseSelectableChannel() throws IOException {
            super.implCloseSelectableChannel();
            InheritedChannel.-$$Nest$smdetachIOStreams();
        }
    }

    private static void checkAccess(Channel channel2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("inheritedChannel"));
        }
    }

    private static Channel createChannel() throws IOException {
        int dup = dup(0);
        int soType0 = soType0(dup);
        if (soType0 != 1 && soType0 != 2) {
            close0(dup);
            return null;
        }
        FileDescriptor fileDescriptor = (FileDescriptor) Reflect.invoke(Reflect.lookupConstructor("java.io.FileDescriptor", new Class[]{Integer.TYPE}), new Object[]{Integer.valueOf(dup)});
        SelectorProvider provider = SelectorProvider.provider();
        if (soType0 == 1) {
            InetAddress peerAddress0 = peerAddress0(dup);
            if (peerAddress0 == null) {
                return new InheritedServerSocketChannelImpl(provider, fileDescriptor);
            }
            return new InheritedSocketChannelImpl(provider, fileDescriptor, new InetSocketAddress(peerAddress0, peerPort0(dup)));
        }
        return new InheritedDatagramChannelImpl(provider, fileDescriptor);
    }

    public static synchronized Channel getChannel() throws IOException {
        Channel channel2;
        synchronized (InheritedChannel.class) {
            if (devnull < 0) {
                devnull = open0("/dev/null", 2);
            }
            if (!haveChannel) {
                channel = createChannel();
                haveChannel = true;
            }
            Channel channel3 = channel;
            if (channel3 != null) {
                checkAccess(channel3);
            }
            channel2 = channel;
        }
        return channel2;
    }

    static {
        IOUtil.load();
        initIDs();
    }
}
