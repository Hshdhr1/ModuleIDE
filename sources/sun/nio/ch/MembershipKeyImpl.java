package sun.nio.ch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MembershipKey;
import java.nio.channels.MulticastChannel;
import java.util.HashSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class MembershipKeyImpl extends MembershipKey {
    private HashSet blockedSet;
    private final MulticastChannel ch;
    private final InetAddress group;
    private final NetworkInterface interf;
    private volatile boolean invalid;
    private final InetAddress source;
    private final Object stateLock;

    /* synthetic */ MembershipKeyImpl(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, MembershipKeyImpl-IA r5) {
        this(multicastChannel, inetAddress, networkInterface, inetAddress2);
    }

    private MembershipKeyImpl(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        this.stateLock = new Object();
        this.ch = multicastChannel;
        this.group = inetAddress;
        this.interf = networkInterface;
        this.source = inetAddress2;
    }

    static class Type4 extends MembershipKeyImpl {
        private final int groupAddress;
        private final int interfAddress;
        private final int sourceAddress;

        Type4(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, int i, int i2, int i3) {
            super(multicastChannel, inetAddress, networkInterface, inetAddress2, null);
            this.groupAddress = i;
            this.interfAddress = i2;
            this.sourceAddress = i3;
        }

        int groupAddress() {
            return this.groupAddress;
        }

        int interfaceAddress() {
            return this.interfAddress;
        }

        int source() {
            return this.sourceAddress;
        }
    }

    static class Type6 extends MembershipKeyImpl {
        private final byte[] groupAddress;
        private final int index;
        private final byte[] sourceAddress;

        Type6(MulticastChannel multicastChannel, InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, byte[] bArr, int i, byte[] bArr2) {
            super(multicastChannel, inetAddress, networkInterface, inetAddress2, null);
            this.groupAddress = bArr;
            this.index = i;
            this.sourceAddress = bArr2;
        }

        byte[] groupAddress() {
            return this.groupAddress;
        }

        int index() {
            return this.index;
        }

        byte[] source() {
            return this.sourceAddress;
        }
    }

    public boolean isValid() {
        return !this.invalid;
    }

    void invalidate() {
        this.invalid = true;
    }

    public void drop() {
        this.ch.drop(this);
    }

    public MulticastChannel channel() {
        return this.ch;
    }

    public InetAddress group() {
        return this.group;
    }

    public NetworkInterface networkInterface() {
        return this.interf;
    }

    public InetAddress sourceAddress() {
        return this.source;
    }

    public MembershipKey block(InetAddress inetAddress) throws IOException {
        if (this.source != null) {
            throw new IllegalStateException("key is source-specific");
        }
        synchronized (this.stateLock) {
            HashSet hashSet = this.blockedSet;
            if (hashSet != null && hashSet.contains(inetAddress)) {
                return this;
            }
            this.ch.block(this, inetAddress);
            if (this.blockedSet == null) {
                this.blockedSet = new HashSet();
            }
            this.blockedSet.add(inetAddress);
            return this;
        }
    }

    public MembershipKey unblock(InetAddress inetAddress) {
        synchronized (this.stateLock) {
            HashSet hashSet = this.blockedSet;
            if (hashSet == null || !hashSet.contains(inetAddress)) {
                throw new IllegalStateException("not blocked");
            }
            this.ch.unblock(this, inetAddress);
            this.blockedSet.remove(inetAddress);
        }
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append('<');
        sb.append(this.group.getHostAddress());
        sb.append(',');
        sb.append(this.interf.getName());
        if (this.source != null) {
            sb.append(',');
            sb.append(this.source.getHostAddress());
        }
        sb.append('>');
        return sb.toString();
    }
}
