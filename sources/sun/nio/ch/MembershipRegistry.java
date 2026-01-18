package sun.nio.ch;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MembershipKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class MembershipRegistry {
    private Map groups = null;

    MembershipRegistry() {
    }

    MembershipKey checkMembership(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        List<MembershipKeyImpl> list;
        Map map = this.groups;
        if (map == null || (list = (List) map.get(inetAddress)) == null) {
            return null;
        }
        for (MembershipKeyImpl membershipKeyImpl : list) {
            if (membershipKeyImpl.networkInterface().equals(networkInterface)) {
                if (inetAddress2 == null) {
                    if (membershipKeyImpl.sourceAddress() != null) {
                        throw new IllegalStateException("Already a member to receive all packets");
                    }
                } else {
                    if (membershipKeyImpl.sourceAddress() == null) {
                        throw new IllegalStateException("Already have source-specific membership");
                    }
                    if (inetAddress2.equals(membershipKeyImpl.sourceAddress())) {
                    }
                }
                return membershipKeyImpl;
            }
        }
        return null;
    }

    void add(MembershipKeyImpl membershipKeyImpl) {
        List list;
        InetAddress group = membershipKeyImpl.group();
        Map map = this.groups;
        if (map == null) {
            this.groups = new HashMap();
            list = null;
        } else {
            list = (List) map.get(group);
        }
        if (list == null) {
            list = new LinkedList();
            this.groups.put(group, list);
        }
        list.add(membershipKeyImpl);
    }

    void remove(MembershipKeyImpl membershipKeyImpl) {
        InetAddress group = membershipKeyImpl.group();
        List list = (List) this.groups.get(group);
        if (list != null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next() == membershipKeyImpl) {
                    it.remove();
                    break;
                }
            }
            if (list.isEmpty()) {
                this.groups.remove(group);
            }
        }
    }

    void invalidateAll() {
        Map map = this.groups;
        if (map != null) {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Iterator it2 = ((List) this.groups.get((InetAddress) it.next())).iterator();
                while (it2.hasNext()) {
                    ((MembershipKeyImpl) it2.next()).invalidate();
                }
            }
        }
    }
}
