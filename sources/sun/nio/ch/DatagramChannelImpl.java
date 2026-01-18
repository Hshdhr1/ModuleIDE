package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.PortUnreachableException;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import sun.net.ResourceManager;
import sun.net.ext.ExtendedSocketOptions;
import sun.nio.ch.MembershipKeyImpl;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class DatagramChannelImpl extends DatagramChannel implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CLOSING = 2;
    private static final int ST_CONNECTED = 1;
    private static final int ST_KILLED = 4;
    private static final int ST_KILLPENDING = 3;
    private static final int ST_UNCONNECTED = 0;
    private static NativeDispatcher nd = new DatagramDispatcher();
    private InetAddress cachedSenderInetAddress;
    private int cachedSenderPort;
    private final ProtocolFamily family;
    private final FileDescriptor fd;
    private final int fdVal;
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private final ReentrantLock readLock;
    private long readerThread;
    private MembershipRegistry registry;
    private InetSocketAddress remoteAddress;
    private boolean reuseAddressEmulated;
    private SocketAddress sender;
    private DatagramSocket socket;
    private int state;
    private final Object stateLock;
    private final ReentrantLock writeLock;
    private long writerThread;

    private static native void disconnect0(FileDescriptor fileDescriptor, boolean z) throws IOException;

    private static native void initIDs();

    private native int receive0(FileDescriptor fileDescriptor, long j, int i, boolean z) throws IOException;

    private native int send0(boolean z, FileDescriptor fileDescriptor, long j, int i, InetAddress inetAddress, int i2) throws IOException;

    static {
        IOUtil.load();
        initIDs();
    }

    public DatagramChannelImpl(SelectorProvider selectorProvider) throws IOException {
        StandardProtocolFamily standardProtocolFamily;
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        this.stateLock = new Object();
        ResourceManager.beforeUdpCreate();
        try {
            if (Net.isIPv6Available()) {
                standardProtocolFamily = StandardProtocolFamily.INET6;
            } else {
                standardProtocolFamily = StandardProtocolFamily.INET;
            }
            this.family = standardProtocolFamily;
            FileDescriptor socket = Net.socket(standardProtocolFamily, false);
            this.fd = socket;
            this.fdVal = IOUtil.fdVal(socket);
        } catch (IOException e) {
            ResourceManager.afterUdpClose();
            throw e;
        }
    }

    public DatagramChannelImpl(SelectorProvider selectorProvider, ProtocolFamily protocolFamily) throws IOException {
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        this.stateLock = new Object();
        DatagramChannelImpl$$ExternalSyntheticBackport0.m(protocolFamily, "'family' is null");
        if (protocolFamily != StandardProtocolFamily.INET && protocolFamily != StandardProtocolFamily.INET6) {
            throw new UnsupportedOperationException("Protocol family not supported");
        }
        if (protocolFamily == StandardProtocolFamily.INET6 && !Net.isIPv6Available()) {
            throw new UnsupportedOperationException("IPv6 not available");
        }
        ResourceManager.beforeUdpCreate();
        try {
            this.family = protocolFamily;
            FileDescriptor socket = Net.socket(protocolFamily, false);
            this.fd = socket;
            this.fdVal = IOUtil.fdVal(socket);
        } catch (IOException e) {
            ResourceManager.afterUdpClose();
            throw e;
        }
    }

    public DatagramChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor) throws IOException {
        StandardProtocolFamily standardProtocolFamily;
        super(selectorProvider);
        this.readLock = new ReentrantLock();
        this.writeLock = new ReentrantLock();
        Object obj = new Object();
        this.stateLock = obj;
        ResourceManager.beforeUdpCreate();
        if (Net.isIPv6Available()) {
            standardProtocolFamily = StandardProtocolFamily.INET6;
        } else {
            standardProtocolFamily = StandardProtocolFamily.INET;
        }
        this.family = standardProtocolFamily;
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        synchronized (obj) {
            this.localAddress = Net.localAddress(fileDescriptor);
        }
    }

    private void ensureOpen() throws ClosedChannelException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
    }

    public DatagramSocket socket() {
        DatagramSocket datagramSocket;
        synchronized (this.stateLock) {
            if (this.socket == null) {
                this.socket = DatagramSocketAdaptor.create(this);
            }
            datagramSocket = this.socket;
        }
        return datagramSocket;
    }

    public SocketAddress getLocalAddress() throws IOException {
        InetSocketAddress revealedLocalAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            revealedLocalAddress = Net.getRevealedLocalAddress(this.localAddress);
        }
        return revealedLocalAddress;
    }

    public SocketAddress getRemoteAddress() throws IOException {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            ensureOpen();
            inetSocketAddress = this.remoteAddress;
        }
        return inetSocketAddress;
    }

    public DatagramChannel setOption(SocketOption socketOption, Object obj) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (socketOption != StandardSocketOptions.IP_TOS && socketOption != StandardSocketOptions.IP_MULTICAST_TTL && socketOption != StandardSocketOptions.IP_MULTICAST_LOOP) {
                if (socketOption != StandardSocketOptions.IP_MULTICAST_IF) {
                    if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind() && this.localAddress != null) {
                        this.reuseAddressEmulated = true;
                        this.isReuseAddress = ((Boolean) obj).booleanValue();
                    }
                    Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, obj);
                    return this;
                }
                if (obj == null) {
                    throw new IllegalArgumentException("Cannot set IP_MULTICAST_IF to 'null'");
                }
                NetworkInterface networkInterface = (NetworkInterface) obj;
                if (this.family == StandardProtocolFamily.INET6) {
                    int index = networkInterface.getIndex();
                    if (index == -1) {
                        throw new IOException("Network interface cannot be identified");
                    }
                    Net.setInterface6(this.fd, index);
                } else {
                    Inet4Address anyInet4Address = Net.anyInet4Address(networkInterface);
                    if (anyInet4Address == null) {
                        throw new IOException("Network interface not configured for IPv4");
                    }
                    Net.setInterface4(this.fd, Net.inet4AsInt(anyInet4Address));
                }
                return this;
            }
            Net.setSocketOption(this.fd, this.family, socketOption, obj);
            return this;
        }
    }

    public Object getOption(SocketOption socketOption) throws IOException {
        socketOption.getClass();
        if (!supportedOptions().contains(socketOption)) {
            throw new UnsupportedOperationException("'" + socketOption + "' not supported");
        }
        synchronized (this.stateLock) {
            ensureOpen();
            if (socketOption != StandardSocketOptions.IP_TOS && socketOption != StandardSocketOptions.IP_MULTICAST_TTL && socketOption != StandardSocketOptions.IP_MULTICAST_LOOP) {
                if (socketOption == StandardSocketOptions.IP_MULTICAST_IF) {
                    if (this.family == StandardProtocolFamily.INET) {
                        int interface4 = Net.getInterface4(this.fd);
                        if (interface4 == 0) {
                            return null;
                        }
                        NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(Net.inet4FromInt(interface4));
                        if (byInetAddress != null) {
                            return byInetAddress;
                        }
                        throw new IOException("Unable to map address to interface");
                    }
                    int interface6 = Net.getInterface6(this.fd);
                    if (interface6 == 0) {
                        return null;
                    }
                    NetworkInterface byIndex = NetworkInterface.getByIndex(interface6);
                    if (byIndex != null) {
                        return byIndex;
                    }
                    throw new IOException("Unable to map index to interface");
                }
                if (socketOption == StandardSocketOptions.SO_REUSEADDR && this.reuseAddressEmulated) {
                    return Boolean.valueOf(this.isReuseAddress);
                }
                return Net.getSocketOption(this.fd, Net.UNSPEC, socketOption);
            }
            return Net.getSocketOption(this.fd, this.family, socketOption);
        }
    }

    private static class DefaultOptionsHolder {
        static final Set defaultOptions = defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set defaultOptions() {
            HashSet hashSet = new HashSet();
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            if (Net.isReusePortAvailable()) {
                hashSet.add(StandardSocketOptions.SO_REUSEPORT);
            }
            hashSet.add(StandardSocketOptions.SO_BROADCAST);
            hashSet.add(StandardSocketOptions.IP_TOS);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_IF);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_TTL);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_LOOP);
            hashSet.addAll(ExtendedSocketOptions.options((short) 2));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    public final Set supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    private SocketAddress beginRead(boolean z, boolean z2) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            ensureOpen();
            inetSocketAddress = this.remoteAddress;
            if (inetSocketAddress == null && z2) {
                throw new NotYetConnectedException();
            }
            if (this.localAddress == null) {
                bindInternal(null);
            }
            if (z) {
                this.readerThread = NativeThread.current();
            }
        }
        return inetSocketAddress;
    }

    private void endRead(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.readerThread = 0L;
                if (this.state == 2) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0064, code lost:
    
        r1 = r8.flip();
        r13.put(r8);
        r1 = r8;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a9 A[Catch: all -> 0x00b4, TRY_ENTER, TryCatch #1 {all -> 0x00b4, blocks: (B:5:0x000b, B:53:0x0041, B:56:0x0048, B:32:0x0093, B:35:0x009a, B:43:0x00a9, B:46:0x00b0, B:47:0x00b3), top: B:4:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.net.SocketAddress receive(java.nio.ByteBuffer r13) throws java.io.IOException {
        /*
            r12 = this;
            boolean r0 = r13.isReadOnly()
            if (r0 != 0) goto Lbb
            java.util.concurrent.locks.ReentrantLock r0 = r12.readLock
            r0.lock()
            boolean r0 = r12.isBlocking()     // Catch: java.lang.Throwable -> Lb4
            r1 = 0
            r2 = 1
            r3 = 0
            java.net.SocketAddress r4 = r12.beginRead(r0, r3)     // Catch: java.lang.Throwable -> La5
            if (r4 == 0) goto L1a
            r4 = 1
            goto L1b
        L1a:
            r4 = 0
        L1b:
            java.lang.SecurityManager r5 = java.lang.System.getSecurityManager()     // Catch: java.lang.Throwable -> La5
            r6 = -2
            r7 = -3
            if (r4 != 0) goto L79
            if (r5 != 0) goto L26
            goto L79
        L26:
            int r8 = r13.remaining()     // Catch: java.lang.Throwable -> La5
            java.nio.ByteBuffer r8 = sun.nio.ch.Util.getTemporaryDirectBuffer(r8)     // Catch: java.lang.Throwable -> La5
        L2e:
            r9 = 0
        L2f:
            java.io.FileDescriptor r10 = r12.fd     // Catch: java.lang.Throwable -> L76
            int r9 = r12.receive(r10, r8, r4)     // Catch: java.lang.Throwable -> L76
            if (r9 != r7) goto L3d
            boolean r10 = r12.isOpen()     // Catch: java.lang.Throwable -> L76
            if (r10 != 0) goto L2f
        L3d:
            if (r9 != r6) goto L51
            if (r8 == 0) goto L44
            sun.nio.ch.Util.releaseTemporaryDirectBuffer(r8)     // Catch: java.lang.Throwable -> Lb4
        L44:
            if (r9 <= 0) goto L47
            goto L48
        L47:
            r2 = 0
        L48:
            r12.endRead(r0, r2)     // Catch: java.lang.Throwable -> Lb4
            java.util.concurrent.locks.ReentrantLock r13 = r12.readLock
            r13.unlock()
            return r1
        L51:
            java.net.SocketAddress r10 = r12.sender     // Catch: java.lang.Throwable -> L76
            java.net.InetSocketAddress r10 = (java.net.InetSocketAddress) r10     // Catch: java.lang.Throwable -> L76
            java.net.InetAddress r11 = r10.getAddress()     // Catch: java.lang.SecurityException -> L6f java.lang.Throwable -> L76
            java.lang.String r11 = r11.getHostAddress()     // Catch: java.lang.SecurityException -> L6f java.lang.Throwable -> L76
            int r10 = r10.getPort()     // Catch: java.lang.SecurityException -> L6f java.lang.Throwable -> L76
            r5.checkAccept(r11, r10)     // Catch: java.lang.SecurityException -> L6f java.lang.Throwable -> L76
            java.nio.Buffer r1 = r8.flip()     // Catch: java.lang.Throwable -> L76
            java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1     // Catch: java.lang.Throwable -> L76
            r13.put(r8)     // Catch: java.lang.Throwable -> L76
            r1 = r8
            goto L8f
        L6f:
            java.nio.Buffer r10 = r8.clear()     // Catch: java.lang.Throwable -> L76
            java.nio.ByteBuffer r10 = (java.nio.ByteBuffer) r10     // Catch: java.lang.Throwable -> L76
            goto L2e
        L76:
            r13 = move-exception
            r1 = r8
            goto La7
        L79:
            r9 = 0
        L7a:
            java.io.FileDescriptor r5 = r12.fd     // Catch: java.lang.Throwable -> La3
            int r9 = r12.receive(r5, r13, r4)     // Catch: java.lang.Throwable -> La3
            if (r9 != r7) goto L88
            boolean r5 = r12.isOpen()     // Catch: java.lang.Throwable -> La3
            if (r5 != 0) goto L7a
        L88:
            if (r9 != r6) goto L8f
            if (r9 <= 0) goto L8d
            goto L48
        L8d:
            r2 = 0
            goto L48
        L8f:
            java.net.SocketAddress r13 = r12.sender     // Catch: java.lang.Throwable -> La3
            if (r1 == 0) goto L96
            sun.nio.ch.Util.releaseTemporaryDirectBuffer(r1)     // Catch: java.lang.Throwable -> Lb4
        L96:
            if (r9 <= 0) goto L99
            goto L9a
        L99:
            r2 = 0
        L9a:
            r12.endRead(r0, r2)     // Catch: java.lang.Throwable -> Lb4
            java.util.concurrent.locks.ReentrantLock r0 = r12.readLock
            r0.unlock()
            return r13
        La3:
            r13 = move-exception
            goto La7
        La5:
            r13 = move-exception
            r9 = 0
        La7:
            if (r1 == 0) goto Lac
            sun.nio.ch.Util.releaseTemporaryDirectBuffer(r1)     // Catch: java.lang.Throwable -> Lb4
        Lac:
            if (r9 <= 0) goto Laf
            goto Lb0
        Laf:
            r2 = 0
        Lb0:
            r12.endRead(r0, r2)     // Catch: java.lang.Throwable -> Lb4
            throw r13     // Catch: java.lang.Throwable -> Lb4
        Lb4:
            r13 = move-exception
            java.util.concurrent.locks.ReentrantLock r0 = r12.readLock
            r0.unlock()
            throw r13
        Lbb:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Read-only buffer"
            r13.<init>(r0)
            goto Lc4
        Lc3:
            throw r13
        Lc4:
            goto Lc3
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.ch.DatagramChannelImpl.receive(java.nio.ByteBuffer):java.net.SocketAddress");
    }

    private int receive(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, boolean z) throws IOException {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i = position <= limit ? limit - position : 0;
        if ((byteBuffer instanceof DirectBuffer) && i > 0) {
            return receiveIntoNativeBuffer(fileDescriptor, byteBuffer, i, position, z);
        }
        int i2 = i;
        int max = Math.max(i2, 1);
        ByteBuffer temporaryDirectBuffer = Util.getTemporaryDirectBuffer(max);
        try {
            int receiveIntoNativeBuffer = receiveIntoNativeBuffer(fileDescriptor, temporaryDirectBuffer, max, 0, z);
            temporaryDirectBuffer.flip();
            if (receiveIntoNativeBuffer > 0 && i2 > 0) {
                byteBuffer.put(temporaryDirectBuffer);
            }
            return receiveIntoNativeBuffer;
        } finally {
            Util.releaseTemporaryDirectBuffer(temporaryDirectBuffer);
        }
    }

    private int receiveIntoNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int i, int i2, boolean z) throws IOException {
        int receive0 = receive0(fileDescriptor, ((DirectBuffer) byteBuffer).address() + i2, i, z);
        if (receive0 > 0) {
            byteBuffer.position(i2 + receive0);
        }
        return receive0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.Object, java.net.SocketAddress] */
    /* JADX WARN: Type inference failed for: r10v12, types: [int] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v7, types: [int] */
    /* JADX WARN: Type inference failed for: r10v8, types: [int] */
    public int send(ByteBuffer byteBuffer, SocketAddress socketAddress) throws IOException {
        byteBuffer.getClass();
        InetSocketAddress checkAddress = Net.checkAddress(socketAddress, this.family);
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                SocketAddress beginWrite = beginWrite(isBlocking, false);
                try {
                    if (beginWrite != null) {
                        if (!socketAddress.equals(beginWrite)) {
                            throw new AlreadyConnectedException();
                        }
                        do {
                            socketAddress = IOUtil.write(this.fd, byteBuffer, -1L, nd);
                            if (socketAddress != -3) {
                                break;
                            }
                        } while (isOpen());
                    } else {
                        SecurityManager securityManager = System.getSecurityManager();
                        if (securityManager != null) {
                            InetAddress address = checkAddress.getAddress();
                            if (address.isMulticastAddress()) {
                                securityManager.checkMulticast(address);
                            } else {
                                securityManager.checkConnect(address.getHostAddress(), checkAddress.getPort());
                            }
                        }
                        do {
                            socketAddress = send(this.fd, byteBuffer, checkAddress);
                            if (socketAddress != -3) {
                                break;
                            }
                        } while (isOpen());
                    }
                    endWrite(isBlocking, socketAddress > 0);
                    return IOStatus.normalize((int) socketAddress);
                } catch (Throwable th) {
                    th = th;
                    endWrite(isBlocking, socketAddress > 0);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                socketAddress = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    private int send(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, InetSocketAddress inetSocketAddress) throws IOException {
        if (byteBuffer instanceof DirectBuffer) {
            return sendFromNativeBuffer(fileDescriptor, byteBuffer, inetSocketAddress);
        }
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        ByteBuffer temporaryDirectBuffer = Util.getTemporaryDirectBuffer(position <= limit ? limit - position : 0);
        try {
            temporaryDirectBuffer.put(byteBuffer);
            temporaryDirectBuffer.flip();
            byteBuffer.position(position);
            int sendFromNativeBuffer = sendFromNativeBuffer(fileDescriptor, temporaryDirectBuffer, inetSocketAddress);
            if (sendFromNativeBuffer > 0) {
                byteBuffer.position(position + sendFromNativeBuffer);
            }
            return sendFromNativeBuffer;
        } finally {
            Util.releaseTemporaryDirectBuffer(temporaryDirectBuffer);
        }
    }

    private int sendFromNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, InetSocketAddress inetSocketAddress) throws IOException {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int i = position <= limit ? limit - position : 0;
        try {
            i = send0(this.family != StandardProtocolFamily.INET, fileDescriptor, ((DirectBuffer) byteBuffer).address() + position, i, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        } catch (PortUnreachableException e) {
            if (isConnected()) {
                throw e;
            }
        }
        if (i > 0) {
            byteBuffer.position(position + i);
        }
        return i;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        int i;
        byteBuffer.getClass();
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking, true);
                i = 0;
                do {
                    try {
                        i = IOUtil.read(this.fd, byteBuffer, -1L, nd);
                        if (i != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endRead(isBlocking, i > 0);
                        throw th;
                    }
                } while (isOpen());
                endRead(isBlocking, i > 0);
                return IOStatus.normalize(i);
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        DatagramChannelImpl$$ExternalSyntheticBackport1.m(i, i2, byteBufferArr.length);
        this.readLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginRead(isBlocking, true);
                j = 0;
                do {
                    try {
                        j = IOUtil.read(this.fd, byteBufferArr, i, i2, nd);
                        if (j != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endRead(isBlocking, j > 0);
                        throw th;
                    }
                } while (isOpen());
                endRead(isBlocking, j > 0);
                return IOStatus.normalize(j);
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
        } finally {
            this.readLock.unlock();
        }
    }

    private SocketAddress beginWrite(boolean z, boolean z2) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (z) {
            begin();
        }
        synchronized (this.stateLock) {
            ensureOpen();
            inetSocketAddress = this.remoteAddress;
            if (inetSocketAddress == null && z2) {
                throw new NotYetConnectedException();
            }
            if (this.localAddress == null) {
                bindInternal(null);
            }
            if (z) {
                this.writerThread = NativeThread.current();
            }
        }
        return inetSocketAddress;
    }

    private void endWrite(boolean z, boolean z2) throws AsynchronousCloseException {
        if (z) {
            synchronized (this.stateLock) {
                this.writerThread = 0L;
                if (this.state == 2) {
                    this.stateLock.notifyAll();
                }
            }
            end(z2);
        }
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        int i;
        byteBuffer.getClass();
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginWrite(isBlocking, true);
                i = 0;
                do {
                    try {
                        i = IOUtil.write(this.fd, byteBuffer, -1L, nd);
                        if (i != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endWrite(isBlocking, i > 0);
                        throw th;
                    }
                } while (isOpen());
                endWrite(isBlocking, i > 0);
                return IOStatus.normalize(i);
            } catch (Throwable th2) {
                th = th2;
                i = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    public long write(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        long j;
        DatagramChannelImpl$$ExternalSyntheticBackport1.m(i, i2, byteBufferArr.length);
        this.writeLock.lock();
        try {
            boolean isBlocking = isBlocking();
            try {
                beginWrite(isBlocking, true);
                j = 0;
                do {
                    try {
                        j = IOUtil.write(this.fd, byteBufferArr, i, i2, nd);
                        if (j != -3) {
                            break;
                        }
                    } catch (Throwable th) {
                        th = th;
                        endWrite(isBlocking, j > 0);
                        throw th;
                    }
                } while (isOpen());
                endWrite(isBlocking, j > 0);
                return IOStatus.normalize(j);
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
        } finally {
            this.writeLock.unlock();
        }
    }

    protected void implConfigureBlocking(boolean z) throws IOException {
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    ensureOpen();
                    IOUtil.configureBlocking(this.fd, z);
                }
            } finally {
                this.writeLock.unlock();
            }
        } finally {
            this.readLock.unlock();
        }
    }

    InetSocketAddress localAddress() {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            inetSocketAddress = this.localAddress;
        }
        return inetSocketAddress;
    }

    InetSocketAddress remoteAddress() {
        InetSocketAddress inetSocketAddress;
        synchronized (this.stateLock) {
            inetSocketAddress = this.remoteAddress;
        }
        return inetSocketAddress;
    }

    public DatagramChannel bind(SocketAddress socketAddress) throws IOException {
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    ensureOpen();
                    if (this.localAddress != null) {
                        throw new AlreadyBoundException();
                    }
                    bindInternal(socketAddress);
                }
                return this;
            } finally {
                this.writeLock.unlock();
            }
        } finally {
            this.readLock.unlock();
        }
    }

    private void bindInternal(SocketAddress socketAddress) throws IOException {
        InetSocketAddress checkAddress;
        if (socketAddress == null) {
            if (this.family == StandardProtocolFamily.INET) {
                checkAddress = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);
            } else {
                checkAddress = new InetSocketAddress(0);
            }
        } else {
            checkAddress = Net.checkAddress(socketAddress, this.family);
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkListen(checkAddress.getPort());
        }
        Net.bind(this.family, this.fd, checkAddress.getAddress(), checkAddress.getPort());
        this.localAddress = Net.localAddress(this.fd);
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.stateLock) {
            z = true;
            if (this.state != 1) {
                z = false;
            }
        }
        return z;
    }

    public DatagramChannel connect(SocketAddress socketAddress) throws IOException {
        InetSocketAddress checkAddress = Net.checkAddress(socketAddress, this.family);
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            InetAddress address = checkAddress.getAddress();
            if (address.isMulticastAddress()) {
                securityManager.checkMulticast(address);
            } else {
                securityManager.checkConnect(address.getHostAddress(), checkAddress.getPort());
                securityManager.checkAccept(address.getHostAddress(), checkAddress.getPort());
            }
        }
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    ensureOpen();
                    if (this.state == 1) {
                        throw new AlreadyConnectedException();
                    }
                    if (Net.connect(this.family, this.fd, checkAddress.getAddress(), checkAddress.getPort()) <= 0) {
                        throw new Error();
                    }
                    this.remoteAddress = checkAddress;
                    this.state = 1;
                    this.localAddress = Net.localAddress(this.fd);
                    boolean isBlocking = isBlocking();
                    if (isBlocking) {
                        IOUtil.configureBlocking(this.fd, false);
                    }
                    try {
                        ByteBuffer allocate = ByteBuffer.allocate(100);
                        while (receive(allocate) != null) {
                            allocate.clear();
                        }
                    } finally {
                        if (isBlocking) {
                            IOUtil.configureBlocking(this.fd, true);
                        }
                    }
                }
                return this;
            } finally {
                this.writeLock.unlock();
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public DatagramChannel disconnect() throws IOException {
        ReentrantLock reentrantLock;
        this.readLock.lock();
        try {
            this.writeLock.lock();
            try {
                synchronized (this.stateLock) {
                    if (isOpen()) {
                        boolean z = true;
                        if (this.state == 1) {
                            if (this.family != StandardProtocolFamily.INET6) {
                                z = false;
                            }
                            disconnect0(this.fd, z);
                            this.remoteAddress = null;
                            this.state = 0;
                            this.localAddress = Net.localAddress(this.fd);
                            reentrantLock = this.writeLock;
                        } else {
                            reentrantLock = this.writeLock;
                        }
                    } else {
                        reentrantLock = this.writeLock;
                    }
                }
                reentrantLock.unlock();
                return this;
            } catch (Throwable th) {
                this.writeLock.unlock();
                throw th;
            }
        } finally {
            this.readLock.unlock();
        }
    }

    private MembershipKey innerJoin(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) throws IOException {
        Throwable th;
        MembershipRegistry membershipRegistry;
        DatagramChannelImpl datagramChannelImpl;
        MembershipKeyImpl type4;
        if (!inetAddress.isMulticastAddress()) {
            throw new IllegalArgumentException("Group not a multicast address");
        }
        if (inetAddress instanceof Inet4Address) {
            if (this.family == StandardProtocolFamily.INET6 && !Net.canIPv6SocketJoinIPv4Group()) {
                throw new IllegalArgumentException("IPv6 socket cannot join IPv4 multicast group");
            }
        } else if (inetAddress instanceof Inet6Address) {
            if (this.family != StandardProtocolFamily.INET6) {
                throw new IllegalArgumentException("Only IPv6 sockets can join IPv6 multicast group");
            }
        } else {
            throw new IllegalArgumentException("Address type not supported");
        }
        if (inetAddress2 != null) {
            if (inetAddress2.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Source address is a wildcard address");
            }
            if (inetAddress2.isMulticastAddress()) {
                throw new IllegalArgumentException("Source address is multicast address");
            }
            if (inetAddress2.getClass() != inetAddress.getClass()) {
                throw new IllegalArgumentException("Source address is different type to group");
            }
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkMulticast(inetAddress);
        }
        synchronized (this.stateLock) {
            try {
                try {
                    ensureOpen();
                    membershipRegistry = this.registry;
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    if (membershipRegistry == null) {
                        this.registry = new MembershipRegistry();
                    } else {
                        MembershipKey checkMembership = membershipRegistry.checkMembership(inetAddress, networkInterface, inetAddress2);
                        if (checkMembership != null) {
                            return checkMembership;
                        }
                    }
                    if (this.family == StandardProtocolFamily.INET6 && ((inetAddress instanceof Inet6Address) || Net.canJoin6WithIPv4Group())) {
                        int index = networkInterface.getIndex();
                        if (index == -1) {
                            throw new IOException("Network interface cannot be identified");
                        }
                        byte[] inet6AsByteArray = Net.inet6AsByteArray(inetAddress);
                        byte[] inet6AsByteArray2 = inetAddress2 == null ? null : Net.inet6AsByteArray(inetAddress2);
                        if (Net.join6(this.fd, inet6AsByteArray, index, inet6AsByteArray2) == -2) {
                            throw new UnsupportedOperationException();
                        }
                        try {
                            type4 = new MembershipKeyImpl.Type6(this, inetAddress, networkInterface, inetAddress2, inet6AsByteArray, index, inet6AsByteArray2);
                            datagramChannelImpl = this;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            throw th;
                        }
                    } else {
                        datagramChannelImpl = this;
                        Inet4Address anyInet4Address = Net.anyInet4Address(networkInterface);
                        if (anyInet4Address == null) {
                            throw new IOException("Network interface not configured for IPv4");
                        }
                        int inet4AsInt = Net.inet4AsInt(inetAddress);
                        int inet4AsInt2 = Net.inet4AsInt(anyInet4Address);
                        int inet4AsInt3 = inetAddress2 == null ? 0 : Net.inet4AsInt(inetAddress2);
                        if (Net.join4(datagramChannelImpl.fd, inet4AsInt, inet4AsInt2, inet4AsInt3) == -2) {
                            throw new UnsupportedOperationException();
                        }
                        type4 = new MembershipKeyImpl.Type4(datagramChannelImpl, inetAddress, networkInterface, inetAddress2, inet4AsInt, inet4AsInt2, inet4AsInt3);
                    }
                    datagramChannelImpl.registry.add(type4);
                    return type4;
                } catch (Throwable th4) {
                    th = th4;
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        }
    }

    public MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface) throws IOException {
        return innerJoin(inetAddress, networkInterface, null);
    }

    public MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) throws IOException {
        inetAddress2.getClass();
        return innerJoin(inetAddress, networkInterface, inetAddress2);
    }

    void drop(MembershipKeyImpl membershipKeyImpl) {
        synchronized (this.stateLock) {
            if (membershipKeyImpl.isValid()) {
                try {
                    if (membershipKeyImpl instanceof MembershipKeyImpl.Type6) {
                        MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6) membershipKeyImpl;
                        Net.drop6(this.fd, type6.groupAddress(), type6.index(), type6.source());
                    } else {
                        MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4) membershipKeyImpl;
                        Net.drop4(this.fd, type4.groupAddress(), type4.interfaceAddress(), type4.source());
                    }
                    membershipKeyImpl.invalidate();
                    this.registry.remove(membershipKeyImpl);
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            }
        }
    }

    void block(MembershipKeyImpl membershipKeyImpl, InetAddress inetAddress) throws IOException {
        int block4;
        synchronized (this.stateLock) {
            if (!membershipKeyImpl.isValid()) {
                throw new IllegalStateException("key is no longer valid");
            }
            if (inetAddress.isAnyLocalAddress()) {
                throw new IllegalArgumentException("Source address is a wildcard address");
            }
            if (inetAddress.isMulticastAddress()) {
                throw new IllegalArgumentException("Source address is multicast address");
            }
            if (inetAddress.getClass() != membershipKeyImpl.group().getClass()) {
                throw new IllegalArgumentException("Source address is different type to group");
            }
            if (membershipKeyImpl instanceof MembershipKeyImpl.Type6) {
                MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6) membershipKeyImpl;
                block4 = Net.block6(this.fd, type6.groupAddress(), type6.index(), Net.inet6AsByteArray(inetAddress));
            } else {
                MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4) membershipKeyImpl;
                block4 = Net.block4(this.fd, type4.groupAddress(), type4.interfaceAddress(), Net.inet4AsInt(inetAddress));
            }
            if (block4 == -2) {
                throw new UnsupportedOperationException();
            }
        }
    }

    void unblock(MembershipKeyImpl membershipKeyImpl, InetAddress inetAddress) {
        synchronized (this.stateLock) {
            if (!membershipKeyImpl.isValid()) {
                throw new IllegalStateException("key is no longer valid");
            }
            try {
                if (membershipKeyImpl instanceof MembershipKeyImpl.Type6) {
                    MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6) membershipKeyImpl;
                    Net.unblock6(this.fd, type6.groupAddress(), type6.index(), Net.inet6AsByteArray(inetAddress));
                } else {
                    MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4) membershipKeyImpl;
                    Net.unblock4(this.fd, type4.groupAddress(), type4.interfaceAddress(), Net.inet4AsInt(inetAddress));
                }
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    protected void implCloseSelectableChannel() throws IOException {
        boolean isBlocking;
        synchronized (this.stateLock) {
            isBlocking = isBlocking();
            this.state = 2;
            MembershipRegistry membershipRegistry = this.registry;
            if (membershipRegistry != null) {
                membershipRegistry.invalidateAll();
            }
        }
        boolean z = false;
        if (isBlocking) {
            synchronized (this.stateLock) {
                long j = this.readerThread;
                long j2 = this.writerThread;
                if (j != 0 || j2 != 0) {
                    nd.preClose(this.fd);
                    if (j != 0) {
                        NativeThread.signal(j);
                    }
                    if (j2 != 0) {
                        NativeThread.signal(j2);
                    }
                    while (true) {
                        if (this.readerThread == 0 && this.writerThread == 0) {
                            break;
                        }
                        try {
                            this.stateLock.wait();
                        } catch (InterruptedException unused) {
                            z = true;
                        }
                    }
                }
            }
        } else {
            this.readLock.lock();
            try {
                this.writeLock.lock();
                this.writeLock.unlock();
            } finally {
                this.readLock.unlock();
            }
        }
        synchronized (this.stateLock) {
            this.state = 3;
        }
        if (!isRegistered()) {
            kill();
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    public void kill() throws IOException {
        synchronized (this.stateLock) {
            if (this.state == 3) {
                this.state = 4;
                try {
                    nd.close(this.fd);
                    ResourceManager.afterUdpClose();
                } catch (Throwable th) {
                    ResourceManager.afterUdpClose();
                    throw th;
                }
            }
        }
    }

    protected void finalize() throws IOException {
        if (this.fd != null) {
            close();
        }
    }

    public boolean translateReadyOps(int i, int i2, SelectionKeyImpl selectionKeyImpl) {
        int nioInterestOps = selectionKeyImpl.nioInterestOps();
        int nioReadyOps = selectionKeyImpl.nioReadyOps();
        if ((Net.POLLNVAL & i) != 0) {
            return false;
        }
        if (((Net.POLLERR | Net.POLLHUP) & i) != 0) {
            selectionKeyImpl.nioReadyOps(nioInterestOps);
            return ((nioReadyOps ^ (-1)) & nioInterestOps) != 0;
        }
        if ((Net.POLLIN & i) != 0 && (nioInterestOps & 1) != 0) {
            i2 |= 1;
        }
        if ((i & Net.POLLOUT) != 0 && (nioInterestOps & 4) != 0) {
            i2 |= 4;
        }
        selectionKeyImpl.nioReadyOps(i2);
        return ((nioReadyOps ^ (-1)) & i2) != 0;
    }

    public boolean translateAndUpdateReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, selectionKeyImpl.nioReadyOps(), selectionKeyImpl);
    }

    public boolean translateAndSetReadyOps(int i, SelectionKeyImpl selectionKeyImpl) {
        return translateReadyOps(i, 0, selectionKeyImpl);
    }

    boolean pollRead(long j) throws IOException {
        boolean isBlocking = isBlocking();
        this.readLock.lock();
        try {
            try {
                beginRead(isBlocking, false);
                return Net.poll(this.fd, Net.POLLIN, j) != 0;
            } finally {
                endRead(isBlocking, false);
            }
        } finally {
            this.readLock.unlock();
        }
    }

    public int translateInterestOps(int i) {
        int i2 = (i & 1) != 0 ? Net.POLLIN : 0;
        if ((i & 4) != 0) {
            i2 |= Net.POLLOUT;
        }
        return (i & 8) != 0 ? Net.POLLIN | i2 : i2;
    }

    public FileDescriptor getFD() {
        return this.fd;
    }

    public int getFDVal() {
        return this.fdVal;
    }
}
